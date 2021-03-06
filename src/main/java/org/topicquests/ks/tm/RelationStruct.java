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
import org.topicquests.ks.tm.api.IRelationStruct;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

/**
 * @author park
 *
 */
public class RelationStruct  implements IRelationStruct {
	private JSONObject data;
	/**
	 * 
	 */
	public RelationStruct() {
		data = new JSONObject();
	}
	
	public RelationStruct(String json) throws Exception {
		JSONParser p = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
		data = (JSONObject)p.parse(json);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IRelationStruct#setRelationType(java.lang.String)
	 */
	@Override
	public void setRelationType(String type) {
		data.put(IRelationStruct.RELATION_TYPE, type);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IRelationStruct#getRelationType()
	 */
	@Override
	public String getRelationType() {
		return (String)data.get(IRelationStruct.RELATION_TYPE);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IRelationStruct#setRelationLabel(java.lang.String)
	 */
	@Override
	public void setRelationLabel(String label) {
		data.put(IRelationStruct.RELATION_LABEL, label);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IRelationStruct#getRelationLabel()
	 */
	@Override
	public String getRelationLabel() {
		return (String)data.get(IRelationStruct.RELATION_LABEL);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IRelationStruct#setTargetIcon(java.lang.String)
	 */
	@Override
	public void setTargetIcon(String iconPath) {
		data.put(IRelationStruct.TARGET_ICON, iconPath);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IRelationStruct#getTargetIcon()
	 */
	@Override
	public String getTargetIcon() {
		return (String)data.get(IRelationStruct.RELATION_LABEL);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IRelationStruct#setTargetLocator(java.lang.String)
	 */
	@Override
	public void setTargetLocator(String locator) {
		data.put(IRelationStruct.TARGET_LOCATOR, locator);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IRelationStruct#getTargetLocator()
	 */
	@Override
	public String getTargetLocator() {
		return (String)data.get(IRelationStruct.TARGET_LOCATOR);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IRelationStruct#setTargetLabel(java.lang.String)
	 */
	@Override
	public void setTargetLabel(String label) {
		data.put(IRelationStruct.TARGET_LABEL, label);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IRelationStruct#getTargetLabel()
	 */
	@Override
	public String getTargetLabel() {
		return (String)data.get(IRelationStruct.TARGET_LABEL);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IRelationStruct#setTranscluderLocator(java.lang.String)
	 */
	@Override
	public void setTargetNodeType(String nodeType) {
		data.put(IRelationStruct.TARGET_TYPE, nodeType);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IRelationStruct#getTranscluderId()
	 */
	@Override
	public String getTargetNodeType() {
		return (String)data.get(IRelationStruct.TARGET_TYPE);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IRelationStruct#toJSON()
	 */
	@Override
	public String toJSON() {
		return data.toJSONString();
	}

	@Override
	public void setSourceOrTarget(String sORt) {
		data.put(IRelationStruct.SOURCE_OR_TARGET, sORt);
	}

	@Override
	public String getSourceOrTarget() {
		return (String)data.get(IRelationStruct.SOURCE_OR_TARGET);
	}

	@Override
	public void setRelationLocator(String locator) {
		data.put(IRelationStruct.RELATION_LOCATOR, locator);
	}

	@Override
	public String getRelationLocator() {
		return (String)data.get(IRelationStruct.RELATION_LOCATOR);
	}

	@Override
	public JSONObject getData() {
		return data;
	}

}
