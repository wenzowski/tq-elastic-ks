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
import java.util.Iterator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import net.minidev.json.JSONObject;

import org.topicquests.common.ResultPojo;
import org.topicquests.common.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.TicketPojo;
import org.topicquests.ks.api.ITQCoreOntology;
import org.topicquests.ks.api.ITQDataProvider;
import org.topicquests.ks.api.ITicket;
import org.topicquests.ks.tm.api.IMergeImplementation;
import org.topicquests.ks.tm.api.IParentChildContainer;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.ks.tm.api.ISubjectProxyModel;
import org.topicquests.ks.tm.api.ITuple;

/**
 * @author park
 *
 */
public class SubjectProxyModel implements ISubjectProxyModel {
	private SystemEnvironment environment;
	private ITQDataProvider database;
	private IMergeImplementation merger;
	private ITicket credentials;

	/**
	 * 
	 */
	public SubjectProxyModel(SystemEnvironment env, ITQDataProvider db) {
		environment = env;
		database = db;
		credentials = new TicketPojo(ITQCoreOntology.SYSTEM_USER);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#addSuperClass(org.topicquests.node.api.ISubjectProxy, java.lang.String)
	 */
	@Override
	public IResult addSuperClass(ISubjectProxy node, String superClassLocator) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#setNodeType(org.topicquests.node.api.ISubjectProxy, java.lang.String)
	 */
	@Override
	public IResult setNodeType(ISubjectProxy node, String typeLocator) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#newNode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public ISubjectProxy newNode(String locator, String label, String description,
			String lang, String userId, String smallImagePath,
			String largeImagePath, boolean isPrivate) {
		ISubjectProxy result = new SubjectProxy(new JSONObject());
		result.setLocator(locator);
		result.setCreatorId(userId);
		Date d = new Date();
		result.setDate(d); 
		result.setLastEditDate(d);
		if (label != null)
			result.addLabel(label, lang, userId, false);
		
		if (smallImagePath != null)
			result.setSmallImage(smallImagePath);
		if (largeImagePath != null)
			result.setImage(largeImagePath);
		if (description != null)
			result.addDetails(description, lang, userId, false);
		result.setIsPrivate(isPrivate);
		result.setVersion(Long.toString(System.currentTimeMillis()));
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#newNode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public ISubjectProxy newNode(String label, String description, String lang,
			String userId, String smallImagePath, String largeImagePath,
			boolean isPrivate) {
		ISubjectProxy result = newNode(newUUID(),label,description,lang,userId,smallImagePath,largeImagePath,isPrivate);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#newSubclassNode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public ISubjectProxy newSubclassNode(String locator, String superclassLocator,
			String label, String description, String lang, String userId,
			String smallImagePath, String largeImagePath, boolean isPrivate) {
		ISubjectProxy result = newNode(locator,label,description,lang,userId,smallImagePath,largeImagePath,isPrivate);
		result.addSuperclassId(superclassLocator);
		List<String>tc = listTransitiveClosure(superclassLocator);
		tc.add(superclassLocator);
		result.setTransitiveClosure(tc);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#newSubclassNode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public ISubjectProxy newSubclassNode(String superclassLocator, String label,
			String description, String lang, String userId,
			String smallImagePath, String largeImagePath, boolean isPrivate) {
		ISubjectProxy result = newNode(newUUID(),label,description,lang,userId,smallImagePath,largeImagePath,isPrivate);
		result.addSuperclassId(superclassLocator);
		List<String>tc = listTransitiveClosure(superclassLocator);
		tc.add(superclassLocator);
		result.setTransitiveClosure(tc);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#newInstanceNode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public ISubjectProxy newInstanceNode(String locator, String typeLocator,
			String label, String description, String lang, String userId,
			String smallImagePath, String largeImagePath, boolean isPrivate) {
		ISubjectProxy result = newNode(locator,label,description,lang,userId,smallImagePath,largeImagePath,isPrivate);
		result.setNodeType(typeLocator);
		List<String>tc = listTransitiveClosure(typeLocator);
		tc.add(typeLocator);
		result.setTransitiveClosure(tc);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#newInstanceNode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public ISubjectProxy newInstanceNode(String typeLocator, String label,
			String description, String lang, String userId,
			String smallImagePath, String largeImagePath, boolean isPrivate) {
		ISubjectProxy result = newNode(newUUID(),label,description,lang,userId,smallImagePath,largeImagePath,isPrivate);
		result.setNodeType(typeLocator);
		List<String>tc = listTransitiveClosure(typeLocator);
		tc.add(typeLocator);
		result.setTransitiveClosure(tc);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#updateNode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, org.topicquests.node.api.ITicket)
	 */
	@Override
	public IResult updateNode(String nodeLocator, String updatedLabel,
			String updatedDetails, String language, String oldLabel,
			String oldDetails, String userId, boolean isLanguageAddition,
			ITicket credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#changePropertyValue(org.topicquests.node.api.ISubjectProxy, java.lang.String, java.lang.String)
	 */
	@Override
	public IResult changePropertyValue(ISubjectProxy node, String key, String newValue) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#addPropertyValueInList(org.topicquests.node.api.ISubjectProxy, java.lang.String, java.lang.String)
	 */
	@Override
	public IResult addPropertyValueInList(ISubjectProxy node, String key,
			String newValue) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#relateNodes(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, boolean)
	 */
	@Override
	public IResult relateNodes(String sourceNodeLocator,
			String targetNodeLocator, String relationTypeLocator,
			String userId, String smallImagePath, String largeImagePath,
			boolean isTransclude, boolean isPrivate) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#relateExistingNodes(org.topicquests.node.api.ISubjectProxy, org.topicquests.node.api.ISubjectProxy, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, boolean)
	 */
	@Override
	public IResult relateExistingNodes(ISubjectProxy sourceNode, ISubjectProxy targetNode,
			String relationTypeLocator, String userId, String smallImagePath,
			String largeImagePath, boolean isTransclude, boolean isPrivate) {
		IResult result = new ResultPojo();
		String signature = sourceNode.getLocator()+relationTypeLocator+targetNode.getLocator();
		ITuple t = (ITuple)this.newInstanceNode(signature, relationTypeLocator, 
				sourceNode.getLocator()+" "+relationTypeLocator+" "+targetNode.getLocator(), "en", userId, smallImagePath, largeImagePath, isPrivate);
		t.setIsTransclude(isTransclude);
		t.setObject(targetNode.getLocator());
		t.setObjectType(ITQCoreOntology.NODE_TYPE);
		t.setSubjectLocator(sourceNode.getLocator());
		t.setSubjectType(ITQCoreOntology.NODE_TYPE);
//		t.setSignature(signature);
		String tlab = targetNode.getLabel("en");
		if (tlab == null) {
			tlab = targetNode.getSubject("en");
		}
		String slab = sourceNode.getLabel("en");
		if (slab == null) {
			slab = sourceNode.getSubject("en");
		}
		if (isPrivate) {
			sourceNode.addRestrictedRelation(relationTypeLocator, signature, 
					relationTypeLocator, targetNode.getSmallImage(), targetNode.getLocator(), tlab, targetNode.getNodeType(), "t");
			targetNode.addRestrictedRelation(relationTypeLocator,  signature, 
					relationTypeLocator, sourceNode.getSmallImage(), sourceNode.getLocator(), slab, sourceNode.getNodeType(), "s");
		} else {
			sourceNode.addRelation(relationTypeLocator, signature, //TODO must add relation details
					relationTypeLocator, targetNode.getSmallImage(), targetNode.getLocator(), tlab, targetNode.getNodeType(), "t");
			targetNode.addRelation(relationTypeLocator, signature, 
					relationTypeLocator, sourceNode.getSmallImage(), sourceNode.getLocator(), slab, sourceNode.getNodeType(), "s");
		}
		///////////////////////////////////////////
		//TODO
		// WE are now OptimisticLockException sensitive
		// And it might be possible that we need the
		// ability to detect that
		///////////////////////////////////////////
		sourceNode.doUpdate(); // update version
		IResult x = database.updateNode(sourceNode, true);
		if (x.hasError())
			result.addErrorString(x.getErrorString());
		targetNode.doUpdate();
		x = database.updateNode(targetNode, true);
		if (x.hasError())
			result.addErrorString(x.getErrorString());
		database.putNode(t);
		if (x.hasError())
			result.addErrorString(x.getErrorString());
		environment.logDebug("SubjectProxyModel.relateNewNodes "+sourceNode.getLocator()+" "+targetNode.getLocator()+" "+t.getLocator()+" | "+result.getErrorString());
		result.setResultObject(t);
		return result;	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#relateNewNodes(org.topicquests.node.api.ISubjectProxy, org.topicquests.node.api.ISubjectProxy, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, boolean)
	 */
	@Override
	public IResult relateNewNodes(ISubjectProxy sourceNode, ISubjectProxy targetNode,
			String relationTypeLocator, String userId, String smallImagePath,
			String largeImagePath, boolean isTransclude, boolean isPrivate) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#relateExistingNodesAsPivots(org.topicquests.node.api.ISubjectProxy, org.topicquests.node.api.ISubjectProxy, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, boolean)
	 */
	@Override
	public IResult relateExistingNodesAsPivots(ISubjectProxy sourceNode,
			ISubjectProxy targetNode, String relationTypeLocator, String userId,
			String smallImagePath, String largeImagePath, boolean isTransclude,
			boolean isPrivate) {
		IResult result = new ResultPojo();
		String signature = sourceNode.getLocator()+relationTypeLocator+targetNode.getLocator();
		ITuple t = (ITuple)this.newInstanceNode(signature, relationTypeLocator, signature,
				sourceNode.getLocator()+" "+relationTypeLocator+" "+targetNode.getLocator(), "en", userId, smallImagePath, largeImagePath, isPrivate);
		t.setIsTransclude(isTransclude);
		t.setObject(targetNode.getLocator());
		t.setObjectType(ITQCoreOntology.NODE_TYPE);
		t.setSubjectLocator(sourceNode.getLocator());
		t.setSubjectType(ITQCoreOntology.NODE_TYPE);
		String tlab = targetNode.getLabel("en");
		if (tlab == null) {
			tlab = targetNode.getSubject("en");
		}
		String slab = sourceNode.getLabel("en");
		if (slab == null) {
			slab = sourceNode.getSubject("en");
		}
		String tLoc = t.getLocator();
		if (isPrivate) {
			sourceNode.addRestrictedPivot(relationTypeLocator, signature, 
					relationTypeLocator, targetNode.getSmallImage(), targetNode.getLocator(), tlab, targetNode.getNodeType(), "t");
			targetNode.addRestrictedPivot(relationTypeLocator,  signature, 
					relationTypeLocator, sourceNode.getSmallImage(), sourceNode.getLocator(), slab, sourceNode.getNodeType(), "s");
		} else {
			sourceNode.addPivot(relationTypeLocator, signature, 
					relationTypeLocator, targetNode.getSmallImage(), targetNode.getLocator(), tlab, targetNode.getNodeType(), "t");
			targetNode.addPivot(relationTypeLocator, signature, 
					relationTypeLocator, sourceNode.getSmallImage(), sourceNode.getLocator(), slab, sourceNode.getNodeType(), "s");
		}
		///////////////////////////////////////////
		//TODO
		// WE are now OptimisticLockException sensitive
		// And it might be possible that we need the
		// ability to detect that
		///////////////////////////////////////////
		sourceNode.doUpdate();
		IResult x = database.updateNode(sourceNode, true);
		if (x.hasError())
			result.addErrorString(x.getErrorString());
		x = database.putNode(targetNode);
		if (x.hasError())
			result.addErrorString(x.getErrorString());
		t.doUpdate();
		database.updateNode(t, true);
		if (x.hasError())
			result.addErrorString(x.getErrorString());
		environment.logDebug("SubjectProxyModel.relateExistingNodesAsPivots "+sourceNode.getLocator()+" "+targetNode.getLocator()+" "+t.getLocator()+" | "+result.getErrorString());
		result.setResultObject(t);
		return result;	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#assertMerge(java.lang.String, java.lang.String, java.util.Map, double, java.lang.String)
	 */
	@Override
	public IResult assertMerge(String sourceNodeLocator,
			String targetNodeLocator, Map<String, Double> mergeData,
			double mergeConfidence, String userLocator) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#assertPossibleMerge(java.lang.String, java.lang.String, java.util.Map, double, java.lang.String)
	 */
	@Override
	public IResult assertPossibleMerge(String sourceNodeLocator,
			String targetNodeLocator, Map<String, Double> mergeData,
			double mergeConfidence, String userLocator) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.node.api.INodeProviderModel#assertUnmerge(java.lang.String, org.topicquests.node.api.ISubjectProxy, java.util.Map, double, java.lang.String)
	 */
	@Override
	public IResult assertUnmerge(String sourceNodeLocator,
			ISubjectProxy targetNodeLocator, Map<String, Double> mergeData,
			double mergeConfidence, String userLocator) {
		// TODO Auto-generated method stub
		return null;
	}
	//////////////////////////////////////////////
	
	private String newUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Returns transitive closure for a given node
	 * @param parentLocator
	 * @return does not return <code>null</code>
	 */
	private List<String> listTransitiveClosure(String parentLocator) {
		IResult x = database.getNode(parentLocator, credentials);
		ISubjectProxy n = (ISubjectProxy)x.getResultObject();
		List<String>result = new ArrayList<String>();
		if (n != null) {
			//clone the list
			List<String> temp  = n.listTransitiveClosure();
			if (temp != null) {
				Iterator<String>itr = temp.iterator();
				while (itr.hasNext())
					result.add(itr.next());
			}
		}
		return result;
	}

	@Override
	public IResult addParentNode(ISubjectProxy proxy, String contextLocator,
			String smallIcon, String locator, String subject) {
		IResult result = new ResultPojo();
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public IResult addChildNode(ISubjectProxy proxy, String contextLocator,
			String smallIcon, String locator, String subject,
			String transcluderLocator) {
		IResult result = new ResultPojo();
		IResult r = null;
		// add child to proxy
		boolean didAdd = ((IParentChildContainer)proxy).addChildNode(contextLocator, smallIcon, locator, subject, transcluderLocator);
		if (didAdd) {
			r = database.updateNode(proxy, true);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			String lox = proxy.getLocator();
			IParentChildContainer root = null;
			if (lox.equals(contextLocator))
				root = (IParentChildContainer)proxy;
			else {
				r = database.getNode(contextLocator, credentials);
				if (r.hasError())
					result.addErrorString(r.getErrorString());
				root = (IParentChildContainer)r.getResultObject();
			}
			System.out.println("ADDCHILDNODE "+lox+" "+contextLocator+" "+root);
			root.addToParentChildList(locator);
			r = database.updateNode((ISubjectProxy)root, true);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
		}
		
		return result;
	}
}
