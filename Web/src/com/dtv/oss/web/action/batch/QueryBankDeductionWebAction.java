package com.dtv.oss.web.action.batch;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.QueryBatchEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryBankDeductionWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		//DTO封装参数说明
		//------------------用于托收头记录查询-------
		//SpareStr1操作批号
		//SpareStr2:处理状态
		//SpareStr3:支付方式
		//SpareStr4:帐务周期
		//SpareTime1:记录创建起始时间
		//SpareTime2:记录创建截止时间
		//---------------结束托收头记录查询-------
		
		//------------用于托收明细的查询-----
		//SpareStr5:客户证号
		//SpareStr6:客户类型
		//SpareStr7:客户所在区域
		//---------结束托收明细的查询-----
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//*************************************托收头记录查询****************************************
		//SpareStr1操作批号
		if(WebUtil.StringHaveContent(request.getParameter("txtBatchNo")))
			dto.setSpareStr1(request.getParameter("txtBatchNo"));
		//SpareStr2:处理状态
		if(WebUtil.StringHaveContent(request.getParameter("txtProcessStatus")))
			dto.setSpareStr2(request.getParameter("txtProcessStatus"));
		//SpareStr3:支付方式
		if(WebUtil.StringHaveContent(request.getParameter("txtMopID")))
			dto.setSpareStr3(request.getParameter("txtMopID"));
		//SpareStr4:帐务周期
		if(WebUtil.StringHaveContent(request.getParameter("txtBillingCycleID")))
			dto.setSpareStr4(request.getParameter("txtBillingCycleID"));
		//SpareTime1:记录创建起始时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:记录创建截止时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		
		
		//****************************用于托收明细的查询*****************************************
		//SpareStr5:客户证号
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")) && (!"0".equals(request.getParameter("txtCustomerID")))){
			//进行格式检查
			if(WebUtil.StringToInt(request.getParameter("txtCustomerID"))==0)
				throw new WebActionException("客户证号，必须为整数！");
			dto.setSpareStr5(request.getParameter("txtCustomerID"));
		}
		//SpareStr6:客户类型
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
			dto.setSpareStr6(request.getParameter("txtCustomerType"));
		//SpareStr7:客户所在区域
		if(WebUtil.StringHaveContent(request.getParameter("txtCustDistrictID")))
			dto.setSpareStr7(request.getParameter("txtCustDistrictID"));
		//SpareStr7:客户所在区域
		if(WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
			dto.setSpareStr8(request.getParameter("txtSeqNo"));
		
		if("header".equalsIgnoreCase(request.getParameter("txtActionType")))
			return new QueryBatchEJBEvent(dto,pageFrom,pageTo,QueryBatchEJBEvent.QUERY_TYPE_BANK_DEDUCTION_HEADER);
		else if("detail".equalsIgnoreCase(request.getParameter("txtActionType")))
			return new QueryBatchEJBEvent(dto,pageFrom,pageTo,QueryBatchEJBEvent.QUERY_TYPE_BANK_DEDUCTION_DETAIL);
		else 
			throw new WebActionException("查询类型未知，无法完成此操作！");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
}
