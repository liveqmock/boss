package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustomerProductDTO;

public interface CustomerProductHome extends javax.ejb.EJBLocalHome {
	public CustomerProduct create(Integer psID) throws CreateException;

	public CustomerProduct create(CustomerProductDTO dto)
			throws CreateException;
 
	public CustomerProduct findByPrimaryKey(Integer psID)
			throws FinderException;
			
	public Collection findByCustomerID(int customerID) throws FinderException;
	
        public Collection findByServiceAccountID(int serviceAccountID) throws FinderException;
        
        public Collection findByServiceAccountIDAndStatus(int serviceAccountID,String status)throws FinderException;
        
        public Collection findByAccountID(int accountID) throws FinderException;
        	
        public Collection findByCustomerIDAndStatus(int customerID,String status) throws FinderException;	

}