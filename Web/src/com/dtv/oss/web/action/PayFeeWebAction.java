/**
 * author :david.Yang
 * 此WebAction 主要提供自动移除Session中的费用。并提供getFeeList获得费用列表;
 * getMultiPayList 和getSinglePayList两种方法分别对应两种不同的支付方式！
 */
package com.dtv.oss.web.action;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.exception.WebActionException;
public abstract class PayFeeWebAction extends BatchBuyWebAction {
	protected abstract EJBEvent encapsulateData(HttpServletRequest request) throws Exception;
	public ArrayList getSessionFeeList(HttpServletRequest request) throws WebActionException {
		 CommandResponse RepCmd = (CommandResponse)(request.getSession().getAttribute(CommonKeys.FEE_SESSION_NAME));	
		 if (RepCmd != null){
			ArrayList lstFee = (ArrayList)(RepCmd.getPayload());
            return lstFee;
		 } else{
			throw new WebActionException("Session中没有费用，不能得到费用列表！");
		 }
	}
	
   public void getPayList(HttpServletRequest request,int customerID,int accountID,
		                       Collection lstPayment, String payType,Collection lstPrePaymentDeduction)
	  throws WebActionException{
	   LogUtility.log(this.getClass(), LogLevel.DEBUG,"*************accepet payFee page begin********"); 
		String[] pay_realMOP =request.getParameterValues("pay_realMOP");
		String[] pay_realaddInfo =request.getParameterValues("pay_realaddInfo");	
		String[] pay_realPay =request.getParameterValues("pay_realpay");
		if (pay_realPay !=null){
		   for(int i=0 ; i<pay_realPay.length ; i++){  	 
			   if (pay_realMOP[i].trim().equals("")) continue;
			   if (Double.parseDouble(pay_realPay[i])*1.0 ==0) continue;
			   String mop_id =pay_realMOP[i].substring(0,pay_realMOP[i].indexOf("-"));
			   String ticketType =pay_realMOP[i].substring(pay_realMOP[i].indexOf("-")+1,pay_realMOP[i].indexOf("_"));
			   String paymentType =pay_realMOP[i].substring(pay_realMOP[i].indexOf("_")+1);
			   String prePaymentType ="";
			   if ("N".equals(paymentType))
					 prePaymentType =CommonKeys.PREPAYMENTTYPE_TRANSLUNARY;
			   else
			    	 prePaymentType =CommonKeys.PREPAYMENTTYPE_CASH;
			   
			   int mopid = Integer.parseInt(mop_id);
			   if (mopid !=10 && mopid !=11){
				  if (lstPayment ==null) throw new WebActionException("支付的初始集合为空！");
			      PaymentRecordDTO dto=new PaymentRecordDTO();
			      dto.setCustID(customerID);
				  dto.setAcctID (accountID);
			  	  dto.setMopID(mopid);
			   	  dto.setAmount(Double.parseDouble(pay_realPay[i]));
			   	  dto.setTicketType(ticketType);
			   	  dto.setTicketNo(pay_realaddInfo[i]);
			   	  dto.setPayType(payType); 
			   	  dto.setPrepaymentType(prePaymentType);
			   	  lstPayment.add(dto);
			  } else{
				  if (lstPrePaymentDeduction ==null) throw new WebActionException("预存抵扣的初始集合为空！");
				  if (accountID ==0)  throw new WebActionException("内部帐户为空，请与管理员联系！");
				  PrepaymentDeductionRecordDTO dto =new PrepaymentDeductionRecordDTO();
				  dto.setCustId(customerID);
				  dto.setAcctId(accountID);
				  dto.setAmount(Double.parseDouble(pay_realPay[i]));
				  dto.setCreatingMethod(CommonKeys.FTCREATINGMETHOD_A);
				  dto.setPrepaymentType(prePaymentType);
				  dto.setComments(pay_realMOP[i]);
				  lstPrePaymentDeduction.add(dto);
			  }
			   LogUtility.log(this.getClass(), LogLevel.DEBUG,"=============pay_realMOP["+i+"]======="+pay_realMOP[i]); 
			   LogUtility.log(this.getClass(), LogLevel.DEBUG,"=============pay_realPay["+i+"]========"+pay_realPay[i]); 	
		   }
		}
		LogUtility.log(this.getClass(), LogLevel.DEBUG,"支付列表==============="+lstPayment);
		LogUtility.log(this.getClass(), LogLevel.DEBUG,"抵扣列表==============="+lstPrePaymentDeduction);
		LogUtility.log(this.getClass(), LogLevel.DEBUG,"*************accepet payAndPre page end********"); 
	}
   
   public void getPayAndPrePayList(HttpServletRequest request,int customerID,int accountID,
		                           Collection lstPayment, Collection lstPrePaymentDeduction) 
      throws WebActionException{
	   LogUtility.log(this.getClass(), LogLevel.DEBUG,"*************accepet payAndPre Payment page begin********"); 
		String[] pay_realMOP =request.getParameterValues("pay_realMOP");
		String[] pay_realaddInfo =request.getParameterValues("pay_realaddInfo");	
		String[] pay_realPay =request.getParameterValues("pay_realpay");
		String[] pay_realPrepay =request.getParameterValues("pay_realprepay");
		if (pay_realPay !=null){
		   for(int i=0 ; i<pay_realPay.length ; i++){ 
			 if (pay_realMOP[i].trim().equals("")) continue;
			 String mop_id =pay_realMOP[i].substring(0,pay_realMOP[i].indexOf("-"));
			 String ticketType =pay_realMOP[i].substring(pay_realMOP[i].indexOf("-")+1,pay_realMOP[i].indexOf("_"));
			 String paymentType =pay_realMOP[i].substring(pay_realMOP[i].indexOf("_")+1);
			 String prePaymentType ="";
			 if ("N".equals(paymentType))
				 prePaymentType =CommonKeys.PREPAYMENTTYPE_TRANSLUNARY;
		     else
		    	 prePaymentType =CommonKeys.PREPAYMENTTYPE_CASH;
			 
			 int mopid = Integer.parseInt(mop_id);
		   	 if (Double.parseDouble(pay_realPay[i])*1.0 !=0){
			     if (mopid !=10 && mopid !=11){
			    	 PaymentRecordDTO dto=new PaymentRecordDTO();
			   	   	 dto.setCustID(customerID);
			   	     dto.setAcctID(accountID);
		   	         dto.setMopID(mopid);
		   	         dto.setAmount(Double.parseDouble(pay_realPay[i])); 
		   	         dto.setTicketType(ticketType);
		   	         dto.setTicketNo(pay_realaddInfo[i]);
		   	         dto.setPayType(CommonKeys.PAYMENTRECORD_TYPE_ACCEPT_CASE);
		   	         dto.setPrepaymentType(prePaymentType);
		   	         lstPayment.add(dto);
		   	     } else {
		   	    	if (lstPrePaymentDeduction ==null) throw new WebActionException("预存抵扣的初始集合为空！");
				    if (accountID ==0)  throw new WebActionException("内部帐户为空，请与管理员联系！");
					PrepaymentDeductionRecordDTO dto =new PrepaymentDeductionRecordDTO();
					dto.setCustId(customerID);
					dto.setAcctId(accountID);
					dto.setAmount(Double.parseDouble(pay_realPay[i]));
					dto.setCreatingMethod(CommonKeys.FTCREATINGMETHOD_A);
					dto.setPrepaymentType(prePaymentType);
					lstPrePaymentDeduction.add(dto); 	 
		   	     }
		   	 }
		   	 
		   	 if (Double.parseDouble(pay_realPrepay[i])*1.0 !=0){
		   		PaymentRecordDTO dto=new PaymentRecordDTO();
		   	    dto.setCustID(customerID);
		   	   	dto.setAcctID(accountID);
		   	    dto.setMopID(mopid);
		   	    dto.setAmount(Double.parseDouble(pay_realPrepay[i]));
		        dto.setPayType(CommonKeys.PAYMENTRECORD_TYPE_PRESAVE);  
		   	    dto.setTicketType(ticketType);
		   	    dto.setTicketNo(pay_realaddInfo[i]);
		   	    dto.setPrepaymentType(prePaymentType);
		   	    lstPayment.add(dto);
		   	 }
		   	LogUtility.log(this.getClass(), LogLevel.DEBUG,"=============pay_realMOP["+i+"]======="+pay_realMOP[i]);
		   	LogUtility.log(this.getClass(), LogLevel.DEBUG,"=============pay_realPay["+i+"]========"+pay_realPay[i]);
		   	LogUtility.log(this.getClass(), LogLevel.DEBUG,"=============pay_realPrepay["+i+"]========"+pay_realPrepay[i]);
		  }
		}
		LogUtility.log(this.getClass(), LogLevel.DEBUG,"支付列表==============="+lstPayment);
		LogUtility.log(this.getClass(), LogLevel.DEBUG,"抵扣列表==============="+lstPrePaymentDeduction);
		LogUtility.log(this.getClass(), LogLevel.DEBUG,"*************accepet payAndPre page end********"); 
	}	
	
	public void doEnd(HttpServletRequest request, CommandResponse cmdResponse){
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "needCheckToken★★★★★★★★★"+needCheckToken());
		if (request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE) == null&&needCheckToken()){
		   if (request.getSession().getAttribute(CommonKeys.FEE_SESSION_NAME) != null) {
				LogUtility.log(this.getClass(), LogLevel.DEBUG, "开始session费用移除★★★★★★★★★",
						request.getSession().getAttribute(CommonKeys.FEE_SESSION_NAME));
		       request.getSession().removeAttribute(CommonKeys.FEE_SESSION_NAME);
	       }
		}
	    super.doEnd(request,cmdResponse);
    }
}
