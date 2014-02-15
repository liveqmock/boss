package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class OrganizationDTO implements ReflectionSupport, java.io.Serializable
{
	private String rank;
	private String registerNo;
	private String status;
	private int orgID;
	private int parentOrgID;
	private String orgName;
	private String orgDesc;
	private String orgType;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
    private java.lang.String hasCustomerFlag;
    private  java.lang.String orgCode;
    private  java.lang.String orgSubType;

	public OrganizationDTO()
	{
	}

	public OrganizationDTO(String rank, String registerNo, String status, int orgID, int parentOrgID, String orgName, String orgDesc, String orgType, String orgSubType, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setRank(rank);
		setRegisterNo(registerNo);
		setStatus(status);
		setOrgID(orgID);
		setParentOrgID(parentOrgID);
		setOrgName(orgName);
		setOrgDesc(orgDesc);
		setOrgType(orgType);
		setOrgSubType(orgSubType);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
	}

	public void setRank(String rank)
	{
		this.rank = rank;
		put("rank");
	}

	public String getRank()
	{
		return rank;
	}
        public void setRegisterNo(String registerNo)
        {
                this.registerNo = registerNo;
                put("registerNo");
        }

        public String getRegisterNo()
        {
                return registerNo;
        }

        public void setHasCustomerFlag(String hasCustomerFlag)
                {
                        this.hasCustomerFlag = hasCustomerFlag;
                        put("hasCustomerFlag");
                }

                public String getHasCustomerFlag()
                {
                        return hasCustomerFlag;
                }

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
		put("orgCode");
	}

	public String getOrgCode()
	{
		return orgCode;
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

	public void setOrgID(int orgID)
	{
		this.orgID = orgID;
		//put("orgID");
	}

	public int getOrgID()
	{
		return orgID;
	}

	public void setParentOrgID(int parentOrgID)
	{
		this.parentOrgID = parentOrgID;
		put("parentOrgID");
	}

	public int getParentOrgID()
	{
		return parentOrgID;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
		put("orgName");
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgDesc(String orgDesc)
	{
		this.orgDesc = orgDesc;
		put("orgDesc");
	}

	public String getOrgDesc()
	{
		return orgDesc;
	}

	public void setOrgType(String orgType)
	{
		this.orgType = orgType;
		put("orgType");
	}

	public String getOrgType()
	{
		return orgType;
	}

	public void setOrgSubType(String orgSubType)
	{
		this.orgSubType = orgSubType;
		put("orgSubType");
	}

	public String getOrgSubType()
	{
		return orgSubType;
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



	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				OrganizationDTO that = (OrganizationDTO) obj;
				return
                    (((this.getHasCustomerFlag() == null) && (that.getHasCustomerFlag() == null)) ||
                           (this.getHasCustomerFlag() != null && this.getHasCustomerFlag().equals(that.getHasCustomerFlag()))) &&
                    (((this.getOrgCode() == null) && (that.getOrgCode() == null)) ||
                   (this.getOrgCode() != null && this.getOrgCode().equals(that.getOrgCode()))) &&
                    (((this.getRank() == null) && (that.getRank() == null)) ||
						(this.getRank() != null && this.getRank().equals(that.getRank()))) &&
					(((this.getRegisterNo() == null) && (that.getRegisterNo() == null)) ||
						(this.getRegisterNo() != null && this.getRegisterNo().equals(that.getRegisterNo()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					this.getOrgID() == that.getOrgID()  &&
					this.getParentOrgID() == that.getParentOrgID()  &&
					(((this.getOrgName() == null) && (that.getOrgName() == null)) ||
						(this.getOrgName() != null && this.getOrgName().equals(that.getOrgName()))) &&
					(((this.getOrgDesc() == null) && (that.getOrgDesc() == null)) ||
						(this.getOrgDesc() != null && this.getOrgDesc().equals(that.getOrgDesc()))) &&
					(((this.getOrgType() == null) && (that.getOrgType() == null)) ||
						(this.getOrgType() != null && this.getOrgType().equals(that.getOrgType()))) &&
					(((this.getOrgSubType() == null) && (that.getOrgSubType() == null)) ||
						(this.getOrgSubType() != null && this.getOrgSubType().equals(that.getOrgSubType()))) &&
					(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
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
		buf.append(rank);
		buf.append(",").append(registerNo);
		buf.append(",").append(status);
		buf.append(",").append(orgID);
		buf.append(",").append(parentOrgID);
		buf.append(",").append(orgName);
		buf.append(",").append(orgDesc);
		buf.append(",").append(orgType);
		buf.append(",").append(orgSubType);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
        buf.append(",").append(hasCustomerFlag);
		buf.append(",").append(orgCode);

		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

