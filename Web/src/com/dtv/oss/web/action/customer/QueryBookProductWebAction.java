package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryBookProductWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		CommonQueryConditionDTO dto = new CommonQueryConditionDTO();
		if(WebUtil.StringHaveContent(request.getParameter("txtCSIID"))){
			dto.setSpareStr1(request.getParameter("txtCSIID"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus"))){
			dto.setStatus(request.getParameter("txtStatus"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtSAID"))){
			dto.setSpareStr2(request.getParameter("txtSAID"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtSerialNo"))){
			dto.setSpareStr3(request.getParameter("txtSerialNo"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTimeStart"))){
			dto.setBeginTime(WebUtil.StringToTimestamp(request.getParameter("txtCreateTimeStart")));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTimeEnd"))){
			dto.setEndTime(WebUtil.StringToTimestamp(request.getParameter("txtCreateTimeEnd")));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtScheduleTimeStart"))){
			dto.setSpareBeginTime(WebUtil.StringToTimestamp(request.getParameter("txtScheduleTimeStart")));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtScheduleTimeEnd"))){
			dto.setSpareEndTime(WebUtil.StringToTimestamp(request.getParameter("txtScheduleTimeEnd")));
		}
		dto.setSpareStr4(request.getParameter("txtCustomerID"));
		
		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,
				CsrQueryEJBEvent.QUERY_BOOK_PRODUCT_CUSTSERVICEINTERACTION);
	}

}
