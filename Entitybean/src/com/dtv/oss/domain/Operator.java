package com.dtv.oss.domain;

import java.sql.Timestamp;
import java.util.Collection;

import com.dtv.oss.dto.OperatorDTO;

public interface Operator extends javax.ejb.EJBLocalObject {
	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setOpGroup_R(Collection opGroup_R);

	public Collection getOpGroup_R();

	public Integer getOperatorID();

	public void setOperatorName(String operatorName);

	public String getOperatorName();

	public void setOperatorDesc(String operatorDesc);

	public String getOperatorDesc();

	public void setLoginID(String loginID);

	public String getLoginID();

	public void setLoginPwd(String loginPwd);

	public String getLoginPwd();

	public void setOrgID(int orgID);

	public int getOrgID();

	public  void setInternalUserFlag(java.lang.String internalUserFlag);
	
	public  java.lang.String getInternalUserFlag();

	 

	public void setLevelID(int levelID);

	public int getLevelID();

	public int ejbUpdate(OperatorDTO dto);
}