package com.dtv.oss.web.taglib.resultset;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.*;
import com.dtv.oss.web.taglib.util.*;

import com.dtv.oss.web.util.*;

import com.dtv.oss.web.taglib.util.QueryPageProp;

public class ResultSetProperty extends TagSupport{
        protected String property = null;

        public String getProperty(){
                return property;
        }
        public void setProperty(String pProperty){
                property = pProperty;
        }

        public int doStartTag() throws JspTagException {

            // checks the property is valid.
            if (property == null) {
                WebPrint.PrintErrorInfo(this.getClass().getName(),
                               "the property of the parameter is not set!");
                return (SKIP_BODY);
            }

            int iVal = -1;

            QueryPageProp beanProp = (QueryPageProp) pageContext.getRequest().getAttribute(WebKeys.CURRENT_RESULTSET_PROPERTY);
            if (beanProp == null) {
                WebPrint.PrintErrorInfo(this.getClass().getName(),
                               "the property bean of the resultset is not set!");
                return (SKIP_BODY);
            }

            property = property.toUpperCase();

            if (property.equals("FROM"))
              iVal = beanProp.getFrom();
            else if (property.equals("TO"))
              iVal = beanProp.getTo();
            else if (property.equals("PREFROM"))
              iVal = beanProp.getPreviousPageFrom();
            else if (property.equals("PRETO"))
              iVal = beanProp.getPreviousPageTo();
            else if (property.equals("NEXTFROM"))
              iVal = beanProp.getNextPageFrom();
            else if (property.equals("NEXTTO"))
              iVal = beanProp.getNextPageTo();
            else if (property.equals("FIRSTFROM"))
              iVal = beanProp.getFirstPageFrom();
            else if (property.equals("FIRSTTO"))
              iVal = beanProp.getFirstPageTo();
            else if (property.equals("LASTFROM"))
              iVal = beanProp.getLastPageFrom();
            else if (property.equals("LASTTO"))
              iVal = beanProp.getLastPageTo();
            else if (property.equals("PAGEAMOUNT"))
              iVal = beanProp.getPageAmount();
            else if (property.equals("RECORDCOUNT"))
              iVal = beanProp.getRecordCount();
            else if (property.equals("CURPAGENO"))
              iVal = beanProp.getCurPageNo();

            try {
              ResponseUtils.write(pageContext, String.valueOf(iVal));
            }
            catch (Exception e) {
              WebPrint.PrintErrorInfo(this.getClass().getName(),
                               " doStartTag" + e.getMessage());
            }

            return (SKIP_BODY);
        }


    public void release() {

        super.release();
        property = null;

    }


}