package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ServiceResourceDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryServiceResourceEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author 260425w 2006-5-30 15:48:24
 */
public class QueryServiceResourceBriefListWebAction extends QueryWebAction {
	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		String strFrom = (String) request.getParameter("txtFrom");
		String strTo = (String) request.getParameter("txtTo");
		if (strFrom == null || strFrom.trim().length() == 0) {
			pageFrom = 1;
		}
		if (strTo == null || strTo.trim().length() == 0) {
			pageTo = 10;
		}

	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		ServiceResourceDTO dto = new ServiceResourceDTO();
		return new QueryServiceResourceEJBEvent(dto, pageFrom, pageTo,
				QueryServiceResourceEJBEvent.QUERY_RESOURCE_BRIEF_LIST);
	}

}
