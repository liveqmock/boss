package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryPrepaymentDeduction extends QueryWebAction{
	 protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
		 CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		 if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate"))){
			 theDTO.setSpareTime1(WebUtil.StringToTimestampWithDayBegin("txtCreateStartDate"));
		 }
		 if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate"))){
			 theDTO.setSpareTime2(WebUtil.StringToTimestampWithDayEnd("txtCreateEndDate"));
		 }
		 if (WebUtil.StringHaveContent(request.getParameter("txtAccountYesNoFlag"))){
			 theDTO.setSpareStr1(request.getParameter("txtAccountYesNoFlag"));
		 }
		 if (WebUtil.StringHaveContent(request.getParameter("txtReferRecordType"))){
			 theDTO.setSpareStr2(request.getParameter("txtReferRecordType"));
		 }
		 if (WebUtil.StringHaveContent(request.getParameter("txtReferRecordID"))){
			 theDTO.setSpareStr3(request.getParameter("txtReferRecordID"));
		 }
		 if (WebUtil.StringHaveContent(request.getParameter("txtPrePaymentType"))){
			 theDTO.setSpareStr4(request.getParameter("txtPrePaymentType"));
		 }
		 if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID"))){
		     theDTO.setSpareStr8(request.getParameter("txtCustomerID"));
		 }
		 if (WebUtil.StringHaveContent(request.getParameter("txtAccountID"))){
		     theDTO.setSpareStr9(request.getParameter("txtAccountID"));
		 }
		 if (WebUtil.StringHaveContent(request.getParameter("txtInvoiceNo")))
			 theDTO.setSpareStr10(request.getParameter("txtInvoiceNo"));
		 
		 return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_PREPAYMENTDEDUCTION);
	 }

}
