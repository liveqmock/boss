package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.MarketSegmentToDistrictDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
 
public class QueryMarketSegmentDistrictWebAction extends QueryWebAction {

	 
	protected EJBEvent encapsulateData(HttpServletRequest request)
	  throws Exception {
		
		 MarketSegmentToDistrictDTO	dto = new MarketSegmentToDistrictDTO();
		
	     if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
	        dto.setDistrictId(WebUtil.StringToInt(request.getParameter("txtDistrictID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtMarketSegmentID")))
	        dto.setMarketSegmentId(WebUtil.StringToInt(request.getParameter("txtMarketSegmentID")));
	    
	  
	    return new ConfigQueryEJBEvent(dto, pageFrom, pageTo, ConfigQueryEJBEvent.QUERY_SEGMENT_DISTRICT);
	    
	}

	 

}
