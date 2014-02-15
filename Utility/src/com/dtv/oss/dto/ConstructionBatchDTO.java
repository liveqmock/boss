package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ConstructionBatchDTO implements ReflectionSupport,Serializable {
    private int batchId;
    private String referSheetNo;
    private String inputFileName;
    private int createOrgId;
    private Timestamp createDate;
    private int createOperatorId;
    private Timestamp auditDate;
    private int auditOperatorId;
    private int auditOrgId;
    private int fiberNodeId;
    private String builderName;
    private String postCode;
    private String constructionName;
    private int districtId;
    private String addressPrefix;
    private int totalHouseNumber;
    private int newHouseNumber;
    private int newPortNumber;
    private String catvTermStatus;
    private String status;
    private String description;
    private String cableType ;
    private String biDirectionFlag ;
    private String catvTermType;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    
    
    public String getBiDirectionFlag() {
		return biDirectionFlag;
	}

	public void setBiDirectionFlag(String biDirectionFlag) {
		this.biDirectionFlag = biDirectionFlag;
		 put("biDirectionFlag");
	}

	public String getCableType() {
		return cableType;
	}

	public void setCableType(String cableType) {
		this.cableType = cableType;
		 put("cableType");
	}

	public String getCatvTermType() {
		return catvTermType;
	}

	public void setCatvTermType(String catvTermType) {
		this.catvTermType = catvTermType;
		 put("catvTermType");
	}

	public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getReferSheetNo() {
        return referSheetNo;
    }

    public void setReferSheetNo(String referSheetNo) {
        this.referSheetNo = referSheetNo;
        put("referSheetNo");
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
        put("inputFileName");
    }

    public int getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(int createOrgId) {
        this.createOrgId = createOrgId;
        put("createOrgId");
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
        put("createDate");
    }

    public int getCreateOperatorId() {
        return createOperatorId;
    }

    public void setCreateOperatorId(int createOperatorId) {
        this.createOperatorId = createOperatorId;
        put("createOperatorId");
    }

    public Timestamp getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Timestamp auditDate) {
        this.auditDate = auditDate;
        put("auditDate");
    }

    public int getAuditOperatorId() {
        return auditOperatorId;
    }

    public void setAuditOperatorId(int auditOperatorId) {
        this.auditOperatorId = auditOperatorId;
        put("auditOperatorId");
    }

    public int getAuditOrgId() {
        return auditOrgId;
    }

    public void setAuditOrgId(int auditOrgId) {
        this.auditOrgId = auditOrgId;
        put("auditOrgId");
    }

    public int getFiberNodeId() {
        return fiberNodeId;
    }

    public void setFiberNodeId(int fiberNodeId) {
        this.fiberNodeId = fiberNodeId;
        put("fiberNodeId");
    }

    public String getBuilderName() {
        return builderName;
    }

    public void setBuilderName(String builderName) {
        this.builderName = builderName;
        put("builderName");
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
        put("postCode");
    }

    public String getConstructionName() {
        return constructionName;
    }

    public void setConstructionName(String constructionName) {
        this.constructionName = constructionName;
        put("constructionName");
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
        put("districtId");
    }

    public String getAddressPrefix() {
        return addressPrefix;
    }

    public void setAddressPrefix(String addressPrefix) {
        this.addressPrefix = addressPrefix;
        put("addressPrefix");
    }

    public int getTotalHouseNumber() {
        return totalHouseNumber;
    }

    public void setTotalHouseNumber(int totalHouseNumber) {
        this.totalHouseNumber = totalHouseNumber;
        put("totalHouseNumber");
    }

    public int getNewHouseNumber() {
        return newHouseNumber;
    }

    public void setNewHouseNumber(int newHouseNumber) {
        this.newHouseNumber = newHouseNumber;
        put("newHouseNumber");
    }

    public int getNewPortNumber() {
        return newPortNumber;
    }

    public void setNewPortNumber(int newPortNumber) {
        this.newPortNumber = newPortNumber;
        put("newPortNumber");
    }

    public String getCatvTermStatus() {
        return catvTermStatus;
    }

    public void setCatvTermStatus(String catvTermStatus) {
        this.catvTermStatus = catvTermStatus;
        put("catvTermStatus");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        put("status");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
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
        if (!(obj instanceof ConstructionBatchDTO))
            return false;
        ConstructionBatchDTO that = (ConstructionBatchDTO) obj;
        if (that.batchId != this.batchId)
      
            return false;
        if (!(that.referSheetNo == null ? this.referSheetNo == null :
              that.referSheetNo.equals(this.referSheetNo)))
            return false;
        if (!(that.inputFileName == null ? this.inputFileName == null :
              that.inputFileName.equals(this.inputFileName)))
            return false;
        if (that.createOrgId != this.createOrgId)
            return false;
        if (!(that.createDate == null ? this.createDate == null :
              that.createDate.equals(this.createDate)))
            return false;
        if (that.createOperatorId != this.createOperatorId)
            return false;
        if (!(that.auditDate == null ? this.auditDate == null :
              that.auditDate.equals(this.auditDate)))
            return false;
        if (that.auditOperatorId != this.auditOperatorId)
            return false;
        if (that.auditOrgId != this.auditOrgId)
            return false;
        if (that.fiberNodeId != this.fiberNodeId)
            return false;
        if (!(that.builderName == null ? this.builderName == null :
              that.builderName.equals(this.builderName)))
            return false;
        if (!(that.postCode == null ? this.postCode == null :
              that.postCode.equals(this.postCode)))
            return false;
        if (!(that.constructionName == null ? this.constructionName == null :
              that.constructionName.equals(this.constructionName)))
            return false;
        if (that.districtId != this.districtId)
            return false;
        if (!(that.addressPrefix == null ? this.addressPrefix == null :
              that.addressPrefix.equals(this.addressPrefix)))
            return false;
        if (that.totalHouseNumber != this.totalHouseNumber)
            return false;
        if (that.newHouseNumber != this.newHouseNumber)
            return false;
        if (that.newPortNumber != this.newPortNumber)
            return false;
        if (!(that.catvTermStatus == null ? this.catvTermStatus == null :
              that.catvTermStatus.equals(this.catvTermStatus)))
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (!(that.cableType == null ? this.cableType == null :
            that.cableType.equals(this.cableType)))
          return false;
        if (!(that.biDirectionFlag == null ? this.biDirectionFlag == null :
            that.biDirectionFlag.equals(this.biDirectionFlag)))
          return false;
        if (!(that.catvTermType == null ? this.catvTermType == null :
            that.catvTermType.equals(this.catvTermType)))
          return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
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
        result = 37 * result + (int)this.batchId;
        result = 37 * result + this.referSheetNo.hashCode();
        result = 37 * result + this.inputFileName.hashCode();
        result = 37 * result + (int)this.createOrgId;
        result = 37 * result + this.createDate.hashCode();
        result = 37 * result + (int)this.createOperatorId;
        result = 37 * result + this.auditDate.hashCode();
        result = 37 * result + (int)this.auditOperatorId;
        result = 37 * result + (int)this.auditOrgId;
        result = 37 * result + (int)this.fiberNodeId;
        result = 37 * result + this.builderName.hashCode();
        result = 37 * result + this.postCode.hashCode();
        result = 37 * result + this.constructionName.hashCode();
        result = 37 * result + (int)this.districtId;
        result = 37 * result + this.addressPrefix.hashCode();
        result = 37 * result + (int)this.totalHouseNumber;
        result = 37 * result + (int)this.newHouseNumber;
        result = 37 * result + (int)this.newPortNumber;
        result = 37 * result + this.catvTermStatus.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + this.cableType.hashCode();
        result = 37 * result + this.biDirectionFlag.hashCode();
        result = 37 * result + this.catvTermType.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(736);
        returnStringBuffer.append("[");
        returnStringBuffer.append("batchId:").append(batchId);
        returnStringBuffer.append("referSheetNo:").append(referSheetNo);
        returnStringBuffer.append("inputFileName:").append(inputFileName);
        returnStringBuffer.append("createOrgId:").append(createOrgId);
        returnStringBuffer.append("createDate:").append(createDate);
        returnStringBuffer.append("createOperatorId:").append(createOperatorId);
        returnStringBuffer.append("auditDate:").append(auditDate);
        returnStringBuffer.append("auditOperatorId:").append(auditOperatorId);
        returnStringBuffer.append("auditOrgId:").append(auditOrgId);
        returnStringBuffer.append("fiberNodeId:").append(fiberNodeId);
        returnStringBuffer.append("builderName:").append(builderName);
        returnStringBuffer.append("postCode:").append(postCode);
        returnStringBuffer.append("constructionName:").append(constructionName);
        returnStringBuffer.append("districtId:").append(districtId);
        returnStringBuffer.append("addressPrefix:").append(addressPrefix);
        returnStringBuffer.append("totalHouseNumber:").append(totalHouseNumber);
        returnStringBuffer.append("newHouseNumber:").append(newHouseNumber);
        returnStringBuffer.append("newPortNumber:").append(newPortNumber);
        returnStringBuffer.append("catvTermStatus:").append(catvTermStatus);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("description:").append(description);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("cableType:").append(cableType);
        returnStringBuffer.append("biDirectionFlag:").append(biDirectionFlag);
        returnStringBuffer.append("catvTermType:").append(catvTermType);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) { map.put(field, Boolean.TRUE); }

    public java.util.Map getMap() { return this.map; }
}
