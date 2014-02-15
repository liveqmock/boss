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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "��ͻ�����");

		GroupCustomerEJBEvent event = (GroupCustomerEJBEvent) ev;
		operatorID = event.getOperatorID();
		this.machineName = ev.getRemoteHostAddress();

		// ��ʼ��
		context = initServiceContext(event);

		LogUtility.log(clazz, LogLevel.DEBUG,
				"Enter execute() GroupCustomerCommand now.");
		try {
			switch (event.getActionType()) {
			// ���������ӿͻ���Ϣ,�ļ��ϴ�
			case GroupCustomerEJBEvent.GROUPCUSTOMERBATCHIMPORT:
				checkGroupCustomerBatchImport(event);
				break;
			// ���������ӿͻ���Ϣ,ȷ��
			case GroupCustomerEJBEvent.GROUPCUSTOMERBATCHIMPORTCONFIRM:
				groupCustomerBatchImport(event);
				break;
			// ���ſͻ��������ü��
			case GroupCustomerEJBEvent.GROUPCUSTOMEROPENFEE:
				LogUtility.log(clazz, LogLevel.DEBUG,
						"case GroupCustomerEJBEvent.GROUPCUSTOMEROPENFEE");
				groupCustomerOpen(event);
				break;
			// ���ſͻ�����ȷ��
			case GroupCustomerEJBEvent.GROUPCUSTOMEROPENCONFIRM:
				LogUtility.log(clazz, LogLevel.DEBUG,
						"case GroupCustomerEJBEvent.GROUPCUSTOMEROPENCONFIRM");
				groupCustomerOpen(event);
				break;
			// �õ�Ӳ���豸
			case GroupCustomerEJBEvent.OPENCHILDCUST_GETDEVICE:
				getDeviceList(event);
				break;
			// �õ������б�
			case GroupCustomerEJBEvent.OPENCHILDCUST_GETFEE:
				checkOpenChildCustomer(event);
				break;
			// ���ſͻ��¿��ӿͻ�
			case GroupCustomerEJBEvent.OPENCHILDCUST_CONFIRM:
				openChildCustomer(event);
				break;
			case GroupCustomerEJBEvent.CHILDCUST_CREATE:
				childCustomerCreate(event);
				break;
				//����ת���˷�����Ϣ
			case GroupCustomerEJBEvent.GROUPTOSINGLEFEE:
				groupToSingleCustomer(event);
				break;
				//����ת����ȷ��
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
			throw new CommandException("δ֪����");
		}
		return response;
	}

	/**
	 * ���ſͻ�ת���˿ͻ�
	 * @param event
	 * @throws ServiceException
	 */
	private void groupToSingleCustomer(GroupCustomerEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"groupToSingleCustomer run.................");

		int customerID = event.getCustomerDTO().getCustomerID();
		int groupCustomerID = event.getCustomerID();
		// ����Ƿ��ü���
		if (event.getActionType() == GroupCustomerEJBEvent.GROUPTOSINGLEFEE) {
			//��ʱû�к��ʵĽӿ�,���ؿյķ���  2007-05-09 chaoqiu begin
			//this.response.setPayload(calculateImmediateFee(event));
			Collection feeCol=new ArrayList();
			feeCol.add(new ImmediateCountFeeInfo());
			this.response.setPayload(feeCol);
			//��ʱû�к��ʵĽӿ�,���ؿյķ���  2007-05-09 chaoqiu end
			return;
		}
		// ������ύ����
		if (event.getActionType() == GroupCustomerEJBEvent.GROUPTOSINGLECONFIRM) {

			// ��������
			CSIService csiService = new CSIService(context);
			csiService.createCustServiceInteraction(event.getCsiDto());

			// ���¿ͻ���Ϣ
			CustomerDTO custDTO = event.getCustomerDTO();
			custDTO
					.setCustomerStyle(CommonConstDefinition.CUSTOMERSTYLE_SINGLE);// ���ÿͻ�����
			// ���¿ͻ���Ϣ,����������
			CustomerService customerService = new CustomerService(context);
			customerService.updateCustomer(custDTO, event.getAddressDTO(),
					event.getMarketInfoList(), true);

			// �����µ��ӿͻ��ʻ�,���ÿͻ�ID���ʻ���Ϣ
			AccountService accountService = new AccountService(context);
			accountService.create(event.getNewCustAccountInfoDTO(), event
					.getAccountAddressDTO());
			Account account = (Account) context.get(Service.ACCOUNT);
			account.setCustomerID(customerID);

			//����ҵ���ʻ���Ʒ�����ʻ�
			Integer accountID=(Integer)context.get(Service.ACCOUNT_ID);
			CustomerProductService cpService=new CustomerProductService();
			cpService.updateCustomerProductAccount(customerID, accountID.intValue());
			// ȡ�ô�����ɵ�����,����ȡ������ID�Ϳͻ�ID,��¼��־,
			CustServiceInteraction csi = (CustServiceInteraction) context
					.get(Service.CSI);

			response.setPayload(customerID + "");

			SystemLogRecorder.createSystemLog(machineName, operatorID,
					customerID, SystemLogRecorder.LOGMODULE_GROUPCUSTOMER,
					"���ſͻ������ӿͻ�ת���˿ͻ�", "���ſͻ������ӿͻ�ת���˿ͻ�,����ID:" + csi.getId()
							+ " ���ſͻ�ID:" + groupCustomerID + " �ӿͻ�ID:"
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
		// ����ͬ��Ч��
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
				SystemLogRecorder.LOGMODULE_GROUPCUSTOMER, "�����ӿͻ�����",
				"�����ӿͻ�����,����ID:" + csi.getId()+";���ſͻ�ID:"+event.getCustomerID()+";�ӿͻ�ID:"+customerID,
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * ͨ���ϴ��ļ����������ӿͻ�.
	 * 
	 * @param event
	 * @throws CommandException
	 * @throws ServiceException
	 */
	private void groupCustomerBatchImport(GroupCustomerEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "�ӿͻ���������...........");

		java.util.Collection customers = event.getGroupCustomerWrapList();
		java.util.Iterator it = customers.iterator();

		Collection packagecol = BusinessUtility.getPackageIDByContractID(event
				.getContractNO());
		// ���ݺ�ͬ���ò�Ʒ����Ϣ
		event.setContractToPackageIDCol(packagecol);
		// ����ͬ��Ч��
		BusinessRuleService brService = new BusinessRuleService(context);
		brService.checkGroupCustomerContract(event.getContractNO(), false,
				event.getCustomerID(), customers.size());
		while (it.hasNext()) {
			GroupCustomerWrap wrap = (GroupCustomerWrap) it.next();
			// ��wrap����Ϣ��װ��event
			encapsulationSubCustomerInfo(wrap, event);
			// ����ӿͻ���Ϣ�Ƿ���ȷ,��������з�����Ϣ,Ҳ�����ﴦ��
			brService.checkCreateGroupSubCustomer(event);
			LogUtility.log(clazz, LogLevel.DEBUG, "�ӿͻ���������...........������");
			// �����ǰ�ύ�����򴴽��ӿͻ���Ϣ

			CSIService csiService = new CSIService(context);
			csiService.createGroupSubCustomer(event, context);
			LogUtility.log(clazz, LogLevel.DEBUG, "�ӿͻ���������...........��������");
			
			Customer customer = (Customer) context.get(Service.CUSTOMER);
			String serviceAccountID = (String)context.get(Service.SERVICE_ACCOUNT_ID);
			// ȡ�ô�����ɵ�����,����ȡ������ID�Ϳͻ�ID,��¼��־,
			CustServiceInteraction csi = (CustServiceInteraction) context
					.get(Service.CSI);
			int customerID = csi.getCustomerID();

			SystemLogRecorder.createSystemLog(machineName, operatorID,
					customerID, SystemLogRecorder.LOGMODULE_GROUPCUSTOMER,
					"���ſͻ��ӿͻ���������", 
					"���ſͻ��ӿͻ�����,����ID��" + csi.getId()
					+" �ͻ�ID:"+customer.getCustomerID()
					+" �ͻ�����:"+customer.getName()
					+" ҵ���ʻ�ID:"+serviceAccountID,
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		}

	}

	/**
	 * ͨ���ϴ��ļ����������ӿͻ��ļ��,��������Ч��¼ʱ�����쳣,�����м�¼��Чʱ���쳣,���һ�����м�¼,���ش�����Ϣ.
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void checkGroupCustomerBatchImport(GroupCustomerEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "�ӿͻ�����������...........");
		boolean isValid = false;
		// ��¼�����¼��Ŀ
		int errNumber = 0;
		ArrayList errList = new ArrayList();

		java.util.Collection customers = event.getGroupCustomerWrapList();
		java.util.Iterator it = customers.iterator();

		Collection packagecol = BusinessUtility.getPackageIDByContractID(event
				.getContractNO());
		// ���ݺ�ͬ���ò�Ʒ����Ϣ
		event.setContractToPackageIDCol(packagecol);
		// ����ͬ��Ч��
		BusinessRuleService brService = new BusinessRuleService(context);
		brService.checkGroupCustomerContract(event.getContractNO(), false,
				event.getCustomerID(), customers.size());
		while (it.hasNext()) {
			GroupCustomerWrap wrap = (GroupCustomerWrap) it.next();
			try {
				// ��wrap����Ϣ��װ��event
				encapsulationSubCustomerInfo(wrap, event);
				// ����ӿͻ���Ϣ�Ƿ���ȷ,��������з�����Ϣ,Ҳ�����ﴦ��
				brService.checkCreateGroupSubCustomer(event);
				LogUtility.log(clazz, LogLevel.DEBUG, "�ӿͻ���������...........������");

				isValid = true;
			} catch (Exception e) {
				// ����д����¼,��ӵ�inValidCol,ѭ����������,
				wrap.setErrString(e.getMessage());
				isValid = false;
				errNumber++;
				errList.add(wrap);
			}
			wrap.setValid(isValid);
			
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "\n�ӿͻ��������������,��Ч�ӿͻ���:"
				+ (customers.size() - errNumber) + ",\n��Ч�ӿͻ���:" + errNumber);
		// ������Ч��¼���Ϸ���,
		if(!errList.isEmpty())
		{
			response.setPayload(errList);
		}
		//���ط���
		else
		{
			//�������
			CommonFeeAndPayObject commonObj= PublicService.encapsulateBaseParamForExitCust(
					event.getCsiDto(),event.getCustomerID(),event.getAccountID());
			Collection shouldPayFeeList = FeeService.calculateImmediateFeeForOpen(event.getCsiDto(),event.getContractToPackageIDCol(), new ArrayList(),commonObj);
			this.response.setPayload(shouldPayFeeList);
			//response.setPayload(calculateImmediateFee(event));
			
		}
	}

	/**
	 * ��wrap��װ��event,�͵����ӿͻ�����һ�����,�÷����������������û�����
	 * 
	 * @param wrap
	 * @param event
	 */
	private void encapsulationSubCustomerInfo(GroupCustomerWrap wrap,
			GroupCustomerEJBEvent event) throws ServiceException {
		NewCustomerInfoDTO newCustomerInfoDTO = wrap.getNewCustomerInfoDTO();
		// �����ӿͻ��ļ��ſͻ���Ϣ
		newCustomerInfoDTO.setGroupCustID(event.getCustomerID());
		newCustomerInfoDTO
				.setCustStyle(CommonConstDefinition.CUSTOMERSTYLE_GROUP);
		newCustomerInfoDTO.setType(CommonConstDefinition.CUSTOMERTYPE_GROUP);
		
		newCustomerInfoDTO.setOpenSourceType(event.getOpenSourceType());

		// �����ӿͻ�DTO��event��,
		event.setNewCustomerInfoDTO(wrap.getNewCustomerInfoDTO());
		// �����ӿͻ���ַ��Ϣ��event��
		event.setAddressDTO(wrap.getAddressDTO());
		// ���õ绰����
		event.setPhoneNo(wrap.getServiceCode());
		int itemID = BusinessUtility.getResoucePhoneIDByPhoneNo(wrap
				.getServiceCode());
		LogUtility.log(clazz, LogLevel.DEBUG, "itemID:" + itemID);
		wrap.setPhoneNoItemID(itemID);
		event.setItemID(itemID);
		// ����Ӳ����Ϣ��event��.
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

		// ����������Ϣ
		CustServiceInteractionDTO csiDTO = new CustServiceInteractionDTO();
		// ��������Ϊ���ſͻ�����
		csiDTO.setAccountID(event.getAccountID());
		csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GS);
		// ��ϵ��,��ϵ�绰Ϊ�ͻ�,�ͻ��绰
		csiDTO.setContactPerson(wrap.getNewCustomerInfoDTO().getName());
		csiDTO.setContactPhone(wrap.getNewCustomerInfoDTO().getTelephone());
		event.setCsiDto(csiDTO);
	}

	/**
	 * ���ſͻ�����
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void groupCustomerOpen(GroupCustomerEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"groupCustomerOpen run.................");

		// ����Ƿ��ü���
		if (event.getActionType() == GroupCustomerEJBEvent.GROUPCUSTOMEROPENFEE) {
			this.response.setPayload(calculateImmediateFee(event));
			return;
		}
		// ������ύ����
		if (event.getActionType() == GroupCustomerEJBEvent.GROUPCUSTOMEROPENCONFIRM) {
			// ����ͬ��Ч��
			BusinessRuleService brService = new BusinessRuleService(context);
			brService.checkGroupCustomerContract(event.getContractDTO()
					.getContractNo(), true, 0, 0);

			CSIService csiService = new CSIService(context);
			csiService.createGroupCustServiceInteractionInfo(event);
			CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
			FeeService.payFeeOperationForOpen(event.getCsiDto(),event.getFeeList(),event.getPaymentList(), event.getCsiPrePaymentDeductionList(),commonObj,operatorID);
			csiService.updateCSIPayStatus(context,event.getCsiDto().getPaymentStatus());
			// ȡ�ô�����ɵ�����,����ȡ������ID�Ϳͻ�ID,��¼��־,
			Customer customer = (Customer) context.get(Service.CUSTOMER);
			Account account =(Account)context.get(Service.ACCOUNT);
			CustServiceInteraction csi = (CustServiceInteraction) context
					.get(Service.CSI);
			int customerID = csi.getCustomerID();

			response.setPayload(customerID + "");

			SystemLogRecorder.createSystemLog(machineName, operatorID,
					customerID, SystemLogRecorder.LOGMODULE_GROUPCUSTOMER,
					"���ſͻ�����", "���ſͻ�����,����ID:" + csi.getId()
					+" �ͻ�ID:"+customer.getCustomerID()
					+" �ͻ�����:"+customer.getName()
					+" �ʻ�ID:"+account.getAccountID(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		}

	}
	
	/**
	 * �������
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	private Collection calculateImmediateFee(GroupCustomerEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz,LogLevel.DEBUG,"������������csi="+event.getCsiDto());
		LogUtility.log(clazz,LogLevel.DEBUG,"������������NewCustInfo="+event.getNewCustomerInfoDTO());
		LogUtility.log(clazz,LogLevel.DEBUG,"������������NewCustAcctInfo="+event.getNewCustAccountInfoDTO());
		CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForOpen(event.getCsiDto(),event.getNewCustomerInfoDTO(),event.getNewCustAccountInfoDTO());
		commonObj.setContractNo(event.getContractNO());
		Collection currentFeeList=FeeService.calculateImmediateFeeForOpen(event.getCsiDto(),event.getContractToPackageIDCol(), null,commonObj);
		return currentFeeList;
	}
	
	/**
	 * �����������(���ڼ��ſͻ��ӿͻ��¿��û�)
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
		//Ѻ��
		commonObj.setDeposit(0);
		currentFeeList=FeeService.calculateImmediateFee(event.getCsiDto(),event.getCsiPackageArray(),  event.getCsiCampaignArray(),commonObj);
		
		//}
		//catch(Exception e)
		//{
		//	e.printStackTrace();
		//}
		System.out.println("______���ط���="+currentFeeList);
		return currentFeeList;
		
	}

	
	private ServiceContext initServiceContext(GroupCustomerEJBEvent event) {
		ServiceContext serviceContext = new ServiceContext();
		String description = "";
		String action = "";
		switch (event.getActionType()) {
		case GroupCustomerEJBEvent.GROUPTOSINGLECONFIRM:
			description = "�����ӿͻ�ת���˿ͻ�";
			action = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GM;
			break;
		case GroupCustomerEJBEvent.GROUPCUSTOMEROPENCONFIRM:
			description = "���ſͻ�����";
			action = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GO;
			break;
		case GroupCustomerEJBEvent.CHILDCUST_CREATE:
		case GroupCustomerEJBEvent.GROUPCUSTOMERBATCHIMPORTCONFIRM:
			int accountID = event.getAccountID();
			serviceContext.put(Service.ACCOUNT_ID, new Integer(accountID));
			description = "���ſͻ��ӿͻ�����";
			action = CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GS;
			break;
		}
		serviceContext.put(Service.ACTION_DESCRTIPION, description);
		serviceContext.put(Service.ACTION_DEFITION, action);
		serviceContext.put(Service.OPERATIOR_ID, new Integer(operatorID));
		serviceContext.put(Service.REMOTE_HOST_ADDRESS, event
				.getRemoteHostAddress());
		LogUtility.log(clazz, LogLevel.DEBUG,
				"������������Service.ACTION_DESCRTIPION: "
						+ (String) serviceContext
								.get(Service.ACTION_DESCRTIPION));
		LogUtility.log(clazz, LogLevel.DEBUG, "������������Service.ACTION_DEFITION:"
				+ (String) serviceContext.get(Service.ACTION_DEFITION));
		LogUtility.log(clazz, LogLevel.DEBUG, "������������Service.OPERATIOR_ID:"
				+ (Integer) serviceContext.get(Service.OPERATIOR_ID));
		LogUtility.log(clazz, LogLevel.DEBUG, "������������Service.ACCOUNT_ID:"
				+ (Integer) serviceContext.get(Service.ACCOUNT_ID));

		return serviceContext;
	}

	/**
	 * �õ�Ӳ���б� fiona
	 * ���û��Ӳ����ֱ�ӼƷ� ���ط�����Ϣ
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
	 * �õ������б� fiona
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
	 * ���ſͻ��¿��ӿͻ� fiona
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void openChildCustomer(GroupCustomerEJBEvent event)
			throws ServiceException {
		ServiceContext serviceContext = initServiceContext(event);
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		// ����ӿͻ�����������Ϣ�豸��Ϣ
		ContractDTO contractDTO = BusinessUtility
				.getContractDTOByContractNo(event.getContractNO());

		businessRuleService.checkGroupCustomerContract(event.getContractNO(),
				false, event.getCustomerID(), 1);
		Collection initCsiproductCol = businessRuleService
				.checkOpenChildDevAndGetCsiProdList(event.getCsiDto(),contractDTO, event.getDeviceTable(),
						true);
		// ����������Ϣ
		CSIService cSIService = new CSIService(serviceContext);
		cSIService.openChildCustServiceAccount(event, initCsiproductCol);
		// ����ϵͳ��־
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), event.getCsiDto()
				.getCustomerID(), SystemLogRecorder.LOGMODULE_GROUPCUSTOMER, "���ſͻ��ӿͻ��¿��û�",
				"���ſͻ��ţ�"+event.getCustomerID()+" �ӿͻ�ID��"+event.getCsiDto().getCustomerID()+" �ӿͻ��¿��û�ʹ�ú�ͬ�ţ�"+event.getContractNO(), SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

}
