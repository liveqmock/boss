package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.LdapProcessLogDTO;

public interface LdapProcessLog extends EJBLocalObject {
    public void setDescription(String description);

    public String getDescription();

    public void setCustomerID(int customerID);

    public int getCustomerID();

    public void setAccountID(int accountID);

    public int getAccountID();

    public void setServiceAccountID(int serviceAccountID);

    public int getServiceAccountID();

    public void setPsID(int psID);

    public int getPsID();

    public void setDeviceID(int deviceID);

    public int getDeviceID();

    public  void setQueueID(int queueID);

    public  int getQueueID();

    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getSeqno();

    public void setCreateTime(Timestamp createTime);

    public Timestamp getCreateTime();

    public void setEventID(int eventID);

    public int getEventID();

    public void setCommandName(String commandName);

    public String getCommandName();

    public void setProcessResult(String processResult);

    public String getProcessResult();
    public int ejbUpdate(LdapProcessLogDTO dto);
}
