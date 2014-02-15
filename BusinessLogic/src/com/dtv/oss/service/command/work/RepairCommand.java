/*
 * Created on 2005-9-21
 * 
 * @author whq
 * 
 * 包括报修的处理和报修工单的处理
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
			case EJBEvent.ASSIGN_REPAIR: //报修派工
				assignRepair(inEvent.getJobCardDtos(),inEvent.getNextOrgID());
				break;
			case EJBEvent.PROCESS_CUSTOMER_PROBLEM: //报修单创建
				processCustomerProblem(inEvent);
				break;
			//				case EJBEvent.REPAIR_ADJUST_OP: //报修单调帐
			//				    break;
			case EJBEvent.MANUAL_TRANSFER_REPAIR_SHEET: //报修单手工流转
				manualTransferRepairSheet(inEvent.getCustProblemProcessDto());
				break;
			case EJBEvent.CALL_CUSTOMER_FOR_REPAIR: //报修回访
				callCustomerForRepair(inEvent.getCustomerProblemDto());
				break;
			case EJBEvent.MANUAL_CLOSE_REPAIR_SHEET: //手工结束报修单
				manualCloseRepairSheet(inEvent.getCustProblemProcessDtos());
				break;
			case EJBEvent.TERMINATE_REPAIR_INFO: //终止维修
				terminateRepairInfo(inEvent.getCustProblemProcessDtos());
				break;
			case EJBEvent.DIAGNOSIS_REPAIR:  //报修诊断
				diagnosisForRepair(inEvent);
				break;
			default:
				throw new IllegalArgumentException(
						"RepairEJBEvent中actionType的设置不正确。");
			}
		} catch (ServiceException ce) {
			LogUtility.log(clazz, LogLevel.ERROR, this, ce);
			throw new CommandException(ce.getMessage());
		} catch (Throwable unkown) {
			LogUtility.log(clazz, LogLevel.FATAL, this, unkown);
			throw new CommandException("发生未知的错误。");
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
					"CustomerProblemDTO中的故障级别(ProblemLevel)没有设置。");

		ServiceContext context = initServiceContext();
		CustomerProblemService cps = new CustomerProblemService(cpDto.getId(),context);

		LogUtility.log(clazz, LogLevel.DEBUG, "■■■■■■" + inEvent.isDoPost());

		LogUtility.log(clazz, LogLevel.DEBUG, "■■■DoPost■■■"
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

	//	报修派工
	private void assignRepair(Collection jobCardDtos,int nextOrgID) throws ServiceException, CommandException {
		if ((jobCardDtos == null) || jobCardDtos.isEmpty())
			throw new ServiceException("jobCardDtos为null或者为空.");
		//if (nextOrgID == 0)
		//	throw new ServiceException("流转部门ID为空！ ");
		Iterator it = jobCardDtos.iterator();
		//if(nextOrgID == 0){
		/*	int canBeTransferedID = BusinessUtility.checkCpColCanAutotransfer(jobCardDtos);
			if (canBeTransferedID == 0){
				throw new ServiceException("所选的报修单默认的流转部门不同！");
			}*/
			//nextOrgID = canBeTransferedID;
		//}
		while (it.hasNext()) {
			JobCardDTO jcDto = (JobCardDTO) it.next();
			assignRepairForEach(jcDto,nextOrgID);
		}

	}

	//报修派工，单个处理
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
				throw new ServiceException("该报修单(报修单ID：" + cpId
						+ ")目前状态不对，只有状态为待处理的报修单才能派工！");

			JobCardService jcs = new RepairJobCardService(context);
			String cpDescription = BusinessUtility.getCustomerProblemDTOByID(jobCardDTO.getReferSheetId()).getProblemDesc();
			jobCardDTO.setDescription(cpDescription);
			jobCardDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
			jobCardDTO.setCreateMethod(CommonConstDefinition.JOBCARDCREATEMETHOD_A);
			jobCardDTO.setReferType(CommonConstDefinition.JOBCARD_REFERTYPE_P);
			jcs.createJobCard(jobCardDTO,nextOrgID);

			cps.assign(jcs.getJobCardNo(),nextOrgID);
			jcs.relateWithOriginalSheet(cps);

			//插入相关的日志记录。
			String description = "报修派工,报修单ID:" + cpId + ",工单ID:"
					+ jcs.getJobCardNo();
			SystemLogRecorder.keyLog(description, "报修派工", machineName,
					SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, jcs
							.getCustomerID());
			
			response.setPayload(new Integer(jcs.getJobCardNo()));
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}

	}

	//报修
	private void processCustomerProblem(RepairEJBEvent inEvent)
			throws HomeFactoryException, FinderException, ServiceException {

		CustomerProblemDTO cpDto = inEvent.getCustomerProblemDto();
		Collection diDtos = inEvent.getDiagnosisInfoDtos();
		int nextOrgID = inEvent.getNextOrgID();
		LogUtility.log(clazz, LogLevel.DEBUG, "■■■nextOrgID■■■" + nextOrgID);

		if (cpDto == null)
			throw new ServiceException(
					"CustomerProblemDTO is not set properly.");
		if (HelperCommonUtil.StringHaveContent(cpDto.getProblemLevel()) == false)
			throw new ServiceException(
					"CustomerProblemDTO中的故障级别(ProblemLevel)没有设置。");

		ServiceContext context = initServiceContext();
		CustomerProblemService cps = new CustomerProblemService(context);

		LogUtility.log(clazz, LogLevel.DEBUG, "■■■cpDto■■■" + cpDto);

		LogUtility.log(clazz, LogLevel.DEBUG, "■■■DoPost■■■"
				+ inEvent.isDoPost());

		if (!inEvent.isDoPost()) {

			Collection feeCol = FeeService
					.customerProblemOpImmediateFeeCalculator(cpDto, cpDto.getCustAccountID(), inEvent
							.getOperatorID());
			response.setPayload(feeCol);
			int autoNextOrgID=BusinessUtility.getAutoNextOrgID(CommonConstDefinition.ROLEORGANIZATIONORGROLE_REPAIRE,cpDto);
			response.setExtraPayLoad(new Integer(autoNextOrgID));
			LogUtility.log(clazz, LogLevel.DEBUG, "■■■autoNextOrgID■■■" + autoNextOrgID);
			cps.checkDiagnosis(diDtos);
			return;
		}
		//debug
		LogUtility.log(clazz, LogLevel.DEBUG, "■■■■■■"
				+ inEvent.getCustomerProblemDto());
		cps.createInstance(cpDto, nextOrgID);
		System.out.println("%%%%%%%%%zhenduan:"+diDtos);
		//创建诊断信息记录
		cps.recordDiagnosisInfo(diDtos);

		//创建维修费用
		//Collection feeCol = FeeService.customerProblemOpImmediateFeeCalculator(
		//		cpDto, cpDto.getCustAccountID(), inEvent.getOperatorID());
		FeeService.createProblemOpFee(cpDto, cpDto.getCustAccountID(), inEvent.getFeeList(),
				inEvent.getOperatorID());
		//record log
		String description = "报修，报修单ID：" + cps.getCpNo();
		SystemLogRecorder.keyLog(description, "报修", machineName,
				SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, cps
						.getCustomerID());
		System.out.println("the 报修单 号码 是 ————————————— " + cps.getCpNo());
		response.setPayload(new Integer(cps.getCpNo()));

	}

	//报修单调帐
	private void repairAdjustOp(CustomerProblemDTO cpDto, Collection diDtos,
			int nextOrgID) throws ServiceException {

	}

	//报修单手工流转
	private void manualTransferRepairSheet(
			CustProblemProcessDTO custProblemProcessDto)
			throws ServiceException {
		if ((custProblemProcessDto == null)
				|| (custProblemProcessDto.getCustProblemId() == 0))
			throw new ServiceException(
					"CustProblemProcessDTO为null或者报修单ID没有设置");
		ServiceContext context = initServiceContext();
		CustomerProblemService cps = new CustomerProblemService(
				custProblemProcessDto.getCustProblemId(), context);
		try {
			if (cps.canManualTransfer() == false)
				throw new ServiceException("只有待处理或者维修不成功的报修单才可以进行流转！");
			cps.manualTransfer(custProblemProcessDto);

			//record log
			String description = "报修单手工流转，报修单ID："
					+ custProblemProcessDto.getCustProblemId();
			SystemLogRecorder.keyLog(description, "报修单手工流转", machineName,
					SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, cps
							.getCustomerID());
		} catch (ServiceException e) {
			throw new ServiceException(e);
		}

	}

	private void checkParameter(CustomerProblemDTO cpDto) throws ServiceException {
		if ((cpDto == null) || (cpDto.getId() == 0))
			throw new ServiceException(
					"CustomerProblemDTO为null或者报修单ID没有设置");
	}

	//报修回访
	private void callCustomerForRepair(CustomerProblemDTO cpDto)
			throws ServiceException {
		checkParameter(cpDto);
		ServiceContext context = initServiceContext();
		CustomerProblemService cps = new CustomerProblemService(cpDto.getId(),
				context);
		try {
			if (cps.canCallCustomerForRepair() == false)
				throw new ServiceException("只有已排障或者无法继续维修的报修单才可以进行报修回访！");
			cps.callCustomerForRepair();

			//record log
			String description = "报修回访，报修单ID：" + cpDto.getId();
			SystemLogRecorder.keyLog(description, "报修回访", machineName,
					SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, cps
							.getCustomerID());

		} catch (ServiceException e) {
			throw new ServiceException(e);
		}
	}

	/*
	 * 手工结束报修单
	 * 批量处理
	 */
	private void manualCloseRepairSheet(Collection custProblemProcessDtos)
			throws ServiceException {
		if ((custProblemProcessDtos == null)
				|| custProblemProcessDtos.isEmpty())
			throw new ServiceException(
					"custProblemProcessDtos为null或者为空.");
		Iterator it = custProblemProcessDtos.iterator();
		while (it.hasNext()) {
			CustProblemProcessDTO cppDto = (CustProblemProcessDTO) it.next();
			manualCloseRepairSheetForEach(cppDto);
		}
	}

	/*
	 * 手工结束报修单
	 * 单个处理
	 */
	private void manualCloseRepairSheetForEach(
			CustProblemProcessDTO custProblemProcessDto)
			throws ServiceException {
		if ((custProblemProcessDto == null)
				|| (custProblemProcessDto.getCustProblemId() == 0))
			throw new ServiceException(
					"CustProblemProcessDTO为null或者报修单ID没有设置");
		ServiceContext context = initServiceContext();
		CustomerProblemService cps = new CustomerProblemService(
				custProblemProcessDto.getCustProblemId(), context);
		try {
			if (cps.canManualClose() == false)
				throw new ServiceException("只有状态为待处理的报修单才能手工结束！");
			cps.manualCloseRepairSheet(custProblemProcessDto);

			//          插入相关的日志记录。
			String description = "手工结束报修单，报修单ID："
					+ custProblemProcessDto.getCustProblemId();
			SystemLogRecorder.keyLog(description, "手工结束报修单", machineName,
					SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, cps
							.getCustomerID());

		} catch (ServiceException e) {
			throw new ServiceException(e);
		}
	}

	/*
	 * 终止维修
	 * 批量处理
	 */
	private void terminateRepairInfo(Collection custProblemProcessDtos)
			throws ServiceException {
		if ((custProblemProcessDtos == null)
				|| custProblemProcessDtos.isEmpty())
			throw new ServiceException(
					"custProblemProcessDtos为null或者为空.");
		Iterator it = custProblemProcessDtos.iterator();
		while (it.hasNext()) {
			CustProblemProcessDTO cppDto = (CustProblemProcessDTO) it.next();
			terminateRepairInfoForEach(cppDto);
		}

	}

	/*
	 * 终止维修
	 * 单个处理
	 */
	private void terminateRepairInfoForEach(
			CustProblemProcessDTO custProblemProcessDto)
			throws ServiceException {
		if ((custProblemProcessDto == null)
				|| (custProblemProcessDto.getCustProblemId() == 0))
			throw new ServiceException(
					"CustProblemProcessDTO为null或者报修单ID没有设置");
		ServiceContext context = initServiceContext();
		CustomerProblemService cps = new CustomerProblemService(
				custProblemProcessDto.getCustProblemId(), context);
		try {
			if (cps.canTerminate() == false)
				throw new ServiceException("只有状态为维修不成功的报修单才能终止！");
			cps.terminateRepairInfo(custProblemProcessDto);

			//插入相关的日志记录。
			String description = "终止维修，报修单ID："
					+ custProblemProcessDto.getCustProblemId();
			SystemLogRecorder.keyLog(description, "终止维修", machineName,
					SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, cps
							.getCustomerID());

		} catch (ServiceException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 初始化ServiceContext
	 * 将一些通用的信息放入ServiceContext
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
