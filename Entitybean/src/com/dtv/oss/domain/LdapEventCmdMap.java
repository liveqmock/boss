package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.LdapEventCmdMapDTO;

public interface LdapEventCmdMap extends EJBLocalObject {
    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public Integer getMapID();

    public void setEventClassID(int eventClassID);

    public int getEventClassID();

    public  void setConditionID(int conditionID);

    public  int getConditionID();

    public void setCommandName(String commandName);

    public String getCommandName();

    public void setPriority(int priority);

    public int getPriority();
    
    public int ejbUpdate(LdapEventCmdMapDTO dto);
}
