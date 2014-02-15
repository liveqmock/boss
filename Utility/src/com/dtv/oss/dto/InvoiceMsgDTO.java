package com.dtv.oss.dto;

import java.sql.Timestamp;

public class InvoiceMsgDTO
    implements ReflectionSupport, java.io.Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int invoiceMessageId;
  private String invoiceMsgName;
  private int invoicePrintFormat;
  private String invoiceMessage;
  private String customerType1;
  private String customerType2;
  private String customerType3;
  private String customerType4;
  private String customerType5;
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

  public int getInvoiceMessageId() {
    return invoiceMessageId;
  }

  public void setInvoiceMessageId(int invoiceMessageId) {
    this.invoiceMessageId = invoiceMessageId;
  }

  public String getInvoiceMsgName() {
    return invoiceMsgName;
  }

  public void setInvoiceMsgName(String invoiceMsgName) {
    this.invoiceMsgName = invoiceMsgName;
     put("invoiceMsgName");
  }

  public int getInvoicePrintFormat() {
    return invoicePrintFormat;
  }

  public void setInvoicePrintFormat(int invoicePrintFormat) {
    this.invoicePrintFormat = invoicePrintFormat;
     put("invoicePrintFormat");
  }

  public String getInvoiceMessage() {
    return invoiceMessage;
  }

  public void setInvoiceMessage(String invoiceMessage) {
    this.invoiceMessage = invoiceMessage;
     put("invoiceMessage");
  }

  public String getCustomerType1() {
    return customerType1;
  }

  public void setCustomerType1(String customerType1) {
    this.customerType1 = customerType1;
     put("customerType1");
  }

  public String getCustomerType2() {
    return customerType2;
  }

  public void setCustomerType2(String customerType2) {
    this.customerType2 = customerType2;
     put("customerType2");
  }

  public String getCustomerType3() {
    return customerType3;
  }

  public void setCustomerType3(String customerType3) {
    this.customerType3 = customerType3;
    put("customerType3");
  }

  public String getCustomerType4() {
    return customerType4;
  }

  public void setCustomerType4(String customerType4) {
    this.customerType4 = customerType4;
    put("customerType4");
  }

  public String getCustomerType5() {
    return customerType5;
  }

  public void setCustomerType5(String customerType5) {
    this.customerType5 = customerType5;
    put("customerType5");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        InvoiceMsgDTO that = (InvoiceMsgDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&

              this.getInvoiceMessageId()  == that.getInvoiceMessageId() &&
            ( ( (this.getInvoiceMsgName() == null) && (that.getInvoiceMsgName() == null)) ||
             (this.getInvoiceMsgName() != null &&
              this.getInvoiceMsgName().equals(that.getInvoiceMsgName()))) &&
            this.getInvoicePrintFormat() == that.getInvoicePrintFormat() &&
            ( ( (this.getInvoiceMessage() == null) && (that.getInvoiceMessage() == null)) ||
             (this.getInvoiceMessage() != null &&
              this.getInvoiceMessage().equals(that.getInvoiceMessage()))) &&
            ( ( (this.getCustomerType1() == null) && (that.getCustomerType1() == null)) ||
             (this.getCustomerType1() != null &&
              this.getCustomerType1().equals(that.getCustomerType1()))) &&
            ( ( (this.getCustomerType2() == null) && (that.getCustomerType2() == null)) ||
             (this.getCustomerType2() != null &&
              this.getCustomerType2().equals(that.getCustomerType2()))) &&
            ( ( (this.getCustomerType3() == null) && (that.getCustomerType3() == null)) ||
             (this.getCustomerType3() != null &&
              this.getCustomerType3().equals(that.getCustomerType3()))) &&
            ( ( (this.getCustomerType4() == null) && (that.getCustomerType4() == null)) ||
             (this.getCustomerType4() != null &&
              this.getCustomerType4().equals(that.getCustomerType4()))) &&
            ( ( (this.getCustomerType5() == null) && (that.getCustomerType5() == null)) ||
             (this.getCustomerType5() != null &&
              this.getCustomerType5().equals(that.getCustomerType5())));
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
     buf.append(invoiceMessageId);
     buf.append(",").append(invoiceMsgName);
     buf.append(",").append(invoicePrintFormat);
      buf.append(",").append(invoiceMessage);

     buf.append(",").append(customerType1);


     buf.append(",").append(customerType2);

     buf.append(",").append(customerType3);
     buf.append(",").append(customerType4);
     buf.append(",").append(customerType5);

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
