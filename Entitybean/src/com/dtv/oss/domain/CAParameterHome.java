package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CAParameterDTO;

public interface CAParameterHome extends javax.ejb.EJBLocalHome {
	public CAParameter create(String pmName) throws CreateException;

	public CAParameter create(CAParameterDTO dto) throws CreateException;

	public CAParameter findByPrimaryKey(String pmName) throws FinderException;
}