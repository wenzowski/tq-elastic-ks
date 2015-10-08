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

package org.topicquests.ks.tm.api;

/**
 * @author park
 * 
 */
public interface IRelationStruct {
	//must be same as in TQTopicMap.relnstruct
	public static final String
		RELATION_TYPE		= "relationType",
		RELATION_LOCATOR	= "relationLocator",
		RELATION_LABEL		= "relationLabel",
		TARGET_TYPE			= "documentType",
		TARGET_ICON			= "documentSmallIcon",
		TARGET_LOCATOR		= "documentLocator",
		TARGET_LABEL		= "documentLabel",
		SOURCE_OR_TARGET	= "sORt";

	void setRelationLocator(String locator);
	String getRelationLocator();
	void setRelationType(String type);
	String getRelationType();
	void setRelationLabel(String label);
	String getRelationLabel();
	void setTargetIcon(String iconPath);
	String getTargetIcon();
	void setTargetLocator(String locator);
	String getTargetLocator();
	void setTargetLabel(String label);
	String getTargetLabel();
	void setTargetNodeType(String nodeType);
	String getTargetNodeType();
	void setSourceOrTarget(String sORt);
	String getSourceOrTarget();
	
	String toJSON();
}
