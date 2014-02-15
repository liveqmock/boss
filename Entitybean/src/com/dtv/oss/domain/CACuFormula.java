package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CACuFormulaDTO;

public interface CACuFormula extends javax.ejb.EJBLocalObject {
	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getFormulaID();

	public void setFormulaString(String formulaString);

	public String getFormulaString();

	public int ejbUpdate(CACuFormulaDTO dto);
}