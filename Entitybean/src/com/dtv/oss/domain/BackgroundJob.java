package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BackgroundJobDTO;

public interface BackgroundJob extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setAction(int action);

	public int getAction();

	public void setCreateOperatorID(int createOperatorID);

	public int getCreateOperatorID();

	public void setComments(String comments);

	public String getComments();

	public void setActionType(int actionType);

	public int getActionType();

	public void setJobName(String jobName);

	public String getJobName();

	public void setJobGroup(String jobGroup);

	public String getJobGroup();

	public void setDescr(String descr);

	public String getDescr();

	public void setJobClassName(String jobClassName);

	public String getJobClassName();

	public void setQueryCriteriaID(int queryCriteriaID);

	public int getQueryCriteriaID();

	public void setEvent(int event);

	public int getEvent();

	public void setStartTime(Timestamp startTime);

	public Timestamp getStartTime();

	public void setSpanTime(long spanTime);

	public long getSpanTime();

	public void setRepeatTime(int repeatTime);

	public int getRepeatTime();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setBeginTime(Timestamp beginTime);

	public Timestamp getBeginTime();

	public void setEndTime(Timestamp endTime);

	public Timestamp getEndTime();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(BackgroundJobDTO dto);
}