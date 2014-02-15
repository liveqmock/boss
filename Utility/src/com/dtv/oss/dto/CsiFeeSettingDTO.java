package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CsiFeeSettingDTO
implements ReflectionSupport, java.io.Serializable{
  private String csiType;
  private int eventClass;
  private String installationType;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  public String getCsiType() {
    return csiType;
  }

  public void setCsiType(String csiType) {
    this.csiType = csiType;
    
  }

  public int getEventClass() {
    return eventClass;
  }

  public void setEventClass(int eventClass) {
    this.eventClass = eventClass;
     
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CsiFeeSettingDTO that = (CsiFeeSettingDTO) obj;
        return ( ( (this.getCsiType() == null) && (that.getCsiType() == null)) ||
                (this.getCsiType() != null &&
                 this.getCsiType().equals(that.getCsiType()))) &&
                 ( ( (this.getInstallationType() == null) && (that.getInstallationType() == null)) ||
                      (this.getInstallationType() != null &&
                       this.getInstallationType().equals(that.getInstallationType()))) &&
                     ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
                         (this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&	
                         (((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
        						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
        					(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
        						(this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod())))&&			 
								 
            this.getEventClass() == that.getEventClass();
      }
    }
    return false;
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
               buf.append(csiType);
               buf.append(",").append(eventClass);
               buf.append(",").append(installationType);
               buf.append(",").append(status);
               buf.append(",").append(dtCreate);
       		   buf.append(",").append(dtLastmod);

    return buf.toString();
  }
/**
 * @return Returns the dtCreate.
 */
public Timestamp getDtCreate() {
	return dtCreate;
}
/**
 * @param dtCreate The dtCreate to set.
 */
public void setDtCreate(Timestamp dtCreate) {
	this.dtCreate = dtCreate;
}
/**
 * @return Returns the dtLastmod.
 */
public Timestamp getDtLastmod() {
	return dtLastmod;
}
/**
 * @param dtLastmod The dtLastmod to set.
 */
public void setDtLastmod(Timestamp dtLastmod) {
	this.dtLastmod = dtLastmod;
}
/**
 * @return Returns the installationType.
 */
public String getInstallationType() {
	return installationType;
}
/**
 * @param installationType The installationType to set.
 */
public void setInstallationType(String installationType) {
	this.installationType = installationType;
	put("installationType");
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
private java.util.Map map = new java.util.HashMap();

public void put(String field) { map.put(field, Boolean.TRUE); }

public java.util.Map getMap() { return this.map; }

}
