package com.dtv.oss.dto;

import java.sql.Timestamp;

public class ManualTransferSettingDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private String sheetType;
    private int fromOrgId;
    private int toOrgId;
    private int priority;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    private  String orgSubRole;
    
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }
    public String getOrgSubRole() {
        return orgSubRole;
    }

    public void setOrgSubRole(String orgSubRole) {
        this.orgSubRole = orgSubRole;
        put("orgSubRole");
    }

    public String getSheetType() {
        return sheetType;
    }

    public void setSheetType(String sheetType) {
        this.sheetType = sheetType;
        put("sheetType");
    }

    public int getFromOrgId() {
        return fromOrgId;
    }

    public void setFromOrgId(int fromOrgId) {
        this.fromOrgId = fromOrgId;
        put("fromOrgId");
    }

    public int getToOrgId() {
        return toOrgId;
    }

    public void setToOrgId(int toOrgId) {
        this.toOrgId = toOrgId;
        put("toOrgId");
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
        put("priority");
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
        if (!(obj instanceof ManualTransferSettingDTO))
            return false;
        ManualTransferSettingDTO that = (ManualTransferSettingDTO) obj;
        
        if (that.sheetType != this.sheetType)
            return false;
        if (!(that.sheetType == null ? this.sheetType == null :
              that.sheetType.equals(this.sheetType)))
            return false;
        if (that.fromOrgId != this.fromOrgId)
            return false;
        if (that.toOrgId != this.toOrgId)
            return false;
        if (!(that.orgSubRole == null ? this.orgSubRole == null :
            that.orgSubRole.equals(this.orgSubRole)))
          return false;
       
        if (that.priority != this.priority)
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
        result = 37 * result + this.sheetType.hashCode();
        result = 37 * result + (int)this.fromOrgId;
        result = 37 * result +  this.orgSubRole.hashCode();
        result = 37 * result + (int)this.toOrgId;
        result = 37 * result + (int)this.priority;
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("sheetType:").append(sheetType);
        returnStringBuffer.append("fromOrgId:").append(fromOrgId);
        returnStringBuffer.append("toOrgId:").append(toOrgId);
        returnStringBuffer.append("priority:").append(priority);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}
