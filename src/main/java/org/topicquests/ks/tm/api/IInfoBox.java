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

import net.minidev.json.JSONObject;

/**
 * @author park
 * An InfoBox is a <em>named</em> object which constitutes
 * a named JSON string
 */
public interface IInfoBox {
	public static final String
		CREATOR_ID		= "creator",
		DATE			= "dat",
		LAST_EDIT_DATE	= "leDat",
		NAME			= "name";
	
	void setName(String name);
	String getName();
	void setCreatorId(String creatorId);
	String getCreatorId();
	void setDate(String date);
	String getDate();
	void setLastEditDate(String date);
	String getLastEditDate();
	
	//////////////////////////////////////
	// The API of an InfoBox is to;
	// 1- take care of provenance
	// 2- allow for adding any information which can
	//    be represented as JSON, either a string, or a collection
	//    of string objects
	// 3- export to a JSON string
	//////////////////////////////////////
	void setProperty(String key, Object value);
	Object getProperty(String key);
	
	JSONObject getData();
	
	//String toJSON();

}
