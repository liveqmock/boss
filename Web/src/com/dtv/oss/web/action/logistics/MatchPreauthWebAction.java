package com.dtv.oss.web.action.logistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DeviceStockEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class MatchPreauthWebAction extends GeneralWebAction{
	
	boolean doPost = false;
	 
	protected boolean needCheckToken()
	{
		return doPost;
	}

  public void doStart(HttpServletRequest request)
	{
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post")))
		{
			if (request.getParameter("confirm_post").equalsIgnoreCase("true")) 
			{
				doPost = true;
			 
			}
		}
	 }
  
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
  	   DeviceStockEJBEvent event= new DeviceStockEJBEvent(); 
  	   DeviceTransitionDTO theDTO = new DeviceTransitionDTO();
     if (WebUtil.StringHaveContent(request.getParameter("txtBatchID")))
	   theDTO.setBatchNo(request.getParameter("txtBatchID"));
     //用fromType这个字段存放serialNo.
     if (WebUtil.StringHaveContent(request.getParameter("txtSerialNoCollection")))
     	theDTO.setFromType(request.getParameter("txtSerialNoCollection"));
     if (WebUtil.StringHaveContent(request.getParameter("txtSerialNo")))
     	theDTO.setFromType(request.getParameter("txtSerialNo"));
      if (WebUtil.StringHaveContent(request.getParameter("txtPreAuth"))){
     	if(request.getParameter("txtPreAuth").equals("Y"))
 	   theDTO.setAction("DP");
     }
     if (WebUtil.StringHaveContent(request.getParameter("ProductList")))
 	    theDTO.setDescription(request.getParameter("ProductList"));
       
        event.setActionType(LogisticsEJBEvent.DEVICE_PREAUTH_MATCH);
        event.setDevTransDTO(theDTO);
        event.setDoPost(doPost);
        return event;
      
    }
}
