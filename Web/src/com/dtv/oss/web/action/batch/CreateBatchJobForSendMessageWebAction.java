package com.dtv.oss.web.action.batch;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.custom.GeHuaUploadCustBatchHeaderDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.GehuaBatchMessageEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class CreateBatchJobForSendMessageWebAction extends GeneralWebAction {
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		GehuaBatchMessageEJBEvent event =new GehuaBatchMessageEJBEvent();
		try {
			BatchJobDTO dto =new BatchJobDTO();
			//填充DTO
			dto.setDescription(request.getParameter("txtBatchJobRemark"));
			dto.setJobType(request.getParameter("txtJobType"));
			dto.setReferId(WebUtil.StringToInt(request.getParameter("txtBatchJobReferID")));
			dto.setReferType(request.getParameter("txtJobReferType"));
			dto.setName(request.getParameter("txtBatchJobName"));
			dto.setScheduleType(request.getParameter("txtScheduleType"));
			dto.setScheduleTime(WebUtil.StringToTimestamp(request.getParameter("txtScheduleTime")));
			event.setScheduleSendNumber(WebUtil.StringToInt(request.getParameter("txtScheduleSendNumber")));
			event.setScheduleSendTimeInterval(WebUtil.StringToInt(request.getParameter("txtScheduleSendTimeInterval")));
			event.setBatchJobDTO(dto);
			
			GeHuaUploadCustBatchHeaderDTO custBatchHeaderDTO=new GeHuaUploadCustBatchHeaderDTO();
			custBatchHeaderDTO.setBatchNo(WebUtil.StringToInt(request.getParameter("txtBatchNo")));
			event.setCustBatchHeaderDTO(custBatchHeaderDTO);
			//设置动作类型
			event.setActionType(EJBEvent.BATCH_MESSAGE_CREATEJOB);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
		return event;
	}
}
