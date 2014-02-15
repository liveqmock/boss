package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DepotDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.DepotEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
/*
 * 仓库操作
 */
public class ModifyDepotWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		DepotDTO dto = new DepotDTO();
		DepotEJBEvent event = new DepotEJBEvent();

		String aType = null;
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType")))
			aType = request.getParameter("txtActionType");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "操作标志 dto" + aType);

		//根据页面txtActionType来设置Event的操作类型,得不到任何操作标志时抛出异常.
		if ("CREATE".equalsIgnoreCase(aType)) {
			dto.setDtCreate(TimestampUtility.getCurrentDateWithoutTime());
			event = new DepotEJBEvent(dto, EJBEvent.DEPOT_CREATE);
		} else if ("MODIFY".equalsIgnoreCase(aType)) {
			event = new DepotEJBEvent(dto, EJBEvent.DEPOT_UPDATE);
		} else if ("DELETE".equalsIgnoreCase(aType)) {
			event = new DepotEJBEvent(dto, EJBEvent.DEPOT_DELETE);
			event.setList(request.getParameterValues("ListID"));
			return event;
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "非法操作");
			throw new WebActionException("非法操作");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtDepotID")))
			dto.setDepotID(WebUtil.StringToInt(request
					.getParameter("txtDepotID")));
		//加个验证,名称不空
		if (WebUtil.StringHaveContent(request.getParameter("txtDepotName"))) {
			dto.setDepotName(request.getParameter("txtDepotName"));
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "缺少仓库名称");
			throw new WebActionException("缺少仓库名称");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
			dto.setDetailAddress(request.getParameter("txtDetailAddress"));
		if (WebUtil.StringHaveContent(request.getParameter("txtDepotStatus")))
			dto.setStatus(request.getParameter("txtDepotStatus"));
		if (WebUtil.StringHaveContent(request.getParameter("txtOwner")))
			dto.setOwnerID(WebUtil
					.StringToInt(request.getParameter("txtOwner")));

		dto.setDtLastmod(TimestampUtility.getCurrentDateWithoutTime());


		LogUtility.log(this.getClass(), LogLevel.DEBUG, "修改仓库 dto"
				+ dto.toString());

		return event;
	}
	protected void afterSuccessfulResponse(HttpServletRequest request,
			CommandResponse cmdResponse) {
		//更新下仓库缓存
		Postern.getDepots(true);
	}
}
