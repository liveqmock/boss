package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CatvTerminalDTO;

public interface CatvTerminalHome extends javax.ejb.EJBLocalHome {
	public CatvTerminal create(java.lang.String id) throws CreateException;

	public CatvTerminal create(CatvTerminalDTO dto) throws CreateException;

	public CatvTerminal findByPrimaryKey(java.lang.String id) throws FinderException;

	 

}