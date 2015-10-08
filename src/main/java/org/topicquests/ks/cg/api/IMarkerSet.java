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
 *
 */
public interface IMarkerSet extends ISubjectProxy {

	/**
	 * Add an {@link IMarker} to the set and return
	 * the unique Id given internally to that <code>marker</code>
	 * @param marker
	 * @return
	 */
	String addMarker(IMarker marker);
	
//	String getNextAvailableMarkerID();
	
	IMarker getMarkerByMarkerID(String markerID);
	
	IMarker getMarkerByIndividual(Object individual);
	
	void setMarkerIndividual(Object individual, IMarker marker);
	
	void removeMarkerIndividual(Object deadIndividual);
}
