package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class QueryCriteriaDTO implements ReflectionSupport, java.io.Serializable
{
	private int id;
	private int jobID;
	private String customerType;
	private String openSourceType;
	private int openSourceTypeID;
	private int districtID;
	private int streetID;
	private int orgID;
	private String feeType;
	private int customerId;
	private String status;
	private Timestamp createDate;
	private String accountStatus;
	private String campaignIDs;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public QueryCriteriaDTO()
	{
	}

	public QueryCriteriaDTO(int id, int jobID, String customerType, String openSourceType, int openSourceTypeID, int districtID, int streetID, int orgID, String campaignIDs, Timestamp dtCreate, Timestamp dtLastmod,String feeType,int customerId,String status,String accountStatus,Timestamp createDate)
	{
		setId(id);
		setJobID(jobID);
		setCustomerType(customerType);
		setOpenSourceType(openSourceType);
		setOpenSourceTypeID(openSourceTypeID);
		setDistrictID(districtID);
		setStreetID(streetID);
		setOrgID(orgID);
		setCampaignIDs(campaignIDs);
		setFeeType(feeType);
		setCustomerId(customerId);
		setStatus(status);
		setAccountStatus(accountStatus);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
		setCreateDate(createDate);
	}

	public void setId(int id)
	{
		this.id = id;
		//put("id");
	}

	public int getId()
	{
		return id;
	}
	public void setCustomerId(int customerId)
	{
		this.customerId = customerId;
		put("customerId");
	}

	public int getCustomerId()
	{
		return customerId;
	}
	
	public void setJobID(int jobID)
	{
		this.jobID = jobID;
		put("jobID");
	}

	public int getJobID()
	{
		return jobID;
	}

	public void setCustomerType(String customerType)
	{
		this.customerType = customerType;
		put("customerType");
	}

	public String getCustomerType()
	{
		return customerType;
	}
	public void setFeeType(String feeType)
	{
		this.feeType = feeType;
		put("feeType");
	}

	public String getFeeType()
	{
		return feeType;
	}
	public void setStatus(String status)
	{
		this.status = status;
		put("status");
	}

	public String getStatus()
	{
		return status;
	}
	public void setAccountStatus(String accountStatus)
	{
		this.accountStatus = accountStatus;
		put("accountStatus");
	}

	public String getAccountStatus()
	{
		return accountStatus;
	}
	public void setOpenSourceType(String openSourceType)
	{
		this.openSourceType = openSourceType;
		put("openSourceType");
	}

	public String getOpenSourceType()
	{
		return openSourceType;
	}

	public void setOpenSourceTypeID(int openSourceTypeID)
	{
		this.openSourceTypeID = openSourceTypeID;
		put("openSourceTypeID");
	}

	public int getOpenSourceTypeID()
	{
		return openSourceTypeID;
	}

	public void setDistrictID(int districtID)
	{
		this.districtID = districtID;
		put("districtID");
	}

	public int getDistrictID()
	{
		return districtID;
	}

	public void setStreetID(int streetID)
	{
		this.streetID = streetID;
		put("streetID");
	}

	public int getStreetID()
	{
		return streetID;
	}

	public void setOrgID(int orgID)
	{
		this.orgID = orgID;
		put("orgID");
	}

	public int getOrgID()
	{
		return orgID;
	}

	public void setCampaignIDs(String campaignIDs)
	{
		this.campaignIDs = campaignIDs;
		put("campaignIDs");
	}

	public String getCampaignIDs()
	{
		return campaignIDs;
	}

	public void setDtCreate(Timestamp dtCreate)
	{
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtCreate()
	{
		return dtCreate;
	}

	public void setDtLastmod(Timestamp dtLastmod)
	{
		this.dtLastmod = dtLastmod;
	}

	public Timestamp getDtLastmod()
	{
		return dtLastmod;
	}

	public void setCreateDate(Timestamp createDate)
	{
		this.createDate = createDate;
		put("createDate");
	}

	public Timestamp getCreateDate()
	{
		return createDate;
	}

	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				QueryCriteriaDTO that = (QueryCriteriaDTO) obj;
				return
					this.getId() == that.getId()  &&
					this.getCustomerId() == that.getCustomerId()  &&
					this.getJobID() == that.getJobID()  &&
					(((this.getCustomerType() == null) && (that.getCustomerType() == null)) ||
						(this.getCustomerType() != null && this.getCustomerType().equals(that.getCustomerType()))) &&
					(((this.getFeeType() == null) && (that.getFeeType() == null)) ||
						(this.getFeeType() != null && this.getFeeType().equals(that.getFeeType()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
					    (this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
				   (((this.getAccountStatus() == null) && (that.getAccountStatus() == null)) ||
						(this.getAccountStatus() != null && this.getAccountStatus().equals(that.getAccountStatus()))) &&				
					(((this.getOpenSourceType() == null) && (that.getOpenSourceType() == null)) ||
						(this.getOpenSourceType() != null && this.getOpenSourceType().equals(that.getOpenSourceType()))) &&
					this.getOpenSourceTypeID() == that.getOpenSourceTypeID()  &&
					this.getDistrictID() == that.getDistrictID()  &&
					this.getStreetID() == that.getStreetID()  &&
					this.getOrgID() == that.getOrgID()  &&
					(((this.getCampaignIDs() == null) && (that.getCampaignIDs() == null)) ||
						(this.getCampaignIDs() != null && this.getCampaignIDs().equals(that.getCampaignIDs()))) &&
					(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
					(((this.getCreateDate() == null) && (that.getCreateDate() == null)) ||
						(this.getCreateDate() != null && this.getCreateDate().equals(that.getCreateDate()))) &&		
					(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
						(this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod())));
			}
		}
		return false;
	}

	public int hashCode()
	{
		return toString().hashCode();
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer(256);
		buf.append(id);
		buf.append(",").append(jobID);
		buf.append(",").append(customerType);
		buf.append(",").append(openSourceType);
		buf.append(",").append(openSourceTypeID);
		buf.append(",").append(districtID);
		buf.append(",").append(streetID);
		buf.append(",").append(createDate);
		buf.append(",").append(orgID);
		buf.append(",").append(campaignIDs);
		buf.append(",").append(feeType);
		buf.append(",").append(customerId);
		buf.append(",").append(accountStatus);
		buf.append(",").append(status);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

