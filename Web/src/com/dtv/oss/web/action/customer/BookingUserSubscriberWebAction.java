package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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


public class BookingUserSubscriberWebAction extends PayFeeWebAction {
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
		setProductInfo(request,ejbEvent);	
		setCSIInfo(request,ejbEvent);                                         
		ejbEvent.setForcedDeposit(WebUtil.StringTodouble(request.getParameter("txtForceDepostMoney")));
		int eJbActionType =0;
		switch (Integer.parseInt(txtActionType)){
			//用于预约增机的预约检查
			case CommonKeys.CHECK_BOOKINGUSER_PRODUCTPG_AND_INVOICE:
				setAccountInfo(request,ejbEvent);
				eJbActionType =ServiceAccountEJBEvent.CHECK_BOOKINGUSER_PRODUCTPG_CAMPAINPG ;
			    ejbEvent.setDoPost(confirmPost);
				break;
			//用于预约增机的产品和优惠检查
			case CommonKeys.CHECK_PRODUCTPG_AND_INVOICE :
				setAccountInfo(request,ejbEvent);
				eJbActionType =ServiceAccountEJBEvent.CHECK_PRODUCTPG_CAMPAINPG ;
				ejbEvent.setDoPost( confirmPost);
			    break;
			// 用于预约增机的硬件检查  
			case CommonKeys.CHECK_HARDPRODUCTINFO :
				setAccountInfo(request,ejbEvent);
				eJbActionType =ServiceAccountEJBEvent.CHECK_HARDPRODUCTINFO;
				setDeviceInfo(request,ejbEvent);
				setProductPropertyInfo(request,ejbEvent);
				ejbEvent.setDoPost( confirmPost);
			    break;
			// 用于预约增机的业务帐户创建
			case CommonKeys.OPEN_BOOKINGUSER_SERVICE_ACCOUNT :
				eJbActionType = ServiceAccountEJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER;
				setDeviceInfo(request,ejbEvent);
				setPayFeeInfo(request,ejbEvent);
			    setAccountInfo(request,ejbEvent);
				setProductPropertyInfo(request,ejbEvent);
				ejbEvent.setDoPost( confirmPost);
				//setNextOrgID(request,ejbEvent);
				break;
			//用于预约增机的预约提交
			case CommonKeys.OPEN_BOOKINGUSER_SERVICE_ACCOUNT_FORBOOKING:
				setAccountInfo(request,ejbEvent);
			    eJbActionType =ServiceAccountEJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER_FORBOOKING;
			//    setCreateJCInfo(request,ejbEvent);
				ejbEvent.setDoPost( confirmPost);
				setNextOrgID(request,ejbEvent);
			    break;
			//用于预约增机的预约修改
			case CommonKeys.OPEN_BOOKINGUSER_SERVICE_ACCOUNT_FORMODIFY:
				eJbActionType =ServiceAccountEJBEvent.CUSTOMER_BOOKINGUSER_UPDATE_SUBSCRIBER;
				ejbEvent.setDoPost( confirmPost);
			    break;
			//用于预约增机的预约取消  
			case CommonKeys.OPEN_BOOKINGUSER_SERVICE_ACCOUNT_FORCANCLE:
				eJbActionType =ServiceAccountEJBEvent.CUSTOMER_BOOKINGUSER_CANCEL_SUBSCRIBER;
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
			throw new WebActionException("请先做客户查询，定位客户！");
		}
	}

	
	private void setProductInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent)
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
			  for (int i = 0; i < aCampaignID.length; i++){
				  System.out.println("aCampaignID["+i+"]========"+aCampaignID[i]);
				  lstCampaignID.add(WebUtil.StringToInteger(aCampaignID[i]));
			  }
		   }
	   }
	   ejbEvent.setCsiPackageArray(lstProductID);
	   ejbEvent.setCsiCampaignArray(lstCampaignID);
	}

	private void setCSIInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent)
	{
		//受理单信息
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU);
		csiDTO.setInstallationType(CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_INSTALL);
		
		if (WebUtil.StringHaveContent(request.getParameter("txtID")))
        	csiDTO.setId(WebUtil.StringToInt(request.getParameter("txtID")));
        if (WebUtil.StringHaveContent(request.getParameter("txtPreferedTime")))
        	csiDTO.setPreferedTime(request.getParameter("txtPreferedTime") );
        if (WebUtil.StringHaveContent(request.getParameter("txtPreferedDate")))
        	csiDTO.setPreferedDate(WebUtil.StringToTimestamp(request.getParameter("txtPreferedDate")));
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	csiDTO.setCustomerID (WebUtil.StringToInt(request.getParameter("txtCustomerID") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
        	csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount") ));
		if (WebUtil.StringHaveContent(request.getParameter("txtContactPhone")))
            csiDTO.setContactPhone(request.getParameter("txtContactPhone"));
		if (WebUtil.StringHaveContent(request.getParameter("txtContactPerson")))
			csiDTO.setContactPerson(request.getParameter("txtContactPerson"));
        if (WebUtil.StringHaveContent(request.getParameter("txtCsiDtLastMod")))
        	csiDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtCsiDtLastMod")));
        if (WebUtil.StringHaveContent(request.getParameter("phoneNo")))
			csiDTO.setServiceCodeList(request.getParameter("phoneNo"));
        if (WebUtil.StringHaveContent(request.getParameter("txtcsiPayOption")))
            csiDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
        if (WebUtil.StringHaveContent(request.getParameter("txtCsiCreateReason")))
        {    
        	csiDTO.setCreateReason(request.getParameter("txtCsiCreateReason"));
        	//zyg 2007.11.22 需要同时设置 受理单的 statusreason 字段
        	csiDTO.setStatusReason(request.getParameter("txtCsiCreateReason"));
        }
       
        if("false".equals(request.getParameter("needPhoneNo")))
        	csiDTO.setServiceCodeList("");
		ejbEvent.setCsiDto( csiDTO);
	} 
	
	private void setDeviceInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent) throws WebActionException
	{
//		硬件设备信息
		HashMap hardFixingMp =new HashMap();
		String  deviceClass =(request.getParameter("DeviceClassList")==null) ? "" : request.getParameter("DeviceClassList");
		String[] deviceClassList =deviceClass.split(";");
		String  terminalDevice =(request.getParameter("TerminalDeviceList")==null) ? "" :request.getParameter("TerminalDeviceList");
		String[] terminalDeviceList =terminalDevice.split(";");
		
		if (deviceClassList.length !=terminalDeviceList.length){
			 throw new WebActionException("硬件设备和硬件值无法匹配！");
		} else {
			if(!("".equals(deviceClass))){
				for (int i=0; i< deviceClassList.length;i++) {
					hardFixingMp.put(terminalDeviceList[i],deviceClassList[i]);
				}
			}
		}
		ejbEvent.setDeviceTable(hardFixingMp);
		
	    //虚拟电话号码
		ejbEvent.setPhoneNo(request.getParameter("phoneNo"));
		ejbEvent.setItemID(WebUtil.StringToInt(request.getParameter("itemID")));
		
		//预约时选择的资源(电话号码)信息 老的电话号码
		ejbEvent.setServiceCodeList(request.getParameter("txtServiceCodeList"));


	}
	private void setPayFeeInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent) throws WebActionException
	{
		int customerID = WebUtil.StringToInt(request.getParameter("txtCustomerID"));
		int accountID = WebUtil.StringToInt(request.getParameter("txtAccount"));
		ejbEvent.setFeeList (getSessionFeeList(request)) ;
		getPayAndPrePayList(request,customerID,accountID,ejbEvent.getPaymentList(),ejbEvent.getCsiPrePaymentDeductionList());
	}
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
						//2008-03-25 hjp 注释此if语句。产品属性的"取值方式"为固定时，也要维护客户产品属性表(t_cpconfigedproperty)表记录
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
	
	private void setAccountInfo(HttpServletRequest request, ServiceAccountEJBEvent ejbEvent) throws WebActionException
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
//			  if ((cmdImp.getPayload() !=null) && !(cmdImp.getPayload() instanceof Collection)){
//			       request.setAttribute(WebKeys.REQUEST_ATTRIBUTE, obj);
//			  }
		  }
	    }
	}
}