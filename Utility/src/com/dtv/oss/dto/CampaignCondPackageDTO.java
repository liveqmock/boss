package com.dtv.oss.dto;

import java.sql.Timestamp;

public class CampaignCondPackageDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private int campaignID;
    private String packageIdList;
    private String hasAllFlag;
    private int packageNum;
    private String newPurchaseFlag;
    private String existingFlag;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    

	/**
	 * @return Returns the campaignID.
	 */
	public int getCampaignID() {
		return campaignID;
	}
	/**
	 * @param campaignID The campaignID to set.
	 */
	public void setCampaignID(int campaignID) {
		this.campaignID = campaignID;
		 put("campaignID");
	}
	/**
	 * @return Returns the dtCreate.
	 */
	public Timestamp getDtCreate() {
		return dtCreate;
	}
	/**
	 * @param dtCreate The dtCreate to set.
	 */
	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
	}
	/**
	 * @return Returns the dtLastmod.
	 */
	public Timestamp getDtLastmod() {
		return dtLastmod;
	}
	/**
	 * @param dtLastmod The dtLastmod to set.
	 */
	public void setDtLastmod(Timestamp dtLastmod) {
		this.dtLastmod = dtLastmod;
	}
	/**
	 * @return Returns the existingFlag.
	 */
	public String getExistingFlag() {
		return existingFlag;
	}
	/**
	 * @param existingFlag The existingFlag to set.
	 */
	public void setExistingFlag(String existingFlag) {
		this.existingFlag = existingFlag;
		 put("existingFlag");
	}
	/**
	 * @return Returns the hasAllFlag.
	 */
	public String getHasAllFlag() {
		return hasAllFlag;
	}
	/**
	 * @param hasAllFlag The hasAllFlag to set.
	 */
	public void setHasAllFlag(String hasAllFlag) {
		this.hasAllFlag = hasAllFlag;
		 put("hasAllFlag");
	}
	/**
	 * @return Returns the newPurchaseFlag.
	 */
	public String getNewPurchaseFlag() {
		return newPurchaseFlag;
	}
	/**
	 * @param newPurchaseFlag The newPurchaseFlag to set.
	 */
	public void setNewPurchaseFlag(String newPurchaseFlag) {
		this.newPurchaseFlag = newPurchaseFlag;
		 put("newPurchaseFlag");
	}
	/**
	 * @return Returns the packageIdList.
	 */
	public String getPackageIdList() {
		return packageIdList;
	}
	/**
	 * @param packageIdList The packageIdList to set.
	 */
	public void setPackageIdList(String packageIdList) {
		this.packageIdList = packageIdList;
		 put("packageIdList");
	}
	/**
	 * @return Returns the packageNum.
	 */
	public int getPackageNum() {
		return packageNum;
	}
	/**
	 * @param packageNum The packageNum to set.
	 */
	public void setPackageNum(int packageNum) {
		this.packageNum = packageNum;
		put("packageNum");
	}
	/**
	 * @return Returns the seqNo.
	 */
	public int getSeqNo() {
		return seqNo;
	}
	/**
	 * @param seqNo The seqNo to set.
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CampaignCondPackageDTO))
            return false;
        CampaignCondPackageDTO that = (CampaignCondPackageDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (that.campaignID != this.campaignID)
            return false;
        if (!(that.packageIdList == null ? this.packageIdList == null :
              that.packageIdList.equals(this.packageIdList)))
            return false;
        if (!(that.hasAllFlag == null ? this.hasAllFlag == null :
              that.hasAllFlag.equals(this.hasAllFlag)))
            return false;
        if (that.packageNum != this.packageNum)
             return false;
         
        
        if (!(that.newPurchaseFlag == null ? this.newPurchaseFlag == null :
              that.newPurchaseFlag.equals(this.newPurchaseFlag)))
            return false;
        if (!(that.existingFlag == null ? this.existingFlag == null :
              that.existingFlag.equals(this.existingFlag)))
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
        result = 37 * result + (int)this.campaignID;
        result = 37 * result + this.packageIdList.hashCode();
        result = 37 * result + this.hasAllFlag.hashCode();
        result = 37 * result + (int)this.packageNum;
        result = 37 * result + this.newPurchaseFlag.hashCode();
        result = 37 * result + this.existingFlag.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(288);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqno:").append(seqNo);
        returnStringBuffer.append("campaignid:").append(campaignID);
        returnStringBuffer.append("packageidlist:").append(packageIdList);
        returnStringBuffer.append("hasallflag:").append(hasAllFlag);
        returnStringBuffer.append("packagenum:").append(packageNum);
        returnStringBuffer.append("newpurchaseflag:").append(newPurchaseFlag);
        returnStringBuffer.append("existingflag:").append(existingFlag);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }
}

