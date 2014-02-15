package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CPParameterDTO implements ReflectionSupport, java.io.Serializable
{
	private int psID;
	private String paramName;
	private String paramValue;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public CPParameterDTO()
	{
	}

	public CPParameterDTO(int psID, String paramName, String paramValue, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setPsID(psID);
		setParamName(paramName);
		setParamValue(paramValue);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
	}

	public void setPsID(int psID)
	{
		this.psID = psID;
		//put("psID");
	}

	public int getPsID()
	{
		return psID;
	}

	public void setParamName(String paramName)
	{
		this.paramName = paramName;
		put("paramName");
	}

	public String getParamName()
	{
		return paramName;
	}

	public void setParamValue(String paramValue)
	{
		this.paramValue = paramValue;
		put("paramValue");
	}

	public String getParamValue()
	{
		return paramValue;
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
				CPParameterDTO that = (CPParameterDTO) obj;
				return
					this.getPsID() == that.getPsID()  &&
					(((this.getParamName() == null) && (that.getParamName() == null)) ||
						(this.getParamName() != null && this.getParamName().equals(that.getParamName()))) &&
					(((this.getParamValue() == null) && (that.getParamValue() == null)) ||
						(this.getParamValue() != null && this.getParamValue().equals(that.getParamValue()))) &&
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
		buf.append(",").append(paramName);
		buf.append(",").append(paramValue);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

