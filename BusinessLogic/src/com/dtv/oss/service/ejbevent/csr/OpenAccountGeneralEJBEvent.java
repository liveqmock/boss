/*
 * Created on 2005-10-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.ejbevent.csr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.CustomerDTO;

/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OpenAccountGeneralEJBEvent extends CsrAbstractEJbevent {
	public static final String KEY_BUYNUM="KEY_BUYNUM";
	public static final String KEY_BUYINDEX="KEY_BUYINDEX";
	public static final String KEY_BUNDLE="KEY_BUNDLE";
	public static final String KEY_MPACKAGE="KEY_MPACKAGE";
	public static final String KEY_SPACKAGE="KEY_SPACKAGE";
	public static final String KEY_CAMPAIGN="KEY_CAMPAIGN";
	public static final String KEY_DEVICES="KEY_DEVICES";
	public static final String KEY_SERIALNO="KEY_SERIALNO";
	public static final String KEY_DEVICECLASS="KEY_DEVICECLASS";
	public static final String KEY_PHONE="KEY_PHONE";
	public static final String KEY_PROPERTYS="KEY_PROPERTYS";
	
	//产品包列表
	private java.util.Collection csiPackageArray; 
	//优惠列表
	private java.util.Collection csiCampaignArray;
	//费用列表 （collection of FT DTO）
	private Collection csiFeeList;		
	//付费列表（collection of  FT DTO）
	private Collection csiPaymentList =new ArrayList();	
	//预存抵扣列表
	private Collection csiPrePaymentDeductionList =new ArrayList();
	//设备硬件信息（serialno 做key，deviceclass做value）
	//private Hashtable deviceTable;
	private HashMap deviceTable;
	//团购详细信息
	private String detailNo;
	//业务账户ID
	private int serviceAccountID;
	//是否创建工单
	private boolean createJobCard;
	//客户信息
	private CustomerDTO custDto;
	//账户信息
	private AccountDTO acctDto;	
	//虚拟电话号码
	private String phoneNo;
	private int itemID;
	
	//预约时老的电话号码：
	private String serviceCodeList;
	/**
	 * @return Returns the itemID.
	 */
	public int getItemID() {
		return itemID;
	}
	/**
	 * @param itemID The itemID to set.
	 */
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	/**
	 * @return Returns the phoneNo.
	 */
	public String getPhoneNo() {
		return phoneNo;
	}
	/**
	 * @param phoneNo The phoneNo to set.
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public OpenAccountGeneralEJBEvent(int actionType) {
		this.actionType = actionType;
	}
	public OpenAccountGeneralEJBEvent() {
	}
	public boolean getCreateJobCard() {
		return createJobCard;
	}
	public void setCreateJobCard(boolean createJobCard) {
		this.createJobCard = createJobCard;
	}
	
	public int getServiceAccountID() {
		return serviceAccountID;
	}
	public void setServiceAccountID(int serviceAccountID) {
		this.serviceAccountID = serviceAccountID;
	}
	
	public HashMap getDeviceTable() {
		return deviceTable;
	}
	public void setDeviceMap(HashMap deviceTable) {
		this.deviceTable = deviceTable;
	}
	public java.util.Collection getCsiCampaignArray() {
		return csiCampaignArray;
	}
	public void setCsiCampaignArray(java.util.Collection csiCampaignArray) {
		this.csiCampaignArray = csiCampaignArray;
	}

	public java.util.Collection getCsiPackageArray() {
		return csiPackageArray;
	}
	public void setCsiPackageArray(java.util.Collection csiPackageArray) {
		this.csiPackageArray = csiPackageArray;
	}
	/**
	 * @return
	 */
	public Collection getCsiFeeList() {
		return csiFeeList;
	}

	/**
	 * @return
	 */
	public Collection getCsiPaymentList() {
		return csiPaymentList;
	}

	public Collection getCsiPrePaymentDeductionList() {
		return csiPrePaymentDeductionList;
	}
	
	/**
	 * @param collection
	 */
	public void setCsiFeeList(Collection collection) {
		csiFeeList = collection;
	}

	/**
	 * @param collection
	 */
	public void setCsiPaymentList(Collection collection) {
		csiPaymentList = collection;
	}

	public void setCsiPrePaymentDeductionList(Collection csiPrePaymentDeductionList) {
		this.csiPrePaymentDeductionList = csiPrePaymentDeductionList;
	}
	/**
	 * @return
	 */
	public String getDetailNo() {
		return detailNo;
	}

	/**
	 * @param string
	 */
	public void setDetailNo(String string) {
		detailNo = string;
	}
	/**
	 * @return
	 */
	public AccountDTO getAcctDto() {
		return acctDto;
	}

	/**
	 * @return
	 */
	public CustomerDTO getCustDto() {
		return custDto;
	}

	/**
	 * @param accountDTO
	 */
	public void setAcctDto(AccountDTO accountDTO) {
		acctDto = accountDTO;
	}

	/**
	 * @param customerDTO
	 */
	public void setCustDto(CustomerDTO customerDTO) {
		custDto = customerDTO;
	}
	
	public String getServiceCodeList() {
		return serviceCodeList;
	}
	
	public void setServiceCodeList(String serviceCodeList) {
		this.serviceCodeList = serviceCodeList;
	}
}
