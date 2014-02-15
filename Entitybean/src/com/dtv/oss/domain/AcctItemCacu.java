package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.AcctItemCacuDTO;

public interface AcctItemCacu extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getAicSerialNo();

	public void setAcctItemTypeID(String acctItemTypeID);

	public String getAcctItemTypeID();

	public void setCacuMode(String cacuMode);

	public String getCacuMode();

	public int ejbUpdate(AcctItemCacuDTO dto);
}