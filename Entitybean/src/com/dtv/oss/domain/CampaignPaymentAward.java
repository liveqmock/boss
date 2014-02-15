package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CampaignPaymentAwardDTO;

public interface CampaignPaymentAward extends EJBLocalObject {

    public Integer getSeqNo();

    public void setCampaignID(int campaignID);

    public int getCampaignID();

    public void setMopID(int mopID);

    public int getMopID();

    public void setIsPrepaymentFlag(String isPrepaymentFlag);

    public String getIsPrepaymentFlag();

    public void setValue(double value);

    public double getValue();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    public int ejbUpdate(CampaignPaymentAwardDTO dto);
}
