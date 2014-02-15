package com.dtv.oss.web.action.config;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BillingCycleTypeDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.QueryCommandResponseImpl;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryFinancialSettingEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author 260425w 2006-5-30 15:48:24
 */
public class QueryFinancialChargeCycleTypeEditingWebAction extends
		QueryWebAction {
	private static final String EDITING_TYPE = "editing_type";

	private static final String EDITING_TYPE_NEW = "new";

	private static final String EDITING_TYPE_UPDATE = "view_update";

	public EJBEvent perform(HttpServletRequest request)
			throws WebActionException {
		String editingType = (String) request.getParameter(EDITING_TYPE);
		System.out.println("*************************************************");
		System.out.println(this.getClass() + ";one.");
		if (editingType != null
				&& EDITING_TYPE_UPDATE.equals(editingType = editingType.trim())) {
			System.out.println(this.getClass() + ";two.");
			return super.perform(request);

		}
		System.out.println(this.getClass() + ";four.");
		return null;
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		String editingType = (String) request.getParameter(EDITING_TYPE);

		if (editingType != null
				&& EDITING_TYPE_UPDATE.equals(editingType = editingType.trim())) {
			BillingCycleTypeDTO dto = new BillingCycleTypeDTO();
			setChargeCycleType(dto, request);
			QueryFinancialSettingEJBEvent ejbEvent = new QueryFinancialSettingEJBEvent(0,1);
			ejbEvent.setDto(dto);
			ejbEvent
					.setType(QueryFinancialSettingEJBEvent.QUERY_CHARGE_CYCLE_TYPE_DETAIL);
			return ejbEvent;
		}
		return null;
	}

	public void doEnd(HttpServletRequest request, CommandResponse cr) {
		System.out.println("*************************************************");
		System.out.println(this.getClass() + ";doEnd.");
		System.out.println("cmdRespose:" + cr);
		if (cr == null) {
			List list = new ArrayList();
			BillingCycleTypeDTO dto = new BillingCycleTypeDTO();
			list.add(dto);
			cr = new QueryCommandResponseImpl(1, list, 0, 1);
		}
		super.doEnd(request, cr);
		String actionType = (String) request.getParameter("ACTION_TYPE");
		if (actionType == null
				|| (actionType = actionType.trim()).length() == 0) {
			actionType = EDITING_TYPE_NEW;
		}
		request.setAttribute(EDITING_TYPE, actionType);
	}

	// /**
	// * the action type parameter name
	// */
	// private static final String QUER_TYPE = "query_type";
	//
	// /**
	// * T_FinancialSetting
	// */
	// private static final String QUER_TYPE_GENERAL_SETTING =
	// "general_setting";
	//
	// /**
	// * the charge cycle objects list
	// */
	// private static final String QUER_TYPE_CHARGE_CYCLE_TYPE_LIST =
	// "charge_cycle_type_list";
	//
	// /**
	// * the detail of a charge cycle object
	// */
	// private static final String QUER_TYPE_CHARGE_CYCLE_TYPE_DETAIL =
	// "charge_cycle_type_detail";
	//
	// /**
	// * the account cycle objects list
	// */
	// private static final String QUER_TYPE_ACCOUNT_CYCLE_TYPE_LIST =
	// "account_cycle_type_list";
	//
	// /**
	// * the detail of a account cycle object
	// */
	// private static final String QUER_TYPE_ACCOUNT_CYCLE_TYPE_DETAIL =
	// "account_cycle_type_detail";
	//
	// /**
	// * the list of references to account cycle objects by customers
	// */
	// private static final String QUER_TYPE_CUSTOMER_ACCOUNT_CYCLE_LIST =
	// "customer_account_cycle_list";
	//
	// /**
	// * the detail of a reference to a account cycle object by one customer
	// */
	// private static final String QUER_TYPE_CUSTOMER_ACCOUNT_CYCLE_DETAIL =
	// "customer_account_cycle_detail";
	//
	// /**
	// * the list of account type objects
	// */
	// private static final String QUER_TYPE_ACCOUNT_TYPE_LIST =
	// "account_type_list";
	//
	// /**
	// * the detail of of a account type object
	// */
	// private static final String QUER_TYPE_ACCOUNT_TYPE_DETAIL =
	// "account_type_detail";
	//
	// protected EJBEvent encapsulateData(HttpServletRequest request)
	// throws Exception {
	// QueryFinancialSettingEJBEvent ejbEvent = new
	// QueryFinancialSettingEJBEvent();
	// int queryActionType =
	// QueryFinancialSettingEJBEvent.QUERY_GENERAL_SETTING;
	//
	// String queryType = (String) request.getParameter(QUER_TYPE);
	// if (queryType == null) {
	// queryType = QUER_TYPE_GENERAL_SETTING;
	// }
	//
	// Object dto = null;
	// if (QUER_TYPE_GENERAL_SETTING.equals(queryType)) {
	// FinancialSettingDTO fsDto = new FinancialSettingDTO();
	// setGeneralSettingDTO(fsDto,request);
	// dto = fsDto;
	// } else if (QUER_TYPE_CHARGE_CYCLE_TYPE_LIST.equals(queryType)) {
	// BillingCycleTypeDTO bctDto = new BillingCycleTypeDTO();
	// setChargeCycleType(bctDto,request);
	// dto = bctDto;
	// } else if (QUER_TYPE_CHARGE_CYCLE_TYPE_DETAIL.equals(queryType)) {
	// BillingCycleTypeDTO bctDto = new BillingCycleTypeDTO();
	// setChargeCycleType(bctDto,request);
	// dto = bctDto;
	// } else if (QUER_TYPE_ACCOUNT_CYCLE_TYPE_LIST.equals(queryType)) {
	// BillingCycleTypeDTO bctDto = new BillingCycleTypeDTO();
	// setAccountCycleType(bctDto,request);
	// dto = bctDto;
	// } else if (QUER_TYPE_ACCOUNT_CYCLE_TYPE_DETAIL.equals(queryType)) {
	// BillingCycleTypeDTO bctDto = new BillingCycleTypeDTO();
	// setAccountCycleType(bctDto,request);
	// dto = bctDto;
	// } else if (QUER_TYPE_CUSTOMER_ACCOUNT_CYCLE_LIST.equals(queryType)) {
	// CustAcctCycleCfgDTO cacDto = new CustAcctCycleCfgDTO();
	// setCustomerAccountCycleType(cacDto,request);
	// dto = cacDto;
	// } else if (QUER_TYPE_CUSTOMER_ACCOUNT_CYCLE_DETAIL.equals(queryType)) {
	// CustAcctCycleCfgDTO cacDto = new CustAcctCycleCfgDTO();
	// setCustomerAccountCycleType(cacDto,request);
	// dto = cacDto;
	// } else if (QUER_TYPE_ACCOUNT_TYPE_LIST.equals(queryType)) {
	// AcctItemTypeDTO aitDto = new AcctItemTypeDTO();
	// setAccountTypeDTO(aitDto,request);
	// dto = aitDto;
	// } else if (QUER_TYPE_ACCOUNT_TYPE_DETAIL.equals(queryType)) {
	// AcctItemTypeDTO aitDto = new AcctItemTypeDTO();
	// setAccountTypeDTO(aitDto,request);
	// dto = aitDto;
	// }
	//
	// ejbEvent.setType(queryActionType);
	// ejbEvent.setDto(dto);
	// return ejbEvent;
	// }

	// /**
	// *
	// * @param dto
	// */
	// private void setGeneralSettingDTO(FinancialSettingDTO dto,
	// HttpServletRequest request) {
	// // 名称NAME
	// // 使用全局统一帐期类型UNIFIEDCYCLEFLAG
	// // 是否计算滞纳金CALCULATEPUNISHMENTFLAG
	// // 滞纳金开始计算天数PUNISHMENTSTARTDATE
	// // 滞纳金帐目类型PUNISHMENTACCTITEMTYPEID
	// // 日滞纳金比率PUNISHMENTRATE
	//
	// // 日常帐单过期天数INVOICEDUEDATE
	// // 欠费付清自动恢复标志AUTORESUMECPFLAG
	// // 零头处理模式SMAILLCHANGEPROCESSMODE
	// // 预存抵扣方式PREPAYMENTDEDUCTIONMODE
	// // 帐单累计模式 INVOICEACCUMULATEMODE
	// String name = (String)request.getParameter("txtName");
	// String unifiedCycleFlag =
	// (String)request.getParameter("txtUnifiedCycleFlag");
	// String calculatedPunishmentFlag =
	// (String)request.getAttribute("txtCalculatedPunishmentFlag");
	// String punishmentStartDate =
	// (String)request.getParameter("txtPunishmentStartDate");
	// String punishmentAccountTypeId =
	// (String)request.getParameter("txtPunishmentAccountTypeId");
	// String punishmentRate =
	// (String)request.getParameter("txtPunishmentRate");
	// String invoiceDueDate =
	// (String)request.getParameter("txtInvoiceDueDate");
	// String autoResumeCpFlag =
	// (String)request.getParameter("txtAutoResumeCpFlag");
	// String smallChangeProcessMode =
	// (String)request.getParameter("txtSmallChangeProcessMode");
	// String prepaymentDeductionMode =
	// (String)request.getParameter("txtPrepaymentDeductionMode");
	// String invoiceAccumulateMode =
	// (String)request.getParameter("txtInvoiceAccumulateMode");
	// if(WebUtil.StringHaveContent(name)){
	// dto.setName(name);
	// }
	// if(WebUtil.StringHaveContent(unifiedCycleFlag)){
	// dto.setUnifiedCycleFlag(unifiedCycleFlag);
	// }
	// if(WebUtil.StringHaveContent(calculatedPunishmentFlag)){
	// dto.setCalculatePunishmentFlag(calculatedPunishmentFlag);
	// }
	// if(WebUtil.StringHaveContent(punishmentStartDate)){
	// dto.setPunishmentStartDate(WebUtil.StringToInt(punishmentStartDate));
	// }
	// if(WebUtil.StringHaveContent(punishmentAccountTypeId)){
	// dto.setPunishmentAcctItemTypeID(punishmentAccountTypeId);
	// }
	// if(WebUtil.StringHaveContent(punishmentRate)){
	// dto.setPunishmenTrate(WebUtil.StringToInt(punishmentRate));
	// }
	// if(WebUtil.StringHaveContent(invoiceDueDate)){
	// dto.setInvoiceDueDate(WebUtil.StringToInt(invoiceDueDate));
	// }
	// if(WebUtil.StringHaveContent(autoResumeCpFlag)){
	// dto.setAutoResumeCpFlag(autoResumeCpFlag);
	// }
	// if(WebUtil.StringHaveContent(smallChangeProcessMode)){
	// dto.setSmallchangeProcessMode(smallChangeProcessMode);
	// }
	// if(WebUtil.StringHaveContent(prepaymentDeductionMode)){
	// dto.setPrepaymentDeductionMode(prepaymentDeductionMode);
	// }
	// if(WebUtil.StringHaveContent(invoiceAccumulateMode)){
	// dto.setInvoiceAccumulateMode(invoiceAccumulateMode);
	// }
	// }
	//
	// private void setAccountTypeDTO(AcctItemTypeDTO dto,
	// HttpServletRequest request) {
	// // T_AcctItemType
	// // 帐目类型标识 ACCTITEMTYPEID 1 1 N VARCHAR2 (20) NULL
	// // 帐目类型名称 ACCTITEMTYPENAME 2 Y VARCHAR2 (50) NULL
	// // 费用类型 FEETYPE 3 Y VARCHAR2 (5) NULL
	// // 归类帐目标志 SUMMARYAIFLAG 4 Y VARCHAR2 (5) NULL
	// // 归类到 SUMMARYTO 5 Y VARCHAR2 (20) NULL
	// // 特殊销帐标识 SPECIALSETOFFFLAG 6 Y VARCHAR2 (5) NULL
	// // 显示级别 SHOWLEVEL 7 Y VARCHAR2 (5) NULL
	// // 状态 STATUS 8 Y VARCHAR2 (5) NULL
	// // DT_CREATE 9 Y TIMESTAMP(6) NULL
	// // DT_LASTMOD 10 Y TIMESTAMP(6) NULL
	// String accountTypeId = (String)request.getParameter("txtAccountTypeId");
	// String accountTypeName =
	// (String)request.getParameter("txtAccountTypeName");
	// String feeType = (String)request.getParameter("txtFeeType");
	// String summaryAiFlag = (String)request.getParameter("txtSummaryAiFlag");
	// String summaryTo = (String)request.getParameter("txtSummaryTo");
	// String specialSetOffFlag =
	// (String)request.getParameter("txtSpecialSetoffFlag");
	// String showLevel = (String)request.getParameter("txtShowLevel");
	// String status = (String)request.getParameter("txtStatus");
	// // String dtCreate = (String)request.getParameter("txtDtCreate");
	// // String dtLastMod = (String)request.getParameter("txtDtLastMod");
	// if(WebUtil.StringHaveContent(accountTypeId)){
	// dto.setAcctItemTypeID(accountTypeId);
	// }
	// if(WebUtil.StringHaveContent(accountTypeName)){
	// dto.setAcctItemTypeName(accountTypeName);
	// }
	// if(WebUtil.StringHaveContent(feeType)){
	// dto.setFeeType(feeType);
	// }
	// if(WebUtil.StringHaveContent(summaryAiFlag)){
	// dto.setSummaryAiFlag(summaryAiFlag);
	// }
	// if(WebUtil.StringHaveContent(summaryTo)){
	// dto.setSummaryTo(summaryTo);
	// }
	// if(WebUtil.StringHaveContent(specialSetOffFlag)){
	// dto.setSpecialSetOffFlag(specialSetOffFlag);
	// }
	// if(WebUtil.StringHaveContent(showLevel)){
	// dto.setShowLevel(showLevel);
	// }
	// if(WebUtil.StringHaveContent(status)){
	// dto.setStatus(status);
	// }
	//		
	// }
	//
	// private void setAccountCycleType(BillingCycleTypeDTO dto,
	// HttpServletRequest request) {
	// // 序号ID 1 1 N NUMBER (10) NULL
	// // CTYPE 3 N VARCHAR2 (5) NULL
	// // 名称 NAME 2 Y VARCHAR2 (50) NULL
	// // 描述 DESCRIPTION 4 Y VARCHAR2 (200) NULL
	// // 状态 STATUS 20 Y VARCHAR2 (5) Null
	// // 对应计费周期类型 BILLINGCYCLETYPEID 11 Y NUMBER (10) NULL
	// // 第一个有效周期 FIRSTVALIDINVOICECYCLEID 12 Y NUMBER (10) NULL
	// // 时间长度 CYCLECOUNT 13 Y NUMBER (10) NULL
	// //
	// // 租费开始时间段 RENTCYCLEBDATE 6 N DATE NULL
	// // 使用费用开始时间 OTHERCYCLEBDATE 7 Y DATE NULL
	// // 帐单生成截止日 ENDINVOICINGDATE 19 Y DATE
	// // 帐单过期日期 INVOICEDUEDATE 10 Y DATE NULL
	// String id = (String) request.getParameter("txtId");
	// String cType = "I";
	// String name = (String)request.getParameter("txtName");
	// String description = (String)request.getParameter("txtDescription");
	// String status = (String)request.getParameter("txtStatus");
	// String billingCycleTypeId =
	// (String)request.getParameter("txtBillingCycleTypeId");
	// String firstValidInvoiceCycleId =
	// (String)request.getParameter("txtFirstValidInvoiceCycleId");
	// String cycleCount = (String)request.getParameter("txtCycleCount");
	// String rentCycleBDate = (String)request.getParameter("txtCycleBDate");
	// String otherCycleBDate =
	// (String)request.getParameter("txtOtherCycleBDate");
	// String endInvoicingDate =
	// (String)request.getParameter("txtEndInvoicingDate");
	// String invoiceDueDate =
	// (String)request.getParameter("txtInvoiceDueDate");
	//		
	// if(WebUtil.StringHaveContent(id)){
	// dto.setId(WebUtil.StringToInt(id));
	// }
	// dto.setCType(cType);
	// if(WebUtil.StringHaveContent(name)){
	// dto.setName(name);
	// }
	// if(WebUtil.StringHaveContent(description)){
	// dto.setDescription(description);
	// }
	// if(WebUtil.StringHaveContent(status)){
	// dto.setStatus(status);
	// }
	// if(WebUtil.StringHaveContent(billingCycleTypeId)){
	// dto.setBillingCycleTypeId(WebUtil.StringToInt(billingCycleTypeId));
	// }
	// if(WebUtil.StringHaveContent(firstValidInvoiceCycleId)){
	// dto.setFirstValidInvoiceCycleId(WebUtil.StringToInt(firstValidInvoiceCycleId));
	// }
	// if(WebUtil.StringHaveContent(cycleCount)){
	// dto.setCycleCount(WebUtil.StringToInt(cycleCount));
	// }
	// if(WebUtil.StringHaveContent(rentCycleBDate)){
	// dto.setRentCyclebDate(Timestamp.valueOf(rentCycleBDate));
	// }
	// //otherCycleBDate endInvoicingDate invoiceDueDate
	// if(WebUtil.StringHaveContent(otherCycleBDate)){
	// dto.setOtherCycleBDate(Timestamp.valueOf(otherCycleBDate));
	// }
	// if(WebUtil.StringHaveContent(endInvoicingDate)){
	// dto.setEndInvoicingDate(Timestamp.valueOf(endInvoicingDate));
	// }
	// if(WebUtil.StringHaveContent(invoiceDueDate)){
	// dto.setInvoiceDueDate(Timestamp.valueOf(invoiceDueDate));
	// }
	// }

	private void setChargeCycleType(BillingCycleTypeDTO dto,
			HttpServletRequest request) {
		// ID 1 1 N NUMBER (10) NULL
		// 名称 NAME 2 Y VARCHAR2 (50) NULL
		// 状态 STATUS 20 Y VARCHAR2 (5) Null
		// 描述 DESCRIPTION 4 Y VARCHAR2 (200) NULL
		// 是否允许预计费 ALLOWPREBILLINGFLAG 8 N VARCHAR2 (5) NULL
		// 开始计费标志 STARTBILLINGFLAG 9 N VARCHAR2 (5) NULL
		// 统一折算标志 UNIFIEDCYCLEFLAG 5 Y VARCHAR2 (5) NULL
		// 第一个计费的周期 FIRSTVALIDINVOICECYCLEID 12 Y NUMBER (10) NULL
		// 折算模式 RENTDISCTMODE 15 Y VARCHAR2 (5) NULL
		// 折算日 RENTDIVIDINGDATE 16 Y DATE NULL
		// 统一周期标志 UNIFIEDDISCTFLAG 14 N VARCHAR2 (5) NULL
		// 其他费计费起始日 OTHERCYCLEBDATE 7 Y DATE NULL
		// 租费计费起始日 RENTCYCLEBDATE 6 N DATE NULL

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

	// private void setCustomerAccountCycleType(CustAcctCycleCfgDTO dto,
	// HttpServletRequest request) {
	// // T_CustAcctCycleCfg
	// // SEQNO 1 1 N NUMBER (10) NULL
	// // BILLINGCYCLETYPEID 2 N NUMBER (10) NULL
	// // CUSTOMERTYPE 3 Y VARCHAR2 (5) NULL
	// // ACCOUNTTYPE 4 Y VARCHAR2 (5) NULL
	// // DT_CREATE 5 Y TIMESTAMP(6) NULL
	// // DT_LASTMOD 6 Y TIMESTAMP(6) NULL
	// String accountType = (String) request.getParameter("txtAccountType");
	// String seqNo = (String) request.getParameter("txtSeqNo");
	// String billingCycleTypeId = (String) request
	// .getParameter("txtBillingCycleTypeId");
	// String customerType = (String) request.getParameter("txtCustomerType");
	// // String dtLastMod = (String) request.getParameter("txtDtLastMod");
	// if (WebUtil.StringHaveContent(seqNo)) {
	// dto.setSeqNo(WebUtil.StringToInt(seqNo));
	// }
	// if (WebUtil.StringHaveContent(accountType)) {
	// dto.setAccountType(accountType);
	// }
	// if (WebUtil.StringHaveContent(billingCycleTypeId)) {
	// dto.setBillingCycleTypeId(WebUtil.StringToInt(billingCycleTypeId));
	// }
	// if (WebUtil.StringHaveContent(customerType)) {
	// dto.setCustomerType(customerType);
	// }
	// }

}
