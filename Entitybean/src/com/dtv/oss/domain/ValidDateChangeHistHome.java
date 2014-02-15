package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ValidDateChangeHistDTO;

public interface ValidDateChangeHistHome extends javax.ejb.EJBLocalHome {
	public ValidDateChangeHist create(Integer sequenceNo)
			throws CreateException;

	public ValidDateChangeHist create(ValidDateChangeHistDTO dto)
			throws CreateException;

	public ValidDateChangeHist findByPrimaryKey(Integer sequenceNo)
			throws FinderException;

	 
}