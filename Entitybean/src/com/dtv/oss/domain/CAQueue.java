package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CAQueueDTO;

public interface CAQueue extends javax.ejb.EJBLocalObject {
	public void setScnr(String scnr);

	public String getScnr();

	public void setStbnr(String stbnr);

	public String getStbnr();

	public void setProductId(int productId);

	public int getProductId();

	public void setOldScnr(String oldScnr);

	public String getOldScnr();

	public void setOldStbnr(String oldStbnr);

	public String getOldStbnr();

	public void setOldProductId(int oldProductId);

	public int getOldProductId();

	public void setStatus(String status);

	public String getStatus();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setDoneTime(Timestamp doneTime);

	public Timestamp getDoneTime();

	public void setHostId(int hostId);

	public int getHostId();

	public void setCondId(int condId);

	public int getCondId();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getQueueId();

	public void setEventId(int eventId);

	public int getEventId();

	public void setEventClass(int eventClass);

	public int getEventClass();

	public void setCommandId(int commandId);

	public int getCommandId();

	public void setCustomerId(int customerId);

	public int getCustomerId();
	
	public  void setOpiID(int opiID);
	
	public  int getOpiID();

	public int ejbUpdate(CAQueueDTO dto);
}