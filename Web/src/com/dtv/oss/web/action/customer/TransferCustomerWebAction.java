package com.dtv.oss.web.action.customer;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.csr.CustomerEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.DynamicServey;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.CurrentCustomer;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p>Title: BOSS 客户过户</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Digivision</p>
 *
 * @author Wesley Shu
 * @version 1.0
 */
public class TransferCustomerWebAction extends PayFeeWebAction {
	boolean doPost = false;
	protected boolean needCheckToken() {
		return doPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);

		doPost = false;
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
				doPost = true;
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		if (!WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			throw new WebActionException("没有相关的客户信息标识");

		CustServiceInteractionDTO csiDto = null;
		CustomerDTO custDto = null;
		AddressDTO custAddrDto = null;
		AccountDTO acctDto = null;
		CustomerEJBEvent cevent = null;

		//过户类型，Y原地过户，N异地过户
		boolean isCreateJobCard = false;
		String csiType = "";
		int actionType = 0;
		if (WebUtil.StringHaveContent(request.getParameter("transferType"))) {
			if (request.getParameter("transferType")
					.equalsIgnoreCase("vicinal")) {
				isCreateJobCard = true;
				csiType = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_TS;
				actionType = EJBEvent.TRANSFERTOSAMEPLACE;
			} else {
				csiType = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_TD;
				actionType = EJBEvent.TRANSFERTODIFFERENTPLACE;
			}
		} else {
			throw new WebActionException("过户类型丢失");
		}

		//设置客户基本信息
		custDto = new CustomerDTO();
		custDto.setCustomerSerialNo(request.getParameter("txtCustomerSerialNo"));
		custDto.setContractNo(request.getParameter("txtContractNo"));
		custDto.setCustomerID(WebUtil.StringToInt(request
				.getParameter("txtCustomerID")));
		custDto.setCustomerType(request.getParameter("txtCustomerType"));
		custDto.setName(request.getParameter("txtNewName"));
		custDto.setGender(request.getParameter("txtNewGender"));
		custDto.setBirthday(WebUtil.StringToTimestamp(request
				.getParameter("txtNewBirthday")));
		custDto.setCardType(request.getParameter("txtNewCardType"));
		custDto.setCardID(request.getParameter("txtNewCardID"));
		custDto.setTelephone(request.getParameter("txtNewTelephone"));
		custDto.setTelephoneMobile(request
				.getParameter("txtNewTelephoneMobile"));
		custDto.setCatvID(request.getParameter("txtCatvID"));
		custDto.setAddressID(WebUtil.StringToInt(request
				.getParameter("txtAddressID")));
		custDto.setDtLastmod(WebUtil.StringToTimestamp(request
				.getParameter("txtCustomerDTlastmod")));
		custDto.setFax(request.getParameter("txtFaxNumber"));
		custDto.setSocialSecCardID(request.getParameter("txtSocialSecCardID"));
		custDto.setEmail(request.getParameter("txtEmail"));
		custDto.setNationality(request.getParameter("txtNationality"));
		custDto.setLoginID(request.getParameter("txtLoginID"));
		custDto.setLoginPwd(request.getParameter("txtLoginPwd"));
		custDto.setOpenSourceType(request.getParameter("txtOpenSourceType"));

		//设置客户帐户信息
		acctDto = new AccountDTO();
		acctDto.setAccountID(WebUtil.StringToInt(request
				.getParameter("txtCustAccount")));

		//设置受理单信息
		csiDto = new CustServiceInteractionDTO();
		csiDto.setCustomerID(WebUtil.StringToInt(request
				.getParameter("txtCustomerID")));
		csiDto.setAccountID(WebUtil.StringToInt(request
				.getParameter("txtCustAccount")));
		csiDto.setType(csiType);
		String txtContactPhone = "";
		if (WebUtil.StringHaveContent(request.getParameter("txtNewTelephone")))
			txtContactPhone = request.getParameter("txtNewTelephone") + "/";
		if (WebUtil.StringHaveContent(request
				.getParameter("txtNewTelephoneMobile"))) {
			txtContactPhone = txtContactPhone
					+ request.getParameter("txtNewTelephoneMobile");
		} else {
			if (!txtContactPhone.equals("")) {
				txtContactPhone = txtContactPhone.substring(0, txtContactPhone
						.length() - 1);
			}
		}
		csiDto.setContactPhone(txtContactPhone);
		csiDto.setContactPerson(request.getParameter("txtNewName"));
		csiDto.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));

		//设置客户地址信息
		custAddrDto = new AddressDTO();
		custAddrDto.setAddressID(WebUtil.StringToInt(request
				.getParameter("txtAddressID")));
		custAddrDto.setDetailAddress((String) request
				.getParameter("txtDetailAddress"));
		custAddrDto
				.setPostcode((String) request.getParameter("txtNewPostcode"));
		custAddrDto.setDistrictID(WebUtil.StringToInt(request
				.getParameter("txtNewDistrict")));
		custAddrDto.setDtLastmod(WebUtil.StringToTimestamp(request
				.getParameter("txtAddressDTlastmod")));

		//客户市场信息
		int customerID = WebUtil.StringToInt(request.getParameter("txtCustomerID"));
		Collection custMarketInfoList = DynamicServey.getCustomermarketInfo(request,"txtDynamicServey",customerID);

		cevent = new CustomerEJBEvent(actionType);
		cevent.setCsiDto(csiDto);
		cevent.setCustAddrDto(custAddrDto);
		cevent.setCustomerDTO(custDto);
		cevent.setAccountDTO(acctDto);
		cevent.setCustMarketInfoList(custMarketInfoList);
		cevent.setCreateJobCard(isCreateJobCard);
		cevent.setDoPost(doPost);
		//设置客户迁移的费用列表和实际交费列表
		if (doPost) {
			cevent.setCsiFeeList(getSessionFeeList(request));
			int accountID  =WebUtil.StringToInt(request.getParameter("txtCustAccount"));
			getPayList(request,customerID,accountID,
					   cevent.getCsiPaymentList(),
					   CommonKeys.PAYMENTRECORD_TYPE_ACCEPT_CASE,
					   cevent.getCsiPrePaymentDeductionList());		
		}
		return cevent;
	}

	protected void afterSuccessfulResponse(HttpServletRequest request,
			CommandResponse cmdResponse) {
		if(doPost)
		   CurrentCustomer.ClearCurrentCustomer(request);
		super.afterSuccessfulResponse(request, cmdResponse);
		
	}
}
