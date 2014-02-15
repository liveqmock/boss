package com.dtv.oss.web.flow;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.flow.FlowHandler;
import com.dtv.oss.web.exception.FlowHandlerException;
import com.dtv.oss.web.exception.WebActionException;

import com.dtv.oss.web.taglib.util.QueryPageProp;

public class QueryCertainObjectFlowHandler implements FlowHandler{
  public void doStart(HttpServletRequest request) {};

  public String processFlow(HttpServletRequest request) throws FlowHandlerException {
      QueryPageProp pgprop = (QueryPageProp)request.getAttribute(WebKeys.CURRENT_RESULTSET_PROPERTY);

      if (pgprop == null)
      {
          request.setAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE, new WebActionException("没有结果返回"));
          return "1";
      }

      if (pgprop.getCurPageRecordCount()==1) return "0";
      else
      {
          if (pgprop.getCurPageRecordCount()>1)
              request.setAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE, new WebActionException("返回了多个结果"));
          else
              request.setAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE, new WebActionException("没有结果返回"));

          return "1";
      }
  }

  public void doEnd(HttpServletRequest request) {};
}