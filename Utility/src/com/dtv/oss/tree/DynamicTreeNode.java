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
	//该对象节点父节点对象
	private DynamicTreeNode parentNode=null;
	//当前节点拥有的节点信息（拥有节点健值和显示信息）
	private NodeMessage currentNode=null;
	//该节点对象拥有的子树节点Map(Map里存放的是DynamicTreeNode的键值对)
	private Map   childNodeMap=null;
	//当前节点所在树的层,ROOT的深度为1，其他以此类推(ROOT++)
	private int   currentLevel=0;
	//该对象节点的兄弟节点对象
	private DynamicTreeNode brotherNode=null;
	//判断当前节点是否有兄弟节点的boolean变量（有是TRUE,没有是FALSE）
	private boolean haveBrother=false;
	//判断当前节点的父节点是否有兄弟节点的boolean变量（有是TRUE,没有是FALSE）
	private boolean parentHaveBrother=false;
	//该树节点所属的根树节点的健值
	private String parentNodeKey=null;
	//存储当前节点树是否处于展开状态（展开：ON,没有展开：OFF）
	private String onAndOff="OFF";
	//判断当前节点是否事父节点的第一个儿子（是：TRUE，不是：FALSE）
	private boolean isFirstChild=false;
	//判断当前节点是否事父节点的最后一个儿子（是：TRUE，不是：FALSE）
	private boolean isLastChild=false;
	/**
	 * 
	 */
	public DynamicTreeNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 生成节点的构造函数
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
