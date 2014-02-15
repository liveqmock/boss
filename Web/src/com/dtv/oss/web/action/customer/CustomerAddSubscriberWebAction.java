package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.dto.custom.CustCustomerBillingRuleDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;


public class CustomerAddSubscriberWebAction extends PayFeeWebAction {
    boolean confirmPost = false;
    String txtActionType ;

	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
				confirmPost = true;
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
    
		txtActionType=request.getParameter("txtActionType");
		if (txtActionType ==null){
			return null;
		}
		ServiceAccountEJBEvent ejbEvent =new ServiceAccountEJBEvent();
		setCustomerInfo(request,ejbEvent);
		setAccountInfo(request,ejbEvent);	
		setProductInfo(request,ejbEvent);	
		setCSIInfo(request,ejbEvent);
		ejbEvent.setForcedDeposit(WebUtil.StringTodouble(request.getParameter("txtForceDepostMoney")));
		int eJbActionType =0;
		switch (Integer.parseInt(txtActionType)){
			case CommonKeys.CHECK_PRODUCTPG_AND_INVOICE :
				eJbActionType =ServiceAccountEJBEvent.CHECK_PRODUCTPG_CAMPAINPG ;
				ejbEvent.setDoPost( confirmPost);
			    break;			    
			case CommonKeys.CHECK_HARDPRODUCTINFO :
				eJbActionType =ServiceAccountEJBEvent.CHECK_HARDPRODUCTINFO;
				setDeviceInfo(request,ejbEvent);
			//	setCreateJCInfo(request,ejbEvent);
				setProductPropertyInfo(request,ejbEvent);
				ejbEvent.setDoPost( confirmPost);
			    break;
			   
			//�����ŵ��������ύ
			case CommonKeys.OPEN_SERVICE_ACCOUNT :
				eJbActionType =ServiceAccountEJBEvent.CUSTOMER_ADD_SUBSCRIBER ;
				setDeviceInfo(request,ejbEvent);
				setPayFeeInfo(request,ejbEvent);
			//	setCreateJCInfo(request,ejbEvent);
				setProductPropertyInfo(request,ejbEvent);
				setNextOrgID(request,ejbEvent);
				ejbEvent.setDoPost( confirmPost);
			    break;
		    default :
		    	break;
		}
		/*********************add by yangchen 2008/07/23 start***************************************************/
		if(confirmPost){
			if("Y".equals(request.getParameter("txtStartAgreement"))){
				String  agreementProduct=(request.getParameter("txtAgreementProductList")==null) ? "" : request.getParameter("txtAgreementProductList");
				String[] agreementProductList =agreementProduct.split(",");
				String  agreementPackage=(request.getParameter("txtAgreementPackageList")==null) ? "" : request.getParameter("txtAgreementPackageList");
				String[] agreementPackageList =agreementPackage.split(",");
				String  agreementPrice=(request.getParameter("txtAgreementPriceList")==null) ? "" : request.getParameter("txtAgreementPriceList");
				String[] agreementPriceList =agreementPrice.split(",");
				String  agreementAcctType=(request.getParameter("txtAgreementAcctTypeList")==null) ? "" : request.getParameter("txtAgreementAcctTypeList");
				String[] agreementAcctTypeList =agreementAcctType.split(",");
				String  agreementStartDate=(request.getParameter("txtAgreementStartDateList")==null) ? "" : request.getParameter("txtAgreementStartDateList");
				String[] agreementStartDateList =agreementStartDate.split(",");
				String  agreementEndDate=(request.getParameter("txtAgreementEndDateList")==null) ? "" : request.getParameter("txtAgreementEndDateList");
				String[] agreementEndDateList =agreementEndDate.split(",");
				
				if(agreementProductList!=null && agreementProductList.length>0){
					Map customerBillingRuleMap=new HashMap();
					for(int i=0;i<agreementProductList.length;i++){
						CustCustomerBillingRuleDTO billRuleDTO=new CustCustomerBillingRuleDTO();
						billRuleDTO.setFeeType(CommonKeys.BRFEETYPE_INFORMATION);
						billRuleDTO.setAcctItemTypeID(agreementAcctTypeList[i]);
						billRuleDTO.setStartDate(WebUtil.StringToTimestamp(agreementStartDateList[i]));
						billRuleDTO.setEndDate(WebUtil.StringToTimestamp(agreementEndDateList[i]));
						billRuleDTO.setProductID(Integer.parseInt(agreementProductList[i]));
						billRuleDTO.setPackageID(Integer.parseInt(agreementPackageList[i]));
						billRuleDTO.setValue(Double.parseDouble(agreementPriceList[i]));
						customerBillingRuleMap.put(new Integer(billRuleDTO.getProductID()), billRuleDTO);
					}
					ejbEvent.setCustomerBillingRuleMap(customerBillingRuleMap);
				}
			}
		}
		/*********************add by yangchen 2008/07/23 end***************************************************/
		ejbEvent.setActionType(eJbActionType );
		return ejbEvent;

	}
	
	private void setCustomerInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent) throws WebActionException
	{
		String customer = request.getParameter("txtCustomerID");
		if (WebUtil.StringHaveContent(customer)) {
			CustomerDTO custDTO = new CustomerDTO();
			custDTO.setCustomerID (WebUtil.StringToInt(customer) );
			ejbEvent.setCustomerDTO ( custDTO);
			ejbEvent.setCustomerID (WebUtil.StringToInt(customer));
		}
		else
		{
			throw new WebActionException("�������ͻ���ѯ����λ�ͻ���");
		}
	}
		
	private void setAccountInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent) throws WebActionException
	{
		String account = request.getParameter("txtAccount");
		if (WebUtil.StringHaveContent(account)) {
			AccountDTO accountDTO = new AccountDTO();
			accountDTO.setAccountID(WebUtil.StringToInt(account) );
			ejbEvent.setAccountDTO( accountDTO);
			ejbEvent.setAccountID(WebUtil.StringToInt(account));			
		}
		else
		{
			throw new WebActionException("��ָ��һ����Ч�ʻ���");
		}
	}
	
	private void setProductInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent)
	{
		//�ͻ�����Ĳ�Ʒ����Ϣ
		ArrayList lstProductID = new ArrayList();
		//�ͻ�ѡ����Ż���Ϣ
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
	
	private void setDeviceInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent) throws WebActionException
	{
//		Ӳ���豸��Ϣ
		HashMap hardFixingMp =new HashMap();
		String  deviceClass =(request.getParameter("DeviceClassList")==null) ? "" : request.getParameter("DeviceClassList");
		String[] deviceClassList =deviceClass.split(";");
		String  terminalDevice =(request.getParameter("TerminalDeviceList")==null) ? "" :request.getParameter("TerminalDeviceList");
		String[] terminalDeviceList =terminalDevice.split(";");
		
		if (deviceClassList.length !=terminalDeviceList.length){
			 throw new WebActionException("Ӳ���豸��Ӳ��ֵ�޷�ƥ�䣡");
		} else {
			if(!("".equals(deviceClass))){
				for (int i=0; i< deviceClassList.length;i++) {
					hardFixingMp.put(terminalDeviceList[i],deviceClassList[i]);
				}
			}
		}
		ejbEvent.setDeviceTable(hardFixingMp);
		
	    //����绰����
		ejbEvent.setPhoneNo(request.getParameter("phoneNo"));
		ejbEvent.setItemID(WebUtil.StringToInt(request.getParameter("itemID")));

	}
	
	private void setPayFeeInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent) throws WebActionException
	{
		int customerID = WebUtil.StringToInt(request.getParameter("txtCustomerID"));
		int accountID = WebUtil.StringToInt(request.getParameter("txtAccount"));
		ejbEvent.setFeeList (getSessionFeeList(request)) ;
		getPayAndPrePayList(request,customerID,accountID,ejbEvent.getPaymentList(),ejbEvent.getCsiPrePaymentDeductionList());
	}
	
	private void setCSIInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent)
	{
		//������Ϣ
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();      
		csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO); 
    	if (WebUtil.StringHaveContent(request.getParameter("txtIsInstall")))
         	csiDTO.setInstallationType(request.getParameter("txtIsInstall") );
        if (WebUtil.StringHaveContent(request.getParameter("txtPreferedTime")))
        	csiDTO.setPreferedTime(request.getParameter("txtPreferedTime") );
        if (WebUtil.StringHaveContent(request.getParameter("txtPreferedDate")))
        	csiDTO.setPreferedDate(WebUtil.StringToTimestamp(request.getParameter("txtPreferedDate")));
        if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
        	csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	csiDTO.setCustomerID (WebUtil.StringToInt(request.getParameter("txtCustomerID") ));
		if (WebUtil.StringHaveContent(request.getParameter("txtContactPhone")))
            csiDTO.setContactPhone(request.getParameter("txtContactPhone"));
		if (WebUtil.StringHaveContent(request.getParameter("txtContactPerson")))
			csiDTO.setContactPerson(request.getParameter("txtContactPerson"));
		if (WebUtil.StringHaveContent(request.getParameter("txtComments")))
			csiDTO.setComments(request.getParameter("txtComments"));
		if (WebUtil.StringHaveContent(request.getParameter("txtcsiPayOption")))
		    csiDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
		if (WebUtil.StringHaveContent(request.getParameter("txtCsiCreateReason")))
		{
			  csiDTO.setCreateReason(request.getParameter("txtCsiCreateReason"));
			  //zyg 2007.11.22 ��Ҫͬʱ���� ������ statusreason �ֶ�
			  csiDTO.setStatusReason(request.getParameter("txtCsiCreateReason"));
		}
		
		ejbEvent.setCsiDto( csiDTO);
	}
	
	/*
	private void setCreateJCInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent)
	{
		String selfInstall = request.getParameter("txtIsInstall");
		if (WebUtil.StringHaveContent(selfInstall)) {
			if(selfInstall.equals( CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_INSTALL ))//���Ű�װ
			{
				ejbEvent.setCreateJobCard(true);
			}
		}
		
	}
	*/
//��Ʒ������Ϣ	
	private void setProductPropertyInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent)
	{
		Map productPropertyMap = new HashMap();
		Map tempMap = new HashMap();
		
		String[] productProperty =request.getParameterValues("txtProductProperty");
		
		if (productProperty != null) {
			for (int j = 0; j < productProperty.length; j++) {
				if (!productProperty[j].equals("")) {
					String[] productPropertys = productProperty[j].split(";");
					for (int i = 0; i < productPropertys.length; i++) {
						String[] strIDs = productPropertys[i].split("_");
						String productID = strIDs[0];
						String propertyCode = strIDs[1];

						ProductPropertyDTO ppDTO = Postern
								.getProductPropertyDTObyPrimaryKey(productID,
										propertyCode);
						//2008-03-25 hjp ע�ʹ�if��䡣��Ʒ���Ե�"ȡֵ��ʽ"Ϊ�̶�ʱ��ҲҪά���ͻ���Ʒ���Ա�(t_cpconfigedproperty)���¼
						//if (!CommonKeys.PRODUCT_PROPERTY_MODE_FIXED.equals(ppDTO.getPropertyMode())) {

							String productPropertyHiddenName = "txtProductProperty"
									+ "_" + productPropertys[i];
							String productPropertyValue = (request
									.getParameter(productPropertyHiddenName) == null) ? ""
									: request
											.getParameter(productPropertyHiddenName);

							if (productPropertyMap.containsKey(productID))
								tempMap = (Map) productPropertyMap
										.get(productID);
							else
								tempMap = new HashMap();
							tempMap.put(propertyCode, productPropertyValue);
							productPropertyMap.put(productID, tempMap);

						//}
					}
				}
			}
		}		
		ejbEvent.setProductPropertyMap(productPropertyMap);
	}
	
	private void setNextOrgID(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent) throws WebActionException
		{
			if (WebUtil.StringHaveContent(request.getParameter("transferType"))){
				if(("auto").equalsIgnoreCase(request.getParameter("transferType"))){
					System.out.println("******auto*******"+request.getParameter("txtAutoNextOrgID")+"*********");
					ejbEvent.setNextOrgID(WebUtil.StringToInt(request.getParameter("txtAutoNextOrgID")));
				}else if(("manual").equalsIgnoreCase(request.getParameter("transferType"))){
					ejbEvent.setNextOrgID(WebUtil.StringToInt(request.getParameter("txtNextOrgID")));
				}
			}
		}
	public void doEnd(HttpServletRequest request, CommandResponse cmdResponse) {
		super.doEnd(request, cmdResponse);
		String txtActionType =(request.getParameter("txtActionType") ==null) ? "" :request.getParameter("txtActionType");
		if ((!txtActionType.equals(""))
				&& (Integer.parseInt(txtActionType) ==CommonKeys.CHECK_PRODUCTPG_AND_INVOICE)){
		   Object obj = request.getAttribute("ResponseFromEJBEvent");
		   if (obj != null) {
			  System.out.println("obj.getClass().getName()=========="+obj.getClass().getName());
			  CommandResponseImp cmdImp =(CommandResponseImp)obj;
			  System.out.println("cmdImp.getPayload()==============="+cmdImp.getPayload());
			  if(CommonKeys.COMMAND_ID_IMMEDIATEFEE.equals(cmdImp.getFlowHandler()))
				  request.setAttribute(WebKeys.REQUEST_ATTRIBUTE, obj);
		  }
	    }
	}
}