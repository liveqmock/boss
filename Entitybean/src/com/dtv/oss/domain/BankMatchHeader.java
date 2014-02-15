package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.BankMatchHeaderDTO;

public interface BankMatchHeader extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setMopId(int mopId);

	public int getMopId();

	public void setInterfaceId(int interfaceId);

	public int getInterfaceId();

	public void setBatchNo(int batchNo);

	public int getBatchNo();

	public void setStartTime(Timestamp startTime);

	public Timestamp getStartTime();

	public void setEndTime(Timestamp endTime);

	public Timestamp getEndTime();

	public void setOperatorId(int operatorId);

	public int getOperatorId();

	public void setOrgId(int orgId);

	public int getOrgId();

	public void setStatus(String status);

	public String getStatus();

	public void setInputFileName(String inputFileName);

	public String getInputFileName();

	public void setTotalRecordNo(int totalRecordNo);

	public int getTotalRecordNo();

	public void setInvalidRecordNo(int invalidRecordNo);

	public int getInvalidRecordNo();

	public void setSuccessRecordNo(int successRecordNo);

	public int getSuccessRecordNo();

	public void setFailRecordNo(int failRecordNo);

	public int getFailRecordNo();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(BankMatchHeaderDTO dto);
}