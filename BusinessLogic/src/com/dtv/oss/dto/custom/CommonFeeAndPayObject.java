/**
 * 
 */
package com.dtv.oss.dto.custom;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author 240910y
 * 这个对象是用来存储与费用处理的有关对象的,考虑到接口的稳定性,特生成这个对象
 */
public class CommonFeeAndPayObject implements Serializable {
	/**
	 * 
	 */
	public CommonFeeAndPayObject() {
		// TODO Auto-generated constructor stub
	}
	private boolean mustPay;
	private Collection spCache;
	/*以下几个变量用来为一次性费用计费条件封装参数用的  开始*/
	private int accountID;
	private int customerID;
	private String accountName;
	private int serviceAccountID;
	private String custType;
	private String acctType;
	private int groupBargainID;
	private String contractNo;
	private int destProductID;//产品升降级后的产品
	private double deposit;//押金,用户开户,增机,购买产品
	/*以下几个变量用来为一次性费用计费条件封装参数用的  开始*/
	
	//以下为模拟业务条件参数
	private String catvTermType; 		// 终端类型
	private String cableType; 			//光缆类型
	private String biDirectionFlag;		//交互式标志
	private int orgPortNum; 			//源端口数
	private int destPortNum;			//目标端口数
	  
	private String userType;			//业务帐户类型
	private String installationType;	//上门安装，自安装
	private String installOnOrgPort;    //新增终端(N) / 新增端口(A) / 恢复开通(O) 
	
	public CommonFeeAndPayObject(boolean mustPay) {
		// TODO Auto-generated constructor stub
		this.mustPay=mustPay;
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
	 * @return the acctType
	 */
	public String getAcctType() {
		return acctType;
	}
	/**
	 * @param acctType the acctType to set
	 */
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	/**
	 * @return the contractNo
	 */
	public String getContractNo() {
		return contractNo;
	}
	/**
	 * @param contractNo the contractNo to set
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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
	 * @return the custType
	 */
	public String getCustType() {
		return custType;
	}
	/**
	 * @param custType the custType to set
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}
	/**
	 * @return the groupBargainID
	 */
	public int getGroupBargainID() {
		return groupBargainID;
	}
	/**
	 * @param groupBargainID the groupBargainID to set
	 */
	public void setGroupBargainID(int groupBargainID) {
		this.groupBargainID = groupBargainID;
	}
	/**
	 * @return the serviceAccountID
	 */
	public int getServiceAccountID() {
		return serviceAccountID;
	}
	/**
	 * @param serviceAccountID the serviceAccountID to set
	 */
	public void setServiceAccountID(int serviceAccountID) {
		this.serviceAccountID = serviceAccountID;
	}
	/**
	 * @return the destProductID
	 */
	public int getDestProductID() {
		return destProductID;
	}
	/**
	 * @param destProductID the destProductID to set
	 */
	public void setDestProductID(int destProductID) {
		this.destProductID = destProductID;
	}
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @return the deposit
	 */
	public double getDeposit() {
		return deposit;
	}
	/**
	 * @param deposit the deposit to set
	 */
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	/**
	 * @return the mustPay
	 */
	public boolean isMustPay() {
		return mustPay;
	}
	/**
	 * @param mustPay the mustPay to set
	 */
	public void setMustPay(boolean mustPay) {
		this.mustPay = mustPay;
	}
	/**
	 * @return the spCache
	 */
	public Collection getSpCache() {
		return spCache;
	}
	/**
	 * @param spCache the spCache to set
	 */
	public void setSpCache(Collection spCache) {
		this.spCache = spCache;
	}
	public String getBiDirectionFlag() {
		return biDirectionFlag;
	}
	public void setBiDirectionFlag(String biDirectionFlag) {
		this.biDirectionFlag = biDirectionFlag;
	}
	public String getCableType() {
		return cableType;
	}
	public void setCableType(String cableType) {
		this.cableType = cableType;
	}
	public String getCatvTermType() {
		return catvTermType;
	}
	public void setCatvTermType(String catvTermType) {
		this.catvTermType = catvTermType;
	}
	public int getDestPortNum() {
		return destPortNum;
	}
	public void setDestPortNum(int destPortNum) {
		this.destPortNum = destPortNum;
	}
	public int getOrgPortNum() {
		return orgPortNum;
	}
	public void setOrgPortNum(int orgPortNum) {
		this.orgPortNum = orgPortNum;
	}
	public String getInstallationType() {
		return installationType;
	}
	public void setInstallationType(String installationType) {
		this.installationType = installationType;
	}
	public String getInstallOnOrgPort() {
		return installOnOrgPort;
	}
	public void setInstallOnOrgPort(String installOnOrgPort) {
		this.installOnOrgPort = installOnOrgPort;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

}
