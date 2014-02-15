package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.ServiceResourceDTO;

public interface ServiceResource extends javax.ejb.EJBLocalObject {
	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public String getResourceName();

	public void setResourceDesc(String resourceDesc);

	public String getResourceDesc();

	public void setResourceType(String valueType);

	public String getResourceType();

	 

	 

	public int ejbUpdate(ServiceResourceDTO dto);
}