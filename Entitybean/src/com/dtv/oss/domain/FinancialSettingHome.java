package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FinancialSettingDTO;

public interface FinancialSettingHome extends javax.ejb.EJBLocalHome {
	public FinancialSetting create(String name) throws CreateException;

	public FinancialSetting create(FinancialSettingDTO dto)
			throws CreateException;

	public FinancialSetting findByPrimaryKey(java.lang.String name)
			throws FinderException;
}