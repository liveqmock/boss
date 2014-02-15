package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CampaignAgmtCampaignDTO;

public interface CampaignAgmtCampaign extends EJBLocalObject {

    public int getCampaignId();

    public int getTargetBundleId();

    public void setTimeLengthUnitType(String timeLengthUnitType);

    public String getTimeLengthUnitType();

    public void setTimeLengthUnitNumber(int timeLengthUnitNumber);

    public int getTimeLengthUnitNumber();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    public int ejbUpdate(CampaignAgmtCampaignDTO dto);
    
}
