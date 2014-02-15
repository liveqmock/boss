package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DeviceClassDTO;

public interface DeviceClassHome extends javax.ejb.EJBLocalHome {
	public DeviceClass create(String deviceClass) throws CreateException;

	public DeviceClass create(DeviceClassDTO dto) throws CreateException;

	public DeviceClass findByPrimaryKey(String deviceClass)
			throws FinderException;
}