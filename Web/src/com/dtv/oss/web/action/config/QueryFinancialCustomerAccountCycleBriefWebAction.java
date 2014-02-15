package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustAcctCycleCfgDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryFinancialSettingEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author 260425w 2006-5-30 15:48:24
 */
public class QueryFinancialCustomerAccountCycleBriefWebAction extends
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

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		if (pageTo == 0) {
			pageTo = 10;
		}
		QueryFinancialSettingEJBEvent ejbEvent = new QueryFinancialSettingEJBEvent(
				pageFrom, pageTo);
		int queryActionType = QueryFinancialSettingEJBEvent.QUERY_CUSTOMER_ACCOUNT_CYCLE_BRIEF_LIST;
		CustAcctCycleCfgDTO cacDto = new CustAcctCycleCfgDTO();
		// setCustomerAccountCycleType(cacDto, request);
		ejbEvent.setType(queryActionType);
		ejbEvent.setDto(cacDto);
		return ejbEvent;
	}

	private void setCustomerAccountCycleType(CustAcctCycleCfgDTO dto,
			HttpServletRequest request) {
		// T_CustAcctCycleCfg
		// SEQNO 1 1 N NUMBER (10) NULL
		// BILLINGCYCLETYPEID 2 N NUMBER (10) NULL
		// CUSTOMERTYPE 3 Y VARCHAR2 (5) NULL
		// ACCOUNTTYPE 4 Y VARCHAR2 (5) NULL
		// DT_CREATE 5 Y TIMESTAMP(6) NULL
		// DT_LASTMOD 6 Y TIMESTAMP(6) NULL
		String accountType = (String) request.getParameter("txtAccountType");
		String seqNo = (String) request.getParameter("txtSeqNo");
		String billingCycleTypeId = (String) request
				.getParameter("txtBillingCycleTypeId");
		String customerType = (String) request.getParameter("txtCustomerType");
		// String dtLastMod = (String) request.getParameter("txtDtLastMod");
		if (WebUtil.StringHaveContent(seqNo)) {
			dto.setSeqNo(WebUtil.StringToInt(seqNo));
		}
		if (WebUtil.StringHaveContent(accountType)) {
			dto.setAccountType(accountType);
		}
		if (WebUtil.StringHaveContent(billingCycleTypeId)) {
			dto.setBillingCycleTypeId(WebUtil.StringToInt(billingCycleTypeId));
		}
		if (WebUtil.StringHaveContent(customerType)) {
			dto.setCustomerType(customerType);
		}
	}

}
