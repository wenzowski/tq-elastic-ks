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
package org.topicquests.ks.api;

import net.minidev.json.JSONObject;

import org.topicquests.common.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.tm.api.IMergeImplementation;
import org.topicquests.ks.tm.api.IMergeResultsListener;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.ks.tm.api.ISubjectProxyModel;
import org.topicquests.ks.tm.api.ITuple;
import org.topicquests.ks.tm.api.ITupleQuery;
import org.topicquests.ks.tm.merge.VirtualizerHandler;

/**
 * @author park
 *
 */
public interface ITQDataProvider {

	/**
	 * This allows us to create plug-in dataproviders
	 * @param env
	 * @param cachesize
	 * @return
	 */
	IResult init(SystemEnvironment env, int cachesize);
	
	/**
	 * Will return <code>true</code> if <code>errorMessage</code>
	 * indicates an OptimisticLock as defined in {@link IErrorMessages}
	 * @param errorMessage
	 * @return
	 */
	boolean isOptimisticLockError(String errorMessage);
	
	/**
	 * Return the {@link INodeModel} installed in this system
	 * @return
	 */
	ISubjectProxyModel getSubjectProxyModel();

	/**
	 * Return the {@link ITupleQuery installed in this system
	 * @return
	 */
	ITupleQuery getTupleQuery();
	
	  /**
	   * Remove an {@link INode} from the internal cache
	   * @param nodeLocator
	   */
	  void removeFromCache(String nodeLocator);
	
	  /**
	   * Returns a UUID String
	   * @return
	   */
	  String getUUID();
	  
	  /**
	   * Returns a UUID String with a <code>prefix</code>
	   * @param prefix
	   * @return
	   */
	  String getUUID_Pre(String prefix);
	  
	  /**
	   * Return a UUID String with a <code>suffix</code>
	   * @param suffix
	   * @return
	   */
	  String getUUID_Post(String suffix);

	/**
	 * Load a {@link ITreeNode} starting from <code>rootNodeLocator</code>
	 * with all its child nodes (<em>subs</em> and <em>instances</em>)
	 * to a depth defined by <code>maxDepth</code>
	 * @param rootNodeLocator
	 * @param maxDepth  -1 means no limit
	 * @param start TODO
	 * @param count TODO
	 * @param credentials
	 * @return
	 */
	IResult loadTree(String rootNodeLocator, int maxDepth, int start, int count, ITicket credentials);

	////////////////////////////////////
	// PROXIES
	////////////////////////////////////
	
	/**
	 * <p>Fetch a node. <code>credentials</code> are required in case
	 * the node is private and a credential must be tested</p>
	 * <p>Error message will be returned if the node is private and insufficient
	 * credentials are presented</p>
	 * <p>Returns <code>null</code> as the result object if there is no node or
	 * if credentials are insufficient</p>
	 * @param locator
	 * @param credentials
	 * @return
	 */
	IResult getNode(String locator, ITicket credentials);
	
	/**
	 * A <code>URL</code> represented in an internal URL
	 * property is considered a <em>subject identity</em>
	 * value, meaning, one and only one proxy can have that
	 * value.
	 * @param url
	 * @param credentials
	 * @return
	 */
	IResult getNodeByURL(String url, ITicket credentials);
	
	/**
	 * 
	 * @param tuple
	 * @param checkVersion
	 * @return
	 */
	IResult putTuple(ITuple tuple, boolean checkVersion);
	  
	/**
	 * Return an <code>ITuple</code> inside an {@link IResult} object or <code>null</code> if not found
	 * @param tupleLocator
	 * @param credentials
	 * @return -- an IResult object that contains either an ITuple or an error message
	 */
	IResult getTuple(String tupleLocator, ITicket credentials);  
	  	  
	/**
	 * Behaves as if to <em>replace</em> <code>node</code>
	 * @param node
	 * @param checkVersion
	 * @return
	 */
	IResult updateNode(ISubjectProxy node, boolean checkVersion);
	  
	/**
	 * Returns a raw {@link INode} as a {@link JSONObject}
	 * @param locator
	 * @param credentials
	 * @return
	 */
	IResult getNodeJSON(String locator, ITicket credentials);
	  
	/**
	 * <p>If <code>node</code> is a <em>merged node</em>, then
	 * return the <em>virtual node</em> which represents it. Otherwise,
	 * return <code>null</code> inside {@link IResult}
	 * @param node
	 * @param credentials
	 * @return
	 */
	IResult getVirtualNodeIfExists(ISubjectProxy node, ITicket credentials);
	  
	/**
	 * Returns a Boolean <code>true</code> if there exists an {@link ITuple} of 
	 * <code>relationLocator</code> and
	 * either a <em>subject</em> or </em>object</em> identified by <code>theLocator</code>
	 * @param theLocator
	 * @param relationLocator
	 * @return
	 */
	IResult existsTupleBySubjectOrObjectAndRelation(String theLocator, String relationLocator);
	  
	/**
	 * Returns a Boolean <code>true</code> if an {@link INode} exists for the given
	 * <code>locator<?code>
	 * @param locator
	 * @return
	 */
	IResult existsNode(String locator);
	
	/**
	 * <p>Tests whether <code>nodeLocator</code> is of type or a subclass of 
	 * <code>targetTypeLocator</code></p>
	 * @param nodeLocator
	 * @param targetTypeLocator
	 * @param credentials
	 * @return
	 */
	IResult nodeIsA(String nodeLocator, String targetTypeLocator, ITicket credentials);
	  
	/**
	 * Assemble a node view based on the node and its various related nodes
	 * @param locator
	 * @param credentials
	 * @return
	 */
	IResult getNodeView(String locator, ITicket credentials);
	  
	/**
	 * <p>Remove a node from the database</p>
	 * <p>This is used for all nodes and tuples</p>
	 * @param locator
	 * @param credentials
	 * @return
	 */
	IResult removeNode(String locator, ITicket credentials);
	  
	/**
	 * Remove <code>node</code>
	 * @param node
	 * @param credentials
	 * @return
	 */
	IResult removeNode(ISubjectProxy node, ITicket credentials);
	
	/**
	 * <p>Put <code>node</code> in the database. Subject it to merge and harvest</p>
	 * <p>Can return an <em>OptimisticLockException</em> error message if version numbers
	 * are not appropriate.</p>
	 * @param node
	 * @param checkVersion
	 * @return
	 */
	IResult putNode(ISubjectProxy node, boolean checkVersion);
	  
	/**
	 * Put <code>node</code> in the database. Subject to harvest; no merge performed
	 * @param node
	 * @param checkVersion
	 * @return
	 */
	IResult putNodeNoMerge(ISubjectProxy node, boolean checkVersion);	
	

	/**
	 * <p>List nodes associated with <code>psi</code></p>
	 * <p>Note: a <code>psi</code> is theoretically a <em>unique</em> identifier
	 * or a node; there shoule be just one node returned, if any.</p>
	 * @param psi
	 * @param start
	 * @param count
	 * @param credentials
	 * @return
	 */
	IResult listNodesByPSI(String psi, int start, int count, ITicket credentials);
	  
  /**
   * <p>List nodes by the combination of a <code>label</code> and <code>typeLocator</code></p>
   * @param label
   * @param typeLocator
   * @param language
   * @param start
   * @param count
   * @param credentials
   * @return
   */
  IResult listNodesByLabelAndType(String label, String typeLocator,String language, int start, int count, ITicket credentials);
  
  /**
   * <p>List nodes by <code>label</code></p>
   * @param lagel
   * @param language 
   * @param start
   * @param count
   * @param credentials
   * @return
   */
  IResult listNodesByLabel(String label,String language, int start, int count, ITicket credentials);
  
  /**
   * <p>Return nodes with labels that are <em>like</em> <code>labelFragment</code></p>
   * <p>A <em>wildcard</em> is added before and after <code>labelFragment</code></p>
   * <p>Example: given the string "My favorite topic"; would be matched with My, favorite, or topic</p>
   * <p>Results are case sensitive</p>
   * @param labelFragment
   * @param language 
   * @param start
   * @param count
   * @param credentials
   * @return
   */
  IResult listNodesByLabelLike(String labelFragment, String language, int start, int count, ITicket credentials);
  
  /**
   * <p>Return nodes with details that are <em>like</em> <code>detailsFragment</code></p>
   * @param detailsFragment
   * @param language 
   * @param start
   * @param count
   * @param credentials
   * @return
   */
  IResult listNodesByDetailsLike(String detailsFragment, String language, int start, int count, ITicket credentials);
  
  /**
   * Answer a particular Solr query string
   * @param queryString
   * @param start
   * @param count
   * @param credentials
   * @return
   */
  IResult listNodesByQuery(String queryString,int start, int count, ITicket credentials);
  
  /**
   * Return nodes created by <code>creatorId</code>
   * @param creatorId
   * @param start
   * @param count
   * @param credentials
   * @return
   */
  IResult listNodesByCreatorId(String creatorId, int start, int count, ITicket credentials);
  
  /**
   * Return nodes of type <code>typeLocator</code>
   * @param typeLocator
   * @param start
   * @param count
   * @param credentials
   * @return
   */
  IResult listNodesByType(String typeLocator,int start, int count, ITicket credentials);
    
  /**
   * Really, this is the same as <code>listNodesByType</code>
   * @param typeLocator
   * @param start
   * @param count
   * @param credentials
   * @return a list of [@link INode} objects or <code>null</code>
   */
  IResult listInstanceNodes(String typeLocator, int start, int count, ITicket credentials);
  
  /**
   * <p>List nodes by type, except if any nodes are merged, do not list them. All virtual nodes
   * will be listed</p>
   * @param typeLocator
   * @param start
   * @param count
   * @param credentials
   * @return
   */
  IResult listTrimmedInstanceNodes(String typeLocator, int start, int count, ITicket credentials);
  
  /**
   * List nodes which are subclasses of <code>superclassLocator</code>
   * @param superclassLocator
   * @param start
   * @param count
   * @param credentials
   * @return
   */
  IResult listSubclassNodes(String superclassLocator, int start, int count, ITicket credentials);
	

	/**
	 * <p>This accepts an {@link ISubjectProxy} expressed as a {@link JSONObject}
	 *  and imports it</p>
	 * <p>The node might be a new node created in an editor,
	 * or an edited existing node.</p>
	 * <p><code>nodeJSON</code> <em>must</em> be a complete proxy representation.</p>
	 * @param nodeJSON
	 * @return can return an OptimisticLock exception
	 */
	IResult updateProxyFromJSON(JSONObject nodeJSON);

	IResult updateProxyFromJSON(String jsonString);
	/**
	 * A <code>url</code> is like a PSI: it's an identity property
	 * @param url
	 * @param start TODO
	 * @param count TODO
	 * @param credentials
	 * @return
	 */ //killed because URL is not going to be like PSI
	//IResult getNodeByURL(String url, ITicket credentials);
	
	IResult listNodesByTypeAndURL(String type, String url, int start, int count, ITicket credentials);
	
	/**
	 * Update <code>node<code> which had its label or subject changed. This entails
	 * patching every node that references <code>proxy</code> using its label or subject
	 * @param node
	 * @param oldLabel
	 * @param newLabel
	 * @param credentials
	 * @return
	 */
	IResult updateNodeLabel(ISubjectProxy node, String oldLabel, String newLabel, ITicket credentials);

	////////////////////////////////////
	// MERGE
	////////////////////////////////////
	/**
	 * Support various ways of asserting a merge between two nodes
	 * @param leftNode
	 * @param rightNode
	 * @param reason
	 * @param userLocator
	 * @param mergeListener 
	 */
	void mergeTwoProxies(ISubjectProxy leftNode, ISubjectProxy rightNode, String reason, String userLocator, IMergeResultsListener mergeListener);
	/**
	 * 
	 * @param h
	 */
	void setVirtualizerHandler(VirtualizerHandler h);
	
	/**
	 * <p>Install an {@link IMergeImplementation} in this system</p>
	 * <p>The implementation is declared in the <code>config.xml</code> file</p>
	 * @param merger
	 */
	void setMergeBean(IMergeImplementation merger);

	//////////////////////////////////////////////////
	// General query support
	//////////////////////////////////////////////////
	/**
	 * <p>Note: <code>queryString</code> is composed of various elements
	 * which take the form <code>field:stuff</code> where stuff could be
	 * in the form of text to find, e.g. "over the rainbow".  In the case
	 * of text to find, that text must be escaped by <code>QueryUtil.escapeQueryCulprits(...)</code></p>
	 * @param queryString
	 * @param start
	 * @param count
	 * @param credentials
	 * @return
	 */
	IResult runQuery(String queryString, int start, int count, ITicket credentials);

}
