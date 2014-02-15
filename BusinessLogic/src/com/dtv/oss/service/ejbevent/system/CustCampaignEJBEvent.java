/*
 * Created on 2004-8-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.ejbevent.system;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerCampaignDTO;
import com.dtv.oss.dto.CPCampaignMapDTO;

public class CustCampaignEJBEvent extends SystemEJBEvent{
	private CustomerCampaignDTO  campaignDTO =null;
	private CPCampaignMapDTO cpCampaignMapDTO=null;
	private int newCampaignID;
	private int ccid;
	private int dateLength;
	private boolean confirmPost;
	private String rfBillingCycleFlag;
	private Collection feeList;		
	private Collection paymentList =new ArrayList();
	private Collection csiPrePaymentDeductionList =new ArrayList();
	private Map addProductMap;
	private Map productPropertyMap;
	private ArrayList cancelProductList;
	private Timestamp newBundleTransferDate;
	private Timestamp newBundleDateTo;
	private HashMap terminalMap;
	private String phoneNo;
	private int itemID;
	private String isReturnDevice;
	private double deviceFee;
	private CustServiceInteractionDTO csiDto;

	//产品包列表 用于协议用户的续费
	private java.util.Collection csiPackageArray; 
	//套餐列表   用于协议用户的续费
	private java.util.Collection csiCampaignArray;
	//续费业务账户（用逗号隔开）   用于协议用户的续费
	private String saId_indexs;
	private Collection saIDList;

	/**
	 * @return the saIDList
	 */
	public Collection getSaIDList() {
		return saIDList;
	}

	/**
	 * @param saIDList the saIDList to set
	 */
	public void setSaIDList(Collection saIDList) {
		this.saIDList = saIDList;
	}

	public CustServiceInteractionDTO getCsiDto() {
		return csiDto;
	}

	public void setCsiDto(CustServiceInteractionDTO csiDto) {
		this.csiDto = csiDto;
	}

	public HashMap getTerminalMap() {
		return terminalMap;
	}

	public void setTerminalMap(HashMap terminalMap) {
		this.terminalMap = terminalMap;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public int getCcid() {
		return ccid;
	}

	public void setCcid(int ccid) {
		this.ccid = ccid;
	}

	public Timestamp getNewBundleDateTo() {
		return newBundleDateTo;
	}

	public void setNewBundleDateTo(Timestamp newBundleDateTo) {
		this.newBundleDateTo = newBundleDateTo;
	}

	public Timestamp getNewBundleTransferDate() {
		return newBundleTransferDate;
	}

	public void setNewBundleTransferDate(Timestamp newBundleTransferDate) {
		this.newBundleTransferDate = newBundleTransferDate;
	}

	public Map getAddProductMap() {
		return addProductMap;
	}

	public void setAddProductMap(Map addProductList) {
		this.addProductMap = addProductList;
	}

	public ArrayList getCancelProductList() {
		return cancelProductList;
	}

	public void setCancelProductList(ArrayList cancelProductList) {
		this.cancelProductList = cancelProductList;
	}

	public int getNewCampaignID() {
		return newCampaignID;
	}

	public String getRfBillingCycleFlag() {
		return rfBillingCycleFlag;
	}

	public void setRfBillingCycleFlag(String rfBillingCycleFlag) {
		this.rfBillingCycleFlag = rfBillingCycleFlag;
	}

	public CustCampaignEJBEvent(){
		super();
	}
	public CustCampaignEJBEvent(int actionType){
		super();
		this.actionType = actionType;
	}
	
	public CustCampaignEJBEvent(int actionType,CustomerCampaignDTO  campaignDTO,CPCampaignMapDTO cpCampaignMapDTO) {
		super();
		this.actionType = actionType;
		this.campaignDTO=campaignDTO;
		this.cpCampaignMapDTO=cpCampaignMapDTO;
	}
	public CustCampaignEJBEvent(int actionType,CustomerCampaignDTO  campaignDTO,CPCampaignMapDTO cpCampaignMapDTO,boolean confirmPost) {
		super();
		this.actionType = actionType;
		this.campaignDTO=campaignDTO;
		this.cpCampaignMapDTO=cpCampaignMapDTO;
		this.confirmPost=confirmPost;
	}
	public CustomerCampaignDTO getCampaignDTO() {
		return campaignDTO;
	}

	public void setCampaignDTO(CustomerCampaignDTO campaignDTO) {
		this.campaignDTO = campaignDTO;
	}

	public CPCampaignMapDTO getCpCampaignMapDTO() {
		return cpCampaignMapDTO;
	}

	public void setCpCampaignMapDTO(CPCampaignMapDTO cpCampaignMapDTO) {
		this.cpCampaignMapDTO = cpCampaignMapDTO;
	}

	public int getDateLength() {
		return dateLength;
	}

	public void setDateLength(int dateLength) {
		this.dateLength = dateLength;
	}

	public void setNewCampaignID(int newCampaignID) {
		this.newCampaignID = newCampaignID;
	}

	public boolean isConfirmPost() {
		return confirmPost;
	}

	public void setConfirmPost(boolean confirmPost) {
		this.confirmPost = confirmPost;
	}

	public Collection getFeeList() {
		return feeList;
	}

	public void setFeeList(Collection feeList) {
		this.feeList = feeList;
	}

	public Collection getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(Collection paymentList) {
		this.paymentList = paymentList;
	}

	public Collection getCsiPrePaymentDeductionList() {
		return csiPrePaymentDeductionList;
	}

	public void setCsiPrePaymentDeductionList(Collection csiPrePaymentDeductionList) {
		this.csiPrePaymentDeductionList = csiPrePaymentDeductionList;
	}

	public Map getProductPropertyMap() {
		return productPropertyMap;
	}

	public void setProductPropertyMap(Map productPropertyMap) {
		this.productPropertyMap = productPropertyMap;
	}

	public double getDeviceFee() {
		return deviceFee;
	}

	public void setDeviceFee(double deviceFee) {
		this.deviceFee = deviceFee;
	}

	public String getIsReturnDevice() {
		return isReturnDevice;
	}

	public void setIsReturnDevice(String isReturnDevice) {
		this.isReturnDevice = isReturnDevice;
	}

	public java.util.Collection getCsiPackageArray() {
		return csiPackageArray;
	}

	public void setCsiPackageArray(java.util.Collection csiPackageArray) {
		this.csiPackageArray = csiPackageArray;
	}

	public java.util.Collection getCsiCampaignArray() {
		return csiCampaignArray;
	}

	public void setCsiCampaignArray(java.util.Collection csiCampaignArray) {
		this.csiCampaignArray = csiCampaignArray;
	}

	public String getSaId_indexs() {
		return saId_indexs;
	}

	public void setSaId_indexs(String saId_indexs) {
		this.saId_indexs = saId_indexs;
	}

	
}
