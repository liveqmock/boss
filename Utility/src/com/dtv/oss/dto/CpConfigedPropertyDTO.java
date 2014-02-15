package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CpConfigedPropertyDTO implements ReflectionSupport, java.io.Serializable
{
	private int psID;
	 
	private String propertyCode;
	 
	private String propertyValue;
	private String status;
	private Timestamp statusTime;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public CpConfigedPropertyDTO()
	{
		this.propertyCode="";
		this.propertyValue="";
		this.status="";
	}

	 

	public void setPsID(int psID)
	{
		this.psID = psID;
		put("psID");
	}

	public int getPsID()
	{
		return psID;
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
	/**
	 * @return Returns the statusTime.
	 */
	public Timestamp getStatusTime() {
		return statusTime;
	}
	/**
	 * @param statusTime The statusTime to set.
	 */
	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
		put("statusTime");
	}
	/**
	 * @param dtCreate The dtCreate to set.
	 */
	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
		put("dtCreate");
	}
	public Timestamp getDtCreate()
	{
		return dtCreate;
	}

	public void setDtLastmod(Timestamp dtLastmod)
	{
		this.dtLastmod = dtLastmod;
		put("dtLastmod");
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
				CpConfigedPropertyDTO that = (CpConfigedPropertyDTO) obj;
				return
					this.getPsID() == that.getPsID()  &&
					 
					(((this.getPropertyCode() == null) && (that.getPropertyCode() == null)) ||
						(this.getPropertyCode() != null && this.getPropertyCode().equals(that.getPropertyCode()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&	
					(((this.getPropertyValue() == null) && (that.getPropertyValue() == null)) ||
						(this.getPropertyValue() != null && this.getPropertyValue().equals(that.getPropertyValue()))) &&		
					(((this.getStatusTime() == null) && (that.getStatusTime() == null)) ||
					  (this.getStatusTime() != null && this.getStatusTime().equals(that.getStatusTime()))) &&			
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
		buf.append(psID);
 
		buf.append(",").append(propertyCode);
		buf.append(",").append(propertyValue);
		buf.append(",").append(status);
		buf.append(",").append(statusTime);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

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
}

