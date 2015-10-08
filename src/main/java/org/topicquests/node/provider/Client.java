/**
 * 
 */
package org.topicquests.node.provider;


import java.util.*;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.topicquests.common.ResultPojo;
import org.topicquests.common.api.IResult;
import org.topicquests.util.ConfigurationHelper;
import org.topicquests.util.TextFileHandler;

import com.google.gson.JsonObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.Update;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;
import io.searchbox.params.Parameters;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

/**
 * @author jackpark
 * @see https://github.com/searchbox-io/Jest/tree/master/jest
 */
public class Client {
	private ProviderEnvironment environment;
	private JestClient client;
	private TextFileHandler handler;
	//TODO make these values config values
//	private final String _ES = "http://localhost:9200"; //TODO CHANGE ME
	private String _INDEX = "topics";
	private final String _TYPE = "core";

	/**
	 * 
	 */
	public Client(ProviderEnvironment env) {
		environment = env;
		Collection<String> uris = getClusters();
		JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder(uris)
                .multiThreaded(true)
                .build());
        client = factory.getObject();
        handler = new TextFileHandler();
        validateIndex();
        System.out.println("Client "+client);
	}
	
	public IResult indexNode(String id, JSONObject object) {
		return indexNode(id, object.toJSONString());
	}

	public IResult indexNode(String id, String object) {
		environment.logDebug("Client.indexNode "+id+" | "+object);
		IResult result = new ResultPojo();
		try {
			Index index = new Index.Builder(object)
					.index(_INDEX)
					.type(_TYPE)
					.id(id)
					.setParameter(Parameters.REFRESH, true)
					.build();
			client.execute(index);			
		} catch (Exception e) {
			result.addErrorString(e.getMessage());
			environment.logError(e.getMessage(), e);
			e.printStackTrace();			
		}
		return result;
	}
		
	IResult updateNode(String id, JSONObject object) {
		IResult result = new ResultPojo();
		try {
			Update b = new Update.Builder(object.toJSONString())
				.index(_INDEX)
				.type(_TYPE)
				.id(id)
				.build();
			client.execute(b);
		} catch (Exception e) {
			result.addErrorString(e.getMessage());
			environment.logError(e.getMessage(), e);
			e.printStackTrace();			
		}
		return result;
	}
	
	public IResult deleteNode(String id) {
		IResult result = new ResultPojo();
		try {
			Delete d = new Delete.Builder(id)
            	.index(_INDEX)
            	.type(_TYPE)
            	.build();
			client.execute(d);
		} catch (Exception e) {
			result.addErrorString(e.getMessage());
			environment.logError(e.getMessage(), e);
			e.printStackTrace();			
		}
		return result;
	}
	
	public IResult getNodeAsJSONObject(String id) {
		IResult result = new ResultPojo();
		try {
			Get get = new Get.Builder(_INDEX, id)
				.type(_TYPE)
				.build();

			JestResult rs = client.execute(get);
			String n = rs.getJsonString();
			environment.logDebug("Client.getNodeAsJSONObject "+n);
			JSONObject jo = null;
			if (n != null) {
				jo = (JSONObject)new JSONParser(JSONParser.MODE_JSON_SIMPLE).parse(n);
			}
			System.out.println("Client.getNodeAsJSONObject "+n);
			Boolean t = (Boolean)jo.get("found");
			if (t) {
				jo = (JSONObject)jo.get("_source");
				environment.logDebug("Client.getNodeAsJSONObject-1 "+jo.toJSONString());
				result.setResultObject(jo);
			} else
				result.setResultObject(null);
		} catch (Exception e) {
			result.addErrorString(e.getMessage());
			environment.logError(e.getMessage(), e);
			e.printStackTrace();			
		}
		return result;
	}
/**	
	public IResult getNodeAsJSONString(String id) {
		IResult result = new ResultPojo();
		try {
			Get get = new Get.Builder(_INDEX, id)
				.type(_TYPE)
				.build();

			JestResult rs = client.execute(get);
			String n = rs.getJsonString();
			result.setResultObject(n);
		} catch (Exception e) {
			result.addErrorString(e.getMessage());
			environment.logError(e.getMessage(), e);
			e.printStackTrace();			
		}
		return result;
	}
	
	/**
	 * 
	 * @param query
	 * @return can return either a <code>List<JSONObject></code> or <code>null</code>
	 */
	public IResult listObjectsByQuery(String  query) {
		IResult result = new ResultPojo();
		try {
			Search search = new Search.Builder(query) 
				.addIndex(_INDEX)
				.addType(_TYPE)
				.build();
			JestResult rslt = client.execute(search);
			String s = rslt.getJsonString();
			System.out.println("Client.listObjectsByQuery "+s);
			//{"_index":"topics","_type":"core","_id":"MyFourthNode","_version":1,"found":true,"_source":{"locator":"MyFourthNode","type":"SomeType","label":"My second node","details":"In which we will see how this works","superClasses":["AnotherType","YetAnotherClass"]}}
			if (s != null) {
				JSONObject jo = (JSONObject)new JSONParser(JSONParser.MODE_JSON_SIMPLE).parse(s);
				s = (String)jo.get("_source");
				System.out.println("Client.listObjectsByQuery-1 "+s);
				JSONObject hits = (JSONObject)jo.get("hits");
				if (hits != null) {
					JSONArray l = (JSONArray)hits.get("hits");
					if (l != null) {
						List<JSONObject>results = new ArrayList<JSONObject>();
						result.setResultObject(results);
						int len = l.size();
						for (int i=0; i<len; i++) {
							jo = (JSONObject)l.get(i);
							results.add((JSONObject)jo.get("_source"));
						}
					}
				} else 
					result.setResultObject(null);
			}
		} catch (Exception e) {
			result.addErrorString(e.getMessage());
			environment.logError(e.getMessage(), e);
			e.printStackTrace();			
		}
		return result;
	}
	private void validateIndex() {
		createIndex();
		createSettings();
	}
	
	Collection<String> getClusters() {
		List<List<String>>clusters = (List<List<String>>)environment.getProperties().get("Clusters");
		int len = clusters.size();
		List<String>entry;
		String name, port;
		Collection<String>result = new ArrayList<String>();
		for (int i=0; i<len; i++) {
			entry = clusters.get(i);
			name = entry.get(0);
			port = entry.get(1);
			result.add("http://"+name+":"+port);
		}
		return result;
	}
	void createMapping(String mapping) {
		try {					
			PutMapping putMapping = new PutMapping.Builder(_INDEX, _TYPE, mapping)
			.build();
			System.out.println("MAPPING "+putMapping.toString());
			client.execute(putMapping);
		} catch (Exception e) {
			environment.logError(e.getMessage(), e);
			e.printStackTrace();			
		}
	}
	void createIndex() {
		List<List<String>>indexes = (List<List<String>>)environment.getProperties().get("IndexNames");
		int len = indexes.size();
		List<String>indices = new ArrayList<String>();
		String mappx;
		JSONObject mappy = null;
		//for (int i=0;i<len;i++) {
			_INDEX = indexes.get(0).get(0);
			mappx = indexes.get(0).get(1);
			mappx = getMappings(mappx);
		
		//}
		System.out.println("CreatingIndex");
		try {
			client.execute(new CreateIndex.Builder(_INDEX).build());
			createMapping(mappx);
		} catch (Exception e) {
			environment.logError(e.getMessage(), e);
			e.printStackTrace();			
		}
	}
	
	void createSettings() {
		String ns = environment.getStringProperty("NumShards");
		String nd = environment.getStringProperty("NumDuplicates");
		int numShards = Integer.parseInt(ns);
		int numReplicas = Integer.parseInt(nd);
		String settings = 
				"{\"settings\" : [ { \"number_of_shards\" :"+numShards+"}," +
                "{\"number_of_replicas\" :"+numReplicas+"}]}";
		System.out.println("CreatingSettings "+settings);
		
		
		try {
			client.execute(new CreateIndex.Builder(_INDEX)
				.settings(ImmutableSettings.builder()
						.loadFromSource(settings)
						.build().getAsMap()).build());
		} catch (Exception e) {
			environment.logError(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	protected String getMappings(String fileName) {
		String mappings = handler.readFile(ConfigurationHelper.findPath(fileName));		
		return mappings;
	}

}
