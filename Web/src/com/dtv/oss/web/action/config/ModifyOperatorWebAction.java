package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigSystemEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: BOSS P5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: chen jiang
 * Date: 2006-6-09
 * Time: 15:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ModifyOperatorWebAction extends GeneralWebAction {

	 
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	OperatorDTO dto = new OperatorDTO();
    	ConfigSystemEJBEvent event= new ConfigSystemEJBEvent();
       
        if("CREATE".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.CREATE_OPERATOR);
	    if("MODIFY".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.MODIFY_OPERATOR);
	    }
	      
	  
	        dto.setOperatorName(request.getParameter("txtOperatorName"));
	   
    	     dto.setOperatorDesc(request.getParameter("txtOperatorDesc"));
	    
	    	   dto.setLoginID(request.getParameter("txtLoginID"));
	     
    	 
            dto.setLoginPwd(request.getParameter("txtLoginPwd"));
    	 
            dto.setLevelID(WebUtil.StringToInt(request.getParameter("txtLevel")));
    	
    	 
            dto.setOrgID(WebUtil.StringToInt(request.getParameter("txtOrgID")));
    	 
            dto.setOperatorID(WebUtil.StringToInt(request.getParameter("txtOperatorID")));
    	 
            dto.setInternalUserFlag(request.getParameter("txtInternalUserFlag"));
    	 
            dto.setStatus(request.getParameter("txtStatus")); 
    	 
           event.setOperDto(dto);
        
        return event;
    }
}
