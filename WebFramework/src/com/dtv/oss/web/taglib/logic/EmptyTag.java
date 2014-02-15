package com.dtv.oss.web.taglib.logic;

import javax.servlet.jsp.JspException;

import com.dtv.oss.web.taglib.util.RequestUtils;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;

public class EmptyTag extends BaseCompareTag {

	//current customer session name
    public static final String CURRENT_CUSTOMER_SESSION_NAME = "ScndKeys.CurrentCustomer";
    
    //客户树,客户下业务帐户ID
    public static final String CURRENT_CUSTOMER_SAID_SESSION_NAME = "SAIDSession";
    
    //客户树,客户ID
    public static final String CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME = "PID";
    
    //客户树,客户下业务帐户的个数
    public static final String CURRENT_CUSTOMER_SACOUNT_SESSION_NAME = "AllNumberSA";
    
  protected boolean getHopeEmptyFlag()
  {
      return true;
  }

  protected boolean condition() throws JspException {
      Object value = null;

      if (this.name!=null)
      {
          value = RequestUtils.lookup(pageContext, name, scope);
          if ((this.property!=null)&&(value!=null))
            value = WebUtil.excuteMethod(value, this.property);
      }
      else if (this.parameter!=null)
      {
          value = pageContext.getRequest().getParameter(this.parameter);
      }
      else
      {
            JspException e = new JspException("Either of name and parameter must be set.");
            throw e;
      }
      
      //客户树CURRENT_CUSTOMER_SESSION_NAME为空时，删除session属性CUSTOMERID和SAID。session属性CUSTOMERID和SAID是用于控制业务帐户显示相关，配合类com.dtv.oss.web.util.CurrentCustomer一起使用。
	  if(pageContext.getSession().getAttribute(CURRENT_CUSTOMER_SESSION_NAME)==null){
		    //客户树,客户下业务帐户的个数(显示业务帐户列表和一个业务帐户时用到)
	   		pageContext.getSession().removeAttribute(CURRENT_CUSTOMER_SACOUNT_SESSION_NAME); 
	   		//客户树,客户下业务帐户ID(显示业务帐户列表和一个业务帐户时用到)
	   		pageContext.getSession().removeAttribute(CURRENT_CUSTOMER_SAID_SESSION_NAME);        
	   		//客户树,客户ID(显示业务帐户列表和一个业务帐户时用到)
	   		pageContext.getSession().removeAttribute(CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME);  
	  }
	  
      if (getHopeEmptyFlag())
      {
          if (value==null) return true;
          else return false;
      }
      else
      {
          if (value!=null) return true;
          else{
        	  return false;         
          }
      }
  }

}