package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CACommandDTO;

public interface CACommand extends javax.ejb.EJBLocalObject {
	public void setDescription(String description);

	public String getDescription();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public int getCommandID();

	public void setCommandName(String commandName);

	public String getCommandName();

	public void setCommandString(String commandString);

	public String getCommandString();

	public int ejbUpdate(CACommandDTO dto);
}