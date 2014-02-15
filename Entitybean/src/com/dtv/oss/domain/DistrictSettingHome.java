package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DistrictSettingDTO;

public interface DistrictSettingHome extends javax.ejb.EJBLocalHome {
	public DistrictSetting create(Integer id) throws CreateException;

	public DistrictSetting create(DistrictSettingDTO dto)
			throws CreateException;

	public Collection findByBelongTo(int belongTo) throws FinderException; 

	public DistrictSetting findByPrimaryKey(Integer id) throws FinderException;
}