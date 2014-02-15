package com.dtv.oss.web.taglib.html;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.*;

import javax.servlet.jsp.PageContext;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.taglib.util.ResponseUtils;
/**
 *
 * <p>Title: BOSS</p>
 * <p>Description: get the class of exception from request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE) and then output the message of the exception.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Digivision</p>
 * @author lhd
 * @version 1.0
 */

public class GetErrorMessageTag extends TagSupport{

    public int doStartTag() throws JspTagException {

        try
        {

            Throwable Val = (Throwable)pageContext.getRequest().getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE);
            if (Val != null)
                ResponseUtils.write(pageContext, Val.getMessage());
            else
                WebPrint.PrintTagDebugInfo(this.getClass().getName(), "without exception happened");

        }
        catch(Exception e)
        {
            WebPrint.PrintErrorInfo(this.getClass().getName(),  "doStartTag exception "+e.getMessage());
        }

        return SKIP_BODY;
    }

}