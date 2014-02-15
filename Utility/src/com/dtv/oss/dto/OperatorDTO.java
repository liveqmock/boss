package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class OperatorDTO implements ReflectionSupport, java.io.Serializable
{
	private String status;
	private int operatorID;
	private String operatorName;
	private String operatorDesc;
	private String loginID;
	private String loginPwd;
	private int orgID;
	 
	private int levelID;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	private String internalUserFlag;


	 

	 

	/**
	 * @return Returns the internalUserFlag.
	 */
	public String getInternalUserFlag() {
		return internalUserFlag;
	}
	/**
	 * @param internalUserFlag The internalUserFlag to set.
	 */
	public void setInternalUserFlag(String internalUserFlag) {
		this.internalUserFlag = internalUserFlag;
		put("internalUserFlag");
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

	public void setOperatorID(int operatorID)
	{
		this.operatorID = operatorID;
		//put("operatorID");
	}

	public int getOperatorID()
	{
		return operatorID;
	}

	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
		put("operatorName");
	}

	public String getOperatorName()
	{
		return operatorName;
	}

	public void setOperatorDesc(String operatorDesc)
	{
		this.operatorDesc = operatorDesc;
		put("operatorDesc");
	}

	public String getOperatorDesc()
	{
		return operatorDesc;
	}

	public void setLoginID(String loginID)
	{
		this.loginID = loginID;
		put("loginID");
	}

	public String getLoginID()
	{
		return loginID;
	}

	public void setLoginPwd(String loginPwd)
	{
		this.loginPwd = loginPwd;
		put("loginPwd");
	}

	public String getLoginPwd()
	{
		return loginPwd;
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

	 

	public void setLevelID(int levelID)
	{
		this.levelID = levelID;
		put("levelID");
	}

	public int getLevelID()
	{
		return levelID;
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
				OperatorDTO that = (OperatorDTO) obj;
				return
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					this.getOperatorID() == that.getOperatorID()  &&
					(((this.getOperatorName() == null) && (that.getOperatorName() == null)) ||
						(this.getOperatorName() != null && this.getOperatorName().equals(that.getOperatorName()))) &&
					(((this.getOperatorDesc() == null) && (that.getOperatorDesc() == null)) ||
						(this.getOperatorDesc() != null && this.getOperatorDesc().equals(that.getOperatorDesc()))) &&
					(((this.getLoginID() == null) && (that.getLoginID() == null)) ||
						(this.getLoginID() != null && this.getLoginID().equals(that.getLoginID()))) &&
					(((this.getLoginPwd() == null) && (that.getLoginPwd() == null)) ||
						(this.getLoginPwd() != null && this.getLoginPwd().equals(that.getLoginPwd()))) &&
					this.getOrgID() == that.getOrgID()  &&
					 
					this.getLevelID() == that.getLevelID()  &&
					(((this.getInternalUserFlag() == null) && (that.getInternalUserFlag() == null)) ||
							(this.getInternalUserFlag() != null && this.getInternalUserFlag().equals(that.getInternalUserFlag()))) &&
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
		buf.append(status);
		buf.append(",").append(operatorID);
		buf.append(",").append(operatorName);
		buf.append(",").append(internalUserFlag);
		buf.append(",").append(loginID);
		buf.append(",").append(loginPwd);
		buf.append(",").append(orgID);
		buf.append(",").append(orgID);
		buf.append(",").append(levelID);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

