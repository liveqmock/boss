package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CampaignToMarketSegmentDTO;

public interface CampaignToMarketSegmentHome extends javax.ejb.EJBLocalHome {
	public CampaignToMarketSegment create(int campaignId,int marketSegmentId)
			throws CreateException;

	public CampaignToMarketSegment create(CampaignToMarketSegmentDTO dto)
			throws CreateException;

	public CampaignToMarketSegment findByPrimaryKey(CampaignToMarketSegmentPK pk)
			throws FinderException;
	 public Collection findByCampainId(int campaignId) throws FinderException;
}