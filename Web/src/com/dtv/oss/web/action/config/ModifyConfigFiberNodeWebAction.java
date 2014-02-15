package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.FiberNodeDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ResourceEJBEvent;
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
public class ModifyConfigFiberNodeWebAction extends GeneralWebAction {

	 

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
        FiberNodeDTO dto = new FiberNodeDTO();
        
        ResourceEJBEvent event= new ResourceEJBEvent();
        
        if("CREATE".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.CONFIG_FIBERNODE_CREATE);
	    if("MODIFY".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.CONFIG_FIBERNODE_UPDATE);
	    }
	     
	    if (WebUtil.StringHaveContent(request.getParameter("txtID")))
	      dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
	    
	     
		    dto.setFiberNodeCode(request.getParameter("txtFiberNodeCode"));   
	    
		    dto.setDistrictId(WebUtil.StringToInt(request.getParameter("txtDistrictID")));   
	   
    	 
           
     
            dto.setDetailAddress(request.getParameter("txtDetailAddress"));
    	 
            dto.setFiberReceiverId(WebUtil.StringToInt(request.getParameter("txtFiberRecieverId")));
    	 
    		dto.setDescription(request.getParameter("txtDescription"));
           event.setFnDto(dto);
        
        return event;
    }
}
