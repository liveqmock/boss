/*
 * Created on 2004-8-22
 * 
 * @author Mac Wang
 */
package com.dtv.oss.service.ejbevent.csr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.dtv.oss.dto.*;
import com.dtv.oss.service.ejbevent.*;

public class CustomerProductEJBEvent extends AbstractEJBEvent {
	private int usedMonth;
	private boolean isSendBackDevice;
	
	private CustServiceInteractionDTO csiDto;

	private java.util.Collection csiPackageArray;

	private java.util.Collection csiCampaignArray;

	private Collection csiFeeList; //collection of FT DTO

	private Collection csiPaymentList =new ArrayList(); //collection of FT DTO
	
	private Collection csiPrePaymentDeductionList =new ArrayList(); //collection of FT DTO

	private java.util.Collection colPsid; //collection of psid in t_customerproduct

	private Collection colCsiCustProductInfo; //collection of CsiCustProductInfoDTO
	
	private Collection colServiceAccountInfo;

	private int referScID; //智能卡ID

	private int referStbID; //机顶盒ID

	private boolean doPost; //true:实际提交,false:不提交，只做预判
	
	private String groupBargainDetailNo; //团购促销 的detail number
	
	private int customerID;
	
	private int accountID;
	
	private int saID;
	
	private int targetSaID;
	
	private String oldSerialNo;
	
	private HashMap deviceMap;
	
	private CustomerBillingRuleDTO customerBillingRuleDTO;
	
	private CatvTerminalDTO catvTerminalDTO;
	
    //客户化计费规则
    private Map customerBillingRuleMap;
	
	public CustomerBillingRuleDTO getCustomerBillingRuleDTO() {
		return customerBillingRuleDTO;
	}
	public void setCustomerBillingRuleDTO(
			CustomerBillingRuleDTO customerBillingRuleDTO) {
		this.customerBillingRuleDTO = customerBillingRuleDTO;
	}
	public CatvTerminalDTO getCatvTerminalDTO() {
		return catvTerminalDTO;
	}
	public void setCatvTerminalDTO(
			CatvTerminalDTO catvTerminalDTO) {
		this.catvTerminalDTO = catvTerminalDTO;
	}
	/**
	 * @return Returns the newSeralNo.
	 */
	public String getNewSeralNo() {
		return newSeralNo;
	}
	/**
	 * @param newSeralNo The newSeralNo to set.
	 */
	public void setNewSeralNo(String newSeralNo) {
		this.newSeralNo = newSeralNo;
	}
	/**
	 * @return Returns the oldSerialNo.
	 */
	public String getOldSerialNo() {
		return oldSerialNo;
	}
	/**
	 * @param oldSerialNo The oldSerialNo to set.
	 */
	public void setOldSerialNo(String oldSerialNo) {
		this.oldSerialNo = oldSerialNo;
	}
	private String newSeralNo;
	
	//客户产品ID
	Collection psidList;
	
	//新的促销ID
	 int newCampaignID;
	 
	 //升/降级的目标产品
	 int productID;
	 
	 //升/降级的目标产品所对应当设备
	 int deviceID;
	 
	 //产品参数列表
	 Collection cpParamList;
	 
	 //产品属性
	 Map productPropertyMap;
	 
	/**
	 * @return Returns the groupBargainDetailNo.
	 */
	public String getGroupBargainDetailNo() {
		return groupBargainDetailNo;
	}

	/**
	 * @param groupBargainDetailNo
	 *            The groupBargainDetailNo to set.
	 */
	public void setGroupBargainDetailNo(String groupBargainDetailNo) {
		this.groupBargainDetailNo = groupBargainDetailNo;
	}

	private CustomerProductDTO customerProductDTO;

	//for add customerproduct
	public CustomerProductEJBEvent(int actionType,
			CustServiceInteractionDTO csiDto, 
			java.util.Collection csiPackageArray,
			java.util.Collection csiCampaignArray,
			java.util.Collection csiFeeList,
			java.util.Collection csiPaymentList, int customerID,int accountID, int saID,boolean doPost) {
		this.actionType = actionType;
		this.csiDto = csiDto;
		this.csiPackageArray = csiPackageArray;
		this.csiCampaignArray = csiCampaignArray;
		this.csiFeeList = csiFeeList;
		this.csiPaymentList = csiPaymentList;
		this.customerID=customerID;
		this.accountID=accountID;
		this.saID=saID;
		this.doPost = doPost;
	}

	//for alter customerproduct
	public CustomerProductEJBEvent(int actionType,
			CustServiceInteractionDTO csiDto, Collection colCsiCustProductInfo,
			java.util.Collection csiCampaignArray,
			java.util.Collection csiFeeList,
			java.util.Collection csiPaymentList, boolean doPost) {
		this.actionType = actionType;
		this.csiDto = csiDto;
		this.colCsiCustProductInfo = colCsiCustProductInfo;
		this.csiCampaignArray = csiCampaignArray;
		this.csiFeeList = csiFeeList;
		this.csiPaymentList = csiPaymentList;
		this.doPost = doPost;
	}

	//for reactive customerproduct
	public CustomerProductEJBEvent(int actionType, Collection colPsid) {
		this.actionType = actionType;
		this.colPsid = colPsid;
	}

	public CustomerProductEJBEvent(int actionType,
			CustomerProductDTO customerProductDTO) {
		this.actionType = actionType;
		this.customerProductDTO = customerProductDTO;
	}

	//	for add customerproduct including hardware
	public CustomerProductEJBEvent(int actionType,
			CustServiceInteractionDTO csiDto, 
			java.util.Collection csiPackageArray,
			java.util.Collection csiCampaignArray,
			java.util.Collection csiFeeList,
			java.util.Collection csiPaymentList, int referScID, int referStbID,
			boolean doPost) {
		this.actionType = actionType;
		this.csiDto = csiDto;
		this.csiPackageArray = csiPackageArray;
		this.csiCampaignArray = csiCampaignArray;
		this.csiFeeList = csiFeeList;
		this.csiPaymentList = csiPaymentList;
		this.referScID = referScID;
		this.referStbID = referStbID;
		this.doPost = doPost;
	}

	//	for suspend customerproduct
	public CustomerProductEJBEvent(int actionType,
			CustServiceInteractionDTO csiDto, Collection csiFeeList,
			Collection csiPaymentList, java.util.Collection colPsid,
			boolean doPost) {
		this.actionType = actionType;
		this.csiDto = csiDto;
		this.csiFeeList = csiFeeList;
		this.csiPaymentList = csiPaymentList;
		this.colPsid = colPsid;
		this.doPost = doPost;
	}

	/**
	 * 
	 */
	public CustomerProductEJBEvent() {
		
		// TODO Auto-generated constructor stub
	}

	public CustServiceInteractionDTO getCsiDto() {
		return csiDto;
	}

	public void setCsiDto(CustServiceInteractionDTO csiDto) {
		this.csiDto = csiDto;
	}

	public java.util.Collection getCsiCampaignArray() {
		return csiCampaignArray;
	}

	public void setCsiCampaignArray(java.util.Collection csiCampaignArray) {
		this.csiCampaignArray = csiCampaignArray;
	}

	public boolean isDoPost() {
		return doPost;
	}

	public void setDoPost(boolean doPost) {
		this.doPost = doPost;
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
	public CustomerProductDTO getCustomerProductDTO() {
		return this.customerProductDTO;
	}

	/**
	 * @param CustomerProductDTO
	 */
	public void setCustomerProductDTO(CustomerProductDTO customerProductDTO) {
		this.customerProductDTO = customerProductDTO;
	}

	public java.util.Collection getColPsid() {
		return colPsid;
	}

	public void setColPsid(java.util.Collection colPsid) {
		this.colPsid = colPsid;
	}

	/**
	 * @return
	 */
	public Collection getColCsiCustProductInfo() {
		return colCsiCustProductInfo;
	}

	/**
	 * @param collection
	 */
	public void setColCsiCustProductInfo(Collection collection) {
		colCsiCustProductInfo = collection;
	}
	
	public int getTargetSaID() {
		return targetSaID;
	}
	
	public void setTargetSaID(int targetSaID) {
		this.targetSaID = targetSaID;
	}
	/**
	 * @return Returns the referScID.
	 */
	public int getReferScID() {
		return referScID;
	}

	/**
	 * @param referScID
	 *            The referScID to set.
	 */
	public void setReferScID(int referScID) {
		this.referScID = referScID;
	}

	/**
	 * @return Returns the referStbID.
	 */
	public int getReferStbID() {
		return referStbID;
	}

	/**
	 * @param referStbID
	 *            The referStbID to set.
	 */
	public void setReferStbID(int referStbID) {
		this.referStbID = referStbID;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getSaID() {
		return saID;
	}

	public void setSaID(int saID) {
		this.saID = saID;
	}

	public Collection getCpParamList() {
		return cpParamList;
	}

	public void setCpParamList(Collection cpParamList) {
		this.cpParamList = cpParamList;
	}

	public int getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}

	public int getNewCampaignID() {
		return newCampaignID;
	}

	public void setNewCampaignID(int newCampaignID) {
		this.newCampaignID = newCampaignID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public Collection getPsidList() {
		return psidList;
	}

	public void setPsidList(Collection psidList) {
		this.psidList = psidList;
	}
	/**
	 * @return Returns the productPropertyMap.
	 */
	public Map getProductPropertyMap() {
		if(productPropertyMap==null)
			productPropertyMap=new LinkedHashMap();
		return productPropertyMap;
	}
	/**
	 * @param productPropertyMap The productPropertyMap to set.
	 */
	public void setProductPropertyMap(Map productPropertyMap) {
		this.productPropertyMap = productPropertyMap;
	}
	public boolean isSendBackDevice() {
		return isSendBackDevice;
	}
	public void setSendBackDevice(boolean isSendBackDevice) {
		this.isSendBackDevice = isSendBackDevice;
	}
	public HashMap getDeviceMap() {
		return deviceMap;
	}
	public void setDeviceMap(HashMap deviceMap) {
		this.deviceMap = deviceMap;
	}
	/**
	 * @return the customerBillingRuleMap
	 */
	public Map getCustomerBillingRuleMap() {
		return customerBillingRuleMap;
	}
	/**
	 * @param customerBillingRuleMap the customerBillingRuleMap to set
	 */
	public void setCustomerBillingRuleMap(Map customerBillingRuleMap) {
		this.customerBillingRuleMap = customerBillingRuleMap;
	}
	public Collection getColServiceAccountInfo() {
		return colServiceAccountInfo;
	}
	public void setColServiceAccountInfo(Collection colServiceAccountInfo) {
		this.colServiceAccountInfo = colServiceAccountInfo;
	}
	
	public int getUsedMonth() {
		return usedMonth;
	}

	public void setUsedMonth(int usedMonth) {
		this.usedMonth = usedMonth;
	}

}