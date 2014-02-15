package com.dtv.oss.web.action.work;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.WorkQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryScheduleWebAction extends QueryWebAction {

	/**
	 * 查询的项：CommonQueryConditionDTO代表的查找项说明
	 * 
	 * 事件类型（txtActionType） 								spareStr3
	 * 事件原因(txtEventReason) 								spareStr4
	 * 排程执行时间(txtExecuteDate1、txtExecuteDate2)			spareTime1、spareTime2
	 * 排程创建时间（txtCreateDate1、txtCreateDate2）			spareTime3、spareTime4
	 * 用户证号(txtCustomerID)								spareStr5
	 * 客户名称(txtCustomerName)								spareStr6
	 * 排程任务状态(txtStatus) 								spareStr7
	 * 
	 * 业务帐户查(txtSAID) : spareStr1
	 * 
	 * 排程批处理ID查(txtBatchID) spareStr2
	 */
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
	    
		//排程批处理ID
	    if (WebUtil.StringHaveContent(request.getParameter("txtBatchID")))
	        dto.setSpareStr1(request.getParameter("txtBatchID"));
	    //业务帐户查
	    if (WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID")))
	      dto.setSpareStr2(request.getParameter("txtServiceAccountID"));
	   //排程任务状态
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
	        dto.setSpareStr7(request.getParameter("txtStatus"));
	    //客户名称
	    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerName")))
	      dto.setSpareStr6(request.getParameter("txtCustomerName"));
	    //用户证号
	    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			dto.setSpareStr5(request.getParameter("txtCustomerID"));
	    //事件原因
	    if (WebUtil.StringHaveContent(request.getParameter("txtEventReason")))
	        dto.setSpareStr4(request.getParameter("txtEventReason"));
	    //事件类型
	    if (WebUtil.StringHaveContent(request.getParameter("txtEventType")))
	        dto.setSpareStr3(request.getParameter("txtEventType"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtReferType")))
	        dto.setSpareStr8(request.getParameter("txtReferType"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtAddress")))
	    	dto.setSpareStr9(request.getParameter("txtAddress"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCounty")))
	    	dto.setDistrict(WebUtil.StringToInt(request.getParameter("txtCounty")));
	    
	        
	    //排程执行时间
	    if (WebUtil.StringHaveContent(request.getParameter("txtExecuteDate1")))
	    	dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtExecuteDate1")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtExecuteDate2")))
	    	dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtExecuteDate2")));
	    
	    //排程创建时间
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreateDate1")))
	    	dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateDate1")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtCreateDate2")))
	    	dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateDate2")));
	    
	    if("customer".equalsIgnoreCase(request.getParameter("txtActionType")))
	    	return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_SCHEDULE_CUSTOMER);
	    else if("all".equalsIgnoreCase(request.getParameter("txtActionType")))
	    	return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_SCHEDULE_ALL);
	    else if("detail".equalsIgnoreCase(request.getParameter("txtActionType")))
	    	return new WorkQueryEJBEvent(dto, pageFrom, pageTo, WorkQueryEJBEvent.QUERY_TYPE_SCHEDULE_DETAIL);
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
