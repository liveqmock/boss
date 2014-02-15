package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CAConditionDTO implements ReflectionSupport, java.io.Serializable
{
	private String description;
	private int condID;
	private String condName;
	private int hostID;
	private String condString;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public CAConditionDTO()
	{
	}

	public CAConditionDTO(String description, int condID, String condName, int hostID, String condString, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setDescription(description);
		setCondID(condID);
		setCondName(condName);
		setHostID(hostID);
		setCondString(condString);
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

	public void setCondID(int condID)
	{
		this.condID = condID;
		//put("condID");
	}

	public int getCondID()
	{
		return condID;
	}

	public void setCondName(String condName)
	{
		this.condName = condName;
		put("condName");
	}

	public String getCondName()
	{
		return condName;
	}

	public void setHostID(int hostID)
	{
		this.hostID = hostID;
		put("hostID");
	}

	public int getHostID()
	{
		return hostID;
	}

	public void setCondString(String condString)
	{
		this.condString = condString;
		put("condString");
	}

	public String getCondString()
	{
		return condString;
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
				CAConditionDTO that = (CAConditionDTO) obj;
				return
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
					this.getCondID() == that.getCondID()  &&
					(((this.getCondName() == null) && (that.getCondName() == null)) ||
						(this.getCondName() != null && this.getCondName().equals(that.getCondName()))) &&
					this.getHostID() == that.getHostID()  &&
					(((this.getCondString() == null) && (that.getCondString() == null)) ||
						(this.getCondString() != null && this.getCondString().equals(that.getCondString()))) &&
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
		buf.append(",").append(condID);
		buf.append(",").append(condName);
		buf.append(",").append(hostID);
		buf.append(",").append(condString);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

