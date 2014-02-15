package com.dtv.oss.dto;

import java.sql.Timestamp;
import com.dtv.oss.dto.ReflectionSupport;

public class ValidDateChangeHistDTO implements ReflectionSupport, java.io.Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private Integer sequenceNo;
  private int customerId;
  private int custAdditionInfoId;
  private Timestamp validBeginDate;
  private Timestamp validEndDate;
  private Timestamp createDate;
  private int operatorId;
  public ValidDateChangeHistDTO(){
  	
  }
  public ValidDateChangeHistDTO(Timestamp createDate,Integer sequenceNo,int custAdditionInfoId,int customerId,int operatorId,Timestamp validBeginDate,Timestamp validEndDate,Timestamp dtCreate,Timestamp dtLastmod){
    setSequenceNo(sequenceNo);
    setCustomerId(customerId);
    setDtCreate(dtCreate);
    setDtLastmod(dtLastmod);
    setCustAdditionInfoId(custAdditionInfoId);
    setOperatorId(operatorId);
    setValidBeginDate(validBeginDate);
    setValidEndDate(validEndDate);
    setCreateDate(createDate);
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

  public Integer getSequenceNo() {
    return sequenceNo;
  }

  public void setSequenceNo(Integer sequenceNo) {
    this.sequenceNo = sequenceNo;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public int getCustAdditionInfoId() {
    return custAdditionInfoId;
  }

  public void setCustAdditionInfoId(int custAdditionInfoId) {
    this.custAdditionInfoId = custAdditionInfoId;
  }

  public Timestamp getValidBeginDate() {
    return validBeginDate;
  }

  public void setValidBeginDate(Timestamp validBeginDate) {
    this.validBeginDate = validBeginDate;
  }

  public Timestamp getValidEndDate() {
    return validEndDate;
  }

  public void setValidEndDate(Timestamp validEndDate) {
    this.validEndDate = validEndDate;
  }

  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
  }

  public int getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(int operatorId) {
    this.operatorId = operatorId;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        ValidDateChangeHistDTO that = (ValidDateChangeHistDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            ( ( (this.getSequenceNo() == null) && (that.getSequenceNo() == null)) ||
             (this.getSequenceNo() != null &&
              this.getSequenceNo().equals(that.getSequenceNo()))) &&
            this.getCustomerId() == that.getCustomerId() &&
            this.getCustAdditionInfoId() == that.getCustAdditionInfoId() &&
            ( ( (this.getValidBeginDate() == null) && (that.getValidBeginDate() == null)) ||
             (this.getValidBeginDate() != null &&
              this.getValidBeginDate().equals(that.getValidBeginDate()))) &&
            ( ( (this.getValidEndDate() == null) && (that.getValidEndDate() == null)) ||
             (this.getValidEndDate() != null &&
              this.getValidEndDate().equals(that
                                            .getValidEndDate()))) &&
            ( ( (this.getCreateDate() == null) && (that.getCreateDate() == null)) ||
             (this.getCreateDate() != null &&
              this.getCreateDate().equals(that.getCreateDate()))) &&
            this.getOperatorId() == that.getOperatorId();
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
                buf.append(sequenceNo);
                buf.append(",").append(operatorId);
                buf.append(",").append(dtCreate);
                buf.append(",").append(dtLastmod);
                buf.append(",").append(customerId);
                buf.append(",").append(custAdditionInfoId);
                buf.append(",").append(validBeginDate);
                buf.append(",").append(validEndDate);
                buf.append(",").append(createDate);


                return buf.toString();
        }

        private java.util.Map map = new java.util.HashMap();

        public void put(String field) { map.put(field, Boolean.TRUE); }

        public java.util.Map getMap() { return this.map; }

}
