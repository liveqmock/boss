package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.AccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.ImmediateCountFeeInfo;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;

import javax.servlet.http.HttpServletRequest;

/**
 *author :David.Yang
 */
public class CloseAccountWebAction extends PayFeeWebAction {
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
	protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        AccountDTO dto = new AccountDTO();
        CustServiceInteractionDTO csiDTO = new CustServiceInteractionDTO();
        if (WebUtil.StringHaveContent(request.getParameter("txtAcctID"))) {
            dto.setAccountID(WebUtil.StringToInt(request.getParameter("txtAcctID")));
            dto.setAccountName(request.getParameter("txtAccountName"));
            dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtAccountDtLastmod")));
            csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAcctID")));
        } else {
            throw new WebActionException("没有找到帐号编码!");
        }
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID"))) {
            dto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
            csiDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        } else {
            throw new WebActionException("没有找到用户编码!");
        }
        
        csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OC);
        csiDTO.setBillCollectionMethod(CommonKeys.CSI_PAYMENT_OPTION_IM);
        
        AddressDTO acctAddrDto =new AddressDTO();
        acctAddrDto.setAddressID(WebUtil.StringToInt(request.getParameter("txtAddressId")));
        acctAddrDto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtAddressDtLastmod")));
        AccountEJBEvent event = new AccountEJBEvent(AccountEJBEvent.CLOSECUSTOMERACCOUNT);
        boolean isReadyForeturnFee = false;
        if ("true".equalsIgnoreCase(request.getParameter("readyForeturnFeeFlag")))
        	isReadyForeturnFee = true;		
        if (confirmPost || isReadyForeturnFee){
           event.setCsiFeeList(getSessionFeeList(request));
           int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
           int acctID =WebUtil.StringToInt(request.getParameter("txtAcctID"));
           String prePaymentType =(isReadyForeturnFee) ? CommonKeys.PAYMENTRECORD_TYPE_ACCEPT_CASE 
        		                                       : CommonKeys.PAYMENTRECORD_TYPE_RETURN_RR;
           getPayList(request, customerID,acctID,
        		   event.getCsiPaymentList(),
        		   prePaymentType,
				   event.getCsiPrePaymentDeductionList());
        }
        
        event.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        event.setAccountDTO(dto);
        event.setCsiDto(csiDTO);
        event.setAcctAddrDto(acctAddrDto);
       	event.setDoPost(confirmPost);
       	event.setReadyForeturnFee(isReadyForeturnFee);
       	
        return event;
    }
	

	public void doEnd(HttpServletRequest request, CommandResponse cmdResponse) {
		super.doEnd(request, cmdResponse);
		if (confirmPost && 
				(request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE) == null)){
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
