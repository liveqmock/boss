package com.dtv.oss.web.action.customer;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.AccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class AccountWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		if (!WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
            throw new WebActionException("没有相关的客户帐户信息标识");
		if (!WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
            throw new WebActionException("没有相关的客户信息标识");
		if (!WebUtil.StringHaveContent(request.getParameter("actionType")))
            throw new WebActionException("操作类型未知");
		
    	AccountEJBEvent event =null;
    	
    	//修改帐户信息
    	if("update".equals(request.getParameter("actionType")))
    		event=new AccountEJBEvent(EJBEvent.UPDATEACCOUNTINFO);
    	else
    		throw new WebActionException("操作类型未知");
    	
    	//帐户信息
        com.dtv.oss.dto.AccountDTO accountDTO = new com.dtv.oss.dto.AccountDTO();
        accountDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
        accountDTO.setAccountName(request.getParameter("txtAccountName"));
        accountDTO.setBankAccountName(request.getParameter("txtBankAccountName"));	
        accountDTO.setAccountType(request.getParameter("txtAccountType"));
        accountDTO.setAddressID(WebUtil.StringToInt(request.getParameter("txtAddressID")));
        accountDTO.setInvalidAddressReason(request.getParameter("txtInvalidAddressReason"));
        accountDTO.setBankAccount(request.getParameter("txtBankAccount"));
        accountDTO.setMopID(WebUtil.StringToInt(request.getParameter("txtMopID")));
        accountDTO.setDtLastmod(Timestamp.valueOf(request.getParameter("txtAccountDtLastmod")));
        accountDTO.setBillAddressFlag(request.getParameter("txtBillAddressFlag"));
        accountDTO.setComments(request.getParameter("txtComments"));
        
        
        //CSI信息
        com.dtv.oss.dto.CustServiceInteractionDTO theDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
        theDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        theDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_IA);
        theDTO.setAccountID(WebUtil.StringToInt(request.getParameter("accountID")));

        //帐户地址信息
        com.dtv.oss.dto.AddressDTO addressDTO = new com.dtv.oss.dto.AddressDTO();
        if(WebUtil.StringHaveContent(request.getParameter("txtAddressID")))
        	addressDTO.setAddressID(WebUtil.StringToInt(request.getParameter("txtAddressID")));
        if(WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
        	addressDTO.setDetailAddress(request.getParameter("txtDetailAddress"));
        if(WebUtil.StringHaveContent(request.getParameter("txtbillCounty")))
        	addressDTO.setDistrictID(WebUtil.StringToInt(request.getParameter("txtbillCounty")));
        if(WebUtil.StringHaveContent(request.getParameter("txtAddressDtLastmod")))
        	addressDTO.setDtLastmod(Timestamp.valueOf(request.getParameter("txtAddressDtLastmod")));
        if(WebUtil.StringHaveContent(request.getParameter("txtPostcode")))
        	addressDTO.setPostcode(request.getParameter("txtPostcode"));
        
       
        event.setAccountDTO(accountDTO);
        event.setCsiDto(theDTO);
        event.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        event.setAcctAddrDto(addressDTO);
        
        return event;
	}

}
