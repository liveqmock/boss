package com.dtv.oss.dto;

 
import java.sql.Timestamp;

public class BatchJobItemDTO
   implements ReflectionSupport, java.io.Serializable {
  private int itemNO;
  private int batchId;
  private int customerId;
  private int accountId;
  private int userId;
  private int custPackageId;
  private int psId;
  private int ccId;
  private String rcdData;
  private String status;
  private Timestamp statusTime;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  public int getItemNO() {
    return itemNO;
  }

  public void setItemNO(int itemNO) {
    this.itemNO = itemNO;
  }

  public int getBatchId() {
    return batchId;
  }

  public void setBatchId(int batchId) {
    this.batchId = batchId;
    put("batchId");
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
    put("customerId");
  }

  public int getAccountId() {
    return accountId;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
    put("accountId");
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
    put("userId");
  }

  public int getCustPackageId() {
    return custPackageId;
  }

  public void setCustPackageId(int custPackageId) {
    this.custPackageId = custPackageId;
    put("custPackageId");
  }

  public int getPsId() {
    return psId;
  }

  public void setPsId(int psId) {
    this.psId = psId;
    put("psId");
  }

  public int getCcId() {
    return ccId;
  }

  public void setCcId(int ccId) {
    this.ccId = ccId;
    put("ccId");
  }

  public String getRcdData() {
    return rcdData;
  }

  public void setRcdData(String rcdData) {
    this.rcdData = rcdData;
    put("rcdData");
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
    put("status");
  }

  public Timestamp getStatusTime() {
    return statusTime;
  }

  public void setStatusTime(Timestamp statusTime) {
    this.statusTime = statusTime;
    put("statusTime");
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

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        BatchJobItemDTO that = (BatchJobItemDTO) obj;
        return this.getItemNO()== that.getItemNO() &&
            this.getBatchId() == that.getBatchId() &&
            this.getCustomerId() == that.getCustomerId() &&
            this.getAccountId() == that.getAccountId() &&
            this.getUserId() == that.getUserId() &&
            this.getCustPackageId() == that.getCustPackageId() &&
            this.getPsId() == that.getPsId() && this.getCcId() == that.getCcId() &&
            ( ( (this.getRcdData() == null) && (that.getRcdData() == null)) ||
             (this.getRcdData() != null &&
              this.getRcdData().equals(that.getRcdData()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getStatusTime() == null) && (that.getStatusTime() == null)) ||
             (this.getStatusTime() != null &&
              this.getStatusTime().equals(that.getStatusTime()))) &&
            ( ( (this.getDtCreate() ==
                 null) && (that.getDtCreate() == null)) ||
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
    return  toString().hashCode();
  }

  
  public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(itemNO);
		buf.append(",").append(customerId);
		buf.append(",").append(status);
		buf.append(",").append(batchId);
		buf.append(",").append(accountId);
		buf.append(",").append(userId);
		buf.append(",").append(custPackageId);
		buf.append(",").append(psId);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(ccId);
		buf.append(",").append(rcdData);
		buf.append(",").append(statusTime);
		 
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}


