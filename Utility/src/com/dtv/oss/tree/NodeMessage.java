/*
 * Created on 2004-11-10
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
public class NodeMessage implements Serializable {
	//�ڵ���Ϣ�Ľ�ֵ
	private String key="";
	//�ڵ���Ϣ����ʾ������Ϣ
	private String keyName="";
	//�����ǰ�ڵ��Ƿ���Ҫ�������ݲ���
	private boolean canAction=false;
	//�����ǰ�ڵ��Ҫ�ύ��ACTION.DO
	private String atcionTarget ="";
	//submit��Ŀ��frame
	private String submitTarget="";
	//��Ż����ύʱ�����Ľ�ֵ��
	private Map keyAndValueMap=null;
	//��ѯҳ����ʼ��
	private String pageFrom="1";
	//��ѯҳ����ʼ�е���ֹ��
	private String pageTo="10";
	//�Ƿ���Ҫ��ҳ����
	private boolean canHaveMultiplePage=true;
	
	/**
	 * ���캯��
	 * @param key
	 * @param keyName
	 */
	public NodeMessage(){
		super();
	}
	public NodeMessage( String key,String keyName ){
		this.key=key;
		this.keyName=keyName;
	}
	public NodeMessage( String key,String keyName,boolean canAction,String atcionTarget ){
		this.key=key;
		this.keyName=keyName;
		this.canAction=canAction;
		this.atcionTarget=atcionTarget;
	}
	public NodeMessage( String key,String keyName,boolean canAction,String atcionTarget,String submitTarget ){
		this.key=key;
		this.keyName=keyName;
		this.canAction=canAction;
		this.atcionTarget=atcionTarget;
		this.submitTarget=submitTarget;
		
	}
	public NodeMessage( String key,String keyName,boolean canAction,String atcionTarget,String submitTarget ,Map keyAndValueMap){
		this.key=key;
		this.keyName=keyName;
		this.canAction=canAction;
		this.atcionTarget=atcionTarget;
		this.submitTarget=submitTarget;
		this.keyAndValueMap=keyAndValueMap;
		
	}
	public void setCanHaveMultiplePage(boolean canHaveMultiplePage){
		this.canHaveMultiplePage = canHaveMultiplePage;
	}
	public boolean canHaveMultiplePage(){
		return this.canHaveMultiplePage;
	}
	public void setKeyAndValueMap(Map keyAndValueMap){
		this.keyAndValueMap = keyAndValueMap;
	}
	public Map getKeyAndValueMap(){
		return this.keyAndValueMap;
	}
	public void putKeyAndValue(String currentKey,String currentValue){
		if(this.keyAndValueMap==null){
			this.keyAndValueMap=new LinkedHashMap();
		}
		this.keyAndValueMap.put(currentKey,currentValue);
	}
	public void setSubmitTarget(String submitTarget){
		this.submitTarget = submitTarget;
	}
	public String getSubmitTarget(){
		return this.submitTarget;
	}
	public void setKey(String key){
		this.key = key;
	}
	public String getKey(){
		return this.key;
	}
	public void setAtcionTarget(String atcionTarget){
		this.atcionTarget = atcionTarget;
	}
	public String getAtcionTarget(){
		return this.atcionTarget;
	}
	
	public void setCanAction(boolean canAction){
		this.canAction = canAction;
	}
	public boolean canAction(){
		return this.canAction;
	}
	
	public void setKeyName(String keyName){
		this.keyName = keyName;
	}
	public String getKeyName(){
		return this.keyName;
	}
	public void setPageFrom(String pageFrom){
		this.pageFrom = pageFrom;
	}
	public String getPageFrom(){
		return pageFrom;
	}
	public void setPageTo(String pageTo){
		this.pageTo = pageTo;
	}
	public String getPageTo(){
		return pageTo;
	}
}
