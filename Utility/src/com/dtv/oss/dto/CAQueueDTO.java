package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CAQueueDTO implements ReflectionSupport, java.io.Serializable
{
	private String scnr;
	private String stbnr;
	private int productID;
	private String oldScnr;
	private String oldStbnr;
	private int oldProductId;
	private String status;
	private Timestamp createTime;
	private Timestamp doneTime;
	private int hostID;
	
	private int condID;
	private int queueID;
	private int eventID;
	private int eventClass;
	private int commandID;
	private int customerID;
	private int opiID;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	private String entitlement;


	public CAQueueDTO()
	{
	}

	 

	public void setScnr(String scnr)
	{
		this.scnr = scnr;
		put("scnr");
	}

	public String getScnr()
	{
		return scnr;
	}
	public void setEntitlement(String entitlement)
	{
		this.entitlement = entitlement;
		put("entitlement");
	}

	public String getEntitlement()
	{
		return entitlement;
	}
	/**
	 * @return Returns the opiID.
	 */
	public int getOpiID() {
		return opiID;
	}
	/**
	 * @param opiID The opiID to set.
	 */
	public void setOpiID(int opiID) {
		this.opiID = opiID;
		put("opiID");
	}

	public void setStbnr(String stbnr)
	{
		this.stbnr = stbnr;
		put("stbnr");
	}

	public String getStbnr()
	{
		return stbnr;
	}

	public void setProductID(int productID)
	{
		this.productID = productID;
		put("productID");
	}

	public int getProductID()
	{
		return productID;
	}

	public void setOldScnr(String oldScnr)
	{
		this.oldScnr = oldScnr;
		put("oldScnr");
	}

	public String getOldScnr()
	{
		return oldScnr;
	}

	public void setOldStbnr(String oldStbnr)
	{
		this.oldStbnr = oldStbnr;
		put("oldStbnr");
	}

	public String getOldStbnr()
	{
		return oldStbnr;
	}

	public void setOldProductId(int oldProductId)
	{
		this.oldProductId = oldProductId;
		put("oldProductId");
	}

	public int getOldProductId()
	{
		return oldProductId;
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

	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
		put("createTime");
	}

	public Timestamp getCreateTime()
	{
		return createTime;
	}

	public void setDoneTime(Timestamp doneTime)
	{
		this.doneTime = doneTime;
		put("doneTime");
	}

	public Timestamp getDoneTime()
	{
		return doneTime;
	}

	public void setHostID(int hostID)
	{
		this.hostID = hostID;
		put("hostID");
	}

	public int getHostID()
	{
		return hostID;
	}

	public void setCondID(int condID)
	{
		this.condID = condID;
		put("condID");
	}

	public int getCondID()
	{
		return condID;
	}

	public void setQueueID(int queueID)
	{
		this.queueID = queueID;
		//put("queueID");
	}

	public int getQueueID()
	{
		return queueID;
	}

	public void setEventID(int eventID)
	{
		this.eventID = eventID;
		put("eventID");
	}

	public int getEventID()
	{
		return eventID;
	}

	public void setEventClass(int eventClass)
	{
		this.eventClass = eventClass;
		put("eventClass");
	}

	public int getEventClass()
	{
		return eventClass;
	}

	public void setCommandID(int commandID)
	{
		this.commandID = commandID;
		put("commandID");
	}

	public int getCommandID()
	{
		return commandID;
	}

	public void setCustomerID(int customerID)
	{
		this.customerID = customerID;
		put("customerID");
	}

	public int getCustomerID()
	{
		return customerID;
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
				CAQueueDTO that = (CAQueueDTO) obj;
				return
					(((this.getScnr() == null) && (that.getScnr() == null)) ||
						(this.getScnr() != null && this.getScnr().equals(that.getScnr()))) &&
					(((this.getStbnr() == null) && (that.getStbnr() == null)) ||
						(this.getStbnr() != null && this.getStbnr().equals(that.getStbnr()))) &&
					this.getProductID() == that.getProductID()  &&
					(((this.getOldScnr() == null) && (that.getOldScnr() == null)) ||
						(this.getOldScnr() != null && this.getOldScnr().equals(that.getOldScnr()))) &&
					(((this.getOldStbnr() == null) && (that.getOldStbnr() == null)) ||
						(this.getOldStbnr() != null && this.getOldStbnr().equals(that.getOldStbnr()))) &&
					(((this.getEntitlement() == null) && (that.getEntitlement() == null)) ||
						(this.getEntitlement() != null && this.getEntitlement().equals(that.getEntitlement()))) &&	
					this.getOldProductId() == that.getOldProductId()  &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					(((this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
						(this.getCreateTime() != null && this.getCreateTime().equals(that.getCreateTime()))) &&
					(((this.getDoneTime() == null) && (that.getDoneTime() == null)) ||
						(this.getDoneTime() != null && this.getDoneTime().equals(that.getDoneTime()))) &&
					this.getHostID() == that.getHostID()  &&
					this.getCondID() == that.getCondID()  &&
					this.getOpiID() == that.getOpiID()  &&
					this.getQueueID() == that.getQueueID()  &&
					this.getEventID() == that.getEventID()  &&
					this.getEventClass() == that.getEventClass()  &&
					this.getCommandID() == that.getCommandID()  &&
					this.getCustomerID() == that.getCustomerID()  &&
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
		buf.append(scnr);
		buf.append(",").append(stbnr);
		buf.append(",").append(productID);
		buf.append(",").append(oldScnr);
		buf.append(",").append(oldStbnr);
		buf.append(",").append(oldProductId);
		buf.append(",").append(status);
		buf.append(",").append(createTime);
		buf.append(",").append(doneTime);
		buf.append(",").append(hostID);
		buf.append(",").append(condID);
		buf.append(",").append(queueID);
		buf.append(",").append(eventID);
		buf.append(",").append(eventClass);
		buf.append(",").append(commandID);
		buf.append(",").append(customerID);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(entitlement);
		buf.append(",").append(opiID);
		return buf.toString();
	}




	private java.util.Map map = new java.util.HashMap();


  public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }







}

