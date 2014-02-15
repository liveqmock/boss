package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

public interface CAProductDef extends EJBLocalObject {

    public int getHostID();

    public int getOpiID();

    public String getCaProductID();

    public void setProductName(String productName);

    public String getProductName();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
}
