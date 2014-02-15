package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.dtv.oss.dto.InvoiceDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.AccountEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.action.PayFeeWebAction;

public class InvoiceBatchPayWebAction extends PayFeeWebAction{

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
		AccountEJBEvent ejbEvent=new AccountEJBEvent(AccountEJBEvent.INVOICE_BATCH_PAY);
		//this.setCsiListInfo(request,ejbEvent);
		setInvoiceListInfo(request,ejbEvent);
		setPayFeeInfo(request,ejbEvent);
		return ejbEvent;
	}
	private void setPayFeeInfo(HttpServletRequest request, AccountEJBEvent ejbEvent) throws WebActionException{
		getPayList(request,0,0,
				   ejbEvent.getCsiPaymentList(),
				   CommonKeys.PAYMENTRECORD_TYPE_BILLING,
				   ejbEvent.getCsiPrePaymentDeductionList());
		
	}
	private void setInvoiceListInfo(HttpServletRequest request,AccountEJBEvent event)throws Exception{
		if(WebUtil.StringHaveContent(request.getParameter("strInvoiceNo"))){
			String[] str=request.getParameter("strInvoiceNo").split(";");
			Map invoiceMap =new HashMap();
			for(int i=0;i<str.length;i++){
				InvoiceDTO dto=Postern.getInvoiceDTOByInvoiceNo(Integer.parseInt(str[i]));
				dto.setInvoiceNo(Integer.parseInt(str[i]));
				if (!invoiceMap.containsKey(new Integer(dto.getAcctID()))){
					Collection invoiceCols =new ArrayList();
					invoiceCols.add(dto);
					invoiceMap.put(new Integer(dto.getAcctID()), invoiceCols);
				}else{
					Collection  invoiceCols=(Collection)invoiceMap.get(new Integer(dto.getAcctID()));
					invoiceCols.add(dto);
				}
			}
			Collection invoiceList=new ArrayList();
			Iterator invoiceItr =invoiceMap.keySet().iterator();
			while (invoiceItr.hasNext()){
				Integer inoviceId =(Integer)invoiceItr.next();
				Collection invoiceCols =(Collection)invoiceMap.get(inoviceId);
				invoiceList.addAll(invoiceCols);
			}
			
			event.setCsiFeeList(invoiceList);
		}
	}
	/*private void setCsiListInfo(HttpServletRequest request,AccountEJBEvent event)throws Exception{
		if(WebUtil.StringHaveContent(request.getParameter("strInvoiceNo"))){
			String[] str=request.getParameter("strInvoiceNo").split(";");
			Collection csiDtoList=new ArrayList();
			for(int i=0;i<str.length;i++){
				CustServiceInteractionDTO csiDTO=new CustServiceInteractionDTO();
				InvoiceDTO iDto=Postern.getInvoiceDTOByInvoiceNo(WebUtil.StringToInt(str[i]));
				csiDTO.setAccountID(iDto.getAcctID());
				csiDTO.setCustomerID(iDto.getCustID());
				csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PI);
				csiDtoList.add(csiDTO);
			}
		}
	}*/
	
}
