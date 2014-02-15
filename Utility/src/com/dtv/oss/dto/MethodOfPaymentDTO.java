package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class MethodOfPaymentDTO implements ReflectionSupport, java.io.Serializable
{
	private String name;
	private String description;
	private String status;
	private String payType;
	private String cashFlag;
	private int partnerID;
	private int mopID;
	private String accountflag;
	private String paymentflag; 
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	private String csiTypeList;


	public MethodOfPaymentDTO()
	{
	}

	public MethodOfPaymentDTO(String cashFlag,String paymentflag,String accountflag, String name, String description, String status, String payType, int partnerID, int mopID, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setPaymentflag(paymentflag);
		setAccountflag(accountflag);
		setName(name);
		setDescription(description);
		setStatus(status);
		setPayType(payType);
		setPartnerID(partnerID);
		setMopID(mopID);
		setCashFlag(cashFlag);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
	}
	 
	/**
	 * @return Returns the csiTypeList.
	 */
	public String getCsiTypeList() {
		return csiTypeList;
	}
	/**
	 * @param csiTypeList The csiTypeList to set.
	 */
	public void setCsiTypeList(String csiTypeList) {
		this.csiTypeList = csiTypeList;
		put("csiTypeList");
	}
	public void setCashFlag(String cashFlag)
	{
		this.cashFlag = cashFlag;
		put("cashFlag");
	}

	public String getCashFlag()
	{
		return cashFlag;
	}
	public void setPaymentflag(String paymentflag)
	{
		this.paymentflag = paymentflag;
		put("paymentflag");
	}

	public String getPaymentflag()
	{
		return paymentflag;
	}
	
	public void setAccountflag(String accountflag)
	{
		this.accountflag = accountflag;
		 
		put("accountflag");
	}

	public String getAccountflag()
	{
		return accountflag;
	}
	public void setName(String name)
	{
		this.name = name;
		put("name");
	}

	public String getName()
	{
		return name;
	}

	public void setDescription(String description)
	{
		this.description = description;
		put("description");
	}

	public String getDescription()
	{
		return description;
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

	public void setPayType(String payType)
	{
		this.payType = payType;
		put("payType");
	}

	public String getPayType()
	{
		return payType;
	}

	public void setPartnerID(int partnerID)
	{
		this.partnerID = partnerID;
		put("partnerID");
	}

	public int getPartnerID()
	{
		return partnerID;
	}

	public void setMopID(int mopID)
	{
		this.mopID = mopID;
		//put("mopID");
	}

	public int getMopID()
	{
		return mopID;
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
				MethodOfPaymentDTO that = (MethodOfPaymentDTO) obj;
				return
				(((this.getPaymentflag() == null) && (that.getPaymentflag() == null)) ||
						(this.getPaymentflag() != null && this.getPaymentflag().equals(that.getPaymentflag()))) &&
					(((this.getAccountflag() == null) && (that.getAccountflag() == null)) ||
						(this.getAccountflag() != null && this.getAccountflag().equals(that.getAccountflag()))) &&
					(((this.getName() == null) && (that.getName() == null)) ||
						(this.getName() != null && this.getName().equals(that.getName()))) &&
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					(((this.getPayType() == null) && (that.getPayType() == null)) ||
						(this.getPayType() != null && this.getPayType().equals(that.getPayType()))) &&
					(((this.getCashFlag() == null) && (that.getCashFlag() == null)) ||
						(this.getCashFlag() != null && this.getCashFlag().equals(that.getCashFlag()))) &&	
						(((this.getCsiTypeList() == null) && (that.getCsiTypeList() == null)) ||
								(this.getCsiTypeList() != null && this.getCsiTypeList().equals(that.getCsiTypeList()))) &&		
					this.getPartnerID() == that.getPartnerID()  &&
					this.getMopID() == that.getMopID()  &&
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
		buf.append(name);
		buf.append(",").append(accountflag);
		buf.append(",").append(paymentflag);
		buf.append(",").append(description);
		buf.append(",").append(status);
		buf.append(",").append(payType);
		buf.append(",").append(partnerID);
		buf.append(",").append(mopID);
		buf.append(",").append(csiTypeList);
		buf.append(",").append(cashFlag);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

