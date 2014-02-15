package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.MarketSegmentDTO;

public interface MarketSegmentHome extends javax.ejb.EJBLocalHome {
	public MarketSegment create(Integer id) throws CreateException;

	public MarketSegment create(MarketSegmentDTO dto) throws CreateException;

	public MarketSegment findByPrimaryKey(Integer id) throws FinderException;
}