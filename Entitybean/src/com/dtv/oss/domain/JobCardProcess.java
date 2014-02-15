package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.JobCardProcessDTO;

public interface JobCardProcess extends javax.ejb.EJBLocalObject {
	public void setAction(String action);

	public String getAction();

	public void setActionTime(Timestamp actionTime);

	public Timestamp getActionTime();

	public void setProcessMan(String processMan);

	public String getProcessMan();

	public void setProcessOrgId(String processOrgId);

	public String getProcessOrgId();

	public void setWorkResult(String workResult);

	public String getWorkResult();

	public void setWorkResultReason(String workResultReason);

	public String getWorkResultReason();

	public void setWorkDate(Timestamp workDate);

	public Timestamp getWorkDate();

	public void setWorkTime(String workTime);

	public String getWorkTime();

	public void setOutOfDateReason(String outOfDateReason);

	public String getOutOfDateReason();

	public void setMemo(String memo);

	public String getMemo();

	public void setNextOrgId(int nextOrgId);

	public int getNextOrgId();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getSeqNo();

	public void setJcId(int jcId);

	public int getJcId();

	public void setCurrentOperatorId(int currentOperatorId);

	public int getCurrentOperatorId();

	public void setCurrentOrgId(int currentOrgId);

	public int getCurrentOrgId();

	public int ejbUpdate(JobCardProcessDTO dto);
}