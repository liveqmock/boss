package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CampaignAgmtCampaignDTO;

public interface CampaignAgmtCampaignHome extends EJBLocalHome {
    public CampaignAgmtCampaign create(int campaignId, int targetBundleId) throws
            CreateException;

    public CampaignAgmtCampaign create(CampaignAgmtCampaignDTO dto) throws CreateException;

    public CampaignAgmtCampaign findByPrimaryKey(CampaignAgmtCampaignPK pk) throws
            FinderException;
}
