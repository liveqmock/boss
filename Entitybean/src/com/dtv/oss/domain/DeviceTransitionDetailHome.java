package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DeviceTransitionDetailDTO;

public interface DeviceTransitionDetailHome extends javax.ejb.EJBLocalHome {
	public DeviceTransitionDetail create(Integer seqNo) throws CreateException;

	public DeviceTransitionDetail create(DeviceTransitionDetailDTO dto)
			throws CreateException;

	public DeviceTransitionDetail findByPrimaryKey(Integer seqNo)
			throws FinderException;

}