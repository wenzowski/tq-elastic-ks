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

import java.io.*;
import java.util.*;

import org.topicquests.support.util.TextFileHandler;
import org.topicquests.support.ResultPojo;
import org.topicquests.support.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.TicketPojo;
import org.topicquests.ks.api.ITQCoreOntology;
import org.topicquests.ks.api.ITQDataProvider;
import org.topicquests.ks.api.ITicket;
import org.topicquests.ks.tm.api.ISubjectProxy;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;


/**
 * @author Park
 * A class to replace hard-coded bootstrapping with
 * file loading from JSON files
 */
public class JSONBootstrap {
	private SystemEnvironment environment;
	private ITQDataProvider database;
	private String userId = ITQCoreOntology.SYSTEM_USER;
	private ITicket credentials;
	private final String path = "data/bootstrap/";
	private TextFileHandler handler;

	/**
	 * 
	 */
	public JSONBootstrap(SystemEnvironment env) {
		this.environment = env;
		this.database = environment.getDatabase();
		this.credentials = new TicketPojo();
		credentials.setUserLocator(userId);
		handler = new TextFileHandler();
		System.out.println("JSONBOOTSTRAP+");
	}

	public IResult bootstrap() {
		System.out.println("JSONBootstrap- ");
		environment.logDebug("JSONBootstrap- ");
		IResult result = new ResultPojo();
		IResult r = database.getNode(ITQCoreOntology.TYPE_TYPE, credentials);
		if (r.hasError())
			result.addErrorString(r.getErrorString());
		environment.logDebug("JSONBootstrap-1 "+r.getErrorString()+" | "+r.getResultObject());
		if (r.getResultObject() == null) {
			File dir = new File(path);
			System.out.println("JSONBOOTSTRAP.bootstrap "+dir.getAbsolutePath());
			File files [] = dir.listFiles();
			int len = files.length;
			File f;
			r = null;
			for (int i=0;i<len;i++) {
				f = files[i];
				System.out.println(f.getAbsolutePath());
				if (f.getName().endsWith(".json")) {
					r = importJSONFile(f);
					if (r.hasError())
						result.addErrorString(r.getErrorString());
				}
			}
		} else {
			ISubjectProxy p = (ISubjectProxy)r.getResultObject();
			System.out.println("FOO "+p.toJSONString());
		}
		return result;
	}
	
	/**
	 * Testing the first line of each file. Return <code>true</code>
	 * if this class already exists.
	 * @param p
	 * @return
	 */
	private boolean seenThis(JSONObject p) {
		boolean result = false;
		String lox = (String)p.get("lox");
		IResult r = database.getNode(lox, credentials);
		result = (r.getResultObject() != null);
		//System.out.println("SEEN THIS "+lox+" "+r.getResultObject());
		return result;
	}
	
	private IResult importJSONFile(File f) {
		environment.logDebug(f.getName());
		IResult result = new ResultPojo();
		String json = handler.readFile(f);
		JSONParser p = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
		try {
			JSONObject jo = (JSONObject)p.parse(json);
			List<JSONObject> o = (List<JSONObject>)jo.get("nodes");
			if (o != null) {
				IResult r;
				Iterator<JSONObject>itr = o.iterator();
				JSONObject x;
				boolean isFirst = true;
				while (itr.hasNext()) {
					x = itr.next();
					if (x.get("lox") != null) {
						if (isFirst) {
							if (seenThis(x)) {
								return result;
							}
							isFirst = false;
						}
						r = buildProxy(x);
						if (r.hasError())
							result.addErrorString(r.getErrorString());
					}
				}
			} else {
				result.addErrorString(f.getName()+" MISSING Data");
			}
		} catch (Exception e) {
			environment.logError("JSONBootstrap1 "+e.getMessage(), e);
			result.addErrorString(e.getMessage());
		}
		return result;
	}
	
	private IResult buildProxy(JSONObject jo)  {
		environment.logDebug("Bootstrap "+jo.toJSONString());
		IResult result = new ResultPojo();
		try {
			ISubjectProxy n = new SubjectProxy(jo);
			n.setCreatorId(userId);
			Date d = new Date();
			String sub = (String)n.getProperty("sbOf");
			if (sub != null)
				n.addTransitiveClosureLocator(sub);
			n.setDate(d);
			n.setLastEditDate(d);
			n.setIsFederated(false);
			n.setIsPrivate(false);
			n.setVersion(Long.toString(System.currentTimeMillis()));
			IResult r = database.putNode(n);
			if (r.hasError())
				result.addErrorString("JSONBootstrap2 "+r.getErrorString());
		} catch (Exception e) {
			environment.logError("JSONBootstrap3 "+e.getMessage(), e);
			result.addErrorString(e.getMessage());
		}
		return result;
	}
}
