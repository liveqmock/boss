package com.dtv.oss.service.ejbevent.logistics;

import java.io.Serializable;
import java.util.ArrayList;

import com.dtv.oss.service.ejbevent.EJBEvent;

/**
 * Logistics模块EJBEvent
 * author     ：zhouxushun 
 * date       : 2005-11-30
 * description:
 * @author 250713z
 *
 */
public class LogisticsEJBEvent implements EJBEvent, Serializable {

	private String commandClassName = null;
	  private int operatorID = 0;
	  protected int actionType = -1;
	  private String remoteHostAddress ="";
	  private ArrayList deviceCheckList ;

	  //动作定义
	  public static final int DEVICE_IN_STOCK = 11;							//入库
	  public static final int DEVICE_OUT_STOCK = 12;						//出库
	  public static final int DEVICE_INVALIDATE = 13;						//报废
	  public static final int DEVICE_REPAIR = 14;							//送修
	  public static final int DEVICE_TRANSITION = 15;						//调拨
	  public static final int DEVICE_MANULCHANGE_STATUS = 16;	
	  public static final int DEVICE_PREAUTH_MATCH = 17;					//配对与授权
	  public static final int DEVICE_PREAUTH_1 = 18;						//授权
	  public static final int UNMATCH_DEVICE = 19;							//解除配对
	  public static final int DEVICE_RELEASEPREAUTH = 20;					//解除预授权
	  public static final int DEVICE_MODIFY = 21;
	  public static final int DEVICE_PREAUTH = 22;
	  public static final int DEVICE_DISAUTH = 23;
	  public static final int DEVICE_REFRESHPREAUTH = 24;					//刷新预授权
	  
	  public static final int FAPIAO_IN = 25;						//发票运入
	  public static final int FAPIAO_BACK = 26;						//发票回库
	  public static final int FAPIAO_BACK_QUERY = 27;				//发票回库查询
	  public static final int FAPIAO_MOVE = 28;				        //发票调拨
	  public static final int FAPIAO_USE = 31;				        //发票领用
	  public static final int FAPIAO_ABANDON = 30;					//发票作废	  
	  public static final int FAPIAO_DISCARD= 29;					//发票报废	
	  
	  public static final int DEVICE_PUT_INTO_DEPOT =30;            //单向设备入库
	  public static final int VOD_DEVICE_PUT_INTO_DEPOT =31;        //双向设备入库
	  
	  //设备信息修改
	  
	  public String getEJBCommandClassName() {
	    return commandClassName;
	  }

	  public void setEJBCommandClassName(String ejbCommandClassName) {
	    this.commandClassName = ejbCommandClassName;
	  }

	  public int getOperatorID() {
	    return operatorID;
	  }

	  public void setOperatorID(int i) {
	    this.operatorID = i;
	  }

	  public int getActionType() {
	    return actionType;
	  }

	  public void setActionType(int actionType) {
	    this.actionType = actionType;
	  }	  
	  public String getRemoteHostAddress(){
		  return this.remoteHostAddress;
	  }
	  public void setRemoteHostAddress(String remoteHostAddress){
		  this.remoteHostAddress=remoteHostAddress;
	  }

	  public ArrayList getDeviceCheckList() {
		return deviceCheckList;
	  }

	  public void setDeviceCheckList(ArrayList deviceCheckList) {
		this.deviceCheckList = deviceCheckList;
	  }
	  
	  
}