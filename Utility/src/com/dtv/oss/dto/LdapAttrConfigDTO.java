package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/** @todo Complete package & import here */

public class LdapAttrConfigDTO
    implements ReflectionSupport, java.io.Serializable {

  private String attrName;
  
  private String fixedValue;
  
  private String  fixedFlag;

  private String prefix;

  private int length;
  
  private String status;

  private Timestamp dtCreate;

  private Timestamp dtLastmod;


/**
 * @return Returns the fixedFlag.
 */
public String getFixedFlag() {
	return fixedFlag;
}
/**
 * @param fixedFlag The fixedFlag to set.
 */
public void setFixedFlag(String fixedFlag) {
	this.fixedFlag = fixedFlag;
	 put("fixedFlag");
}
/**
 * @return Returns the fixedValue.
 */
public String getFixedValue() {
	return fixedValue;
}
/**
 * @param fixedValue The fixedValue to set.
 */
public void setFixedValue(String fixedValue) {
	this.fixedValue = fixedValue;
	 put("fixedValue");
}
/**
 * @return Returns the length.
 */
public int getLength() {
	return length;
}
/**
 * @param length The length to set.
 */
public void setLength(int length) {
	this.length = length;
	 put("length");
}
/**
 * @return Returns the prefix.
 */
public String getPrefix() {
	return prefix;
}
/**
 * @param prefix The prefix to set.
 */
public void setPrefix(String prefix) {
	this.prefix = prefix;
	 put("prefix");
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
 
  public LdapAttrConfigDTO() {
  }

  public void setAttrName(String attrName) {
    this.attrName = attrName;
   

  }

  public String getAttrName() {
    return attrName;
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

    buf.append(",").append(attrName);
    buf.append(",").append(fixedFlag);
    buf.append(",").append(fixedValue);
    buf.append(",").append(length);
    buf.append(",").append(status);
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
