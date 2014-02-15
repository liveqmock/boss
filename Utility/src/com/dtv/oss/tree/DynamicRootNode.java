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
	//�ڵ㱻�������Ӧ��չ������ȣ���ʼ��ֵΪ1
	private int currentClickDeep=1;
	//�������и����ڵ�
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
	 * ���ݵ���Ļ����жϵ������Ӧ��չ�������
	 * @param currentClickNode ������Ľڵ�
	 */
	public void setCurrentClickDeep(DynamicTreeNode currentClickNode){
		//�ýڵ㱾����չ�������
		if("ON".equalsIgnoreCase(currentClickNode.getOnAndOff())){
			if(currentClickDeep<=(currentClickNode.getCurrentLevel()+1)){
				currentClickDeep=currentClickNode.getCurrentLevel()+1;	
			}
		//�ýڵ㱾����δչ�������
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
