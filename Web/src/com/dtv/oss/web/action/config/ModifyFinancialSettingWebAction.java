package com.dtv.oss.web.action.config;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AcctItemTypeDTO;
import com.dtv.oss.dto.BillingCycleTypeDTO;
import com.dtv.oss.dto.CustAcctCycleCfgDTO;
import com.dtv.oss.dto.FinancialSettingDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.FinancialSettingEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * @author wanghao 2006-6-1 15:29:25
 */
public class ModifyFinancialSettingWebAction extends GeneralWebAction {
	private static String dateFormatPattern = "yyyy-MM-dd";

	private static final String ACTION_TYPE = "action_type";

	private static final String ACTION_TYPE_GENERAL_UPDATE = "general_update";

	private static final String ACTION_TYPE_ACCOUNT_TYPE_NEW = "account_type_new";

	private static final String ACTION_TYPE_ACCOUNT_TYPE_UPDATE = "account_type_update";

	private static final String ACTION_TYPE_ACCOUNT_TYPE_DELETE = "account_type_delete";

	private static final String ACTION_TYPE_ACCOUNT_CYCLE_TYPE_NEW = "account_cycle_type_new";

	private static final String ACTION_TYPE_ACCOUNT_CYCLE_TYPE_UPDATE = "account_cycle_type_update";

	private static final String ACTION_TYPE_ACCOUNT_CYCLE_TYPE_DELETE = "account_cycle_type_delete";

	private static final String ACTION_TYPE_CHARGE_CYCLE_TYPE_NEW = "charge_cycle_type_new";

	private static final String ACTION_TYPE_CHARGE_CYCLE_TYPE_UPDATE = "charge_cycle_type_update";

	private static final String ACTION_TYPE_CHARGE_CYCLE_TYPE_DELETE = "charge_cycle_type_delete";

	private static final String ACTION_TYPE_CUSTOMER_ACCOUNT_CYCLE_TYPE_NEW = "customer_account_cycle_type_new";

	private static final String ACTION_TYPE_CUSTOMER_ACCOUNT_CYCLE_TYPE_UPDATE = "customer_account_cycle_type_update";

	private static final String ACTION_TYPE_CUSTOMER_ACCOUNT_CYCLE_TYPE_DELETE = "customer_account_cycle_type_delete";

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		String actionType = (String) request.getParameter(ACTION_TYPE);
		if (actionType == null) {
			return null;
		}

		FinancialSettingEJBEvent event = new FinancialSettingEJBEvent();
		Object dto = null;
		int type = 0;
		String backUrl = null;
		if (ACTION_TYPE_GENERAL_UPDATE.equals(actionType)) {
			FinancialSettingDTO fsDto = new FinancialSettingDTO();
			setGeneralSettingDTOForUpdate(fsDto, request);
			dto = fsDto;
			System.out.println("****************************fsDto:" + dto);
			type = FinancialSettingEJBEvent.UPDATE_GENERAL_SETTING;
			backUrl = "financial_general_setting_editing.do";
		} else if (ACTION_TYPE_ACCOUNT_TYPE_NEW.equals(actionType)) {
			AcctItemTypeDTO aitDto = new AcctItemTypeDTO();
			setAccountTypeDTOForCreate(aitDto, request);
			dto = aitDto;
			type = FinancialSettingEJBEvent.NEW_ACCOUNT_TYPE;
			backUrl = "account_type_brief.do";
		} else if (ACTION_TYPE_ACCOUNT_TYPE_UPDATE.equals(actionType)) {
			AcctItemTypeDTO aitDto = new AcctItemTypeDTO();
			setAccountTypeDTOForUpdate(aitDto, request);
			dto = aitDto;
			type = FinancialSettingEJBEvent.UPDATE_ACCOUNT_TYPE;
			backUrl = "account_type_brief.do";
		} else if (ACTION_TYPE_ACCOUNT_TYPE_DELETE.equals(actionType)) {
			AcctItemTypeDTO aitDto = new AcctItemTypeDTO();
			setAccountTypeDTOForDelete(aitDto, request);
			dto = aitDto;
			type = FinancialSettingEJBEvent.DELETE_ACCOUNT_TYPE;
			backUrl = "account_type_brief.do";
		} else if (ACTION_TYPE_ACCOUNT_CYCLE_TYPE_NEW.equals(actionType)) {
			BillingCycleTypeDTO bctDto = new BillingCycleTypeDTO();
			setAccountCycleTypeForCreate(bctDto, request);
			bctDto.setUnifiedDisctFlag("Y");
			dto = bctDto;
			type = FinancialSettingEJBEvent.NEW_ACCOUNT_CYCLE_TYPE;
			backUrl = "account_cycle_type_brief.do";
		} else if (ACTION_TYPE_ACCOUNT_CYCLE_TYPE_UPDATE.equals(actionType)) {
			BillingCycleTypeDTO bctDto = new BillingCycleTypeDTO();
			setAccountCycleTypeForUpdate(bctDto, request);
			dto = bctDto;
			type = FinancialSettingEJBEvent.UPDATE_ACCOUNT_CYCLE_TYPE;
			backUrl = "account_cycle_type_brief.do";
		} else if (ACTION_TYPE_ACCOUNT_CYCLE_TYPE_DELETE.equals(actionType)) {
			BillingCycleTypeDTO bctDto = new BillingCycleTypeDTO();
			setAccountCycleTypeForDelete(bctDto, request);
			dto = bctDto;
			type = FinancialSettingEJBEvent.DELETE_ACCOUNT_CYCLE_TYPE;
			backUrl = "account_cycle_type_brief.do";
		} else if (ACTION_TYPE_CHARGE_CYCLE_TYPE_NEW.equals(actionType)) {
			BillingCycleTypeDTO bctDto = new BillingCycleTypeDTO();
			setChargeCycleTypeForCreate(bctDto, request);
			dto = bctDto;
			type = FinancialSettingEJBEvent.NEW_CHARGE_CYCLE_TYPE;
			backUrl = "charge_cycle_type_brief.do";
		} else if (ACTION_TYPE_CHARGE_CYCLE_TYPE_UPDATE.equals(actionType)) {
			BillingCycleTypeDTO bctDto = new BillingCycleTypeDTO();
			setChargeCycleTypeForUpdate(bctDto, request);
			dto = bctDto;
			type = FinancialSettingEJBEvent.UPDATE_CHARGE_CYCLE_TYPE;
			backUrl = "charge_cycle_type_brief.do";
		} else if (ACTION_TYPE_CHARGE_CYCLE_TYPE_DELETE.equals(actionType)) {
			BillingCycleTypeDTO bctDto = new BillingCycleTypeDTO();
			setChargeCycleTypeForDelete(bctDto, request);
			dto = bctDto;
			type = FinancialSettingEJBEvent.DELETE_CHARGE_CYCLE_TYPE;
			backUrl = "charge_cycle_type_brief.do";
		} else if (ACTION_TYPE_CUSTOMER_ACCOUNT_CYCLE_TYPE_NEW
				.equals(actionType)) {
			CustAcctCycleCfgDTO caccDto = new CustAcctCycleCfgDTO();
			setCustomerAccountCycleTypeForCreate(caccDto, request);
			dto = caccDto;
			type = FinancialSettingEJBEvent.NEW_CUSTOMER_ACCOUNT_CYCLE;
			backUrl = "customer_account_cycle_type_brief.do";
		} else if (ACTION_TYPE_CUSTOMER_ACCOUNT_CYCLE_TYPE_UPDATE
				.equals(actionType)) {
			CustAcctCycleCfgDTO caccDto = new CustAcctCycleCfgDTO();
			setCustomerAccountCycleTypeForUpdate(caccDto, request);
			dto = caccDto;
			type = FinancialSettingEJBEvent.UPDATE_CUSTOMER_ACCOUNT_CYCLE;
			backUrl = "customer_account_cycle_type_brief.do";
		} else if (ACTION_TYPE_CUSTOMER_ACCOUNT_CYCLE_TYPE_DELETE
				.equals(actionType)) {
			CustAcctCycleCfgDTO caccDto = new CustAcctCycleCfgDTO();
			setCustomerAccountCycleTypeForDelete(caccDto, request);
			dto = caccDto;
			type = FinancialSettingEJBEvent.DELETE_CUSTOMER_ACCOUNT_CYCLE;
			backUrl = "customer_account_cycle_type_brief.do";
		}
		request.setAttribute("back_url", backUrl);
		event.setDto(dto);
		event.setActionType(type);
		System.out.println(getClass().getName() + ":encapsulate:dto:" + dto);
		return event;
	}

	private void setAccountTypeDTOForCreate(AcctItemTypeDTO dto,
			HttpServletRequest request) {
		// T_AcctItemType
		// 帐目类型标识 ACCTITEMTYPEID 1 1 N VARCHAR2 (20) NULL
		// 帐目类型名称 ACCTITEMTYPENAME 2 Y VARCHAR2 (50) NULL
		// 费用类型 FEETYPE 3 Y VARCHAR2 (5) NULL
		// 归类帐目标志 SUMMARYAIFLAG 4 Y VARCHAR2 (5) NULL
		// 归类到 SUMMARYTO 5 Y VARCHAR2 (20) NULL
		// 特殊销帐标识 SPECIALSETOFFFLAG 6 Y VARCHAR2 (5) NULL
		// 显示级别 SHOWLEVEL 7 Y VARCHAR2 (5) NULL
		// 状态 STATUS 8 Y VARCHAR2 (5) NULL
		// DT_CREATE 9 Y TIMESTAMP(6) NULL
		// DT_LASTMOD 10 Y TIMESTAMP(6) NULL
		String accountTypeId = (String) request
				.getParameter("txtAcctItemTypeID");
		String accountTypeName = (String) request
				.getParameter("txtAcctItemTypeName");
		String feeType = (String) request.getParameter("txtFeeType");
		String summaryAiFlag = (String) request
				.getParameter("txtSummaryAiFlag");
		String systemFlag = (String) request
		.getParameter("txtSystemFlag");
		String summaryTo = (String) request.getParameter("txtSummaryTo");
		String specialSetOffFlag = (String) request
				.getParameter("txtSpecialSetoffFlag");
		String showLevel = (String) request.getParameter("txtShowLevel");
		String status = (String) request.getParameter("txtStatus");
		// String dtCreate = (String)request.getParameter("txtDtCreate");
		// String dtLastMod = (String)request.getParameter("txtDtLastMod");
		if (WebUtil.StringHaveContent(accountTypeId)) {
			dto.setAcctItemTypeID(accountTypeId);
		}
		if (WebUtil.StringHaveContent(accountTypeName)) {
			dto.setAcctItemTypeName(accountTypeName);
		}
		if (WebUtil.StringHaveContent(feeType)) {
			dto.setFeeType(feeType);
		}
		dto.setSystemFlag(systemFlag);
		if (WebUtil.StringHaveContent(summaryAiFlag)) {
			dto.setSummaryAiFlag(summaryAiFlag);
		}
		if (WebUtil.StringHaveContent(summaryTo)) {
			dto.setSummaryTo(summaryTo);
		}
		if (WebUtil.StringHaveContent(specialSetOffFlag)) {
			dto.setSpecialSetOffFlag(specialSetOffFlag);
		}
		if (WebUtil.StringHaveContent(showLevel)) {
			dto.setShowLevel(showLevel);
		}
		if (WebUtil.StringHaveContent(status)) {
			dto.setStatus(status);
		}
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		dto.setDtCreate(currentTime);
		dto.setDtLastmod(currentTime);
	}

	private void setAccountCycleTypeForCreate(BillingCycleTypeDTO dto,
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
		String rentCycleBDate = (String) request
				.getParameter("txtRentCycleBDate");
		String otherCycleBDate = (String) request
				.getParameter("txtOtherCycleBDate");
		String endInvoicingDate = (String) request
				.getParameter("txtEndInvoicingDate");
		String invoiceDueDate = (String) request
				.getParameter("txtInvoiceDueDate");

		if (WebUtil.StringHaveContent(id)) {
			dto.setBillingCycleTypeId(WebUtil.StringToInt(id));
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

		Timestamp ts = null;
		DateFormat format = new SimpleDateFormat(dateFormatPattern);
		if (WebUtil.StringHaveContent(rentCycleBDate)) {
			try {
				ts = new Timestamp(format.parse(rentCycleBDate).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dto.setRentCyclebDate(ts);
		}
		// otherCycleBDate endInvoicingDate invoiceDueDate
		if (WebUtil.StringHaveContent(otherCycleBDate)) {
			try {
				ts = new Timestamp(format.parse(otherCycleBDate).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dto.setOtherCycleBDate(ts);
		}
		if (WebUtil.StringHaveContent(endInvoicingDate)) {
			try {
				ts = new Timestamp(format.parse(endInvoicingDate).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dto.setEndInvoicingDate(ts);
		}
		if (WebUtil.StringHaveContent(invoiceDueDate)) {
			try {
				ts = new Timestamp(format.parse(invoiceDueDate).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dto.setInvoiceDueDate(ts);
		}

		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		dto.setDtCreate(currentTime);
		dto.setDtLastmod(currentTime);
		dto.setAllowPrebillingFlag("Y");
		dto.setStartBillingFlag("Y");
		dto.setUnifiedDisctFlag("Y");

	}

	private void setChargeCycleTypeForCreate(BillingCycleTypeDTO dto,
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
		String cycleCount = (String) request.getParameter("txtCycleCount");

		if (WebUtil.StringHaveContent(id)) {
			dto.setBillingCycleTypeId(WebUtil.StringToInt(id));
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
			dto.setRentDisctMode(rentDisctMode);
		}
		if (WebUtil.StringHaveContent(cycleCount)) {
			dto.setCycleCount(WebUtil.StringToInt(cycleCount));
		}

		Timestamp ts = null;
		DateFormat format = new SimpleDateFormat(dateFormatPattern);
		if (WebUtil.StringHaveContent(rentDividingDate)) {
			try {
				ts = new Timestamp(format.parse(rentDividingDate).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dto.setRentDividingDate(ts);
		}
		if (WebUtil.StringHaveContent(unifiedDisctFlag)) {
			dto.setUnifiedDisctFlag(unifiedDisctFlag);
		}

		if (WebUtil.StringHaveContent(rentCycleBDate)) {
			try {
				ts = new Timestamp(format.parse(rentCycleBDate).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dto.setRentCyclebDate(ts);
		}
		// otherCycleBDate endInvoicingDate invoiceDueDate
		if (WebUtil.StringHaveContent(otherCycleBDate)) {
			try {
				ts = new Timestamp(format.parse(otherCycleBDate).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dto.setOtherCycleBDate(ts);
		}
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		dto.setDtCreate(currentTime);
		dto.setDtLastmod(currentTime);
	}

	private void setCustomerAccountCycleTypeForCreate(CustAcctCycleCfgDTO dto,
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
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		dto.setDtCreate(currentTime);
		dto.setDtLastmod(currentTime);
	}

	/**
	 * 
	 * @param dto
	 */
	private void setGeneralSettingDTOForUpdate(FinancialSettingDTO dto,
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
				.getParameter("txtCalculatePunishmentFlag");
		String punishmentStartDate = (String) request
				.getParameter("txtPunishmentStartDate");
		String punishmentAccountTypeId = (String) request
				.getParameter("txtPunishmentAcctItemTypeID");
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
		 
			dto.setName(name);
		 
		 
			dto.setUnifiedCycleFlag(unifiedCycleFlag);
		 
		 
			dto.setCalculatePunishmentFlag(calculatedPunishmentFlag);
	 
		 
			dto.setPunishmentStartDate(WebUtil
							.StringToInt(punishmentStartDate));
	 
	 
			dto.setPunishmentAcctItemTypeID(punishmentAccountTypeId);
		 
		 
			dto.setPunishmenTrate(WebUtil.StringTodouble(punishmentRate));
		 
	 
			dto.setInvoiceDueDate(WebUtil.StringToInt(invoiceDueDate));
		 
		 
			dto.setAutoResumeCpFlag(autoResumeCpFlag);
		 
		 
			dto.setSmallchangeProcessMode(smallChangeProcessMode);
		 
		 
			dto.setPrepaymentDeductionMode(prepaymentDeductionMode);
		 
		 
			dto.setInvoiceAccumulateMode(invoiceAccumulateMode);
		 
		String dtLastMod = (String) request.getParameter("txtDtLastMod");
		Timestamp lastModifiedTime = null;
		if (dtLastMod == null || (dtLastMod = dtLastMod.trim()).length() == 0) {
			lastModifiedTime = new Timestamp(System.currentTimeMillis());
		} else {
			long time = Long.valueOf(dtLastMod).longValue();
			lastModifiedTime = new Timestamp(time);
		}
		dto.setDtLastmod(lastModifiedTime);
	}

	private void setAccountTypeDTOForUpdate(AcctItemTypeDTO dto,
			HttpServletRequest request) {
		// T_AcctItemType
		// 帐目类型标识 ACCTITEMTYPEID 1 1 N VARCHAR2 (20) NULL
		// 帐目类型名称 ACCTITEMTYPENAME 2 Y VARCHAR2 (50) NULL
		// 费用类型 FEETYPE 3 Y VARCHAR2 (5) NULL
		// 归类帐目标志 SUMMARYAIFLAG 4 Y VARCHAR2 (5) NULL
		// 归类到 SUMMARYTO 5 Y VARCHAR2 (20) NULL
		// 特殊销帐标识 SPECIALSETOFFFLAG 6 Y VARCHAR2 (5) NULL
		// 显示级别 SHOWLEVEL 7 Y VARCHAR2 (5) NULL
		// 状态 STATUS 8 Y VARCHAR2 (5) NULL
		// DT_CREATE 9 Y TIMESTAMP(6) NULL
		// DT_LASTMOD 10 Y TIMESTAMP(6) NULL
		String accountTypeId = (String) request
				.getParameter("txtAcctItemTypeID");
		String accountTypeName = (String) request
				.getParameter("txtAcctItemTypeName");
		String feeType = (String) request.getParameter("txtFeeType");
		String summaryAiFlag = (String) request
				.getParameter("txtSummaryAiFlag");
		String systemFlag = (String) request
		.getParameter("txtSystemFlag");
		String summaryTo = (String) request.getParameter("txtSummaryTo");
		String specialSetOffFlag = (String) request
				.getParameter("txtSpecialSetoffFlag");
		String showLevel = (String) request.getParameter("txtShowLevel");
		String status = (String) request.getParameter("txtStatus");
		// String dtCreate = (String)request.getParameter("txtDtCreate");
	 
			dto.setAcctItemTypeID(accountTypeId);
		 
		 
			dto.setAcctItemTypeName(accountTypeName);
		    dto.setSystemFlag(systemFlag);
		 
			dto.setFeeType(feeType);
		 
		 
			dto.setSummaryAiFlag(summaryAiFlag);
		 
	 
			dto.setSummaryTo(summaryTo);
		 
			dto.setSpecialSetOffFlag(specialSetOffFlag);
		 
	 
			dto.setShowLevel(showLevel);
		 
		 
			dto.setStatus(status);
		 
		String dtLastMod = (String) request.getParameter("txtDtLastMod");
		 
		dto.setDtLastmod(WebUtil.StringToTimestamp(dtLastMod));
	}

	private void setAccountCycleTypeForUpdate(BillingCycleTypeDTO dto,
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
		String rentCycleBDate = (String) request.getParameter("txtRentCycleBDate");
		String otherCycleBDate = (String) request
				.getParameter("txtOtherCycleBDate");
		String endInvoicingDate = (String) request
				.getParameter("txtEndInvoicingDate");
		String invoiceDueDate = (String) request
				.getParameter("txtInvoiceDueDate");

	 
			dto.setId(WebUtil.StringToInt(id));
		 
		  dto.setCType(cType);
		 
			dto.setName(name);
		 
		 
			dto.setDescription(description);
		 
	 
			dto.setStatus(status);
		 
		 
			dto.setBillingCycleTypeId(WebUtil.StringToInt(billingCycleTypeId));
		 
	 
			dto.setFirstValidInvoiceCycleId(WebUtil
					.StringToInt(firstValidInvoiceCycleId));
	 
		 
			dto.setCycleCount(WebUtil.StringToInt(cycleCount));
	 
		    
		    dto.setRentCyclebDate(WebUtil.StringToTimestamp(rentCycleBDate));
		    dto.setOtherCycleBDate(WebUtil.StringToTimestamp(otherCycleBDate));
		    dto.setEndInvoicingDate(WebUtil.StringToTimestamp(endInvoicingDate));
		    dto.setInvoiceDueDate(WebUtil.StringToTimestamp(invoiceDueDate));
		 
		 
		 String dtLastMod = (String) request.getParameter("txtDtLastMod");
		if (WebUtil.StringHaveContent(dtLastMod)) 
		dto.setDtLastmod(WebUtil.StringToTimestamp(dtLastMod));
		dto.setAllowPrebillingFlag("Y");
		dto.setStartBillingFlag("Y");
		dto.setUnifiedDisctFlag("Y");
	}

	private void setChargeCycleTypeForUpdate(BillingCycleTypeDTO dto,
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
		String cycleCount = (String) request.getParameter("txtCycleCount");
		 
			dto.setCycleCount(WebUtil.StringToInt(cycleCount));
		 
		 
			dto.setId(WebUtil.StringToInt(id));
		 
		    dto.setCType(cType);
		 
			dto.setName(name);
		 
		 
			dto.setDescription(description);
		 
		 
			dto.setStatus(status);
		 
		 
			dto.setAllowPrebillingFlag(allowPrebillingFlag);
		 
	 
			dto.setStartBillingFlag(startBillingFlag);
		 
		 
			dto.setUnifiedCycleFlag(unifiedCycleFlag);
		 
	    
			dto.setFirstValidInvoiceCycleId(WebUtil
					.StringToInt(firstValidInvoiceCycleId));
		 
	 
		    	dto.setRentDisctMode(rentDisctMode);
			    dto.setRentDividingDate(WebUtil.StringToTimestamp(rentDividingDate));
			    dto.setRentCyclebDate(WebUtil.StringToTimestamp(rentCycleBDate));
			    dto.setOtherCycleBDate(WebUtil.StringToTimestamp(otherCycleBDate));
			   
	 
		 
		 
			dto.setUnifiedDisctFlag(unifiedDisctFlag);
		 

		 
		 
		String dtLastMod = (String) request.getParameter("txtDtLastMod");
		 dto.setDtLastmod(WebUtil.StringToTimestamp(dtLastMod));
		 
	}

	private void setCustomerAccountCycleTypeForUpdate(CustAcctCycleCfgDTO dto,
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
		 
		 
			dto.setSeqNo(WebUtil.StringToInt(seqNo));
		 
		 
			dto.setAccountType(accountType);
		 
		 
			dto.setBillingCycleTypeId(WebUtil.StringToInt(billingCycleTypeId));
		 
	 
			dto.setCustomerType(customerType);
		 
			String dtLastMod = (String) request.getParameter("txtDtLastMod");
			 dto.setDtLastmod(WebUtil.StringToTimestamp(dtLastMod));
	}

	/**
	 * 
	 * @param dto
	 */
	// private void setGeneralSettingDTOForDelete(FinancialSettingDTO dto,
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
	// // String unifiedCycleFlag =
	// (String)request.getParameter("txtUnifiedCycleFlag");
	// // String calculatedPunishmentFlag =
	// (String)request.getAttribute("txtCalculatedPunishmentFlag");
	// // String punishmentStartDate =
	// (String)request.getParameter("txtPunishmentStartDate");
	// // String punishmentAccountTypeId =
	// (String)request.getParameter("txtPunishmentAccountTypeId");
	// // String punishmentRate =
	// (String)request.getParameter("txtPunishmentRate");
	// // String invoiceDueDate =
	// (String)request.getParameter("txtInvoiceDueDate");
	// // String autoResumeCpFlag =
	// (String)request.getParameter("txtAutoResumeCpFlag");
	// // String smallChangeProcessMode =
	// (String)request.getParameter("txtSmallChangeProcessMode");
	// // String prepaymentDeductionMode =
	// (String)request.getParameter("txtPrepaymentDeductionMode");
	// // String invoiceAccumulateMode =
	// (String)request.getParameter("txtInvoiceAccumulateMode");
	// if(WebUtil.StringHaveContent(name)){
	// dto.setName(name);
	// }else{
	// throw new RuntimeException("the name field cann't be null.");
	// }
	// // if(WebUtil.StringHaveContent(unifiedCycleFlag)){
	// // dto.setUnifiedCycleFlag(unifiedCycleFlag);
	// // }
	// // if(WebUtil.StringHaveContent(calculatedPunishmentFlag)){
	// // dto.setCalculatePunishmentFlag(calculatedPunishmentFlag);
	// // }
	// // if(WebUtil.StringHaveContent(punishmentStartDate)){
	// // dto.setPunishmentStartDate(WebUtil.StringToInt(punishmentStartDate));
	// // }
	// // if(WebUtil.StringHaveContent(punishmentAccountTypeId)){
	// // dto.setPunishmentAcctItemTypeID(punishmentAccountTypeId);
	// // }
	// // if(WebUtil.StringHaveContent(punishmentRate)){
	// // dto.setPunishmenTrate(WebUtil.StringToInt(punishmentRate));
	// // }
	// // if(WebUtil.StringHaveContent(invoiceDueDate)){
	// // dto.setInvoiceDueDate(WebUtil.StringToInt(invoiceDueDate));
	// // }
	// // if(WebUtil.StringHaveContent(autoResumeCpFlag)){
	// // dto.setAutoResumeCpFlag(autoResumeCpFlag);
	// // }
	// // if(WebUtil.StringHaveContent(smallChangeProcessMode)){
	// // dto.setSmallchangeProcessMode(smallChangeProcessMode);
	// // }
	// // if(WebUtil.StringHaveContent(prepaymentDeductionMode)){
	// // dto.setPrepaymentDeductionMode(prepaymentDeductionMode);
	// // }
	// // if(WebUtil.StringHaveContent(invoiceAccumulateMode)){
	// // dto.setInvoiceAccumulateMode(invoiceAccumulateMode);
	// // }
	// }
	private void setAccountTypeDTOForDelete(AcctItemTypeDTO dto,
			HttpServletRequest request) {
		// T_AcctItemType
		// 帐目类型标识 ACCTITEMTYPEID 1 1 N VARCHAR2 (20) NULL
		// 帐目类型名称 ACCTITEMTYPENAME 2 Y VARCHAR2 (50) NULL
		// 费用类型 FEETYPE 3 Y VARCHAR2 (5) NULL
		// 归类帐目标志 SUMMARYAIFLAG 4 Y VARCHAR2 (5) NULL
		// 归类到 SUMMARYTO 5 Y VARCHAR2 (20) NULL
		// 特殊销帐标识 SPECIALSETOFFFLAG 6 Y VARCHAR2 (5) NULL
		// 显示级别 SHOWLEVEL 7 Y VARCHAR2 (5) NULL
		// 状态 STATUS 8 Y VARCHAR2 (5) NULL
		// DT_CREATE 9 Y TIMESTAMP(6) NULL
		// DT_LASTMOD 10 Y TIMESTAMP(6) NULL
		String accountTypeId = (String) request
				.getParameter("txtAccountTypeId");
		// String accountTypeName =
		// (String)request.getParameter("txtAccountTypeName");
		// String feeType = (String)request.getParameter("txtFeeType");
		// String summaryAiFlag =
		// (String)request.getParameter("txtSummaryAiFlag");
		// String summaryTo = (String)request.getParameter("txtSummaryTo");
		// String specialSetOffFlag =
		// (String)request.getParameter("txtSpecialSetoffFlag");
		// String showLevel = (String)request.getParameter("txtShowLevel");
		// String status = (String)request.getParameter("txtStatus");
		// // String dtCreate = (String)request.getParameter("txtDtCreate");
		// // String dtLastMod = (String)request.getParameter("txtDtLastMod");
		if (WebUtil.StringHaveContent(accountTypeId)) {
			dto.setAcctItemTypeID(accountTypeId);
		} else {
			throw new RuntimeException("account type id cann't be null!");
		}
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

	}

	private void setAccountCycleTypeForDelete(BillingCycleTypeDTO dto,
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
		// String cType = "I";
		// String name = (String)request.getParameter("txtName");
		// String description = (String)request.getParameter("txtDescription");
		// String status = (String)request.getParameter("txtStatus");
		// String billingCycleTypeId =
		// (String)request.getParameter("txtBillingCycleTypeId");
		// String firstValidInvoiceCycleId =
		// (String)request.getParameter("txtFirstValidInvoiceCycleId");
		// String cycleCount = (String)request.getParameter("txtCycleCount");
		// String rentCycleBDate =
		// (String)request.getParameter("txtCycleBDate");
		// String otherCycleBDate =
		// (String)request.getParameter("txtOtherCycleBDate");
		// String endInvoicingDate =
		// (String)request.getParameter("txtEndInvoicingDate");
		// String invoiceDueDate =
		// (String)request.getParameter("txtInvoiceDueDate");
		//		
		if (WebUtil.StringHaveContent(id)) {
			dto.setBillingCycleTypeId(WebUtil.StringToInt(id));
		} else {
			throw new RuntimeException(
					"the account cycle type id of the object to be deleted cann't be null!");
		}
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
	}

	private void setChargeCycleTypeForDelete(BillingCycleTypeDTO dto,
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
		// String cType = "B";
		// String name = (String)request.getParameter("txtName");
		// String description = (String)request.getParameter("txtDescription");
		// String status = (String)request.getParameter("txtStatus");
		// String allowPrebillingFlag =
		// (String)request.getParameter("txtAllowPrebillingFlag");
		// String startBillingFlag =
		// (String)request.getParameter("txtStartBillingFlag");
		// String unifiedCycleFlag =
		// (String)request.getParameter("txtUnifiedCycleFlag");
		// String firstValidInvoiceCycleId =
		// (String)request.getParameter("txtFirstValidInvoiceCycleId");
		// String rentDisctMode =
		// (String)request.getParameter("txtRentDisctMode");
		// String rentDividingDate =
		// (String)request.getParameter("txtRentDividingDate");
		// String unifiedDisctFlag =
		// (String)request.getParameter("txtUnifiedDisctFlag");
		// String otherCycleBDate =
		// (String)request.getParameter("txtOtherCycleBDate");
		// String rentCycleBDate =
		// (String)request.getParameter("txtRentCycleBDate");

		if (WebUtil.StringHaveContent(id)) {
			dto.setBillingCycleTypeId(WebUtil.StringToInt(id));
		} else {
			throw new RuntimeException(
					"the charge cycle type id of the object to be deleted cann't be null!");
		}
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
		// if(WebUtil.StringHaveContent(allowPrebillingFlag)){
		// dto.setAllowPrebillingFlag(allowPrebillingFlag);
		// }
		// if(WebUtil.StringHaveContent(startBillingFlag)){
		// dto.setStartBillingFlag(startBillingFlag);
		// }
		// if(WebUtil.StringHaveContent(unifiedCycleFlag)){
		// dto.setUnifiedCycleFlag(unifiedCycleFlag);
		// }
		// if(WebUtil.StringHaveContent(firstValidInvoiceCycleId)){
		// dto.setFirstValidInvoiceCycleId(WebUtil.StringToInt(firstValidInvoiceCycleId));
		// }
		// if(WebUtil.StringHaveContent(rentDisctMode)){
		// dto.setUnifiedCycleFlag(rentDisctMode);
		// }
		// if(WebUtil.StringHaveContent(rentDividingDate)){
		// dto.setUnifiedCycleFlag(rentDividingDate);
		// }
		// if(WebUtil.StringHaveContent(unifiedDisctFlag)){
		// dto.setUnifiedCycleFlag(unifiedDisctFlag);
		// }
		//		
		// if(WebUtil.StringHaveContent(rentCycleBDate)){
		// dto.setRentCyclebDate(Timestamp.valueOf(rentCycleBDate));
		// }
		// //otherCycleBDate endInvoicingDate invoiceDueDate
		// if(WebUtil.StringHaveContent(otherCycleBDate)){
		// dto.setOtherCycleBDate(Timestamp.valueOf(otherCycleBDate));
		// }
		//		
	}

	private void setCustomerAccountCycleTypeForDelete(CustAcctCycleCfgDTO dto,
			HttpServletRequest request) {
		// T_CustAcctCycleCfg
		// SEQNO 1 1 N NUMBER (10) NULL
		// BILLINGCYCLETYPEID 2 N NUMBER (10) NULL
		// CUSTOMERTYPE 3 Y VARCHAR2 (5) NULL
		// ACCOUNTTYPE 4 Y VARCHAR2 (5) NULL
		// DT_CREATE 5 Y TIMESTAMP(6) NULL
		// DT_LASTMOD 6 Y TIMESTAMP(6) NULL
		// String accountType = (String) request.getParameter("txtAccountType");
		String seqNo = (String) request.getParameter("txtSeqNo");
		// String billingCycleTypeId = (String) request
		// .getParameter("txtBillingCycleTypeId");
		// String customerType = (String)
		// request.getParameter("txtCustomerType");
		// String dtLastMod = (String) request.getParameter("txtDtLastMod");
		if (WebUtil.StringHaveContent(seqNo)) {
			dto.setSeqNo(WebUtil.StringToInt(seqNo));
		} else {
			throw new RuntimeException(
					"the seq no of the object to be deleted cann't be null!");
		}
		// if (WebUtil.StringHaveContent(accountType)) {
		// dto.setAccountType(accountType);
		// }
		// if (WebUtil.StringHaveContent(billingCycleTypeId)) {
		// dto.setBillingCycleTypeId(WebUtil.StringToInt(billingCycleTypeId));
		// }
		// if (WebUtil.StringHaveContent(customerType)) {
		// dto.setCustomerType(customerType);
		// }
	}

}
