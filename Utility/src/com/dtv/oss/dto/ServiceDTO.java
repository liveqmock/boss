package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class ServiceDTO implements ReflectionSupport, java.io.Serializable
{
	private String description;
	private Timestamp dateFrom;
	private Timestamp dateTo;
	private String status;
	private int serviceID;
	private String serviceName;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public ServiceDTO()
	{
	}

	public ServiceDTO(String description, Timestamp dateFrom, Timestamp dateTo, String status, int serviceID, String serviceName, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setDescription(description);
		setDateFrom(dateFrom);
		setDateTo(dateTo);
		setStatus(status);
		setServiceID(serviceID);
		setServiceName(serviceName);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
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

	public void setDateFrom(Timestamp dateFrom)
	{
		this.dateFrom = dateFrom;
		put("dateFrom");
	}

	public Timestamp getDateFrom()
	{
		return dateFrom;
	}

	public void setDateTo(Timestamp dateTo)
	{
		this.dateTo = dateTo;
		put("dateTo");
	}

	public Timestamp getDateTo()
	{
		return dateTo;
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

	public void setServiceID(int serviceID)
	{
		this.serviceID = serviceID;
		//put("serviceID");
	}

	public int getServiceID()
	{
		return serviceID;
	}

	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
		put("serviceName");
	}

	public String getServiceName()
	{
		return serviceName;
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
				ServiceDTO that = (ServiceDTO) obj;
				return
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
					(((this.getDateFrom() == null) && (that.getDateFrom() == null)) ||
						(this.getDateFrom() != null && this.getDateFrom().equals(that.getDateFrom()))) &&
					(((this.getDateTo() == null) && (that.getDateTo() == null)) ||
						(this.getDateTo() != null && this.getDateTo().equals(that.getDateTo()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					this.getServiceID() == that.getServiceID()  &&
					(((this.getServiceName() == null) && (that.getServiceName() == null)) ||
						(this.getServiceName() != null && this.getServiceName().equals(that.getServiceName()))) &&
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
		buf.append(",").append(dateFrom);
		buf.append(",").append(dateTo);
		buf.append(",").append(status);
		buf.append(",").append(serviceID);
		buf.append(",").append(serviceName);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

