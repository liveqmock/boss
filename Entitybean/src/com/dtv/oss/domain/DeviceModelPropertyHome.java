package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DeviceModelPropertyDTO;

public interface DeviceModelPropertyHome extends javax.ejb.EJBLocalHome {
	public DeviceModelProperty create(String deviceModel)
			throws CreateException;

	public DeviceModelProperty create(DeviceModelPropertyDTO dto)
			throws CreateException;

	public DeviceModelProperty findByPrimaryKey(String deviceModel)
			throws FinderException;
}