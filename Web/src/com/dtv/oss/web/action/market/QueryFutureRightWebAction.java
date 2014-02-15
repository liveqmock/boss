package com.dtv.oss.web.action.market;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.system.SystemQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryFutureRightWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		 if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
		    dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateStartDate")));
		 if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateEndDate")));
		 if (WebUtil.StringHaveContent(request.getParameter("txtExcuteStartDate")))
			 dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtExcuteStartDate")));
		 if (WebUtil.StringHaveContent(request.getParameter("txtExcuteEndDate")))
			 dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtExcuteEndDate")));
		 if (WebUtil.StringHaveContent(request.getParameter("txtCancelStartDate")))
			 dto.setSpareTime5(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCancelStartDate")));
		 if (WebUtil.StringHaveContent(request.getParameter("txtCancelEndDate")))
			 dto.setSpareTime6(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCancelEndDate")));
		 if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
		     dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		 if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
			 dto.setNo(request.getParameter("txtSeqNo"));
		 
		 return new SystemQueryEJBEvent(dto, pageFrom, pageTo, SystemQueryEJBEvent.QUERY_FUTURERIGHT);
    }

}
