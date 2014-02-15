package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CustMarketInfoDTO
    implements ReflectionSupport, java.io.Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int customerId;
  private int infoValueId;
  private int infoSettingId;
  private int id;
  private String memo;
/**
 * @return Returns the id.
 */
public int getId() {
	return id;
}
/**
 * @param id The id to set.
 */
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

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public int getInfoValueId() {
    return infoValueId;
  }

  public void setInfoValueId(int infoValueId) {
    this.infoValueId = infoValueId;
    put("infoValueId");
    
  }

  public int getInfoSettingId() {
    return infoSettingId;
  }

  public void setInfoSettingId(int infoSettingId) {
    this.infoSettingId = infoSettingId;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CustMarketInfoDTO that = (CustMarketInfoDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
                 ( ( (this.getMemo() == null) && (that.getMemo() == null)) ||
               (this.getMemo() != null && this.getMemo().equals(that.getMemo()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getCustomerId() == that.getCustomerId() &&
            this.getInfoValueId() == that.getInfoValueId() &&
            
            this.getInfoSettingId() == that.getInfoSettingId();
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
                   buf.append(dtCreate);
                   buf.append(",").append(dtLastmod);
                   buf.append(",").append(customerId);
                   buf.append(",").append(infoSettingId);
                   buf.append(",").append(memo);
                   buf.append(",").append(id);
                   buf.append(infoValueId);

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


