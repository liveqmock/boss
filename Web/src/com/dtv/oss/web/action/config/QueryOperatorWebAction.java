package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryOperatorWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		OperatorDTO dto =new OperatorDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setOrgID(WebUtil.StringToInt(request.getParameter("txtOrgID")));
		if(WebUtil.StringHaveContent(request.getParameter("txtOperatorID")))
			dto.setOperatorID(WebUtil.StringToInt(request.getParameter("txtOperatorID")));
		if(WebUtil.StringHaveContent(request.getParameter("txtOperatorName")))
			dto.setOperatorName(request.getParameter("txtOperatorName"));
 
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setStatus(request.getParameter("txtStatus"));
		if(WebUtil.StringHaveContent(request.getParameter("txtLoginID")))
			dto.setLoginID(request.getParameter("txtLoginID"));
 
		if(WebUtil.StringHaveContent(request.getParameter("txtOperatorAccount")))
			dto.setLoginID(request.getParameter("txtOperatorAccount"));
 
		return new ConfigQueryEJBEvent(dto,pageFrom,pageTo,ConfigQueryEJBEvent.OPERATOR_SUB_GROUP_QUERY);	
	}
    
}
