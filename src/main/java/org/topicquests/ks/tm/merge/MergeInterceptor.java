/*
 * Copyright 2013, TopicQuests
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
package org.topicquests.ks.tm.merge;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.util.ConfigurationHelper;

/**
 * @author park
 * <p>This behaves as an <em>interceptor</em> to send out
 * {@link ISubjectProxy} objects for merge
 * </p>
 */
public class MergeInterceptor {
	private SystemEnvironment environment;
	private final String agentTag = "NewDocument";
	private int myPort;
	private Properties p = new Properties();
	private boolean isRunning = true;
	private SameLabelDetector sameLabelDetector;
	private Worker thread;
	private boolean shouldPropagate = false; // default

	/**
	 * @param env TODO
	 * 
	 */
	public MergeInterceptor(SystemEnvironment env) throws Exception {
		isRunning = true;
		//file must be in classpath
		File f = new File(ConfigurationHelper.findPath("agents.properties"));
		FileInputStream fis = new FileInputStream(f);
		p.load(fis);
		fis.close();
		String portx = p.getProperty("port");
		myPort = Integer.parseInt(portx);
		thread = new Worker();
		sameLabelDetector = new SameLabelDetector(environment);
		String sp = env.getStringProperty("MergeListenerPropagate");
		shouldPropagate = !(sp.equalsIgnoreCase("No"));
	}
	
	public void acceptNodeForMerge(ISubjectProxy node) {
		synchronized(thread) {
			thread.addDocument(node);
			thread.notify();
		}
	}

	public void shutDown() {
		synchronized(thread) {
			isRunning = false;
			thread.notify();
		}
		sameLabelDetector.shutDown();
	}

	/**
	 * Send the data
	 * @param data
	 */
	//TODO this is blocking!!!
	void serveData(String data) {
		ServerSocket srvr = null;
		Socket skt = null;
	    try {
	        srvr = new ServerSocket(myPort);
	        //java.net.BindException: Address already in use: JVM_Bind
	        System.out.println("DocumentProcessor socket "+srvr);
	        skt = srvr.accept();
	        System.out.print("Server has connected!\n");
	        PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
	        System.out.print("Sending string: '" + data + "'\n");
	        out.print(data);
	        out.flush();
	        out.close();
	    }
	    catch(Exception e) {
	    	//NOTE: this especially happens if there is no listener running
	        System.out.print("Whoops! MergeInterceptor didn't work!\n");
	        e.printStackTrace();
	        //TODO figure out how to get this into Solr's logging system
	    } finally {
	    	try {
	    		if (skt != null)
	    			skt.close();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	try {
	    		if (srvr != null)
	    			srvr.close();
	    	} catch (Exception x) {
	    		x.printStackTrace();
	    	}

	    }
	}
	
	class Worker extends Thread {
		private List<ISubjectProxy>documents;
		private boolean isStillRunning = true;
		
		Worker() {
			documents = new ArrayList<ISubjectProxy>();
			this.start();
		}
		
		public void addDocument(ISubjectProxy doc) {
			synchronized(documents) {
				System.out.println("INTERCEPTING "+doc.getLocator()+" "+documents.size()+" "+isRunning);
				documents.add(doc);
				documents.notifyAll();
				System.out.println("INTERCEPTED");
			}
		}
		
		public void run() {
			ISubjectProxy theDoc = null;
			while (isStillRunning) {
				synchronized(documents) {
					System.out.println("INTERX "+documents.size()+" "+isRunning);
					if (documents.isEmpty()) {
						if (!isRunning)
							isStillRunning = false;
						else {
							try {
								documents.wait();
								System.out.println("NOTIFIED");
							} catch (Exception e) {e.printStackTrace();}
							if (!documents.isEmpty()) {
								theDoc = documents.remove(0);
								System.out.println("INTERCEPTING2 "+theDoc.getLocator()+" "+documents.size());								
							}
						}
					}
					else {
						theDoc = documents.remove(0);
						System.out.println("INTERCEPTING3 "+theDoc.getLocator());
					}
				}
				if (theDoc != null) {
					sameLabelDetector.acceptProxy(theDoc);
					if (shouldPropagate)
						serveData(theDoc.toJSONString());
					theDoc = null;
				}
			}
		}
	}
}
