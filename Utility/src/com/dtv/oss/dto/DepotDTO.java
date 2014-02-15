package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class DepotDTO implements ReflectionSupport, java.io.Serializable
{
	private String status;
	private int depotID;
	private String depotName;
	private String detailAddress;
	private int ownerID;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public DepotDTO()
	{
	}

	public DepotDTO(String status, int depotID, String depotName, String detailAddress, int ownerID, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setStatus(status);
		setDepotID(depotID);
		setDepotName(depotName);
		setDetailAddress(detailAddress);
		setOwnerID(ownerID);
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

	public void setDepotID(int depotID)
	{
		this.depotID = depotID;
		//put("depotID");
	}

	public int getDepotID()
	{
		return depotID;
	}

	public void setDepotName(String depotName)
	{
		this.depotName = depotName;
		put("depotName");
	}

	public String getDepotName()
	{
		return depotName;
	}

	public void setDetailAddress(String detailAddress)
	{
		this.detailAddress = detailAddress;
		put("detailAddress");
	}

	public String getDetailAddress()
	{
		return detailAddress;
	}

	public void setOwnerID(int ownerID)
	{
		this.ownerID = ownerID;
		put("ownerID");
	}

	public int getOwnerID()
	{
		return ownerID;
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
				DepotDTO that = (DepotDTO) obj;
				return
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					this.getDepotID() == that.getDepotID()  &&
					(((this.getDepotName() == null) && (that.getDepotName() == null)) ||
						(this.getDepotName() != null && this.getDepotName().equals(that.getDepotName()))) &&
					(((this.getDetailAddress() == null) && (that.getDetailAddress() == null)) ||
						(this.getDetailAddress() != null && this.getDetailAddress().equals(that.getDetailAddress()))) &&
					this.getOwnerID() == that.getOwnerID()  &&
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
		buf.append(",").append(depotID);
		buf.append(",").append(depotName);
		buf.append(",").append(detailAddress);
		buf.append(",").append(ownerID);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

