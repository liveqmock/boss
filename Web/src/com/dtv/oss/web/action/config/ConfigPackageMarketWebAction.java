package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigProductEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

public class ConfigPackageMarketWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		 
		ConfigProductEJBEvent event=new ConfigProductEJBEvent(EJBEvent.CONFIG_PACKAGE_MARKET_SEGMENT); 
		 
		if(WebUtil.StringHaveContent(request.getParameter("SegmentList")))
			event.setSegmentIDstr(request.getParameter("SegmentList"));
		 
		if(WebUtil.StringHaveContent(request.getParameter("txtPackageID")))
			event.setPackageID(WebUtil.StringToInt(request.getParameter("txtPackageID")));
		 
		 return event;
	}

	 

}
