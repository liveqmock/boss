package com.dtv.oss.dto;

import java.sql.Timestamp;

public class BidimConfigSettingValueDTO
    implements ReflectionSupport, java.io.Serializable {
  private int id;
  private String code;
  private String description;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private String defaultOrNot;
  private int priority;

  private int settingId;
  public int getId() {
   return id;
 }

 public void setId(int id) {
   this.id = id;
 }


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
     put("code");
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
      put("description");
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

  public int getSettingId() {
    return settingId;
  }

  public void setSettingId(int settingId) {
    this.settingId = settingId;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        BidimConfigSettingValueDTO that = (BidimConfigSettingValueDTO) obj;
        return this.getId() == that.getId() &&
            ( ( (this.getCode() == null) && (that.getCode() == null)) ||
             (this.getCode() != null && this.getCode().equals(that.getCode()))) &&
            ( ( (this.getDescription() == null) && (that.getDescription() == null)) ||
             (this.getDescription() != null &&
              this.getDescription().equals(that.getDescription()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
              (((this.getDefaultOrNot() == null) && (that
            		.getDefaultOrNot() == null)) || (this
            		.getDefaultOrNot() != null && this
            		.getDefaultOrNot().equals(that.getDefaultOrNot())))		
            && this.getPriority() == that.getPriority() &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getSettingId() == that.getSettingId
            ();
      }
    }
    return false;
  }

  public int hashCode() {
   return toString().hashCode();
}
 public String toString() {
    StringBuffer buf = new StringBuffer(256);
    buf.append(code);
    buf.append(",").append(id);
    buf.append(",").append(description);
    buf.append(",").append(settingId);

    buf.append(",").append(dtCreate);
    buf.append(",").append(dtLastmod);

    buf.append(",").append(status);
    buf.append(",").append(priority);
    buf.append(",").append(defaultOrNot);
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
 * @return Returns the defaultOrNot.
 */
public String getDefaultOrNot() {
	return defaultOrNot;
}
/**
 * @param defaultOrNot The defaultOrNot to set.
 */
public void setDefaultOrNot(String defaultOrNot) {
	this.defaultOrNot = defaultOrNot;
	 put("defaultOrNot");
}
/**
 * @return Returns the priority.
 */
public int getPriority() {
	return priority;
}
/**
 * @param priority The priority to set.
 */
public void setPriority(int priority) {
	this.priority = priority;
	 put("priority");
}
 }


