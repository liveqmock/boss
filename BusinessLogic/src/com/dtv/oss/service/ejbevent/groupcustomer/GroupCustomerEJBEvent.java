package com.dtv.oss.service.ejbevent.groupcustomer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.NewCustAccountInfoDTO;
import com.dtv.oss.dto.NewCustomerInfoDTO;

import com.dtv.oss.dto.ContractDTO;
import com.dtv.oss.dto.wrap.groupcustomer.GroupCustomerWrap;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class GroupCustomerEJBEvent extends AbstractEJBEvent {
	/** ************start******************* */
	//市场信息
	private ArrayList marketInfoList;
	// 受理单
	private CustServiceInteractionDTO csiDto;
	
	//新客户信息
	private NewCustomerInfoDTO newCustomerInfoDTO=null;
	
	//新客户帐户信息
	private NewCustAccountInfoDTO newCustAccountInfoDTO=null;
	
	//客户
	private CustomerDTO customerDTO=null;

	//合同
	private ContractDTO contractDTO = null;

	//集团客户帐户
	private AccountDTO accountDTO=null;
	
	//集团客户帐户地址
	private AddressDTO AccountAddressDTO=null;
	
	//子客户批量导入,文件路径
	private String filePath=null;
	
	//客户地址
	private AddressDTO addressDTO=null;

	//客户信息封装
	private GroupCustomerWrap groupCustomerWrap = null;
	
	private Collection groupCustomerWrapList=null;

	// private Hashtable deviceTable;
	private HashMap deviceTable;

	// 是否真实提交
	private boolean doPost;

	// 费用列表
	private Collection feeList;

	// 付费列表
	private Collection paymentList =new ArrayList();
	
	private Collection csiPrePaymentDeductionList =new ArrayList(); //collection of FT DTO

	// 合同号
	private String contractNO;
	
	//集团客户ID,用在子客户开户时,
	private int customerID;
	
	//帐户ID,用在子客户开户时
	private int accountID;

	// 产品属性
	private Map productPropertyMap;

	// 电话号码
	private String phoneNo;

	private int itemID;
	
	private Collection contractToPackageIDCol = null;

	private int actionType;
	
	private String openSourceType;
	
	//产品包列表
	private java.util.Collection csiPackageArray;
	//优惠列表
	private java.util.Collection csiCampaignArray;
	
	public static final int GROUPCUSTOMEROPEN = 4;

	public static final int GROUPCUSTOMEROPENFEE = 5;

	public static final int GROUPCUSTOMEROPENCONFIRM = 6;

	public static final int CONTRACT_CREATE = 2;

	public static final int CONTRACT_MODIFY = 3;

	public static final int GROUPCUSTOMERBATCHIMPORT = 1;
	public static final int GROUPCUSTOMERBATCHIMPORTFEE = 7;
	public static final int GROUPCUSTOMERBATCHIMPORTCONFIRM = 8;
	public static final int GROUPTOSINGLEFEE = 9;
	public static final int GROUPTOSINGLECONFIRM = 10;

	public static final int OPENCHILDCUST_GETDEVICE = 21;

	public static final int OPENCHILDCUST_GETFEE = 22;

	public static final int OPENCHILDCUST_CONFIRM = 23;
	
	public static final int CHILDCUST_CREATE=24;
	

	public CustServiceInteractionDTO getCsiDto() {
		return csiDto;
	}

	public void setCsiDto(CustServiceInteractionDTO csiDto) {
		this.csiDto = csiDto;
	}

	public HashMap getDeviceTable() {
		return deviceTable;
	}

	public void setDeviceTable(HashMap deviceTable) {
		this.deviceTable = deviceTable;
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

	public String getContractNO() {
		return contractNO;
	}

	public void setContractNO(String contractNO) {
		this.contractNO = contractNO;
	}

	public Map getProductPropertyMap() {
		return productPropertyMap;
	}

	public void setProductPropertyMap(Map productPropertyMap) {
		this.productPropertyMap = productPropertyMap;
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
	
	public String getOpenSourceType() {
		return openSourceType;
	}

	public void setOpenSourceType(String openSourceType) {
		this.openSourceType = openSourceType;
	}

	/** **************end***************** */



	/**
	 * @return Returns the contractToPackageIDCol.
	 */
	public Collection getContractToPackageIDCol() {
		return contractToPackageIDCol;
	}

	/**
	 * @param contractToPackageIDCol
	 *            The contractToPackageIDCol to set.
	 */
	public void setContractToPackageIDCol(Collection contractToPackageIDCol) {
		this.contractToPackageIDCol = contractToPackageIDCol;
	}

	public GroupCustomerEJBEvent() {
	}

	public GroupCustomerEJBEvent(int action, GroupCustomerWrap groupCustomerWrap) {
		actionType = action;
		this.groupCustomerWrap = groupCustomerWrap;
	}

	public GroupCustomerEJBEvent(int action) {
		actionType = action;
	}

	/**
	 * @return the actionType
	 */
	public int getActionType() {
		return actionType;
	}

	/**
	 * @param actionType
	 *            the actionType to set
	 */
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	/**
	 * @return the groupCustomerWrap
	 */
	public GroupCustomerWrap getGroupCustomerWrap() {
		return groupCustomerWrap;
	}

	/**
	 * @param groupCustomerWrap
	 *            the groupCustomerWrap to set
	 */
	public void setGroupCustomerWrap(GroupCustomerWrap groupCustomerWrap) {
		this.groupCustomerWrap = groupCustomerWrap;
	}


	/**
	 * @return the contractDTO
	 */
	public ContractDTO getContractDTO() {
		return contractDTO;
	}

	/**
	 * @param contractDTO
	 *            the contractDTO to set
	 */
	public void setContractDTO(ContractDTO contractDTO) {
		this.contractDTO = contractDTO;

	}

	/**
	 * @return the doPost
	 */
	public boolean isDoPost() {
		return doPost;
	}

	/**
	 * @param doPost
	 *            the doPost to set
	 */
	public void setDoPost(boolean doPost) {
		this.doPost = doPost;
	}

	/**
	 * @return the accountAddressDTO
	 */
	public AddressDTO getAccountAddressDTO() {
		return AccountAddressDTO;
	}

	/**
	 * @param accountAddressDTO the accountAddressDTO to set
	 */
	public void setAccountAddressDTO(AddressDTO accountAddressDTO) {
		AccountAddressDTO = accountAddressDTO;
	}

	/**
	 * @return the accountDTO
	 */
	public AccountDTO getAccountDTO() {
		return accountDTO;
	}

	/**
	 * @param accountDTO the accountDTO to set
	 */
	public void setAccountDTO(AccountDTO accountDTO) {
		this.accountDTO = accountDTO;
	}

	/**
	 * @return the addressDTO
	 */
	public AddressDTO getAddressDTO() {
		return addressDTO;
	}

	/**
	 * @param addressDTO the addressDTO to set
	 */
	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}

	/**
	 * @return the customerDTO
	 */
	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	/**
	 * @param customerDTO the customerDTO to set
	 */
	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	/**
	 * @return the newCustomerInfoDTO
	 */
	public NewCustomerInfoDTO getNewCustomerInfoDTO() {
		return newCustomerInfoDTO;
	}

	/**
	 * @param newCustomerInfoDTO the newCustomerInfoDTO to set
	 */
	public void setNewCustomerInfoDTO(NewCustomerInfoDTO newCustomerInfoDTO) {
		this.newCustomerInfoDTO = newCustomerInfoDTO;
	}

	/**
	 * @return the newCustAccountInfoDTO
	 */
	public NewCustAccountInfoDTO getNewCustAccountInfoDTO() {
		return newCustAccountInfoDTO;
	}

	/**
	 * @param newCustAccountInfoDTO the newCustAccountInfoDTO to set
	 */
	public void setNewCustAccountInfoDTO(NewCustAccountInfoDTO newCustAccountInfoDTO) {
		this.newCustAccountInfoDTO = newCustAccountInfoDTO;
	}

	/**
	 * @return the accountID
	 */
	public int getAccountID() {
		return accountID;
	}

	/**
	 * @param accountID the accountID to set
	 */
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	/**
	 * @return the customerID
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the groupCustomerWrapList
	 */
	public Collection getGroupCustomerWrapList() {
		return groupCustomerWrapList;
	}

	/**
	 * @param groupCustomerWrapList the groupCustomerWrapList to set
	 */
	public void setGroupCustomerWrapList(Collection groupCustomerWrapList) {
		this.groupCustomerWrapList = groupCustomerWrapList;
	}

	/**
	 * @return the marketInfoList
	 */
	public ArrayList getMarketInfoList() {
		return marketInfoList;
	}

	/**
	 * @param marketInfoList the marketInfoList to set
	 */
	public void setMarketInfoList(ArrayList marketInfoList) {
		this.marketInfoList = marketInfoList;
	}
	/**
	 * @return Returns the csiPackageArray.
	 */
	public java.util.Collection getCsiPackageArray() {
		return csiPackageArray;
	}
	/**
	 * @param csiPackageArray The csiPackageArray to set.
	 */
	public void setCsiPackageArray(java.util.Collection csiPackageArray) {
		this.csiPackageArray = csiPackageArray;
	}
	/**
	 * @return Returns the csiCampaignArray.
	 */
	public java.util.Collection getCsiCampaignArray() {
		return csiCampaignArray;
	}
	/**
	 * @param csiCampaignArray The csiCampaignArray to set.
	 */
	public void setCsiCampaignArray(java.util.Collection csiCampaignArray) {
		this.csiCampaignArray = csiCampaignArray;
	}
}
