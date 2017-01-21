/**
 * 
 */
package org.topicquests.ks.tm.merge;

import java.io.*;
import java.util.*;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.support.util.TextFileHandler;

/**
 * @author park
 *
 */
public class SameLabelDetector {
	private SystemEnvironment environment;
	private JSONObject data;
	private final String 
		fileName = "SameLabels.json";

	/**
	 * 
	 */
	public SameLabelDetector(SystemEnvironment env) {
		environment = env;
		bootData();
		if (data == null)
			data = new JSONObject();
	}
	
	public void acceptProxy(ISubjectProxy p) {
		System.out.println("SameLabel "+p.getLocator());
		synchronized(data) {
			List<String> labels = p.listLabels();
			List<String> hits;
			if (labels != null) {
				String label, locator = p.getLocator();
				Iterator<String>itr = labels.iterator();
				while (itr.hasNext()) {
					label = itr.next();
					label = label.toLowerCase(); //TODO study this
					hits = (List<String>)data.get(label);
					if (hits == null) {
						hits = new ArrayList<String>();
						hits.add(locator);
					} else if (!hits.contains(locator)) {
						hits.add(locator);
					}
					data.put(label, hits);
				}
			}
		}
	}
	
	void bootData() {
		System.out.println("SameLabelBoot");
		try {
			data = null;
			TextFileHandler h = new TextFileHandler();
			File f = new File(fileName);
			if (f.exists()) {
				String dx = h.readFile(f);
				JSONParser p = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
				data = (JSONObject)p.parse(dx);
			}
		} catch (Exception e) {
			environment.logError(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	public void shutDown() {
		System.out.println("SameLabelShutDown "+data.toJSONString());
		try {
			File f = new File("SameLabels.json");
			FileOutputStream fos = new FileOutputStream(f);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			PrintWriter out = new PrintWriter(bos);
			export(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			environment.logError(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	void export(Writer out) throws Exception {
		synchronized(data) {
			System.out.println("exporting "+data.size());
			data.writeJSONString(out);
			out.flush();
			out.close();
		System.out.println("exported");
		}
	}
	
}
