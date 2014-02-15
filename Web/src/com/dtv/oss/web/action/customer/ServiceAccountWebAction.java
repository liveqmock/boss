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
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.dto.SystemEventDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.DynamicServey;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;

public class ServiceAccountWebAction extends PayFeeWebAction {
	boolean confirmPost = false;

	boolean cleanSideBarInfo = false;

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
		try {
			String txtActionType = request.getParameter("txtActionType");
			ServiceAccountEJBEvent ejbEvent = new ServiceAccountEJBEvent();
			setServiceAccountInfo(request, ejbEvent);
			setCustomerInfo(request, ejbEvent);
			setAccountInfo(request, ejbEvent);
			if (confirmPost) {
				setPayFeeInfo(request, ejbEvent);
			}

			int eJbActionType = 0;
			switch (WebUtil.StringToInt(txtActionType)) {
			case CommonKeys.SERVICE_ACCOUNT_PAUSE:
				eJbActionType = ServiceAccountEJBEvent.SUSPEND;
				ejbEvent.setDoPost(confirmPost);
				setCSIInfo(request, ejbEvent,
						CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UP);
				break;
			case CommonKeys.SERVICE_ACCOUNT_RENT:
				eJbActionType = ServiceAccountEJBEvent.RENT;
				ejbEvent.setDoPost(confirmPost);
				setCSIInfo(request, ejbEvent,
						CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_RT);
				break;
			case CommonKeys.SERVICE_ACCOUNT_CLOSE:
				eJbActionType = ServiceAccountEJBEvent.CLOSE;
				ejbEvent.setDoPost(confirmPost);
				ejbEvent.setIsSendBack(request.getParameter("txtIsSendBack"));
				setCSIInfo(request, ejbEvent,
						CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UC);
				if (confirmPost)
					cleanSideBarInfo = true;
				//������ʽ�ύ��ʱ��,��װǰ̨������豸������Ϣ.
				if (!confirmPost) {
					String deviceFee = request.getParameter("txtDeviceFee");
					if (WebUtil.StringHaveContent(deviceFee) && 0 != WebUtil.StringTodouble(deviceFee)) {
						AccountItemDTO acctItemDto = new AccountItemDTO();
						acctItemDto.setValue(WebUtil.StringTodouble(deviceFee));
						acctItemDto.setAcctItemTypeID("D000000");
						acctItemDto.setForcedDepositFlag(CommonKeys.FORCEDDEPOSITFLAG_N);
						acctItemDto.setAcctID(WebUtil.StringToInt(request.getParameter("txtAccount")));
						acctItemDto.setCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
						acctItemDto.setServiceAccountID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));
						ArrayList feeList=new ArrayList();
						feeList.add(acctItemDto);
						ejbEvent.setDeviceFeeList(feeList);
					}
				}
				break;
			case CommonKeys.SERVICE_ACCOUNT_BEFOREHAND_CLOSE:
				eJbActionType = ServiceAccountEJBEvent.BEFOREHAND_CLOSE;
				ejbEvent.setDoPost(confirmPost);
				ejbEvent.setIsSendBack(request.getParameter("txtIsSendBack"));
				setCSIInfo(request, ejbEvent,CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_SP);
				if (confirmPost)
					cleanSideBarInfo = true;
				//������ʽ�ύ��ʱ��,��װǰ̨������豸������Ϣ.
				if (!confirmPost) {
					String deviceFee = request.getParameter("txtDeviceFee");
					if (WebUtil.StringHaveContent(deviceFee) && 0 != WebUtil.StringTodouble(deviceFee)) {
						AccountItemDTO acctItemDto = new AccountItemDTO();
						acctItemDto.setValue(WebUtil.StringTodouble(deviceFee));
						acctItemDto.setAcctItemTypeID("D000000");
						acctItemDto.setForcedDepositFlag(CommonKeys.FORCEDDEPOSITFLAG_N);
						acctItemDto.setAcctID(WebUtil.StringToInt(request.getParameter("txtAccount")));
						acctItemDto.setCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
						acctItemDto.setServiceAccountID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));
						ArrayList feeList=new ArrayList();
						feeList.add(acctItemDto);
						ejbEvent.setDeviceFeeList(feeList);
					}
				}
				break;
			case CommonKeys.SERVICE_ACCOUNT_REAL_CLOSE:
				eJbActionType = ServiceAccountEJBEvent.REAL_CLOSE;
				ejbEvent.setDoPost(confirmPost);
				ejbEvent.setIsSendBack(request.getParameter("txtIsSendBack"));
				setCSIInfo(request, ejbEvent,CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_RC);
				if (confirmPost)
					cleanSideBarInfo = true;
				break;
			case CommonKeys.SERVICE_ACCOUNT_RESUME:
				eJbActionType = ServiceAccountEJBEvent.RESUME;
				ejbEvent.setDoPost(confirmPost);
				setCSIInfo(request, ejbEvent,
						CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UR);
				String txtCPIDs = request.getParameter("txtCPIDs");
				Collection lstCpId = new ArrayList();
				if (WebUtil.StringHaveContent(txtCPIDs)) {
					String[] cpID = txtCPIDs.split(";");
					if ((cpID != null) && (cpID.length > 0)) {
						for (int i = 0; i < cpID.length; i++)
							lstCpId.add(WebUtil.StringToInteger(cpID[i]));
					}
				}
				ejbEvent.setColPsid(lstCpId);
				break;

			case CommonKeys.SERVICE_ACCOUNT_TRANSFER:
				eJbActionType = ServiceAccountEJBEvent.TRANSFER;

				ejbEvent.setServiceAccountID(WebUtil.StringToInt(request
						.getParameter("txtSAID")));
				ejbEvent.setCustomerID(WebUtil.StringToInt(request
						.getParameter("txtCustomerID")));
				ejbEvent.setAccountID(WebUtil.StringToInt(request
						.getParameter("txtAccount")));

				// ϵͳ���û�����
				if (CommonKeys.SA_TRANFER_TYPE_I.equals(request
						.getParameter("txtTransferType"))) {
					ejbEvent.setDoPost(confirmPost);
					ejbEvent.setTranferSAType(request
							.getParameter("txtTransferType"));
					setCSIInfo(request, ejbEvent,
							CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UT);
					if (confirmPost) {
						ejbEvent.setNewAccountID(WebUtil.StringToInt(request
								.getParameter("txtNewAccountID")));
						ejbEvent.setNewCustomerID(WebUtil.StringToInt(request
								.getParameter("txtNewCustomerID")));
						cleanSideBarInfo = true;
					}
				}
				// ϵͳ���û�����
				else if (CommonKeys.SA_TRANFER_TYPE_O.equals(request
						.getParameter("txtTransferType"))) {
					ejbEvent.setDoPost(confirmPost);
					encapsulateDataForSATranOut(request, ejbEvent);
					ejbEvent.setTranferSAType(request
							.getParameter("txtTransferType"));
					
					if (confirmPost)
						cleanSideBarInfo = true;
				} else
					throw new WebActionException("ҵ���ʻ���������δ֪��");
				break;
			//�ͻ���Ʒ����Ȩ
			case CommonKeys.CUSTOMER_PRODUCT_RESEND:
				eJbActionType = ServiceAccountEJBEvent.REAUTHORIZEPRODUCT;
				ejbEvent.setDoPost(confirmPost);
				break;
			//ҵ���˻�����Ȩ	
			case CommonKeys.SERVICE_ACCOUNT_RESEND:
				eJbActionType = ServiceAccountEJBEvent.RESEND_EMM_BY_SERVICE_ACCOUNT;
				ejbEvent.setDoPost(confirmPost);
				break;
			case CommonKeys.CUSTOMER_PRODUCT_PARTNERSHIP:
				eJbActionType = ServiceAccountEJBEvent.PARTNERSHIP;
				ejbEvent.setDoPost(confirmPost);
				break;
			case CommonKeys.CUSTOMER_PRODUCT_CANCELPARTNERSHIP:
				eJbActionType = ServiceAccountEJBEvent.CANCEL_PARTNERSHIP;
				ejbEvent.setDoPost(confirmPost);
				break;
			case CommonKeys.CUSTOMER_PRODUCT_CLEARPASSWORD:
				eJbActionType = ServiceAccountEJBEvent.CLEARPASSWORD;
				ejbEvent.setDoPost(confirmPost);
				break;
			case CommonKeys.CUSTOMER_PRODUCT_SENDMAIL:
				eJbActionType = ServiceAccountEJBEvent.SEND_EMAIL_CA;
				ejbEvent.setContent(request.getParameter("content"));
				ejbEvent.setDoPost(confirmPost);
				break;
			case CommonKeys.SERVICE_ACCOUNT_CODE_UPDATE:
				eJbActionType = ServiceAccountEJBEvent.SERVICE_ACCOUNT_CODE_UPDATE;
				setServiceAccountCode(request, ejbEvent);
				ejbEvent.setSysEventDto(encapsulateSystemEventDto(request));
				ejbEvent.setDoPost(confirmPost);
				break;
			default:
				break;
			}

			ejbEvent.setActionType(eJbActionType);
			return ejbEvent;
			// return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}

	}

	private void setServiceAccountCode(HttpServletRequest request,
			ServiceAccountEJBEvent ejbEvent) throws WebActionException {
		String phoneNo = request.getParameter("phoneNo");
		String oldPhoneNo = request.getParameter("oldPhoneNo");
		int itemID = WebUtil.StringToInt(request.getParameter("itemID"));
		if (WebUtil.StringHaveContent(phoneNo)) {
			ejbEvent.setServiceAccountCode(phoneNo);
		} else {
			throw new WebActionException("�º���Ϊ�գ�");
		}
		if (WebUtil.StringHaveContent(oldPhoneNo)) {
			ejbEvent.setOldServiceAccountCode(oldPhoneNo);
		} else {
			throw new WebActionException("�ɺ���Ϊ�գ�");
		}
		ejbEvent.setItemID(itemID);
		if (ejbEvent.getServiceAccountCode().equalsIgnoreCase(
				ejbEvent.getOldServiceAccountCode())) {
			throw new WebActionException("�º���;ɺ���һ����");
		}
	}

	private void setServiceAccountInfo(HttpServletRequest request,
			ServiceAccountEJBEvent ejbEvent) throws WebActionException {
		String saID = request.getParameter("txtServiceAccountID");
		
		if (WebUtil.StringHaveContent(saID)) {
			ejbEvent.setServiceAccountID(WebUtil.StringToInt(saID.trim()));
		} else {
			throw new WebActionException("��ָ����Ҫ��ͣ��ҵ���ʻ���");
		}
	}

	private void setCustomerInfo(HttpServletRequest request,
			ServiceAccountEJBEvent ejbEvent) throws WebActionException {
		String customer = request.getParameter("txtCustomerID");
		if (WebUtil.StringHaveContent(customer)) {
			CustomerDTO custDTO = new CustomerDTO();
			custDTO.setCustomerID(WebUtil.StringToInt(customer));
			ejbEvent.setCustomerDTO(custDTO);
			ejbEvent.setCustomerID(WebUtil.StringToInt(customer));
		} else {
			throw new WebActionException("�������ͻ���ѯ����λ�ͻ���");
		}
	}

	private void setAccountInfo(HttpServletRequest request,
			ServiceAccountEJBEvent ejbEvent) throws WebActionException {
		String account = request.getParameter("txtAccount");
		if (WebUtil.StringHaveContent(account)) {
			AccountDTO accountDTO = new AccountDTO();
			accountDTO.setAccountID(WebUtil.StringToInt(account));
			ejbEvent.setAccountDTO(accountDTO);
			ejbEvent.setAccountID(WebUtil.StringToInt(account));
		} 
//		else {
//			throw new WebActionException("��ָ��һ����Ч�ʻ���");
//		}
	}

	private void setPayFeeInfo(HttpServletRequest request,
			ServiceAccountEJBEvent ejbEvent) throws WebActionException {
		ejbEvent.setFeeList(getSessionFeeList(request));
		int customerID =WebUtil.StringToInt(request.getParameter("txtCustomerID"));
		int accountID =WebUtil.StringToInt(request.getParameter("txtAccount"));
		getPayList(request,customerID,accountID,
				   ejbEvent.getPaymentList(),
				   CommonKeys.PAYMENTRECORD_TYPE_ACCEPT_CASE,
				   ejbEvent.getCsiPrePaymentDeductionList());
		
	}

	private void setCSIInfo(HttpServletRequest request,
			ServiceAccountEJBEvent ejbEvent, String csiType) {
		// ������Ϣ
		com.dtv.oss.dto.CustServiceInteractionDTO csiDTO = new com.dtv.oss.dto.CustServiceInteractionDTO();
		csiDTO.setType(csiType);
		if (WebUtil.StringHaveContent(request.getParameter("txtPreferedTime")))
			csiDTO.setPreferedTime(request.getParameter("txtPreferedTime"));
		if (WebUtil.StringHaveContent(request.getParameter("txtPreferedDate")))
			csiDTO.setPreferedDate(WebUtil.StringToTimestamp(request.getParameter("txtPreferedDate")));
		if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
			csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount")));
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
			csiDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
	
		if (WebUtil.StringHaveContent(request.getParameter("txtcsiPayOption")))
            csiDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
		if (WebUtil.StringHaveContent(request.getParameter("txtCsiCreateReason")))
		{    
			csiDTO.setCreateReason(request.getParameter("txtCsiCreateReason"));
			
			//zyg 2007.11.22 ��Ҫͬʱ���� ������ statusreason �ֶ�
			csiDTO.setStatusReason(request.getParameter("txtCsiCreateReason"));
		}
		if (WebUtil.StringHaveContent(request.getParameter("txtComments")))
            csiDTO.setComments(request.getParameter("txtComments"));
		
		ejbEvent.setCsiDto(csiDTO);
	}
	
	//���ù�����Ϣ
	private void setCATVJobCardInfo(HttpServletRequest request,
			ServiceAccountEJBEvent ejbEvent , String subtype) throws WebActionException {
		//������Ϣ
		JobCardDTO jobCardDTO = new JobCardDTO();	
		//��������
	    jobCardDTO.setJobType(CommonKeys.JOBCARD_TYPE_CATVNETWORK);
	     // ����������
	    jobCardDTO.setSubType(subtype);
	    //��������
	    if(CommonKeys.JOBCARD_SUBTYPE_F.equals(subtype))
	    	jobCardDTO.setDescription("�����ҵ��");
	    else if(CommonKeys.JOBCARD_SUBTYPE_H.equals(subtype))
	    	jobCardDTO.setDescription("�ָ���ͨ��ҵ��");
	     //��ת����
		 if (WebUtil.StringHaveContent(request.getParameter("transferType"))){
			   if(("auto").equalsIgnoreCase(request.getParameter("transferType"))){				   
				   jobCardDTO.setProcessOrgId(WebUtil.StringToInt(request.getParameter("txtAutoNextOrgID")));
			   }else if(("manual").equalsIgnoreCase(request.getParameter("transferType"))){
			   		jobCardDTO.setProcessOrgId(WebUtil.StringToInt(request.getParameter("txtNextOrgID")));
			   }
		 }
	    //�����ʻ�id
	      if (WebUtil.StringHaveContent(request.getParameter("txtAccount")))
	    	  jobCardDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount")));
		ejbEvent.setJobCardDto(jobCardDTO);
	}
	

	// ϵͳ���û��������û������ݷ�װ
	private void encapsulateDataForSATranOut(HttpServletRequest request,
			ServiceAccountEJBEvent event) throws WebActionException {

		// �ͻ���Ϣ
		CustomerDTO customerDTO = new CustomerDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtBirthday")))
			customerDTO.setBirthday(WebUtil.StringToTimestamp(request
					.getParameter("txtBirthday")));
		customerDTO.setCardID(request.getParameter("txtCardID"));
		customerDTO.setCardType(request.getParameter("txtCardType"));
		customerDTO.setCatvID(request.getParameter("txtCatvID"));
		customerDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
		customerDTO.setCustomerType(request.getParameter("txtCustType"));
		customerDTO.setEmail(request.getParameter("txtEmail"));
		customerDTO.setFax(request.getParameter("txtFaxNumber"));
		customerDTO.setGender(request.getParameter("txtGender"));
		customerDTO.setName(request.getParameter("txtName"));
		customerDTO.setNationality(request.getParameter("txtNationality"));
		customerDTO.setOpenSourceType(request.getParameter("txtOpenSourceType"));
		customerDTO.setOpenSourceTypeID(WebUtil.StringToInt(request
				.getParameter("txtOpenSourceID")));
		customerDTO.setSocialSecCardID(request
				.getParameter("txtSocialSecCardID"));
		customerDTO.setTelephone(request.getParameter("txtTelephone"));
		customerDTO.setTelephoneMobile(request.getParameter("txtMobileNumber"));
		if (WebUtil.StringHaveContent(request.getParameter("txtContractNo"))) 
			   customerDTO.setContractNo(request.getParameter("txtContractNo"));
		event.setCustomerDTO(customerDTO);

		// �¿ͻ��˻���Ϣ
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setMopID(WebUtil.StringToInt(request
				.getParameter("txtMopID")));
		accountDTO.setAccountName(request.getParameter("txtAccountName"));
		accountDTO.setBankAccount(request.getParameter("txtBankAccount"));
		accountDTO.setBankAccountName(request
				.getParameter("txtBankAccountName"));
		accountDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
		accountDTO.setAccountType(request.getParameter("txtAccountType"));
		event.setAccountDTO(accountDTO);

		// ������Ϣ
		CustServiceInteractionDTO csiDTO = new CustServiceInteractionDTO();
		String txtContactPhone = "";
		if (WebUtil.StringHaveContent(request.getParameter("txtTelephone")))
			txtContactPhone = request.getParameter("txtTelephone") + "/";
		if (WebUtil.StringHaveContent(request.getParameter("txtMobileNumber"))) {
			txtContactPhone = txtContactPhone
					+ request.getParameter("txtMobileNumber");
		} else {
			if (!txtContactPhone.equals(""))
				txtContactPhone = txtContactPhone.substring(0, txtContactPhone
						.length() - 1);
		}
		csiDTO.setContactPhone(txtContactPhone);
		csiDTO.setContactPerson(request.getParameter("txtName"));
		csiDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
		csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UT);
		csiDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		csiDTO.setAccountID(WebUtil.StringToInt(request.getParameter("txtAccount")));
	
		csiDTO.setInstallationType(CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL);
		
		if (WebUtil.StringHaveContent(request.getParameter("txtCsiCreateReason")))
		{
			csiDTO.setCreateReason(request.getParameter("txtCsiCreateReason"));
			//zyg 2007.11.22 ��Ҫͬʱ���� ������ statusreason �ֶ�
			csiDTO.setStatusReason(request.getParameter("txtCsiCreateReason"));
		}
		
		if (WebUtil.StringHaveContent(request.getParameter("txtcsiPayOption")))
	        csiDTO.setBillCollectionMethod(request.getParameter("txtcsiPayOption"));
		
		event.setCsiDto(csiDTO);

		// ��ַ��Ϣ
		String txtDetailAddress = (request.getParameter("txtDetailAddress") == null) ? ""
				: request.getParameter("txtDetailAddress");
		String txtBillDetailAddress = (request
				.getParameter("txtBillDetailAddress") == null) ? "" : request
				.getParameter("txtBillDetailAddress");

		String billAddress = "";
		if (!txtBillDetailAddress.equals("")) {
			billAddress = txtBillDetailAddress;
		}
		if (txtBillDetailAddress.equals("") && !txtDetailAddress.equals("")) {
			billAddress = txtDetailAddress;
		}

		// ��ַ����
		String txtCounty = (request.getParameter("txtCounty") == null) ? ""
				: request.getParameter("txtCounty");
		String txtbillCounty = (request.getParameter("txtbillCounty") == null) ? ""
				: request.getParameter("txtbillCounty");
		String county = "";
		if (!txtbillCounty.equals(""))
			county = txtbillCounty;
		if (txtbillCounty.equals("") && !(txtCounty.equals("")))
			county = txtCounty;

		// �ͻ���װ��ַ
		com.dtv.oss.dto.AddressDTO custAddressDTO = new com.dtv.oss.dto.AddressDTO();
		custAddressDTO.setPostcode(request.getParameter("txtPostcode"));
		custAddressDTO.setDetailAddress(txtDetailAddress);
		custAddressDTO.setDistrictID(WebUtil.StringToInt(txtCounty));
		event.setCustAddrDTO(custAddressDTO);

		// �ͻ��ʵ���ַ
		com.dtv.oss.dto.AddressDTO acctAddressDTO = new com.dtv.oss.dto.AddressDTO();
		acctAddressDTO.setPostcode(request.getParameter("txtBillPostcode"));
		acctAddressDTO.setDetailAddress(billAddress);
		acctAddressDTO.setDistrictID(WebUtil.StringToInt(county));
		event.setAccAddrDTO(acctAddressDTO);

		// �ͻ��г���Ϣ
		Collection custMarketInfoList = DynamicServey.getNewCustomerMarketInfo(
				request, "txtDynamicServey");

		event.setCustMarketInfoList(custMarketInfoList);
	}

	protected void afterSuccessfulResponse(HttpServletRequest request,
			CommandResponse cmdResponse) {
		if (cleanSideBarInfo) {
			// request.getSession().removeAttribute(CommonKeys.CURRENT_CUSTOMER_SESSION_NAME);
			request.getSession().removeAttribute(WebKeys.SIDERBARMENUID);
			request.getSession().removeAttribute(WebKeys.SIDERBARTDID);
		}
	}

	private SystemEventDTO encapsulateSystemEventDto(HttpServletRequest request) {
		SystemEventDTO dto = new SystemEventDTO();
		String serviceAccountID = request.getParameter("txtServiceAccountID");
		String accountID = request.getParameter("txtAccount");
		String customerID = request.getParameter("txtCustomerID");
		String strProductID = request.getParameter("txtSystemEventProductID");
		String phoneNo = request.getParameter("phoneNo");
		String oldPhoneNo = request.getParameter("oldPhoneNo");

		String deviceID = request.getParameter("txtDeviceID");

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "serviceAccountID"
				+ serviceAccountID);
		LogUtility
				.log(this.getClass(), LogLevel.DEBUG, "accountID" + accountID);
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "customerID"
				+ customerID);
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "deviceID" + deviceID);
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "strProductID"
				+ strProductID);

		if (WebUtil.StringHaveContent(serviceAccountID)) {
			dto.setServiceAccountID(WebUtil.StringToInt(serviceAccountID));
		}
		if (WebUtil.StringHaveContent(accountID)) {
			dto.setAccountID(WebUtil.StringToInt(accountID));
		}
		if (WebUtil.StringHaveContent(customerID)) {
			dto.setCustomerID(WebUtil.StringToInt(customerID));
		}
		if (WebUtil.StringHaveContent(deviceID)) {
			dto.setScDeviceID(WebUtil.StringToInt(deviceID));
		}
		if (WebUtil.StringHaveContent(strProductID)) {
			dto.setProductID(WebUtil.StringToInt(strProductID));
		}
		if (WebUtil.StringHaveContent(phoneNo)) {
			dto.setServiceCode(phoneNo);
		}
		if (WebUtil.StringHaveContent(oldPhoneNo)) {
			dto.setOldServiceCode(oldPhoneNo);
		}
		return dto;
	}
}
