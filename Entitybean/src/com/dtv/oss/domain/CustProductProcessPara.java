package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CustProductProcessParaDTO;

public interface CustProductProcessPara extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setPsId(int psId);

	public int getPsId();

	public void setCpcmId(int cpcmId);

	public int getCpcmId();

	public void setAction(String action);

	public String getAction();

	public void setActionTime(Timestamp actionTime);

	public Timestamp getActionTime();

	public void setOrgId(int orgId);

	public int getOrgId();

	public void setOperatorId(int operatorId);

	public int getOperatorId();

	public void setProcessTime(Timestamp processTime);

	public Timestamp getProcessTime();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(CustProductProcessParaDTO dto);
}