package com.dtv.oss.web.action.customer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.AccountEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class ModifyAccountConfirmWebAction extends GeneralWebAction {
	
	private ThreadLocal local = new ThreadLocal();

	private String gets(String pa, int index)
	{
		HttpServletRequest request = (HttpServletRequest) local.get();
	
		String[] rets = request.getParameterValues(pa);
		String ret="";
		try{ ret=rets[index];}catch(Exception e){}
		return ret;
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		

		local.set(request);
		
		String rets = request.getParameter("txtAccountSize");
		int length=Integer.parseInt(rets);
		
		String txtCustomerID=request.getParameter("txtCustomerID");
		String txtCsiID =request.getParameter("txtCsiID");		
		if (!WebUtil.StringHaveContent(txtCustomerID))
           throw new WebActionException("没有相关的客户信息标识");
		
    	AccountEJBEvent event =new AccountEJBEvent(EJBEvent.CHECKBANKINFOFORMAT);
    	
    	Collection  addressDTOList = new ArrayList();
    	Collection  accountDTOList = new ArrayList();

    	for(int i=0;i<length;i++){

	    	//帐户信息
	        com.dtv.oss.dto.AccountDTO accountDTO = new com.dtv.oss.dto.AccountDTO();
	        accountDTO.setAccountID(WebUtil.StringToInt(gets("txtAccountID",i)));
	        accountDTO.setAccountName(gets("txtAccountName",i));
	        accountDTO.setAccountType(gets("txtAccountType",i));
	        accountDTO.setBillAddressFlag(gets("txtBillAddressFlag",i));
	        accountDTO.setInvalidAddressReason(gets("txtInvalidAddressReason",i));
	        accountDTO.setBankAccountName(gets("txtBankAccountName",i));	           
	        accountDTO.setAddressID(WebUtil.StringToInt(gets("txtAddressID",i)));	               
	        accountDTO.setBankAccount(gets("txtBankAccount",i));	        
	        accountDTO.setMopID(WebUtil.StringToInt(gets("txtMopID",i)));	        
	        accountDTO.setDtLastmod(Timestamp.valueOf(gets("txtAccountDtLastmod",i)));	        

	        //帐户地址信息
	        com.dtv.oss.dto.AddressDTO addressDTO = new com.dtv.oss.dto.AddressDTO();
        	addressDTO.setAddressID(WebUtil.StringToInt(gets("txtAddressID",i)));      	
        	addressDTO.setDetailAddress(gets("txtDetailAddress",i));       	
        	addressDTO.setDistrictID(WebUtil.StringToInt(gets("txtbillCounty",i)));    	
        	addressDTO.setDtLastmod(Timestamp.valueOf(gets("txtAddressDtLastmod",i)));       	
        	addressDTO.setPostcode(gets("txtPostcode",i));

        	addressDTOList.add(addressDTO);
        	accountDTOList.add(accountDTO);
	        
    	}
        event.setAcctAddressDTOList(addressDTOList);
        event.setAccountDTOList(accountDTOList);
        event.setCsiID(WebUtil.StringToInt(txtCsiID));
        event.setCustomerID(WebUtil.StringToInt(txtCustomerID));

        return event;
	}

}
