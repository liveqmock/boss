package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BillingCycleTypeDTO;

public interface BillingCycleTypeHome extends javax.ejb.EJBLocalHome {
	public BillingCycleType create(Integer id) throws CreateException;

	public BillingCycleType create(BillingCycleTypeDTO dto) throws CreateException;
  public BillingCycleType findByPrimaryKey(Integer id) throws FinderException;

}