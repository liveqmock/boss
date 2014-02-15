package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.ContractProcessLogDTO;

public interface ContractProcessLog extends EJBLocalObject {
    public void setAction(String action);

    public String getAction();

    public void setDescription(String description);

    public String getDescription();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public void setActionTime(Timestamp actionTime);

    public Timestamp getActionTime();


    


    public Integer getSeqNo();

    public void setContractNo(String contractNo);

    public String getContractNo();
    public int ejbUpdate(ContractProcessLogDTO dto);
}
