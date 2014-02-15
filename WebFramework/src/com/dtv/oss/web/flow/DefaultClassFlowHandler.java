package com.dtv.oss.web.flow;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.flow.FlowHandler;
import com.dtv.oss.web.exception.FlowHandlerException;

public class DefaultClassFlowHandler implements FlowHandler{
    public void doStart(HttpServletRequest request) {};

    public String processFlow(HttpServletRequest request) throws FlowHandlerException {
        if (request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE)!=null) return "1";

        /*
        Integer errorCode = (Integer)request.getAttribute(WebKeys.PAGE_ERROR_CODE_REQUEST_ATTRIBUTE);
        if (errorCode == null) return "0"; //normal process
        if (errorCode.intValue() != 0) return "1"; //error process
        */

        return "0"; //normal process
    }

    public void doEnd(HttpServletRequest request) {};
}
