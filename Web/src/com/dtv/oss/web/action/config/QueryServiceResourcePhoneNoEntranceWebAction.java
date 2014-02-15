package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ServiceResourceDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.QueryCommandResponseImpl;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryServiceResourceEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author 260425w 2006-5-30 15:48:24
 */
public class QueryServiceResourcePhoneNoEntranceWebAction extends
		QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		ServiceResourceDTO dto = new ServiceResourceDTO();
		dto.setResourceName(request.getParameter("txtResourceName"));
		// System.out
		// .println(getClass() + "resourceName:" + dto.getResourceName());
		return new QueryServiceResourceEJBEvent(dto, pageFrom, pageTo,
				QueryServiceResourceEJBEvent.QUERY_RESOURCE_OBJECT);
	}
	
	public void doEnd(HttpServletRequest request, CommandResponse cr) {
		System.out.println("*************************************************");
		System.out.println(this.getClass() + ";doEnd.");
		System.out.println("cmdRespose:" + cr);
		if (cr == null) {
			List list = new ArrayList();
			ServiceResourceDTO dto = new ServiceResourceDTO();
			list.add(dto);
			cr = new QueryCommandResponseImpl(1, list, 0, 1);
		}
		super.doEnd(request, cr);		
	}

}
