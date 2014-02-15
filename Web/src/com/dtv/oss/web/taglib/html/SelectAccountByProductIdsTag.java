package com.dtv.oss.web.taglib.html;

/**
 * @author Stone Liang
 * @version 1.0
 */

import javax.servlet.jsp.JspException;
import java.util.*;

import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.CurrentCustomer;
import com.dtv.oss.web.taglib.html.SelectTag;

public class SelectAccountByProductIdsTag extends SelectTag {
    protected String mapName = null;
	protected String productIds = null;
    protected String custId = null; 

	/**
	 * @return Returns the mapName.
	 */
	public String getMapName() {
		return mapName;
	}
	/**
	 * @param mapName The mapName to set.
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	/**
	 * @return Returns the custId.
	 */
	public String getCustId() {
		return custId;
	}
	/**
	 * @param custId The custId to set.
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}
	/**
	 * @return Returns the productIds.
	 */
	public String getProductIds() {
		return productIds;
	}
	/**
	 * @param productIds The productIds to set.
	 */
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
    public int doStartTag() throws JspException {
        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");
System.out.println("****************************************************productIds = "+ productIds);       
System.out.println("****************************************************custId = "+ custId);

         if(productIds != null)
         {
          	if(mapName == null)
         		mapName = "custAccounts";
          	Map map=null;
            if(custId == null)
            	custId = String.valueOf(CurrentCustomer.getCurrentCustomerDTO(pageContext.getSession()).getCustomerID());
            map=Postern.getAccountsByProductIDs (productIds,custId);
            if (map!=null) pageContext.setAttribute(mapName, map);
System.out.println("******************************************************map = " + map);            
    	}

        WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");
        return super.doStartTag();

    }

	/**
	* Release any acquired resources.
	*/
	public void release() {
	
	    super.release();
	    mapName = null;
	    productIds = null;
	    custId = null;
	}
}