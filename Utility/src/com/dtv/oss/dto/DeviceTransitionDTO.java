package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class DeviceTransitionDTO implements ReflectionSupport, java.io.Serializable
{
	private String description;
	private String action;
	private int deviceNumber;
	private String fromType;
	private int fromID;
	private String statusReason;
	private String toType;
	private int toID;
	private String dataFileName;
	private String status;
	private int batchID;
	private String batchNo;
	private Timestamp createTime;
	private int operatorID;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public DeviceTransitionDTO()
	{
	}

	public DeviceTransitionDTO(String description, String action, int deviceNumber, String fromType, int fromID, String statusReason, String toType, int toID, String toDeviceStatus, String dataFileName, String status, int batchID, String batchNo, Timestamp createTime, int operatorID, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setDescription(description);
		setAction(action);
		setDeviceNumber(deviceNumber);
		setFromType(fromType);
		setFromID(fromID);
		setStatusReason(statusReason);
		setToType(toType);
		setToID(toID);
		setDataFileName(dataFileName);
		setStatus(status);
		setBatchID(batchID);
		setBatchNo(batchNo);
		setCreateTime(createTime);
		setOperatorID(operatorID);
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

	public void setAction(String action)
	{
		this.action = action;
		put("action");
	}

	public String getAction()
	{
		return action;
	}

	public void setDeviceNumber(int deviceNumber)
	{
		this.deviceNumber = deviceNumber;
		put("deviceNumber");
	}

	public int getDeviceNumber()
	{
		return deviceNumber;
	}

	public void setFromType(String fromType)
	{
		this.fromType = fromType;
		put("fromType");
	}

	public String getFromType()
	{
		return fromType;
	}

	public void setFromID(int fromID)
	{
		this.fromID = fromID;
		put("fromID");
	}

	public int getFromID()
	{
		return fromID;
	}

	public void setStatusReason(String statusReason)
	{
		this.statusReason = statusReason;
		put("statusReason");
	}

	public String getStatusReason()
	{
		return statusReason;
	}

	public void setToType(String toType)
	{
		this.toType = toType;
		put("toType");
	}

	public String getToType()
	{
		return toType;
	}

	public void setToID(int toID)
	{
		this.toID = toID;
		put("toID");
	}

	public int getToID()
	{
		return toID;
	}

	public void setDataFileName(String dataFileName)
	{
		this.dataFileName = dataFileName;
		put("dataFileName");
	}

	public String getDataFileName()
	{
		return dataFileName;
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

	public void setBatchID(int batchID)
	{
		this.batchID = batchID;
		//put("batchID");
	}

	public int getBatchID()
	{
		return batchID;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
		put("batchNo");
	}

	public String getBatchNo()
	{
		return batchNo;
	}

	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
		put("createTime");
	}

	public Timestamp getCreateTime()
	{
		return createTime;
	}

	public void setOperatorID(int operatorID)
	{
		this.operatorID = operatorID;
		put("operatorID");
	}

	public int getOperatorID()
	{
		return operatorID;
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
				DeviceTransitionDTO that = (DeviceTransitionDTO) obj;
				return
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
					(((this.getAction() == null) && (that.getAction() == null)) ||
						(this.getAction() != null && this.getAction().equals(that.getAction()))) &&
					this.getDeviceNumber() == that.getDeviceNumber()  &&
					(((this.getFromType() == null) && (that.getFromType() == null)) ||
						(this.getFromType() != null && this.getFromType().equals(that.getFromType()))) &&
					this.getFromID() == that.getFromID()  &&
					(((this.getStatusReason() == null) && (that.getStatusReason() == null)) ||
						(this.getStatusReason() != null && this.getStatusReason().equals(that.getStatusReason()))) &&
					(((this.getToType() == null) && (that.getToType() == null)) ||
						(this.getToType() != null && this.getToType().equals(that.getToType()))) &&
					this.getToID() == that.getToID()  &&
					(((this.getDataFileName() == null) && (that.getDataFileName() == null)) ||
						(this.getDataFileName() != null && this.getDataFileName().equals(that.getDataFileName()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					this.getBatchID() == that.getBatchID()  &&
					(((this.getBatchNo() == null) && (that.getBatchNo() == null)) ||
						(this.getBatchNo() != null && this.getBatchNo().equals(that.getBatchNo()))) &&
					(((this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
						(this.getCreateTime() != null && this.getCreateTime().equals(that.getCreateTime()))) &&
					this.getOperatorID() == that.getOperatorID()  &&
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
		buf.append(",").append(action);
		buf.append(",").append(deviceNumber);
		buf.append(",").append(fromType);
		buf.append(",").append(fromID);
		buf.append(",").append(statusReason);
		buf.append(",").append(toType);
		buf.append(",").append(toID);
		buf.append(",").append(dataFileName);
		buf.append(",").append(status);
		buf.append(",").append(batchID);
		buf.append(",").append(batchNo);
		buf.append(",").append(createTime);
		buf.append(",").append(operatorID);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

