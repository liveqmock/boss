package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LdapHostDTO;
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
 * Date: 2006-4-14
 * Time: 15:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ModifyConfigLDAPHostWebAction extends GeneralWebAction {

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
    	
    	LdapHostDTO dto = new LdapHostDTO();
        
    	ConfigLdapEJBEvent event= new ConfigLdapEJBEvent();
        
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.ADD_LDAP_HOST);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.MODIFY_LDAP_HOST);
	    }
	   
	    if (WebUtil.StringHaveContent(request.getParameter("txtHostID")))
	       dto.setHostID(WebUtil.StringToInt(request.getParameter("txtHostID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtHostName")))
		      dto.setHostName(request.getParameter("txtHostName")); 
	    if (WebUtil.StringHaveContent(request.getParameter("txtProtocolType")))
		    dto.setProtocolType(request.getParameter("txtProtocolType")); 
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtIpAddress")))
		    dto.setIpAddress(request.getParameter("txtIpAddress"));   
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
    	   dto.setStatus(request.getParameter("txtStatus"));
    	 
    	if (WebUtil.StringHaveContent(request.getParameter("txtPort")))
            dto.setPort(WebUtil.StringToInt(request.getParameter("txtPort")));
    	if (WebUtil.StringHaveContent(request.getParameter("txtLoginID")))
            dto.setLoginID(request.getParameter("txtLoginID"));
    	if (WebUtil.StringHaveContent(request.getParameter("txtLoginPWD")))
            dto.setLoginPWD(request.getParameter("txtLoginPWD"));
    	
    	if (WebUtil.StringHaveContent(request.getParameter("txtcmentrydir")))
            dto.setCmentrydir(request.getParameter("txtcmentrydir"));
    	 
    	 
           event.setLdapHostDto(dto);
        
        return event;
    }
}
