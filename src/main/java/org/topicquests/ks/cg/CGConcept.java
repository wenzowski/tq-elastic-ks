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
import java.util.Map;

import net.minidev.json.JSONObject;

import org.topicquests.support.api.IResult;
import org.topicquests.ks.cg.api.ICGConcept;
import org.topicquests.ks.cg.api.ICGConceptLinkStruct;
import org.topicquests.ks.cg.api.IConceptType;
import org.topicquests.ks.cg.api.IConceptualGraph;
import org.topicquests.ks.cg.api.ICopyingScheme;
import org.topicquests.ks.cg.api.ICoreferenceSet;
import org.topicquests.ks.cg.api.IMatchResult;
import org.topicquests.ks.cg.api.IMatchingScheme;
import org.topicquests.ks.cg.api.IReferent;
import org.topicquests.ks.cg.api.IRelation;
import org.topicquests.ks.tm.SubjectProxy;

/**
 * @author park
 *
 */
public class CGConcept extends SubjectProxy implements ICGConcept {

	/**
	 * @param jo
	 */
	public CGConcept(JSONObject jo) {
		super(jo);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.ICGConcept#getReferent()
	 */
	@Override
	public IReferent getReferent() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.ICGConcept#setReferent(org.topicquests.ks.cg.api.IReferent)
	 */
	@Override
	public void setReferent(IReferent newReferent) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.ICGConcept#addInLink(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addInLink(String contextLocator, String linkTypeLocator,
			String smallIcon, String locator, String subject) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.ICGConcept#addOutLink(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addOutLink(String contextLocator, String linkTypeLocator,
			String smallIcon, String locator, String subject) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.ICGConcept#removeInLink(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void removeInLink(String contextLocator, String linkTypeLocator,
			String locator) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.ICGConcept#removeOutLink(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void removeOutLink(String contextLocator, String linkTypeLocator,
			String locator) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.ICGConcept#listInLinks(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ICGConceptLinkStruct> listInLinks(String contextLocator,
			String linkTypeLocator) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.cg.api.ICGConcept#listOutLinks(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ICGConceptLinkStruct> listOutLinks(String contextLocator,
			String linkTypeLocator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<ICoreferenceSet> listCoreferenceSets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ICoreferenceSet> getCorefenceSetList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<ICGConcept> listCoreferenceConcepts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDominantConcept() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDefiningConcept() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addCoreferenceSet(ICoreferenceSet newCorefSet) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeCoreferenceSet(ICoreferenceSet deadCorefSet) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnclosedBy(ICGConcept concept) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnclosedBy(IConceptualGraph graph) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IResult isolate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<IRelation> listRelators() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICGConcept copy(ICopyingScheme copyScheme) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICGConcept copy(ICopyingScheme copyScheme,
			Map<Object, Object> substitutionTable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResult restrictTo(IConceptType subType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResult restrictTo(IReferent newReferent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResult restrictTo(IConceptType subType, IReferent newReferent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isContext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IConceptualGraph getEnclosedGraph() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isGeneric() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean testCoreference(ICGConcept first, ICGConcept second) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IMatchResult matchConcepts(ICGConcept first, ICGConcept second,
			IMatchingScheme matchingScheme) {
		// TODO Auto-generated method stub
		return null;
	}

}
