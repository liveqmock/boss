package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class ProtocolDTO implements ReflectionSupport, java.io.Serializable
{
	private int customerID;
	private int productPackageID;
	private double singlePrice;
    private String  feeType ;
    private String status ;
	private String  acctitemTypeID;
	private Timestamp dateFrom;
	private Timestamp dateTo;
	private int  usedCount;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	/**
	 * @return the customerID
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
		put("customerID");
	}

	/**
	 * @return the productPackageID
	 */
	public int getProductPackageID() {
		return productPackageID;
	}

	/**
	 * @param productPackageID the productPackageID to set
	 */
	public void setProductPackageID(int productPackageID) {
		this.productPackageID = productPackageID;
		put("productPackageID");
	}

	/**
	 * @return the singlePrice
	 */
	public double getSinglePrice() {
		return singlePrice;
	}

	/**
	 * @param singlePrice the singlePrice to set
	 */
	public void setSinglePrice(double singlePrice) {
		this.singlePrice = singlePrice;
		put("singlePrice");
	}

	/**
	 * @return the feeType
	 */
	public String getFeeType() {
		return feeType;
	}

	/**
	 * @param feeType the feeType to set
	 */
	public void setFeeType(String feeType) {
		this.feeType = feeType;
		put("feeType");
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
		put("status");
	}

	/**
	 * @return the acctitemTypeID
	 */
	public String getAcctitemTypeID() {
		return acctitemTypeID;
	}

	/**
	 * @param acctitemTypeID the acctitemTypeID to set
	 */
	public void setAcctitemTypeID(String acctitemTypeID) {
		this.acctitemTypeID = acctitemTypeID;
		put("acctitemTypeID");
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
				ProtocolDTO that = (ProtocolDTO) obj;
				return
					(this.getCustomerID() == that.getCustomerID()  &&
					this.getProductPackageID() == that.getProductPackageID()  &&
					this.getSinglePrice() == that.getSinglePrice()  &&                
					(((this.getAcctitemTypeID() == null) && (that.getAcctitemTypeID() == null)) ||
							(this.getAcctitemTypeID() != null && this.getAcctitemTypeID().equals(that.getAcctitemTypeID()))) &&
                    (((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					(((this.getFeeType() == null) && (that.getFeeType() == null)) ||
						(this.getFeeType() != null && this.getFeeType().equals(that.getFeeType()))) &&      
					(((this.getDateFrom() == null) && (that.getDateFrom() == null)) ||
						(this.getDateFrom() != null && this.getDateFrom().equals(that.getDateFrom()))) &&           
					(((this.getDateTo() == null) && (that.getDateTo() == null)) ||
						(this.getDateTo() != null && this.getDateTo().equals(that.getDateTo()))) && 
					(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
					(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
						(this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod()))));
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
		buf.append(customerID);
		buf.append(",").append(productPackageID);
		buf.append(",").append(singlePrice);
        buf.append(",").append(feeType);
		buf.append(",").append(status);
		buf.append(",").append(acctitemTypeID);
		buf.append(",").append(dateFrom);
		buf.append(",").append(dateTo);
		buf.append(",").append(usedCount);
		buf.append(",").append(dtCreate);
        buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

	/**
	 * @return the dateFrom
	 */
	public Timestamp getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(Timestamp dateFrom) {
		this.dateFrom = dateFrom;
		put("dateFrom");
	}

	/**
	 * @return the dateTo
	 */
	public Timestamp getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo the dateTo to set
	 */
	public void setDateTo(Timestamp dateTo) {
		this.dateTo = dateTo;
		put("dateTo");
	}

	public int getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(int usedCount) {
		this.usedCount = usedCount;
		put("usedCount");
	}


}

