package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CustomerProductDTO
    implements ReflectionSupport, java.io.Serializable {
  private int customerID;
  private int accountID;
  private int serviceAccountID;
  private int productID;

  private int referPackageID;
  private Timestamp pauseTime;
  private Timestamp activateTime;
  private String status;
  private int deviceID;
  private int linkToDevice1; 
  private int linkToDevice2; 
  private int psID;
  private String statusReason;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private Timestamp cancelTime;
  private Timestamp createTime;
  private Timestamp startTime;
  private Timestamp endTime;
  private String financeOption;
  private String deviceProvideFlag;
  
/**
 * @return the deviceProvideFlag
 */
public String getDeviceProvideFlag() {
	return deviceProvideFlag;
}
/**
 * @param deviceProvideFlag the deviceProvideFlag to set
 */
public void setDeviceProvideFlag(String deviceProvideFlag) {
	this.deviceProvideFlag = deviceProvideFlag;
	put("deviceProvideFlag");
}
/**
 * @return Returns the endTime.
 */
public Timestamp getEndTime() {
	return endTime;
}
/**
 * @param endTime The endTime to set.
 */
public void setEndTime(Timestamp endTime) {
	this.endTime = endTime;
	put("endTime");
}
/**
 * @return Returns the financeOption.
 */
public String getFinanceOption() {
	return financeOption;
}
/**
 * @param financeOption The financeOption to set.
 */
public void setFinanceOption(String financeOption) {
	this.financeOption = financeOption;
	put("financeOption");
}
/**
 * @return Returns the startTime.
 */
public Timestamp getStartTime() {
	return startTime;
}
/**
 * @param startTime The startTime to set.
 */
public void setStartTime(Timestamp startTime) {
	this.startTime = startTime;
	put("startTime");
}
 

  public CustomerProductDTO() {
  }

/**
 * @return Returns the linkToDevice1.
 */
public int getLinkToDevice1() {
	return linkToDevice1;
}
/**
 * @param linkToDevice1 The linkToDevice1 to set.
 */
public void setLinkToDevice1(int linkToDevice1) {
	this.linkToDevice1 = linkToDevice1;
	put("linkToDevice1");
}
/**
 * @return Returns the linkToDevice2.
 */
public int getLinkToDevice2() {
	return linkToDevice2;
}
/**
 * @param linkToDevice2 The linkToDevice2 to set.
 */
public void setLinkToDevice2(int linkToDevice2) {
	this.linkToDevice2 = linkToDevice2;
	put("linkToDevice1");
}
 
  public void setCustomerID(int customerID) {
    this.customerID = customerID;
    put("customerID");
  }

  public int getCustomerID() {
    return customerID;
  }

  public void setAccountID(int accountID) {
    this.accountID = accountID;
    put("accountID");
  }

  public int getAccountID() {
    return accountID;
  }

  public void setServiceAccountID(int serviceAccountID) {
    this.serviceAccountID = serviceAccountID;
    put("serviceAccountID");
  }

  public int getServiceAccountID() {
    return serviceAccountID;
  }

  public void setProductID(int productID) {
    this.productID = productID;
    put("productID");
  }

  public int getProductID() {
    return productID;
  }

  public void setReferPackageID(int referPackageID) {
    this.referPackageID = referPackageID;
    put("referPackageID");
  }

  public int getReferPackageID() {
    return referPackageID;
  }

  public void setPauseTime(Timestamp pauseTime) {
    this.pauseTime = pauseTime;
    put("pauseTime");
  }

  public Timestamp getPauseTime() {
    return pauseTime;
  }

  public void setActivateTime(Timestamp activateTime) {
    this.activateTime = activateTime;
    put("activateTime");
  }

  public Timestamp getActivateTime() {
    return activateTime;
  }

  public void setStatus(String status) {
    this.status = status;
    put("status");
  }

  public String getStatus() {
    return status;
  }

  public void setStatusReason(String statusReason) {
    this.statusReason = statusReason;
    put("statusReason");
  }

  public String getStatusReason() {
    return statusReason;
  }

  public void setDeviceID(int deviceID) {
    this.deviceID = deviceID;
    put("deviceID");
  }

  public int getDeviceID() {
    return deviceID;
  }

   



  public void setPsID(int psID) {
    this.psID = psID;
    //put("psID");
  }

  public int getPsID() {
    return psID;
  }

  public void setCancelTime(Timestamp cancelTime) {
    this.cancelTime = cancelTime;
    put("cancelTime");
  }

  public Timestamp getCancelTime() {
    return cancelTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
    put("createTime");
  }

  public Timestamp getCreateTime() {
    return createTime;
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
        CustomerProductDTO that = (CustomerProductDTO) obj;
        return
            this.getCustomerID() == that.getCustomerID() &&
            this.getAccountID() == that.getAccountID() &&
            this.getServiceAccountID() == that.getServiceAccountID() &&
            this.getProductID() == that.getProductID() &&
            this.getLinkToDevice1() == that.getLinkToDevice1() &&
            this.getLinkToDevice2() == that.getLinkToDevice2() &&
            this.getReferPackageID() == that.getReferPackageID() &&
            ( ( (this.getActivateTime() == null) && (that.getActivateTime() == null)) ||
             (this.getActivateTime() != null &&
              this.getActivateTime().equals(that.getActivateTime()))) &&
            ( ( (this.getPauseTime() == null) && (that.getPauseTime() == null)) ||
             (this.getPauseTime() != null &&
              this.getPauseTime().equals(that.getPauseTime()))) &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) &&
            ( ( (this.getCancelTime() == null) && (that.getCancelTime() == null)) ||
             (this.getCancelTime() != null &&
              this.getCancelTime().equals(that.getCancelTime()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getStatusReason() == null) && (that.getStatusReason() == null)) ||
             (this.getStatusReason() != null &&
              this.getStatusReason().equals(that.getStatusReason()))) &&
            this.getDeviceID() == that.getDeviceID() &&
           
             
            this.getPsID() == that.getPsID() &&
            ( ( (this.getStartTime() == null) && (that.getStartTime() == null)) ||
                    (this.getStartTime() != null &&
                     this.getStartTime().equals(that.getStartTime()))) &&
             ( ( (this.getEndTime() == null) && (that.getEndTime() == null)) ||
                            (this.getEndTime() != null &&
                             this.getEndTime().equals(that.getEndTime()))) &&
               ( ( (this.getFinanceOption() == null) && (that.getFinanceOption() == null)) ||
                     (this.getFinanceOption() != null &&
                                  this.getFinanceOption().equals(that.getFinanceOption()))) &&					 
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
    buf.append("<").append(customerID);
    buf.append(",").append(accountID);
    buf.append(",").append(serviceAccountID);
    buf.append(",").append(productID);

    buf.append(",").append(referPackageID);
    buf.append(",").append(this.cancelTime);
    buf.append(",").append(this.createTime);
    buf.append(",").append(this.activateTime);
    buf.append(",").append(this.pauseTime);
    buf.append(",").append(status);
    buf.append(",").append(statusReason);
    buf.append(",").append(deviceID);
    buf.append(",").append(linkToDevice1);
    buf.append(",").append(linkToDevice2);

    buf.append(",").append(psID);
    buf.append(",").append(financeOption);
    buf.append(",").append(dtCreate);
    buf.append(",").append(dtLastmod);
    buf.append(",").append(startTime);
    buf.append(",").append(endTime).append(">\n");
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
