package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserPointsExchangeActivityDTO
    implements ReflectionSupport, Serializable {
  private Integer id;
  private String name;
  private String descr;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private Timestamp dateStart;
  private Timestamp dateEnd;
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
    put("name");
  }

  public String getDescr() {
    return descr;
  }

  public void setDescr(String descr) {
    this.descr = descr;
    put("descr");
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

  public Timestamp getDateStart() {
    return dateStart;
  }

  public void setDateStart(Timestamp dateStart) {
    this.dateStart = dateStart;
    put("dateStart");
  }

  public Timestamp getDateEnd() {
    return dateEnd;
  }

  public void setDateEnd(Timestamp dateEnd) {
    this.dateEnd = dateEnd;
    put("dateEnd");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        UserPointsExchangeActivityDTO that = (UserPointsExchangeActivityDTO)
            obj;
        return ( ( (this.getId() == null) && (that.getId() == null)) ||
                (this.getId() != null && this.getId().equals(that.getId()))) &&
            ( ( (this.getName() == null) && (that.getName() == null)) ||
             (this.getName() != null && this.getName().equals(that.getName()))) &&
            ( ( (this.getDescr() == null) && (that.getDescr() == null)) ||
             (this.getDescr() != null && this.getDescr().equals(that.getDescr()))) &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            ( ( (this.getDateStart() == null) && (that.getDateStart()
                                                  == null)) ||
             (this.getDateStart() != null &&
              this.getDateStart().equals(that.getDateStart()))) &&
            ( ( (this.getDateEnd() == null) && (that.getDateEnd() == null)) ||
             (this.getDateEnd() != null &&
              this.getDateEnd().equals(that.getDateEnd())));
      }
    }
    return false;
  }

  public int hashCode() {
    return  
            toString().hashCode();
  }

  
  public String toString(){
	StringBuffer buf = new StringBuffer(256);
	buf.append(id);
	buf.append(",").append(name);
	buf.append(",").append(descr);
	buf.append(",").append(status);
	buf.append(",").append(dateStart);
	buf.append(",").append(dateEnd);
	 
	buf.append(",").append(dtCreate);
	buf.append(",").append(dtLastmod);

       
	return buf.toString();
  }

  private java.util.Map map = new java.util.HashMap();

  public void put(String field){
	map.put(field, Boolean.TRUE);
  }

  public java.util.Map getMap(){
	return this.map;
  }

}

