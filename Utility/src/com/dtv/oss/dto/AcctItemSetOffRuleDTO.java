package com.dtv.oss.dto;

import java.sql.Timestamp;

public class AcctItemSetOffRuleDTO
  implements ReflectionSupport, java.io.Serializable {
  private int seqNo;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int sType;
  private String srcAcctItemTypeId;
  private String destAcctItemTypeId;
  public int getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(int seqNo) {
    this.seqNo = seqNo;
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

  public int getSType() {
    return sType;
  }

  public void setSType(int sType) {
    this.sType = sType;
    put("sType");
  }

  public String getSrcAcctItemTypeId() {
    return srcAcctItemTypeId;
  }

  public void setSrcAcctItemTypeId(String srcAcctItemTypeId) {
    this.srcAcctItemTypeId = srcAcctItemTypeId;
     put("srcAcctItemTypeId");
  }

  public String getDestAcctItemTypeId() {
    return destAcctItemTypeId;
  }

  public void setDestAcctItemTypeId(String destAcctItemTypeId) {
    this.destAcctItemTypeId = destAcctItemTypeId;
     put("destAcctItemTypeId");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        AcctItemSetOffRuleDTO that = (AcctItemSetOffRuleDTO) obj;
        return this.getSeqNo() == that.getSeqNo()  &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getSType() == that.getSType() &&
            ( ( (this.getSrcAcctItemTypeId() == null) &&
               (that.getSrcAcctItemTypeId() == null)) ||
             (this.getSrcAcctItemTypeId() != null &&
              this.getSrcAcctItemTypeId().equals(that.getSrcAcctItemTypeId()))) &&
            ( ( (this.getDestAcctItemTypeId() == null) &&
               (that.getDestAcctItemTypeId() == null)) ||
             (this.getDestAcctItemTypeId() != null &&
              this.getDestAcctItemTypeId().equals(that.getDestAcctItemTypeId())));
      }
    }
    return false;
  }



  public String toString() {
     StringBuffer buf = new StringBuffer(256);
     buf.append(seqNo);
     buf.append(",").append(dtCreate);
     buf.append(",").append(sType);
     buf.append(",").append(dtLastmod);
     buf.append(",").append(srcAcctItemTypeId);
     buf.append(destAcctItemTypeId);

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
