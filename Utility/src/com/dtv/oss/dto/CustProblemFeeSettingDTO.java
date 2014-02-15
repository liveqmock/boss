package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CustProblemFeeSettingDTO
    implements Serializable {
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private String problemLevel;
  private String problemCategory;
  private int eventClass;
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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

  public String getProblemLevel() {
    return problemLevel;
  }

  public void setProblemLevel(String problemLevel) {
    this.problemLevel = problemLevel;
  }

  public String getProblemCategory() {
    return problemCategory;
  }

  public void setProblemCategory(String problemCategory) {
    this.problemCategory = problemCategory;
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
        CustProblemFeeSettingDTO that = (CustProblemFeeSettingDTO) obj;
        return ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
                (this.getStatus() != null &&
                 this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            ( ( (this.getProblemLevel() == null) && (that.getProblemLevel() == null)) ||
             (this.getProblemLevel() != null &&
              this.getProblemLevel().equals(that.getProblemLevel()))) &&
            ( ( (this.getProblemCategory() == null) && (that.getProblemCategory() == null)) ||
             (this.getProblemCategory() != null &&
              this.getProblemCategory().equals(that.getProblemCategory()))) &&
            this.getEventClass() == that.getEventClass();
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
    buf.append(problemCategory);
    buf.append(",").append(status);
     
    buf.append(",").append(eventClass);
    buf.append(",").append(dtCreate);
    buf.append(",").append(dtLastmod);

    return buf.toString();
  }

   
}
