package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.FapiaoTransitionDTO;

import java.sql.Timestamp;

public interface FapiaoTransition extends EJBLocalObject {
    public void setAction(String action);

    public String getAction();

    public void setFileName(String fileName);

    public String getFileName();

    

    public void setStatus(String status);

    public String getStatus();

    public void setCreateTime(Timestamp createTime);

    public Timestamp getCreateTime();

    public void setPrintCode(String printCode);

    public String getPrintCode();

    public void setPrintNumber(String printNumber);

    public String getPrintNumber();

    public void setTaxControlCode(String taxControlCode);

    public String getTaxControlCode();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public Integer getSeqNo();

    public void setVolumnSeqNo(int volumnSeqNo);

    public int getVolumnSeqNo();

    public void setTotal(int total);

    public int getTotal();

    public void setOpID(int opID);

    public int getOpID();

    public void setOrgID(int orgID);

    public int getOrgID();
    
    public int ejbUpdate(FapiaoTransitionDTO dto);
}
