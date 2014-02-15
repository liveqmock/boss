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
	public static final int OPERATOR_LOGIN 							= 100; //操作员 登录
	public static final int OPERATOR_LOGOUT 						= 101; //操作员 登出
	public static final int OPERATOR_CHANGE_PWD					= 102; //操作员 修改密码
	
	public static final int CAMPAIGN_CREATE							= 201; //优惠活动 新建
	public static final int CAMPAIGN_DELETE							= 202; //优惠活动 删除
	public static final int CAMPAIGN_UPDATE							= 203; //优惠活动 更新
	
	public static final int GROUPBARGAIN_CREATE					= 301; //团购 新建
	public static final int GROUPBARGAIN_DELETE					= 302; //团购 删除
	public static final int GROUPBARGAIN_UPDATE					= 303; //团购 更新
	public static final int GROUPBARGAIN_SALE						= 304; //团购 更新

	public static final int GROUPBARGAIN_DETAIL_DELETE	= 401; //团购 删除
	public static final int GROUPBARGAIN_DETAIL_UPDATE	= 402; //团购 更新	
	

	public static final int CUST_CAMAPAIGN_MODIFY=601;				//促销维护
	public static final int CUST_CAMAPAIGN_CANCEL=602;				//取消客户促销
	public static final int CUST_CAMAPAIGN_MANUAL_GRANT=603;		//人工授予促销活动
	public static final int CUST_BUNDLE_MODIFY=604;					//套餐维护
	public static final int CUST_BUNDLE_DELAY=605;					//套餐延期
	public static final int CUST_BUNDLE_CANCEL=606;					//取消客户套餐
	public static final int CUST_BUNDLE_TRANSFER=607;				//套餐转换
	public static final int CUST_BUNDLE_STOP=608;					//停止客户套餐
	public static final int CUST_CAMAPAIGN_DELAY=609;				//促销延迟
	public static final int CUST_CAMAPAIGN_TRANSFER=610;			//促销转换
	public static final int CUST_BUNDLE_PREPAYMENT=611;				//套餐预付
    public static final int PROTOCOL_BUNDLE_PREPAYMENT =622;        //协议用户批量续费
	
	
	
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
