package com.dtv.oss.web.action.customer;

/**
 * author: david.Yang 
 */
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

//import oss class
import com.dtv.oss.web.util.*;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.action.MultipleWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.service.ejbevent.csr.BookEJBEvent;
import com.dtv.oss.service.ejbevent.csr.CallBackInfoEJBEvent;
import com.dtv.oss.util.DynamicServey;


public class ServiceInteractionWebAction extends MultipleWebAction{
	
  boolean confirmPost = false;
  protected boolean needCheckToken(){
	   return confirmPost;
  }
  public void doStart(HttpServletRequest request){
      super.doStart(request);
      confirmPost = false;
      if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))){
          if (request.getParameter("confirm_post").equalsIgnoreCase("true")) confirmPost = true;
      }
  }

  protected int getSepecialAction(String pAction){
        //这里仅仅是取消预约，取消安装在CancelInstallWebAction中实现	
        if (pAction.equals(CommonKeys.CANCEL_OF_BOOKING))
            return BookEJBEvent.CANCELBOOKING;
        if (pAction.equals("callback"))
            return BookEJBEvent.CALLFOROPENACCOUNT;
        if (pAction.equals("returnfee"))
        	return BookEJBEvent.RETURNFEE4FAILINSTALLATION;
        return -1;
    }

    protected boolean isAllowedAction() {
        switch (actionType){
          case BookEJBEvent.CALLFOROPENACCOUNT:
          //这里仅仅是取消预约，取消安装在CancelInstallWebAction中实现	
          case BookEJBEvent.CANCELBOOKING:	
          case BookEJBEvent.RETURNFEE4FAILINSTALLATION:
            return true;
        }

        return false;

    }

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        if (!WebUtil.StringHaveContent(request.getParameter("txtID")))
              throw new WebActionException("没有相关的受理单信息标识");

            com.dtv.oss.dto.CustServiceInteractionDTO theDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
            int strcsiid = WebUtil.StringToInt(request.getParameter("txtID"));
            theDTO.setId(strcsiid);
            
        switch (actionType){
          case BookEJBEvent.CANCELBOOKING:
          	//这里仅仅是取消预约，取消安装在CancelInstallWebAction中实现	
          	theDTO.setStatusReason(request.getParameter("txtStatusReason"));
          	theDTO.setStatus(CommonKeys.CSIPROCESSLOG_ACTION_BOOKINGCANCEL);
          	BookEJBEvent event =new BookEJBEvent(BookEJBEvent.CANCELBOOKING);
          	event.setCsiDto(theDTO);
          	return event;
          	
          case BookEJBEvent.CALLFOROPENACCOUNT:
        	String paramName ="txtDynamicServey";
        	String sourceType =request.getParameter("txtTypeShow");
            Collection	callBackInfoList =DynamicServey.getCallBackInfo(request,paramName,sourceType,strcsiid);
        	  
            theDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
            
            CallBackInfoEJBEvent callbackEvent =null;
            if("true".equalsIgnoreCase(request.getParameter("txtSaveStates"))){
            	callbackEvent =new CallBackInfoEJBEvent(actionType);
            }else{
            	callbackEvent =new CallBackInfoEJBEvent(CallBackInfoEJBEvent.SETCALLFLAG4OPENACCOUNT);
            }
            callbackEvent.setCsiDto(theDTO);
            callbackEvent.setCallBackInfoList(callBackInfoList);
            callbackEvent.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
            return callbackEvent;
            
          case BookEJBEvent.RETURNFEE4FAILINSTALLATION:
        	 BookEJBEvent event1 =new BookEJBEvent(BookEJBEvent.RETURNFEE4FAILINSTALLATION);
        	 com.dtv.oss.dto.CustServiceInteractionDTO csiDto =new com.dtv.oss.dto.CustServiceInteractionDTO();
        	 csiDto.setId(WebUtil.StringToInt(request.getParameter("txtCsiID")));
        	 csiDto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        	 csiDto.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
        	 event1.setCsiDto(csiDto);
        	 return event1;
        }

        return null;

    }

}