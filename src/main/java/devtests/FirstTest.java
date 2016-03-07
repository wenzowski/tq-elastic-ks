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
package devtests;

import net.minidev.json.JSONObject;

import org.topicquests.common.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.tm.SubjectProxy;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.ks.tm.api.ISubjectProxyModel;
import org.topicquests.node.provider.Client;
import org.topicquests.node.provider.ProviderEnvironment;

/**
 * @author park
 *
 */
public class FirstTest {
	private SystemEnvironment environment;
	private ISubjectProxyModel proxyModel;
	private ProviderEnvironment database;
	private Client client;
	private final String
		//oldlocator = "MyTenthNode",
		index = "topics",
		locator = "MyFirstNode",
		label = "My first node",
		description = "In which we are testing",
		lang = "en",
		userId = "jackpark",
		smallImagePath = "smallimage.png",
		largeImagePath = "largeimage.png";

	/**
	 * 
	 */
	public FirstTest() {
		environment = new SystemEnvironment();
		database = environment.getProvider();
		proxyModel = environment.getSubjectProxyModel();
		client = database.getClient();
		ISubjectProxy n = proxyModel.newNode(locator, label, description, lang, userId, smallImagePath, largeImagePath, false);
		System.out.println("AAA "+n.toJSONString());
		//AAA {"crDt":"2015-09-12T14:25:14-07:00","crtr":"jackpark",
		//"lox":"MyEleventhNode","sIco":"smallimage.png","isPrv":"false",
		//"lEdDt":"2015-09-12T14:25:14-07:00",
		//"details":["In which we are testing"],
		//"label":["My 11th node"],"lIco":"largeimage.png"}

		client.indexNode(n.getLocator(), index, n.getData());
		IResult r = client.getNodeAsJSONObject(locator, index);
		System.out.println("BBB "+r.getErrorString()+" | "+r.getResultObject());
		if (r.getResultObject() != null) {
			//could not cast to an interface
			JSONObject x = (JSONObject)r.getResultObject();
			n = new SubjectProxy(x);
			System.out.println("CCC "+n.toJSONString());
			
		}
		environment.shutDown();
	}

}
