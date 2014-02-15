package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.UserPointsExchangeActivityDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
 
public class QueryUserPointsActivityWebAction extends QueryWebAction {

	 
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		UserPointsExchangeActivityDTO	dto = new UserPointsExchangeActivityDTO();
	    
		// 活动id
	    if (WebUtil.StringHaveContent(request.getParameter("txtActivityID")))
	        dto.setId(new Integer(WebUtil.StringToInt(request.getParameter("txtActivityID"))));
	    
	    // 活动名称
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtName")))
	      dto.setName(request.getParameter("txtName"));
	    //活动开始时间
	    if (WebUtil.StringHaveContent(request.getParameter("txtStartTime")))
		      dto.setDateStart(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtStartTime")));
	    ///活动结束时间
	    if (WebUtil.StringHaveContent(request.getParameter("txtEndTime")))
		      dto.setDateEnd(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtEndTime")));
	    return new ConfigQueryEJBEvent(dto, pageFrom, pageTo, ConfigQueryEJBEvent.QUERY_POINTS_ACTIVITY);
	    
	}

	 

}
