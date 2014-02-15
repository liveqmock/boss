package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.DownloadWebAction;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryPaymentRecordAllWebAction extends DownloadWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		//DTO封装参数说明
		//------------------用于支付查询-------
		//SpareStr1:支付序号
		//SpareStr2:客户证号
		//SpareStr3:帐户号
		//SpareStr4:组织
		//SpareStr5:区域
		//SpareStr6:付费方式
		//SpareStr7:支付类别
		//SpareStr8:代用券类型
		//SpareStr9:券号
		//SpareStr10:关联单据类型
		//SpareStr11:关联单据号
		//SpareStr12:来源
		//SpareStr13:来源单据号
		//SpareStr14:状态
		//SpareStr15:出帐标志
		//SpareStr16:帐单号
/*	2007/2/11 yangchen move	
		//SpareTime1:支付时间1
		//SpareTime2:支付时间2
*/
		//beginTime :创建开始时间
		//endTime   ：创建结束时间
		
		//SpareStr17:创建来源
		//SpareStr18:预存款类型
		//SpareStr19:收费人operatorid
		//---------------用于支付查询-------


		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

		//*************************************用于所有支付记录查询****************************************
		//SpareStr2:客户证号(下面使用了两个不同的来源参数,一个是给帐务管理下的支付记录查询用的(txtCustID,txtAcctID),一个是给客户下的用的(txtCustomerID,txtAccountID) add by 杨晨)
		if(WebUtil.StringHaveContent(request.getParameter("txtCustID")))
			dto.setSpareStr2(request.getParameter("txtCustID"));
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			dto.setSpareStr2(request.getParameter("txtCustomerID"));
		//SpareStr3:帐户号
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctID")))
			dto.setSpareStr3(request.getParameter("txtAcctID"));
		if(WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
			dto.setSpareStr3(request.getParameter("txtAccountID"));
		//SpareStr4:组织（收款组织）
		if(WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
			dto.setSpareStr4(request.getParameter("txtOrgID"));
		//SpareStr5:区域
		if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			dto.setSpareStr5(request.getParameter("txtDistrictID"));
		//SpareStr6:付费方式
		if(WebUtil.StringHaveContent(request.getParameter("txtMOPID")))
			dto.setSpareStr6(request.getParameter("txtMOPID"));
		//SpareStr7:支付类别
		if(WebUtil.StringHaveContent(request.getParameter("txtPayType")))
			dto.setSpareStr7(request.getParameter("txtPayType"));
		//SpareStr8:代用券类型
		if(WebUtil.StringHaveContent(request.getParameter("txtTicketType")))
			dto.setSpareStr8(request.getParameter("txtTicketType"));
		//SpareStr9:券号
		if(WebUtil.StringHaveContent(request.getParameter("txtTicketNo")))
			dto.setSpareStr9(request.getParameter("txtTicketNo"));
		//SpareStr10:关联单据类型
		if(WebUtil.StringHaveContent(request.getParameter("txtReferType")))
			dto.setSpareStr10(request.getParameter("txtReferType"));
		//SpareStr11:关联单据号
		if(WebUtil.StringHaveContent(request.getParameter("txtReferID")))
			dto.setSpareStr11(request.getParameter("txtReferID"));
		//SpareStr12:来源
		if(WebUtil.StringHaveContent(request.getParameter("txtSourceType")))
			dto.setSpareStr12(request.getParameter("txtSourceType"));
		//SpareStr13:来源单据号
		if(WebUtil.StringHaveContent(request.getParameter("txtSourceRecordID")))
			dto.setSpareStr13(request.getParameter("txtSourceRecordID"));
		//SpareStr14:状态
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setSpareStr14(request.getParameter("txtStatus"));
		//SpareStr15:出帐标志
		if(WebUtil.StringHaveContent(request.getParameter("txtInvoicedFlag")))
			dto.setSpareStr15(request.getParameter("txtInvoicedFlag"));
        //SpareStr16:帐单号
		if (WebUtil.StringHaveContent(request.getParameter("txtInvoiceNo")))
			dto.setSpareStr16(request.getParameter("txtInvoiceNo"));
/*  2007/2/11 yangchen move	
		//SpareTime1:创建起始时间
		if(WebUtil.StringHaveContent(request.getParameter("txtPaymentTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtPaymentTime1")));
		//SpareTime2:创建截止时间
		if(WebUtil.StringHaveContent(request.getParameter("txtPaymentTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtPaymentTime2")));
*/
/*   yangchen 2007/2/11  add  start */		
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
	    //创建来源
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreatingMethod")))
			dto.setSpareStr17(request.getParameter("txtCreatingMethod"));
	    //预存款类型
	    if (WebUtil.StringHaveContent(request.getParameter("txtPrePaymentType")))
			dto.setSpareStr18(request.getParameter("txtPrePaymentType"));
	    //收款人operatorid
	    if (WebUtil.StringHaveContent(request.getParameter("txtCollectorID")))
			dto.setSpareStr19(request.getParameter("txtCollectorID"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtAddress")))
	    	dto.setSpareStr20(request.getParameter("txtAddress"));
/*   yangchen 2007/2/11  add  end */			
		//****************************用于详细支付记录查询*****************************************
		//SpareStr1:记录ID
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNO")) && (!"0".equals(request.getParameter("txtSeqNO")))){
			dto.setSpareStr1(request.getParameter("txtSeqNO"));
		}

		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_PAMENTT_RECORD);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
