/**
 * 
 */
package org.topicquests.ks.graph.api;

import java.util.Map;

import org.topicquests.support.api.IResult;


/**
 * @author park
 *
 */
public interface IGraphProvider {

	/**
	 * When an {@link IVertex} is fetched, must populate
	 * its edges;
	 * @param id
	 * @return
	 */
	IResult getVertex(String id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	IResult getEdge(String id);
	
	/**
	 * Populate the edges of this <code>v</code>
	 * @param v
	 * @return
	 */
	IResult populateVertex(IVertex v);
	
	/////////////////////////////
	// TODO
	//   More elaborate queries
	/////////////////////////////
	/**
	 * Provide the ability to update a {@link Vertex}
	 * @param v
	 * @param checkVersion TODO
	 * @return
	 */
	IResult updateVertex(IVertex v, boolean checkVersion);
	
	/**
	 * Provide the ability to update an {@link Edge}
	 * @param e
	 * @param checkVersion TODO
	 * @return
	 */
	IResult updateEdge(IEdge e, boolean checkVersion);
	
	/**
	 * Extended ability to create a {@link Vertex} with <code>properties</code>
	 * @param id
	 * @param v TODO
	 * @return
	 */
	IResult addVertex(String id, IVertex v);
	
	/**
	 * Extended ability to create an {@link Edge} with <code>properties</code>
	 * @param id
	 * @param outVertex
	 * @param inVertex
	 * @param label
	 * @return
	 */
	IResult addEdge(String id, IVertex outVertex, IVertex inVertex,
			String label);
	
	void removeVertexFromCache(String vertexId);
	void removeEdgeFromCache(String edgeId);
}
