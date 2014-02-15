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

public class QueryDevicePreauthHisWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtDeviceID")))
			theDTO.setSpareStr1(request.getParameter("txtDeviceID"));

		return new LogisticsQueryEJBEvent(theDTO, pageFrom, pageTo,
				LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_PREAUTH_HISTORY);
	}
}