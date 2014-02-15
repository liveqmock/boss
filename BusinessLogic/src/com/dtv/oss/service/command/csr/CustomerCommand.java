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
 * 客户相关的操作 author ：zhouxushun date : 2005-11-14 description:
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
			// 修改客户信息
			case EJBEvent.ALTERCUSTOMERINFO:
				alterCustomerInfo(inEvent);
				break;
			// 迁移
			case EJBEvent.MOVETODIFFERENTPLACE:
				moveUserToDifferentPlace(inEvent);
				break;
			// 异地过户
			case EJBEvent.TRANSFERTODIFFERENTPLACE:
				transferCustomer(inEvent);
				break;
			// 原地过户
			case EJBEvent.TRANSFERTOSAMEPLACE:
				transferCustomer(inEvent);
				break;
			// 销户
			case EJBEvent.CLOSE_CUSTOMER:
				closeCustomer(inEvent);
				break;
			// 退户
			case EJBEvent.WITHDRAW:
				withdraw(inEvent);
				break;
			// 客户下所有用户重授权
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
			throw new CommandException("未知错误。");
		}
		return response;
	}

	
	/**
	 * 对客户下的所有用户进行重授权
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void reauthorizeForSAByCustomer(CustomerEJBEvent inEvent)
	throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);
		Collection saIDCol=BusinessUtility.getSaIDColByCustID(inEvent.getCustomerDTO().getCustomerID());
		if(!saIDCol.isEmpty()){
			Iterator saIDIter=saIDCol.iterator();
			//处理重授权
			ServiceAccountService saService=new ServiceAccountService(serviceContext);
			saService.setOperatorID(operatorID);
			saService.batchResendEMM(SystemEventRecorder.SE_CA_SERVICEACCOUNT_RESENDEMM);
		}
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent.getCustomerDTO().getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "批量产品重授权", "授权的业务帐户....", SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * 录入协议价
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void createProtocol(CustomerEJBEvent inEvent)throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);
		BusinessUtility.createProtocol(inEvent.getProtocolList());
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent.getCustomerDTO().getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "协议价录入", "录入协议价", SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 更新协议价
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
				SystemLogRecorder.LOGMODULE_CUSTSERV, "协议价录入", "录入协议价", SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 修改客户信息
	 * 
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void alterCustomerInfo(CustomerEJBEvent inEvent)
			throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);

		// 取得受理单类型，置入ServiceContext
		if (inEvent.getCsiDto() != null)
			serviceContext.put(Service.CSI_TYPE, inEvent.getCsiDto().getType());
		// 获取受理单DTO
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		// 获取客户地址：custAddressDTO
		AddressDTO custAddressDTO = inEvent.getCustAddressDTO();
		// 获取customerDTO
		CustomerDTO customerDTO = inEvent.getCustomerDTO();
		// 获取doPost
		boolean doPost = inEvent.isDoPost();

		if (!doPost)
			return;

		CustomerInfoWrap oldCustomerInfo = BusinessUtility.getCustomerInfoWrapByID(customerDTO.getCustomerID());
		// 创建受理单和相关对象
		CSIService csiService = new CSIService(serviceContext);
		csiService.createCustServiceInteraction(csiDTO);
		
		// 更新客户信息
		CustomerService customerService = new CustomerService(serviceContext);
		customerService.updateCustomer(customerDTO, custAddressDTO, inEvent
				.getCustMarketInfoList(),true);
		
		// 记录系统日志//日志记录动作原来在customerService里,由侯12-06移到这里
		//取出原来的客户信息,和新的客户信息进行比较,填写日志内容.
		Customer customer=(Customer) serviceContext.get(Service.CUSTOMER);
		Address address=(Address) serviceContext.get(Service.CUSTOMER_ADDRESS_EJB);
		int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		String custStyle = null;
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		custStyle = businessRuleService.getCustStyle(customerDTO
				.getCustomerID());
		StringBuffer logDesc=new StringBuffer();
		logDesc.append("修改客户资料,");
		logDesc.append("受理单ID:");
		logDesc.append(csiID);
		logDesc.append(";修改客户资料,客户ID:");
		logDesc.append(customer.getCustomerID());

		logDesc.append(SystemLogRecorder.appendDescString(";客户类型:",
				oldCustomerInfo.getCustomerTypeDesc(), BusinessUtility
						.getCommonNameByKey("SET_C_CUSTOMERTYPE", customer
								.getCustomerType())));
		logDesc.append(SystemLogRecorder.appendDescString(";姓名:",
				oldCustomerInfo.getCustDto().getName(), 
				customer.getName()));
		logDesc.append(SystemLogRecorder.appendDescString(";证件类型:",
				oldCustomerInfo.getCustomerCardTypeDesc(), 
				BusinessUtility.getCommonNameByKey("SET_C_CUSTOMERCARDTYPE",customer.getCardType())));
		logDesc.append(SystemLogRecorder.appendDescString(";证件号码:",
				oldCustomerInfo.getCustDto().getCardID(), 
				customer.getCardID()));
		logDesc.append(SystemLogRecorder.appendDescString(";固定电话:",
				oldCustomerInfo.getCustDto().getTelephone(), 
				customer.getTelephone()));
		logDesc.append(SystemLogRecorder.appendDescString(";移动电话:",
				oldCustomerInfo.getCustDto().getTelephoneMobile(), 
				customer.getTelephoneMobile()));

		if(address!=null){
			logDesc.append(SystemLogRecorder.appendDescString(";区域:",
					oldCustomerInfo.getDistrictSettingDTO().getName(), 
					BusinessUtility.getDistrictNameById(address.getDistrictID())));
			logDesc.append(SystemLogRecorder.appendDescString(";详细地址:",
					oldCustomerInfo.getAddrDto().getDetailAddress(), 
					address.getDetailAddress()));
			logDesc.append(SystemLogRecorder.appendDescString(";邮编:",
					oldCustomerInfo.getAddrDto().getPostcode(), 
					address.getPostcode()));
		}
		logDesc.append(SystemLogRecorder.appendDescString(";来源渠道:",
				BusinessUtility.getCommonNameByKey("SET_C_CUSTOPENSOURCETYPE",oldCustomerInfo.getCustDto().getOpenSourceType()), 
				BusinessUtility.getCommonNameByKey("SET_C_CUSTOPENSOURCETYPE",customer.getOpenSourceType())));
		logDesc
				.append(SystemLogRecorder.appendDescString(";模拟用户代码:",
						oldCustomerInfo.getCustDto().getCatvID(), customer
								.getCatvID()));
		logDesc.append(SystemLogRecorder.appendDescString(";国籍:",
				BusinessUtility.getCommonNameByKey("SET_C_NATIONALITY",
						oldCustomerInfo.getCustDto().getNationality()),
				BusinessUtility.getCommonNameByKey("SET_C_NATIONALITY",
						customer.getNationality())));
		logDesc.append(SystemLogRecorder.appendDescString(";社保卡编号:",
				oldCustomerInfo.getCustDto().getSocialSecCardID(), customer
						.getSocialSecCardID()));

		logDesc.append(SystemLogRecorder.appendDescString(";性别:",
				BusinessUtility.getCommonNameByKey("SET_C_CUSTOMERGENDER",
						oldCustomerInfo.getCustDto().getGender()),
				BusinessUtility.getCommonNameByKey("SET_C_CUSTOMERGENDER",
						customer.getGender())));
		logDesc.append(SystemLogRecorder.appendDescString(";Email 地址:",
				oldCustomerInfo.getCustDto().getEmail(), customer.getEmail()));

		logDesc.append(SystemLogRecorder.appendDescString(";协议编号:",
				oldCustomerInfo.getCustDto().getContractNo(), customer
						.getContractNo()));
		logDesc.append(SystemLogRecorder.appendDescString(";传真:",
				oldCustomerInfo.getCustDto().getFax(), customer.getFax()));
		logDesc.append(SystemLogRecorder.appendDescString(";出生日期:",
				oldCustomerInfo.getCustDto().getBirthday()!=null?oldCustomerInfo.getCustDto().getBirthday().toLocaleString():null,
				customer.getBirthday()!=null?customer.getBirthday().toLocaleString():null));
				
		logDesc.append(SystemLogRecorder.appendDescString(";登陆名:",
				oldCustomerInfo.getCustDto().getLoginID(), 
				customer.getLoginID()));
		logDesc.append(SystemLogRecorder.appendDescString(";登陆密码:",
				oldCustomerInfo.getCustDto().getLoginPwd(), 
				customer.getLoginPwd()));
		logDesc.append(SystemLogRecorder.appendDescString(";客户资料备注:",
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
				sysModule, "修改客户资料",
				logDesc.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 迁移
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

		// 是否真实提交
		if (!doPost) {
			//计费
			CommonFeeAndPayObject paramObj=PublicService.encapsulateBaseParamForExitCust(inEvent.getCsiDto(), inEvent.getCsiDto().getCustomerID(),inEvent.getCsiDto().getAccountID());
			Collection  currentFeeList=FeeService.calculateImmediateFeeForCustService(inEvent.getCsiDto(),null,null,paramObj);
			this.response.setPayload(currentFeeList);
			LogUtility.log(CustomerCommand.class, LogLevel.DEBUG,
					"■■moveUserToDifferentPlace■■");
			return;
		}
		// 创建受理单和相关对象
		CSIService csiService = new CSIService(serviceContext);
		csiService.moveCustomer(inEvent);

		//校验银行帐号,wp@20080227    	
		
		AccountDTO accountDTO=inEvent.getAccountDTO();
		if(accountDTO.getBankAccount()!=null && !"".equals(accountDTO.getBankAccount())){
			if(accountDTO.getMopID()!=0){
				String functionResult = BusinessUtility.callFunctionForCheckBankAccount(accountDTO.getMopID(),accountDTO.getBankAccount());
				if(!"true".equals(functionResult))
					throw new ServiceException(functionResult);
			}
		}

		//处理费用支付
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
	 * 过户，含原地过户和异地过户
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

		// 是否真实提交
		if (!doPost) {
			//计费
			CommonFeeAndPayObject paramObj=PublicService.encapsulateBaseParamForExitCust(inEvent.getCsiDto(), inEvent.getCsiDto().getCustomerID(),inEvent.getCsiDto().getAccountID());
			Collection  currentFeeList=FeeService.calculateImmediateFeeForCustService(inEvent.getCsiDto(),null,null,paramObj);
			this.response.setPayload(currentFeeList);
			LogUtility.log(CustomerCommand.class, LogLevel.DEBUG,
					"■■transferCustomer■■");
			return;
		}

		// 创建受理单和相关对象
		CSIService csiService = new CSIService(serviceContext);
		csiService.transferCustomer(inEvent);
		//处理费用支付
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
	 * 销户
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

		// 如果不是提交，则返回费用列表给前台
		if (!doPost) {
			if(!inEvent.isReadyForeturnFee()){
				// 费用列表
				Collection shouldPayFeeList = null;
				shouldPayFeeList = FeeService.customerOpImmediateFeeCalculator(
						inEvent.getCsiDto(), 0, this.operatorID, null);
				this.response.setPayload(shouldPayFeeList);
				return;
			}else{
				//费用列表
				CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
				FeeService.dealAccountFeeAndPayment(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
				this.response.setPayload(inEvent.getCsiFeeList());
				return;
				
			}
		}

		// 获取受理单类型，置入ServiceContext
		if (inEvent.getCsiDto() != null)
			serviceContext.put(Service.CSI_TYPE, inEvent.getCsiDto().getType());
		// 获取受理单：csiDTO
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		//将受理原因记录到受理单的创建原因
		csiDTO.setCreateReason(csiDTO.getStatusReason());
		// 获取客户ID：csiDTO.getCustomerID()
		int customerID = csiDTO.getCustomerID();
		
		// 创建受理单和相关对象
		CSIService csiService = new CSIService(serviceContext);

		csiService.createCustServiceInteraction(csiDTO);
		//创建客户产品的备份信息
		csiService.createCsiCustProductInfo(csiDTO);

		// 费用处理和产品费用计算
//		FeeService.payFeeOperationForReturnFee(inEvent.getCsiDto(), inEvent
//				.getCsiFeeList(), inEvent.getCsiPaymentList(), operatorID);
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForReturnFee(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		
		//取消客户正常的套餐或者促销
		CampaignService campaignService= new CampaignService(serviceContext);
		campaignService.cancelCustCampaignForCust(customerID,0,0);

		// 返回受理单id
		int csiID = 0;
		if (serviceContext.get(Service.CSI_ID) != null)
			csiID = ((Integer) serviceContext.get(Service.CSI_ID)).intValue();
		// 处理客户销户
		CustomerService customerService = new CustomerService(serviceContext);
		customerService.closeCustomer(customerID);
		
		this.response.setPayload(new Integer(csiID));
	}

	/**
	 * 退户
	 * 
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void withdraw(CustomerEJBEvent inEvent) throws ServiceException {
		ServiceContext serviceContext = initServiceContext(inEvent);
		boolean doPost = inEvent.isDoPost();

		// 杨晨追加
		BusinessRuleService brService = new BusinessRuleService(serviceContext);
		brService.checkOperationCustomer(inEvent);
		// 如果不是提交，则返回费用列表给前台
		if (!doPost) {
			if(!inEvent.isReadyForeturnFee()){
				// 费用列表
				Collection shouldPayFeeList = null;
				shouldPayFeeList = FeeService.customerOpImmediateFeeCalculator(
						inEvent.getCsiDto(), 0, this.operatorID, inEvent
								.getCsiFeeList());
				this.response.setPayload(shouldPayFeeList);
				return;
			}else{
				//费用列表
				CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
				FeeService.dealAccountFeeAndPayment(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
				this.response.setPayload(inEvent.getCsiFeeList());
				return;
			}
		}

		// 获取受理单类型，置入ServiceContext
		if (inEvent.getCsiDto() != null)
			serviceContext.put(Service.CSI_TYPE, inEvent.getCsiDto().getType());
		// 获取受理单：csiDTO
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		// 获取客户ID：csiDTO.getCustomerID()
		int customerID = csiDTO.getCustomerID();
		// 退还的设备列表
		Collection deviceList = inEvent.getCustMarketInfoList();

		// 创建受理单和相关对象
		CSIService csiService = new CSIService(serviceContext);
		csiService.createCustServiceInteraction(csiDTO);
		//创建客户产品的备份信息
		csiService.createCsiCustProductInfo(csiDTO);
		//费用处理和产品费用计算
		//FeeService.payFeeOperationForReturnFee(inEvent.getCsiDto(), inEvent
		//		.getCsiFeeList(), inEvent.getCsiPaymentList(), operatorID);
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForReturnFee(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);		
		csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		//取消客户正常的套餐或者促销
		CampaignService campaignService= new CampaignService(serviceContext);
		campaignService.cancelCustCampaignForCust(customerID,0,0);
		
		// 返回受理单id
		int csiID = 0;
		if (serviceContext.get(Service.CSI_ID) != null)
			csiID = ((Integer) serviceContext.get(Service.CSI_ID)).intValue();
		
		//处理客户退户
		CustomerService customerService = new CustomerService(serviceContext);
		customerService.withdrawCustomer(customerID, deviceList);
		this.response.setPayload(new Integer(csiID));
	}

	/**
	 * 初始化ServiceContext
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
		// 修改客户信息
		case EJBEvent.ALTERCUSTOMERINFO:
			description = "修改客户信息";
			action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			break;
		// 迁移
		case EJBEvent.MOVETODIFFERENTPLACE:
			description = "迁移";
			action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			break;
		// 异地过户
		case EJBEvent.TRANSFERTODIFFERENTPLACE:
			description = "异地过户";
			action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			break;
		// 原地过户
		case EJBEvent.TRANSFERTOSAMEPLACE:
			description = "原地过户";
			action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			break;
		// 销户
		case EJBEvent.CLOSE_CUSTOMER:
			description = "销户";
			action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			break;
		// 退户
		case EJBEvent.WITHDRAW:
			description = "退户";
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
