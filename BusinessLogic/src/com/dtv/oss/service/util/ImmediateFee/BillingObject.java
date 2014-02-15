/*
 * Created on 2007-03-04
 *
 * @author liudi
 *  
 * 计费对象定义
 * 每个BillingObject,代表了一组有关联关系的计费对象。
 * 
 */
package com.dtv.oss.service.util.ImmediateFee;

import java.util.Collection;
import java.util.HashMap;

public class BillingObject implements java.io.Serializable{

  public String boType;	//'G'计算团购已支付信息，default 'F'：计算费用信息

  public String sHasPackage; // 'Y' 有Package需要计算一次性费用,'N' 没有Package.
 
  private int accountID;    //帐户号
  private int customerID;   //客户号
  private int serviceAccountID; //业务帐户号
  private String custType;  //客户类型
  private String acctType;  //帐户类型
  private HashMap package2ProductMap; //产品包对应的产品
  private Collection campaignCol; //促销 + 套餐
  private int groupBargainID; //团购号
  private String contractNo; //合同号
  private int destProductID;//产品升降级后的产品
  private int orgPackageID;	//原产品价格
  private int destPackageID;//目标产品包ID
  private String catvTermType; // 终端类型
  private String cableType; //光缆类型
  private String biDirectionFlag;//交互式标志
  private int orgPortNum; //源端口数
  private int destPortNum;//目标端口数

  private String userType;
  private String installationType;
  private String installOnOrgPort;
  
  
  public BillingObject(){
	boType = "F";
    sHasPackage ="Y";
    accountID = 0;
    customerID = 0;
    serviceAccountID = 0;
    custType = "";
    acctType = "";
    contractNo = "";
    groupBargainID = 0;
    package2ProductMap = null;
    campaignCol = null; 
    destProductID = 0;
    orgPackageID = 0;
    destPackageID = 0;
    orgPortNum = 0;
    destPortNum = 0;
    installOnOrgPort = "";
    installationType = "";
  }

  public BillingObject(
                       int accountID,
                       int customerID,
                       int serviceAccountID,
                       String custType,
                       String acctType,
                       HashMap package2ProductMap,
                       Collection CampaignCol,
          	           int groupBargainID,
          	           String contractNo){
	boType = "F";
    sHasPackage ="Y";
    accountID = 0;
    customerID = 0;
    serviceAccountID = 0;
    custType = "";
    acctType = "";
    contractNo = "";
    groupBargainID = 0;
    package2ProductMap = null;
    campaignCol = null;
    destProductID = 0;   	       	
    this.package2ProductMap = package2ProductMap;
    this.custType = custType;
    this.acctType = acctType;
    this.groupBargainID = groupBargainID;
    this.contractNo = contractNo;
    if(custType == null) custType = "";
    if(acctType == null) acctType = "";
  }
  public void setBOType(String sType)
  {
	this.boType = sType;
  }
  public String getBOType()
  {
    return boType;
  }
  public void setDestPackageID(int destPackageID)
  {
     this.destPackageID = destPackageID;
  }
  
  public int getDestPackageID()
  {
		return destPackageID;
  }

  public void setOrgPackageID(int orgPackageID)
  {
     this.orgPackageID = orgPackageID;
  }
  
  public int getOrgPackageID()
  {
		return orgPackageID;
  }
  public void setDestProductID(int destProductID)
  {
  	this.destProductID = destProductID;
  }
  
  public int getDestProductID()
  {
  	return destProductID;
  }
  public String toString() 
  {
      StringBuffer buf = new StringBuffer(18);
	  buf.append("boType=" + boType);
	  buf.append(",sHasPackage=" + sHasPackage);
      buf.append(",customerID="+ customerID);
      buf.append(",custType="+ custType);
      buf.append(",accountID="+ accountID);
      buf.append(",acctType="+ acctType);
	  buf.append(",userType="+ userType);
	  buf.append(",catvTermType="+ catvTermType);
	  buf.append(",cableType="+ cableType);
	  buf.append(",biDirectionFlag="+ biDirectionFlag);
 	  buf.append(",orgPortNum="+ orgPortNum);
	  buf.append(",destPortNum="+ destPortNum);
	  buf.append(",installOnOrgPort="+ installOnOrgPort);
	  buf.append(",installationType="+ installationType); 
      buf.append(",serviceAccountID="+ serviceAccountID);
      buf.append(",groupBargainID="+ groupBargainID);
      buf.append(",contractNo="+ contractNo);
      buf.append(",destProductID="+ destProductID);
      buf.append(",campaignCol="+ campaignCol);
      buf.append(",package2ProductMap="+ package2ProductMap);
      return buf.toString();
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
 * @return the campaignCol
 */
public Collection getCampaignCol() {
	return campaignCol;
}

/**
 * @param campaignCol the campaignCol to set
 */
public void setCampaignCol(Collection campaignCol) {
	this.campaignCol = campaignCol;
}

/**
 * @return the sHasPackage
 */
public String getSHasPackage() {
	return sHasPackage;
}

/**
 * @param hasPackage the sHasPackage to set
 */
public void setSHasPackage(String hasPackage) {
	sHasPackage = hasPackage;
}

/**
 * @return the package2ProductMap
 */
public HashMap getPackage2ProductMap() {
	return package2ProductMap;
}

/**
 * @param package2ProductMap the package2ProductMap to set
 */
public void setPackage2ProductMap(HashMap package2ProductMap) {
	this.package2ProductMap = package2ProductMap;
}
public void setCATVTermType(String catvType)
{
	this.catvTermType = catvType;
}
public String getCATVType()
{
	return catvTermType;
}
public void setCableType(String cableType)
{
	this.cableType = cableType;
}
public String getCableType()
{
	return cableType;
}
public void setBiDirectionFlag(String biFlag)
{
	this.biDirectionFlag = biFlag;
}
public String getBiDirectionFlag()
{
	return biDirectionFlag;
}
public void setOrgPortNum(int orgNum)
{
	this.orgPortNum = orgNum;
}
public int getOrgPortNum()
{
	return orgPortNum;
}
public void setDestPortNum(int destPortNum)
{
	this.destPortNum = destPortNum;
}
public int getDestPortNum()
{
	return destPortNum;
}

public void setUserType(String ut)
{
    this.userType = ut;
}
public String getUserType()
{
    return userType;
}

public void setInstallationType(String installationType)
{
    this.installationType = installationType;
}

public String getInstallationType()
{
    return installationType;
}

public void setInstallOnOrgPort(String installOnOrgPort)
{
    this.installOnOrgPort = installOnOrgPort;
}

public String getInstallOnOrgPort()
{
    return installOnOrgPort;
}

}