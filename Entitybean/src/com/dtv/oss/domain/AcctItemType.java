package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.AcctItemTypeDTO;

public interface AcctItemType extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setFeeType(String feeType);

	public String getFeeType();

	public String getAcctItemTypeID();

	public void setAcctItemTypeName(String acctItemTypeName);

	public String getAcctItemTypeName();

	public void setShowLevel(String showLevel);

	public String getShowLevel();

	public void setSummaryAiFlag(String summaryAiFlag);

	public String getSummaryAiFlag();

	public void setSummaryTo(String summaryTo);

	public String getSummaryTo();

	public void setSpecialSetOffFlag(String specialSetOffFlag);

	public String getSpecialSetOffFlag();

	public void setStatus(String status);

	public String getStatus();
	
	public  java.lang.String getSystemFlag();
	public  void setAcctItemTypeID(java.lang.String acctItemTypeID);

	public int ejbUpdate(AcctItemTypeDTO dto);
}