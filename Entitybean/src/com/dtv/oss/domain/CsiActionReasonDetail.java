package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;
import java.math.BigDecimal;

public interface CsiActionReasonDetail extends EJBLocalObject {
    public void setKey(String key);

    public String getKey();

    public void setValue(String value);

    public String getValue();

    public void setPriority(int priority);

    public int getPriority();

    public void setDefaultValueFlag(String defaultValueFlag);

    public String getDefaultValueFlag();


    public  void setStatus(String status);

    public  String getStatus();


    public Integer getSeqNo();

    public void setReferSeqNo(int referSeqNo);

    public int getReferSeqNo();
}
