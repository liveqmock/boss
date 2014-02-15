package com.dtv.oss.web.action.config;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BillingCycleTypeDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryFinancialSettingEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author 260425w 2006-5-30 15:48:24
 */
public class QueryFinancialChargeCycleTypeBriefWebAction extends QueryWebAction {
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
		int queryActionType = QueryFinancialSettingEJBEvent.QUERY_CHARGE_CYCLE_TYPE_BRIEF_LIST;

		BillingCycleTypeDTO bctDto = new BillingCycleTypeDTO();
		// setChargeCycleType(bctDto, request);
		ejbEvent.setType(queryActionType);
		ejbEvent.setDto(bctDto);
		return ejbEvent;
	}

	private void setChargeCycleType(BillingCycleTypeDTO dto,
			HttpServletRequest request) {
		// ID 1 1 N NUMBER (10) NULL
		// ���� NAME 2 Y VARCHAR2 (50) NULL
		// ״̬ STATUS 20 Y VARCHAR2 (5) Null
		// ���� DESCRIPTION 4 Y VARCHAR2 (200) NULL
		// �Ƿ�����Ԥ�Ʒ� ALLOWPREBILLINGFLAG 8 N VARCHAR2 (5) NULL
		// ��ʼ�Ʒѱ�־ STARTBILLINGFLAG 9 N VARCHAR2 (5) NULL
		// ͳһ�����־ UNIFIEDCYCLEFLAG 5 Y VARCHAR2 (5) NULL
		// ��һ���Ʒѵ����� FIRSTVALIDINVOICECYCLEID 12 Y NUMBER (10) NULL
		// ����ģʽ RENTDISCTMODE 15 Y VARCHAR2 (5) NULL
		// ������ RENTDIVIDINGDATE 16 Y DATE NULL
		// ͳһ���ڱ�־ UNIFIEDDISCTFLAG 14 N VARCHAR2 (5) NULL
		// �����ѼƷ���ʼ�� OTHERCYCLEBDATE 7 Y DATE NULL
		// ��ѼƷ���ʼ�� RENTCYCLEBDATE 6 N DATE NULL

		String id = (String) request.getParameter("txtId");
		String cType = "B";
		String name = (String) request.getParameter("txtName");
		String description = (String) request.getParameter("txtDescription");
		String status = (String) request.getParameter("txtStatus");
		String allowPrebillingFlag = (String) request
				.getParameter("txtAllowPrebillingFlag");
		String startBillingFlag = (String) request
				.getParameter("txtStartBillingFlag");
		String unifiedCycleFlag = (String) request
				.getParameter("txtUnifiedCycleFlag");
		String firstValidInvoiceCycleId = (String) request
				.getParameter("txtFirstValidInvoiceCycleId");
		String rentDisctMode = (String) request
				.getParameter("txtRentDisctMode");
		String rentDividingDate = (String) request
				.getParameter("txtRentDividingDate");
		String unifiedDisctFlag = (String) request
				.getParameter("txtUnifiedDisctFlag");
		String otherCycleBDate = (String) request
				.getParameter("txtOtherCycleBDate");
		String rentCycleBDate = (String) request
				.getParameter("txtRentCycleBDate");
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
		if (WebUtil.StringHaveContent(allowPrebillingFlag)) {
			dto.setAllowPrebillingFlag(allowPrebillingFlag);
		}
		if (WebUtil.StringHaveContent(startBillingFlag)) {
			dto.setStartBillingFlag(startBillingFlag);
		}
		if (WebUtil.StringHaveContent(unifiedCycleFlag)) {
			dto.setUnifiedCycleFlag(unifiedCycleFlag);
		}
		if (WebUtil.StringHaveContent(firstValidInvoiceCycleId)) {
			dto.setFirstValidInvoiceCycleId(WebUtil
					.StringToInt(firstValidInvoiceCycleId));
		}
		if (WebUtil.StringHaveContent(rentDisctMode)) {
			dto.setUnifiedCycleFlag(rentDisctMode);
		}
		if (WebUtil.StringHaveContent(rentDividingDate)) {
			dto.setUnifiedCycleFlag(rentDividingDate);
		}
		if (WebUtil.StringHaveContent(unifiedDisctFlag)) {
			dto.setUnifiedCycleFlag(unifiedDisctFlag);
		}

		if (WebUtil.StringHaveContent(rentCycleBDate)) {
			dto.setRentCyclebDate(Timestamp.valueOf(rentCycleBDate));
		}
		// otherCycleBDate endInvoicingDate invoiceDueDate
		if (WebUtil.StringHaveContent(otherCycleBDate)) {
			dto.setOtherCycleBDate(Timestamp.valueOf(otherCycleBDate));
		}

	}

}
