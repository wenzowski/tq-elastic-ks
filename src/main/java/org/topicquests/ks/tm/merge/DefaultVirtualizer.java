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

package org.topicquests.ks.tm.merge;

import java.util.*;

import org.topicquests.common.ResultPojo;
import org.topicquests.common.api.IResult;
import org.topicquests.ks.TicketPojo;
import org.topicquests.ks.api.ITQCoreOntology;
import org.topicquests.ks.api.ITicket;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.ks.tm.api.IVirtualizer;

/**
 * @author park
 * <p>Decide what, if anything to merge:<br/>
 * <li>If no VirtualNode exists, create one</li>
 * <li>If VirtualNode exists, use it</li>
 * <li>Then use SetUnion tools in {@link BaseVirtualizer} to merge nodes</li>
 * </p>
 * <p>This is just a tool for any {@link IMergeImplementation} MergeBean</p>
 */
public class DefaultVirtualizer extends BaseVirtualizer implements IVirtualizer {


	/* (non-Javadoc)
	 * @see org.topicquests.topicmap.json.model.api.IVirtualizer#createVirtualNode(org.topicquests.model.api.INode, org.topicquests.model.api.INode, java.lang.String)
	 * Must return the virtualNode's locator
	 * <code>mergeData</code> is a key=reason string, value=double weight
	 */
	@Override
	public IResult createVirtualNode(ISubjectProxy primary, ISubjectProxy merge,
			Map<String,Double> mergeData, double confidence,
			String userLocator) {
		environment.logDebug("DefaultVirtualizer.createVirtualNode- "+primary+" "+merge+" "+userLocator);
		ITicket credentials =  new TicketPojo(userLocator);
		IResult result = new ResultPojo();
		// Both are the same node?
		if (primary.getLocator().equals(merge.getLocator()))
			return result;
		ISubjectProxy virtualNode = null;
		boolean primaryHadVirtual = false;
		boolean primaryIsMerged = (primary.getProperty(ITQCoreOntology.MERGE_TUPLE_PROPERTY) != null);
		boolean mergeIsMerged = (merge.getProperty(ITQCoreOntology.MERGE_TUPLE_PROPERTY) != null);
		// Both already merged?
		if (primaryIsMerged && mergeIsMerged)
			return result;
		//Have these two, or either one of them, been merged before?
		//TODO rewrite this with primaryIsMerged and mergedIsMerged
		IResult r = database.getVirtualNodeIfExists(primary, credentials);
		if (r.hasError())
			result.addErrorString(r.getErrorString());
		if (r.getResultObject() != null) {
			virtualNode = (ISubjectProxy)r.getResultObject();
			primaryHadVirtual = true;
		} else {
			r = database.getVirtualNodeIfExists(merge, credentials);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			if (r.getResultObject() != null)
				virtualNode = (ISubjectProxy)r.getResultObject();
		}
		String theType;
		List<String>sups;
		String smallImagePath, largeImagePath;
		boolean isPrivate = false;
		//If so, then get that virtual proxy and use it
		if (virtualNode != null) {
			//the case where we are updating the virtual node due to
			// another merge with it
			ISubjectProxy mymerge = merge;
			if (!primaryHadVirtual)
				mymerge = primary;
			theType = mymerge.getNodeType();
			sups = mymerge.listSuperclassIds();
			smallImagePath = mymerge.getSmallImage();
			largeImagePath = mymerge.getImage();
			//WARNING: we presently do not test privacy in merge testing
			isPrivate = mymerge.getIsPrivate();
			virtualNode = nodeModel.newNode(null, null, "", userLocator, smallImagePath, largeImagePath, isPrivate);
			virtualNode.setIsVirtualProxy(true);
			if (theType != null)
				virtualNode.setNodeType(theType);
			if (sups != null && !sups.isEmpty()) {
				Iterator<String>itr = sups.iterator();
				while (itr.hasNext())
					virtualNode.addSuperclassId(itr.next());
			}
			setUnionProperties(virtualNode,mymerge);
			r = database.putNode(virtualNode);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			r = super.wireMerge(virtualNode, mymerge, mergeData, confidence, userLocator);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			String tupleLocator = (String)r.getResultObject();
			r = database.putNode(virtualNode);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			r = database.putNode(mymerge);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			r = super.reWireNodeGraph(mymerge.getLocator(), virtualNode.getLocator(), tupleLocator, credentials);
			if (r.hasError())
				result.addErrorString(r.getErrorString());			
		} else {
			//Otherwise, create a new virtual proxy
			theType = primary.getNodeType();
			sups = primary.listSuperclassIds();
			smallImagePath = primary.getSmallImage();
			largeImagePath = primary.getImage();
			//WARNING: we presently do not test privacy in merge testing
			isPrivate = primary.getIsPrivate();
			virtualNode = nodeModel.newNode(null, null, "", userLocator, smallImagePath, largeImagePath, isPrivate);
			virtualNode.setIsVirtualProxy(true);
			if (theType != null)
				virtualNode.setNodeType(theType);
			if (sups != null && !sups.isEmpty()) {
				Iterator<String>itr = sups.iterator();
				while (itr.hasNext())
					virtualNode.addSuperclassId(itr.next());
			}
			setUnionProperties(virtualNode,primary);
			setUnionProperties(virtualNode,merge);
			r = database.putNode(virtualNode);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			//virtualNode is now constructed
			//Time to wire them together
			r = super.wireMerge(virtualNode, primary, mergeData, confidence, userLocator);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			String tupleLocator = (String)r.getResultObject();
			//now, save these nodes
			r = database.putNode(virtualNode);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			r = database.putNode(primary);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			r = super.reWireNodeGraph(primary.getLocator(), virtualNode.getLocator(), tupleLocator, credentials);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			r = super.wireMerge(virtualNode, merge, mergeData, confidence, userLocator);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			tupleLocator = (String)r.getResultObject();
			r = database.putNode(merge);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			r = super.reWireNodeGraph(primary.getLocator(), merge.getLocator(), tupleLocator, credentials);
			if (r.hasError())
				result.addErrorString(r.getErrorString());
			
		}
		result.setResultObject(virtualNode.getLocator());
		environment.logDebug("DefaultVirtualizer.createVirtualNode+ "+result.getResultObject());
		return result;
	}



}
