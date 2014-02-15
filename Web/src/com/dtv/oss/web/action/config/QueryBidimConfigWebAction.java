package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BidimConfigSettingDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryBidimConfigWebAction extends QueryWebAction {
	// action_type
	public static final String ACTION_TYPE = "query_type";

	public static final String ACTION_TYPE_ENTRANCE = "entrance";

	public static final String ACTION_TYPE_QUERY = "result";

	// paramete names
	public static final String PARAMETER_CONFIG_TYPE = "txtConfigType";

	public static final String PARAMETER_CONFIG_SUB_TYPE = "txtConfigSubType";

	public static final String PARAMETER_VALUE_TYPE = "txtValueType";

	public static final String PARAMETER_STATUS = "txtStatus";

	public EJBEvent perform(HttpServletRequest request)
			throws WebActionException {
		// CurrentCustomer.ClearCurrentCustomer(request);
		String actionType = request.getParameter(ACTION_TYPE);
		System.out.println("#################################");
		System.out
				.println(" in QueryBidimConfigWebAction.perform(),actionType="
						+ actionType);
		System.out.println("#################################");
		if (actionType != null && actionType.trim().equals(ACTION_TYPE_QUERY)) {
			return super.perform(request);
		}

		return null;

	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		System.out.println("#################################");
		System.out.println("in QueryBidimConfigWebAction.encapsulateData");
		System.out.println("#################################");
		BidimConfigSettingDTO dto = new BidimConfigSettingDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtConfigType"))) {
			dto.setConfigType(request.getParameter("txtConfigType"));
			request.setAttribute("txtConfigType", dto.getConfigType());
		}
		/*
		 * if (WebUtil.StringHaveContent(request.getParameter("txtID")))
		 * dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
		 */
		if (WebUtil.StringHaveContent(request.getParameter("txtConfigSubType"))) {
			dto.setConfigSubType(request.getParameter("txtConfigSubType"));
			request.setAttribute("txtConfigSubType", dto.getConfigSubType());
		}
		
		if (WebUtil.StringHaveContent(request.getParameter("txtValueType"))) {
			dto.setValueType(request.getParameter("txtValueType"));
			request.setAttribute("txtValueType", dto.getValueType());
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtStatus"))) {
			dto.setStatus(request.getParameter("txtStatus"));
			request.setAttribute("txtStatus", dto.getStatus());
		}// txtFromtxtTotxtPage
		request.setAttribute("has_queried", "true");

		if (WebUtil.StringHaveContent(request.getParameter("txtFrom"))) {
			request.setAttribute("txtFrom", request.getParameter("txtFrom"));
		} else {
			request.setAttribute("txtFrom", "0");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtTo"))) {
			request.setAttribute("txtTo", request.getParameter("txtTo"));
		} else {
			request.setAttribute("txtTo", "10");
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtPage"))) {
			request.setAttribute("txtPage", request.getParameter("txtPage"));
		}

		return new ConfigQueryEJBEvent(dto, pageFrom, pageTo,
				ConfigQueryEJBEvent.QUERY_BIDIM_CONFIG);

	}
}
