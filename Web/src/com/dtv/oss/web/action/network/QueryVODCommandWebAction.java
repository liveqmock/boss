package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVODInterfaceEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryVODCommandWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		 CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();  
		
		if(WebUtil.StringHaveContent(request.getParameter("txtStartTime")))
			dto.setBeginTime(WebUtil.StringToTimestamp(request.getParameter("txtStartTime")));
		if(WebUtil.StringHaveContent(request.getParameter("txtEndTime")))
			dto.setEndTime(WebUtil.StringToTimestamp(request.getParameter("txtEndTime")));
		 
		if(WebUtil.StringHaveContent(request.getParameter("txtEventClass")))
			dto.setSpareStr1(request.getParameter("txtEventClass"));
		if(WebUtil.StringHaveContent(request.getParameter("txtCustmerID")))
			dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustmerID")));
		if(WebUtil.StringHaveContent(request.getParameter("txtSerialNo")))
			dto.setSpareStr2(request.getParameter("txtSerialNo"));
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setStatus(request.getParameter("txtStatus"));
		if(WebUtil.StringHaveContent(request.getParameter("txtHostID")))
			dto.setSpareStr3(request.getParameter("txtHostID"));   
		
		return new QueryVODInterfaceEJBEvent(dto,pageFrom,pageTo,QueryVODInterfaceEJBEvent.VOD_EVENT_QUERY);	
	}
    
}
