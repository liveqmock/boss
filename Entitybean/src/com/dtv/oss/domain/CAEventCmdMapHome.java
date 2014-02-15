package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CAEventCmdMapDTO;

public interface CAEventCmdMapHome extends javax.ejb.EJBLocalHome {
	public CAEventCmdMap create(Integer mapID) throws CreateException;

	public CAEventCmdMap create(CAEventCmdMapDTO dto) throws CreateException;

	public CAEventCmdMap findByPrimaryKey(Integer mapID) throws FinderException;
}