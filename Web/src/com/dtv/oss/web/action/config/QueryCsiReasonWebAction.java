package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CsiActionReasonSettingDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryCsiReasonWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		CsiActionReasonSettingDTO dto =new CsiActionReasonSettingDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtCsiType")))
			dto.setCsiType(request.getParameter("txtCsiType"));
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
			dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
		if(WebUtil.StringHaveContent(request.getParameter("txtAction")))
			dto.setAction(request.getParameter("txtAction"));
		if(WebUtil.StringHaveContent(request.getParameter("txtDisplayName")))
			dto.setDisplayName(request.getParameter("txtDisplayName"));
		if(WebUtil.StringHaveContent(request.getParameter("txtFlag")))
			dto.setCanEmptyFlag(request.getParameter("txtFlag"));
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setStatus(request.getParameter("txtStatus"));
		  
		return new ConfigQueryEJBEvent(dto,pageFrom,pageTo,ConfigQueryEJBEvent.CSI_REASON_QUERY);	
	}
    
}
