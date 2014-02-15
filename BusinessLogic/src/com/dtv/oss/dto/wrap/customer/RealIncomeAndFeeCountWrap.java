/**
 * 
 */
package com.dtv.oss.dto.wrap.customer;

/**
 * @author 240910y
 *
 */
public class RealIncomeAndFeeCountWrap implements java.io.Serializable{
	private String acctItemTypeID;
	private double value;
	private int recordCount;
	/**
	 * @return the acctItemTypeID
	 */
	public String getAcctItemTypeID() {
		return acctItemTypeID;
	}
	/**
	 * @param acctItemTypeID the acctItemTypeID to set
	 */
	public void setAcctItemTypeID(String acctItemTypeID) {
		this.acctItemTypeID = acctItemTypeID;
	}
	/**
	 * @return the recordCount
	 */
	public int getRecordCount() {
		return recordCount;
	}
	/**
	 * @param recordCount the recordCount to set
	 */
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
}
