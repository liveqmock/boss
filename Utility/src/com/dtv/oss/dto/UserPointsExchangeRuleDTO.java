package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserPointsExchangeRuleDTO
    implements ReflectionSupport,Serializable {
  private int id;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int activityId;
  private int exchangeGoodsTypeId;
  private int exchangeGoodsAmount;
  private int exchangePointsValue;
  private String custTypeList;
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
    put("status");
  }
  public String getCustTypeList() {
    return custTypeList;
  }

  public void setCustTypeList(String custTypeList) {
    this.custTypeList = custTypeList;
    put("custTypeList");
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

  public int getExchangeGoodsTypeId() {
    return exchangeGoodsTypeId;
  }

  public void setExchangeGoodsTypeId(int exchangeGoodsTypeId) {
    this.exchangeGoodsTypeId = exchangeGoodsTypeId;
    put("exchangeGoodsTypeId");
  }

  public int getExchangeGoodsAmount() {
    return exchangeGoodsAmount;
  }

  public void setExchangeGoodsAmount(int exchangeGoodsAmount) {
    this.exchangeGoodsAmount = exchangeGoodsAmount;
    put("exchangeGoodsAmount");
  }

  public int getExchangePointsValue() {
    return exchangePointsValue;
  }

  public void setExchangePointsValue(int exchangePointsValue) {
    this.exchangePointsValue = exchangePointsValue;
    put("exchangePointsValue");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
      	UserPointsExchangeRuleDTO that = (UserPointsExchangeRuleDTO) obj;
        return 
                 this.getId()  == that.getId()  &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
              ( ( (this.getCustTypeList() == null) && (that.getCustTypeList() == null)) ||
                    (this.getCustTypeList() != null &&
                     this.getCustTypeList().equals(that.getCustTypeList()))) && 
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getActivityId() == that.getActivityId() &&
            this.getExchangeGoodsTypeId() == that.getExchangeGoodsTypeId() &&
            this.getExchangeGoodsAmount() == that.getExchangeGoodsAmount() &&
            this.getExchangePointsValue() == that.getExchangePointsValue();
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
		buf.append(",").append(status);
		buf.append(",").append(exchangeGoodsTypeId);
		buf.append(",").append(exchangeGoodsAmount);
		buf.append(",").append(exchangePointsValue);
		buf.append(",").append(custTypeList);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
	 
	 
       
		return buf.toString();
	}

private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}
