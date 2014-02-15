package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

public interface LdapCondition extends EJBLocalObject {
    public void setDescription(String description);

    public String getDescription();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public Integer getCondId();

    public void setCondName(String condName);

    public String getCondName();

    public void setHostId(int hostId);

    public int getHostId();

    public void setCondString(String condString);

    public String getCondString();
}
