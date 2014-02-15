package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BillingCycleDTO;

public interface BillingCycleHome extends javax.ejb.EJBLocalHome {
	public BillingCycle create(Integer id) throws CreateException;

	public BillingCycle create(BillingCycleDTO dto) throws CreateException;

	public BillingCycle findByPrimaryKey(Integer id) throws FinderException;
	
	public Collection findAllByCtype(java.lang.String cType) throws FinderException;

}