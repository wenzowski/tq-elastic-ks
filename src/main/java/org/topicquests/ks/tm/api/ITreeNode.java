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

import java.util.List;

/**
 * @author park
 *
 */
public interface ITreeNode {

	void setNodeLocator(String locator);
	String getNodeLocator();
	
	void setNodeLabel(String label);
	String getNodeLabel();
	
	void addSubclassChild(ITreeNode c);
	
	void addInstanceChild(ITreeNode c);
	
	int getSubclassCount();
	
	int getInstanceCount();
	
	/**
	 * Can return <code>null</code>
	 * @return
	 */
	List<ITreeNode> listSubclassChildNodes();
	
	/**
	 * Can return <code>null</code>
	 * @return
	 */
	List<ITreeNode> listInstanceChildNodes();
	
	String simpleToXML();
}
