package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VOIPConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.VOIPEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

public class VOIPConditionWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		VOIPConditionDTO dto=new VOIPConditionDTO();
		String aType = null;
		VOIPEJBEvent event=new VOIPEJBEvent();
		if (WebUtil.StringHaveContent(request.getParameter("actionFlag")))
			aType = request.getParameter("actionFlag");
		
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "²Ù×÷±êÖ¾" + aType);
		
		if (WebUtil.StringHaveContent(request.getParameter("txtConditionName")))
			dto.setConditionName(request.getParameter("txtConditionName"));
		if (WebUtil.StringHaveContent(request.getParameter("txtConditionString")))
			dto.setConditionString(request.getParameter("txtConditionString"));
		if (WebUtil.StringHaveContent(request.getParameter("txtDescription")))
			dto.setDescription(request.getParameter("txtDescription"));
		if (WebUtil.StringHaveContent(request.getParameter("txtConditionID")))
			dto.setConditionID(WebUtil.StringToInt(request.getParameter("txtConditionID")));
		    event.setDto(dto);
		if("add".equals(aType)){
			event.setActionType(VOIPEJBEvent.CONDITION_ADD);
			return event;
		}else if("modify".equals(aType)){
			event.setActionType(VOIPEJBEvent.CONDITION_MODIFY);
			return event;
		}
		return null;
	}

}
