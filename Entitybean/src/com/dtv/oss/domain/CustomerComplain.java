package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.CustomerComplainDTO;

public interface CustomerComplain extends javax.ejb.EJBLocalObject {
	public Integer getCustComplainId();

	public void setCustomerId(int customerId);

	public int getCustomerId();

	public void setType(String type);

	public String getType();

	public void setContent(String content);

	public String getContent();

	public void setRequest(String request);

	public String getRequest();

	public void setComplainedOrgId(int complainedOrgId);

	public int getComplainedOrgId();

	public void setComplainedPerson(String complainedPerson);

	public String getComplainedPerson();

	public void setContactPerson(String contactPerson);

	public String getContactPerson();

	public void setContactPhone(String contactPhone);

	public String getContactPhone();

	public void setCallBackFlag(String callBackFlag);

	public String getCallBackFlag();

	public void setCallBackDate(Timestamp callBackDate);

	public Timestamp getCallBackDate();

	public void setCurrentWorkOrgID(int currentWorkOrgID);

	public int getCurrentWorkOrgID();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();


	public int ejbUpdate(CustomerComplainDTO dto);
}