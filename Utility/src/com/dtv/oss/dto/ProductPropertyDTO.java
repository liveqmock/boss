package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProductPropertyDTO implements ReflectionSupport, Serializable {
	private String description="";

	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	private int productId;

	private String propertyName;

	private String propertyCode;

	private String propertyMode;

	private String resourceName;
	
	private String propertyType;
	
	private String propertyValue;

	 

	/**
	 * @return Returns the propertyType.
	 */
	public String getPropertyType() {
		return propertyType;
	}
	/**
	 * @param propertyType The propertyType to set.
	 */
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
		put("propertyType");
	}
	/**
	 * @return Returns the propertyValue.
	 */
	public String getPropertyValue() {
		return propertyValue;
	}
	/**
	 * @param propertyValue The propertyValue to set.
	 */
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
		put("propertyValue");
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		put("description");
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
		put("dtCreate");
	}

	public Timestamp getDtLastmod() {
		return dtLastmod;
	}

	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
		put("dtLastmod");
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
		//put("productId");
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
//		put("propertyName");
	}

	public String getPropertyMode() {
		return propertyMode;
	}

	public void setPropertyMode(String propertyMode) {
		this.propertyMode = propertyMode;
		put("propertyMode");
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				ProductPropertyDTO that = (ProductPropertyDTO) obj;
				return (((this.getDescription() == null) && (that
						.getDescription() == null)) || (this.getDescription() != null && this
						.getDescription().equals(that.getDescription())))
						&& (((this.getDtCreate() == null) && (that
								.getDtCreate() == null)) || (this.getDtCreate() != null && this
								.getDtCreate().equals(that.getDtCreate())))
						&& (((this.getDtLastmod() == null) && (that
								.getDtLastmod() == null)) || (this
								.getDtLastmod() != null && this.getDtLastmod()
								.equals(that.getDtLastmod())))
						&& this.getProductId() == that.getProductId()
						&& (((this.getPropertyName() == null) && (that
								.getPropertyName() == null)) || (this
								.getPropertyName() != null && this
								.getPropertyName().equals(
										that.getPropertyName())))
						&& (((this.getPropertyCode() == null) && (that
								.getPropertyCode() == null)) || (this
								.getPropertyCode() != null && this
								.getPropertyCode().equals(
										that.getPropertyCode())))
  && (((this.getPropertyType() == null) && (that
		.getPropertyType() == null)) || (this
		.getPropertyType() != null && this
		.getPropertyType().equals(
				that.getPropertyType())))
 && (((this.getPropertyValue() == null) && (that
		.getPropertyValue() == null)) || (this
		.getPropertyValue() != null && this
		.getPropertyValue().equals(
				that.getPropertyValue())))
						&& (((this.getPropertyMode() == null) && (that
								.getPropertyMode() == null)) || (this
								.getPropertyMode() != null && this
								.getPropertyMode().equals(
										that.getPropertyMode())));
			}
		}
		return false;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(256);
		buf.append(description);
		buf.append(",").append(productId);
		buf.append(",").append(propertyName);
		buf.append(",").append(dtCreate);

		buf.append(",").append(dtLastmod);

		buf.append(",").append(propertyMode);
		buf.append(",").append(propertyCode);
		buf.append(",").append(resourceName);
		buf.append(",").append(propertyValue);
		buf.append(",").append(propertyName);

		return buf.toString();

	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) {
		map.put(field, Boolean.TRUE);
	}

	public java.util.Map getMap() {
		return this.map;
	}

	 

	/**
	 * @return Returns the propertyCode.
	 */
	public String getPropertyCode() {
		return propertyCode;
	}

	/**
	 * @param propertyCode The propertyCode to set.
	 */
	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
		put("propertyCode");
	}

	/**
	 * @return Returns the resourceType.
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceType The resourceType to set.
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
		put("resourceName");
	}
}
