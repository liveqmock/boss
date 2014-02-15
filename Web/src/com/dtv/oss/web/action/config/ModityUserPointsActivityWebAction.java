package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.UserPointsExchangeActivityDTO;
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
public class ModityUserPointsActivityWebAction extends GeneralWebAction {

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
    	
        UserPointsExchangeActivityDTO dto = new UserPointsExchangeActivityDTO();
        
        ConfigActivityEJBEvent event= new ConfigActivityEJBEvent();
        
        if("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.CONFIG_ACTIVITY_CREATE);
	    if("update".equals(request.getParameter("Action"))){
	    	dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
			event.setActionType(EJBEvent.CONFIG_ACTIVITY_UPDATE);
	    }
	    if("deleteDetail".equals(request.getParameter("Action"))){
	    	 event.setActionType(EJBEvent.CONFIG_ACTIVITY_DELETE);
	    }
	    dto.setId(new Integer(WebUtil.StringToInt(request.getParameter("txtID"))));
            
        //活动开始时间
    	   dto.setDateStart(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtStartTime")));
    	//活动结束时间
    	   dto.setDateEnd(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtEndTime")));
    	   dto.setStatus(request.getParameter("txtStatus"));
    	   if (WebUtil.StringHaveContent(request.getParameter("txtDescription")))
           dto.setDescr(request.getParameter("txtDescription"));
    	   if (WebUtil.StringHaveContent(request.getParameter("txtName")))
            dto.setName(request.getParameter("txtName"));
    	   if (WebUtil.StringHaveContent(request.getParameter("vartxtActivityID")))
            dto.setId(new Integer(WebUtil.StringToInt(request.getParameter("vartxtActivityID"))));
           event.setActivityDto(dto);
        
        return event;
    }
}
