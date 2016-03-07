/**
 * 
 */
package org.topicquests.node.provider;


import java.util.*;

import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.common.settings.ImmutableSettings;
//import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.nex.util.LRUCache;
import org.topicquests.common.ResultPojo;
import org.topicquests.common.api.IResult;
import org.topicquests.ks.api.IErrorMessages;
import org.topicquests.ks.api.IVersionable;
import org.topicquests.util.ConfigurationHelper;
import org.topicquests.util.TextFileHandler;

import com.google.gson.Gson;







import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Count;
import io.searchbox.core.CountResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Doc;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.MultiGet;
import io.searchbox.core.MultiSearch;
import io.searchbox.core.Search;
import io.searchbox.core.Update;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.mapping.PutMapping;
import io.searchbox.params.Parameters;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

/**
 * @author jackpark
 * @see https://github.com/searchbox-io/Jest/tree/master/jest
 * @see https://www.elastic.co/guide/en/elasticsearch/reference/1.4/docs-multi-get.html
 * @see https://github.com/searchbox-io/Jest/blob/master/jest-common/src/main/java/io/searchbox/core/MultiGet.java
 * @see https://github.com/searchbox-io/Jest/blob/master/jest-common/src/test/java/io/searchbox/core/MultiGetTest.java
 * 
 */
public class Client {
	private ProviderEnvironment environment;
	private JestClient client;
	private TextFileHandler handler;
	// cache for freshly indexed or updated (reindexed) nodes
	private LRUCache objectCache;
	//NOTE we support just one type
	private final String _TYPE = "core";
	/**
	 * 
	 */
	public Client(ProviderEnvironment env) {
		environment = env;
		objectCache = new LRUCache(512); //That should be enough to buy time
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
	
	/**
	 * Index <code>object</code>
	 * @param id
	 * @param index
	 * @param object
	 * @param type
	 * @return
	 */
	public IResult indexNode(String id, String index, JSONObject object) {
		environment.logDebug("Client.indexNode "+id+" | "+object);
		IResult result = new ResultPojo();
		try {
			Index _index = new Index.Builder(object)
					.index(index)
					.type(_TYPE)
					.id(id)
					.setParameter(Parameters.REFRESH, true)
					.build();
			client.execute(_index);
			//add when we index a node
			objectCache.add(id, object);
		} catch (Exception e) {
			result.addErrorString(e.getMessage());
			environment.logError(e.getMessage(), e);
			e.printStackTrace();			
		}
		return result;
	}


	/**
	 * Update an already-indexed node
	 * @param id
	 * @param index
	 * @param object
	 * @param checkVersion
	 * @return
	 */
	public IResult updateNode(String id, String index, JSONObject object, boolean checkVersion) {
		IResult result = null;
		if (checkVersion) {
			result = this.compareVersions(id, index, object);
			if (result.getResultObjectA() != null)
				return result; // we had an OptimisticLockException
			//Returns the current version in resultObjectA
			//Otherwise, continue using result, including any error messages
		} else {
			//otherwise, just go ahead and update
			result = new ResultPojo();
		}
		try {
			
			Update b = new Update.Builder(object.toJSONString())
				.index(index)
				.type(_TYPE)
				.id(id)
				.build();
			client.execute(b);
			objectCache.add(id, object); // latest version
			System.out.println("CLIENT_UPDATE "+id+" "+objectCache.get(id));
		} catch (Exception e) {
			result.addErrorString(e.getMessage());
			environment.logError(e.getMessage(), e);
			e.printStackTrace();			
		}
		return result;
	}
	
	/**
	 * Delete a node identified by <code>id</code>
	 * @param id
	 * @param index
	 * @return
	 */
	public IResult deleteNode(String id, String index) {
		IResult result = new ResultPojo();
		try {
			Delete d = new Delete.Builder(id)
            	.index(index)
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
	
	/**
	 * Fetch a node identified by <code>id</code> and return it
	 * as a {@link JSONObject}
	 * @param id
	 * @param index
	 * @return
	 */
	public IResult getNodeAsJSONObject(String id, String index) {
		IResult result = new ResultPojo();
		//first, see if it's cached locally
		result.setResultObject(objectCache.get(id));
		System.out.println("CLIENT_GET "+id+" "+result.getResultObject());
		if (result.getResultObject() == null) {
			try {
				Get get = new Get.Builder(index, id)
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
				} 
			} catch (Exception e) {
				result.addErrorString(e.getMessage());
				environment.logError(e.getMessage(), e);
				e.printStackTrace();			
			}
		}
		return result;
	}

	/**
	 * Fetch a collection of nodes as identified by <code>locators</code>
	 * @param locators
	 * @param index
	 * @return List<JSONObject> or null;
	 */
	public IResult multiGetNodes(List<String>locators, String index) {
		IResult result = new ResultPojo();
		result.setResultObject(null);
		Collection<Doc> docs = new ArrayList<Doc>();
		Iterator<String>itr = locators.iterator();
		while (itr.hasNext()) {
			docs.add(new Doc(index, _TYPE, itr.next()));
		}
		try {
			MultiGet get = new MultiGet.Builder.ByDoc(docs).build();
			JestResult rs = client.execute(get);
			String n = rs.getJsonString();
			//System.out.println("MGA "+n);
			//MGA {"docs":
			//[{"_index":"topics","_type":"core","_id":"TypeType","_version":2,"found":true,
			//"_source":{"crDt":"2015-12-07T21:39:55-08:00","crtr":"SystemUser","lox":"TypeType","sIco":"/images/cogwheel_sm.png","isPrv":false,"_ver":"1449553195215","lEdDt":"2015-12-07T21:39:55-08:00","details":"Topic Map root type","label":"Type type","lIco":"/images/cogwheel.png","isFdrtd":false}},{"_index":"topics","_type":"core","_id":"ClassType","_version":2,"found":true,"_source":{"crtr":"SystemUser","_ver":"1449553198216","lEdDt":"2015-12-07T21:39:58-08:00","label":"Class type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-12-07T21:39:58-08:00","sbOf":"TypeType","lox":"ClassType","sIco":"/images/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Class type","lIco":"/images/cogwheel.png"}},{"_index":"topics","_type":"core","_id":"NodeType","_version":1,"found":true,"_source":{"crtr":"SystemUser","_ver":"1448384289926","lEdDt":"2015-11-24T08:58:09-08:00","label":"ClassType","isFdrtd":false,"trCl":["TypeType","ClassType"],"crDt":"2015-11-24T08:58:09-08:00","sbOf":"ClassType","lox":"NodeType","sIco":"/images/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Node type","lIco":"/images/cogwheel.png"}}]}

			if (n != null) {
				JSONParser p = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
				JSONObject ja, jo = (JSONObject)p.parse(n);
				List<JSONObject>dx = (List<JSONObject>)jo.get("docs");
				if (dx != null) {
					List<JSONObject>rslt = new ArrayList<>();
					Iterator<JSONObject>itx = dx.iterator();
					while (itx.hasNext()) {
						jo = itx.next();
						ja = (JSONObject)jo.get("_source");
						rslt.add(ja);
					}
					result.setResultObject(rslt);
				}
			}
		} catch (Exception e) {
			result.addErrorString(e.getMessage());
			environment.logError(e.getMessage(), e);
			e.printStackTrace();			
		}
		return result;
	}
	/** Not ready for this yet
	public IResult multiSearch(List<String> query, String index) {
		IResult result = new ResultPojo();
		Collection<Search> l = new ArrayList<>();
		//TODO
		try {
			 MultiSearch get = new MultiSearch.Builder(l).build();
			 JestResult rs = client.execute(get);
			 //TODO
		} catch (Exception e) {
			result.addErrorString(e.getMessage());
			environment.logError(e.getMessage(), e);
			e.printStackTrace();						
		}
		
		return result;
	}
	*/
	/** not ready for this yet
	public IResult count(String query, String index) {
		IResult result = new ResultPojo();
		try {
			Count count = new Count.Builder()
				.query(query)
				.addIndex(index)
				.addType(_TYPE).build();
			CountResult rslt = null; //TODO
			//JEST documentation is far too ambiguous here
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
	public IResult listObjectsByQuery(String  query, String index) {
		IResult result = new ResultPojo();
		try {
			Search search = new Search.Builder(query) 
				.addIndex(index)
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
	
	///////////////////////
	// UTILITIES
	// Dependency on values supplied by an XML config file
	///////////////////////
	private void validateIndex() {
		createIndex();
	}
	
	private Collection<String> getClusters() {
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
	private void createMapping(String mapping, String index) {
		try {					
			PutMapping putMapping = new PutMapping.Builder(index,_TYPE, mapping)
			.build();
			System.out.println("MAPPING "+putMapping.toString());
			JestResult jr = client.execute(putMapping);
			if (jr.getErrorMessage() != null)
				environment.logError("JestError "+jr.getErrorMessage(), null);
		} catch (Exception e) {
			environment.logError(e.getMessage(), e);
			e.printStackTrace();			
		}
	}
	private void createIndex() {
		List<List<String>>indexes = (List<List<String>>)environment.getProperties().get("IndexNames");
		int len = indexes.size();
		System.out.println("JEST0 "+len);
	//	List<String>indices = new ArrayList<String>();
		JestResult jr;
		String mappx;
		int foundCode = 0; //too == found, 404 == notfound
		JSONObject mappy = null;
		String _INDEX;
		for (int i=0;i<len;i++) {
			_INDEX = indexes.get(i).get(0);
			mappx = indexes.get(i).get(1);
			mappx = getMappings(mappx);
			System.out.println("CreatingIndex "+_INDEX);
			
			try {
				jr = client.execute(new IndicesExists.Builder(_INDEX).build());
				foundCode = jr.getResponseCode();
				if (foundCode == 404) {
					jr = client.execute(new CreateIndex.Builder(_INDEX).build());
					createMapping(mappx, _INDEX);
					createSettings(_INDEX);
					if (jr.getErrorMessage() != null)
						environment.logError("JestError "+jr.getErrorMessage(), null);
				}
			} catch (Exception e) {
				environment.logError(e.getMessage(), e);
				e.printStackTrace();			
			}
		}
	}
	
	private void createSettings(String index) {
		String ns = environment.getStringProperty("NumShards");
		String nd = environment.getStringProperty("NumDuplicates");
		int numShards = Integer.parseInt(ns);
		int numReplicas = Integer.parseInt(nd);
		String settings = 
				"{\"settings\" : [ { \"number_of_shards\" :"+numShards+"}," +
                "{\"number_of_replicas\" :"+numReplicas+"}]}";
		System.out.println("CreatingSettings "+settings);
		
		
		try {
			JestResult jr = client.execute(new CreateIndex.Builder(index)
				.settings(ImmutableSettings.builder()
						.loadFromSource(settings)
						.build().getAsMap()).build());
			if (jr.getErrorMessage() != null)
				environment.logError("JestError "+jr.getErrorMessage(), null);
		} catch (Exception e) {
			environment.logError(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private String getMappings(String fileName) {
		String mappings = handler.readFile(ConfigurationHelper.findPath(fileName));		
		return mappings;
	}
	
	private IResult compareVersions(String id, String index, JSONObject newObject) {
		IResult result = this.getNodeAsJSONObject(id, index);
		System.out.println("Client.compareVersions "+id+" "+result.getResultObject()+" | "+result.getErrorString());
		JSONObject jo = (JSONObject)result.getResultObject();
		////////////////////////////////
		// There may be a corner case where we are fetching an object which
		// is still not in the index. This will return a null object.
		// I suspect that calls for a cache here.
		////////////////////////////////
		if (jo == null) {
			result.addErrorString(IErrorMessages.NODE_MISSING+" "+id);
			return result;
		}
		String ov = (String)jo.get(IVersionable.VERSION_PROPERTY);
		if (ov != null) {
			long olv = Long.parseLong(ov);
			String nv = (String)newObject.get(IVersionable.VERSION_PROPERTY);
			if (nv != null) {
				long nlv = Long.parseLong(nv);
				if (nlv < olv) {
					//OptimisticLockException: new is less than old
					result.setResultObjectA(jo);
					result.setResultObject(null);
					result.addErrorString(IErrorMessages.OPTIMISTIC_LOCK_EXCEPTION);
				} else {
					result.setResultObject(null);
					result.setResultObjectA(null);
				}
			} else {
				result.setResultObject(null);
				result.setResultObjectA(null);
				result.addErrorString(IErrorMessages.MISSING_VERSION_PROPERTY+": "+id);
			}
		} else {
			result.setResultObject(null);
			result.setResultObjectA(null);
			result.addErrorString(IErrorMessages.MISSING_VERSION_PROPERTY+": "+id);			
		}
		return result;
	}

}
