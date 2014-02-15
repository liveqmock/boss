package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BillBoardDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryBillBoardWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		BillBoardDTO dto =new BillBoardDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtName")))
			dto.setName(request.getParameter("txtName"));
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
			dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
		if(WebUtil.StringHaveContent(request.getParameter("txtPublishPerson")))
			dto.setPublishPerson(request.getParameter("txtPublishPerson"));
		if(WebUtil.StringHaveContent(request.getParameter("txtPublishReason")))
			dto.setPublishReason(request.getParameter("txtPublishReason"));
		if(WebUtil.StringHaveContent(request.getParameter("txtGrade")))
			dto.setGrade(request.getParameter("txtGrade"));
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setStatus(request.getParameter("txtStatus"));
		 if (WebUtil.StringHaveContent(request.getParameter("txtPublishStartDate")))
	    	dto.setDateFrom(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtPublishStartDate")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtPublishEndDate")))
	    	dto.setDateTo(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtPublishEndDate")));
		return new ConfigQueryEJBEvent(dto,pageFrom,pageTo,ConfigQueryEJBEvent.Bill_BOARD_QUERY);	
	}
    
}
