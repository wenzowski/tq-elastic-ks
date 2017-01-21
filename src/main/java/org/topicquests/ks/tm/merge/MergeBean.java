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

package org.topicquests.ks.tm.merge;

import java.util.Map;

import org.topicquests.support.ResultPojo;
import org.topicquests.support.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.api.ITQDataProvider;
import org.topicquests.ks.tm.api.IMergeImplementation;
import org.topicquests.ks.tm.api.ISubjectProxyModel;
import org.topicquests.ks.tm.api.IVirtualizer;

/**
 * @author park
 *
 */
public class MergeBean implements IMergeImplementation {
	private SystemEnvironment environment;
	private ITQDataProvider database;
	private ISubjectProxyModel model;
	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IMergeImplementation#init(org.topicquests.model.api.IEnvironment)
	 */
	@Override
	public void init(SystemEnvironment env) {
		this.environment = env;
		database = environment.getDatabase();
	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IMergeImplementation#setNodeModel(org.topicquests.model.api.INodeModel)
	 */
	@Override
	public void setNodeModel(ISubjectProxyModel m) {
		model = m;

	}

	/* (non-Javadoc)
	 * @see org.topicquests.model.api.IMergeImplementation#assertMerge(java.lang.String, java.lang.String, java.util.Map, double, java.lang.String)
	 */
	@Override
	public IResult assertMerge(String sourceNodeLocator,
			String targetNodeLocator, Map<String, Double> mergeData,
			double mergeConfidence, IVirtualizer virtualizer, String userLocator) {
		IResult result = new ResultPojo();
		// TODO Auto-generated method stub
		return result;
	}

}
