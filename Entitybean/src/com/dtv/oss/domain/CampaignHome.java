package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CampaignDTO;

public interface CampaignHome extends javax.ejb.EJBLocalHome {
	public Campaign create(Integer campaignID) throws CreateException;

	public Campaign create(CampaignDTO dto) throws CreateException;

	public Campaign findByPrimaryKey(Integer campaignID) throws FinderException;

}