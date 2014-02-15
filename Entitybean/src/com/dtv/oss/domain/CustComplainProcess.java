package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CustComplainProcessDTO;

public interface CustComplainProcess extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setCustComplainId(int custComplainId);

	public int getCustComplainId();

	public void setCurrentOrgId(int currentOrgId);

	public int getCurrentOrgId();

	public void setCurrentOperatorId(int currentOperatorId);

	public int getCurrentOperatorId();

	public void setNextOrgId(int nextOrgId);

	public int getNextOrgId();

	public void setAction(String action);

	public String getAction();

	public void setCreateDate(Timestamp createDate);

	public Timestamp getCreateDate();

	public void setStatus(String status);

	public String getStatus();

	public void setDescription(String description);

	public String getDescription();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(CustComplainProcessDTO dto);
}