/*
 * Created on 2005-9-21
 * 
 * @author whq
 * 
 * �������޵Ĵ���ͱ��޹����Ĵ���
 */
package com.dtv.oss.service.command.work;

import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.*;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;

import com.dtv.oss.service.ejbevent.work.RepairEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.dto.*;
import com.dtv.oss.service.component.CustomerProblemService;
import com.dtv.oss.service.component.JobCardService;
import com.dtv.oss.service.component.RepairJobCardService;
import com.dtv.oss.service.util.HelperCommonUtil;
import com.dtv.oss.service.component.*;
import com.dtv.oss.util.TimestampUtility;

import java.util.*;

import javax.ejb.FinderException;

public class RepairCommand extends Command {
	private static final Class clazz = RepairCommand.class;

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		RepairEJBEvent inEvent = (RepairEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");

		try {
			switch (inEvent.getActionType()) {
			case EJBEvent.ASSIGN_REPAIR: //�����ɹ�
				assignRepair(inEvent.getJobCardDtos(),inEvent.getNextOrgID());
				break;
			case EJBEvent.PROCESS_CUSTOMER_PROBLEM: //���޵�����
				processCustomerProblem(inEvent);
				break;
			//				case EJBEvent.REPAIR_ADJUST_OP: //���޵�����
			//				    break;
			case EJBEvent.MANUAL_TRANSFER_REPAIR_SHEET: //���޵��ֹ���ת
				manualTransferRepairSheet(inEvent.getCustProblemProcessDto());
				break;
			case EJBEvent.CALL_CUSTOMER_FOR_REPAIR: //���޻ط�
				callCustomerForRepair(inEvent.getCustomerProblemDto());
				break;
			case EJBEvent.MANUAL_CLOSE_REPAIR_SHEET: //�ֹ��������޵�
				manualCloseRepairSheet(inEvent.getCustProblemProcessDtos());
				break;
			case EJBEvent.TERMINATE_REPAIR_INFO: //��ֹά��
				terminateRepairInfo(inEvent.getCustProblemProcessDtos());
				break;
			case EJBEvent.DIAGNOSIS_REPAIR:  //�������
				diagnosisForRepair(inEvent);
				break;
			default:
				throw new IllegalArgumentException(
						"RepairEJBEvent��actionType�����ò���ȷ��");
			}
		} catch (ServiceException ce) {
			LogUtility.log(clazz, LogLevel.ERROR, this, ce);
			throw new CommandException(ce.getMessage());
		} catch (Throwable unkown) {
			LogUtility.log(clazz, LogLevel.FATAL, this, unkown);
			throw new CommandException("����δ֪�Ĵ���");
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "Leave execute() now.");
		return response;
	}

	private void diagnosisForRepair(RepairEJBEvent inEvent) throws ServiceException, FinderException, HomeFactoryException {
		CustomerProblemDTO cpDto = inEvent.getCustomerProblemDto();
		Collection diDtos = inEvent.getDiagnosisInfoDtos();
		int nextOrgID = inEvent.getNextOrgID();

		if (cpDto == null)
			throw new ServiceException(
					"CustomerProblemDTO is not set properly.");
		if (HelperCommonUtil.StringHaveContent(cpDto.getProblemLevel()) == false)
			throw new ServiceException(
					"CustomerProblemDTO�еĹ��ϼ���(ProblemLevel)û�����á�");

		ServiceContext context = initServiceContext();
		CustomerProblemService cps = new CustomerProblemService(cpDto.getId(),context);

		LogUtility.log(clazz, LogLevel.DEBUG, "������������" + inEvent.isDoPost());

		LogUtility.log(clazz, LogLevel.DEBUG, "������DoPost������"
				+ inEvent.isDoPost());

		if (!inEvent.isDoPost()) {
			int autoNextOrgID=BusinessUtility.getAutoNextOrgID(CommonConstDefinition.ROLEORGANIZATIONORGROLE_REPAIRE,cpDto,inEvent.getDiagnosisResult());
			cps.checkDiagnosis(diDtos);
			response.setPayload(new Integer(autoNextOrgID));
			return;
		}
		cps.recordDiagnosisInfo(diDtos);
		cps.diagnosisForRepair(inEvent,nextOrgID);
		
	}

	//	�����ɹ�
	private void assignRepair(Collection jobCardDtos,int nextOrgID) throws ServiceException, CommandException {
		if ((jobCardDtos == null) || jobCardDtos.isEmpty())
			throw new ServiceException("jobCardDtosΪnull����Ϊ��.");
		//if (nextOrgID == 0)
		//	throw new ServiceException("��ת����IDΪ�գ� ");
		Iterator it = jobCardDtos.iterator();
		//if(nextOrgID == 0){
		/*	int canBeTransferedID = BusinessUtility.checkCpColCanAutotransfer(jobCardDtos);
			if (canBeTransferedID == 0){
				throw new ServiceException("��ѡ�ı��޵�Ĭ�ϵ���ת���Ų�ͬ��");
			}*/
			//nextOrgID = canBeTransferedID;
		//}
		while (it.hasNext()) {
			JobCardDTO jcDto = (JobCardDTO) it.next();
			assignRepairForEach(jcDto,nextOrgID);
		}

	}

	//�����ɹ�����������
	private void assignRepairForEach(JobCardDTO jobCardDTO, int nextOrgID)
			throws ServiceException {
		if ((jobCardDTO == null) || (jobCardDTO.getReferSheetId() == 0))
			throw new ServiceException("jobCardDTO is not set properly");
		//if (nextOrgID == 0)
		//	throw new ServiceException("nextOrgID has no proper value");
		ServiceContext context = initServiceContext();
		try {
			int cpId = jobCardDTO.getReferSheetId();
			
			CustomerProblemService cps = new CustomerProblemService(cpId,
					context);

			if (cps.canBeAssigned() == false)
				throw new ServiceException("�ñ��޵�(���޵�ID��" + cpId
						+ ")Ŀǰ״̬���ԣ�ֻ��״̬Ϊ������ı��޵������ɹ���");

			JobCardService jcs = new RepairJobCardService(context);
			String cpDescription = BusinessUtility.getCustomerProblemDTOByID(jobCardDTO.getReferSheetId()).getProblemDesc();
			jobCardDTO.setDescription(cpDescription);
			jobCardDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
			jobCardDTO.setCreateMethod(CommonConstDefinition.JOBCARDCREATEMETHOD_A);
			jobCardDTO.setReferType(CommonConstDefinition.JOBCARD_REFERTYPE_P);
			jcs.createJobCard(jobCardDTO,nextOrgID);

			cps.assign(jcs.getJobCardNo(),nextOrgID);
			jcs.relateWithOriginalSheet(cps);

			//������ص���־��¼��
			String description = "�����ɹ�,���޵�ID:" + cpId + ",����ID:"
					+ jcs.getJobCardNo();
			SystemLogRecorder.keyLog(description, "�����ɹ�", machineName,
					SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs
							.getCustomerID());
			
			response.setPayload(new Integer(jcs.getJobCardNo()));
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}

	}

	//����
	private void processCustomerProblem(RepairEJBEvent inEvent)
			throws HomeFactoryException, FinderException, ServiceException {

		CustomerProblemDTO cpDto = inEvent.getCustomerProblemDto();
		Collection diDtos = inEvent.getDiagnosisInfoDtos();
		int nextOrgID = inEvent.getNextOrgID();
		LogUtility.log(clazz, LogLevel.DEBUG, "������nextOrgID������" + nextOrgID);

		if (cpDto == null)
			throw new ServiceException(
					"CustomerProblemDTO is not set properly.");
		if (HelperCommonUtil.StringHaveContent(cpDto.getProblemLevel()) == false)
			throw new ServiceException(
					"CustomerProblemDTO�еĹ��ϼ���(ProblemLevel)û�����á�");

		ServiceContext context = initServiceContext();
		CustomerProblemService cps = new CustomerProblemService(context);

		LogUtility.log(clazz, LogLevel.DEBUG, "������cpDto������" + cpDto);

		LogUtility.log(clazz, LogLevel.DEBUG, "������DoPost������"
				+ inEvent.isDoPost());

		if (!inEvent.isDoPost()) {

			Collection feeCol = FeeService
					.customerProblemOpImmediateFeeCalculator(cpDto, cpDto.getCustAccountID(), inEvent
							.getOperatorID());
			response.setPayload(feeCol);
			int autoNextOrgID=BusinessUtility.getAutoNextOrgID(CommonConstDefinition.ROLEORGANIZATIONORGROLE_REPAIRE,cpDto);
			response.setExtraPayLoad(new Integer(autoNextOrgID));
			LogUtility.log(clazz, LogLevel.DEBUG, "������autoNextOrgID������" + autoNextOrgID);
			cps.checkDiagnosis(diDtos);
			return;
		}
		//debug
		LogUtility.log(clazz, LogLevel.DEBUG, "������������"
				+ inEvent.getCustomerProblemDto());
		cps.createInstance(cpDto, nextOrgID);
		System.out.println("%%%%%%%%%zhenduan:"+diDtos);
		//���������Ϣ��¼
		cps.recordDiagnosisInfo(diDtos);

		//����ά�޷���
		//Collection feeCol = FeeService.customerProblemOpImmediateFeeCalculator(
		//		cpDto, cpDto.getCustAccountID(), inEvent.getOperatorID());
		FeeService.createProblemOpFee(cpDto, cpDto.getCustAccountID(), inEvent.getFeeList(),
				inEvent.getOperatorID());
		//record log
		String description = "���ޣ����޵�ID��" + cps.getCpNo();
		SystemLogRecorder.keyLog(description, "����", machineName,
				SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, cps
						.getCustomerID());
		System.out.println("the ���޵� ���� �� �������������������������� " + cps.getCpNo());
		response.setPayload(new Integer(cps.getCpNo()));

	}

	//���޵�����
	private void repairAdjustOp(CustomerProblemDTO cpDto, Collection diDtos,
			int nextOrgID) throws ServiceException {

	}

	//���޵��ֹ���ת
	private void manualTransferRepairSheet(
			CustProblemProcessDTO custProblemProcessDto)
			throws ServiceException {
		if ((custProblemProcessDto == null)
				|| (custProblemProcessDto.getCustProblemId() == 0))
			throw new ServiceException(
					"CustProblemProcessDTOΪnull���߱��޵�IDû������");
		ServiceContext context = initServiceContext();
		CustomerProblemService cps = new CustomerProblemService(
				custProblemProcessDto.getCustProblemId(), context);
		try {
			if (cps.canManualTransfer() == false)
				throw new ServiceException("ֻ�д��������ά�޲��ɹ��ı��޵��ſ��Խ�����ת��");
			cps.manualTransfer(custProblemProcessDto);

			//record log
			String description = "���޵��ֹ���ת�����޵�ID��"
					+ custProblemProcessDto.getCustProblemId();
			SystemLogRecorder.keyLog(description, "���޵��ֹ���ת", machineName,
					SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, cps
							.getCustomerID());
		} catch (ServiceException e) {
			throw new ServiceException(e);
		}

	}

	private void checkParameter(CustomerProblemDTO cpDto) throws ServiceException {
		if ((cpDto == null) || (cpDto.getId() == 0))
			throw new ServiceException(
					"CustomerProblemDTOΪnull���߱��޵�IDû������");
	}

	//���޻ط�
	private void callCustomerForRepair(CustomerProblemDTO cpDto)
			throws ServiceException {
		checkParameter(cpDto);
		ServiceContext context = initServiceContext();
		CustomerProblemService cps = new CustomerProblemService(cpDto.getId(),
				context);
		try {
			if (cps.canCallCustomerForRepair() == false)
				throw new ServiceException("ֻ�������ϻ����޷�����ά�޵ı��޵��ſ��Խ��б��޻طã�");
			cps.callCustomerForRepair();

			//record log
			String description = "���޻طã����޵�ID��" + cpDto.getId();
			SystemLogRecorder.keyLog(description, "���޻ط�", machineName,
					SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, cps
							.getCustomerID());

		} catch (ServiceException e) {
			throw new ServiceException(e);
		}
	}

	/*
	 * �ֹ��������޵�
	 * ��������
	 */
	private void manualCloseRepairSheet(Collection custProblemProcessDtos)
			throws ServiceException {
		if ((custProblemProcessDtos == null)
				|| custProblemProcessDtos.isEmpty())
			throw new ServiceException(
					"custProblemProcessDtosΪnull����Ϊ��.");
		Iterator it = custProblemProcessDtos.iterator();
		while (it.hasNext()) {
			CustProblemProcessDTO cppDto = (CustProblemProcessDTO) it.next();
			manualCloseRepairSheetForEach(cppDto);
		}
	}

	/*
	 * �ֹ��������޵�
	 * ��������
	 */
	private void manualCloseRepairSheetForEach(
			CustProblemProcessDTO custProblemProcessDto)
			throws ServiceException {
		if ((custProblemProcessDto == null)
				|| (custProblemProcessDto.getCustProblemId() == 0))
			throw new ServiceException(
					"CustProblemProcessDTOΪnull���߱��޵�IDû������");
		ServiceContext context = initServiceContext();
		CustomerProblemService cps = new CustomerProblemService(
				custProblemProcessDto.getCustProblemId(), context);
		try {
			if (cps.canManualClose() == false)
				throw new ServiceException("ֻ��״̬Ϊ������ı��޵������ֹ�������");
			cps.manualCloseRepairSheet(custProblemProcessDto);

			//          ������ص���־��¼��
			String description = "�ֹ��������޵������޵�ID��"
					+ custProblemProcessDto.getCustProblemId();
			SystemLogRecorder.keyLog(description, "�ֹ��������޵�", machineName,
					SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, cps
							.getCustomerID());

		} catch (ServiceException e) {
			throw new ServiceException(e);
		}
	}

	/*
	 * ��ֹά��
	 * ��������
	 */
	private void terminateRepairInfo(Collection custProblemProcessDtos)
			throws ServiceException {
		if ((custProblemProcessDtos == null)
				|| custProblemProcessDtos.isEmpty())
			throw new ServiceException(
					"custProblemProcessDtosΪnull����Ϊ��.");
		Iterator it = custProblemProcessDtos.iterator();
		while (it.hasNext()) {
			CustProblemProcessDTO cppDto = (CustProblemProcessDTO) it.next();
			terminateRepairInfoForEach(cppDto);
		}

	}

	/*
	 * ��ֹά��
	 * ��������
	 */
	private void terminateRepairInfoForEach(
			CustProblemProcessDTO custProblemProcessDto)
			throws ServiceException {
		if ((custProblemProcessDto == null)
				|| (custProblemProcessDto.getCustProblemId() == 0))
			throw new ServiceException(
					"CustProblemProcessDTOΪnull���߱��޵�IDû������");
		ServiceContext context = initServiceContext();
		CustomerProblemService cps = new CustomerProblemService(
				custProblemProcessDto.getCustProblemId(), context);
		try {
			if (cps.canTerminate() == false)
				throw new ServiceException("ֻ��״̬Ϊά�޲��ɹ��ı��޵�������ֹ��");
			cps.terminateRepairInfo(custProblemProcessDto);

			//������ص���־��¼��
			String description = "��ֹά�ޣ����޵�ID��"
					+ custProblemProcessDto.getCustProblemId();
			SystemLogRecorder.keyLog(description, "��ֹά��", machineName,
					SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, cps
							.getCustomerID());

		} catch (ServiceException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * ��ʼ��ServiceContext
	 * ��һЩͨ�õ���Ϣ����ServiceContext
	 */
	private ServiceContext initServiceContext() {
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.put(Service.OPERATIOR_ID, new Integer(operatorID));

		return serviceContext;
	}

	public static void main(String[] args) {
		//	    try {
		//	    RepairCommand test = new RepairCommand();
		//	    test.checkParameter(null);
		//	    } catch (Exception e) {
		//	        LogUtility.log(RepairCommand.class, LogLevel.ERROR, e);
		//	        e.printStackTrace();
		//	    }
	}
}
