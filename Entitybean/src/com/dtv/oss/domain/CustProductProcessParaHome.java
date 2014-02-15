package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustProductProcessParaDTO;

public interface CustProductProcessParaHome extends javax.ejb.EJBLocalHome {
	public CustProductProcessPara create(Integer id) throws CreateException;

	public CustProductProcessPara create(CustProductProcessParaDTO dto)
			throws CreateException;

	public CustProductProcessPara findByPrimaryKey(Integer id)
			throws FinderException;
}