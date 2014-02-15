package com.dtv.oss.service.command.groupcustomer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.dtv.oss.domain.Account;
import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.Customer;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.ContractDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.NewCustomerInfoDTO;
import com.dtv.oss.dto.custom.CommonFeeAndPayObject;
import com.dtv.oss.dto.wrap.groupcustomer.GroupCustomerWrap;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.AccountService;
import com.dtv.oss.service.component.BusinessRuleService;
import com.dtv.oss.service.component.CSIService;
import com.dtv.oss.service.component.CustomerProductService;
import com.dtv.oss.service.component.CustomerService;
import com.dtv.oss.service.component.FeeService;
import com.dtv.oss.service.component.ImmediateCountFeeInfo;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.OpenAccountCheckEJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.ejbevent.groupcustomer.GroupCustomerEJBEvent;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.service.util.ImmediateFee.ImmediateFeeList;

public class GroupCustomerCommand extends Command {
	private static final Class clazz = GroupCustomerCommand.class;

	CommandResponseImp response = null;

	private ServiceContext context;

	private int operatorID = 0;

	private String machineName = "";

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "大客户管理");

		GroupCustomerEJBEvent event = (GroupCustomerEJBEvent) ev;
		operatorID = event.getOperatorID();
		this.machineName = ev.getRemoteHostAddress();

		// 初始化
		context = initServiceContext(event);

		LogUtility.log(clazz, LogLevel.DEBUG,
				"Enter execute() GroupCustomerCommand now.");
		try {
			switch (event.getActionType()) {
			// 批量导入子客户信息,文件上传
			case GroupCustomerEJBEvent.GROUPCUSTOMERBATCHIMPORT:
				checkGroupCustomerBatchImport(event);
				break;
			// 批量导入子客户信息,确认
			case GroupCustomerEJBEvent.GROUPCUSTOMERBATCHIMPORTCONFIRM:
				groupCustomerBatchImport(event);
				break;
			// 集团客户开户费用检查
			case GroupCustomerEJBEvent.GROUPCUSTOMEROPENFEE:
				LogUtility.log(clazz, LogLevel.DEBUG,
						"case GroupCustomerEJBEvent.GROUPCUSTOMEROPENFEE");
				groupCustomerOpen(event);
				break;
			// 集团客户开户确认
			case GroupCustomerEJBEvent.GROUPCUSTOMEROPENCONFIRM:
				LogUtility.log(clazz, LogLevel.DEBUG,
						"case GroupCustomerEJBEvent.GROUPCUSTOMEROPENCONFIRM");
				groupCustomerOpen(event);
				break;
			// 得到硬件设备
			case GroupCustomerEJBEvent.OPENCHILDCUST_GETDEVICE:
				getDeviceList(event);
				break;
			// 得到费用列表
			case GroupCustomerEJBEvent.OPENCHILDCUST_GETFEE:
				checkOpenChildCustomer(event);
				break;
			// 集团客户新开子客户
			case GroupCustomerEJBEvent.OPENCHILDCUST_CONFIRM:
				openChildCustomer(event);
				break;
			case GroupCustomerEJBEvent.CHILDCUST_CREATE:
				childCustomerCreate(event);
				break;
				//集团转个人费用信息
			case GroupCustomerEJBEvent.GROUPTOSINGLEFEE:
				groupToSingleCustomer(event);
				break;
				//集团转个人确认
			case GroupCustomerEJBEvent.GROUPTOSINGLECONFIRM:
				groupToSingleCustomer(event);
				break;
			default:
				break;
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
	 * 集团客户转个人客户
	 * @param event
	 * @throws ServiceException
	 */
	private void groupToSingleCustomer(GroupCustomerEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"groupToSingleCustomer run.................");

		int customerID = event.getCustomerDTO().getCustomerID();
		int groupCustomerID = event.getCustomerID();
		// 如果是费用计算
		if (event.getActionType() == GroupCustomerEJBEvent.GROUPTOSINGLEFEE) {
			//暂时没有合适的接口,返回空的费用  2007-05-09 chaoqiu begin
			//this.response.setPayload(calculateImmediateFee(event));
			Collection feeCol=new ArrayList();
			feeCol.add(new ImmediateCountFeeInfo());
			this.response.setPayload(feeCol);
			//暂时没有合适的接口,返回空的费用  2007-05-09 chaoqiu end
			return;
		}
		// 如果是提交操作
		if (event.getActionType() == GroupCustomerEJBEvent.GROUPTOSINGLECONFIRM) {

			// 创建受理单
			CSIService csiService = new CSIService(context);
			csiService.createCustServiceInteraction(event.getCsiDto());

			// 更新客户信息
			CustomerDTO custDTO = event.getCustomerDTO();
			custDTO
					.setCustomerStyle(CommonConstDefinition.CUSTOMERSTYLE_SINGLE);// 设置客户类型
			// 更新客户信息,并创建备份
			CustomerService customerService = new CustomerService(context);
			customerService.updateCustomer(custDTO, event.getAddressDTO(),
					event.getMarketInfoList(), true);

			// 创建新的子客户帐户,设置客户ID到帐户信息
			AccountService accountService = new AccountService(context);
			accountService.create(event.getNewCustAccountInfoDTO(), event
					.getAccountAddressDTO());
			Account account = (Account) context.get(Service.ACCOUNT);
			account.setCustomerID(customerID);

			//更新业务帐户产品付费帐户
			Integer accountID=(Integer)context.get(Service.ACCOUNT_ID);
			CustomerProductService cpService=new CustomerProductService();
			cpService.updateCustomerProductAccount(customerID, accountID.intValue());
			// 取得创建完成的受理单,用来取出受理单ID和客户ID,记录日志,
			CustServiceInteraction csi = (CustServiceInteraction) context
					.get(Service.CSI);

			response.setPayload(customerID + "");

			SystemLogRecorder.createSystemLog(machineName, operatorID,
					customerID, SystemLogRecorder.LOGMODULE_GROUPCUSTOMER,
					"集团客户集团子客户转个人客户", "集团客户集团子客户转个人客户,受理单ID:" + csi.getId()
							+ " 集团客户ID:" + groupCustomerID + " 子客户ID:"
							+ customerID, SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		}

	}

	private void childCustomerCreate(GroupCustomerEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"childCustomerCreate run.................");
		Collection packagecol = BusinessUtility.getPackageIDByContractID(event
				.getContractNO());
		// 检查合同有效性
		BusinessRuleService brService = new BusinessRuleService(context);
		brService.checkGroupCustomerContract(event.getContractNO(), false,
				event.getCustomerID(), 1);
		event.setContractToPackageIDCol(packagecol);
		CSIService csiService = new CSIService(context);
		context.put(Service.CSI_TYPE,
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GS);
		csiService.createGroupSubCustomer(event, context);

		CustServiceInteraction csi = (CustServiceInteraction) context
				.get(Service.CSI);
		int customerID = csi.getCustomerID();

		SystemLogRecorder.createSystemLog(machineName, operatorID, customerID,
				SystemLogRecorder.LOGMODULE_GROUPCUSTOMER, "集团子客户开户",
				"集团子客户开户,受理单ID:" + csi.getId()+";集团客户ID:"+event.getCustomerID()+";子客户ID:"+customerID,
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 通过上传文件批量导入子客户.
	 * 
	 * @param event
	 * @throws CommandException
	 * @throws ServiceException
	 */
	private void groupCustomerBatchImport(GroupCustomerEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "子客户批量导入...........");

		java.util.Collection customers = event.getGroupCustomerWrapList();
		java.util.Iterator it = customers.iterator();

		Collection packagecol = BusinessUtility.getPackageIDByContractID(event
				.getContractNO());
		// 根据合同设置产品包信息
		event.setContractToPackageIDCol(packagecol);
		// 检查合同有效性
		BusinessRuleService brService = new BusinessRuleService(context);
		brService.checkGroupCustomerContract(event.getContractNO(), false,
				event.getCustomerID(), customers.size());
		while (it.hasNext()) {
			GroupCustomerWrap wrap = (GroupCustomerWrap) it.next();
			// 把wrap的信息封装成event
			encapsulationSubCustomerInfo(wrap, event);
			// 检查子客户信息是否正确,如果将来有费用信息,也在这里处理
			brService.checkCreateGroupSubCustomer(event);
			LogUtility.log(clazz, LogLevel.DEBUG, "子客户批量导入...........检查结束");
			// 如果当前提交操作则创建子客户信息

			CSIService csiService = new CSIService(context);
			csiService.createGroupSubCustomer(event, context);
			LogUtility.log(clazz, LogLevel.DEBUG, "子客户批量导入...........创建结束");
			
			Customer customer = (Customer) context.get(Service.CUSTOMER);
			String serviceAccountID = (String)context.get(Service.SERVICE_ACCOUNT_ID);
			// 取得创建完成的受理单,用来取出受理单ID和客户ID,记录日志,
			CustServiceInteraction csi = (CustServiceInteraction) context
					.get(Service.CSI);
			int customerID = csi.getCustomerID();

			SystemLogRecorder.createSystemLog(machineName, operatorID,
					customerID, SystemLogRecorder.LOGMODULE_GROUPCUSTOMER,
					"集团客户子客户批量导入", 
					"集团客户子客户导入,受理单ID：" + csi.getId()
					+" 客户ID:"+customer.getCustomerID()
					+" 客户名称:"+customer.getName()
					+" 业务帐户ID:"+serviceAccountID,
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		}

	}

	/**
	 * 通过上传文件批量导入子客户的检查,当存在有效记录时不抛异常,当所有记录无效时抛异常,检查一遍所有记录,返回错误信息.
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void checkGroupCustomerBatchImport(GroupCustomerEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "子客户批量导入检查...........");
		boolean isValid = false;
		// 记录错误记录数目
		int errNumber = 0;
		ArrayList errList = new ArrayList();

		java.util.Collection customers = event.getGroupCustomerWrapList();
		java.util.Iterator it = customers.iterator();

		Collection packagecol = BusinessUtility.getPackageIDByContractID(event
				.getContractNO());
		// 根据合同设置产品包信息
		event.setContractToPackageIDCol(packagecol);
		// 检查合同有效性
		BusinessRuleService brService = new BusinessRuleService(context);
		brService.checkGroupCustomerContract(event.getContractNO(), false,
				event.getCustomerID(), customers.size());
		while (it.hasNext()) {
			GroupCustomerWrap wrap = (GroupCustomerWrap) it.next();
			try {
				// 把wrap的信息封装成event
				encapsulationSubCustomerInfo(wrap, event);
				// 检查子客户信息是否正确,如果将来有费用信息,也在这里处理
				brService.checkCreateGroupSubCustomer(event);
				LogUtility.log(clazz, LogLevel.DEBUG, "子客户批量导入...........检查结束");

				isValid = true;
			} catch (Exception e) {
				// 如果有错误记录,添加到inValidCol,循环继续工作,
				wrap.setErrString(e.getMessage());
				isValid = false;
				errNumber++;
				errList.add(wrap);
			}
			wrap.setValid(isValid);
			
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "\n子客户批量导入检查完成,有效子客户数:"
				+ (customers.size() - errNumber) + ",\n无效子客户数:" + errNumber);
		// 设置无效记录集合返回,
		if(!errList.isEmpty())
		{
			response.setPayload(errList);
		}
		//返回费用
		else
		{
			//计算费用
			CommonFeeAndPayObject commonObj= PublicService.encapsulateBaseParamForExitCust(
					event.getCsiDto(),event.getCustomerID(),event.getAccountID());
			Collection shouldPayFeeList = FeeService.calculateImmediateFeeForOpen(event.getCsiDto(),event.getContractToPackageIDCol(), new ArrayList(),commonObj);
			this.response.setPayload(shouldPayFeeList);
			//response.setPayload(calculateImmediateFee(event));
			
		}
	}

	/**
	 * 把wrap包装成event,和单独子客户开户一个入口,该方法仅适用于批量用户导入
	 * 
	 * @param wrap
	 * @param event
	 */
	private void encapsulationSubCustomerInfo(GroupCustomerWrap wrap,
			GroupCustomerEJBEvent event) throws ServiceException {
		NewCustomerInfoDTO newCustomerInfoDTO = wrap.getNewCustomerInfoDTO();
		// 设置子客户的集团客户信息
		newCustomerInfoDTO.setGroupCustID(event.getCustomerID());
		newCustomerInfoDTO
				.setCustStyle(CommonConstDefinition.CUSTOMERSTYLE_GROUP);
		newCustomerInfoDTO.setType(CommonConstDefinition.CUSTOMERTYPE_GROUP);
		
		newCustomerInfoDTO.setOpenSourceType(event.getOpenSourceType());

		// 设置子客户DTO到event中,
		event.setNewCustomerInfoDTO(wrap.getNewCustomerInfoDTO());
		// 设置子客户地址信息到event中
		event.setAddressDTO(wrap.getAddressDTO());
		// 设置电话号码
		event.setPhoneNo(wrap.getServiceCode());
		int itemID = BusinessUtility.getResoucePhoneIDByPhoneNo(wrap
				.getServiceCode());
		LogUtility.log(clazz, LogLevel.DEBUG, "itemID:" + itemID);
		wrap.setPhoneNoItemID(itemID);
		event.setItemID(itemID);
		// 设置硬件信息到event中.
		String scSerialNo = wrap.getSCSerialNo();
		String stbSerialNo = wrap.getSTBSerialNo();
		String ippSerialNo = wrap.getIPPSerialNo();
		HashMap deviceTable = new HashMap();
		if (scSerialNo != null && !"".equals(scSerialNo)) {
			deviceTable.put(scSerialNo, "SC");
		}
		if (stbSerialNo != null && !"".equals(stbSerialNo)) {
			deviceTable.put(stbSerialNo, "STB");
		}
		if (ippSerialNo != null && !"".equals(ippSerialNo)) {
			String deviceClass = BusinessUtility
					.getDeviceBySerialNo(ippSerialNo).getDeviceClass();
			deviceTable.put(ippSerialNo, deviceClass);
		}
		event.setDeviceTable(deviceTable);

		// 设置受理单信息
		CustServiceInteractionDTO csiDTO = new CustServiceInteractionDTO();
		// 受理单类型为集团客户开户
		csiDTO.setAccountID(event.getAccountID());
		csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GS);
		// 联系人,联系电话为客户,客户电话
		csiDTO.setContactPerson(wrap.getNewCustomerInfoDTO().getName());
		csiDTO.setContactPhone(wrap.getNewCustomerInfoDTO().getTelephone());
		event.setCsiDto(csiDTO);
	}

	/**
	 * 集团客户开户
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void groupCustomerOpen(GroupCustomerEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"groupCustomerOpen run.................");

		// 如果是费用计算
		if (event.getActionType() == GroupCustomerEJBEvent.GROUPCUSTOMEROPENFEE) {
			this.response.setPayload(calculateImmediateFee(event));
			return;
		}
		// 如果是提交操作
		if (event.getActionType() == GroupCustomerEJBEvent.GROUPCUSTOMEROPENCONFIRM) {
			// 检查合同有效性
			BusinessRuleService brService = new BusinessRuleService(context);
			brService.checkGroupCustomerContract(event.getContractDTO()
					.getContractNo(), true, 0, 0);

			CSIService csiService = new CSIService(context);
			csiService.createGroupCustServiceInteractionInfo(event);
			CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
			FeeService.payFeeOperationForOpen(event.getCsiDto(),event.getFeeList(),event.getPaymentList(), event.getCsiPrePaymentDeductionList(),commonObj,operatorID);
			csiService.updateCSIPayStatus(context,event.getCsiDto().getPaymentStatus());
			// 取得创建完成的受理单,用来取出受理单ID和客户ID,记录日志,
			Customer customer = (Customer) context.get(Service.CUSTOMER);
			Account account =(Account)context.get(Service.ACCOUNT);
			CustServiceInteraction csi = (CustServiceInteraction) context
					.get(Service.CSI);
			int customerID = csi.getCustomerID();

			response.setPayload(customerID + "");

			SystemLogRecorder.createSystemLog(machineName, operatorID,
					customerID, SystemLogRecorder.LOGMODULE_GROUPCUSTOMER,
					"集团客户开户", "集团客户开户,受理单ID:" + csi.getId()
					+" 客户ID:"+customer.getCustomerID()
					+" 客户名称:"+customer.getName()
					+" 帐户ID:"+account.getAccountID(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		}

	}
	
	/**
	 * 计算费用
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	private Collection calculateImmediateFee(GroupCustomerEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz,LogLevel.DEBUG,"■■■■■■csi="+event.getCsiDto());
		LogUtility.log(clazz,LogLevel.DEBUG,"■■■■■■NewCustInfo="+event.getNewCustomerInfoDTO());
		LogUtility.log(clazz,LogLevel.DEBUG,"■■■■■■NewCustAcctInfo="+event.getNewCustAccountInfoDTO());
		CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForOpen(event.getCsiDto(),event.getNewCustomerInfoDTO(),event.getNewCustAccountInfoDTO());
		commonObj.setContractNo(event.getContractNO());
		Collection currentFeeList=FeeService.calculateImmediateFeeForOpen(event.getCsiDto(),event.getContractToPackageIDCol(), null,commonObj);
		return currentFeeList;
	}
	
	/**
	 * 计算受理费用(用于集团客户子客户新开用户)
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	private Collection calculateImmediateFeeForNewOpenServiceAccount(GroupCustomerEJBEvent event)throws ServiceException{
		
		Collection currentFeeList = null;
		//try{
		//System.out.println("______GroupCampaignID="+event.getCsiDto().getGroupCampaignID());
		//System.out.println("______CustomerID="+event.getCsiDto().getCustomerID());
		//System.out.println("______AccountID="+event.getCsiDto().getAccountID());
		//System.out.println("______type="+event.getCsiDto().getType());
		CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForExitCust
		(event.getCsiDto(),event.getCsiDto().getCustomerID(),event.getCsiDto().getAccountID());
		//押金
		commonObj.setDeposit(0);
		currentFeeList=FeeService.calculateImmediateFee(event.getCsiDto(),event.getCsiPackageArray(),  event.getCsiCampaignArray(),commonObj);
		
		//}
		//catch(Exception e)
		//{
		//	e.printStackTrace();
		//}
		System.out.println("______返回费用="+currentFeeList);
		return currentFeeList;
		
	}

	
	private ServiceContext initServiceContext(GroupCustomerEJBEvent event) {
		ServiceContext serviceContext = new ServiceContext();
		String description = "";
		String action = "";
		switch (event.getActionType()) {
		case GroupCustomerEJBEvent.GROUPTOSINGLECONFIRM:
			description = "集团子客户转个人客户";
			action = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GM;
			break;
		case GroupCustomerEJBEvent.GROUPCUSTOMEROPENCONFIRM:
			description = "集团客户开户";
			action = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GO;
			break;
		case GroupCustomerEJBEvent.CHILDCUST_CREATE:
		case GroupCustomerEJBEvent.GROUPCUSTOMERBATCHIMPORTCONFIRM:
			int accountID = event.getAccountID();
			serviceContext.put(Service.ACCOUNT_ID, new Integer(accountID));
			description = "集团客户子客户开户";
			action = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GS;
			break;
		}
		serviceContext.put(Service.ACTION_DESCRTIPION, description);
		serviceContext.put(Service.ACTION_DEFITION, action);
		serviceContext.put(Service.OPERATIOR_ID, new Integer(operatorID));
		serviceContext.put(Service.REMOTE_HOST_ADDRESS, event
				.getRemoteHostAddress());
		LogUtility.log(clazz, LogLevel.DEBUG,
				"■■■■■■Service.ACTION_DESCRTIPION: "
						+ (String) serviceContext
								.get(Service.ACTION_DESCRTIPION));
		LogUtility.log(clazz, LogLevel.DEBUG, "■■■■■■Service.ACTION_DEFITION:"
				+ (String) serviceContext.get(Service.ACTION_DEFITION));
		LogUtility.log(clazz, LogLevel.DEBUG, "■■■■■■Service.OPERATIOR_ID:"
				+ (Integer) serviceContext.get(Service.OPERATIOR_ID));
		LogUtility.log(clazz, LogLevel.DEBUG, "■■■■■■Service.ACCOUNT_ID:"
				+ (Integer) serviceContext.get(Service.ACCOUNT_ID));

		return serviceContext;
	}

	/**
	 * 得到硬件列表 fiona
	 * 如果没有硬件则直接计费 返回费用信息
	 * @param event
	 * @throws ServiceException
	 */
	private void getDeviceList(GroupCustomerEJBEvent event)
			throws ServiceException {
		ServiceContext serviceContext = new ServiceContext();
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		ContractDTO contractDTO = BusinessUtility
				.getContractDTOByContractNo(event.getContractNO());
		businessRuleService.checkOpenChildContract(contractDTO, 1);

		Collection packagecol = BusinessUtility.getPackageIDByContractID(event
				.getContractNO());
		
		Collection  deviceClassList = PublicService.getDependencyDeviceClassBygetCsiPackageArray(event.getCsiDto(),null,packagecol);
		
		if (deviceClassList !=null && !deviceClassList.isEmpty())
		    this.response.setPayload(deviceClassList);
		else
		{
			this.response.setPayload(calculateImmediateFeeForNewOpenServiceAccount(event));
			this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
		}
	}

	/**
	 * 得到费用列表 fiona
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void checkOpenChildCustomer(GroupCustomerEJBEvent event)
			throws ServiceException {
		ServiceContext serviceContext = new ServiceContext();
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		ContractDTO contractDTO = BusinessUtility
				.getContractDTOByContractNo(event.getContractNO());
		businessRuleService.checkGroupCustomerContract(event.getContractNO(),
				false, event.getCustomerID(), 1);
		businessRuleService.checkOpenChildDevAndGetCsiProdList(event.getCsiDto(),contractDTO,
				event.getDeviceTable(), false);
		this.response.setPayload(calculateImmediateFeeForNewOpenServiceAccount(event));
	}

	/**
	 * 集团客户新开子客户 fiona
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void openChildCustomer(GroupCustomerEJBEvent event)
			throws ServiceException {
		ServiceContext serviceContext = initServiceContext(event);
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		// 检查子客户批量开户信息设备信息
		ContractDTO contractDTO = BusinessUtility
				.getContractDTOByContractNo(event.getContractNO());

		businessRuleService.checkGroupCustomerContract(event.getContractNO(),
				false, event.getCustomerID(), 1);
		Collection initCsiproductCol = businessRuleService
				.checkOpenChildDevAndGetCsiProdList(event.getCsiDto(),contractDTO, event.getDeviceTable(),
						true);
		// 创建受理单信息
		CSIService cSIService = new CSIService(serviceContext);
		cSIService.openChildCustServiceAccount(event, initCsiproductCol);
		// 创建系统日志
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), event.getCsiDto()
				.getCustomerID(), SystemLogRecorder.LOGMODULE_GROUPCUSTOMER, "集团客户子客户新开用户",
				"集团客户号："+event.getCustomerID()+" 子客户ID："+event.getCsiDto().getCustomerID()+" 子客户新开用户使用合同号："+event.getContractNO(), SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

}
