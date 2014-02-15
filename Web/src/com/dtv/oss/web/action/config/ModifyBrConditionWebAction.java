package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BrconditionDTO;
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
public class ModifyBrConditionWebAction extends GeneralWebAction {

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
    	
    	BrconditionDTO dto = new BrconditionDTO();
        
        ConfigBillingEJBEvent event= new ConfigBillingEJBEvent();
        
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.BILLING_RULE_CONDITION_CREATE);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.BILLING_RULE_CONDITION_MODIFY);
	    }
	      if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(EJBEvent.BILLING_RULE_CONDITION_DELE);
	    }
	     
	    if (WebUtil.StringHaveContent(request.getParameter("txtConditionType")))
	        dto.setCntType(request.getParameter("txtConditionType"));
	   
	        dto.setBrcntID(WebUtil.StringToInt(request.getParameter("txtBrcntID")));
	     
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
    	     dto.setStatus(request.getParameter("txtStatus"));
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtConditionName")))
	    	   dto.setCntName(request.getParameter("txtConditionName"));
	    
    	if (WebUtil.StringHaveContent(request.getParameter("txtCustTypeList")))
            dto.setCntValueStr(request.getParameter("txtCustTypeList"));
    	 
           event.setBrDto(dto);
        
        return event;
    }
}
