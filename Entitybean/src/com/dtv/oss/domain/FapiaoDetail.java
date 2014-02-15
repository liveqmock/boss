package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.FapiaoDetailDTO;

import java.sql.Timestamp;

public interface FapiaoDetail extends javax.ejb.EJBLocalObject {
    public void setUnit(String unit);

    public String getUnit();

    public void setAmount(double amount);

    public double getAmount();

    public void setCompleteTaxTag(String completeTaxTag);

    public String getCompleteTaxTag();

    public void setIsFixed(String isFixed);

    public String getIsFixed();

    public void setAuthBy(String authBy);

    public String getAuthBy();

    public void setStatus(String status);

    public String getStatus();

    public void setCreateTime(Timestamp createTime);

    public Timestamp getCreateTime();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLast);

    public Timestamp getDtLastmod();

    public Integer getSeqNo();

    public void setFapiaoSeqNo(int fapiaoSeqNo);

    public int getFapiaoSeqNo();

    public void setQuantity(int quantity);

    public int getQuantity();
    
    public int ejbUpdate(FapiaoDetailDTO dto);
}
