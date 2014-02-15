package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LdapHostDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.service.ejbevent.network.NetworkQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryLDAPHostWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		 LdapHostDTO dto =new LdapHostDTO();
		
		  if (WebUtil.StringHaveContent(request.getParameter("txtHostID")))
	         dto.setHostID(WebUtil.StringToInt(request.getParameter("txtHostID")));
	     
	 
		
		return new NetworkQueryEJBEvent(dto,0,0,NetworkQueryEJBEvent.LDAP_HOST_QUERY);	
	}
    
}
