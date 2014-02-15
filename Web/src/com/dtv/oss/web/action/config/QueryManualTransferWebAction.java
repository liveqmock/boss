package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ManualTransferSettingDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryManualTransferWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		ManualTransferSettingDTO dto =new ManualTransferSettingDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtFromOrgId")))
			dto.setFromOrgId(WebUtil.StringToInt(request.getParameter("txtFromOrgId")));
		if(WebUtil.StringHaveContent(request.getParameter("txtToOrgId")))
			dto.setToOrgId(WebUtil.StringToInt(request.getParameter("txtToOrgId")));
		if(WebUtil.StringHaveContent(request.getParameter("txtSheetType")))
			dto.setSheetType(request.getParameter("txtSheetType"));
		if(WebUtil.StringHaveContent(request.getParameter("txtSubType")))
			dto.setOrgSubRole(request.getParameter("txtSubType"));
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
			dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
 
		 
		return new ConfigQueryEJBEvent(dto,pageFrom,pageTo,ConfigQueryEJBEvent.MANUAL_TRANSFER_QUERY);	
	}
    
}
