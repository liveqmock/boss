package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryAdjustAccountWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		//DTO封装参数说明
		//------------------用于调帐查询-------
		//SpareStr1:调帐序号
		//SpareStr2:客户证号
		//SpareStr3:帐户号
		//SpareStr4:调帐原因
		//SpareStr5:调帐类型
		//SpareStr6:所属组织
		//SpareStr7:区域
		//SpareStr8:调帐对象
		//SpareStr9:调帐对象ID
		//SpareStr10:状态
		//SpareStr11:预存调整标记
		
		//SpareStr12:操作员ID
		//SpareStr13:调账方向

		//SpareTime1:创建起始时间
		//SpareTime2:创建截止时间
		//---------------用于调帐查询-------


		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

		//*************************************用于所有调帐查询****************************************
		//SpareStr2:客户证号
		if(WebUtil.StringHaveContent(request.getParameter("txtCustID")))
			dto.setSpareStr2(request.getParameter("txtCustID"));
		//SpareStr3:帐户号
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctID")))
			dto.setSpareStr3(request.getParameter("txtAcctID"));
		//SpareStr4:调帐原因
		if(WebUtil.StringHaveContent(request.getParameter("txtReason")))
			dto.setSpareStr4(request.getParameter("txtReason"));
		//SpareStr5:调帐类型
		if(WebUtil.StringHaveContent(request.getParameter("txtAdjustmentType")))
			dto.setSpareStr5(request.getParameter("txtAdjustmentType"));
		//SpareStr6:所属组织
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr6(request.getParameter("txtOrgID"));
		//SpareStr7:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr7(request.getParameter("txtDistrictID"));
		//SpareStr8:调帐对象
		if(WebUtil.StringHaveContent(request.getParameter("txtReferRecordType")))
			dto.setSpareStr8(request.getParameter("txtReferRecordType"));
		//SpareStr9:调帐对象ID
		if(WebUtil.StringHaveContent(request.getParameter("txtReferRecordID")))
			dto.setSpareStr9(request.getParameter("txtReferRecordID"));
		//SpareStr10:状态
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setSpareStr10(request.getParameter("txtStatus"));
		//SpareStr11:预存调整标记
		if(WebUtil.StringHaveContent(request.getParameter("txtAdjustmentPrepaymentFlag")))
			dto.setSpareStr11(request.getParameter("txtAdjustmentPrepaymentFlag"));
		
		//SpareTime1:创建起始时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1DatePart")))
			dto.setSpareTime1(WebUtil.StringToTimestampDefaultDayBegin(
	        		request.getParameter("txtCreateTime1DatePart"),
					request.getParameter("txtCreateTime1HourPart")
					));
		//SpareTime2:创建截止时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2DatePart")))
			dto.setSpareTime2(WebUtil.StringToTimestampDefaultDayBegin(
	        		request.getParameter("txtCreateTime2DatePart"),
					request.getParameter("txtCreateTime2HourPart")
					));

		//****************************用于调帐具体查询*****************************************
		//SpareStr1:记录ID
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNO")) && (!"0".equals(request.getParameter("txtSeqNO")))){
			dto.setSpareStr1(request.getParameter("txtSeqNO"));
		}
		//SpareStr12:操作员ID
		if(WebUtil.StringHaveContent(request.getParameter("txtOpID"))){
			dto.setSpareStr12(request.getParameter("txtOpID"));
		}
		//SpareStr13:调账方向
		if(WebUtil.StringHaveContent(request.getParameter("txtAdjustmentDirection"))){
			dto.setSpareStr13(request.getParameter("txtAdjustmentDirection"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtAddress")))
			dto.setSpareStr14(request.getParameter("txtAddress"));
		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_ADJUST_ACCOUNT);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
