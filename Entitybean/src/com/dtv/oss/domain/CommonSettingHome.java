package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CommonSettingDTO;

public interface CommonSettingHome extends javax.ejb.EJBLocalHome {
	public CommonSetting create(String name) throws CreateException;

	public CommonSetting create(CommonSettingDTO dto) throws CreateException;

	public CommonSetting findByPrimaryKey(String name) throws FinderException;
}