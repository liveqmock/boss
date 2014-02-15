package com.dtv.oss.web.action.batch;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.QueryBatchEJBEvent;
import com.dtv.oss.web.action.DownloadWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class BatchSendMessageQueryWebAction extends DownloadWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		//DTO封装参数说明
		//------------------用于批量发送消息信息查询------
		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
		
		//*************************************用于批量发送消息信息查询****************************************
		if(WebUtil.StringHaveContent(request.getParameter("txtJobType")))
			dto.setSpareStr1(request.getParameter("txtJobType"));
		
		//SpareTime1:导入日期开始点
		if(WebUtil.StringHaveContent(request.getParameter("txtImportDateFrom")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtImportDateFrom")));
		//SpareTime2:导入日期结束点
		if(WebUtil.StringHaveContent(request.getParameter("txtImportDateTo")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtImportDateTo")));
		//SpareStr1:记录ID
		if(WebUtil.StringHaveContent(request.getParameter("txtNo")) && (!"0".equals(request.getParameter("txtNo")))){
			//进行格式检查
			if(WebUtil.StringToInt(request.getParameter("txtNo"))==0)
				throw new WebActionException("记录ID格式错误，必须为整数！");
			dto.setSpareStr1(request.getParameter("txtNo"));
			setSubType(request.getParameter("txtSubType"));
			setQueryParameter(new Integer(request.getParameter("txtNo")));
		}
		return new QueryBatchEJBEvent(dto,pageFrom,pageTo,QueryBatchEJBEvent.QUERY_TYPE_BATCH_SEND_MESSAGE);
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
}
