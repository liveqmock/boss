package com.dtv.oss.service.ejbevent;

import java.io.Serializable;

public class QueryEJBEvent implements EJBEvent, Serializable {
	//侯2007-12-19增加,由前台来决定是不是进行分页,用于导出数据,
	//用于决定是不是下载查询.
	private boolean isDown=false;	
	/**
	 * Sub-command class to process this EJBEvent object
	 */
	private String  commandClassName = null;

	/**
	 * private List property to store query criteria
	 *
	 */
	private Object dto;

	/**
	 * end index
	 */
	private int to;

	/**
	 * start index, from 0
	 */
	private int from;
	
	private String remoteHostAddress ="";

	/**
	 * direction: "previous"|"next"
	 * Note: deprecated, according to from-to get list 2003/8/12
	 */
	private String direction = "";

	private int type;
	private int operatorID = 0;

	
	public QueryEJBEvent() {
	}
	
	/**
	 * constructor
	 */
	public QueryEJBEvent(Object dto, int from, int to, int querytype) {
		this.dto = dto;
		this.from = from;
        this.to = to;
        this.type = querytype;
	}



    public String getEJBCommandClassName() {
        /**@todo Implement this com.dtv.oss.ejb.service.ejbevent.EJBEvent method*/
        return commandClassName;
    }
    public void setEJBCommandClassName(String ejbCommandClassName) {
        /**@todo Implement this com.dtv.oss.ejb.service.ejbevent.EJBEvent method*/
        commandClassName = ejbCommandClassName;
    }

    public Object getCriteriaDTO() { return dto; }
    public void setCriteriaDTO(Object dto) { this.dto = dto; }

    public String getDirection() { return direction; }
    public void setDirection(String val) { direction = val; }
    public int getFrom() { return from; }
    public int getTo() { return to; }

	public int getType() {return type;}
	public void setType(int i) {type = i;}
	public int getOperatorID() {return operatorID;}
	public void setOperatorID(int i) {operatorID = i;}
	public String getRemoteHostAddress(){
		return this.remoteHostAddress;
	}
	public void setRemoteHostAddress(String remoteHostAddress){
		this.remoteHostAddress=remoteHostAddress;
	}

	public boolean isDown() {
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}



}