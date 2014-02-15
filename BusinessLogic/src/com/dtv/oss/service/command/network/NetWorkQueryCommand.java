package com.dtv.oss.service.command.network;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
 
import com.dtv.oss.service.ejbevent.network.NetworkQueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.network.LDAPCommandListHandler;
import com.dtv.oss.service.listhandler.network.LDAPListHandler;
import com.dtv.oss.service.listhandler.network.LDAPProductListHandler;
import com.dtv.oss.service.listhandler.network.LdapAttrConfigListHandler;
import com.dtv.oss.service.listhandler.network.LdapEventCmdMapListHandler;
import com.dtv.oss.service.listhandler.network.LdapProdToSmsProdListHandler;
import com.dtv.oss.service.listhandler.network.QueryLdapCondListHandler;

public class NetWorkQueryCommand extends QueryCommand {

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		switch (event.getType()) {
		case NetworkQueryEJBEvent.LDAP_HOST_QUERY:
			handler = new LDAPListHandler();
			break;
		case NetworkQueryEJBEvent.LDAP_PRODUCT_QUERY:
			handler = new LDAPProductListHandler();
			break;
		case NetworkQueryEJBEvent.LDAP_COMMAND_QUERY:
			handler = new LDAPCommandListHandler();
			break;	
		case NetworkQueryEJBEvent.LDAP_EVENTCMDMAP_QUERY:
			handler = new LdapEventCmdMapListHandler();
			break;	
		case NetworkQueryEJBEvent.LDAP_SMSPRODTOPROD_QUERY:
			handler = new LdapProdToSmsProdListHandler();
			break;
		case NetworkQueryEJBEvent.LDAP_ATTR_QUERY:
			handler = new LdapAttrConfigListHandler();
			break;		
		case NetworkQueryEJBEvent.LDAP_COND_QUERY:
			handler = new QueryLdapCondListHandler();
			break;	
		 		
		default:
			break;
		}
		return handler;
	}
}