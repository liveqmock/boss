package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CatvTermProcessItemDTO;

public interface CatvTermProcessItem extends EJBLocalObject {
    public void setStatus(String status);

    public String getStatus();

    public void setComments(String comments);

    public String getComments();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getItemNo();

    public void setBatchId(int batchId);

    public int getBatchId();

    public void setCatvId(String catvId);

    public String getCatvId();

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
    
    public int ejbUpdate(CatvTermProcessItemDTO dto);
}
