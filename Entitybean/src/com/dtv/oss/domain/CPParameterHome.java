package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CPParameterDTO;

public interface CPParameterHome extends javax.ejb.EJBLocalHome {
	public CPParameter create(Integer psID, String paramName)
			throws CreateException;

	public CPParameter create(CPParameterDTO dto) throws CreateException;

	public CPParameter findByPrimaryKey(CPParameterPK pk)
			throws FinderException;

}