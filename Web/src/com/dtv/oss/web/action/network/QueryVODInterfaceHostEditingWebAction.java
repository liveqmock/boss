package com.dtv.oss.web.action.network;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VODInterfaceHostDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.QueryCommandResponseImpl;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVODInterfaceEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author 260425w 2006-5-30 15:48:24
 */
public class QueryVODInterfaceHostEditingWebAction extends QueryWebAction {

	private static final String EDITING_TYPE = "editing_type";

	private static final String EDITING_TYPE_NEW = "new";

	private static final String EDITING_TYPE_UPDATE = "update";

	private static final String EDITING_TYPE_VIEW = "view";

	private String editingType;

	public EJBEvent perform(HttpServletRequest request)
			throws WebActionException {
		editingType = (String) request.getParameter(EDITING_TYPE);
		if (editingType == null
				|| (editingType = editingType.trim()).length() == 0) {
			editingType = EDITING_TYPE_VIEW;
		}
		System.out.println(getClass() + ",editingtype:" + editingType);
		request.setAttribute(EDITING_TYPE, editingType);
		if (EDITING_TYPE_VIEW.equals(editingType)
				|| EDITING_TYPE_UPDATE.equals(editingType)) {
			return super.perform(request);
		}
		return null;
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		VODInterfaceHostDTO dto = new VODInterfaceHostDTO();
		setDtoByRequest(dto, request);
		QueryVODInterfaceEJBEvent event = new QueryVODInterfaceEJBEvent(dto, 1,
				1, QueryVODInterfaceEJBEvent.QUERY_HOST_DETAIL);
		return event;

	}

	private void setDtoByRequest(VODInterfaceHostDTO dto,
			HttpServletRequest request) {
		String hostId = request.getParameter("txtHostID");
		if (hostId == null || (hostId = hostId.trim()).length() == 0) {
			hostId = "0";
		}
		dto.setHostID(Integer.valueOf(hostId).intValue());
		System.out.println(getClass() + ":hostID:" + dto.getHostID());
	}

	public void doEnd(HttpServletRequest request, CommandResponse cr) {

		if (cr == null) {
			List list = new ArrayList();
			VODInterfaceHostDTO dto = new VODInterfaceHostDTO();
			list.add(dto);
			cr = new QueryCommandResponseImpl(1, list, 0, 1);
		}
		super.doEnd(request, cr);
	}

	public static void main(String[] args) {
		System.out.println(Postern.getAllProductIDAndName());
	}
}
