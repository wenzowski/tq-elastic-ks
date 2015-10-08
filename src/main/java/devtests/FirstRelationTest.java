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

import org.topicquests.common.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.TicketPojo;
import org.topicquests.ks.api.ITQCoreOntology;
import org.topicquests.ks.api.ITQDataProvider;
import org.topicquests.ks.api.ITicket;
import org.topicquests.ks.tm.api.IRelationsLegend;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.ks.tm.api.ISubjectProxyModel;

/**
 * @author park
 *
 */
public class FirstRelationTest {
	private SystemEnvironment environment;
	private ITQDataProvider database;
	private ISubjectProxyModel proxyModel;
	private ITicket credentials;
	private final String
		lox1 = "MyFirstClass",
		lox2 = "MySecondClass",
		type = "ClassType";

	/**
	 * 
	 */
	public FirstRelationTest() {
		environment = new SystemEnvironment();
		database = environment.getDatabase();
		proxyModel = database.getSubjectProxyModel();
		credentials = new TicketPojo(ITQCoreOntology.SYSTEM_USER);
		ISubjectProxy n = proxyModel
				.newInstanceNode(lox1, type, "My first node", "Hi there", "en", "jackpark", "smallImagePath.png", "largeImagePath.png", false);
		System.out.println("AAA "+n.toJSONString());
		IResult r = database.putNode(n, true);
		System.out.println("BBB "+r.getErrorString()+" | "+r.getResultObject());
		ISubjectProxy n1 = proxyModel
				.newInstanceNode(lox2, type, "My second node", "Back atcha", "en", "jackpark", "smallImagePath.png", "largeImagePath.png", false);
		System.out.println("CCC "+n1.toJSONString());
		r = database.putNode(n1, true);
		System.out.println("DDD "+r.getErrorString()+" | "+r.getResultObject());
		
		r = proxyModel.relateExistingNodes(n, n1, IRelationsLegend.CAUSES_RELATION_TYPE, "jackpark", "smallImagePath.png", "largeImagePath.png", false, false);
		System.out.println("EEE "+r.getErrorString()+" | "+r.getResultObject());
		if (r.getResultObject() != null) {
			ISubjectProxy reln  = (ISubjectProxy)r.getResultObject();
			System.out.println("FFF "+reln.toJSONString());
		}
		r = database.getNode(lox1, credentials);
		n = (ISubjectProxy)r.getResultObject();
		System.out.println("GGG "+n.toJSONString());
		r = database.getNode(lox2, credentials);
		n = (ISubjectProxy)r.getResultObject();
		System.out.println("HHH "+n.toJSONString());
		environment.shutDown();
		System.exit(0);
	}

}
/**
AAA {"crDt":"2015-09-14T21:29:11-07:00","trCl":["TypeType","ClassType"],"crtr":"jackpark","lox":"MyFirstClass","sIco":"smallImagePath.png","isPrv":false,"_ver":"1442291351325","lEdDt":"2015-09-14T21:29:11-07:00","details":["Hi there"],"label":["My first node"],"lIco":"largeImagePath.png","inOf":"ClassType"}
BBB  | null
CCC {"crDt":"2015-09-14T21:29:11-07:00","trCl":["TypeType","ClassType","ClassType"],"crtr":"jackpark","lox":"MySecondClass","sIco":"smallImagePath.png","isPrv":false,"_ver":"1442291351427","lEdDt":"2015-09-14T21:29:11-07:00","details":["Back atcha"],"label":["My second node"],"lIco":"largeImagePath.png","inOf":"ClassType"}
DDD  | null
EEE  | org.topicquests.ks.tm.SubjectProxy@7b9a4292
FFF {"crtr":"jackpark","_ver":"1442291351512","lEdDt":"2015-09-14T21:29:11-07:00","tupST":"NodeType","label":["CausesRelationType"],"inOf":"MyFirstClassCausesRelationTypeMySecondClass","tupOT":"NodeType","crDt":"2015-09-14T21:29:11-07:00","trCl":["MyFirstClassCausesRelationTypeMySecondClass"],"tupS":"MyFirstClass","isTrcld":"false","lox":"0e83b9bd-f5df-4250-97b4-0b5ddb81513b","tupO":"MySecondClass","sIco":"smallImagePath.png","isPrv":false,"details":["MyFirstClass CausesRelationType MySecondClass"],"lIco":"largeImagePath.png"}
{
    "crtr": "jackpark",
    "_ver": "1442291351512",
    "lEdDt": "2015-09-14T21:29:11-07:00",
    "tupST": "NodeType",
    "label": [
        "CausesRelationType"
    ],
    "inOf": "MyFirstClassCausesRelationTypeMySecondClass",
    "tupOT": "NodeType",
    "crDt": "2015-09-14T21:29:11-07:00",
    "trCl": [
        "MyFirstClassCausesRelationTypeMySecondClass"
    ],
    "tupS": "MyFirstClass",
    "isTrcld": "false",
    "lox": "0e83b9bd-f5df-4250-97b4-0b5ddb81513b",
    "tupO": "MySecondClass",
    "sIco": "smallImagePath.png",
    "isPrv": false,
    "details": [
        "MyFirstClass CausesRelationType MySecondClass"
    ],
    "lIco": "largeImagePath.png"
}
GGG {"crtr":"jackpark","_ver":"1442291351325","lEdDt":"2015-09-14T21:29:11-07:00","label":["My first node"],"inOf":"ClassType","crDt":"2015-09-14T21:29:11-07:00","trCl":["TypeType","ClassType","ClassType"],"tpL":["{\"relationType\":\"CausesRelationType\",\"documentLocator\":\"MySecondClass\",\"relationLocator\":\"MyFirstClassCausesRelationTypeMySecondClass\",\"documentLabel\":\"My second node\",\"documentType\":\"ClassType\",\"relationLabel\":\"CausesRelationType\",\"documentSmallIcon\":\"smallImagePath.png\"}"],"lox":"MyFirstClass","sIco":"smallImagePath.png","isPrv":false,"details":["Hi there"],"lIco":"largeImagePath.png"}
{
    "crtr": "jackpark",
    "_ver": "1442291351325",
    "lEdDt": "2015-09-14T21:29:11-07:00",
    "label": [
        "My first node"
    ],
    "inOf": "ClassType",
    "crDt": "2015-09-14T21:29:11-07:00",
    "trCl": [
        "TypeType",
        "ClassType",
        "ClassType"
    ],
    "tpL": [
        "{\"relationType\":\"CausesRelationType\",\"documentLocator\":\"MySecondClass\",\"relationLocator\":\"MyFirstClassCausesRelationTypeMySecondClass\",\"documentLabel\":\"My second node\",\"documentType\":\"ClassType\",\"relationLabel\":\"CausesRelationType\",\"documentSmallIcon\":\"smallImagePath.png\"}"
    ],
    "lox": "MyFirstClass",
    "sIco": "smallImagePath.png",
    "isPrv": false,
    "details": [
        "Hi there"
    ],
    "lIco": "largeImagePath.png"
}
HHH {"crtr":"jackpark","_ver":"1442291351427","lEdDt":"2015-09-14T21:29:11-07:00","label":["My second node"],"inOf":"ClassType","crDt":"2015-09-14T21:29:11-07:00","trCl":["TypeType","ClassType","ClassType"],"tpL":["{\"relationType\":\"CausesRelationType\",\"documentLocator\":\"MyFirstClass\",\"relationLocator\":\"MyFirstClassCausesRelationTypeMySecondClass\",\"documentLabel\":\"My first node\",\"documentType\":\"ClassType\",\"relationLabel\":\"CausesRelationType\",\"documentSmallIcon\":\"smallImagePath.png\"}"],"lox":"MySecondClass","sIco":"smallImagePath.png","isPrv":false,"details":["Back atcha"],"lIco":"largeImagePath.png"}
{
    "crtr": "jackpark",
    "_ver": "1442291351427",
    "lEdDt": "2015-09-14T21:29:11-07:00",
    "label": [
        "My second node"
    ],
    "inOf": "ClassType",
    "crDt": "2015-09-14T21:29:11-07:00",
    "trCl": [
        "TypeType",
        "ClassType",
        "ClassType"
    ],
    "tpL": [
        "{\"relationType\":\"CausesRelationType\",\"documentLocator\":\"MyFirstClass\",\"relationLocator\":\"MyFirstClassCausesRelationTypeMySecondClass\",\"documentLabel\":\"My first node\",\"documentType\":\"ClassType\",\"relationLabel\":\"CausesRelationType\",\"documentSmallIcon\":\"smallImagePath.png\"}"
    ],
    "lox": "MySecondClass",
    "sIco": "smallImagePath.png",
    "isPrv": false,
    "details": [
        "Back atcha"
    ],
    "lIco": "largeImagePath.png"
}
 */
