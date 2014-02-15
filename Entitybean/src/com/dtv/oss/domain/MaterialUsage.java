package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.MaterialUsageDTO;

public interface MaterialUsage extends EJBLocalObject {
    public void setMemo(String memo);

    public String getMemo();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getSeqNo();

    public void setJobCardId(int jobCardId);

    public int getJobCardId();

    public void setInfoSettingId(int infoSettingId);

    public int getInfoSettingId();

    public void setInfoValueId(int infoValueId);

    public int getInfoValueId();
    public int ejbUpdate(MaterialUsageDTO dto);
}
