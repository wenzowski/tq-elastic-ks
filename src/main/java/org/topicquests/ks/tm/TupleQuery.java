/*
 * Copyright 2013, TopicQuests
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

import java.util.*;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import org.topicquests.support.ResultPojo;
import org.topicquests.support.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.api.ITQCoreOntology;
import org.topicquests.ks.api.ITQDataProvider;
import org.topicquests.ks.api.ITicket;
import org.topicquests.ks.tm.api.ITuple;
import org.topicquests.ks.tm.api.ITupleQuery;
import org.topicquests.support.util.LoggingPlatform;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

/**
 * @author park
 *
 */
public class TupleQuery implements ITupleQuery {
	private SystemEnvironment environment;
	private ITQDataProvider database;
	//private IJSONDocStoreModel jsonModel;
	//private CredentialUtility credentialUtil;
//	private JSONParser parser;
	//private final String
		//defined in jsonblobstore-props.xml
	//	TOPIC_INDEX		= IJSONTopicMapOntology.TOPIC_INDEX,
	//	CORE_TYPE		= IJSONTopicMapOntology.CORE_TYPE;

	/**
	 * 
	 */
	public TupleQuery(SystemEnvironment env, ITQDataProvider d) {
		environment = env;
		database = d;
	//	jsonModel = j;
//		parser = new JSONParser();
	//	credentialUtil = new CredentialUtility(database,jsonModel);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.ITupleQuery#listObjectNodesByRelationAndObjectRole(java.lang.String, java.lang.String, int, int, java.util.Set)
	 */
	@Override
	public IResult listObjectNodesByRelationAndObjectRole(String relationLocator, String objectRoleLocator, int start, int count, ITicket credentials) {
		BoolQueryBuilder qba = QueryBuilders.boolQuery();
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, relationLocator);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_OBJECT_ROLE_PROPERTY, objectRoleLocator);
		qba.must(qb1);
		qba.must(qb2);		
		environment.logDebug("TupleQuery.listObjectNodesByRelationAndObjectRole- "+qba.toString());
		IResult result =  this.pluckNodes(qba, ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, start, credentials, count);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.ITupleQuery#listObjectNodesByRelationAndSubjectRole(java.lang.String, java.lang.String, int, int, java.util.Set)
	 */
	@Override
	public IResult listObjectNodesByRelationAndSubjectRole(String relationLocator, String subjectRoleLocator, int start, int count, ITicket credentials) {
		BoolQueryBuilder qba = QueryBuilders.boolQuery();
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, relationLocator);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_SUBJECT_ROLE_PROPERTY, subjectRoleLocator);
		qba.must(qb1);
		qba.must(qb2);		
		environment.logDebug("TupleQuery.listObjectNodesByRelationAndSubjectRole- "+qba.toString());
		IResult result =  this.pluckNodes(qba, ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, start, credentials, count);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.ITupleQuery#listObjectNodesBySubjectAndRelation(java.lang.String, java.lang.String, int, int, java.util.Set)
	 */
	@Override
	public IResult listObjectNodesBySubjectAndRelation(String subjectLocator, String relationLocator, int start, int count, ITicket credentials) {
		BoolQueryBuilder qba = QueryBuilders.boolQuery();
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, relationLocator);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, subjectLocator);
		qba.must(qb1);
		qba.must(qb2);		
		environment.logDebug("TupleQuery.listObjectNodesBySubjectAndRelation- "+qba.toString());
		IResult result =  this.pluckNodes(qba, ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, start, credentials, count);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.ITupleQuery#listObjectNodesBySubjectAndRelationAndScope(java.lang.String, java.lang.String, java.lang.String, int, int, java.util.Set)
	 */
	@Override
	public IResult listObjectNodesBySubjectAndRelationAndScope(String subjectLocator, String relationLocator, String scopeLocator, int start, int count, ITicket credentials) {
		BoolQueryBuilder qba = QueryBuilders.boolQuery();
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, relationLocator);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, subjectLocator);
		QueryBuilder qb3 = QueryBuilders.termQuery(ITQCoreOntology.SCOPE_LIST_PROPERTY_TYPE, scopeLocator);
		qba.must(qb1);
		qba.must(qb2);	
		qba.must(qb3);
		environment.logDebug("TupleQuery.listObjectNodesBySubjectAndRelationAndScope- "+qba.toString());
		IResult result =  this.pluckNodes(qba, ITQCoreOntology.TUPLE_OBJECT_PROPERTY, start, credentials, count);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.ITupleQuery#listSubjectNodesByObjectAndRelation(java.lang.String, java.lang.String, int, int, java.util.Set)
	 */
	@Override
	public IResult listSubjectNodesByObjectAndRelation(String objectLocator, String relationLocator, int start, int count, ITicket credentials) {
		BoolQueryBuilder qba = QueryBuilders.boolQuery();
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, relationLocator);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_OBJECT_PROPERTY, objectLocator);
		qba.must(qb1);
		qba.must(qb2);		
		environment.logDebug("TupleQuery.listSubjectNodesByObjectAndRelation- "+qba.toString());
		IResult result =  this.pluckNodes(qba, ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, start, credentials, count);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.ITupleQuery#listSubjectNodesByObjectAndRelationAndScope(java.lang.String, java.lang.String, java.lang.String, int, int, java.util.Set)
	 */
	@Override
	public IResult listSubjectNodesByObjectAndRelationAndScope(String objectLocator, String relationLocator, String scopeLocator, int start, int count, ITicket credentials) {
		BoolQueryBuilder qba = QueryBuilders.boolQuery();
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, relationLocator);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_OBJECT_PROPERTY, objectLocator);
		QueryBuilder qb3 = QueryBuilders.termQuery(ITQCoreOntology.SCOPE_LIST_PROPERTY_TYPE, scopeLocator);
		qba.must(qb1);
		qba.must(qb2);	
		qba.must(qb3);
		environment.logDebug("TupleQuery.listSubjectNodesByObjectAndRelationAndScope- "+qba.toString());
		IResult result =  this.pluckNodes(qba, ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, start, credentials, count);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.ITupleQuery#listSubjectNodesByRelationAndObjectRole(java.lang.String, java.lang.String, int, int, java.util.Set)
	 */
	@Override
	public IResult listSubjectNodesByRelationAndObjectRole(String relationLocator, String objectRoleLocator, int start, int count, ITicket credentials) {
		BoolQueryBuilder qba = QueryBuilders.boolQuery();
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, relationLocator);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_OBJECT_ROLE_PROPERTY, objectRoleLocator);
		qba.must(qb1);
		qba.must(qb2);		
		environment.logDebug("TupleQuery.listSubjectNodesByRelationAndObjectRole- "+qba.toString());
		IResult result =  this.pluckNodes(qba, ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, start, credentials, count);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.ITupleQuery#listSubjectNodesByRelationAndSubjectRole(java.lang.String, java.lang.String, int, int, java.util.Set)
	 */
	@Override
	public IResult listSubjectNodesByRelationAndSubjectRole(String relationLocator, String subjectRoleLocator, int start, int count, ITicket credentials) {
		BoolQueryBuilder qba = QueryBuilders.boolQuery();
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, relationLocator);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_SUBJECT_ROLE_PROPERTY, subjectRoleLocator);
		qba.must(qb1);
		qba.must(qb2);		
		environment.logDebug("TupleQuery.listSubjectNodesByRelationAndSubjectRole- "+qba.toString());
		IResult result =  this.pluckNodes(qba, ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, start, credentials, count);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.ITupleQuery#listTuplesByObjectLocator(java.lang.String, int, int, java.util.Set)
	 */
	@Override
	public IResult listTuplesByObjectLocator(String objectLocator, int start, int count,
			ITicket  credentials) {
		IResult result = null;
		//TODO
		return result;	
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.ITupleQuery#listTuplesByPredTypeAndObject(java.lang.String, java.lang.String, int, int, java.util.Set)
	 */
	@Override
	public IResult listTuplesByPredTypeAndObject(String predType, String obj, int start, int count, ITicket  credentials) {
		BoolQueryBuilder qba = QueryBuilders.boolQuery();
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, predType);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_OBJECT_PROPERTY, obj);
		qba.must(qb1);
		qba.must(qb2);		
		environment.logDebug("TupleQuery.listTuplesByPredTypeAndObject- "+qba.toString());
		IResult result =  null; /*jsonModel.runQuery(TOPIC_INDEX, qba, 0, -1, CORE_TYPE);
		if (result.getResultObject() != null) {
			result.setResultObject(null);
			List<String>docs = (List<String>)result.getResultObject();
			if (docs != null && !docs.isEmpty()) {
				List<ITuple>tups = new ArrayList<ITuple>();
				result.setResultObject(tups);
				String json;
				ITuple n;
				JSONObject jo;
				Iterator<String>itr = docs.iterator();
				try {
					while(itr.hasNext()) {
						json = itr.next();
						jo = jsonToJSON(json);
						if (credentialUtil.checkCredentials(jo,credentials)) 
							tups.add((ITuple)new Node(jo));
						
					}
				} catch (Exception e) {
					environment.logError(e.getMessage(), e);
					result.addErrorString(e.getMessage());
				}
			}
		} */
		return result;	
	}

	/**
	 * Pluck objects from a list of tuples
	 * @param qb
	 * @param type  subject or object
	 * @param start TODO
	 * @param credentials
	 * @param count TODO
	 * @return <code>null</code> or <code>List<INode></code>
	 */
	private IResult pluckNodes(QueryBuilder qb, String type, int start, ITicket credentials, int count) {
		IResult result = null; /*jsonModel.runQuery(TOPIC_INDEX, qb, 0, -1, CORE_TYPE);
		if (result.getResultObject() != null) {
			result.setResultObject(null);
			List<String>tupleDocs = (List<String>)result.getResultObject();
			try {
				if (tupleDocs != null && !tupleDocs.isEmpty()) {
					String json;
					Iterator<String>itr = tupleDocs.iterator();
					List<INode>nl = new ArrayList<INode>();
					result.setResultObject(nl);
					INode n;
					JSONObject jo;
					IResult r;
					while(itr.hasNext()) {
						json = itr.next();
						jo = jsonToJSON(json);
						r = database.getNode((String)jo.get(type), credentials);
						if (r.hasError())
							result.addErrorString(r.getErrorString());
						if (r.getResultObject() != null)
							nl.add((INode)r.getResultObject());
					}
					
				}
				
			} catch (Exception e) {
				environment.logError(e.getMessage(),e);
				result.addErrorString(e.getMessage());
			}
		} */
		return result;
	}
	

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.ITupleQuery#listTuplesBySubject(java.lang.String, int, int, java.util.Set)
	 */
	@Override
	public IResult listTuplesBySubject(String subjectLocator, int start, int count,
			ITicket  credentials) {
		//listDocumentsByProperty(String index, String key, String value, int start, int count, String... types)
		IResult result = null; /* jsonModel.listDocumentsByProperty(TOPIC_INDEX, ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, 
				subjectLocator, start, count, CORE_TYPE);
		if (result.getResultObject() != null) {
			List<String>docs = (List<String>)result.getResultObject();
			String json;
			Iterator<String>itr = docs.iterator();
			List<INode>nl = new ArrayList<INode>();
			result.setResultObject(nl);
			INode n;
			JSONObject jo;
			try {
				while(itr.hasNext()) {
					json = itr.next();
					jo = jsonToJSON(json);
					if (credentialUtil.checkCredentials(jo,credentials)) 
						nl.add(new Node(jo));
					
				}
			} catch (Exception e) {
				environment.logError(e.getMessage(), e);
				result.addErrorString(e.getMessage());
			}
		} */
		return result;	
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.ITupleQuery#listTuplesBySubjectAndPredType(java.lang.String, java.lang.String, int, int, java.util.Set)
	 */
	@Override
	public IResult listTuplesBySubjectAndPredType(String subjectLocator, String predType, int start, int count, ITicket  credentials) {
		BoolQueryBuilder qba = QueryBuilders.boolQuery();
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, predType);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, subjectLocator);
		qba.must(qb1);
		qba.must(qb2);		
		environment.logDebug("TupleQuery.listTuplesBySubjectAndPredType- "+qba.toString());
		IResult result = null;/* jsonModel.runQuery(TOPIC_INDEX, qba, 0, -1, CORE_TYPE);
		if (result.getResultObject() != null) {
			result.setResultObject(null);
			List<String>docs = (List<String>)result.getResultObject();
			if (docs != null && !docs.isEmpty()) {
				List<ITuple>tups = new ArrayList<ITuple>();
				result.setResultObject(tups);
				String json;
				ITuple n;
				JSONObject jo;
				Iterator<String>itr = docs.iterator();
				try {
					while(itr.hasNext()) {
						json = itr.next();
						jo = jsonToJSON(json);
						if (credentialUtil.checkCredentials(jo,credentials)) 
							tups.add((ITuple)new Node(jo));
						
					}
				} catch (Exception e) {
					environment.logError(e.getMessage(), e);
					result.addErrorString(e.getMessage());
				}
			}
		} */
		return result;	
	}

	private JSONObject jsonToJSON(String json) throws Exception {
		return (JSONObject)new JSONParser(JSONParser.MODE_JSON_SIMPLE).parse(json);
	}

	@Override
	public IResult listTuplesByLabel(String [] labels, int start, int count, ITicket  credentials) {
		IResult result = new ResultPojo();
		BoolQueryBuilder qba =null;
		QueryBuilder qb1 = null;
		QueryBuilder qb2 =null;
		ITuple t;
		IResult r;
		Set<String> subresult = new HashSet<String> ();
		for (String lax: labels) {
			qba = QueryBuilders.boolQuery();
			qb1 = QueryBuilders.termQuery(ITQCoreOntology.LABEL_PROPERTY, lax);
			qb2 = QueryBuilders.wildcardQuery(ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, "*");
			qba.must(qb1);
			qba.must(qb2);	
			environment.logDebug("TupleQuery.listTuplesByLabel- "+qba.toString());
	//		r =  jsonModel.runQuery(TOPIC_INDEX, qba, 0, -1, CORE_TYPE);
	//		if (r.hasError())
	//			result.addErrorString(r.getErrorString());
	//		if (r.getResultObject()!= null)
	//			subresult.addAll((List<String>)r.getResultObject());

		}
		if (!subresult.isEmpty()) {
			List<String>rl = new ArrayList<String>();
			rl.addAll(subresult);
			result.setResultObject(rl);
		}
		return result;
	}

	@Override
	public IResult listTuplesByPredTypeAndObjectOrSubject(String predType,
			String obj, int start, int count, ITicket  credentials) {
		BoolQueryBuilder qba = QueryBuilders.boolQuery();
		QueryBuilder qb1 = QueryBuilders.termQuery(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, predType);
		QueryBuilder qb2 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_OBJECT_PROPERTY, obj);
		QueryBuilder qb3 = QueryBuilders.termQuery(ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, obj);
		qba.must(qb1);
		qba.should(qb2);
		qba.should(qb3);
		environment.logDebug("TupleQuery.listTuplesByPredTypeAndObjectOrSubject- "+qba.toString());
		IResult result = null;/* jsonModel.runQuery(IJSONTopicMapOntology.TOPIC_INDEX, qba, start, count, IJSONTopicMapOntology.CORE_TYPE);
		System.out.println("AAA "+result.getResultObject());
		if (result.getResultObject() != null) {
			List<String>docs = (List<String>)result.getResultObject();
			result.setResultObject(null);
			if (docs != null && !docs.isEmpty()) {
				List<JSONObject>tups = new ArrayList<JSONObject>();
				result.setResultObject(tups);
				String json;
				ITuple n;
				JSONObject jo;
				Iterator<String>itr = docs.iterator();
				try {
					while(itr.hasNext()) {
						json = itr.next();
						jo = jsonToJSON(json);
						if (credentialUtil.checkCredentials(jo,credentials)) 
							tups.add(jo);
						
					}
				} catch (Exception e) {
					environment.logError(e.getMessage(), e);
					result.addErrorString(e.getMessage());
				}
			}
		} */
		return result;	
	 }

}
