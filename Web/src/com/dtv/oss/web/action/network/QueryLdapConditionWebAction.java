package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LdapConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.NetworkQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryLdapConditionWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		 LdapConditionDTO	dto = new LdapConditionDTO();  
		
		if(WebUtil.StringHaveContent(request.getParameter("txtCondID")))
			dto.setCondId(WebUtil.StringToInt(request.getParameter("txtCondID").trim()));
		if(WebUtil.StringHaveContent(request.getParameter("txtCondName")))
			dto.setCondName(request.getParameter("txtCondName").trim());
		 
		if(WebUtil.StringHaveContent(request.getParameter("txtHostID")))
			dto.setHostId(WebUtil.StringToInt(request.getParameter("txtHostID").trim()));
		 
		return new NetworkQueryEJBEvent(dto,pageFrom,pageTo,NetworkQueryEJBEvent.LDAP_COND_QUERY);	
	}
    
}
