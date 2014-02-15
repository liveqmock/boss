/*
 * Created on 2007-9-10
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.web.action.work;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.JobCardEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;

/**
 * @author 260904l
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CancelRepairWebAction extends GeneralWebAction{

	/* (non-Javadoc)
	 * @see com.dtv.oss.web.action.GeneralWebAction#encapsulateData(javax.servlet.http.HttpServletRequest)
	 */
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		//取消维修工单
		JobCardEJBEvent jcEvent = new JobCardEJBEvent();
		JobCardProcessDTO jcpDto=new JobCardProcessDTO();
		if(WebUtil.StringHaveContent(request.getParameter("txtJobCardID"))){
			jcpDto.setJcId(WebUtil.StringToInt(request.getParameter("txtJobCardID")));
		}		
		jcpDto.setWorkResultReason(request.getParameter("txtWorkResultReason"));
		jcpDto.setWorkResult(CommonKeys.CUSTOMERPROBLEM_STATUS_FAIL);
		jcEvent.setActionType(EJBEvent.CANCEL_REPAIR_JOB_CARD);
		jcEvent.setJobcardProcessDto(jcpDto);
		return jcEvent;

	}		

}
