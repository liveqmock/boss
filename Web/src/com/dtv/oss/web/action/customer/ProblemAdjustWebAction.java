package com.dtv.oss.web.action.customer;

/**
 * ������,
 * ����07-1-17,����ʽ����
 */
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AccountAdjustmentDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.AccountAdjustEJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;

public class ProblemAdjustWebAction extends GeneralWebAction {
	
	protected boolean needCheckToken() {
		return true;
	}
	
	private int getAdjustType(HttpServletRequest request) throws WebActionException{
		String adjustType=request.getParameter("txtAdjustType");
		if (!WebUtil.StringHaveContent(adjustType))
            throw new WebActionException("δ֪���͵ĵ���!");
		if(CommonKeys.ADJUST_REFERCODETYPE_F.equals(adjustType)){
			return AccountAdjustEJBEvent.PROBLEM_ADJUST_FEE;
		}else if(CommonKeys.ADJUST_REFERCODETYPE_C.equals(adjustType)){
			return AccountAdjustEJBEvent.PROBLEM_ADJUST_PAYMENT;
		}else{
            throw new WebActionException("δ֪���͵ĵ���!");
		}
	}
	
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
    	AccountAdjustEJBEvent event =new AccountAdjustEJBEvent();
    	
    	int adjustType=getAdjustType(request);
		event.setActionType(adjustType);
		
		event.setAccountAdjustmentDTO(encapsulateAccountAdjustmentDTO(request));
		
		if(AccountAdjustEJBEvent.PROBLEM_ADJUST_FEE==adjustType){
			event.setAccountItemDTO(encapsulateAccountItemDTO(request));
		}else if(AccountAdjustEJBEvent.PROBLEM_ADJUST_PAYMENT==adjustType){
			event.setPaymentRecordDTO(encapsulatePaymentRecordDTO(request));
		}
	        
        return event;
	}

	private AccountItemDTO encapsulateAccountItemDTO(HttpServletRequest request) throws WebActionException{
		AccountItemDTO acctItemDto=new AccountItemDTO();
        String referID=request.getParameter("txtID");
        if(WebUtil.StringHaveContent(referID)){
        	acctItemDto.setReferID(WebUtil.StringToInt(referID)); 
        }else{
        	throw new WebActionException("��Ч�ı��޵�!");
        }
		String accountID=request.getParameter("txtAccountID");
        if(WebUtil.StringHaveContent(accountID)){
        	acctItemDto.setAcctID(WebUtil.StringToInt(accountID));
        }else{
        	throw new WebActionException("��Ч���ʻ�!");
        }
		String customerID=request.getParameter("txtCustomerID");
        if(WebUtil.StringHaveContent(customerID)){
        	acctItemDto.setCustID(WebUtil.StringToInt(customerID));
        }else{
        	throw new WebActionException("��Ч�Ŀͻ�!");
        }
		String serviceAccountID=request.getParameter("txtServiceAccountID");
        if(WebUtil.StringHaveContent(serviceAccountID)){
        	acctItemDto.setServiceAccountID(WebUtil.StringToInt(serviceAccountID));
        }        
		String amount=request.getParameter("txtAmount");
        if(WebUtil.StringHaveContent(amount)){
        	acctItemDto.setValue(WebUtil.StringTodouble(amount));
        }else{
        	throw new WebActionException("��Ч�ĵ��ʽ��!");
        }
		String feeType=request.getParameter("txtFeeType");
        if(WebUtil.StringHaveContent(feeType)){
        	acctItemDto.setFeeType(feeType);
        }else{
        	throw new WebActionException("��Ч�ķ�������!");
        }
		String acctItemType=request.getParameter("txtAcctItemType");
        if(WebUtil.StringHaveContent(acctItemType)){
        	acctItemDto.setAcctItemTypeID(acctItemType);
        }else{
        	throw new WebActionException("��Ч����Ŀ����!");
        }
        String billingCycle=request.getParameter("txtBillingCycle");
        if(WebUtil.StringHaveContent(billingCycle)){
        	acctItemDto.setBillingCycleID(WebUtil.StringToInt(billingCycle));
        }
 
        String feeReMark=request.getParameter("txtFeeReMark");
        if(WebUtil.StringHaveContent(feeReMark)){
        	acctItemDto.setComments(feeReMark);
        }
		return acctItemDto;
	}
	private PaymentRecordDTO encapsulatePaymentRecordDTO(HttpServletRequest request) throws WebActionException{
		PaymentRecordDTO paymentDto=new PaymentRecordDTO();
        String referID=request.getParameter("txtID");
        if(WebUtil.StringHaveContent(referID)){
        	paymentDto.setReferID(WebUtil.StringToInt(referID)); 
        	paymentDto.setSourceRecordID(WebUtil.StringToInt(referID));
        	paymentDto.setPaidTo(WebUtil.StringToInt(referID));
        }else{
        	throw new WebActionException("��Ч�ı��޵�!");
        }
		String accountID=request.getParameter("txtAccountID");
        if(WebUtil.StringHaveContent(accountID)){
        	paymentDto.setAcctID(WebUtil.StringToInt(accountID));
        }else{
        	throw new WebActionException("��Ч���ʻ�!");
        }
		String customerID=request.getParameter("txtCustomerID");
        if(WebUtil.StringHaveContent(customerID)){
        	paymentDto.setCustID(WebUtil.StringToInt(customerID));
        }else{
        	throw new WebActionException("��Ч�Ŀͻ�!");
        }
		String amount=request.getParameter("txtAmount");
        if(WebUtil.StringHaveContent(amount)){
        	paymentDto.setAmount(WebUtil.StringTodouble(amount));
        }else{
        	throw new WebActionException("��Ч�ĵ��ʽ��!");
        }
        String mop=request.getParameter("MOP");
        String mopid=mop.split("-")[0];
        if(WebUtil.StringToInt(mopid)>0){
        	paymentDto.setMopID(WebUtil.StringToInt(mopid));
        	String cashFlag=mop.split("-")[1].split("_")[1];
        	String payType=mop.split("-")[1].split("_")[0];

        	if("Y".equals(cashFlag)){
            	paymentDto.setPrepaymentType(CommonKeys.PREPAYMENTTYPE_CASH);
        	}else{
            	paymentDto.setPrepaymentType(CommonKeys.PREPAYMENTTYPE_TRANSLUNARY);
        	}
        	//��ǰ֧����ʽ�ǵֿ�ȯʱ,��Ҫ����ȯ��
        	if(CommonKeys.PAYMENTTICKETTY_DK.equals(payType)){
        		String ticketNO=request.getParameter("txtTicketNO");
                if(WebUtil.StringHaveContent(ticketNO)){
                	paymentDto.setTicketType(payType);
                	paymentDto.setTicketNo(ticketNO);
                }else{
                	throw new WebActionException("��Ч�ĵֿ�ȯ��!");
                }
        	}
        }else{
        	throw new WebActionException("��Ч��֧����ʽ!");
        }
        String collectorID=request.getParameter("txtCollectorID");
        String collectorName=request.getParameter("txtCollectorName");
        if(WebUtil.StringHaveContent(collectorID)){
        	paymentDto.setOpID(WebUtil.StringToInt(collectorID));
        }else if(WebUtil.StringHaveContent(collectorName)){
        	int opid=Postern.getOperatorIDByLoginID(collectorName);
        	LogUtility.log(this.getClass(), LogLevel.DEBUG, "collectorName:"+ collectorName);
        	LogUtility.log(this.getClass(), LogLevel.DEBUG, "opid:"+ opid);
        	if(opid!=0){
        		paymentDto.setOpID(opid);
        	}else{
            	throw new WebActionException("��Ч���շ���!�շ����ʺ��������.");
        	}
        }else{
        	throw new WebActionException("��Ч���շ���!δ������Ч���շ�����Ϣ.");
        }
        String paymentTime=request.getParameter("txtCollectorDate");
        if(WebUtil.StringHaveContent(paymentTime)){
        	paymentDto.setPaymentTime(WebUtil.StringToTimestamp(paymentTime));
        }else{
        	throw new WebActionException("��Ч���շ�����!");
        }
		String payReMark=request.getParameter("txtPayReMark");
		paymentDto.setComments(payReMark);
		return paymentDto;
	}
	private AccountAdjustmentDTO encapsulateAccountAdjustmentDTO(HttpServletRequest request) throws WebActionException{
		AccountAdjustmentDTO adjustDto=new AccountAdjustmentDTO();
		
		String accountID=request.getParameter("txtAccountID");
        if(WebUtil.StringHaveContent(accountID)){
        	adjustDto.setAcctID(WebUtil.StringToInt(accountID));
        }else{
        	throw new WebActionException("��Ч���ʻ�!");
        }
		String customerID=request.getParameter("txtCustomerID");
        if(WebUtil.StringHaveContent(customerID)){
        	adjustDto.setCustID(WebUtil.StringToInt(customerID));
        }else{
        	throw new WebActionException("��Ч�Ŀͻ�!");
        }
		String adjustReason=request.getParameter("txtAdjustReason");
        if(WebUtil.StringHaveContent(adjustReason)){
        	adjustDto.setReason(adjustReason);
        }else{
        	throw new WebActionException("��Ч�ĵ���ԭ��!");
        }
		String adjustDate=request.getParameter("txtAdjustDate");
        if(WebUtil.StringHaveContent(adjustDate)){
//        	adjustDto.setCreateTime(WebUtil.StringToTimestamp(adjustDate));
        	adjustDto.setAdjustmentDate(WebUtil.StringToTimestamp(adjustDate)); 
        }else{
        	throw new WebActionException("��Ч�ĵ�������!");
        }
        
		String adjustReMark=request.getParameter("txtAdjustReMark");
		adjustDto.setComments(adjustReMark);
		
		return adjustDto;
	}
}
