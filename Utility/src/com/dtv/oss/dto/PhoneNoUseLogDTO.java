package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class PhoneNoUseLogDTO implements ReflectionSupport, java.io.Serializable
{
	 
	private int seqNo;
	private int opID;
	private int orgID;
	private int resourceItemID;
	private String  phoneNo;
	private String areaCode;
	private String countryCode;
	private String action;
	private String description;
	private int deviceID;
	private String networkID;
	private int custID;
	private int psID;
	private int userID;
	private String status;
	private Timestamp createTime;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
    


		/**
	 * @return Returns the action.
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action The action to set.
	 */
	public void setAction(String action) {
		this.action = action;
		put("action");
	}
	/**
	 * @return Returns the createTime.
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime The createTime to set.
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		put("createTime");
	}
	/**
	 * @return Returns the custID.
	 */
	public int getCustID() {
		return custID;
	}
	/**
	 * @param custID The custID to set.
	 */
	public void setCustID(int custID) {
		this.custID = custID;
		put("custID");
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
		put("description");
	}
	/**
	 * @return Returns the deviceID.
	 */
	public int getDeviceID() {
		return deviceID;
	}
	/**
	 * @param deviceID The deviceID to set.
	 */
	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
		put("deviceID");
	}
	/**
	 * @return Returns the networkID.
	 */
	public String getNetworkID() {
		return networkID;
	}
	/**
	 * @param networkID The networkID to set.
	 */
	public void setNetworkID(String networkID) {
		this.networkID = networkID;
		put("networkID");
	}
	/**
	 * @return Returns the opID.
	 */
	public int getOpID() {
		return opID;
	}
	/**
	 * @param opID The opID to set.
	 */
	public void setOpID(int opID) {
		this.opID = opID;
		put("opID");
	}
	/**
	 * @return Returns the orgID.
	 */
	public int getOrgID() {
		return orgID;
	}
	/**
	 * @param orgID The orgID to set.
	 */
	public void setOrgID(int orgID) {
		this.orgID = orgID;
		put("orgID");
	}
	/**
	 * @return Returns the psID.
	 */
	public int getPsID() {
		return psID;
	}
	/**
	 * @param psID The psID to set.
	 */
	public void setPsID(int psID) {
		this.psID = psID;
		put("psID");
	}
	/**
	 * @return Returns the resourceItemID.
	 */
	public int getResourceItemID() {
		return resourceItemID;
	}
	/**
	 * @param resourceItemID The resourceItemID to set.
	 */
	public void setResourceItemID(int resourceItemID) {
		this.resourceItemID = resourceItemID;
		put("resourceItemID");
	}
	/**
	 * @return Returns the seqNo.
	 */
	public int getSeqNo() {
		return seqNo;
	}
	/**
	 * @param seqNo The seqNo to set.
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	/**
	 * @return Returns the userID.
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * @param userID The userID to set.
	 */
	public void setUserID(int userID) {
		this.userID = userID;
		put("userID");
	}
public PhoneNoUseLogDTO()
	{
	}
	 

	public void setStatus(String status)
	{
		this.status = status;
		put("status");
	}

	public String getStatus()
	{
		return status;
	}

	public void setDtCreate(Timestamp dtCreate)
	{
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtCreate()
	{
		return dtCreate;
	}

	public void setDtLastmod(Timestamp dtLastmod)
	{
		this.dtLastmod = dtLastmod;
	}

	public Timestamp getDtLastmod()
	{
		return dtLastmod;
	}

	 

	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				PhoneNoUseLogDTO that = (PhoneNoUseLogDTO) obj;
				return
					(((this.getAreaCode() == null) && (that.getAreaCode() == null)) ||
						(this.getAreaCode() != null && this.getAreaCode().equals(that.getAreaCode()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					this.getSeqNo() == that.getSeqNo()  &&
					(((this.getPhoneNo() == null) && (that.getPhoneNo() == null)) ||
						(this.getPhoneNo() != null && this.getPhoneNo().equals(that.getPhoneNo()))) &&
					(((this.getCountryCode() == null) && (that.getCountryCode() == null)) ||
						(this.getCountryCode() != null && this.getCountryCode().equals(that.getCountryCode()))) &&
					(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
					(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
						(this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod()))) &&
					(((this.getAction() == null) && (that.getAction() == null)) ||
							(this.getAction() != null && this.getAction().equals(that.getAction()))) &&
					(((this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
							(this.getCreateTime() != null && this.getCreateTime().equals(that.getCreateTime()))) &&							
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
							(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
					this.getCustID() == that.getCustID()  &&
					this.getDeviceID() == that.getDeviceID()  &&
					this.getOpID() == that.getOpID()  &&
					this.getOrgID() == that.getOrgID()  &&
					this.getPsID() == that.getPsID()  &&
					this.getUserID() == that.getUserID()  &&
					this.getResourceItemID() == that.getResourceItemID()  &&
					(((this.getNetworkID() == null) && (that.getNetworkID() == null)) ||
							(this.getNetworkID() != null && this.getNetworkID().equals(that.getNetworkID()))) ;
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
		buf.append(",").append(phoneNo);
		buf.append(",").append(status);
		buf.append(",").append(areaCode);
		buf.append(",").append(countryCode);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(seqNo);
		buf.append(",").append(opID);
		buf.append(",").append(orgID);
		buf.append(",").append(resourceItemID);
		buf.append(",").append(action);
		buf.append(",").append(description);
		buf.append(",").append(deviceID);
		buf.append(",").append(networkID);
		buf.append(",").append(custID);
		buf.append(",").append(psID);
		buf.append(",").append(userID);
		buf.append(",").append(createTime);

		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

	/**
	 * @return Returns the areaCode.
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * @param areaCode The areaCode to set.
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
		 put("areaCode");
	}
	/**
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * @param countryCode The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
		 put("countryCode");
	}
	/**
	 * @return Returns the phoneNo.
	 */
	public String getPhoneNo() {
		return phoneNo;
	}
	/**
	 * @param phoneNo The phoneNo to set.
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
		 put("phoneNo");
	}

}

