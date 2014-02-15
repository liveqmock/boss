package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerProductEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;

public class BatchAddPackageWebAction extends PayFeeWebAction {
    boolean confirmPost = false;
	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("txtDoPost"))) {
			if (request.getParameter("txtDoPost").equalsIgnoreCase("TRUE"))
				confirmPost = true;
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		String txtActionType =request.getParameter("txtActionType");
		CustomerProductEJBEvent ejbEvent =new CustomerProductEJBEvent();
		setCustomerInfo(request,ejbEvent);
		setAccountInfo(request,ejbEvent);	
		setProductInfo(request,ejbEvent);	
		ejbEvent.setActionType(CustomerProductEJBEvent.BATCHADD_PRODUCTPACKAGE);
		setServiceAccountInfo(request,ejbEvent);
		setCSIInfo(request,ejbEvent);
		setUsedMonth(request,ejbEvent);
		ejbEvent.setDoPost(confirmPost);
		if(confirmPost){	
		   setPayFeeInfo(request,ejbEvent);
		}
		return ejbEvent;
		
	}
	
	private void setCustomerInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent) throws WebActionException{
		String customer = request.getParameter("txtCustomerID");
		if (WebUtil.StringHaveContent(customer)) {
			ejbEvent.setCustomerID (WebUtil.StringToInt(customer));
		}
		else
		{
			throw new WebActionException("请先做客户查询，定位客户！");
		}
	}
	
	private void setAccountInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent) throws WebActionException{
		String account = request.getParameter("txtAccount");
		if (WebUtil.StringHaveContent(account)) {
			ejbEvent.setAccountID(WebUtil.StringToInt(account));			
		}
		else
		{
			throw new WebActionException("请指定一个有效帐户！");
		}
	}
	
	private void setPayFeeInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent) throws WebActionException
	{
        //	费用与支付
		int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
		int accountID = WebUtil.StringToInt(request.getParameter("txtAccount"));
	    
		ejbEvent.setCsiFeeList(getSessionFeeList(request)) ;
		getPayAndPrePayList(request,customerID,accountID,ejbEvent.getCsiPaymentList(),ejbEvent.getCsiPrePaymentDeductionList());	
	}
	
	private void setProductInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent)
	{
		//客户购买的产品包信息
		ArrayList lstProductID = new ArrayList();
		//客户选择的优惠信息
		ArrayList lstCampaignID = new ArrayList();

	   if (WebUtil.StringHaveContent(request.getParameter("ProductList"))) {
		  String[] aProductID = request.getParameter("ProductList").split(";");
		  if ((aProductID != null) && (aProductID.length > 0)) {
			for (int i = 0; i < aProductID.length; i++)
				lstProductID.add(WebUtil.StringToInteger(aProductID[i]));
		  }
	   }
	  
	   if (WebUtil.StringHaveContent(request.getParameter("CampaignList"))) {
		   String[] aCampaignID = request.getParameter("CampaignList").split(";");
		   if ((aCampaignID != null) && (aCampaignID.length > 0)) {
			  for (int i = 0; i < aCampaignID.length; i++)
				  lstCampaignID.add(WebUtil.StringToInteger(aCampaignID[i]));
		   }
	   }
	   ejbEvent.setCsiPackageArray(lstProductID);
	   ejbEvent.setCsiCampaignArray(lstCampaignID);
	   
	}
	
	private void setCSIInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent)
	{
		//受理单信息
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN);
    
        if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
        	csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	csiDTO.setCustomerID (WebUtil.StringToInt(request.getParameter("txtCustomerID") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtUsedMonth"))){
			csiDTO.setPoint(WebUtil.StringToInt(request.getParameter("txtUsedMonth")));
		}
  
        csiDTO.setBillCollectionMethod("IM");
        csiDTO.setComments("批量增加客户产品包。");
        
		ejbEvent.setCsiDto( csiDTO);
	}
	
	private void setServiceAccountInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent)
	{
		String[] saIds =request.getParameterValues("saId_indexs");
		Collection saCols =new ArrayList();
		for (int i=0;i<saIds.length;i++){
			saCols.add(saIds[i]);
		}
		ejbEvent.setColServiceAccountInfo(saCols);
	}

	private void setUsedMonth(HttpServletRequest request, CustomerProductEJBEvent ejbEvent) throws WebActionException
	{
		String txtUsedMonth =request.getParameter("txtUsedMonth");
		ejbEvent.setUsedMonth(WebUtil.StringToInt(txtUsedMonth));
	}
}
