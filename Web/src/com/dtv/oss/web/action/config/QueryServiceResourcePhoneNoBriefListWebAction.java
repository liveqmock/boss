package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryServiceResourceEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author 260425w 2006-5-30 15:48:24
 */
public class QueryServiceResourcePhoneNoBriefListWebAction extends
		QueryWebAction {
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

	public EJBEvent perform(HttpServletRequest request)
			throws WebActionException {
		String queryType = request.getParameter("query_type");
		request.setAttribute("query_type",queryType);
		if (queryType != null && "entrance".equals(queryType)) {
			ResourcePhoneNoDTO dto = new ResourcePhoneNoDTO();
			String resourceName = request.getParameter("txtResourceName");
			dto.setResourceName(resourceName);
			request.setAttribute("QueryConditionDto", dto);
			return null;
		}
		return super.perform(request);
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		ResourcePhoneNoDTO dto = new ResourcePhoneNoDTO();

		String resourceName = request.getParameter("txtResourceName");
		String status = request.getParameter("txtStatus");
		String countryCode = request.getParameter("txtCountryCode");
		String areaCode = request.getParameter("txtAreaCode");
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictId"))){
			int districtId = WebUtil.StringToInt(request.getParameter("txtDistrictId"));
			dto.setDistrictId(districtId);
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtGrade"))){
			dto.setGrade(request.getParameter("txtGrade"));
		}

		String phoneBegin = request.getParameter("txtPhoneNoBegin");
		String phoneEnd = request.getParameter("txtPhoneNoEnd");
		dto.setResourceName(resourceName);
		dto.setStatus(status);
		dto.setCountryCode(countryCode);
		dto.setAreaCode(areaCode);
		dto.setPhoneNoBegin(phoneBegin);
		dto.setPhoneNoEnd(phoneEnd);
		request.setAttribute("QueryConditionDto", dto);

		return new QueryServiceResourceEJBEvent(dto, pageFrom, pageTo,
				QueryServiceResourceEJBEvent.QUERY_RESOURCE_DETAIL_BRIEF_LIST);
	}

}
