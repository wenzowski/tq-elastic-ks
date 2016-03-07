/**
 * 
 */
package org.topicquests.ks.graph.api;

import java.util.Iterator;

/**
 * @author park
 * @see http://www.tinkerpop.com/docs/javadocs/blueprints/2.0.0/com/tinkerpop/blueprints/Query.html
 */
public interface IQuery {
	
	/**
	 * return the number of edges that are unfiltered
	 * set direction first
	 * @return
	 */
	long count();
	
	/**
	 * Set  the query direction
	 * @param direction
	 * @return
	 */
	IQuery direction(String direction);
	
	Iterator<IEdge> edges();
	
	IQuery has(String key, Object value);
	
	IQuery labels(String...labels);
	
	IQuery limit(long max);
	
	/**
	 * Return vertices on the other end of matching edges
	 * @return
	 */
	Iterator<IVertex> vertices();
}
