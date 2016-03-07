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
package org.topicquests.ks.cg.api;

import java.util.List;

import org.topicquests.ks.graph.api.IVertex;

/**
 * 
 * @author park
 * <p>A ConceptualGraph is a topic which serves to contain
 *  a collection of {@link ICGConcept} objects and {@link ICGRelation}
 *  objects.</p>
 */
public interface IConceptualGraph extends IVertex {

	////////////////////////////////////////
	// Technical note:
	//  This is modeled as a container of locators rather
	//  than a container of objects
	///////////////////////////////////////
	List<String> listConceptLocators();
	void addConceptLocator(String locator);
	void removeConceptLocator(String locator);
	
	List<String> listRelationLocators();
	void addRelationLocator(String locator);
	void removeRelationLocator(String locator);
	
	//////////////////////////////////
	// AN OPEN RESEARCH QUESTION:
	//  Is it possible that a given ConceptualGraph
	//  could exist in more than one uneverse, that is,
	//  with more than one parent graph?
	//////////////////////////////////
	String getParentGraphLocator();
	void setParentGraphLocator(String locator);
}
