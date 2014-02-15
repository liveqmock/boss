package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VODInterfaceLogDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVODInterfaceEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * the query web action class of all finacial information settiings
 * 
 * @author 260425w 2006-5-30 15:48:24
 */
public class QueryVODInterfaceLogWebAction extends QueryWebAction {
	 
	 protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
			
        VODInterfaceLogDTO dto = new VODInterfaceLogDTO();
			 
		   if (WebUtil.StringHaveContent(request.getParameter("txtType")))
		        dto.setType(request.getParameter("txtType"));
		    if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeBegin")))
		        dto.setCreateTimeBegin(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTimeBegin")));
		    if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeEnd")))
		        dto.setCreateTimeEnd(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTimeEnd")));
		    if (WebUtil.StringHaveContent(request.getParameter("txtDeviceNO")))
		        dto.setDeviceNO(request.getParameter("txtDeviceNO"));
		    if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
		        dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		    if (WebUtil.StringHaveContent(request.getParameter("txtSeqNO")))
		        dto.setSeqNO(WebUtil.StringToInt(request.getParameter("txtSeqNO")));
		    if (WebUtil.StringHaveContent(request.getParameter("txtProcessResult")))
		        dto.setProcessResult(request.getParameter("txtProcessResult"));
		    if (WebUtil.StringHaveContent(request.getParameter("txtQueueID")))
		        dto.setQueueID(WebUtil.StringToInt(request.getParameter("txtQueueID")));
		    return new QueryVODInterfaceEJBEvent(dto, pageFrom, pageTo, QueryVODInterfaceEJBEvent.QUERY_LOG_LIST);
		    
		}
	    
	}

	 

	 
	 

	 

	 
		 

	 
 
