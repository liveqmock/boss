package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CAConditionDTO;
import com.dtv.oss.dto.CAHostDTO;
import com.dtv.oss.dto.CAProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.CaEJBEvent;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/*
 * CA条件
 */

public class CaConditionWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		CAConditionDTO dto = new CAConditionDTO();
		CaEJBEvent event = new CaEJBEvent();

		String aType = null;
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType")))
			aType = request.getParameter("txtActionType");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "操作标志" + aType);

		// 根据页面txtActionType来设置Event的操作类型,得不到任何操作标志时抛出异常.
		if ("CA_CONDITION_MODIFY".equalsIgnoreCase(aType)) {
			event = new CaEJBEvent(dto, CaEJBEvent.CA_CONDITION_MODIFY);
		} else if ("CA_CONDITION_CREATE".equalsIgnoreCase(aType)) {
			dto.setDtCreate(TimestampUtility.getCurrentDateWithoutTime());
			event = new CaEJBEvent(dto, CaEJBEvent.CA_CONDITION_CREATE);
		} else {
			LogUtility.log(getClass(), LogLevel.WARN, "非法操作");
			throw new WebActionException("非法操作");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtCondID")))
			dto.setCondID(WebUtil.StringToInt(request.getParameter("txtCondID")));

		 
			dto.setCondName(request.getParameter("txtCondName"));
		 
			dto.setHostID(WebUtil.StringToInt(request.getParameter("txtHostID")));
		 
			dto.setCondString(request.getParameter("txtCondString"));
		 
			dto.setDescription(request.getParameter("txtDesc"));

		   dto.setDtLastmod(TimestampUtility.getCurrentDateWithoutTime());

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA产品设置 dto"
				+ dto.toString());

		return event;
	}

}
