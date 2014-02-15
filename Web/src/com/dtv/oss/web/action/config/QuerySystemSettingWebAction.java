package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

 
import com.dtv.oss.dto.SystemSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QuerySystemSettingWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		 SystemSettingDTO dto =new SystemSettingDTO();
		
		  if (WebUtil.StringHaveContent(request.getParameter("txtName")))
	         dto.setName(request.getParameter("txtName"));
	     
		 
		
		return new ConfigQueryEJBEvent(dto,0,0,ConfigQueryEJBEvent.SYSTEM_SETTING_QUERY);	
	}
	
	public void doEnd(HttpServletRequest request, CommandResponse cmdResponse) {
		super.doEnd(request, cmdResponse);
		Object payload = cmdResponse.getPayload();
		LogUtility.log(getClass(), LogLevel.DEBUG, "payload" + payload);
		Object obj = request.getAttribute("ResponseQueryResult");
		LogUtility.log(getClass(), LogLevel.DEBUG, "ResponseQueryResult" + obj);
		if (obj == null) {
			//request.setAttribute("ResponseQueryResult", payload);
		}

	}
    
}
