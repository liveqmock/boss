package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BillingRuleDTO;
import com.dtv.oss.dto.FeeSplitPlanDTO;
import com.dtv.oss.dto.FeeSplitPlanItemDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigBillingEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p>
 * Title: BOSS P5
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Digivision
 * </p>
 * User: chen jiang Date: 2007-4-30 Time: 15:22:51 To change this template use
 * File | Settings | File Templates.
 */
public class ModifyFeeSplitPlanItemWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {

		FeeSplitPlanItemDTO dto = new FeeSplitPlanItemDTO();
		ConfigBillingEJBEvent event = new ConfigBillingEJBEvent();

		if ("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.FEE_PLAN_ITEM_CREATE);
		else if ("update".equals(request.getParameter("Action"))) {
			 
			event.setActionType(EJBEvent.FEE_PLAN_ITEM_MODIFY);
		} else if ("deleteDetail".equals(request.getParameter("Action"))) {
			event.setActionType(EJBEvent.FEE_PLAN_DELETE);
		} else {
			throw new WebActionException("action type is not correct");
		}

		dto.setFeeSplitPlanID(WebUtil.StringToInt(request.getParameter("txtFeeSplitPlanID")));
		if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
		dto.setSeqNo(new Integer(request
				.getParameter("txtSeqNo")));
		dto.setTimeCycleNO(WebUtil.StringToInt(request
				.getParameter("txtTimeCycleNo")));
        dto.setValue(WebUtil.StringTodouble(request.getParameter("txtValue")));
		event.setFeeSplitPlanItemDto(dto);

		return event;
	}
}
