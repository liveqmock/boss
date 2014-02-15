package com.dtv.oss.web.action.batch;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.QueryBatchEJBEvent;
import com.dtv.oss.web.action.DownloadWebAction;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryBatchWebAction extends DownloadWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		//DTO封装参数说明
		//------------------用于批量查询-------
		//SpareStr1:查询操作名称
		//SpareStr2:结果集类型
		//SpareStr3:执行方式
		//SpareStr4:状态
		//SpareStr5:状态2
		//SpareTime1:记录创建起始时间
		//SpareTime2:记录创建截止时间
		//SpareTime3:预定执行起始时间
		//SpareTime4:预定执行截止时间
		//SpareTime5:实际执行起始时间
		//SpareTime6:实际执行截止时间
		//---------------结束用于批量查询-------
		
		//------------用于批量查询结果的查询-----
		//SpareStr8:记录ID
		//SpareStr9:状态
		//SpareStr10:用户证号
		//SpareStr11:账户号
		//SpareStr12:业务账户号
		//SpareStr13:运营商产品
		//SpareStr14:运营商产品包
		//SpareStr15:促销活动
		//---------结束用于批量查询结果的查询-----
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//*************************************用于批量查询****************************************
		//递交查询的页面类型 用于区别是否模板查询
		if(WebUtil.StringHaveContent(request.getParameter("txtTemplateFlag")))
			dto.setSpareStr6(request.getParameter("txtTemplateFlag"));
		//SpareStr1:查询操作名称
		if(WebUtil.StringHaveContent(request.getParameter("txtQueryName")))
			dto.setSpareStr1(request.getParameter("txtQueryName"));
		//SpareStr2:结果集类型
		if(WebUtil.StringHaveContent(request.getParameter("txtQueryType")))
			dto.setSpareStr2(request.getParameter("txtQueryType"));
		//SpareStr3:执行方式
		if(WebUtil.StringHaveContent(request.getParameter("txtScheduleType")))
			dto.setSpareStr3(request.getParameter("txtScheduleType"));
		//SpareStr4:状态
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			dto.setSpareStr4(request.getParameter("txtStatus"));
		//SpareStr5:状态2
		if(WebUtil.StringHaveContent(request.getParameter("txtStatus1")))
			dto.setSpareStr5(request.getParameter("txtStatus1"));
		//SpareTime1:记录创建起始时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:记录创建截止时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		//SpareTime3:预定执行起始时间
		if(WebUtil.StringHaveContent(request.getParameter("txtScheduleTime1")))
			dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtScheduleTime1")));
		//SpareTime4:预定执行截止时间
		if(WebUtil.StringHaveContent(request.getParameter("txtScheduleTime2")))
			dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtScheduleTime2")));
		//SpareTime5:实际执行起始时间
		if(WebUtil.StringHaveContent(request.getParameter("txtPerformTime1")))
			dto.setSpareTime5(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtPerformTime1")));
		//SpareTime6:实际执行截止时间
		if(WebUtil.StringHaveContent(request.getParameter("txtPerformTime2")))
			dto.setSpareTime6(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtPerformTime2")));
		
		
		//****************************用于批量查询结果的查询*****************************************
		
		//SpareStr8:记录ID
		if(WebUtil.StringHaveContent(request.getParameter("txtQueryID")) && (!"0".equals(request.getParameter("txtQueryID")))){
			//进行格式检查
			if(WebUtil.StringToInt(request.getParameter("txtQueryID"))==0)
				throw new WebActionException("记录ID格式错误，必须为整数！");
			dto.setSpareStr8(request.getParameter("txtQueryID"));
			setSubType(request.getParameter("txtQueryType"));
			setQueryParameter(new Integer(request.getParameter("txtQueryID")));
		}
		//SpareStr9:状态
		if(WebUtil.StringHaveContent(request.getParameter("txtRusultItemStatus")))
			dto.setSpareStr9(request.getParameter("txtRusultItemStatus"));
		//SpareStr10:用户证号
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			dto.setSpareStr10(request.getParameter("txtCustomerID"));
		//SpareStr11:账户号
		if(WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
			dto.setSpareStr11(request.getParameter("txtAccountID"));
		//SpareStr12:业务账户号
		if(WebUtil.StringHaveContent(request.getParameter("txtUserID")))
			dto.setSpareStr12(request.getParameter("txtUserID"));
		//SpareStr13:运营商产品
		if(WebUtil.StringHaveContent(request.getParameter("txtCPProductIDList")))
			dto.setSpareStr13(request.getParameter("txtCPProductIDList"));
		//SpareStr14:运营商产品包
		if(WebUtil.StringHaveContent(request.getParameter("txtCPProductPackageIDList")))
			dto.setSpareStr14(request.getParameter("txtCPProductPackageIDList"));
		//SpareStr15:促销活动
		if(WebUtil.StringHaveContent(request.getParameter("txtCPCampaignIDList")))
			dto.setSpareStr15(request.getParameter("txtCPCampaignIDList"));
		
		if("batch".equalsIgnoreCase(request.getParameter("txtActionType")))
			return new QueryBatchEJBEvent(dto,pageFrom,pageTo,QueryBatchEJBEvent.QUERY_TYPE_BATCH_QUERY);
		else if("result".equalsIgnoreCase(request.getParameter("txtActionType")))
			return new QueryBatchEJBEvent(dto,pageFrom,pageTo,QueryBatchEJBEvent.QUERY_TYPE_BATCH_RESULT_ITEM);
		else 
			throw new WebActionException("查找参数错误！");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
}
