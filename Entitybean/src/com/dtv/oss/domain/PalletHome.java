package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.PalletDTO;

public interface PalletHome extends javax.ejb.EJBLocalHome {
	public Pallet create(Integer palletID) throws CreateException;

	public Pallet create(PalletDTO dto) throws CreateException;

	public Pallet findByPrimaryKey(Integer palletID) throws FinderException;

}