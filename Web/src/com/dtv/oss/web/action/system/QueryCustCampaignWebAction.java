package com.dtv.oss.web.action.system;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CPCampaignMapDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.service.ejbevent.system.SystemQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author fiona
 * @version 1.0
 */

public class QueryCustCampaignWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		CommonQueryConditionDTO dto = new CommonQueryConditionDTO();

		if (WebUtil.StringHaveContent(request.getParameter("txtCCID")))
			dto.setSpareStr1(request.getParameter("txtCCID"));
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			dto.setSpareStr2(request.getParameter("txtCustomerID"));
		if (WebUtil.StringHaveContent(request.getParameter("txtCampaignID")))
			dto.setSpareStr3(request.getParameter("txtCampaignID"));
		if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setSpareStr4(request.getParameter("txtStatus"));
		if (WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
			dto.setSpareStr5(request.getParameter("txtAccountID"));
		if (WebUtil.StringHaveContent(request
				.getParameter("txtServiceAccountID")))
			dto.setSpareStr6(request.getParameter("txtServiceAccountID"));

		if (WebUtil.StringHaveContent(request.getParameter("txtDateFromF")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request
					.getParameter("txtDateFromF")));
		if (WebUtil.StringHaveContent(request.getParameter("txtDateFromT")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request
					.getParameter("txtDateFromT")));
		if (WebUtil.StringHaveContent(request.getParameter("txtDateToF")))
			dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request
					.getParameter("txtDateToF")));
		if (WebUtil.StringHaveContent(request.getParameter("txtDateToT")))
			dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request
					.getParameter("txtDateToT")));

		if ("campaign".equalsIgnoreCase(request.getParameter("txtActionType"))) {
			dto.setSpareStr20(CommonKeys.CAMPAIGNCAMPAIGNTYPE_NORMAL);
			return new SystemQueryEJBEvent(dto, pageFrom, pageTo,
					SystemQueryEJBEvent.QUERY_CUSTOMER_CAMPAIGN);
		} else if ("campaignMap".equalsIgnoreCase(request
				.getParameter("txtActionType"))) {
			dto.setSpareStr20(CommonKeys.CAMPAIGNCAMPAIGNTYPE_NORMAL);
			return new SystemQueryEJBEvent(dto, pageFrom, pageTo,
					SystemQueryEJBEvent.QUERY_CUSTOMER_CAMPAIGN_MAP);
		} else if ("bundle".equalsIgnoreCase(request
				.getParameter("txtActionType"))) {
			dto.setSpareStr20(CommonKeys.CAMPAIGNCAMPAIGNTYPE_OPEN);
			return new SystemQueryEJBEvent(dto, pageFrom, pageTo,
					SystemQueryEJBEvent.QUERY_CUSTOMER_BUNDLE);
		} else if ("bundleMap".equalsIgnoreCase(request
				.getParameter("txtActionType"))) {
			dto.setSpareStr20(CommonKeys.CAMPAIGNCAMPAIGNTYPE_OPEN);
			return new SystemQueryEJBEvent(dto, pageFrom, pageTo,
					SystemQueryEJBEvent.QUERY_CUSTOMER_BUNDLE_MAP);
		} else if ("campaignList".equalsIgnoreCase(request
				.getParameter("txtActionType"))) {
			CPCampaignMapDTO cpCampaignMapDTO = new CPCampaignMapDTO();
			if (!WebUtil.StringHaveContent(request.getParameter("txtPsId")))
				throw new WebActionException("参数错误，没有客户产品ID");
			cpCampaignMapDTO.setCustProductID(WebUtil.StringToInt(request
					.getParameter("txtPsId")));

			return new SystemQueryEJBEvent(cpCampaignMapDTO, pageFrom, pageTo,
					CsrQueryEJBEvent.QUERY_CAMPAIGN_LIST);
		} else
			throw new WebActionException("参数错误，查询动作类型未知！");
	}
}
