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

/**
 * @author park
 *
 */
public interface IRelationType extends IType {

	
	void setTypeDefinition(IRelationTypeDefinition newDefinition);
	
	/**
	 * Can return <code>null</code>
	 * @return
	 */
	IRelationTypeDefinition getTypeDefinition();
	
	/**
     * <p>Sets the valence for this type.  A value of -1 indicates that the valence is undefined.
     * The valence is the number of arcs (or arguments) that this relation type possesses.
     * Changing the valence has no effect on existing relations using this type except that
     * they many return a different value when their isComplete() method is called.
     * It is up to the application to ensure that all such relations are corrected by the
     * additional or removal of arguments.  In most cases, it is expected that this method
     * will be used to change a valence from unspecified to a specific value, rather than
     * from one specific value to another.</p>
     * <p>Error if a type definition has been specified for this type.</p>
	 * @param newValence
	 * @return
	 */
	IResult setValence(int newValence);
	
	/**
	 * 
	 * @return the valence for this type, or -1 if the valence is undefined
	 */
	int getValence();
	
	//IRelationType[] getProperSubTypes();
	
	Iterator<IRelationType> listProperSubTypes();
	
	//IRelationType[] getSuperProperTypes();

	Iterator<IRelationType> listProperSuperTypes();
	
	//IRelationType[] getImmediateSubTypes();

	Iterator<IRelationType> listImmediateSubTypes();

	//IRelationType[] getImmediateSuperTypes();
	
	Iterator<IRelationType> listImmediateSuperTypes();
	
	boolean hasSubType(IRelationType queryType);

	boolean hasSuperType(IRelationType queryType);
	
	boolean hasProperSubType(IRelationType queryType);
	
	boolean hasProperSuperType(IRelationType queryType);
	
	boolean matchRelationTypes(IRelationType first, IRelationType second, 
		    IMatchingScheme matchingScheme);
	
}
