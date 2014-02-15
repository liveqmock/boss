package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.InvoiceDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.AccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.PayFeeWebAction;

/**
 * 支付帐单 
 * @author Stone Liang
 * 2006/01/18
 */

public class PayBillWebAction extends PayFeeWebAction{
	boolean confirmPost = false;
	protected boolean needCheckToken() {
		return confirmPost;
	}
	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("txtDoPost"))) {
			if(request.getParameter("txtDoPost").equalsIgnoreCase( "TRUE"))
				confirmPost = true;
		}
	}	
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
		String billop =request.getParameter("bill_op");
		AccountEJBEvent ejbEvent =null;
		if (WebUtil.StringHaveContent(billop)){
			if (billop.equalsIgnoreCase("pay")){
			   ejbEvent=new AccountEJBEvent(EJBEvent.PAYBYBILL);
			   ejbEvent.setDoPost( confirmPost);
			   setCSIInfo(request,ejbEvent);
			   setCustomerInfo(request,ejbEvent);
			   setAccountInfo(request,ejbEvent);
			   setInvoiceInfo(request,ejbEvent);
			   if(confirmPost){
				  setFeeInfo(request,ejbEvent);
			   }
		    }
			if (billop.equalsIgnoreCase("check")){
			   ejbEvent=new AccountEJBEvent(EJBEvent.PAYCHEK);
			   ejbEvent.setDoPost( confirmPost);
			   setInvoiceInfo(request,ejbEvent);
			}
		}
		return ejbEvent;
    }
	private void setInvoiceInfo(HttpServletRequest request, AccountEJBEvent ejbEvent)
	{
		//帐单信息
		InvoiceDTO invoiceDTO = new InvoiceDTO();
        if (WebUtil.StringHaveContent(request.getParameter("txtInvoiceNo")))
        	invoiceDTO.setInvoiceNo( WebUtil.StringToInt(request.getParameter("txtInvoiceNo") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
        	invoiceDTO.setAcctID (WebUtil.StringToInt(request.getParameter("txtAccountID") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	invoiceDTO.setCustID( WebUtil.StringToInt(request.getParameter("txtCustomerID") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtPayAmount")))
        	invoiceDTO.setPayAmount ( WebUtil.StringTodouble( request.getParameter("txtPayAmount") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtPunishment")))
        	invoiceDTO.setPunishment( WebUtil.StringTodouble( request.getParameter("txtPunishment") ));
		if (WebUtil.StringHaveContent(request.getParameter("txtBarCode")))
			invoiceDTO.setBarCode(request.getParameter("txtBarCode"));
        
        ejbEvent.setInvoiceDTO ( invoiceDTO);
	}   
	private void setCustomerInfo(HttpServletRequest request, AccountEJBEvent ejbEvent) throws WebActionException
	{
		String customer = request.getParameter("txtCustomerID");
		if (WebUtil.StringHaveContent(customer)) {
			ejbEvent.setCustomerID (WebUtil.StringToInt(customer));
		}
		else
		{
			throw new WebActionException("请先做客户查询，定位客户！");
		}
	}
		
	private void setAccountInfo(HttpServletRequest request, AccountEJBEvent ejbEvent) throws WebActionException
	{
		String account = request.getParameter("txtAccountID");
		if (WebUtil.StringHaveContent(account)) {
			AccountDTO acctDto = new AccountDTO();
			acctDto.setAccountID( WebUtil.StringToInt(account));
			String acctName = Postern.getAcctNameById(WebUtil.StringToInt(account));
			acctDto.setAccountName(acctName);
			ejbEvent.setAccountDTO(acctDto);			
		}
		else
		{
			throw new WebActionException("请指定一个有效帐户！");
		}
	}
	
	private void setFeeInfo(HttpServletRequest request, AccountEJBEvent ejbEvent) throws WebActionException
	{
		//费用列表
		ArrayList feeList = new ArrayList();
		//滞纳金
		if(Float.parseFloat(request.getParameter("txtPunishment"))>0)
		{
			AccountItemDTO feeDto = new AccountItemDTO();
			feeDto.setValue( Float.parseFloat(request.getParameter("txtPunishment")));
			feeDto.setAcctItemTypeID(Postern.getAcctItemTypeIDyFieldName("PunishmentAcctItemTypeID"));
			feeDto.setStatus(CommonKeys.SET_F_FTSTATUS_P);
			feeDto.setForcedDepositFlag(CommonKeys.FORCEDDEPOSITFLAG_N);
			feeDto.setReferType(CommonKeys.AIREFERTYPE_M);
			feeDto.setInvoiceFlag( CommonKeys.FORCEDDEPOSITFLAG_Y);
			feeDto.setInvoiceNO(WebUtil.StringToInt(request.getParameter("txtInvoiceNo") ));
			feeDto.setCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
			feeDto.setAcctID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
			feeList.add( feeDto);
		}
		//帐单金额后台取
		ejbEvent.setCsiFeeList(feeList) ;
		
		//支付列表	
		int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
		int accountID =WebUtil.StringToInt(request.getParameter("txtAccountID"));
		
		getPayList(request,customerID,accountID,
				   ejbEvent.getCsiPaymentList(),
				   CommonKeys.PAYMENTRECORD_TYPE_BILLING,
				   ejbEvent.getCsiPrePaymentDeductionList());
		
	}
	
	private void setCSIInfo(HttpServletRequest request, AccountEJBEvent ejbEvent)
	{
		//受理单信息
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		
        csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PI);
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	csiDTO.setCustomerID (WebUtil.StringToInt(request.getParameter("txtCustomerID") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtAccountID")))
        	csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccountID") ));
        
        csiDTO.setBillCollectionMethod(CommonKeys.CSI_PAYMENT_OPTION_IM);
        ejbEvent.setCsiDto( csiDTO);
	}
	
}