package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class VOIPRecordDTO implements Serializable,ReflectionSupport{
	
	private int aa_no;
	private int billingCycleID;
	private int custID;
	private int acctID;
	private int serviceAccountID;
	private String acctItemTypeID;
	private String sourceType;
	
	private String pointOrigin;
	private String pointTarget;
	private Timestamp date1;
	private Timestamp date2;
	private int units;
	private double rateAmount;
	private double discount;
	private int unitsCredited;
	private double realValue;
	
	private Map map=new HashMap();
	
	
	public int getAcctID() {
		return acctID;
	}

	public void setAcctID(int acctID) {
		this.acctID = acctID;
		put("acctID");
	}

	public String getAcctItemTypeID() {
		return acctItemTypeID;
	}

	public void setAcctItemTypeID(String acctItemTypeID) {
		this.acctItemTypeID = acctItemTypeID;
		put("acctItemTypeID");
	}

	public int getBillingCycleID() {
		return billingCycleID;
	}

	public void setBillingCycleID(int billingCycleID) {
		this.billingCycleID = billingCycleID;
		put("billingCycleID");
	}

	public int getCustID() {
		return custID;
	}

	public void setCustID(int customerID) {
		this.custID = customerID;
		put("custID");
	}

	public Timestamp getDate1() {
		return date1;
	}

	public void setDate1(Timestamp date1) {
		this.date1 = date1;
		put("date1");
	}

	public Timestamp getDate2() {
		return date2;
	}

	public void setDate2(Timestamp date2) {
		this.date2 = date2;
		put("date2");
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
		put("discount");
	}

	public String getSourceType(){
		return sourceType;
	}
	
	public void setSourceType(String sourceType){
		this.sourceType = sourceType;
		put("sourceType");
	}
	
	public String getPointOrigin() {
		return pointOrigin;
	}

	public void setPointOrigin(String pointOrigin) {
		this.pointOrigin = pointOrigin;
		put("pointOrigin");
	}

	public String getPointTarget() {
		return pointTarget;
	}

	public void setPointTarget(String pointTarget) {
		this.pointTarget = pointTarget;
		put("pointTarget");
	}

	public double getRateAmount() {
		return rateAmount;
	}

	public void setRateAmount(double rateAmount) {
		this.rateAmount = rateAmount;
		put("rateAmount");
	}

	public double getRealValue() {
		return realValue;
	}

	public void setRealValue(double realValue) {
		this.realValue = realValue;
		put("realValue");
	}

	public int getServiceAccountID() {
		return serviceAccountID;
	}

	public void setServiceAccountID(int serviceAccountID) {
		this.serviceAccountID = serviceAccountID;
		put("serviceAccountID");
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
		put("units");
	}

	public int getUnitsCredited() {
		return unitsCredited;
	}

	public void setUnitsCredited(int unitsCredited) {
		this.unitsCredited = unitsCredited;
		put("unitsCredited");
	}

	public void put(String field) {
		map.put(field,Boolean.TRUE);
		
	}

	public Map getMap() {
		// TODO Auto-generated method stub
		return this.map;
	}
	public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(billingCycleID);
		buf.append(",").append(custID);
		buf.append(",").append(acctID);
		buf.append(",").append(serviceAccountID);
		buf.append(",").append(acctItemTypeID);
		buf.append(",").append(sourceType);
		buf.append(",").append(pointOrigin);
		buf.append(",").append(pointTarget);
		buf.append(",").append(date1);
		buf.append(",").append(date2);
		buf.append(",").append(units);
		buf.append(",").append(rateAmount);
		buf.append(",").append(discount);
		buf.append(",").append(unitsCredited);
		buf.append(",").append(realValue);
		return buf.toString();
	}
	
	public int hashCode(){
		return toString().hashCode();
	}

	public int getAa_no() {
		return aa_no;
	}

	public void setAa_no(int aa_no) {
		this.aa_no = aa_no;
	}
}
