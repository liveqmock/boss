/*
 * Created on 2005-12-6
 *
 * @author whq
 * 
 * 该类表示费用明细
 */
package com.dtv.oss.service.util.ImmediateFee;

public class ReturnFee implements java.io.Serializable{

  //结算账户ID
  private int accountID;
  
  //客户ID
  private int customerID;
  
  //业务账户ID
  private int serviceAccountID;
  
  //运营商产品ID
  private int productID;
  //费用类型代码
  private String feeType;
  //费用类型名称
  private String feeName;
  //账目类型代码
  private String acctItemTypeID;
  //账目类型名称
  private String acctItemTypeName;
  //费用金额
  private double value;
  //预存费是否允许退还 取值只有两种：'Y' / 'N'
  private String allowReturn;
  //强制预存标志
  private String forcedDepositFlag;
  
  private String rfbillingcycleflag;
  
  private int fspID;
  
  private int ccid;
  
  public void setCCID(int cid)
  {
    ccid = cid;
  }
  
  public int getCCID()
  {
    return ccid;
  }
  
  public void setRfBillingcycleFlag(String s)
  {
      rfbillingcycleflag = s;
  }
  public void setFspID(int i)
  {
  	fspID = i;
  }
  
  public int getFspID()
  {
  	return fspID;
  }
  
  public String getRfBillingCycleFlag()
  {
  	return rfbillingcycleflag;
  }
  
  //状态(W : 待付 , P : 已预付)
  private String status;

  public int getProductID(){
	return this.productID;
  }

  public String getFeeType(){
	return this.feeType;
  }

  public String getFeeName(){
	return this.feeName;
  }

  public String getAcctItemTypeID(){
	return this.acctItemTypeID;
  }

  public String getAcctItemTypeName(){
	return this.acctItemTypeName;
  }

  public double getValue(){
	return this.value;
  }

  public String getStatus(){
	return this.status;
  }

  public void setProductID(int productID){
	this.productID = productID;
  }

  public void setFeeType(String feeType){
	this.feeType = feeType;
  }

  public void setFeeName(String feeName){
	this.feeName = feeName;
  }

  public void setAcctItemTypeID(String acctItemTypeID){
	this.acctItemTypeID = acctItemTypeID;
  }

  public void setAcctItemTypeName(String acctItemTypeName){
	this.acctItemTypeName = acctItemTypeName;
  }

  public void setValue(double value){
	this.value = value;
  }

  public void setStatus(String status){
	this.status = status;
  }

  public ReturnFee(){
  }

  public ReturnFee(int productID, String feeType, String feeName, 
          		   String acctItemTypeID, String acctItemTypeName, 
          		   double value, String allowReturn, String status,int accountID,int customerID,int serviceAccountID){
	this.productID = productID;
	this.feeType = feeType;
	this.feeName = feeName;
	this.acctItemTypeID = acctItemTypeID;
	this.acctItemTypeName = acctItemTypeName;
	this.value = value;
	this.allowReturn = allowReturn;
	this.status = status;
	this.serviceAccountID = serviceAccountID;
	this.accountID = accountID;
	this.customerID = customerID;
	this.forcedDepositFlag = "N";
	this.fspID = 0;
  }

	public String getAllowReturn() {
	    return allowReturn;
	}
	public void setAllowReturn(String allowReturn) {
	    this.allowReturn = allowReturn;
	}
	public String getForcedDepositFlag() {
	    return forcedDepositFlag;
	}
	public void setForcedDepositFlag(String forcedDepositFlag) {
	    this.forcedDepositFlag = forcedDepositFlag;
	}
	
	
	public void setAccountID(int actID)
	{
		accountID = actID;
	}
	public void setCustomerID(int custID)
	{
		customerID = custID;
	}
	public void setServiceAccountID(int saID)
	{
		serviceAccountID = saID;
	}	
	public int getAccountID()
	{
		return accountID;
	}
	public int getCustomerID()
	{
		return customerID;
	}
	public int getServiceAccountID()
	{
		return serviceAccountID;
	}
		
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("productID="+productID);
        sb.append(",feeType="+feeType);
        sb.append(",feeName="+feeName);
        sb.append(",acctItemTypeID="+acctItemTypeID);
        sb.append(",acctItemTypeName="+acctItemTypeName);
        sb.append(",value="+value);
        sb.append(",forcedDepositFlag="+forcedDepositFlag);
        sb.append(",status="+status);
        sb.append(",rfbillingcycleflag=" + rfbillingcycleflag);
        sb.append(",fspID="+fspID);
        
        return sb.toString();
    }
}