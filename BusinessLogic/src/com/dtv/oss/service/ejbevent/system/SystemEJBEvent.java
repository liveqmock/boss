/*
 * Created on 2004-8-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.ejbevent.system;
import java.io.Serializable;

import com.dtv.oss.service.ejbevent.EJBEvent;
/**
 * @author 240322y
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SystemEJBEvent implements EJBEvent, Serializable {
	public static final int OPERATOR_LOGIN 							= 100; //����Ա ��¼
	public static final int OPERATOR_LOGOUT 						= 101; //����Ա �ǳ�
	public static final int OPERATOR_CHANGE_PWD					= 102; //����Ա �޸�����
	
	public static final int CAMPAIGN_CREATE							= 201; //�Żݻ �½�
	public static final int CAMPAIGN_DELETE							= 202; //�Żݻ ɾ��
	public static final int CAMPAIGN_UPDATE							= 203; //�Żݻ ����
	
	public static final int GROUPBARGAIN_CREATE					= 301; //�Ź� �½�
	public static final int GROUPBARGAIN_DELETE					= 302; //�Ź� ɾ��
	public static final int GROUPBARGAIN_UPDATE					= 303; //�Ź� ����
	public static final int GROUPBARGAIN_SALE						= 304; //�Ź� ����

	public static final int GROUPBARGAIN_DETAIL_DELETE	= 401; //�Ź� ɾ��
	public static final int GROUPBARGAIN_DETAIL_UPDATE	= 402; //�Ź� ����	
	

	public static final int CUST_CAMAPAIGN_MODIFY=601;				//����ά��
	public static final int CUST_CAMAPAIGN_CANCEL=602;				//ȡ���ͻ�����
	public static final int CUST_CAMAPAIGN_MANUAL_GRANT=603;		//�˹���������
	public static final int CUST_BUNDLE_MODIFY=604;					//�ײ�ά��
	public static final int CUST_BUNDLE_DELAY=605;					//�ײ�����
	public static final int CUST_BUNDLE_CANCEL=606;					//ȡ���ͻ��ײ�
	public static final int CUST_BUNDLE_TRANSFER=607;				//�ײ�ת��
	public static final int CUST_BUNDLE_STOP=608;					//ֹͣ�ͻ��ײ�
	public static final int CUST_CAMAPAIGN_DELAY=609;				//�����ӳ�
	public static final int CUST_CAMAPAIGN_TRANSFER=610;			//����ת��
	public static final int CUST_BUNDLE_PREPAYMENT=611;				//�ײ�Ԥ��
    public static final int PROTOCOL_BUNDLE_PREPAYMENT =622;        //Э���û���������
	
	
	
	private String commandClassName = null;
	private int operatorID = 0;
	protected int actionType = -1;
	 private String remoteHostAddress ="";
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
}
