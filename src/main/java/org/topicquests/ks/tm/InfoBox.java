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
import org.topicquests.ks.tm.api.IInfoBox;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

/**
 * @author park
 *
 */
public class InfoBox implements IInfoBox {
	private JSONObject data;

	/**
	 * 
	 */
	public InfoBox() {
		data = new JSONObject();
	}
	
	public InfoBox(String json) throws Exception {
		JSONParser p = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
		data = (JSONObject)p.parse(json);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.node.IInfoBox#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		data.put(IInfoBox.NAME, name);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.node.IInfoBox#getName()
	 */
	@Override
	public String getName() {
		return (String)data.get(IInfoBox.NAME);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.node.IInfoBox#setCreatorId(java.lang.String)
	 */
	@Override
	public void setCreatorId(String creatorId) {
		data.put(IInfoBox.CREATOR_ID, creatorId);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.node.IInfoBox#getCreatorId()
	 */
	@Override
	public String getCreatorId() {
		return (String)data.get(IInfoBox.CREATOR_ID);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.node.IInfoBox#setDate(java.lang.String)
	 */
	@Override
	public void setDate(String date) {
		data.put(IInfoBox.DATE, date);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.node.IInfoBox#getDate()
	 */
	@Override
	public String getDate() {
		return (String)data.get(IInfoBox.DATE);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.node.IInfoBox#setLastEditDate(java.lang.String)
	 */
	@Override
	public void setLastEditDate(String date) {
		data.put(IInfoBox.LAST_EDIT_DATE, date);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.node.IInfoBox#getLastEditDate()
	 */
	@Override
	public String getLastEditDate() {
		return (String)data.get(IInfoBox.LAST_EDIT_DATE);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.node.IInfoBox#setProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setProperty(String key, Object value) {
		data.put(key, value);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.node.IInfoBox#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String key) {
		return data.get(key);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.node.IInfoBox#toJSON()
	 */
//	@Override
//	public String toJSON() {
//		return data.toJSONString();
//	}

	@Override
	public JSONObject getData() {
		return data;
	}

}
