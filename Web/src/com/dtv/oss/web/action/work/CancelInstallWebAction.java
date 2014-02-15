package com.dtv.oss.web.action.work;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.JobCardEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.web.util.*;


public class CancelInstallWebAction extends GeneralWebAction {
	
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	//取消安装工单
    	JobCardEJBEvent jcEvent = new JobCardEJBEvent();
        JobCardProcessDTO jcpDto=new JobCardProcessDTO();       
        jcpDto.setJcId(Integer.parseInt(request.getParameter("txtJobCardID")));
        jcpDto.setWorkResultReason(request.getParameter("txtWorkResultReason"));
        jcpDto.setWorkResult(CommonKeys.CSIPROCESSLOG_ACTION_INSTALLCANCEL);
        jcEvent.setActionType(EJBEvent.CANCEL_INSTALLATION_JOB_CARD);
        jcEvent.setJobcardProcessDto(jcpDto);
      	return jcEvent;
    }
}
