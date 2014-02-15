package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryPaymentRecordWebAction extends QueryWebAction{
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
    	  PaymentRecordDTO theDTO = new PaymentRecordDTO();
          if (WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
                theDTO.setAcctID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
          if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
                theDTO.setCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
          if (WebUtil.StringHaveContent(request.getParameter("txtInvoiceNo")))
                theDTO.setInvoiceNo(WebUtil.StringToInt(request.getParameter("txtInvoiceNo")));
          if (WebUtil.StringHaveContent(request.getParameter("txtID")))
                theDTO.setSeqNo(WebUtil.StringToInt(request.getParameter("txtID")));
          if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeFrom")))
        	    theDTO.setDtCreate(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTimeFrom")));
          if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeTo")))
                theDTO.setDtLastmod(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTimeTo")));
          if (WebUtil.StringHaveContent(request.getParameter("CSIID")))
                theDTO.setSourceRecordID(WebUtil.StringToInt(request.getParameter("CSIID")));
          if (WebUtil.StringHaveContent(request.getParameter("SourceType")))
      	      theDTO.setSourceType(request.getParameter("SourceType"));
          if (WebUtil.StringHaveContent(request.getParameter("txtReferType")))
    	      theDTO.setReferType(request.getParameter("txtReferType"));
          if (WebUtil.StringHaveContent(request.getParameter("txtReferID")))
    	    theDTO.setReferID(WebUtil.StringToInt(request.getParameter("txtReferID")));
 
          return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_PAYMENT_RECORD);
        }
    
}
