package com.dtv.oss.dto;

import java.sql.Timestamp;

public class ServiceResourceDetailDTO
    implements ReflectionSupport, java.io.Serializable {
  private int id;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private String resourceName;
  private String detailValue;
  private String detailDesc;
  private int usedCustId;
  private int usedServiceAccountId;
  private int usedPsId;
  private String usedpsPropertyName;
  private Timestamp usedDate;
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
    put("id");
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

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
     put("resourceName");
  }

  public String getDetailValue() {
    return detailValue;
  }

  public void setDetailValue(String detailValue) {
    this.detailValue = detailValue;
     put("detailValue");
  }

  public String getDetailDesc() {
    return detailDesc;
  }

  public void setDetailDesc(String detailDesc) {
    this.detailDesc = detailDesc;
      put("detailDesc");
  }

  public int getUsedCustId() {
    return usedCustId;
  }

  public void setUsedCustId(int usedCustId) {
    this.usedCustId = usedCustId;
    put("usedCustId");
  }

  public int getUsedServiceAccountId() {
    return usedServiceAccountId;
  }

  public void setUsedServiceAccountId(int usedServiceAccountId) {
    this.usedServiceAccountId = usedServiceAccountId;
     put("usedServiceAccountId");
  }

  public int getUsedPsId() {
    return usedPsId;
  }

  public void setUsedPsId(int usedPsId) {
    this.usedPsId = usedPsId;
     put("usedPsId");
  }

  public String getUsedpsPropertyName() {
    return usedpsPropertyName;
  }

  public void setUsedpsPropertyName(String usedpsPropertyName) {
    this.usedpsPropertyName = usedpsPropertyName;
     put("usedpsPropertyName");
  }

  public Timestamp getUsedDate() {
    return usedDate;
  }

  public void setUsedDate(Timestamp usedDate) {
    this.usedDate = usedDate;
     put("usedDate");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        ServiceResourceDetailDTO that = (ServiceResourceDetailDTO) obj;
        return
                this.getId() == that.getId()  &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            ( ( (this.getResourceName() == null) && (that.getResourceName() == null)) ||
             (this.getResourceName() != null &&
              this.getResourceName().equals(that.getResourceName()))) &&
            ( ( (this.getDetailValue() == null) && (that.getDetailValue() == null)) ||
             (this.getDetailValue() != null &&
              this.getDetailValue().equals(that.getDetailValue())))
            &&
            ( ( (this.getDetailDesc() == null) && (that.getDetailDesc() == null)) ||
             (this.getDetailDesc() != null &&
              this.getDetailDesc().equals(that.getDetailDesc()))) &&
            this.getUsedCustId() == that.getUsedCustId() &&
            this.getUsedServiceAccountId() == that.getUsedServiceAccountId() &&
            this.getUsedPsId() == that.getUsedPsId() &&
            ( ( (this.getUsedpsPropertyName() == null) &&
               (that.getUsedpsPropertyName() == null)) ||
             (this.getUsedpsPropertyName() != null &&
              this.getUsedpsPropertyName().equals(that.getUsedpsPropertyName()))) &&
            ( ( (this.getUsedDate() == null) && (that.getUsedDate() == null)) ||
             (this.getUsedDate() != null &&
              this.getUsedDate().equals(that.getUsedDate())));
      }
    }
    return false;
  }

  public int hashCode()
         {
                 return toString().hashCode();
         }



  public String toString()
          {
                  StringBuffer buf = new StringBuffer(256);
                  buf.append(id);
                  buf.append(",").append(resourceName);
                  buf.append(",").append(detailValue);
                  buf.append(",").append(status);
                  buf.append(",").append(detailDesc);
                  buf.append(",").append(usedCustId);
                  buf.append(",").append(dtCreate);
                  buf.append(",").append(dtLastmod);

                  buf.append(",").append(usedServiceAccountId);
                  buf.append(",").append(usedPsId);
                  buf.append(",").append(usedpsPropertyName);

                  buf.append(",").append(usedDate);
                  return buf.toString();
          }

          private java.util.Map map = new java.util.HashMap();

          public void put(String field) { map.put(field, Boolean.TRUE); }

          public java.util.Map getMap() { return this.map; }

  }


