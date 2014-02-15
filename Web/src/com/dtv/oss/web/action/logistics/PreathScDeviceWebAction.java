package com.dtv.oss.web.action.logistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DeviceStockEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class PreathScDeviceWebAction extends GeneralWebAction{
	
 
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
  	   DeviceStockEJBEvent event= new DeviceStockEJBEvent(); 
  	   DeviceTransitionDTO theDTO = new DeviceTransitionDTO();
     if (WebUtil.StringHaveContent(request.getParameter("txtBatchID")))
	   theDTO.setBatchNo(request.getParameter("txtBatchID"));
     //用fromType这个字段存放serialNo.
      
     if (WebUtil.StringHaveContent(request.getParameter("txtSerialNo")))
     	theDTO.setFromType(request.getParameter("txtSerialNo"));
     
     if (WebUtil.StringHaveContent(request.getParameter("ProductList")))
 	    theDTO.setDescription(request.getParameter("ProductList"));
     //放置deviceId
     if (WebUtil.StringHaveContent(request.getParameter("txtDeviceID")))
 	    theDTO.setDeviceNumber(WebUtil.StringToInt(request.getParameter("txtDeviceID"))); 
        event.setActionType(LogisticsEJBEvent.DEVICE_PREAUTH_1);
        event.setDevTransDTO(theDTO);
         
        return event;
      
    }
}
