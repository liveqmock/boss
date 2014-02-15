package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.MethodOfPaymentDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigBillingEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: BOSS P5</p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: Digivision</p>
 * User: chen jiang
 * Date: 2006-4-24
 * Time: 15:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ModifyMethodOfPaymentWebAction extends GeneralWebAction {

	boolean doPost = false;
	 
	protected boolean needCheckToken()
	{
		return doPost;
	}
	 
	 
	
   public void doStart(HttpServletRequest request)
	{
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm")))
		{
			if (request.getParameter("confirm").equalsIgnoreCase("true")) 
			{
				doPost = true;
				 
			 
			}
		}
		 
	 }

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	MethodOfPaymentDTO dto = new MethodOfPaymentDTO();
        
        ConfigBillingEJBEvent event= new ConfigBillingEJBEvent();
        
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.METHOD_OF_PAYMENT_CREATE);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.METHOD_OF_PAYMENT_MODIFY);
	    }
	      if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(EJBEvent.METHOD_OF_PAYMENT_DELE);
	    }
	     
	 
	        dto.setName(request.getParameter("name"));
	  
	        dto.setMopID(WebUtil.StringToInt(request.getParameter("txtMopID")));
	     
	   
    	     dto.setStatus(request.getParameter("txtStatus"));
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtPayType")))
	    	   dto.setPayType(request.getParameter("txtPayType"));
	    
    	 
            dto.setAccountflag(request.getParameter("txtAccountflag"));
    	 
            dto.setPaymentflag(request.getParameter("txtPaymentflag"));
     
            dto.setCashFlag(request.getParameter("txtCashFlag"));
            
            dto.setCsiTypeList(request.getParameter("txtCsiTypeList"));
     
            dto.setDescription(request.getParameter("txtDescription"));
    	 
	        dto.setPartnerID(WebUtil.StringToInt(request.getParameter("txtPartID")));
	     
           event.setMethodOfPaymentDto(dto);
        
        return event;
    }
}
