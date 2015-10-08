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
public interface IAirStruct {
	//Must be same as in TQTopicMap.airstruct
	public static final String
		TEXT			= "theText",
		LAST_EDIT_DATE	= "lastEditDate",
		CREATOR_ID	 	= "creatorId",
		EDIT_COMMENT	= "editComment";
	
	void setText(String text);
	String getText();
	void setLastEditDate(String date);
	String getLastEditDate();
	void setEditComment(String comment);
	String getEditComment();
	void setCreator(String creatorId);
	String getCreatorId();
	
	String toJSON();
}
