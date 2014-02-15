package com.dtv.oss.web.action.groupcustomer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.web.util.*;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.ContractDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.NewCustAccountInfoDTO;
import com.dtv.oss.dto.NewCustomerInfoDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.service.ejbevent.groupcustomer.GroupCustomerEJBEvent;

/**
 * 
 * @author 260327h 集团客户开户
 */
public class GroupCustomerOpenWebAction extends PayFeeWebAction {

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
		GroupCustomerEJBEvent event = new GroupCustomerEJBEvent();

		String actionType = request.getParameter("txtActionType");

		if (actionType == null)
			return null;

		try {
			if (actionType.equalsIgnoreCase("openInfo")) {
				event.setContractDTO(getContractInfo(request));
				return null;
			} else if (actionType.equalsIgnoreCase("openFee")
					|| actionType.equalsIgnoreCase("openConfirm")) {
				event.setContractDTO(getContractInfo(request));
				event.setNewCustomerInfoDTO(getNewCustomerInfo(request));
				event.setAddressDTO(getAddressInfo(request));
				event.setCsiDto(getCustServiceInteractionInfo(request));
				event.setNewCustAccountInfoDTO(getNewCustAccountInfo(request));
				event.setAccountAddressDTO(getAccountAddressDTO(request));

				if (actionType.equalsIgnoreCase("openFee")) {
					event.setActionType(GroupCustomerEJBEvent.GROUPCUSTOMEROPENFEE);
				} else {
					event.setFeeList(getSessionFeeList(request)) ;
					getPayAndPrePayList(request,0,0,event.getPaymentList(),event.getCsiPrePaymentDeductionList());
					event.setActionType(GroupCustomerEJBEvent.GROUPCUSTOMEROPENCONFIRM);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}		
		return event;
	}

	private ContractDTO getContractInfo(HttpServletRequest request)
			throws WebActionException {
		ContractDTO contractDTO = new ContractDTO();
		String contractNo = request.getParameter("txtContractNo");
		if (WebUtil.StringHaveContent(contractNo)) {
			contractDTO.setContractNo(contractNo);
		} else {
			throw new WebActionException("没有有效的合同！");
		}
		return contractDTO;
	}

	private NewCustomerInfoDTO getNewCustomerInfo(HttpServletRequest request)
			throws WebActionException {
		NewCustomerInfoDTO custDTO = new NewCustomerInfoDTO();
		String name = request.getParameter("txtName");
		String type = request.getParameter("txtType");
		String telephone = request.getParameter("txtTelephone");
		String openSourceType = request.getParameter("txtOpenSourceType");
		String openSourceID = request.getParameter("txtOpenSourceID");

		custDTO.setType(type);
		custDTO.setTelephone(telephone);
		if(WebUtil.StringHaveContent(openSourceID)){
			custDTO.setOpenSourceID(Integer.parseInt(openSourceID));
		}

		if (WebUtil.StringHaveContent(openSourceType)) {
			custDTO.setOpenSourceType(openSourceType);
		} else {
			throw new WebActionException("无效的客户来源！");
		}
		if (WebUtil.StringHaveContent(name)) {
			custDTO.setName(name);
		} else {
			throw new WebActionException("无效的客户名称！");
		}
		
		String agentName = request.getParameter("txtAgentName");
		String agentCardType = request.getParameter("txtAgentCardType");
		String agentCardID = request.getParameter("txtAgentCardID");
		String agentTelephone = request.getParameter("txtAgentTelephone");

		if (WebUtil.StringHaveContent(agentName))
			custDTO.setAgentName(agentName);
		if (WebUtil.StringHaveContent(agentCardType))
			custDTO.setCardType(agentCardType);
		if (WebUtil.StringHaveContent(agentCardID))
			custDTO.setCardID(agentCardID);
		if (WebUtil.StringHaveContent(agentTelephone))
			custDTO.setMobileNumber(agentTelephone);

		return custDTO;
	}

	private AddressDTO getAddressInfo(HttpServletRequest request)
			throws WebActionException {
		AddressDTO addrDTO = new AddressDTO();
		String districtID = request.getParameter("txtDistrict");
		String postcode = request.getParameter("txtPostcode");
		String detailAddress = request.getParameter("txtDetailAddress");

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

		String agentName = request.getParameter("txtAgentName");
		String agentCardType = request.getParameter("txtAgentCardType");
		String agentCardID = request.getParameter("txtAgentCardID");
		String agentTelephone = request.getParameter("txtAgentTelephone");

		//集团客户开户
		csiDTO.setType(CommonKeys.CUSTSERVICEINTERACTIONTYPE_GO);
		if (WebUtil.StringHaveContent(agentName))
			csiDTO.setAgentName(agentName);
		if (WebUtil.StringHaveContent(agentCardType))
			csiDTO.setAgentCardType(agentCardType);
		if (WebUtil.StringHaveContent(agentCardID))
			csiDTO.setAgentCardID(agentCardID);
		if (WebUtil.StringHaveContent(agentTelephone))
			csiDTO.setContactPhone(agentTelephone);

		// 集团客户所有安装均为自安装
		csiDTO.setInstallationType(CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL);
		
		if (WebUtil.StringHaveContent(request.getParameter("txtcsiPayOption")))
	        csiDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
		return csiDTO;
	}

	private AddressDTO getAccountAddressDTO(HttpServletRequest request) {
		AddressDTO addrDTO = new AddressDTO();

		String txtDetailAddress = (request.getParameter("txtDetailAddress") == null) ? ""
				: request.getParameter("txtDetailAddress");//txtPostcode
		String txtBillDetailAddress = (request
				.getParameter("txtBillDetailAddress") == null) ? "" : request
				.getParameter("txtBillDetailAddress");
		String billPostcode = request.getParameter("txtBillPostcode")==null?"":request.getParameter("txtBillPostcode");
		String billCounty = request.getParameter("txtbillCounty");

		String billAddress = "";
		if (!txtBillDetailAddress.equals("")) {
			billAddress = txtBillDetailAddress;
		}else
		{
			billAddress = txtDetailAddress;
		}
		
		if("".equals(billPostcode))
		{
			billPostcode = request.getParameter("txtPostcode")==null?"":request.getParameter("txtPostcode");
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