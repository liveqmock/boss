package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CatvTermMigrationDTO implements ReflectionSupport,Serializable {
    private String catvID;
    private int areaID;
    private int createOpID;
    private Timestamp createDate;
    private String migrationStatus;
    private Timestamp migrationDoneDate;
    private int dtvCustomerID;
    private int creditNum;
    private int usedCreditNum;
    private String workerName;
    private String comments;
    private String migrationOption;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    public String getCatvID() {
        return catvID;
    }

    public void setCatvID(String catvID) {
        this.catvID = catvID;
        put("catvID");
    }

    public int getAreaID() {
        return areaID;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
        put("areaID");
    }

    public int getCreateOpID() {
        return createOpID;
    }

    public void setCreateOpID(int createOpID) {
        this.createOpID = createOpID;
        put("createOpID");
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
        put("createDate");
    }

    public String getMigrationStatus() {
        return migrationStatus;
    }

    public void setMigrationStatus(String migrationStatus) {
        this.migrationStatus = migrationStatus;
        put("migrationStatus");
    }

    public Timestamp getMigrationDoneDate() {
        return migrationDoneDate;
    }

    public void setMigrationDoneDate(Timestamp migrationDoneDate) {
        this.migrationDoneDate = migrationDoneDate;
        put("migrationDoneDate");
    }

    public int getDtvCustomerID() {
        return dtvCustomerID;
    }

    public void setDtvCustomerID(int dtvCustomerID) {
        this.dtvCustomerID = dtvCustomerID;
        put("dtvCustomerID");
    }

    public int getCreditNum() {
        return creditNum;
    }

    public void setCreditNum(int creditNum) {
        this.creditNum = creditNum;
        put("creditNum");
    }

    public int getUsedCreditNum() {
        return usedCreditNum;
    }

    public void setUsedCreditNum(int usedCreditNum) {
        this.usedCreditNum = usedCreditNum;
        put("usedCreditNum");
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
        put("workerName");
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
        put("comments");
    }

    public String getMigrationOption() {
        return migrationOption;
    }

    public void setMigrationOption(String migrationOption) {
        this.migrationOption = migrationOption;
        put("migrationOption");
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
        if (!(obj instanceof CatvTermMigrationDTO))
            return false;
        CatvTermMigrationDTO that = (CatvTermMigrationDTO) obj;
        
        if (!(that.catvID == null ? this.catvID == null :
            that.catvID.equals(this.catvID)))
        
            return false;
        if (that.areaID != this.areaID)
            return false;
        if (that.createOpID != this.createOpID)
            return false;
        if (!(that.createDate == null ? this.createDate == null :
              that.createDate.equals(this.createDate)))
            return false;
        if (!(that.migrationStatus == null ? this.migrationStatus == null :
              that.migrationStatus.equals(this.migrationStatus)))
            return false;
        if (!(that.migrationDoneDate == null ? this.migrationDoneDate == null :
              that.migrationDoneDate.equals(this.migrationDoneDate)))
            return false;
        if (that.dtvCustomerID != this.dtvCustomerID)
            return false;
        if (that.creditNum != this.creditNum)
            return false;
        if (that.usedCreditNum != this.usedCreditNum)
            return false;
        if (!(that.workerName == null ? this.workerName == null :
              that.workerName.equals(this.workerName)))
            return false;
        if (!(that.comments == null ? this.comments == null :
              that.comments.equals(this.comments)))
            return false;
        if (!(that.migrationOption == null ? this.migrationOption == null :
              that.migrationOption.equals(this.migrationOption)))
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
        result = 37 * result +  this.catvID.hashCode();
        result = 37 * result + (int)this.areaID;
        result = 37 * result + (int)this.createOpID;
        result = 37 * result + this.createDate.hashCode();
        result = 37 * result + this.migrationStatus.hashCode();
        result = 37 * result + this.migrationDoneDate.hashCode();
        result = 37 * result + (int)this.dtvCustomerID;
        result = 37 * result + (int)this.creditNum;
        result = 37 * result + (int)this.usedCreditNum;
        result = 37 * result + this.workerName.hashCode();
        result = 37 * result + this.comments.hashCode();
        result = 37 * result + this.migrationOption.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(448);
        returnStringBuffer.append("[");
        returnStringBuffer.append("catvID:").append(catvID);
        returnStringBuffer.append("areaID:").append(areaID);
        returnStringBuffer.append("createOpID:").append(createOpID);
        returnStringBuffer.append("createDate:").append(createDate);
        returnStringBuffer.append("migrationStatus:").append(migrationStatus);
        returnStringBuffer.append("migrationDoneDate:").append(
                migrationDoneDate);
        returnStringBuffer.append("dtvCustomerID:").append(dtvCustomerID);
        returnStringBuffer.append("creditNum:").append(creditNum);
        returnStringBuffer.append("usedCreditNum:").append(usedCreditNum);
        returnStringBuffer.append("workerName:").append(workerName);
        returnStringBuffer.append("comments:").append(comments);
        returnStringBuffer.append("migrationOption:").append(migrationOption);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }
}
