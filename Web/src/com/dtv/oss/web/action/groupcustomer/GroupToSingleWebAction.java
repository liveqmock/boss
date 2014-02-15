package com.dtv.oss.web.action.groupcustomer;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.web.util.*;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustMarketInfoDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.NewCustAccountInfoDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.service.ejbevent.groupcustomer.GroupCustomerEJBEvent;

/**
 * 
 * @author 260327h 集团客户转个人
 */
public class GroupToSingleWebAction extends PayFeeWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		GroupCustomerEJBEvent event = new GroupCustomerEJBEvent();

		String actionType = request.getParameter("txtActionType");

		if (actionType == null)
			return null;

		try {
			event.setCustomerDTO(this.getCustomerInfo(request));
			event.setAddressDTO(this.getAddressInfo(request));
			event.setCsiDto(this.getCustServiceInteractionInfo(request));
			event.setCustomerID(WebUtil.StringToInt(request.getParameter("txtGroupCustID")));
			event.setNewCustAccountInfoDTO(this
					.getNewCustAccountInfo(request));
			event.setAccountAddressDTO(this.getAccountAddressDTO(request));
			if (actionType.equalsIgnoreCase("groupToSingleFee")) {
				event.setActionType(GroupCustomerEJBEvent.GROUPTOSINGLEFEE);
			}else if(actionType.equalsIgnoreCase("groupToSingleConfirm")){
				event.setMarketInfoList(getMarketInfo(request));
				event.setActionType(GroupCustomerEJBEvent.GROUPTOSINGLECONFIRM);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}		
		return event;
	}
	private ArrayList getMarketInfo(HttpServletRequest request){
		//客户市场信息
		ArrayList custMarketInfoList =new ArrayList();
		String marketId =(request.getParameter("txtDynamicServey")==null) ? "" : request.getParameter("txtDynamicServey");
		String[] maketIds =marketId.split(";");
		for(int i=0 ;i<maketIds.length; i++){
			CustMarketInfoDTO custMarketInfoDto =new CustMarketInfoDTO();
			String marketInfoHiddenName ="txtDynamicServey"+"_"+maketIds[i];
			String marketInfoHiddenValue =request.getParameter(marketInfoHiddenName);
			if (marketInfoHiddenValue !=null && !marketInfoHiddenValue.equals("")){
			   custMarketInfoDto.setInfoSettingId(Integer.parseInt(maketIds[i]));
			   custMarketInfoDto.setInfoValueId(WebUtil.StringToInt(marketInfoHiddenValue));
			   custMarketInfoList.add(custMarketInfoDto);
			}
		}
		return custMarketInfoList;
	}
/**
 * txtCustomerID/txtName/txtGender/txtBirthday/txtNationality/txtType/txtSocialSecCardID
 * /txtCardType/txtCardID/txtTelephone/txtMobileNumber/txtFaxNumber/txtEmail/txtCatvID/
 * txtBillDetailAddress/txtBillPostcode/txtbillCounty/txtMopID/
 * txtBankAccount/txtBankAccountName/txtAccountType/txtAccountName/txtOpenSourceType
 * @param request
 * @return
 * @throws WebActionException
 */
	private CustomerDTO getCustomerInfo(HttpServletRequest request)
			throws WebActionException {
		CustomerDTO custDTO = new CustomerDTO();
		String customerID=request.getParameter("txtCustomerID");
		String addressID=request.getParameter("txtCustAddressID");
		String name = request.getParameter("txtName");
		String gender=request.getParameter("txtGender");
		String birthday=request.getParameter("txtBirthday");
		String nationality=request.getParameter("txtNationality");
		String type = request.getParameter("txtType");
		String socialSecCardID = request.getParameter("txtSocialSecCardID");
		String cardType = request.getParameter("txtCardType");
		String cardID = request.getParameter("txtCardID");
		String telephone = request.getParameter("txtTelephone");
		String mobileNumber = request.getParameter("txtMobileNumber");
		String faxNumber = request.getParameter("txtFaxNumber");
		String email = request.getParameter("txtEmail");
		String catvID = request.getParameter("txtCatvID");
		String dtlastmod=request.getParameter("txtCustomerDTlastmod");
		
		String openSourceType = request.getParameter("txtOpenSourceType");
	

		if(WebUtil.StringHaveContent(customerID)){
			custDTO.setCustomerID(WebUtil.StringToInt(customerID));
		}else{
			throw new WebActionException("无效的集团子客户！");
		}
		if(WebUtil.StringHaveContent(addressID)){
			custDTO.setAddressID(WebUtil.StringToInt(addressID));
		}else{
			throw new WebActionException("无效的地址信息！");
		}
		custDTO.setName(name);
		custDTO.setGender(gender);
		if(WebUtil.StringHaveContent(birthday))
			custDTO.setBirthday(WebUtil.StringToTimestamp(birthday));
		custDTO.setNationality(nationality);
		custDTO.setCustomerType(type);
		custDTO.setSocialSecCardID(socialSecCardID);
		custDTO.setCardType(cardType);
		custDTO.setCardID(cardID);
		custDTO.setTelephone(telephone);
		custDTO.setTelephoneMobile(mobileNumber);
		custDTO.setFax(faxNumber);
		custDTO.setEmail(email);
		custDTO.setCatvID(catvID);
		custDTO.setOpenSourceType(openSourceType);
		if(WebUtil.StringHaveContent(dtlastmod)){
			custDTO.setDtLastmod(WebUtil.StringToTimestamp(dtlastmod));
		}
		if (WebUtil.StringHaveContent(name)) {
			custDTO.setName(name);
		} else {
			throw new WebActionException("无效的客户名称！");
		}
		
		return custDTO;
	}
/**
 * /txtCounty/txtPostcode/txtDetailAddress
 * @param request
 * @return
 * @throws WebActionException
 */
	private AddressDTO getAddressInfo(HttpServletRequest request)
			throws WebActionException {
		AddressDTO addrDTO = new AddressDTO();
		String addressID=request.getParameter("txtCustAddressID");
		String districtID = request.getParameter("txtCounty");
		String postcode = request.getParameter("txtPostcode");
		String detailAddress = request.getParameter("txtDetailAddress");
		String dtlastmod=request.getParameter("txtAddressDTlastmod");
		
		if(WebUtil.StringHaveContent(dtlastmod)){
			addrDTO.setDtLastmod(WebUtil.StringToTimestamp(dtlastmod));
		}

		if(WebUtil.StringHaveContent(addressID)){
			addrDTO.setAddressID(WebUtil.StringToInt(addressID));
		}else{
			throw new WebActionException("无效的地址信息！");
		}
		addrDTO.setPostcode(postcode);
		addrDTO.setDetailAddress(detailAddress);
		if (WebUtil.StringHaveContent(districtID)) {
			addrDTO.setDistrictID(WebUtil.StringToInt(districtID));
		} else {
			throw new WebActionException("无效的客户区域！");
		}
		return addrDTO;
	}

	private CustServiceInteractionDTO getCustServiceInteractionInfo(
			HttpServletRequest request) {
		CustServiceInteractionDTO csiDTO = new CustServiceInteractionDTO();

		String customerID=request.getParameter("txtCustomerID");
		String agentName = request.getParameter("txtAgentName");
		String agentCardType = request.getParameter("txtAgentCardType");
		String agentCardID = request.getParameter("txtAgentCardID");
		String agentTelephone = request.getParameter("txtAgentTelephone");
		String groupCustID=request.getParameter("txtGroupCustID");
		
		//集团客户子客户转个人客户
		csiDTO.setCustomerID(WebUtil.StringToInt(customerID));
		if (WebUtil.StringHaveContent(groupCustID))
			csiDTO.setComments(groupCustID);
		csiDTO.setType(CommonKeys.CUSTSERVICEINTERACTIONTYPE_GM);
		if (WebUtil.StringHaveContent(agentName))
			csiDTO.setAgentName(agentName);
		if (WebUtil.StringHaveContent(agentCardType))
			csiDTO.setAgentCardType(agentCardType);
		if (WebUtil.StringHaveContent(agentCardID))
			csiDTO.setAgentCardID(agentCardID);
		if (WebUtil.StringHaveContent(agentTelephone))
			csiDTO
					.setContactPhone(agentTelephone);

		return csiDTO;
	}

	private AddressDTO getAccountAddressDTO(HttpServletRequest request) {
		AddressDTO addrDTO = new AddressDTO();
		String billCounty = request.getParameter("txtbillCounty");
	

		String billAddress = (request.getParameter("txtBillDetailAddress") == null) ? "" : request.getParameter("txtBillDetailAddress");
		if ("".equals(billAddress)) {
			billAddress = (request.getParameter("txtDetailAddress") == null) ? "": request.getParameter("txtDetailAddress");
		}
		
		
		String billPostcode = (request.getParameter("txtBillPostcode") == null) ? "" : request.getParameter("txtBillPostcode");
		if ("".equals(billPostcode)) {
			billPostcode = (request.getParameter("txtPostcode") == null) ? "": request.getParameter("txtPostcode");
		}

		addrDTO.setPostcode(billPostcode);
		addrDTO.setDetailAddress(billAddress);
		if(WebUtil.StringHaveContent(billCounty))
			addrDTO.setDistrictID(Integer.parseInt(billCounty));

		return addrDTO;
	}

	private NewCustAccountInfoDTO getNewCustAccountInfo(
			HttpServletRequest request) throws WebActionException {
		NewCustAccountInfoDTO acctDTO = new NewCustAccountInfoDTO();

		String mopID = request.getParameter("txtMopID");
		String bankAccount = request.getParameter("txtBankAccount");
		String bankAccountName = request.getParameter("txtBankAccountName");
		String accountType = request.getParameter("txtAccountType");
		String txtAccountName = request.getParameter("txtAccountName");

		acctDTO.setMopID(WebUtil.StringToInt(mopID));
		acctDTO.setBankAccountName(bankAccountName);
		acctDTO.setBankAccount(bankAccount);
		acctDTO.setAccountName(txtAccountName);
		acctDTO.setAccountType(accountType);

		return acctDTO;
	}
  protected void afterSuccessfulResponse(HttpServletRequest request,
			CommandResponse cmdResponse) {
		request.setAttribute("CMDRESPONSECUSTOMERID",
				cmdResponse.getPayload());
	}
}