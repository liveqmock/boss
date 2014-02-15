package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CACommandDTO;

public interface CACommandHome extends javax.ejb.EJBLocalHome {
	public CACommand create(int commandID) throws CreateException;

	public CACommand create(CACommandDTO dto) throws CreateException;

	public Collection findByCommandName(String name) throws FinderException; 

	public CACommand findByPrimaryKey(CACommandPK pk) throws FinderException;
}