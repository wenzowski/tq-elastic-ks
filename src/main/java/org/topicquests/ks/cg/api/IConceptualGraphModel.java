/**
 * 
 */
package org.topicquests.ks.cg.api;

import org.topicquests.ks.tm.api.ISubjectProxy;

/**
 * @author park
 *
 */
public interface IConceptualGraphModel {

	/**
	 * Create a new {@link ICGConcept} pivoted to <code>topicLocator</code>
	 * @param conceptLocator can be <code>null</code>
	 * @param topicLocator
	 * @param userLocator
	 * @return
	 */
	ISubjectProxy newConcept(String conceptLocator,
							 String topicLocator,
							 String userLocator);
}
