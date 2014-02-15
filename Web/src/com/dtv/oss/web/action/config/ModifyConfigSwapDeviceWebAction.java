package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.SwapDeviceReason2StatusDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.DeviceModelEJBEvent;
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
public class ModifyConfigSwapDeviceWebAction extends GeneralWebAction {

	 
		 
	 

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	SwapDeviceReason2StatusDTO dto = new SwapDeviceReason2StatusDTO();
        
    	DeviceModelEJBEvent event= new DeviceModelEJBEvent();
        
        
        if("create".equals(request.getParameter("Action"))) 
			event.setActionType(DeviceModelEJBEvent.SWAPDEVICE_CREATE);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(DeviceModelEJBEvent.SWAPDEVICE_UPDATE);
	    }
	    if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(EJBEvent.CONFIG_GOODS_DELETE);
	    }
	    if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
	        dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
	     
		    dto.setStatus(request.getParameter("txtStatus"));    
	     
    	   dto.setReasonStrList(request.getParameter("txtReasonStrList"));
    	 
           dto.setToStatus(request.getParameter("txtToStatus"));
    	 
            
            event.setSwapDto(dto);
        
        return event;
    }
}
