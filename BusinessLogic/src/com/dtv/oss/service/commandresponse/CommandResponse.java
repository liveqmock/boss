package com.dtv.oss.service.commandresponse;

import java.util.HashMap;

public class CommandResponse implements java.io.Serializable {

	/**
	 * ���ز�ѯ�Ľ����
	 */
    private Object payload = null;
    
    private int errorCode = 0;
    //�������������(���ݺ�̨�Ĵ�����������������ת�ı�־)����ת��־
    private String flowHandler=null;
    
    private HashMap map =null;

    /**
     * ������Ϣ
     * add by jason.zhou
     */
    private Object extraPayLoad=null;
    
    public CommandResponse(Object payload) {
        this.payload = payload;
        this.errorCode = 0;
        this.extraPayLoad=null;
        this.map=null;
        //System.out.println("CommandResponse's construct is called...");
        //System.out.println("and the errorCode is :" + errorCode);
    }

    //add by jason.zhou
    public CommandResponse(Object payload,Object extralPayload){
    	this.payload=payload;
    	this.extraPayLoad=extralPayload;
    	this.errorCode=0;
    }
    
    public CommandResponse(Object payload,Object extralPayload,HashMap map ){
    	this.payload=payload;
    	this.extraPayLoad=extralPayload;
    	this.errorCode=0;
    	this.map=map;
    }
    
    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public Object getPayload() {
        return payload;
    }
	
	public Object getExtraPayLoad() {
		return extraPayLoad;
	}

	public void setExtraPayLoad(Object extraPayLoad) {
		this.extraPayLoad = extraPayLoad;
	}

	/**
	 * @deprecated
	 * now the EJB tier give client error notice through CommandException,
	 * more information to see CommandException.java
	 */
    public int getErrorCode() { return errorCode; }
	/**
	 * @deprecated
	 * the same with getErrorCode() method
	 */    
    public void setErrorCode(int val) { this.errorCode = val; }

	/**
	 * @return the flowHandler
	 */
	public String getFlowHandler() {
		return flowHandler;
	}

	/**
	 * @param flowHandler the flowHandler to set
	 */
	public void setFlowHandler(String flowHandler) {
		this.flowHandler = flowHandler;
	}
	
	/**
	 * @return the map
	 */
	public HashMap getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(HashMap map) {
		this.map = map;
	}
}