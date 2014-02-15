package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CampaignCondPackageDTO;

public interface CampaignCondPackage extends EJBLocalObject {

    public Integer getSeqNo();

    public void setCampaignID(int campaignID);

    public int getCampaignID();

    public void setPackageIdList(String packageIdList);

    public String getPackageIdList();

    public void setHasAllFlag(String hasAllFlag);

    public String getHasAllFlag();

    public void setPackageNum(int packageNum);

    public int getPackageNum();

    public void setNewPurchaseFlag(String newPurchaseFlag);

    public String getNewPurchaseFlag();

    public void setExistingFlag(String existingFlag);

    public String getExistingFlag();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    public int ejbUpdate(CampaignCondPackageDTO dto);
}
