package com.dtv.oss.web.flow;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.web.util.*;
import com.dtv.oss.web.exception.FlowHandlerException;

public class LoginFlowHandler implements FlowHandler{
  public void doStart(HttpServletRequest request) {};

  public String processFlow(HttpServletRequest request) throws FlowHandlerException {
      if (request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE)!=null) return "1";

      if ((CurrentOperator.GetCurrentOperatorPrivilege(request)==null)
              ||(CurrentOperator.GetCurrentOperator(request)==null))
            return "1";

      return "0"; //normal process

  }

  public void doEnd(HttpServletRequest request) {};

}