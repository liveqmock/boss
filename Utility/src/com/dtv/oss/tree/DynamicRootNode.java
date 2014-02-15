/*
 * Created on 2004-11-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.tree;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DynamicRootNode implements Serializable {
	//节点被点击后树应该展开的深度，初始化值为1
	private int currentClickDeep=1;
	//存贮所有根树节点
	private Map nodeMap=null;
	/**
	 * 
	 */
	public DynamicRootNode() {
		super();
	}
	public DynamicRootNode(Map nodeMap) {
		super();
		this.nodeMap = nodeMap;
	}
	public void setCurrentClickDeep(int currentClickDeep){
		this.currentClickDeep = currentClickDeep;
	}
	/**
	 * 根据点击的击点判断点击后树应该展开的深度
	 * @param currentClickNode 被点击的节点
	 */
	public void setCurrentClickDeep(DynamicTreeNode currentClickNode){
		//该节点本身是展开的情况
		if("ON".equalsIgnoreCase(currentClickNode.getOnAndOff())){
			if(currentClickDeep<=(currentClickNode.getCurrentLevel()+1)){
				currentClickDeep=currentClickNode.getCurrentLevel()+1;	
			}
		//该节点本身是未展开的情况
		}else if("OFF".equalsIgnoreCase(currentClickNode.getOnAndOff())){
			if(currentClickDeep<currentClickNode.getCurrentLevel()){
				currentClickDeep=currentClickNode.getCurrentLevel();	
			}
		}
	}
	public int getCurrentClickDeep(){
		return currentClickDeep;
	}
	public void setNodeMap(Map nodeMap){
		this.nodeMap = nodeMap;
	}
	public Map getNodeMap(){
		return nodeMap;
	}
	public void setRootNode(String key,DynamicTreeNode node){
		if(nodeMap!=null){
			nodeMap.put( key, node);
		}else{
			nodeMap=new LinkedHashMap();
			nodeMap.put( key, node);
		}
	}
}
