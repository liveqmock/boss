package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.InvoiceDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.AccountEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class SingleAccountPayWebAction extends PayFeeWebAction {
	boolean confirmPost = false;

    protected boolean needCheckToken() {
	    return confirmPost;
	}
    
	public void doStart(HttpServletRequest request) {
		super.doStart(request);

		confirmPost = false;
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
				confirmPost = true;
		}
	}
	protected EJBEvent encapsulateData(HttpServletRequest request)
	throws Exception {
		AccountEJBEvent ejbEvent =new AccountEJBEvent(AccountEJBEvent.SINGLE_ACCOUNT_PAY);	 
		setInvoiceListInfo(request,ejbEvent);
		setPayFeeInfo(request,ejbEvent);
		setAccountDTO(request,ejbEvent);
		return ejbEvent;
	}
	private void setPayFeeInfo(HttpServletRequest request, AccountEJBEvent ejbEvent) throws WebActionException{
		getPayAndPrePayList(request,0,0,ejbEvent.getCsiPaymentList(),ejbEvent.getCsiPrePaymentDeductionList());
	}
	private void setInvoiceListInfo(HttpServletRequest request,AccountEJBEvent event)throws Exception{
		if(WebUtil.StringHaveContent(request.getParameter("strInvoiceNo"))){
			String[] str=request.getParameter("strInvoiceNo").split(";");
			Collection invoiceList=new ArrayList();
			for(int i=0;i<str.length;i++){
				InvoiceDTO dto=Postern.getInvoiceDTOByInvoiceNo(Integer.parseInt(str[i]));
				dto.setInvoiceNo(Integer.parseInt(str[i]));
				invoiceList.add(dto);
			}
			event.setCsiFeeList(invoiceList);
		}
	}
	private void setAccountDTO(HttpServletRequest request,AccountEJBEvent event)throws Exception{
		String txtAccountID = request.getParameter("txtAccountID");
		if(WebUtil.StringHaveContent(request.getParameter("txtAccountID"))){
			AccountDTO accountDTO =new AccountDTO();
			accountDTO.setAccountID(WebUtil.StringToInt(txtAccountID));		
			event.setAccountDTO(accountDTO);
		} else{
			throw new Exception("没有设置帐户信息!");
		}
	
	}
	

}
