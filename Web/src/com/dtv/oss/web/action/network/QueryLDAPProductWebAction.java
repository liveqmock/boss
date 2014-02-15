package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LdapProductDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.NetworkQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryLDAPProductWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		 LdapProductDTO dto =new LdapProductDTO();
		
		  if (WebUtil.StringHaveContent(request.getParameter("txtProductName")))
	         dto.setProductName(request.getParameter("txtProductName"));
	     
	 
		
		return new NetworkQueryEJBEvent(dto,0,0,NetworkQueryEJBEvent.LDAP_PRODUCT_QUERY);	
	}
    
}
