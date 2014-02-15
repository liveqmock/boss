package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserMigrationRegInfoDTO implements ReflectionSupport,Serializable {
    private String catvID;
    private String referSheetNo;
    private String userIntention;
    private String userFeedBack;
    private String userFeedBackDetail;
    private String buyPayTvFlag;
    private String comments;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public String getCatvID() {
        return catvID;
    }

    public void setCatvID(String catvID) {
        this.catvID = catvID;
      //  put("catvID");
    }

    public String getReferSheetNo() {
        return referSheetNo;
    }

    public void setReferSheetNo(String referSheetNo) {
        this.referSheetNo = referSheetNo;
        put("referSheetNo");
    }

    public String getUserIntention() {
        return userIntention;
    }

    public void setUserIntention(String userIntention) {
        this.userIntention = userIntention;
        put("userIntention");
    }

    public String getUserFeedBack() {
        return userFeedBack;
    }

    public void setUserFeedBack(String userFeedBack) {
        this.userFeedBack = userFeedBack;
        put("userFeedBack");
    }

    public String getUserFeedBackDetail() {
        return userFeedBackDetail;
    }

    public void setUserFeedBackDetail(String userFeedBackDetail) {
        this.userFeedBackDetail = userFeedBackDetail;
        put("userFeedBackDetail");
    }

    public String getBuyPayTvFlag() {
        return buyPayTvFlag;
    }

    public void setBuyPayTvFlag(String buyPayTvFlag) {
        this.buyPayTvFlag = buyPayTvFlag;
        put("buyPayTvFlag");
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
        put("comments");
    }

    public Timestamp getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Timestamp dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Timestamp getDtLastmod() {
        return dtLastmod;
    }

    public void setDtLastmod(Timestamp dtLastmod) {
        this.dtLastmod = dtLastmod;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof UserMigrationRegInfoDTO))
            return false;
        UserMigrationRegInfoDTO that = (UserMigrationRegInfoDTO) obj;
       
       if (!(that.catvID == null ? this.catvID == null :
                 that.catvID.equals(this.catvID))) 
            return false;
        if (!(that.referSheetNo == null ? this.referSheetNo == null :
              that.referSheetNo.equals(this.referSheetNo)))
            return false;
        if (!(that.userIntention == null ? this.userIntention == null :
              that.userIntention.equals(this.userIntention)))
            return false;
        if (!(that.userFeedBack == null ? this.userFeedBack == null :
              that.userFeedBack.equals(this.userFeedBack)))
            return false;
        if (!(that.userFeedBackDetail == null ? this.userFeedBackDetail == null :
              that.userFeedBackDetail.equals(this.userFeedBackDetail)))
            return false;
        if (!(that.buyPayTvFlag == null ? this.buyPayTvFlag == null :
              that.buyPayTvFlag.equals(this.buyPayTvFlag)))
            return false;
        if (!(that.comments == null ? this.comments == null :
              that.comments.equals(this.comments)))
            return false;
        if (!(that.dtCreate == null ? this.dtCreate == null :
              that.dtCreate.equals(this.dtCreate)))
            return false;
        if (!(that.dtLastmod == null ? this.dtLastmod == null :
              that.dtLastmod.equals(this.dtLastmod)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.catvID.hashCode();
        result = 37 * result + this.referSheetNo.hashCode();
        result = 37 * result + this.userIntention.hashCode();
        result = 37 * result + this.userFeedBack.hashCode();
        result = 37 * result + this.userFeedBackDetail.hashCode();
        result = 37 * result + this.buyPayTvFlag.hashCode();
        result = 37 * result + this.comments.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(288);
        returnStringBuffer.append("[");
        returnStringBuffer.append("catvID:").append(catvID);
        returnStringBuffer.append("referSheetNo:").append(referSheetNo);
        returnStringBuffer.append("userIntention:").append(userIntention);
        returnStringBuffer.append("userFeedBack:").append(userFeedBack);
        returnStringBuffer.append("userFeedBackDetail:").append(
                userFeedBackDetail);
        returnStringBuffer.append("buyPayTvFlag:").append(buyPayTvFlag);
        returnStringBuffer.append("comments:").append(comments);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }
}
