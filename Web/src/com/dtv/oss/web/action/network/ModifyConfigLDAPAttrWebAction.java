package com.dtv.oss.web.action.network;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LdapAttrConfigDTO;
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
public class ModifyConfigLDAPAttrWebAction extends GeneralWebAction {

	boolean doPost = false;
	 
	protected boolean needCheckToken()
	{
		return doPost;
	}
	 
	 
	
   public void doStart(HttpServletRequest request)
	{
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm")))
		{
			if (request.getParameter("confirm").equalsIgnoreCase("true")) 
			{
				doPost = true;
				 
			 
			}
		}
		 
	 }

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	LdapAttrConfigDTO dto = new LdapAttrConfigDTO();
        
    	ConfigLdapEJBEvent event= new ConfigLdapEJBEvent();
       
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.ADD_LDAPATTR);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.MODIFY_LDAPATTR);
	    }
	   
	    if (WebUtil.StringHaveContent(request.getParameter("txtLength")))
	       dto.setLength(WebUtil.StringToInt(request.getParameter("txtLength")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtAttrName")))
		      dto.setAttrName(request.getParameter("txtAttrName")); 
	    if (WebUtil.StringHaveContent(request.getParameter("txtFixedFlag")))
		    dto.setFixedFlag(request.getParameter("txtFixedFlag")); 
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtFixedValue")))
		    dto.setFixedValue(request.getParameter("txtFixedValue"));   
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
    	   dto.setStatus(request.getParameter("txtStatus"));
    	 
    	if (WebUtil.StringHaveContent(request.getParameter("txtPrefix")))
            dto.setPrefix(request.getParameter("txtPrefix"));
    	 
    	 
           event.setAttrDto(dto);
        
        return event;
    }
}
