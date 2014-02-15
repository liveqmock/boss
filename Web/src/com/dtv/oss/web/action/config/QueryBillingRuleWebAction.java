package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BillingRuleDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryBillingRuleWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		BillingRuleDTO dto =new BillingRuleDTO();
		
//		 »î¶¯id
	    if (WebUtil.StringHaveContent(request.getParameter("txtID")))
	        dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtEventClass")))
	        dto.setEventClass(WebUtil.StringToInt(request.getParameter("txtEventClass")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtProductID")))
	        dto.setProductId(WebUtil.StringToInt(request.getParameter("txtProductID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtProductPackageID")))
	        dto.setPackageId(WebUtil.StringToInt(request.getParameter("txtProductPackageID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtEventReason")))
	        dto.setEventReason(request.getParameter("txtEventReason"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtDestProductID")))
	        dto.setDestProductId(WebUtil.StringToInt(request.getParameter("txtDestProductID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCustType")))
	        dto.setBrcntIdCustType(WebUtil.StringToInt(request.getParameter("txtCustType")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCampaign")))
	        dto.setBrcntIdCampaign(WebUtil.StringToInt(request.getParameter("txtCampaign")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtAcctItem")))
	        dto.setBrcntIdAcctType(WebUtil.StringToInt(request.getParameter("txtAcctItem")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
	        dto.setStatus(request.getParameter("txtStatus"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCatvTermType")))
	        dto.setBrCntIDCATVTermType(WebUtil.StringToInt(request.getParameter("txtCatvTermType")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCableType")))
	        dto.setBrCntIDCableType(WebUtil.StringToInt(request.getParameter("txtCableType")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtDoubleFlag")))
	        dto.setBrCntIDBiDrectionFlag(WebUtil.StringToInt(request.getParameter("txtDoubleFlag")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtUserType")))
	        dto.setBrCntIDUserType(WebUtil.StringToInt(request.getParameter("txtUserType")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtMarketSegmentID")))
	        dto.setBrCntIDMarketSegmentID(WebUtil.StringToInt(request.getParameter("txtMarketSegmentID")));
	    return new ConfigQueryEJBEvent(dto, pageFrom, pageTo, ConfigQueryEJBEvent.CONFIG_BILLING_RULE_QUERY);
	    
	}
    
}
