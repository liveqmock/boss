package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class SystemModuleDTO
    implements Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private String moduleName;
  private String moduleDesc;
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

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public String getModuleDesc() {
    return moduleDesc;
  }

  public void setModuleDesc(String moduleDesc) {
    this.moduleDesc = moduleDesc;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        SystemModuleDTO that = (SystemModuleDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            ( ( (this.getModuleName() == null) && (that.getModuleName() == null)) ||
             (this.getModuleName() != null &&
              this.getModuleName().equals(that.getModuleName()))) &&
            ( ( (this.getModuleDesc() == null) && (that.getModuleDesc() == null)) ||
             (this.getModuleDesc() != null &&
              this.getModuleDesc().equals(that.getModuleDesc())));
      }
    }
    return false;
  }

  public int hashCode() {
    return (dtCreate + "" + dtLastmod + moduleName + moduleDesc).hashCode();
  }

  public String toString() {
    return dtCreate + ", " + dtLastmod + ", " + moduleName + ", " + moduleDesc;
  }
}
