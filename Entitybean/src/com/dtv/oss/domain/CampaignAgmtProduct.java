package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;
import java.math.BigDecimal;
import java.sql.Timestamp;

public interface CampaignAgmtProduct extends EJBLocalObject {

    public int getCampaignID();

    public int getProductID();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
}
