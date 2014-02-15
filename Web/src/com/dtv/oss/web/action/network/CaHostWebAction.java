package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CAHostDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.CaEJBEvent;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/*
 * CA接口信息查询
 */

public class CaHostWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		CAHostDTO dto = new CAHostDTO();
		CaEJBEvent event = new CaEJBEvent();

		String aType = null;
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType")))
			aType = request.getParameter("txtActionType");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "操作标志 dto" + aType);

		// 根据页面txtActionType来设置Event的操作类型,得不到任何操作标志时抛出异常.
		if ("CA_HOST_MODIFY".equalsIgnoreCase(aType)) {
			event = new CaEJBEvent(dto, CaEJBEvent.CA_HOST_MODIFY);
//			event=new CaEJBEvent();
//			event.setDto(dto);
//			event.setActionType(CaEJBEvent.CA_HOST_MODIFY);
		} else if ("CA_HOST_CREATE".equalsIgnoreCase(aType)) {
			dto.setDtCreate(TimestampUtility.getCurrentDateWithoutTime());
			event = new CaEJBEvent(dto, CaEJBEvent.CA_HOST_CREATE);
//			event=new CaEJBEvent();
//			event.setDto(dto);
//			event.setActionType(CaEJBEvent.CA_HOST_CREATE);
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "非法操作");
			throw new WebActionException("非法操作");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtHostID")))
			dto.setHostID(WebUtil.StringToInt(request.getParameter("txtHostID")));

		if (WebUtil.StringHaveContent(request.getParameter("txtHostName")))
			dto.setHostName(request.getParameter("txtHostName"));
		if (WebUtil.StringHaveContent(request.getParameter("txtCaType")))
			dto.setCaType(request.getParameter("txtCaType"));
		if (WebUtil.StringHaveContent(request.getParameter("txtIP")))
			dto.setIp(request.getParameter("txtIP"));
		if (WebUtil.StringHaveContent(request.getParameter("txtPort")))
			dto.setPort(WebUtil.StringToInt(request.getParameter("txtPort")));
		if (WebUtil.StringHaveContent(request.getParameter("txtIPBack")))
			dto.setIpBack(request.getParameter("txtIPBack"));
		if (WebUtil.StringHaveContent(request.getParameter("txtPortBack")))
			dto.setPortBack(WebUtil.StringToInt(request
					.getParameter("txtPortBack")));
		if (WebUtil.StringHaveContent(request.getParameter("txtProtocolType")))
			dto.setProtocolType(request.getParameter("txtProtocolType"));
		if (WebUtil.StringHaveContent(request.getParameter("txtLoopSize")))
			dto.setLoopSize(WebUtil.StringToInt(request
					.getParameter("txtLoopSize")));
		if (WebUtil.StringHaveContent(request.getParameter("txtLoopInterval")))
			dto.setLoopInterval(WebUtil.StringToInt(request
					.getParameter("txtLoopInterval")));
		if (WebUtil.StringHaveContent(request.getParameter("txtTryTime")))
			dto.setTryTime(WebUtil.StringToInt(request
					.getParameter("txtTryTime")));
		if (WebUtil
				.StringHaveContent(request.getParameter("txtValidStartTime")))
			dto.setValidStartTime(WebUtil.StringToTimestamp(request
					.getParameter("txtValidStartTime")));
		if (WebUtil.StringHaveContent(request.getParameter("txtValidEndTime")))
			dto.setValidEndTime(WebUtil.StringToTimestamp(request
					.getParameter("txtValidEndTime")));
		if (WebUtil.StringHaveContent(request.getParameter("txtOperatorID")))
			dto.setOperatorID(WebUtil.StringToInt(request
					.getParameter("txtOperatorID")));
		if (WebUtil.StringHaveContent(request.getParameter("txtMd5Key")))
			dto.setMd5key(request.getParameter("txtMd5Key"));
		if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setStatus(request.getParameter("txtStatus"));
		if (WebUtil.StringHaveContent(request.getParameter("txtDesc")))
			dto.setDescription(request.getParameter("txtDesc"));

		dto.setDtLastmod(TimestampUtility.getCurrentDateWithoutTime());

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA接口设置 dto"
				+ dto.toString());

		return event;
	}

}
