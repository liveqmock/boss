package com.dtv.oss.web.action.system;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.system.LogQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Digivision</p>
 * @author chen jiang
 * @version 1.0
 */

public class LogQueryWebAction extends QueryWebAction {
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		com.dtv.oss.dto.SystemLogDTO dto = new com.dtv.oss.dto.SystemLogDTO();

		if (WebUtil.StringHaveContent(request.getParameter("txtOperatorID")))
			dto.setOperatorID(WebUtil.StringToInt(request
					.getParameter("txtOperatorID")));
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			dto.setCustomerID(WebUtil.StringToInt(request
					.getParameter("txtCustomerID")));

		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerName")))
			dto.setCustomerName(request.getParameter("txtCustomerName"));
		if (WebUtil.StringHaveContent(request.getParameter("txtSequenceNO")))
			dto.setSequenceNo(WebUtil.StringToInt(request
					.getParameter("txtSequenceNO")));

		if (WebUtil.StringHaveContent(request.getParameter("txtLoginID")))
			dto.setLoginID(request.getParameter("txtLoginID"));
		if (WebUtil.StringHaveContent(request.getParameter("txtOperation")))
			dto.setOperation(request.getParameter("txtOperation"));
		if (WebUtil.StringHaveContent(request.getParameter("txtMachineName")))
			dto.setMachineName(request.getParameter("txtMachineName"));

		if (WebUtil.StringHaveContent(request.getParameter("txtLogClass")))
			dto.setLogClass(request.getParameter("txtLogClass"));
		if (WebUtil.StringHaveContent(request.getParameter("txtModuleName")))
			dto.setModuleName(request.getParameter("txtModuleName"));
		
		/**
		if (WebUtil.StringHaveContent(request.getParameter("txtStartTimeDatePart")))
			dto.setDtCreate(WebUtil.StringToTimestampDefaultDayBegin(request
					.getParameter("txtStartTimeDatePart"), request.getParameter("txtStartTimeHourPart")));
		if (WebUtil.StringHaveContent(request.getParameter("txtEndTimeDatePart")))
			dto.setDtLastmod(WebUtil.StringToTimestampDefaultDayEnd(request
					.getParameter("txtEndTimeDatePart"), request.getParameter("txtEndTimeHourPart")));
		**/
		
		if (WebUtil.StringHaveContent(request.getParameter("txtStartTime")))
			dto.setDtCreate(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtStartTime")));
		if (WebUtil.StringHaveContent(request.getParameter("txtEndTime")))
			dto.setDtLastmod(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtEndTime")));
		
		return new LogQueryEJBEvent(dto, pageFrom, pageTo,
				LogQueryEJBEvent.QUERY_SYSTEM_LOG);
	}

}
