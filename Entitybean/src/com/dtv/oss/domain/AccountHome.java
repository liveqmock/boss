package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.AccountDTO;

public interface AccountHome extends javax.ejb.EJBLocalHome {
	
  public Account findByPrimaryKey(Integer accountID) throws FinderException;
  
  public Account create(Integer accountID) throws CreateException;

  public Account create(AccountDTO dto)
	throws CreateException;
  
  public Collection findByCustomerId(int customerId)
    throws FinderException;

		 

}