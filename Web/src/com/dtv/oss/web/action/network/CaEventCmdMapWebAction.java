package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CAEventCmdMapDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.CaEJBEvent;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/*
 * CA事件命令映射
 */

public class CaEventCmdMapWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		CAEventCmdMapDTO dto = new CAEventCmdMapDTO();
		CaEJBEvent event = new CaEJBEvent();

		String aType = null;
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType")))
			aType = request.getParameter("txtActionType");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "操作标志" + aType);

		// 根据页面txtActionType来设置Event的操作类型,得不到任何操作标志时抛出异常.
		if ("CA_EVENTCMDMAP_MODIFY".equalsIgnoreCase(aType)) {
			event = new CaEJBEvent(dto, CaEJBEvent.CA_EVENTCMDMAP_MODIFY);
		} else if ("CA_EVENTCMDMAP_CREATE".equalsIgnoreCase(aType)) {
			dto.setDtCreate(TimestampUtility.getCurrentDateWithoutTime());
			event = new CaEJBEvent(dto, CaEJBEvent.CA_EVENTCMDMAP_CREATE);
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "非法操作");
			throw new WebActionException("非法操作");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtMapID")))
			dto.setMapID(WebUtil.StringToInt(request.getParameter("txtMapID")));

		 
			dto.setMapEventID(WebUtil.StringToInt(request.getParameter("txtEventID")));
		 
			dto.setMapConditionID(WebUtil.StringToInt(request.getParameter("txtCondID")));
		 
			dto.setMapCmdID(WebUtil.StringToInt(request.getParameter("txtCmdID")));
		 
			dto.setPriority(WebUtil.StringToInt(request.getParameter("txtPriority")));
		 
			dto.setStatus(request.getParameter("txtStatus"));
		 
			dto.setDescription(request.getParameter("txtDesc"));

		dto.setDtLastmod(TimestampUtility.getCurrentDateWithoutTime());

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA产品设置 dto"
				+ dto.toString());

		return event;
	}

}
