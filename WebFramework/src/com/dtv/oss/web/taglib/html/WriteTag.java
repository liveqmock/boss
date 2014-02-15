package com.dtv.oss.web.taglib.html;



import java.math.BigDecimal;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.*;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.taglib.util.RequestUtils;

public class WriteTag extends BodyTagSupport {

        protected Object value = null;

	protected String name = null;

	public String getName(){
		return name;
	}
	public void setName(String pName){
		name = pName;
	}

	protected String property = null;

	public String getProperty(){
		return property;
	}
        public void setProperty(String pProperty){
        	property = pProperty;
        }

        /**
         * The scope to search for the bean named by the name property, or
         * "any scope" if null.
         */
        protected String scope = null;

        public String getScope() {
            return (this.scope);
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public void PrintInHtml() throws JspException{
            if (value!=null){
            	if(value instanceof Double){
            		//解决“科学记数表示法“的问题
            		BigDecimal big = new BigDecimal(((Double)value).doubleValue());
            		ResponseUtils.write(pageContext,big.setScale(2,BigDecimal.ROUND_HALF_EVEN).toString());
            	}
            	else
                ResponseUtils.write(pageContext, value.toString());
            }
            //---------------DELETE BY YANGCHEN 2006/01/05---------------START--------------
            //else
            //  WebPrint.PrintDebugInfo("WriteTag", "property "+property+" does not exist or equals null !");
            //---------------DELETE BY YANGCHEN 2006/01/05--------------- END--------------
        }

        public int doStartTag() throws JspException {
          if (name!=null)
          {
            Object objSource = RequestUtils.lookup(pageContext, name, scope);

            if (objSource != null)
            {
              try
              {
                value = WebUtil.excuteMethod(objSource,
                                             WebUtil.Name2GetMethod(property));
              }
              catch (Exception e)
              {
                WebPrint.PrintErrorInfo("WriteTag", e.getMessage());
              }
            }
          }

          if (value==null)
          {
            //---------------DELETE BY YANGCHEN 2006/01/05---------------START--------------
            //WebPrint.PrintErrorInfo(this.getClass().getName(), "Attribute " + property + " does not exist!");
            //---------------DELETE BY YANGCHEN 2006/01/05--------------- END--------------
             return (SKIP_BODY);
          }

          //output to html page
          PrintInHtml();

          return (EVAL_BODY_BUFFERED);
        }



    public void release() {

        super.release();
        name = null;
        property = null;
        value = null;
        scope = null;
    }

}
