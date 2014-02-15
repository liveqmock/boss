package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class MachineRoomDTO implements ReflectionSupport,Serializable {
    private int id;
    private String machineRoomCode;
    private String machineRoomName;
    private String description;
    private String detailAddress;
    private int districtId;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMachineRoomCode() {
        return machineRoomCode;
    }

    public void setMachineRoomCode(String machineRoomCode) {
        this.machineRoomCode = machineRoomCode;
        put("machineRoomCode");
    }
    public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
		put("districtId");
	}

    public String getMachineRoomName() {
        return machineRoomName;
    }

    public void setMachineRoomName(String machineRoomName) {
        this.machineRoomName = machineRoomName;
        put("machineRoomName");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
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
        if (!(obj instanceof MachineRoomDTO))
            return false;
        MachineRoomDTO that = (MachineRoomDTO) obj;
        if (that.id != this.id)
            return false;
       if (that.districtId != this.districtId)
            
            return false;
        if (!(that.machineRoomCode == null ? this.machineRoomCode == null :
              that.machineRoomCode.equals(this.machineRoomCode)))
            return false;
        if (!(that.machineRoomName == null ? this.machineRoomName == null :
              that.machineRoomName.equals(this.machineRoomName)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
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
        result = 37 * result + this.machineRoomCode.hashCode();
        result = 37 * result + this.machineRoomName.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + this.detailAddress.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(224);
        returnStringBuffer.append("[");
        returnStringBuffer.append("id:").append(id);
        returnStringBuffer.append("machineRoomCode:").append(machineRoomCode);
        returnStringBuffer.append("districtId:").append(districtId);
        returnStringBuffer.append("machineRoomName:").append(machineRoomName);
        returnStringBuffer.append("description:").append(description);
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
