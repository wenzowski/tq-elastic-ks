/**
 * 
 */
package org.topicquests.ks.graph.api;

import java.util.Map;

import org.topicquests.common.api.IResult;


/**
 * @author park
 *
 */
public interface IGraphProvider {

	/**
	 * This version goes to the default graph index
	 * @return
	 */
	IVertex newVertex();
	
	IVertex newVertex(String vIndex, String eIndex);
	
	/**
	 * When an {@link IVertex} is fetched, must populate
	 * its edges;
	 * @param id
	 * @param isPopulated TODO
	 * @return
	 */
	IResult getVertex(String id, boolean isPopulated);
	
	IResult getVertex(String id, String vIndex, String eIndex, boolean isPopulated);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	IResult getEdge(String id);
	
	IResult getEdge(String id, String vIndex, String eIndex);
	
	/**
	 * Populate the edges of this <code>v</code>
	 * @param v
	 * @return
	 */
	IResult populateVertex(IVertex v);
	
	//IResult populateVertex(IVertex v, String vIndex, String eIndex);
	
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
	
	//IResult updateVertex(IVertex v, String vIndex, boolean checkVersion);
	
	/**
	 * Provide the ability to update an {@link Edge}
	 * @param e
	 * @param checkVersion TODO
	 * @return
	 */
	IResult updateEdge(IEdge e, boolean checkVersion);
	
	//IResult updateEdge(IEdge e, String eIndex, boolean checkVersion);
	
	/**
	 * Extended ability to create a {@link Vertex} with <code>properties</code>
	 * @param v TODO
	 * @return
	 */
	IResult addVertex(IVertex v);
	
	//IResult addVertex(IVertex v, String vIndex);
	
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
	
	//IResult addEdge(String id, IVertex outVertex, IVertex inVertex, 
	//		String label, String vIndex, String eIndex);
	
	void removeVertexFromCache(String vertexId);

	void removeEdgeFromCache(String edgeId);
}
