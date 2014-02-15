package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DtvMigrationAreaDTO implements ReflectionSupport,Serializable {
    private int id;
    private Timestamp createDate;
    private Integer createOpId;
    private String areaCode;
    private String areaName;
    private String description;
    private int regionalAreaId;
    private String migrationTeamName;
    private String migrationTeamId;
    private Timestamp batchStartDate;
    private Timestamp batchEndDate;
    private String status;
    private Timestamp statusDate;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    private int planMigrationRoomNum;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
        put("createDate");
    }

    public Integer getCreateOpId() {
        return createOpId;
    }

    public void setCreateOpId(Integer createOpId) {
        this.createOpId = createOpId;
        put("createOpId");
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
        put("areaCode");
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
        put("areaName");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
    }

    public int getRegionalAreaId() {
        return regionalAreaId;
    }

    public void setRegionalAreaId(int regionalAreaId) {
        this.regionalAreaId = regionalAreaId;
        put("regionalAreaId");
    }

    public String getMigrationTeamName() {
        return migrationTeamName;
    }

    public void setMigrationTeamName(String migrationTeamName) {
        this.migrationTeamName = migrationTeamName;
        put("migrationTeamName");
    }

    public String getMigrationTeamId() {
        return migrationTeamId;
    }

    public void setMigrationTeamId(String migrationTeamId) {
        this.migrationTeamId = migrationTeamId;
        put("migrationTeamId");
    }

    public Timestamp getBatchStartDate() {
        return batchStartDate;
    }

    public void setBatchStartDate(Timestamp batchStartDate) {
        this.batchStartDate = batchStartDate;
        put("batchStartDate");
    }

    public Timestamp getBatchEndDate() {
        return batchEndDate;
    }

    public void setBatchEndDate(Timestamp batchEndDate) {
        this.batchEndDate = batchEndDate;
        put("batchEndDate");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        put("status");
    }

    public Timestamp getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Timestamp statusDate) {
        this.statusDate = statusDate;
        put("statusDate");
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
    
    public int getPlanMigrationRoomNum() {
		return planMigrationRoomNum;
	}

	public void setPlanMigrationRoomNum(int planMigrationRoomNum) {
		this.planMigrationRoomNum = planMigrationRoomNum;
		put("planMigrationRoomNum");
	}
	
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof DtvMigrationAreaDTO))
            return false;
        DtvMigrationAreaDTO that = (DtvMigrationAreaDTO) obj;
        if(that.id!=this.id)
            return false;
        if (!(that.createDate == null ? this.createDate == null :
              that.createDate.equals(this.createDate)))
            return false;
        if (!(that.createOpId == null ? this.createOpId == null :
              that.createOpId.equals(this.createOpId)))
            return false;
        if (!(that.areaCode == null ? this.areaCode == null :
              that.areaCode.equals(this.areaCode)))
            return false;
        if (!(that.areaName == null ? this.areaName == null :
              that.areaName.equals(this.areaName)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
            return false;
        if (that.regionalAreaId != this.regionalAreaId)
            return false;
        if (!(that.migrationTeamName == null ? this.migrationTeamName == null :
              that.migrationTeamName.equals(this.migrationTeamName)))
            return false;
        if (!(that.migrationTeamId == null ? this.migrationTeamId == null :
              that.migrationTeamId.equals(this.migrationTeamId)))
            return false;
        if (!(that.batchStartDate == null ? this.batchStartDate == null :
              that.batchStartDate.equals(this.batchStartDate)))
            return false;
        if (!(that.batchEndDate == null ? this.batchEndDate == null :
              that.batchEndDate.equals(this.batchEndDate)))
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (!(that.statusDate == null ? this.statusDate == null :
              that.statusDate.equals(this.statusDate)))
            return false;
        if (!(that.dtCreate == null ? this.dtCreate == null :
              that.dtCreate.equals(this.dtCreate)))
            return false;
        if (!(that.dtLastmod == null ? this.dtLastmod == null :
              that.dtLastmod.equals(this.dtLastmod)))
            return false;
        if (that.planMigrationRoomNum != this.planMigrationRoomNum)
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int)this.id;
        result = 37 * result + this.createDate.hashCode();
        result = 37 * result + this.createOpId.hashCode();
        result = 37 * result + this.areaCode.hashCode();
        result = 37 * result + this.areaName.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + (int)this.regionalAreaId;
        result = 37 * result + this.migrationTeamName.hashCode();
        result = 37 * result + this.migrationTeamId.hashCode();
        result = 37 * result + this.batchStartDate.hashCode();
        result = 37 * result + this.batchEndDate.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.statusDate.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(480);
        returnStringBuffer.append("[");
        returnStringBuffer.append("id:").append(id);
        returnStringBuffer.append("createDate:").append(createDate);
        returnStringBuffer.append("createOpId:").append(createOpId);
        returnStringBuffer.append("areaCode:").append(areaCode);
        returnStringBuffer.append("areaName:").append(areaName);
        returnStringBuffer.append("description:").append(description);
        returnStringBuffer.append("regionalAreaId:").append(regionalAreaId);
        returnStringBuffer.append("migrationTeamName:").append(
                migrationTeamName);
        returnStringBuffer.append("migrationTeamId:").append(migrationTeamId);
        returnStringBuffer.append("batchStartDate:").append(batchStartDate);
        returnStringBuffer.append("batchEndDate:").append(batchEndDate);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("statusDate:").append(statusDate);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("planMigrationRoomNum:").append(planMigrationRoomNum);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }

	

  
}
