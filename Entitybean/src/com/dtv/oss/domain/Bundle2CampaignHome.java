package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.Bundle2CampaignDTO;

public interface Bundle2CampaignHome extends EJBLocalHome {
    public Bundle2Campaign create(java.lang.Integer packageID) throws
            CreateException;

    public Bundle2Campaign create(Bundle2CampaignDTO dto) throws CreateException;

    public Bundle2Campaign findByPrimaryKey(java.lang.Integer packageID) throws
            FinderException;
    public Collection findByCampaignID(int campaignId) throws
     FinderException;
    
    
}
