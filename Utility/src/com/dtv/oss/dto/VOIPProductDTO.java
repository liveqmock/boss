package com.dtv.oss.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class VOIPProductDTO implements ReflectionSupport,Serializable{
	private int productID;
	private String productName;
	private String propertyName;
	private String sssrvType;
	private String description;
	private String sssrvCode;
	private String dtCreate;
	private String dtLastmod;
	
	public Map map=new HashMap();
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		put("description");
	}

	public String getSssrvType() {
		return sssrvType;
	}

	public void setSssrvType(String serviceType) {
		this.sssrvType = serviceType;
		put("sssrvType");
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
		//put("SMSID");
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String name) {
		productName = name;
		put("productName");
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String voipProductName) {
		this.propertyName = voipProductName;
		put("propertyName");
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(productID);
		buf.append(",").append(productName);
		buf.append(",").append(propertyName);
		buf.append(",").append(sssrvCode);
		buf.append(",").append(sssrvType);
		buf.append(",").append(description);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}
	
	public int hashCode(){
		return toString().hashCode();
	}
	
	public void put(String field) {
		map.put(field,Boolean.TRUE);
		
	}

	public Map getMap() {
		// TODO Auto-generated method stub
		return this.map;
	}

	public String getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(String dtCreate) {
		this.dtCreate = dtCreate;
		 
	}

	public String getDtLastmod() {
		return dtLastmod;
	}

	public void setDtLastmod(String dtLastmod) {
		this.dtLastmod = dtLastmod;
		 
	}

	public String getSssrvCode() {
		return sssrvCode;
	}

	public void setSssrvCode(String sssrvCode) {
		this.sssrvCode = sssrvCode;
		//put("sssrvCode");
	}

}
