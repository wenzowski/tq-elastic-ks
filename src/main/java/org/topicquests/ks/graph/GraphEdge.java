/**
 * 
 */
package org.topicquests.ks.graph;

import java.util.*;

import net.minidev.json.JSONObject;

import org.topicquests.ks.graph.api.IEdge;
import org.topicquests.ks.graph.api.IElement;
import org.topicquests.ks.graph.api.IGraphOntology;
import org.topicquests.ks.graph.api.IVertex;

/**
 * @author park
 *
 */
public class GraphEdge implements IEdge {
	private JSONObject data;

	/**
	 * 
	 */
	public GraphEdge(String vIndex, String eIndex) {
		data = new JSONObject();
		this.setVertexIndex(vIndex);
		this.setEdgeIndex(eIndex);
	}

	public GraphEdge(JSONObject jo) {
		data = jo;
	}
	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IElement#getId()
	 */
	@Override
	public String getId() {
		return (String)data.get(IGraphOntology.ID_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IElement#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		data.put(IGraphOntology.ID_PROPERTY, id);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IElement#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String key) {
		return data.get(key);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IElement#setProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setProperty(String key, Object value) {
		data.put(key, value);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IElement#removeProperty(java.lang.String)
	 */
	@Override
	public void removeProperty(String key) {
		data.remove(key);
	}
	
	@Override
	public boolean hasKeyValue(String key, Object value) {
		Object v = this.getProperty(key);
		if (v != null)
			return v.equals(value);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IElement#listPropertyKeys()
	 */
	@Override
	public Iterator<String> listPropertyKeys() {
		return data.keySet().iterator();
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IElement#setLabel(java.lang.String)
	 */
	@Override
	public void setLabel(String label) {
		data.put(IGraphOntology.LABEL_PROPERTY, label);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IElement#getLabel()
	 */
	@Override
	public String getLabel() {
		return (String)data.get(IGraphOntology.LABEL_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IElement#toJSONString()
	 */
	@Override
	public String toJSONString() {
		return data.toJSONString();
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IEdge#getVertex(java.lang.String)
	 */
	@Override
	public IElement getVertex(String direction) {
		JSONObject jo;
		if (direction.equals(IGraphOntology.DIRECTION_IN))
			jo = (JSONObject)data.get(IGraphOntology.IN_VERTEX_PROPERTY);
		else
			jo = (JSONObject)data.get(IGraphOntology.OUT_VERTEX_PROPERTY);
		return new GraphVertex(jo);
	}
	
	@Override
	public void setVertex(IElement v, String direction) {
		if (direction.equals(IGraphOntology.DIRECTION_IN)) {
			data.put(IGraphOntology.IN_VERTEX_PROPERTY, v.getData());
			data.put(IGraphOntology.IN_VERTEX_ID_PROPERTY_TYPE, v.getId());
		} else {
			data.put(IGraphOntology.OUT_VERTEX_PROPERTY, v.getData());
			data.put(IGraphOntology.OUT_VERTEX_ID_PROPERTY_TYPE, v.getId());			
		}
	}

	@Override
	public void setVersion(String version) {
		data.put(IGraphOntology.VERSION_PROPERTY, version);
	}

	@Override
	public String getVersion() {
		return (String)data.get(IGraphOntology.VERSION_PROPERTY);
	}
	@Override
	public JSONObject getData() {
		return data;
	}

	@Override
	public boolean isVertex() {
		return false;
	}

	@Override
	public void doUpdate() {
		this.setVersion(Long.toString(System.currentTimeMillis()));
	}

	@Override
	public void setVertexIndex(String index) {
		data.put(VERTEX_INDEX_PROP, index);

	}

	@Override
	public String getVertexIndex() {
		return (String)data.getAsString(VERTEX_INDEX_PROP);
	}

	@Override
	public void setEdgeIndex(String index) {
		data.put(EDGE_INDEX_PROP, index);
	}

	@Override
	public String getEdgeIndex() {
		return (String)data.getAsString(EDGE_INDEX_PROP);
	}

}
