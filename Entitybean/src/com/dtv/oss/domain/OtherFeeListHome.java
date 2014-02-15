package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.OtherFeeListDTO;

public interface OtherFeeListHome extends javax.ejb.EJBLocalHome {
	public OtherFeeList create(Integer id) throws CreateException;

	public OtherFeeList create(OtherFeeListDTO dto) throws CreateException;

	public OtherFeeList findByPrimaryKey(Integer id) throws FinderException;
}