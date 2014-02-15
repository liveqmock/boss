package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CustomerComplainDTO
    implements ReflectionSupport, java.io.Serializable {
  private int custComplainId;
  private int customerId;
  private String type;
  private String content;
  private String request;
  private int complainedOrgId;
  private String complainedPerson;
  private String contactPerson;
  private String contactPhone;
  private String callBackFlag;
  private Timestamp callBackDate;
  private int currentWorkOrgID;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
     put("type");
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
      put("content");
  }

  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
      put("request");
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
       put("status");
  }

  public int getCustComplainId() {
    return custComplainId;
  }

  public void setCustComplainId(int custComplainId) {
    this.custComplainId = custComplainId;

  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
       put("customerId");
  }

  public int getComplainedOrgId() {
    return complainedOrgId;
  }

  public void setComplainedOrgId(int complainedOrgId) {
    this.complainedOrgId = complainedOrgId;
    put("complainedOrgId");
  }

  public String getComplainedPerson() {
    return complainedPerson;
  }

  public void setComplainedPerson(String complainedPerson) {
    this.complainedPerson = complainedPerson;
     put("complainedPerson");
  }

  public Timestamp getCallBackDate() {
    return callBackDate;
  }

  public void setCallBackDate(Timestamp callBackDate) {
    this.callBackDate = callBackDate;
     put("callBackDate");
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

  public String getCallBackFlag() {
    return callBackFlag;
  }

  public void setCallBackFlag(String callBackFlag) {
    this.callBackFlag = callBackFlag;
      put("callBackFlag");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CustomerComplainDTO that = (CustomerComplainDTO) obj;
        return ( ( (this.getType() == null) && (that.getType() == null)) ||
                (this.getType() != null && this.getType().equals(that.getType()))) &&
            ( ( (this.getContent() == null) && (that.getContent() == null)) ||
             (this.getContent() != null &&
              this.getContent().equals(that.getContent()))) &&
            ( ( (this.getRequest() == null) && (that.getRequest() == null)) ||
             (this.getRequest() != null &&
              this.getRequest().equals(that.getRequest()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
           ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
            (this.getDtCreate() != null &&
             this.getDtCreate().equals(that.getDtCreate()))) &&
           ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
            (this.getDtLastmod() != null &&
             this.getDtLastmod().equals(that.getDtLastmod()))) &&

             this.getCustComplainId() == that.getCustComplainId() &&
             this.getCurrentWorkOrgID() == that.getCurrentWorkOrgID() &&
            this.getCustomerId() == that.getCustomerId() &&
            this.getComplainedOrgId() == that.getComplainedOrgId() &&
            ( ( (this.getComplainedPerson() == null) &&
               (that.getComplainedPerson
                () == null)) ||
             (this.getComplainedPerson() != null &&
              this.getComplainedPerson().equals(that.getComplainedPerson()))) &&
            ( ( (this.getCallBackDate() == null) && (that.getCallBackDate() == null)) ||
             (this.getCallBackDate() != null &&
              this.getCallBackDate().equals(that.getCallBackDate()))) &&
            ( ( (this.getContactPerson() == null) && (that.getContactPerson() == null)) ||
             (this.getContactPerson() != null &&
              this.getContactPerson().equals(that.getContactPerson()))) &&
            ( ( (this.getContactPhone() == null) && (that.getContactPhone() == null)) ||
             (this.getContactPhone() != null &&
              this.getContactPhone().equals(that.getContactPhone()))) &&
            ( ( (this.getCallBackFlag() == null) && (that.getCallBackFlag() == null)) ||
             (this.getCallBackFlag() != null &&
              this.getCallBackFlag().equals(that.getCallBackFlag())));
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
                 buf.append(status);
                 buf.append(",").append(type);
                 buf.append(",").append(content);
                 buf.append(",").append(request);
                 buf.append(",").append(status);
                 buf.append(",").append(contactPhone);
                  buf.append(",").append(custComplainId);
                  buf.append(",").append(customerId);
                 buf.append(",").append(complainedOrgId);
                 buf.append(",").append(callBackDate);
                 buf.append(",").append(complainedPerson);
                 buf.append(",").append(callBackFlag);
                  buf.append(",").append(dtLastmod);
                  buf.append(",").append(currentWorkOrgID);
                 buf.append(dtCreate);
                 return buf.toString();
         }

         private java.util.Map map = new java.util.HashMap();

         public void put(String field) { map.put(field, Boolean.TRUE); }

         public java.util.Map getMap() { return this.map; }

		public int getCurrentWorkOrgID() {
			return currentWorkOrgID;
		}

		public void setCurrentWorkOrgID(int currentWorkOrgID) {
			this.currentWorkOrgID = currentWorkOrgID;
			put("currentWorkOrgID");
		}

}

