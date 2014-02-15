package com.dtv.oss.web.action.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AcctItemTypeDTO;
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
public class QueryFinancialAccountTypeEditingWebAction extends QueryWebAction {

	private static final String EDITING_TYPE = "editing_type";

	private static final String EDITING_TYPE_NEW = "new";

	private static final String EDITING_TYPE_UPDATE = "view_update";

	public EJBEvent perform(HttpServletRequest request)
			throws WebActionException {
		String editingType = (String) request.getParameter(EDITING_TYPE);
		setQueryFileterAttribute(request);
		if (editingType != null
				&& EDITING_TYPE_UPDATE.equals(editingType = editingType.trim())) {

			return super.perform(request);
		}
		return null;
	}

	private void setQueryFileterAttribute(HttpServletRequest request) {
		AcctItemTypeDTO dto = new AcctItemTypeDTO();
		setAccountTypeFilterDTO(dto, request);
		request.setAttribute("QueryFilter", dto);
		String from = request.getParameter("txtFrom");
		String to = request.getParameter("txtTo");
		request.setAttribute("txtFrom", from);
		request.setAttribute("txtTo", to);
	}

	private void setAccountTypeFilterDTO(AcctItemTypeDTO dto,
			HttpServletRequest request) {
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

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		String editingType = (String) request.getParameter(EDITING_TYPE);

		if (editingType != null
				&& EDITING_TYPE_UPDATE.equals(editingType = editingType.trim())) {
			AcctItemTypeDTO dto = new AcctItemTypeDTO();
			setAccountTypeDTO(dto, request);
			QueryFinancialSettingEJBEvent ejbEvent = new QueryFinancialSettingEJBEvent(
					0, 1);
			ejbEvent.setDto(dto);
			ejbEvent
					.setType(QueryFinancialSettingEJBEvent.QUERY_ACCOUNT_TYPE_DETAIL);
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
			AcctItemTypeDTO dto = new AcctItemTypeDTO();
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
				.getParameter("txtAcctItemTypeID");
		String accountTypeName = (String) request
				.getParameter("txtAccountTypeName");
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
