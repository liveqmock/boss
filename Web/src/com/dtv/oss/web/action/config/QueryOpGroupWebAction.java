package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.OpGroupDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryOpGroupWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		OpGroupDTO dto =new OpGroupDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtOpGroupID")))
			dto.setOpGroupID(WebUtil.StringToInt(request.getParameter("txtOpGroupID")));
		if(WebUtil.StringHaveContent(request.getParameter("txtOpGroupName")))
			dto.setOpGroupName(request.getParameter("txtOpGroupName"));
		 
		
		return new ConfigQueryEJBEvent(dto,pageFrom,pageTo,ConfigQueryEJBEvent.CONFIG_OPGROUP_QUERY);	
	}
    
}
