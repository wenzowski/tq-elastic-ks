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
package org.topicquests.ks.cg;

import java.util.Iterator;
import java.util.List;

import net.minidev.json.JSONObject;

import org.topicquests.support.api.IResult;
import org.topicquests.ks.cg.api.IConceptualGraph;
import org.topicquests.ks.graph.GraphVertex;
import org.topicquests.ks.graph.api.IEdge;
import org.topicquests.ks.graph.api.IGraphProvider;
import org.topicquests.ks.graph.api.IQuery;
import org.topicquests.ks.tm.SubjectProxy;

/**
 * @author park
 *
 */
public class CG extends GraphVertex implements IConceptualGraph {

	/**
	 * @param jo
	 */
	public CG(JSONObject jo) {
		super(jo);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.IConceptualGraph#listConceptLocators()
	 */
	@Override
	public List<String> listConceptLocators() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.IConceptualGraph#addConceptLocator(java.lang.String)
	 */
	@Override
	public void addConceptLocator(String locator) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.IConceptualGraph#removeConceptLocator(java.lang.String)
	 */
	@Override
	public void removeConceptLocator(String locator) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.IConceptualGraph#listRelationLocators()
	 */
	@Override
	public List<String> listRelationLocators() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.IConceptualGraph#addRelationLocator(java.lang.String)
	 */
	@Override
	public void addRelationLocator(String locator) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.IConceptualGraph#removeRelationLocator(java.lang.String)
	 */
	@Override
	public void removeRelationLocator(String locator) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.IConceptualGraph#getParentGraphLocator()
	 */
	@Override
	public String getParentGraphLocator() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.IConceptualGraph#setParentGraphLocator(java.lang.String)
	 */
	@Override
	public void setParentGraphLocator(String locator) {
		// TODO Auto-generated method stub

	}


}
