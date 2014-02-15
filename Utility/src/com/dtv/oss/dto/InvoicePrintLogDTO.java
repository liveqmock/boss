package com.dtv.oss.dto;

import java.sql.Timestamp;

public class InvoicePrintLogDTO
    implements ReflectionSupport, java.io.Serializable {
  private int id;
  private String status;
  private int totalrecnum;
  private int processedrecnum;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int invoicePrintInterfaceId;
  private String processType;
  private String fileUrl;
  private int operatorId;
  private Timestamp ioDateTime;
  private Timestamp ioProcessTime;
  private String mopName;
  private String invoiceStatus;
  private int billingCycleId;
  private Timestamp invoiceCreateDateFrom;
  private Timestamp invoiceCreateDateTo;
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
     put("status");
  }

  public int getTotalrecnum() {
    return totalrecnum;
  }

  public void setTotalrecnum(int totalrecnum) {
    this.totalrecnum = totalrecnum;
      put("totalrecnum");
  }

  public int getProcessedrecnum() {
    return processedrecnum;
  }

  public void setProcessedrecnum(int processedrecnum) {
    this.processedrecnum = processedrecnum;
     put("processedrecnum");
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

  public int getInvoicePrintInterfaceId() {
    return invoicePrintInterfaceId;
  }

  public void setInvoicePrintInterfaceId(int invoicePrintInterfaceId) {
    this.invoicePrintInterfaceId = invoicePrintInterfaceId;
      put("invoicePrintInterfaceId");
  }

  public String getProcessType() {
    return processType;
  }

  public void setProcessType(String processType) {
    this.processType = processType;
     put("processType");
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
      put("fileUrl");
  }

  public int getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(int operatorId) {
    this.operatorId = operatorId;
     put("operatorId");
  }

  public Timestamp getIoDateTime() {
    return ioDateTime;
  }

  public void setIoDateTime(Timestamp ioDateTime) {
    this.ioDateTime = ioDateTime;
    put("ioDateTime");
  }

  public Timestamp getIoProcessTime() {
    return ioProcessTime;
  }

  public void setIoProcessTime(Timestamp ioProcessTime) {
    this.ioProcessTime = ioProcessTime;
     put("ioProcessTime");
  }

  public String getMopName() {
    return mopName;
  }

  public void setMopName(String mopName) {
    this.mopName = mopName;
    put("mopName");
  }

  public String getInvoiceStatus() {
    return invoiceStatus;
  }

  public void setInvoiceStatus(String invoiceStatus) {
    this.invoiceStatus = invoiceStatus;
      put("invoiceStatus");
  }

  public int getBillingCycleId() {
    return billingCycleId;
  }

  public void setBillingCycleId(int billingCycleId) {
    this.billingCycleId = billingCycleId;
      put("billingCycleId");
  }

  public Timestamp getInvoiceCreateDateFrom() {
    return invoiceCreateDateFrom;
  }

  public void setInvoiceCreateDateFrom(Timestamp invoiceCreateDateFrom) {
    this.invoiceCreateDateFrom = invoiceCreateDateFrom;
    put("invoiceCreateDateFrom");
  }

  public Timestamp getInvoiceCreateDateTo() {
    return invoiceCreateDateTo;
  }

  public void setInvoiceCreateDateTo(Timestamp invoiceCreateDateTo) {
    this.invoiceCreateDateTo = invoiceCreateDateTo;
     put("invoiceCreateDateTo");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        InvoicePrintLogDTO that = (InvoicePrintLogDTO) obj;
        return
                this.getId() == that.getId()  &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            this.getTotalrecnum() == that.getTotalrecnum() &&
            this.getProcessedrecnum() == that.getProcessedrecnum() &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getInvoicePrintInterfaceId() ==
            that.getInvoicePrintInterfaceId() &&
            ( ( (this.getProcessType() == null) && (that.getProcessType() == null)) ||
             (this.getProcessType() != null &&
              this.getProcessType().equals(that.getProcessType()))
             ) &&
            ( ( (this.getFileUrl() == null) && (that.getFileUrl() == null)) ||
             (this.getFileUrl() != null &&
              this.getFileUrl().equals(that.getFileUrl()))) &&
            this.getOperatorId() == that.getOperatorId() &&
            ( ( (this.getIoDateTime() == null) && (that.getIoDateTime() == null)) ||
             (this.getIoDateTime() != null &&
              this.getIoDateTime().equals(that.getIoDateTime()))) &&
            ( ( (this.getIoProcessTime() == null) && (that.getIoProcessTime() == null)) ||
             (this.getIoProcessTime() != null &&
              this.getIoProcessTime().equals(that.getIoProcessTime()))) &&
            ( ( (this.getMopName() == null) && (that.getMopName() == null)) ||
             (this.getMopName() != null &&
              this.getMopName().equals(that.getMopName()))) &&
            ( ( (this.getInvoiceStatus() == null) && (that.getInvoiceStatus() == null)) ||
             (this.getInvoiceStatus() != null &&
              this.getInvoiceStatus().equals(that.getInvoiceStatus()))) &&
            this.getBillingCycleId() == that.getBillingCycleId() &&
            ( ( (this.getInvoiceCreateDateFrom() == null) &&
               (that.getInvoiceCreateDateFrom() == null)) ||
             (this.getInvoiceCreateDateFrom
              () != null &&
              this.getInvoiceCreateDateFrom().equals(that.getInvoiceCreateDateFrom()))) &&
            ( ( (this.getInvoiceCreateDateTo() == null) &&
               (that.getInvoiceCreateDateTo() == null)) ||
             (this.getInvoiceCreateDateTo() != null &&
              this.getInvoiceCreateDateTo().equals(that.getInvoiceCreateDateTo())));
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
      buf.append(id);
      buf.append(",").append(status);
      buf.append(",").append(totalrecnum);
       buf.append(",").append(processedrecnum);
      buf.append(",").append(invoicePrintInterfaceId);
      buf.append(",").append(ioProcessTime);
      buf.append(",").append(mopName);
      buf.append(",").append(invoiceStatus);
      buf.append(",").append(billingCycleId);
      buf.append(",").append(invoiceCreateDateFrom);
      buf.append(",").append(invoiceCreateDateTo);


      buf.append(",").append(processType);

      buf.append(",").append(fileUrl);
      buf.append(",").append(operatorId);
      buf.append(",").append(ioDateTime);

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

