package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.ScheduleProcessLogDTO;

public interface ScheduleProcessLog extends javax.ejb.EJBLocalObject {
	public void setAction(String action);

	public String getAction();

	public void setResult(String result);

	public String getResult();

	public void setComments(String comments);

	public String getComments();

	public void setOpId(int opId);

	public int getOpId();

	public void setOrgId(int orgId);

	public int getOrgId();

	public void setCreateDate(Timestamp createDate);

	public Timestamp getCreateDate();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getSeqNo();

	public void setScheduleId(int scheduleId);

	public int getScheduleId();

	public int ejbUpdate(ScheduleProcessLogDTO dto);
}