package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.dtv.oss.dto.AitSetOffRuleDTO;

public interface AitSetOffRuleHome extends javax.ejb.EJBLocalHome {
	public AitSetOffRule create(Integer seqNo) throws CreateException;

	public AitSetOffRule create(AitSetOffRuleDTO dto)
			throws CreateException;

	public AitSetOffRule findByPrimaryKey(Integer seqNo)
			throws FinderException;
}