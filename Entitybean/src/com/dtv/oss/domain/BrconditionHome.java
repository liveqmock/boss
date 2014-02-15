package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BrconditionDTO;

public interface BrconditionHome extends javax.ejb.EJBLocalHome {
	public Brcondition create(Integer brcntID) throws CreateException;

	public Brcondition create(BrconditionDTO dto) throws CreateException;

	public Brcondition findByPrimaryKey(Integer brcntID) throws FinderException;

}