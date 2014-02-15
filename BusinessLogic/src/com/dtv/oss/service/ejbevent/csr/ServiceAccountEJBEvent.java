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
 * ҵ���ʻ�EJBEvent author ��zhouxushun date : 2005-11-14 description:
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
    //�ͻ����Ʒѹ���
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

	// ҵ���ʻ�
	private int serviceAccountID;
	// ҵ���ʻ��������
	private String serviceAccountCode;
	// ҵ���ʻ��������
	private String oldServiceAccountCode;
	// �ͻ�DTO
	private CustomerDTO customerDTO;
	// �ʻ�DTO
	private AccountDTO accountDTO;
	// �ͻ���ַ
	private AddressDTO custAddrDTO;
	// �ʻ���ַ
	private AddressDTO accAddrDTO;
	// �����б�
	private Collection feeList;
	// �����б�
	private Collection paymentList = new ArrayList();
	// �Ƿ���ʵ�ύ
	private boolean doPost;
	// ��Ʒ���б�
	private java.util.Collection csiPackageArray;
	// �Ż��б�
	private java.util.Collection csiCampaignArray;

	private Collection csiPrePaymentDeductionList = new ArrayList(); // collection
																		// of FT
																		// DTO

	// Ѻ����Ϣ
	private double forcedDeposit;
	// �豸Ӳ����Ϣ��serialno ��key��deviceclass��value��

	// �ָ���Ʒ����ҵ���ʻ��ָ�,�����Integer��ʽ������
	private Collection ColPsid;

	// private Hashtable deviceTable;
	private HashMap deviceTable;
	// �Ƿ񴴽�����
	private boolean createJobCard;
	// ������Ϣ����
	private String content;
	// ����
	CustServiceInteractionDTO csiDto;
	// �ͻ�ID
	int customerID;
	// �ʻ�ID
	int accountID;
	// �¿ͻ�ID
	int newCustomerID;
	// ���ʻ�ID
	int newAccountID;

	// ҵ���ʻ���������
	private String tranferSAType;

	// �ͻ��г���Ϣ
	private Collection custMarketInfoList;

	// �绰����
	private String phoneNo;

	private int itemID;

	// ��Ʒ����
	private Map productPropertyMap;

	// ԤԼʱ�ϵĵ绰���룺
	private String serviceCodeList;

	// ����ʱ�ɹ�����ת����id
	private int nextOrgID;

	// �˻��豸���
	private String isSendBack;

	// Ԥװǰ̨��װ�ķ���
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
