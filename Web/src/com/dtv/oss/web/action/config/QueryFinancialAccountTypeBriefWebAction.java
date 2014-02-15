package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AcctItemTypeDTO;
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
public class QueryFinancialAccountTypeBriefWebAction extends QueryWebAction {

	 
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		AcctItemTypeDTO aitDto = new AcctItemTypeDTO();
		setAccountTypeDTO(aitDto, request);
		System.out.println(getClass() + ",aitDto:" + aitDto);

		int queryActionType = QueryFinancialSettingEJBEvent.QUERY_ACCOUNT_TYPE_BRIEF_LIST;
		QueryFinancialSettingEJBEvent ejbEvent = new QueryFinancialSettingEJBEvent(
				aitDto,pageFrom,pageTo, queryActionType);
		System.out.println(getClass() + ",event:" + ejbEvent);
		System.out.println(getClass() + ",event.getOperatorID:" + ejbEvent.getOperatorID());
		System.out.println(getClass() + "instance:" + this);
		return ejbEvent;
	}

	private void setAccountTypeDTO(AcctItemTypeDTO dto,
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
				.getParameter("txtAcctItemTypeID1");
		String accountTypeName = (String) request
				.getParameter("txtAcctItemTypeName");
		String feeType = (String) request.getParameter("txtFeeType");
		String summaryAiFlag = (String) request
				.getParameter("txtSummaryAiFlag");
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

	}

}
