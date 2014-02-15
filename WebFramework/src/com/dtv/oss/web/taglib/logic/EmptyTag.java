package com.dtv.oss.web.taglib.logic;

import javax.servlet.jsp.JspException;

import com.dtv.oss.web.taglib.util.RequestUtils;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;

public class EmptyTag extends BaseCompareTag {

	//current customer session name
    public static final String CURRENT_CUSTOMER_SESSION_NAME = "ScndKeys.CurrentCustomer";
    
    //�ͻ���,�ͻ���ҵ���ʻ�ID
    public static final String CURRENT_CUSTOMER_SAID_SESSION_NAME = "SAIDSession";
    
    //�ͻ���,�ͻ�ID
    public static final String CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME = "PID";
    
    //�ͻ���,�ͻ���ҵ���ʻ��ĸ���
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
      
      //�ͻ���CURRENT_CUSTOMER_SESSION_NAMEΪ��ʱ��ɾ��session����CUSTOMERID��SAID��session����CUSTOMERID��SAID�����ڿ���ҵ���ʻ���ʾ��أ������com.dtv.oss.web.util.CurrentCustomerһ��ʹ�á�
	  if(pageContext.getSession().getAttribute(CURRENT_CUSTOMER_SESSION_NAME)==null){
		    //�ͻ���,�ͻ���ҵ���ʻ��ĸ���(��ʾҵ���ʻ��б��һ��ҵ���ʻ�ʱ�õ�)
	   		pageContext.getSession().removeAttribute(CURRENT_CUSTOMER_SACOUNT_SESSION_NAME); 
	   		//�ͻ���,�ͻ���ҵ���ʻ�ID(��ʾҵ���ʻ��б��һ��ҵ���ʻ�ʱ�õ�)
	   		pageContext.getSession().removeAttribute(CURRENT_CUSTOMER_SAID_SESSION_NAME);        
	   		//�ͻ���,�ͻ�ID(��ʾҵ���ʻ��б��һ��ҵ���ʻ�ʱ�õ�)
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