package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CsiActionReasonSettingDTO;
import com.dtv.oss.dto.CsiTypeReason2DeviceDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigCustomerModifyEJBEvent;
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
public class ModifyConfigCsiReason2DeviceWebAction extends GeneralWebAction {

	 
		 
	 

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	CsiTypeReason2DeviceDTO dto = new CsiTypeReason2DeviceDTO();
        
    	DeviceModelEJBEvent event= new DeviceModelEJBEvent();
        
        
        if("create".equals(request.getParameter("Action"))) 
			event.setActionType(DeviceModelEJBEvent.DEVICEREASON_CREATE);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(DeviceModelEJBEvent.DEVICEREASON_UPDATE);
	    }
	    if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(EJBEvent.CONFIG_GOODS_DELETE);
	    }
	    if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
	        dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
	     
		    dto.setStatus(request.getParameter("txtStatus"));    
	     
    	   dto.setCsiType(request.getParameter("txtCsiType"));
    	 
           dto.setComments(request.getParameter("txtComments"));
    	 
            dto.setCsiCreateReason(request.getParameter("txtCsiCreateReason"));
     
            dto.setReferDeviceModel(request.getParameter("txtReferDeviceModel"));
    	     
    	    dto.setReferPurpose(request.getParameter("txtReferPurposeList"));
            event.setReason2DeviceDto(dto);
        
        return event;
    }
}
