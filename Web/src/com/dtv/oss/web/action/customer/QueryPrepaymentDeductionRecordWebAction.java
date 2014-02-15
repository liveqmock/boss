package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryPrepaymentDeductionRecordWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		//DTO封装参数说明
		//------------------用于预存抵扣记录查询-------
		//SpareStr1:序号
		//SpareStr2:客户证号
		//SpareStr3:帐户号
		//SpareStr4:组织
		//SpareStr5:区域
		//SpareStr6:扣款类型
		//SpareStr7:来源记录类型
		//SpareStr8:来源单据号
		//SpareStr9:出帐标志
		//SpareStr10:状态
		//SpareStr11:创建来源
		//SpareStr12:帐单号
/*		yangchen 2007/2/11 move
		//SpareTime1:抵扣时间1
		//SpareTime2:抵扣时间2
*/
		//beginTime :创建开始时间
		//endTime   ：创建结束时间
		//SpareStr13:关联单据类型
		//SpareStr14:关联单据号
		//---------------用于调帐查询-------


		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

		//*************************************用于所有预存抵扣记录查询****************************************
		//SpareStr2:客户证号
		if(WebUtil.StringHaveContent(request.getParameter("txtCustID")))
			dto.setSpareStr2(request.getParameter("txtCustID"));
		//SpareStr3:帐户号
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctID")))
			dto.setSpareStr3(request.getParameter("txtAcctID"));
		//SpareStr4:组织
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr4(request.getParameter("txtOrgID"));
		//SpareStr5:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr5(request.getParameter("txtDistrictID"));
		//SpareStr6:扣款类型
		if(WebUtil.StringHaveContent(request.getParameter("txtPrePaymentType")))
			dto.setSpareStr6(request.getParameter("txtPrePaymentType"));
		//SpareStr7:来源记录类型
		if(WebUtil.StringHaveContent(request.getParameter("txtReferRecordType")))
			dto.setSpareStr7(request.getParameter("txtReferRecordType"));
		//SpareStr8:来源单据号
		if(WebUtil.StringHaveContent(request.getParameter("txtReferRecordID")))
			dto.setSpareStr8(request.getParameter("txtReferRecordID"));
		//SpareStr9:出帐标志
		if(WebUtil.StringHaveContent(request.getParameter("txtInvoicedFlag")))
			dto.setSpareStr9(request.getParameter("txtInvoicedFlag"));
		//SpareStr10:状态
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setSpareStr10(request.getParameter("txtStatus"));
		//SpareStr11:创建来源
		if(WebUtil.StringHaveContent(request.getParameter("txtCreatingMethod")))
			dto.setSpareStr11(request.getParameter("txtCreatingMethod"));
		//SpareStr12:帐单号
		if (WebUtil.StringHaveContent(request.getParameter("txtInvoiceNo")))
			dto.setSpareStr12(request.getParameter("txtInvoiceNo"));
/*		yangchen 2007/2/11 move
		//SpareTime1:抵扣时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:抵扣时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));

*/		
/* yangchen 2007/2/11 add start*/
		//创建时间(起始),创建时间(结束)
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreateStartDateDatePart")))
	        dto.setBeginTime(WebUtil.StringToTimestampDefaultDayBegin(
	        		request.getParameter("txtCreateStartDateDatePart"),
					request.getParameter("txtCreateStartDateHourPart")
					));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreateEndDateDatePart")))
	        dto.setEndTime(WebUtil.StringToTimestampDefaultDayEnd(
	        		request.getParameter("txtCreateEndDateDatePart"),
					request.getParameter("txtCreateEndDateHourPart")
					));
	    //关联单据类型
	    if (WebUtil.StringHaveContent(request.getParameter("txtReferSheetType")))
			dto.setSpareStr13(request.getParameter("txtReferSheetType"));
	    //关联单据号
	    if (WebUtil.StringHaveContent(request.getParameter("txtReferSheetID")))
			dto.setSpareStr14(request.getParameter("txtReferSheetID"));
	    
/* yangchen 2007/2/11 add end*/
		//****************************用于预存抵扣记录查询*****************************************
		//SpareStr1:记录ID
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNO")) && (!"0".equals(request.getParameter("txtSeqNO")))){
			dto.setSpareStr1(request.getParameter("txtSeqNO"));
		}

		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_PREPAYMENT_DEDUCTION_RECORD);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
