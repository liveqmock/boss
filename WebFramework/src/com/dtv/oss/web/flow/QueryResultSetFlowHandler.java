package com.dtv.oss.web.flow;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.flow.FlowHandler;
import com.dtv.oss.web.exception.FlowHandlerException;
import com.dtv.oss.web.taglib.util.QueryPageProp;

public class QueryResultSetFlowHandler implements FlowHandler{
    public void doStart(HttpServletRequest request) {};

    public String processFlow(HttpServletRequest request) throws FlowHandlerException {
      QueryPageProp pageProp = (QueryPageProp) request.getAttribute(WebKeys.CURRENT_RESULTSET_PROPERTY);
      int iRecordCount = pageProp.getRecordCount();
      if (iRecordCount <= 0)
      {
        return "0";
      }
      else
      {
        return "1";
      }
    }

    public void doEnd(HttpServletRequest request) {};

}
