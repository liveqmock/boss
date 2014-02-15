package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.AcctItemSetOffRuleDTO;

public interface AcctItemSetOffRuleHome extends javax.ejb.EJBLocalHome {
	public AcctItemSetOffRule create(Integer seqNo) throws CreateException;

	public AcctItemSetOffRule create(AcctItemSetOffRuleDTO dto)
			throws CreateException;

	public AcctItemSetOffRule findByPrimaryKey(Integer seqNo)
			throws FinderException;
}