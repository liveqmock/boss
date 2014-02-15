package com.dtv.oss.web.action.customer;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerProductEJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;

public class BookCustomerProductWebAction extends GeneralWebAction{
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

	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		CustomerProductEJBEvent event = null;
		String txtActionType = request.getParameter("txtActionType");
		if(txtActionType == null){
			throw new WebActionException("BookCustomerProductWebAction 不知道需要进行的操作！");
		}
		int actionType = WebUtil.StringToInt(txtActionType);
		switch(actionType){
		case CommonKeys.CHECK_PRODUCTPG_CAMPAINPG:
			event = new CustomerProductEJBEvent();
			event.setActionType(EJBEvent.CHECK_PRODUCTPG_CAMPAINPG);
			break;
		case CommonKeys.ADD_PRODUCT:
			event = new CustomerProductEJBEvent();
			event.setActionType(EJBEvent.BOOKING);
			break;
		case CommonKeys.BOOK_PRODUCT_MODIFY:
			event = new CustomerProductEJBEvent();
			event.setActionType(EJBEvent.ALTERBOOKING);
			break;
		case CommonKeys.CUSTOMER_PRODUCT_CANCEL:
			event = new CustomerProductEJBEvent();
			event.setActionType(EJBEvent.CANCELBOOKING);
			break;
		default:
			throw new WebActionException("无法定位所要进行的操作！");
		}
		
		if(WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID"))){
			event.setSaID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));
		}else{
			throw new WebActionException("无法定位业务帐户！");
		}
		
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID"))) {						
			event.setCustomerID (WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		}
		else{
			throw new WebActionException("请先做客户查询，定位客户！");
		}
		
		if (WebUtil.StringHaveContent(request.getParameter("txtAccount"))){
			event.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount")));
		}else{
			throw new WebActionException("无法定位帐户号");
		}
		setCSIInfo(request,event);
		setProductInfo(request,event);
		return event;
	}	
	
	private void setProductInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent)
	{
		//客户购买的产品包信息
		ArrayList lstProductID = new ArrayList();
		//客户选择的优惠信息
		ArrayList lstCampaignID = new ArrayList();

	   if (WebUtil.StringHaveContent(request.getParameter("ProductList"))) {
		  String[] aProductID = request.getParameter("ProductList").split(";");
		  if ((aProductID != null) && (aProductID.length > 0)) {
			for (int i = 0; i < aProductID.length; i++)
				lstProductID.add(WebUtil.StringToInteger(aProductID[i]));
		  }
	   }
	  
	   if (WebUtil.StringHaveContent(request.getParameter("CampaignList"))) {
		   String[] aCampaignID = request.getParameter("CampaignList").split(";");
		   if ((aCampaignID != null) && (aCampaignID.length > 0)) {
			  for (int i = 0; i < aCampaignID.length; i++){
				  System.out.println("aCampaignID["+i+"]========"+aCampaignID[i]);
				  lstCampaignID.add(WebUtil.StringToInteger(aCampaignID[i]));
			  }
		   }
	   }
	   ejbEvent.setCsiPackageArray(lstProductID);
	   ejbEvent.setCsiCampaignArray(lstCampaignID);
	}
	
	private void setCSIInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent)
	{
		//受理单信息
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP);
		//csiDTO.setInstallationType(CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL);
		csiDTO.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY);
		csiDTO.setCreateReason(request.getParameter("txtCsiCreateReason"));
		
		//zyg 2007.11.22 需要同时设置 受理单的 statusreason 字段
		csiDTO.setStatusReason(request.getParameter("txtCsiCreateReason"));
		
		if (WebUtil.StringHaveContent(request.getParameter("txtID")))
        	csiDTO.setId(WebUtil.StringToInt(request.getParameter("txtID")));
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	csiDTO.setCustomerID (WebUtil.StringToInt(request.getParameter("txtCustomerID") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
        	csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount") ));
		if (WebUtil.StringHaveContent(request.getParameter("txtContactPhone")))
            csiDTO.setContactPhone(request.getParameter("txtContactPhone"));
		if (WebUtil.StringHaveContent(request.getParameter("txtContactPerson")))
			csiDTO.setContactPerson(request.getParameter("txtContactPerson"));
        if (WebUtil.StringHaveContent(request.getParameter("txtCsiDtLastMod")))
        	csiDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtCsiDtLastMod")));
        if (WebUtil.StringHaveContent(request.getParameter("txtScheduleTime")))
        	csiDTO.setScheduleTime(WebUtil.StringToTimestamp(request.getParameter("txtScheduleTime")));
        if (WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
        	csiDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
        if (WebUtil.StringHaveContent(request.getParameter("txtUsedMonth")))
        	csiDTO.setPoint(WebUtil.StringToInt(request.getParameter("txtUsedMonth")));
        
		ejbEvent.setCsiDto( csiDTO);
	}

}
