package com.dtv.oss.dto;

import java.sql.Timestamp;

public class NewCustomerMarketInfoDTO
    implements  ReflectionSupport, java.io.Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int newCustomerId;
  private int infoSettingId;
  private int infoValueId;
  private String memo;
  private int ID; 
  private int csiID;
  
/**
 * @return Returns the csiID.
 */
public int getCsiID() {
	return csiID;
}
/**
 * @param csiID The csiID to set.
 */
public void setCsiID(int csiID) {
	this.csiID = csiID;
	put("csiID");
}
/**
 * @return Returns the iD.
 */
public int getID() {
	return ID;
}
/**
 * @param id The iD to set.
 */
public void setID(int id) {
	ID = id;
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

  public int getNewCustomerId() {
    return newCustomerId;
  }

  public void setNewCustomerId(int newCustomerId) {
    this.newCustomerId = newCustomerId;
    put("newCustomerId");
  }

  public int getInfoSettingId() {
    return infoSettingId;
  }

  public void setInfoSettingId(int infoSettingId) {
    this.infoSettingId = infoSettingId;
    put("infoSettingId");
  }

  public int getInfoValueId() {
    return infoValueId;
  }

  public void setInfoValueId(int infoValueId) {
    this.infoValueId = infoValueId;
    put("infoValueId");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        NewCustomerMarketInfoDTO that = (NewCustomerMarketInfoDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getNewCustomerId() == that.getNewCustomerId() &&
             this.getInfoSettingId() == that.getInfoSettingId()  &&
             this.getID() == that.getID()  &&
             this.getCsiID() == that.getCsiID()  &&
             ( ( (this.getMemo() == null) && (that.getMemo() == null)) ||
                    (this.getMemo() != null &&
                     this.getMemo().equals(that.getMemo())));
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

      buf.append(",").append(infoSettingId);
      buf.append(",").append(newCustomerId);
      buf.append(",").append(ID);
      buf.append(",").append(csiID);
      buf.append(",").append(infoValueId);

      buf.append(",").append(dtCreate);
      buf.append(",").append(dtLastmod);
      buf.append(",").append(memo);
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

