package com.dtv.oss.web.action.customer;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.domain.Customer;
import com.dtv.oss.domain.CustomerHome;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.CurrentCustomer;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.util.Postern;
/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Digivision</p>
 *
 * @author Wesley Shu
 * @version 1.0
 */
public class MoveCustomerWebAction extends PayFeeWebAction {
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

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
        if (!WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
            throw new WebActionException("没有相关的客户信息标识");
    	if (!WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
			throw new WebActionException("没有相关的客户类型");
    	
        CustServiceInteractionDTO csiDto = null;
        CustomerEJBEvent cevent = null;
        boolean doPost = false;
        if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
            if (request.getParameter("confirm_post").equalsIgnoreCase("true")) doPost = true;
        }
        int privateActionType = 0;
        privateActionType = EJBEvent.MOVETODIFFERENTPLACE;
          
        //客户受理单信息设置
        csiDto = new CustServiceInteractionDTO();
        csiDto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        csiDto.setAccountID(WebUtil.StringToInt(request.getParameter("txtCustAccount")));
        csiDto.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_MD);
        csiDto.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
       
        //客户地址信息设置
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressID(WebUtil.StringToInt(request.getParameter("txtAddressID")));
        addressDTO.setDetailAddress((String) request.getParameter("txtDetailAddress"));
        addressDTO.setPostcode((String) request.getParameter("txtPostcode"));
        addressDTO.setDistrictID(WebUtil.StringToInt(request.getParameter("txtNewDistrict")));
        addressDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtAddressDTlastmod")));
        
        //客户信息设置
        CustomerDTO customerDTO = new CustomerDTO();
        // ************填充 newcustomerinfo 需要,customerDTO的其余字段也得设置********** /
		if (request.getParameter("txtCustomerID") != null){
			CustomerDTO custParameter = Postern.getCustomerByID(Integer.parseInt((String) request.getParameter("txtCustomerID")));
			customerDTO.setNationality(custParameter.getNationality());
			customerDTO.setGender(custParameter.getGender());
			customerDTO.setName(custParameter.getName());
			customerDTO.setBirthday(custParameter.getBirthday());
			customerDTO.setCardType(custParameter.getCardType());
			customerDTO.setCardID(custParameter.getCardID());
			customerDTO.setLoginID(custParameter.getLoginID());
			customerDTO.setLoginPwd(custParameter.getLoginPwd());
			customerDTO.setComments(custParameter.getComments());
			customerDTO.setSocialSecCardID(custParameter.getSocialSecCardID());
			customerDTO.setFax(custParameter.getFax());
			customerDTO.setEmail(custParameter.getEmail());
			customerDTO.setOpenSourceType(custParameter.getOpenSourceType());
			customerDTO.setOpenSourceTypeID(custParameter.getOpenSourceTypeID());
		}
		// ***********结束 ******************************/
        customerDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        customerDTO.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtCustomerDTlastmod")));
        customerDTO.setTelephone(request.getParameter("txtTelephone"));
        customerDTO.setTelephoneMobile(request.getParameter("txtTelephoneMobile"));
        customerDTO.setCustomerType(request.getParameter("txtCustomerType"));
        customerDTO.setCatvID ( request.getParameter("txtCatvID"));
        customerDTO.setAddressID(WebUtil.StringToInt(request.getParameter("txtAddressID")));
        customerDTO.setCustomerSerialNo(request.getParameter("txtCustomerSerialNo"));
        customerDTO.setContractNo(request.getParameter("txtContractNo"));
        //客户帐户信息设置
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtCustAccount")));

        cevent = new CustomerEJBEvent(privateActionType);
        cevent.setCsiDto(csiDto);
        cevent.setCustAddrDto(addressDTO);
        cevent.setCustomerDTO(customerDTO);
        cevent.setAccountDTO(accountDTO);
        cevent.setDoPost(doPost);
        
        //设置费用
        if(doPost){
			cevent.setCsiFeeList(getSessionFeeList(request));
			int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
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