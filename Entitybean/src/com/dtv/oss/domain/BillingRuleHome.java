package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BillingRuleDTO;

public interface BillingRuleHome extends javax.ejb.EJBLocalHome {
	public BillingRule create(Integer id) throws CreateException;

	public BillingRule findByPrimaryKey(Integer id) throws FinderException;

	public BillingRule create(BillingRuleDTO dto) throws CreateException;
}