package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DeviceTransitionDTO;

public interface DeviceTransitionHome extends javax.ejb.EJBLocalHome {
	public DeviceTransition create(Integer batchID) throws CreateException;

	public DeviceTransition create(DeviceTransitionDTO dto)
			throws CreateException;

	public DeviceTransition findByPrimaryKey(Integer batchID)
			throws FinderException;

}