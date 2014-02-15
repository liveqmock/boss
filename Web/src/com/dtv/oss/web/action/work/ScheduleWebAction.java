package com.dtv.oss.web.action.work;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.ScheduleEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class ScheduleWebAction extends GeneralWebAction {

	boolean confirmPost = false;
	
	protected boolean needCheckToken()
	{
		return confirmPost;
	}
	
	public void doStart(HttpServletRequest request) {
		super.doStart(request);
    	if (WebUtil.StringHaveContent(request.getParameter("txtDoPost"))) {
			if(request.getParameter("txtDoPost").equalsIgnoreCase("TRUE"))
					confirmPost = true;
			}
	}	
	
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		ScheduleEJBEvent event=new ScheduleEJBEvent();
		
		if(!WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
            throw new WebActionException("没有相关的客户信息标识");
		
		if(!WebUtil.StringHaveContent(request.getParameter("txtActionType")))
            throw new WebActionException("操作类型未知");
		
		if("create".equalsIgnoreCase(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.SCHEDULE_CREATE);
		if("modify".equalsIgnoreCase(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.SCHEDULE_MODIFY);
		if("cancel".equalsIgnoreCase(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.SCHEDULE_CANCEL);
		
		//当动作为修改或创建的时候，必须要输入执行时间
		if("create".equalsIgnoreCase(request.getParameter("txtActionType")) || "modify".equalsIgnoreCase(request.getParameter("txtActionType"))){
			if(!WebUtil.StringHaveContent(request.getParameter("txtExecuteDate")))
	            throw new WebActionException("没有相应的时间设置");
		}
			
		//排程
		BatchJobDTO batchJobDTO=new BatchJobDTO();
		batchJobDTO.setScheduleTime(WebUtil.StringToTimestamp(request.getParameter("txtExecuteDate")));
		batchJobDTO.setJobType(request.getParameter("txtEventType"));
		
		String eventReason=(request.getParameter("txtEventReason")==null) ? "" : request.getParameter("txtEventReason");
		 
		batchJobDTO.setReasonCode(eventReason);
		String memo=(request.getParameter("txtMemo")==null) ? "" : request.getParameter("txtMemo");
		batchJobDTO.setDescription(memo);
		event.setBatchJobDTO(batchJobDTO);
		
		//产品ID
		String cpIDs =(request.getParameter("txtCPIDs")==null) ? "" : request.getParameter("txtCPIDs");
		Collection cpIDList=new ArrayList();
		String[] cpID =cpIDs.split(";");
		for(int i=0; i<cpID.length; i++)
		{
			cpIDList.add(new Integer(WebUtil.StringToInt(cpID[i])));
		}
		event.setCpIDList(cpIDList);
		

		//排程ID
		String scheduleIDs =(request.getParameter("txtScheduleIDs")==null) ? "" : request.getParameter("txtScheduleIDs");
		Collection scheduleIDList=new ArrayList();
		String[] scheduleID =scheduleIDs.split(";");
		for(int i=0; i<scheduleID.length; i++)
		{
			scheduleIDList.add(new Integer(WebUtil.StringToInt(scheduleID[i])));
		}
		event.setSheduleIDList(scheduleIDList);
		event.setDoPost(confirmPost);
		
		return event;
	}

}
