/**
 * 
 */
package org.topicquests.ks.graph;

import java.util.*;

import net.minidev.json.JSONObject;

import org.topicquests.support.util.LRUCache;
import org.topicquests.support.ResultPojo;
import org.topicquests.support.api.IResult;
import org.topicquests.ks.graph.api.IEdge;
import org.topicquests.ks.graph.api.IGraphOntology;
import org.topicquests.ks.graph.api.IGraphProvider;
import org.topicquests.ks.graph.api.IVertex;
import org.topicquests.node.provider.Client;

/**
 * @author park
 *
 */
public class GraphProvider implements IGraphProvider {
	private GraphEnvironment environment;
	private Client elasticDatabase;
	private final String VERTEX_INDEX = "vertices";
	private final String EDGE_INDEX = "edges";
	private LRUCache vertexCache;
	private LRUCache edgeCache;
	private final int cacheSize = 8192;
	
	/**
	 * 
	 */
	public GraphProvider(GraphEnvironment env, Client db) {
		environment = env;
		elasticDatabase = db;
		vertexCache = new LRUCache(cacheSize);
		edgeCache = new LRUCache(cacheSize);
	}

	@Override
	public void removeVertexFromCache(String vertexId) {
		vertexCache.remove(vertexId);
	}
	@Override
	public void removeEdgeFromCache(String edgeId) {
		edgeCache.remove(edgeId);
	}
	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IGraphProvider#updateVertex(org.topicquests.hyperbrane.graph.api.IVertex)
	 */
	@Override
	public IResult updateVertex(IVertex v, boolean checkVersion) {
		removeVertexFromCache(v.getId());
		return elasticDatabase.updateFullNode(v.getId(), VERTEX_INDEX, v.getData(), checkVersion);
	}

	private IResult indexVertex(IVertex v) {
		JSONObject jo = v.getData();
		jo.remove(IGraphOntology.OUT_EDGE_LIST_PROPERTY);
		jo.remove(IGraphOntology.IN_EDGE_LIST_PROPERTY);
		System.out.println("INDEXING V "+jo.toJSONString());
		IResult result = elasticDatabase.indexNode(v.getId(), VERTEX_INDEX, jo);
		return result;
	}
	
	private IResult indexEdge(IEdge e) {
		//FIRST, strip edge of its loaded vertex objects
		JSONObject jo = e.getData();
		jo.remove(IGraphOntology.OUT_VERTEX_PROPERTY);
		jo.remove(IGraphOntology.IN_VERTEX_PROPERTY);
		IResult result = elasticDatabase.indexNode(e.getId(), EDGE_INDEX, jo);
		return result;	
	}
	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IGraphProvider#updateEdge(org.topicquests.hyperbrane.graph.api.IEdge)
	 */
	@Override
	public IResult updateEdge(IEdge e, boolean checkVersion) {
		removeEdgeFromCache(e.getId());
		return elasticDatabase.updateFullNode(e.getId(), EDGE_INDEX, e.getData(), checkVersion);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IGraphProvider#addVertex(java.lang.String, net.minidev.json.JSONObject)
	 */
	@Override
	public IResult addVertex(String id, IVertex v) {
		return indexVertex(v);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IGraphProvider#addEdge(java.lang.String, org.topicquests.hyperbrane.graph.api.IVertex, org.topicquests.hyperbrane.graph.api.IVertex, java.lang.String, net.minidev.json.JSONObject)
	 */
	@Override
	public IResult addEdge(String id, IVertex outVertex, IVertex inVertex,
			String label) {
		IEdge e = new GraphEdge();
		e.setLabel(label);
		e.setId(id);
		e.setVersion(Long.toString(System.currentTimeMillis()));
		e.setVertex(inVertex, IGraphOntology.DIRECTION_IN);
		e.setVertex(outVertex, IGraphOntology.DIRECTION_OUT);
//		System.out.println("ADDINGEDGE0 "+e.toJSONString());
		inVertex.addEdge(IGraphOntology.DIRECTION_OUT, e);
		inVertex.setVersion(Long.toString(System.currentTimeMillis()));
//		System.out.println("ADDINGEDGE1 "+inVertex.toJSONString());
		outVertex.addEdge(IGraphOntology.DIRECTION_IN, e);
		outVertex.setVersion(Long.toString(System.currentTimeMillis()));
//		System.out.println("ADDINGEDGE2 "+outVertex.toJSONString());
		IResult result = new ResultPojo();
		IResult r = this.updateVertex(inVertex, true);
		if (r.hasError())
			result.addErrorString(r.getErrorString());
		r = this.updateVertex(outVertex, true);
		if (r.hasError())
			result.addErrorString(r.getErrorString());
		r = indexEdge(e);
		if (r.hasError())
			result.addErrorString(r.getErrorString());

		return result;
	}

	@Override
	public IResult getVertex(String id) {
		IResult result = null;
		IVertex v = (IVertex)vertexCache.get(id);
		if (v != null) {
			result = new ResultPojo();
			result.setResultObject(v);
			return result;
		}
		result = _getVertex(id);
		JSONObject jo = (JSONObject)result.getResultObject();
		if (jo != null) {
			v = new GraphVertex(jo);
			vertexCache.add(id, v);
			String eid;
			Iterator<String>itr;
			List<String> edges = (List<String>)jo.get(IGraphOntology.IN_EDGE_ID_LIST_PROPERTY_TYPE);
			if (edges != null)
				grabEdges(result, IGraphOntology.DIRECTION_IN, v, edges);
			edges = (List<String>)jo.get(IGraphOntology.OUT_EDGE_ID_LIST_PROPERTY_TYPE);
			if (edges != null)
				grabEdges(result, IGraphOntology.DIRECTION_OUT, v, edges);
			result.setResultObject(v);
		}
		return result;
	}

	public IResult populateVertex(IVertex v) {
		IResult result = new ResultPojo();
		System.out.println("PopulateVertex "+v.toJSONString());
		List<String> edges = null;
		Iterator<IEdge>ite = v.listEdges(IGraphOntology.DIRECTION_IN, (String [])null);
		if (ite == null) { // alredy populated?
			edges = (List<String>)v.getData().get(IGraphOntology.IN_EDGE_ID_LIST_PROPERTY_TYPE);
			if (edges != null)
				grabEdges(result, IGraphOntology.DIRECTION_IN, v, edges);
		}
		ite = v.listEdges(IGraphOntology.DIRECTION_OUT, (String [])null);
		if (ite == null) { // already populated?
			edges = (List<String>)v.getData().get(IGraphOntology.OUT_EDGE_ID_LIST_PROPERTY_TYPE);
			if (edges != null)
				grabEdges(result, IGraphOntology.DIRECTION_OUT, v, edges);
		}
		result.setResultObject(v);
		//return it to the cache
		vertexCache.add(v.getId(), v);
		return result;
	}
	
	/**
	 * Called only <code>v</code> has not been populated in <code>direction</code>
	 * @param r
	 * @param direction
	 * @param v
	 * @param edges
	 */
	private void grabEdges(IResult r, String direction, IVertex v, List<String>edges) {
		String eid;
		Iterator<String>itr = edges.iterator();
		IResult rx;
		IEdge ex;
		while (itr.hasNext()) {
			eid = itr.next();
			rx = getEdge(eid);
			if (rx.hasError())
				r.addErrorString(rx.getErrorString());
			ex = (IEdge)rx.getResultObject();
			if (ex != null)
				v.addEdge(direction, ex);
		}
	}
	/////////////////////////////////////
	// ISSUE:
	//   Get a Vertex from the database.
	//   It comes without the Edges populated.
	//   Populate each Edge
	//   Each Edge comes without its Vertices populated with Edges
	//   Enormous potential for circularity here.
	//   To populate a vertex,
	//		1- Fetch it 
	//			If it's in the cache, you're done
	//      2- For each Edge ID in each direction
	//			2A: Fetch the Edge
	//			2B: For each Vertex Id (in and out)
	//				2B1: Fetch the vertex
	//					If it's in cache, you're done
	//				2B2: recurse -- that's where stuff happens
	///////////////////////////////////////

	private IResult _getVertex(String id) {
		IResult result = elasticDatabase.getNodeAsJSONObject(id, VERTEX_INDEX);
		return result;
	}
	
	@Override
	public IResult getEdge(String id) {
		IResult result = null;
		IEdge edx = (IEdge)edgeCache.get(id);
		if (edx != null) {
			result = new ResultPojo();
		} else {
			result = elasticDatabase.getNodeAsJSONObject(id, EDGE_INDEX);
			JSONObject jo = (JSONObject)result.getResultObject();
			if (jo != null) {
				edx = new GraphEdge(jo);
				IResult r = populateEdge(edx);
				if (r.hasError())
					result.addErrorString(r.getErrorString());
				edgeCache.add(id, edx);
			}
			
		}
		result.setResultObject(edx);
		return result;
	}

	private IResult populateEdge(IEdge e) {
		IResult result = new ResultPojo();
		JSONObject jo = e.getData();
		String lox = (String)jo.get(IGraphOntology.IN_VERTEX_ID_PROPERTY_TYPE);
		IResult r = this._getVertex(lox);
		if (r.hasError())
			result.addErrorString(r.getErrorString());
		JSONObject jx = (JSONObject)r.getResultObject();
		if (jx != null)
			e.setVertex(new GraphVertex(jx), IGraphOntology.DIRECTION_IN);
		lox = (String)jo.get(IGraphOntology.OUT_VERTEX_ID_PROPERTY_TYPE);
		r = this._getVertex(lox);
		if (r.hasError())
			result.addErrorString(r.getErrorString());
		jx = (JSONObject)r.getResultObject();
		if (jx != null)
			e.setVertex(new GraphVertex(jx), IGraphOntology.DIRECTION_OUT);
		return result;
	}
}
