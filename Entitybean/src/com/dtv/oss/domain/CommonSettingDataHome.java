package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CommonSettingDataDTO;

public interface CommonSettingDataHome extends javax.ejb.EJBLocalHome {
	public CommonSettingData create(String name, String key)
			throws CreateException;

	public CommonSettingData create(CommonSettingDataDTO dto)
			throws CreateException;

	 

	public CommonSettingData findByPrimaryKey(CommonSettingDataPK pk)
			throws FinderException;
}