package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DeviceModelDTO;

public interface DeviceModelHome extends javax.ejb.EJBLocalHome {
	public DeviceModel create(String deviceModel) throws CreateException;

	public DeviceModel create(DeviceModelDTO dto) throws CreateException;

	public DeviceModel findByPrimaryKey(String deviceModel)
			throws FinderException;

}