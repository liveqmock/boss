package com.dtv.oss.web.action.logistics;


import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.dto.DeviceBatchPreauthDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DeviceStockEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class DeviceDismatchWebAction extends GeneralWebAction{
		
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
		DeviceBatchPreauthDTO theDTO = new DeviceBatchPreauthDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtBatchID")))
			theDTO.setReferSheetSerialNO(request.getParameter("txtBatchID"));
		//用fileName这个字段存放serialNoCollection
		String content = "";
		if(WebUtil.StringHaveContent(request.getParameter("txtSCSerialNoCollection")))
			content = request.getParameter("txtSCSerialNoCollection");
System.out.println(content);
		if(WebUtil.StringHaveContent(request.getParameter("txtDesc")))
			theDTO.setDescription(request.getParameter("txtDesc"));
		theDTO.setFileName(content);
		event.setActionType(LogisticsEJBEvent.UNMATCH_DEVICE);
		event.setDeviceBatchPreauthDTO(theDTO);
		event.setDoPost(doPost);
		return event;		
	}
}
