package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DepotDTO;

public interface DepotHome extends javax.ejb.EJBLocalHome {
	public Depot create(Integer depotID) throws CreateException;

	public Depot create(DepotDTO dto) throws CreateException;

	public Depot findByPrimaryKey(Integer depotID) throws FinderException;

}