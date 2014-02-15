/*
 * Created on 2005-10-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CatvTerminalDTO;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.BookEJBEvent;
import com.dtv.oss.service.ejbevent.csr.OpenAccountCheckEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;

public class CheckAccountAndCustomerInfoWebAction extends GeneralWebAction { 
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
			
		String txtActionType =request.getParameter("txtActionType");
		String openFlag =(request.getParameter("OpenFlag")==null) ? "" :request.getParameter("OpenFlag");
		int eJbActionType =0;
		
		
		if (txtActionType ==null || txtActionType.equals("")){
			return null;   //表示返回上一步
		}	
		
		if (openFlag.equals("") || (!openFlag.equals(CommonKeys.ACTION_OF_BOOKING) 
		 		                && !openFlag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT) 
		   	                    && !openFlag.equals(CommonKeys.ACTION_OF_BOOKINGAGENT)
								&& !openFlag.equals(CommonKeys.ACTION_OF_SHOPACCOUNT)
								&& !openFlag.equals(CommonKeys.ACTION_OF_CATVSHOPACCOUNT)
								&& !openFlag.equals(CommonKeys.ACTION_OF_BOOK_DIRECTLY)) ){
		   	  throw new WebActionException("WebAction 不知道是什么类型的开户！");
		}
		
		
		switch (Integer.parseInt(txtActionType)){
			case CommonKeys.CHECK_CUSTOMERINFO :
				eJbActionType =OpenAccountCheckEJBEvent.CHECK_CUSTOMERINFO;
			    break;
			case CommonKeys.CHECK_PRODUCTPG_CAMPAINPG :
				eJbActionType =OpenAccountCheckEJBEvent.CHECK_PRODUCTPG_CAMPAINPG ;
			    break;
			case CommonKeys.CHECK_HARDPRODUCTINFO :
				eJbActionType =OpenAccountCheckEJBEvent.CHECK_HARDPRODUCTINFO;
			    break;
			case CommonKeys.CHECK_FREEINFO :
				eJbActionType =OpenAccountCheckEJBEvent.CHECK_FREEINFO ;
			    break;
			case CommonKeys.CHECK_CATV_CUSTOMERINOF :
				eJbActionType =OpenAccountCheckEJBEvent.CHECK_OPEN_ACCOUNT_CATV ;
				break;
			case CommonKeys.CACULATE_OPEN_CATV_FEE :
				eJbActionType =OpenAccountCheckEJBEvent.CACULATE_OPEN_CATV_FEE ;
			    break;
		    default :
		    	break;
		}
		
		
		OpenAccountCheckEJBEvent openAccountCheckEJBEvent =new  OpenAccountCheckEJBEvent(eJbActionType);
		
	
		//新客户信息
		com.dtv.oss.dto.NewCustomerInfoDTO customerDTO = new com.dtv.oss.dto.NewCustomerInfoDTO();
		customerDTO.setCustomerSerialNo(request.getParameter("txtCustomerSerialNo"));
		customerDTO.setCatvID(request.getParameter("txtCatvID"));
		customerDTO.setType(request.getParameter("txtCustType"));
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
		customerDTO.setTimeRangeStart(WebUtil.StringToTimestamp(request.getParameter("txtTimeRangeStart")));
		customerDTO.setTimeRangeEnd(WebUtil.StringToTimestamp(request.getParameter("txtTimeRangeEnd")));
		customerDTO.setLoginID(request.getParameter("txtLoginID"));
        customerDTO.setLoginPWD(request.getParameter("txtLoginPwd"));
  
		//新客户新账户信息
		com.dtv.oss.dto.NewCustAccountInfoDTO accountDTO = new com.dtv.oss.dto.NewCustAccountInfoDTO();
		accountDTO.setDescription(request.getParameter("txtDescription"));
		accountDTO.setMopID(WebUtil.StringToInt(request.getParameter("txtMopID")));
		accountDTO.setBankAccountName(request.getParameter("txtBankAccountName"));
		accountDTO.setBankAccount(request.getParameter("txtBankAccount"));
		accountDTO.setAccountName(request.getParameter("txtAccountName"));
		accountDTO.setAddressFlag(request.getParameter("txtAddressFlag"));
		accountDTO.setAccountType(request.getParameter("txtAccountType"));

		//受理单信息
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtBankAccount")));
		csiDTO.setAgentCardID(request.getParameter("txtCardID"));
		csiDTO.setAgentCardType(request.getParameter("txtCardType"));
		String csiType ="";
		
		if (openFlag.equals(CommonKeys.ACTION_OF_BOOKING)){
			csiType = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK ;
			csiDTO.setInstallationType(CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_INSTALL);
		} else if (openFlag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)){
			csiType = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB ;	
		} else if (openFlag.equals(CommonKeys.ACTION_OF_BOOKINGAGENT)){
			csiType = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK ;
			openAccountCheckEJBEvent.setAgent(true);
		} else if (openFlag.equals(CommonKeys.ACTION_OF_SHOPACCOUNT)){
			csiType = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS ;
			String txtIsInstall =(request.getParameter("txtIsInstall")==null) ? CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL
                    : request.getParameter("txtIsInstall");
            csiDTO.setInstallationType(txtIsInstall);
		} 
		else if (openFlag.equals(CommonKeys.ACTION_OF_BOOK_DIRECTLY)){
			csiType = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CO;
		}
		else if (openFlag.equals(CommonKeys.ACTION_OF_CATVSHOPACCOUNT)){
			csiType = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO;
			String txtIsInstall =(request.getParameter("txtIsInstall")==null) ? CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL
                    : request.getParameter("txtIsInstall");
			csiDTO.setInstallationType(txtIsInstall);
			//csiDTO.setInstallationType(CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_INSTALL);
		}
		
		csiDTO.setType(csiType);
		if (!openFlag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT) ){
		  if (WebUtil.StringHaveContent(request.getParameter("txtPreferedTime")) 
				&&	WebUtil.StringHaveContent(request.getParameter("txtPreferedDate")))	{
		      csiDTO.setPreferedTime(request.getParameter("txtPreferedTime"));
		      csiDTO.setPreferedDate(WebUtil.StringToTimestamp(request.getParameter("txtPreferedDate")));
		      openAccountCheckEJBEvent.setCreateJobCard(true);
		  }
    	}
		
		
		String txtReferBookingSheetID =(request.getParameter("txtReferBookingSheetID")==null) ? "0" : request.getParameter("txtReferBookingSheetID");
		csiDTO.setReferBookingSheetID(Integer.parseInt(txtReferBookingSheetID));
		csiDTO.setReferSheetID(request.getParameter("txtReferSheetID"));
		csiDTO.setCreateReason(request.getParameter("txtCsiCreateReason"));
		//zyg 2007.11.22 需要同时设置 受理单的 statusreason 字段
		csiDTO.setStatusReason(request.getParameter("txtCsiCreateReason"));
		
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

		//客户帐单地址
		com.dtv.oss.dto.AddressDTO acctAddressDTO = new com.dtv.oss.dto.AddressDTO();
		acctAddressDTO.setPostcode(request.getParameter("txtBillPostcode"));
		acctAddressDTO.setDetailAddress(billAddress);
		acctAddressDTO.setDistrictID(Integer.parseInt(county));
		
		//团购券号
		String txtGroupBargainDetailNo =request.getParameter("txtGroupBargainDetailNo");
		
		//客户购买的产品包信息
		ArrayList lstProductID = new ArrayList();
		//客户选择的优惠信息
		ArrayList lstCampaignID = new ArrayList();
		if (txtGroupBargainDetailNo ==null || txtGroupBargainDetailNo.equals("")) {
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
		}
        
		
        //		硬件设备信息
		//Hashtable hardFixingMp =new Hashtable();
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

		openAccountCheckEJBEvent.setNewCustInfo(customerDTO);
	    openAccountCheckEJBEvent.setNewCustAcctInfo(accountDTO);
	    openAccountCheckEJBEvent.setCsiDto(csiDTO);
	    openAccountCheckEJBEvent.setCustAddrDto(custAddressDTO);
	    openAccountCheckEJBEvent.setAcctAddrDto(acctAddressDTO);
	    openAccountCheckEJBEvent.setDetailNo(txtGroupBargainDetailNo);
	    openAccountCheckEJBEvent.setCsiPackageArray(lstProductID);
	    openAccountCheckEJBEvent.setCsiCampaignArray(lstCampaignID);
	    openAccountCheckEJBEvent.setDeviceMap(hardFixingMp);
	    openAccountCheckEJBEvent.setForcedDeposit(WebUtil.StringTodouble(request.getParameter("txtForceDepostMoney")));
	    
	    
	    //终端信息
	    openAccountCheckEJBEvent.setCatvID(request.getParameter("txtCatvID"));
	    openAccountCheckEJBEvent.setAddPortNum(WebUtil.StringToInt(request.getParameter("txtAddPortNum")));
		CatvTerminalDTO catvTerminalDTO=new CatvTerminalDTO();
		//终端类型/终端编号/所属光节点/线缆类型/双向网是否改造
		catvTerminalDTO.setCatvTermType(request.getParameter("txtCatvTermType"));
		catvTerminalDTO.setFiberNodeID(WebUtil.StringToInt(request.getParameter("txtFiberNodeID")));
		catvTerminalDTO.setCableType(request.getParameter("txtCableType"));
		catvTerminalDTO.setBiDirectionFlag(request.getParameter("txtBiDirectionFlag"));
		openAccountCheckEJBEvent.setCatvTerminalDTO(catvTerminalDTO);
		
		
		//产品属性信息
		//侯6-26修改,针对多个产品包提交问题,当多个产品包提交时request.getParameter("txtProductProperty")会得到一个数组,因为jsp页面上设置的serverName是一样的,
		//只做了形式上修改,添加一层循环,用来分解productProperty数组
		Map productPropertyMap = new HashMap();
		Map tempMap = null;
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
									: request.getParameter(productPropertyHiddenName);

							if (productPropertyMap.containsKey(productID))
								tempMap = (Map) productPropertyMap.get(productID);
							else
								tempMap = new HashMap();
							tempMap.put(propertyCode, productPropertyValue);
							productPropertyMap.put(productID, tempMap);

						//}
					}
				}
			}
		}
		openAccountCheckEJBEvent.setProductPropertyMap(productPropertyMap);
		
	    System.out.println("openAccountCheckEJBEvent.getNewCustInfo()======="+openAccountCheckEJBEvent.getNewCustInfo());
	    System.out.println("openAccountCheckEJBEvent.getNewCustAcctInfo()======="+openAccountCheckEJBEvent.getNewCustAcctInfo());
	    System.out.println("openAccountCheckEJBEvent.getCsiDto()==============="+openAccountCheckEJBEvent.getCsiDto());
	    
	    System.out.println("openAccountCheckEJBEvent.getCatvTerminalDTO()==============="+openAccountCheckEJBEvent.getCatvTerminalDTO());
	    
		return openAccountCheckEJBEvent;
	}
	
	public void doEnd(HttpServletRequest request, CommandResponse cmdResponse) {
		super.doEnd(request, cmdResponse);
		String txtActionType =(request.getParameter("txtActionType") ==null) ? "" :request.getParameter("txtActionType");
		if ((!txtActionType.equals(""))
				&& (Integer.parseInt(txtActionType) ==CommonKeys.CHECK_PRODUCTPG_CAMPAINPG
						|| Integer.parseInt(txtActionType) ==CommonKeys.CHECK_CATV_CUSTOMERINOF)){
		   Object obj = request.getAttribute("ResponseFromEJBEvent");
		   if (obj != null) {
			  System.out.println("obj.getClass().getName()=========="+obj.getClass().getName());
			  CommandResponseImp cmdImp =(CommandResponseImp)obj;
			  System.out.println("cmdImp.getPayload()==============="+cmdImp.getPayload());
			  
			  if(CommonKeys.COMMAND_ID_IMMEDIATEFEE.equals(cmdImp.getFlowHandler()))
				  request.setAttribute(WebKeys.REQUEST_ATTRIBUTE, obj);
			  
			  //if ((cmdImp.getPayload() !=null) && !(cmdImp.getPayload() instanceof Collection)){
			  //    request.setAttribute(WebKeys.REQUEST_ATTRIBUTE, obj);
			  //}
		  }
	    }
	}
}
