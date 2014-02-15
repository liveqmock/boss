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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CustCustomerBillingRuleDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerProductEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebOperationUtil;
import com.dtv.oss.web.util.WebUtil;


public class CustomerProductCreateWebAction extends PayFeeWebAction {
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
		String saID = request.getParameter("txtServiceAccountID");
		if (WebUtil.StringHaveContent(saID)) {
			ejbEvent.setSaID (WebUtil.StringToInt(saID));
		}
		setCustomerInfo(request,ejbEvent);
		setAccountInfo(request,ejbEvent);	
		setProductInfo(request,ejbEvent);	
		setCSIInfo(request,ejbEvent);
		setHardFixingMp(request,ejbEvent);
		setUsedMonth(request,ejbEvent);
		int eJbActionType =0;
		switch (Integer.parseInt(txtActionType)){
			case CommonKeys.CHECK_PRODUCTPG_AND_INVOICE :
				eJbActionType =CustomerProductEJBEvent.CHECK_PRODUCTPG_CAMPAINPG ;
				ejbEvent.setDoPost( confirmPost);
			    break;
			case CommonKeys.CHECK_HARDPRODUCTINFO :
				eJbActionType =CustomerProductEJBEvent.CHECK_HARDPRODUCTINFO ;
				//封装产品属性
				setProductProperty(request,ejbEvent);
				ejbEvent.setDoPost( confirmPost);
			    break; 
			case CommonKeys.ADD_PRODUCT :
				eJbActionType =CustomerProductEJBEvent.PURCHASE ;
				setPayFeeInfo(request,ejbEvent);
				//封装产品属性
				setProductProperty(request,ejbEvent);
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
        //return null;

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
	private void setProductProperty(HttpServletRequest request, CustomerProductEJBEvent ejbEvent) throws WebActionException{
		String propertyTagName="productProperty";
		String strPropertyNameList="";

		LogUtility.log(this.getClass(),LogLevel.DEBUG,"strPropertyNameList:"+request.getParameter(propertyTagName));
		String []arrPropertyCodes=request.getParameterValues(propertyTagName);
		if(arrPropertyCodes!=null&&arrPropertyCodes.length>0){
			for(int i=0;i<arrPropertyCodes.length;i++){
				strPropertyNameList+=arrPropertyCodes[i];
			}
		}else{
			strPropertyNameList = request.getParameter(propertyTagName);
		}
//		LogUtility.log(this.getClass(),LogLevel.DEBUG,"strPropertyNameList:"+strPropertyNameList);
		
		if (WebUtil.StringHaveContent(strPropertyNameList)) {
			//arrPropertyName是全部的CpConfigProperty控件名称数组(productID_propertyCode)
			String []arrPropertyName=strPropertyNameList.split(";");
//			LogUtility.log(this.getClass(),LogLevel.DEBUG,"从propertyTag得到的控件数:"+arrPropertyName.length);
			for(int i=0; i<arrPropertyName.length;i++){
				String codes=arrPropertyName[i];
				String propertyName=propertyTagName+"_"+codes;
				String productID=codes.split("_")[0];
				String propertyCode=codes.split("_")[1];
				String propertyValue=request.getParameter(propertyName);
//				LogUtility.log(this.getClass(),LogLevel.DEBUG,"从页面request封装得到的DTO:"+dto);
				Map productMap=ejbEvent.getProductPropertyMap();
				Map productPropertyMap=null;
				//Integer key=Integer.valueOf(productID);
				if(productMap.containsKey(productID)){
					productPropertyMap=(Map) productMap.get(productID);
					productPropertyMap.put(propertyCode,propertyValue);
				}else{
					productPropertyMap=new LinkedHashMap();
					productPropertyMap.put(propertyCode,propertyValue);
				}
				productMap.put(productID,productPropertyMap);
			}
		}
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
	private void setHardFixingMp(HttpServletRequest request, CustomerProductEJBEvent ejbEvent) throws WebActionException{
		//硬件设备信息
		
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
		if(hardFixingMp!=null && !hardFixingMp.isEmpty())
			System.out.println("hardFixingMp========"+hardFixingMp);
		
		ejbEvent.setDeviceMap(hardFixingMp);
	}


	private void setPayFeeInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent) throws WebActionException
	{
        //	费用与支付
		int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
		int accountID = WebUtil.StringToInt(request.getParameter("txtAccount"));
	    
		ejbEvent.setCsiFeeList(getSessionFeeList(request)) ;
		getPayAndPrePayList(request,customerID,accountID,ejbEvent.getCsiPaymentList(),ejbEvent.getCsiPrePaymentDeductionList());	
	}
	
	private void setCSIInfo(HttpServletRequest request, CustomerProductEJBEvent ejbEvent)
	{
		//受理单信息
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN);
		csiDTO.setCreateReason(request.getParameter("txtCsiCreateReason"));
		
		//zyg 2007.11.22 需要同时设置 受理单的 statusreason 字段 
		csiDTO.setStatusReason(request.getParameter("txtCsiCreateReason"));
    
        if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
        	csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	csiDTO.setCustomerID (WebUtil.StringToInt(request.getParameter("txtCustomerID") ));
        if (WebUtil.StringHaveContent(request.getParameter("txtcsiPayOption")))
            csiDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
        if (WebUtil.StringHaveContent(request.getParameter("txtUsedMonth"))){
			csiDTO.setPoint(WebUtil.StringToInt(request.getParameter("txtUsedMonth")));
		}
		ejbEvent.setCsiDto( csiDTO);
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
	protected  void afterSuccessfulResponse(HttpServletRequest request, CommandResponse cmdResponse) {
		//对新增的产品做预计费处理
		if(confirmPost){
				try{
					Object responseObj=cmdResponse.getPayload();
					if(responseObj!=null)
						WebOperationUtil.preCalculateFeeInfo(((Integer)responseObj).intValue(), WebOperationUtil.getOperator(request.getSession()).getOperatorID());
				}catch (Exception ex){
					throw new RuntimeException("预计费错误："+ex.getMessage());
				}
		}
		
	}
	
	private void setUsedMonth(HttpServletRequest request, CustomerProductEJBEvent ejbEvent) throws WebActionException
	{
		String txtUsedMonth =request.getParameter("txtUsedMonth");
		ejbEvent.setUsedMonth(WebUtil.StringToInt(txtUsedMonth));
	}
	
	
}