package com.dtv.oss.web.action.batch;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.BatchJobEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.util.TimestampUtility;

public class BatchJobWebAction extends GeneralWebAction {
	boolean confirmPost = false;
	protected boolean needCheckToken()
	{
		return confirmPost;
	}
	public void doStart(HttpServletRequest request){
		super.doStart(request);
	}
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		/**
		 * 动作定义（txtActionType）
		 * 批量任务单创建:      create
		 * 批量任务单修改:      modify
		 * 批量任务单取消:      cancel
		 */
		BatchJobEJBEvent event=new BatchJobEJBEvent();
		if("create".equals(request.getParameter("txtAction")))
			event.setActionType(EJBEvent.BATCHJOB_CREATE);
		else if("modify".equals(request.getParameter("txtAction")))
			event.setActionType(EJBEvent.BATCHJOB_MODIFY);
		else if("cancel".equals(request.getParameter("txtAction")))
			event.setActionType(EJBEvent.BATCHJOB_CANCEL);
		else
			throw new WebActionException("批量任务单动作类型未知！");

		BatchJobDTO dto =new BatchJobDTO();

		//填充DTO
		dto.setDescription(request.getParameter("txtBatchJobRemark"));
		dto.setDtCreate(WebUtil.StringToTimestamp(request.getParameter("txtCreateDate")));
		dto.setJobType(request.getParameter("txtJobType"));
		dto.setName(request.getParameter("txtBatchJobName"));
		dto.setReferId(WebUtil.StringToInt(request.getParameter("txtBatchJobReferID")));
		dto.setReferType(request.getParameter("txtJobReferType"));
		dto.setTotoalRecordNo(WebUtil.StringToInt(request.getParameter("txtBatchJobAmount")));
		dto.setCreateOpId(WebUtil.StringToInt(request.getParameter("txtCreateOperatorID")));
		dto.setCreateTime(WebUtil.StringToTimestamp(request.getParameter("txtCreateDate")));
		dto.setScheduleType(request.getParameter("txtScheduleType"));
		dto.setStatus(request.getParameter("txtJobStatus"));
		dto.setScheduleTime(WebUtil.StringToTimestamp(request.getParameter("txtScheduleTime")));
		dto.setBatchId(WebUtil.StringToInt(request.getParameter("txtBatchID")));
		if("create".equals(request.getParameter("txtAction")))
			dto.setDtLastmod(TimestampUtility.getCurrentDateWithoutTime());
		else
			dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
		
		event.setScheduleSendNumber(WebUtil.StringToInt(request.getParameter("txtScheduleSendNumber")));
		event.setScheduleSendTimeInterval(WebUtil.StringToInt(request.getParameter("txtScheduleSendTimeInterval")));
		
		event.setBatchJobDTO(dto);
		return event;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
