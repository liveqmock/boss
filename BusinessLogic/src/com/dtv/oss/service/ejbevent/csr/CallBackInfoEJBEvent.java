/*
 * Created on 2005-11-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.ejbevent.csr;


import java.util.*;

/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CallBackInfoEJBEvent extends CsrAbstractEJbevent {
	public CallBackInfoEJBEvent(int actionType) {
		this.actionType = actionType;
	}
	//�ط���Ϣ�б�
	private Collection  callBackInfoList;
	//�ͻ�id
	private int customerID ;
	//�ͻ����޵�id
	private int custProblemID ;
	//�ͻ�Ͷ�ߵ�id
	private int custComplainID ;
	public Collection geCallBackInfoList() {
		return callBackInfoList;
	}
	public void setCallBackInfoList(Collection callBackInfoList) {
		this.callBackInfoList = callBackInfoList;
	}
	
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public int getCustProblemID() {
		return custProblemID;
	}
	public void setCustProblemID(int custProblemID) {
		this.custProblemID = custProblemID;
	}
	public int getCustComplainID() {
		return custComplainID;
	}
	public void setCustComplainID(int custComplainID) {
		this.custComplainID = custComplainID;
	}
}
