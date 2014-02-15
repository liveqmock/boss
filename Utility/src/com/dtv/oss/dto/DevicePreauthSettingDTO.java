package com.dtv.oss.dto;

 
import java.sql.Timestamp;

public class DevicePreauthSettingDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private String deviceStatusStr;
    private String deviceModel;
    private String operationTypeStr;
    private String preauthProductStr;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getDeviceStatusStr() {
        return deviceStatusStr;
    }

    public void setDeviceStatusStr(String deviceStatusStr) {
        this.deviceStatusStr = deviceStatusStr;
        put("deviceStatusStr");
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
        put("deviceModel");
    }

    public String getOperationTypeStr() {
        return operationTypeStr;
    }

    public void setOperationTypeStr(String operationTypeStr) {
        this.operationTypeStr = operationTypeStr;
        put("operationTypeStr");
    }

    public String getPreauthProductStr() {
        return preauthProductStr;
    }

    public void setPreauthProductStr(String preauthProductStr) {
        this.preauthProductStr = preauthProductStr;
        put("preauthProductStr");
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
        if (!(obj instanceof DevicePreauthSettingDTO))
            return false;
        DevicePreauthSettingDTO that = (DevicePreauthSettingDTO) obj;
        if (that.seqNo != this.seqNo)
        
            return false;
        if (!(that.deviceStatusStr == null ? this.deviceStatusStr == null :
              that.deviceStatusStr.equals(this.deviceStatusStr)))
            return false;
        if (!(that.deviceModel == null ? this.deviceModel == null :
              that.deviceModel.equals(this.deviceModel)))
            return false;
        if (!(that.operationTypeStr == null ? this.operationTypeStr == null :
              that.operationTypeStr.equals(this.operationTypeStr)))
            return false;
        if (!(that.preauthProductStr == null ? this.preauthProductStr == null :
              that.preauthProductStr.equals(this.preauthProductStr)))
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
        result = 37 * result + this.deviceStatusStr.hashCode();
        result = 37 * result + this.deviceModel.hashCode();
        result = 37 * result + this.operationTypeStr.hashCode();
        result = 37 * result + this.preauthProductStr.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("deviceStatusStr:").append(deviceStatusStr);
        returnStringBuffer.append("deviceModel:").append(deviceModel);
        returnStringBuffer.append("operationTypeStr:").append(operationTypeStr);
        returnStringBuffer.append("preauthProductStr:").append(
                preauthProductStr);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }
}
