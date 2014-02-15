package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class CustomerProblemSumStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)throws Exception {
		
		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();

	    if (WebUtil.StringHaveContent(request.getParameter("txtOperatorID")))
	      theDTO.setOperator(request.getParameter("txtOperatorID"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtStartDate"))) 
	      theDTO.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtStartDate")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtEndDate")))
	      theDTO.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtEndDate")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
	      theDTO.setSpareStr1(request.getParameter("txtOrgID"));
	    
	    return new StatQueryEJBEvent(theDTO, pageFrom, pageTo,StatQueryEJBEvent.QUERY_TYPE_CUSTOMER_PROBLEM_SUM_STAT);
	}

}
