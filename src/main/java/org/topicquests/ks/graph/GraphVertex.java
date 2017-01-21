/**
 * 
 */
package org.topicquests.ks.graph;

import java.util.*;

import net.minidev.json.JSONObject;

import org.topicquests.support.ResultPojo;
import org.topicquests.support.api.IResult;
import org.topicquests.ks.graph.api.IEdge;
import org.topicquests.ks.graph.api.IElement;
import org.topicquests.ks.graph.api.IGraphOntology;
import org.topicquests.ks.graph.api.IGraphProvider;
import org.topicquests.ks.graph.api.IQuery;
import org.topicquests.ks.graph.api.IVertex;

/**
 * @author park
 *
 */
public class GraphVertex implements IVertex {
	private JSONObject data;
	/**
	 * 
	 */
	public GraphVertex() {
		data = new JSONObject();
	}
	
	public GraphVertex(JSONObject jo) {
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

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IElement#listPropertyKeys()
	 */
	@Override
	public Iterator<String> listPropertyKeys() {
		return data.keySet().iterator();
	}
	@Override
	public boolean hasKeyValue(String key, Object value) {
		Object v = this.getProperty(key);
		if (v != null)
			return v.equals(value);
		return false;
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
	 * @see org.topicquests.hyperbrane.graph.api.IVertex#listEdges(java.lang.String, java.lang.String[])
	 */
	@Override
	public Iterator<IEdge> listEdges(String direction, String... labels) {
		//TODO to turn this into a query means a join would be needed
		String prop = IGraphOntology.IN_EDGE_LIST_PROPERTY;
		if (direction.equals(IGraphOntology.DIRECTION_OUT))
			prop = IGraphOntology.OUT_EDGE_LIST_PROPERTY;
		List<IEdge> edges = (List<IEdge>)data.get(prop);
		if (edges != null) {
			List<IEdge> result = new ArrayList<IEdge>();
			IEdge e;
			String lbl;
			Iterator<IEdge> itr = edges.iterator();
			while (itr.hasNext()) {
				e = itr.next();
				lbl = e.getLabel();
				if (labels == null)
					result.add(e);
				else {
					for (String l : labels) {
						if (l.equals(lbl)) {
							result.add(e);
							break;
						}
					}
				}
			}
			return result.iterator();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IVertex#listAdjacentVertices(java.lang.String, java.lang.String[])
	 */
	@Override
	public IResult listAdjacentVertices(String direction,
			IGraphProvider graphDatabase, String... labels) {
		System.out.println("LISTADJACENTVERTS- "+data.toJSONString());
		IResult result = new ResultPojo();
		Iterator<IEdge> edges = this.listEdges(direction, labels);
		System.out.println("LISTADJACENTVERTS-1 "+edges);
		if (edges != null) {
			List<IVertex> rx = new ArrayList<IVertex>();
			IElement v;
			IVertex v1;
			IEdge e;
			IResult r;
			while (edges.hasNext()) {
				e = edges.next();
				System.out.println("LISTADJACENTVERTS-2 "+e.toJSONString());
				v = e.getVertex(IGraphOntology.DIRECTION_OUT);
				if (v.isVertex()) {
					v1 = (IVertex)v;
					r = graphDatabase.populateVertex(v1);
					if (r.hasError())
						result.addErrorString(r.getErrorString());
					rx.add(v1);
				}
			}
			result.setResultObject(rx);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IVertex#query()
	 */
	@Override
	public IQuery query() {
		return new VertexQuery(this);
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
	public void addEdge(String direction, IEdge e) {
		String prop = IGraphOntology.IN_EDGE_LIST_PROPERTY;
		if (direction.equals(IGraphOntology.DIRECTION_OUT))
			prop = IGraphOntology.OUT_EDGE_LIST_PROPERTY;
		List<IEdge> edges = (List<IEdge>)data.get(prop);
		if (edges == null) {
			edges = new ArrayList<IEdge>();
		}
		edges.add(e);
		data.put(prop, edges);
		prop = IGraphOntology.IN_EDGE_ID_LIST_PROPERTY_TYPE;
		if (direction.equals(IGraphOntology.DIRECTION_OUT))
			prop = IGraphOntology.OUT_EDGE_ID_LIST_PROPERTY_TYPE;
		List<String> eids = (List<String>)data.get(prop);
		if (eids == null)
			eids = new ArrayList<String>();
		if (!eids.contains(e.getId()))
			eids.add(e.getId());
		data.put(prop, eids);
	}
	
	@Override
	public boolean isVertex() {
		return true;
	}
	
	@Override
	public void doUpdate() {
		this.setVersion(Long.toString(System.currentTimeMillis()));
	}

}
