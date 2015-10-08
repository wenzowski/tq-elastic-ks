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

import java.util.List;
import java.util.Map;

import org.nex.config.ConfigPullParser;
import org.topicquests.common.api.IResult;
import org.topicquests.ks.api.IExtendedConsoleDisplay;
import org.topicquests.ks.api.IExtendedEnvironment;
import org.topicquests.ks.api.ITQDataProvider;
import org.topicquests.ks.tm.JSONBootstrap;
import org.topicquests.ks.tm.SubjectProxyModel;
import org.topicquests.ks.tm.TQSystemDataProvider;
import org.topicquests.ks.tm.api.ISubjectProxyModel;
import org.topicquests.ks.tm.merge.VirtualizerHandler;
import org.topicquests.node.provider.ProviderEnvironment;
import org.topicquests.util.LoggingPlatform;
import org.topicquests.util.Tracer;

/**
 * @author park
 *
 */
public class SystemEnvironment implements IExtendedEnvironment {
	private LoggingPlatform logger = LoggingPlatform.getInstance("logger.properties");
	private Map<String,Object>configProps;
	private ProviderEnvironment provider;
	private ISubjectProxyModel proxyModel;
	private ITQDataProvider database = null;
	private VirtualizerHandler virtualizerHandler = null;
	private StatisticsUtility stats;
	private JSONBootstrap jsonBootstrapper;
	private IExtendedConsoleDisplay console;
	private SearchEnvironment searchEnvironment;




	/**
	 * 
	 */
	public SystemEnvironment() {
		ConfigPullParser p = new ConfigPullParser("topicmap-props.xml");
		configProps = p.getProperties();
		provider = new ProviderEnvironment();
		int cacheSize = 8192; //TODO get from config
		try {
			database = new TQSystemDataProvider(this, cacheSize);
			virtualizerHandler = new VirtualizerHandler(this);
			database.setVirtualizerHandler(virtualizerHandler);
		} catch (Exception e) {
			logError(e.getMessage(), e);
			e.printStackTrace();
			//TODO  this is fatal -- System.exit
		}
		proxyModel = database.getSubjectProxyModel();
		init();
		logDebug("Environment Started");
	}
	
	public void setConsole(IExtendedConsoleDisplay c) {
		console = c;
		searchEnvironment = new SearchEnvironment(this);
	}
	public ITQDataProvider getDatabase() {
		return database;
	}
	public ProviderEnvironment getProvider() {
		return provider;
	}
	
	public ISubjectProxyModel getSubjectProxyModel() {
		return proxyModel;
	}
	
	public VirtualizerHandler getVirtualizerHandler() {
		return virtualizerHandler;
	}
	
	public StatisticsUtility getStats() {
		return stats;
	}
	
	@Override
	public IExtendedConsoleDisplay getConsoleDisplay() {
		return console;
	}

	@Override
	public SearchEnvironment getSearchEnvironment() {
		return searchEnvironment;
	}

	/**
	 * Available to extensions if needed by way of additional JSON files
	 */
	public void bootstrap() {
		JSONBootstrap bs = new JSONBootstrap(this);
		IResult r = bs.bootstrap();
	}

	public void shutDown() {
		//TODO
	}
	
	private void init() {
		if (stats == null)
			stats = new StatisticsUtility();
		String bs = getStringProperty("ShouldBootstrap");
		boolean shouldBootstrap = false; // default value
		if (bs != null)
			shouldBootstrap = bs.equalsIgnoreCase("Yes");
		if (shouldBootstrap)
			bootstrap();

	}

	/////////////////////////////
	// Utilities
	/////////////////////////////
	@Override
	public Map<String,Object> getProperties() {
		return configProps;
	}
	
	public Object getProperty(String key) {
		return configProps.get(key);
	}
	@Override
	public String getStringProperty(String key) {
		return (String)configProps.get(key);
	}
	@Override
	public List<List<String>> getListProperty(String key) {
		return (List<List<String>>)configProps.get(key);
	}

	public void logDebug(String msg) {
		logger.logDebug(msg);
	}
	
	public void logError(String msg, Exception e) {
		logger.logError(msg,e);
	}
	
	public void record(String msg) {
		logger.record(msg);
	}

	public Tracer getTracer(String name) {
		return logger.getTracer(name);
	}

	@Override
	public ITQDataProvider getDataProvider() {
		// TODO Auto-generated method stub
		return null;
	}



}
