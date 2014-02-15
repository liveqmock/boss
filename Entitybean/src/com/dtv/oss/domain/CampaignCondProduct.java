package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CampaignCondProductDTO;

public interface CampaignCondProduct extends EJBLocalObject {

    public Integer getSeqNo();

    public void setCampaignID(int campaignID);

    public int getCampaignID();

    public void setProductIdList(String productIdList);

    public String getProductIdList();

    public void setHasAllFlag(String hasAllFlag);

    public String getHasAllFlag();

    public void setProductNum(int productNum);

    public int getProductNum();

    public void setNewCaptureFlag(String newCaptureFlag);

    public String getNewCaptureFlag();

    public void setExistingFlag(String existingFlag);

    public String getExistingFlag();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    public int ejbUpdate(CampaignCondProductDTO dto);
}
