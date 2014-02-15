package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;


public class CustomerBatchAddSubscriberWebAction extends PayFeeWebAction {
    boolean confirmPost = false;
    String txtActionType ;

	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
				confirmPost = true;
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
    
		try{
		txtActionType=request.getParameter("txtActionType");
		if (txtActionType ==null){
			return null;
		}
		ServiceAccountEJBEvent ejbEvent =new ServiceAccountEJBEvent();
		setCustomerInfo(request,ejbEvent);
		setAccountInfo(request,ejbEvent);	
		setUsedMonth(request,ejbEvent);
//		setProductInfo(request,ejbEvent);
		ejbEvent.setBuyProductList(getBuyProductInfo(request));
		setCSIInfo(request,ejbEvent);
		ejbEvent.setForcedDeposit(WebUtil.StringTodouble(request.getParameter("txtForceDepostMoney")));
		int eJbActionType =0;
		switch (Integer.parseInt(txtActionType)){
		//参数检查.
			case CommonKeys.CHECK_BATCHBUY_PRODUCTINFO :
				eJbActionType =ServiceAccountEJBEvent.CHECK_BATCHBUY_PRODUCTINFO;
				ejbEvent.setDoPost( confirmPost);
			    break;
			   
			//用于门店增机的提交
			case CommonKeys.OPEN_BATCHBUY_SERVICE_ACCOUNT :
				eJbActionType =ServiceAccountEJBEvent.CUSTOMER_BATCHBUY_ADD_SUBSCRIBER ;
				setPayFeeInfo(request,ejbEvent);
				setNextOrgID(request,ejbEvent);
				ejbEvent.setDoPost( confirmPost);
			    break;
		    default :
		    	break;
		}
		ejbEvent.setActionType(eJbActionType );
		return ejbEvent;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private void setCustomerInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent) throws WebActionException
	{
		String customer = request.getParameter("txtCustomerID");
		if (WebUtil.StringHaveContent(customer)) {
			CustomerDTO custDTO = new CustomerDTO();
			custDTO.setCustomerID (WebUtil.StringToInt(customer) );
			ejbEvent.setCustomerDTO ( custDTO);
			ejbEvent.setCustomerID (WebUtil.StringToInt(customer));
		}
		else
		{
			throw new WebActionException("请先做客户查询，定位客户！");
		}
	}
		
	private void setAccountInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent) throws WebActionException
	{
		String account = request.getParameter("txtAccount");
		if (WebUtil.StringHaveContent(account)) {
			AccountDTO accountDTO = new AccountDTO();
			accountDTO.setAccountID(WebUtil.StringToInt(account) );
			ejbEvent.setAccountDTO( accountDTO);
			ejbEvent.setAccountID(WebUtil.StringToInt(account));			
		}
		else
		{
			throw new WebActionException("请指定一个有效帐户！");
		}
	}
	
	private void setUsedMonth(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent) throws WebActionException
	{
		String txtUsedMonth =request.getParameter("txtUsedMonth");
		ejbEvent.setUsedMonth(WebUtil.StringToInt(txtUsedMonth));
	}
	
	
	private void setPayFeeInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent) throws WebActionException
	{
		int customerID = WebUtil.StringToInt(request.getParameter("txtCustomerID"));
		int accountID = WebUtil.StringToInt(request.getParameter("txtAccount"));
		ejbEvent.setFeeList (getSessionFeeList(request)) ;
		getPayAndPrePayList(request,customerID,accountID,ejbEvent.getPaymentList(),ejbEvent.getCsiPrePaymentDeductionList());
	}
	
	private void setCSIInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent)
	{
		//受理单信息
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();      
		csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO); 
    	if (WebUtil.StringHaveContent(request.getParameter("txtIsInstall")))
         	csiDTO.setInstallationType(request.getParameter("txtIsInstall") );
        if (WebUtil.StringHaveContent(request.getParameter("txtPreferedTime")))
        	csiDTO.setPreferedTime(request.getParameter("txtPreferedTime") );
        if (WebUtil.StringHaveContent(request.getParameter("txtPreferedDate")))
        	csiDTO.setPreferedDate(WebUtil.StringToTimestamp(request.getParameter("txtPreferedDate")));
        if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
        	csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	csiDTO.setCustomerID (WebUtil.StringToInt(request.getParameter("txtCustomerID") ));
		if (WebUtil.StringHaveContent(request.getParameter("txtContactPhone")))
            csiDTO.setContactPhone(request.getParameter("txtContactPhone"));
		if (WebUtil.StringHaveContent(request.getParameter("txtContactPerson")))
			csiDTO.setContactPerson(request.getParameter("txtContactPerson"));
		if (WebUtil.StringHaveContent(request.getParameter("txtComments")))
			csiDTO.setComments(request.getParameter("txtComments"));
		if (WebUtil.StringHaveContent(request.getParameter("txtcsiPayOption")))
		    csiDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
		if (WebUtil.StringHaveContent(request.getParameter("txtCsiCreateReason"))){
		    csiDTO.setCreateReason(request.getParameter("txtCsiCreateReason"));
			//zyg 2007.11.22 需要同时设置 受理单的 statusreason 字段
		  csiDTO.setStatusReason(request.getParameter("txtCsiCreateReason"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtUsedMonth"))){
			csiDTO.setPoint(WebUtil.StringToInt(request.getParameter("txtUsedMonth")));
		}
		ejbEvent.setCsiDto( csiDTO);
	}
	
	
	private void setNextOrgID(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent) throws WebActionException
		{
			if (WebUtil.StringHaveContent(request.getParameter("transferType"))){
				if(("auto").equalsIgnoreCase(request.getParameter("transferType"))){
					System.out.println("******auto*******"+request.getParameter("txtAutoNextOrgID")+"*********");
					ejbEvent.setNextOrgID(WebUtil.StringToInt(request.getParameter("txtAutoNextOrgID")));
				}else if(("manual").equalsIgnoreCase(request.getParameter("transferType"))){
					ejbEvent.setNextOrgID(WebUtil.StringToInt(request.getParameter("txtNextOrgID")));
				}
			}
		}
	public void doEnd(HttpServletRequest request, CommandResponse cmdResponse) {
		super.doEnd(request, cmdResponse);
		String txtActionType =(request.getParameter("txtActionType") ==null) ? "" :request.getParameter("txtActionType");
		if ((!txtActionType.equals(""))
				&& (Integer.parseInt(txtActionType) ==CommonKeys.CHECK_PRODUCTPG_AND_INVOICE)){
		   Object obj = request.getAttribute("ResponseFromEJBEvent");
		   if (obj != null) {
			  System.out.println("obj.getClass().getName()=========="+obj.getClass().getName());
			  CommandResponseImp cmdImp =(CommandResponseImp)obj;
			  System.out.println("cmdImp.getPayload()==============="+cmdImp.getPayload());
			  if(CommonKeys.COMMAND_ID_IMMEDIATEFEE.equals(cmdImp.getFlowHandler()))
				  request.setAttribute(WebKeys.REQUEST_ATTRIBUTE, obj);
		  }
	    }
	}
}