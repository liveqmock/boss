/*
 * Created on 2005-11-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.command.csr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dtv.oss.domain.Address;
import com.dtv.oss.domain.Customer;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.custom.CommonFeeAndPayObject;
import com.dtv.oss.dto.wrap.customer.CustomerInfoWrap;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.component.BusinessRuleService;
import com.dtv.oss.service.component.CSIService;
import com.dtv.oss.service.component.CampaignService;
import com.dtv.oss.service.component.CustomerService;
import com.dtv.oss.service.component.FeeService;
import com.dtv.oss.service.component.ImmediatePayFeeService;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.component.ServiceAccountService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerEJBEvent;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.PayFeeUtil;
import com.dtv.oss.service.util.SystemEventRecorder;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.service.util.ImmediateFee.ImmediateFeeCalculator;
import com.dtv.oss.service.util.ImmediateFee.ImmediateFeeList;

/**
 * �ͻ���صĲ��� author ��zhouxushun date : 2005-11-14 description:
 * 
 * @author 250713z
 * 
 */
public class CustomerCommand extends Command {
	private static final Class clazz = CustomerCommand.class;

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		CustomerEJBEvent inEvent = (CustomerEJBEvent) ev;
		this.operatorID = inEvent.getOperatorID();
		this.machineName = inEvent.getRemoteHostAddress();
		LogUtility.log(clazz, LogLevel.DEBUG, "Enter " + clazz.getName()
				+ " execute() now.");
		try {
			switch (inEvent.getActionType()) {
			// �޸Ŀͻ���Ϣ
			case EJBEvent.ALTERCUSTOMERINFO:
				alterCustomerInfo(inEvent);
				break;
			// Ǩ��
			case EJBEvent.MOVETODIFFERENTPLACE:
				moveUserToDifferentPlace(inEvent);
				break;
			// ��ع���
			case EJBEvent.TRANSFERTODIFFERENTPLACE:
				transferCustomer(inEvent);
				break;
			// ԭ�ع���
			case EJBEvent.TRANSFERTOSAMEPLACE:
				transferCustomer(inEvent);
				break;
			// ����
			case EJBEvent.CLOSE_CUSTOMER:
				closeCustomer(inEvent);
				break;
			// �˻�
			case EJBEvent.WITHDRAW:
				withdraw(inEvent);
				break;
			// �ͻ��������û�����Ȩ
			case EJBEvent.REAUTHORIZE_FOR_SA_BY_CUSTOMER:
				reauthorizeForSAByCustomer(inEvent);
				break;
			case EJBEvent.CUSTOMER_PROTOCOL:
				updateProtocol(inEvent);
				break;
			default:
				throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
			}
		} catch (ServiceException ce) {
			LogUtility.log(clazz, LogLevel.ERROR, this, ce);
			throw new CommandException(ce.getMessage());
		} catch (Throwable unkown) {
			LogUtility.log(clazz, LogLevel.FATAL, this, unkown);
			throw new CommandException("δ֪����");
		}
		return response;
	}

	
	/**
	 * �Կͻ��µ������û���������Ȩ
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void reauthorizeForSAByCustomer(CustomerEJBEvent inEvent)
	throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);
		Collection saIDCol=BusinessUtility.getSaIDColByCustID(inEvent.getCustomerDTO().getCustomerID());
		if(!saIDCol.isEmpty()){
			Iterator saIDIter=saIDCol.iterator();
			//��������Ȩ
			ServiceAccountService saService=new ServiceAccountService(serviceContext);
			saService.setOperatorID(operatorID);
			saService.batchResendEMM(SystemEventRecorder.SE_CA_SERVICEACCOUNT_RESENDEMM);
		}
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent.getCustomerDTO().getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "������Ʒ����Ȩ", "��Ȩ��ҵ���ʻ�....", SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * ¼��Э���
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void createProtocol(CustomerEJBEvent inEvent)throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);
		BusinessUtility.createProtocol(inEvent.getProtocolList());
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent.getCustomerDTO().getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "Э���¼��", "¼��Э���", SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * ����Э���
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void updateProtocol(CustomerEJBEvent inEvent)throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);
		BusinessUtility.deleteProtocol(inEvent.getCustomerDTO().getCustomerID());
		BusinessUtility.createProtocol(inEvent.getProtocolList());
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent.getCustomerDTO().getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "Э���¼��", "¼��Э���", SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * �޸Ŀͻ���Ϣ
	 * 
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void alterCustomerInfo(CustomerEJBEvent inEvent)
			throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);

		// ȡ���������ͣ�����ServiceContext
		if (inEvent.getCsiDto() != null)
			serviceContext.put(Service.CSI_TYPE, inEvent.getCsiDto().getType());
		// ��ȡ����DTO
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		// ��ȡ�ͻ���ַ��custAddressDTO
		AddressDTO custAddressDTO = inEvent.getCustAddressDTO();
		// ��ȡcustomerDTO
		CustomerDTO customerDTO = inEvent.getCustomerDTO();
		// ��ȡdoPost
		boolean doPost = inEvent.isDoPost();

		if (!doPost)
			return;

		CustomerInfoWrap oldCustomerInfo = BusinessUtility.getCustomerInfoWrapByID(customerDTO.getCustomerID());
		// ������������ض���
		CSIService csiService = new CSIService(serviceContext);
		csiService.createCustServiceInteraction(csiDTO);
		
		// ���¿ͻ���Ϣ
		CustomerService customerService = new CustomerService(serviceContext);
		customerService.updateCustomer(customerDTO, custAddressDTO, inEvent
				.getCustMarketInfoList(),true);
		
		// ��¼ϵͳ��־//��־��¼����ԭ����customerService��,�ɺ�12-06�Ƶ�����
		//ȡ��ԭ���Ŀͻ���Ϣ,���µĿͻ���Ϣ���бȽ�,��д��־����.
		Customer customer=(Customer) serviceContext.get(Service.CUSTOMER);
		Address address=(Address) serviceContext.get(Service.CUSTOMER_ADDRESS_EJB);
		int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		String custStyle = null;
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		custStyle = businessRuleService.getCustStyle(customerDTO
				.getCustomerID());
		StringBuffer logDesc=new StringBuffer();
		logDesc.append("�޸Ŀͻ�����,");
		logDesc.append("����ID:");
		logDesc.append(csiID);
		logDesc.append(";�޸Ŀͻ�����,�ͻ�ID:");
		logDesc.append(customer.getCustomerID());

		logDesc.append(SystemLogRecorder.appendDescString(";�ͻ�����:",
				oldCustomerInfo.getCustomerTypeDesc(), BusinessUtility
						.getCommonNameByKey("SET_C_CUSTOMERTYPE", customer
								.getCustomerType())));
		logDesc.append(SystemLogRecorder.appendDescString(";����:",
				oldCustomerInfo.getCustDto().getName(), 
				customer.getName()));
		logDesc.append(SystemLogRecorder.appendDescString(";֤������:",
				oldCustomerInfo.getCustomerCardTypeDesc(), 
				BusinessUtility.getCommonNameByKey("SET_C_CUSTOMERCARDTYPE",customer.getCardType())));
		logDesc.append(SystemLogRecorder.appendDescString(";֤������:",
				oldCustomerInfo.getCustDto().getCardID(), 
				customer.getCardID()));
		logDesc.append(SystemLogRecorder.appendDescString(";�̶��绰:",
				oldCustomerInfo.getCustDto().getTelephone(), 
				customer.getTelephone()));
		logDesc.append(SystemLogRecorder.appendDescString(";�ƶ��绰:",
				oldCustomerInfo.getCustDto().getTelephoneMobile(), 
				customer.getTelephoneMobile()));

		if(address!=null){
			logDesc.append(SystemLogRecorder.appendDescString(";����:",
					oldCustomerInfo.getDistrictSettingDTO().getName(), 
					BusinessUtility.getDistrictNameById(address.getDistrictID())));
			logDesc.append(SystemLogRecorder.appendDescString(";��ϸ��ַ:",
					oldCustomerInfo.getAddrDto().getDetailAddress(), 
					address.getDetailAddress()));
			logDesc.append(SystemLogRecorder.appendDescString(";�ʱ�:",
					oldCustomerInfo.getAddrDto().getPostcode(), 
					address.getPostcode()));
		}
		logDesc.append(SystemLogRecorder.appendDescString(";��Դ����:",
				BusinessUtility.getCommonNameByKey("SET_C_CUSTOPENSOURCETYPE",oldCustomerInfo.getCustDto().getOpenSourceType()), 
				BusinessUtility.getCommonNameByKey("SET_C_CUSTOPENSOURCETYPE",customer.getOpenSourceType())));
		logDesc
				.append(SystemLogRecorder.appendDescString(";ģ���û�����:",
						oldCustomerInfo.getCustDto().getCatvID(), customer
								.getCatvID()));
		logDesc.append(SystemLogRecorder.appendDescString(";����:",
				BusinessUtility.getCommonNameByKey("SET_C_NATIONALITY",
						oldCustomerInfo.getCustDto().getNationality()),
				BusinessUtility.getCommonNameByKey("SET_C_NATIONALITY",
						customer.getNationality())));
		logDesc.append(SystemLogRecorder.appendDescString(";�籣�����:",
				oldCustomerInfo.getCustDto().getSocialSecCardID(), customer
						.getSocialSecCardID()));

		logDesc.append(SystemLogRecorder.appendDescString(";�Ա�:",
				BusinessUtility.getCommonNameByKey("SET_C_CUSTOMERGENDER",
						oldCustomerInfo.getCustDto().getGender()),
				BusinessUtility.getCommonNameByKey("SET_C_CUSTOMERGENDER",
						customer.getGender())));
		logDesc.append(SystemLogRecorder.appendDescString(";Email ��ַ:",
				oldCustomerInfo.getCustDto().getEmail(), customer.getEmail()));

		logDesc.append(SystemLogRecorder.appendDescString(";Э����:",
				oldCustomerInfo.getCustDto().getContractNo(), customer
						.getContractNo()));
		logDesc.append(SystemLogRecorder.appendDescString(";����:",
				oldCustomerInfo.getCustDto().getFax(), customer.getFax()));
		logDesc.append(SystemLogRecorder.appendDescString(";��������:",
				oldCustomerInfo.getCustDto().getBirthday()!=null?oldCustomerInfo.getCustDto().getBirthday().toLocaleString():null,
				customer.getBirthday()!=null?customer.getBirthday().toLocaleString():null));
				
		logDesc.append(SystemLogRecorder.appendDescString(";��½��:",
				oldCustomerInfo.getCustDto().getLoginID(), 
				customer.getLoginID()));
		logDesc.append(SystemLogRecorder.appendDescString(";��½����:",
				oldCustomerInfo.getCustDto().getLoginPwd(), 
				customer.getLoginPwd()));
		logDesc.append(SystemLogRecorder.appendDescString(";�ͻ����ϱ�ע:",
				oldCustomerInfo.getCustDto().getComments(), 
				customer.getComments()));
		String sysModule="";
		if ("G".equalsIgnoreCase(custStyle)) {
			sysModule=SystemLogRecorder.LOGMODULE_GROUPCUSTOMER;
		} else{
			sysModule=SystemLogRecorder.LOGMODULE_CUSTSERV;
		}
		SystemLogRecorder.createSystemLog(machineName, operatorID,
				customerDTO.getCustomerID(),
				sysModule, "�޸Ŀͻ�����",
				logDesc.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * Ǩ��
	 * 
	 * @param inEvent
	 * @throws CommandException
	 */
	private void moveUserToDifferentPlace(CustomerEJBEvent inEvent)
			throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);
		boolean doPost = inEvent.isDoPost();
		BusinessRuleService brService = new BusinessRuleService(serviceContext);
		brService.checkOperationCustomer(inEvent);
		CustomerDTO customerDTO = inEvent.getCustomerDTO();

		// �Ƿ���ʵ�ύ
		if (!doPost) {
			//�Ʒ�
			CommonFeeAndPayObject paramObj=PublicService.encapsulateBaseParamForExitCust(inEvent.getCsiDto(), inEvent.getCsiDto().getCustomerID(),inEvent.getCsiDto().getAccountID());
			Collection  currentFeeList=FeeService.calculateImmediateFeeForCustService(inEvent.getCsiDto(),null,null,paramObj);
			this.response.setPayload(currentFeeList);
			LogUtility.log(CustomerCommand.class, LogLevel.DEBUG,
					"����moveUserToDifferentPlace����");
			return;
		}
		// ������������ض���
		CSIService csiService = new CSIService(serviceContext);
		csiService.moveCustomer(inEvent);

		//У�������ʺ�,wp@20080227    	
		
		AccountDTO accountDTO=inEvent.getAccountDTO();
		if(accountDTO.getBankAccount()!=null && !"".equals(accountDTO.getBankAccount())){
			if(accountDTO.getMopID()!=0){
				String functionResult = BusinessUtility.callFunctionForCheckBankAccount(accountDTO.getMopID(),accountDTO.getBankAccount());
				if(!"true".equals(functionResult))
					throw new ServiceException(functionResult);
			}
		}

		//�������֧��
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		Map returnMap = new HashMap();
		returnMap.put("CustServiceInteractionID", serviceContext
				.get(Service.CSI_ID));
		returnMap.put("CustomerID", new Integer(customerDTO.getCustomerID()));

		this.response.setPayload(returnMap);
	}

	/**
	 * ��������ԭ�ع�������ع���
	 * 
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void transferCustomer(CustomerEJBEvent inEvent)
			throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);

		boolean doPost = inEvent.isDoPost();
		BusinessRuleService brService = new BusinessRuleService(serviceContext);
		brService.checkOperationCustomer(inEvent);
		CustomerDTO customerDTO = inEvent.getCustomerDTO();

		// �Ƿ���ʵ�ύ
		if (!doPost) {
			//�Ʒ�
			CommonFeeAndPayObject paramObj=PublicService.encapsulateBaseParamForExitCust(inEvent.getCsiDto(), inEvent.getCsiDto().getCustomerID(),inEvent.getCsiDto().getAccountID());
			Collection  currentFeeList=FeeService.calculateImmediateFeeForCustService(inEvent.getCsiDto(),null,null,paramObj);
			this.response.setPayload(currentFeeList);
			LogUtility.log(CustomerCommand.class, LogLevel.DEBUG,
					"����transferCustomer����");
			return;
		}

		// ������������ض���
		CSIService csiService = new CSIService(serviceContext);
		csiService.transferCustomer(inEvent);
		//�������֧��
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		Map returnMap = new HashMap();
		returnMap.put("CustServiceInteractionID", serviceContext
				.get(Service.CSI_ID));
		returnMap.put("CustomerID", new Integer(customerDTO.getCustomerID()));

		this.response.setPayload(returnMap);

	}

	/**
	 * ����
	 * 
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void closeCustomer(CustomerEJBEvent inEvent)
			throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);

		boolean doPost = inEvent.isDoPost();

		// check
		BusinessRuleService brService = new BusinessRuleService(serviceContext);
		brService.checkOperationCustomer(inEvent);

		// ��������ύ���򷵻ط����б��ǰ̨
		if (!doPost) {
			if(!inEvent.isReadyForeturnFee()){
				// �����б�
				Collection shouldPayFeeList = null;
				shouldPayFeeList = FeeService.customerOpImmediateFeeCalculator(
						inEvent.getCsiDto(), 0, this.operatorID, null);
				this.response.setPayload(shouldPayFeeList);
				return;
			}else{
				//�����б�
				CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
				FeeService.dealAccountFeeAndPayment(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
				this.response.setPayload(inEvent.getCsiFeeList());
				return;
				
			}
		}

		// ��ȡ�������ͣ�����ServiceContext
		if (inEvent.getCsiDto() != null)
			serviceContext.put(Service.CSI_TYPE, inEvent.getCsiDto().getType());
		// ��ȡ������csiDTO
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		//������ԭ���¼�������Ĵ���ԭ��
		csiDTO.setCreateReason(csiDTO.getStatusReason());
		// ��ȡ�ͻ�ID��csiDTO.getCustomerID()
		int customerID = csiDTO.getCustomerID();
		
		// ������������ض���
		CSIService csiService = new CSIService(serviceContext);

		csiService.createCustServiceInteraction(csiDTO);
		//�����ͻ���Ʒ�ı�����Ϣ
		csiService.createCsiCustProductInfo(csiDTO);

		// ���ô���Ͳ�Ʒ���ü���
//		FeeService.payFeeOperationForReturnFee(inEvent.getCsiDto(), inEvent
//				.getCsiFeeList(), inEvent.getCsiPaymentList(), operatorID);
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForReturnFee(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		
		//ȡ���ͻ��������ײͻ��ߴ���
		CampaignService campaignService= new CampaignService(serviceContext);
		campaignService.cancelCustCampaignForCust(customerID,0,0);

		// ��������id
		int csiID = 0;
		if (serviceContext.get(Service.CSI_ID) != null)
			csiID = ((Integer) serviceContext.get(Service.CSI_ID)).intValue();
		// ����ͻ�����
		CustomerService customerService = new CustomerService(serviceContext);
		customerService.closeCustomer(customerID);
		
		this.response.setPayload(new Integer(csiID));
	}

	/**
	 * �˻�
	 * 
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void withdraw(CustomerEJBEvent inEvent) throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);
		boolean doPost = inEvent.isDoPost();

		// �׷��
		BusinessRuleService brService = new BusinessRuleService(serviceContext);
		brService.checkOperationCustomer(inEvent);
		// ��������ύ���򷵻ط����б��ǰ̨
		if (!doPost) {
			if(!inEvent.isReadyForeturnFee()){
				// �����б�
				Collection shouldPayFeeList = null;
				shouldPayFeeList = FeeService.customerOpImmediateFeeCalculator(
						inEvent.getCsiDto(), 0, this.operatorID, inEvent
								.getCsiFeeList());
				this.response.setPayload(shouldPayFeeList);
				return;
			}else{
				//�����б�
				CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
				FeeService.dealAccountFeeAndPayment(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
				this.response.setPayload(inEvent.getCsiFeeList());
				return;
			}
		}

		// ��ȡ�������ͣ�����ServiceContext
		if (inEvent.getCsiDto() != null)
			serviceContext.put(Service.CSI_TYPE, inEvent.getCsiDto().getType());
		// ��ȡ������csiDTO
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		// ��ȡ�ͻ�ID��csiDTO.getCustomerID()
		int customerID = csiDTO.getCustomerID();
		// �˻����豸�б�
		Collection deviceList = inEvent.getCustMarketInfoList();

		// ������������ض���
		CSIService csiService = new CSIService(serviceContext);
		csiService.createCustServiceInteraction(csiDTO);
		//�����ͻ���Ʒ�ı�����Ϣ
		csiService.createCsiCustProductInfo(csiDTO);
		//���ô���Ͳ�Ʒ���ü���
		//FeeService.payFeeOperationForReturnFee(inEvent.getCsiDto(), inEvent
		//		.getCsiFeeList(), inEvent.getCsiPaymentList(), operatorID);
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForReturnFee(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);		
		csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		//ȡ���ͻ��������ײͻ��ߴ���
		CampaignService campaignService= new CampaignService(serviceContext);
		campaignService.cancelCustCampaignForCust(customerID,0,0);
		
		// ��������id
		int csiID = 0;
		if (serviceContext.get(Service.CSI_ID) != null)
			csiID = ((Integer) serviceContext.get(Service.CSI_ID)).intValue();
		
		//����ͻ��˻�
		CustomerService customerService = new CustomerService(serviceContext);
		customerService.withdrawCustomer(customerID, deviceList);
		this.response.setPayload(new Integer(csiID));
	}

	/**
	 * ��ʼ��ServiceContext
	 * 
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(CustomerEJBEvent event) {
		String description = "";
		String action = "";

		ServiceContext serviceContext = new ServiceContext();
		serviceContext.put(Service.OPERATIOR_ID, new Integer(operatorID));
		serviceContext.put(Service.REMOTE_HOST_ADDRESS, event
				.getRemoteHostAddress());
		if (event.getCustomerDTO() != null) {
			serviceContext.put(Service.CUSTOMER_ID, new Integer(event
					.getCustomerDTO().getCustomerID()));
		}
		if (event.getCsiDto() != null) {
			serviceContext.put(Service.CUSTOMER_ID, new Integer(event
					.getCsiDto().getCustomerID()));
		}

		switch (event.getActionType()) {
		// �޸Ŀͻ���Ϣ
		case EJBEvent.ALTERCUSTOMERINFO:
			description = "�޸Ŀͻ���Ϣ";
			action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			break;
		// Ǩ��
		case EJBEvent.MOVETODIFFERENTPLACE:
			description = "Ǩ��";
			action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			break;
		// ��ع���
		case EJBEvent.TRANSFERTODIFFERENTPLACE:
			description = "��ع���";
			action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			break;
		// ԭ�ع���
		case EJBEvent.TRANSFERTOSAMEPLACE:
			description = "ԭ�ع���";
			action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			break;
		// ����
		case EJBEvent.CLOSE_CUSTOMER:
			description = "����";
			action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			break;
		// �˻�
		case EJBEvent.WITHDRAW:
			description = "�˻�";
			action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			break;

		default:
			description = "";
			action = "";
		}
		serviceContext.put(Service.ACTION_DESCRTIPION, description);
		serviceContext.put(Service.ACTION_DEFITION, action);

		return serviceContext;
	}
}
