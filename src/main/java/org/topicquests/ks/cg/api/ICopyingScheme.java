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
 *
 */
public interface ICopyingScheme {
	public final static int 
	  /** Graph copy control flag: a duplicate graph will be created.  **/
	  GR_COPY_DUPLICATE = 0,
	  /** Graph copy control flag: a reference to the existing graph will be used.  **/
	  GR_COPY_REFERENCE = 1,
	  
	  /** Concept copy control flag: a duplicate node will be created.  **/
	  CN_COPY_DUPLICATE = 10,
	  /** Concept copy control flag: a reference to the existing node will be used.  **/
	  CN_COPY_REFERENCE = 11,
	  
	  /** Relation copy control flag: a duplicate node will be created.  **/
	  RN_COPY_DUPLICATE = 20,
	  /** Relation copy control flag: a reference to the existing node will be used.  **/
	  RN_COPY_REFERENCE = 21,
	  
	  /** Designator copy control flag: a duplicate designator will be created.  **/
	  DG_COPY_DUPLICATE = 40,
	  /** Designator copy control flag: a reference to the existing designator will be used.  **/
	  DG_COPY_REFERENCE = 41,
	    
	  /** Comment copy control flag: node and graph comments will not be copied.  **/
	  COMM_COPY_OFF = 50,
	  /** Comment copy control flag: node and graph comments will be copied.  **/
	  COMM_COPY_ON = 51;
	
	int getGraphFlag();
	
	int getConceptFlag();
	
	int getRelationFlag();
	
	int getDesignatorFlag();
	
	int getCommentFlag();
	
	ICopyingScheme getNestedCopyingScheme();
}
