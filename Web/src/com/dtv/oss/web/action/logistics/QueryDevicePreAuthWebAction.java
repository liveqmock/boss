package com.dtv.oss.web.action.logistics;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryDevicePreAuthWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtBatchId")))
			theDTO.setSpareStr1(request.getParameter("txtBatchId"));
		if (WebUtil.StringHaveContent(request.getParameter("txtDeviceID")))
			theDTO.setSpareStr2(request.getParameter("txtDeviceID"));
		if (WebUtil.StringHaveContent(request.getParameter("txtSerialNo")))
			theDTO.setSpareStr3(request.getParameter("txtSerialNo"));
		if (WebUtil.StringHaveContent(request.getParameter("txtOperationType")))
			theDTO.setSpareStr4(request.getParameter("txtOperationType"));
		if (WebUtil.StringHaveContent(request.getParameter("txtProductIDList")))
			theDTO.setSpareStr5(request.getParameter("txtProductIDList"));

		if (WebUtil.StringHaveContent(request
				.getParameter("txtCreateTimeBegin")))
			theDTO.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request
					.getParameter("txtCreateTimeBegin")));
		if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeEnd")))
			theDTO.setEndTime(WebUtil.StringToTimestampWithDayEnd(request
					.getParameter("txtCreateTimeEnd")));

		return new LogisticsQueryEJBEvent(theDTO, pageFrom, pageTo,
				LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_PREAUTH);
	}

}