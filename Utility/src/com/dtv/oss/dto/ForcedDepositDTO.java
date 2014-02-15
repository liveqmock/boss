package com.dtv.oss.dto;


import java.sql.Timestamp;

public class ForcedDepositDTO implements ReflectionSupport, java.io.Serializable{
  private String status;
  private double money;
  private String description;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int seqNo;
  private int customerID;
  private int userID;
  private int csiID;
  private Timestamp fromDate;
  private Timestamp toDate;
  private String refersheetID;
  private int createOperator;
  private int createorgID;
  private Timestamp createTime;
  private int processOperatorID;
  private int processOrgID;
  private java.sql.Timestamp processTime;
  private double withdrawMoney;
  private double seizureMoney;
  private int withdrawCsiID;
  

  public ForcedDepositDTO()
        {
        }
  public int getWithdrawCsiID() {
    return withdrawCsiID;
  }

  public void setWithdrawCsiID(int withdrawCsiID) {
    this.withdrawCsiID = withdrawCsiID;
    put("withdrawCsiID");
  }
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
    put("status");
  }
  
  public double getMoney() {
    return money;
  }

  public void setMoney(double money) {
    this.money = money;
     put("money");
  }
  public int getProcessOperatorID() {
     return processOperatorID;
   }

   public void setProcessOperatorID(int processOperatorID) {
     this.processOperatorID = processOperatorID;
      put("processOperatorID");
   }
   public int getProcessOrgID() {
        return processOrgID;
      }

      public void setProcessOrgID(int processOrgID) {
        this.processOrgID = processOrgID;
         put("processOrgID");
      }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
      put("description");
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

  public int getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(int seqNo) {
    this.seqNo = seqNo;
  }

  public int getCustomerID() {
    return customerID;

  }

  public void setCustomerID(int customerID) {
    this.customerID = customerID;
     put("customerID");
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
     put("userID");
  }

  public int getCsiID() {
    return csiID;
  }

  public void setCsiID(int csiID) {
    this.csiID = csiID;
     put("csiID");
  }

  public Timestamp getFromDate() {
    return fromDate;
  }

  public void setFromDate(Timestamp fromDate) {
    this.fromDate = fromDate;
     put("fromDate");
  }

  public Timestamp getToDate() {
    return toDate;
  }

  public void setToDate(Timestamp toDate) {
    this.toDate = toDate;
      put("toDate");
  }

  public String getRefersheetID() {
    return refersheetID;
  }

  public void setRefersheetID(String refersheetID) {
    this.refersheetID = refersheetID;
     put("refersheetID");
  }

  public int getCreateOperator() {
    return createOperator;
  }

  public void setCreateOperator(int createOperator) {
    this.createOperator = createOperator;
     put("createOperator");
  }

  public int getCreateorgID() {
    return createorgID;
  }

  public void setCreateorgID(int createorgID) {
    this.createorgID = createorgID;
      put("createorgID");
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
     put("createTime");
  }




  public double getWithdrawMoney() {
    return withdrawMoney;
  }

  public void setWithdrawMoney(double withdrawMoney) {
    this.withdrawMoney = withdrawMoney;
    put("withdrawMoney");
  }

  public double getSeizureMoney() {
    return seizureMoney;
  }

  public void setSeizureMoney(double seizureMoney) {
    this.seizureMoney = seizureMoney;
     put("seizureMoney");
  }

  

  public Timestamp getProcessTime() {
    return processTime;
  }

  public void setProcessTime(Timestamp processTime) {
    this.processTime = processTime;
    put("processTime");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        ForcedDepositDTO that = (ForcedDepositDTO) obj;
        return ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
                (this.getStatus() != null &&
                 this.getStatus().equals(that.getStatus()))) &&
            this.getMoney() == that.getMoney() &&
            ( ( (this.getDescription() == null) && (that.getDescription() == null)) ||
             (this.getDescription() != null &&
              this.getDescription().equals(that.getDescription()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getSeqNo() ==  that.getSeqNo()  &&
            this.getCustomerID() == that.getCustomerID() &&
            this.getWithdrawCsiID() == that.getWithdrawCsiID() &&
            this.getUserID() == that.getUserID() &&
            this.getCsiID() == that.getCsiID() && ( (
            (this.getFromDate() == null) && (that.getFromDate() == null)) ||
            (this.getFromDate() != null &&
             this.getFromDate().equals(that.getFromDate()))) &&
            ( ( (this.getToDate() == null) && (that.getToDate() == null)) ||
             (this.getToDate() != null &&
              this.getToDate().equals(that.getToDate()))) &&
            ( ( (this.getRefersheetID() == null) && (that.getRefersheetID() == null)) ||
             (this.getRefersheetID() != null &&
              this.getRefersheetID().equals(that.getRefersheetID()))) &&
            this.getCreateOperator() == that.getCreateOperator() &&
            this.getCreateorgID() == that.getCreateorgID() &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) &&
			  this.getWithdrawMoney () == that.getWithdrawMoney(); 
           
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
        buf.append(status);
        buf.append(",").append(money);
        buf.append(",").append(description);
        buf.append(",").append(dtCreate);
        buf.append(",").append(dtLastmod);
        buf.append(",").append(seqNo);
        buf.append(",").append(createTime);
        buf.append(",").append(customerID);
        buf.append(",").append(userID);
        buf.append(",").append(fromDate);
        buf.append(",").append(toDate);
        buf.append(",").append(refersheetID);
        buf.append(",").append(createOperator);
        buf.append(",").append(createorgID);
        buf.append(",").append(customerID);
        buf.append(",").append(dtCreate);
        buf.append(",").append(dtLastmod);
        buf.append(",").append(withdrawMoney);
        buf.append(",").append(seizureMoney);
        buf.append(",").append(withdrawCsiID);
        buf.append(",").append(seizureMoney);
        return buf.toString();
     }

  private java.util.Map map = new java.util.HashMap();

  public void put(String field) { map.put(field, Boolean.TRUE); }

  public java.util.Map getMap() { return this.map; }

}
