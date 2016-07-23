/**
 * 
 */
package org.topicquests.ks.graph;

import java.util.*;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.topicquests.common.ResultPojo;
import org.topicquests.common.api.IResult;
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
	public GraphVertex(String vIndex, String eIndex) {
		data = new JSONObject();
		this.setVertexIndex(vIndex);
		this.setEdgeIndex(eIndex);
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
		List<JSONObject> edges = (List<JSONObject>)data.get(prop);
		if (edges != null) {
			List<IEdge> result = new ArrayList<IEdge>();
			JSONObject jo;
			IEdge e;
			String lbl;
			Iterator<JSONObject> itr = edges.iterator();
			while (itr.hasNext()) {
				jo = itr.next();
				e = new GraphEdge(jo);
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
		System.out.println("SV- "+version);
		data.put(IGraphOntology.VERSION_PROPERTY, version);
		System.out.println("SV+ "+this.toJSONString());
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
		System.out.println("AE- "+this.toJSONString()+"\n"+e.toJSONString());
		final JSONObject myData = data;
		JSONObject jo = e.getData();
		jo = (JSONObject)jo.clone();
		String prop = IGraphOntology.IN_EDGE_ID_LIST_PROPERTY_TYPE;
		if (direction.equals(IGraphOntology.DIRECTION_OUT)) {
			prop = IGraphOntology.OUT_EDGE_ID_LIST_PROPERTY_TYPE;
		} 
		List<String> eids = (List<String>)myData.get(prop);
		System.out.println("AE-0 "+eids+" "+e.getId());
		///////////////////////
		//addEdge has two behaviors:
		// if this is a new operation, addEdge just adds the edgeID
		// otherwise, it's a population exercise: put the edge in
		// as a JSONObject
		///////////////////////
		if (eids != null && eids.contains(e.getId())) {
			//IEdge ex = new GraphEdge(jo);
			//IF it's an IN edge, that edge's OUT vertex is this Vertex
			//IF it's an OUT edge, that edge's IN vertex is this Vertex
			prop = IGraphOntology.IN_EDGE_LIST_PROPERTY;
			if (direction.equals(IGraphOntology.DIRECTION_OUT)) {
				prop = IGraphOntology.OUT_EDGE_LIST_PROPERTY;
				jo.remove(IGraphOntology.IN_VERTEX_PROPERTY);
			} else {
				jo.remove(IGraphOntology.OUT_VERTEX_PROPERTY);
			}
			JSONArray edges = (JSONArray)data.get(prop);
			if (edges == null) {
				edges = new JSONArray();
				//data.put(prop, edges);
			}
			if (!edges.contains(jo))
				edges.add(jo);
			System.out.println("AE-1 "+this.getId()+" "+jo);
			System.out.println("AE-1a "+myData);
			System.out.println("AE-1b "+prop+" "+edges);
			myData.put(prop, edges);
			System.out.println("AE-1c "+myData.keySet());
			System.out.println("AE-2 "+prop+" "+myData.size());
		} else {
			if (eids == null)
				eids = new ArrayList<String>();
			if (!eids.contains(e.getId()))
				eids.add(e.getId());
			myData.put(prop, eids);
		}
		data = myData;
	}
	
	@Override
	public boolean isVertex() {
		return true;
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
