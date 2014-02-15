package com.dtv.oss.web.action.logistics;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.dto.DeviceBatchPreauthDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DeviceStockEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.FileUpload;
import com.dtv.oss.web.util.FileUtility;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.smartUpload.Request;

public class DeviceDisauthWebAction extends GeneralWebAction {
	boolean doPost = false;
	 
	protected boolean needCheckToken(){
		return doPost;
	}

    public void doStart(HttpServletRequest request){
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))){
			if (request.getParameter("confirm_post").equalsIgnoreCase("true")) {
				doPost = true;
			}
		}
	 }

	protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException{
		DeviceStockEJBEvent event= new DeviceStockEJBEvent(); 
		event.setDoPost(doPost);
		event.setActionType(LogisticsEJBEvent.DEVICE_DISAUTH);
		try {
			if (!doPost){
			   FileUpload upload=new FileUpload(request,1024000,"xls");		
			   //处理文件上传
			   String     filePath = upload.saveFile();
			   Request    dataRequest=upload.getRequest(); 	
			   ArrayList deviceCheckList=FileUtility.pareXlsFiletoDevice(filePath,"N");
			   event.setDeviceCheckList(deviceCheckList);
			}else{
	  	       DeviceBatchPreauthDTO theDTO = new DeviceBatchPreauthDTO();
		       if (WebUtil.StringHaveContent(request.getParameter("txtBatchID")))
			      theDTO.setReferSheetSerialNO(request.getParameter("txtBatchID"));
		       String content = "";
	   		   String[] serialNo =request.getParameterValues("serialNo");
			
			   if (serialNo != null) {
			      for (int i = 0; i < serialNo.length; i++) {
				      content = content +serialNo[i]+ "\r\n";
			      }
			      theDTO.setFileName(content);
			   }
		       if(WebUtil.StringHaveContent(request.getParameter("txtDesc")))
			      theDTO.setDescription(request.getParameter("txtDesc"));

         	   event.setDeviceBatchPreauthDTO(theDTO);
			}
		}catch (Exception e) {
		   e.printStackTrace();		
		   throw new WebActionException(e.getMessage());
		}
		
		return event;		
	}
}
