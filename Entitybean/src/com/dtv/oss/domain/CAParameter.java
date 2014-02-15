package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CAParameterDTO;

public interface CAParameter extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public String getPmName();

	public void setPmType(String pmType);

	public String getPmType();

	public void setPmSize(int pmSize);

	public int getPmSize();

	public void setPmHasDefault(int pmHasDefault);

	public int getPmHasDefault();

	public void setPmDefault(String pmDefault);

	public String getPmDefault();

	public void setPmDesc(String pmDesc);

	public String getPmDesc();

	public int ejbUpdate(CAParameterDTO dto);
}