package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CsiProcessLogDTO;

public interface CsiProcessLog extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setCsiID(int csiID);

	public int getCsiID();

	public void setAction(String action);

	public String getAction();

	public void setDescription(String description);

	public String getDescription();

	public void setActionTime(Timestamp actionTime);

	public Timestamp getActionTime();

	public void setOperatorID(int operatorID);

	public int getOperatorID();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setOrgID(int orgID);

	public int getOrgID();

	public void setInvoiceNo(int invoiceNo);

	public int getInvoiceNo();

	public void setWorkResult(String workResult);

	public String getWorkResult();

	public void setWorkResultReason(String workResultReason);

	public String getWorkResultReason();

	public int ejbUpdate(CsiProcessLogDTO dto);
}