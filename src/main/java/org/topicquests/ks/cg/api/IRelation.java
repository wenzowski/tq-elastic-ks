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

import java.util.Map;
import java.util.List;
import java.util.Iterator;

import org.topicquests.common.api.IResult;
import org.topicquests.ks.tm.api.ISubjectProxy;


/**
 * @author park
 *  <p>COMPARE TO {@link ITuple}</p>
 */
public interface IRelation extends ISubjectProxy {

	IRelationType getType();
	void setType(IRelationType newType);
	
	//IConcept[] getArguments();
	
	Iterator<ICGConcept> listArguments();
	
	//IConcept[] getInputArguments();
	
	Iterator<ICGConcept> listInputArguments();
	
	//IConcept[] getOutputArguments();
	
	Iterator<ICGConcept> listOutputArguments();
	
	/**
	 * Returns <code>true</code> if replaced
	 * @param oldConcept
	 * @param newConcept
	 * @return
	 */
	boolean replaceArgument(ICGConcept oldConcept, ICGConcept newConcept);
	
	boolean replaceInputArgument(ICGConcept oldConcept, ICGConcept newConcept);
	
	boolean replaceOutputArgument(ICGConcept oldConcept, ICGConcept newConcept);
	
	boolean relatesConcept(ICGConcept concept);
	
	/**
	 * Can return an error string if <code>index is out of bounds
	 * @param index
	 * @param newConcept
	 * @return
	 */
	IResult setArgument(int index, ICGConcept newConcept);
	
	IResult setArguments(List<ICGConcept> newConcepts);
	
	void setInputArgument(int index, ICGConcept newConcept);
	
	void setOutputArgument(int index, ICGConcept newConcept);
	
	int getOutputStartIndex();
	
	void setOutputStartIndex(int newOutputStartIndex);
	
	int getValence();
	
    /**
     * <p>Returns <code>true</code> if this relation's arguments are completely specified
     * (are all non-null).  If the relation has a type, its valence will be
     * checked to ensure that the relation is complete according to the type.
     * This check is made in case the valence of the type has changed.  If it
     * has changed, the arguments will be adjusted accordingly.</p>
     * 
     * @return 
     */
	boolean isComplete();
	
	IRelation copy(ICopyingScheme copyScheme);
	
	IRelation copy(ICopyingScheme copyScheme, Map<Object,Object> substitutionTable);
		
	IResult restrictTo(IRelationType subType);
	
	boolean matchRelations(IRelation first, IRelation second, 
			    IMatchingScheme matchingScheme);
	
	boolean matchArcs(IRelation first, IRelation second, IMatchingScheme matchingScheme);
	
	
}
