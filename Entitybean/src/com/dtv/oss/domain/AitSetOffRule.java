package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.AitSetOffRuleDTO;

public interface AitSetOffRule extends javax.ejb.EJBLocalObject {
	public Integer getSeqNo();

	public void setSType(int sType);

	public int getSType();

	public void setSrcAcctItemTypeId(String srcAcctItemTypeId);

	public String getSrcAcctItemTypeId();

	public void setDestAcctItemTypeId(String destAcctItemTypeId);

	public String getDestAcctItemTypeId();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int ejbUpdate(AitSetOffRuleDTO dto);
}