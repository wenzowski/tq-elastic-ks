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

/**
 * @author park
     * Interface for marker comparators.  During graph matching, and in other situations,
     * the Notio package may need to establish whether two markers are equivalent (refer
     * to the same entity).  
  	 * This decision is likely to be application-specific since markers may refer to 
  	 * entities outside of the scope of the Notio layer, or the application may wish to 
  	 * form relationships when comparisons are made.  In order to allow applications to
  	 * specify the exact rules, they may provide an implementation of this interface as
  	 * part of a MatchingScheme.  The comparator will be called whenever two markers are
  	 * compared.  Possible uses include:
  	 * <UL>
  	 *   <LI>comparing complex data objects such as images referred to by markers
  	 *   <LI>testing and creating equivalencies between markers due to assertions about them
  	 *   <LI>references to external database for which the markers are keys
  	 * </UL>
 *
 */
public interface IMarkerComparator {

	boolean compareMarkers(IMarker firstMarker, IMarker secondMarker);
}
