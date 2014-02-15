package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryBandInterfaceEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class QueryBandInterfaceWebAction  extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		String queryFlag=request.getParameter("queryFlag");
		if(!WebUtil.StringHaveContent(queryFlag)){
			throw new WebActionException("²éÑ¯±êÖ¾¶ªÊ§.");
		}
		if(queryFlag.equalsIgnoreCase("log")){
			CommonQueryConditionDTO dto=new CommonQueryConditionDTO();
			
			if(WebUtil.StringHaveContent(request.getParameter("txtCommandName")))
				dto.setSpareStr1(request.getParameter("txtCommandName"));
			if(WebUtil.StringHaveContent(request.getParameter("txtProcessResult")))
				dto.setSpareStr2(request.getParameter("txtProcessResult"));
			if(WebUtil.StringHaveContent(request.getParameter("txtCustmerID")))
				dto.setSpareStr3(request.getParameter("txtCustmerID"));
			if(WebUtil.StringHaveContent(request.getParameter("txtCustmerName")))
				dto.setSpareStr4(request.getParameter("txtCustmerName"));
			if(WebUtil.StringHaveContent(request.getParameter("txtBandName")))
				dto.setSpareStr5(request.getParameter("txtBandName"));
			
			String srartTime=request.getParameter("txtStartTime");
			if(WebUtil.StringHaveContent(srartTime)){
				dto.setBeginTime(WebUtil.StringToTimestamp(srartTime));
			}
			String endTime=request.getParameter("txtEndTime");
			if(WebUtil.StringHaveContent(endTime)){
				dto.setEndTime(WebUtil.StringToTimestamp(endTime));
			}
			
			
			return new QueryBandInterfaceEJBEvent(dto,pageFrom,pageTo,QueryBandInterfaceEJBEvent.QUERY_BAND_LOG);
		}
		return null;
	}
}
