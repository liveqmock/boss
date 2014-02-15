package com.dtv.oss.web.taglib.resultset;

/**
 * <p>Title: BOSS P4</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.*;

import java.util.*;
import com.dtv.oss.web.taglib.util.*;

import com.dtv.oss.web.util.*;

import com.dtv.oss.web.taglib.util.QueryPageProp;

public class HasResultSet extends BodyTagSupport{

        public int doStartTag() throws JspException {

            QueryPageProp beanProp = (QueryPageProp) pageContext.getRequest().getAttribute(WebKeys.CURRENT_RESULTSET_PROPERTY);
            if (beanProp == null)
                return (SKIP_BODY);
            else
                return (EVAL_BODY_BUFFERED);
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

    public void release() {

        super.release();


    }

}