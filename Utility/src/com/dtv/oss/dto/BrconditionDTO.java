package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/** @todo Complete package & import here */

public class BrconditionDTO
    implements ReflectionSupport, java.io.Serializable {

  public String cntType;

  private int brcntID;

  private String cntName;

  private String cntValueStr;
  
/**
 * @return Returns the brcntID.
 */
public int getBrcntID() {
	return brcntID;
}
/**
 * @param brcntID The brcntID to set.
 */
public void setBrcntID(int brcntID) {
	this.brcntID = brcntID;
	//put("brcntID");
}
/**
 * @return Returns the cntName.
 */
public String getCntName() {
	return cntName;
}
/**
 * @param cntName The cntName to set.
 */
public void setCntName(String cntName) {
	this.cntName = cntName;
	put("cntName");
}
/**
 * @return Returns the cntType.
 */
public String getCntType() {
	return cntType;
}
/**
 * @param cntType The cntType to set.
 */
public void setCntType(String cntType) {
	this.cntType = cntType;
	put("cntType");
}
/**
 * @return Returns the cntValueStr.
 */
public String getCntValueStr() {
	return cntValueStr;
}
/**
 * @param cntValueStr The cntValueStr to set.
 */
public void setCntValueStr(String cntValueStr) {
	this.cntValueStr = cntValueStr;
	put("cntValueStr");
}
/**
 * @return Returns the status.
 */
public String getStatus() {
	return status;
}
/**
 * @param status The status to set.
 */
public void setStatus(String status) {
	this.status = status;
	put("status");
}
  private String status;

  private Timestamp dtCreate;

  private Timestamp dtLastmod;

  public BrconditionDTO() {
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

  public int hashCode() {
    return toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);

    buf.append(",").append(cntType);
    buf.append(",").append(cntValueStr);
    buf.append(",").append(cntName);
    buf.append(",").append(status);
    buf.append(",").append(brcntID);
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
