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
import com.dtv.oss.web.exception.WebActionException;

public class QueryServiceResourceObjectEditingWebAction extends QueryWebAction {

	public EJBEvent perform(HttpServletRequest request)
			throws WebActionException {
		String editingType = (String) request.getParameter("editing_type");
		request.setAttribute("editing_type", editingType);
		if (!"new".equals(editingType)) {			
			return super.perform(request);
		}else{
			String resouceName = request.getParameter("txtResourceName");
			request.setAttribute("txtResourceName",resouceName);
			String resouceType = request.getParameter("txtResourceType");
			request.setAttribute("txtResourceType",resouceType);
			
		}
		return null;
	}

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
		String editingType = (String) request.getParameter("editing_type");
		request.setAttribute("editing_type", editingType);
		if (cr == null) {
			List list = new ArrayList();
			ServiceResourceDTO dto = new ServiceResourceDTO();
			list.add(dto);
			cr = new QueryCommandResponseImpl(1, list, 0, 1);
		}
		super.doEnd(request, cr);
		String actionType = (String) request.getParameter("editing_type");
		if (actionType == null
				|| (actionType = actionType.trim()).length() == 0) {
			actionType = "new";
		}
		request.setAttribute("editing_type", actionType);
	}
}
