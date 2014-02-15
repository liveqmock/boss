package com.dtv.oss.dto;

import java.sql.Timestamp;

public class QueryResultItemDTO
     implements ReflectionSupport, java.io.Serializable {
  private int itemNO;
  private int queryId;
  private int customerId;
  private int accountId;
  private int userId;
  private int packageId;
  private int psId;
  private int ccId;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int productId;
   
  public int getItemNO() {
    return itemNO;
  }

  public void setItemNO(int itemNO) {
    this.itemNO = itemNO;
  }

  public int getQueryId() {
    return queryId;
  }

  public void setQueryId(int queryId) {
    this.queryId = queryId;
    put("queryId");
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

  public int getPackageId() {
    return packageId;
  }

  public void setPackageId(int packageId) {
    this.packageId = packageId;
    put("packageId");
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
    put("status");
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
        QueryResultItemDTO that = (QueryResultItemDTO) obj;
        return
           this.getItemNO()  == that.getItemNO() &&
            this.getQueryId() == that.getQueryId() &&
            this.getCustomerId() == that.getCustomerId() &&
            this.getAccountId() == that.getAccountId() &&
            this.getUserId() == that.getUserId() &&
            this.getPackageId() == that.getPackageId() &&
            this.getProductId() == that.getProductId() &&
            this.getPsId() == that.getPsId() && this.getCcId() == that.getCcId() &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
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
    return
            toString().hashCode();
  }


  public String toString()
          {
                  StringBuffer buf = new StringBuffer(256);
                  buf.append(itemNO);
                  buf.append(",").append(queryId);
                  buf.append(",").append(customerId);
                  buf.append(",").append(accountId);
                  buf.append(",").append(userId);
                  buf.append(",").append(packageId);
                  buf.append(",").append(psId);
                  buf.append(",").append(ccId);
                  buf.append(",").append(productId);
                  buf.append(",").append(status);

                  buf.append(",").append(dtCreate);

                  buf.append(",").append(dtLastmod);



                  return buf.toString();
          }

          private java.util.Map map = new java.util.HashMap();

          public void put(String field) { map.put(field, Boolean.TRUE); }

          public java.util.Map getMap() { return this.map; }


/**
 * @return Returns the productId.
 */
public int getProductId() {
	return productId;
}
/**
 * @param productId The productId to set.
 */
public void setProductId(int productId) {
	this.productId = productId;
	put("productId");
}
  }


