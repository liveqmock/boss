package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BillingRuleDTO;
import com.dtv.oss.dto.FeeSplitPlanDTO;
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
 * User: chen jiang Date: 2006-4-24 Time: 15:22:51 To change this template use
 * File | Settings | File Templates.
 */
public class ModifyFeeSplitPlanWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {

		FeeSplitPlanDTO dto = new FeeSplitPlanDTO();
		ConfigBillingEJBEvent event = new ConfigBillingEJBEvent();

		if ("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.FEE_PLAN_CREATE);
		else if ("update".equals(request.getParameter("Action"))) {
			 
			event.setActionType(EJBEvent.FEE_PLAN_MODIFY);
		} else if ("deleteDetail".equals(request.getParameter("Action"))) {
			event.setActionType(EJBEvent.FEE_PLAN_DELETE);
		} else {
			throw new WebActionException("action type is not correct");
		}

		dto.setPlanName(request.getParameter("txtPlanName"));
		if (WebUtil.StringHaveContent(request.getParameter("txtFeeSplitPlanID")))
		dto.setFeeSplitPlanID(new Integer(request
				.getParameter("txtFeeSplitPlanID")));
		dto.setTotalTimeCycleNo(WebUtil.StringToInt(request
				.getParameter("txtTotalTimeCycleNo")));

		event.setFeeSplitPlanDto(dto);

		return event;
	}
}
