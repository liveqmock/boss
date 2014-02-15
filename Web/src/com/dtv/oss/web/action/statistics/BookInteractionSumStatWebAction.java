package com.dtv.oss.web.action.statistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class BookInteractionSumStatWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)throws Exception {
		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();

	    if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceType")))
	      theDTO.setType(request.getParameter("txtOpenSourceType"));
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
		      theDTO.setSpareStr1(request.getParameter("txtOrgID"));
	    //操作员类别
	    //if (WebUtil.StringHaveContent(request.getParameter("txtOperatorClass")))
		//      theDTO.setSpareStr2(request.getParameter("txtOperatorClass"));
	    //创建时间
	    if (WebUtil.StringHaveContent(request.getParameter("txtStartDate")))
	      theDTO.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtStartDate")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtEndDate")))
	      theDTO.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtEndDate")));
	    //预约上门时间
	    if (WebUtil.StringHaveContent(request.getParameter("txtStartDate1")))
	      theDTO.setSpareBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtStartDate1")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtEndDate1")))
	      theDTO.setSpareEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtEndDate1")));

	    return new StatQueryEJBEvent(theDTO, pageFrom, pageTo,StatQueryEJBEvent.QUERY_TYPE_BOOK_INTERACTION_SUM_STAT);
	}

}
