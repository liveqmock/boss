package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.ScheduleDTO;

public interface Schedule extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setCsiId(int csiId);

	public int getCsiId();

	public void setCustId(int custId);

	public int getCustId();

	public void setAcctId(int acctId);

	public int getAcctId();

	public void setServiceAccountId(int serviceAccountId);

	public int getServiceAccountId();

	public void setPsId(int psId);

	public int getPsId();

	public void setEvent(int event);

	public int getEvent();

	public void setReason(String reason);

	public String getReason();

	public void setCreateDate(Timestamp createDate);

	public Timestamp getCreateDate();

	public void setExecuteDate(Timestamp executeDate);

	public Timestamp getExecuteDate();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(ScheduleDTO dto);
}