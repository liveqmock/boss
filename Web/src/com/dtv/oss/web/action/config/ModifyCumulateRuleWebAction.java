package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.UserPointsCumulatedRuleDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigActivityEJBEvent;
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
public class ModifyCumulateRuleWebAction extends GeneralWebAction {

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
    	
    	UserPointsCumulatedRuleDTO dto = new UserPointsCumulatedRuleDTO();
        
        ConfigActivityEJBEvent event= new ConfigActivityEJBEvent();
        
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.CONFIG_CUMULATED_RULE_CREATE);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.CONFIG_CUMULATED_RULE_EDIT);
	    }
	    if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(EJBEvent.CONFIG_CUMULATED_RULE_DELETE);
	    }
	 
		    dto.setCondEvent(WebUtil.StringToInt(request.getParameter("txtCondEvent")));   
	    
		    dto.setProductID(WebUtil.StringToInt(request.getParameter("txtProductID")));  
	   
    	   dto.setStatus(request.getParameter("txtStatus"));
    	 
    	 
           dto.setDescr(request.getParameter("txtDescription"));
    	 
            dto.setCustTypeList(request.getParameter("txtCustTypeList"));
    	 
            dto.setCondValue1(WebUtil.StringTodouble(request.getParameter("txtCondValue1")));
     
            dto.setCondValue2(WebUtil.StringTodouble(request.getParameter("txtCondValue2")));
     
            dto.setAddedPoints(WebUtil.StringToInt(request.getParameter("txtAddedPoints")));
     
            dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
            if(WebUtil.StringHaveContent(request.getParameter("vartxtDetailID"))) 
         	   dto.setId(WebUtil.StringToInt(request.getParameter("vartxtDetailID")));
            else
                dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
            event.setCumulatedRuleDto(dto);
        
        return event;
    }
}
