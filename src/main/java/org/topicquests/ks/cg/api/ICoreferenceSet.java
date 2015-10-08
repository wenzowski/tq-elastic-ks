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

import org.topicquests.common.api.IResult;
import org.topicquests.ks.tm.api.ISubjectProxy;

/**
 * @author park
 *
 */
public interface ICoreferenceSet extends ISubjectProxy {

	 void setDefiningLabel(String newDefLabel);
	 
	 String getDefiningLabel();
	 
	 IResult setDefiningConcept(ICGConcept newDefConcept);
	 
	 ICGConcept getDefiningConcept();
	 
	 int size();
	 
	 IResult addCoreferentConcept(ICGConcept newConcept);
	 
	 /**
	  * Can return an error message
	  * @param deadConcept
	  * @return returns Boolean <code>true</code> if object changed
	  */
	 IResult removeCoreferentConcept(ICGConcept deadConcept);
	 
	 IResult removeConcepts(ICGConcept deadConcepts[]);
	 
	 //boolean validateScope();
	 //void establishScope();
	 
	 boolean hasConcept(ICGConcept concept);
	 
	 boolean hasDominantConcept(ICGConcept concept);
	 
	 boolean hasSubordinateConcept(ICGConcept concept);
	 
	 //IConcept[] getCoreferentConcepts();
	 
	 Iterator<ICGConcept> listCoreferentConcepts();
	 
	 //IConcept[] getDominantConcepts();
	 
	 Iterator<ICGConcept> listDominantConcepts();
	 
	 //IConcept[] getSubordinateConcepts();

	 Iterator<ICGConcept> listSubordinateConcepts();
	 
	 IResult setEnableScopeChecking(boolean flag);
	 
	 boolean getEnableScopeChecking();

}
