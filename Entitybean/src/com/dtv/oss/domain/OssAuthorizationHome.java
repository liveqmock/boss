package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.OssAuthorizationDTO;

public interface OssAuthorizationHome extends javax.ejb.EJBLocalHome {
	public OssAuthorization create(int deviceID,int productID) throws CreateException;

	public OssAuthorization create(OssAuthorizationDTO dto) throws CreateException;
	
	public OssAuthorization findByPrimaryKey(OssAuthorizationPK pk) throws FinderException;
	
	public Collection findByDeviceID(int deviceID)    throws FinderException;
	
	public Collection findByDeviceIDAndProductID(int deviceID,int productID)  throws FinderException;

}