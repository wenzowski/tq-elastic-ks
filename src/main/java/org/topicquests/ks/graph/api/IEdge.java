/**
 * 
 */
package org.topicquests.ks.graph.api;

/**
 * @author park
 * @see http://www.tinkerpop.com/docs/javadocs/blueprints/2.0.0/com/tinkerpop/blueprints/Edge.html
 */
public interface IEdge extends IElement {

	/**
	 * We are asking for either the <em>In</em> or
	 * <em>Out</em> vertex, but those could be either
	 * an {@link IVertex} or an {@link IEdge}
	 * @param direction
	 * @return
	 */
	IElement getVertex(String direction);
	
	void setVertex(IElement v, String direction);
}
