/**
 * author:david.Yang
 */
package com.dtv.oss.web.action.customer;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

//import oss class
import com.dtv.oss.web.util.*;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.service.ejbevent.csr.AccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;

public class AccountCreateWebAction extends PayFeeWebAction {
    boolean confirmPost = false;

    protected boolean needCheckToken() {
        return confirmPost;
    }

    public void doStart(HttpServletRequest request) {
        super.doStart(request);

        confirmPost = false;
        if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
            if (request.getParameter("confirm_post").equalsIgnoreCase("true")) confirmPost = true;
        }

    }

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	if (!WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
            throw new WebActionException("没有相关的客户信息标识");
    	
    	AccountEJBEvent ejbEvent = new AccountEJBEvent(AccountEJBEvent.OPENACCOUNTFORCUSTOMER);
    	           
    	setCSIDto(request,ejbEvent);
    	setAccountDto(request,ejbEvent);
    	setADDressDto(request,ejbEvent);
    	setPayFeeInfo(request,ejbEvent);
                
    	ejbEvent.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));

    	ejbEvent.setDoPost(confirmPost);
	    
        return ejbEvent;
        
    }
    
    public void setCSIDto(HttpServletRequest request, AccountEJBEvent ejbEvent) throws WebActionException{
   	    com.dtv.oss.dto.CustServiceInteractionDTO theDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
        theDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        theDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OA);
        if (WebUtil.StringHaveContent(request.getParameter("txtcsiPayOption")))
        	theDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
        ejbEvent.setCsiDto(theDTO) ;
    }

    public void setAccountDto(HttpServletRequest request, AccountEJBEvent ejbEvent) throws WebActionException{
    	 com.dtv.oss.dto.AccountDTO accountDTO = new com.dtv.oss.dto.AccountDTO();
         accountDTO.setMopID(WebUtil.StringToInt(request.getParameter("txtMopID")));
         accountDTO.setBankAccount(request.getParameter("txtBankAccount"));
         accountDTO.setAccountName(request.getParameter("txtAccountName"));
         accountDTO.setBankAccountName(request.getParameter("txtBankAccountName"));
         accountDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
         accountDTO.setAccountType(request.getParameter("txtAccountType"));
         ejbEvent.setAccountDTO(accountDTO);
    }
    
   
    public void setADDressDto(HttpServletRequest request, AccountEJBEvent ejbEvent) throws WebActionException{
    	 com.dtv.oss.dto.AddressDTO addressDTO = new com.dtv.oss.dto.AddressDTO();
         addressDTO.setDistrictID(WebUtil.StringToInt(request.getParameter("txtCounty")));
         addressDTO.setPostcode(request.getParameter("txtPostcode"));
         addressDTO.setDetailAddress(request.getParameter("txtDetailAddress"));
         ejbEvent.setAcctAddrDto(addressDTO);
    }
    
    public void setPayFeeInfo(HttpServletRequest request,AccountEJBEvent ejbEvent) throws WebActionException{
         //支付和费用  
         if (confirmPost){	
        	ejbEvent.setCsiFeeList(getSessionFeeList(request));
            getPayAndPrePayList(request,WebUtil.StringToInt(request.getParameter("txtCustomerID")),
            		                   0,ejbEvent.getCsiPaymentList(),null);
         }
        
    }
    
    
}
