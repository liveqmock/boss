package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class GroupBargainDetailDTO implements ReflectionSupport, java.io.Serializable
{
	private int id;
	private String detailNo;
	private int groupBargainID;
	private int userID;
	private Timestamp usedDate;
	private String status;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public GroupBargainDetailDTO()
	{
	}

	public GroupBargainDetailDTO(int id, String detailNo, int groupBargainID, int userID, Timestamp usedDate, String status, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setId(id);
		setDetailNo(detailNo);
		setGroupBargainID(groupBargainID);
		setUserID(userID);
		setUsedDate(usedDate);
		setStatus(status);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
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

	public void setDetailNo(String detailNo)
	{
		this.detailNo = detailNo;
		put("detailNo");
	}

	public String getDetailNo()
	{
		return detailNo;
	}

	public void setGroupBargainID(int groupBargainID)
	{
		this.groupBargainID = groupBargainID;
		put("groupBargainID");
	}

	public int getGroupBargainID()
	{
		return groupBargainID;
	}

	public void setUserID(int userID)
	{
		this.userID = userID;
		put("userID");
	}

	public int getUserID()
	{
		return userID;
	}

	public void setUsedDate(Timestamp usedDate)
	{
		this.usedDate = usedDate;
		put("usedDate");
	}

	public Timestamp getUsedDate()
	{
		return usedDate;
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
				GroupBargainDetailDTO that = (GroupBargainDetailDTO) obj;
				return
					this.getId() == that.getId()  &&
					(((this.getDetailNo() == null) && (that.getDetailNo() == null)) ||
						(this.getDetailNo() != null && this.getDetailNo().equals(that.getDetailNo()))) &&
					this.getGroupBargainID() == that.getGroupBargainID()  &&
					this.getUserID() == that.getUserID()  &&
					(((this.getUsedDate() == null) && (that.getUsedDate() == null)) ||
						(this.getUsedDate() != null && this.getUsedDate().equals(that.getUsedDate()))) &&
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
		buf.append(",").append(detailNo);
		buf.append(",").append(groupBargainID);
		buf.append(",").append(userID);
		buf.append(",").append(usedDate);
		buf.append(",").append(status);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

