package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class BackgroundJobDTO implements ReflectionSupport, java.io.Serializable
{
	private int id;
	private int action;
	private int actionType;
	private String jobName;
	private String jobGroup;
	private String descr;
	private String jobClassName;
	private int queryCriteriaID;
	private int event;
	private Timestamp startTime;
	private long spanTime;
	private int repeatTime;
	private String status;
	private int createOperatorID;
	private String comments;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	private Timestamp beginTime;
	private Timestamp endTime;


	public BackgroundJobDTO()
	{
	}

	public BackgroundJobDTO(Timestamp endTime,Timestamp beginTime,int createOperatorID,String comments,int id, int action, int actionType, String jobName, String jobGroup, String descr, String jobClassName, int queryCriteriaID, int event, Timestamp startTime, long spanTime, int repeatTime, String status, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setId(id);
		setAction(action);
		setActionType(actionType);
		setJobName(jobName);
		setJobGroup(jobGroup);
		setDescr(descr);
		setJobClassName(jobClassName);
		setQueryCriteriaID(queryCriteriaID);
		setEvent(event);
		setStartTime(startTime);
		setSpanTime(spanTime);
		setRepeatTime(repeatTime);
		setStatus(status);
		setDtCreate(dtCreate);
		setCreateOperatorID(createOperatorID);
		setComments(comments);
		setDtLastmod(dtLastmod);
		setBeginTime(beginTime);
		setEndTime(endTime);
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
	public void setCreateOperatorID(int createOperatorID)
	{
		this.createOperatorID = createOperatorID;
		put("createOperatorID");
	}

	public int getCreateOperatorID()
	{
		return createOperatorID;
	}
	
	public void setComments(String comments)
	{
		this.comments = comments;
		put("comments");
	}

	public String getComments()
	{
		return comments;
	}
	public void setAction(int action)
	{
		this.action = action;
		put("action");
	}

	public int getAction()
	{
		return action;
	}

	public void setActionType(int actionType)
	{
		this.actionType = actionType;
		put("actionType");
	}

	public int getActionType()
	{
		return actionType;
	}

	public void setJobName(String jobName)
	{
		this.jobName = jobName;
		put("jobName");
	}

	public String getJobName()
	{
		return jobName;
	}

	public void setJobGroup(String jobGroup)
	{
		this.jobGroup = jobGroup;
		put("jobGroup");
	}

	public String getJobGroup()
	{
		return jobGroup;
	}

	public void setDescr(String descr)
	{
		this.descr = descr;
		put("descr");
	}

	public String getDescr()
	{
		return descr;
	}

	public void setJobClassName(String jobClassName)
	{
		this.jobClassName = jobClassName;
		put("jobClassName");
	}

	public String getJobClassName()
	{
		return jobClassName;
	}

	public void setQueryCriteriaID(int queryCriteriaID)
	{
		this.queryCriteriaID = queryCriteriaID;
		put("queryCriteriaID");
	}

	public int getQueryCriteriaID()
	{
		return queryCriteriaID;
	}

	public void setEvent(int event)
	{
		this.event = event;
		put("event");
	}

	public int getEvent()
	{
		return event;
	}

	public void setStartTime(Timestamp startTime)
	{
		this.startTime = startTime;
		put("startTime");
	}

	public Timestamp getStartTime()
	{
		return startTime;
	}

	public void setSpanTime(long spanTime)
	{
		this.spanTime = spanTime;
		put("spanTime");
	}

	public long getSpanTime()
	{
		return spanTime;
	}

	public void setRepeatTime(int repeatTime)
	{
		this.repeatTime = repeatTime;
		put("repeatTime");
	}

	public int getRepeatTime()
	{
		return repeatTime;
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

	public void setEndTime(Timestamp endTime)
	{
		this.endTime = endTime;
	}

	public Timestamp getEndTime()
	{
		return endTime;
	}

	public void setBeginTime(Timestamp beginTime)
	{
		this.beginTime = beginTime;
	}

	public Timestamp getBeginTime()
	{
		return beginTime;
	}

	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				BackgroundJobDTO that = (BackgroundJobDTO) obj;
				return
					this.getId() == that.getId()  &&
					this.getCreateOperatorID() == that.getCreateOperatorID()  &&
					this.getAction() == that.getAction()  &&
					this.getActionType() == that.getActionType()  &&
					(((this.getJobName() == null) && (that.getJobName() == null)) ||
						(this.getJobName() != null && this.getJobName().equals(that.getJobName()))) &&
					(((this.getComments() == null) && (that.getComments() == null)) ||
						(this.getComments() != null && this.getComments().equals(that.getComments()))) &&		
					(((this.getJobGroup() == null) && (that.getJobGroup() == null)) ||
						(this.getJobGroup() != null && this.getJobGroup().equals(that.getJobGroup()))) &&
					(((this.getDescr() == null) && (that.getDescr() == null)) ||
						(this.getDescr() != null && this.getDescr().equals(that.getDescr()))) &&
					(((this.getJobClassName() == null) && (that.getJobClassName() == null)) ||
						(this.getJobClassName() != null && this.getJobClassName().equals(that.getJobClassName()))) &&
					this.getQueryCriteriaID() == that.getQueryCriteriaID()  &&
					this.getEvent() == that.getEvent()  &&
					(((this.getStartTime() == null) && (that.getStartTime() == null)) ||
						(this.getStartTime() != null && this.getStartTime().equals(that.getStartTime()))) &&
					this.getSpanTime() == that.getSpanTime()  &&
					this.getRepeatTime() == that.getRepeatTime()  &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
							(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
						(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
							(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
					(((this.getBeginTime() == null) && (that.getBeginTime() == null)) ||
						(this.getBeginTime() != null && this.getBeginTime().equals(that.getBeginTime()))) &&
					(((this.getEndTime() == null) && (that.getEndTime() == null)) ||
						(this.getEndTime() != null && this.getEndTime().equals(that.getEndTime()))) &&
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
		buf.append(",").append(action);
		buf.append(",").append(actionType);
		buf.append(",").append(jobName);
		buf.append(",").append(jobGroup);
		buf.append(",").append(descr);
		buf.append(",").append(jobClassName);
		buf.append(",").append(queryCriteriaID);
		buf.append(",").append(event);
		buf.append(",").append(startTime);
		buf.append(",").append(spanTime);
		buf.append(",").append(repeatTime);
		buf.append(",").append(status);
		buf.append(",").append(createOperatorID);
		buf.append(",").append(comments);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(beginTime);
		buf.append(",").append(endTime);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

