package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.EJBLocalHome;
 
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CampaignAgmtPackageDTO;

public interface CampaignAgmtPackageHome extends EJBLocalHome {
    public CampaignAgmtPackage create(int campaignID, int packageID) throws
            CreateException;

    public CampaignAgmtPackage create(CampaignAgmtPackageDTO dto) throws CreateException;

    public CampaignAgmtPackage findByPrimaryKey(CampaignAgmtPackagePK pk) throws
            FinderException;
    
    public Collection findByCampaignID(int campaignID) throws
            FinderException;
}
