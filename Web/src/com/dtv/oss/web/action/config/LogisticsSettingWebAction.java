package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LogisticsSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.LogisticsSettingEJBEvent;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
/*
 * 修改设备管理全局配置
 */
public class LogisticsSettingWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		LogisticsSettingDTO dto = new LogisticsSettingDTO();
		LogisticsSettingEJBEvent event = new LogisticsSettingEJBEvent();

		String aType = null;
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType")))
			aType = request.getParameter("txtActionType");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "操作标志 dto" + aType);

		// 根据页面txtActionType来设置Event的操作类型,得不到任何操作标志时抛出异常.
		if ("MODIFY".equalsIgnoreCase(aType)) {
			dto.setDtCreate(TimestampUtility.getCurrentDateWithoutTime());
			event = new LogisticsSettingEJBEvent(dto, LogisticsSettingEJBEvent.LOGISTICSSETTING_MODIFY);
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "非法操作");
			throw new WebActionException("非法操作");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
			dto.setSeqNo(WebUtil.StringToInt(request
					.getParameter("txtSeqNo")));

		if (WebUtil.StringHaveContent(request.getParameter("txtInAndOut")))
			dto.setInAndOut(request.getParameter("txtInAndOut"));

		if (WebUtil.StringHaveContent(request.getParameter("txtOutOrgnization")))
			dto.setOutOrgnization(WebUtil.StringToInt(request.getParameter("txtOutOrgnization")));
		
		if (WebUtil.StringHaveContent(request.getParameter("txtMatchAndPreauth")))
			dto.setMatchAndPreauth(request.getParameter("txtMatchAndPreauth"));
		
		if (WebUtil.StringHaveContent(request.getParameter("txtProduct1")))
			dto.setPreauthProductid1(WebUtil.StringToInt(request.getParameter("txtProduct1")));
		if (WebUtil.StringHaveContent(request.getParameter("txtProduct2")))
			dto.setPreauthProductid2(WebUtil.StringToInt(request.getParameter("txtProduct2")));
		if (WebUtil.StringHaveContent(request.getParameter("txtProduct3")))
			dto.setPreauthProductid3(WebUtil.StringToInt(request.getParameter("txtProduct3")));
		if (WebUtil.StringHaveContent(request.getParameter("txtProduct4")))
			dto.setPreauthProductid4(WebUtil.StringToInt(request.getParameter("txtProduct4")));
		if (WebUtil.StringHaveContent(request.getParameter("txtProduct5")))
			dto.setPreauthProductid5(WebUtil.StringToInt(request.getParameter("txtProduct5")));

		if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setStatus(request.getParameter("txtStatus"));

		dto.setDtLastmod(TimestampUtility.getCurrentDateWithoutTime());


		LogUtility.log(this.getClass(), LogLevel.DEBUG, "修改设备管理全局配置 dto"
				+ dto.toString());

		return event;
	}

}
