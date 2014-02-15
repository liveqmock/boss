package com.dtv.oss.domain;

import java.sql.Timestamp;

import com.dtv.oss.dto.QueryCriteriaDTO;

public interface QueryCriteria extends javax.ejb.EJBLocalObject {
	public Integer getId();

	public void setJobID(int jobID);

	public int getJobID();

	public Timestamp getCreateDate();

	public void setCreateDate(Timestamp createDate);

	public void setCustomerType(String customerType);

	public String getCustomerType();

	public void setOpenSourceType(String openSourceType);

	public String getOpenSourceType();

	public void setOpenSourceTypeID(int openSourceTypeID);

	public int getOpenSourceTypeID();

	public void setDistrictID(int districtID);

	public int getDistrictID();

	public void setStreetID(int streetID);

	public int getStreetID();

	public void setOrgID(int orgID);

	public int getOrgID();

	public void setCampaignIDs(String campaignIDs);

	public String getCampaignIDs();

	public void setDtCreate(Timestamp dtCreate);

	public Timestamp getDtCreate();

	public void setDtLastmod(Timestamp dtLastmod);

	public Timestamp getDtLastmod();

	public void setFeeType(String feeType);

	public String getFeeType();

	public void setCustomerId(int customerId);

	public int getCustomerId();

	public void setStatus(String status);

	public String getStatus();

	public void setAccountStatus(String accountStatus);

	public String getAccountStatus();

	public int ejbUpdate(QueryCriteriaDTO dto);
}