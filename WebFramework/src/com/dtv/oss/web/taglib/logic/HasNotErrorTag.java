package com.dtv.oss.web.taglib.logic;

import javax.servlet.jsp.JspException;

import com.dtv.oss.web.taglib.util.RequestUtils;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.WebKeys;

public class HasNotErrorTag extends BaseCompareTag{
    protected boolean getHopeEmptyFlag()
    {
        return true;
    }

    protected boolean condition() throws JspException {

        if (pageContext.getRequest().getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE)==null)
        {
            if (getHopeEmptyFlag()) return true;
            else return false;
        }
        else
        {
            if (getHopeEmptyFlag()) return false;
            else return true;
        }

    }


}