package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CACommandDTO implements ReflectionSupport, java.io.Serializable
{
	private String description;
	private int commandID;
	private String commandName;
	private String commandString;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public CACommandDTO()
	{
	}

	public CACommandDTO(String description, int commandID, String commandName, String commandString, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setDescription(description);
		setCommandID(commandID);
		setCommandName(commandName);
		setCommandString(commandString);
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

	public void setCommandID(int commandID)
	{
		this.commandID = commandID;
		//put("commandID");
	}

	public int getCommandID()
	{
		return commandID;
	}

	public void setCommandName(String commandName)
	{
		this.commandName = commandName;
		put("commandName");
	}

	public String getCommandName()
	{
		return commandName;
	}

	public void setCommandString(String commandString)
	{
		this.commandString = commandString;
		put("commandString");
	}

	public String getCommandString()
	{
		return commandString;
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
				CACommandDTO that = (CACommandDTO) obj;
				return
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
					this.getCommandID() == that.getCommandID()  &&
					(((this.getCommandName() == null) && (that.getCommandName() == null)) ||
						(this.getCommandName() != null && this.getCommandName().equals(that.getCommandName()))) &&
					(((this.getCommandString() == null) && (that.getCommandString() == null)) ||
						(this.getCommandString() != null && this.getCommandString().equals(that.getCommandString()))) &&
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
		buf.append(",").append(commandID);
		buf.append(",").append(commandName);
		buf.append(",").append(commandString);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

