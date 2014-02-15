package com.dtv.oss.web.flow;
/**
 * 下载 流转,如果有异常,转入默认1,没有异常进入9.
 */

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.web.exception.FlowHandlerException;
import com.dtv.oss.web.util.WebKeys;

public class DownloadFlowHandler implements FlowHandler{
	private boolean isDown=false;
    public void doStart(HttpServletRequest request) {
    	String val2=request.getParameter(WebKeys.ISDOWNPARANAME);
    	if("true".equals(val2)){
    		isDown=true;
    	}
    }

    public String processFlow(HttpServletRequest request) throws FlowHandlerException {
    	LogUtility.log(this.getClass(), LogLevel.DEBUG, "processFlow.isDown★★★★★★★★★"+isDown);
    	LogUtility.log(this.getClass(), LogLevel.DEBUG, "processFlow.isDown★★★★★★★★★"+request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE));
        if (request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE)!=null){
        	return "1";
        }else if(isDown){
    		return "9";
    	}
		return "1";
    }

    public void doEnd(HttpServletRequest request) {};

}
