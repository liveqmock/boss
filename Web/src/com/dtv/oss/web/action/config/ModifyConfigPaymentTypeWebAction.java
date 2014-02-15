package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CommonSettingDataDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigPaymentTypeEJBEvent;
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
public class ModifyConfigPaymentTypeWebAction extends GeneralWebAction {

	 

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	CommonSettingDataDTO dto = new CommonSettingDataDTO();
        
    	ConfigPaymentTypeEJBEvent event= new ConfigPaymentTypeEJBEvent();
        
        if("create".equals(request.getParameter("Action")))
			event.setActionType(ConfigPaymentTypeEJBEvent.CONFIG_PAYMENTTYPE_NEW);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(ConfigPaymentTypeEJBEvent.CONFIG_PAYMENTTYPE_UPDATE);
	    }
	    if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(ConfigPaymentTypeEJBEvent.CONFIG_PAYMENTTYPE_DELETE);
	    }
	    if (WebUtil.StringHaveContent(request.getParameter("key")))
	       dto.setKey(request.getParameter("key"));
	    if (WebUtil.StringHaveContent(request.getParameter("txtKey")))
		      dto.setKey(request.getParameter("txtKey"));
	   
		       dto.setName(request.getParameter("name"));
	  
		    dto.setValue(request.getParameter("value"));    
	    
    	   dto.setStatus(request.getParameter("txtStatus"));
    	 
           dto.setDescription(request.getParameter("description"));
    	 
            dto.setDefaultOrNot(request.getParameter("defaultOrNot"));
    	 
    		dto.setPriority(WebUtil.StringToInt(request.getParameter("priority")));
    	 
    		event.setDefaultKey(request.getParameter("defaultKey"));
           event.setDto(dto);
        
        return event;
    }
}
