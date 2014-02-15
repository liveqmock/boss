package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class PalletDTO implements ReflectionSupport, java.io.Serializable
{
	private String status;
	private int palletID;
	private String palletName;
	private String palletDesc;
	private int maxNumberAllowed;
	private int depotID;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public PalletDTO()
	{
	}

	public PalletDTO(String status, int palletID, String palletName, String palletDesc, int maxNumberAllowed, int depotID, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setStatus(status);
		setPalletID(palletID);
		setPalletName(palletName);
		setPalletDesc(palletDesc);
		setMaxNumberAllowed(maxNumberAllowed);
		setDepotID(depotID);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
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

	public void setPalletID(int palletID)
	{
		this.palletID = palletID;
		//put("palletID");
	}

	public int getPalletID()
	{
		return palletID;
	}

	public void setPalletName(String palletName)
	{
		this.palletName = palletName;
		put("palletName");
	}

	public String getPalletName()
	{
		return palletName;
	}

	public void setPalletDesc(String palletDesc)
	{
		this.palletDesc = palletDesc;
		put("palletDesc");
	}

	public String getPalletDesc()
	{
		return palletDesc;
	}

	public void setMaxNumberAllowed(int maxNumberAllowed)
	{
		this.maxNumberAllowed = maxNumberAllowed;
		put("maxNumberAllowed");
	}

	public int getMaxNumberAllowed()
	{
		return maxNumberAllowed;
	}

	public void setDepotID(int depotID)
	{
		this.depotID = depotID;
		put("depotID");
	}

	public int getDepotID()
	{
		return depotID;
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
				PalletDTO that = (PalletDTO) obj;
				return
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					this.getPalletID() == that.getPalletID()  &&
					(((this.getPalletName() == null) && (that.getPalletName() == null)) ||
						(this.getPalletName() != null && this.getPalletName().equals(that.getPalletName()))) &&
					(((this.getPalletDesc() == null) && (that.getPalletDesc() == null)) ||
						(this.getPalletDesc() != null && this.getPalletDesc().equals(that.getPalletDesc()))) &&
					this.getMaxNumberAllowed() == that.getMaxNumberAllowed()  &&
					this.getDepotID() == that.getDepotID()  &&
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
		buf.append(status);
		buf.append(",").append(palletID);
		buf.append(",").append(palletName);
		buf.append(",").append(palletDesc);
		buf.append(",").append(maxNumberAllowed);
		buf.append(",").append(depotID);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

