package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.MarketSegmentDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
 
public class QueryMarketSegmentWebAction extends QueryWebAction {

	 
	protected EJBEvent encapsulateData(HttpServletRequest request)
	  throws Exception {
		
		  MarketSegmentDTO	dto = new MarketSegmentDTO();
		
	     if (WebUtil.StringHaveContent(request.getParameter("txtID")))
	        dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtName")))
	        dto.setName(request.getParameter("txtName"));
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
	        dto.setStatus(request.getParameter("txtStatus"));
	    return new ConfigQueryEJBEvent(dto, pageFrom, pageTo, ConfigQueryEJBEvent.QUERY_SEGMENT);
	    
	}

	 

}
