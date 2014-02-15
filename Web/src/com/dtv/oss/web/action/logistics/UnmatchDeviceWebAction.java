package com.dtv.oss.web.action.logistics;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DeviceStockEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;


public class UnmatchDeviceWebAction extends GeneralWebAction {
	
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	 //取消设备配对
    	 ArrayList deviceCol=new ArrayList();
    	 DeviceStockEJBEvent event= new DeviceStockEJBEvent(); 
    	 TerminalDeviceDTO terminalDeviceDto1=new TerminalDeviceDTO();       
    	 terminalDeviceDto1.setDeviceID(WebUtil.StringToInt(request.getParameter("txtDeviceID")));
    	 deviceCol.add(terminalDeviceDto1);
    	 TerminalDeviceDTO terminalDeviceDto2=new TerminalDeviceDTO();  
    	 terminalDeviceDto2.setDeviceID(WebUtil.StringToInt(request.getParameter("txtMatchDeviceID")));
    	 deviceCol.add(terminalDeviceDto2);
    	 //存放操作批号
    	 event.setToStatus(request.getParameter("txtUnMatchBatchID"));
    	 event.setDevArray(deviceCol);
    	 event.setActionType(LogisticsEJBEvent.UNMATCH_DEVICE);
    	 
      	return event;
    }
}
