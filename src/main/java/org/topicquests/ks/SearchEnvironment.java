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
package org.topicquests.ks;

import java.util.*;

import org.topicquests.support.api.IResult;
import org.topicquests.ks.api.IExtendedConsoleDisplay;
import org.topicquests.ks.api.IExtendedEnvironment;
import org.topicquests.ks.api.ITQDataProvider;
import org.topicquests.ks.api.ITicket;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.ks.ui.SearchTab;
/**
 * @author park
 *
 */
public class SearchEnvironment {
	private SystemEnvironment environment;
	private SearchTab searchtab;
	private ITQDataProvider queryModel;
	
	
	/**
	 * 
	 */
	public SearchEnvironment(IExtendedEnvironment e) {
		environment = (SystemEnvironment)e;
		IExtendedConsoleDisplay mainframe = environment.getConsoleDisplay();
		if (mainframe == null)
			throw new RuntimeException("SearchEnvironment null console");
		searchtab = mainframe.getSearchTab();
		queryModel = environment.getDatabase();
	}

	/**
	 * Use <code>query</code> to retrieve <em>hits</em> from the JSON database
	 * and return them to {@link SearchTab}
	 * @param query
	 * @param language
	 */
	public void acceptLabelQuery(String query, String language, int start, int count, ITicket credentials) {
		System.out.println("LabelQuery: "+query);
		IResult r = queryModel.listNodesByLabel(query, language, start, count, credentials);
		if (r.getResultObject() != null) {
			List<ISubjectProxy>nodes = (List<ISubjectProxy>)r.getResultObject();
			ISubjectProxy n;
			Iterator<ISubjectProxy>itr = nodes.iterator();
			StringBuilder buf = new StringBuilder();
			List<String>strings;
			boolean isFirst = true;
			int len = 0;
			while (itr.hasNext()) {
				n = itr.next();
				buf.append("Locator: ");
				buf.append(n.getLocator());
				buf.append("\nDetails: ");
				strings = n.listLabels();
				if (strings != null && !strings.isEmpty()) {
					len = strings.size();
					for (int i=0;i<len;i++) {
						if (!isFirst)
							buf.append(", ");
						else
							isFirst = false;
						buf.append(strings.get(i));
					}
				}
				buf.append("\n");
				searchtab.addSearchHit(buf.toString());
				buf.setLength(0);		
			}
		} else
			searchtab.addSearchHit("Nothing found");
	}
	
	/**
	 * Use <code>query</code> to retrieve <em>hits</em> from the JSON database
	 * and return them to {@link SearchTab}
	 * @param query
	 * @param language
	 */
	public void acceptDetailsQuery(String query, String language, int start, int count, ITicket credentials) {
		System.out.println("DetailsQuery: "+query);
		IResult r = queryModel.listNodesByDetailsLike(query, language, start, count, credentials);	
		if (r.getResultObject() != null) {
			List<ISubjectProxy>nodes = (List<ISubjectProxy>)r.getResultObject();
			ISubjectProxy n;
			Iterator<ISubjectProxy>itr = nodes.iterator();
			StringBuilder buf = new StringBuilder();
			List<String>strings;
			boolean isFirst = true;
			int len = 0;
			while (itr.hasNext()) {
				n = itr.next();
				buf.append("Locator: ");
				buf.append(n.getLocator());
				buf.append("\nLabel: ");
				strings = n.listDetails();
				if (strings != null && !strings.isEmpty()) {
					len = strings.size();
					for (int i=0;i<len;i++) {
						if (!isFirst)
							buf.append(", ");
						else
							isFirst = false;
						buf.append(strings.get(i));
					}
				}
				buf.append("\n");
				searchtab.addSearchHit(buf.toString());
				buf.setLength(0);	
			}
		} else
			searchtab.addSearchHit("Nothing found");
	}

}
