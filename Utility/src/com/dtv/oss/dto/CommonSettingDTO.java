package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CommonSettingDTO
    implements ReflectionSupport, java.io.Serializable {
  private String name;
  private String description;
  private String module;
  private String attable;
  private String field;
  private String fixed;
  private String type;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;

  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
     put("description");
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
    put("module");
  }

  public String getAttable() {
    return attable;
  }

  public void setAttable(String attable) {
    this.attable = attable;
     put("attable");
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
     put("field");
  }

  public String getFixed() {
    return fixed;
  }

  public void setFixed(String fixed) {
    this.fixed = fixed;
    put("fixed");
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
    put("type");
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

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CommonSettingDTO that = (CommonSettingDTO) obj;
        return ( ( (this.getName() == null) && (that.getName() == null)) ||
                (this.getName() != null && this.getName().equals(that.getName()))) &&
            ( ( (this.getDescription() == null) && (that.getDescription() == null)) ||
             (this.getDescription() != null &&
              this.getDescription().equals(that.getDescription()))) &&
            ( ( (this.getModule() == null) && (that.getModule() == null)) ||
             (this.getModule() != null &&
              this.getModule().equals(that.getModule()))) &&
            ( ( (this.getAttable() == null) && (that.getAttable() == null)) ||
             (this.getAttable() != null &&
              this.getAttable().equals(that.getAttable()))) &&
            ( ( (this.getField() == null) && (that.getField() == null)) ||
             (this.getField() != null && this.getField().equals(that.getField()))) &&
            ( ( (this.getFixed() == null) && (that.getFixed() == null)) ||
             (this.getFixed() != null && this.getFixed().equals(that.getFixed()))) &&
            ( ( (this.getType() == null) && (that.getType() == null)) || (this
            .getType() != null && this.getType().equals(that.getType()))) &&
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

        return  toString().hashCode();
}

  public String toString() {
    StringBuffer buf = new StringBuffer(256);
              buf.append(dtCreate);
              buf.append(",").append(dtLastmod);
              buf.append(",").append(name);
              buf.append(",").append(description);
              buf.append(",").append(attable);
              buf.append(",").append(module);
              buf.append(",").append(field);
              buf.append(",").append(type);
              buf.append(fixed);
              return buf.toString();

  }
  private java.util.Map map = new java.util.HashMap();

  public void put(String field) { map.put(field, Boolean.TRUE); }

   public java.util.Map getMap() { return this.map; }

  }

