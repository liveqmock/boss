package com.dtv.oss.service.ejbevent.csr;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.dto.SystemEventDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

/**
 * 业务帐户EJBEvent author ：zhouxushun date : 2005-11-14 description:
 * 
 * @author 250713z
 * 
 */
public class ServiceAccountEJBEvent extends AbstractEJBEvent {
	private int usedMonth;
	private SystemEventDTO sysEventDto;
	private JobCardDTO jobCardDto;
	private AddressDTO addressDTO;
	private String[] serviceAcctIdStr ;
	
	private ArrayList buyDeviceList;
	private ArrayList buyProductList;
    //客户化计费规则
    private Map customerBillingRuleMap;
    
	public ArrayList getBuyDeviceList() {
		return buyDeviceList;
	}

	public void setBuyDeviceList(ArrayList buyDeviceList) {
		this.buyDeviceList = buyDeviceList;
	}

	public ArrayList getBuyProductList() {
		return buyProductList;
	}

	public void setBuyProductList(ArrayList buyProductList) {
		this.buyProductList = buyProductList;
	}

	/**
	 * @return Returns the newAccountID.
	 */
	public int getNewAccountID() {
		return newAccountID;
	}

	/**
	 * @param newAccountID
	 *            The newAccountID to set.
	 */
	public void setNewAccountID(int newAccountID) {
		this.newAccountID = newAccountID;
	}

	/**
	 * @return Returns the newCustomerID.
	 */
	public int getNewCustomerID() {
		return newCustomerID;
	}

	/**
	 * @param newCustomerID
	 *            The newCustomerID to set.
	 */
	public void setNewCustomerID(int newCustomerID) {
		this.newCustomerID = newCustomerID;
	}

	// 业务帐户
	private int serviceAccountID;
	// 业务帐户服务号码
	private String serviceAccountCode;
	// 业务帐户服务号码
	private String oldServiceAccountCode;
	// 客户DTO
	private CustomerDTO customerDTO;
	// 帐户DTO
	private AccountDTO accountDTO;
	// 客户地址
	private AddressDTO custAddrDTO;
	// 帐户地址
	private AddressDTO accAddrDTO;
	// 费用列表
	private Collection feeList;
	// 付费列表
	private Collection paymentList = new ArrayList();
	// 是否真实提交
	private boolean doPost;
	// 产品包列表
	private java.util.Collection csiPackageArray;
	// 优惠列表
	private java.util.Collection csiCampaignArray;

	private Collection csiPrePaymentDeductionList = new ArrayList(); // collection
																		// of FT
																		// DTO

	// 押金信息
	private double forcedDeposit;
	// 设备硬件信息（serialno 做key，deviceclass做value）

	// 恢复产品，用业务帐户恢复,存放以Integer格式的数据
	private Collection ColPsid;

	// private Hashtable deviceTable;
	private HashMap deviceTable;
	// 是否创建工单
	private boolean createJobCard;
	// 发送消息内容
	private String content;
	// 受理单
	CustServiceInteractionDTO csiDto;
	// 客户ID
	int customerID;
	// 帐户ID
	int accountID;
	// 新客户ID
	int newCustomerID;
	// 新帐户ID
	int newAccountID;

	// 业务帐户过户类型
	private String tranferSAType;

	// 客户市场信息
	private Collection custMarketInfoList;

	// 电话号码
	private String phoneNo;

	private int itemID;

	// 产品属性
	private Map productPropertyMap;

	// 预约时老的电话号码：
	private String serviceCodeList;

	// 增机时派工的流转部门id
	private int nextOrgID;

	// 退还设备与否
	private String isSendBack;

	// 预装前台封装的费用
	private Collection deviceFeeList;

	public Collection getDeviceFeeList() {
		return deviceFeeList;
	}

	public void setDeviceFeeList(Collection deviceFeeList) {
		this.deviceFeeList = deviceFeeList;
	}

	/**
	 * @return Returns the productPropertyMap.
	 */
	public Map getProductPropertyMap() {
		return productPropertyMap;
	}

	/**
	 * @param productPropertyMap
	 *            The productPropertyMap to set.
	 */
	public void setProductPropertyMap(Map productPropertyMap) {
		this.productPropertyMap = productPropertyMap;
	}

	/**
	 * @return Returns the itemID.
	 */
	public int getItemID() {
		return itemID;
	}

	/**
	 * @param itemID
	 *            The itemID to set.
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
	 * @param phoneNo
	 *            The phoneNo to set.
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Collection getCustMarketInfoList() {
		return custMarketInfoList;
	}

	public void setCustMarketInfoList(Collection custMarketInfoList) {
		this.custMarketInfoList = custMarketInfoList;
	}

	public String getTranferSAType() {
		return tranferSAType;
	}

	public void setTranferSAType(String tranferSAType) {
		this.tranferSAType = tranferSAType;
	}

	public ServiceAccountEJBEvent(int actionType, int serviceAccountID,
			CustomerDTO customerDTO, AccountDTO accountDTO,
			AddressDTO custAddrDTO, AddressDTO accAddrDTO, Collection feeList,
			Collection paymentList, int customerID, int accountID,
			CustServiceInteractionDTO csiDTO, boolean doPost) {
		this.actionType = actionType;
		this.serviceAccountID = serviceAccountID;
		this.customerDTO = customerDTO;
		this.accountDTO = accountDTO;
		this.custAddrDTO = custAddrDTO;
		this.accAddrDTO = accAddrDTO;
		this.feeList = feeList;
		this.paymentList = paymentList;
		this.csiDto = csiDTO;
		this.customerID = customerID;
		this.accountID = accountID;
		this.doPost = doPost;
	}

	public ServiceAccountEJBEvent() {
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public AddressDTO getAccAddrDTO() {
		return accAddrDTO;
	}

	public void setAccAddrDTO(AddressDTO accAddrDTO) {
		this.accAddrDTO = accAddrDTO;
	}

	public AddressDTO getCustAddrDTO() {
		return custAddrDTO;
	}

	public void setCustAddrDTO(AddressDTO custAddrDTO) {
		this.custAddrDTO = custAddrDTO;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public CustServiceInteractionDTO getCsiDto() {
		return csiDto;
	}

	public void setCsiDto(CustServiceInteractionDTO csiDto) {
		this.csiDto = csiDto;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public ServiceAccountEJBEvent(int actionType) {
		this.actionType = actionType;
	}

	public boolean isDoPost() {
		return doPost;
	}

	public void setDoPost(boolean doPost) {
		this.doPost = doPost;
	}

	public void setAccountDTO(AccountDTO accountDTO) {
		this.accountDTO = accountDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public void setFeeList(Collection feeList) {
		this.feeList = feeList;
	}

	public void setPaymentList(Collection paymentList) {
		this.paymentList = paymentList;
	}

	public void setServiceAccountID(int serviceAccountID) {
		this.serviceAccountID = serviceAccountID;
	}

	public AccountDTO getAccountDTO() {
		return accountDTO;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public Collection getFeeList() {
		return feeList;
	}

	public Collection getPaymentList() {
		return paymentList;
	}

	public int getServiceAccountID() {
		return serviceAccountID;
	}

	/**
	 * @return Returns the createJobCard.
	 */
	// public boolean isCreateJobCard() {
	// return createJobCard;
	// }
	/**
	 * @param createJobCard
	 *            The createJobCard to set.
	 */
	// public void setCreateJobCard(boolean createJobCard) {
	// this.createJobCard = createJobCard;
	// }
	/**
	 * @return Returns the csiCampaignArray.
	 */
	public java.util.Collection getCsiCampaignArray() {
		return csiCampaignArray;
	}

	/**
	 * @param csiCampaignArray
	 *            The csiCampaignArray to set.
	 */
	public void setCsiCampaignArray(java.util.Collection csiCampaignArray) {
		this.csiCampaignArray = csiCampaignArray;
	}

	/**
	 * @return Returns the csiPackageArray.
	 */
	public java.util.Collection getCsiPackageArray() {
		return csiPackageArray;
	}

	/**
	 * @param csiPackageArray
	 *            The csiPackageArray to set.
	 */
	public void setCsiPackageArray(java.util.Collection csiPackageArray) {
		this.csiPackageArray = csiPackageArray;
	}

	/**
	 * @return Returns the deviceTable.
	 */
	public HashMap getDeviceTable() {
		return deviceTable;
	}

	/**
	 * @param deviceTable
	 *            The deviceTable to set.
	 */
	public void setDeviceTable(HashMap deviceTable) {
		this.deviceTable = deviceTable;
	}

	/**
	 * @return Returns the serviceAccountCode.
	 */
	public String getServiceAccountCode() {
		return serviceAccountCode;
	}

	/**
	 * @param serviceAccountCode
	 *            The serviceAccountCode to set.
	 */
	public void setServiceAccountCode(String serviceAccountCode) {
		this.serviceAccountCode = serviceAccountCode;
	}

	/**
	 * @return Returns the oldServiceAccountCode.
	 */
	public String getOldServiceAccountCode() {
		return oldServiceAccountCode;
	}

	/**
	 * @param oldServiceAccountCode
	 *            The oldServiceAccountCode to set.
	 */
	public void setOldServiceAccountCode(String oldServiceAccountCode) {
		this.oldServiceAccountCode = oldServiceAccountCode;
	}

	/**
	 * @return Returns the sysEventDto.
	 */
	public SystemEventDTO getSysEventDto() {
		return sysEventDto;
	}

	/**
	 * @param sysEventDto
	 *            The sysEventDto to set.
	 */
	public void setSysEventDto(SystemEventDTO sysEventDto) {
		this.sysEventDto = sysEventDto;
	}

	public Collection getColPsid() {
		return ColPsid;
	}

	public void setColPsid(Collection colPsid) {
		ColPsid = colPsid;
	}

	/**
	 * @return the csiPrePaymentDeductionList
	 */
	public Collection getCsiPrePaymentDeductionList() {
		return csiPrePaymentDeductionList;
	}

	/**
	 * @param csiPrePaymentDeductionList
	 *            the csiPrePaymentDeductionList to set
	 */
	public void setCsiPrePaymentDeductionList(
			Collection csiPrePaymentDeductionList) {
		this.csiPrePaymentDeductionList = csiPrePaymentDeductionList;
	}

	public double getForcedDeposit() {
		return forcedDeposit;
	}

	public void setForcedDeposit(double forcedDeposit) {
		this.forcedDeposit = forcedDeposit;
	}

	public String getServiceCodeList() {
		return serviceCodeList;
	}

	public void setServiceCodeList(String serviceCodeList) {
		this.serviceCodeList = serviceCodeList;
	}

	public String getIsSendBack() {
		return isSendBack;
	}

	public void setIsSendBack(String isSendBack) {
		this.isSendBack = isSendBack;
	}

	/**
	 * @return Returns the nextOrgID.
	 */
	public int getNextOrgID() {
		return nextOrgID;
	}

	/**
	 * @param nextOrgID
	 *            The nextOrgID to set.
	 */
	public void setNextOrgID(int nextOrgID) {
		this.nextOrgID = nextOrgID;
	}

	public JobCardDTO getJobCardDto() {
		return jobCardDto;
	}

	public void setJobCardDto(JobCardDTO jcDto) {
		this.jobCardDto = jcDto;
	}

	/**
	 * @return the addressDTO
	 */
	public AddressDTO getAddressDTO() {
		return addressDTO;
	}

	/**
	 * @param addressDTO
	 *            the addressDTO to set
	 */
	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
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

	public int getUsedMonth() {
		return usedMonth;
	}

	public void setUsedMonth(int usedMonth) {
		this.usedMonth = usedMonth;
	}

	public String[] getServiceAcctIdStr() {
		return serviceAcctIdStr;
	}

	public void setServiceAcctIdStr(String[] serviceAcctIdStr) {
		this.serviceAcctIdStr = serviceAcctIdStr;
	}

	
}
