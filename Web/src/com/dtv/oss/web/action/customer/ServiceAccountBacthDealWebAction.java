package com.dtv.oss.web.action.customer;

import java.util.*;

import javax.servlet.http.HttpServletRequest;


import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.dto.ServiceAccountDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;

public class ServiceAccountBacthDealWebAction extends PayFeeWebAction {
	boolean confirmPost = false;

	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("txtDoPost"))) {
			if (request.getParameter("txtDoPost").equalsIgnoreCase("TRUE"))
				confirmPost = true;
		}
	}
	
	protected EJBEvent encapsulateData(HttpServletRequest request)
	throws Exception {
		ServiceAccountEJBEvent ejbEvent = new ServiceAccountEJBEvent();
		try{
		   String[] str=request.getParameterValues("Index");
		   String serviceAcctIds ="";
		   for (int i=0; i<str.length;i++){
			 if (serviceAcctIds.equals("")){
				 serviceAcctIds =str[i];
			 } else{
				 serviceAcctIds =serviceAcctIds +","+str[i];
			 }
		   }
		   int actionType = WebUtil.StringToInt(request.getParameter("txtActionType"));
		   checkServiceAcctInfo(serviceAcctIds,actionType);
		   ejbEvent.setServiceAcctIdStr(str);
		   ejbEvent.setDoPost(confirmPost);
		   if (confirmPost) {
				setPayFeeInfo(request, ejbEvent);
		   }
		   int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
		   int accountID =WebUtil.StringToInt(request.getParameter("txtAccount"));
		   ejbEvent.setCustomerID(customerID);
		   ejbEvent.setAccountID(accountID);
		   
		   switch (actionType) {
			  case CommonKeys.BATCH_SERVICE_ACCOUNT_PAUSE:
				ejbEvent.setActionType(ServiceAccountEJBEvent.BATCH_SUSPEND);
				setCSIInfo(request, ejbEvent,
						CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UP);
		        break;
			  case CommonKeys.BATCH_SERVICE_ACCOUNT_RESUME:
				ejbEvent.setActionType(ServiceAccountEJBEvent.BATCH_RESUME);
				Collection lstCpId = new ArrayList();
				for (int i=0;i<str.length;i++){
					Collection cpList =Postern.getCustomerProductDTOByServiceAccountID(Integer.parseInt(str[i]));
					Iterator cpItr =cpList.iterator();
					while(cpItr.hasNext()){
						CustomerProductDTO cpDto=(CustomerProductDTO)cpItr.next();
						if (cpDto.getStatus().equals("S") || cpDto.getStatus().equals("H")){
							lstCpId.add(new Integer(cpDto.getPsID()));
						}
					}
				}
				ejbEvent.setColPsid(lstCpId);
				setCSIInfo(request, ejbEvent,
							CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UR);
			    break;  
			  default:
				break;
		   }
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		}
		return  ejbEvent;
    }
	
	private void checkServiceAcctInfo(String serviceAcctIds,int actionType)throws Exception{
	    String exptionStr ="";
	    Collection serviceAcctCols =null;
		switch (actionType) {
		    case CommonKeys.BATCH_SERVICE_ACCOUNT_PAUSE:
		       serviceAcctCols=Postern.getServiceAccountDto(serviceAcctIds,"'S','H'");
		       break;
		    case CommonKeys.BATCH_SERVICE_ACCOUNT_RESUME:
		       serviceAcctCols=Postern.getServiceAccountDto(serviceAcctIds,"'N'");
			   break;
		    default:
				break;
		}
		
		Iterator serviceAcctItr =serviceAcctCols.iterator();
	    while (serviceAcctItr.hasNext()){
	        ServiceAccountDTO serviceAcct =(ServiceAccountDTO)serviceAcctItr.next();
	        if (exptionStr.equals("")){
	     	    exptionStr = String.valueOf(serviceAcct.getServiceAccountID());
	    	}else{
	       	    exptionStr =exptionStr+";"+String.valueOf(serviceAcct.getServiceAccountID());
	    	}
	    }
		if (!exptionStr.equals("")){
			switch (actionType) {
		       case CommonKeys.BATCH_SERVICE_ACCOUNT_PAUSE:
			       exptionStr =exptionStr+"等业务帐户的状态非正常,不能做暂停请重新选择!";
			       break;
		       case CommonKeys.BATCH_SERVICE_ACCOUNT_RESUME:
		    	   exptionStr =exptionStr+"等业务帐户的状态非暂停,不能做恢复请重新选择!";
			       break; 
		       default:
				   break;    	
		    }
		    throw new Exception(exptionStr);
	   }
	}
	
	private void setCSIInfo(HttpServletRequest request,
			ServiceAccountEJBEvent ejbEvent, String csiType) {
		// 受理单信息
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		csiDTO.setType(csiType);
		if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
			csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount")));
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			csiDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
	
		if (WebUtil.StringHaveContent(request.getParameter("txtcsiPayOption")))
            csiDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
		if (WebUtil.StringHaveContent(request.getParameter("txtCsiCreateReason")))
            csiDTO.setCreateReason(request.getParameter("txtCsiCreateReason"));
		ejbEvent.setCsiDto(csiDTO);
	}
	
	private void setPayFeeInfo(HttpServletRequest request,
			ServiceAccountEJBEvent ejbEvent) throws WebActionException {
		ejbEvent.setFeeList(getSessionFeeList(request));
		int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
		int accountID =WebUtil.StringToInt(request.getParameter("txtAccount"));
		getPayList(request,customerID,accountID,
				   ejbEvent.getPaymentList(),
				   CommonKeys.PAYMENTRECORD_TYPE_ACCEPT_CASE,
				   ejbEvent.getCsiPrePaymentDeductionList());
		
	}
}
