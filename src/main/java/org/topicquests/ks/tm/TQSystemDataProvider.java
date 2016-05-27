/*
 * Copyright 2015, TopicQuests
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.topicquests.ks.tm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.nex.util.LRUCache;
import org.topicquests.common.ResultPojo;
import org.topicquests.common.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.api.IErrorMessages;
import org.topicquests.ks.api.ITQCoreOntology;
import org.topicquests.ks.api.ITQDataProvider;
import org.topicquests.ks.api.ITicket;
import org.topicquests.ks.tm.api.IMergeImplementation;
import org.topicquests.ks.tm.api.IMergeResultsListener;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.ks.tm.api.ISubjectProxyModel;
import org.topicquests.ks.tm.api.ITreeNode;
import org.topicquests.ks.tm.api.ITuple;
import org.topicquests.ks.tm.api.ITupleQuery;
import org.topicquests.ks.tm.merge.MergeInterceptor;
import org.topicquests.ks.tm.merge.VirtualizerHandler;
import org.topicquests.node.provider.Client;
import org.topicquests.node.provider.ProviderEnvironment;

/**
 * @author park
 *
 */
public class TQSystemDataProvider implements ITQDataProvider {
	private SystemEnvironment environment;
	private ProviderEnvironment provider;
	private Client database;
	private ISubjectProxyModel proxyModel;
	private MergeInterceptor interceptor;
	private ITupleQuery tupleQuery;
	private VirtualizerHandler mergePerformer;
	private CredentialUtility credentialUtility;
	private final String _INDEX = "topics";

	/** We only save public nodes in this cache */
	private LRUCache nodeCache;

	

	/**
	 * 
	 */
	public TQSystemDataProvider(SystemEnvironment env, int cachesize) throws Exception {
		environment = env;
		provider = environment.getProvider();
		database = provider.getClient();
		proxyModel = new SubjectProxyModel(environment, this);
		//interceptor can toss an exception if it has network issues
		interceptor = new MergeInterceptor(environment);
		nodeCache = new LRUCache(cachesize);
		tupleQuery = new TupleQuery(environment, this);
		credentialUtility = new CredentialUtility(environment);

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#init(org.topicquests.ks.SystemEnvironment, int)
	 */
	@Override
	public IResult init(SystemEnvironment env, int cachesize) {
		// NOT USED
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#getSubjectProxyModel()
	 */
	@Override
	public ISubjectProxyModel getSubjectProxyModel() {
		return proxyModel;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#getTupleQuery()
	 */
	@Override
	public ITupleQuery getTupleQuery() {
		return tupleQuery;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#removeFromCache(java.lang.String)
	 */
	@Override
	public void removeFromCache(String nodeLocator) {
		nodeCache.remove(nodeLocator);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#getUUID()
	 */
	@Override
	public String getUUID() {
		return UUID.randomUUID().toString();
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#getUUID_Pre(java.lang.String)
	 */
	@Override
	public String getUUID_Pre(String prefix) {
		return prefix+getUUID();
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#getUUID_Post(java.lang.String)
	 */
	@Override
	public String getUUID_Post(String suffix) {
		return getUUID()+suffix;
	}

	@Override
	public boolean isOptimisticLockError(String errorMessage) {
		int where = errorMessage.indexOf(IErrorMessages.OPTIMISTIC_LOCK_EXCEPTION);
		return (where > -1);
	}

	private List<String>loopStopper = null;

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#loadTree(java.lang.String, int, int, int, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult loadTree(String rootNodeLocator, int maxDepth, int start,
			int count, ITicket credentials) {
		IResult result = new ResultPojo();
		loopStopper = new ArrayList<String>();
		//Get the root node
		IResult r = this.getNode(rootNodeLocator, credentials);
		if (r.hasError())
			result.addErrorString(r.getErrorString());
		ITreeNode root = new TreeNode(rootNodeLocator);
		ISubjectProxy n = (ISubjectProxy)r.getResultObject();
		String label = n.getLabel("en");
		root.setNodeLabel(label);
		result.setResultObject(root);
		//now populate its child nodes
		recursiveWalkDownTree(result,root,maxDepth,maxDepth,start,count,credentials);
		//environment.logDebug("JSONDocStoreTopicMapProvider.loadTree "+rootNodeLocator+" "+root.getSubclassCount()+" "+root.getInstanceCount());
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#getNode(java.lang.String, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult getNode(String locator, ITicket credentials) {
		Object nx = nodeCache.get(locator);
		IResult result = null;
		if (nx != null) {
			result = new ResultPojo();
			result.setResultObject(nx);
		} else {
			result = database.getNodeAsJSONObject(locator, _INDEX);
			JSONObject jo = (JSONObject)result.getResultObject();
			System.out.println("GETNODE "+locator+" "+jo);
			result.setResultObject(null); //default in case bad credentials
			if (jo != null) {

				ISubjectProxy n = new SubjectProxy(jo);
				int what = this.checkCredentials(n, credentials);
				if (what == 1) {
					result.setResultObject(n);
					if (!n.getIsPrivate())
						nodeCache.add(locator, n);
				} else if (what == 0){
					result.addErrorString(IErrorMessages.INSUFFICIENT_CREDENTIALS);
				} else {
					result.addErrorString(IErrorMessages.NODE_REMOVED);
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#getNodeByURL(java.lang.String, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult getNodeByURL(String url, ITicket credentials) {
		IResult result = new ResultPojo();
		String q = getMatchQuery(ITQCoreOntology.RESOURCE_URL_PROPERTY, url);
		IResult r = database.listObjectsByQuery(q, _INDEX);
		List<JSONObject> l = (List<JSONObject>)r.getResultObject();
		if (l != null) {
			JSONObject jo = l.get(0);
			ISubjectProxy n = new SubjectProxy(jo);
			int what = this.checkCredentials(n, credentials);
			if (what == 1) {
				result.setResultObject(n);
				if (!n.getIsPrivate())
					nodeCache.add(n.getLocator(), n);
			} else if (what == 0){
				result.addErrorString(IErrorMessages.INSUFFICIENT_CREDENTIALS);
			} else {
				result.addErrorString(IErrorMessages.NODE_REMOVED);
			}

		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#putTuple(org.topicquests.ks.tm.api.ITuple, boolean)
	 */
	@Override
	public IResult putTuple(ITuple tuple, boolean checkVersion) {
		return putNode(tuple);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#getTuple(java.lang.String, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult getTuple(String tupleLocator, ITicket credentials) {
		return getNode(tupleLocator, credentials);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#updateNode(org.topicquests.ks.tm.api.ISubjectProxy, boolean)
	 */
	@Override
	public IResult updateNode(ISubjectProxy node, boolean checkVersion) {
		IResult result = database.updateFullNode(node.getLocator(), _INDEX, 
				node.getData(), checkVersion);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#getNodeJSON(java.lang.String, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult getNodeJSON(String locator, ITicket credentials) {
		IResult result = getNode(locator, credentials);
		if (result.getResultObject() != null) {
			ISubjectProxy n = (ISubjectProxy)result.getResultObject();
			result.setResultObject(n.getData());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#getVirtualNodeIfExists(org.topicquests.ks.tm.api.ISubjectProxy, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult getVirtualNodeIfExists(ISubjectProxy node,
			ITicket credentials) {
		IResult result = new ResultPojo();
		List<String> tups = node.listMergeTupleLocators();
		//a merged node should have just one-- that is tups.size() == 1
		//a virtualnode could have many
		if (node.getIsVirtualProxy() ) {
			result.setResultObject(node);
		} else if (tups != null) {
			String tuplox = tups.get(0);
			IResult r = this.getNode(tuplox, credentials);
			ITuple tup = (ITuple)r.getResultObject();
			if (r.hasError()) result.addErrorString(r.getErrorString());
			//this ITuple has this node as object, virtualnode as subject
			//see BaseVirtualizer.relateNodes
			tuplox = tup.getSubjectLocator();
			r = this.getNode(tuplox, credentials);
			result.setResultObject(r.getResultObject());
			if (r.hasError()) result.addErrorString(r.getErrorString());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#existsTupleBySubjectOrObjectAndRelation(java.lang.String, java.lang.String)
	 */
	@Override
	public IResult existsTupleBySubjectOrObjectAndRelation(String theLocator,
			String relationLocator) {
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, theLocator);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_OBJECT_PROPERTY, theLocator);
		QueryBuilder qb3 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, relationLocator);
		qb.must(qb3);
		qb.should(qb1);
		qb.should(qb2);
		//NOTE we are not crafting start, count here
		//environment.logDebug("JSONDocStoreTopicMapProvider.existsTupleBySubjectOrObjectAndRelation- "+qb.toString());
		IResult result =  database.listObjectsByQuery(qb.toString(), _INDEX);
				//jsonModel.runQuery(TOPIC_INDEX, qb, 0, -1, CORE_TYPE);
		if (result.getResultObject() != null)
			result.setResultObject(new Boolean(true));
		else
			result.setResultObject(new Boolean(false));
		return result;	
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#existsNode(java.lang.String)
	 */
	@Override
	public IResult existsNode(String locator) {
		IResult result = database.getNodeAsJSONObject(locator, _INDEX);
		
		if (result.getResultObject() == null)
			result.setResultObject(new Boolean(false));
		else
			result.setResultObject(new Boolean(true));
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#nodeIsA(java.lang.String, java.lang.String, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult nodeIsA(String nodeLocator, String targetTypeLocator,
			ITicket credentials) {
		IResult result = this.getNode(nodeLocator, credentials);
		ISubjectProxy n = (ISubjectProxy)result.getResultObject();
		boolean t = n.isA(targetTypeLocator);
		result.setResultObject(new Boolean(t));
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#getNodeView(java.lang.String, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult getNodeView(String locator, ITicket credentials) {
		IResult result = new ResultPojo();
		// TODO Auto-generated method stub
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#removeNode(java.lang.String, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult removeNode(String locator, ITicket credentials) {
		IResult result = new ResultPojo();
		IResult r = this.getNode(locator, credentials);
		ISubjectProxy n = (ISubjectProxy)r.getResultObject();
		if (r.hasError()) result.addErrorString(r.getErrorString());
		r = removeNode(n, credentials);
		if (r.hasError()) result.addErrorString(r.getErrorString());
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#removeNode(org.topicquests.ks.tm.api.ISubjectProxy, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult removeNode(ISubjectProxy node, ITicket credentials) {
		IResult result = new ResultPojo();
		//first, set not live
		node.setIsLive(false);
		//save it
		IResult r = this.putNode(node);
		//now, deal with its network
		//TODO
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#putNode(org.topicquests.ks.tm.api.ISubjectProxy, boolean)
	 */
	@Override
	public IResult putNode(ISubjectProxy node) {
		IResult result = putNodeNoMerge(node);
		System.out.println("PUTNODE "+node.getLocator()+" "+result.hasError());
		if (!result.hasError()) {
			interceptor.acceptNodeForMerge(node);
			nodeCache.add(node.getLocator(), node);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#putNodeNoMerge(org.topicquests.ks.tm.api.ISubjectProxy, boolean)
	 */
	@Override
	public IResult putNodeNoMerge(ISubjectProxy node) {
		environment.logDebug("TQSystemDataProvider.putNodeNoMerge- "+node.toJSONString());
		IResult result =database.indexNode(node.getLocator(), _INDEX,  node.getData());
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#listNodesByPSI(java.lang.String, int, int, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult listNodesByPSI(String psi, int start, int count,
			ITicket credentials) {
		return this.listNodesByKeyword(ITQCoreOntology.PSI_PROPERTY_TYPE, psi, start, count, credentials);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#listNodesByLabelAndType(java.lang.String, java.lang.String, java.lang.String, int, int, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult listNodesByLabelAndType(String label, String typeLocator,
			String language, int start, int count, ITicket credentials) {
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		String labprop = makeField(ITQCoreOntology.LABEL_PROPERTY, language);
		QueryBuilder qb1 = QueryBuilders.termQuery(labprop, label);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, typeLocator);
		qb.must(qb1);
		qb.should(qb2);
		String q = this.createQueryFromQuery(qb2, start, count);
		//environment.logDebug("JSONDocStoreTopicMapProvider.listNodesByLabelAndType- "+qb.toString());
		IResult result = database.listObjectsByQuery(q, _INDEX);
		if (result.getResultObject() != null) {
			List<JSONObject> l = (List<JSONObject>)result.getResultObject();
			int len = l.size();
			if (len > 0) {
				List<ISubjectProxy>rslt = this.pluckProxies(l, credentials);;
				result.setResultObject(rslt);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#listNodesByLabel(java.lang.String, java.lang.String, int, int, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult listNodesByLabel(String label, String language, int start,
			int count, ITicket credentials) {

		String labprop = makeField(ITQCoreOntology.LABEL_PROPERTY, language);
		return this.listNodesByKeyword(labprop, label, start, count, credentials);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#listNodesByLabelLike(java.lang.String, java.lang.String, int, int, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult listNodesByLabelLike(String labelFragment, String language,
			int start, int count, ITicket credentials) {
		String labprop = makeField(ITQCoreOntology.LABEL_PROPERTY, language);
		QueryBuilder qb = QueryBuilders.wildcardQuery(labprop, labelFragment);
		String q = this.createQueryFromQuery(qb, start, count);
		IResult result = database.listObjectsByQuery(q, _INDEX);
		if (result.getResultObject() != null) {
			List<JSONObject> l = (List<JSONObject>)result.getResultObject();
			int len = l.size();
			if (len > 0) {
				List<ISubjectProxy>rslt = this.pluckProxies(l, credentials);;
				result.setResultObject(rslt);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#listNodesByDetailsLike(java.lang.String, java.lang.String, int, int, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult listNodesByDetailsLike(String detailsFragment,
			String language, int start, int count, ITicket credentials) {
		String detprop = makeField(ITQCoreOntology.DETAILS_PROPERTY, language);
		QueryBuilder qb = QueryBuilders.wildcardQuery(detprop, detailsFragment);
		System.out.println("BBB "+qb);
		String q = this.createQueryFromQuery(qb, start, count);
		System.out.println("ListNodesByDetailsLike "+q);
		IResult result = database.listObjectsByQuery(q, _INDEX);
		if (result.getResultObject() != null) {
			List<JSONObject> l = (List<JSONObject>)result.getResultObject();
			int len = l.size();
			if (len > 0) {
				List<ISubjectProxy>rslt = this.pluckProxies(l, credentials);;
				result.setResultObject(rslt);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#listNodesByQuery(java.lang.String, int, int, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult listNodesByQuery(String queryString, int start, int count,
			ITicket credentials) {
		String q = this.createQueryFromStringQuery(queryString, start, count);
		IResult result = database.listObjectsByQuery(q, _INDEX);
		if (result.getResultObject() != null) {
			List<JSONObject> l = (List<JSONObject>)result.getResultObject();
			int len = l.size();
			if (len > 0) {
				List<ISubjectProxy>rslt = this.pluckProxies(l, credentials);;
				result.setResultObject(rslt);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#listNodesByCreatorId(java.lang.String, int, int, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult listNodesByCreatorId(String creatorId, int start, int count,
			ITicket credentials) {
		return this.listNodesByKeyword(ITQCoreOntology.CREATOR_ID_PROPERTY, creatorId, start, count, credentials);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#listNodesByType(java.lang.String, int, int, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult listNodesByType(String typeLocator, int start, int count,
			ITicket credentials) {
		return this.listNodesByKeyword(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, typeLocator, start, count, credentials);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#listInstanceNodes(java.lang.String, int, int, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult listInstanceNodes(String typeLocator, int start, int count,
			ITicket credentials) {
		IResult result = new ResultPojo();
		String q = this.getMatchQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, typeLocator, start, count);
		IResult r = database.listObjectsByQuery(q, _INDEX);
		List<JSONObject> l = (List<JSONObject>)r.getResultObject();
		if (l != null) {
			List<ISubjectProxy>rslt = this.pluckProxies(l, credentials);
			result.setResultObject(rslt);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#listTrimmedInstanceNodes(java.lang.String, int, int, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult listTrimmedInstanceNodes(String typeLocator, int start,
			int count, ITicket credentials) {
		BoolQueryBuilder qba = QueryBuilders.boolQuery();
		BoolQueryBuilder qbb = QueryBuilders.boolQuery();
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, typeLocator);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.IS_VIRTUAL_PROXY, true);
		qba.must(qb1);
		qba.must(qb2);
		QueryBuilder qb3 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, typeLocator);
		QueryBuilder qb4 = QueryBuilders.wildcardQuery(ITQCoreOntology.MERGE_TUPLE_PROPERTY, "*");
		qbb.must(qb3);
		qbb.mustNot(qb4);
		BoolQueryBuilder qbc = QueryBuilders.boolQuery();
		qbc.should(qba);
		qbc.should(qbb);
		String q = this.createQueryFromQuery(qbc, start, count);
		IResult result = database.listObjectsByQuery(q, _INDEX);
		if (result.getResultObject() != null) {
			List<JSONObject> l = (List<JSONObject>)result.getResultObject();
			int len = l.size();
			if (len > 0) {
				List<ISubjectProxy>rslt = this.pluckProxies(l, credentials);;
				result.setResultObject(rslt);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#listSubclassNodes(java.lang.String, int, int, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult listSubclassNodes(String superclassLocator, int start,
			int count, ITicket credentials) {
		IResult result = new ResultPojo();
		String q = this.getMatchQuery(ITQCoreOntology.SUBCLASS_OF_PROPERTY_TYPE, superclassLocator, start, count);
		IResult r = database.listObjectsByQuery(q, _INDEX);
		List<JSONObject> l = (List<JSONObject>)r.getResultObject();
		if (l != null) {
			List<ISubjectProxy>rslt = this.pluckProxies(l, credentials);
			result.setResultObject(rslt);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#updateProxyFromJSON(java.lang.String)
	 */
	@Override
	public IResult updateProxyFromJSON(JSONObject nodeJSON, boolean checkVersion) {
		IResult result = new ResultPojo();
		if (nodeJSON == null) {
			result.addErrorString(IErrorMessages.BAD_JSON_UPDATE_NODE);
		} else {
			//PUNTING at some risk
			ISubjectProxy p = new SubjectProxy(nodeJSON);
			p.doUpdate();
			result = this.updateNode(p, checkVersion);
			p = null;
		}
		return result;
	}

	@Override
	public IResult updateProxyFromJSON(String jsonString, boolean checkVersion) {
		JSONObject jo = null;
		try {
			jo = (JSONObject)new JSONParser(JSONParser.MODE_JSON_SIMPLE).parse(jsonString);
		} catch (Exception e) {
			environment.logError(e.getMessage(), e);
			e.printStackTrace();
		}
		return updateProxyFromJSON(jo, checkVersion);
	}
	
	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#listNodesByTypeAndURL(java.lang.String, java.lang.String, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult listNodesByTypeAndURL(String type, String url,
			int start, int count, ITicket credentials) {
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		//NOTE this will answer to subclasses and types
		//TODO that's probably wrong
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.SUBCLASS_OF_PROPERTY_TYPE, type);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, type);
		QueryBuilder qb3 = QueryBuilders.termQuery(ITQCoreOntology.RESOURCE_URL_PROPERTY, url);
		qb.must(qb3);
		qb.should(qb1);
		qb.should(qb2);
		String q = this.createQueryFromQuery(qb, start, count);
		IResult result = database.listObjectsByQuery(q, _INDEX);
		if (result.getResultObject() != null) {
			List<JSONObject> l = (List<JSONObject>)result.getResultObject();
			int len = l.size();
			if (len > 0) {
				List<ISubjectProxy>rslt = this.pluckProxies(l, credentials);;
				result.setResultObject(rslt);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#updateNodeLabel(org.topicquests.ks.tm.api.ISubjectProxy, java.lang.String, java.lang.String, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult updateNodeLabel(ISubjectProxy node, String oldLabel,
			String newLabel, boolean checkVersion, ITicket credentials) {
		IResult result = new ResultPojo();
		// TODO Auto-generated method stub
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#mergeTwoProxies(org.topicquests.ks.tm.api.ISubjectProxy, org.topicquests.ks.tm.api.ISubjectProxy, java.lang.String, java.lang.String, org.topicquests.ks.tm.api.IMergeResultsListener)
	 */
	@Override
	public void mergeTwoProxies(ISubjectProxy leftNode,
			ISubjectProxy rightNode, String reason, String userLocator,
			IMergeResultsListener mergeListener) {
		Map<String,Double> mergeData = new HashMap<String,Double>();
		String rx = reason;
		if (rx == null || rx.equals(""))
			rx = "No reason given: user-suggested";
		mergeData.put(rx, 1.0);
		mergePerformer.performMerge(leftNode, rightNode, mergeData, 1.0, userLocator, mergeListener);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#setVirtualizerHandler(org.topicquests.ks.tm.merge.VirtualizerHandler)
	 */
	@Override
	public void setVirtualizerHandler(VirtualizerHandler h) {
		mergePerformer = h;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#setMergeBean(org.topicquests.ks.tm.api.IMergeImplementation)
	 */
	@Override
	public void setMergeBean(IMergeImplementation merger) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.api.ITQDataProvider#runQuery(java.lang.String, int, int, org.topicquests.ks.api.ITicket)
	 */
	@Override
	public IResult runQuery(String queryString, int start, int count,
			ITicket credentials) {
		String q = this.createQueryFromStringQuery(queryString, start, count);
		IResult result = database.listObjectsByQuery(q, _INDEX);
		if (result.getResultObject() != null) {
			List<JSONObject> l = (List<JSONObject>)result.getResultObject();
			int len = l.size();
			if (len > 0) {
				List<ISubjectProxy>rslt = this.pluckProxies(l, credentials);;
				result.setResultObject(rslt);
			}
		}
		return result;
	}
/////////////////////////////////////////
	
	private String getMatchQuery(String key, String value) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery(key, value));
		String q = searchSourceBuilder.toString();
		return q;
	}
	
	private String getMatchQuery(String key, String value, int start, int count) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery(key, value));
		searchSourceBuilder.from(start);
		if (count > -1)
			searchSourceBuilder.size(count);
		String q = searchSourceBuilder.toString();
//		System.out.println("GETMATCHQUERY "+q);
		return q;
	}

	private QueryBuilder startKeywordQuery(String key, String value) {
		QueryBuilder qb = QueryBuilders.termQuery(key, value);
		return qb;
	}
	
	private String createQueryFromQuery(QueryBuilder qb, int start, int count) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(qb);
		searchSourceBuilder.from(start);
		if (count > -1)
			searchSourceBuilder.size(count);
		return searchSourceBuilder.toString();
	}
	private String createQueryFromStringQuery(String qb, int start, int count) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(qb);
		searchSourceBuilder.from(start);
		if (count > -1)
			searchSourceBuilder.size(count);
		return searchSourceBuilder.toString();
	}
	
	/**
	 * <p>The {@link INode} implementation uses this to calculate
	 * which actual label or details field to use depending on language.
	 * We need it for fetching nodes based on label or details</p>
	 * @param fieldBase
	 * @param language
	 * @return
	 */
	private String makeField(String fieldBase, String language) {
		String result = fieldBase;
		if (!language.equals("en"))
			result += language;
		return result;
	}
	
	private List<ISubjectProxy> pluckProxies(List<JSONObject> lp, ITicket credentials) {
		List<ISubjectProxy>rslt = new ArrayList<ISubjectProxy>();
		ISubjectProxy p;
		JSONObject jo;
		int len = lp.size();
		for (int i=0;i<len;i++) {
			jo = lp.get(i);
			System.out.println("FFFF "+jo.toJSONString());
			p = new SubjectProxy(jo);
			int what  =  checkCredentials(p, credentials);
			if (what == 1)
				rslt.add(p);		
		}
		return rslt;
	}

	private void recursiveWalkDownTree(IResult result, ITreeNode root, 
			int maxDepth, int curDepth, int start, int count, ITicket credentials) {
		//stopping rule
		if (curDepth == 0)
			return;
		//Given this root, grab its children, then recurse on them
		String lox = root.getNodeLocator();
		if (loopStopper.contains(lox))
			return;
		loopStopper.add(lox);
		//Note: the day will come when -1 will bite us in the butt due to huge
		//collections
		IResult r = this.listSubclassNodes(lox, start, count, credentials);
		if (r.hasError())
			result.addErrorString(r.getErrorString());
		List<ISubjectProxy>kids = null;
		Iterator<ISubjectProxy>itr;
		ISubjectProxy snapper;
		ITreeNode child;
		if (r.getResultObject() != null) {
			kids = (List<ISubjectProxy>)r.getResultObject();
			itr = kids.iterator();
			while (itr.hasNext()) {
				//get the kid
				snapper = itr.next();
				child = new TreeNode(snapper.getLocator(), snapper.getLabel("en"));
				root.addSubclassChild(child);
				//now populate it
				recursiveWalkDownTree(result,child,maxDepth, --curDepth, start, count, credentials);
			}
		}
		r = this.listInstanceNodes(lox, 0, 200, credentials);
		if (r.hasError())
			result.addErrorString(r.getErrorString());
		if (r.getResultObject() != null) {
			kids = (List<ISubjectProxy>)r.getResultObject();
			itr = kids.iterator();
			while (itr.hasNext()) {
				//get the kid
				snapper = itr.next();
				child = new TreeNode(snapper.getLocator(), snapper.getLabel("en"));
				root.addInstanceChild(child);
				//now populate it
				recursiveWalkDownTree(result,child,maxDepth, --curDepth,start,count,credentials);
			}
		}
		
	}
	
	private IResult listNodesByKeyword(String key, String value, int start, int count, ITicket credentials) {
		//String q = this.getMatchQuery(key, value);
		QueryBuilder qb1 = QueryBuilders.termQuery(key, value);

		System.out.println("AAA "+qb1.toString());
		String q = this.createQueryFromQuery(qb1, start, count);
		System.out.println("ListNodesByKeyword "+q);
		IResult result = database.listObjectsByQuery(q, _INDEX);
		if (result.getResultObject() != null) {
			List<JSONObject> l = (List<JSONObject>)result.getResultObject();
			int len = l.size();
			if (len > 0) {
				List<ISubjectProxy>rslt = this.pluckProxies(l, credentials);;
				result.setResultObject(rslt);
			}
		}
		return result;
	}

	@Override
	public IResult multiGetNodes(List<String> locators, ITicket credentials) {
		IResult result = database.multiGetNodes(locators, _INDEX);
		return null;
	}
	/**
	 * <p>Return <code>1</code> if sufficient <code>credentials</code>
	 * to allow viewing this <code>node></p>
	 * <p>Return <code>0</code> if not sufficient <code>credentials</code></p>
	 * <p>Return <code>-1</code> if node has been removed: (isAlive = false)</p>
	 * @param node
	 * @param credentials
	 * @return
	 */
	private int checkCredentials(ISubjectProxy node, ITicket credentials) {
		return credentialUtility.checkCredentials(node, credentials);
	}

	public void shutDown() {
		interceptor.shutDown();
	}

}
