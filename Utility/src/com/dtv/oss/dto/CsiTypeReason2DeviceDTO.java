package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CsiTypeReason2DeviceDTO implements ReflectionSupport, Serializable {
    private int seqNo;
    private String csiType;
    private String csiCreateReason;
    private int referPackageId; 
    private int referProductId;
    private String referDeviceModel;
    private String referPurpose;
    private String comments;
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

    public String getCsiCreateReason() {
        return csiCreateReason;
    }

    public void setCsiCreateReason(String csiCreateReason) {
        this.csiCreateReason = csiCreateReason;
        put("csiCreateReason");
    }

    public int getReferPackageId() {
        return referPackageId;
    }

    public void setReferPackageId(int referPackageId) {
        this.referPackageId = referPackageId;
        put("referPackageId");
    }

    public int getReferProductId() {
        return referProductId;
       
    }

    public void setReferProductId(int referProductId) {
        this.referProductId = referProductId;
    }

    public String getReferDeviceModel() {
        return referDeviceModel;
    }

    public void setReferDeviceModel(String referDeviceModel) {
        this.referDeviceModel = referDeviceModel;
        put("referDeviceModel");
    }

    public String getReferPurpose() {
        return referPurpose;
    }

    public void setReferPurpose(String referPurpose) {
        this.referPurpose = referPurpose;
        put("referPurpose");
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
        if (!(obj instanceof CsiTypeReason2DeviceDTO))
            return false;
        CsiTypeReason2DeviceDTO that = (CsiTypeReason2DeviceDTO) obj;
        if (that.seqNo != this.seqNo)
        
            return false;
        if (!(that.csiType == null ? this.csiType == null :
              that.csiType.equals(this.csiType)))
            return false;
        if (!(that.csiCreateReason == null ? this.csiCreateReason == null :
              that.csiCreateReason.equals(this.csiCreateReason)))
            return false;
        if (that.referPackageId != this.referPackageId)
            return false;
        if (that.referProductId != this.referProductId)
            return false;
        if (!(that.referDeviceModel == null ? this.referDeviceModel == null :
              that.referDeviceModel.equals(this.referDeviceModel)))
            return false;
        if (!(that.referPurpose == null ? this.referPurpose == null :
              that.referPurpose.equals(this.referPurpose)))
            return false;
        if (!(that.status == null ? this.status == null :
            that.status.equals(this.status)))
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
        
        result = 37 * result + (int)this.seqNo;
        result = 37 * result + this.csiType.hashCode();
        result = 37 * result + this.csiCreateReason.hashCode();
        result = 37 * result + (int)this.referPackageId;
        result = 37 * result + (int)this.referProductId;
        result = 37 * result + this.referDeviceModel.hashCode();
        result = 37 * result + this.referPurpose.hashCode();
        result = 37 * result + this.comments.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(320);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("csiType:").append(csiType);
        returnStringBuffer.append("csiCreateReason:").append(csiCreateReason);
        returnStringBuffer.append("referPackageId:").append(referPackageId);
        returnStringBuffer.append("referProductId:").append(referProductId);
        returnStringBuffer.append("referDeviceModel:").append(referDeviceModel);
        returnStringBuffer.append("referPurpose:").append(referPurpose);
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
