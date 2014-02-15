package com.dtv.oss.web.taglib.cc;
import javax.servlet.jsp.JspException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.CurrentCustomer;
import com.dtv.oss.web.taglib.logic.LoopTag;

/**
 *
 * <p>Title: BOSS</p>
 * <p>Description: The tag is extended by GridTag. It is only used for the current customer.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Digivision</p>
 * @author lhd
 * @version 1.0
 */

public class CurrentCustomerLoopTag extends LoopTag {


     /**
     * collectionType is defined as COLLECTION_ACCOUNT, COLLECTION_PROBLEM, COLLECTION_COMPLAIN and COLLECTION_JOBCARD
     * which are placed in class CurrentCustomer.
     */
      private int collectionType = 0;
      private int customerId =0;

      public void setCollectionType(String pType){
             collectionType = WebUtil.StringToInt(pType);
      }
      
      public void setCustomerId(int pId){
    	     customerId =pId;
      }

      public int doStartTag() throws JspException {

    	  int selectSAID=WebUtil.StringToInt(pageContext.getRequest().getParameter("txtServiceAccountID"));
    	  collection = CurrentCustomer.getCurrentCustomerSubCollection(pageContext.getSession(), collectionType,selectSAID);
    	  return super.doStartTag();

      }
      
    
      


    public void release() {
        super.release();
        collectionType = 0;

    }


}