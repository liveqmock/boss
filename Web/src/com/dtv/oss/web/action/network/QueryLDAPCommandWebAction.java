package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LdapCommandDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.NetworkQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;

public class QueryLDAPCommandWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		 LdapCommandDTO dto =new LdapCommandDTO();
		
		  
	 
		
		return new NetworkQueryEJBEvent(dto,0,0,NetworkQueryEJBEvent.LDAP_COMMAND_QUERY);	
	}
    
}
