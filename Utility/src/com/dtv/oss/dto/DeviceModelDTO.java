package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/
/*
 * 2006-5-9ºîÈð¾üÌí¼ÓserialLength×Ö¶Î
 */
public class DeviceModelDTO implements ReflectionSupport, java.io.Serializable
{
	private String description;
	private int providerID;
	private int serialLength;
	private String deviceClass;
	private String status;
	private String deviceModel;
	private String viewInCdtFlag;
	private int keySerialNoFrom;
	private int keySerialNoTo;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	private String manageDeviceFlag;
	private String businessType;

	 
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
		put("businessType");
	}
	/**
	 * @return Returns the managerDeviceFlag.
	 */
	public String getManageDeviceFlag() {
		return manageDeviceFlag;
	}
	/**
	 * @param managerDeviceFlag The managerDeviceFlag to set.
	 */
	public void setManageDeviceFlag(String manageDeviceFlag) {
		
		this.manageDeviceFlag = manageDeviceFlag;
		put("manageDeviceFlag");
	}
	public void setDescription(String description)
	{
		this.description = description;
		put("description");
	}

	public String getDescription()
	{
		return description;
	}

	public void setProviderID(int providerID)
	{
		this.providerID = providerID;
		put("providerID");
	}

	public int getProviderID()
	{
		return providerID;
	}

	public void setDeviceClass(String deviceClass)
	{
		this.deviceClass = deviceClass;
		put("deviceClass");
	}

	public String getDeviceClass()
	{
		return deviceClass;
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

	public void setDeviceModel(String deviceModel)
	{
		this.deviceModel = deviceModel;
		//put("deviceModel");
	}

	public String getDeviceModel()
	{
		return deviceModel;
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
				DeviceModelDTO that = (DeviceModelDTO) obj;
				return
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
						(((this.getBusinessType() == null) && (that.getBusinessType() == null)) ||
								(this.getBusinessType() != null && this.getBusinessType().equals(that.getBusinessType())))&&
					this.getProviderID() == that.getProviderID()  &&
					(((this.getDeviceClass() == null) && (that.getDeviceClass() == null)) ||
						(this.getDeviceClass() != null && this.getDeviceClass().equals(that.getDeviceClass()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
						(((this.getManageDeviceFlag() == null) && (that.getManageDeviceFlag() == null)) ||
								(this.getManageDeviceFlag() != null && this.getManageDeviceFlag().equals(that.getManageDeviceFlag()))) &&	
					(((this.getDeviceModel() == null) && (that.getDeviceModel() == null)) ||
						(this.getDeviceModel() != null && this.getDeviceModel().equals(that.getDeviceModel()))) &&
					(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
					(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
						(this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod())));
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
		buf.append(description);
		buf.append(",").append(providerID);
		buf.append(",").append(deviceClass);
		buf.append(",").append(status);
		buf.append(",").append(deviceModel);
		buf.append(",").append(manageDeviceFlag);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(keySerialNoFrom);
		buf.append(",").append(keySerialNoTo);
		buf.append(",").append(viewInCdtFlag);
		buf.append(",").append(businessType);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

	/**
	 * @return Returns the serialLength.
	 */
	public int getSerialLength() {
		return serialLength;
	}

	/**
	 * @param serialLength The serialLength to set.
	 */
	public void setSerialLength(int serialLength) {
		this.serialLength = serialLength;
		put("serialLength");
	}
	public int getKeySerialNoFrom() {
		return keySerialNoFrom;
	}
	public void setKeySerialNoFrom(int keySerialNoFrom) {
		this.keySerialNoFrom = keySerialNoFrom;
		put("keySerialNoFrom");
	}
	public int getKeySerialNoTo() {
		return keySerialNoTo;
	}
	public void setKeySerialNoTo(int keySerialNoTo) {
		this.keySerialNoTo = keySerialNoTo;
		put("keySerialNoTo");
	}
	public String getViewInCdtFlag() {
		return viewInCdtFlag;
	}
	public void setViewInCdtFlag(String viewInCdtFlag) {
		this.viewInCdtFlag = viewInCdtFlag;
		put("viewInCdtFlag");
	}

}

