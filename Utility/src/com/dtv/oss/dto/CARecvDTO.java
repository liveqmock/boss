package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CARecvDTO implements ReflectionSupport, java.io.Serializable
{
	private int queueID;
	private int eventID;
	private int transID;
	private String recvData;
	private String recvString;
	private String errorCode;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public CARecvDTO()
	{
	}

	public CARecvDTO(int queueID, int eventID, int transID, String recvData, String recvString, String errorCode, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setQueueID(queueID);
		setEventID(eventID);
		setTransID(transID);
		setRecvData(recvData);
		setRecvString(recvString);
		setErrorCode(errorCode);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
	}

	public void setQueueID(int queueID)
	{
		this.queueID = queueID;
		//put("queueID");
	}

	public int getQueueID()
	{
		return queueID;
	}

	public void setEventID(int eventID)
	{
		this.eventID = eventID;
		put("eventID");
	}

	public int getEventID()
	{
		return eventID;
	}

	public void setTransID(int transID)
	{
		this.transID = transID;
		put("transID");
	}

	public int getTransID()
	{
		return transID;
	}

	public void setRecvData(String recvData)
	{
		this.recvData = recvData;
		put("recvData");
	}

	public String getRecvData()
	{
		return recvData;
	}

	public void setRecvString(String recvString)
	{
		this.recvString = recvString;
		put("recvString");
	}

	public String getRecvString()
	{
		return recvString;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
		put("errorCode");
	}

	public String getErrorCode()
	{
		return errorCode;
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
				CARecvDTO that = (CARecvDTO) obj;
				return
					this.getQueueID() == that.getQueueID()  &&
					this.getEventID() == that.getEventID()  &&
					this.getTransID() == that.getTransID()  &&
					(((this.getRecvData() == null) && (that.getRecvData() == null)) ||
						(this.getRecvData() != null && this.getRecvData().equals(that.getRecvData()))) &&
					(((this.getRecvString() == null) && (that.getRecvString() == null)) ||
						(this.getRecvString() != null && this.getRecvString().equals(that.getRecvString()))) &&
					(((this.getErrorCode() == null) && (that.getErrorCode() == null)) ||
						(this.getErrorCode() != null && this.getErrorCode().equals(that.getErrorCode()))) &&
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
		buf.append(queueID);
		buf.append(",").append(eventID);
		buf.append(",").append(transID);
		buf.append(",").append(recvData);
		buf.append(",").append(recvString);
		buf.append(",").append(errorCode);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

