package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.dto.CustMarketInfoDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.OpenAccountCheckEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

/**
 * 
 * author     ：zhouxushun 
 * date       : 2005-10-11
 * description:用户开户时，选择软件产品后，通过用户的软件产品查找相应的硬件类型
 * 
 * @author 250713z
 *
 */
public class OpenChooseTerminalDeviceWebAction extends GeneralWebAction {

	
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
    	OpenAccountCheckEJBEvent event=new OpenAccountCheckEJBEvent(OpenAccountCheckEJBEvent.CHECK_FREEINFO);
		
		//请自行添加event参数
		
		//新客户信息
		com.dtv.oss.dto.NewCustomerInfoDTO customerDTO = new com.dtv.oss.dto.NewCustomerInfoDTO();
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
		customerDTO.setTimeRangeStart(WebUtil.StringToTimestamp(request.getParameter("txtTimeRangeStart")));
		customerDTO.setTimeRangeEnd(WebUtil.StringToTimestamp(request.getParameter("txtTimeRangeEnd")));
  
		//新客户新账户信息
		com.dtv.oss.dto.NewCustAccountInfoDTO accountDTO = new com.dtv.oss.dto.NewCustAccountInfoDTO();
		accountDTO.setDescription(request.getParameter("txtDescription"));
		accountDTO.setMopID(WebUtil.StringToInt(request.getParameter("txtMopID")));
		accountDTO.setBankAccountName(request.getParameter("txtBankAccountName"));
		accountDTO.setBankAccount(request.getParameter("txtBankAccount"));
		accountDTO.setAccountName(request.getParameter("txtAccountName"));
		accountDTO.setAddressFlag(request.getParameter("txtAddressFlag"));

		//受理单信息
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtBankAccount")));
		csiDTO.setAgentCardID(request.getParameter("txtCardID"));
		csiDTO.setAgentCardType(request.getParameter("txtCardType"));
		csiDTO.setType(request.getParameter("txtType"));
		csiDTO.setPreferedTime(request.getParameter("txtPreferedTime"));
		csiDTO.setPreferedDate(WebUtil.StringToTimestamp(request.getParameter("txtPreferedDate")));

        //客户安装地址
		com.dtv.oss.dto.AddressDTO custAddressDTO = new com.dtv.oss.dto.AddressDTO();
		custAddressDTO.setPostcode(request.getParameter("txtPostcode"));
		custAddressDTO.setDetailAddress(request.getParameter("txtDetailAddress"));

		//客户帐单地址
		com.dtv.oss.dto.AddressDTO acctAddressDTO = new com.dtv.oss.dto.AddressDTO();
		acctAddressDTO.setPostcode(request.getParameter("txtBillPostcode"));
		acctAddressDTO.setDetailAddress(request.getParameter("txtBillDetailAddress"));
		
		//团购券号
		String txtGroupBargainDetailNo =request.getParameter("txtGroupBargainDetailNo");
		
		//客户购买的产品包信息
		ArrayList lstProductID = new ArrayList();
		if (WebUtil.StringHaveContent(request.getParameter("ProductList"))) {
			String[] aProductID = request.getParameter("ProductList")
					.split(";");
			if ((aProductID != null) && (aProductID.length > 0)) {
				for (int i = 0; i < aProductID.length; i++)
					lstProductID.add(WebUtil.StringToInteger(aProductID[i]));
			}

		}

		//客户选择的优惠信息
		ArrayList lstCampaignID = new ArrayList();
		if (WebUtil.StringHaveContent(request.getParameter("CampaignList"))) {
			String[] aCampaignID = request.getParameter("CampaignList").split(
					";");
			if ((aCampaignID != null) && (aCampaignID.length > 0)) {
				for (int i = 0; i < aCampaignID.length; i++)
					lstCampaignID.add(WebUtil.StringToInteger(aCampaignID[i]));
			}
		}
        
		//客户市场信息
		ArrayList custMarketInfoList =new ArrayList();
		CustMarketInfoDTO custMarketInfoDto =new CustMarketInfoDTO();
		custMarketInfoList.add(custMarketInfoDto);
		
		//硬件设备信息
		//Hashtable hardFixingMp =new Hashtable();
		HashMap hardFixingMp =new HashMap();
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
}
