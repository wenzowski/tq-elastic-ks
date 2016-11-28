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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minidev.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.nex.util.DateUtil;
import org.topicquests.common.ResultPojo;
import org.topicquests.common.api.IResult;
import org.topicquests.ks.api.ITQCoreOntology;
import org.topicquests.ks.tm.api.IAirStruct;
import org.topicquests.ks.tm.api.IChildStruct;
import org.topicquests.ks.tm.api.IInfoBox;
import org.topicquests.ks.tm.api.IParentChildContainer;
import org.topicquests.ks.tm.api.IParentChildStruct;
import org.topicquests.ks.tm.api.IRelationStruct;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.ks.tm.api.ITuple;

/**
 * @author park
 *
 */
public class SubjectProxy implements 
		ISubjectProxy, ITuple, IParentChildContainer {
	//we sometimes extend this object
	protected JSONObject data;
	/**
	 * @param jo
	 */
	public SubjectProxy(JSONObject jo) {
		data = jo;
	}
	

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#setRelationWeight(double)
	 */
	@Override
	public void setRelationWeight(double weight) {
		data.put(ITQCoreOntology.RELATION_WEIGHT, new Double(weight));

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#getRelationWeight()
	 */
	@Override
	public double getRelationWeight() {
		Double d = (Double)data.get(ITQCoreOntology.RELATION_WEIGHT);
		if (d == null)
			return -9999;
		return d.doubleValue();
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#setObject(java.lang.String)
	 */
	@Override
	public void setObject(String value) {
		data.put(ITQCoreOntology.TUPLE_OBJECT_PROPERTY, value);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#setObjectType(java.lang.String)
	 */
	@Override
	public void setObjectType(String typeLocator) {
		data.put(ITQCoreOntology.TUPLE_OBJECT_TYPE_PROPERTY, typeLocator);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#getObject()
	 */
	@Override
	public String getObject() {
		return (String)data.get(ITQCoreOntology.TUPLE_OBJECT_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#getObjectType()
	 */
	@Override
	public String getObjectType() {
		return (String)data.get(ITQCoreOntology.TUPLE_OBJECT_TYPE_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#setObjectRole(java.lang.String)
	 */
	@Override
	public void setObjectRole(String roleLocator) {
		data.put(ITQCoreOntology.TUPLE_OBJECT_ROLE_PROPERTY, roleLocator);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#getObjectRole()
	 */
	@Override
	public String getObjectRole() {
		return (String)data.get(ITQCoreOntology.TUPLE_OBJECT_ROLE_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#setSubjectLocator(java.lang.String)
	 */
	@Override
	public void setSubjectLocator(String locator) {
		data.put(ITQCoreOntology.TUPLE_SUBJECT_PROPERTY, locator);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#getSubjectLocator()
	 */
	@Override
	public String getSubjectLocator() {
		return (String)data.get(ITQCoreOntology.TUPLE_SUBJECT_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#setSubjectType(java.lang.String)
	 */
	@Override
	public void setSubjectType(String subjectType) {
		data.put(ITQCoreOntology.TUPLE_SUBJECT_TYPE_PROPERTY, subjectType);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#getSubjectType()
	 */
	@Override
	public String getSubjectType() {
		return (String)data.get(ITQCoreOntology.TUPLE_SUBJECT_TYPE_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#setSubjectRole(java.lang.String)
	 */
	@Override
	public void setSubjectRole(String roleLocator) {
		data.put(ITQCoreOntology.TUPLE_SUBJECT_ROLE_PROPERTY, roleLocator);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#getSubjectRole()
	 */
	@Override
	public String getSubjectRole() {
		return (String)data.get(ITQCoreOntology.TUPLE_SUBJECT_ROLE_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#setIsTransclude(boolean)
	 */
	@Override
	public void setIsTransclude(boolean isT) {
		String x = (isT ? "true":"false");
		data.put(ITQCoreOntology.TUPLE_IS_TRANSCLUDE_PROPERTY, x);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#setIsTransclude(java.lang.String)
	 */
	@Override
	public void setIsTransclude(String t) {
		setIsTransclude(t.equalsIgnoreCase("true"));
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#getIsTransclude()
	 */
	@Override
	public boolean getIsTransclude() {
		Object x = data.get(ITQCoreOntology.TUPLE_IS_TRANSCLUDE_PROPERTY);
		if (x != null) {
			if (x instanceof String) {
				if (x != null) {
					return Boolean.parseBoolean((String)x);
				}
			} else {
				return ((Boolean)x).booleanValue();
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#addScope(java.lang.String)
	 */
	@Override
	public void addScope(String scopeLocator) {
		this.addPropertyValue(ITQCoreOntology.SCOPE_LIST_PROPERTY_TYPE, scopeLocator);

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#addMergeReason(java.lang.String)
	 */
	@Override
	public void addMergeReason(String reason) {
		this.addMultivaluedSetStringProperty(ITQCoreOntology.MERGE_REASON_RULES_PROPERTY, reason);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#listMergeReasons()
	 */
	@Override
	public List<String> listMergeReasons() {
		return this.getMultivaluedProperty(ITQCoreOntology.MERGE_REASON_RULES_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#listScopes()
	 */
	@Override
	public List<String> listScopes() {
		return this.getMultivaluedProperty(ITQCoreOntology.SCOPE_LIST_PROPERTY_TYPE);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#setThemeLocator(java.lang.String)
	 */
	@Override
	public void setThemeLocator(String themeLocator) {
		data.put(ITQCoreOntology.TUPLE_THEME_PROPERTY, themeLocator);

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ITuple#getThemeLocator()
	 */
	@Override
	public String getThemeLocator() {
		return (String)data.get(ITQCoreOntology.TUPLE_THEME_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#doUpdate()
	 */
	@Override
	public IResult doUpdate() {
		IResult result = new ResultPojo();
		IResult temp;
		//change lastEditDate
		setLastEditDate(new Date());
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#makeField(java.lang.String, java.lang.String)
	 */
	@Override
	public String makeField(String fieldBase, String language) {
		String result = fieldBase;
		if (!language.equals("en"))
			result += language;
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#localIsA(java.lang.String)
	 */
	@Override
	public boolean localIsA(String typeLocator) {
		if (this.getNodeType().equals(typeLocator))
			return true;
		List<String>sups = this.listSuperclassIds();
		if (sups != null && !sups.isEmpty())
			return sups.contains(typeLocator);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setLocator(java.lang.String)
	 */
	@Override
	public void setLocator(String locator) {
		data.put(ITQCoreOntology.LOCATOR_PROPERTY, locator);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getLocator()
	 */
	@Override
	public String getLocator() {
		return (String)data.get(ITQCoreOntology.LOCATOR_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setCreatorId(java.lang.String)
	 */
	@Override
	public void setCreatorId(String id) {
		data.put(ITQCoreOntology.CREATOR_ID_PROPERTY, id);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getCreatorId()
	 */
	@Override
	public String getCreatorId() {
		return (String)data.get(ITQCoreOntology.CREATOR_ID_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#addMergeTupleLocator(java.lang.String)
	 */
	@Override
	public void addMergeTupleLocator(String locator) {
		Object o = data.get(ITQCoreOntology.MERGE_TUPLE_PROPERTY);
		if (o == null)
			data.put(ITQCoreOntology.MERGE_TUPLE_PROPERTY, locator);
		else if (o instanceof String) {
			if (!o.equals(locator)) {
				List<String> x = new ArrayList<String>();
				x.add((String)o);
				x.add(locator);
				data.put(ITQCoreOntology.MERGE_TUPLE_PROPERTY, x);
			}
		} else {
			List<String>x = (List<String>)o;
			if (!x.contains(locator)) {
				x.add(locator);
				data.put(ITQCoreOntology.MERGE_TUPLE_PROPERTY, x);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getMergeTupleLocator()
	 */
	@Override
	public String getMergeTupleLocator() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listMergeTupleLocators()
	 */
	@Override
	public List<String> listMergeTupleLocators() {
		Object o = data.get(ITQCoreOntology.MERGE_TUPLE_PROPERTY);
		List<String>result = null;
		if (o instanceof List) 
			result = (List<String>)o;
		else {
			result = new ArrayList<String>();
			result.add((String)o);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#isTuple()
	 */
	@Override
	public boolean isTuple() {
		String test = getSubjectLocator();
		//is this a node or a tuple?
		if (test != null && ! test.equals(""))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setVersion(java.lang.String)
	 */
	@Override
	public void setVersion(String version) {
		data.put(ITQCoreOntology.VERSION, version);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getVersion()
	 */
	@Override
	public String getVersion() {
		return (String)data.get(ITQCoreOntology.VERSION);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setDate(java.util.Date)
	 */
	@Override
	public void setDate(Date date) {
		data.put(ITQCoreOntology.CREATED_DATE_PROPERTY, DateUtil.formatIso8601(date));//DateUtil.defaultTimestamp(date));
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setDate(java.lang.String)
	 */
	@Override
	public void setDate(String date) {
		data.put(ITQCoreOntology.CREATED_DATE_PROPERTY, date);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getDate()
	 */
	@Override
	public Date getDate() {
		String dx = (String)data.get(ITQCoreOntology.CREATED_DATE_PROPERTY);
		return DateUtil.fromIso8601(dx); //fromDefaultTimestamp(dx);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setLastEditDate(java.util.Date)
	 */
	@Override
	public void setLastEditDate(Date date) {
		data.put(ITQCoreOntology.LAST_EDIT_DATE_PROPERTY, DateUtil.formatIso8601(date));//DateUtil.defaultTimestamp(date));
		setVersion(Long.toString(System.currentTimeMillis()));
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setLastEditDate(java.lang.String)
	 */
	@Override
	public void setLastEditDate(String date) {
		data.put(ITQCoreOntology.LAST_EDIT_DATE_PROPERTY, date);//DateUtil.defaultTimestamp(date));
		setVersion(Long.toString(System.currentTimeMillis()));
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getLastEditDate()
	 */
	@Override
	public Date getLastEditDate() {
		String dx = (String)data.get(ITQCoreOntology.LAST_EDIT_DATE_PROPERTY);
		return DateUtil.fromIso8601(dx); //fromDefaultTimestamp(dx);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getProperties()
	 */
	@Override
	public JSONObject getData() {
		return data;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#toXML()
	 */
	@Override
	public String toJSONString() {
		return data.toJSONString();
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setIsFederated(boolean)
	 */
	@Override
	public void setIsFederated(boolean t) {
		data.put(ITQCoreOntology.IS_FEDERATED, new Boolean(t));

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getIsFederated()
	 */
	@Override
	public boolean getIsFederated() {
		Boolean o = (Boolean)data.get(ITQCoreOntology.IS_FEDERATED);
		if (o != null)
			return o.booleanValue();
		return false;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#addLabel(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void addLabel(String label, String language, String userId,
			boolean isLanguageAddition) {
		String field = makeField(ITQCoreOntology.LABEL_PROPERTY,language);
		addMultivaluedSetStringProperty(field, label);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#addSmallLabel(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void addSmallLabel(String label, String language, String userId,
			boolean isLanguageAddition) {
		String field = makeField(ITQCoreOntology.SMALL_LABEL_PROPERTY,language);
		addMultivaluedSetStringProperty(field, label);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getLabel(java.lang.String)
	 */
	@Override
	public String getLabel(String language) {
		String field = makeField(ITQCoreOntology.LABEL_PROPERTY, language);
		return getFirstListValue(field);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getSmallLabel(java.lang.String)
	 */
	@Override
	public String getSmallLabel(String language) {
		String field = makeField(ITQCoreOntology.SMALL_LABEL_PROPERTY,language);
		return getFirstListValue(field);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listLabels()
	 */
	@Override
	public List<String> listLabels() {
		List<String> l = fetchLabels(ITQCoreOntology.LABEL_PROPERTY);
		return concatinateStringLists(l);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listLabels(java.lang.String)
	 */
	@Override
	public List<String> listLabels(String language) {
		String field = makeField(ITQCoreOntology.LABEL_PROPERTY,language);
		return this.getMultivaluedProperty(field);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listSmallLabels()
	 */
	@Override
	public List<String> listSmallLabels() {
		List<String> l = fetchLabels(ITQCoreOntology.SMALL_LABEL_PROPERTY);
		return l;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listSmallLabels(java.lang.String)
	 */
	@Override
	public List<String> listSmallLabels(String language) {
		String field = makeField(ITQCoreOntology.SMALL_LABEL_PROPERTY,language);
		return this.getMultivaluedProperty(field);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#addDetails(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void addDetails(String details, String language, String userId,
			boolean isLanguageAddition) {
		String field = makeField(ITQCoreOntology.DETAILS_PROPERTY,language);
		addMultivaluedSetStringProperty(field, details);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getDetails(java.lang.String)
	 */
	@Override
	public String getDetails(String language) {
		String field = makeField(ITQCoreOntology.DETAILS_PROPERTY,language);
		return getFirstListValue(field);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listDetails()
	 */
	@Override
	public List<String> listDetails() {
		List<String> l = fetchTextFields(ITQCoreOntology.DETAILS_PROPERTY);
		return concatinateStringLists(l);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listDetails(java.lang.String)
	 */
	@Override
	public List<String> listDetails(String language) {
		String field = makeField(ITQCoreOntology.DETAILS_PROPERTY,language);
		return this.getMultivaluedProperty(field);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setSmallImage(java.lang.String)
	 */
	@Override
	public void setSmallImage(String img) {
		data.put(ITQCoreOntology.SMALL_IMAGE_PATH, img);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setImage(java.lang.String)
	 */
	@Override
	public void setImage(String img) {
		data.put(ITQCoreOntology.LARGE_IMAGE_PATH, img);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getSmallImage()
	 */
	@Override
	public String getSmallImage() {
		return (String)data.get(ITQCoreOntology.SMALL_IMAGE_PATH);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getImage()
	 */
	@Override
	public String getImage() {
		return (String)data.get(ITQCoreOntology.LARGE_IMAGE_PATH);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setNodeType(java.lang.String)
	 */
	@Override
	public void setNodeType(String typeLocator) {
		data.put(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE, typeLocator);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getNodeType()
	 */
	@Override
	public String getNodeType() {
		return (String)data.get(ITQCoreOntology.INSTANCE_OF_PROPERTY_TYPE);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setIsVirtualProxy(boolean)
	 */
	@Override
	public void setIsVirtualProxy(boolean t) {
		data.put(ITQCoreOntology.IS_VIRTUAL_PROXY, t);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getIsVirtualProxy()
	 */
	@Override
	public boolean getIsVirtualProxy() {
		Object x = data.get(ITQCoreOntology.IS_VIRTUAL_PROXY);
		if (x != null) {
			if (x instanceof String) {
				if (x != null) {
					return Boolean.parseBoolean((String)x);
				}
			} else {
				return ((Boolean)x).booleanValue();
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#addSuperclassId(java.lang.String)
	 */
	@Override
	public void addSuperclassId(String superclassLocator) {
		List<String> ids = listSuperclassIds();
		if (ids == null || ids.size() == 0) {
			ids = new ArrayList<String>();
			data.put(ITQCoreOntology.SUBCLASS_OF_PROPERTY_TYPE, ids);
		}
		if (!ids.contains(superclassLocator))
			ids.add(superclassLocator);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setProperty(String key, Object value) {
		data.put(key, value);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#addPropertyValue(java.lang.String, java.lang.String)
	 */
	@Override
	public void addPropertyValue(String key, String value) {
		Object vx = getProperty(key);
		//TODO this behavior does not create a list
		if (vx == null)
			setProperty(key,value);
		else {
			List<String>vl = null;
			if (vx instanceof String) {
				vl = new ArrayList<String>();
				vl.add((String)vx);
				data.put(key, vl);
			} else
				vl = (List<String>)vx;
			vl.add(value);
		}

	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String key) {
		return data.get(key);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listSuperclassIds()
	 */
	@Override
	public List<String> listSuperclassIds() {
		return getMultivaluedProperty(ITQCoreOntology.SUBCLASS_OF_PROPERTY_TYPE);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setIsPrivate(boolean)
	 */
	@Override
	public void setIsPrivate(boolean isPrivate) {
		//String x = (isPrivate ? "true":"false");
		data.put(ITQCoreOntology.IS_PRIVATE_PROPERTY, isPrivate);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setIsPrivate(java.lang.String)
	 */
	@Override
	public void setIsPrivate(String t) {
		setIsPrivate(t.equalsIgnoreCase("true"));
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getIsPrivate()
	 */
	@Override
	public boolean getIsPrivate() {
		Object x = data.get(ITQCoreOntology.IS_PRIVATE_PROPERTY);
		System.out.println("GETISPRIVATE "+x);
		if (x != null) {
			if (x instanceof String) {
				if (x != null) {
					return Boolean.parseBoolean((String)x);
				}
			} else {
				return ((Boolean)x).booleanValue();
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setIsLive(boolean)
	 */
	@Override
	public void setIsLive(boolean t) {
		data.put(ITQCoreOntology.IS_LIVE, t);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getIsLive()
	 */
	@Override
	public boolean getIsLive() {
		Boolean o = (Boolean)data.get(ITQCoreOntology.IS_LIVE);
		if (o != null)
			return o.booleanValue();
		return true;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#isA(java.lang.String)
	 */
	@Override
	public boolean isA(String typeLocator) {
		if (this.getLocator().equals(typeLocator))
			return true;
		if (this.getNodeType() != null && this.getNodeType().equals(typeLocator))
			return true;
		List<String>sups = this.listTransitiveClosure();
		if (sups != null && !sups.isEmpty())
			return sups.contains(typeLocator);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setURL(java.lang.String)
	 */
	@Override
	public void setURL(String url) {
		data.put(ITQCoreOntology.RESOURCE_URL_PROPERTY, url);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getURL()
	 */
	@Override
	public String getURL() {
		return (String)data.get(ITQCoreOntology.RESOURCE_URL_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#addPSI(java.lang.String)
	 */
	@Override
	public void addPSI(String psi) {
		List<String> ids = getMultivaluedProperty(ITQCoreOntology.PSI_PROPERTY_TYPE);
		// no duplicates allowed
		if (!ids.contains(psi))
			ids.add(psi);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listPSIValues()
	 */
	@Override
	public List<String> listPSIValues() {
		List<String> result = getMultivaluedProperty(ITQCoreOntology.PSI_PROPERTY_TYPE);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listTransitiveClosure()
	 */
	@Override
	public List<String> listTransitiveClosure() {
		List<String>result = (List<String>)data.get(ITQCoreOntology.TRANSITIVE_CLOSURE_PROPERTY_TYPE);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setTransitiveClosure(java.util.List)
	 */
	@Override
	public void setTransitiveClosure(List<String> tc) {
		System.out.println("SubjectProxy.setTransitiveClosure "+tc);
		data.put(ITQCoreOntology.TRANSITIVE_CLOSURE_PROPERTY_TYPE, tc);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#addTransitiveClosureLocator(java.lang.String)
	 */
	@Override
	public void addTransitiveClosureLocator(String locator) {
		List<String>result = (List<String>)data.get(ITQCoreOntology.TRANSITIVE_CLOSURE_PROPERTY_TYPE);
		//should never be null, but must look
		if (result == null)
			result = new ArrayList<String>();
		if (!result.contains(locator)) {
			result.add(locator);
			data.put(ITQCoreOntology.TRANSITIVE_CLOSURE_PROPERTY_TYPE, result);
		}
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#removeTransitiveClosureLocator(java.lang.String)
	 */
	@Override
	public void removeTransitiveClosureLocator(String locator) {
		List<String>result = (List<String>)data.get(ITQCoreOntology.TRANSITIVE_CLOSURE_PROPERTY_TYPE);
		//should never be null, but must look
		if (result != null) {
			result.remove(locator);
			data.put(ITQCoreOntology.TRANSITIVE_CLOSURE_PROPERTY_TYPE, result);
		}
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#addRelation(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addRelation(String relationTypeLocator, String relationLocator,
			String relationLabel, String targetSmallIcon, String targetLocator,
			String targetLabel, String nodeType, String sourceOrTarget) {
		IRelationStruct s = new RelationStruct();
		s.setRelationLocator(relationLocator);
		s.setRelationType(relationTypeLocator);
		s.setRelationLabel(relationLabel);
		s.setTargetIcon(targetSmallIcon);
		s.setTargetLocator(targetLocator);
		s.setTargetLabel(targetLabel);
		s.setTargetNodeType(nodeType);
		s.setSourceOrTarget(sourceOrTarget);
		this.addMultivaluedSetJSONProperty(ITQCoreOntology.TUPLE_LIST_PROPERTY, s.getData());
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#addRestrictedRelation(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addRestrictedRelation(String relationTypeLocator,
			String relationLocator, String relationLabel,
			String targetSmallIcon, String targetLocator, String targetLabel,
			String nodeType, String sourceOrTarget) {
		IRelationStruct s = new RelationStruct();
		s.setRelationLocator(relationLocator);
		s.setRelationType(relationTypeLocator);
		s.setRelationLabel(relationLabel);
		s.setTargetIcon(targetSmallIcon);
		s.setTargetLocator(targetLocator);
		s.setTargetLabel(targetLabel);
		s.setTargetNodeType(nodeType);
		addMultivaluedSetJSONProperty(ITQCoreOntology.TUPLE_LIST_PROPERTY_RESTRICTED,s.getData());
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listRelationsByRelationType(java.lang.String)
	 */
	@Override
	public List<JSONObject> listRelationsByRelationType(String relationType) {
		List<JSONObject> relns = this.getMultivaluedJSONproperty(ITQCoreOntology.TUPLE_LIST_PROPERTY);
		if (relns == null)
			return new ArrayList<>();
		else if (relationType == null)
			return relns;
		else {
			//This is messy because we are dealing with JSON strings
			// based on IRelationStruct
			List<JSONObject> result = new ArrayList<>();
			JSONObject rln,x;
			int len = relns.size();
			for (int i=0;i<len;i++) {
				rln = relns.get(i);
				//x = pluckRelationTypeFromJSONString(rln);
				if (rln != null && rln.get("relationType").equals(relationType))
					result.add(rln);
			}
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listRestrictedRelationsByRelationType(java.lang.String)
	 */
	@Override
	public List<JSONObject> listRestrictedRelationsByRelationType(
			String relationType) {
		List<JSONObject> relns = this.getMultivaluedJSONproperty(ITQCoreOntology.TUPLE_LIST_PROPERTY_RESTRICTED);
		if (relns == null)
			return new ArrayList<>();
		else if (relationType == null)
			return relns;
		else {
			//This is messy because we are dealing with JSON strings
			// based on IRelationStruct
			List<JSONObject> result = new ArrayList<>();
			JSONObject rln,x;
			int len = relns.size();
			for (int i=0;i<len;i++) {
				rln = relns.get(i);
				//x = pluckRelationTypeFromJSONString(rln);
				if (rln != null && rln.get("relationType").equals(relationType))
					result.add(rln);
			}
			return result;
		}	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#addPivot(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addPivot(String relationTypeLocator, String relationLocator,
			String relationLabel, String documentSmallIcon,
			String targetLocator, String targetLabel, String nodeType,
			String sourceOrTarget) {
		IRelationStruct s = new RelationStruct();
		s.setRelationLocator(relationLocator);
		s.setRelationType(relationTypeLocator);
		s.setRelationLabel(relationLabel);
		s.setTargetIcon(documentSmallIcon);
		s.setTargetLabel(targetLabel);
		s.setTargetLocator(targetLocator);
		s.setTargetNodeType(nodeType);
		this.addMultivaluedSetJSONProperty(ITQCoreOntology.PIVOT_LIST_PROPERTY, s.getData());
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#addRestrictedPivot(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addRestrictedPivot(String relationTypeLocator,
			String relationLocator, String relationLabel,
			String documentSmallIcon, String targetLocator, String targetLabel,
			String nodeType, String sourceOrTarget) {
		IRelationStruct s = new RelationStruct();
		s.setRelationLocator(relationLocator);
		s.setRelationType(relationTypeLocator);
		s.setRelationLabel(relationLabel);
		s.setTargetIcon(documentSmallIcon);
		s.setTargetLocator(targetLocator);
		s.setTargetLabel(targetLabel);
		s.setTargetNodeType(nodeType);
		addMultivaluedSetJSONProperty(ITQCoreOntology.RESTRICTED_PIVOT_LIST_PROPERTY,s.getData());
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listPivotsByRelationType(java.lang.String)
	 */
	@Override
	public List<JSONObject> listPivotsByRelationType(String relationType) {
		List<JSONObject> relns = this.getMultivaluedJSONproperty(ITQCoreOntology.PIVOT_LIST_PROPERTY);
		if (relns == null)
			return new ArrayList<>();
		else if (relationType == null)
			return relns;
		else {
			//This is messy because we are dealing with JSON strings
			// based on IRelationStruct
			List<JSONObject> result = new ArrayList<>();
			JSONObject rln,x;
			int len = relns.size();
			for (int i=0;i<len;i++) {
				rln = relns.get(i);
				//x = pluckRelationTypeFromJSONString(rln);
				if (rln != null && rln.get("relationType").equals(relationType))
					result.add(rln);
			}
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listRestrictedPivotsByRelationType(java.lang.String)
	 */
	@Override
	public List<JSONObject> listRestrictedPivotsByRelationType(String relationType) {
		List<JSONObject> relns = this.getMultivaluedJSONproperty(ITQCoreOntology.RESTRICTED_PIVOT_LIST_PROPERTY);
		if (relns == null)
			return new ArrayList<JSONObject>();
		else if (relationType == null)
			return relns;
		else {
			//This is messy because we are dealing with JSON strings
			// based on IRelationStruct
			List<JSONObject> result = new ArrayList<>();
			JSONObject rln,x;
			int len = relns.size();
			for (int i=0;i<len;i++) {
				rln = relns.get(i);
				//x = pluckRelationTypeFromJSONString(rln);
				if (rln != null && rln.get("relationType").equals(relationType))
					result.add(rln);
			}
			return result;
		}
}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.IParentChildContainer#addChildNode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addChildNode(String contextLocator, String smallIcon,
			String locator, String subject, String transcluderLocator) {
		boolean result = false; // default
		IChildStruct s = new ChildStruct();
		s.setContextLocator(contextLocator);
		s.setLocator(locator);
		s.setSmallIcon(smallIcon);
		s.setSubject(subject);
		if (transcluderLocator != null)
			s.setTranscluderLocator(transcluderLocator);
		//String json = s.toJSON();
		List<JSONObject> relns = getMultivaluedJSONproperty(ITQCoreOntology.CHILD_NODE_LIST);
		if (relns == null) {
			relns = new ArrayList<>();
			data.put(ITQCoreOntology.CHILD_NODE_LIST, relns);
		}
		System.out.println("PROXY_ADDCHILD "+relns+" "+s.getData());
		if (!relns.contains(s.getData())) {
			relns.add(s.getData());
			this.data.put(ITQCoreOntology.CHILD_NODE_LIST, relns);
			System.out.println("PROXY_ADDCHILD2 "+relns);
			doUpdate();
			result = true;
		}	
		return result;
	}
	

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listChildNodes(java.lang.String)
	 */
	@Override
	public List<JSONObject> listChildNodes(String contextLocator) {
		List<JSONObject> relns = this.getMultivaluedJSONproperty(ITQCoreOntology.CHILD_NODE_LIST);
		if (relns == null)
			return new ArrayList<>();
		else if (contextLocator == null)
			return relns;
		else {
			//This is messy because we are dealing with JSON strings
			// based on IChildStruct
			List<JSONObject> result = new ArrayList<>();
			JSONObject rln,x;
			int len = relns.size();
			for (int i=0;i<len;i++) {
				rln = relns.get(i);
				//x = pluckContextFromJSONString(rln);
				if (rln != null && rln.get("contextLocator").equals(contextLocator))
					result.add(rln);
			}
			return result;
		}
	}

	public void addToParentChildList(String locator) {
		//IParentChildStruct s = new ParentChildStruct(locator, context);
		List<String> l = (List<String>)this.data.get(ITQCoreOntology.PARENT_CHILD_PROPERTY_TYPE);
		if (l == null) {
			l = new ArrayList<>();
		}
		if (!l.contains(locator)) {
			l.add(locator);
		}
		data.put(ITQCoreOntology.PARENT_CHILD_PROPERTY_TYPE, l);
		doUpdate();
	}
	
	@Override
	public List<String> listParentChildTree() {
		List<String> l = (List<String>)this.data.get(ITQCoreOntology.PARENT_CHILD_PROPERTY_TYPE);
		return l;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.IParentChildContainer#addParentNode(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addParentNode(String contextLocator, String smallIcon,
			String locator, String subject) {
		IChildStruct s = new ChildStruct();
		s.setContextLocator(contextLocator);
		s.setLocator(locator);
		s.setSmallIcon(smallIcon);
		s.setSubject(subject);
		//String json = s.toJSON();
		List<JSONObject> relns = getMultivaluedJSONproperty(ITQCoreOntology.PARENT_NODE_LIST);
		if (relns == null)
			relns = new ArrayList<>();
		if (!relns.contains(s.getData())) {
			relns.add(s.getData());
			this.data.put(ITQCoreOntology.PARENT_NODE_LIST, relns);
			doUpdate();
			//addToParentChildList(locator);
		}
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listParentNodes(java.lang.String)
	 */
	@Override
	public List<JSONObject> listParentNodes(String contextLocator) {
		List<JSONObject> relns = this.getMultivaluedJSONproperty(ITQCoreOntology.PARENT_NODE_LIST);
		if (relns == null)
			return new ArrayList<>();
		else if (contextLocator == null)
			return relns;
		else {
			//This is messy because we are dealing with JSON strings
			// based on IChildStruct
			List<JSONObject> result = new ArrayList<>();
			JSONObject rln,x;
			int len = relns.size();
			for (int i=0;i<len;i++) {
				rln = relns.get(i);
				//x = pluckContextFromJSONString(rln);
				if (rln != null && rln.get("contextLocator").equals(contextLocator))
					result.add(rln);
			}
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#addACLValue(java.lang.String)
	 */
	@Override
	public void addACLValue(String value) {
		List<String> l = getMultivaluedProperty(ITQCoreOntology.RESTRICTION_PROPERTY_TYPE);
		if (!l.contains(value)) {
			l.add(value);
			data.put(ITQCoreOntology.RESTRICTION_PROPERTY_TYPE, l);
		}
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#removeACLValue(java.lang.String)
	 */
	@Override
	public void removeACLValue(String value) {
		List<String> l = (List<String>)data.get(ITQCoreOntology.RESTRICTION_PROPERTY_TYPE);
		if (l != null)
			l.remove(value);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listACLValues()
	 */
	@Override
	public List<String> listACLValues() {
		List<String> result = getMultivaluedProperty(ITQCoreOntology.RESTRICTION_PROPERTY_TYPE);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#containsACL(java.lang.String)
	 */
	@Override
	public boolean containsACL(String value) {
		List<String> creds = listACLValues();
		return creds.contains(value);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#putInfoBox(org.topicquests.ks.tm.api.IInfoBox)
	 */
	@Override
	public void putInfoBox(IInfoBox infoBox) {
		Map<String,JSONObject>ib = getInfoBoxes();
		ib.put(infoBox.getName(), infoBox.getData());
		setInfoBoxes(ib);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getInfoBox(java.lang.String)
	 */
	@Override
	public JSONObject getInfoBox(String name) {
		Map<String,JSONObject>ib = getInfoBoxes();
		return ib.get(name);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#removeInfoBox(java.lang.String)
	 */
	@Override
	public void removeInfoBox(String name) {
		Map<String,JSONObject>ib = getInfoBoxes();
		ib.remove(name);
		setInfoBoxes(ib);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listInfoBoxes()
	 */
	@Override
	public List<JSONObject> listInfoBoxes() {
		List<JSONObject>result = new ArrayList<>();
		Map<String,JSONObject>ib = getInfoBoxes();
		Iterator<String>itr = ib.keySet().iterator();
		while (itr.hasNext())
			result.add(ib.get(itr.next()));
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setSubject(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void setSubject(String subjectString, String language,
			String userLocator) {
		String key = makeField(ITQCoreOntology.AIR_SUBJECT_PROPERTY, language);
		IAirStruct s = new AirStruct();
		s.setLastEditDate(DateUtil.formatIso8601(new Date())); //defaultTimestamp(new Date()));
		s.setText(subjectString);
		s.setEditComment("");
		s.setCreator(userLocator);
		this.data.put(key, s.toJSON());
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getSubject(java.lang.String)
	 */
	@Override
	public String getSubject(String language) {
		String key = makeField(ITQCoreOntology.AIR_SUBJECT_PROPERTY, language);
		return (String)this.data.get(key);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#updateSubject(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateSubject(String updatedSubject, String language,
			String userLocator, String comment) {
		String key = makeField(ITQCoreOntology.AIR_SUBJECT_PROPERTY, language);
		String versionKey = makeField(ITQCoreOntology.AIR_SUBJECT_VERSION_PROPERTY, language);
		//Updating means making a new version, and pushing the old one onto a version stack
		String current = getSubject(language);
		List<String>versions = this.listSubjectVersions(language);
		IAirStruct s = new AirStruct();
		s.setLastEditDate(DateUtil.formatIso8601(new Date())); //defaultTimestamp(new Date()));
		s.setText(updatedSubject);
		s.setEditComment(comment);
		s.setCreator(userLocator);
		this.data.put(key, s.toJSON());
		versions.add(current);
		this.data.put(versionKey, versions);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#setBody(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void setBody(String bodyString, String language, String userLocator) {
		String key = makeField(ITQCoreOntology.AIR_BODY_PROPERTY, language);
		IAirStruct s = new AirStruct();
		s.setLastEditDate(DateUtil.formatIso8601(new Date())); //defaultTimestamp(new Date()));
		s.setText(bodyString);
		s.setEditComment("");
		s.setCreator(userLocator);
		this.data.put(key, s.toJSON());
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#getBody(java.lang.String)
	 */
	@Override
	public String getBody(String language) {
		String key = makeField(ITQCoreOntology.AIR_BODY_PROPERTY, language);
		return (String)this.data.get(key);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#updateBody(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateBody(String updatedBody, String language,
			String userLocator, String comment) {
		String key = makeField(ITQCoreOntology.AIR_BODY_PROPERTY, language);
		String versionKey = makeField(ITQCoreOntology.AIR_BODY_VERSION_PROPERTY, language);
		String current = getSubject(language);
		List<String>versions = this.listSubjectVersions(language);
		IAirStruct s = new AirStruct();
		s.setLastEditDate(DateUtil.formatIso8601(new Date())); //defaultTimestamp(new Date()));
		s.setText(updatedBody);
		s.setEditComment(comment);
		s.setCreator(userLocator);
		this.data.put(key, s.toJSON());
		versions.add(current);
		this.data.put(versionKey, versions);
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listBodyVersions(java.lang.String)
	 */
	@Override
	public List<String> listBodyVersions(String language) {
		String versionKey = makeField(ITQCoreOntology.AIR_BODY_VERSION_PROPERTY, language);
		List<String>result = (List<String>)this.data.get(versionKey);
		if (result == null)
			result = new ArrayList<String>();
		return result;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.ks.tm.api.ISubjectProxy#listSubjectVersions(java.lang.String)
	 */
	@Override
	public List<String> listSubjectVersions(String language) {
		String versionKey = makeField(ITQCoreOntology.AIR_SUBJECT_VERSION_PROPERTY, language);
		List<String>result = (List<String>)this.data.get(versionKey);
		if (result == null)
			result = new ArrayList<String>();
		return result;
	}

	//////////////////////////////////
	private String getFirstListValue(String key) {
		List<String> l = this.getMultivaluedProperty(key);
		if (l.size()> 0)
			return l.get(0);		
		return "";
	}

	/**
	 * <p>Utility method for multivalued properties which may return a String</p>
	 * <p>If this turns a string value into a list value, it installs that list in properties</p>
	 * @param key
	 * @return does not return <code>null</code>
	 */
	private List<String> getMultivaluedProperty(String key) {
		List<String> result = null;
		Object op = data.get(key);
		if (op != null) {
			if (op instanceof String) {
				result = new ArrayList<String>();
				result.add((String)op);
			} else {
				result = (List<String>)op;
			} 
		} else {
			result = new ArrayList<String>();
			data.put(key, result);
		}
		return result;
	}
	
	private List<JSONObject> getMultivaluedJSONproperty(String key) {
		List<JSONObject> result = null;
		Object op = data.get(key);
		if (op != null) {
			if (op instanceof JSONObject) {
				result = new ArrayList<>();
				result.add((JSONObject)op);
			} else {
				result = (List<JSONObject>)op;
			} 
		} else {
			result = new ArrayList<JSONObject>();
			data.put(key, result);
		}
		return result;
	}
	/**
	 * Given a <code>baseField</code>, e.g. "label", fetch all
	 * label fields regardless of language codes
	 * @param baseField
	 * @return
	 */
	private List<String> fetchTextFields(String baseField) {
		List<String>result = new ArrayList<String>();
		Iterator<String>itr = data.keySet().iterator();
		String key;
		while (itr.hasNext()) {
			key = itr.next();
			if (key.startsWith(baseField))
				result.add(key);
		}
		return result;
	}

	/**
	 * Concatinate all the List<String> values indexed by <code>keys</code>
	 * @param keys
	 * @return
	 */
	private List<String> concatinateStringLists(List<String>keys) {
		List<String>result = new ArrayList<String>();
		int len= keys.size();
		String key;
		for (int i=0;i<len;i++) {
			key = keys.get(i);
			result.addAll(this.getMultivaluedProperty(key));
		}
		return result;
	}

	private void addMultivaluedSetJSONProperty(String key, JSONObject value) {
		Object o = data.get(key);
		List<JSONObject> ll;
		if (o == null) {
			ll = new ArrayList<>();
		} else if ( o instanceof JSONObject) {
			ll = new ArrayList<>();
			ll.add((JSONObject)o);
		} else {
			ll = (List<JSONObject>)o;
		}
		if (!ll.contains(value))
			ll.add(value);
		data.put(key, ll);
	}
	
	private void addMultivaluedSetStringProperty(String key, String value) {
		
		Object o = data.get(key);
		List<String> ll;
		if (o == null) {
			ll = new ArrayList<String>();
		} else if ( o instanceof String) {
			ll = new ArrayList<String>();
			ll.add((String)o);
		} else {
			ll = (List<String>)o;
		}
		if (!ll.contains(value))
			ll.add(value);
		data.put(key, ll);
	}
	
/**	private String pluckRelationTypeFromJSONString(String json) {
		String result = null;
		String str = json;
		int where = str.indexOf(IRelationStruct.RELATION_TYPE);
		if (where > -1) {
			str = StringUtils.substring(str, where+(IRelationStruct.RELATION_TYPE.length()));
			//that should leave :"foo" and we want foo
			where = str.indexOf('\"');
			str = StringUtils.substring(str, where);
			where = str.indexOf('\"');
			str = StringUtils.substring(str, 0, where);
			result = str;
		}
		System.out.println("Node.pluckRelationTypeFromJSONString "+json+" | "+result);
		return result;
	} */
	
	//TODO : would it be faster to just parse the string and fetch context?
/*	private String pluckContextFromJSONString(String json) {
		String result = null;
		String str = json;
		int where = str.indexOf(IChildStruct.CONTEXT_LOCATOR);
		if (where > -1) {
			str = StringUtils.substring(str, where+(IChildStruct.CONTEXT_LOCATOR.length()));
			//that should leave :"foo" and we want foo
			where = str.indexOf('\"');
			str = StringUtils.substring(str, where);
			where = str.indexOf('\"');
			str = StringUtils.substring(str, 0,where);
			result = str;
		}
		System.out.println("Node.pluckRelationTypeFromJSONString "+json+" | "+result);
		return result;
	} */

	/**
	 * Given a <code>baseField</code>, e.g. "label", fetch all
	 * label fields regardless of language codes
	 * @param baseField
	 * @return
	 */
	private List<String> fetchLabels(String baseField) {
		List<String>result = new ArrayList<String>();
		Iterator<String>itr = data.keySet().iterator();
		String key;
		while (itr.hasNext()) {
			key = itr.next();
			if (key.startsWith(baseField))
				result.add(key);
		}
		return result;
	}

	/////////////////////////////////////////////
	// AN InfoBox structure is a MAP of named infoboxes
	// Since we are dealing with JSON here, this should be easy!
	/////////////////////////////////////////////

	/**
	 * Internal
	 * @return does not return <code>null</code>
	 */
	private Map<String,JSONObject> getInfoBoxes() {
		Map<String,JSONObject> result = (Map<String,JSONObject>)data.get(ITQCoreOntology.INFO_BOX_LIST_PROPERTY);
		if (result == null)
			result = new HashMap<String,JSONObject>();
		return result;
	}
	
	/**
	 * Internal
	 * @param boxes
	 */
	private void setInfoBoxes(Map<String,JSONObject> boxes) {
		this.data.put(ITQCoreOntology.INFO_BOX_LIST_PROPERTY, boxes);
	}



}
