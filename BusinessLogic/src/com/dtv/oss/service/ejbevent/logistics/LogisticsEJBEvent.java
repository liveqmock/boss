package com.dtv.oss.service.ejbevent.logistics;

import java.io.Serializable;
import java.util.ArrayList;

import com.dtv.oss.service.ejbevent.EJBEvent;

/**
 * Logisticsģ��EJBEvent
 * author     ��zhouxushun 
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

	  //��������
	  public static final int DEVICE_IN_STOCK = 11;							//���
	  public static final int DEVICE_OUT_STOCK = 12;						//����
	  public static final int DEVICE_INVALIDATE = 13;						//����
	  public static final int DEVICE_REPAIR = 14;							//����
	  public static final int DEVICE_TRANSITION = 15;						//����
	  public static final int DEVICE_MANULCHANGE_STATUS = 16;	
	  public static final int DEVICE_PREAUTH_MATCH = 17;					//�������Ȩ
	  public static final int DEVICE_PREAUTH_1 = 18;						//��Ȩ
	  public static final int UNMATCH_DEVICE = 19;							//������
	  public static final int DEVICE_RELEASEPREAUTH = 20;					//���Ԥ��Ȩ
	  public static final int DEVICE_MODIFY = 21;
	  public static final int DEVICE_PREAUTH = 22;
	  public static final int DEVICE_DISAUTH = 23;
	  public static final int DEVICE_REFRESHPREAUTH = 24;					//ˢ��Ԥ��Ȩ
	  
	  public static final int FAPIAO_IN = 25;						//��Ʊ����
	  public static final int FAPIAO_BACK = 26;						//��Ʊ�ؿ�
	  public static final int FAPIAO_BACK_QUERY = 27;				//��Ʊ�ؿ��ѯ
	  public static final int FAPIAO_MOVE = 28;				        //��Ʊ����
	  public static final int FAPIAO_USE = 31;				        //��Ʊ����
	  public static final int FAPIAO_ABANDON = 30;					//��Ʊ����	  
	  public static final int FAPIAO_DISCARD= 29;					//��Ʊ����	
	  
	  public static final int DEVICE_PUT_INTO_DEPOT =30;            //�����豸���
	  public static final int VOD_DEVICE_PUT_INTO_DEPOT =31;        //˫���豸���
	  
	  //�豸��Ϣ�޸�
	  
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