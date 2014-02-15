/*
 * �����2007-1-26
 */
package com.dtv.oss.service.component;

import javax.ejb.*;

import java.sql.Timestamp;
import java.util.*;

import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.log.*;
import com.dtv.oss.dto.*;
import com.dtv.oss.domain.*;
import com.dtv.oss.service.util.*;
import com.dtv.oss.service.factory.*;
import com.dtv.oss.util.*;
import com.dtv.oss.service.ejbevent.csr.AccountAdjustEJBEvent;

/**
 * 
 * 
 * �������ʻ���ص�ҵ������
 */
public class AccountAdjustService extends AbstractService {
	private static final Class clazz = AccountAdjustService.class;

	private ServiceContext serviceContext = null;

	public AccountAdjustService(int operatorID) {
		this.setOperatorID(operatorID);
	}

	/**
	 * constructer method
	 * 
	 * @param context
	 */
	public AccountAdjustService(ServiceContext serviceContext) {
		this.serviceContext = serviceContext;
		setOperatorID(PublicService.getCurrentOperatorID(serviceContext));
	}

	public AccountAdjustment adjustCSIFee(AccountItemDTO accountItemDTO,
			AccountAdjustmentDTO accountAdjustmentDTO) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "adjustCSIFee:::::::::::::;:");
		try {
			int orgID = BusinessUtility.FindOrgIDByOperatorID(getOperatorID());
			Timestamp curDate = TimestampUtility.getCurrentTimestamp();

			// ��ֵ

			accountItemDTO.setCreateTime(curDate);
			accountItemDTO.setOperatorID(getOperatorID());
			accountItemDTO.setOrgID(orgID);
			accountItemDTO
					.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_NO);
			if("P".equals(accountItemDTO.getFeeType())){
				accountItemDTO
				.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_YES);
			}
			accountItemDTO.setDate1(curDate);
			accountItemDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			accountItemDTO.setReferType(CommonConstDefinition.FTREFERTYPE_M);
			accountItemDTO.setInvoiceFlag(CommonConstDefinition.YESNOFLAG_NO);
			accountItemDTO.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_W);

			accountItemDTO
					.setAdjustmentFlag(CommonConstDefinition.YESNOFLAG_YES);
			accountItemDTO
					.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_M);

			AccountItemHome acctItemHome = HomeLocater.getAccountItemHome();
			AccountItem accountItem = acctItemHome.create(accountItemDTO);
			accountItemDTO.setAiNO(accountItem.getAiNO().intValue());

			LogUtility.log(clazz, LogLevel.DEBUG, "accountItemDTO:"
					+ accountItemDTO);
			// ��ֵ
			accountAdjustmentDTO.setCreateTime(curDate);
			accountAdjustmentDTO.setCreateOpID(getOperatorID());
			accountAdjustmentDTO.setCreateOrgID(orgID);


			accountAdjustmentDTO
					.setAdjustmentType(CommonConstDefinition.ADJUST_TYPE_S);
			accountAdjustmentDTO
					.setStatus(CommonConstDefinition.ADJUST_STATUS_VALIDATION);
			accountAdjustmentDTO
					.setReferRecordType(CommonConstDefinition.ADJUST_REFERCODETYPE_F);

			AccountAdjustmentHome acctAdjustmentHome = HomeLocater
					.getAccountAdjustmentHome();
			AccountAdjustment accountAdjustment = acctAdjustmentHome
					.create(accountAdjustmentDTO);
			accountAdjustmentDTO.setSeqNo(accountAdjustment.getSeqNo()
					.intValue());
			accountAdjustment
					.setReferRecordID(accountItem.getAiNO().intValue());
			accountItem.setSourceRecordID(accountAdjustment.getSeqNo()
					.intValue());
			accountItem
					.setAdjustmentNo(accountAdjustment.getSeqNo().intValue());

			int csiID=accountItemDTO.getReferID();
			double tatolFee=((Double)serviceContext.get(Service.TATOLFEE)).doubleValue();
			double tatolPayment=((Double)serviceContext.get(Service.TATOLPAYMENT)).doubleValue();
			modifyCsiPaymetnStatus(csiID,tatolFee+accountItemDTO.getValue(),tatolPayment);

			//����
			Collection feeList=(ArrayList)serviceContext.get(Service.FEELIST);
			feeList.add(accountItemDTO);
			Collection paymentList=(ArrayList)serviceContext.get(Service.PAYMENTLIST);

			ImmediatePayFeeService feeService = new ImmediatePayFeeService(
					feeList, paymentList, null, null);

			feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
			feeService.setId(csiID);
			feeService.payFeeDB(feeService.AdjustSetOff());
			
			return accountAdjustment;
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��λ����");
			throw new ServiceException("��λ����");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��������");
			throw new ServiceException("��������");
		}

	}

	public AccountAdjustment adjustCSIPayment(
			PaymentRecordDTO paymentRecordDTO,
			AccountAdjustmentDTO accountAdjustmentDTO) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "adjustCSIPayment:::::::::::::;:");

		try {
			int orgID = BusinessUtility.FindOrgIDByOperatorID(getOperatorID());
			Timestamp curDate = TimestampUtility.getCurrentTimestamp();

			paymentRecordDTO.setCreateTime(curDate);
			paymentRecordDTO
					.setSourceType(CommonConstDefinition.FTREFERTYPE_M);
			paymentRecordDTO.setOrgID(BusinessUtility
					.FindOrgIDByOperatorID(paymentRecordDTO.getOpID()));// �շ�����֯ID
			paymentRecordDTO
					.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
			paymentRecordDTO.setReferType(CommonConstDefinition.FTREFERTYPE_M);
			paymentRecordDTO
					.setStatus(CommonConstDefinition.ADJUST_STATUS_VALIDATION);
			paymentRecordDTO
					.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
			paymentRecordDTO
					.setAdjustmentFlag(CommonConstDefinition.YESNOFLAG_YES);
			paymentRecordDTO
					.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_M);

			PaymentRecordHome paymentRecordHome = HomeLocater
					.getPaymentRecordHome();
			PaymentRecord paymentRecord = paymentRecordHome
					.create(paymentRecordDTO);
			paymentRecordDTO.setSeqNo(paymentRecord.getSeqNo().intValue());

			LogUtility.log(clazz, LogLevel.DEBUG, "accountAdjustmentDTO:"
					+ accountAdjustmentDTO);

			accountAdjustmentDTO.setCreateTime(curDate);
			accountAdjustmentDTO.setCreateOpID(getOperatorID());
			accountAdjustmentDTO.setCreateOrgID(orgID);

			accountAdjustmentDTO
					.setAdjustmentType(CommonConstDefinition.ADJUST_TYPE_S);


			accountAdjustmentDTO
					.setStatus(CommonConstDefinition.ADJUST_STATUS_VALIDATION);

			AccountAdjustmentHome acctAdjustmentHome = HomeLocater
					.getAccountAdjustmentHome();
			AccountAdjustment accountAdjustment = acctAdjustmentHome
					.create(accountAdjustmentDTO);
			accountAdjustmentDTO.setSeqNo(accountAdjustment.getSeqNo()
					.intValue());
			accountAdjustment.setReferRecordID(paymentRecord.getSeqNo()
					.intValue());
			paymentRecord.setAdjustmentNo(accountAdjustment.getSeqNo()
					.intValue());

			//����
			int csiID=paymentRecordDTO.getReferID();
			Collection feeList=(ArrayList)serviceContext.get(Service.FEELIST);
			Collection paymentList=(ArrayList)serviceContext.get(Service.PAYMENTLIST);
			paymentList.add(paymentRecordDTO);
			ImmediatePayFeeService feeService = new ImmediatePayFeeService(
					feeList, paymentList, null, null);

			feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
			feeService.setId(csiID);
			feeService.payFeeDB(feeService.AdjustSetOff());
			
			double tatolFee=((Double)serviceContext.get(Service.TATOLFEE)).doubleValue();
			double tatolPayment=((Double)serviceContext.get(Service.TATOLPAYMENT)).doubleValue();
			modifyCsiPaymetnStatus(csiID,tatolFee,tatolPayment+paymentRecordDTO.getAmount());
			
			LogUtility.log(clazz, LogLevel.DEBUG, "paymentRecord:"
					+ paymentRecordDTO);
			return accountAdjustment;
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��λ����");
			throw new ServiceException("��λ����");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��������");
			throw new ServiceException("��������");
		}
	}
	private void modifyCsiPaymetnStatus(int csiID,double totalFee,double totalPayment){
		LogUtility.log(clazz, LogLevel.DEBUG, "modifyCsiPaymetnStatus.csiID:"+ csiID);
		LogUtility.log(clazz, LogLevel.DEBUG, "modifyCsiPaymetnStatus.totalFee:"+ totalFee);
		LogUtility.log(clazz, LogLevel.DEBUG, "modifyCsiPaymetnStatus.totalPayment:"+ totalPayment);
		try {
			//if(totalFee==totalPayment){
			if(Math.abs(totalFee-totalPayment)<0.0001){
				CustServiceInteractionHome cHome=HomeLocater.getCustServiceInteractionHome();
				CustServiceInteraction csi=cHome.findByPrimaryKey(new Integer(csiID));
				csi.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
			}
		} catch (HomeFactoryException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		}
		
	}
	public AccountAdjustment adjustProblemFee(AccountItemDTO accountItemDTO,
			AccountAdjustmentDTO accountAdjustmentDTO) throws ServiceException {
		try {
			int orgID = BusinessUtility.FindOrgIDByOperatorID(getOperatorID());
			Timestamp curDate = TimestampUtility.getCurrentTimestamp();

			// ��ֵ

			accountItemDTO.setCreateTime(curDate);
			accountItemDTO.setOperatorID(getOperatorID());
			accountItemDTO.setOrgID(orgID);
			accountItemDTO
					.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_NO);
			if("P".equals(accountItemDTO.getFeeType())){
				accountItemDTO
				.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_YES);
			}			
			accountItemDTO.setDate1(curDate);
			accountItemDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			accountItemDTO.setReferType(CommonConstDefinition.FTREFERTYPE_P);
			accountItemDTO.setInvoiceFlag(CommonConstDefinition.YESNOFLAG_NO);
			accountItemDTO.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_W);

			accountItemDTO
					.setAdjustmentFlag(CommonConstDefinition.YESNOFLAG_YES);
			accountItemDTO
					.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_M);

			AccountItemHome acctItemHome = HomeLocater.getAccountItemHome();
			AccountItem accountItem = acctItemHome.create(accountItemDTO);
			accountItemDTO.setAiNO(accountItem.getAiNO().intValue());

			LogUtility.log(clazz, LogLevel.DEBUG, "accountItemDTO:"
					+ accountItemDTO);
			// ��ֵ
			accountAdjustmentDTO.setCreateTime(curDate);
			accountAdjustmentDTO.setCreateOpID(getOperatorID());
			accountAdjustmentDTO.setCreateOrgID(orgID);


			accountAdjustmentDTO
					.setAdjustmentType(CommonConstDefinition.ADJUST_TYPE_C);
			accountAdjustmentDTO
					.setStatus(CommonConstDefinition.ADJUST_STATUS_VALIDATION);
			accountAdjustmentDTO
					.setReferRecordType(CommonConstDefinition.ADJUST_REFERCODETYPE_F);

			AccountAdjustmentHome acctAdjustmentHome = HomeLocater
					.getAccountAdjustmentHome();
			AccountAdjustment accountAdjustment = acctAdjustmentHome
					.create(accountAdjustmentDTO);
			accountAdjustmentDTO.setSeqNo(accountAdjustment.getSeqNo()
					.intValue());
			accountAdjustment
					.setReferRecordID(accountItem.getAiNO().intValue());
			accountItem.setSourceRecordID(accountAdjustment.getSeqNo()
					.intValue());
			accountItem
					.setAdjustmentNo(accountAdjustment.getSeqNo().intValue());

			//����
			int problemID=accountItemDTO.getReferID();
			Collection feeList=BusinessUtility.getFeeListByTypeAndID(problemID, CommonConstDefinition.FTREFERTYPE_P, false);
			Collection paymentList=BusinessUtility.getPaymentListByTypeAndID(problemID, CommonConstDefinition.FTREFERTYPE_P, false);

			ImmediatePayFeeService feeService = new ImmediatePayFeeService(
					feeList, paymentList, null, null);

			feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_M);
			feeService.setId(problemID);
			feeService.payFeeDB(feeService.AdjustSetOff());
			
			return accountAdjustment;
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��λ����");
			throw new ServiceException("��λ����");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��������");
			throw new ServiceException("��������");
		}

	}

	public AccountAdjustment adjustProblemPayment(
			PaymentRecordDTO paymentRecordDTO,
			AccountAdjustmentDTO accountAdjustmentDTO) throws ServiceException {
		try {
			int orgID = BusinessUtility.FindOrgIDByOperatorID(getOperatorID());
			Timestamp curDate = TimestampUtility.getCurrentTimestamp();

			paymentRecordDTO.setCreateTime(curDate);
			paymentRecordDTO
					.setSourceType(CommonConstDefinition.FTREFERTYPE_P);
			paymentRecordDTO.setOrgID(BusinessUtility
					.FindOrgIDByOperatorID(paymentRecordDTO.getOpID()));// �շ�����֯ID
			paymentRecordDTO
					.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
			paymentRecordDTO.setReferType(CommonConstDefinition.FTREFERTYPE_P);
			paymentRecordDTO
					.setStatus(CommonConstDefinition.ADJUST_STATUS_VALIDATION);
			paymentRecordDTO
					.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
			paymentRecordDTO
					.setAdjustmentFlag(CommonConstDefinition.YESNOFLAG_YES);
			paymentRecordDTO
					.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_M);

			PaymentRecordHome paymentRecordHome = HomeLocater
					.getPaymentRecordHome();
			PaymentRecord paymentRecord = paymentRecordHome
					.create(paymentRecordDTO);
			paymentRecordDTO.setSeqNo(paymentRecord.getSeqNo().intValue());

			LogUtility.log(clazz, LogLevel.DEBUG, "accountAdjustmentDTO:"
					+ accountAdjustmentDTO);

			accountAdjustmentDTO.setCreateTime(curDate);
			accountAdjustmentDTO.setCreateOpID(getOperatorID());
			accountAdjustmentDTO.setCreateOrgID(orgID);

			accountAdjustmentDTO
					.setAdjustmentType(CommonConstDefinition.ADJUST_TYPE_C);


			accountAdjustmentDTO
					.setStatus(CommonConstDefinition.ADJUST_STATUS_VALIDATION);

			AccountAdjustmentHome acctAdjustmentHome = HomeLocater
					.getAccountAdjustmentHome();
			AccountAdjustment accountAdjustment = acctAdjustmentHome
					.create(accountAdjustmentDTO);
			accountAdjustmentDTO.setSeqNo(accountAdjustment.getSeqNo()
					.intValue());
			accountAdjustment.setReferRecordID(paymentRecord.getSeqNo()
					.intValue());
			paymentRecord.setAdjustmentNo(accountAdjustment.getSeqNo()
					.intValue());

			//����
			int problemID=paymentRecordDTO.getReferID();
			Collection feeList=BusinessUtility.getFeeListByTypeAndID(problemID, CommonConstDefinition.FTREFERTYPE_P, false);
			Collection paymentList=BusinessUtility.getPaymentListByTypeAndID(problemID, CommonConstDefinition.FTREFERTYPE_P, false);

			ImmediatePayFeeService feeService = new ImmediatePayFeeService(
					feeList, paymentList, null, null);

			feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_M);
			feeService.setId(problemID);
			feeService.payFeeDB(feeService.AdjustSetOff());
			
			LogUtility.log(clazz, LogLevel.DEBUG, "paymentRecord:"
					+ paymentRecordDTO);
			return accountAdjustment;
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��λ����");
			throw new ServiceException("��λ����");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��������");
			throw new ServiceException("��������");
		}
	}

	/**
	 * �����ʵ�֧��/Ԥ��,��װ֧����¼,���ʼ�¼��Ԥ��ֿۼ�¼������,�����г־û�����,���������ʽӿ�.
	 * 
	 * @param paymentRecordDTO
	 * @param accountAdjustmentDTO
	 * @return
	 * @throws ServiceException
	 */
	public AccountAdjustment adjustBillPayment(
			PaymentRecordDTO paymentRecordDTO,
			AccountAdjustmentDTO accountAdjustmentDTO) throws ServiceException {
		try {
			int orgID = BusinessUtility.FindOrgIDByOperatorID(getOperatorID());
			Timestamp curDate = TimestampUtility.getCurrentTimestamp();

			paymentRecordDTO
					.setSourceType(CommonConstDefinition.PAYMENTRECORDSOURCETYPE_ADJUST);
			paymentRecordDTO.setOrgID(BusinessUtility
					.FindOrgIDByOperatorID(paymentRecordDTO.getOpID()));// �շ�����֯ID
			paymentRecordDTO.setCreateTime(curDate);
			paymentRecordDTO
					.setStatus(CommonConstDefinition.ADJUST_STATUS_VALIDATION);
			paymentRecordDTO
					.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_YES);
			paymentRecordDTO
					.setAdjustmentFlag(CommonConstDefinition.YESNOFLAG_YES);
			paymentRecordDTO
					.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_M);

			PaymentRecordHome paymentRecordHome = HomeLocater
					.getPaymentRecordHome();
			PaymentRecord paymentRecord = paymentRecordHome
					.create(paymentRecordDTO);
			paymentRecordDTO.setSeqNo(paymentRecord.getSeqNo().intValue());

			LogUtility.log(clazz, LogLevel.DEBUG, "accountAdjustmentDTO:"
					+ accountAdjustmentDTO);

			accountAdjustmentDTO.setCreateTime(curDate);
			accountAdjustmentDTO.setCreateOpID(getOperatorID());
			accountAdjustmentDTO.setCreateOrgID(orgID);


			accountAdjustmentDTO
					.setAdjustmentType(CommonConstDefinition.ADJUST_TYPE_I);

			accountAdjustmentDTO
					.setStatus(CommonConstDefinition.ADJUST_STATUS_VALIDATION);

			AccountAdjustmentHome acctAdjustmentHome = HomeLocater
					.getAccountAdjustmentHome();
			AccountAdjustment accountAdjustment = acctAdjustmentHome
					.create(accountAdjustmentDTO);
			accountAdjustmentDTO.setSeqNo(accountAdjustment.getSeqNo()
					.intValue());
			accountAdjustment.setReferRecordID(paymentRecord.getSeqNo()
					.intValue());
			paymentRecord.setSourceRecordId(accountAdjustment.getSeqNo()
					.intValue());
			paymentRecord.setAdjustmentNo(accountAdjustment.getSeqNo()
					.intValue());

			// �޸��ʵ�����.
			int invoiceNo = paymentRecord.getInvoiceNo();
			InvoiceHome invoiceHome = HomeLocater.getInvoiceHome();
			Invoice invoice = invoiceHome.findByPrimaryKey(new Integer(
					invoiceNo));
			invoice.setAmount(invoice.getAmount() - paymentRecord.getAmount());
			invoice.setPaymentTotal(invoice.getPaymentTotal()
					+ paymentRecord.getAmount());
			invoice.setDtLastmod(curDate);
			// ����ʵ��֧��ʱ,���۽���������.
			if (CommonConstDefinition.ADJUST_REFERCODETYPE_C
					.equals(accountAdjustment.getReferRecordType())) {
				Collection paymentList = new ArrayList();
				paymentList.add(paymentRecordDTO);
				Collection feeList = BusinessUtility
						.getFeeListByInvoice(invoiceNo);
				cancelInvoice(invoice, feeList, paymentList, null);
				// ����ʵ��Ԥ��ʱ,���������������
			} else if (CommonConstDefinition.ADJUST_REFERCODETYPE_P
					.equals(accountAdjustment.getReferRecordType())) {
				// Collection paymentList = new ArrayList();
				// paymentList.add(paymentRecordDTO);
				Collection feeList = BusinessUtility
						.getFeeListByInvoice(invoiceNo);
				// ��֧����¼��װһ��Ԥ��ֿۼ�¼.
				PrepaymentDeductionRecordDTO prePaymentDTO = new PrepaymentDeductionRecordDTO();
				prePaymentDTO.setAcctId(paymentRecordDTO.getAcctID());
				prePaymentDTO.setCustId(paymentRecordDTO.getCustID());
				prePaymentDTO.setAmount(paymentRecordDTO.getAmount());
				prePaymentDTO.setCreateTime(curDate);
				prePaymentDTO
						.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				prePaymentDTO
						.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_YES);
				prePaymentDTO.setInvoiceNo(invoiceNo);
				prePaymentDTO
						.setReferRecordType(CommonConstDefinition.F_PDR_REFERRECORDTYPE_I);
				prePaymentDTO.setReferRecordId(invoiceNo);
				prePaymentDTO
						.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_T);
				prePaymentDTO.setPrepaymentType(paymentRecordDTO
						.getPrepaymentType());
				prePaymentDTO.setOpId(getOperatorID());
				prePaymentDTO.setOrgId(orgID);
				// �־û�Ԥ��ֿۼ�¼
				PrepaymentDeductionRecordHome preHome = HomeLocater
						.getPrepaymentDeductionRecordHome();
				PrepaymentDeductionRecord prePayment = preHome
						.create(prePaymentDTO);
				prePaymentDTO.setSeqNo(prePayment.getSeqNo().intValue());
				//�����ʵ�����Ԥ���ܶ�ͱ���Ԥ��ֿ��ܶ�.
				invoice.setPrepaymentTotal(invoice.getPrepaymentTotal()
						+ paymentRecord.getAmount());
				invoice.setPrepaymentDeductionTotal(invoice
						.getPrepaymentDeductionTotal()
						+ paymentRecord.getAmount());

				Collection prePaymentList = new ArrayList();
				prePaymentList.add(prePaymentDTO);
				cancelInvoice(invoice, feeList, new ArrayList(), prePaymentList);
			}

			LogUtility.log(clazz, LogLevel.DEBUG, "paymentRecord:"
					+ paymentRecordDTO);
			return accountAdjustment;
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��λ����");
			throw new ServiceException("��λ����");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��������");
			throw new ServiceException("��������");
		} catch (FinderException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "���ҳ���");
			throw new ServiceException("���ҳ���");
		}
	}

	/**
	 * �����ʵ�����,��װ���ü�¼,���ʼ�¼������,�����г־û�����,���������ʽӿ�.
	 * 
	 * @param accountItemDTO
	 * @param accountAdjustmentDTO
	 * @return
	 * @throws ServiceException
	 */
	public AccountAdjustment adjustBillFee(AccountItemDTO accountItemDTO,
			AccountAdjustmentDTO accountAdjustmentDTO) throws ServiceException {
		try {
			int orgID = BusinessUtility.FindOrgIDByOperatorID(getOperatorID());
			Timestamp curDate = TimestampUtility.getCurrentTimestamp();

			// ��ֵ

			accountItemDTO.setCreateTime(curDate);
			accountItemDTO.setOperatorID(getOperatorID());
			accountItemDTO.setOrgID(orgID);
						
			accountItemDTO
					.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_NO);
			if("P".equals(accountItemDTO.getFeeType())){
				accountItemDTO
				.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_YES);
			}			
			accountItemDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			accountItemDTO.setInvoiceFlag(CommonConstDefinition.YESNOFLAG_YES);
			accountItemDTO.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_W);

			accountItemDTO
					.setAdjustmentFlag(CommonConstDefinition.YESNOFLAG_YES);
			accountItemDTO
					.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_M);

			AccountItemHome acctItemHome = HomeLocater.getAccountItemHome();
			AccountItem accountItem = acctItemHome.create(accountItemDTO);
			accountItemDTO.setAiNO(accountItem.getAiNO().intValue());

			LogUtility.log(clazz, LogLevel.DEBUG, "accountItemDTO:"
					+ accountItemDTO);
			// ��ֵ
			accountAdjustmentDTO.setCreateTime(curDate);
			accountAdjustmentDTO.setCreateOpID(getOperatorID());
			accountAdjustmentDTO.setCreateOrgID(orgID);

			accountAdjustmentDTO
					.setAdjustmentType(CommonConstDefinition.ADJUST_TYPE_I);
			accountAdjustmentDTO
					.setStatus(CommonConstDefinition.ADJUST_STATUS_VALIDATION);

			AccountAdjustmentHome acctAdjustmentHome = HomeLocater
					.getAccountAdjustmentHome();
			AccountAdjustment accountAdjustment = acctAdjustmentHome
					.create(accountAdjustmentDTO);
			accountAdjustmentDTO.setSeqNo(accountAdjustment.getSeqNo()
					.intValue());
			accountAdjustment
					.setReferRecordID(accountItem.getAiNO().intValue());
			accountItem.setSourceRecordID(accountAdjustment.getSeqNo()
					.intValue());
			accountItem
					.setAdjustmentNo(accountAdjustment.getSeqNo().intValue());

			int invoiceNo = accountItem.getInvoiceNO();
			InvoiceHome invoiceHome = HomeLocater.getInvoiceHome();
			Invoice invoice = invoiceHome.findByPrimaryKey(new Integer(
					invoiceNo));
			invoice.setAmount(invoice.getAmount() + accountItem.getValue());
			invoice.setFeeTotal(invoice.getFeeTotal() + accountItem.getValue());
			invoice.setDtLastmod(curDate);
			// ��������,���۵��ʽ��,��������
			Collection feeList = BusinessUtility.getFeeListByInvoice(invoiceNo);
			feeList.add(accountItemDTO);
			cancelInvoice(invoice, feeList, new ArrayList(), null);

			return accountAdjustment;
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��λ����");
			throw new ServiceException("��λ����");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��������");
			throw new ServiceException("��������");
		} catch (FinderException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "���ҳ���");
			throw new ServiceException("���ҳ���");
		}

	}

	/**
	 * ˽�е����ʷ���,��װ��һЩ����ǰ��Ĺ��ò���,�������ʽӿڵ���/�ʻ�״̬����/�ͻ���Ʒ״̬����.
	 * 
	 * @param invoiceNo
	 * @param feeList
	 * @param paymentList
	 * @param prePaymentList
	 * @throws ServiceException
	 */
	private void cancelInvoice(Invoice invoice, Collection feeList,
			Collection paymentList, Collection prePaymentList)
			throws ServiceException {
		try {
			if (invoice != null) {
				LogUtility.log(clazz, LogLevel.DEBUG, "��������:"
						+ invoice.getInvoiceNo());
			}
			if (feeList != null) {
				LogUtility.log(clazz, LogLevel.DEBUG, "�����б�:" + feeList.size());
			}
			if (paymentList != null) {
				LogUtility.log(clazz, LogLevel.DEBUG, "֧���б�:"
						+ paymentList.size());
			}
			if (prePaymentList != null) {
				LogUtility.log(clazz, LogLevel.DEBUG, "�ֿ��б�:"
						+ prePaymentList.size());
			}

			Timestamp curDate = TimestampUtility.getCurrentTimestamp();

			// �������ʽӿ�

			ImmediatePayFeeService feeService = new ImmediatePayFeeService(
					feeList, paymentList, prePaymentList, null);

			feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_I);
			feeService.setId(invoice.getInvoiceNo().intValue());
			feeService.payFeeDB(feeService.AdjustSetOff());

			// �����ƽ���ʵ�,�����ʵ�״̬Ϊ�Ѹ�.
			//if (invoice.getAmount() == 0) {
			if (Math.abs(invoice.getAmount())<0.0001) {
				int orgID = BusinessUtility.FindOrgIDByOperatorID(getOperatorID());
				invoice.setStatus(CommonConstDefinition.INVOICE_STATUS_PAID);
				invoice.setPayDate(curDate);
				invoice.setPayOpId(getOperatorID());
				invoice.setPayOrgId(orgID);
			}
			// �����ʻ�״̬.
			Integer accountID = new Integer(invoice.getAcctID());

			AccountHome accountHome = HomeLocater.getAccountHome();
			Account account = accountHome.findByPrimaryKey(accountID);
			account.setStatus(CommonConstDefinition.ACCOUNT_STATUS_NORMAL);
			account.setDtLastmod(curDate);
	    	if(!BusinessUtility.existNopaidInvoice(accountID.intValue())){
				    LogUtility.log(FeeService.class,LogLevel.ERROR,"��ʼ���¿ͻ���Ʒ,ҵ���ʻ�״̬,����������¼�.");
				Collection psIDCol = BusinessUtility
						.getPsIDListByCond(
								0,
								accountID.intValue(),
								CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_SYSTEMSUSPEND);
				// ����ҵ���˻��Ͳ�Ʒ��״̬
				CustomerProductService
						.updateCustomerProduct(psIDCol, 0, getOperatorID());
	    	}
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��λ����");
			throw new ServiceException("��λ����");
		} catch (FinderException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "���ҳ���");
			throw new ServiceException("���ҳ���");
		}
	}

	/**
	 * �����ʻ�����,��װ���ü�¼,���ʼ�¼������,�����г־û�����.
	 * 
	 * @param accountItemDTO
	 * @param accountAdjustmentDTO
	 * @return
	 * @throws ServiceException
	 */
	public AccountAdjustment adjustAccountFee(AccountItemDTO accountItemDTO,
			AccountAdjustmentDTO accountAdjustmentDTO) throws ServiceException {
		try {
			int orgID = BusinessUtility.FindOrgIDByOperatorID(getOperatorID());
			Timestamp curDate = TimestampUtility.getCurrentTimestamp();

			// ��ֵ

			accountItemDTO.setCreateTime(curDate);
			accountItemDTO.setOperatorID(getOperatorID());
			accountItemDTO.setOrgID(orgID);
			accountItemDTO
			.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_NO);
			if("P".equals(accountItemDTO.getFeeType())){
				accountItemDTO
				.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_YES);
			}
			accountItemDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			accountItemDTO
					.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_M);
			accountItemDTO.setInvoiceFlag(CommonConstDefinition.YESNOFLAG_NO);
			accountItemDTO.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_W);
			// accountItemDTO
			// .setFeeType(BusinessUtility
			// .getFeeTypeByItemTypeID(accountItemDTO
			// .getAcctItemTypeID()));
			accountItemDTO
					.setAdjustmentFlag(CommonConstDefinition.YESNOFLAG_YES);
			accountItemDTO
					.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_M);

			AccountItemHome acctItemHome = HomeLocater.getAccountItemHome();
			AccountItem accountItem = acctItemHome.create(accountItemDTO);

			// ��ֵ
			accountAdjustmentDTO.setCreateTime(curDate);
			accountAdjustmentDTO.setCreateOpID(getOperatorID());
			accountAdjustmentDTO.setCreateOrgID(orgID);

			accountAdjustmentDTO
					.setAdjustmentType(CommonConstDefinition.ADJUST_TYPE_M);
			accountAdjustmentDTO
					.setStatus(CommonConstDefinition.ADJUST_STATUS_VALIDATION);

			AccountAdjustmentHome acctAdjustmentHome = HomeLocater
					.getAccountAdjustmentHome();
			AccountAdjustment accountAdjustment = acctAdjustmentHome
					.create(accountAdjustmentDTO);
			accountAdjustment
					.setReferRecordID(accountItem.getAiNO().intValue());
			accountItem.setSourceRecordID(accountAdjustment.getSeqNo()
					.intValue());
			accountItem
					.setAdjustmentNo(accountAdjustment.getSeqNo().intValue());

			return accountAdjustment;
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��λ����");
			throw new ServiceException("��λ����");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��������");
			throw new ServiceException("��������");
		}

	}

	/**
	 * �����ʻ�֧��/Ԥ��,��װ֧����¼,���ʼ�¼������,�����г־û�����.����Ԥ���ʱ���ʻ���Ԥ������޸�.
	 * 
	 * @param adjustType
	 * @param paymentRecordDTO
	 * @param accountAdjustmentDTO
	 * @return
	 * @throws ServiceException
	 */
	public AccountAdjustment adjustAccountPayment(int adjustType,
			PaymentRecordDTO paymentRecordDTO,
			AccountAdjustmentDTO accountAdjustmentDTO) throws ServiceException {
		try {
			int orgID = BusinessUtility.FindOrgIDByOperatorID(getOperatorID());
			Timestamp curDate = TimestampUtility.getCurrentTimestamp();

			paymentRecordDTO
					.setSourceType(CommonConstDefinition.PAYMENTRECORDSOURCETYPE_ADJUST);
			paymentRecordDTO
					.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
			paymentRecordDTO.setOrgID(BusinessUtility
					.FindOrgIDByOperatorID(paymentRecordDTO.getOpID()));// �շ�����֯ID
			paymentRecordDTO.setCreateTime(curDate);
			paymentRecordDTO
					.setStatus(CommonConstDefinition.ADJUST_STATUS_VALIDATION);
			paymentRecordDTO
					.setAdjustmentFlag(CommonConstDefinition.YESNOFLAG_YES);
			paymentRecordDTO
					.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_M);

			PaymentRecordHome paymentRecordHome = HomeLocater
					.getPaymentRecordHome();
			PaymentRecord paymentRecord = paymentRecordHome
					.create(paymentRecordDTO);

			accountAdjustmentDTO.setCreateTime(curDate);
			accountAdjustmentDTO.setCreateOpID(getOperatorID());
			accountAdjustmentDTO.setCreateOrgID(orgID);


			accountAdjustmentDTO
					.setAdjustmentType(CommonConstDefinition.ADJUST_TYPE_M);

			accountAdjustmentDTO
					.setStatus(CommonConstDefinition.ADJUST_STATUS_VALIDATION);

			AccountAdjustmentHome acctAdjustmentHome = HomeLocater
					.getAccountAdjustmentHome();
			AccountAdjustment accountAdjustment = acctAdjustmentHome
					.create(accountAdjustmentDTO);
			accountAdjustment.setReferRecordID(paymentRecord.getSeqNo()
					.intValue());
			paymentRecord.setSourceRecordId(accountAdjustment.getSeqNo()
					.intValue());
			paymentRecord.setAdjustmentNo(accountAdjustment.getSeqNo()
					.intValue());

			// �����������ǵ���Ԥ���ʱ��,��Ҫ�����ʻ��ϵ�Ԥ����.
			if (AccountAdjustEJBEvent.ACCOUNT_ADJUST_PREPAYMENT == adjustType) {
				int accountID = accountAdjustmentDTO.getAcctID();
				AccountHome ahome = HomeLocater.getAccountHome();
				Account account = ahome
						.findByPrimaryKey(new Integer(accountID));
				String prePaymentType = paymentRecord.getPrepaymentType();
				double adjustAmount = paymentRecord.getAmount();
				if (CommonConstDefinition.PREPAYMENTTYPE_CASH
						.equals(prePaymentType)) {
					double curCashDeposit = account.getCashDeposit();
					account.setCashDeposit(curCashDeposit + adjustAmount);
				} else if (CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY
						.equals(prePaymentType)) {
					double curTokenDeposit = account.getTokenDeposit();
					account.setTokenDeposit(curTokenDeposit + adjustAmount);
				} else {
					throw new ServiceException("��Ч��֧����ʽ");
				}
			}

			LogUtility.log(clazz, LogLevel.DEBUG, "accountAdjustment:"
					+ accountAdjustmentDTO);
			LogUtility.log(clazz, LogLevel.DEBUG, "paymentRecord:"
					+ paymentRecordDTO);
			return accountAdjustment;
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��λ����");
			throw new ServiceException("��λ����");
		} catch (FinderException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "���ҳ���");
			throw new ServiceException("���ҳ���");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��������");
			throw new ServiceException("��������");
		}

	}

	/**
	 * ûʵ��,����,
	 * 
	 * @param paymentRecordDTO
	 * @param accountAdjustmentDTO
	 * @return
	 * @throws ServiceException
	 */
	public AccountAdjustment adjustAccountPrePaymentDeduction(
			PrepaymentDeductionRecordDTO prePaymentDeductionRecordDTO,
			AccountAdjustmentDTO accountAdjustmentDTO) throws ServiceException {
		try {
			PrepaymentDeductionRecordHome prePaymentDeductionRecordHome = HomeLocater
					.getPrepaymentDeductionRecordHome();
			PrepaymentDeductionRecord prePaymentDeductionRecord = prePaymentDeductionRecordHome
					.create(prePaymentDeductionRecordDTO);
			AccountAdjustmentHome acctAdjustmentHome = HomeLocater
					.getAccountAdjustmentHome();
			AccountAdjustment accountAdjustment = acctAdjustmentHome
					.create(accountAdjustmentDTO);
			accountAdjustment.setReferRecordID(prePaymentDeductionRecord
					.getSeqNo().intValue());
			prePaymentDeductionRecord.setReferRecordId(accountAdjustment
					.getSeqNo().intValue());
			return accountAdjustment;
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��λ����");
			throw new ServiceException("��λ����");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "��������");
			throw new ServiceException("��������");
		}

	}

}
