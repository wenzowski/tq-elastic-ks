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

import java.util.List;

import org.topicquests.support.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.TicketPojo;
import org.topicquests.ks.api.ITQCoreOntology;
import org.topicquests.ks.api.ITQDataProvider;
import org.topicquests.ks.api.ITicket;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.ks.tm.api.ISubjectProxyModel;
import org.topicquests.node.provider.ProviderEnvironment;

/**
 * @author park
 *
 */
public class BootTest {
	private SystemEnvironment environment;
	private ITQDataProvider database;
	private ITicket credentials;

	/**
	 * 
	 */
	public BootTest() {
		environment = new SystemEnvironment();
		database = environment.getDatabase();
		credentials = new TicketPojo(ITQCoreOntology.SYSTEM_USER);
		
		IResult r = database.getNode("TypeType", credentials);
		ISubjectProxy p = (ISubjectProxy)r.getResultObject();
		System.out.println("AAA "+r.getErrorString()+" | "+p);
		if (p != null)
			System.out.println("BBB "+p.toJSONString());
		r = database.getNode("ClassType", credentials);
		p = (ISubjectProxy)r.getResultObject();
		System.out.println("CCC "+r.getErrorString()+" | "+p);
		if (p != null)
			System.out.println("DDD "+p.toJSONString());
		r = database.listSubclassNodes("TypeType", 0, -1, credentials);
		List<ISubjectProxy> lp = (List<ISubjectProxy>)r.getResultObject();
		System.out.println("EEE "+r.getErrorString()+" "+lp);
		if (lp != null) {
			int len = lp.size();
			for (int i=0;i<len;i++) {
				System.out.println(lp.get(i).toJSONString());
			}
		}
		r = database.listSubclassNodes("PropertyType", 0, -1, credentials);
		lp = (List<ISubjectProxy>)r.getResultObject();
		System.out.println("FFF "+r.getErrorString()+" "+lp);
		if (lp != null) {
			int len = lp.size();
			for (int i=0;i<len;i++) {
				System.out.println(lp.get(i).toJSONString());
			}
		}
		//It works!
		environment.shutDown();
		System.exit(0);
	}

}
/**
DevTesterStarting
Start document
Start tag properties
Start tag parameter
End tag parameter // 
Start tag parameter
End tag parameter // 
Start tag parameter
End tag parameter // 
Start tag parameter
End tag parameter // 
Start tag parameter
End tag parameter // 
Start tag parameter
End tag parameter // 
Start tag parameter
End tag parameter // 
Start tag parameter
End tag parameter // 
Start tag parameter
End tag parameter // 
Start tag parameter
End tag parameter // 
End tag properties // 
Start document
Start tag properties
Start tag parameter
End tag parameter // 
Start tag parameter
End tag parameter // 
Start tag parameter
End tag parameter // 
Start tag list
Start tag parameter
End tag parameter // 
End tag list // 
Start tag list
Start tag parameter
End tag parameter // 
End tag list // 
End tag properties // 
Client config {NumDuplicates=0, NumShards=1, Clusters=[[localhost, 9200]], IndexNames=[[topics, mappings.json]], Model=org.topicquests.persist.json.es.ElasticSearchClusterModel}
CreatingIndex
MAPPING io.searchbox.indices.mapping.PutMapping@77e4c80f[uri=topics/core/_mapping,method=PUT]
CreatingSettings {"settings" : [ { "number_of_shards" :1},{"number_of_replicas" :0}]}
Client io.searchbox.client.http.JestHttpClient@69b2283a
JSONBOOTSTRAP+
JSONBOOTSTRAP.bootstrap C:\projects\eclipseTQ\workspace\TQElasticKnowledgeSystem\data\bootstrap
C:\projects\eclipseTQ\workspace\TQElasticKnowledgeSystem\data\bootstrap\biblio.json
C:\projects\eclipseTQ\workspace\TQElasticKnowledgeSystem\data\bootstrap\biomed.tm
C:\projects\eclipseTQ\workspace\TQElasticKnowledgeSystem\data\bootstrap\core.json
Client.getNodeAsJSONObject {"_index":"topics","_type":"core","_id":"TypeType","found":false}
GETNODE TypeType null
C:\projects\eclipseTQ\workspace\TQElasticKnowledgeSystem\data\bootstrap\core.tm
C:\projects\eclipseTQ\workspace\TQElasticKnowledgeSystem\data\bootstrap\learner.json
Client.getNodeAsJSONObject {"_index":"topics","_type":"core","_id":"HarvestedClassType","found":false}
GETNODE HarvestedClassType null
C:\projects\eclipseTQ\workspace\TQElasticKnowledgeSystem\data\bootstrap\pubtype.json
Client.getNodeAsJSONObject {"_index":"topics","_type":"core","_id":"JournalArticleType","found":false}
GETNODE JournalArticleType null
C:\projects\eclipseTQ\workspace\TQElasticKnowledgeSystem\data\bootstrap\relations.json
Client.getNodeAsJSONObject {"_index":"topics","_type":"core","_id":"CausesRelationType","found":false}
GETNODE CausesRelationType null
C:\projects\eclipseTQ\workspace\TQElasticKnowledgeSystem\data\bootstrap\tago.json
Client.getNodeAsJSONObject {"_index":"topics","_type":"core","_id":"StashedResourceNodeType","found":false}
GETNODE StashedResourceNodeType null
C:\projects\eclipseTQ\workspace\TQElasticKnowledgeSystem\data\bootstrap\temp.tx
Client.getNodeAsJSONObject {"_index":"topics","_type":"core","_id":"TypeType","_version":1,"found":true,"_source":{"crDt":"2015-09-13T14:47:56-07:00","crtr":"SystemUser","lox":"TypeType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"_ver":"1442180876833","lEdDt":"2015-09-13T14:47:56-07:00","details":"Topic Map root type","label":"Type type","lIco":"\/images\/cogwheel.png","isFdrtd":false}}
GETNODE TypeType {"found":true,"_index":"topics","_type":"core","_source":{"crDt":"2015-09-13T14:47:56-07:00","crtr":"SystemUser","lox":"TypeType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"_ver":"1442180876833","lEdDt":"2015-09-13T14:47:56-07:00","details":"Topic Map root type","label":"Type type","lIco":"\/images\/cogwheel.png","isFdrtd":false},"_id":"TypeType","_version":1}
GETISPRIVATE null
AAA  | org.topicquests.ks.tm.SubjectProxy@79924b
BBB {"found":true,"_index":"topics","_type":"core","_source":{"crDt":"2015-09-13T14:47:56-07:00","crtr":"SystemUser","lox":"TypeType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"_ver":"1442180876833","lEdDt":"2015-09-13T14:47:56-07:00","details":"Topic Map root type","label":"Type type","lIco":"\/images\/cogwheel.png","isFdrtd":false},"_id":"TypeType","_version":1}
Client.getNodeAsJSONObject {"_index":"topics","_type":"core","_id":"ClassType","_version":1,"found":true,"_source":{"crtr":"SystemUser","_ver":"1442180877090","lEdDt":"2015-09-13T14:47:57-07:00","label":"Class type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"ClassType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Class type","lIco":"\/images\/cogwheel.png"}}
GETNODE ClassType {"found":true,"_index":"topics","_type":"core","_source":{"crtr":"SystemUser","_ver":"1442180877090","lEdDt":"2015-09-13T14:47:57-07:00","label":"Class type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"ClassType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Class type","lIco":"\/images\/cogwheel.png"},"_id":"ClassType","_version":1}
GETISPRIVATE null
CCC  | org.topicquests.ks.tm.SubjectProxy@7b9a4292
DDD {"found":true,"_index":"topics","_type":"core","_source":{"crtr":"SystemUser","_ver":"1442180877090","lEdDt":"2015-09-13T14:47:57-07:00","label":"Class type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"ClassType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Class type","lIco":"\/images\/cogwheel.png"},"_id":"ClassType","_version":1}
Client.listObjectsByQuery {"took":97,"timed_out":false,"_shards":{"total":5,"successful":5,"failed":0},"hits":{"total":10,"max_score":4.135494,"hits":[{"_index":"topics","_type":"core","_id":"PropertyType","_score":4.135494,"_source":{"crtr":"SystemUser","_ver":"1442180877369","lEdDt":"2015-09-13T14:47:57-07:00","label":"Property type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"PropertyType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Property type","lIco":"\/images\/cogwheel.png"}},{"_index":"topics","_type":"core","_id":"RelationType","_score":3.2246234,"_source":{"crtr":"SystemUser","_ver":"1442180877189","lEdDt":"2015-09-13T14:47:57-07:00","label":"Relation type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"RelationType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Relation type","lIco":"\/images\/cogwheel.png"}},{"_index":"topics","_type":"core","_id":"OntologyType","_score":3.2246234,"_source":{"crtr":"SystemUser","_ver":"1442180877279","lEdDt":"2015-09-13T14:47:57-07:00","label":"Ontology type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"OntologyType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Ontology type","lIco":"\/images\/cogwheel.png"}},{"_index":"topics","_type":"core","_id":"RuleType","_score":3.2246234,"_source":{"crtr":"SystemUser","_ver":"1442180877472","lEdDt":"2015-09-13T14:47:57-07:00","label":"Rule type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"RuleType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Rule type","lIco":"\/images\/cogwheel.png"}},{"_index":"topics","_type":"core","_id":"LegendType","_score":3.2246234,"_source":{"crtr":"SystemUser","_ver":"1442180877810","lEdDt":"2015-09-13T14:47:57-07:00","label":"LegendType type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"LegendType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Legend type","lIco":"\/images\/cogwheel.png"}},{"_index":"topics","_type":"core","_id":"ScopeType","_score":3.2246234,"_source":{"crtr":"SystemUser","_ver":"1442180877923","lEdDt":"2015-09-13T14:47:57-07:00","label":"ScopeType type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"ScopeType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Scope type","lIco":"\/images\/cogwheel.png"}},{"_index":"topics","_type":"core","_id":"UserType","_score":3.2246234,"_source":{"crtr":"SystemUser","_ver":"1442180877634","lEdDt":"2015-09-13T14:47:57-07:00","label":"UserType type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"UserType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper User type","lIco":"\/images\/cogwheel.png"}},{"_index":"topics","_type":"core","_id":"ResourceType","_score":3.1102133,"_source":{"crtr":"SystemUser","_ver":"1442180877725","lEdDt":"2015-09-13T14:47:57-07:00","label":"ResourceType type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"ResourceType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Resource type","lIco":"\/images\/cogwheel.png"}},{"_index":"topics","_type":"core","_id":"ClassType","_score":3.1102133,"_source":{"crtr":"SystemUser","_ver":"1442180877090","lEdDt":"2015-09-13T14:47:57-07:00","label":"Class type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"ClassType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Class type","lIco":"\/images\/cogwheel.png"}},{"_index":"topics","_type":"core","_id":"RoleType","_score":3.1102133,"_source":{"crtr":"SystemUser","_ver":"1442180877555","lEdDt":"2015-09-13T14:47:57-07:00","label":"Role type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"RoleType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Role type","lIco":"\/images\/cogwheel.png"}}]}}
Client.listObjectsByQuery-1 null
EEE  [org.topicquests.ks.tm.SubjectProxy@7dcf94f8, org.topicquests.ks.tm.SubjectProxy@229f66ed, org.topicquests.ks.tm.SubjectProxy@31190526, org.topicquests.ks.tm.SubjectProxy@662ac478, org.topicquests.ks.tm.SubjectProxy@6743e411, org.topicquests.ks.tm.SubjectProxy@3eb25e1a, org.topicquests.ks.tm.SubjectProxy@477b4cdf, org.topicquests.ks.tm.SubjectProxy@77c2494c, org.topicquests.ks.tm.SubjectProxy@f5958c9, org.topicquests.ks.tm.SubjectProxy@233795b6]
{"crtr":"SystemUser","_ver":"1442180877369","lEdDt":"2015-09-13T14:47:57-07:00","label":"Property type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"PropertyType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Property type","lIco":"\/images\/cogwheel.png"}
{"crtr":"SystemUser","_ver":"1442180877189","lEdDt":"2015-09-13T14:47:57-07:00","label":"Relation type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"RelationType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Relation type","lIco":"\/images\/cogwheel.png"}
{"crtr":"SystemUser","_ver":"1442180877279","lEdDt":"2015-09-13T14:47:57-07:00","label":"Ontology type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"OntologyType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Ontology type","lIco":"\/images\/cogwheel.png"}
{"crtr":"SystemUser","_ver":"1442180877472","lEdDt":"2015-09-13T14:47:57-07:00","label":"Rule type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"RuleType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Rule type","lIco":"\/images\/cogwheel.png"}
{"crtr":"SystemUser","_ver":"1442180877810","lEdDt":"2015-09-13T14:47:57-07:00","label":"LegendType type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"LegendType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Legend type","lIco":"\/images\/cogwheel.png"}
{"crtr":"SystemUser","_ver":"1442180877923","lEdDt":"2015-09-13T14:47:57-07:00","label":"ScopeType type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"ScopeType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Scope type","lIco":"\/images\/cogwheel.png"}
{"crtr":"SystemUser","_ver":"1442180877634","lEdDt":"2015-09-13T14:47:57-07:00","label":"UserType type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"UserType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper User type","lIco":"\/images\/cogwheel.png"}
{"crtr":"SystemUser","_ver":"1442180877725","lEdDt":"2015-09-13T14:47:57-07:00","label":"ResourceType type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"ResourceType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Resource type","lIco":"\/images\/cogwheel.png"}
{"crtr":"SystemUser","_ver":"1442180877090","lEdDt":"2015-09-13T14:47:57-07:00","label":"Class type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"ClassType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Class type","lIco":"\/images\/cogwheel.png"}
{"crtr":"SystemUser","_ver":"1442180877555","lEdDt":"2015-09-13T14:47:57-07:00","label":"Role type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-09-13T14:47:57-07:00","sbOf":"TypeType","lox":"RoleType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Role type","lIco":"\/images\/cogwheel.png"}
Client.listObjectsByQuery {"took":8,"timed_out":false,"_shards":{"total":5,"successful":5,"failed":0},"hits":{"total":40,"max_score":2.8870695,"hits":[{"_index":"topics","_type":"core","_id":"RestrictedTupleListProperty","_score":2.8870695,"_source":{"crtr":"SystemUser","_ver":"1442180884891","lEdDt":"2015-09-13T14:48:04-07:00","label":"TupleListProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:04-07:00","sbOf":"PropertyType","lox":"RestrictedTupleListProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper RestrictedTupleList property type; list of tuples which have restrictions.","lIco":"\/images\/snowflake.png"}},{"_index":"topics","_type":"core","_id":"BacklinkListProperty","_score":2.8870695,"_source":{"crtr":"SystemUser","_ver":"1442180885358","lEdDt":"2015-09-13T14:48:05-07:00","label":"BacklinkListProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:05-07:00","sbOf":"PropertyType","lox":"BacklinkListProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper BacklinkList property type","lIco":"\/images\/snowflake.png"}},{"_index":"topics","_type":"core","_id":"PivotListProperty","_score":2.8870695,"_source":{"crtr":"SystemUser","_ver":"1442180884987","lEdDt":"2015-09-13T14:48:04-07:00","label":"PivotListProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:04-07:00","sbOf":"PropertyType","lox":"PivotListProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper PivotList property type","lIco":"\/images\/snowflake.png"}},{"_index":"topics","_type":"core","_id":"TupleSubjectType","_score":2.8870695,"_source":{"crtr":"SystemUser","_ver":"1442180886198","lEdDt":"2015-09-13T14:48:06-07:00","label":"TupleSubjectType","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:06-07:00","sbOf":"PropertyType","lox":"TupleSubjectType","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper TupleSubjectType property type","lIco":"\/images\/snowflake.png"}},{"_index":"topics","_type":"core","_id":"InstanceOfProperty","_score":2.3437347,"_source":{"crtr":"SystemUser","_ver":"1442180883849","lEdDt":"2015-09-13T14:48:03-07:00","label":"InstanceOfProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:03-07:00","sbOf":"PropertyType","lox":"InstanceOfProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper InstanceOf property type; the topic identifier of which this topic is an instance.","lIco":"\/images\/snowflake.png"}},{"_index":"topics","_type":"core","_id":"LastEditDateProperty","_score":2.3437347,"_source":{"crtr":"SystemUser","_ver":"1442180883423","lEdDt":"2015-09-13T14:48:03-07:00","label":"LastEditDateProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:03-07:00","sbOf":"PropertyType","lox":"LastEditDateProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper LastEditDate property type","lIco":"\/images\/snowflake.png"}},{"_index":"topics","_type":"core","_id":"CreatedDateProperty","_score":2.3437347,"_source":{"crtr":"SystemUser","_ver":"1442180883346","lEdDt":"2015-09-13T14:48:03-07:00","label":"CreatedDateProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:03-07:00","sbOf":"PropertyType","lox":"CreatedDateProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper CreatedDate property type","lIco":"\/images\/snowflake.png"}},{"_index":"topics","_type":"core","_id":"CreatorIdProperty","_score":2.3437347,"_source":{"crtr":"SystemUser","_ver":"1442180883496","lEdDt":"2015-09-13T14:48:03-07:00","label":"CreatorIdProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:03-07:00","sbOf":"PropertyType","lox":"CreatorIdProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper CreatorId property type","lIco":"\/images\/snowflake.png"}},{"_index":"topics","_type":"core","_id":"RestrictionProperty","_score":2.3437347,"_source":{"crtr":"SystemUser","_ver":"1442180883928","lEdDt":"2015-09-13T14:48:03-07:00","label":"RestrictionProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:03-07:00","sbOf":"PropertyType","lox":"RestrictionProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper Restriction property type; list of restrictions attached to this topic.","lIco":"\/images\/snowflake.png"}},{"_index":"topics","_type":"core","_id":"LargeImagePathProperty","_score":2.3437347,"_source":{"crtr":"SystemUser","_ver":"1442180884080","lEdDt":"2015-09-13T14:48:04-07:00","label":"LargeImagePathProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:04-07:00","sbOf":"PropertyType","lox":"LargeImagePathProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper LargeImagePath property type","lIco":"\/images\/snowflake.png"}}]}}
Client.listObjectsByQuery-1 null
FFF  [org.topicquests.ks.tm.SubjectProxy@3eb738bb, org.topicquests.ks.tm.SubjectProxy@5bda8e08, org.topicquests.ks.tm.SubjectProxy@1e800aaa, org.topicquests.ks.tm.SubjectProxy@185a6e9, org.topicquests.ks.tm.SubjectProxy@6f03482, org.topicquests.ks.tm.SubjectProxy@9d5509a, org.topicquests.ks.tm.SubjectProxy@179ece50, org.topicquests.ks.tm.SubjectProxy@3b0090a4, org.topicquests.ks.tm.SubjectProxy@3cd3e762, org.topicquests.ks.tm.SubjectProxy@1fa121e2]
{"crtr":"SystemUser","_ver":"1442180884891","lEdDt":"2015-09-13T14:48:04-07:00","label":"TupleListProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:04-07:00","sbOf":"PropertyType","lox":"RestrictedTupleListProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper RestrictedTupleList property type; list of tuples which have restrictions.","lIco":"\/images\/snowflake.png"}
{"crtr":"SystemUser","_ver":"1442180885358","lEdDt":"2015-09-13T14:48:05-07:00","label":"BacklinkListProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:05-07:00","sbOf":"PropertyType","lox":"BacklinkListProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper BacklinkList property type","lIco":"\/images\/snowflake.png"}
{"crtr":"SystemUser","_ver":"1442180884987","lEdDt":"2015-09-13T14:48:04-07:00","label":"PivotListProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:04-07:00","sbOf":"PropertyType","lox":"PivotListProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper PivotList property type","lIco":"\/images\/snowflake.png"}
{"crtr":"SystemUser","_ver":"1442180886198","lEdDt":"2015-09-13T14:48:06-07:00","label":"TupleSubjectType","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:06-07:00","sbOf":"PropertyType","lox":"TupleSubjectType","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper TupleSubjectType property type","lIco":"\/images\/snowflake.png"}
{"crtr":"SystemUser","_ver":"1442180883849","lEdDt":"2015-09-13T14:48:03-07:00","label":"InstanceOfProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:03-07:00","sbOf":"PropertyType","lox":"InstanceOfProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper InstanceOf property type; the topic identifier of which this topic is an instance.","lIco":"\/images\/snowflake.png"}
{"crtr":"SystemUser","_ver":"1442180883423","lEdDt":"2015-09-13T14:48:03-07:00","label":"LastEditDateProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:03-07:00","sbOf":"PropertyType","lox":"LastEditDateProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper LastEditDate property type","lIco":"\/images\/snowflake.png"}
{"crtr":"SystemUser","_ver":"1442180883346","lEdDt":"2015-09-13T14:48:03-07:00","label":"CreatedDateProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:03-07:00","sbOf":"PropertyType","lox":"CreatedDateProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper CreatedDate property type","lIco":"\/images\/snowflake.png"}
{"crtr":"SystemUser","_ver":"1442180883496","lEdDt":"2015-09-13T14:48:03-07:00","label":"CreatorIdProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:03-07:00","sbOf":"PropertyType","lox":"CreatorIdProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper CreatorId property type","lIco":"\/images\/snowflake.png"}
{"crtr":"SystemUser","_ver":"1442180883928","lEdDt":"2015-09-13T14:48:03-07:00","label":"RestrictionProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:03-07:00","sbOf":"PropertyType","lox":"RestrictionProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper Restriction property type; list of restrictions attached to this topic.","lIco":"\/images\/snowflake.png"}
{"crtr":"SystemUser","_ver":"1442180884080","lEdDt":"2015-09-13T14:48:04-07:00","label":"LargeImagePathProperty","isFdrtd":false,"trCl":["TypeType","PropertyType"],"crDt":"2015-09-13T14:48:04-07:00","sbOf":"PropertyType","lox":"LargeImagePathProperty","sIco":"\/images\/snowflake_sm.png","isPrv":false,"details":"Topic Map upper LargeImagePath property type","lIco":"\/images\/snowflake.png"}

 */
