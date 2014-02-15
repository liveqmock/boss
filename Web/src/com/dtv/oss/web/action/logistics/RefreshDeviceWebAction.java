package com.dtv.oss.web.action.logistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DeviceStockEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;


public class RefreshDeviceWebAction extends GeneralWebAction {
	
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
	DeviceStockEJBEvent event= new DeviceStockEJBEvent(); 
	TerminalDeviceDTO dtDto=new TerminalDeviceDTO();

	dtDto.setSerialNo(request.getParameter("txtSCSerialNo"));
	event.setTerDeviceDTO(dtDto);
	
	event.setActionType(LogisticsEJBEvent.DEVICE_REFRESHPREAUTH);

	return event;
    }
}
