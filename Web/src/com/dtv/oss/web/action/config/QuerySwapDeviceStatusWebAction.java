package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.SwapDeviceReason2StatusDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QuerySwapDeviceStatusWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		SwapDeviceReason2StatusDTO dto =new SwapDeviceReason2StatusDTO();
		
		 
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
			dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
		 
		 
		  
		return new ConfigQueryEJBEvent(dto,pageFrom,pageTo,ConfigQueryEJBEvent.SWAP_DEVICE_REASON);	
	}
    
}
