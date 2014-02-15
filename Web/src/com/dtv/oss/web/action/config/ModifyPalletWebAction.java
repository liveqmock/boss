package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.PalletDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.PalletEJBEvent;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
/**
 * 货架操作
 * @author 260327h
 *
 */
public class ModifyPalletWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		PalletDTO dto = new PalletDTO();
		PalletEJBEvent event = new PalletEJBEvent();

		String aType = null;
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType")))
			aType = request.getParameter("txtActionType");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "操作标志 dto" + aType);

		//根据页面txtActionType来设置Event的操作类型,得不到任何操作标志时抛出异常.
		if ("CREATE".equalsIgnoreCase(aType)) {
			dto.setDtCreate(TimestampUtility.getCurrentDateWithoutTime());
			event = new PalletEJBEvent(dto, PalletEJBEvent.PALLET_CREATE);
		} else if ("MODIFY".equalsIgnoreCase(aType)) {
			event = new PalletEJBEvent(dto, PalletEJBEvent.PALLET_UPDATE);
		} else if ("DELETE".equalsIgnoreCase(aType)) {
			event = new PalletEJBEvent(dto, PalletEJBEvent.PALLET_DELETE);
			event.setList(request.getParameterValues("ListID"));
			return event;
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "非法操作");
			throw new WebActionException("非法操作");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtPalletID")))
			dto.setPalletID(WebUtil.StringToInt(request
					.getParameter("txtPalletID")));
		//加个验证,名称不空
		if (WebUtil.StringHaveContent(request.getParameter("txtPalletName"))) {
			dto.setPalletName(request.getParameter("txtPalletName"));
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "缺少仓库名称");
			throw new WebActionException("缺少仓库名称");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtPalletStatus")))
			dto.setStatus(request.getParameter("txtPalletStatus"));
		if (WebUtil.StringHaveContent(request.getParameter("txtMaxNumber")))
			dto.setMaxNumberAllowed(WebUtil.StringToInt(request
					.getParameter("txtMaxNumber")));
		if (WebUtil.StringHaveContent(request.getParameter("txtOwner")))
			dto.setDepotID(WebUtil
					.StringToInt(request.getParameter("txtOwner")));
		if (WebUtil.StringHaveContent(request.getParameter("txtPalletDesc")))
			dto.setPalletDesc(request.getParameter("txtPalletDesc"));

		dto.setDtLastmod(TimestampUtility.getCurrentDateWithoutTime());

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "修改仓库 dto"
				+ dto.toString());

		return event;
	}

}
