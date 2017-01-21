/**
 * 
 */
package org.topicquests.ks.graph.api;

import java.util.Iterator;

import org.topicquests.support.api.IResult;

/**
 * @author park
 * @see http://www.tinkerpop.com/docs/javadocs/blueprints/2.0.0/com/tinkerpop/blueprints/Vertex.html
 */
public interface IVertex extends IElement {

	/**
	 * if <code>labels</code> == <code>null</code>, return all
	 * @param direction
	 * @param labels 
	 * @return can return <code>null</code> if no edges in <code>direction</code>
	 */
	Iterator<IEdge> listEdges(String direction, String...labels);
	
	/**
	 * NOTE: this {@link IVertex} must be populated (edges populated)
	 * before this method is called
	 * @param direction
	 * @param graphDatabase
	 * @param labels
	 * @return
	 */
	IResult listAdjacentVertices(String direction, IGraphProvider graphDatabase, String...labels);
	
	IQuery query();
	
	void addEdge(String direction, IEdge e);
}
