/*
 * Created on 2007-8-2
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.web.action.catv;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.JobCardEJBEvent;
import com.dtv.oss.web.util.WebUtil;

/**
 * @author 260904l
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CloseCatvWebAction {
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception
		{
   
		  JobCardEJBEvent jcEvent = new JobCardEJBEvent();
		  JobCardProcessDTO jcpDto= new JobCardProcessDTO();
		  jcpDto.setJcId(WebUtil.StringToInt(request.getParameter("txtJobCardID")));
		  jcpDto.setMemo(request.getParameter("txtMemo")); 
		  jcEvent.setActionType(EJBEvent.CATV_CLOSE_JOBCARD);
		  jcEvent.setDoPost(true);
		  jcEvent.setJobcardProcessDto(jcpDto);
		  return  jcEvent;
		}
}
