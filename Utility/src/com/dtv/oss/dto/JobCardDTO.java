package com.dtv.oss.dto;

import java.sql.Timestamp;

public class JobCardDTO
    implements ReflectionSupport, java.io.Serializable {
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int jobCardId;
  private String jobType;
  private int referSheetId;
  private int custId;
  private int custServiceAccountId;
  private String contactPerson;
  private String contactPhone;
  private String troubleSubType;
  private String TroubleType;
  private String resolutionType;
  private String subType;
  private int addressId;
  private int workCount;
  private Timestamp dueDate;
  private Timestamp preferedDate;
  private Timestamp firstWorkDate;
  private int oldAddressId;
  
  //预约上门时间段
  private String preferedTime;
  
  //预约结果
  private String workResult;
  
  //逾期原因
  private String outOfDateReason;
  
  //上门工作时间
  private Timestamp workDate;
  
//上门工作时间段
  private String workTime;
  
  private int processOrgId; 
  
  private String workResultReason; 
  
  private int addPortNumber;
  private int oldPortNumber;
  private String description;
  private int accountID;
  
  private Timestamp createTime;
  private int createOpID;
  private int createOrgID;
  private String referType;
  private String createMethod;
  private String manualCreateReason;
  private String payStatus;
  private Timestamp payDate;
  private Timestamp statusReasonDate;
  
  private String catvID;
  
  
public String getCatvID() {
	return catvID;
}
public void setCatvID(String catvID) {
	this.catvID = catvID;
	put("catvID");
}
public String getCreateMethod() {
	return createMethod;
}
public void setCreateMethod(String createMethod) {
	this.createMethod = createMethod;
	put("createMethod");
}
public int getCreateOrgID() {
	return createOrgID;
}
public void setCreateOrgID(int createOrgID) {
	this.createOrgID = createOrgID;
	put("createOrgID");
}
public Timestamp getCreateTime() {
	return createTime;
}
public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
	put("createTime");
}
public String getManualCreateReason() {
	return manualCreateReason;
}
public void setManualCreateReason(String manualCreateReason) {
	this.manualCreateReason = manualCreateReason;
	put("manualCreateReason");
}
public int getCreateOpID() {
	return createOpID;
}
public void setCreateOpID(int createOpID) {
	this.createOpID = createOpID;
	put("createOpID");
}
public Timestamp getPayDate() {
	return payDate;
}
public void setPayDate(Timestamp payDate) {
	this.payDate = payDate;
	put("payDate");
}
public String getPayStatus() {
	return payStatus;
}
public void setPayStatus(String payStatus) {
	this.payStatus = payStatus;
	put("payStatus");
}
public String getReferType() {
	return referType;
}
public void setReferType(String referType) {
	this.referType = referType;
	put("referType");
}
public Timestamp getStatusReasonDate() {
	return statusReasonDate;
}
public void setStatusReasonDate(Timestamp statusReasonDate) {
	this.statusReasonDate = statusReasonDate;
	put("statusReasonDate");
}
public int getAccountID() {
	return accountID;
}
public void setAccountID(int accountID) {
	this.accountID = accountID;
	put("accountID");
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
	put("description");
}
public int getOldPortNumber() {
	return oldPortNumber;
}
public void setOldPortNumber(int oldPortNumber) {
	this.oldPortNumber = oldPortNumber;
	put("oldPortNumber");
}
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

public int getAddPortNumber() {
	return addPortNumber;
}
public void setAddPortNumber(int addPortNumber) {
	this.addPortNumber = addPortNumber;
	put("addPortNumber");
}
/**
 * @return Returns the preferedDate.
 */
public Timestamp getPreferedDate() {
	return preferedDate;
}
/**
 * @param preferedDate The preferedDate to set.
 */
public void setPreferedDate(Timestamp preferedDate) {
	this.preferedDate = preferedDate;
	put("preferedDate");
}
 
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
     put("status");
  }
  
  public String getSubType() {
	    return subType;
	  }

public void setSubType(String subType) {
	    this.subType = subType;
	     put("subType");
	  }

  public Timestamp getDtCreate() {
    return dtCreate;
  }

  public void setDtCreate(Timestamp dtCreate) {
    this.dtCreate = dtCreate;
  }

  public Timestamp getDtLastmod() {
    return dtLastmod;
  }

  public void setDtLastmod(Timestamp dtLastmod) {
    this.dtLastmod = dtLastmod;
  }

  public int getJobCardId() {
    return jobCardId;
  }

  public void setJobCardId(int jobCardId) {
    this.jobCardId = jobCardId;

  }

  public String getJobType() {
    return jobType;
  }

  public void setJobType(String jobType) {
    this.jobType = jobType;
     put("jobType");
  }

  public int getReferSheetId() {
    return referSheetId;
  }

  public void setReferSheetId(int referSheetId) {
    this.referSheetId = referSheetId;
    put("referSheetId");
  }

  public int getCustId() {
    return custId;
  }

  public void setCustId(int custId) {
    this.custId = custId;
      put("custId");
  }

  public int getCustServiceAccountId() {
    return custServiceAccountId;
  }

  public void setCustServiceAccountId(int custServiceAccountId) {
    this.custServiceAccountId = custServiceAccountId;
    put("custServiceAccountId");
  }

  public String getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
     put("contactPerson");
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
     put("contactPhone");
  }

  public int getAddressId() {
    return addressId;
  }

  public void setAddressId(int addressId) {
    this.addressId = addressId;
    put("addressId");
  }

  public int getWorkCount() {
    return workCount;
  }

  public void setWorkCount(int workCount) {
    this.workCount = workCount;
    put("workCount");
  }

  public Timestamp getDueDate() {
    return dueDate;
  }

  public void setDueDate(Timestamp dueDate) {
    this.dueDate = dueDate;
    put("dueDate");

  }

  public Timestamp getFirstWorkDate() {
    return firstWorkDate;
  }

  public void setFirstWorkDate(Timestamp firstWorkDate) {
    this.firstWorkDate = firstWorkDate;
      put("firstWorkDate");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        JobCardDTO that = (JobCardDTO) obj;
        return ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
                (this.getStatus() != null &&
                 this.getStatus().equals(that.getStatus()))) &&
                 ( ( (this.getPreferedDate() == null) && (that.getPreferedDate() == null)) ||
                        (this.getPreferedDate() != null &&
                         this.getPreferedDate().equals(that.getPreferedDate()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
          this.getJobCardId() == that.getJobCardId()  &&
            ( ( (this.getJobType() == null) && (that.getJobType() == null)) ||
             (this.getJobType() != null &&
              this.getJobType().equals(that.getJobType()))) &&
            this.getReferSheetId() == that.getReferSheetId() &&
            this.getCustId() == that.getCustId() &&
            this.getAddPortNumber() == that.getAddPortNumber() &&
            this.getCustServiceAccountId() == that.getCustServiceAccountId() &&
            ( ( (this.getContactPerson() == null) && (that.getContactPerson() == null)) ||
             (this.getContactPerson() != null &&
              this.getContactPerson().equals(that.getContactPerson()))) &&
              ( ( (this.getSubType() == null) && (that.getSubType() == null)) ||
                      (this.getSubType() != null &&
                       this.getSubType().equals(that.getSubType()))) &&
              ( ( (this.getPreferedTime() == null) && (that.getPreferedTime() == null)) ||
                    (this.getPreferedTime() != null &&
                     this.getPreferedTime().equals(that.getPreferedTime()))) &&
                 ( ( (this.getWorkResult() == null) && (that.getWorkResult() == null)) ||
                  (this.getWorkResult() != null && this.getWorkResult().equals(that.getWorkResult()))) &&	
                  ( ( (this.getOutOfDateReason() == null) && (that.getOutOfDateReason() == null)) ||
                        (this.getOutOfDateReason() != null && this.getOutOfDateReason().equals(that.getOutOfDateReason()))) &&	
                        ( ( (this.getWorkTime() == null) && (that.getWorkTime() == null)) ||
                                (this.getWorkTime() != null && this.getWorkTime().equals(that.getWorkTime()))) &&	
                                ( ( (this.getWorkDate() == null) && (that.getWorkDate() == null)) ||
                                        (this.getWorkDate() != null && this.getWorkDate().equals(that.getWorkDate()))) &&								
            ( ( (this.getContactPhone() == null) && (that.getContactPhone() == null)) ||
             (this.getContactPhone() != null &&
              this.getContactPhone().equals(that.getContactPhone()))) &&
            this.getAddressId() == that.getAddressId() &&
            this.getProcessOrgId() == that.getProcessOrgId() &&
            this.getOldPortNumber() == that.getOldPortNumber() &&
            this.getAccountID() == that.getAccountID() &&
            this.getWorkCount() == that.getWorkCount() &&
            this.getCreateOpID() == that.getCreateOpID() &&
            this.getCreateOrgID() == that.getCreateOrgID() &&
            this.getOldAddressId() == that.getOldAddressId() &&
          //  this.getCatvID() == that.getCatvID() &&
            ( ( (this.getCatvID() == null) && (that.getCatvID() == null)) ||
                    (this.getCatvID() != null && this.getCatvID().equals(that.getCatvID()))) &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
                    (this.getCreateTime() != null && this.getCreateTime().equals(that.getCreateTime()))) &&
             ( ( (this.getReferType() == null) && (that.getReferType() == null)) ||
                       (this.getReferType() != null && this.getReferType().equals(that.getReferType()))) && 
             ( ( (this.getCreateMethod() == null) && (that.getCreateMethod() == null)) ||
                        (this.getCreateMethod() != null && this.getCreateMethod().equals(that.getCreateMethod()))) &&  
               ( ( (this.getManualCreateReason() == null) && (that.getManualCreateReason() == null)) ||
                      (this.getManualCreateReason() != null && this.getManualCreateReason().equals(that.getManualCreateReason()))) &&   
                  ( ( (this.getPayStatus() == null) && (that.getPayStatus() == null)) ||
                              (this.getPayStatus() != null && this.getPayStatus().equals(that.getPayStatus()))) &&    
                  ( ( (this.getPayDate() == null) && (that.getPayDate() == null)) ||
                             (this.getPayDate() != null && this.getPayDate().equals(that.getPayDate()))) &&  
                  ( ( (this.getStatusReasonDate() == null) && (that.getStatusReasonDate() == null)) ||
                      (this.getStatusReasonDate() != null && this.getStatusReasonDate().equals(that.getStatusReasonDate()))) &&                    
            ( ( (this.getWorkResultReason() == null) && (that.getWorkResultReason() == null)) ||
                    (this.getWorkResultReason() != null && this.getWorkResultReason().equals(that.getWorkResultReason()))) &&
           ( ( (this.getDescription() == null) && (that.getDescription() == null)) ||
                  (this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&        
            ( ( (this.getDueDate() == null) && (that.getDueDate() == null)) ||
             (this.getDueDate() != null && this.getDueDate().equals(that.getDueDate()))) &&
            ( ( (this.getFirstWorkDate() == null) && (that.getFirstWorkDate() == null)) ||
             (this.getFirstWorkDate() != null &&
              this.getFirstWorkDate().equals(that.getFirstWorkDate())));
      }
    }
    return false;
  }

  public int hashCode() {
    return
           toString().hashCode();
  }



  public String toString() {
    StringBuffer buf = new StringBuffer(256);
    buf.append(status);
   
   
    
    
    buf.append(",").append(createTime);
    buf.append(",").append(createOpID);
    buf.append(",").append(createOrgID);
    buf.append(",").append(referType);
    buf.append(",").append(createMethod);
    buf.append(",").append(manualCreateReason);
    buf.append(",").append(payStatus);
    buf.append(",").append(payDate);
    buf.append(",").append(statusReasonDate);
    buf.append(",").append(catvID);
    buf.append(",").append(jobCardId);
    buf.append(",").append(jobType);
    buf.append(",").append(subType);
    buf.append(",").append(referSheetId);
    buf.append(",").append(custId);
    buf.append(",").append(addPortNumber);
    buf.append(",").append(custServiceAccountId);
    buf.append(",").append(contactPerson);
    buf.append(",").append(contactPhone);
    buf.append(",").append(addressId);
    buf.append(",").append(workCount);
    buf.append(",").append(dueDate);
    buf.append(",").append(firstWorkDate);
    buf.append(",").append(preferedDate);
    buf.append(",").append(TroubleType);
    buf.append(",").append(resolutionType);
     
    buf.append(",").append(preferedTime);
    buf.append(",").append(workResult);
    buf.append(",").append(outOfDateReason);
    buf.append(",").append(workDate);
    buf.append(",").append(workTime);
    buf.append(",").append(processOrgId);
    buf.append(",").append(workResultReason);
    buf.append(",").append(dtCreate);
    buf.append(",").append(dtLastmod);
    buf.append(",").append(oldPortNumber);
    buf.append(",").append(description);
    buf.append(",").append(accountID);
    buf.append(",").append(oldAddressId);
    return buf.toString();
  }

  private java.util.Map map = new java.util.HashMap();

  public void put(String field) {
    map.put(field, Boolean.TRUE);
  }

  public java.util.Map getMap() {
    return this.map;
  }

 
 
 
 
/**
 * @return Returns the resolutionType.
 */
public String getResolutionType() {
	return resolutionType;
}
/**
 * @param resolutionType The resolutionType to set.
 */
public void setResolutionType(String resolutionType) {
	this.resolutionType = resolutionType;
	put("resolutionType");
}
/**
 * @return Returns the troubleSubType.
 */
public String getTroubleSubType() {
	return troubleSubType;
}
/**
 * @param troubleSubType The troubleSubType to set.
 */
public void setTroubleSubType(String troubleSubType) {
	this.troubleSubType = troubleSubType;
	put("troubleSubType");
}
/**
 * @return Returns the troubleType.
 */
public String getTroubleType() {
	return TroubleType;
}
/**
 * @param troubleType The troubleType to set.
 */
public void setTroubleType(String troubleType) {
	TroubleType = troubleType;
	put("troubleType");
}
/**
 * @return Returns the outofDateReason.
 */
public String getOutOfDateReason() {
	return outOfDateReason;
}
/**
 * @param outofDateReason The outofDateReason to set.
 */
public void setOutOfDateReason(String outOfDateReason) {
	this.outOfDateReason = outOfDateReason;
	put("outOfDateReason");
}
/**
 * @return Returns the preferedTime.
 */
public String getPreferedTime() {
	return preferedTime;
}
/**
 * @param preferedTime The preferedTime to set.
 */
public void setPreferedTime(String preferedTime) {
	this.preferedTime = preferedTime;
	put("preferedTime");
}
/**
 * @return Returns the workDate.
 */
public Timestamp getWorkDate() {
	return workDate;
}
/**
 * @param workDate The workDate to set.
 */
public void setWorkDate(Timestamp workDate) {
	this.workDate = workDate;
	put("workDate");
}
/**
 * @return Returns the workResult.
 */
public String getWorkResult() {
	return workResult;
}
/**
 * @param workResult The workResult to set.
 */
public void setWorkResult(String workResult) {
	this.workResult = workResult;
	put("workResult");
}
/**
 * @return Returns the workTime.
 */
public String getWorkTime() {
	return workTime;
}
/**
 * @param workTime The workTime to set.
 */
public void setWorkTime(String workTime) {
	this.workTime = workTime;
	put("workTime");
}
public String getWorkResultReason() {
	return workResultReason;
}
public void setWorkResultReason(String workResultReason) {
	this.workResultReason = workResultReason;
	put("workResultReason");
}
public int getOldAddressId() {
	return oldAddressId;
}
public void setOldAddressId(int oldAddressId) {
	this.oldAddressId = oldAddressId;
	put("oldAddressId");
}
}

