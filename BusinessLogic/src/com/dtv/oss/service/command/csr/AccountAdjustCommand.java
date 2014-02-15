/**
 * ��,2007-1-26
 */
package com.dtv.oss.service.command.csr;

import javax.ejb.FinderException;

import com.dtv.oss.domain.AccountAdjustment;
import com.dtv.oss.dto.AccountAdjustmentDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.*;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.component.AccountAdjustService;
import com.dtv.oss.service.component.BusinessRuleService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.*;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.SystemLogRecorder;

/**
 * @author Hou �ʵ����ʺ��ʻ����ʶ�������,
 */
public class AccountAdjustCommand extends Command {
	private static final Class clazz = AccountAdjustCommand.class;

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		AccountAdjustEJBEvent inEvent = (AccountAdjustEJBEvent) ev;
		this.operatorID = inEvent.getOperatorID();
		this.machineName = inEvent.getRemoteHostAddress();
		LogUtility.log(clazz, LogLevel.DEBUG, "Enter " + clazz.getName()
				+ " execute() now.operatorID:"+operatorID);
		try {
			switch (inEvent.getActionType()) {
			case AccountAdjustEJBEvent.ACCOUNT_ADJUST_PREPAYMENTDEDUCTION:
				adjustAccountPrePaymentDeduction(inEvent);
				break;
			case AccountAdjustEJBEvent.ACCOUNT_ADJUST_PREPAYMENT:
				adjustAccountPayment(inEvent);
				break;
			case AccountAdjustEJBEvent.ACCOUNT_ADJUST_FEE:
				adjustAccountFee(inEvent);
				break;
			case AccountAdjustEJBEvent.ACCOUNT_ADJUST_SPECIALFEE:
				adjustAccountFee(inEvent);
				break;
			case AccountAdjustEJBEvent.ACCOUNT_ADJUST_PAYMENT:
				adjustAccountPayment(inEvent);
				break;
			case AccountAdjustEJBEvent.BILL_ADJUST_FEE:
				adjustBillFee(inEvent);
				break;
			case AccountAdjustEJBEvent.BILL_ADJUST_PREPAYMENT:
				adjustBillPayment(inEvent);
				break;
			case AccountAdjustEJBEvent.BILL_ADJUST_PAYMENT:
				adjustBillPayment(inEvent);
				break;
			case AccountAdjustEJBEvent.PROBLEM_ADJUST_PAYMENT:
				adjustProblemPayment(inEvent);
				break;
			case AccountAdjustEJBEvent.PROBLEM_ADJUST_FEE:
				adjustProblemFee(inEvent);
				break;
			case AccountAdjustEJBEvent.CSI_ADJUST_PAYMENT:
				adjustCSIPayment(inEvent);
				break;
			case AccountAdjustEJBEvent.CSI_ADJUST_FEE:
				adjustCSIFee(inEvent);
				break;
			default:
				throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
			}
		} catch (ServiceException ce) {
			LogUtility.log(clazz, LogLevel.ERROR, this, ce);
			throw new CommandException(ce.getMessage());
		} catch (Throwable unkown) {
			LogUtility.log(clazz, LogLevel.FATAL, this, unkown);
			throw new CommandException(unkown.getMessage());
		}
		return response;
	}

	private void adjustCSIFee(AccountAdjustEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "adjustCSIFee::::::::::::");
		
		ServiceContext serviceContext=initServiceContext(event);
		BusinessRuleService ruleService = new BusinessRuleService(serviceContext);
		AccountAdjustService aaService = new AccountAdjustService(serviceContext);

		AccountItemDTO accountItemDTO = event.getAccountItemDTO();

		AccountAdjustmentDTO accountAdjustmentDTO = event
				.getAccountAdjustmentDTO();
		accountAdjustmentDTO
				.setReferRecordType(CommonConstDefinition.ADJUST_REFERCODETYPE_F);

		// ҵ����
		int csiID = accountItemDTO.getReferID();
		ruleService.checkCSIAdjustment(csiID,accountItemDTO.getAcctID(),
				accountItemDTO.getValue(), accountAdjustmentDTO);

		// �ύ����
		AccountAdjustment accountAdjustment = aaService.adjustCSIFee(
				accountItemDTO, accountAdjustmentDTO);

		// ��¼��־
		String logDescript = "��������--" + "��Ӧ�շ��õ���,����ID:" + csiID+",";
		createSystemLogWithAdjust(accountItemDTO.getValue(), accountAdjustment,
				"��������", logDescript);
	}

	private void adjustCSIPayment(AccountAdjustEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "adjustCSIPayment::::::::::::");

		ServiceContext serviceContext=initServiceContext(event);
		BusinessRuleService ruleService = new BusinessRuleService(serviceContext);
		AccountAdjustService aaService = new AccountAdjustService(serviceContext);

		PaymentRecordDTO paymentRecordDTO = event.getPaymentRecordDTO();
		double amount = paymentRecordDTO.getAmount();
		AccountAdjustmentDTO accountAdjustmentDTO = event
				.getAccountAdjustmentDTO();
		accountAdjustmentDTO
		.setReferRecordType(CommonConstDefinition.ADJUST_REFERCODETYPE_C);
		int csiID = paymentRecordDTO.getReferID();
		// ҵ����
		ruleService.checkCSIAdjustment(csiID, paymentRecordDTO.getAcctID(),paymentRecordDTO
				.getAmount(), accountAdjustmentDTO);

		// �ύ����
		AccountAdjustment accountAdjustment = aaService.adjustCSIPayment(
				paymentRecordDTO, accountAdjustmentDTO);

		// ��¼��־
		String logDescript = "";
		logDescript = "��������--" + "��ʵ��֧������,����ID:" + csiID+",";
		if (CommonConstDefinition.PREPAYMENTTYPE_CASH.equals(paymentRecordDTO
				.getPrepaymentType())) {
			logDescript += "�ֽ�֧��,";
		} else if (CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY
				.equals(paymentRecordDTO.getPrepaymentType())) {
			logDescript += "�������֧��,";
		}

		createSystemLogWithAdjust(amount, accountAdjustment, "��������",
				logDescript);
	}

	private void adjustProblemFee(AccountAdjustEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "adjustProblemFee:");


		ServiceContext serviceContext=initServiceContext(event);
		BusinessRuleService ruleService = new BusinessRuleService(serviceContext);
		AccountAdjustService aaService = new AccountAdjustService(serviceContext);

		AccountItemDTO accountItemDTO = event.getAccountItemDTO();

		AccountAdjustmentDTO accountAdjustmentDTO = event
				.getAccountAdjustmentDTO();
		accountAdjustmentDTO
				.setReferRecordType(CommonConstDefinition.ADJUST_REFERCODETYPE_F);

		// ҵ����
		int problemID = accountItemDTO.getReferID();
		ruleService.checkProblemAdjustment(problemID,
				accountItemDTO.getValue(), accountAdjustmentDTO);

		// �ύ����
		AccountAdjustment accountAdjustment = aaService.adjustProblemFee(
				accountItemDTO, accountAdjustmentDTO);

		// ��¼��־
		String logDescript = "���޵�����--" + "��Ӧ�շ��õ���,���޵�ID:" + problemID+",";
		createSystemLogWithAdjust(accountItemDTO.getValue(), accountAdjustment,
				"���޵�����", logDescript);
	}

	private void adjustProblemPayment(AccountAdjustEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "adjustProblemPayment:");

		ServiceContext serviceContext=initServiceContext(event);
		BusinessRuleService ruleService = new BusinessRuleService(serviceContext);
		AccountAdjustService aaService = new AccountAdjustService(serviceContext);

		PaymentRecordDTO paymentRecordDTO = event.getPaymentRecordDTO();
		double amount = paymentRecordDTO.getAmount();
		AccountAdjustmentDTO accountAdjustmentDTO = event
				.getAccountAdjustmentDTO();
		accountAdjustmentDTO
		.setReferRecordType(CommonConstDefinition.ADJUST_REFERCODETYPE_C);

		int problemID = paymentRecordDTO.getReferID();
		// ҵ����
		ruleService.checkProblemAdjustment(problemID, paymentRecordDTO
				.getAmount(), accountAdjustmentDTO);

		// �ύ����
		AccountAdjustment accountAdjustment = aaService.adjustProblemPayment(
				paymentRecordDTO, accountAdjustmentDTO);

		// ��¼��־
		String logDescript = "";
		logDescript = "���޵�����--" + "��ʵ��֧������,���޵�ID:" + problemID+",";
		if (CommonConstDefinition.PREPAYMENTTYPE_CASH.equals(paymentRecordDTO
				.getPrepaymentType())) {
			logDescript += "�ֽ�֧��,";
		} else if (CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY
				.equals(paymentRecordDTO.getPrepaymentType())) {
			logDescript += "�������֧��,";
		}

		createSystemLogWithAdjust(amount, accountAdjustment, "���޵�����",
				logDescript);
	}

	/**
	 * �ʵ�����,��ʵ��֧����ʵ��Ԥ��
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void adjustBillPayment(AccountAdjustEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "adjustBillPayment:");


		ServiceContext serviceContext=initServiceContext(event);
		BusinessRuleService ruleService = new BusinessRuleService(serviceContext);
		AccountAdjustService aaService = new AccountAdjustService(serviceContext);

		PaymentRecordDTO paymentRecordDTO = event.getPaymentRecordDTO();
		double amount = paymentRecordDTO.getAmount();
		AccountAdjustmentDTO accountAdjustmentDTO = event
				.getAccountAdjustmentDTO();

		if (AccountAdjustEJBEvent.BILL_ADJUST_PREPAYMENT == event
				.getActionType()) {
			paymentRecordDTO
					.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE);
			accountAdjustmentDTO
					.setReferRecordType(CommonConstDefinition.ADJUST_REFERCODETYPE_P);

		} else if (AccountAdjustEJBEvent.BILL_ADJUST_PAYMENT == event
				.getActionType()) {
			paymentRecordDTO
					.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
			paymentRecordDTO.setPaidTo(paymentRecordDTO.getInvoiceNo());

			accountAdjustmentDTO
					.setReferRecordType(CommonConstDefinition.ADJUST_REFERCODETYPE_C);
		}
		// ҵ����
		ruleService.checkBillAdjustment(paymentRecordDTO.getInvoiceNo(),
				paymentRecordDTO.getAmount(), accountAdjustmentDTO);

		// �ύ����
		AccountAdjustment accountAdjustment = aaService.adjustBillPayment(
				paymentRecordDTO, accountAdjustmentDTO);

		// ��¼��־
		String logDescript = "";
		if (AccountAdjustEJBEvent.BILL_ADJUST_PAYMENT == event.getActionType()) {
			logDescript = "�ʵ�����--" + "��ʵ��֧������,�ʵ���:"
					+ paymentRecordDTO.getInvoiceNo() + ",";
		} else if (AccountAdjustEJBEvent.BILL_ADJUST_PREPAYMENT == event
				.getActionType()) {
			logDescript = "�ʵ�����--" + "��ʵ��Ԥ�����,�ʵ���:"
					+ paymentRecordDTO.getInvoiceNo() + ",";
		}
		if (CommonConstDefinition.PREPAYMENTTYPE_CASH.equals(paymentRecordDTO
				.getPrepaymentType())) {
			logDescript += "�ֽ�֧��,";
		} else if (CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY
				.equals(paymentRecordDTO.getPrepaymentType())) {
			logDescript += "�������֧��,";
		}

		createSystemLogWithAdjust(amount, accountAdjustment, "�ʵ�����",
				logDescript);
	}

	/**
	 * �ʵ�����,Ӧ�շ��õ���
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void adjustBillFee(AccountAdjustEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "adjustBillFee:");


		ServiceContext serviceContext=initServiceContext(event);
		BusinessRuleService ruleService = new BusinessRuleService(serviceContext);
		AccountAdjustService aaService = new AccountAdjustService(serviceContext);

		AccountItemDTO accountItemDTO = event.getAccountItemDTO();

		AccountAdjustmentDTO accountAdjustmentDTO = event
				.getAccountAdjustmentDTO();
		accountAdjustmentDTO
				.setReferRecordType(CommonConstDefinition.ADJUST_REFERCODETYPE_F);

		// ҵ����
		ruleService.checkBillAdjustment(accountItemDTO.getInvoiceNO(),
				accountItemDTO.getValue(), accountAdjustmentDTO);

		// �ύ����
		AccountAdjustment accountAdjustment = aaService.adjustBillFee(
				accountItemDTO, accountAdjustmentDTO);

		// ��¼��־
		String logDescript = "�ʵ�����--" + "��Ӧ�շ��õ���,�ʵ���:"
				+ accountItemDTO.getInvoiceNO() + ",";
		createSystemLogWithAdjust(accountItemDTO.getValue(), accountAdjustment,
				"�ʵ�����", logDescript);
	}

	/**
	 * �ʻ�����,Ӧ�շ���.
	 * 
	 * @param event
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void adjustAccountFee(AccountAdjustEJBEvent event)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "adjustAccountFee:");


		ServiceContext serviceContext=initServiceContext(event);
		BusinessRuleService ruleService = new BusinessRuleService(serviceContext);
		AccountAdjustService aaService = new AccountAdjustService(serviceContext);

		AccountItemDTO accountItemDTO = event.getAccountItemDTO();

		AccountAdjustmentDTO accountAdjustmentDTO = event
				.getAccountAdjustmentDTO();
		accountAdjustmentDTO
				.setReferRecordType(CommonConstDefinition.ADJUST_REFERCODETYPE_F);

		if (event.getActionType() !=AccountAdjustEJBEvent.ACCOUNT_ADJUST_SPECIALFEE){
			// ҵ����
		   ruleService.checkAccountAdjustment(accountAdjustmentDTO,accountItemDTO, null, null);
		}
		// �ύ����
		AccountAdjustment accountAdjustment = aaService.adjustAccountFee(
				accountItemDTO, accountAdjustmentDTO);

		// ��¼��־
		String logDescript = "�ʻ�����--"
				+ "��Ӧ�շ��õ���,"
				+ "��Ŀ����:"
				+ BusinessUtility.getAcctItemTypeName(accountItemDTO
						.getAcctItemTypeID()) + ",";
		createSystemLogWithAdjust(accountItemDTO.getValue(), accountAdjustment,
				"�ʻ�����", logDescript);
	}

	private void adjustAccountPrePaymentDeduction(AccountAdjustEJBEvent event) {

	}

	/**
	 * �ʻ�����,ʵ��֧����ʵ��Ԥ�����
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void adjustAccountPayment(AccountAdjustEJBEvent event)
			throws ServiceException {


		ServiceContext serviceContext=initServiceContext(event);
		BusinessRuleService ruleService = new BusinessRuleService(serviceContext);
		AccountAdjustService aaService = new AccountAdjustService(serviceContext);
		AccountAdjustmentDTO accountAdjustmentDTO = event
				.getAccountAdjustmentDTO();

		PaymentRecordDTO paymentRecordDTO = event.getPaymentRecordDTO();

		if (AccountAdjustEJBEvent.ACCOUNT_ADJUST_PREPAYMENT == event
				.getActionType()) {
			paymentRecordDTO
					.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE);

			accountAdjustmentDTO
					.setReferRecordType(CommonConstDefinition.ADJUST_REFERCODETYPE_P);
		} else if (AccountAdjustEJBEvent.ACCOUNT_ADJUST_PAYMENT == event
				.getActionType()) {
			paymentRecordDTO
					.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);

			accountAdjustmentDTO
					.setReferRecordType(CommonConstDefinition.ADJUST_REFERCODETYPE_C);
		}
		
		// ҵ����
		ruleService.checkAccountAdjustment(accountAdjustmentDTO, null,
				paymentRecordDTO, null);
		AccountAdjustment accountAdjustment = aaService.adjustAccountPayment(
				event.getActionType(), paymentRecordDTO, accountAdjustmentDTO);
		String logDescript = "";
		if (AccountAdjustEJBEvent.ACCOUNT_ADJUST_PAYMENT == event
				.getActionType()) {
			logDescript = "�ʻ�����--" + "��ʵ��֧������,";
		} else if (AccountAdjustEJBEvent.ACCOUNT_ADJUST_PREPAYMENT == event
				.getActionType()) {
			logDescript = "�ʻ�����--" + "��ʵ��Ԥ�����,";
		}
		if (CommonConstDefinition.PREPAYMENTTYPE_CASH.equals(paymentRecordDTO
				.getPrepaymentType())) {
			logDescript += "�ֽ�֧��,";
		} else if (CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY
				.equals(paymentRecordDTO.getPrepaymentType())) {
			logDescript += "�������֧��,";
		}

		// ��¼��־
		createSystemLogWithAdjust(paymentRecordDTO.getAmount(),
				accountAdjustment, "�ʻ�����", logDescript);
	}

	/**
	 * ����д��־����,��Ϊÿһ��¼дһ����־
	 * 
	 * @param accountAdjustmentCol
	 * @param machineName
	 * @param operatorID
	 * @param customerID
	 * @param operation
	 * @param logDesc
	 * @throws ServiceException
	 */
	private void createSystemLogWithAdjust(double amount,
			AccountAdjustment accountAdjustment, String operation,
			String logDesc) throws ServiceException {

		StringBuffer re = new StringBuffer();

		re.append("���ʵ�:");
		re.append(accountAdjustment.getSeqNo());
		re.append(",��������:");
		re.append(BusinessUtility.getCommonNameByKey(
				"SET_F_ACCOUNTADJUSTMENTTYPE", accountAdjustment
						.getAdjustmentType()));
		re.append(",����ԭ��:");
		re
				.append(BusinessUtility.getCommonNameByKey(
						"SET_F_ACCOUNTADJUSTMENTREASON", accountAdjustment
								.getReason()));
		re.append(",���ʶ���:(");
		re.append(accountAdjustment.getReferRecordID());
		re.append(")");
		re.append(BusinessUtility.getCommonNameByKey(
				"SET_F_ADJUSTMENTREFERRECORDTYPE", accountAdjustment
						.getReferRecordType()));
		re.append(",���:");
		re.append(amount);

		SystemLogRecorder.createSystemLog(machineName, operatorID,
				accountAdjustment.getCustID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc
						+ re.toString(), SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * ��ʼ��ServiceContext
	 * 
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(AccountAdjustEJBEvent event) {
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.put(Service.OPERATIOR_ID, new Integer(operatorID));
		return serviceContext;
	}
}
