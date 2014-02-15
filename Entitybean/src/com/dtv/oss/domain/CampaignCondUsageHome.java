package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CampaignCondUsageDTO;

public interface CampaignCondUsageHome extends EJBLocalHome {
    public CampaignCondUsage create(int campaignID, String usageType) throws
            CreateException;

    public CampaignCondUsage create(CampaignCondUsageDTO dto) throws CreateException;

    public CampaignCondUsage findByPrimaryKey(CampaignCondUsagePK pk) throws
            FinderException;
}
