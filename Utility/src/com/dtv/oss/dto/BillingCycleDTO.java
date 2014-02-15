package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class BillingCycleDTO implements ReflectionSupport, java.io.Serializable
{
	private int id;
	private int billingCycleTypeID;
	private String name;
	private String description;
	private String cType;
	private Timestamp invoiceDueDate;
	private Timestamp rentCycleBTime;
	private Timestamp endInvoicingDate;
	 
	/**
	 * @return Returns the cType.
	 */
	public String getCType() {
		return cType;
	}
	/**
	 * @param type The cType to set.
	 */
	public void setCType(String cType) {
		this.cType = cType;
		put("cType");
	}
	/**
	 * @return Returns the invoiceDueDate.
	 */
	public Timestamp getInvoiceDueDate() {
		return invoiceDueDate;
	}
	/**
	 * @param invoiceDueDate The invoiceDueDate to set.
	 */
	public void setInvoiceDueDate(Timestamp invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
		put("invoiceDueDate");
	}
	private Timestamp rentCycleETime;
	private Timestamp otherCycleBTime;
	private Timestamp otherCycleETime;
    private String status;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	/**
	 * @return Returns the endInvoicingDate.
	 */
	public Timestamp getEndInvoicingDate() {
		return endInvoicingDate;
	}
	/**
	 * @param endInvoicingDate The endInvoicingDate to set.
	 */
	public void setEndInvoicingDate(Timestamp endInvoicingDate) {
		this.endInvoicingDate = endInvoicingDate;
		put("endInvoicingDate");
	}
	public BillingCycleDTO()
	{
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

	public void setBillingCycleTypeID(int billingCycleTypeID)
	{
		this.billingCycleTypeID = billingCycleTypeID;
		put("billingCycleTypeID");
	}

	public int getBillingCycleTypeID()
	{
		return billingCycleTypeID;
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




	 
	public void setRentCycleBTime(Timestamp rentCycleBTime)
	{
		this.rentCycleBTime = rentCycleBTime;
		put("rentCycleBTime");
	}

	public Timestamp getRentCycleBTime()
	{
		return rentCycleBTime;
	}

	public void setRentCycleETime(Timestamp rentCycleETime)
	{
		this.rentCycleETime = rentCycleETime;
		put("rentCycleETime");
	}

	public Timestamp getRentCycleETime()
	{
		return rentCycleETime;
	}

	public void setOtherCycleBTime(Timestamp otherCycleBTime)
	{
		this.otherCycleBTime = otherCycleBTime;
		put("otherCycleBTime");
	}

	public Timestamp getOtherCycleBTime()
	{
		return otherCycleBTime;
	}

	public void setOtherCycleETime(Timestamp otherCycleETime)
	{
		this.otherCycleETime = otherCycleETime;
		put("otherCycleETime");
	}

	public Timestamp getOtherCycleETime()
	{
		return otherCycleETime;
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
				BillingCycleDTO that = (BillingCycleDTO) obj;
				return
					this.getId() == that.getId()  &&
					this.getBillingCycleTypeID() == that.getBillingCycleTypeID()  &&
					(((this.getName() == null) && (that.getName() == null)) ||
						(this.getName() != null && this.getName().equals(that.getName()))) &&
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
					(((this.getCType() == null) && (that.getCType() == null)) ||
						(this.getCType() != null && this.getCType().equals(that.getCType()))) &&
					(((this.getInvoiceDueDate() == null) && (that.getInvoiceDueDate() == null)) ||
						(this.getInvoiceDueDate() != null && this.getInvoiceDueDate().equals(that.getInvoiceDueDate()))) &&		
					(((this.getRentCycleBTime() == null) && (that.getRentCycleBTime() == null)) ||
						(this.getRentCycleBTime() != null && this.getRentCycleBTime().equals(that.getRentCycleBTime()))) &&
					(((this.getRentCycleETime() == null) && (that.getRentCycleETime() == null)) ||
						(this.getRentCycleETime() != null && this.getRentCycleETime().equals(that.getRentCycleETime()))) &&
					(((this.getOtherCycleBTime() == null) && (that.getOtherCycleBTime() == null)) ||
						(this.getOtherCycleBTime() != null && this.getOtherCycleBTime().equals(that.getOtherCycleBTime()))) &&
					(((this.getOtherCycleETime() == null) && (that.getOtherCycleETime() == null)) ||
						(this.getOtherCycleETime() != null && this.getOtherCycleETime().equals(that.getOtherCycleETime()))) &&

					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
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
		buf.append(id);
		buf.append(",").append(billingCycleTypeID);
		buf.append(",").append(name);
		buf.append(",").append(description);
		buf.append(",").append(cType);
		buf.append(",").append(invoiceDueDate);
		buf.append(",").append(rentCycleBTime);
		buf.append(",").append(rentCycleETime);
		buf.append(",").append(otherCycleBTime);
		buf.append(",").append(otherCycleETime);

		buf.append(",").append(status);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

