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
public interface ICGConceptLinkStruct {

	public static final String
	CONTEXT_LOCATOR		= "contextLox",
	LOCATOR				= "locator",
	ICON				= "smallImagePath",
	SUBJECT				= "subject",
	LINK_TYPE_LOCATOR	= "linkTypeLox";

	void setContextLocator(String context);
	String getContextLocator();
	void setLocator(String locator);
	String getLocator();
	void setSmallIcon(String iconPath);
	String getSmallIcon();
	void setSubject(String subject);
	String getSubject();
	void setLinkTypeLocator(String linkTypeLocator);
	String getLinkTypeLocator();

	String toJSON();
}
