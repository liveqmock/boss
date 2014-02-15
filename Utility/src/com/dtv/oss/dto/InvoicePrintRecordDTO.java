package com.dtv.oss.dto;

import java.sql.Timestamp;

public class InvoicePrintRecordDTO
    implements ReflectionSupport, java.io.Serializable {
  private int id;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int invoiceNo;
  private int printFormatId;
  private Timestamp printDate;
  private String taxIdentify;
  private int operatorId;
  private int orgId;
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public int getInvoiceNo() {
    return invoiceNo;
  }

  public void setInvoiceNo(int invoiceNo) {
    this.invoiceNo = invoiceNo;
     put("invoiceNo");
  }

  public int getPrintFormatId() {
    return printFormatId;
  }

  public void setPrintFormatId(int printFormatId) {
    this.printFormatId = printFormatId;
    put("printFormatId");
  }

  public Timestamp getPrintDate() {
    return printDate;
  }

  public void setPrintDate(Timestamp printDate) {
    this.printDate = printDate;
     put("printDate");
  }

  public String getTaxIdentify() {
    return taxIdentify;
  }

  public void setTaxIdentify(String taxIdentify) {
    this.taxIdentify = taxIdentify;
     put("taxIdentify");
  }

  public int getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(int operatorId) {
    this.operatorId = operatorId;
     put("operatorId");
  }

  public int getOrgId() {
    return orgId;
  }

  public void setOrgId(int orgId) {
    this.orgId = orgId;
     put("orgId");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        InvoicePrintRecordDTO that = (InvoicePrintRecordDTO) obj;
        return
            this.getId() == that.getId() &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getInvoiceNo() == that.getInvoiceNo() &&
            this.getPrintFormatId() == that.getPrintFormatId() &&
            ( ( (this.getPrintDate() == null) && (that.getPrintDate() == null)) ||
             (this.getPrintDate() != null &&
              this.getPrintDate().equals(that.getPrintDate()))) &&
            ( ( (this.getTaxIdentify() == null) && (that.getTaxIdentify() == null)) ||
             (this.getTaxIdentify() != null &&
              this.getTaxIdentify().equals(that.getTaxIdentify()))) &&
            this.getOperatorId() == that.getOperatorId() && this.getOrgId
            () == that.getOrgId();
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
    buf.append(",").append(invoiceNo);
    buf.append(",").append(printFormatId);
    buf.append(",").append(printDate);
    buf.append(",").append(taxIdentify);
    buf.append(",").append(operatorId);
    buf.append(",").append(orgId);
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
