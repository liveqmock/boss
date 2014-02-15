package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BillingRuleItemDTO;

public interface BillingRuleItemHome extends javax.ejb.EJBLocalHome {
	
	public BillingRuleItem create(Integer id) throws CreateException;

	public BillingRuleItem findByPrimaryKey(Integer id) throws FinderException;

	public BillingRuleItem create(BillingRuleItemDTO dto) throws CreateException;
	
	public Collection findByBrId(int brId) throws FinderException;
	
}