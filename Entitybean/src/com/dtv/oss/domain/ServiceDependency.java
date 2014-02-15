package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.ServiceDependencyDTO;

public interface ServiceDependency extends javax.ejb.EJBLocalObject {
	public void setType(String type);

	public String getType();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int getServiceId();

	public int getReferServiceId();

	public int ejbUpdate(ServiceDependencyDTO dto);
}