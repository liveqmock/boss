package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.FiberTransmitterDTO;
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
public class ModifyConfigFiberTransmitterWebAction extends GeneralWebAction {

	 

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	FiberTransmitterDTO dto = new FiberTransmitterDTO();
        
        ResourceEJBEvent event= new ResourceEJBEvent();
        
        if("CREATE".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.CONFIG_FIBERTRANSMITTER_CREATE);
	    if("MODIFY".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.CONFIG_FIBERTRANSMITTER_UPDATE);
	    }
	     
	    if (WebUtil.StringHaveContent(request.getParameter("txtID")))
	      dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
	    
	   
		    dto.setFiberTransmitterCode(request.getParameter("txtFiberTransmitterCode"));   
	    
		    dto.setDistrictId(WebUtil.StringToInt(request.getParameter("txtDistrictID")));   
	   
    	 
            dto.setDetailAddress(request.getParameter("txtDetailAddress"));
    	 
            dto.setMachineRoomId(WebUtil.StringToInt(request.getParameter("txtMachineRoomId")));
    	 
    		dto.setDescription(request.getParameter("txtDescription"));
           event.setFtDto(dto);
        
        return event;
    }
}
