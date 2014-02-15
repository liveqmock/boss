package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.PackageToMarketSegmentDTO;

public interface PackageToMarketSegmentHome extends javax.ejb.EJBLocalHome {
	public PackageToMarketSegment create(int packageId, int marketSegmentId)
			throws CreateException;

	public PackageToMarketSegment create(PackageToMarketSegmentDTO dto)
			throws CreateException;

	public PackageToMarketSegment findByPrimaryKey(PackageToMarketSegmentPK pk)
			throws FinderException;
	
	public Collection findByPackageID(int packageID)
	
	         throws FinderException;
}