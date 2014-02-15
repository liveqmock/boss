package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CACuFormulaDTO implements ReflectionSupport, java.io.Serializable
{
	private int formulaID;
	private String formulaString;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public CACuFormulaDTO()
	{
	}

	public CACuFormulaDTO(int formulaID, String formulaString, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setFormulaID(formulaID);
		setFormulaString(formulaString);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
	}

	public void setFormulaID(int formulaID)
	{
		this.formulaID = formulaID;
		//put("formulaID");
	}

	public int getFormulaID()
	{
		return formulaID;
	}

	public void setFormulaString(String formulaString)
	{
		this.formulaString = formulaString;
		put("formulaString");
	}

	public String getFormulaString()
	{
		return formulaString;
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
				CACuFormulaDTO that = (CACuFormulaDTO) obj;
				return
					this.getFormulaID() == that.getFormulaID()  &&
					(((this.getFormulaString() == null) && (that.getFormulaString() == null)) ||
						(this.getFormulaString() != null && this.getFormulaString().equals(that.getFormulaString()))) &&
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
		buf.append(formulaID);
		buf.append(",").append(formulaString);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

