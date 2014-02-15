/**
 * 
 */
package com.dtv.oss.dto.custom;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.CustomerBillingRuleDTO;

/**
 * @author 240910y
 *
 */
public class CustCustomerBillingRuleDTO extends CustomerBillingRuleDTO {

	/**
	 * 
	 */
	public CustCustomerBillingRuleDTO() {
		// TODO Auto-generated constructor stub
	}
	private int productID;
	private int packageID;
	/**
	 * @return the packageID
	 */
	public int getPackageID() {
		return packageID;
	}
	/**
	 * @param packageID the packageID to set
	 */
	public void setPackageID(int packageID) {
		this.packageID = packageID;
	}
	/**
	 * @return the productID
	 */
	public int getProductID() {
		return productID;
	}
	/**
	 * @param productID the productID to set
	 */
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public int hashCode()
	{
		return toString().hashCode();
	}
	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				CustCustomerBillingRuleDTO that = (CustCustomerBillingRuleDTO) obj;
				return super.equals(obj)&&
				this.getProductID() == that.getProductID() &&
				this.getPackageID() == that.getPackageID() ;
			}
		}
		return false;
	}
	public String toString()
	{	StringBuffer buf = new StringBuffer(256);
		buf.append(super.toString());
		buf.append(",").append(productID);
		buf.append(",").append(packageID);
		return buf.toString();
	}
}
