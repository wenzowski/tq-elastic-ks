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

package org.topicquests.ks.tm.api;

import java.util.Map;

import org.topicquests.common.api.IResult;
import org.topicquests.ks.SystemEnvironment;

/**
 * @author park
 *
 */
public interface IVirtualizer {

	/**
	 * Create a <em>VirtualNode</em>
	 * @param primary
	 * @param merge
	 * @param mergeData
	 * @param confidence
	 * @param userLocator
	 * @return returns the locator of the created VirtualNode
	 */
	IResult createVirtualNode(ISubjectProxy primary, ISubjectProxy merge,
				Map<String,Double> mergeData, double confidence,
				String userLocator);
	
	/**
	 * Init allows us to use different implementations
	 * @param env
	 */
	void init(SystemEnvironment env);
}
