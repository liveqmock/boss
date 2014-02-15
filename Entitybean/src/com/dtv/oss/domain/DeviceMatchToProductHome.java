package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DeviceMatchToProductDTO;

public interface DeviceMatchToProductHome extends javax.ejb.EJBLocalHome {
	public DeviceMatchToProduct create(int productId, java.lang.String devicemodel)
			throws CreateException;

	public DeviceMatchToProduct create(DeviceMatchToProductDTO dto)
			throws CreateException;

	public DeviceMatchToProduct findByPrimaryKey(DeviceMatchToProductPK pk)
			throws FinderException;
	public Collection findByProductId(int id)
			throws FinderException;		
			
}