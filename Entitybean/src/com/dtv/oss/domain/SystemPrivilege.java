package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.SystemPrivilegeDTO;

public interface SystemPrivilege extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(SystemPrivilegeDTO dto);

	public Integer getPrivID();

	public void setPrivName(String privName);

	public String getPrivName();

	public void setPrivDesc(String privDesc);

	public String getPrivDesc();

	public void setModuleName(String moduleName);

	public String getModuleName();

	public void setLevelID(int levelID);

	public int getLevelID();
}