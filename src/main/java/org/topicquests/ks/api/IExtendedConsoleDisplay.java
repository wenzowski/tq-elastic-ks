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
package org.topicquests.ks.api;

import javax.swing.JPanel;

import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.ui.SearchTab;
import org.topicquests.ks.ui.SuggestedMergeTab;

/**
 * @author park
 *
 */
public interface IExtendedConsoleDisplay {

	void toConsole(String text);
	
	void toStatus(String text);

	/**
	 * Make the {@link IConsoleDisplay} extensible by adding
	 * standAlone <code>tab</code> objects
	 * @param name
	 * @param tab
	 */
	void addStandaloneTab(String name, JPanel tab);
	
	/**
	 * Add other {@link IEnvironment} objects for shutting down
	 * @param e
	 */
	void addShutDownEnvironments(IEnvironment e);
	
	/**
	 * Allow to set the <code>title</code>
	 * @param title
	 */
	void setConsoleTitle(String title);
	
	/**
	 * Returns this tab
	 * @return can return <code>null</code>
	 */
	SuggestedMergeTab getSuggestedMergeTab();
	
	/**
	 * Returns this tab
	 * @return
	 */
	SearchTab getSearchTab();
	
}
