package com.dtv.oss.web.action.logistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DeviceStockEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;


public class ReleaseDeviceWebAction extends GeneralWebAction {
	
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	 
    	 DeviceStockEJBEvent event= new DeviceStockEJBEvent(); 
    	 DeviceTransitionDTO dtDto=new DeviceTransitionDTO();
    	 //´æ·ÅÉè±ðid
    	 dtDto.setDeviceNumber(WebUtil.StringToInt(request.getParameter("txtDeviceId")));
    	 
    	 dtDto.setBatchNo(request.getParameter("txtReleaseBatchID"));
    	 
    	 event.setDevTransDTO(dtDto);
    	 
    	 event.setActionType(LogisticsEJBEvent.DEVICE_RELEASEPREAUTH);
    	 
      	return event;
    }
}
