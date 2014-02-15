package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.EJBLocalHome;
 
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CampaignAgmtProductDTO;

public interface CampaignAgmtProductHome extends EJBLocalHome {
    public CampaignAgmtProduct create(int campaignID, int productID) throws
            CreateException;

    public CampaignAgmtProduct create(CampaignAgmtProductDTO dto) throws CreateException;

    public CampaignAgmtProduct findByPrimaryKey(CampaignAgmtProductPK pk) throws
            FinderException;
    
    public Collection findByCampaignID(int campaignID) throws
            FinderException;
}
