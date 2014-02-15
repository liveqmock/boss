package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;
import java.sql.Timestamp;

public interface CatvJobCardConfig extends EJBLocalObject {
    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public String getCsiType();

    public String getJobCardType();

    public String getJobCardSubType();

    public void setJobcardStatus(String jobcardStatus);

    public String getJobcardStatus();
}
