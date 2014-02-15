package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VODInterfaceProductDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVODInterfaceEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author 260425w 2006-5-30 15:48:24
 */
public class QueryVODInterfaceProductWebAction extends QueryWebAction {

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
		VODInterfaceProductDTO dto = new VODInterfaceProductDTO();
		setDtoByRequest(dto);
		QueryVODInterfaceEJBEvent ejbEvent = new QueryVODInterfaceEJBEvent(dto,
				pageFrom, pageTo, QueryVODInterfaceEJBEvent.QUERY_PRODUCT_LIST);
		return ejbEvent;
	}

	private void setDtoByRequest(VODInterfaceProductDTO dto) {

	}
}
