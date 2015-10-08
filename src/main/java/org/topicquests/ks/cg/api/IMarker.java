/*
 * Copyright 2015, TopicQuests
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.topicquests.ks.cg.api;

import org.topicquests.ks.tm.api.ISubjectProxy;

/**
 * @author park
 * <p>A marker is the same thing as a topic locator:
 * [type:marker] -> [Person:Sue]</p>
 */
public interface IMarker extends ISubjectProxy {
	
	//String getMarkerID();
	
	/**
     * Frees the individual associated with this marker.
     * This means that the reference to the individual will be set to null, allowing
     * it to be garbage-collected if no other references exist.  The marker remains
     * as an entry in its MarkerSet but is no longer associated with an individual.
     * If no individual is associated with this Marker when freeIndividual() is called,
     * nothing happens.
	 */
	void freeIndividual();

	Object getIndividual();
	
	/**
	 * Returns the {@link IMarkerSet} to which this individual belongs
	 * @return
	 */
	IMarkerSet getMarkerSet();
	
	boolean conformsToType(IConceptType queryType);
	
	void addTypeConformance(IConceptType newType);
	
	void removeTypeConformance(IConceptType deadType);
}
