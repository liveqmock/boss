package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class AccountAdjustmentDTO
    implements ReflectionSupport, java.io.Serializable {

  private int createOrgID;
  private Timestamp createTime;
  private String reason;
  private String status;
  private int custID;
  private int acctID;
  private int seqNo;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private Timestamp adjustmentDate;
  private String comments;
  private String adjustmentType;
  private int referRecordID;
  private int createOpID;
  private String referRecordType;
  private int referSheetID; 
  
  public AccountAdjustmentDTO() {
  }
/**
 * @return Returns the referSheetID.
 */
public int getReferSheetID() {
	return referSheetID;
}
/**
 * @param referSheetID The referSheetID to set.
 */
public void setReferSheetID(int referSheetID) {
	this.referSheetID = referSheetID;
	 put("referSheetID");
}
  public void setAdjustmentType(String adjustmentType) {
    this.adjustmentType = adjustmentType;
    put("adjustmentType");
  }

  public String getAdjustmentType() {
    return adjustmentType;
  }

  public void setCreateOpID(int createOpID) {
      this.createOpID = createOpID;
      put("createOpID");
    }

    public int getCreateOpID() {
      return createOpID;
    }

  public void setCreateOrgID(int createOrgID) {
    this.createOrgID = createOrgID;
    put("createOrgID");
  }

  public int getCreateOrgID() {
    return createOrgID;
  }

  public void setReferRecordID(int referRecordID) {
    this.referRecordID = referRecordID;
    put("referRecordID");

  }

  public int getReferRecordID() {
    return referRecordID;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
    put("createTime");
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setStatus(String status) {
    this.status = status;
    put("status");
  }

  public String getStatus() {
    return status;
  }

  public void setCustID(int custID) {
    this.custID = custID;
    put("custID");
  }

  public int getCustID() {
    return custID;
  }

  public void setAcctID(int acctID) {
    this.acctID = acctID;
    put("acctID");
  }

  public int getAcctID() {
    return acctID;
  }

  public void setComments(String comments) {
    this.comments = comments;
    put("comments");
  }

  public String getComments() {
    return comments;
  }
  public void setReason(String reason) {
     this.reason = reason;
     put("reason");
   }

   public String getReason() {
     return reason;
   }

  public void setSeqNo(int seqNo) {
    this.seqNo = seqNo;
    //put("seqNo");
  }

  public int getSeqNo() {
    return seqNo;
  }

  public void setDtCreate(Timestamp dtCreate) {
    this.dtCreate = dtCreate;
  }

  public Timestamp getDtCreate() {
    return dtCreate;
  }

  public void setDtLastmod(Timestamp dtLastmod) {
    this.dtLastmod = dtLastmod;
  }

  public Timestamp getDtLastmod() {
    return dtLastmod;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        AccountAdjustmentDTO that = (AccountAdjustmentDTO) obj;
        return
            this.getCreateOrgID() == that.getCreateOrgID() &&
            this.getReferRecordID() == that.getReferRecordID() &&
            this.getCreateOpID() == that.getCreateOpID() &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) &&
              this.getReferRecordID() == that.getReferRecordID() &&
            ( ( (this.getReason() == null) && (that.getReason() == null)) ||
             (this.getReason() != null &&
              this.getReason().equals(that.getReason()))) &&
             ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
             ( ( (this.getComments() == null) && (that.getComments() == null)) ||
             (this.getComments() != null &&
              this.getComments().equals(that.getComments()))) &&
             this.getCustID() == that.getCustID() &&
             this.getAcctID() == that.getAcctID() &&
             this.getSeqNo() == that.getSeqNo() &&
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
    return toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
    buf.append(createOrgID);
    buf.append(",").append(createTime);
    buf.append(",").append(reason);
    buf.append(",").append(status);
    buf.append(",").append(custID);
    buf.append(",").append(acctID);
    buf.append(",").append(seqNo);
    buf.append(",").append(dtCreate);
    buf.append(",").append(dtLastmod);
    buf.append(",").append(comments);
    buf.append(",").append(adjustmentType);
    buf.append(",").append(referRecordID);
    buf.append(",").append(createOpID);
    buf.append(",").append(referRecordType);
    buf.append(",").append(referSheetID);
    return buf.toString();
  }

  private java.util.Map map = new java.util.HashMap();

  public void put(String field) {
    map.put(field, Boolean.TRUE);
  }

  public java.util.Map getMap() {
    return this.map;
  }
/**
 * @return Returns the referRecordType.
 */
public String getReferRecordType() {
	return referRecordType;
}
/**
 * @param referRecordType The referRecordType to set.
 */
public void setReferRecordType(String referRecordType) {
	this.referRecordType = referRecordType;
}
public Timestamp getAdjustmentDate() {
	return adjustmentDate;
}
public void setAdjustmentDate(Timestamp adjustmentDate) {
	this.adjustmentDate = adjustmentDate;
}
}
