package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LdapEventCmdMapDTO;
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
public class ModifyConfigLDAPCmdMapWebAction extends GeneralWebAction {

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
    	
    	LdapEventCmdMapDTO dto = new LdapEventCmdMapDTO();
        
    	ConfigLdapEJBEvent event= new ConfigLdapEJBEvent();
        
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.ADD_LDAP_EVENTCMDMAP);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.MODIFY_LDAP_EVENTCMDMAP);
	    }
	   
	    if (WebUtil.StringHaveContent(request.getParameter("txtMapID")))
	       dto.setMapID(WebUtil.StringToInt(request.getParameter("txtMapID")));
	    if (WebUtil.StringHaveContent(request.getParameter("txtEventClassID")))
		      dto.setEventClassID(WebUtil.StringToInt(request.getParameter("txtEventClassID"))); 
	    if (WebUtil.StringHaveContent(request.getParameter("txtCommandName")))
		    dto.setCommandName(request.getParameter("txtCommandName")); 
	    if (WebUtil.StringHaveContent(request.getParameter("txtConditionID")))
		    dto.setConditionID(WebUtil.StringToInt(request.getParameter("txtConditionID")));   
	    if (WebUtil.StringHaveContent(request.getParameter("txtPriority")))
		    dto.setPriority(WebUtil.StringToInt(request.getParameter("txtPriority")));   
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
    	   dto.setStatus(request.getParameter("txtStatus"));
    	 
     
    	 
    	 
           event.setLdapEventCmdMapDto(dto);
        
        return event;
    }
}
