package com.dtv.oss.dto;

import java.sql.Timestamp;

public class UserPointsCumulatedRuleDTO
    implements ReflectionSupport, java.io.Serializable {
  private int id;
  private String descr;
  private double condValue1;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private String custTypeList;
  private int condEvent;
  private double condValue2;
  private int addedPoints;
  private int productID;
  
/**
 * @return Returns the productID.
 */
public int getProductID() {
	return productID;
}
/**
 * @param productID The productID to set.
 */
public void setProductID(int productID) {
	this.productID = productID;
	 put("productID");
}
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescr() {
    return descr;
  }

  public void setDescr(String descr) {
    this.descr = descr;
    put("descr");
  }

  public double getCondValue1() {
    return condValue1;
  }

  public void setCondValue1(double condValue1) {
    this.condValue1 = condValue1;
    put("condValue1");
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

  public String getCustTypeList() {
    return custTypeList;
  }

  public void setCustTypeList(String custTypeList) {
    this.custTypeList = custTypeList;
    put("custTypeList");
  }

  public int getCondEvent() {
    return condEvent;
  }

  public void setCondEvent(int condEvent) {
    this.condEvent = condEvent;
    put("condEvent");
  }

  public double getCondValue2() {
    return condValue2;
  }

  public void setCondValue2(double condValue2) {
    this.condValue2 = condValue2;
    put("condValue2");
  }

  public int getAddedPoints() {
    return addedPoints;
  }

  public void setAddedPoints(int addedPoints) {
    this.addedPoints = addedPoints;
    put("addedPoints");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        UserPointsCumulatedRuleDTO that = (UserPointsCumulatedRuleDTO) obj;
        return 
              this.getId() == that.getId() &&
            ( ( (this.getDescr() == null) && (that.getDescr() == null)) ||
             (this.getDescr() != null && this.getDescr().equals(that.getDescr()))) &&
            this.getCondValue1() == that.getCondValue1() &&
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            ( ( (this.getCustTypeList() == null) && (that.getCustTypeList() == null)) ||
             (this.getCustTypeList() != null &&
              this.getCustTypeList().equals(that.getCustTypeList()))) &&
            this.getCondEvent() == that.getCondEvent() &&
            this.getProductID() == that.getProductID() &&
            this.getCondValue2() == that.getCondValue2() &&
            this.getAddedPoints() == that.getAddedPoints();
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
		buf.append(id);
		buf.append(",").append(descr);
		buf.append(",").append(condValue1);
		buf.append(",").append(status);
		buf.append(",").append(custTypeList);
		buf.append(",").append(condEvent);
		buf.append(",").append(condValue2);
		buf.append(",").append(addedPoints);
		buf.append(",").append(productID);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
        
		return buf.toString();
	}

  private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

