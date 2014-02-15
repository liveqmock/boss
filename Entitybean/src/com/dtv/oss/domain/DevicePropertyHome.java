package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DevicePropertyDTO;

public interface DevicePropertyHome extends javax.ejb.EJBLocalHome {
	public DeviceProperty create(int deviceId,String propertyName) throws CreateException;

	public DeviceProperty create(DevicePropertyDTO dto) throws CreateException;

	public DeviceProperty findByPrimaryKey(DevicePropertyPK pk)
			throws FinderException;
}