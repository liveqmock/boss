package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CampaignRelationDTO;

public interface CampaignRelation extends EJBLocalObject {
    public void setRelationtype(String relationtype);

    public String getRelationtype();

    public void setCampaignFromID(int campaignFromID);

    public int getCampaignFromID();

    public void setCampaignToID(int campaignToID);

    public int getCampaignToID();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    public Integer getSeqNo();
    public int ejbUpdate(CampaignRelationDTO dto);
}
