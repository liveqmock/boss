package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

public interface XSlbdyf extends EJBLocalObject {
    public void setZt(String zt);

    public String getZt();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getLsh();

    public void setSltcID(int sltcID);

    public int getSltcID();

    public void setLyID(int lyID);

    public int getLyID();

    public void setSldID(int sldID);

    public int getSldID();

    public void setTczfjh(int tczfjh);

    public int getTczfjh();

    public void setKsRQ(Timestamp ksRQ);

    public Timestamp getKsRQ();

    public void setJsRQ(Timestamp jsRQ);

    public Timestamp getJsRQ();

    public void setZmLX(String zmLX);

    public String getZmLX();

    public void setFyLX(String fyLX);

    public String getFyLX();

    public void setYfje(int yfje);

    public int getYfje();
}
