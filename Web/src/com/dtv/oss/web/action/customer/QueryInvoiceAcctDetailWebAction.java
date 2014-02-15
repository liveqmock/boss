package com.dtv.oss.web.action.customer;

/**
 * author :david.Yang
 */
import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;

public class QueryInvoiceAcctDetailWebAction extends QueryWebAction{
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
      com.dtv.oss.dto.AccountItemDTO theDTO = new com.dtv.oss.dto.AccountItemDTO();
      if (WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
            theDTO.setAcctID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
      if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
            theDTO.setCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
      if (WebUtil.StringHaveContent(request.getParameter("txtInvoiceNo")))
            theDTO.setInvoiceNO(WebUtil.StringToInt(request.getParameter("txtInvoiceNo")));
      if (WebUtil.StringHaveContent(request.getParameter("txtID")))
    	    theDTO.setAiNO(WebUtil.StringToInt(request.getParameter("txtID")));
      if (WebUtil.StringHaveContent(request.getParameter("CSIID")))
            theDTO.setReferID(WebUtil.StringToInt(request.getParameter("CSIID")));
      if (WebUtil.StringHaveContent(request.getParameter("ReferType")))
    	    theDTO.setReferType(request.getParameter("ReferType"));
      if (WebUtil.StringHaveContent(request.getParameter("txtReferID")))
	    theDTO.setReferID(WebUtil.StringToInt(request.getParameter("txtReferID")));
      if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeFrom")))
  	       theDTO.setDtCreate(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTimeFrom")));
       if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeTo")))
          theDTO.setDtLastmod(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTimeTo")));
      return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUSTACCOUNTITEM_RECORD);
    }

}