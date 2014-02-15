package com.dtv.oss.web.flow;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.web.exception.FlowHandlerException;
import com.dtv.oss.web.util.WebKeys;

public class RequestAttributeClassFlowHandler implements FlowHandler{
    public void doStart(HttpServletRequest request) {};

    public String processFlow(HttpServletRequest request) throws FlowHandlerException {
        if (request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE)!=null) 
        	return "2";
        else {
        	if (request.getAttribute(WebKeys.REQUEST_ATTRIBUTE)!=null)
        		return "1";
        	else 
        		return "0";  	
        }

    }

    public void doEnd(HttpServletRequest request) {};
}
