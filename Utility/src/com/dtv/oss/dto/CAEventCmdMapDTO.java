package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CAEventCmdMapDTO implements ReflectionSupport, java.io.Serializable
{
	private String description;
	private int priority;
	private String status;
	private int mapID;
	private int mapCmdID;
	private int mapEventID;
	private int mapConditionID;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public CAEventCmdMapDTO()
	{
	}

	public CAEventCmdMapDTO(String description, int priority, String status, int mapID, int mapCmdID, int mapEventID, int mapConditionID, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setDescription(description);
		setPriority(priority);
		setStatus(status);
		setMapID(mapID);
		setMapCmdID(mapCmdID);
		setMapEventID(mapEventID);
		setMapConditionID(mapConditionID);
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

	public void setPriority(int priority)
	{
		this.priority = priority;
		put("priority");
	}

	public int getPriority()
	{
		return priority;
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

	public void setMapID(int mapID)
	{
		this.mapID = mapID;
		//put("mapID");
	}

	public int getMapID()
	{
		return mapID;
	}

	public void setMapCmdID(int mapCmdID)
	{
		this.mapCmdID = mapCmdID;
		put("mapCmdID");
	}

	public int getMapCmdID()
	{
		return mapCmdID;
	}

	public void setMapEventID(int mapEventID)
	{
		this.mapEventID = mapEventID;
		put("mapEventID");
	}

	public int getMapEventID()
	{
		return mapEventID;
	}

	public void setMapConditionID(int mapConditionID)
	{
		this.mapConditionID = mapConditionID;
		put("mapConditionID");
	}

	public int getMapConditionID()
	{
		return mapConditionID;
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
				CAEventCmdMapDTO that = (CAEventCmdMapDTO) obj;
				return
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
					this.getPriority() == that.getPriority()  &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					this.getMapID() == that.getMapID()  &&
					this.getMapCmdID() == that.getMapCmdID()  &&
					this.getMapEventID() == that.getMapEventID()  &&
					this.getMapConditionID() == that.getMapConditionID()  &&
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
		buf.append(",").append(priority);
		buf.append(",").append(status);
		buf.append(",").append(mapID);
		buf.append(",").append(mapCmdID);
		buf.append(",").append(mapEventID);
		buf.append(",").append(mapConditionID);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

