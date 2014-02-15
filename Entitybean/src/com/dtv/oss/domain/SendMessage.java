package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.SendMessageDTO;

public interface SendMessage extends javax.ejb.EJBLocalObject {
	public void setContent(String content);

	public String getContent();

	public void setStatus(String status);

	public String getStatus();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public Integer getMessageId();

	public void setOrgId(int orgId);

	public int getOrgId();

	public void setOperatorId(int operatorId);

	public int getOperatorId();

	public void setCreateTime(Timestamp createTime);

	public Timestamp getCreateTime();

	public void setProcessTime(Timestamp processTime);

	public Timestamp getProcessTime();

	public void setSendType(String sendType);

	public String getSendType();

	public void setSourceType(String sourceType);

	public String getSourceType();

	public void setModuleName(String moduleName);

	public String getModuleName();

	public void setCustomerId(int customerId);

	public int getCustomerId();

	public void setServiceAccountId(int serviceAccountId);

	public int getServiceAccountId();

	public int ejbUpdate(SendMessageDTO dto);
}