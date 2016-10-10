/**
 * 
 */
package org.topicquests.ks.graph.api;

import java.util.Iterator;

import net.minidev.json.JSONObject;

/**
 * @author park
 *
 */
public interface IElement {
	
	String getId();
	
	void setId(String id);
	
	Object getProperty(String key);
	
	void setProperty(String key, Object value);
	
	void removeProperty(String key);
	
	boolean hasKeyValue(String key, Object value);
	
	Iterator<String> listPropertyKeys();
	
	void setLabel(String label);
	
	String getLabel();
	
	String toJSONString();
	
	void setVersion(String version);
	
	String getVersion();
	
	JSONObject getData();

	/**
	 * An {@link IEdge} can have one of {@link IVertex} or
	 * {@link IEdge} as subject and/or object. Thus, they will
	 * be modeled as {@link IElement} objects
	 * @return
	 */
	boolean isVertex();
	
	/**
	 * Update the <em>version</em> of this object
	 */
	void doUpdate();
}
