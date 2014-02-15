package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.UserPointsCumulatedRuleDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
 
public class QueryUserPointsCumulateRuleWebAction extends QueryWebAction {

	 
	protected EJBEvent encapsulateData(HttpServletRequest request)
	  throws Exception {
		UserPointsCumulatedRuleDTO	dto = new UserPointsCumulatedRuleDTO();
	    
		 
	    if (WebUtil.StringHaveContent(request.getParameter("txtID")))
	        dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCondEvent")))
	        dto.setCondEvent(WebUtil.StringToInt(request.getParameter("txtCondEvent")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtProductID")))
	        dto.setProductID(WebUtil.StringToInt(request.getParameter("txtProductID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
	        dto.setStatus(request.getParameter("txtStatus"));
	    return new ConfigQueryEJBEvent(dto, pageFrom, pageTo, ConfigQueryEJBEvent.QUERY_CUMU_RULE);
	    
	}

	 

}
