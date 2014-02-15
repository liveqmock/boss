package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.Bc2ICDTO;

public interface Bc2ICHome extends javax.ejb.EJBLocalHome {
	public Bc2IC create(Integer seqNo) throws CreateException;

	public Bc2IC create(Bc2ICDTO dto) throws CreateException;

	public Bc2IC findByPrimaryKey(Integer seqNo) throws FinderException;
}