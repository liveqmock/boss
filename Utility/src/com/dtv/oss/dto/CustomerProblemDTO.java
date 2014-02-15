package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CustomerProblemDTO
    implements ReflectionSupport, java.io.Serializable {
  private int id;
  private String problemLevel;
  private int referJobCardID;
  private int custID;
  private int custServiceAccountID;
  private String contactPerson;
  private String contactphone;
  private String problemDesc;
  private Timestamp createDate;
  private Timestamp dueDate;
  private String problemCategory;
  private String status;
  private String callBackFlag;
  private int addressID;
  private Timestamp callBackDate;
  private int createOpId;
  private int createOrgID;
  private String isManualTransfer;
  private int manualTransferFromOrgID ;
  
  
public String getIsManualTransfer() {
	return isManualTransfer;
}
public void setIsManualTransfer(String isManualTransfer) {
	this.isManualTransfer = isManualTransfer;
	put("isManualTransfer");
}
public int getManualTransferFromOrgID() {
	return manualTransferFromOrgID;
}
public void setManualTransferFromOrgID(int manualTransferFromOrgID) {
	this.manualTransferFromOrgID = manualTransferFromOrgID;
	put("manualTransferFromOrgID");
}
/**
 * @return Returns the createOpId.
 */
public int getCreateOpId() {
	return createOpId;
}
/**
 * @param createOpId The createOpId to set.
 */
public void setCreateOpId(int createOpId) {
	this.createOpId = createOpId;
	 put("createOpId");
}
/**
 * @return Returns the createOrgID.
 */
public int getCreateOrgID() {
	return createOrgID;
}
/**
 * @param createOrgID The createOrgID to set.
 */
public void setCreateOrgID(int createOrgID) {
	this.createOrgID = createOrgID;
	 put("createOrgID");
}
/**
 * @return Returns the callBackDate.
 */
public Timestamp getCallBackDate() {
	return callBackDate;
}
/**
 * @param callBackDate The callBackDate to set.
 */
public void setCallBackDate(Timestamp callBackDate) {
	this.callBackDate = callBackDate;
	 put("callBackDate");
}
  /**
 * @return Returns the custAccountID.
 */
public int getCustAccountID() {
	return custAccountID;
}
/**
 * @param custAccountID The custAccountID to set.
 */
public void setCustAccountID(int custAccountID) {
	this.custAccountID = custAccountID;
	 put("custAccountID");
}
private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int processOrgId;
  private int custAccountID ;
/**
 * @return Returns the processOrgId.
 */
public int getProcessOrgId() {
	return processOrgId;
}
/**
 * @param processOrgId The processOrgId to set.
 */
public void setProcessOrgId(int processOrgId) {
	this.processOrgId = processOrgId;
	 put("processOrgId");
}
  private String feeClass;

  public CustomerProblemDTO() {
  }

  /**
   * @param closeReason2
   */

  public void setId(int id) {
    this.id = id;
    //put("id");
  }

  public int getId() {
    return id;
  }

  public void setFeeClass(String feeClass) {
    this.feeClass = feeClass;
    put("feeClass");
  }

  public String getFeeClass() {
    return feeClass;
  }

   

  public void setProblemLevel(String problemLevel) {
    this.problemLevel = problemLevel;
    put("problemLevel");
  }

  public String getProblemLevel() {
    return problemLevel;
  }

  public void setReferJobCardID(int referJobCardID) {
    this.referJobCardID = referJobCardID;
    put("referJobCardID");
  }

  public int getReferJobCardID() {
    return referJobCardID;
  }

 public void setCustID(int custID) {
    this.custID = custID;
    put("custID");
  }

  public int getCustID() {
    return custID;
  }

  public void setCustServiceAccountID(int custServiceAccountID) {
    this.custServiceAccountID = custServiceAccountID;
    put("custServiceAccountID");
  }

  public int getCustServiceAccountID() {
    return custServiceAccountID;
  }






  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
    put("contactPerson");
  }

  public String getContactPerson() {
    return contactPerson;
  }

  public void setContactphone(String contactphone) {
    this.contactphone = contactphone;
    put("contactphone");
  }

  public String getContactphone() {
    return contactphone;
  }

  public void setProblemDesc(String problemDesc) {
    this.problemDesc = problemDesc;
    put("problemDesc");
  }

  public String getProblemDesc() {
    return problemDesc;
  }




  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
    put("createDate");
  }

  public Timestamp getCreateDate() {
    return createDate;
  }





  public void setDueDate(Timestamp dueDate) {
    this.dueDate = dueDate;
    put("dueDate");
  }

  public Timestamp getDueDate() {
    return dueDate;
  }

   
   

 public void setProblemCategory(String problemCategory) {
    this.problemCategory = problemCategory;
    put("problemCategory");
  }

  public String getProblemCategory() {
    return problemCategory;
  }










  public void setStatus(String status) {
    this.status = status;
    put("status");
  }

  public String getStatus() {
    return status;
  }

  public void setCallBackFlag(String callBackFlag) {
    this.callBackFlag = callBackFlag;
    put("callBackFlag");
  }

  public String getCallBackFlag() {
    return callBackFlag;
  }



  public void setAddressID(int addressID) {
    this.addressID = addressID;
    put("addressID");
  }

  public int getAddressID() {
    return addressID;
  }

  public void setDtCreate(Timestamp dtCreate) {
    this.dtCreate = dtCreate;
  }

  public Timestamp getDtCreate() {
    return dtCreate;
  }

  public void setDtLastmod(Timestamp dtLastmod) {
    this.dtLastmod = dtLastmod;
  }

  public Timestamp getDtLastmod() {
    return dtLastmod;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CustomerProblemDTO that = (CustomerProblemDTO) obj;
        return
            this.getId() == that.getId() &&
            

            ( ( (this.getProblemLevel() == null) && (that.getProblemLevel() == null)) ||
             (this.getProblemLevel() != null &&
              this.getProblemLevel().equals(that.getProblemLevel()))) &&
            this.getReferJobCardID() == that.getReferJobCardID() &&
            this.getProcessOrgId() == that.getProcessOrgId() &&

            this.getCustID() == that.getCustID() &&
            this.getManualTransferFromOrgID() == that.getManualTransferFromOrgID() &&
            this.getCustServiceAccountID() == that.getCustServiceAccountID() &&

            ( ( (this.getContactPerson() == null) && (that.getContactPerson() == null)) ||
             (this.getContactPerson() != null &&
              this.getContactPerson().equals(that.getContactPerson()))) &&
              ( ( (this.getIsManualTransfer() == null) && (that.getIsManualTransfer() == null)) ||
                      (this.getIsManualTransfer() != null &&
                       this.getIsManualTransfer().equals(that.getIsManualTransfer()))) &&
            ( ( (this.getProblemCategory() == null) && (that.getProblemCategory() == null)) ||
             (this.getProblemCategory() != null &&
              this.getProblemCategory().equals(that.getProblemCategory()))) &&
            ( ( (this.getContactphone() == null) && (that.getContactphone() == null)) ||
             (this.getContactphone() != null &&
              this.getContactphone().equals(that.getContactphone()))) &&
            ( ( (this.getProblemDesc() == null) && (that.getProblemDesc() == null)) ||
             (this.getProblemDesc() != null &&
              this.getProblemDesc().equals(that.getProblemDesc()))) &&

            ( ( (this.getCreateDate() == null) && (that.getCreateDate() == null)) ||
             (this.getCreateDate() != null &&
              this.getCreateDate().equals(that.getCreateDate()))) &&
            ( ( (this.getFeeClass() == null) && (that.getFeeClass() == null)) ||
             (this.getFeeClass() != null &&
              this.getFeeClass().equals(that.getFeeClass()))) &&

            ( ( (this.getDueDate() == null) && (that.getDueDate() == null)) ||
             (this.getDueDate() != null &&
              this.getDueDate().equals(that.getDueDate()))) &&
           
            

            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
              ( ( (this.getCallBackDate() == null) && (that.getCallBackDate() == null)) ||
                    (this.getCallBackDate() != null &&
                     this.getCallBackDate().equals(that.getCallBackDate()))) &&
            ( ( (this.getCallBackFlag() == null) && (that.getCallBackFlag() == null)) ||
             (this.getCallBackFlag() != null &&
              this.getCallBackFlag().equals(that.getCallBackFlag()))) &&

            this.getAddressID() == that.getAddressID() &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod())));
      }
    }
    return false;
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
    buf.append(id);

  
    buf.append(",").append(feeClass);
    buf.append(",").append(problemLevel);
    buf.append(",").append(referJobCardID);
    
    buf.append(",").append(custID);
    buf.append(",").append(custServiceAccountID);

    buf.append(",").append(contactPerson);
    buf.append(",").append(contactphone);
    buf.append(",").append(problemDesc);
    buf.append(",").append(processOrgId);
    buf.append(",").append(createDate);
    
    buf.append(",").append(callBackDate);

    buf.append(",").append(dueDate);
    buf.append(",").append(problemCategory);
   

    buf.append(",").append(status);
    buf.append(",").append(callBackFlag);

    buf.append(",").append(addressID);
    buf.append(",").append(dtCreate);
    buf.append(",").append(dtLastmod);
    buf.append(",").append(createOpId);
    buf.append(",").append(createOrgID);
    buf.append(",").append(isManualTransfer);
    buf.append(",").append(manualTransferFromOrgID);
   
    return buf.toString();
  }

  private java.util.Map map = new java.util.HashMap();

  public void put(String field) {
    map.put(field, Boolean.TRUE);
  }

  public java.util.Map getMap() {
    return this.map;
  }

}
