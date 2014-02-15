package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

public interface NetworkTerminalUseSetting extends EJBLocalObject {
    public int getServiceId();

    public String getTerminalType();

    public void setBindType(String bindtype);

    public String getBindType();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
}
