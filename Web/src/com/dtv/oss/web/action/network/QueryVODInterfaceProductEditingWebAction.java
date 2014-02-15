package com.dtv.oss.web.action.network;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VODInterfaceProductDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.QueryCommandResponseImpl;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVODInterfaceEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author 260425w 2006-5-30 15:48:24
 */
public class QueryVODInterfaceProductEditingWebAction extends QueryWebAction {

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
				|| EDITING_TYPE_UPDATE.equals(editingType = editingType.trim())) {
			return super.perform(request);
		}
		return null;
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		VODInterfaceProductDTO dto = new VODInterfaceProductDTO();
		setDtoByRequest(dto, request);
		QueryVODInterfaceEJBEvent event = new QueryVODInterfaceEJBEvent(dto, 1,
				1, QueryVODInterfaceEJBEvent.QUERY_PRODUCT_DETAIL);
		return event;

	}

	private void setDtoByRequest(VODInterfaceProductDTO dto,
			HttpServletRequest request) {
		String smsProductID = request.getParameter("txtSmsProductID");
		if (smsProductID == null
				|| (smsProductID = smsProductID.trim()).length() == 0) {
			smsProductID = "0";
		}
		dto.setSmsProductID(Integer.valueOf(smsProductID).intValue());
	}

	public void doEnd(HttpServletRequest request, CommandResponse cr) {

		if (cr == null) {
			List list = new ArrayList();
			VODInterfaceProductDTO dto = new VODInterfaceProductDTO();
			list.add(dto);
			cr = new QueryCommandResponseImpl(1, list, 0, 1);
		}
		super.doEnd(request, cr);
	}

}
