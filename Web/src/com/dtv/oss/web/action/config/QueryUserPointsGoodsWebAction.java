package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.UserPointsExchangeGoodsDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
 
public class QueryUserPointsGoodsWebAction extends QueryWebAction {

	 
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		UserPointsExchangeGoodsDTO	dto = new UserPointsExchangeGoodsDTO();
	    
		// »î¶¯id
	    if (WebUtil.StringHaveContent(request.getParameter("txtActivityID")))
	        dto.setActivityId(WebUtil.StringToInt(request.getParameter("txtActivityID")));
	    if (WebUtil.StringHaveContent(request.getParameter("vartxtActivityID")))
	        dto.setActivityId(WebUtil.StringToInt(request.getParameter("vartxtActivityID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtID")))
	        dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
	    return new ConfigQueryEJBEvent(dto, pageFrom, pageTo, ConfigQueryEJBEvent.QUERY_POINTS_GOODS);
	    
	}

	 

}
