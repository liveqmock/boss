package com.dtv.oss.web.action.customer;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.AccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;

public class PrepayWebAction extends PayFeeWebAction {
	boolean confirmPost = false;
	protected boolean needCheckToken()
	{
		return confirmPost;
	}
	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("txtDoPost"))) {
			if(request.getParameter("txtDoPost").equalsIgnoreCase( "TRUE")){
				confirmPost = true;
			}
		}
	}	
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		AccountEJBEvent ejbEvent = new AccountEJBEvent(EJBEvent.PRESAVE);
    	ejbEvent.setDoPost (confirmPost);
    	setCustomerInfo(request,ejbEvent);
		setAccountInfo(request,ejbEvent);
		setCSIInfo(request,ejbEvent);
		setFeeInfo(request,ejbEvent);

	
		return ejbEvent;
	}
	private void setCustomerInfo(HttpServletRequest request, AccountEJBEvent ejbEvent) throws WebActionException
	{
		String customer = request.getParameter("txtCustomerID");
		if (WebUtil.StringHaveContent(customer)) {
			ejbEvent.setCustomerID( WebUtil.StringToInt(customer));
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
			AccountDTO accountDTO = new AccountDTO();
			accountDTO.setAccountID(WebUtil.StringToInt(account) );
			ejbEvent.setAccountDTO( accountDTO);
		}
		else
		{
			throw new WebActionException("请指定一个有效帐户！");
		}
	}
	private void setFeeInfo(HttpServletRequest request, AccountEJBEvent ejbEvent) 
	   throws WebActionException{		
		  getPayList(request, WebUtil.StringToInt(request.getParameter("txtCustomerID"))
				            , WebUtil.StringToInt(request.getParameter("txtAccountID"))
				            , ejbEvent.getCsiPaymentList()
				            , CommonKeys.PAYMENTRECORD_TYPE_PRESAVE
				            , null);
	}
	
	private void setCSIInfo(HttpServletRequest request, AccountEJBEvent ejbEvent)
	{
		//受理单信息
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		csiDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
		csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_AD);
		csiDTO.setBillCollectionMethod(CommonKeys.CSI_PAYMENT_OPTION_IM);
		ejbEvent.setCsiDto( csiDTO);
	}	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
