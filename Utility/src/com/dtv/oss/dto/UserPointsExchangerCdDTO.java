package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserPointsExchangerCdDTO
    implements ReflectionSupport,Serializable {
  private int id;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int activityId;
  private Timestamp createTime;
  private int createOperatorId;
  private int userId;
  private int accountId;
  private int userPointsBefore;
  private int userPointPost;
  private int exchangePoints;
  private int exchangeGoodsTypeId;
  private int exchangeGoodsAmount;
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
    put("createTime");
  }

  public int getCreateOperatorId() {
    return createOperatorId;
  }

  public void setCreateOperatorId(int createOperatorId) {
    this.createOperatorId = createOperatorId;
    put("createOperatorId");
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
    put("userId");
  }

  public int getAccountId() {
    return accountId;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
    put("accountId");
  }

  public int getUserPointsBefore() {
    return userPointsBefore;
  }

  public void setUserPointsBefore(int userPointsBefore) {
    this.userPointsBefore = userPointsBefore;
    put("userPointsBefore");
  }

  public int getUserPointPost() {
    return userPointPost;
  }

  public void setUserPointPost(int userPointPost) {
    this.userPointPost = userPointPost;
    put("userPointPost");
  }

  public int getExchangePoints() {
    return exchangePoints;
  }

  public void setExchangePoints(int exchangePoints) {
    this.exchangePoints = exchangePoints;
    put("exchangePoints");
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

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
      	UserPointsExchangerCdDTO that = (UserPointsExchangerCdDTO) obj;
        return  this.getId()  == that.getId() &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getActivityId() == that.getActivityId() &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) &&
            this.getCreateOperatorId() == that.getCreateOperatorId() &&
            this.getUserId() == that.getUserId() &&
            this.getAccountId() == that.getAccountId() &&
            this.getUserPointsBefore() == that.getUserPointsBefore() &&
            this.getUserPointPost() == that.getUserPointPost() && this
            .getExchangePoints() == that.getExchangePoints() &&
            this.getExchangeGoodsTypeId() == that.getExchangeGoodsTypeId() &&
            this.getExchangeGoodsAmount() == that.getExchangeGoodsAmount();
      }
    }
    return false;
  }

  public int hashCode() {
    return  toString().hashCode();
  }

  
  public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(id);
		buf.append(",").append(activityId);
		buf.append(",").append(createTime);
		buf.append(",").append(createOperatorId);
		buf.append(",").append(userId);
		buf.append(",").append(accountId);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(userPointsBefore);
		buf.append(",").append(userPointPost);
		buf.append(",").append(exchangePoints);
		buf.append(",").append(exchangeGoodsTypeId);
		buf.append(",").append(exchangeGoodsAmount);
         
		return buf.toString();
	}

private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}
