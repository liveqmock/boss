package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.MachineRoomDTO;
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
 * Date: 2007-1-12
 * Time: 15:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ModifyMachineRoomWebAction extends GeneralWebAction {

	 
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	MachineRoomDTO dto = new MachineRoomDTO();
    	ResourceEJBEvent event= new ResourceEJBEvent();
         
       
        if("CREATE".equals(request.getParameter("txtActionType"))){
			event.setActionType(EJBEvent.CONFIG_MACHINEROOM_CREATE);
             
           }
	     else if("UPDATE".equals(request.getParameter("txtActionType"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
	    	 
			event.setActionType(EJBEvent.CONFIG_MACHINEROOM_UPDATE);
	    }
	      else if("deleteDetail".equals(request.getParameter("txtActionType"))){
	    	 event.setActionType(EJBEvent.CONFIG_MACHINEROOM_REMOVE);
	    }
	      if (WebUtil.StringHaveContent(request.getParameter("txtID")))
	        dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
 
	        dto.setDescription(request.getParameter("txtDesc"));
	 
    	    dto.setDetailAddress(request.getParameter("txtDetailAddress"));
	 
	    	dto.setMachineRoomCode(request.getParameter("txtMachineRoomCode"));
	    	
	    	dto.setMachineRoomName(request.getParameter("txtMachineRoomName"));
    
             
   
            
           event.setMrDto(dto);
            
        return event;
    }
}
