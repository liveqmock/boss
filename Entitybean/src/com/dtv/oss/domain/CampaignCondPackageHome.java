package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
 
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CampaignCondPackageDTO;

public interface CampaignCondPackageHome extends EJBLocalHome {
    public CampaignCondPackage create(Integer seqNo) throws CreateException;

    public CampaignCondPackage create(CampaignCondPackageDTO dto) throws CreateException;

    public CampaignCondPackage findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
