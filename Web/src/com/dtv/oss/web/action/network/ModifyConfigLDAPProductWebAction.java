package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LdapProductDTO;
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
public class ModifyConfigLDAPProductWebAction extends GeneralWebAction {

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
    	
    	LdapProductDTO dto = new LdapProductDTO();
        
    	ConfigLdapEJBEvent event= new ConfigLdapEJBEvent();
        
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.ADD_LDAP_PRODUCT);
	    if("update".equals(request.getParameter("Action"))){
	    	 
			event.setActionType(EJBEvent.MODIFY_LDAP_PRODUCT);
	    }
	   
	    if (WebUtil.StringHaveContent(request.getParameter("txtProdcutName")))
	       dto.setProductName(request.getParameter("txtProdcutName"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtDescription")))
		      dto.setDescription(request.getParameter("txtDescription")); 
	    
	    
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
    	   dto.setStatus(request.getParameter("txtStatus"));
    	 
    	 
    	 
           event.setLdapProductDto(dto);
        
        return event;
    }
}
