package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.ValidDateChangeHistDTO;

public interface ValidDateChangeHist extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getSequenceNo();

	public void setCustomerId(int customerId);

	public int getCustomerId();

	public void setCustAdditionInfoId(int custAdditionInfoId);

	public int getCustAdditionInfoId();

	public void setValidBeginDate(Timestamp validBeginDate);

	public Timestamp getValidBeginDate();

	public void setValidEndDate(Timestamp validEndDate);

	public Timestamp getValidEndDate();

	public void setCreateDate(Timestamp createDate);

	public Timestamp getCreateDate();

	public void setOperatorId(int operatorId);

	public int getOperatorId();

	public int ejbUpdate(ValidDateChangeHistDTO dto);
}