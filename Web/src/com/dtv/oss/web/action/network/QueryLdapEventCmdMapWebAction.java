package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LdapEventCmdMapDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.service.ejbevent.network.NetworkQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;

public class QueryLdapEventCmdMapWebAction extends QueryWebAction {
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		
	 	LdapEventCmdMapDTO dto =new LdapEventCmdMapDTO();
		
		  if (WebUtil.StringHaveContent(request.getParameter("txtMapID")))
	         dto.setMapID(WebUtil.StringToInt(request.getParameter("txtMapID")));
	     
	 
		
		return new NetworkQueryEJBEvent(dto,0,0,NetworkQueryEJBEvent.LDAP_EVENTCMDMAP_QUERY);	
	}
    
}
