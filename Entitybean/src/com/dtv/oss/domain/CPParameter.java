package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CPParameterDTO;

public interface CPParameter extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getPsID();

	public String getParamName();

	public void setParamValue(String paramValue);

	public String getParamValue();

	public int ejbUpdate(CPParameterDTO dto);
}