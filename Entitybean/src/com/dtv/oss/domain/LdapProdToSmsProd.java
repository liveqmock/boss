package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

public interface LdapProdToSmsProd extends EJBLocalObject {

    public Integer getSmsProductId();

    public void setLdapProductName(String ldapProductName);

    public String getLdapProductName();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    
    public  void setPriority(int priority);

    public  int getPriority();
}
