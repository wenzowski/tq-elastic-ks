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
package org.topicquests.ks.tm;

//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import org.topicquests.ks.tm.api.IChildStruct;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

/**
 * @author park
 *
 */
public class ChildStruct implements IChildStruct {
	private JSONObject data;

	/**
	 * 
	 */
	public ChildStruct() {
		data = new JSONObject();
	}

	public ChildStruct(String json) throws Exception {
		JSONParser p = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
		data = (JSONObject)p.parse(json);
	}
	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IChildStruct#setContextLocator(java.lang.String)
	 */
	@Override
	public void setContextLocator(String context) {
		data.put(IChildStruct.CONTEXT_LOCATOR, context);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IChildStruct#getContextLocator()
	 */
	@Override
	public String getContextLocator() {
		return (String)data.get(IChildStruct.CONTEXT_LOCATOR);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IChildStruct#setLocator(java.lang.String)
	 */
	@Override
	public void setLocator(String locator) {
		data.put(IChildStruct.LOCATOR, locator);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IChildStruct#getLocator()
	 */
	@Override
	public String getLocator() {
		return (String)data.get(IChildStruct.LOCATOR);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IChildStruct#setSmallIcon(java.lang.String)
	 */
	@Override
	public void setSmallIcon(String iconPath) {
		data.put(IChildStruct.ICON, iconPath);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IChildStruct#getSmallIcon()
	 */
	@Override
	public String getSmallIcon() {
		return (String)data.get(IChildStruct.ICON);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IChildStruct#setTranscluderLocator(java.lang.String)
	 */
	@Override
	public void setTranscluderLocator(String transcluderId) {
		data.put(IChildStruct.TRANSCLUDER_ID, transcluderId);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IChildStruct#getTranscluderId()
	 */
	@Override
	public String getTranscluderId() {
		return (String)data.get(IChildStruct.TRANSCLUDER_ID);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IChildStruct#toJSON()
	 */
	@Override
	public String toJSON() {
		return data.toJSONString();
	}

	@Override
	public void setSubject(String subject) {
		data.put(IChildStruct.SUBJECT, subject);
	}

	@Override
	public String getSubject() {
		return (String)data.get(IChildStruct.SUBJECT);
	}

	@Override
	public JSONObject getData() {
		return data;
	}

}
