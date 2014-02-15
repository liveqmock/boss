package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.LdapHostDTO;

public interface LdapHost extends EJBLocalObject {
    public void setCmentrydir(String cmentrydir);

    public String getCmentrydir();

    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getHostID();

    public void setHostName(String hostName);

    public String getHostName();

    public void setProtocolType(String protocolType);

    public String getProtocolType();

    public void setIpAddress(String ipAddress);

    public String getIpAddress();

    public void setPort(int port);

    public int getPort();

    public void setLoginID(String loginID);

    public String getLoginID();

    public void setLoginPWD(String loginPWD);

    public String getLoginPWD();
    public int ejbUpdate(LdapHostDTO dto);
}
