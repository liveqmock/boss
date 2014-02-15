package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.CAWalletChargeRecordDTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public interface CAWalletChargeRecord extends EJBLocalObject {
    public void setStatus(String status);

    public String getStatus();

    public void setCaWalletCode(String caWalletCode);

    public String getCaWalletCode();

    public void setCsiId(int csiId);

    public int getCsiId();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getSeqNo();

    public void setWalletId(int walletId);

    public int getWalletId();

    public void setCustomerId(int customerId);

    public int getCustomerId();

    public void setServiceAccountId(int serviceAccountId);

    public int getServiceAccountId();

    public void setScSerialNo(String scSerialNo);

    public String getScSerialNo();

    public void setCreateTime(Timestamp createTime);

    public Timestamp getCreateTime();

    public void setOpId(int opId);

    public int getOpId();

    public void setOrgId(int orgId);

    public int getOrgId();

    public void setMopId(int mopId);

    public int getMopId();

    public void setPoint(int point);

    public int getPoint();

    public void setValue(BigDecimal value);

    public BigDecimal getValue();
    
    public int ejbUpdate(CAWalletChargeRecordDTO dto);
}
