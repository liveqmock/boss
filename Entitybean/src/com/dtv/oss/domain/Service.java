package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.ServiceDTO;

public interface Service extends javax.ejb.EJBLocalObject {
	public void setDescription(String description);

	public String getDescription();

	public void setDateFrom(Timestamp dateFrom);

	public Timestamp getDateFrom();

	public void setDateTo(Timestamp dateTo);

	public Timestamp getDateTo();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getServiceID();

	public void setServiceName(String serviceName);

	public String getServiceName();

	public int ejbUpdate(ServiceDTO dto);
}