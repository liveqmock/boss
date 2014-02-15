package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class OpGroupDTO implements ReflectionSupport, java.io.Serializable
{
	private int opGroupID;
	private String opGroupName;
	private String opGroupDesc;
	private String opGroupLevel;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	private String systemFlag;
 
	
	
	 

	/**
	 * @return Returns the systemFlag.
	 */
	public String getSystemFlag() {
		return systemFlag;
	}
	/**
	 * @param systemFlag The systemFlag to set.
	 */
	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
		put("systemFlag");
	}
	public void setOpGroupID(int opGroupID)
	{
		this.opGroupID = opGroupID;
		//put("opGroupID");
	}

	public int getOpGroupID()
	{
		return opGroupID;
	}

	public void setOpGroupName(String opGroupName)
	{
		this.opGroupName = opGroupName;
		put("opGroupName");
	}

	public String getOpGroupName()
	{
		return opGroupName;
	}

	public void setOpGroupDesc(String opGroupDesc)
	{
		this.opGroupDesc = opGroupDesc;
		put("opGroupDesc");
	}

	public String getOpGroupDesc()
	{
		return opGroupDesc;
	}

	public void setOpGroupLevel(String opGroupLevel)
	{
		this.opGroupLevel = opGroupLevel;
		put("opGroupLevel");
	}

	public String getOpGroupLevel()
	{
		return opGroupLevel;
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
				OpGroupDTO that = (OpGroupDTO) obj;
				return
					this.getOpGroupID() == that.getOpGroupID()  &&
					(((this.getOpGroupName() == null) && (that.getOpGroupName() == null)) ||
						(this.getOpGroupName() != null && this.getOpGroupName().equals(that.getOpGroupName()))) &&
					(((this.getOpGroupDesc() == null) && (that.getOpGroupDesc() == null)) ||
						(this.getOpGroupDesc() != null && this.getOpGroupDesc().equals(that.getOpGroupDesc()))) &&
					(((this.getOpGroupLevel() == null) && (that.getOpGroupLevel() == null)) ||
						(this.getOpGroupLevel() != null && this.getOpGroupLevel().equals(that.getOpGroupLevel()))) &&
					(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
						(((this.getSystemFlag() == null) && (that.getSystemFlag() == null)) ||
								(this.getSystemFlag() != null && this.getSystemFlag().equals(that.getSystemFlag()))) &&	
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
		buf.append(opGroupID);
		buf.append(",").append(opGroupName);
		buf.append(",").append(opGroupDesc);
		buf.append(",").append(opGroupLevel);
		buf.append(",").append(systemFlag);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

