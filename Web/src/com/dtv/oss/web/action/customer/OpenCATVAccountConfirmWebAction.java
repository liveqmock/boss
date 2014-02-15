package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Yangyong
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.util.DynamicServey;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.*;
//import oss class
import com.dtv.oss.dto.CatvTerminalDTO;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.BookEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;

public class OpenCATVAccountConfirmWebAction extends PayFeeWebAction {
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

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
 
		BookEJBEvent event =null;
		
		event=new BookEJBEvent(EJBEvent.OPEN_CATV_ACCOUNT);
		   
		//�¿ͻ���Ϣ
		com.dtv.oss.dto.NewCustomerInfoDTO customerDTO = new com.dtv.oss.dto.NewCustomerInfoDTO();
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
        customerDTO.setLoginID(request.getParameter("txtLoginID"));
        customerDTO.setLoginPWD(request.getParameter("txtLoginPwd"));
		if (WebUtil.StringHaveContent(request.getParameter("txtContractNo"))) 
		   customerDTO.setContractNo(request.getParameter("txtContractNo"));
		customerDTO.setTimeRangeStart(WebUtil.StringToTimestamp(request.getParameter("txtTimeRangeStart")));
		customerDTO.setTimeRangeEnd(WebUtil.StringToTimestamp(request.getParameter("txtTimeRangeEnd")));
        if (WebUtil.StringHaveContent(request.getParameter("txtBirthday")))
            customerDTO.setBirthday(WebUtil.StringToTimestamp(request.getParameter("txtBirthday")));
        customerDTO.setComments(request.getParameter("txtComments"));

		//�¿ͻ����˻���Ϣ
		com.dtv.oss.dto.NewCustAccountInfoDTO accountDTO = new com.dtv.oss.dto.NewCustAccountInfoDTO();
		accountDTO.setDescription(request.getParameter("txtDescription"));
		accountDTO.setMopID(WebUtil.StringToInt(request.getParameter("txtMopID")));
		accountDTO.setBankAccountName(request.getParameter("txtBankAccountName"));
		accountDTO.setBankAccount(request.getParameter("txtBankAccount"));
		accountDTO.setAccountName(request.getParameter("txtAccountName"));
		accountDTO.setAddressFlag(request.getParameter("txtAddressFlag"));
		accountDTO.setAccountType(request.getParameter("txtAccountType"));

		//������Ϣ
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtBankAccount")));
		String csiType = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO ;
		String txtIsInstall =(request.getParameter("txtIsInstall")==null) ?
				CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL: request.getParameter("txtIsInstall");
		csiDTO.setInstallationType(txtIsInstall);
		csiDTO.setType(csiType);
				
		String txtReferBookingSheetID =(request.getParameter("txtReferBookingSheetID")==null) ? "0" : request.getParameter("txtReferBookingSheetID");
		csiDTO.setReferBookingSheetID(Integer.parseInt(txtReferBookingSheetID));
		csiDTO.setReferSheetID(request.getParameter("txtReferSheetID"));
		csiDTO.setComments(request.getParameter("txtComments"));
		csiDTO.setContactPerson(request.getParameter("txtName"));
		String txtContactPhone ="";
		if (WebUtil.StringHaveContent(request.getParameter("txtTelephone"))) 
			txtContactPhone =request.getParameter("txtTelephone");
		if (WebUtil.StringHaveContent(request.getParameter("txtMobileNumber"))){
		    if (!txtContactPhone.equals("")) 
		    	txtContactPhone =txtContactPhone +" / "+request.getParameter("txtMobileNumber");
		    else
		    	txtContactPhone =request.getParameter("txtMobileNumber");
		}
		csiDTO.setContactPhone(txtContactPhone);

		//add by stone on 30 Jun.,2006
        if (WebUtil.StringHaveContent(request.getParameter("txtPreferedDate")))
            csiDTO.setPreferedDate(WebUtil.StringToTimestamp(request.getParameter("txtPreferedDate")));
        csiDTO.setPreferedTime(request.getParameter("txtPreferedTime"));
		//add end
        csiDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
        csiDTO.setCreateReason(request.getParameter("txtCsiCreateReason"));

        //zyg 2007.11.22 ��Ҫͬʱ�޸������� statusreason �ֶ� 
        csiDTO.setStatusReason(request.getParameter("txtCsiCreateReason"));
        
		String txtDetailAddress =(request.getParameter("txtDetailAddress")==null) ? "" : request.getParameter("txtDetailAddress");
		String txtBillDetailAddress =(request.getParameter("txtBillDetailAddress")==null) ? "" : request.getParameter("txtBillDetailAddress");
		
        if (txtBillDetailAddress.equals("")){
        	txtBillDetailAddress =txtDetailAddress;
        }
        
        String txtPostcode =(request.getParameter("txtPostcode")==null) ? "" : request.getParameter("txtPostcode");
		String txtBillPostcode =(request.getParameter("txtBillPostcode")==null) ? "" : request.getParameter("txtBillPostcode");
        if (txtBillPostcode.equals("")){
        	txtBillPostcode =txtPostcode;
        }
        
        
        String txtCounty =(request.getParameter("txtCounty")==null) ? "" : request.getParameter("txtCounty");
        String txtbillCounty =(request.getParameter("txtbillCounty")==null) ? "" :request.getParameter("txtbillCounty");
        
        if (txtbillCounty.equals("")){
        	txtbillCounty =txtCounty;
        }
        
		//�ͻ���װ��ַ
		com.dtv.oss.dto.AddressDTO custAddressDTO = new com.dtv.oss.dto.AddressDTO();
		custAddressDTO.setPostcode(txtPostcode);
		custAddressDTO.setDetailAddress(txtDetailAddress);
		custAddressDTO.setDistrictID(Integer.parseInt(txtCounty));

		//�ͻ��ʵ���ַ
		com.dtv.oss.dto.AddressDTO acctAddressDTO = new com.dtv.oss.dto.AddressDTO();
		acctAddressDTO.setPostcode(txtBillPostcode);
		acctAddressDTO.setDetailAddress(txtBillDetailAddress);
		acctAddressDTO.setDistrictID(Integer.parseInt(txtbillCounty));
		
		//�ͻ��г���Ϣ
		Collection custMarketInfoList =DynamicServey.getNewCustomerMarketInfo(request,"txtDynamicServey");
		
		//�Ź�ȯ��
		String txtGroupBargainDetailNo =request.getParameter("txtGroupBargainDetailNo");
		
		//�ͻ�����Ĳ�Ʒ����Ϣ
		ArrayList lstProductID = new ArrayList();
		if (WebUtil.StringHaveContent(request.getParameter("ProductList"))) {
			String[] aProductID = request.getParameter("ProductList")
					.split(";");
			if ((aProductID != null) && (aProductID.length > 0)) {
				for (int i = 0; i < aProductID.length; i++)
					lstProductID.add(WebUtil.StringToInteger(aProductID[i]));
			}

		}

		//��Ʒ������Ϣ
		//��6-26�޸�,��Զ����Ʒ���ύ����,�������Ʒ���ύʱrequest.getParameter("txtProductProperty")��õ�һ������,��Ϊjspҳ�������õ�serverName��һ����,
		//ֻ������ʽ���޸�,���һ��ѭ��,�����ֽ�productProperty����
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
		
		//�ͻ�ѡ����Ż���Ϣ
		ArrayList lstCampaignID = new ArrayList();
		if (WebUtil.StringHaveContent(request.getParameter("CampaignList"))) {
			String[] aCampaignID = request.getParameter("CampaignList").split(
					";");
			if ((aCampaignID != null) && (aCampaignID.length > 0)) {
				for (int i = 0; i < aCampaignID.length; i++)
					lstCampaignID.add(WebUtil.StringToInteger(aCampaignID[i]));
			}
		}
        
		
		
		//Ӳ���豸��Ϣ
		//Hashtable hardFixingMp =new Hashtable();
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
		if(hardFixingMp!=null && !hardFixingMp.isEmpty())
			System.out.println("hardFixingMp========"+hardFixingMp);
		
	    //����绰����
	    event.setPhoneNo(request.getParameter("phoneNo"));
	    event.setItemID(WebUtil.StringToInt(request.getParameter("itemID")));
	    csiDTO.setServiceCodeList(request.getParameter("phoneNo"));
	    
	    // ԤԼʱѡ�����Դ(�绰����)��Ϣ �ϵĵ绰����
	    event.setServiceCodeList(request.getParameter("txtServiceCodeList"));

	    
		event.setNewCustInfo(customerDTO);
		event.setNewCustAcctInfo(accountDTO);
		event.setCsiDto(csiDTO);
		event.setCustAddrDto(custAddressDTO);
		event.setAcctAddrDto(acctAddressDTO);
		event.setDetailNo(txtGroupBargainDetailNo);
		if (txtGroupBargainDetailNo ==null || txtGroupBargainDetailNo.trim().equals("")) 
			event.setCsiPackageArray(lstProductID);
		event.setCsiCampaignArray(lstCampaignID);
		event.setNewCustMarketInfoList(custMarketInfoList);
		event.setDeviceMap(hardFixingMp);
        // ������֧��
		event.setCsiFeeList(getSessionFeeList(request)) ;
		getPayAndPrePayList(request,0,0,event.getCsiPaymentList(),event.getCsiPrePaymentDeductionList());
		event.setProductPropertyMap(productPropertyMap); 
		event.setForcedDeposit(WebUtil.StringTodouble(request.getParameter("txtForceDepostMoney")));
		
		if (WebUtil.StringHaveContent(request.getParameter("transferType"))){
			  if(("auto").equalsIgnoreCase(request.getParameter("transferType"))){
				  System.out.println("******auto*******"+request.getParameter("txtAutoNextOrgID")+"*********");
				  event.setNextOrgID(WebUtil.StringToInt(request.getParameter("txtAutoNextOrgID")));
			  }else if(("manual").equalsIgnoreCase(request.getParameter("transferType"))){
				  event.setNextOrgID(WebUtil.StringToInt(request.getParameter("txtNextOrgID")));
			  }
		}
		
		//�ն���Ϣ
		event.setCatvID(request.getParameter("txtCatvID"));
		event.setAddPortNum(WebUtil.StringToInt(request.getParameter("txtAddPortNum")));

		CatvTerminalDTO catvTerminalDTO=new CatvTerminalDTO();
		//�ն�����/�ն˱��/������ڵ�/��������/˫�����Ƿ����
		catvTerminalDTO.setCatvTermType(request.getParameter("txtCatvTermType"));
		catvTerminalDTO.setFiberNodeID(WebUtil.StringToInt(request.getParameter("txtFiberNodeID")));
		catvTerminalDTO.setCableType(request.getParameter("txtCableType"));
		catvTerminalDTO.setBiDirectionFlag(request.getParameter("txtBiDirectionFlag"));
		event.setCatvTerminalDTO(catvTerminalDTO);
		
        return event;

	}

}