package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VOIPRecordDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryVOIPInfoEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryVOIPRecordWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		VOIPRecordDTO dto=new VOIPRecordDTO();
		if(WebUtil.StringHaveContent(request.getParameter("txtBillingCycleID"))){
			dto.setBillingCycleID(WebUtil.StringToInt(request.getParameter("txtBillingCycleID")));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctID"))){
			dto.setAcctID(WebUtil.StringToInt(request.getParameter("txtAcctID")));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtCustID"))){
			dto.setCustID(WebUtil.StringToInt(request.getParameter("txtCustID")));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtPointOrigin"))){
			dto.setPointOrigin(request.getParameter("txtPointOrigin"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctItemTypeID"))){
			dto.setAcctItemTypeID(request.getParameter("txtAcctItemTypeID"));
		}
		if(WebUtil.StringHaveContent(request.getParameter("txtSourceType"))){
			dto.setSourceType(request.getParameter("txtSourceType"));
		}
		
		return new QueryVOIPInfoEJBEvent(dto,pageFrom,pageTo,QueryVOIPInfoEJBEvent.QUERY_VOIP_RECORD_LIST);
	}

}
