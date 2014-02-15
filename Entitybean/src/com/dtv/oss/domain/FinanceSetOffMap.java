package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.FinanceSetOffMapDTO;

public interface FinanceSetOffMap extends javax.ejb.EJBLocalObject {
	public void setSType(String sType);

	public String getSType();

	public void setPlusReferType(String plusReferType);

	public String getPlusReferType();

	public void setPlusReferId(int plusReferId);

	public int getPlusReferId();

	public void setMinusReferType(String minusReferType);

	public String getMinusReferType();

	public void setMinusReferId(int minusReferId);

	public int getMinusReferId();
	
	 public  void setComments(String comments);
	  public  String getComments();

	public void setValue(double value);

	public double getValue();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getSeqNo();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setOpId(int opId);

	public int getOpId();

	public void setOrgId(int orgId);

	public int getOrgId();

	public void setCustId(int custId);

	public int getCustId();

	public void setAcctId(int acctId);

	public int getAcctId();

	public int ejbUpdate(FinanceSetOffMapDTO dto);
}