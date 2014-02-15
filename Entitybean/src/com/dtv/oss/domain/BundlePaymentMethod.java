package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.BundlePaymentMethodDTO;

public interface BundlePaymentMethod extends EJBLocalObject {

    public int getBundleID();

    public String getRfBillingCycleFlag();

    public void setBundlePaymentName(String bundlePaymentName);

    public String getBundlePaymentName();

    public void setTimeUnitLengthNumber(int timeUnitLengthNumber);

    public int getTimeUnitLengthNumber();

    public void setTimeUnitLengthType(String timeUnitLengthType);

    public String getTimeUnitLengthType();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    
    public int ejbUpdate(BundlePaymentMethodDTO dto);
}
