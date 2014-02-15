package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FinanceSetOffMapDTO;

public interface FinanceSetOffMapHome extends javax.ejb.EJBLocalHome {
	public FinanceSetOffMap create(Integer seqNo) throws CreateException;

	public FinanceSetOffMap create(FinanceSetOffMapDTO dto)
			throws CreateException;

	public FinanceSetOffMap findByPrimaryKey(Integer seqNo)
			throws FinderException;
	
	public Collection findByPlusReferId(int plusReferId)
	        throws FinderException;
}