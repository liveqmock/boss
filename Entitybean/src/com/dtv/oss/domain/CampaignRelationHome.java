package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CampaignRelationDTO;

public interface CampaignRelationHome extends EJBLocalHome {
    public CampaignRelation create(Integer seqNo) throws CreateException;

   

    public CampaignRelation create(CampaignRelationDTO dto) throws CreateException;

    public CampaignRelation findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
