package com.dtv.oss.dto;

import java.sql.Timestamp;

public class FinanceSetOffMapDTO
    implements  ReflectionSupport, java.io.Serializable {
  private String sType;
  private double value;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int seqNo;
  private Timestamp createTime;
  private int opId;
  private int orgId;
  private int custId;
  private int acctId;
  private String plusReferType;
  private int plusReferId;
  private String minusReferType;
  private int minusReferId;
  private String comments;
  /**
	 * @return Returns the comments.
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments The comments to set.
	 */
	public void setComments(String comments) {
		this.comments = comments;
		 put("comments");
	}
  public String getSType() {
    return sType;
  }

  public void setSType(String sType) {
    this.sType = sType;
    put("sType");
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
      put("value");
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

  public int getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(int seqNo) {
    this.seqNo = seqNo;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public int getOpId() {
    return opId;
  }

  public void setOpId(int opId) {
    this.opId = opId;
     put("opId");
  }

  public int getOrgId() {
    return orgId;
  }

  public void setOrgId(int orgId) {
    this.orgId = orgId;
     put("orgId");
  }

  public int getCustId() {
    return custId;
  }

  public void setCustId(int custId) {
    this.custId = custId;
     put("custId");
  }

  public int getAcctId() {
    return acctId;
  }

  public void setAcctId(int acctId) {
    this.acctId = acctId;
     put("acctId");
  }

  public String getPlusReferType() {
    return plusReferType;
  }

  public void setPlusReferType(String plusReferType) {
    this.plusReferType = plusReferType;
    put("plusReferType");
  }

  public int getPlusReferId() {
    return plusReferId;
  }

  public void setPlusReferId(int plusReferId) {
    this.plusReferId = plusReferId;
      put("plusReferId");
  }

  public String getMinusReferType() {
    return minusReferType;
  }

  public void setMinusReferType(String minusReferType) {
    this.minusReferType = minusReferType;
     put("minusReferType");
  }

  public int getMinusReferId() {
    return minusReferId;
  }

  public void setMinusReferId(int minusReferId) {
    this.minusReferId = minusReferId;
     put("minusReferId");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        FinanceSetOffMapDTO that = (FinanceSetOffMapDTO) obj;
        return ( ( (this.getSType() == null) && (that.getSType() == null)) ||
                (this.getSType() != null && this.getSType().equals(that.getSType()))) &&
            this.getValue() == that.getValue() &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getSeqNo()  == that.getSeqNo() &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) &&
            this.getOpId() == that.getOpId() &&
            this.getOrgId() == that.getOrgId() &&
            this.getCustId() == that.getCustId() &&
            this.getAcctId() == that.getAcctId() &&
            ( ( (this.getPlusReferType() == null) && (that.getPlusReferType() == null)) ||
             (this.getPlusReferType() != null &&
              this.getPlusReferType().equals(that.getPlusReferType()))) &&
            this.getPlusReferId() == that.getPlusReferId() &&
            ( ( (this.getMinusReferType() == null) && (that.getMinusReferType() == null)) ||
             (this.getMinusReferType() != null &&
              this.getMinusReferType().equals(that.getMinusReferType()))) &&
            this.getMinusReferId() == that.getMinusReferId();
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
                   buf.append(sType);
                   buf.append(",").append(sType);
                   buf.append(",").append(status);
                   buf.append(",").append(value);
                   buf.append(",").append(seqNo);

                   buf.append(",").append(createTime);
                  buf.append(",").append(opId);
                   buf.append(",").append(orgId);
                   buf.append(",").append(custId);
                   buf.append(",").append(acctId);
                   buf.append(",").append(plusReferType);
                    buf.append(",").append(plusReferId);
                    buf.append(",").append(minusReferType);
                  buf.append(",").append(minusReferId);
                  buf.append(",").append(comments);

                   buf.append(",").append(dtCreate);
                   buf.append(",").append(dtLastmod);
                   return buf.toString();
           }

           private java.util.Map map = new java.util.HashMap();

           public void put(String field) { map.put(field, Boolean.TRUE); }

           public java.util.Map getMap() { return this.map; }

   }


