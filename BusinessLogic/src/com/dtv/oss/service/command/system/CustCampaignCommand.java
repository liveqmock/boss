/*
 * Created on 2004-8-12
 * 
 * @author yanjian
 */
package com.dtv.oss.service.command.system;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.CustomerCampaign;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.domain.ServiceAccount;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.BundlePaymentMethodDTO;
import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerCampaignDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.dto.custom.BatchNoDTO;
import com.dtv.oss.dto.custom.CommonFeeAndPayObject;
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
import com.dtv.oss.service.component.CustomerProductService;
import com.dtv.oss.service.component.FeeService;
import com.dtv.oss.service.component.ImmediateCountFeeInfo;
import com.dtv.oss.service.component.ImmediateCountFeeService;
import com.dtv.oss.service.component.ImmediatePayFeeService;
import com.dtv.oss.service.component.ServiceAccountService;
// import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.system.CustCampaignEJBEvent;
import com.dtv.oss.service.ejbevent.system.SystemEJBEvent;
import com.dtv.oss.service.factory.HomeFactory;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.InfoFeeImmediateBilling;
import com.dtv.oss.service.util.PayFeeUtil;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.service.util.ImmediateFee.ImmediateFeeCalculator;
import com.dtv.oss.util.TimestampUtility;

public class CustCampaignCommand extends Command {
	private static final String commandName = "CustCampaignCommand";

	private static final Class clazz = CustCampaignCommand.class;

	HomeFactory homeFac = null;

	private int operatorID = 0;

	private String machineName = "";

	private ServiceContext serviceContext = null;

	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		CustCampaignEJBEvent inEvent = (CustCampaignEJBEvent) ev;
		response = new CommandResponseImp(null);
		operatorID = inEvent.getOperatorID();
		machineName = inEvent.getRemoteHostAddress();
		serviceContext = initServiceContext(inEvent);
		try {
			homeFac = HomeFactory.getFactory();
			switch (inEvent.getActionType()) {
			// begin fiona
			// ȡ������
			case SystemEJBEvent.CUST_CAMAPAIGN_CANCEL:
				CustCampaignCancel(inEvent);
				break;
			// �ͻ���Ʒ����ά��
			case SystemEJBEvent.CUST_CAMAPAIGN_MODIFY:
				CustCampaignModify(inEvent);
				break;
			// �ӳٴ���
			case SystemEJBEvent.CUST_CAMAPAIGN_DELAY:
				custBundleDelay(inEvent);
				break;
			// �ֹ��������
			case SystemEJBEvent.CUST_CAMAPAIGN_MANUAL_GRANT:
				manualGrantCampaign(inEvent);
				break;
			// �ײ�ά��
			case SystemEJBEvent.CUST_BUNDLE_MODIFY:
				custBundleModify(inEvent);
				break;
			// �ײ�ȡ��
			case SystemEJBEvent.CUST_BUNDLE_CANCEL:
				custBundleCancel(inEvent);
				break;
			// �ײ�ֹͣ
			case SystemEJBEvent.CUST_BUNDLE_STOP:
				custBundleStop(inEvent);
				break;
			// �ײ�����
			case SystemEJBEvent.CUST_BUNDLE_PREPAYMENT:
				custBundlePrePayment(inEvent);
				break;
			// �ײ�ת��
			case SystemEJBEvent.CUST_BUNDLE_TRANSFER:
				custBundleTransfer(inEvent);
				break;
			//Э��ͻ�����
			case SystemEJBEvent.PROTOCOL_BUNDLE_PREPAYMENT:
			    protocolPrePayment(inEvent);
			    break;
			default:
				throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
			}
		} catch (ServiceException ce) {
			LogUtility.log(clazz, LogLevel.ERROR, this, ce);
			throw new CommandException(ce.getMessage());
		} catch (Throwable unkown) {
			LogUtility.log(clazz, LogLevel.FATAL, this, unkown);
			throw new CommandException("����δ֪�Ĵ���");
		}
		return response;
	}

	/**
	 * ȡ���ͻ�����
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void CustCampaignCancel(CustCampaignEJBEvent inEvent)
			throws ServiceException {

		CustomerCampaignDTO dto = inEvent.getCampaignDTO();
		// CustomerCampaignDTO oldDto =
		// BusinessUtility.getCustomerCampaignDTOByID(dto.getCcID());
		// ����Ʒ�Ƿ����ȡ������
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		businessRuleService.checkCusCampaign(dto.getCcID());
		// ȡ���ͻ���Ʒ����
		CampaignService campaignService = new CampaignService(serviceContext);
		CustomerCampaign cc=campaignService.custCampaignCancel(dto);
		// ����ϵͳ��־
		StringBuffer logDesc = new StringBuffer();
		List cancelProduct=BusinessUtility.getPsIDListByCcID(dto.getCcID());
		logDesc
				.append("�ͻ�����ά��������ȡ��;�ͻ�����ID:")
				.append(dto.getCcID())
				.append(";��������:")
				.append(BusinessUtility.getCampaignNameByCCId(dto.getCcID()))
				.append((cancelProduct!=null&&!cancelProduct.isEmpty()?(";��ؿͻ���Ʒ:"+cancelProduct.toString()):"")) ;

		SystemLogRecorder.createSystemLog(machineName, operatorID, cc
				.getCustomerID(), SystemLogRecorder.LOGMODULE_CUSTSERV,
				"�ͻ�����ά��", logDesc.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * �ͻ���Ʒ����ά��
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void CustCampaignModify(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		CustomerCampaignDTO dto = inEvent.getCampaignDTO();
		CustomerCampaignDTO oldDto = BusinessUtility
				.getCustomerCampaignDTOByID(dto.getCcID());
		// ����Ʒ�Ƿ�����޸�����
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		businessRuleService.checkCusCampaign(dto.getCcID());
		// ���¿ͻ���Ʒ������Ϣ
		CampaignService campaignService = new CampaignService(serviceContext);
		campaignService.CustCampaignModify(dto);
		// ����ϵͳ��־
		StringBuffer logDesc = new StringBuffer();
		logDesc.append("�ͻ�����ά��������ά��;�ͻ�����ID:" + dto.getCcID() + ";��������:"
				+ BusinessUtility.getCampaignNameByCCId(dto.getCcID()));
		logDesc.append(SystemLogRecorder.appendDescString(";�Ƿ�������:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowAlter()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAllowAlter())));
		logDesc.append(SystemLogRecorder.appendDescString(";�Ƿ�������ͣ:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowPause()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAllowPause())));
		logDesc.append(SystemLogRecorder.appendDescString(";�Ƿ�����Ǩ��:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowTransition()), BusinessUtility
						.getCommonNameByKey("SET_G_YESNOFLAG", dto
								.getAllowTransition())));
		logDesc.append(SystemLogRecorder.appendDescString(";�Ƿ��������:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowTransfer()), BusinessUtility
						.getCommonNameByKey("SET_G_YESNOFLAG", dto
								.getAllowTransfer())));
		logDesc.append(SystemLogRecorder.appendDescString(";�Ƿ�����ȡ��:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowClose()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAllowClose())));
		logDesc.append(SystemLogRecorder.appendDescString(";��ע:", oldDto
				.getComments(), dto.getComments()));

		SystemLogRecorder.createSystemLog(machineName, operatorID, oldDto
				.getCustomerID(), SystemLogRecorder.LOGMODULE_CUSTSERV,
				"�ͻ�����ά��", logDesc.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	// �ӳٿͻ�������Ʒ
	private void custBundleDelay(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		// BusinessRuleService businessRuleService=new
		// BusinessRuleService(serviceContext);
		// ����Ʒ�Ƿ�����ӳ�����
		// businessRuleService.checkCusCampaign(inEvent.getCampaignDTO().getCcID());
		// ����Ƿ���ز�Ʒ
		// businessRuleService.checkNoExistMultiCampaign(inEvent.getCampaignDTO(),CommonConstDefinition.YESNOFLAG_NO);
		int customerCampaignID = inEvent.getCampaignDTO().getCcID();
		Timestamp endDateTo =inEvent.getCampaignDTO().getDateTo();
		String    comments  =inEvent.getCampaignDTO().getComments();

		// �ӳٲ�Ʒ�����¼�¼
		CampaignService campaignService = new CampaignService(serviceContext);
		CustomerCampaign cc = campaignService.custBundleDelay(
				customerCampaignID, endDateTo,comments);

		// ����ϵͳ��־
		// CampaignDTO
		// ccDTO=BusinessUtility.getCampaignDTOByID(cc.getCampaignID());
		SystemLogRecorder.createSystemLog(machineName, operatorID, cc.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�ͻ��ײ�ά��",
				"�ͻ��ײ�ά��--�ײ�����,�ͻ��ײ�ID��"
						+ cc.getCcID()
						+ ",�ײ�����:"
						+ BusinessUtility.getCampaignNameByCCId(cc.getCcID()
								.intValue()) + ",����" + TimestampUtility.TimestampToDateString(endDateTo)
						, SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}



	/**
	 * �ֹ���������
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void manualGrantCampaign(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		
//		LogUtility.log(clazz, LogLevel.DEBUG, "operatorID>>>>>>>>>>1"+operatorID);
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		CustomerCampaignDTO customerCampaignDTO = inEvent.getCampaignDTO();
		// ���ҵ���ʻ����ʻ���Ʒ�Ƿ������������
		businessRuleService.checkGrantCampaign(customerCampaignDTO, true);
		// �����µļ�¼
		//��װ������Ϣ
		CustServiceInteractionDTO csiDto=encapsulateCustomerCampaignCSIInfo(0,inEvent);
		if (!inEvent.isConfirmPost()) {
			LogUtility.log(clazz, LogLevel.DEBUG,
					"CustCampaignCommand.campaignid:>>>>>" + customerCampaignDTO.getCampaignID());
			ImmediateCountFeeInfo feeInfo = FeeService.calculateManualGrantCampaignFeeInfo(csiDto, customerCampaignDTO); 
		    
			Collection immediateCountFeeCols = new ArrayList();
			immediateCountFeeCols.add(feeInfo);
			this.response.setPayload(immediateCountFeeCols);
			LogUtility.log(clazz, LogLevel.DEBUG,
					"CustCampaignCommand.custBundlePrePayment.feeList:>>>>>"
							+ feeInfo);
			return;
		}
		
		CSIService csiService=new CSIService(serviceContext);
		//������������
		csiService.createCustServiceInteraction(csiDto);
		
		CampaignService campaignService = new CampaignService(serviceContext);
		CustomerCampaign cc = campaignService
				.grantNewCampaign(customerCampaignDTO);
		//ȡ����������
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		cc.setCsiID(csiEJB.getId().intValue());
		//ȡ����ؿͻ���Ʒ
		Collection psidList=(Collection) serviceContext.get(Service.PSID);
		//���� ������صĿͻ���Ʒ
		campaignService.createCsiCustProductInfoWithCampaign(csiEJB.getId().intValue(),cc,CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_NEWPRODUCT);
	    //����֧������
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.inEvent.getFeeList():>>>>>" + inEvent.getFeeList());
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.inEvent.getPaymentList():>>>>>" + inEvent.getPaymentList());
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.inEvent.getCsiPrePaymentDeductionList():>>>>>" + inEvent.getCsiPrePaymentDeductionList());

		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(csiDto,inEvent.getFeeList(),inEvent.getPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);

		//������������״̬
		csiService.updateCSIPayStatus(serviceContext, CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		// ����ϵͳ��־
		String logDesc = "";
		CampaignDTO ccDTO = BusinessUtility
				.getCampaignDTOByID(customerCampaignDTO.getCampaignID());
		if (customerCampaignDTO.getServiceAccountID() != 0) {
			logDesc = "����ҵ���ʻ�:" + customerCampaignDTO.getServiceAccountID();
		} else {
			logDesc = "�����ʻ�:" + customerCampaignDTO.getAccountID();
		}
		csiEJB.setComments(logDesc+",�ͻ�����:"+ccDTO.getCampaignName());
		SystemLogRecorder.createSystemLog(machineName, operatorID,
				cc.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�ֹ���������", "�ֹ���������,"
						+ "�ͻ�����ID:" + cc.getCcID() + ",��������:"
						+ ccDTO.getCampaignName() 
						+ ",����ID:"+csiEJB.getId().intValue()+"," + logDesc,
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * ά���ͻ��ײ�
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void custBundleModify(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		CustomerCampaignDTO dto = inEvent.getCampaignDTO();
		CustomerCampaignDTO oldDto = BusinessUtility
				.getCustomerCampaignDTOByID(dto.getCcID());
		// ����Ʒ�Ƿ�����޸�����
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		businessRuleService.checkCusCampaign(dto.getCcID());
		// ���¿ͻ���Ʒ������Ϣ
		CampaignService campaignService = new CampaignService(serviceContext);
		campaignService.CustCampaignModify(dto);
		// ����ϵͳ��־
		StringBuffer logDesc = new StringBuffer();
		logDesc.append("�ͻ��ײ�ά�����ײ�ά��;�ͻ��ײ�ID:" + dto.getCcID() + ";�ײ�����:"
				+ BusinessUtility.getCampaignNameByCCId(dto.getCcID()));
		logDesc.append(SystemLogRecorder.appendDescString(";�Ƿ�������:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowAlter()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAllowAlter())));
		logDesc.append(SystemLogRecorder.appendDescString(";�Ƿ�������ͣ:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowPause()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAllowPause())));
		logDesc.append(SystemLogRecorder.appendDescString(";�Ƿ�����Ǩ��:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowTransition()), BusinessUtility
						.getCommonNameByKey("SET_G_YESNOFLAG", dto
								.getAllowTransition())));
		logDesc.append(SystemLogRecorder.appendDescString(";�Ƿ��������:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowTransfer()), BusinessUtility
						.getCommonNameByKey("SET_G_YESNOFLAG", dto
								.getAllowTransfer())));
		logDesc.append(SystemLogRecorder.appendDescString(";�Ƿ�����ȡ��:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowClose()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAllowClose())));
		logDesc.append(SystemLogRecorder.appendDescString(";�Ƿ��Զ�����:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAutoExtendFlag()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAutoExtendFlag())));
		logDesc.append(SystemLogRecorder.appendDescString(";��ע:", oldDto
				.getComments(), dto.getComments()));

		SystemLogRecorder.createSystemLog(machineName, operatorID, oldDto
				.getCustomerID(), SystemLogRecorder.LOGMODULE_CUSTSERV,
				"�ͻ��ײ�ά��", logDesc.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * �ײ�ȡ��
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void custBundleCancel(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.CustBundleCancel:>>>>>");

		int ccid=inEvent.getCampaignDTO().getCcID();
		CustomerCampaignDTO dto = BusinessUtility.getCustomerCampaignDTOByID(ccid);
		serviceContext.put(Service.CUSTOMER_ID, new Integer(dto.getCustomerID()));
		serviceContext.put(Service.ACCOUNT_ID, new Integer(dto.getAccountID()));
		// ����ײ��Ƿ����ȡ������
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		//���м��,���ܷ�ȡ���ײ�.
		Map saMap = businessRuleService.checkCustomerBundleCancel(ccid);
		CustServiceInteractionDTO csiDto=encapsulateCustomerCampaignCSIInfo(ccid,inEvent);
		if (!inEvent.isConfirmPost()) {
			Collection shouldPayFeeList=new ArrayList();

			AccountItemDTO deviceFee=FeeService.encapsulateDeviceReturnFee(inEvent.getDeviceFee(), dto.getCustomerID(), dto.getAccountID(), 0);
			ArrayList deviceFeeList=new ArrayList();
			if(deviceFee!=null){
				deviceFeeList.add(deviceFee);
			}
			shouldPayFeeList=FeeService.customerBundleCancelImmediateFeeCalculator(csiDto,ccid,saMap,deviceFeeList);

			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(clazz, LogLevel.DEBUG,
					"CustCampaignCommand.CustBundleCancel.shouldPayFeeList:>>>>>"
							+ shouldPayFeeList.size());
			return;
		}
		boolean isReturnDevice=false;
		if(inEvent.getIsReturnDevice()!=null&&CommonConstDefinition.YESNOFLAG_YES.equals(inEvent.getIsReturnDevice())){
			isReturnDevice=true;
		}
		//��������
		CSIService csiService=new CSIService(serviceContext);
		csiService.createCustServiceInteraction(csiDto);
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		
		ArrayList csiPsidList=new ArrayList();
		//ȡ���ͻ���Ʒ
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		//�����������,��ҵ���ʻ�Ϊ��λ, 
		java.util.Iterator sait=saMap.keySet().iterator();
		StringBuffer saLogInfo=new StringBuffer();
		while(sait.hasNext()){
			Integer said=(Integer) sait.next();
			ArrayList cpList=(ArrayList) saMap.get(said);
			ArrayList psidList=new ArrayList();
			StringBuffer psidLogInfo=new StringBuffer();
			for(int i=0;i<cpList.size();i++){
				int psid=((CustomerProductDTO)cpList.get(i)).getPsID();
				psidList.add(new Integer(psid));
				psidLogInfo.append(psid).append(",");
			}
			csiPsidList.addAll(psidList);
    	    ArrayList saCpList = BusinessUtility.getCustProdDTOListBySaAndPsID(
					said.intValue(), 0, "");
			// �����ǰҵ���ʻ����ײͲ�Ʒ��Ϣ����ҵ���ʻ���Ч��Ʒ����,����ȡ��ҵ���ʻ��ķ���,�������ȡ���ͻ���Ʒ�ķ���,
			if (saCpList.size() == psidList.size()) {
				saLogInfo.append(",ҵ���ʻ�ID:").append(said.intValue()).append(
						",��ҵ���ʻ���ȡ��,ҵ���ʻ��±�ȡ���Ŀͻ���ƷID(").append(psidLogInfo)
						.append(")");
				LogUtility.log(clazz, LogLevel.DEBUG,
						"custBundleCancel.ȡ��ҵ���ʻ�:" + +said.intValue());
				
				if(CommonConstDefinition.YESNOFLAG_YES.equalsIgnoreCase(inEvent.getIsReturnDevice()))
					isReturnDevice=true;
				saService.close(said.intValue(),isReturnDevice);
			} else {
				saLogInfo.append(",ҵ���ʻ�ID:").append(said.intValue()).append(
						",��ҵ���ʻ�������,ҵ���ʻ��±�ȡ���Ŀͻ���ƷID(").append(psidLogInfo)
						.append(")");
				LogUtility.log(clazz, LogLevel.DEBUG,
						"custBundleCancel.ȡ���ͻ���Ʒ:" + psidList);
				CustomerProductService cpService = new CustomerProductService(
						serviceContext, dto.getCustomerID(),
						dto.getAccountID(), said.intValue());
				cpService.cancelCustomerProduct(psidList, isReturnDevice);
			}
		}
		//������Ϣ��
		BatchNoDTO  batchNoDTO=new BatchNoDTO();
		
		/*ע�͵� by david.Yang ,������õ�ʱ��û�и÷��á�����Ҳ��Ӧ����
		Collection infoFeeList=InfoFeeImmediateBilling
		.callImmediateBillingCampaign(
				ccid+"",
				CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_TRANSFE,
				csiDto.getCreateOperatorId(), csiDto.getCreateORGId(),batchNoDTO);
		LogUtility.log(clazz,LogLevel.DEBUG,"infoFeeList.size>>>"+infoFeeList.size());
		*/
		
		Collection infoFeeList=new ArrayList();
		
		// ȡ���ͻ���Ʒ�ײ�
		CampaignService campaignService = new CampaignService(serviceContext);
		CustomerCampaign cc=campaignService.custBundleCancel(ccid);
		//����������Ʒ��Ϣ
		csiService.createCsiCustProductInfo(csiEJB,csiPsidList);
//		campaignService.createCsiCustProductInfoWithCampaign(csiEJB.getId().intValue(),cc,CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_CANCEL);
		//����������á��ɷ�
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(csiDto,inEvent.getFeeList(),inEvent.getPaymentList(),FeeService.reencapsulateFeeInfo(csiDto,infoFeeList,operatorID),null,commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext, csiDto.getPaymentStatus());

		// ����ϵͳ��־
		SystemLogRecorder.createSystemLog(machineName, operatorID, dto
				.getCustomerID(), SystemLogRecorder.LOGMODULE_CUSTSERV,
				"�ͻ��ײ�ȡ��", "�ͻ��ײ�ȡ��;"+
				"����ID:" + csiDto.getId()
				+"�ͻ��ײ�ID:" + dto.getCcID() + ";�ײ�����:"
						+ BusinessUtility.getCampaignNameByCCId(dto.getCcID())
						+ saLogInfo.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
		
		//��������ID,���ڴ�ӡ
		this.response.setPayload(new Integer(csiDto.getId()));
	}

	/**
	 * �ײ���ֹ
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void custBundleStop(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		CustomerCampaignDTO dto = inEvent.getCampaignDTO();
		CampaignService campaignService = new CampaignService(serviceContext);
		campaignService.close();

		SystemLogRecorder.createSystemLog(machineName, operatorID, dto
				.getCustomerID(), SystemLogRecorder.LOGMODULE_CUSTSERV,
				"�ͻ��ײ���ֹ", "�ͻ��ײ���ֹ���ײ���ֹ;�ͻ��ײ�ID:" + dto.getCcID() + ";�ײ�����:"
						+ BusinessUtility.getCampaignNameByCCId(dto.getCcID())
						+ "��ؿͻ���Ʒ:"
						+ BusinessUtility.getPsIDListByCcID(dto.getCcID()),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * �ײ�����
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void custBundlePrePayment(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment:>>>>>");
		int ccid = inEvent.getCampaignDTO().getCcID();
		String rfBillingCycleFlag = inEvent.getRfBillingCycleFlag();
		CustomerCampaignDTO custCampaignDto = BusinessUtility
				.getCustomerCampaignDTOByID(ccid);

		// ����ײ��Ƿ����ȡ������
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		businessRuleService.checkCustomerCampaignPrePayment(ccid);
		CustServiceInteractionDTO csiDto=encapsulateCustomerCampaignCSIInfo(ccid,inEvent);
		if (!inEvent.isConfirmPost()) {
			LogUtility.log(clazz, LogLevel.DEBUG,
					"CustCampaignCommand.ccid:>>>>>" + ccid + "\nrfBillingCycleFlag:>>>>"
							+ rfBillingCycleFlag);

			Collection feeList = InfoFeeImmediateBilling
					.callCustomerCampaignResume(ccid, rfBillingCycleFlag, operatorID, BusinessUtility
							.getOpOrgIDByOperatorID(operatorID)); 
			
			ImmediateCountFeeInfo  immediateCountFeeInfo=ImmediateCountFeeService.countFee(csiDto.getAccountID(),csiDto.getType(),null,null,feeList);
		    
			Collection immediateCountFeeCols = new ArrayList();
			immediateCountFeeCols.add(immediateCountFeeInfo);
			this.response.setPayload(immediateCountFeeCols);
			LogUtility.log(clazz, LogLevel.DEBUG,
					"CustCampaignCommand.custBundlePrePayment.feeList:>>>>>"
							+ feeList);
			return;
		}
		CSIService csiService=new CSIService(serviceContext);
		csiService.createCustServiceInteraction(csiDto);
		// �ײ�����
		CampaignService campaignService = new CampaignService(serviceContext);
		BundlePaymentMethodDTO paymentMethodDto = BusinessUtility
		.getBundlePaymentMethodDTO(custCampaignDto.getCampaignID(), rfBillingCycleFlag);
		CustomerCampaign cc=campaignService.custBundlePrePayment(ccid, paymentMethodDto);

		// �־û����á�֧����Ԥ��ֿ�
		Collection acctItemList =new ArrayList();
		Collection feeList = inEvent.getFeeList();
		Iterator   feeIter =feeList.iterator();
		if (feeIter.hasNext()){
			ImmediateCountFeeInfo imm = (ImmediateCountFeeInfo)feeIter.next();
			acctItemList =imm.getAcctItemList();
		}
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment.acctItemList:>>>>>"
						, acctItemList);
		
	    ImmediatePayFeeService feeService = new ImmediatePayFeeService(
		FeeService.reencapsulateFeeInfo(csiDto,cc,custCampaignDto,rfBillingCycleFlag, acctItemList), 
				        FeeService.reencapsulatePaymentInfo(csiDto, inEvent.getPaymentList(), operatorID), 
						FeeService.reecapsulatePrePaymentInfo(csiDto,inEvent.getCsiPrePaymentDeductionList(),null,operatorID),
				        null);
		FeeService.createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),
				feeService.getPaymentList(), feeService.getPrePaymentList(),null);
		
		boolean mustPay = FeeService.mustCreateFinancesetOffMap(csiDto.getType(), false,
				feeService.getFeeList(), feeService.getPaymentList(),
				feeService.getPrePaymentList());
		if(mustPay)csiDto.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
		feeService.setId(csiDto.getId());
		PayFeeUtil.updatePrepaymentList(feeService.getPaymentList());
		PayFeeUtil.updatePrepaymentDuctionList(feeService.getPrePaymentList());		
		feeService.payFeeDB(feeService.payFee());
		//������������״̬
		csiService.updateCSIPayStatus(serviceContext, csiDto.getPaymentStatus());
		
		//2008-01-24 �����ײ����ѵ��շѵ��ݴ�ӡ���������õĴ�ӡ������Ҫ��ѯ��������id��
		this.response.setPayload(new Integer(csiDto.getId()));
		
		// ����ϵͳ��־
		SystemLogRecorder.createSystemLog(machineName, operatorID,
				cc.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�ײ�����", "�ײ�����;"
				+ "����ID:" + csiDto.getId()
						+";�ͻ��ײ�ID:"
						+ ccid
						+ ";�ײ�����:"
						+ BusinessUtility.getCampaignNameByCCId(ccid)
						+ ";����ʱ�䳤��:"
						+ paymentMethodDto.getTimeUnitLengthNumber()
						+ BusinessUtility.getCommonNameByKey("SET_M_CAMPAIGNTIMELENGTHUNITTYPE",paymentMethodDto.getTimeUnitLengthType())
						+ ";NID����:" 
						+ TimestampUtility.TimestampToDateString(custCampaignDto.getNbrDate())
						+ "-->" 
						+ TimestampUtility.TimestampToDateString(cc.getNbrDate())
						+ ";���Ѻ�Э����Ч�ڼ�:"
						+ TimestampUtility.TimestampToDateString(cc.getDateFrom()) 
						+ "-->" 
						+ TimestampUtility.TimestampToDateString(cc.getDateTo()),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	
	/*
	 * Э��ۿͻ�����
	 */
	private void protocolPrePayment(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.protocolPrePayment:>>>>>");
		if(inEvent.getCsiPackageArray()==null)
 			inEvent.setCsiPackageArray(new ArrayList());
    	 inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));

		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		businessRuleService.checkCustProtocolPackage(inEvent.getCsiDto().getCustomerID(),inEvent.getCsiPackageArray());
		System.out.println("inEvent.getCsiDto().getAccountID()------>"+inEvent.getCsiDto().getAccountID());
		if (inEvent.getCsiDto().getAccountID() ==0)	return;

		if (!inEvent.isConfirmPost()) {
			String saIdStr =inEvent.getSaId_indexs();
						
			Collection protocolList =BusinessUtility.CaculateFeeForProtocol(saIdStr,inEvent.getDateLength(),inEvent.getCsiPackageArray());
			ImmediateCountFeeInfo immediateCountFeeInfo= FeeService.encapsulateFeeInfoAndAccountInfo(inEvent.getCsiDto().getAccountID() ,inEvent.getCsiDto().getType(),null,null);
			immediateCountFeeInfo.setAccountid(inEvent.getCsiDto().getAccountID());
			immediateCountFeeInfo.setAcctItemList(protocolList);
			if(protocolList!=null && !protocolList.isEmpty()){
				Iterator protocolIter=protocolList.iterator();
				long count=0l;
				while(protocolIter.hasNext()){
					AccountItemDTO accountItemDTO=(AccountItemDTO)protocolIter.next();
					count=count+ImmediatePayFeeService.double2long(accountItemDTO.getValue());
				}
				immediateCountFeeInfo.setTotalValueAccountItem((double)count/100);
			}
			Collection currentFeeList =new ArrayList();
			currentFeeList.add(immediateCountFeeInfo);
			this.response.setPayload(currentFeeList);
		}else{
			CSIService csiService=new CSIService(serviceContext);	
			csiService.createCustServiceInteraction(inEvent.getCsiDto());
		    //�޸Ŀͻ���Ʒ�Ľ���ʱ��
		    if(inEvent.getSaIDList()!=null &&!inEvent.getSaIDList().isEmpty()){
		    	Iterator saIDIter=inEvent.getSaIDList().iterator();
		    	while(saIDIter.hasNext()){
		    		CustomerProductService cpService = new CustomerProductService(serviceContext);
				    cpService.changCustProdEndTimeByExtend(inEvent.getCsiPackageArray(),((Integer)saIDIter.next()).intValue(),inEvent.getDateLength(),inEvent.getCsiDto().getId());
		    	}
		    }
		   
			FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),null,null,new CommonFeeAndPayObject(),inEvent.getOperatorID());
			csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
			
			this.response.setPayload(new Integer(inEvent.getCsiDto().getId()));
			// ����ϵͳ��־
			SystemLogRecorder.createSystemLog(machineName, operatorID,
					inEvent.getCsiDto().getCustomerID(),
					SystemLogRecorder.LOGMODULE_CUSTSERV, "Э������", "Э������;"
					+ "����ID:" + inEvent.getCsiDto().getId()
							+";����ҵ���˻�:"
							+ inEvent.getSaId_indexs()
							+ ";��Ʒ������:"
							+ inEvent.getCsiPackageArray()
							+ ";����ʱ�䳤��:"
							+ inEvent.getDateLength()
							,
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
			
		}
		
		
	}
	
	private void custBundleTransfer(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment:>>>>>");
		int ccid = inEvent.getCcid();
		int newCampaignID=inEvent.getNewCampaignID();
		Map addProductMap=inEvent.getAddProductMap();
		ArrayList cancelCpList=inEvent.getCancelProductList();
		Map productPropertyMap=inEvent.getProductPropertyMap();
		HashMap terminalMap=inEvent.getTerminalMap();
		String phoneNo=inEvent.getPhoneNo();
		int itemID=inEvent.getItemID();
		CustomerCampaignDTO custCampaignDto = BusinessUtility
				.getCustomerCampaignDTOByID(ccid);
		
		java.sql.Timestamp dateFrom = custCampaignDto.getDateFrom();
		java.sql.Timestamp dateTo   = custCampaignDto.getDateTo();
		java.sql.Timestamp nbrDate  = custCampaignDto.getNbrDate();
		LogUtility.log(clazz, LogLevel.DEBUG,
		"CustCampaignCommand.custBundlePrePayment.ccid>>>>>"+ccid);
		LogUtility.log(clazz, LogLevel.DEBUG,
		"CustCampaignCommand.custBundlePrePayment.newCampaignID>>>>>"+newCampaignID);
		LogUtility.log(clazz, LogLevel.DEBUG,
		"CustCampaignCommand.custBundlePrePayment.addProductMap>>>>>"+addProductMap);
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment.cancelCpList>>>>>"+cancelCpList);
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment.productPropertyMap>>>>>"+productPropertyMap);
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment.terminalList>>>>>"+terminalMap);
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment.phoneNo>>>>>"+phoneNo+">>>>itemID"+itemID);
		
		// ����ײ��Ƿ����ȡ������
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		businessRuleService.checkBundleTransfer(inEvent.getCsiDto(),custCampaignDto,newCampaignID,addProductMap,cancelCpList,terminalMap);
		//��װ��������.
		CustServiceInteractionDTO csiDto = encapsulateCustomerCampaignCSIInfo(
				ccid, inEvent);
		//�Ʒ�
		if (!inEvent.isConfirmPost()) {
			/*  ��ʱȡ���Ʒѡ���������������ת������������Ǯ��ֻ��ÿ���µ���Ϣ���ò�ͬ���� modify by david.Yang
			CustomerDTO custDto=BusinessUtility.getCustomerDTOByCustomerID(custCampaignDto.getCustomerID());
			AccountDTO acctDto=BusinessUtility.getAcctDTOByAcctID(custCampaignDto.getAccountID());
			int oldPackageID=BusinessUtility.getProductPackageIDWithCampaignID(custCampaignDto.getCampaignID());
			int newPackageID=BusinessUtility.getProductPackageIDWithCampaignID(newCampaignID);
			ImmediateCountFeeInfo feeInfo=FeeService.boundleTrans(csiDto,custDto.getCustomerType(),acctDto.getAccountType(),ccid,newCampaignID,oldPackageID,newPackageID,addProductMap.keySet(),operatorID);
			AccountItemDTO deviceFee=FeeService.encapsulateDeviceReturnFee(inEvent.getDeviceFee(), custCampaignDto.getCustomerID(), custCampaignDto.getAccountID(), 0);
			if(deviceFee!=null){
				feeInfo.getAcctItemList().add(deviceFee);
				feeInfo.setTotalValueAccountItem(FeeService.countFee(feeInfo.getAcctItemList()));
			}
			LogUtility.log(clazz, LogLevel.DEBUG,
					"CustCampaignCommand.custBundlePrePayment.feeInfo>>>>>",feeInfo);
			ArrayList feeList=new ArrayList();
			feeList.add(feeInfo);
			this.response.setPayload(feeList);
			*/
			return ;
		}
		boolean isReturnDevice=false;
		if(inEvent.getIsReturnDevice()!=null&&CommonConstDefinition.YESNOFLAG_YES.equals(inEvent.getIsReturnDevice())){
			isReturnDevice=true;
		}
		CSIService csiService=new CSIService(serviceContext);
		csiService.createCustServiceInteraction(csiDto);
		ArrayList campaignList=new ArrayList();
		campaignList.add(new Integer(newCampaignID));
		csiService.createCsiCustProductInfo( addProductMap.values(), null, campaignList, csiDto);
		CampaignService campaignService=new CampaignService(serviceContext);
		CustomerCampaign cc=campaignService.custBundleTransfer(custCampaignDto, newCampaignID,cancelCpList,addProductMap,productPropertyMap,phoneNo,itemID,isReturnDevice);
		cc.setDateFrom(dateFrom);
		cc.setDateTo(dateTo);
		cc.setNbrDate(nbrDate);
		//�������.
		LogUtility.log(clazz, LogLevel.DEBUG,
				"inEvent.getFeeList()>>>>>",inEvent.getFeeList());		
		LogUtility.log(clazz, LogLevel.DEBUG,
				"inEvent.getPaymentList()>>>>>",inEvent.getPaymentList());		
		FeeService.payFeeOperationForOpen(csiDto,inEvent.getFeeList(),inEvent.getPaymentList(),inEvent.getCsiPrePaymentDeductionList(),new CommonFeeAndPayObject(),inEvent.getOperatorID());
		
		csiService.updateCSIPayStatus(serviceContext, csiDto.getPaymentStatus());
		// ����ϵͳ��־
		ArrayList newSaList=serviceContext.get(Service.NEWSERVICEACCOUNTIDLIST)!=null?(ArrayList)serviceContext.get(Service.NEWSERVICEACCOUNTIDLIST):null;
		ArrayList cancelSaList=serviceContext.get(Service.CANCELSERVICEACCOUNTIDLIST)!=null?(ArrayList)serviceContext.get(Service.CANCELSERVICEACCOUNTIDLIST):null;
		ArrayList addProductCol = new ArrayList();
		addProductCol.add(addProductMap.keySet());
		StringBuffer logDesc = new StringBuffer();
		logDesc.append("�ײ�ת��;");
		logDesc.append("����ID:");
		logDesc.append(csiDto.getId());
		logDesc.append(";�ɵĿͻ��ײ�ID:");
		logDesc.append(ccid);
		logDesc.append(";�µĿͻ��ײ�ID:");
		logDesc.append(cc.getCcID());
		logDesc.append(SystemLogRecorder.appendDescString(";�ײ�:",
				BusinessUtility.getCampaignNameByCCId(ccid), BusinessUtility
						.getCampaignDTOByID(newCampaignID).getCampaignName()));
		if (addProductMap != null && !addProductMap.isEmpty()) {
			logDesc.append(";�����ӵĲ�Ʒ:");
			logDesc.append(BusinessUtility.getProductDesByProductID(addProductCol));
		}
		if (cancelCpList != null && !cancelCpList.isEmpty()) {
			logDesc.append(";ȡ���Ŀͻ���Ʒ:");
			logDesc.append(BusinessUtility
							.getProductDescListByPSIDList(cancelCpList));
		}
		if(newSaList!=null&&!newSaList.isEmpty()){
			logDesc.append("�����ӵ�ҵ���ʻ�:");
			logDesc.append(newSaList.toString());
		}
		if(cancelSaList!=null&&!cancelSaList.isEmpty()){
			logDesc.append("ɾ����ҵ���ʻ�:");
			logDesc.append(cancelSaList.toString());
		}
		SystemLogRecorder.createSystemLog(machineName, operatorID,
				custCampaignDto.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�ײ�ת��", logDesc
						.toString(), SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private ServiceContext initServiceContext(CustCampaignEJBEvent event) {
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.put(Service.OPERATIOR_ID, new Integer(event.getOperatorID()));
		serviceContext.put(Service.REMOTE_HOST_ADDRESS, event
				.getRemoteHostAddress());
		return serviceContext;
	}
//	private void updateCSIStatus(CustServiceInteractionDTO csiDto)
//			throws ServiceException {
////		boolean mustPay = mustCreateFinancesetOffMap(csiDto.getType(), false,
////				feeService.getFeeList(), feeService.getPaymentList(),
////				feeService.getPrePaymentList());
//		CSIService csiService = new CSIService(serviceContext);
//		csiService
//				.updateCSIPayStatus(serviceContext, csiDto.getPaymentStatus());
//	}
	private CustServiceInteractionDTO encapsulateCustomerCampaignCSIInfo(
			int ccid, CustCampaignEJBEvent inEvent) {
		CustServiceInteractionDTO csiDto = inEvent.getCsiDto();
		if(inEvent.getCsiDto()==null){
			csiDto = new CustServiceInteractionDTO();
		}
		
		CustomerCampaignDTO ccDto = null;
		if(ccid!=0){
			ccDto=BusinessUtility
			.getCustomerCampaignDTOByID(ccid);
		}else{
			ccDto=inEvent.getCampaignDTO();
		}
		csiDto.setCreateOperatorId(operatorID);
		csiDto.setCreateORGId(BusinessUtility
						.getOpOrgIDByOperatorID(operatorID));
		csiDto.setAccountID(ccDto.getAccountID());
		csiDto.setCustomerID(ccDto.getCustomerID());
//		csiDto
//		.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		csiDto.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
		csiDto.setComments(ccid+"");
		int actionType=inEvent.getActionType();
		switch (actionType) {
			// �ײ�����
		case SystemEJBEvent.CUST_BUNDLE_DELAY:
			csiDto
					.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BDE);
			break;
			//�ײ�ת��
		case SystemEJBEvent.CUST_BUNDLE_TRANSFER:
			csiDto
					.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BDS);
			break;
			//�ײ�����
		case SystemEJBEvent.CUST_BUNDLE_PREPAYMENT:
			csiDto
					.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BDP);
			break;
			//�ײ�ȡ��
		case SystemEJBEvent.CUST_BUNDLE_CANCEL:
			csiDto
					.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BDC);
			break;
			//�ֹ��������
		case SystemEJBEvent.CUST_CAMAPAIGN_MANUAL_GRANT:
			csiDto
			.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_MAC);
			break;
		default:
			return null;
		}
		return csiDto;
	}
}
