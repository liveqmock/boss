package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CampaignCondCampaignDTO;
 
import java.sql.Timestamp;

public interface CampaignCondCampaign extends EJBLocalObject {

    public Integer getSeqNo();

    public void setCampaignID(int campaignID);

    public int getCampaignID();

    public void setCampaignIDList(String campaignIDList);

    public String getCampaignIDList();

    public void setHasAllFlag(String hasAllFlag);

    public String getHasAllFlag();

    public void setCampaignNum(int campaignNum);

    public int getCampaignNum();

    public void setNewCaptureFlag(String newCaptureFlag);

    public String getNewCaptureFlag();

    public void setExistingFlag(String existingFlag);

    public String getExistingFlag();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    public int ejbUpdate(CampaignCondCampaignDTO dto);
}
