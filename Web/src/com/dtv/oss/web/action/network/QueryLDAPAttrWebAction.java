package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LdapAttrConfigDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.NetworkQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryLDAPAttrWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		LdapAttrConfigDTO dto =new LdapAttrConfigDTO();
		
		  if (WebUtil.StringHaveContent(request.getParameter("txtAttrName")))
	         dto.setAttrName(request.getParameter("txtAttrName"));
	     
	 
		
		return new NetworkQueryEJBEvent(dto,0,0,NetworkQueryEJBEvent.LDAP_ATTR_QUERY);	
	}
    
}
