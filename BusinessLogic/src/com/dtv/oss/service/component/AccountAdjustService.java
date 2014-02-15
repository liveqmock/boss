/*
 * 侯瑞军2007-1-26
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
 * 处理与帐户相关的业务增加
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

			// 赋值

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
			// 赋值
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

			//销帐
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
			LogUtility.log(clazz, LogLevel.WARN, "定位出错");
			throw new ServiceException("定位出错");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "创建出错");
			throw new ServiceException("创建出错");
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
					.FindOrgIDByOperatorID(paymentRecordDTO.getOpID()));// 收费人组织ID
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

			//销帐
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
			LogUtility.log(clazz, LogLevel.WARN, "定位出错");
			throw new ServiceException("定位出错");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "创建出错");
			throw new ServiceException("创建出错");
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

			// 赋值

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
			// 赋值
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

			//销帐
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
			LogUtility.log(clazz, LogLevel.WARN, "定位出错");
			throw new ServiceException("定位出错");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "创建出错");
			throw new ServiceException("创建出错");
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
					.FindOrgIDByOperatorID(paymentRecordDTO.getOpID()));// 收费人组织ID
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

			//销帐
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
			LogUtility.log(clazz, LogLevel.WARN, "定位出错");
			throw new ServiceException("定位出错");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "创建出错");
			throw new ServiceException("创建出错");
		}
	}

	/**
	 * 调整帐单支付/预存,封装支付记录,调帐记录及预存抵扣记录的数据,并进行持久化操作,最后调用销帐接口.
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
					.FindOrgIDByOperatorID(paymentRecordDTO.getOpID()));// 收费人组织ID
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

			// 修改帐单数据.
			int invoiceNo = paymentRecord.getInvoiceNo();
			InvoiceHome invoiceHome = HomeLocater.getInvoiceHome();
			Invoice invoice = invoiceHome.findByPrimaryKey(new Integer(
					invoiceNo));
			invoice.setAmount(invoice.getAmount() - paymentRecord.getAmount());
			invoice.setPaymentTotal(invoice.getPaymentTotal()
					+ paymentRecord.getAmount());
			invoice.setDtLastmod(curDate);
			// 调整实收支付时,不论金额都调用销帐.
			if (CommonConstDefinition.ADJUST_REFERCODETYPE_C
					.equals(accountAdjustment.getReferRecordType())) {
				Collection paymentList = new ArrayList();
				paymentList.add(paymentRecordDTO);
				Collection feeList = BusinessUtility
						.getFeeListByInvoice(invoiceNo);
				cancelInvoice(invoice, feeList, paymentList, null);
				// 调整实收预存时,金额大于零调用销帐
			} else if (CommonConstDefinition.ADJUST_REFERCODETYPE_P
					.equals(accountAdjustment.getReferRecordType())) {
				// Collection paymentList = new ArrayList();
				// paymentList.add(paymentRecordDTO);
				Collection feeList = BusinessUtility
						.getFeeListByInvoice(invoiceNo);
				// 用支付记录封装一条预存抵扣记录.
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
				// 持久化预存抵扣记录
				PrepaymentDeductionRecordHome preHome = HomeLocater
						.getPrepaymentDeductionRecordHome();
				PrepaymentDeductionRecord prePayment = preHome
						.create(prePaymentDTO);
				prePaymentDTO.setSeqNo(prePayment.getSeqNo().intValue());
				//更新帐单本期预存总额和本期预存抵扣总额.
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
			LogUtility.log(clazz, LogLevel.WARN, "定位出错");
			throw new ServiceException("定位出错");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "创建出错");
			throw new ServiceException("创建出错");
		} catch (FinderException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "查找出错");
			throw new ServiceException("查找出错");
		}
	}

	/**
	 * 调整帐单费用,封装费用记录,调帐记录的数据,并进行持久化操作,最后调用销帐接口.
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

			// 赋值

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
			// 赋值
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
			// 调用销帐,不论调帐金额,进行销帐
			Collection feeList = BusinessUtility.getFeeListByInvoice(invoiceNo);
			feeList.add(accountItemDTO);
			cancelInvoice(invoice, feeList, new ArrayList(), null);

			return accountAdjustment;
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "定位出错");
			throw new ServiceException("定位出错");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "创建出错");
			throw new ServiceException("创建出错");
		} catch (FinderException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "查找出错");
			throw new ServiceException("查找出错");
		}

	}

	/**
	 * 私有的销帐方法,封装了一些销帐前后的共用操作,包括销帐接口调用/帐户状态更改/客户产品状态更改.
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
				LogUtility.log(clazz, LogLevel.DEBUG, "进入销帐:"
						+ invoice.getInvoiceNo());
			}
			if (feeList != null) {
				LogUtility.log(clazz, LogLevel.DEBUG, "费用列表:" + feeList.size());
			}
			if (paymentList != null) {
				LogUtility.log(clazz, LogLevel.DEBUG, "支付列表:"
						+ paymentList.size());
			}
			if (prePaymentList != null) {
				LogUtility.log(clazz, LogLevel.DEBUG, "抵扣列表:"
						+ prePaymentList.size());
			}

			Timestamp curDate = TimestampUtility.getCurrentTimestamp();

			// 调用销帐接口

			ImmediatePayFeeService feeService = new ImmediatePayFeeService(
					feeList, paymentList, prePaymentList, null);

			feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_I);
			feeService.setId(invoice.getInvoiceNo().intValue());
			feeService.payFeeDB(feeService.AdjustSetOff());

			// 如果调平了帐单,设置帐单状态为已付.
			//if (invoice.getAmount() == 0) {
			if (Math.abs(invoice.getAmount())<0.0001) {
				int orgID = BusinessUtility.FindOrgIDByOperatorID(getOperatorID());
				invoice.setStatus(CommonConstDefinition.INVOICE_STATUS_PAID);
				invoice.setPayDate(curDate);
				invoice.setPayOpId(getOperatorID());
				invoice.setPayOrgId(orgID);
			}
			// 更新帐户状态.
			Integer accountID = new Integer(invoice.getAcctID());

			AccountHome accountHome = HomeLocater.getAccountHome();
			Account account = accountHome.findByPrimaryKey(accountID);
			account.setStatus(CommonConstDefinition.ACCOUNT_STATUS_NORMAL);
			account.setDtLastmod(curDate);
	    	if(!BusinessUtility.existNopaidInvoice(accountID.intValue())){
				    LogUtility.log(FeeService.class,LogLevel.ERROR,"开始更新客户产品,业务帐户状态,并生成相关事件.");
				Collection psIDCol = BusinessUtility
						.getPsIDListByCond(
								0,
								accountID.intValue(),
								CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_SYSTEMSUSPEND);
				// 更新业务账户和产品的状态
				CustomerProductService
						.updateCustomerProduct(psIDCol, 0, getOperatorID());
	    	}
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "定位出错");
			throw new ServiceException("定位出错");
		} catch (FinderException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "查找出错");
			throw new ServiceException("查找出错");
		}
	}

	/**
	 * 调整帐户费用,封装费用记录,调帐记录的数据,并进行持久化操作.
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

			// 赋值

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

			// 赋值
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
			LogUtility.log(clazz, LogLevel.WARN, "定位出错");
			throw new ServiceException("定位出错");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "创建出错");
			throw new ServiceException("创建出错");
		}

	}

	/**
	 * 调整帐户支付/预存,封装支付记录,调帐记录的数据,并进行持久化操作.调整预存的时候帐户上预存金额的修改.
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
					.FindOrgIDByOperatorID(paymentRecordDTO.getOpID()));// 收费人组织ID
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

			// 当调帐类型是调整预存的时候,需要更新帐户上的预存金额.
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
					throw new ServiceException("无效的支付方式");
				}
			}

			LogUtility.log(clazz, LogLevel.DEBUG, "accountAdjustment:"
					+ accountAdjustmentDTO);
			LogUtility.log(clazz, LogLevel.DEBUG, "paymentRecord:"
					+ paymentRecordDTO);
			return accountAdjustment;
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "定位出错");
			throw new ServiceException("定位出错");
		} catch (FinderException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "查找出错");
			throw new ServiceException("查找出错");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "创建出错");
			throw new ServiceException("创建出错");
		}

	}

	/**
	 * 没实现,空着,
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
			LogUtility.log(clazz, LogLevel.WARN, "定位出错");
			throw new ServiceException("定位出错");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.WARN, "创建出错");
			throw new ServiceException("创建出错");
		}

	}

}
