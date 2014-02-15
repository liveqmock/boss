package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CsiTypeReason2DeviceDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryCsiReasonDeviceWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		CsiTypeReason2DeviceDTO dto =new CsiTypeReason2DeviceDTO();
		
		if(WebUtil.StringHaveContent(request.getParameter("txtCsiType")))
			dto.setCsiType(request.getParameter("txtCsiType"));
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
			dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
		if(WebUtil.StringHaveContent(request.getParameter("txtCsiCreateReason")))
			dto.setCsiCreateReason(request.getParameter("txtCsiCreateReason"));
		if(WebUtil.StringHaveContent(request.getParameter("txtReferPurpose")))
			dto.setReferPurpose(request.getParameter("txtReferPurpose"));
	 
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setStatus(request.getParameter("txtStatus"));
		  
		return new ConfigQueryEJBEvent(dto,pageFrom,pageTo,ConfigQueryEJBEvent.CSI_TYPE_REASON2DEVICE);	
	}
    
}
