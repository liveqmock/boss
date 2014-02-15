package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.MethodOfPaymentDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryMethodOfPaymentWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		MethodOfPaymentDTO dto =new MethodOfPaymentDTO();
		
		  if (WebUtil.StringHaveContent(request.getParameter("txtMopID")))
	        dto.setMopID(WebUtil.StringToInt(request.getParameter("txtMopID")));
	     
	 
		
		return new ConfigQueryEJBEvent(dto,0,0,ConfigQueryEJBEvent.CONFIG_METHOD_OF_PAYMENT_QUERY);	
	}
    
}
