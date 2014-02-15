package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.BundlePrepaymentDTO;

public interface BundlePrepayment extends EJBLocalObject {

    public Integer getCampaignId();

    public void setTargetBundleId(int targetBundleId);

    public int getTargetBundleId();

    public void setBundlePrepaymentPlanId(int bundlePrepaymentPlanId);

    public int getBundlePrepaymentPlanId();

    public void setTimeUnitLengthType(String timeUnitLengthType);

    public String getTimeUnitLengthType();

    public void setTimeUnitLengthNumber(int timeUnitLengthNumber);

    public int getTimeUnitLengthNumber();

    public void setAcctItemTypeId(String acctItemTypeId);

    public String getAcctItemTypeId();

    public void setFeeType(String feeType);

    public String getFeeType();

    public void setAmount(double amount);

    public double getAmount();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    public int ejbUpdate(BundlePrepaymentDTO dto);
}
