package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CPCampaignMapDTO implements ReflectionSupport, java.io.Serializable
{
	private int id;
	private int custProductID;
	private int ccId;
	private String status;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public CPCampaignMapDTO()
	{
	}

	 

	public void setId(int id)
	{
		this.id = id;
		//put("id");
	}

	public int getId()
	{
		return id;
	}
	public int getCcId()
	{
		return ccId;
	}
	public void setCcId(int ccId)
	{
		this.ccId = ccId;
		put("ccId");
	}

	public void setCustProductID(int custProductID)
	{
		this.custProductID = custProductID;
		put("custProductID");
	}

	public int getCustProductID()
	{
		return custProductID;
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
				CPCampaignMapDTO that = (CPCampaignMapDTO) obj;
				return
					this.getId() == that.getId()  &&
					this.getCustProductID() == that.getCustProductID()  &&
					this.getCcId() == that.getCcId()  &&
					 
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
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
		buf.append(id);
		buf.append(",").append(custProductID);
	 
		buf.append(",").append(status);
		buf.append(",").append(dtCreate);
		buf.append(",").append(ccId);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

