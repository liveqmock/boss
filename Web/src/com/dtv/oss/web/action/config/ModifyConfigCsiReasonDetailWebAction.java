package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CsiActionReasonDetailDTO;
import com.dtv.oss.dto.CsiActionReasonSettingDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigCustomerModifyEJBEvent;
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
public class ModifyConfigCsiReasonDetailWebAction extends GeneralWebAction {

	 
		 
	 

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	CsiActionReasonDetailDTO dto = new CsiActionReasonDetailDTO();
        
    	ConfigCustomerModifyEJBEvent event= new ConfigCustomerModifyEJBEvent();
        
        
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.CONFIG_CSI_REASON_DETAIL_NEW);
	    if("update".equals(request.getParameter("Action"))){
	    	 
			event.setActionType(EJBEvent.CONFIG_CSI_REASON_DETAIL_UPDATE);
	    }
	    if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(EJBEvent.CONFIG_GOODS_DELETE);
	    }
	    if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
	        dto.setSeqNo(WebUtil.StringToInt(request.getParameter("txtSeqNo")));
	     
		    dto.setStatus(request.getParameter("txtStatus"));    
	     
    	   dto.setDefaultValueFlag(request.getParameter("txtDefaultValueFlag"));
    	 
            dto.setKey(request.getParameter("txtKey"));
            dto.setValue(request.getParameter("txtValue"));
    	    dto.setReferSeqNo(WebUtil.StringToInt(request.getParameter("txtReferSeqNo")));
            dto.setPriority(WebUtil.StringToInt(request.getParameter("txtPriority")));
    	 
           event.setCsiReasonDetailDto(dto);
        
        return event;
    }
}
