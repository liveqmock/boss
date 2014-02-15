package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VOIPGatewayDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.VOIPEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

public class VOIPGatewayWebAction extends GeneralWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		VOIPGatewayDTO dto=new VOIPGatewayDTO();
		String aType = null;
		String prevDevsType=null;
		VOIPEJBEvent event=new VOIPEJBEvent();
		if (WebUtil.StringHaveContent(request.getParameter("actionFlag")))
			aType = request.getParameter("actionFlag");
		
		if (WebUtil.StringHaveContent(request.getParameter("prevDevsType")))
			prevDevsType = request.getParameter("prevDevsType");
		
		event.setPrevDevsType(prevDevsType);
		
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "²Ù×÷±êÖ¾" + aType);
		if (WebUtil.StringHaveContent(request.getParameter("txtdeviceModel")))
			dto.setDeviceModel(request.getParameter("txtdeviceModel"));
		if (WebUtil.StringHaveContent(request.getParameter("txtdevsType")))
			dto.setDevsType(request.getParameter("txtdevsType"));
		if (WebUtil.StringHaveContent(request.getParameter("txtdescription")))
			dto.setDescription(request.getParameter("txtdescription"));
		event.setDto(dto);
		if("add".equals(aType)){
			event.setActionType(VOIPEJBEvent.GATEWAY_ADD);
			return event;
		}else if("modify".equals(aType)){
			event.setActionType(VOIPEJBEvent.GATEWAY_MODIFY);
			return event;
		}
		return null;
	}

}
