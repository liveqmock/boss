package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CampaignCondProductDTO;

public interface CampaignCondProductHome extends EJBLocalHome {
    public CampaignCondProduct create(Integer seqNo) throws CreateException;

    public CampaignCondProduct create(CampaignCondProductDTO dto) throws CreateException;

    public CampaignCondProduct findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
