package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;
import java.sql.Timestamp;

public interface LdapProduct extends EJBLocalObject {
    public void setDescription(String description);

    public String getDescription();

    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public String getProductName();
}
