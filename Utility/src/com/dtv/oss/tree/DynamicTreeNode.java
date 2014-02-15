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
public class DynamicTreeNode  implements Serializable {
	//�ö���ڵ㸸�ڵ����
	private DynamicTreeNode parentNode=null;
	//��ǰ�ڵ�ӵ�еĽڵ���Ϣ��ӵ�нڵ㽡ֵ����ʾ��Ϣ��
	private NodeMessage currentNode=null;
	//�ýڵ����ӵ�е������ڵ�Map(Map���ŵ���DynamicTreeNode�ļ�ֵ��)
	private Map   childNodeMap=null;
	//��ǰ�ڵ��������Ĳ�,ROOT�����Ϊ1�������Դ�����(ROOT++)
	private int   currentLevel=0;
	//�ö���ڵ���ֵܽڵ����
	private DynamicTreeNode brotherNode=null;
	//�жϵ�ǰ�ڵ��Ƿ����ֵܽڵ��boolean����������TRUE,û����FALSE��
	private boolean haveBrother=false;
	//�жϵ�ǰ�ڵ�ĸ��ڵ��Ƿ����ֵܽڵ��boolean����������TRUE,û����FALSE��
	private boolean parentHaveBrother=false;
	//�����ڵ������ĸ����ڵ�Ľ�ֵ
	private String parentNodeKey=null;
	//�洢��ǰ�ڵ����Ƿ���չ��״̬��չ����ON,û��չ����OFF��
	private String onAndOff="OFF";
	//�жϵ�ǰ�ڵ��Ƿ��¸��ڵ�ĵ�һ�����ӣ��ǣ�TRUE�����ǣ�FALSE��
	private boolean isFirstChild=false;
	//�жϵ�ǰ�ڵ��Ƿ��¸��ڵ�����һ�����ӣ��ǣ�TRUE�����ǣ�FALSE��
	private boolean isLastChild=false;
	/**
	 * 
	 */
	public DynamicTreeNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * ���ɽڵ�Ĺ��캯��
	 * @param parentNode
	 * @param currentNode
	 * @param childNodeMap
	 */
	public DynamicTreeNode(DynamicTreeNode parentNode,NodeMessage currentNode,Map childNodeMap){
		super();
		this.parentNode=parentNode;
		this.currentNode=currentNode;
		this.childNodeMap=childNodeMap;
	}
	/**
	 * 
	 * @param onAndOff
	 */
	public void setOnAndOff(String onAndOff){
		if("OFF".equalsIgnoreCase(onAndOff)){
			this.onAndOff = "ON";
		}else if("ON".equalsIgnoreCase(onAndOff)){ 
			this.onAndOff = "OFF";
		}
	}
	public String getOnAndOff(){
		return onAndOff;
	}
	/**
	 * 
	 * @param parentNodeKey
	 */
	public void setParentNodeKey(String parentNodeKey){
		this.parentNodeKey = parentNodeKey;
	}
	public String getParentNodeKey(){
		return parentNodeKey;
	}
	/**
	 * 
	 * @param parentNode
	 */
	public void setParentNode(DynamicTreeNode parentNode){
		this.parentNode = parentNode;
	}
	public DynamicTreeNode getParentNode(){
		return parentNode;
	}
	/**
	 * 
	 * @param brotherNode
	 */
	public void setBrotherNode(DynamicTreeNode brotherNode){
		this.brotherNode = brotherNode;
	}
	public DynamicTreeNode getBrotherNode(){
		return brotherNode;
	}
	/**
	 * 
	 * @param currentNode
	 */
	public void setCurrentNode(NodeMessage currentNode){
		this.currentNode = currentNode;
	}
	public NodeMessage getCurrentNode(){
		return currentNode;
	}	
	/**
	 * 
	 * @param childNodeMap
	 */
	public void setChildNodeMap(Map childNodeMap){
		this.childNodeMap = childNodeMap;
	}
	public Map getChildNodeMap(){
		return childNodeMap;
	}
	/**
	 * 
	 * @param key
	 * @param node
	 */
	public void setChildNode(String key,DynamicTreeNode node){
		if(childNodeMap==null){
			childNodeMap=new LinkedHashMap();
			childNodeMap.put(key,node);
		}else{
			childNodeMap.put(key,node);
		}
	}
	public DynamicTreeNode getChildNode(String key){
		if(childNodeMap!=null){
			return (DynamicTreeNode)childNodeMap.get(key);
		}else{
			return null;
		}
	}
	/**
	 * 
	 * @param currentLevel
	 */
	public void setCurrentLevel(int currentLevel){
		this.currentLevel = currentLevel;
	}
	public int getCurrentLevel(){
		return currentLevel;
	}
	/**
	 * 
	 * @param haveBrother
	 */
	public void setHaveBrother(boolean haveBrother){
		this.haveBrother = haveBrother;
	}
	public boolean haveBrother(){
		return haveBrother;
	}
	/**
	 * 
	 * @param parentHaveBrother
	 */
	public void setParentHaveBrother(boolean parentHaveBrother){
		this.parentHaveBrother = parentHaveBrother;
	}
	public boolean parentHaveBrother(){
		return parentHaveBrother;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isRootNode(){
		if(parentNode==null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 
	 * @return
	 */
	public boolean isParentNode(){
		if(childNodeMap==null || childNodeMap.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 
	 * @return
	 */
	public boolean hasChildNode(){
		if(childNodeMap!=null && !childNodeMap.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean containChild(String key){
		if(childNodeMap!=null){
			if((DynamicTreeNode)childNodeMap.get(key)!=null){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	/**
	 * 
	 * @param isFirstChild
	 */
	public void setIsFirstChild(boolean isFirstChild){
		this.isFirstChild = isFirstChild;
	}
	public boolean isFirstChild(){
		return isFirstChild;
	}
	/**
	 * 
	 * @param isLastChild
	 */
	public void setIsLastChild(boolean isLastChild){
		this.isLastChild = isLastChild;
	}
	public boolean isLastChild(){
		return isLastChild;
	}
	
	public String toString(){
		StringBuffer res=new StringBuffer();
		res.append("\nDynamicTreeNode.currentNode.getKey()		"+this.currentNode.getKey());
		res.append("\nDynamicTreeNode.currentNode.getKeyName()  "+this.currentNode.getKeyName());
		res.append("\nDynamicTreeNode.this.onAndOff       		"+this.onAndOff);
		res.append("\nDynamicTreeNode.currentLevel        		"+this.currentLevel);
		res.append("\nDynamicTreeNode.hasChildNode()	  		"+this.hasChildNode());
		res.append("\nDynamicTreeNode.isFirstChild        		"+this.isFirstChild);
		res.append("\nDynamicTreeNode.isLastChild         		"+this.isLastChild);
		res.append("\nDynamicTreeNode.haveBrother         		"+this.haveBrother);
		res.append("\nDynamicTreeNode.parentNodeKey         	"+this.parentNodeKey);
		
		return res.toString();
	}
}
