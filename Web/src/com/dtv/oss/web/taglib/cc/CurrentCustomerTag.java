package com.dtv.oss.web.taglib.cc;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.*;

import com.dtv.oss.web.taglib.util.ResponseUtils;

import com.dtv.oss.util.Postern;
import com.dtv.oss.dto.CustomerDTO;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.CurrentCustomer;

public class CurrentCustomerTag extends BodyTagSupport {

	private String property = null;

	public String getProperty(){
		return property;
	}
        public void setProperty(String pProperty){
        	property = pProperty;
        }

        private String typeName = null;

        public String getTypeName(){
                return typeName;
        }
        public void setTypeName(String pTypeName){
                typeName = pTypeName;
        }


        public int doStartTag() throws JspException {
            CustomerDTO dtoCustomer = CurrentCustomer.getCurrentCustomerDTO(pageContext.getSession());
            if (dtoCustomer==null)
            {
                WebPrint.PrintErrorInfo(this.getClass().getName(), "current customer dto is null");
                return SKIP_BODY;
            }

            Object value = WebUtil.excuteMethod(dtoCustomer, WebUtil.Name2GetMethod(property));
            if (value==null)
            {
                WebPrint.PrintErrorInfo(this.getClass().getName(), "can not get property --"+property);
                return SKIP_BODY;
            }

            if (WebUtil.StringHaveContent(typeName))
            {
                if (value instanceof String)
                {
                    String strVal = (String)value;
                    value = Postern.getHashValueByNameKey(typeName, strVal);
                }
                else
                  WebPrint.PrintErrorInfo(this.getClass().getName(), "the value of typeName --"+typeName+" is invalid");
            }
            /*
            if (WebUtil.StringHaveContent(style))
            {
                            //bit0=1 denotes chars of return which will be erased
                            if ((WebUtil.StringToInt(style) & 1)!=0)
                              sVal = CommonUtils.RemoveAwayReturn(sVal);

            }*/

            ResponseUtils.write(pageContext, value.toString());

            return (SKIP_BODY);
        }


        public void release() {

            super.release();
            typeName = null;
            property = null;

        }


}
