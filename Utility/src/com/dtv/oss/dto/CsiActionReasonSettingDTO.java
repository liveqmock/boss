package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CsiActionReasonSettingDTO  implements ReflectionSupport, Serializable {
    private int seqNo;
    private String csiType;
    private String action;
    private String displayName;
    private String canEmptyFlag;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    private String status;
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getCsiType() {
        return csiType;
    }

    public void setCsiType(String csiType) {
        this.csiType = csiType;
        put("csiType");
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
        put("action");
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        put("displayName");
    }

    public String getCanEmptyFlag() {
        return canEmptyFlag;
    }

    public void setCanEmptyFlag(String canEmptyFlag) {
        this.canEmptyFlag = canEmptyFlag;
        put("canEmptyFlag");
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
        if (!(obj instanceof CsiActionReasonSettingDTO))
            return false;
        CsiActionReasonSettingDTO that = (CsiActionReasonSettingDTO) obj;
        if (that.seqNo != this.seqNo)
            return false;
        if (!(that.csiType == null ? this.csiType == null :
              that.csiType.equals(this.csiType)))
            return false;
        if (!(that.action == null ? this.action == null :
              that.action.equals(this.action)))
            return false;
        if (!(that.displayName == null ? this.displayName == null :
              that.displayName.equals(this.displayName)))
            return false;
        if (!(that.canEmptyFlag == null ? this.canEmptyFlag == null :
              that.canEmptyFlag.equals(this.canEmptyFlag)))
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
        
        result = 37 * result + this.csiType.hashCode();
        result = 37 * result + this.action.hashCode();
        result = 37 * result + this.displayName.hashCode();
        result = 37 * result + this.canEmptyFlag.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("csiType:").append(csiType);
        returnStringBuffer.append("action:").append(action);
        returnStringBuffer.append("displayName:").append(displayName);
        returnStringBuffer.append("canEmptyFlag:").append(canEmptyFlag);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }
}
