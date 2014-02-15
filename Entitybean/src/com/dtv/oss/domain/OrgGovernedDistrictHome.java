package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.OrgGovernedDistrictDTO;

public interface OrgGovernedDistrictHome extends javax.ejb.EJBLocalHome {
	public OrgGovernedDistrict create(int orgId, int districtId)
			throws CreateException;

	public OrgGovernedDistrict create(OrgGovernedDistrictDTO dto)
			throws CreateException;

	public OrgGovernedDistrict findByPrimaryKey(OrgGovernedDistrictPK pk)
			throws FinderException;
}