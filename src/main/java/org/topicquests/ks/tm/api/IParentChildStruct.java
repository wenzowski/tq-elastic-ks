/**
 * 
 */
package org.topicquests.ks.tm.api;

import net.minidev.json.JSONObject;

/**
 * @author park
 * @deprecated -- not used anymore
 */
public interface IParentChildStruct {

	void setContextLocator(String context);
	String getContextLocator();
	void setLocator(String locator);
	String getLocator();

	JSONObject getData();
	
	String toJSON();
}
