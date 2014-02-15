package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.PackageLineDTO;

public interface PackageLineHome extends javax.ejb.EJBLocalHome {
	public PackageLine create(int packageId, int productId)
			throws CreateException;

	public PackageLine create(PackageLineDTO dto) throws CreateException;
	public Collection findProductIdByPackageId(int packageId) throws FinderException;
	public PackageLine findByPrimaryKey(PackageLinePK pk)
			throws FinderException;
}