package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LdapProdToSmsProdDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.NetworkQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryLdapProdToSmsProdWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
		 LdapProdToSmsProdDTO dto =new LdapProdToSmsProdDTO();
		
		  if (WebUtil.StringHaveContent(request.getParameter("txtSmsProductId")))
	         dto.setSmsProductId(WebUtil.StringToInt(request.getParameter("txtSmsProductId")));
	     
	 
		
		return new NetworkQueryEJBEvent(dto,0,0,NetworkQueryEJBEvent.LDAP_SMSPRODTOPROD_QUERY);	
	}
    
}
