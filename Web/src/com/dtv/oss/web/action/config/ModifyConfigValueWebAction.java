package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BidimConfigSettingValueDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigBidimEJBEvent;
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
public class ModifyConfigValueWebAction extends GeneralWebAction {

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
    	
    	BidimConfigSettingValueDTO dto = new BidimConfigSettingValueDTO();
        
        ConfigBidimEJBEvent event= new ConfigBidimEJBEvent();
        
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.CONFIG_VALUE_CREATE);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.CONFIG_VALUE_EDIT);
	    }
	    if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(EJBEvent.CONFIG_VALUE_DELETE);
	    }
	    if (WebUtil.StringHaveContent(request.getParameter("txtCode")))
	      dto.setCode(request.getParameter("txtCode"));
	     
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
    	   dto.setStatus(request.getParameter("txtStatus"));
	    
	   
    	if (WebUtil.StringHaveContent(request.getParameter("txtDescription")))
           dto.setDescription(request.getParameter("txtDescription"));
    	 
     
    	if (WebUtil.StringHaveContent(request.getParameter("txtID")))
            dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
    	if (WebUtil.StringHaveContent(request.getParameter("varID")))
            dto.setId(WebUtil.StringToInt(request.getParameter("varID")));
    	if (WebUtil.StringHaveContent(request.getParameter("txtSettingID")))
            dto.setSettingId(WebUtil.StringToInt(request.getParameter("txtSettingID")));
    	 
    	
           event.setBidimValueDto(dto);
        
        return event;
    }
}
