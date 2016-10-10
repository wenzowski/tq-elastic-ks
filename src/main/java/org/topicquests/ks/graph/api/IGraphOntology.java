/**
 * 
 */
package org.topicquests.ks.graph.api;

/**
 * @author park
 *
 */
public interface IGraphOntology {
	public static final String
		ID_PROPERTY 								= "id",
		LABEL_PROPERTY								= "label",
		IN_VERTEX_ID_PROPERTY_TYPE					="InVertexId",
		OUT_VERTEX_ID_PROPERTY_TYPE					="OutVertexId",
		IN_EDGE_ID_LIST_PROPERTY_TYPE				= "inEdgeIdList",
		OUT_EDGE_ID_LIST_PROPERTY_TYPE				= "outEdgeIdList",
		VERSION_PROPERTY							= "vers",
		//These are the actual vertices of an edge
		// loaded when edge is fetched
		IN_VERTEX_PROPERTY							= "InVert",
		OUT_VERTEX_PROPERTY							= "OutVert",
		//These must be loaded
		IN_EDGE_LIST_PROPERTY						= "InEdges",
		OUT_EDGE_LIST_PROPERTY						= "OutEdges";
	
	public static final String
		DIRECTION_IN = "inD",
		DIRECTION_OUT	= "outD";

}
