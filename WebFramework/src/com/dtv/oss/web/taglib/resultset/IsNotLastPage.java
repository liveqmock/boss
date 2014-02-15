package com.dtv.oss.web.taglib.resultset;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import com.dtv.oss.web.util.*;

import com.dtv.oss.web.taglib.util.QueryPageProp;

public class IsNotLastPage extends BodyTagSupport{

        public int doStartTag() throws JspException
        {

            QueryPageProp beanProp = (QueryPageProp) pageContext.getRequest().getAttribute(WebKeys.CURRENT_RESULTSET_PROPERTY);
            if (beanProp == null) {
                WebPrint.PrintErrorInfo(this.getClass().getName(),
                             "the property bean of the resultset is not set!");
                return (SKIP_BODY);
            }

            if (!beanProp.isLastPage()) return (EVAL_BODY_BUFFERED);
              else return (SKIP_BODY);

        }


        public int doAfterBody() throws JspException {

            WebPrint.PrintTagDebugInfo(this.getClass().getName(),"doAfterBody enter ...");

            if (bodyContent != null)
            {
                try
                {
                    JspWriter out = pageContext.getOut();
                    if (out instanceof BodyContent)
                      out = ((BodyContent) out).getEnclosingWriter();
                    out.println(bodyContent.getString());
                }
                catch(Exception e)
                {
                    WebPrint.PrintErrorInfo(this.getClass().getName(),e.getMessage());
                }
                bodyContent.clearBody();
            }

            WebPrint.PrintTagDebugInfo(this.getClass().getName(),"doAfterBody return.");

            return (SKIP_BODY);

        }

}