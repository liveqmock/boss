package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class SystemLogDTO implements ReflectionSupport, java.io.Serializable{
  private String operation;
  private String logDesc;
  private String logClass;
  private String logType;
  private int sequenceNo;
  private String machineName;
  private String operatorName;
  private String moduleName;
  private Timestamp createDateTime;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;

  private int customerID;
  private java.lang.String customerName;
  private int operatorID;
  private java.lang.String loginID;
  private int serviceAccountID;
  private int accountID;
  private int psID;
  private int orgID;

  public SystemLogDTO(){
  }

  public void setOrgID(int orgID){
	this.orgID = orgID;
	put("orgID");
  }

  public int getOrgID(){
	return orgID;
  }  
  
  public int getPsID(){
	return psID;
  }  
  public void setPsID(int psID){
	this.psID = psID;
	put("psID");
  }
  public void setOperation(String operation){
	this.operation = operation;
	put("operation");
  }

  public String getOperation(){
	return operation;
  }

  public void setLogDesc(String logDesc){
	this.logDesc = logDesc;
	put("logDesc");
  }

  public String getLogDesc(){
	return logDesc;
  }

  public void setLogClass(String logClass){
	this.logClass = logClass;
	put("logClass");
  }

  public String getLogClass(){
	return logClass;
  }

  public void setLogType(String logType){
	this.logType = logType;
	put("logType");
  }

  public String getLogType(){
	return logType;
  }

  public void setSequenceNo(int sequenceNo){
	this.sequenceNo = sequenceNo;
	//put("sequenceNo");
  }

  public int getSequenceNo(){
	return sequenceNo;
  }

  public void setMachineName(String machineName){
	this.machineName = machineName;
	put("machineName");
  }

  public String getMachineName(){
	return machineName;
  }

  public void setOperatorName(String operatorName){
	this.operatorName = operatorName;
	put("operatorName");
  }

  public String getOperatorName(){
	return operatorName;
  }

  public void setModuleName(String moduleName){
	this.moduleName = moduleName;
	put("moduleName");
  }

  public String getModuleName(){
	return moduleName;
  }

  public void setCreateDateTime(Timestamp createDateTime){
	this.createDateTime = createDateTime;
	put("createDateTime");
  }

  public Timestamp getCreateDateTime(){
	return createDateTime;
  }

  public void setDtCreate(Timestamp dtCreate){
	this.dtCreate = dtCreate;
  }

  public Timestamp getDtCreate(){
	return dtCreate;
  }

  public void setDtLastmod(Timestamp dtLastmod){
	this.dtLastmod = dtLastmod;
  }

  public Timestamp getDtLastmod(){
	return dtLastmod;
  }

  public int getCustomerID(){
	return customerID;
  }

  public java.lang.String getCustomerName(){
	return customerName;
  }

  public int getOperatorID(){
	return operatorID;
  }

  public java.lang.String getLoginID(){
	return loginID;
  }

  public void setCustomerID(int customerID){
	this.customerID = customerID;
	put("customerID");
  }

  public void setCustomerName(java.lang.String customerName){
	this.customerName = customerName;
	put("customerName");
  }

  public void setOperatorID(int operatorID){
	this.operatorID = operatorID;
	put("operatorID");
  }

  public void setLoginID(java.lang.String loginID){
	this.loginID = loginID;
	put("loginID");
  }
  public   void setServiceAccountID(int serviceAccountID){
        this.serviceAccountID = serviceAccountID;
	put("serviceAccountID");
  }
  public int getServiceAccountID(){
       return serviceAccountID;
 }

 public   void setAccountID(int  accountID){
       this.accountID = accountID;
       put("accountID");
 }
 public int getAccountID(){
      return accountID;
}


  public boolean equals(Object obj){
	if(obj != null){
	  if(this.getClass().equals(obj.getClass())){
		SystemLogDTO that = (SystemLogDTO)obj;
		return
			(((this.getOperation() == null) && (that.getOperation() == null)) ||
			 (this.getOperation() != null && this.getOperation().equals(that.getOperation()))) &&
			(((this.getLogDesc() == null) && (that.getLogDesc() == null)) ||
			 (this.getLogDesc() != null && this.getLogDesc().equals(that.getLogDesc()))) &&
			(((this.getLogClass() == null) && (that.getLogClass() == null)) ||
			 (this.getLogClass() != null && this.getLogClass().equals(that.getLogClass()))) &&
			(((this.getLogType() == null) && (that.getLogType() == null)) ||
			 (this.getLogType() != null && this.getLogType().equals(that.getLogType()))) &&
			this.getSequenceNo() == that.getSequenceNo() &&
			this.getCustomerID() == that.getCustomerID() &&
			this.getOperatorID() == that.getOperatorID() &&
            this.getAccountID() == that.getAccountID() &&
            this.getPsID() == that.getPsID() &&
            this.getOrgID() == that.getOrgID() &&
			this.getServiceAccountID() == that.getServiceAccountID() &&
			(((this.getMachineName() == null) && (that.getMachineName() == null)) ||
			 (this.getMachineName() != null && this.getMachineName().equals(that.getMachineName()))) &&
			(((this.getOperatorName() == null) && (that.getOperatorName() == null)) ||
			 (this.getOperatorName() != null && this.getOperatorName().equals(that.getOperatorName()))) &&
			(((this.getModuleName() == null) && (that.getModuleName() == null)) ||
			 (this.getModuleName() != null && this.getModuleName().equals(that.getModuleName()))) &&
			(((this.getCreateDateTime() == null) && (that.getCreateDateTime() == null)) ||
			 (this.getCreateDateTime() != null && this.getCreateDateTime().equals(that.getCreateDateTime()))) &&
			(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
			 (this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
			(((this.getCustomerName() == null) && (that.getCustomerName() == null)) ||
			 (this.getCustomerName() != null && this.getCustomerName().equals(that.getCustomerName()))) &&
			(((this.getLoginID() == null) && (that.getLoginID() == null)) ||
			 (this.getLoginID() != null && this.getLoginID().equals(that.getLoginID()))) &&
			(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
			 (this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod())));
	  }
	}
	return false;
  }

  public int hashCode(){
	return toString().hashCode();
  }

  public String toString(){
	StringBuffer buf = new StringBuffer(256);
	buf.append("operation").append(operation).append("\n");
	buf.append("logDesc").append(",").append(logDesc).append("\n");
	buf.append("logClass").append(",").append(logClass).append("\n");
	buf.append("logType").append(",").append(logType).append("\n");
	buf.append("sequenceNo").append(",").append(sequenceNo).append("\n");
	buf.append("machineName").append(",").append(machineName).append("\n");
	buf.append("operatorName").append(",").append(operatorName).append("\n");
	buf.append("moduleName").append(",").append(moduleName).append("\n");
	buf.append("createDateTime").append(",").append(createDateTime).append("\n");
    buf.append("customerID").append(",").append(customerID).append("\n");
	buf.append("customerName").append(",").append(customerName).append("\n");
	buf.append("operatorID").append(",").append(operatorID).append("\n");
	buf.append("loginID").append(",").append(loginID).append("\n");
	buf.append("psID").append(",").append(psID).append("\n");
	buf.append("orgID").append(",").append(orgID).append("\n");
	buf.append("dtCreate").append(",").append(dtCreate).append("\n");
	buf.append("dtLastmod").append(",").append(dtLastmod).append("\n");
	buf.append("serviceAccountID").append(",").append(serviceAccountID).append("\n");
	buf.append("accountID").append(",").append(accountID).append("\n");
	return buf.toString();
  }

  private java.util.Map map = new java.util.HashMap();

  public void put(String field){
	map.put(field, Boolean.TRUE);
  }

  public java.util.Map getMap(){
	return this.map;
  }

}
