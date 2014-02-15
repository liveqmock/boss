package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ManualTransferSettingDTO;
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
 * Date: 2007-4-19
 * Time: 15:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ModifyManualTransferWebAction extends GeneralWebAction {

	 
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	ManualTransferSettingDTO dto = new ManualTransferSettingDTO();
        ConfigSystemEJBEvent event= new ConfigSystemEJBEvent();
         
       
        if("CREATE".equals(request.getParameter("txtActionType"))){
			event.setActionType(EJBEvent.MANUAL_TRANSFER_CREATE);
             
           }
	     else if("UPDATE".equals(request.getParameter("txtActionType"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
	    	 
			event.setActionType(EJBEvent.MANUAL_TRANSFER_MODIFY);
	    }
	      else if("DELETE".equals(request.getParameter("txtActionType"))){
	    	 event.setActionType(EJBEvent.MANUAL_TRANSFER_DELETE);
	    }
	      if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
	        dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
 
	        dto.setFromOrgId(WebUtil.StringToInt(request.getParameter("txtFromOrgId")));
	 
    	    dto.setToOrgId(WebUtil.StringToInt(request.getParameter("txtToOrgId")));
	 
	    	dto.setPriority(WebUtil.StringToInt(request.getParameter("txtPriority")));
	    	
	    	dto.setSheetType(request.getParameter("txtSheetType"));
    
	    	dto.setOrgSubRole(request.getParameter("txtSubType"));
            
           event.setManuTransSettingDto(dto);
            
        return event;
    }
}
