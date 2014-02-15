package com.dtv.oss.dto;

import java.sql.Timestamp;

/* DTO created by awk, script coded by YJ */

/** @todo Complete package & import here */

public class ResourcePhoneNoDTO implements ReflectionSupport,
		java.io.Serializable {
	private int itemID;

	private String resourceName;

	private String phoneNo;

	private String areaCode;

	private String countryCode;

	private String comments;

	private String status;

	private Timestamp statusTime;

	private Timestamp dtCreate;

	private Timestamp dtLastmod;

	private String grade;
	
	private double chooseNoFee;
	/**
	 * when creating phoneNo,the phoneNoBegin acts as the first phoneNo when
	 * quering ,it acts as the bottom border of the conditon extent
	 */
	private String phoneNoBegin;

	/**
	 * when creating phoneNo,the phoneNoEnd acts as the last phoneNo when
	 * quering ,it acts as the top border of the conditon extent
	 */
	private String phoneNoEnd;
	
	private int districtId; 

	public ResourcePhoneNoDTO() {
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public String getStatus() {
		return status;
	}

	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtCreate() {
		return dtCreate;
	}

	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
	}

	public Timestamp getDtLastmod() {
		return dtLastmod;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				ResourcePhoneNoDTO that = (ResourcePhoneNoDTO) obj;
				return (((this.getResourceName() == null) && (that
						.getResourceName() == null)) || (this.getResourceName() != null && this
						.getResourceName().equals(that.getResourceName())))
						&& (((this.getAreaCode() == null) && (that
								.getAreaCode() == null)) || (this.getAreaCode() != null && this
								.getAreaCode().equals(that.getAreaCode())))
						&& (((this.getComments() == null) && (that
								.getComments() == null)) || (this.getComments() != null && this
								.getComments().equals(that.getComments())))
						&& (((this.getStatus() == null) && (that.getStatus() == null)) || (this
								.getStatus() != null && this.getStatus()
								.equals(that.getStatus())))
						&& this.getItemID() == that.getItemID()
                        && this.getDistrictId() == that.getDistrictId()
						&& (((this.getPhoneNo() == null) && (that.getPhoneNo() == null)) || (this
								.getPhoneNo() != null && this.getPhoneNo()
								.equals(that.getPhoneNo())))
						&&

						(((this.getCountryCode() == null) && (that
								.getCountryCode() == null)) || (this
								.getCountryCode() != null && this
								.getCountryCode().equals(that.getCountryCode())))
						&& (((this.getDtCreate() == null) && (that
								.getDtCreate() == null)) || (this.getDtCreate() != null && this
								.getDtCreate().equals(that.getDtCreate())))
						&& (((this.getStatusTime() == null) && (that
								.getStatusTime() == null)) || (this
								.getStatusTime() != null && this
								.getStatusTime().equals(that.getStatusTime())))
						&& (((this.getDtLastmod() == null) && (that
								.getDtLastmod() == null)) || (this
								.getDtLastmod() != null && this.getDtLastmod()
								.equals(that.getDtLastmod())));
			}
		}
		return false;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(256);
		buf.append("itemID=").append(itemID);
		buf.append(",").append("resourceName=").append(resourceName);
		buf.append(",").append("phoneNo=").append(phoneNo);
		buf.append(",").append("status=").append(status);
		buf.append(",").append("areaCode=").append(areaCode);
		buf.append(",").append("countryCode=").append(countryCode);
		buf.append(",").append("comments=").append(comments);
		buf.append(",").append("dtCreate=").append(dtCreate);
		buf.append(",").append("dtLastmod=").append(dtLastmod);
		buf.append(",").append("districtId=").append(districtId);
		buf.append(",").append("statusTime=").append(statusTime);

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
	 * @return Returns the areaCode.
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode
	 *            The areaCode to set.
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
		put("areaCode");
	}

	/**
	 * @return Returns the comments.
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            The comments to set.
	 */
	public void setComments(String comments) {
		this.comments = comments;
		put("comments");
	}

	/**
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
		put("countryCode");
	}

	/**
	 * @return Returns the itemID.
	 */
	public int getItemID() {
		return itemID;
	}

	/**
	 * @param itemID
	 *            The itemID to set.
	 */
	public void setItemID(int itemID) {
		this.itemID = itemID;
		put("itemID");
	}

	/**
	 * @return Returns the phoneNo.
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo
	 *            The phoneNo to set.
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
		put("phoneNo");
	}

	/**
	 * @return Returns the resourceName.
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName
	 *            The resourceName to set.
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
		put("resourceName");
	}

	/**
	 * @return Returns the statusTime.
	 */
	public Timestamp getStatusTime() {
		return statusTime;
	}

	/**
	 * @param statusTime
	 *            The statusTime to set.
	 */
	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
		put("statusTime");
	}

	public String getPhoneNoBegin() {
		return phoneNoBegin;
	}

	public void setPhoneNoBegin(String phoneNoBegin) {
		this.phoneNoBegin = phoneNoBegin;
		put("phoneNoBegin");
	}

	public String getPhoneNoEnd() {
		return phoneNoEnd;
	}

	public void setPhoneNoEnd(String phoneNoEnd) {
		this.phoneNoEnd = phoneNoEnd;
		put("phoneNoEnd");
	}

	/**
	 * @return Returns the districtId.
	 */
	public int getDistrictId() {
		return districtId;
	}
	/**
	 * @param districtId The districtId to set.
	 */
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
		put("districtId");
	}

	/**
	 * @return Returns the chooseNoFee.
	 */
	public double getChooseNoFee() {
		return chooseNoFee;
	}

	/**
	 * @param chooseNoFee The chooseNoFee to set.
	 */
	public void setChooseNoFee(double chooseNoFee) {
		this.chooseNoFee = chooseNoFee;
	}

	/**
	 * @return Returns the grade.
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * @param grade The grade to set.
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
}
