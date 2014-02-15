package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.InvoiceDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.AccountEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;

public class MergeInvoiceBatchPayWebAction extends GeneralWebAction{
	
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
	
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		AccountEJBEvent ejbEvent=new AccountEJBEvent(AccountEJBEvent.MERGEINVOICE_BATCH_PAY);
		setInvoiceListInfo(request,ejbEvent);
		setPayFeeInfo(request,ejbEvent);
		return ejbEvent;
	}
	
	private void setInvoiceListInfo(HttpServletRequest request,AccountEJBEvent event)throws Exception{
		if(WebUtil.StringHaveContent(request.getParameter("invoiceIds"))){
			String[] invoiceIds=request.getParameterValues("invoiceIds");
			Collection invoiceList=new ArrayList();
			for(int i=0;i<invoiceIds.length;i++){
				String[] str =invoiceIds[i].split(";");
				for (int j=0; j<str.length; j++){
					InvoiceDTO invoiceDto=Postern.getInvoiceDTOByInvoiceNo(Integer.parseInt(str[j]));
				    invoiceList.add(invoiceDto);
				}
			}
			event.setCsiFeeList(invoiceList);
		}
	}
	
	private void setPayFeeInfo(HttpServletRequest request, AccountEJBEvent ejbEvent) throws WebActionException{
		String pay_realMOP =request.getParameter("pay_realMOP");
		String pay_realaddInfo =request.getParameter("pay_realaddInfo");	
		String pay_realPay =request.getParameter("billPayTotal");
		String pay_realPrepay =request.getParameter("custAveragePrePay");
		if (pay_realMOP.trim().equals("")){
			throw new WebActionException("支付方式的配置参数有误,请与管理员联系!");
		}
		
		String mop_id =pay_realMOP.substring(0,pay_realMOP.indexOf("-"));
		String ticketType =pay_realMOP.substring(pay_realMOP.indexOf("-")+1,pay_realMOP.indexOf("_"));
		String paymentType =pay_realMOP.substring(pay_realMOP.indexOf("_")+1);
		String prePaymentType ="";
		if ("N".equals(paymentType))
			 prePaymentType =CommonKeys.PREPAYMENTTYPE_TRANSLUNARY;
	    else
	    	 prePaymentType =CommonKeys.PREPAYMENTTYPE_CASH;
		 
		int mopid = Integer.parseInt(mop_id);
		if (Float.parseFloat(pay_realPay)*1.0 !=0){ 
			 PaymentRecordDTO dto=new PaymentRecordDTO();
	   	   	 dto.setMopID(mopid);
  	         dto.setAmount(Float.parseFloat(pay_realPay)); 
  	         dto.setTicketType(ticketType);
  	         dto.setTicketNo(pay_realaddInfo);
  	         dto.setPayType(CommonKeys.PAYMENTRECORD_TYPE_ACCEPT_CASE);
  	         dto.setPrepaymentType(prePaymentType);
  	         ejbEvent.getCsiPaymentList().add(dto);
		}
		
		if(WebUtil.StringHaveContent(request.getParameter("strAcctIds"))){
			String[] str=request.getParameter("strAcctIds").split(";");
			for(int i=0;i<str.length;i++){
				AccountDTO acctDto =Postern.getAccountDto(Integer.parseInt(str[i]));
				if (Float.parseFloat(pay_realPrepay)*1.0 !=0){
			   		PaymentRecordDTO dto=new PaymentRecordDTO();
			   	    dto.setCustID(acctDto.getCustomerID());
			   	   	dto.setAcctID(acctDto.getAccountID());
			   	    dto.setMopID(mopid);
			   	    dto.setAmount(Float.parseFloat(pay_realPrepay));
			        dto.setPayType(CommonKeys.PAYMENTRECORD_TYPE_PRESAVE);  
			   	    dto.setTicketType(ticketType);
			   	    dto.setTicketNo(pay_realaddInfo);
			   	    dto.setPrepaymentType(prePaymentType);
			   	    ejbEvent.getCsiPrePaymentDeductionList().add(dto);
			   	 }
			}
		}       
		 
		 
	}

}
