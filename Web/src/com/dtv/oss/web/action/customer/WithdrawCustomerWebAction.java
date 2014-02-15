 
package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.ImmediateCountFeeInfo;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p>Title: BOSS 客户退户</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Digivision</p>
 *
 * @author Nile chen
 * @version 1.0
 */
public class WithdrawCustomerWebAction  extends PayFeeWebAction{

	boolean doPost = false;
	protected boolean needCheckToken(){
		return doPost;
	}

    public void doStart(HttpServletRequest request){
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post")))
		{
			if (request.getParameter("confirm_post").equalsIgnoreCase("true")) 
			{
				doPost = true;
			}
		}
	 }
    
	 protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException{
        CustomerEJBEvent ejbEvent = new CustomerEJBEvent(EJBEvent.WITHDRAW);
      
        ejbEvent.setDoPost(doPost);
		setCustomerInfo(request,ejbEvent);
		setAccountInfo(request,ejbEvent);
		setCSIInfo(request,ejbEvent);
		boolean isReadyForeturnFee = false;
        if ("true".equalsIgnoreCase(request.getParameter("readyForeturnFeeFlag")))
        	isReadyForeturnFee = true;		
		if(doPost || isReadyForeturnFee){
			String prePaymentType =(isReadyForeturnFee) ? CommonKeys.PAYMENTRECORD_TYPE_ACCEPT_CASE 
	                 : CommonKeys.PAYMENTRECORD_TYPE_RETURN_RR;
			setFeeInfo(request,ejbEvent,prePaymentType);
		}
		else{
			setDeviceFee(request,ejbEvent);
		}
		ejbEvent.setReadyForeturnFee(isReadyForeturnFee);
        return ejbEvent;
		//return null;

	}
	private void setCustomerInfo(HttpServletRequest request, CustomerEJBEvent ejbEvent) throws WebActionException{
		String customer = request.getParameter("txtCustomerID");
		if (WebUtil.StringHaveContent(customer)) {
			CustomerDTO custDTO = new CustomerDTO();
			custDTO.setCustomerID (WebUtil.StringToInt(customer) );
			ejbEvent.setCustomerDTO ( custDTO);
		}
		else
		{
			throw new WebActionException("请先做客户查询，定位客户！");
		}
	}
	
	private void setAccountInfo(HttpServletRequest request, CustomerEJBEvent ejbEvent) throws WebActionException{
		String account = request.getParameter("txtAccount");
		if (WebUtil.StringHaveContent(account)) {
			AccountDTO accountDTO = new AccountDTO();
			accountDTO.setAccountID(WebUtil.StringToInt(account) );
			ejbEvent.setAccountDTO( accountDTO);
		}
		else
		{
			throw new WebActionException("请指定一个有效帐户！");
		}
	}
	
	private void setDeviceFee(HttpServletRequest request, CustomerEJBEvent ejbEvent) throws WebActionException{
			ArrayList lstFee = new ArrayList();
			double realDeviceFee = WebUtil.StringTodouble(request.getParameter("txtRealDeviceFee"));
			if (realDeviceFee !=0){
			   AccountItemDTO dto=new AccountItemDTO();
			   dto.setCustID( WebUtil.StringToInt(request.getParameter("txtCustomerID")));
	    	   dto.setAcctID ( WebUtil.StringToInt(request.getParameter("txtAccount")));
	    	   dto.setAcctItemTypeID(Postern.getAcctItemTypeIDyFieldName("ReturnDeviceAcctItemTypeID"));//设备费
	    	   dto.setValue(realDeviceFee);
	    	   dto.setForcedDepositFlag( CommonKeys.FORCEDDEPOSITFLAG_N);//不是强制预存
	    	   dto.setReferType(CommonKeys.AIREFERTYPE_M );
	    	   lstFee.add(dto);
			}	
			ejbEvent.setCsiFeeList(lstFee) ;
	}
	private void setFeeInfo(HttpServletRequest request, CustomerEJBEvent ejbEvent,String prePaymentType) 
	   throws WebActionException {
		  ejbEvent.setCsiFeeList(getSessionFeeList(request));
		  int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
		  int accountID =WebUtil.StringToInt(request.getParameter("txtAccount"));
	  	  getPayList(request,customerID,accountID,
				    ejbEvent.getCsiPaymentList(),
				    prePaymentType,
				    ejbEvent.getCsiPrePaymentDeductionList());		
	}
	
	private void setCSIInfo(HttpServletRequest request, CustomerEJBEvent ejbEvent){
		//受理单信息
		CustServiceInteractionDTO csiDTO = new CustServiceInteractionDTO();
		csiDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount")));
		csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_WC);
		csiDTO.setBillCollectionMethod(CommonKeys.CSI_PAYMENT_OPTION_IM);
		csiDTO.setComments(request.getParameter("txtComments"));
		ejbEvent.setCsiDto( csiDTO);
	}	

	public void doEnd(HttpServletRequest request, CommandResponse cmdResponse) {
		super.doEnd(request, cmdResponse);
		if (doPost && 
				(request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE) == null)){
			 request.getSession().removeAttribute(CommonKeys.CURRENT_CUSTOMER_SESSION_NAME);
			 request.getSession().removeAttribute(WebKeys.SIDERBARMENUID);		  
		     request.getSession().removeAttribute(WebKeys.SIDERBARTDID);
		}
		
		Object obj = request.getAttribute("ResponseFromEJBEvent");
		if (obj != null) {
			CommandResponseImp cmdImp =(CommandResponseImp)obj;
		    Object feeCols =cmdImp.getPayload();
			if ((feeCols instanceof Collection) && !((Collection)feeCols).isEmpty()){
		       Iterator   feeIter =((Collection)feeCols).iterator();
		       if (feeIter.hasNext()){
		          Object feeobj =feeIter.next();
		          if (feeobj instanceof ImmediateCountFeeInfo){
		             ImmediateCountFeeInfo immeCountFeeInfo =(ImmediateCountFeeInfo)feeobj;
		             double acctTotal =immeCountFeeInfo.getPreCashDoposit() +immeCountFeeInfo.getPreTokenDoposit();
		             double newPrepayTotal =0 ;
		             Collection newPrepayRecordList =(immeCountFeeInfo.getNewPrepayRecordList()==null) ? new ArrayList() : immeCountFeeInfo.getNewPrepayRecordList();
		             Iterator  newPrepayRecordIter =newPrepayRecordList.iterator();
		             while (newPrepayRecordIter.hasNext()){
		                PrepaymentDeductionRecordDTO dto =(PrepaymentDeductionRecordDTO)newPrepayRecordIter.next();
		                newPrepayTotal =newPrepayTotal +dto.getAmount();
		             }
		             //if (acctTotal ==newPrepayTotal){
		             if (Math.abs(acctTotal - newPrepayTotal)<0.0001){
		                request.setAttribute(WebKeys.REQUEST_ATTRIBUTE, obj);
		             }
		         }
		     }
	      }	
	   }
	}

}
