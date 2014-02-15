package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.NewCustAccountInfoDTO;

public interface NewCustAccountInfoHome extends javax.ejb.EJBLocalHome {
	public NewCustAccountInfo create(Integer id) throws CreateException;

	public NewCustAccountInfo create(NewCustAccountInfoDTO dto)
			throws CreateException;

	 

	public NewCustAccountInfo findByPrimaryKey(Integer id)
			throws FinderException;

}