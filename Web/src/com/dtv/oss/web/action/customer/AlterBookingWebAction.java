/**
 * author :david.Yang
 */
package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
//import oss class
import com.dtv.oss.util.DynamicServey;
import com.dtv.oss.web.util.*;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.BookEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;

public class AlterBookingWebAction extends GeneralWebAction{
	boolean confirmPost = false;
	  protected boolean needCheckToken(){
	      return confirmPost;
	  }

	  public void doStart(HttpServletRequest request){
	      super.doStart(request);
	      confirmPost = false;
	      if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))){
	           if (request.getParameter("confirm_post").equalsIgnoreCase("true")) confirmPost = true;
	      }
      }
	   
	  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException{
	   	  BookEJBEvent event =null;
		  String actionFlag =request.getParameter("ActionFlag");
		  if (actionFlag ==null){
		  	 throw new WebActionException("AlterBookingWebAction 不知道JSP的动作！");
		  }
		  
		  if (actionFlag.equals(CommonKeys.MODIFY_OF_BOOKING)){
		  	 event = new BookEJBEvent(EJBEvent.ALTERBOOKING);
		  }
		  
		  if (actionFlag.equals(CommonKeys.CONFIRM_OF_BOOKING)){
		  	 event =new BookEJBEvent(EJBEvent.CHECK_AGENT_BOOKING);
		  	 event.setCreateJobCard(true);
		  	 event.setAgent(true);
		  	 setNextOrgID(request,event);
		  } 
		  
	   	  //新客户信息
		  com.dtv.oss.dto.NewCustomerInfoDTO customerDTO = new com.dtv.oss.dto.NewCustomerInfoDTO();
		  customerDTO.setId(WebUtil.StringToInt(request.getParameter("txtCustomerId")));
		  customerDTO.setCatvID(request.getParameter("txtCatvID"));
		  customerDTO.setType(request.getParameter("txtType"));
		  customerDTO.setNationality(request.getParameter("txtNationality"));
		  customerDTO.setGender(request.getParameter("txtGender"));
		  customerDTO.setName(request.getParameter("txtName"));
		  customerDTO.setCardType(request.getParameter("txtCardType"));
		  customerDTO.setCardID(request.getParameter("txtCardID"));
		  customerDTO.setOpenSourceType(request.getParameter("txtOpenSourceType"));
		  customerDTO.setOpenSourceID(WebUtil.StringToInt(request.getParameter("txtOpenSourceID")));
		  customerDTO.setSocialSecCardID(request.getParameter("txtSocialSecCardID"));
		  customerDTO.setTelephone(request.getParameter("txtTelephone"));
		  customerDTO.setMobileNumber(request.getParameter("txtMobileNumber"));
		  customerDTO.setFaxNumber(request.getParameter("txtFaxNumber"));
		  customerDTO.setEmail(request.getParameter("txtEmail"));
		  customerDTO.setContractNo(request.getParameter("txtContractNo"));
		  if (WebUtil.StringHaveContent(request.getParameter("txtBirthday")))
	            customerDTO.setBirthday(WebUtil.StringToTimestamp(request.getParameter("txtBirthday")));
		  
		  customerDTO.setTimeRangeStart(WebUtil.StringToTimestamp(request.getParameter("txtTimeRangeStart")));
		  customerDTO.setTimeRangeEnd(WebUtil.StringToTimestamp(request.getParameter("txtTimeRangeEnd")));
          customerDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtNewCustomerDtLastmod")));
		  
	      //新客户新账户信息
          String txtAcctID  =request.getParameter("txtAcctID");
          System.out.println("txtAcctID========"+txtAcctID);
          
		  com.dtv.oss.dto.NewCustAccountInfoDTO accountDTO = new com.dtv.oss.dto.NewCustAccountInfoDTO();
		  accountDTO.setDescription(request.getParameter("txtDescription"));
		  accountDTO.setId(WebUtil.StringToInt(txtAcctID));
		  accountDTO.setMopID(WebUtil.StringToInt(request.getParameter("txtMopID")));
		  accountDTO.setBankAccountName(request.getParameter("txtBankAccountName"));
		  accountDTO.setBankAccount(request.getParameter("txtBankAccount"));
		  accountDTO.setAccountName(request.getParameter("txtAccountName"));
		  accountDTO.setAccountType(request.getParameter("txtAccountType"));
		  accountDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtNewAcctLastmod")));


		  //受理单信息
		  com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		  String csiType = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK ;
		  csiDTO.setInstallationType(CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_INSTALL);
		  csiDTO.setComments(request.getParameter("txtComments"));
		  csiDTO.setType(csiType);
		  csiDTO.setPreferedTime(request.getParameter("txtPreferedTime"));
		  csiDTO.setPreferedDate(WebUtil.StringToTimestamp(request.getParameter("txtPreferedDate")));
		  String txtReferBookingSheetID =(request.getParameter("txtReferBookingSheetID")==null) ? "0" : request.getParameter("txtReferBookingSheetID");
		  csiDTO.setReferBookingSheetID(Integer.parseInt(txtReferBookingSheetID));
		  csiDTO.setId(Integer.parseInt(request.getParameter("txtCsiId")));
		  if (WebUtil.StringHaveContent(request.getParameter("txtCsiCreateReason")))
		  {    
		  	csiDTO.setCreateReason(request.getParameter("txtCsiCreateReason"));
		  	//zyg 2007.11.22 需要同时设置 受理单的 statusreason 字段
		  	csiDTO.setStatusReason(request.getParameter("txtCsiCreateReason"));
		  }
		  
		  csiDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtCsiDtLastmod")));
		  
        csiDTO.setAgentName(request.getParameter("txtAgentName"));
        csiDTO.setAgentTelephone(request.getParameter("txtAgentTelephone"));
        csiDTO.setAgentCardType(request.getParameter("txtAgentCardType"));
        csiDTO.setAgentCardID(request.getParameter("txtAgentCardId"));
	        
		  String txtDetailAddress =(request.getParameter("txtDetailAddress")==null) ? "" : request.getParameter("txtDetailAddress");
		  String txtBillDetailAddress =(request.getParameter("txtBillDetailAddress")==null) ? "" : request.getParameter("txtBillDetailAddress");
		
          String billAddress ="";

          if (!txtBillDetailAddress.equals("")){
        	  billAddress =txtBillDetailAddress;
          }
          if (txtBillDetailAddress.equals("") && !txtDetailAddress.equals("")){
              billAddress =txtDetailAddress;
          }
        
          String txtCounty =(request.getParameter("txtCounty")==null) ? "" : request.getParameter("txtCounty");
          String txtbillCounty =(request.getParameter("txtbillCounty")==null) ? "" :request.getParameter("txtbillCounty");
        
          String county ="";
        
          if (!txtbillCounty.equals("")){
        	 county =txtbillCounty;
          }
          if (txtbillCounty.equals("") && !(txtCounty.equals(""))){
             county =txtCounty;
          }
        
		  //客户安装地址
		  com.dtv.oss.dto.AddressDTO custAddressDTO = new com.dtv.oss.dto.AddressDTO();
		  custAddressDTO.setPostcode(request.getParameter("txtPostcode"));
		  custAddressDTO.setDetailAddress(txtDetailAddress);
		  custAddressDTO.setDistrictID(Integer.parseInt(txtCounty));
		  custAddressDTO.setAddressID(WebUtil.StringToInt(request.getParameter("txtAddressId")));
		  custAddressDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtCustAddrLastmod")));

		  //客户帐单地址
		  com.dtv.oss.dto.AddressDTO acctAddressDTO = new com.dtv.oss.dto.AddressDTO();
		  acctAddressDTO.setPostcode(request.getParameter("txtBillPostcode"));
		  acctAddressDTO.setDetailAddress(billAddress);
		  acctAddressDTO.setDistrictID(Integer.parseInt(county));
		  acctAddressDTO.setAddressID(WebUtil.StringToInt(request.getParameter("txtBillAddressId")));
		  acctAddressDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtAcctAddrLastmod")));
		
		  //团购券号
		  String txtGroupBargainDetailNo =request.getParameter("txtGroupBargainDetailNo");
		
		  //客户购买的产品包信息
		  ArrayList lstProductID = new ArrayList();
		  if (WebUtil.StringHaveContent(request.getParameter("ProductList"))) {
			  String[] aProductID = request.getParameter("ProductList").split(";");
			  if ((aProductID != null) && (aProductID.length > 0)) {
			     for (int i = 0; i < aProductID.length; i++)
					lstProductID.add(WebUtil.StringToInteger(aProductID[i]));
			  }

		  }

		  //客户选择的优惠信息
		  ArrayList lstCampaignID = new ArrayList();
		  if (WebUtil.StringHaveContent(request.getParameter("CampaignList"))) {
			  String[] aCampaignID = request.getParameter("CampaignList").split(";");
			  if ((aCampaignID != null) && (aCampaignID.length > 0)) {
			     for (int i = 0; i < aCampaignID.length; i++)
					lstCampaignID.add(WebUtil.StringToInteger(aCampaignID[i]));
			  }
		  }
        
		  //客户市场信息
		  Collection custMarketInfoList =DynamicServey.getNewCustomerMarketInfo(request,"txtDynamicServey");
		
		  //硬件设备信息
		  //Hashtable hardFixingMp =new Hashtable();
		  HashMap hardFixingMp =new HashMap();
		  String  deviceClass =(request.getParameter("DeviceClassList")==null) ? "" : request.getParameter("DeviceClassList");
		  String[] deviceClassList =deviceClass.split(";");
		  String  terminalDevice =(request.getParameter("TerminalDeviceList")==null) ? "" :request.getParameter("TerminalDeviceList");
		  String[] terminalDeviceList =terminalDevice.split(";");
		
		  if (deviceClassList.length !=terminalDeviceList.length){
			   throw new WebActionException("硬件设备和硬件值无法匹配！");
		   } else {
			  for (int i=0; i< deviceClassList.length;i++) {
		 		 hardFixingMp.put(terminalDeviceList[i],deviceClassList[i]);
			  }
		   }
		
		   System.out.println("hardFixingMp========"+hardFixingMp);
		      
	    	//虚拟电话号码()
		    event.setPhoneNo(request.getParameter("phoneNo"));
		    event.setItemID(WebUtil.StringToInt(request.getParameter("itemID")));
		    //更改预约单后 电话号码存储
			csiDTO.setServiceCodeList(request.getParameter("phoneNo"));
			if("false".equals(request.getParameter("needPhoneNo")))
	        	csiDTO.setServiceCodeList("");
			
		    

			System.out.println("__新____phoneNo="+event.getPhoneNo()+":::itemID="+event.getItemID());
		   
		   event.setNewCustInfo(customerDTO);
		   event.setNewCustAcctInfo(accountDTO);
		   event.setCsiDto(csiDTO);
		   event.setCustAddrDto(custAddressDTO);
		   event.setAcctAddrDto(acctAddressDTO);
		   event.setDetailNo(txtGroupBargainDetailNo);
		   event.setCsiPackageArray(lstProductID);
		   event.setCsiCampaignArray(lstCampaignID);
		   event.setNewCustMarketInfoList(custMarketInfoList);
		   event.setDeviceMap(hardFixingMp);
		   
           return event;

	    }
	private void setNextOrgID(HttpServletRequest request, BookEJBEvent ejbEvent) throws WebActionException{
		if (WebUtil.StringHaveContent(request.getParameter("transferType"))){
			if(("auto").equalsIgnoreCase(request.getParameter("transferType"))){
				System.out.println("******auto*******"+request.getParameter("txtAutoNextOrgID")+"*********");
				ejbEvent.setNextOrgID(WebUtil.StringToInt(request.getParameter("txtAutoNextOrgID")));
			}else if(("manual").equalsIgnoreCase(request.getParameter("transferType"))){
				ejbEvent.setNextOrgID(WebUtil.StringToInt(request.getParameter("txtNextOrgID")));
			}
		}
	}
}
