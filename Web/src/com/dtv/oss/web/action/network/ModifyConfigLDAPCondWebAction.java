package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LdapAttrConfigDTO;
import com.dtv.oss.dto.LdapConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.ConfigLdapEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: BOSS P5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: chen jiang
 * Date: 2006-7-10
 * Time: 15:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ModifyConfigLDAPCondWebAction extends GeneralWebAction {

	 
   
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	LdapConditionDTO dto = new LdapConditionDTO();
        
    	ConfigLdapEJBEvent event= new ConfigLdapEJBEvent();
       
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.ADD_LDAPCOND);
	    if("update".equals(request.getParameter("Action"))){
	    	 
			event.setActionType(EJBEvent.MODIFY_LDAPCOND);
	    }
	   
	    
	       dto.setCondName(request.getParameter("txtCondName"));
	    
		   dto.setCondString(request.getParameter("txtCondString")); 
	    
		    dto.setHostId(WebUtil.StringToInt(request.getParameter("txtHostID"))); 
	    
	    
		   
	    if (WebUtil.StringHaveContent(request.getParameter("txtCondID")))
	    	 dto.setCondId(WebUtil.StringToInt(request.getParameter("txtCondID")));  
    	 
    	 
            dto.setDescription(request.getParameter("txtDesption"));
    	 
    	 
           event.setCondDto(dto);
        
        return event;
    }
}
