/**
 * 
 */
package org.topicquests.ks.graph;

import org.topicquests.ks.graph.api.IGraphProvider;

/**
 * @author park
 *
 */
public class GraphUtility {
	private static GraphUtility instance;
	private static GraphEnvironment environment;
	private static IGraphProvider database;
	
	/**
	 * Initialized in {@link GraphEnvironment}
	 */
	public GraphUtility(GraphEnvironment env, IGraphProvider db) {
		environment = env;
		database = db;
		instance = this;
	}
	
	public static GraphUtility getInstance() {
		return instance;
	}
	public static IGraphProvider getDatabase() {
		return database;
	}

	public static void logDebug(String msg) {
		environment.logDebug(msg);

	}

	public static void logError(String msg, Exception e) {
		environment.logError(msg, e);
	}

}
