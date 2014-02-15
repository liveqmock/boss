package com.dtv.oss.web.action.config;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BillingCycleTypeDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryFinancialSettingEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author 260425w 2006-5-30 15:48:24
 */
public class QueryFinancialAccountCycleTypeBriefWebAction extends
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
		int queryActionType = QueryFinancialSettingEJBEvent.QUERY_ACCOUNT_CYCLE_TYPE_BRIEF_LIST;
		Object dto = null;
		BillingCycleTypeDTO bctDto = new BillingCycleTypeDTO();
		// setAccountCycleType(bctDto, request);
		dto = bctDto;

		ejbEvent.setType(queryActionType);
		ejbEvent.setDto(dto);
		return ejbEvent;
	}

	private void setAccountCycleType(BillingCycleTypeDTO dto,
			HttpServletRequest request) {
		// 序号ID 1 1 N NUMBER (10) NULL
		// CTYPE 3 N VARCHAR2 (5) NULL
		// 名称 NAME 2 Y VARCHAR2 (50) NULL
		// 描述 DESCRIPTION 4 Y VARCHAR2 (200) NULL
		// 状态 STATUS 20 Y VARCHAR2 (5) Null
		// 对应计费周期类型 BILLINGCYCLETYPEID 11 Y NUMBER (10) NULL
		// 第一个有效周期 FIRSTVALIDINVOICECYCLEID 12 Y NUMBER (10) NULL
		// 时间长度 CYCLECOUNT 13 Y NUMBER (10) NULL
		//
		// 租费开始时间段 RENTCYCLEBDATE 6 N DATE NULL
		// 使用费用开始时间 OTHERCYCLEBDATE 7 Y DATE NULL
		// 帐单生成截止日 ENDINVOICINGDATE 19 Y DATE
		// 帐单过期日期 INVOICEDUEDATE 10 Y DATE NULL
		String id = (String) request.getParameter("txtId");
		String cType = "I";
		String name = (String) request.getParameter("txtName");
		String description = (String) request.getParameter("txtDescription");
		String status = (String) request.getParameter("txtStatus");
		String billingCycleTypeId = (String) request
				.getParameter("txtBillingCycleTypeId");
		String firstValidInvoiceCycleId = (String) request
				.getParameter("txtFirstValidInvoiceCycleId");
		String cycleCount = (String) request.getParameter("txtCycleCount");
		String rentCycleBDate = (String) request.getParameter("txtCycleBDate");
		String otherCycleBDate = (String) request
				.getParameter("txtOtherCycleBDate");
		String endInvoicingDate = (String) request
				.getParameter("txtEndInvoicingDate");
		String invoiceDueDate = (String) request
				.getParameter("txtInvoiceDueDate");
		if (WebUtil.StringHaveContent(id)) {
			dto.setId(WebUtil.StringToInt(id));
		}
		dto.setCType(cType);
		if (WebUtil.StringHaveContent(name)) {
			dto.setName(name);
		}
		if (WebUtil.StringHaveContent(description)) {
			dto.setDescription(description);
		}
		if (WebUtil.StringHaveContent(status)) {
			dto.setStatus(status);
		}
		if (WebUtil.StringHaveContent(billingCycleTypeId)) {
			dto.setBillingCycleTypeId(WebUtil.StringToInt(billingCycleTypeId));
		}
		if (WebUtil.StringHaveContent(firstValidInvoiceCycleId)) {
			dto.setFirstValidInvoiceCycleId(WebUtil
					.StringToInt(firstValidInvoiceCycleId));
		}
		if (WebUtil.StringHaveContent(cycleCount)) {
			dto.setCycleCount(WebUtil.StringToInt(cycleCount));
		}
		if (WebUtil.StringHaveContent(rentCycleBDate)) {
			dto.setRentCyclebDate(Timestamp.valueOf(rentCycleBDate));
		}
		// otherCycleBDate endInvoicingDate invoiceDueDate
		if (WebUtil.StringHaveContent(otherCycleBDate)) {
			dto.setOtherCycleBDate(Timestamp.valueOf(otherCycleBDate));
		}
		if (WebUtil.StringHaveContent(endInvoicingDate)) {
			dto.setEndInvoicingDate(Timestamp.valueOf(endInvoicingDate));
		}
		if (WebUtil.StringHaveContent(invoiceDueDate)) {
			dto.setInvoiceDueDate(Timestamp.valueOf(invoiceDueDate));
		}
	}

}
