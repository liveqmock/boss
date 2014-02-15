package com.dtv.oss.dto;

import java.sql.Timestamp;

public class SendMessageDTO
    implements ReflectionSupport, java.io.Serializable {
  private String content;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private Integer messageId;
  private int orgId;
  private int operatorId;
  private Timestamp createTime;
  private Timestamp processTime;
  private String sendType;
  private String sourceType;
  private String moduleName;
  private int customerId;
  private int serviceAccountId;
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
     put("content");
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

  public Integer getMessageId() {
    return messageId;
  }

  public void setMessageId(Integer messageId) {
    this.messageId = messageId;
     put("messageId");
  }

  public int getOrgId() {
    return orgId;
  }

  public void setOrgId(int orgId) {
    this.orgId = orgId;
     put("orgId");
  }

  public int getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(int operatorId) {
    this.operatorId = operatorId;
      put("operatorId");
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getProcessTime() {
    return processTime;
  }

  public void setProcessTime(Timestamp processTime) {
    this.processTime = processTime;
     put("processTime");
  }

  public String getSendType() {
    return sendType;
  }

  public void setSendType(String sendType) {
    this.sendType = sendType;
     put("sendType");
  }

  public String getSourceType() {
    return sourceType;
  }

  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
      put("sourceType");
  }

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
     put("moduleName");
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
       put("customerId");
  }

  public int getServiceAccountId() {
    return serviceAccountId;
  }

  public void setServiceAccountId(int serviceAccountId) {
    this.serviceAccountId = serviceAccountId;
       put("serviceAccountId");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        SendMessageDTO that = (SendMessageDTO) obj;
        return ( ( (this.getContent() == null) && (that.getContent() == null)) ||
                (this.getContent() != null &&
                 this.getContent().equals(that.getContent()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            ( ( (this.getMessageId() == null) && (that.getMessageId() == null)) ||
             (this.getMessageId() != null &&
              this.getMessageId().equals(that.getMessageId()))) &&
            this.getOrgId() == that.getOrgId() &&
            this.getOperatorId() == that.getOperatorId() &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime
              () != null && this.getCreateTime().equals(that.getCreateTime()))) &&
            ( ( (this.getProcessTime() == null) && (that.getProcessTime() == null)) ||
             (this.getProcessTime() != null &&
              this.getProcessTime().equals(that.getProcessTime()))) &&
            ( ( (this.getSendType() == null) && (that.getSendType() == null)) ||
             (this.getSendType() != null &&
              this.getSendType().equals(that.getSendType()))) &&
            ( ( (this.getSourceType() == null) && (that.getSourceType() == null)) ||
             (this.getSourceType() != null &&
              this.getSourceType().equals(that.getSourceType()))) &&
            ( ( (this.getModuleName() == null) && (that.getModuleName() == null)) ||
             (this.getModuleName() != null &&
              this.getModuleName().equals(that.getModuleName()))) &&
            this.getCustomerId() == that.getCustomerId() &&
            this.getServiceAccountId() == that.getServiceAccountId();
      }
    }
    return false;
  }

  public int hashCode() {
    return  toString(). hashCode();
  }


  public String toString() {
       StringBuffer buf = new StringBuffer(256);
       buf.append(content);
       buf.append(",").append(status);
       buf.append(",").append(messageId);
       buf.append(",").append(orgId);
       buf.append(",").append(operatorId);
       buf.append(",").append(createTime);
       buf.append(",").append(orgId);
       buf.append(",").append(processTime);

       buf.append(",").append(sendType);
       buf.append(",").append(sourceType);
       buf.append(",").append(moduleName);
       buf.append(",").append(customerId);
       buf.append(",").append(serviceAccountId);
       buf.append(",").append(dtCreate);
       buf.append(",").append(dtLastmod);

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


