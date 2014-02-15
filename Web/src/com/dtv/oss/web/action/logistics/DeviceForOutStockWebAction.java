package com.dtv.oss.web.action.logistics;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author chen jiang
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DeviceStockEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class DeviceForOutStockWebAction extends GeneralWebAction{
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
	    com.dtv.oss.dto.DeviceTransitionDTO theDTO = new com.dtv.oss.dto.DeviceTransitionDTO();
        theDTO.setBatchNo(request.getParameter("txtBatchNo"));
        
        theDTO.setToID(WebUtil.StringToInt(request.getParameter("txtOrgID")));
        theDTO.setStatusReason(request.getParameter("txtStatusReason"));
        //设置备注
        theDTO.setDescription(request.getParameter("txtComments"));
        //设置设备序列列表
        event.setDevArrayStr(request.getParameter("txtSerialNoCollection"));
        
        event.setDevTransDTO(theDTO);
        event.setActionType(LogisticsEJBEvent.DEVICE_OUT_STOCK);
        event.setDoPost(doPost);
        
        return event;
    }

}