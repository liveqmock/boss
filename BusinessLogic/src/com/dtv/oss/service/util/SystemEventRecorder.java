/*
 * Created on 2003-9-2
 *
 * @file SystemEventRecorder.java
 */
package com.dtv.oss.service.util;

import javax.ejb.CreateException;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.factory.*;
import com.dtv.oss.dto.*;
import com.dtv.oss.domain.*;

/**
 * @author Mac Wang
 * 
 *  
 */
public class SystemEventRecorder {
	//	------区域-------------------------开始--------------

	//------区域-------------------------结束--------------
	//为了方便起见，这部分的代码依保留，重构代码必须放到以上区域中，以外区域的代码将要删除
	public static final int SE_CUST_OPEN = 110;

	public static final int SE_CUSTINFO_MOD = 111;

	public static final int SE_CUSTPWD_MOD = 112;

	public static final int SE_CUST_CANCEL = 113;

	public static final int SE_CUST_WITHDRWAN = 114;

	public static final int SE_CUST_TRANSFER = 120;

	public static final int SE_CUST_MOVE = 121;

	public static final int SE_ACCOUNT_OPEN = 151;

	//	public static final int SE_ACCOUNT_PAUSE = 152;
	//	public static final int SE_ACCOUNT_RESUME = 153;
	public static final int SE_ACCOUNT_CANCEL = 154;

	public static final int SE_SERVICEACCOUNT_OPEN = 161;

	//modify by zhouxushun for pause serviceaccount , 2005-11-1
	public static final int SE_SERVICEACCOUNT_PAUSE = 162;

	public static final int SE_SERVICEACCOUNT_RESUME = 163;

	public static final int SE_SERVICEACCOUNT_CANCEL = 164;
	//业务账户过户系统时间
	public static final int SE_SERVICEACCOUNT_TRANSFER = 166;

	//	public static final int SE_SERVICEACCOUNT_TRANSFER = 165;

	public static final int SE_CUSTPROD_PURCHASE = 310;

	public static final int SE_CUSTPROD_ACTIVE = 311;

	public static final int SE_CUSTPROD_PAUSE = 312;

	public static final int SE_CUSTPROD_RESUME = 313;

	public static final int SE_CUSTPROD_CANCEL = 314;

	public static final int SE_CUSTPROD_UPGRADE = 315;

	public static final int SE_CUSTPROD_DOWNGRADE = 316;

	//	public static final int SE_CUSTPROD_TRANSFER = 320;
	public static final int SE_CUSTDEV_EXCHANGE = 321;

	public static final int SE_CUSTDEV_EXCHANGE_NEW = 322;

	public static final int SE_CUSTDEV_PURCHASE = 610;

	public static final int SE_CUSTDEV_LEASE = 611;

	public static final int SE_CUSTDEV_RETURN = 612;

	public static final int SE_DEV_EXCHANGE = 613;

	//	public static final int SE_CUSTDEV_MOVE = 614;
	//	public static final int SE_CUSTDEV_CANCEL = 615;
	
	public static final int DEVICE_PUT = 620; //设备入库事件

	public static final int SE_ORDER_CANCEL = 701;

	public static final int SE_ORDER_EXECUTE = 702;

	public static final int SE_ORDER_RETURN = 703;

	public static final int SE_CUSTFEE_ONINVOICE = 800; //根据帐单

	public static final int SE_CUSTFEE_PRESAVE = 801; //预存款

	public static final int PRODUCT_PROPERTY_UPDATE=331;
	public static final int SERVICE_CODE_UPDATE=171;
	public static final int SE_SERVICEACCOUNT_RENT = 168;
	/**
	 * //CA接口事件 public static final int SE_CA_RESENDEMM = 901; public static
	 * final int SE_CA_CLEARPWD = 902; public static final int SE_CA_UNMATCH =
	 * 903; //清除机卡配对 public static final int SE_CA_SENDMAIL = 904; public static
	 * final int SE_CA_SENDSPECIALCACOMMAND = 909;
	 * 
	 * =======
	 */
	//CA接口事件 (2006-2-23 modified by wesley)
	public static final int SE_CA_SERVICEACCOUNT_RESENDEMM = 900; //业务帐户重授权
	
	public static final int SE_CA_RESENDEMM = 901; //产品重授权

	public static final int SE_CA_CLEARPWD = 902; //清除密码

	public static final int SE_CA_MATCH = 903; //机卡配对

	public static final int SE_CA_SENDMAIL = 904; //发送EMAIL
	
	public static final int WALLET_CREATE = 906;
	
	public static final int WALLET_CHARGE = 907;

	public static final int SE_CA_SENDSPECIALCACOMMAND = 909; //发送特殊CA命令

	public static final int DEVICE_PREAUTH = 910; //智能卡预授权

	public static final int DEVICE_RELEASEPREAUTH = 911; //清除智能卡预授权

	public static final int SE_CA_UNMATCH = 912; //清除机卡配对

	//status
	public static final String SE_STATUS_CREATE = "C";

	public static final String SE_STATUS_PROCESS = "P";

	public SystemEventRecorder() {
	}

	public static SystemEvent AddEventWithAll(int eventclass, int customerid,
			int accountid, int serviceaccountid, int productid, int csiid,
			int psid, int scdeviceid, String scserialno, int stbdeviceid,
			String stbserialno, int oldscdeviceid, String oldscserialno,
			int oldstbdeviceid, String oldstbserialno, int oldproductid,
			int commandid, int operatorid, int invoiceno, String eventreason,
			String oldcustproductstatus, String status)
			throws HomeFactoryException, javax.ejb.CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventclass);
		dto.setCustomerID(customerid);
		dto.setAccountID(accountid);
		dto.setServiceAccountID(serviceaccountid);
		dto.setProductID(productid);
		dto.setCsiID(csiid);
		dto.setPsID(psid);
		dto.setScDeviceID(scdeviceid);
		dto.setScSerialNo(scserialno);
		dto.setStbDeviceID(stbdeviceid);
		dto.setStbSerialNo(stbserialno);
		dto.setOldScDeviceID(oldscdeviceid);
		dto.setOldScSerialNo(oldscserialno);
		dto.setOldStbDviceID(oldstbdeviceid);
		dto.setOldStbSerialNo(oldstbserialno);
		dto.setOldProductID(oldproductid);
		dto.setCommandID(commandid);
		dto.setOperatorID(operatorid);
		dto.setInvoiceNo(invoiceno);
		dto.setEventReason(eventreason);
		dto.setOldCustProductStatus(oldcustproductstatus);
		dto.setStatus(status);

		return createSystemEvent(dto);
	}

	private static SystemEvent createSystemEvent(SystemEventDTO dto)
			throws HomeFactoryException, CreateException {
		SystemEventHome systemEventHome = (SystemEventHome) HomeLocater
				.getSystemEventHome();
		return systemEventHome.create(dto);
	}

	public static SystemEvent AddEvent4Customer(int eventclass, int customerid,
			int accountid, int serviceaccountid, int csiid, int operatorid,
			String status) throws HomeFactoryException,
			javax.ejb.CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventclass);
		dto.setCustomerID(customerid);
		dto.setAccountID(accountid);
		dto.setServiceAccountID(serviceaccountid);
		dto.setCsiID(csiid);
		dto.setOperatorID(operatorid);
		dto.setStatus(status);

		return createSystemEvent(dto);
	}

	public static SystemEvent AddEvent4Invoice(int eventclass, int customerid,
			int accountid, int csiid, int operatorid, int invoiceno,
			String status) throws HomeFactoryException,
			javax.ejb.CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventclass);
		dto.setCustomerID(customerid);
		dto.setAccountID(accountid);
		dto.setCsiID(csiid);
		dto.setOperatorID(operatorid);
		dto.setInvoiceNo(invoiceno);
		dto.setStatus(status);

		return createSystemEvent(dto);
	}

	/**
	 * 软件产品升降级事件（2006-2-17 shuwei 新增）
	 * 
	 * @param eventclass
	 *            int
	 * @param customerid
	 *            int
	 * @param accountid
	 *            int
	 * @param serviceaccountid
	 *            int
	 * @param productid
	 *            int
	 * @param oldProductid
	 *            int
	 * @param psid
	 *            int
	 * @param scdeviceid
	 *            int
	 * @param scserialno
	 *            String
	 * @param stbdeviceid
	 *            int
	 * @param stbserialno
	 *            String
	 * @param operatorid
	 *            int
	 * @param oldCustProductStatus
	 *            String
	 * @param status
	 *            String
	 * @return SystemEvent
	 * @throws HomeFactoryException
	 * @throws CreateException
	 */
	public static SystemEvent AddEvent4Product(int eventclass, int customerid,
			int accountid, int serviceaccountid, int productid,
			int oldProductid, int psid, int scdeviceid, String scserialno,
			int stbdeviceid, String stbserialno, int operatorid,
			String oldCustProductStatus, String status)
			throws HomeFactoryException, javax.ejb.CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventclass);
		dto.setCustomerID(customerid);
		dto.setAccountID(accountid);
		dto.setServiceAccountID(serviceaccountid);
		dto.setProductID(productid);
		dto.setOldProductID(oldProductid);
		dto.setPsID(psid);
		dto.setScDeviceID(scdeviceid);
		dto.setScSerialNo(scserialno);
		dto.setStbDeviceID(stbdeviceid);
		dto.setStbSerialNo(stbserialno);
		dto.setOperatorID(operatorid);
		dto.setOldCustProductStatus(oldCustProductStatus);
		dto.setStatus(status);

		return createSystemEvent(dto);
	}
//	更换产品
	public static SystemEvent AddEvent4SwapProduct(int eventclass, int customerid,
			int accountid, int serviceaccountid, int productid, int csiid,
			int psid, int scdeviceid, String scserialno, int stbdeviceid,
			String stbserialno,int oldScdeviceid,String oldScserialno, int oldStbdeviceid,
			String oldStbserialno,int operatorid, String status)
			throws HomeFactoryException, javax.ejb.CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventclass);
		dto.setCustomerID(customerid);
		dto.setAccountID(accountid);
		dto.setServiceAccountID(serviceaccountid);
		dto.setProductID(productid);
		dto.setCsiID(csiid);
		dto.setPsID(psid);
		dto.setScDeviceID(scdeviceid);
		dto.setScSerialNo(scserialno);
		dto.setStbDeviceID(stbdeviceid);
		dto.setStbSerialNo(stbserialno);
		dto.setOperatorID(operatorid);
		dto.setStatus(status);
		dto.setOldScDeviceID(oldScdeviceid);
		dto.setOldScSerialNo(oldScserialno);
		dto.setOldStbDviceID(oldStbdeviceid);
		dto.setOldStbSerialNo(oldStbserialno);

		return createSystemEvent(dto);
	}
	//购买产品，硬件产品重授权
	public static SystemEvent AddEvent4Product(int eventclass, int customerid,
			int accountid, int serviceaccountid, int productid, int csiid,
			int psid, int scdeviceid, String scserialno, int stbdeviceid,
			String stbserialno, int operatorid, String status)
			throws HomeFactoryException, javax.ejb.CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventclass);
		dto.setCustomerID(customerid);
		dto.setAccountID(accountid);
		dto.setServiceAccountID(serviceaccountid);
		dto.setProductID(productid);
		dto.setCsiID(csiid);
		dto.setPsID(psid);
		dto.setScDeviceID(scdeviceid);
		dto.setScSerialNo(scserialno);
		dto.setStbDeviceID(stbdeviceid);
		dto.setStbSerialNo(stbserialno);
		dto.setOperatorID(operatorid);
		dto.setStatus(status);

		return createSystemEvent(dto);
	}

	//产品事件--不需要设备序列号
	public static SystemEvent AddEvent4Product(int eventclass, int customerid,
			int accountid, int serviceaccountid, int productid, int csiid,
			int psid, int oldproductid, int operatorid,
			String oldcustproductstatus, String status)
			throws HomeFactoryException, javax.ejb.CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventclass);
		dto.setCustomerID(customerid);
		dto.setAccountID(accountid);
		dto.setServiceAccountID(serviceaccountid);
		dto.setProductID(productid);
		dto.setCsiID(csiid);
		dto.setPsID(psid);

		dto.setOldProductID(oldproductid);
		dto.setOperatorID(operatorid);
		dto.setOldCustProductStatus(oldcustproductstatus);
		dto.setStatus(status);

		return createSystemEvent(dto);
	}

	//	产品事件--需要设备序列号
	public static SystemEvent AddEvent4Product(int eventclass, int customerid,
			int accountid, int serviceaccountid, int productid, int csiid,
			int psid, int scdeviceid, String scserialno, int stbdeviceid,
			String stbserialno, int oldproductid, int operatorid,
			String oldcustproductstatus, String status)
			throws HomeFactoryException, javax.ejb.CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventclass);
		dto.setCustomerID(customerid);
		dto.setAccountID(accountid);
		dto.setServiceAccountID(serviceaccountid);
		dto.setProductID(productid);
		dto.setCsiID(csiid);
		dto.setPsID(psid);
		dto.setScDeviceID(scdeviceid);
		dto.setScSerialNo(scserialno);
		dto.setStbDeviceID(stbdeviceid);
		dto.setStbSerialNo(stbserialno);
		dto.setOldProductID(oldproductid);
		dto.setOperatorID(operatorid);
		dto.setOldCustProductStatus(oldcustproductstatus);
		dto.setStatus(status);

		return createSystemEvent(dto);
	}

	/**
	 * 业务帐户事件 add by zhouxushun , 2005-11-1
	 * 
	 * @param eventClass
	 * @param customerID
	 * @param accountid
	 * @param serviceAccountID
	 * @param csiid
	 * @param linkToDevice1
	 * @param linkToDevice1SerialNo
	 * @param linkToDevice2
	 * @param linkToDevice2SerialNo
	 * @param operatorid
	 * @param status
	 * @return @throws
	 *         HomeFactoryException
	 * @throws CreateException
	 */
	public static SystemEvent addEvent4ServiceAccount(int eventClass,
			int customerID, int accountID, int serviceAccountID, int csiID,
			int linkToDevice1, String linkToDevice1SerialNo, int linkToDevice2,
			String linkToDevice2SerialNo, int operatorID, String status,String oldSAstatus)
			throws HomeFactoryException, CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventClass);
		dto.setCustomerID(customerID);
		dto.setAccountID(accountID);
		dto.setServiceAccountID(serviceAccountID);
		dto.setCsiID(csiID);
		dto.setStbDeviceID(linkToDevice1);
		dto.setStbSerialNo(linkToDevice1SerialNo);
		dto.setScDeviceID(linkToDevice2);
		dto.setScSerialNo(linkToDevice2SerialNo);
		dto.setOperatorID(operatorID);
		dto.setStatus(status);
		dto.setOldCustProductStatus(oldSAstatus);
		return createSystemEvent(dto);
	}

	/**
	 * 业务帐户事件（重发授权、机卡解配对、消除密码） add by shuwei , 2006-2-17
	 * 
	 * @param eventClass
	 * @param customerID
	 * @param accountid
	 * @param serviceAccountID
	 * @param productID
	 * @param psID
	 * @param linkToDevice1
	 * @param linkToDevice1SerialNo
	 * @param linkToDevice2
	 * @param linkToDevice2SerialNo
	 * @param operatorid
	 * @param status
	 * @return @throws
	 *         HomeFactoryException
	 * @throws CreateException
	 */
	public static SystemEvent addEvent4ServiceAccount(int eventClass,
			int customerID, int accountID, int serviceAccountID, int productID,
			int psID, int linkToDevice1, String linkToDevice1SerialNo,
			int linkToDevice2, String linkToDevice2SerialNo, int operatorID,
			String content, String status) throws HomeFactoryException,
			CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventClass);
		dto.setCustomerID(customerID);
		dto.setAccountID(accountID);
		dto.setServiceAccountID(serviceAccountID);
		dto.setProductID(productID);
		dto.setPsID(psID);
		dto.setStbDeviceID(linkToDevice1);
		dto.setStbSerialNo(linkToDevice1SerialNo);
		dto.setScDeviceID(linkToDevice2);
		dto.setScSerialNo(linkToDevice2SerialNo);
		dto.setOperatorID(operatorID);
		dto.setStatus(status);
		dto.setRecordData(content);
		return createSystemEvent(dto);
	}

	/**
	 * 智能卡与授权事件
	 * 
	 * add by chenjiang , 2006-2-28
	 * 
	 * @param eventClass
	 * 
	 * @param productID
	 * @param SCDeviceID
	 * @param SCSerialNo
	 * 
	 * @param operatorid
	 * @param status
	 * @return @throws
	 *         HomeFactoryException
	 * @throws CreateException
	 */
	public static SystemEvent addEvent4DeviceMatchPreauth(int eventClass,
			int productID, int SCDeviceID, String SCSerialNo, int STBDeviceID,
			String STBSerialNO, int operatorID, String status)
			throws HomeFactoryException, CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventClass);

		dto.setProductID(productID);

		dto.setScDeviceID(SCDeviceID);
		dto.setScSerialNo(SCSerialNo);
		dto.setStbDeviceID(STBDeviceID);
		dto.setStbSerialNo(STBSerialNO);
		dto.setOperatorID(operatorID);
		dto.setStatus(status);
		LogUtility.log(SystemEventRecorder.class, LogLevel.DEBUG, "系统事件dto为"
				+ dto);
		return createSystemEvent(dto);
	}

	public static SystemEvent addEvent4DevicePreauth(int eventClass,
			int productID, int SCDeviceID, String SCSerialNo,

			int operatorID, String status) throws HomeFactoryException,
			CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventClass);

		dto.setProductID(productID);

		dto.setScDeviceID(SCDeviceID);
		dto.setScSerialNo(SCSerialNo);

		dto.setOperatorID(operatorID);
		dto.setStatus(status);
		LogUtility.log(SystemEventRecorder.class, LogLevel.DEBUG, "系统事件dto为"
				+ dto);
		return createSystemEvent(dto);
	}

	/**
	 * 机卡解除配对事件
	 * 
	 * add by chenjiang , 2006-3-1
	 * 
	 * @param eventClass
	 * 
	 * @param productID
	 * @param SCDeviceID
	 * @param SCSerialNo
	 * 
	 * @param operatorid
	 * @param status
	 * @return @throws
	 *         HomeFactoryException
	 * @throws CreateException
	 */
	public static SystemEvent addEvent4DeviceUnmatch(int eventClass,
			int SCDeviceID, String SCSerialNo, int STBDeviceID,
			String STBSerialNo, int operatorID, String status)
			throws HomeFactoryException, CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventClass);
		dto.setScDeviceID(SCDeviceID);
		dto.setScSerialNo(SCSerialNo);
		dto.setStbDeviceID(STBDeviceID);
		dto.setStbSerialNo(STBSerialNo);
		dto.setOperatorID(operatorID);
		dto.setStatus(status);
		dto.setProductID(CsrBusinessUtility.getProductIDByDeviceID(SCDeviceID));
		LogUtility.log(SystemEventRecorder.class, LogLevel.DEBUG, "系统事件dto为"
				+ dto);
		return createSystemEvent(dto);
	}

	/**
	 * 清除智能卡所有预授权
	 * 
	 * add by chenjiang , 2006-3-1
	 * 
	 * @param eventClass
	 * 
	 * @param productID
	 * @param SCDeviceID
	 * @param SCSerialNo
	 * 
	 * @param operatorid
	 * @param status
	 * @return @throws
	 *         HomeFactoryException
	 * @throws CreateException
	 */
	public static SystemEvent addEvent4ReleaseDevicePreauth(int eventClass,
			int SCDeviceID, String SCSerialNo,

			int operatorID, String status) throws HomeFactoryException,
			CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventClass);
		dto.setScDeviceID(SCDeviceID);
		dto.setScSerialNo(SCSerialNo);
		dto.setOperatorID(operatorID);
		dto.setStatus(status);
		dto.setProductID(CsrBusinessUtility.getProductIDByDeviceID(SCDeviceID));
		LogUtility.log(SystemEventRecorder.class, LogLevel.DEBUG, "系统事件dto为"
				+ dto);
		return createSystemEvent(dto);
	}

	//机卡配对事件
	public static SystemEvent addEvent4DeviceMatch(int eventClass,
			int customerID, int accountID, int serviceAccountID,
			int SCDeviceID, String SCSerialNo, int STBDeviceID,
			String STBSerialNo, int operatorID, int productId,String status)
			throws HomeFactoryException, CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventClass);
		dto.setCustomerID(customerID);
		dto.setAccountID(accountID);
		dto.setServiceAccountID(serviceAccountID);
		dto.setScDeviceID(SCDeviceID);
		dto.setScSerialNo(SCSerialNo);
		dto.setStbDeviceID(STBDeviceID);
		dto.setStbSerialNo(STBSerialNo);
		dto.setProductID(productId);
		dto.setOperatorID(operatorID);
		dto.setStatus(status);
		LogUtility.log(SystemEventRecorder.class, LogLevel.DEBUG, "系统事件dto为"
				+ dto);
		return createSystemEvent(dto);
	}
	//设备入库事件
	public static SystemEvent addEvent4DevicePut(int eventClass,
			int SCDeviceID, String SCSerialNo,
			int STBDeviceID, String STBSerialNo,

			int operatorID, String status) throws HomeFactoryException,
			CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventClass);
		dto.setProductID(0);
		dto.setScDeviceID(SCDeviceID);
		dto.setScSerialNo(SCSerialNo);
		dto.setStbDeviceID(STBDeviceID);
		dto.setStbSerialNo(STBSerialNo);

		dto.setOperatorID(operatorID);
		dto.setStatus(status);
		LogUtility.log(SystemEventRecorder.class, LogLevel.DEBUG, "系统事件dto为"
				+ dto);
		return createSystemEvent(dto);
	}
	
	public static SystemEvent addEvent4CpConfigedProperty(SystemEventDTO dto)
			throws HomeFactoryException, CreateException {
	
		LogUtility.log(SystemEventRecorder.class, LogLevel.DEBUG, "系统事件dto为"
				+ dto);
		return createSystemEvent(dto);
	}
	
	public static SystemEvent addEvent4CAWallet(int eventClass, int customerId,
			int serviceAccountId, int productID, int psid, int scDeviceID,
			String SCSerialNo, int STBDeviceId, String STBSerialNo,
			String scWalletCode, int point,int operatorId) throws HomeFactoryException,
			CreateException {
		SystemEventDTO dto = new SystemEventDTO();
		dto.setEventClass(eventClass);
		dto.setCustomerID(customerId);
		dto.setServiceAccountID(serviceAccountId);
		dto.setProductID(productID);
		dto.setPsID(psid);
		dto.setScDeviceID(scDeviceID);
		dto.setScSerialNo(SCSerialNo);
		dto.setStbDeviceID(STBDeviceId);
		dto.setStbSerialNo(STBSerialNo);
		dto.setCaWalletCode(scWalletCode);
		dto.setAmount(point);
		dto.setStatus(SE_STATUS_CREATE);
		dto.setOperatorID(operatorId);
		LogUtility.log(SystemEventRecorder.class,LogLevel.DEBUG,"■■开始钱包创建事件■"+dto); 
		return createSystemEvent(dto);
	}
	public static void main(String[] args) {
	}
}