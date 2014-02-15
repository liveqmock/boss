package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CustAdditionInfoDTO;

public interface CustAdditionInfo extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setCustomerId(int customerId);

	public int getCustomerId();

	public void setApplicationDept(String applicationDept);

	public String getApplicationDept();

	public void setApplicant(String applicant);

	public String getApplicant();

	public void setBeginValidDate(Timestamp beginValidDate);

	public Timestamp getBeginValidDate();

	public void setEndValidDate(Timestamp endValidDate);

	public Timestamp getEndValidDate();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setDurationLimited(String durationLimited);

	public String getDurationLimited();

	public int ejbUpdate(CustAdditionInfoDTO dto);

}