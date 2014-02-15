package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustomerBillingRuleDTO;

public interface CustomerBillingRuleHome extends javax.ejb.EJBLocalHome {
	public CustomerBillingRule create(Integer id) throws CreateException;

	public CustomerBillingRule create(CustomerBillingRuleDTO dto)
			throws CreateException;
 

	public CustomerBillingRule findByPrimaryKey(Integer id)
			throws FinderException;

}