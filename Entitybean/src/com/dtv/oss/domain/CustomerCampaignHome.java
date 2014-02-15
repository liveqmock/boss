package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CustomerCampaignDTO;

public interface CustomerCampaignHome extends javax.ejb.EJBLocalHome {
	public CustomerCampaign create(Integer ccID) throws CreateException;

	public CustomerCampaign create(CustomerCampaignDTO dto)
			throws CreateException;

	public CustomerCampaign findByPrimaryKey(Integer ccID)
			throws FinderException;
	
	public Collection findBySAID(int id) throws FinderException;
	
	public Collection findByCsiIDAndCampaignID(int csiID,int campaignID) throws FinderException;

    public Collection findByCsiID(int csiID) throws FinderException;
}