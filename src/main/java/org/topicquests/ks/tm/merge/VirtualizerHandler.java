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

package org.topicquests.ks.tm.merge;

import java.util.*;

import org.topicquests.support.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.tm.api.IMergeResultsListener;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.ks.tm.api.IVirtualizer;

/**
 * @author park
 *
 */
public class VirtualizerHandler {
	private SystemEnvironment environment;
	private IVirtualizer virtualizer;
	private Worker worker;
	
	/**
	 * 
	 */
	public VirtualizerHandler(SystemEnvironment env) {
		environment = env;
		//virtualizer = environment.getVirtualizer();
		worker = new Worker();
		worker.start();
	}
	
	public void performMerge(ISubjectProxy primary, ISubjectProxy merge,
			Map<String,Double> mergeData, double confidence,
			String userLocator, IMergeResultsListener listener) {
		worker.addWorkerObject(new WorkerObject(primary,merge,mergeData,
				confidence,userLocator,listener));
	}
	
	public void shutDown() {
		if (worker != null)
			worker.shutDown();
	}
	class WorkerObject {
		public ISubjectProxy primary, merge;
		public Map<String,Double>mergeData;
		public double confidence;
		public String userLocator;
		public IMergeResultsListener listener;
		
		public WorkerObject(ISubjectProxy primary, ISubjectProxy merge,
				Map<String,Double> mergeData, double confidence,
				String userLocator,IMergeResultsListener listener) {
			this.primary = primary;
			this.merge = merge;
			this.mergeData = mergeData;
			this.confidence = confidence;
			this.userLocator = userLocator;
			this.listener = listener;
		}
	}
	
	class Worker extends Thread {
		private List<WorkerObject>objects = new ArrayList<WorkerObject>();
		private boolean isRunning = true;
		
		public void addWorkerObject(WorkerObject o) {
			synchronized(objects) {
				objects.add(o);
				objects.notify();
			}
		}
		
		public void shutDown() {
			synchronized(objects) {
				isRunning = false;
				objects.notify();
			}
		}
		
		public void run() {
			WorkerObject theO = null;
			while (isRunning) {
				synchronized(objects) {
					if (objects.isEmpty()) {
						try {
							objects.wait();
						} catch (Exception e) {}
					}
					if (isRunning && !objects.isEmpty())
						theO = objects.remove(0);
				}
				if (isRunning && theO != null) {
					doIt(theO);
					theO = null;
				}
			}
		}
		
		void doIt(WorkerObject wo) {
			IResult r = virtualizer.createVirtualNode(wo.primary, wo.merge, 
					wo.mergeData, wo.confidence, wo.userLocator);
			if (wo.listener != null) {
				if (r.getResultObject() != null)
					wo.listener.acceptMergeResults((String)r.getResultObject(), wo.primary.getLocator(),
							wo.merge.getLocator(), r.getErrorString());
			}
		}
	}

}
