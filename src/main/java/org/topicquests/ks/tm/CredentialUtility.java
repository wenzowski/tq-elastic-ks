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
package org.topicquests.ks.tm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.api.ITicket;
import org.topicquests.ks.tm.api.ISubjectProxy;

/**
 * @author park
 *
 */
public class CredentialUtility {
	private SystemEnvironment environment;

	/**
	 * 
	 */
	public CredentialUtility(SystemEnvironment env) {
		environment = env;
	}

	/**
	 * <p>Return <code>1</code> if sufficient <code>credentials</code>
	 * to allow viewing this <code>node></p>
	 * <p>Return <code>0</code> if not sufficient <code>credentials</code></p>
	 * <p>Return <code>-1</code> if node has been removed: (isAlive = false)</p>
	 * @param node
	 * @param credentials
	 * @return
	 */
	public int checkCredentials(ISubjectProxy node, ITicket credentials) {
		int what = 1; //default
		if (!node.getIsLive())
			return -1;
		if (node.getIsPrivate()) {
			//node created by this dude?
			String cid = node.getCreatorId();
			if (!cid.equals(credentials.getUserLocator())) {
				//Same avatar?
				boolean found = false;
				List<String>l = credentials.listAvatars();
				if (l != null) {
					if (l.contains(cid))
						found = true;
				}
				if (!found) {
					//check goup
					Set<String> groupLocators = Sets.newHashSet(credentials.listGroupLocators());
					Set<String> aclValues = Sets.newHashSet(node.listACLValues());
					SetView<String> intersection = Sets.intersection(groupLocators, aclValues);
					found = !intersection.isEmpty();
				}
				if (!found)
					return 0;
			}
		}
		return what; 
	}

}
