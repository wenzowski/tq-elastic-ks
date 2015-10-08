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

import org.topicquests.common.api.IResult;
import org.topicquests.ks.tm.api.ISubjectProxy;

/**
 * @author park
 * <p>These appear to be normal topic map functions</p>
 */
public interface ITypeHierarchy extends ISubjectProxy {
	public static final String 
	  /** The predefined type label for the universal type. **/
	  UNIVERSAL_TYPE_LABEL = "Universal",
	  /** The predefined type label for the absurd type. **/
	  ABSURD_TYPE_LABEL = "Absurd";

	IResult addTypeToHierarchy(IType newType, IType supertypes[], IType subtypes[]);
	
	IResult addTypeToHierarchy(IType newType, IType supertype, IType subtype);

	IResult addTypeToHierarchy(IType newType);

	IResult addSuperTypesToType(IType subjectType, IType newSuperTypes[]);
	
	IResult addSuperTypeToType(IType subjectType, IType newSuperType);
	
	IResult addSubTypesToType(IType subjectType, IType newSubTypes[]);
	
	IResult addSubTypeToType(IType subjectType, IType newSubType);
	
	IResult updateTypeLabel(IType type, String oldLabel, String newLabel);
	
    /**
     * Determines whether subject is a supertype of object.
     *
     * @param subject  the potential supertype being tested.
     * @param object  the potential subtype being tested.
     * @return <code>true</code> if subject is a supertype of object.
     */
	boolean isSuperTypeOf(IType subject, IType object);
	
	boolean isSubTypeOf(IType subject, IType object);
	
	boolean isProperSubTypeOf(IType subject, IType object);
	
	boolean isProperSuperTypeOf(IType subject, IType object);
}
