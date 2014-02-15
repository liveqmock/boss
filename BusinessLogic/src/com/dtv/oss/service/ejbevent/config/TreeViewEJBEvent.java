/*
 * Created on 2004-10-25
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
import com.dtv.oss.tree.DynamicRootNode;

public class TreeViewEJBEvent extends AbstractEJBEvent {
	public static final int QUERY_TEST_TREE_VIEW      =11111;
	private String  keyValue=null;
	private String  rootKeyValue=null;
	private DynamicRootNode dynamicRootNode =null;
	
	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public String getRootKeyValue() {
		return rootKeyValue;
	}
	public void setDynamicRootNode(DynamicRootNode dynamicRootNode) {
		this.dynamicRootNode = dynamicRootNode;
	}
	public DynamicRootNode getDynamicRootNode() {
		return this.dynamicRootNode;
	}
	public void setRootKeyValue(String rootKeyValue) {
		this.rootKeyValue = rootKeyValue;
	}
	public TreeViewEJBEvent(
		int actionType,
		String keyValue) {
		super();
		this.actionType = actionType;
		this.keyValue = keyValue;
	}
	public TreeViewEJBEvent(
			int actionType,
			String keyValue,
			DynamicRootNode dynamicRootNode) {
			super();
			this.actionType = actionType;
			this.keyValue = keyValue;
			this.dynamicRootNode = dynamicRootNode;
		}
}