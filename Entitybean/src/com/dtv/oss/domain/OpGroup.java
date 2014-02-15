package com.dtv.oss.domain;

import java.sql.Timestamp;
import java.util.Collection;

import com.dtv.oss.dto.OpGroupDTO;

public interface OpGroup extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setSystemPrivilege_R(Collection systemPrivilege_R);

	public Collection getSystemPrivilege_R();

	public Integer getOpGroupID();

	public void setOpGroupName(String opGroupName);

	public String getOpGroupName();

	public void setOpGroupDesc(String opGroupDesc);

	public String getOpGroupDesc();

	public void setOpGroupLevel(String opGroupLevel);

	public String getOpGroupLevel();
	
	public  void setSystemFlag(java.lang.String systemFlag);
	
	public  java.lang.String getSystemFlag();

	public int ejbUpdate(OpGroupDTO dto);
}