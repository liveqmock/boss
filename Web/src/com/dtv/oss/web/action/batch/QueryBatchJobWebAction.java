package com.dtv.oss.web.action.batch;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.QueryBatchJobEJBEvent;
import com.dtv.oss.web.action.DownloadWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryBatchJobWebAction extends DownloadWebAction{
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		//DTO封装参数说明
		//------------------用于批处理任务单查询-------
		//SpareStr1:任务单号
		//SpareStr2:任务名称
		//SpareStr3:任务状态
		//SpareStr4:任务类型
		//SpareStr5:设定执行方式
		//SpareStr6:创建人
		//SpareTime1:创建起始时间
		//SpareTime2:创建截止时间
		//SpareTime3:预定执行起始时间
		//SpareTime4:预定执行截止时间
		//---------------结束用于批处理任务单查询-------

		//------------用于批处理任务单结果集的查询-----
		//SpareStr1:任务单号
		//---------结束用于批处理任务单结果集的查询-----

		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

		//*************************************用于批处理任务单查询****************************************
		//SpareStr2:任务名称
		if(WebUtil.StringHaveContent(request.getParameter("txtBatchJobName")))
			dto.setSpareStr2(request.getParameter("txtBatchJobName"));
		//SpareStr3:任务状态
		if(WebUtil.StringHaveContent(request.getParameter("txtBatchJobStatus")))
			dto.setSpareStr3(request.getParameter("txtBatchJobStatus"));
		//SpareStr4:任务类型
		if(WebUtil.StringHaveContent(request.getParameter("txtJobType")))
			dto.setSpareStr4(request.getParameter("txtJobType"));
		//SpareStr5:设定执行方式
		if(WebUtil.StringHaveContent(request.getParameter("txtScheduleType")))
			dto.setSpareStr5(request.getParameter("txtScheduleType"));
		//SpareStr6:创建人
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateName")))
			dto.setSpareStr6(request.getParameter("txtCreateName"));
		//SpareTime1:创建起始时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateStartDate")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateStartDate")));
		//SpareTime2:创建截止时间
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateEndDate")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateEndDate")));
		//SpareTime3:预定执行起始时间
		if(WebUtil.StringHaveContent(request.getParameter("txtActionStartDate")))
			dto.setSpareTime3(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtActionStartDate")));
		//SpareTime4:预定执行截止时间
		if(WebUtil.StringHaveContent(request.getParameter("txtActionEndDate")))
			dto.setSpareTime4(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtActionEndDate")));

		//****************************用于批处理任务单结果集的查询*****************************************
		//SpareStr1:记录ID
		if(WebUtil.StringHaveContent(request.getParameter("txtBatchID")) && (!"0".equals(request.getParameter("txtBatchID")))){
			//进行格式检查
			if(WebUtil.StringToInt(request.getParameter("txtBatchID"))==0)
				throw new WebActionException("记录ID格式错误，必须为整数！");
			dto.setSpareStr1(request.getParameter("txtBatchID"));
			setSubType(request.getParameter("txtJobType"));
			setQueryParameter(new Integer(request.getParameter("txtBatchID")));
		}

		if("batch".equalsIgnoreCase(request.getParameter("txtActionType")))
			return new QueryBatchJobEJBEvent(dto,pageFrom,pageTo,QueryBatchJobEJBEvent.BATCHJOB_QUERY);
		else if("result".equalsIgnoreCase(request.getParameter("txtActionType")))
			return new QueryBatchJobEJBEvent(dto,pageFrom,pageTo,QueryBatchJobEJBEvent.BATCHJOB_ITEM_QUERY);
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
