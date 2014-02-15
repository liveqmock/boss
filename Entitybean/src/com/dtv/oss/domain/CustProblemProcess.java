package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CustProblemProcessDTO;

public interface CustProblemProcess extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setCustProblemId(int custProblemId);

	public int getCustProblemId();

	public void setCurrentorgId(int currentorgId);

	public int getCurrentorgId();

	public void setCurrentOperatorId(int currentOperatorId);

	public int getCurrentOperatorId();

	public void setAction(String action);

	public String getAction();

	public void setCreateDate(Timestamp createDate);

	public Timestamp getCreateDate();

	public void setWorkResult(String workResult);

	public String getWorkResult();

	public void setWorkResultReason(String workResultReason);

	public String getWorkResultReason();

	public void setNextOrgId(int nextOrgId);

	public int getNextOrgId();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();
	
	public  void setMemo(String memo);
	
	public  String getMemo();

	public int ejbUpdate(CustProblemProcessDTO dto);
}