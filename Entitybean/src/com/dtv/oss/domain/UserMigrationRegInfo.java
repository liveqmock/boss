package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.UserMigrationRegInfoDTO;

public interface UserMigrationRegInfo extends EJBLocalObject {
    public void setComments(String comments);

    public String getComments();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    public java.lang.String getCatvID();

    public void setReferSheetNo(String referSheetNo);

    public String getReferSheetNo();

    public void setUserIntention(String userIntention);

    public String getUserIntention();

    public void setUserFeedBack(String userFeedBack);

    public String getUserFeedBack();

    public void setUserFeedBackDetail(String userFeedBackDetail);

    public String getUserFeedBackDetail();

    public void setBuyPayTvFlag(String buyPayTvFlag);

    public String getBuyPayTvFlag();
    public int ejbUpdate(UserMigrationRegInfoDTO dto);
}
