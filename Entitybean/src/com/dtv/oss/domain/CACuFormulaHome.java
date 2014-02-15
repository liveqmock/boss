package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CACuFormulaDTO;

public interface CACuFormulaHome extends javax.ejb.EJBLocalHome {
	public CACuFormula create(Integer formulaID) throws CreateException;

	public CACuFormula create(CACuFormulaDTO dto) throws CreateException;

	public CACuFormula findByPrimaryKey(Integer formulaID)
			throws FinderException;
}