package com.dtv.oss.domain;
import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.LdapCommandDTO;

public interface LdapCommand extends EJBLocalObject {
    public void setDescription(String description);

    public String getDescription();

    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public String getCommandName();

    public void setCommandString(String commandString);

    public String getCommandString();
    
    public int ejbUpdate(LdapCommandDTO dto);
    
    
}
