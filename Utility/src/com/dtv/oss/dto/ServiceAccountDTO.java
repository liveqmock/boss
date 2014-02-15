package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class ServiceAccountDTO implements ReflectionSupport, java.io.Serializable
{
	private String description;
	private String status;
	private String userType;
	private int serviceAccountID;
	private int serviceID;
	private int customerID;
	private int userID;
	private int subscriberID;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	private String serviceCode;
    private java.sql.Timestamp createTime;
    private String serviceAccountName;
	 

	/**
	 * @return Returns the serviceAccountName.
	 */
	public String getServiceAccountName() {
		return serviceAccountName;
	}
	/**
	 * @param serviceAccountName The serviceAccountName to set.
	 */
	public void setServiceAccountName(String serviceAccountName) {
		this.serviceAccountName = serviceAccountName;
		put("serviceAccountName");
	}
	public void setUserType(String userType)
	{
		this.userType = userType;
		put("userType");
	}

	public String getUserType()
	{
		return userType;
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


	public void setStatus(String status)
	{
		this.status = status;
		put("status");
	}

	public String getStatus()
	{
		return status;
	}

	public void setServiceAccountID(int serviceAccountID)
	{
		this.serviceAccountID = serviceAccountID;
		//put("serviceAccountID");
	}

	public int getServiceAccountID()
	{
		return serviceAccountID;
	}

	public void setServiceID(int serviceID)
	{
		this.serviceID = serviceID;
		put("serviceID");
	}

	public int getServiceID()
	{
		return serviceID;
	}

	public void setCustomerID(int customerID)
	{
		this.customerID = customerID;
		put("customerID");
	}

	public int getCustomerID()
	{
		return customerID;
	}

	public void setUserID(int userID)
	{
		this.userID = userID;
		put("userID");
	}

	public int getUserID()
	{
		return userID;
	}

	public void setSubscriberID(int subscriberID)
	{
		this.subscriberID = subscriberID;
		put("subscriberID");
	}

	public int getSubscriberID()
	{
		return subscriberID;
	}

	public void setCreateTime(java.sql.Timestamp createTime)
	{
		this.createTime = createTime;
		put("createTime;");
	}

	public java.sql.Timestamp getCreateTime()
	{
		return createTime;
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
				ServiceAccountDTO that = (ServiceAccountDTO) obj;
				return
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					this.getServiceAccountID() == that.getServiceAccountID()  &&
					this.getServiceID() == that.getServiceID()  &&
					this.getCustomerID() == that.getCustomerID()  &&
					this.getUserID() == that.getUserID()  &&
					this.getSubscriberID() == that.getSubscriberID()  &&
					(((this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
						(this.getCreateTime() != null && this.getCreateTime().equals(that.getCreateTime()))) &&
						(((this.getServiceAccountName() == null) && (that.getServiceAccountName() == null)) ||
								(this.getServiceAccountName() != null && this.getServiceAccountName().equals(that.getServiceAccountName()))) &&	
						(((this.getUserType() == null) && (that.getUserType() == null)) ||
								(this.getUserType() != null && this.getUserType().equals(that.getUserType()))) &&
					(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
					(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
						(this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod()))) &&
					(((this.getServiceCode() == null) && (that.getServiceCode() == null)) ||
							(this.getServiceCode() != null && this.getServiceCode().equals(that.getServiceCode())));
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
		buf.append(",").append(status);
		buf.append(",").append(serviceAccountID);
		buf.append(",").append(serviceID);
		buf.append(",").append(customerID);
		buf.append(",").append(userID);
		buf.append(",").append(userType);
		buf.append(",").append(subscriberID);
        buf.append(",").append(createTime);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(serviceCode);
		buf.append(",").append(serviceAccountName);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

	/**
	 * @return Returns the serviceCode.
	 */
	public String getServiceCode() {
		return serviceCode;
	}
	/**
	 * @param serviceCode The serviceCode to set.
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
		put("serviceCode");
	}
}

