package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BrconditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryBrConditionWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		BrconditionDTO dto =new BrconditionDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtBrcntID")))
			dto.setBrcntID(WebUtil.StringToInt(request.getParameter("txtBrcntID")));
		if(WebUtil.StringHaveContent(request.getParameter("txtConditionType")))
			dto.setCntType(request.getParameter("txtConditionType"));
		if(WebUtil.StringHaveContent(request.getParameter("txtConditionName")))
			dto.setCntName(request.getParameter("txtConditionName"));
		 
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setStatus(request.getParameter("txtStatus"));
		
		return new ConfigQueryEJBEvent(dto,pageFrom,pageTo,ConfigQueryEJBEvent.CONFIG_BRCONDITION_QUERY);	
	}
    
}
