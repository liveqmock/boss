package com.dtv.oss.web.action.market;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CAWalletDefineDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.market.MarketEJBEvent;
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
 * User: chen jiang Date: 2006-3-20 Time: 15:22:51 To change this template use
 * File | Settings | File Templates.
 */
public class ModifyIppvWebAction extends GeneralWebAction {

	boolean confirmPost = false;

	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {

		super.doStart(request);

		confirmPost = false;
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
				confirmPost = true;
		}

	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {

		CAWalletDefineDTO ippvDTO = new CAWalletDefineDTO();

		MarketEJBEvent event = new MarketEJBEvent();
		if ("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.IPPV_CREATE);
		if ("update".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.IPPV_UPDATE);
		/*
		 * if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
		 * event.setSeqNo(WebUtil.StringToInt(request
		 * .getParameter("txtSeqNo")));
		 */

		if (WebUtil.StringHaveContent(request.getParameter("caWalletCode")))
			ippvDTO.setCaWalletCode(request.getParameter("caWalletCode"));
		if (WebUtil.StringHaveContent(request.getParameter("caWalletName")))
			ippvDTO.setCaWalletName(request.getParameter("caWalletName"));
		if (WebUtil.StringHaveContent(request.getParameter("deviceModelList")))
			ippvDTO.setDeviceModelList(request.getParameter("deviceModelList"));
		if (WebUtil.StringHaveContent(request.getParameter("required")))
			ippvDTO.setRequired(request.getParameter("required"));
		if (WebUtil.StringHaveContent(request.getParameter("rate")))
			ippvDTO.setRate(new BigDecimal(request.getParameter("rate")));

		if (WebUtil.StringHaveContent(request.getParameter("dtLastmod")))
			ippvDTO.setDtLastmod(WebUtil.StringToTimestamp(request
					.getParameter("dtLastmod")));

		event.setDoPost(confirmPost);
		event.setIppvDTO(ippvDTO);
		
		return event;
	}
}
