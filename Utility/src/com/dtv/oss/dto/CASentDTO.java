package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CASentDTO implements ReflectionSupport, java.io.Serializable
{
	private String status;
	private String errorCode;
	private int hostID;
	private int queueID;
	private int eventID;
	private int transID;
	private String sentData;
	private String sendString;
	private Timestamp sentTime;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public CASentDTO()
	{
	}

	public CASentDTO(String status, String errorCode, int hostID, int queueID, int eventID, int transID, String sentData, String sendString, Timestamp sentTime, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setStatus(status);
		setErrorCode(errorCode);
		setHostID(hostID);
		setQueueID(queueID);
		setEventID(eventID);
		setTransID(transID);
		setSentData(sentData);
		setSendString(sendString);
		setSentTime(sentTime);
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

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
		put("errorCode");
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setHostID(int hostID)
	{
		this.hostID = hostID;
		put("hostID");
	}

	public int getHostID()
	{
		return hostID;
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

	public void setSentData(String sentData)
	{
		this.sentData = sentData;
		put("sentData");
	}

	public String getSentData()
	{
		return sentData;
	}

	public void setSendString(String sendString)
	{
		this.sendString = sendString;
		put("sendString");
	}

	public String getSendString()
	{
		return sendString;
	}

	public void setSentTime(Timestamp sentTime)
	{
		this.sentTime = sentTime;
		put("sentTime");
	}

	public Timestamp getSentTime()
	{
		return sentTime;
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
				CASentDTO that = (CASentDTO) obj;
				return
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					(((this.getErrorCode() == null) && (that.getErrorCode() == null)) ||
						(this.getErrorCode() != null && this.getErrorCode().equals(that.getErrorCode()))) &&
					this.getHostID() == that.getHostID()  &&
					this.getQueueID() == that.getQueueID()  &&
					this.getEventID() == that.getEventID()  &&
					this.getTransID() == that.getTransID()  &&
					(((this.getSentData() == null) && (that.getSentData() == null)) ||
						(this.getSentData() != null && this.getSentData().equals(that.getSentData()))) &&
					(((this.getSendString() == null) && (that.getSendString() == null)) ||
						(this.getSendString() != null && this.getSendString().equals(that.getSendString()))) &&
					(((this.getSentTime() == null) && (that.getSentTime() == null)) ||
						(this.getSentTime() != null && this.getSentTime().equals(that.getSentTime()))) &&
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
		buf.append(",").append(errorCode);
		buf.append(",").append(hostID);
		buf.append(",").append(queueID);
		buf.append(",").append(eventID);
		buf.append(",").append(transID);
		buf.append(",").append(sentData);
		buf.append(",").append(sendString);
		buf.append(",").append(sentTime);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

