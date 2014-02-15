package com.dtv.oss.web.action.customer;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerProductEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.action.GeneralWebAction;

/**
 * 暂停/恢复客户产品，提交
 * @author Stone Liang
 * 2005/11/30
 */

public class CustomerProductStopAndResumeWebAction extends GeneralWebAction{
	protected boolean needCheckToken() {
		return false;
	}
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
		String txtActionType =request.getParameter("txtActionType");
		CustomerProductEJBEvent ejbEvent =new CustomerProductEJBEvent();
		ejbEvent.setDoPost( true);
		setCustomerInfo(request,ejbEvent);
		setProductInfo(request,ejbEvent);	
		setCSIInfo(request,ejbEvent);
		setSAInfo(request,ejbEvent);
		int eJbActionType =0;
		switch (Integer.parseInt(txtActionType)){
			case CommonKeys.CUSTOMER_PRODUCT_PAUSE :
				eJbActionType =CustomerProductEJBEvent.SUSPEND ;
				setAccountInfo(request,ejbEvent);	
				setFeeInfo(request,ejbEvent);
			    break;
			case CommonKeys.CUSTOMER_PRODUCT_RESUME :
				eJbActionType =CustomerProductEJBEvent.RESUME;
			    break;

		    default :
		    	break;
		}
		ejbEvent.setActionType(eJbActionType );
		return ejbEvent;
    }
	private void setSAInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent) throws WebActionException
	{
		String saID = request.getParameter("txtSAID");
		if (WebUtil.StringHaveContent(saID)) {
			ejbEvent.setSaID(WebUtil.StringToInt(saID));
		}
		else
		{
			throw new WebActionException("业务帐户信息缺失！");
		}
	}
	   
	private void setCustomerInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent) throws WebActionException
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
		
	private void setAccountInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent) throws WebActionException
	{
		String account = request.getParameter("txtAccount");
		if (WebUtil.StringHaveContent(account)) {
			ejbEvent.setAccountID(WebUtil.StringToInt(account));			
		}
		else
		{
			throw new WebActionException("请指定一个有效帐户！");
		}
	}
	
	private void setProductInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent)
	{
		//客户暂停/恢复的产品ID信息
		ArrayList lstProductID = new ArrayList();

	   if (WebUtil.StringHaveContent(request.getParameter("txtCPIDs"))) {
		  String[] aProductID = request.getParameter("txtCPIDs").split(";");
		  if ((aProductID != null) && (aProductID.length > 0)) {
			for (int i = 0; i < aProductID.length; i++)
				lstProductID.add(WebUtil.StringToInteger(aProductID[i]));
		  }
	   }

	   ejbEvent.setColPsid(lstProductID);

	}
	private void setFeeInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent) throws WebActionException
	{
		//费用信息，可以考虑从后台直接获取，不从前台传递过去了
		ArrayList lstFee = new ArrayList();  
	    ArrayList lstPayment = new ArrayList();

		String[] realMOP =request.getParameterValues("realMOP");
		String[] realaddInfo =request.getParameterValues("realaddInfo");	
		String[] realPay =request.getParameterValues("realpay");
		String[] realPrepay =request.getParameterValues("realprepay");

		if (realMOP !=null){
		   for(int i=0 ; i<realMOP.length ; i++){
		   	 PaymentRecordDTO realPayDto = new PaymentRecordDTO();
		   	 PaymentRecordDTO prePayDto = new PaymentRecordDTO();
		   	 //AccountItemDTO feeDto = new AccountItemDTO();
		   	 
			/*if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID"))) {
				feeDto.setCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
			}
			if (WebUtil.StringHaveContent(request.getParameter("txtAccount"))) {
				feeDto.setAcctID (WebUtil.StringToInt(request.getParameter("txtAccount")));
			}*/
		   	 
		   	realPayDto.setMopID(Integer.parseInt(realMOP[i]));
		   	realPayDto.setAmount(Double.parseDouble(realPay[i])-Double.parseDouble(realPrepay[i]));
		   	lstPayment.add(realPayDto);
		   	
		   	prePayDto.setMopID(Integer.parseInt(realMOP[i]));
		   	prePayDto.setAmount(Double.parseDouble(realPrepay[i]));
		   	lstPayment.add(prePayDto);
		   	 //lstFee.add(feeDto);
		  }
		}
	    //ejbEvent.setFeeList( lstFee);
	    ejbEvent.setCsiPaymentList( lstPayment);
	}
	
	private void setCSIInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent)
	{
		//受理单信息
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		
		String txtActionType =request.getParameter("txtActionType");
		if(Integer.parseInt(txtActionType) == CommonKeys.CUSTOMER_PRODUCT_PAUSE)
		{
	        if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
	        	csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount") ));
	        csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PS);
		}
		else if(Integer.parseInt(txtActionType) == CommonKeys.CUSTOMER_PRODUCT_RESUME)
		{
			csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_RM);
		}
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	csiDTO.setCustomerID (WebUtil.StringToInt(request.getParameter("txtCustomerID") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtEventReason")))
        	csiDTO.setStatusReason( request.getParameter("txtEventReason") );
		
		ejbEvent.setCsiDto( csiDTO);
	}
	
}