package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CsiProcessLogDTO;

public interface CsiProcessLogHome extends javax.ejb.EJBLocalHome {
	public CsiProcessLog create(Integer id) throws CreateException;

	public CsiProcessLog create(CsiProcessLogDTO dto) throws CreateException;

	public CsiProcessLog findByPrimaryKey(Integer id) throws FinderException;

}