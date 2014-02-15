package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CampaignCondCampaignDTO;

public interface CampaignCondCampaignHome extends EJBLocalHome {
    public CampaignCondCampaign create(Integer seqNo) throws
            CreateException;

    public CampaignCondCampaign create(CampaignCondCampaignDTO dto) throws CreateException;

    public CampaignCondCampaign findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
