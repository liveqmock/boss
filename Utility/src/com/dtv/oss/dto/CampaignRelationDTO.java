package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CampaignRelationDTO  implements ReflectionSupport, java.io.Serializable {  
    private int seqNo;
    private String relationtype;
    private int campaignFromID;
    private int campaignToID;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getRelationtype() {
        return relationtype;
    }

    public void setRelationtype(String relationtype) {
        this.relationtype = relationtype;
        put("relationtype");
    }

    public int getCampaignFromID() {
        return campaignFromID;
    }

    public void setCampaignFromID(int campaignFromID) {
        this.campaignFromID = campaignFromID;
        put("campaignFromID");
    }

    public int getCampaignToID() {
        return campaignToID;
    }

    public void setCampaignToID(int campaignToID) {
        this.campaignToID = campaignToID;
        put("campaignToID");
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
        if (!(obj instanceof CampaignRelationDTO))
            return false;
        CampaignRelationDTO that = (CampaignRelationDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (!(that.relationtype == null ? this.relationtype == null :
              that.relationtype.equals(this.relationtype)))
            return false;
        if (that.campaignFromID != this.campaignFromID)
            return false;
        if (that.campaignToID != this.campaignToID)
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
        result = 37 * result + (int)this.seqNo;
        result = 37 * result + this.relationtype.hashCode();
        result = 37 * result + (int)this.campaignFromID;
        result = 37 * result + (int)this.campaignToID;
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(192);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("relationtype:").append(relationtype);
        returnStringBuffer.append("campaignFromID:").append(campaignFromID);
        returnStringBuffer.append("campaignToID:").append(campaignToID);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) {
      map.put(field, Boolean.TRUE);
    }

    public java.util.Map getMap() {
      return this.map;
    }
}
