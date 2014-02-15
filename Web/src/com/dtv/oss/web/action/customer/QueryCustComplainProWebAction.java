package com.dtv.oss.web.action.customer;
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustComplainProcessDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
public class QueryCustComplainProWebAction extends QueryWebAction{

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		CustComplainProcessDTO pdto=new CustComplainProcessDTO();
		if(WebUtil.StringHaveContent(request.getParameter("txtCustComplainId"))){
			pdto.setCustComplainId(WebUtil.StringToInt(request.getParameter("txtCustComplainId")));
		}
		
		return new CsrQueryEJBEvent(pdto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_COMPLAIN_PROCESS);
	}

}
