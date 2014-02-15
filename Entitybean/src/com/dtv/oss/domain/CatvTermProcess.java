package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CatvTermProcessDTO;

public interface CatvTermProcess extends EJBLocalObject {
    public void setStatus(String status);

    public String getStatus();

    public void setDescription(String description);

    public String getDescription();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getBatchId();

    public void setProcessType(String processType);

    public String getProcessType();

    public void setCreateDate(Timestamp createDate);

    public Timestamp getCreateDate();

    public void setCreateOperatorId(int createOperatorId);

    public int getCreateOperatorId();

    public void setCreateOrgId(int createOrgId);

    public int getCreateOrgId();

    public void setAuditDate(Timestamp auditDate);

    public Timestamp getAuditDate();

    public void setAuditOperatorId(int auditOperatorId);

    public int getAuditOperatorId();

    public void setAuditOrgId(int auditOrgId);

    public int getAuditOrgId();
    public int ejbUpdate(CatvTermProcessDTO dto);
}
