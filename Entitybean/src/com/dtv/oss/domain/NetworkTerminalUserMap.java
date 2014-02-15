package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.NetworkTerminalUserMapDTO;

public interface NetworkTerminalUserMap extends EJBLocalObject {
    public void setStatus(String status);

    public String getStatus();

    public void setStatusDate(Timestamp statusDate);

    public Timestamp getStatusDate();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getSeqNo();

    public void setTerminalType(String terminalType);

    public String getTerminalType();

    public void setTerminalID(int terminalID);

    public int getTerminalID();

    public void setCustomerID(int customerID);

    public int getCustomerID();

    public void setUserID(int userID);

    public int getUserID();

    public void setOwnerFlag(String ownerFlag);

    public String getOwnerFlag();
    
    public int ejbUpdate(NetworkTerminalUserMapDTO dto);
}
