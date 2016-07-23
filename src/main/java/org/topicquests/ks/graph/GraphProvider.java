/**
 * 
 */
package org.topicquests.ks.graph;

import java.util.*;

import net.minidev.json.JSONObject;

import org.nex.util.LRUCache;
import org.topicquests.common.ResultPojo;
import org.topicquests.common.api.IResult;
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
		return updateVertex(v, v.getVertexIndex(), checkVersion);
	}


	
	IResult updateVertex(IVertex v, String vIndex, boolean checkVersion) {
		removeVertexFromCache(v.getId());
		JSONObject jo = v.getData();
		jo.remove(IGraphOntology.OUT_EDGE_LIST_PROPERTY);
		jo.remove(IGraphOntology.IN_EDGE_LIST_PROPERTY);
		System.out.println("UPDATEVERTEX "+jo.keySet()+" "+vIndex);
		return elasticDatabase.updateFullNode(v.getId(), vIndex, jo, checkVersion);
	}


	private IResult indexVertex(IVertex v, String vIndex) {
		JSONObject jo = v.getData();
		//FIRST, strip edges off since their endpoints might change
		jo.remove(IGraphOntology.OUT_EDGE_LIST_PROPERTY);
		jo.remove(IGraphOntology.IN_EDGE_LIST_PROPERTY);
		System.out.println("INDEXING V "+jo.toJSONString());
		IResult result = elasticDatabase.indexNode(v.getId(), vIndex, jo);
		return result;
	}
	
	private IResult indexEdge(IEdge e, String eIndex) {
		//FIRST, strip edge of its loaded vertex objects
		JSONObject jo = e.getData();
		jo.remove(IGraphOntology.OUT_VERTEX_PROPERTY);
		jo.remove(IGraphOntology.IN_VERTEX_PROPERTY);
		IResult result = elasticDatabase.indexNode(e.getId(), eIndex, jo);
		return result;	
	}
	
	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IGraphProvider#updateEdge(org.topicquests.hyperbrane.graph.api.IEdge)
	 */
	@Override
	public IResult updateEdge(IEdge e, boolean checkVersion) {
		return updateEdge(e, e.getEdgeIndex(), checkVersion);
	}

	
	IResult updateEdge(IEdge e, String eIndex, boolean checkVersion) {
		removeEdgeFromCache(e.getId());
		JSONObject jo = e.getData();
		jo.remove(IGraphOntology.OUT_VERTEX_PROPERTY);
		jo.remove(IGraphOntology.IN_VERTEX_PROPERTY);
		return elasticDatabase.updateFullNode(e.getId(), eIndex, jo, checkVersion);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IGraphProvider#addVertex(java.lang.String, net.minidev.json.JSONObject)
	 */
	@Override
	public IResult addVertex(IVertex v) {
		return indexVertex(v, v.getVertexIndex());
	}


	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IGraphProvider#addEdge(java.lang.String, org.topicquests.hyperbrane.graph.api.IVertex, org.topicquests.hyperbrane.graph.api.IVertex, java.lang.String, net.minidev.json.JSONObject)
	 */
	@Override
	public IResult addEdge(String id, IVertex outVertex, IVertex inVertex,
			String label) {
		return addEdge(id, outVertex, inVertex, label, outVertex.getVertexIndex(), outVertex.getEdgeIndex());
	}

	
	IResult addEdge(String id, IVertex outVertex, IVertex inVertex, String label, String vIndex, String eIndex) {
		IEdge e = new GraphEdge(vIndex, eIndex);
		e.setLabel(label);
		e.setId(id);
		e.setVersion(Long.toString(System.currentTimeMillis()));
		e.setVertex(inVertex, IGraphOntology.DIRECTION_IN);
		e.setVertex(outVertex, IGraphOntology.DIRECTION_OUT);
		JSONObject jo = (JSONObject)e.getData().clone();
		System.out.println("ADDINGEDGE0 "+e.toJSONString());
		inVertex.addEdge(IGraphOntology.DIRECTION_OUT, e);
		inVertex.doUpdate();
		System.out.println("ADDINGEDGE1 "+inVertex.toJSONString());
		outVertex.addEdge(IGraphOntology.DIRECTION_IN, new GraphEdge(jo));
		outVertex.doUpdate();
		System.out.println("ADDINGEDGE2 "+outVertex.toJSONString());
		IResult result = new ResultPojo();
		IResult r = this.updateVertex(inVertex, vIndex, true);
		if (r.hasError())
			result.addErrorString(r.getErrorString());
		r = this.updateVertex(outVertex, vIndex, true);
		if (r.hasError())
			result.addErrorString(r.getErrorString());
		r = indexEdge(e, eIndex);
		if (r.hasError())
			result.addErrorString(r.getErrorString());

		return result;
	}
	
	@Override
	public IResult getVertex(String id, boolean isPopulated) {
		return getVertex(id, VERTEX_INDEX, EDGE_INDEX, isPopulated);
	}
	
	@Override
	public IResult getVertex(String id, String vIndex, String eIndex, boolean isPopulated) {
		IResult result = new ResultPojo();
		IVertex v = null; //(IVertex)vertexCache.get(id);
		JSONObject jo = null;
		if (v == null) {
			IResult r = _getVertex(id, vIndex);
			jo = (JSONObject)r.getResultObject();
			
			if (jo == null) {
				return result; // null
			}
			System.out.println("GET "+id+" "+jo.keySet());
			///////////////////////////////
			//TODO
			// THERE is a bug in which InEdges are showing up in the
			// Client's cache -- NOT in the index.
			// LOTS of traces do not show how it is getting there, so
			// we do this cheat:
			//   we clear the cache and try again to force a fetch
			//   from the index
			///////////////////////////////
			Object o = jo.get(IGraphOntology.IN_EDGE_LIST_PROPERTY);
			Object o1 = jo.get(IGraphOntology.OUT_EDGE_LIST_PROPERTY);
			if (o != null || o1 != null) {
				System.out.println("GETTTT bad");
				elasticDatabase.clearCache();
				r = _getVertex(id, vIndex);
					jo = (JSONObject)r.getResultObject();
			}
			v = new GraphVertex(jo);
		} else {
			jo = v.getData();
		}
		//System.out.println("GET1 "+id+" "+jo);
		if (isPopulated) {
			String eid;
			Iterator<String>itr;
			List<String> edges = (List<String>)jo.get(IGraphOntology.IN_EDGE_ID_LIST_PROPERTY_TYPE);
			System.out.println("INS "+edges);
			if (edges != null)
				grabEdges(result, IGraphOntology.DIRECTION_IN, v, edges, vIndex, eIndex);
			edges = (List<String>)jo.get(IGraphOntology.OUT_EDGE_ID_LIST_PROPERTY_TYPE);
			System.out.println("OUTS "+edges);
			if (edges != null)
				grabEdges(result, IGraphOntology.DIRECTION_OUT, v, edges, vIndex, eIndex);
			result.setResultObject(v);
		}
		System.out.println("GET+ "+id);
		return result;	
	}

	public IResult populateVertex(IVertex v) {
		return populateVertex(v, v.getVertexIndex(), v.getEdgeIndex());
	}
	
	IResult populateVertex(IVertex v, String vIndex, String eIndex) {
		IResult result = new ResultPojo();
		System.out.println("PopulateVertex "+v.toJSONString());
		Iterator<String> itr;
		List<String> edges = null;
		Iterator<IEdge>ite = v.listEdges(IGraphOntology.DIRECTION_IN, null);
		if (ite == null) { // alredy populated?
			edges = (List<String>)v.getData().get(IGraphOntology.IN_EDGE_ID_LIST_PROPERTY_TYPE);
			if (edges != null)
				grabEdges(result, IGraphOntology.DIRECTION_IN, v, edges, vIndex, eIndex);
		}
		ite = v.listEdges(IGraphOntology.DIRECTION_OUT, null);
		if (ite == null) { // already populated?
			edges = (List<String>)v.getData().get(IGraphOntology.OUT_EDGE_ID_LIST_PROPERTY_TYPE);
			if (edges != null)
				grabEdges(result, IGraphOntology.DIRECTION_OUT, v, edges, vIndex, eIndex);
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
	 * @param vIndex TODO
	 * @param eIndex 
	 */
	private void grabEdges(IResult r, String direction, IVertex v, List<String>edges, String vIndex, String eIndex) {
		System.out.println("GEDGES "+edges);
		Iterator<IEdge> l = v.listEdges(direction, null);
		if (l == null) {
			String eid;
			Iterator<String>itr = edges.iterator();
			IResult rx;
			IEdge ex;
			while (itr.hasNext()) {
				eid = itr.next();
				rx = getEdge(eid, vIndex, eIndex);
				if (rx.hasError())
					r.addErrorString(rx.getErrorString());
				ex = (IEdge)rx.getResultObject();
				System.out.println("GEDGES2 "+ex.toJSONString());			
				if (ex != null)
					v.addEdge(direction, ex);
			}
		}
		System.out.println("GEDGES+");
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

	private IResult _getVertex(String id, String index) {
		IResult result = elasticDatabase.getNodeAsJSONObject(id, index);
		return result;
	}
	
	@Override
	public IResult getEdge(String id) {
		return getEdge(id, VERTEX_INDEX, EDGE_INDEX);
	}

	@Override
	public IResult getEdge(String id, String vIndex, String eIndex) {
		IResult result =  new ResultPojo();;
		IEdge edx = null; //(IEdge)edgeCache.get(id);
		System.out.println("GetEdge "+id+" "+edx);
		//elasticDatabase.clearCache();
		if (edx == null) {
			IResult r = elasticDatabase.getNodeAsJSONObject(id, eIndex);
			////////////////////
			//TODO
			// see getVertex hack
			///////////////////
			JSONObject jo = (JSONObject)r.getResultObject();
			Object o = jo.get(IGraphOntology.OUT_VERTEX_PROPERTY);
			Object o1 = jo.get(IGraphOntology.IN_VERTEX_PROPERTY);
			if (o != null || o1 != null) {
				System.out.println("GEEE bad");
				elasticDatabase.clearCache();
				r = elasticDatabase.getNodeAsJSONObject(id, eIndex);
				jo = (JSONObject)r.getResultObject();
			}
			System.out.println("GetEdge-1"+jo);
			if (jo != null) {
				edx = new GraphEdge(jo);
				r = populateEdge(edx, vIndex);
				if (r.hasError())
					result.addErrorString(r.getErrorString());
				edgeCache.add(id, edx);
			}
		}
		System.out.println("GetEdge+ "+edx.getData().keySet());		
		result.setResultObject(edx);
		return result;
	}

	private IResult populateEdge(IEdge e, String vIndex) {
		IResult result = new ResultPojo();
		JSONObject jo = e.getData();
		String lox = (String)jo.get(IGraphOntology.IN_VERTEX_ID_PROPERTY_TYPE);
		IResult r = this._getVertex(lox, vIndex);
		if (r.hasError())
			result.addErrorString(r.getErrorString());
		JSONObject jx = (JSONObject)r.getResultObject();
		if (jx != null)
			e.setVertex(new GraphVertex(jx), IGraphOntology.DIRECTION_IN);
		lox = (String)jo.get(IGraphOntology.OUT_VERTEX_ID_PROPERTY_TYPE);
		r = this._getVertex(lox, vIndex);
		if (r.hasError())
			result.addErrorString(r.getErrorString());
		jx = (JSONObject)r.getResultObject();
		if (jx != null)
			e.setVertex(new GraphVertex(jx), IGraphOntology.DIRECTION_OUT);
		return result;
	}

	@Override
	public IVertex newVertex() {
		return newVertex(VERTEX_INDEX, EDGE_INDEX);
	}

	@Override
	public IVertex newVertex(String vIndex, String eIndex) {
		return new GraphVertex(vIndex, eIndex);
	}





}
