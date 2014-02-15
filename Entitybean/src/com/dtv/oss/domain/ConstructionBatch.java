package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.ConstructionBatchDTO;

public interface ConstructionBatch extends EJBLocalObject {
    public void setStatus(String status);

    public String getStatus();

    public void setDescription(String description);

    public String getDescription();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    public  void setBiDirectionFlag(String biDirectionFlag);

    public  String getBiDirectionFlag();

    public  void setCableType(String cableType);

    public  String getCableType();

    public  void setCatvTermType(String catvTermType);

    public  String getCatvTermType();


    public Integer getBatchId();

    public void setReferSheetNo(String referSheetNo);

    public String getReferSheetNo();

    public void setInputFileName(String inputFileName);

    public String getInputFileName();

    public void setCreateOrgId(int createOrgId);

    public int getCreateOrgId();

    public void setCreateDate(Timestamp createDate);

    public Timestamp getCreateDate();

    public void setCreateOperatorId(int createOperatorId);

    public int getCreateOperatorId();

    public void setAuditDate(Timestamp auditDate);

    public Timestamp getAuditDate();

    public void setAuditOperatorId(int auditOperatorId);

    public int getAuditOperatorId();

    public void setAuditOrgId(int auditOrgId);

    public int getAuditOrgId();

    public void setFiberNodeId(int fiberNodeId);

    public int getFiberNodeId();

    public void setBuilderName(String builderName);

    public String getBuilderName();

    public void setPostCode(String postCode);

    public String getPostCode();

    public void setConstructionName(String constructionName);

    public String getConstructionName();

    public void setDistrictId(int districtId);

    public int getDistrictId();

    public void setAddressPrefix(String addressPrefix);

    public String getAddressPrefix();

    public void setTotalHouseNumber(int totalHouseNumber);

    public int getTotalHouseNumber();

    public void setNewHouseNumber(int newHouseNumber);

    public int getNewHouseNumber();

    public void setNewPortNumber(int newPortNumber);

    public int getNewPortNumber();

    public void setCatvTermStatus(String catvTermStatus);

    public String getCatvTermStatus();
    
    public int ejbUpdate(ConstructionBatchDTO dto);
}
