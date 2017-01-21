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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.topicquests.support.api.IResult;
import org.topicquests.ks.tm.api.ISubjectProxy;


/**
 * @author park
 * <p> An <code>ICGConcept</code> is just an ordinary topic
 *  in a topic map, except that it has in and out edges.</p>
 * <p> Since it is a topic in a topic map, it might end up in
 *  more than one graph <em>context</em>, so we can use the
 *  {@link ICGConceptLinkStruct} structure to model each in and each out
 *  link, where that struct's <em>contextLocator</em> is the locator
 *  associated in which Conceptual graph which those links are valid.
 *  Each struct is associated with a linkType as well.
 *  </p>
 *  <p> An <code>ICGConcept</code> is a <em>topic</em> which is a
 *  <em>casting (role)</em> of a specific subject in the topicmap</p>
 *  <p>It will be modeled as a specific type of <em>Pivot</em></p>
 */
public interface ICGConcept extends ISubjectProxy {

	IReferent getReferent();
	 
	void setReferent(IReferent newReferent);
	
	void addInLink(String contextLocator, String linkTypeLocator, String smallIcon, String locator, String subject);

	void addOutLink(String contextLocator, String linkTypeLocator, String smallIcon, String locator, String subject);

	void removeInLink(String contextLocator, String linkTypeLocator, String locator);
	
	void removeOutLink(String contextLocator, String linkTypeLocator, String locator);
	
	/**
	 * <p>Returns all inLinks if both params == <code>null</code></p>
	 * <p>Returns all inLinks for a given <code>linkType</code> if
	 *  only <code>contextLocator</code> == null.</p>
	 * <p>Returns all inLinks for a given <code>contextLocator</code> if
	 *  only <code>linkType</code> == null.</p>
	 * @param contextLocator 
	 * @param linkTypeLocator
	 * @return
	 */
	List<ICGConceptLinkStruct> listInLinks(String contextLocator, String linkTypeLocator);
	
	/**
	 * <p>Returns all outLinks if both params == <code>null</code></p>
	 * <p>Returns all outLinks for a given <code>linkType</code> if
	 *  only <code>contextLocator</code> == null.</p>
	 * <p>Returns all outLinks for a given <code>contextLocator</code> if
	 *  only <code>linkType</code> == null.</p>
	 * @param contextLocator
	 * @param linkTypeLocator
	 * @return
	 */
	List<ICGConceptLinkStruct> listOutLinks(String contextLocator, String linkTypeLocator);

    /**
     * Returns the coreferences sets of which this concept is a member.
	 * If this method returns an empty array, then this concept is considered to be
	 * the defining concept and sole member of an "implicit" coreference set.
	 * This means that that the isDefiningConcept() method will return true if
	 * this method returns an empty array.
	 * Coreference sets only need to be created when they have multiple
	 * concepts.
     *
     * @return an array containing all of the coreference sets of which this
     * concept is a member, possibly empty.
     */
	Iterator<ICoreferenceSet> listCoreferenceSets();

	List<ICoreferenceSet> getCorefenceSetList();

    /**
     * Returns all of the concepts coreferent to this concept.
     * This is essentially the union of all the coreferent sets to
     * which this concept belongs.  Note that every concept is coreferent
     * to itself so this method will always return at least one element.
		 * Note that if this concept has no explicitly added coreference sets,
		 * this method will still return the concept itself, the sole member of an
		 * "implicit" coreference set.
     *
     * @return an array containing of the concepts coreferent to this concept.
     */
	Iterator<ICGConcept> listCoreferenceConcepts();

    /**
     * Returns true if this concept is a dominant node of a coreference set.
	 * Note that if this concept is not part of any explicit coreference sets, 
	 * it is considered to be a dominant node and sole member of an "implicit"
	 * coreference set, so this method will return true.
	 * 
     *
     * @return true if this concept is a dominant node of a coreference set.
     */
	boolean isDominantConcept();

    /**
     * Returns true if this concept is the defining concept of a coreference
     * set.
	 * Note that if this concept is not part of any explicit coreference sets, 
	 * it is considered to be the defining node and sole member of an "implicit"
	 * coreference set, so this method will return true.
     *
     * @return true if this concept is the defining concept of a coreference set.
     */
	boolean isDefiningConcept();

    /**
     * Adds <code>newCorefSet</code> to this concept.
     *
     * @param newCorefSet  the coref set to be added.
     * @return <code>true if <code>newCoreSet</code> is added
     */
	boolean addCoreferenceSet(ICoreferenceSet newCorefSet);
	 
    /**
     * Removes<code>deadCorefSet</code> from this concept.
     *
     * @param deadCorefSet  the coref set to be removed.
     * @return <code>true if <code>newCoreSet</code> is removed
     */
	boolean removeCoreferenceSet(ICoreferenceSet deadCorefSet);
	 
	/**
	 * Returns true if this concept is enclosed by the specified concept.
	 *
	 * @param concept  the concept being checked for as enclosing this concept.
	 * @return true if this concept is enclosed by the specified concept.
	 */
	boolean isEnclosedBy(ICGConcept concept);
	 
	/**
	 * Returns true if this concept is enclosed by the specified graph.
	 *
	 * @param graph  the graph being checked for as enclosing this concept.
	 * @return true if this concept is enclosed by the specified graph.
	 */
	boolean isEnclosedBy(IConceptualGraph graph);
	 
    /**
     * Isolates this concept by removing it from all coreference sets
     * to which it belongs and by isolating any and all concepts that may
     * be nested within it.
     * @return a <code>List<ICoreferenceSet></code> of changed objects which need persisting
     * If that returned list is empty, no changes made here
     * 	
     * @bug This should be done in some cleaner way, either in Graph or some other way here.
     */
	IResult isolate();
	
    /**
     * Returns an Iterator (possibly empty) of the relations in the enclosing
     * graph that relate this concept.  This method will return null if the
     * concept does not belong to any graph (getEnclosingGraph() returns null).
     *
     * @return 
     * @bug Should this method be in Graph instead?
     */
	Iterator<IRelation> listRelators();

	ICGConcept copy(ICopyingScheme copyScheme);
	 
	ICGConcept copy(ICopyingScheme copyScheme, Map<Object,Object> substitutionTable);
	 
	IResult restrictTo(IConceptType subType);
	 
	IResult restrictTo(IReferent newReferent);
	 
	IResult restrictTo(IConceptType subType, IReferent newReferent);
	 
    /**
     * Returns true if this concept is a context.  A context is a concept
     * whose descriptor is a non-blank conceptual graph.
     * This method will return true if this concept has a referent which returns
     * true when its isContext() method is called.
     *
     * @return true if this concept is a context.
     * @see notio.Referent#isContext()
     */
	boolean isContext();
	 

    /**
      * Returns the enclosed (nested) descriptor graph if this concept is a context 
      * or null otherwise.
      * This is really a convenience method for quickly traversing compound graphs.
      * It is a counter-part to getEnclosingGraph().
      *
      * @return the enclosed graph if this concept is a context, or null otherwise.
      * @see notio.Node#getEnclosingGraph()
      */
	IConceptualGraph getEnclosedGraph();
	 
    /**
     * <p>Returns <code>true</code> if this concept is generic; <code>false</code> if the concept is
     * specific.  A generic concept either has a referent of <code>null</code>, or a referent
     * in which the quantifier, designator, and descriptor are <code>null</code>.</p>
     *
     * @return 
     */
	boolean isGeneric();
	 
    /**
     * Returns true if the two concepts specified are coreferent.
		 * Note that every concept is coreferent to itself so this method will
		 * return true if the first and second concept are the same concept.
     *
     * @param first  the first concept.
     * @param second  the second concept.
     * @return true if the two concepts specified are coreferent.
     */
	boolean testCoreference(ICGConcept first, ICGConcept second);
	 
	IMatchResult matchConcepts(ICGConcept first, ICGConcept second,
			    IMatchingScheme matchingScheme);
}
