package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.QueryCommandResponseImpl;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryServiceResourceEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author 260425w 2006-5-30 15:48:24
 */
public class QueryServiceResourcePhoneNoEditingWebAction extends QueryWebAction {
	public EJBEvent perform(HttpServletRequest request)
			throws WebActionException {
		String editingType = (String) request.getParameter("editing_type");
		request.setAttribute("editing_type", editingType);
		if (!"new".equals(editingType)) {

			return super.perform(request);
		}

		// else {
		// ResourcePhoneNoDTO dto = new ResourcePhoneNoDTO();
		// String resourceName = request.getParameter("txtResourceName");
		// dto.setResourceName(resourceName);
		// request.setAttribute("QueryConditionDto", dto);
		String resourceName = request.getParameter("txtResourceName");
		request.setAttribute("txtResourceName", resourceName);
		String resourceType = request.getParameter("txtResourceType");
		request.setAttribute("txtResourceType", resourceType);
		// }
		return null;
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		ResourcePhoneNoDTO dto = new ResourcePhoneNoDTO();
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
			ResourcePhoneNoDTO dto = new ResourcePhoneNoDTO();
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
