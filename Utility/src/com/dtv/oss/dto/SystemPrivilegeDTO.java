package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class SystemPrivilegeDTO implements ReflectionSupport, java.io.Serializable
{
	private int privID;
	private String privName;
	private String privDesc;
	private String moduleName;
	private int levelID;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public SystemPrivilegeDTO()
	{
	}

	public SystemPrivilegeDTO(int privID, String privName, String privDesc, String moduleName, int levelID, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setPrivID(privID);
		setPrivName(privName);
		setPrivDesc(privDesc);
		setModuleName(moduleName);
		setLevelID(levelID);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
	}

	public void setPrivID(int privID)
	{
		this.privID = privID;
		//put("privID");
	}

	public int getPrivID()
	{
		return privID;
	}

	public void setPrivName(String privName)
	{
		this.privName = privName;
		put("privName");
	}

	public String getPrivName()
	{
		return privName;
	}

	public void setPrivDesc(String privDesc)
	{
		this.privDesc = privDesc;
		put("privDesc");
	}

	public String getPrivDesc()
	{
		return privDesc;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
		put("moduleName");
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setLevelID(int levelID)
	{
		this.levelID = levelID;
		put("levelID");
	}

	public int getLevelID()
	{
		return levelID;
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
				SystemPrivilegeDTO that = (SystemPrivilegeDTO) obj;
				return
					this.getPrivID() == that.getPrivID()  &&
					(((this.getPrivName() == null) && (that.getPrivName() == null)) ||
						(this.getPrivName() != null && this.getPrivName().equals(that.getPrivName()))) &&
					(((this.getPrivDesc() == null) && (that.getPrivDesc() == null)) ||
						(this.getPrivDesc() != null && this.getPrivDesc().equals(that.getPrivDesc()))) &&
					(((this.getModuleName() == null) && (that.getModuleName() == null)) ||
						(this.getModuleName() != null && this.getModuleName().equals(that.getModuleName()))) &&
					this.getLevelID() == that.getLevelID()  &&
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
		buf.append(privID);
		buf.append(",").append(privName);
		buf.append(",").append(privDesc);
		buf.append(",").append(moduleName);
		buf.append(",").append(levelID);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

