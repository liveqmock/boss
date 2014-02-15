package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BidimConfigSettingDTO;
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
public class ModifyConfigWebAction extends GeneralWebAction {

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
    	
    	BidimConfigSettingDTO dto = new BidimConfigSettingDTO();
        
        ConfigBidimEJBEvent event= new ConfigBidimEJBEvent();
        
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.CONFIG_BIDIM_CREATE);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.CONFIG_BIDIM_EDIT);
	    }
	    if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(EJBEvent.CONFIG_BIDIM_DELETE);
	    }
	    if (WebUtil.StringHaveContent(request.getParameter("txtValueType")))
	      dto.setValueType(request.getParameter("txtValueType"));
	     
	    if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
    	   dto.setStatus(request.getParameter("txtStatus"));
	    
	    if (WebUtil.StringHaveContent(request.getParameter("txtAllowNull")))
	    	   dto.setAllowNull(request.getParameter("txtAllowNull"));
	    
    	if (WebUtil.StringHaveContent(request.getParameter("txtDescription")))
           dto.setDescription(request.getParameter("txtDescription"));
    	if (WebUtil.StringHaveContent(request.getParameter("txtName")))
            dto.setName(request.getParameter("txtName"));
    	if (WebUtil.StringHaveContent(request.getParameter("txtServiceID")))
            dto.setServiceId(WebUtil.StringToInt(request.getParameter("txtServiceID")));
    	if (WebUtil.StringHaveContent(request.getParameter("txtID")))
            dto.setId(WebUtil.StringToInt(request.getParameter("txtID")));
    	if (WebUtil.StringHaveContent(request.getParameter("varID")))
            dto.setId(WebUtil.StringToInt(request.getParameter("varID")));
    	if (WebUtil.StringHaveContent(request.getParameter("txtConfigType")))
            dto.setConfigType(request.getParameter("txtConfigType"));
    	if (WebUtil.StringHaveContent(request.getParameter("txtConfigSubType")))
            dto.setConfigSubType(request.getParameter("txtConfigSubType"));
    	
           event.setBidimDto(dto);
        
        return event;
    }
}
