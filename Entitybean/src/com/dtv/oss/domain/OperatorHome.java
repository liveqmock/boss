package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.OperatorDTO;

public interface OperatorHome extends javax.ejb.EJBLocalHome {
	public Operator create(Integer operatorID) throws CreateException;

	public Operator create(OperatorDTO dto) throws CreateException;
        public Operator findByLoginID(String loginID) throws FinderException;
	 

	public Operator findByPrimaryKey(Integer operatorID) throws FinderException;

}