package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;
 

public class UserPointsExchangeGoodsDTO
    implements  ReflectionSupport,Serializable {
  private int id;
  private String name;
  private String descr;
  private int amount;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int activityId;
  public int getId() {
    return id;
  }
 
  public void setId(int id) {
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

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
    put("amount");
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

  public int getActivityId() {
    return activityId;
  }

  public void setActivityId(int activityId) {
    this.activityId = activityId;
    put("activityId");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
      	UserPointsExchangeGoodsDTO that = (UserPointsExchangeGoodsDTO) obj;
        return  this.getId() == that.getId()  &&
            ( ( (this.getName() == null) && (that.getName() == null)) ||
             (this.getName() != null && this.getName().equals(that.getName()))) &&
            ( ( (this.getDescr() == null) && (that.getDescr() == null)) ||
             (this.getDescr() != null && this.getDescr().equals(that.getDescr()))) &&
            this.getAmount() == that.getAmount() &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getActivityId() ==
            that.getActivityId();
      }
    }
    return false;
  }

  public int hashCode() {
    return  
            toString().hashCode();
  }

  
  public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(id);
		buf.append(",").append(activityId);
		buf.append(",").append(amount);
		buf.append(",").append(status);
		buf.append(",").append(name);
		buf.append(",").append(descr);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
           
		return buf.toString();
	}

private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}
