package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.AccountAdjustmentDTO;

public interface AccountAdjustmentHome extends javax.ejb.EJBLocalHome {
	public AccountAdjustment create(Integer seqNo) throws CreateException;

	public AccountAdjustment create(AccountAdjustmentDTO dto)
			throws CreateException;
			
       public Collection findByCustomerID(int customerID) throws FinderException;

       public Collection findByReferRecordID(int referRecordID)  throws FinderException;
	 
	   public AccountAdjustment findByPrimaryKey(Integer seqNo)
			throws FinderException;
			
}