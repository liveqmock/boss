package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class OssAuthorizationDTO implements ReflectionSupport, java.io.Serializable
{
	private int deviceID;
	private String deviceSerialNo;
    private int productID;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    

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

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		put("productID");
		this.productID = productID;
	}
	
	public int getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
		put("deviceID");
	}

	public String getDeviceSerialNo() {
		return deviceSerialNo;
	}

	public void setDeviceSerialNo(String deviceSerialNo) {
		this.deviceSerialNo = deviceSerialNo;
		put("deviceSerialNo");
	}
	
	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				OssAuthorizationDTO that = (OssAuthorizationDTO) obj;
				return
					(((this.getDeviceSerialNo() == null) && (that.getDeviceSerialNo() == null)) ||
						(this.getDeviceSerialNo() != null && this.getDeviceSerialNo().equals(that.getDeviceSerialNo()))) &&
						this.getProductID() == that.getProductID()  && 
						this.getDeviceID() == that.getDeviceID()  && 
						(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate())))&&
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
       buf.append(",").append(deviceID);
		buf.append(",").append(productID);
		buf.append(",").append(deviceSerialNo);
        buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

	


}

