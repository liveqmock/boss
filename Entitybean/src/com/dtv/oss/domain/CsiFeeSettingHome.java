package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CsiFeeSettingDTO;

public interface CsiFeeSettingHome extends javax.ejb.EJBLocalHome {
	public CsiFeeSetting create(String csiType, int eventClass)
			throws CreateException;

	public CsiFeeSetting create(CsiFeeSettingDTO dto) throws CreateException;

	public CsiFeeSetting findByPrimaryKey(CsiFeeSettingPK pk)
			throws FinderException;
}