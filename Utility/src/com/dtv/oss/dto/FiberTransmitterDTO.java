package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class FiberTransmitterDTO implements ReflectionSupport,Serializable {
    private int id;
    private String fiberTransmitterCode;
    private String description;
    private int machineRoomId;
    private int districtId;
    private String detailAddress;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
		put("districtId");
	}

    public String getFiberTransmitterCode() {
        return fiberTransmitterCode;
    }

    public void setFiberTransmitterCode(String fiberTransmitterCode) {
        this.fiberTransmitterCode = fiberTransmitterCode;
        put("fiberTransmitterCode");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
    }

    public int getMachineRoomId() {
        return machineRoomId;
    }

    public void setMachineRoomId(int machineRoomId) {
        this.machineRoomId = machineRoomId;
        put("machineRoomId");
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
        put("detailAddress");
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
        if (!(obj instanceof FiberTransmitterDTO))
            return false;
        FiberTransmitterDTO that = (FiberTransmitterDTO) obj;
        if (that.id != this.id)
       
            return false;
        if (that.districtId != this.districtId)
            
            return false;
        if (!(that.fiberTransmitterCode == null ? this.fiberTransmitterCode == null :
              that.fiberTransmitterCode.equals(this.fiberTransmitterCode)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
            return false;
        if (that.machineRoomId != this.machineRoomId)
            return false;
        if (!(that.detailAddress == null ? this.detailAddress == null :
              that.detailAddress.equals(this.detailAddress)))
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
        result = 37 * result + (int)this.id;
        result = 37 * result + (int)this.districtId;
        result = 37 * result + this.fiberTransmitterCode.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + (int)this.machineRoomId;
        result = 37 * result + this.detailAddress.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("id:").append(id);
        returnStringBuffer.append("districtId:").append(districtId);
        returnStringBuffer.append("fiberTransmitterCode:").append(
                fiberTransmitterCode);
        returnStringBuffer.append("description:").append(description);
        returnStringBuffer.append("machineRoomId:").append(machineRoomId);
        returnStringBuffer.append("detailAddress:").append(detailAddress);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }
}
