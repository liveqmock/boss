package com.dtv.oss.dto;

import java.sql.Timestamp;

public class DiagnosisInfoDTO
    implements ReflectionSupport, java.io.Serializable {
  private int id;
  private String referSourceType ;
  private int infoValueId;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int infoSettingId;
  private int referSourceId;
  private String memo;
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getReferSourceType () {
    return referSourceType ;
  }

  public void setReferSourceType (String referSourceType ) {
    this.referSourceType  = referSourceType ;
     put("referSourceType ");
  }

  public int getInfoValueId() {
    return infoValueId;
  }

  public void setInfoValueId(int infoValueId) {
    this.infoValueId = infoValueId;
    put("infoValueId");
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

  public int getInfoSettingId() {
    return infoSettingId;
  }

  public void setInfoSettingId(int infoSettingId) {
    this.infoSettingId = infoSettingId;
   put("infoSettingId");
  }

  public int getReferSourceId() {
    return referSourceId;
  }

  public void setReferSourceId(int referSourceId) {
    this.referSourceId = referSourceId;
      put("referSourceId");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        DiagnosisInfoDTO that = (DiagnosisInfoDTO) obj;
        return  this.getId()  == that.getId()  &&
            ( ( (this.getReferSourceType() == null) && (that.getReferSourceType() == null)) ||
             (this.getReferSourceType() != null && this.getReferSourceType().equals(that.getReferSourceType()))) &&
             this.getInfoValueId() == that.getInfoValueId() &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
              ( ( (this.getMemo() == null) && (that.getMemo() == null)) ||
                    (this.getMemo() != null && this.getMemo().equals(that.getMemo()))) &&
            this.getInfoSettingId() == that.getInfoSettingId() &&
            this.getReferSourceId() == that.getReferSourceId();
        
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
                  buf.append(id);
                  buf.append(",").append(referSourceType);
                  buf.append(",").append(infoValueId);
                  buf.append(",").append(infoSettingId);
                  buf.append(",").append(referSourceId);
                  buf.append(",").append(memo);

                  buf.append(",").append(dtCreate);
                  buf.append(",").append(dtLastmod);
                  return buf.toString();
          }

          private java.util.Map map = new java.util.HashMap();

          public void put(String field) { map.put(field, Boolean.TRUE); }

          public java.util.Map getMap() { return this.map; }

          /**
           * @return Returns the memo.
           */
          public String getMemo() {
          	return memo;
          }
          /**
           * @param memo The memo to set.
           */
          public void setMemo(String memo) {
          	this.memo = memo;
          	put("memo");
          }
             }


