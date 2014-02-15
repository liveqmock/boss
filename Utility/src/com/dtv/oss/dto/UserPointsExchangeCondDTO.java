package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserPointsExchangeCondDTO
    implements ReflectionSupport,Serializable {
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int condId;
  private int activityId;
  private String custTypeList;
  private String accountClassList;
  
  private String mopIdList;
  private int pointRange1;
  private int pointRange2;
  private int exchangeGoodsTypeID;
  private String status;
  
/**
 * @return Returns the exchangeGoodsTypeID.
 */
public int getExchangeGoodsTypeID() {
	return exchangeGoodsTypeID;
}
/**
 * @param exchangeGoodsTypeID The exchangeGoodsTypeID to set.
 */
public void setExchangeGoodsTypeID(int exchangeGoodsTypeID) {
	this.exchangeGoodsTypeID = exchangeGoodsTypeID;
	 put("exchangeGoodsTypeID");
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

  public int getCondId() {
    return condId;
  }

  public void setCondId(int condId) {
    this.condId = condId;
    
  }

  public int getActivityId() {
    return activityId;
  }

  public void setActivityId(int activityId) {
    this.activityId = activityId;
    put("activityId");
  }
  
  public String getCustTypeList() {
   
    return custTypeList;
  }

  public void setCustTypeList(String custTypeList) {
    this.custTypeList = custTypeList;
    put("custTypeList");
  }

  public String getAccountClassList() {
    return accountClassList;
  }

  public void setAccountClassList(String accountClassList) {
    this.accountClassList = accountClassList;
    put("accountClassList");
  }

  public String getMopIdList() {
    return mopIdList;
  }

  public void setMopIdList(String mopIdList) {
    this.mopIdList = mopIdList;
    put("mopIdList");
  }

  public int getPointRange1() {
    return pointRange1;
  }

  public void setPointRange1(int pointRange1) {
    this.pointRange1 = pointRange1;
    put("pointRange1");
  }
  

  public int getPointRange2() {
    return pointRange2;
  }

  public void setPointRange2(int pointRange2) {
    this.pointRange2 = pointRange2;
    put("pointRange2");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
      	UserPointsExchangeCondDTO that = (UserPointsExchangeCondDTO) obj;
        return ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
                (this.getDtCreate() != null &&
                 this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
              this.getCondId() == that.getCondId()  &&
            this.getActivityId() == that.getActivityId() &&
            ( ( (this.getCustTypeList() == null) && (that.getCustTypeList() == null)) ||
             (this.getCustTypeList() != null &&
              this.getCustTypeList().equals(that.getCustTypeList()))) &&
            ( ( (this.getAccountClassList() == null) &&
               (that.getAccountClassList() == null)) ||
             (this.getAccountClassList() != null &&
              this.getAccountClassList().equals(that.getAccountClassList()))) &&
            ( ( (this.getMopIdList() == null) && (that.getMopIdList() == null)) ||
             (this.getMopIdList() != null &&
              this.getMopIdList().equals(that.getMopIdList()))) &&
              ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
                    (this.getStatus() != null &&
                     this.getStatus().equals(that.getStatus()))) && 
			 this.getExchangeGoodsTypeID() == that.getExchangeGoodsTypeID()&& 
            this.getPointRange1() == that.getPointRange1() &&
            this.getPointRange2() == that.getPointRange2();
        
      }
    }
    return false;
  }

  public int hashCode()
	{
		return toString().hashCode();
	}
  public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(condId);
		buf.append(",").append(activityId);
		buf.append(",").append(custTypeList);
		buf.append(",").append(accountClassList);
		buf.append(",").append(mopIdList);
		buf.append(",").append(pointRange1);
		buf.append(",").append(pointRange2);
		buf.append(",").append(status);
		buf.append(",").append(exchangeGoodsTypeID);
		 
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
             
		return buf.toString();
	}
  
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

