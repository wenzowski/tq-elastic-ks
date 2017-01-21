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

import org.topicquests.support.api.IResult;
import org.topicquests.ks.tm.api.ISubjectProxy;

/**
 * @author park
 *
 */
public interface IType extends ISubjectProxy {

	/**
	 * Error if the type belongs to a hierarchy and the new label is already in use within it.
	 * @param newLabel
	 * @return
	 */
	IResult setLabel(String newLabel);

	String getLabel();
	
	void setComment(String newComment);
	
	String getComment();
	
	void setHierarchy(ITypeHierarchy newHierarchy);
	
	ITypeHierarchy getHierarchy();
}
