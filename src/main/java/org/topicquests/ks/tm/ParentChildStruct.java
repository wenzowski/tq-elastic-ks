/**
 * 
 */
package org.topicquests.ks.tm;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import org.topicquests.ks.tm.api.IChildStruct;
import org.topicquests.ks.tm.api.IParentChildStruct;

/**
 * @author park
 * @deprecated  not used anymore
 */
public class ParentChildStruct implements IParentChildStruct {
	private JSONObject data;

	/**
	 * 
	 */
	public ParentChildStruct() {
		data = new JSONObject();
	}
	
	public ParentChildStruct(JSONObject jo) {
		data = jo;
	}
	public ParentChildStruct(String locator, String context) {
		data = new JSONObject();
		setLocator(locator);
		setContextLocator(context);
	}

	public ParentChildStruct(String json) throws Exception {
		JSONParser p = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
		data = (JSONObject)p.parse(json);
	}
	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.IParentChildStruct#setContextLocator(java.lang.String)
	 */
	@Override
	public void setContextLocator(String context) {
		data.put(IChildStruct.CONTEXT_LOCATOR, context);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.IParentChildStruct#getContextLocator()
	 */
	@Override
	public String getContextLocator() {
		return (String)data.get(IChildStruct.CONTEXT_LOCATOR);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.IParentChildStruct#setLocator(java.lang.String)
	 */
	@Override
	public void setLocator(String locator) {
		data.put(IChildStruct.LOCATOR, locator);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.IParentChildStruct#getLocator()
	 */
	@Override
	public String getLocator() {
		return (String)data.get(IChildStruct.LOCATOR);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.IParentChildStruct#toJSON()
	 */
	@Override
	public String toJSON() {
		return data.toJSONString();
	}

	@Override
	public JSONObject getData() {
		return data;
	}

}
