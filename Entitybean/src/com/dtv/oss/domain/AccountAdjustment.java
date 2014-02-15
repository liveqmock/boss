package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.AccountAdjustmentDTO;

public interface AccountAdjustment extends javax.ejb.EJBLocalObject {
	public Integer getSeqNo();

	public void setCustID(int custID);

	public int getCustID();

	public void setAcctID(int acctID);

	public int getAcctID();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setCreateOrgID(int createOrgID);

	public int getCreateOrgID();

	public void setReason(String reason);

	public String getReason();

	public void setStatus(String status);

	public String getStatus();

	 

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setComments(String comments);

	public String getComments();

	public void setAdjustmentDate(Timestamp adjustmentDate);	 
	public Timestamp getAdjustmentDate();	 

	public void setAdjustmentType(String adjustmentType);

	public String getAdjustmentType();

	public void setCreateOpID(int createOpID);

	public int getCreateOpID();
	
	public   void setReferRecordID(int referRecordID);
	
	public   void setReferRecordType(String referRecordType);
	
	public   int getReferRecordID();
	 
	public   String getReferRecordType();
	public   void setReferSheetID(int referSheetID);
	public   int getReferSheetID();

	public int ejbUpdate(AccountAdjustmentDTO dto);
}