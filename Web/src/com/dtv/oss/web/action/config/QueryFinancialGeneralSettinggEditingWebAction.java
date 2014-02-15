package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.FinancialSettingDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.QueryCommandResponseImpl;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryFinancialSettingEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author 260425w 2006-5-30 15:48:24
 */
public class QueryFinancialGeneralSettinggEditingWebAction extends
		QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		QueryFinancialSettingEJBEvent ejbEvent = new QueryFinancialSettingEJBEvent(
				0, 1);
		FinancialSettingDTO fsDto = new FinancialSettingDTO();
		setGeneralSettingDTO(fsDto, request);
		ejbEvent.setDto(fsDto);
		ejbEvent.setType(QueryFinancialSettingEJBEvent.QUERY_GENERAL_SETTING);
		return ejbEvent;
	}

	/**
	 * 
	 * @param dto
	 */
	private void setGeneralSettingDTO(FinancialSettingDTO dto,
			HttpServletRequest request) {
		// 名称NAME
		// 使用全局统一帐期类型UNIFIEDCYCLEFLAG
		// 是否计算滞纳金CALCULATEPUNISHMENTFLAG
		// 滞纳金开始计算天数PUNISHMENTSTARTDATE
		// 滞纳金帐目类型PUNISHMENTACCTITEMTYPEID
		// 日滞纳金比率PUNISHMENTRATE

		// 日常帐单过期天数INVOICEDUEDATE
		// 欠费付清自动恢复标志AUTORESUMECPFLAG
		// 零头处理模式SMAILLCHANGEPROCESSMODE
		// 预存抵扣方式PREPAYMENTDEDUCTIONMODE
		// 帐单累计模式 INVOICEACCUMULATEMODE
		String name = (String) request.getParameter("txtName");
		String unifiedCycleFlag = (String) request
				.getParameter("txtUnifiedCycleFlag");
		String calculatedPunishmentFlag = (String) request
				.getAttribute("txtCalculatePunishmentFlag");
		String punishmentStartDate = (String) request
				.getParameter("txtPunishmentStartDate");
		String punishmentAccountTypeId = (String) request
				.getParameter("txtPunishmentAccountTypeId");
		String punishmentRate = (String) request
				.getParameter("txtPunishmentRate");
		String invoiceDueDate = (String) request
				.getParameter("txtInvoiceDueDate");
		String autoResumeCpFlag = (String) request
				.getParameter("txtAutoResumeCpFlag");
		String smallChangeProcessMode = (String) request
				.getParameter("txtSmallChangeProcessMode");
		String prepaymentDeductionMode = (String) request
				.getParameter("txtPrepaymentDeductionMode");
		String invoiceAccumulateMode = (String) request
				.getParameter("txtInvoiceAccumulateMode");
		if (WebUtil.StringHaveContent(name)) {
			dto.setName(name);
		}
		if (WebUtil.StringHaveContent(unifiedCycleFlag)) {
			dto.setUnifiedCycleFlag(unifiedCycleFlag);
		}
		if (WebUtil.StringHaveContent(calculatedPunishmentFlag)) {
			dto.setCalculatePunishmentFlag(calculatedPunishmentFlag);
		}
		if (WebUtil.StringHaveContent(punishmentStartDate)) {
			dto
					.setPunishmentStartDate(WebUtil
							.StringToInt(punishmentStartDate));
		}
		if (WebUtil.StringHaveContent(punishmentAccountTypeId)) {
			dto.setPunishmentAcctItemTypeID(punishmentAccountTypeId);
		}
		if (WebUtil.StringHaveContent(punishmentRate)) {
			dto.setPunishmenTrate(WebUtil.StringToInt(punishmentRate));
		}
		if (WebUtil.StringHaveContent(invoiceDueDate)) {
			dto.setInvoiceDueDate(WebUtil.StringToInt(invoiceDueDate));
		}
		if (WebUtil.StringHaveContent(autoResumeCpFlag)) {
			dto.setAutoResumeCpFlag(autoResumeCpFlag);
		}
		if (WebUtil.StringHaveContent(smallChangeProcessMode)) {
			dto.setSmallchangeProcessMode(smallChangeProcessMode);
		}
		if (WebUtil.StringHaveContent(prepaymentDeductionMode)) {
			dto.setPrepaymentDeductionMode(prepaymentDeductionMode);
		}
		if (WebUtil.StringHaveContent(invoiceAccumulateMode)) {
			dto.setInvoiceAccumulateMode(invoiceAccumulateMode);
		}
	}

	public void doEnd(HttpServletRequest request, CommandResponse cmdResponse) {
		System.out.println("*************************************************");
		System.out.println(this.getClass() + ";doEnd.");
		System.out.println("cmdRespose:" + cmdResponse);
		if (cmdResponse == null) {
			List list = new ArrayList();
			FinancialSettingDTO dto = new FinancialSettingDTO();
			list.add(dto);
			cmdResponse = new QueryCommandResponseImpl(1, list, 0, 1);
			request.setAttribute("already_exist", "false");
		} else {
			System.out.println("cmdRespose.getPayload:"
					+ cmdResponse.getPayload());
			List list = (List) cmdResponse.getPayload();
			if (list != null && !list.isEmpty()) {
				request.setAttribute("already_exist", "true");
			} else {
				list = new ArrayList();
				FinancialSettingDTO dto = new FinancialSettingDTO();
				list.add(dto);
				cmdResponse.setPayload(list);
				request.setAttribute("already_exist", "false");
			}
		}
		super.doEnd(request, cmdResponse);

	}

}
