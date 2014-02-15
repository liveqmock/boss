package com.dtv.oss.web.taglib.html;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.*;
import com.dtv.oss.web.taglib.util.*;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;

public class GetQueryPropTag extends TagSupport {

        private String fromMethodName = "From";
        private String toMethodName = "To";

        private String sizeMethodName = "Size";

        public String getSizeMethodName(){
                return sizeMethodName;
        }
        public void setSizeMethodName(String pName){
                sizeMethodName = pName;
        }

        private String requestObjectName = null;

        public String getRequestObjectName()
        {
            return requestObjectName;
        }
        public void setRequestObjectName(String pName)
        {
            requestObjectName = pName;

        }

        private String beanName = null;

        public String getBeanName()
        {
            return beanName;
        }
        public void setBeanName(String pName)
        {
            beanName = pName;

        }

        public int doStartTag() throws JspTagException {
            if ((requestObjectName==null)||(beanName==null))
            {
                WebPrint.PrintErrorInfo(this.getClass().getName(),  "requestObjectName and beanName must be set");
                return SKIP_BODY;
            }

            Object RepCmd = (Object)pageContext.getRequest().getAttribute(requestObjectName);

            QueryPageProp pagebean = (QueryPageProp)pageContext.getAttribute(beanName);
            if (pagebean==null)
            {
                WebPrint.PrintErrorInfo(this.getClass().getName(),  "can not get bean --"+ beanName);
                return SKIP_BODY;
            }

            int iPageSize=10;
            Integer iFrom=null;
            Integer iTo=null;

            if (RepCmd!=null)
            {
                //get size
                Object obj = WebUtil.excuteMethod(RepCmd,WebUtil.Name2GetMethod(sizeMethodName));
                if (obj!=null)
                {
                     pagebean.setRecordCount(Integer.valueOf(obj.toString()).intValue());
                }

                //get from
                try
                {
                    iFrom = (Integer)WebUtil.excuteMethod(RepCmd, WebUtil.Name2GetMethod(fromMethodName));
                }
                catch(Exception e)
                {
                    WebPrint.PrintErrorInfo("GetQueryPropTag", "get invoke method getFrom exception: "+e);
                }


                //get to
                try
                {
                    iTo = (Integer)WebUtil.excuteMethod(RepCmd, WebUtil.Name2GetMethod(toMethodName));
                }
                catch(Exception e)
                {
                    WebPrint.PrintErrorInfo("GetQueryPropTag", "get invoke method getTo exception: "+e);
                }

                if ((iFrom!=null)&&(iTo!=null))
                {
                     iPageSize= iTo.intValue()-iFrom.intValue()+1;
                     pagebean.setFrom(iFrom.intValue());
                     pagebean.setPageSize(iPageSize);
                }

            }


            return SKIP_BODY;

        }

    public void release() {
        super.release();

        fromMethodName = "From";
        toMethodName = "To";
        sizeMethodName = "Size";
        requestObjectName = null;
        beanName = null;

    }

}