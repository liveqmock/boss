package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;

public class QueryServiceAccountWebAction extends QueryWebAction{

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
      CustomerProductDTO theDTO = new CustomerProductDTO();
      if (WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID")))
        theDTO.setServiceAccountID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID").trim()));
      if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        theDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
/*
      if (WebUtil.StringHaveContent(request.getParameter("txtPsID")))//CustomerProductID
              theDTO.setPsID(WebUtil.StringToInt(request.getParameter("txtPsID")));
      if (WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
              theDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
      if (WebUtil.StringHaveContent(request.getParameter("txtProductID")))
              theDTO.setProductID(WebUtil.StringToInt(request.getParameter("txtProductID")));
      if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
              theDTO.setStatus(request.getParameter("txtStatus"));
*/
      
      if("showForSechedule".equals(request.getParameter("txtActionType")))//为排程查询 只查询软件产品
    	  return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_SERVICE_ACCOUNT_FOR_SCHEDULE);
      
       return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_SERVICE_ACCOUNT);

    }

}