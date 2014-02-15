package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.MarketSegmentToDistrictDTO;

public interface MarketSegmentToDistrictHome extends javax.ejb.EJBLocalHome {
	public MarketSegmentToDistrict create(int marketSegmentId, int districtId)
			throws CreateException;

	public MarketSegmentToDistrict create(MarketSegmentToDistrictDTO dto)
			throws CreateException;

	public MarketSegmentToDistrict findByPrimaryKey(MarketSegmentToDistrictPK pk)
			throws FinderException;
	public Collection  findByMarketSegmentID(int marketSegmentID)
	       throws FinderException;
}