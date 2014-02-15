/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.component;

import java.util.*;
 
import java.sql.*;

import javax.ejb.FinderException;
import com.dtv.oss.log.*;
import com.dtv.oss.dto.*;
import com.dtv.oss.domain.*;
import  com.dtv.oss.util.*;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.*;
import com.dtv.oss.service.ejbevent.groupcustomer.GroupCustomerEJBEvent;
import com.dtv.oss.service.ejbevent.voice.VoiceEJBEvent;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CsrBusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.dto.custom.*;
import com.dtv.oss.service.util.HelperCommonUtil;
/**
 * @author Leon Liu0
 *
 *  ҵ���߼����������
 */
public class BusinessRuleService {
	private static final Class clazz = BusinessRuleService.class; 
	private ServiceContext serviceContext=null;

	public BusinessRuleService(ServiceContext serviceContext){
		this.serviceContext=serviceContext;
	}
	/**
	 * ����ʻ����ʵ���Ч��,
	 * @param accountAdjustmentDTO ���ʼ�¼,�����װ��Ч��ReferRecordType��Ϣ,����������������������ֵ�������.
	 * @param accountItemDTO ���ü�¼,
	 * @param paymentRecordDTO
	 * @param prepaymentDeductionRecordDTO
	 * @throws ServiceException
	 */
	public void checkAccountAdjustment(
			AccountAdjustmentDTO accountAdjustmentDTO,
			AccountItemDTO accountItemDTO, PaymentRecordDTO paymentRecordDTO,
			PrepaymentDeductionRecordDTO prepaymentDeductionRecordDTO) throws ServiceException {
		String type =accountAdjustmentDTO.getReferRecordType();
		LogUtility.log(clazz,LogLevel.DEBUG,"��������:"+type);

		int accountID=accountAdjustmentDTO.getAcctID();
		AccountDTO account=BusinessUtility.getAcctDTOByAcctID(accountID);
		if(CommonConstDefinition.ACCOUNT_STATUS_CLOSE.equals(account.getStatus())){
			throw new ServiceException("�رյ��ʻ����ܽ��е���!");
		}
		if(CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_F.equals(type)){
			double sumFee=BusinessUtility.getNoInvoicedAccountItemByAccountID(accountID);
			LogUtility.log(clazz,LogLevel.DEBUG,"�ʻ�:"+accountID+",δ���ʵ��ܵķ���:"+sumFee);
			double sumPayment=BusinessUtility.getNoInvoicedPaymentRecordByAccountID(accountID);
			LogUtility.log(clazz,LogLevel.DEBUG,"�ʻ�:"+accountID+",δ���ʵ��ܵ�֧��:"+sumPayment);
			double sumPrePaymentDeduction=BusinessUtility.getNoInvoicedPrePaymentDeductionRecordByAccountID(accountID);
			LogUtility.log(clazz,LogLevel.DEBUG,"�ʻ�:"+accountID+",δ���ʵ��ܵĵֿ�:"+sumPrePaymentDeduction);
			if(accountItemDTO.getValue()+sumFee-sumPayment-sumPrePaymentDeduction<0){
				LogUtility.log(clazz,LogLevel.DEBUG,"�ʻ�:"+accountID+",����ֵ:"+(accountItemDTO.getValue()+sumFee-sumPayment-sumPrePaymentDeduction));
				throw new ServiceException("���ʽ�� + δ���˵ķ��� - δ���˵�֧������Ԥ�棩- δ���˵�Ԥ��ֿۼ�¼������ڵ�����!");
			}
		}else if (CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_P.equals(type)){
			double adjustAmount=paymentRecordDTO.getAmount();
			double cashDeposit=account.getCashDeposit();
			double tokenDeposit=account.getTokenDeposit();
			String prePaymentType=paymentRecordDTO.getPrepaymentType();
			if(CommonConstDefinition.PREPAYMENTTYPE_CASH.equals(prePaymentType)){
				if(cashDeposit+adjustAmount<0)throw new ServiceException("���ʺ��ʻ����ֽ�Ԥ�治��С����!");
			}else if(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY.equals(prePaymentType)){
				if(tokenDeposit+adjustAmount<0)throw new ServiceException("���ʺ��ʻ����������Ԥ�治��С����!");
			}else{
				throw new ServiceException("��Ч��֧����ʽ");
			}

		}else if (CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_C.equals(type)){
			double sumFee=BusinessUtility.getNoInvoicedAccountItemByAccountID(accountID);
			double sumPayment=BusinessUtility.getNoInvoicedPaymentRecordByAccountID(accountID);
			double sumPrePaymentDeduction=BusinessUtility.getNoInvoicedPrePaymentDeductionRecordByAccountID(accountID);
			if(sumFee-sumPayment-sumPrePaymentDeduction-paymentRecordDTO.getAmount()<0){
				throw new ServiceException("δ���˵ķ���-δ���˵�֧������Ԥ�棩- δ���˵�Ԥ��ֿۼ�¼ �C ���ʽ�������ڵ�����!");
			}
		}else if (CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_D.equals(type)){
			
		}else{
			throw new ServiceException("δ֪�ĵ�������!!");
		}
	}
/**
 * ����ʵ����ʵ���Ч��,
 * @param invoiceNo �ʵ���,
 * @param amount ���ʽ��
 * @param accountAdjustmentDTO ���ʼ�¼,�����װ��Ч��ReferRecordType��Ϣ,����������������������ֵ�������.
 * @throws ServiceException
 */
	public void checkBillAdjustment(int invoiceNo,
			double amount,
			AccountAdjustmentDTO accountAdjustmentDTO
			) throws ServiceException {
		String type =accountAdjustmentDTO.getReferRecordType();
		LogUtility.log(clazz,LogLevel.DEBUG,"��������:"+type);

		int accountID=accountAdjustmentDTO.getAcctID();
		AccountDTO account=BusinessUtility.getAcctDTOByAcctID(accountID);
		if(CommonConstDefinition.ACCOUNT_STATUS_CLOSE.equals(account.getStatus())){
			throw new ServiceException("�رյ��ʻ����ܽ��е���!");
		}
		InvoiceDTO invoiceDto=BusinessUtility.getInvoiceDTOByInvoiceNO(invoiceNo);
		if(CommonConstDefinition.INVOICE_STATUS_PAID.equals(invoiceDto.getStatus())){
			throw new ServiceException("�Ѿ�֧�����ʵ����ܵ���!");
		}
		if(CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_F.equals(type)){
			if(invoiceDto.getAmount()+amount<0){
				throw new ServiceException("�˵��������ӵ��ʽ�������ڵ�����!");
			}
		}else if (CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_P.equals(type)||
				CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_C.equals(type)){
			if(invoiceDto.getAmount()-amount<0){
				throw new ServiceException("�˵������������ʽ�������ڵ�����!");
			}
		}else if (CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_D.equals(type)){
			
		}else{
			throw new ServiceException("δ֪�ĵ�������!!");
		}
	}
	/**
	 * Լ��: ���ʽ��(����) + ���� �C ֧�� �C ���ʽ��(֧��) >= 0. ���ʷ���ֻ����Ӧ�շ��á�ʵ��֧����
	 * @param amount
	 * @param accountAdjustmentDTO
	 * @throws ServiceException
	 */
	public void checkProblemAdjustment(int problemID,double amount,
			AccountAdjustmentDTO accountAdjustmentDTO
			) throws ServiceException {
		Collection feeList=BusinessUtility.getFeeListByTypeAndID(problemID, CommonConstDefinition.FTREFERTYPE_P, false);
		double feeSum=0;
		Iterator it=feeList.iterator();
		while(it.hasNext()){
			AccountItemDTO accountItem=(AccountItemDTO) it.next();
			feeSum+=accountItem.getValue();
		}
		Collection paymentList=BusinessUtility.getPaymentListByTypeAndID(problemID, CommonConstDefinition.FTREFERTYPE_P, false);
		double paymentSum=0;
		it=paymentList.iterator();
		while(it.hasNext()){
			PaymentRecordDTO paymentRecord=(PaymentRecordDTO) it.next();
			paymentSum+=paymentRecord.getAmount();
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "feeSum:"+ feeSum);
		LogUtility.log(clazz, LogLevel.DEBUG, "paymentSum:"+ paymentSum);
		//�����Ӧ��
		if(CommonConstDefinition.ADJUST_REFERCODETYPE_F.equals(accountAdjustmentDTO.getReferRecordType())){
			if(amount+feeSum-paymentSum<0){
				throw new ServiceException("���ʽ��(����) + ���� �C ֧�� ������ڵ�����!");
			}
		}else if(CommonConstDefinition.ADJUST_REFERCODETYPE_C.equals(accountAdjustmentDTO.getReferRecordType())){
			if(feeSum-paymentSum-amount<0){
				throw new ServiceException("���� �C ֧�� - ���ʽ��(����) ������ڵ�����!");
			}
		}else{
			throw new ServiceException("δ֪�Ĳ���!");
		}
	}
	public void checkCSIAdjustment(int csiID,int accountID,double amount,
			AccountAdjustmentDTO accountAdjustmentDTO
			) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "checkCSIAdjustment>>>>>>>>>>>");
		
		CustServiceInteractionDTO csiDto=BusinessUtility.getCSIDTOByID(csiID);
//		if(!CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT.equals(csiDto.getStatus())){
//			throw new ServiceException("ֻ�д�����������ſ��Ե���!");
//		}
		if(!CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY.equals(csiDto.getPaymentStatus())){
			throw new ServiceException("ֻ��δ���ѵ������ſ��Ե���!");
		}
		//���ú�֧��ֻ���鵱ǰѡ���ʻ���
		Collection feeList=BusinessUtility.getFeeListByTypeAndID(csiID, CommonConstDefinition.FTREFERTYPE_M, false);
		double feeSum=0;
		Iterator it=feeList.iterator();
		while(it.hasNext()){
			AccountItemDTO accountItem=(AccountItemDTO) it.next();
			if(accountItem.getAcctID()==accountID){
				feeSum+=accountItem.getValue();
			}
		}
		Collection paymentList=BusinessUtility.getPaymentListByTypeAndID(csiID, CommonConstDefinition.FTREFERTYPE_M, false);
		double paymentSum=0;
		it=paymentList.iterator();
		while(it.hasNext()){
			PaymentRecordDTO paymentRecord=(PaymentRecordDTO) it.next();
			if(paymentRecord.getAcctID()==accountID){
				paymentSum+=paymentRecord.getAmount();
			}
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "feeSum:"+ feeSum);
		serviceContext.put(Service.FEELIST,feeList);
		serviceContext.put(Service.TATOLFEE,new Double(feeSum));
		
		LogUtility.log(clazz, LogLevel.DEBUG, "paymentSum:"+ paymentSum);
		serviceContext.put(Service.PAYMENTLIST,paymentList);
		serviceContext.put(Service.TATOLPAYMENT,new Double(paymentSum));
		
		//�����Ӧ��
		if(CommonConstDefinition.ADJUST_REFERCODETYPE_F.equals(accountAdjustmentDTO.getReferRecordType())){
			if(amount+feeSum-paymentSum<0){
				throw new ServiceException("���ʽ��(����) + ���� �C ֧�� ������ڵ�����!");
			}
		}else if(CommonConstDefinition.ADJUST_REFERCODETYPE_C.equals(accountAdjustmentDTO.getReferRecordType())){
			if(feeSum-paymentSum-amount<0){
				throw new ServiceException("���� �C ֧�� - ���ʽ��(����) ������ڵ�����!");
			}
		}else{
			throw new ServiceException("δ֪�Ĳ���!");
		}
	}	
	/**
	 * �����������
	 * 
	 * �������׼��ɾ����,û����,
	 * @param id
	 * @param accountID
	 * @param type
	 * @param feeAdjustlist
	 * @param paymentAdjustList
	 * @param preDeductionAdjustList
	 * @throws ServiceException
	 */
	public void checkCPAjustment(int id,int accountID,String type,Collection feeAdjustlist,Collection paymentAdjustList,Collection preDeductionAdjustList) throws ServiceException{
		CustomerProblemDTO cpDTO=BusinessUtility.getCustomerProblemDTOByID(id);
		if(!CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT.equals(cpDTO.getStatus())&&
			!CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS.equals(cpDTO.getStatus())){
			throw new ServiceException("��״̬�µı��޵����ܽ��е��ʲ���");
		}
		String unCondition=" SETOFFFLAG ='"+CommonConstDefinition.SETOFFFLAG_W+"' AND ACCTID="+accountID;
		String haveCondition=" SETOFFFLAG ='"+CommonConstDefinition.SETOFFFLAG_D+"' AND ACCTID="+accountID;
		String cpCond= "  AND  REFERTYPE='"+CommonConstDefinition.FTREFERTYPE_P+"' AND REFERID="+id;
		//δ���˵ķ����ܶ�
		double unSetFeeAmont=BusinessUtility.getSumFeeValueByCond(unCondition+cpCond,null);
		//�����˷����ܶ�
		double haveSetFeeAmont=BusinessUtility.getSumFeeValueByCond(haveCondition+cpCond,null);
		
		if(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT.equals(cpDTO.getStatus())){
			if(unSetFeeAmont<0){
				throw new ServiceException("δ���˵ķ����ܶ������ڵ���0");
			}
		}else if(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS.equals(cpDTO.getStatus())){ 
			if(haveSetFeeAmont<0){
				throw new ServiceException("�����˵ķ����ܶ������ڵ���0");
			}
		}
		//�����ܺ�
		double feeTotal=0;
		if(feeAdjustlist!=null && !feeAdjustlist.isEmpty()){
			Iterator feeAdjustIter=feeAdjustlist.iterator();
			while(feeAdjustIter.hasNext()){
				FeeAdjustmentWrapDTO currentFeeWrap=(FeeAdjustmentWrapDTO)feeAdjustIter.next();
				AccountItemDTO accountItemDTO=currentFeeWrap.getAccountItemDTO();
				feeTotal+=accountItemDTO.getValue();
			}
		}
		//�ܽ��
		double totalPay=0;
		if(paymentAdjustList!=null && !paymentAdjustList.isEmpty()){
			Iterator paymentIter=paymentAdjustList.iterator();
			while(paymentIter.hasNext()){
				PaymentAdjustWrapDTO currentpaymentWrap=(PaymentAdjustWrapDTO)paymentIter.next();
				PaymentRecordDTO paymentRecordDTO=currentpaymentWrap.getPaymentRecordDTO();
				totalPay+=paymentRecordDTO.getAmount();
			}
		}
		if(CommonConstDefinition.SETOFFFLAG_W.equals(type)){
			if(feeTotal+unSetFeeAmont<0){
				throw new ServiceException("���˲����ķ��ý��ͱ��޵���δ���˵ķ��õĴ����˽��֮�ͱ�����ڵ���0");
			}
		}else if(CommonConstDefinition.SETOFFFLAG_D.equals(type)){
			if(feeTotal+haveSetFeeAmont<0){
				throw new ServiceException("���˲����ķ��ý��ͱ��޵��������˵ķ��õ������˽��֮�ͱ�����ڵ���0");
			}
			//if(totalPay!=feeTotal){
			if(Math.abs(totalPay - feeTotal)>0.0001){
				throw new ServiceException("���˲����ķ��ý��͵��˲�����֧�����֮�ͱ�����ڵ���0");
			}
		}
	}
	
	
	/**
	 * ����ʵ�����
	 * * �������׼��ɾ����,û����,
	 * @param csiDTO
	 * @param type
	 * @param feeAdjustlist
	 * @param paymentAdjustList
	 * @throws ServiceException
	 */
	public  void checkInvoiceAjustment(int id,int accountID,String type,Collection feeAdjustlist,Collection paymentAdjustList,Collection preDeductionAdjustList) throws ServiceException{
		InvoiceDTO invoiceDTO=BusinessUtility.getInvoiceDTOByInvoiceNO(id);
		if(CommonConstDefinition.SETOFFFLAG_W.equals(type)){
			if(!CommonConstDefinition.INVOICE_STATUS_WAIT.equals(invoiceDTO.getStatus())&&
				!CommonConstDefinition.INVOICE_STATUS_OWE.equals(invoiceDTO.getStatus())&&
				!CommonConstDefinition.INVOICE_STATUS_BADLOCK.equals(invoiceDTO.getStatus())){
				throw new ServiceException("��״̬�µ��ʵ����ܽ��е��ʲ���");
			}
		}else if(CommonConstDefinition.SETOFFFLAG_D.equals(type)){
			if(!CommonConstDefinition.INVOICE_STATUS_PAID.equals(invoiceDTO.getStatus())&&
					!CommonConstDefinition.INVOICE_STATUS_WAIT.equals(invoiceDTO.getStatus())){
				throw new ServiceException("��״̬�µ��ʵ����ܽ��е��ʲ���");
			}
			if(CommonConstDefinition.INVOICE_STATUS_WAIT.equals(invoiceDTO.getStatus())){
				//�˵���֧��״̬�� �Ѹ� ���� �����������Ѿ�����֧���ķ���
				if(invoiceDTO.getFeeTotal()<=invoiceDTO.getPaymentTotal()){
					throw new ServiceException("���ʵ�û����֧���ķ���");
				}
			}
		}
		String unCondition=" SETOFFFLAG ='"+CommonConstDefinition.SETOFFFLAG_W+"' AND ACCTID="+accountID+ " AND INVOICEDFLAG='"+CommonConstDefinition.YESNOFLAG_YES+"' AND INVOICENO="+id;
		String haveCondition=" SETOFFFLAG ='"+CommonConstDefinition.SETOFFFLAG_D+"' AND ACCTID="+accountID+" AND INVOICEDFLAG='"+CommonConstDefinition.YESNOFLAG_YES+"' AND INVOICENO="+id;
		String invoiceCond= " INVOICEDFLAG='"+CommonConstDefinition.YESNOFLAG_YES+"' AND INVOICENO="+id+" AND ACCTID="+accountID;
		//δ���˵ķ����ܶ�
		double unSetFeeAmont=BusinessUtility.getSumFeeValueByCond(unCondition,null);
		//�����˷����ܶ�
		double haveSetFeeAmont=BusinessUtility.getSumFeeValueByCond(haveCondition,null);
		//��������δ���˷���
		double partunSetFeeAmont=BusinessUtility.getSumFeeValueByCond(invoiceCond,CommonConstDefinition.SETOFFFLAG_W);
		//�������������˷���
		double parthaveSetFeeAmont=BusinessUtility.getSumFeeValueByCond(invoiceCond,CommonConstDefinition.SETOFFFLAG_D);
		//�����ܺ�
		double feeTotal=0;
		//�ܽ��
		double totalPay=0;
		if(feeAdjustlist!=null && !feeAdjustlist.isEmpty()){

			Iterator feeAdjustIter=feeAdjustlist.iterator();
			while(feeAdjustIter.hasNext()){
				FeeAdjustmentWrapDTO currentFeeWrap=(FeeAdjustmentWrapDTO)feeAdjustIter.next();
				AccountItemDTO accountItemDTO=currentFeeWrap.getAccountItemDTO();
				feeTotal+=accountItemDTO.getValue();
			}
			
			System.out.println("type=============="+type);
			System.out.println("unSetFeeAmont=========="+unSetFeeAmont);
			System.out.println("partunSetFeeAmont========"+partunSetFeeAmont);
			System.out.println("feeTotal================"+feeTotal);
			
			if(CommonConstDefinition.SETOFFFLAG_W.equals(type)){
				if(unSetFeeAmont+partunSetFeeAmont<0){
					throw new  ServiceException("�ʵ������δ���˵ķ����ܶ�֮�Ͳ���С�ڣ�");
				}
				if(unSetFeeAmont+partunSetFeeAmont+feeTotal<0){
					throw new  ServiceException("δ���˵ķ����ܶ�͵��ʷ����ܶ�֮�Ͳ���С�ڣ�");
				}
				
			}else if(CommonConstDefinition.SETOFFFLAG_D.equals(type)){
				if(haveSetFeeAmont+parthaveSetFeeAmont<0){
					throw new  ServiceException("�ʵ�����������˵ķ����ܶ�֮�Ͳ���С�ڣ�");
				}
				if(haveSetFeeAmont+parthaveSetFeeAmont+feeTotal<0){
					throw new  ServiceException("�����˵ķ����ܶ�͵��ʷ����ܶ�֮�Ͳ���С�ڣ�");
				}
			}
		}
		if(paymentAdjustList!=null && !paymentAdjustList.isEmpty()){
			Iterator paymentIter=paymentAdjustList.iterator();
			while(paymentIter.hasNext()){
				PaymentAdjustWrapDTO currentpaymentWrap=(PaymentAdjustWrapDTO)paymentIter.next();
				PaymentRecordDTO paymentRecordDTO=currentpaymentWrap.getPaymentRecordDTO();
				totalPay+=paymentRecordDTO.getAmount();
			}
		}
		//�ֿ��ܶ�
		double totalDeduction=0;
		//�ֽ�ֿ��ܶ�
		double cashTotalDeduction=0;
		//����ֿ��ܶ�
		double tokenTotalDeduction=0;
		//���Ԥ��ֿ۵���
		if(preDeductionAdjustList!=null && !preDeductionAdjustList.isEmpty()){
			Iterator deductionIter=preDeductionAdjustList.iterator();
			while(deductionIter.hasNext()){
				PreDeductionAdjustWrapDTO curDeductionAdjustWrap=(PreDeductionAdjustWrapDTO)deductionIter.next();
				PrepaymentDeductionRecordDTO preDeductionDTO=curDeductionAdjustWrap.getPreDeductionDTO();
				totalDeduction+=preDeductionDTO.getAmount();
				if(CommonConstDefinition.PREPAYMENTTYPE_CASH.equals(preDeductionDTO.getPrepaymentType())){
					cashTotalDeduction+=preDeductionDTO.getAmount();
				}else if(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY.equals(preDeductionDTO.getPrepaymentType())){
					tokenTotalDeduction+=preDeductionDTO.getAmount();
				}
			}
		}
		if(CommonConstDefinition.SETOFFFLAG_D.equals(type)){
			//if(totalDeduction+totalPay!=feeTotal){
			if(Math.abs(totalDeduction+totalPay-feeTotal)>0.0001){
				throw new  ServiceException("���ʵķ����ܶ������ڵ���֧���ܶ�͵���Ԥ��ֿ۽��֮��");
			}
			//�ֽ�Ԥ��ֿ�
			String preDutionCond=" INVOICEDFLAG='"+CommonConstDefinition.YESNOFLAG_YES+"' AND INVOICENO="+id ;
			double preDeductionAmount=BusinessUtility.getSumDeductionRecordValueByFeeCond(preDutionCond);
			if(preDeductionAmount+totalDeduction<0){
				throw new  ServiceException("���˲�����Ԥ��ֿ۽����˵�����֮ǰ��Ԥ��ֿ۽��֮�ͱ�����ڵ���0");
			}
			AccountDTO accountDTO= BusinessUtility.getAcctDTOByAcctID(accountID);
			if(accountDTO.getCashDeposit()-cashTotalDeduction<0){
				throw new  ServiceException("�ʻ��ֽ�Ԥ������ȥ���˲�����Ԥ��ֿ۽���С�ڣ�");
			}
			if(accountDTO.getTokenDeposit()-tokenTotalDeduction<0){
				throw new  ServiceException("�ʻ�����Ԥ������ȥ���˲�����Ԥ��ֿ۽���С�ڣ�");
			}
		}
	}
	/**
	 * ����ʻ�����
	 * * �������׼��ɾ����,û����,
	 * @param csiDTO
	 * @param type
	 * @param feeAdjustlist
	 * @param paymentAdjustList
	 * @throws ServiceException
	 */
	public  void checkAccountAjustment(int accountID,String type,Collection feeAdjustlist,Collection paymentAdjustList,Collection preDeductionAdjustList) throws ServiceException{
		String unCondition=" SETOFFFLAG='"+CommonConstDefinition.SETOFFFLAG_W+"'" +" AND INVOICEDFLAG='"+CommonConstDefinition.YESNOFLAG_NO+"' AND ACCTID="+accountID;
		String haveCondition=" SETOFFFLAG='"+CommonConstDefinition.SETOFFFLAG_D+"'"+" AND INVOICEDFLAG='"+CommonConstDefinition.YESNOFLAG_NO+"' AND ACCTID="+accountID;
		String partCond=" INVOICEDFLAG='"+CommonConstDefinition.YESNOFLAG_NO+"' AND ACCTID="+accountID;
		//δ���˵ķ����ܶ�
		double unSetFeeAmont=BusinessUtility.getSumFeeValueByCond(unCondition,null);
		//�����˷����ܶ�
		double haveSetFeeAmont=BusinessUtility.getSumFeeValueByCond(haveCondition,null);
		//��������δ���˷���
		double partunSetFeeAmont=BusinessUtility.getSumFeeValueByCond(partCond,CommonConstDefinition.SETOFFFLAG_W);
		//�������������˷���
		double parthaveSetFeeAmont=BusinessUtility.getSumFeeValueByCond(partCond,CommonConstDefinition.SETOFFFLAG_D);
		//�����ܺ�
		double feeTotal=0;
		if(feeAdjustlist!=null && !feeAdjustlist.isEmpty()){

			Iterator feeAdjustIter=feeAdjustlist.iterator();
			while(feeAdjustIter.hasNext()){
				FeeAdjustmentWrapDTO currentFeeWrap=(FeeAdjustmentWrapDTO)feeAdjustIter.next();
				AccountItemDTO accountItemDTO=currentFeeWrap.getAccountItemDTO();
				feeTotal+=accountItemDTO.getValue();
			}
			//δ���˽��м��
			if(CommonConstDefinition.SETOFFFLAG_W.equals(type)){
				if(unSetFeeAmont+partunSetFeeAmont<0){
					throw new  ServiceException("δ���˵ķ����ܶ������ڵ��ڣ�");
				}
				if(unSetFeeAmont+partunSetFeeAmont+feeTotal<0){
					throw new  ServiceException("δ���˵ķ����ܶ�͵��ʷ����ܶ�֮�ͱ�����ڵ��ڣ�");
				}
			//�����˽��м��	
			}else if(CommonConstDefinition.SETOFFFLAG_D.equals(type)){
				if(haveSetFeeAmont+parthaveSetFeeAmont<0){
					throw new  ServiceException("�����˵ķ����ܶ������ڵ��ڣ�");
				}
				if(haveSetFeeAmont+parthaveSetFeeAmont+feeTotal<0){
					throw new  ServiceException("�����˵ķ����ܶ�͵��ʷ����ܶ�֮�ͱ�����ڵ��ڣ�");
				}
			}
		}
		//�ֽ�֧��
		double cashPayAmount=0;
		//�������֧��
		double tokenPayAmount=0;
		//�ܽ��
		double totalPay=0;
		if(paymentAdjustList!=null && !paymentAdjustList.isEmpty()){
			Iterator paymentIter=paymentAdjustList.iterator();
			while(paymentIter.hasNext()){
				PaymentAdjustWrapDTO currentpaymentWrap=(PaymentAdjustWrapDTO)paymentIter.next();
				PaymentRecordDTO paymentRecordDTO=currentpaymentWrap.getPaymentRecordDTO();
				totalPay+=paymentRecordDTO.getAmount();
				if(BusinessUtility.isCash(paymentRecordDTO.getMopID())){
					cashPayAmount+=paymentRecordDTO.getAmount();	
				}else{
					tokenPayAmount+=paymentRecordDTO.getAmount();	
				}
			}
		}
		//�ֿ��ܶ�
		double totalDeduction=0;
		//�ֽ�ֿ��ܶ�
		double cashTotalDeduction=0;
		//����ֿ��ܶ�
		double tokenTotalDeduction=0;
		//���Ԥ��ֿ۵���
		if(preDeductionAdjustList!=null && !preDeductionAdjustList.isEmpty()){
			Iterator deductionIter=preDeductionAdjustList.iterator();
			while(deductionIter.hasNext()){
				PreDeductionAdjustWrapDTO curDeductionAdjustWrap=(PreDeductionAdjustWrapDTO)deductionIter.next();
				PrepaymentDeductionRecordDTO preDeductionDTO=curDeductionAdjustWrap.getPreDeductionDTO();
				totalDeduction+=preDeductionDTO.getAmount();
				if(CommonConstDefinition.PREPAYMENTTYPE_CASH.equals(preDeductionDTO.getPrepaymentType())){
					cashTotalDeduction+=preDeductionDTO.getAmount();
				}else if(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY.equals(preDeductionDTO.getPrepaymentType())){
					tokenTotalDeduction+=preDeductionDTO.getAmount();
				}
			}
		}
		//�����˷��õ��ʽ��еļ��
		if(CommonConstDefinition.SETOFFFLAG_D.equals(type)){
			//if(feeTotal!=totalPay+totalDeduction){
			if(Math.abs(feeTotal-(totalPay+totalDeduction))>0.0001){
				throw new  ServiceException("���ʵķ����ܶ������ڵ��ʵ�֧���ۺϺ�Ԥ��ֿ۽��֮��");
			}
			AccountDTO accountDTO= BusinessUtility.getAcctDTOByAcctID(accountID);
			if(accountDTO.getCashDeposit()-cashTotalDeduction<0){
				throw new  ServiceException("�ʻ��ֽ�Ԥ������ȥ���˲�����Ԥ��ֿ۽���С�ڣ�");
			}
			if(accountDTO.getTokenDeposit()-tokenTotalDeduction<0){
				throw new  ServiceException("�ʻ�����Ԥ������ȥ���˲�����Ԥ��ֿ۽���С�ڣ�");
			}
		//�ʻ����ʽ��еļ��
		}else{
			AccountDTO accountDTO= BusinessUtility.getAcctDTOByAcctID(accountID);
			if(accountDTO.getCashDeposit()+cashPayAmount<0){
				throw new  ServiceException("���ʲ������ֽ�Ԥ�������ʻ��ֽ�Ԥ�����֮�Ͳ���С�ڣ�");
			}
			if(accountDTO.getTokenDeposit()+tokenPayAmount<0){
				throw new  ServiceException("���ʲ���������Ԥ�������ʻ�����Ԥ�����֮�Ͳ���С�ڣ�");
			}
		}
		
	}
	/**
	 * �����������ʱ����Ϣ
	 * * �������׼��ɾ����,û����,
	 * @param csiId
	 * @param feeAdjustlist
	 * @param paymentAdjustList
	 * @throws ServiceException
	 */
	public  void checkCsiAjustment(int csiId,int accountID,Collection feeAdjustlist,Collection paymentAdjustList,Collection preDeductionAdjustList) throws ServiceException{
		//������ý��
		double csiPayAmount=0;
		//Ԥ�����
		double cisPrepayment=0;
		//�ܵķ���֧��
		double totalPayAmount=0;
		//�ֽ�֧��
		double cashPayAmount=0;
		//�������֧��
		double tokenPayAmount=0;
		
		//�ֽ�ǿ��֧��Ԥ��
		double cashPrePayAmount=0;
		//�������ǿ��֧��Ԥ��
		double tokenPrePayAmount=0;
		//ǿ��Ԥ�����
		double forceDepositAmount=0;
		//�������
		double csiFee=0;
		//�����ܺ�
		double feeTotal=0;
		
		
		//�ֿ��ܶ�
		double totalDeduction=0;
		//�ֽ�ֿ��ܶ�
		double cashTotalDeduction=0;
		//����ֿ��ܶ�
		double tokenTotalDeduction=0;
		//������õ���
		if(feeAdjustlist!=null && !feeAdjustlist.isEmpty()){
			Iterator feeIter=feeAdjustlist.iterator();
			while(feeIter.hasNext()){
				FeeAdjustmentWrapDTO currentFeeWrap=(FeeAdjustmentWrapDTO)feeIter.next();
				AccountItemDTO accountItemDTO=currentFeeWrap.getAccountItemDTO();
				LogUtility.log(clazz,LogLevel.DEBUG,"������checkCsiAjustment accountItemDTO������" + accountItemDTO);
				feeTotal+=accountItemDTO.getValue();
				if(FeeService.isForceDeposit(accountItemDTO)){
					forceDepositAmount+=accountItemDTO.getValue();
				}else{
					csiFee+=accountItemDTO.getValue();
				}
			}
		}
		//���֧������
		if(paymentAdjustList!=null && !paymentAdjustList.isEmpty()){
			Iterator paymentIter=paymentAdjustList.iterator();
			while(paymentIter.hasNext()){
				PaymentAdjustWrapDTO currentpaymentWrap=(PaymentAdjustWrapDTO)paymentIter.next();
				PaymentRecordDTO paymentRecordDTO=currentpaymentWrap.getPaymentRecordDTO();
				LogUtility.log(clazz,LogLevel.DEBUG,"������checkCsiAjustment paymentRecordDTO������" + paymentRecordDTO);
					totalPayAmount+=paymentRecordDTO.getAmount();
					if(FeeService.isCsiOnceFee(paymentRecordDTO)){
						csiPayAmount+=paymentRecordDTO.getAmount();
						if(BusinessUtility.isCash(paymentRecordDTO.getMopID())){
							cashPayAmount+=paymentRecordDTO.getAmount();	
						}else{
							tokenPayAmount+=paymentRecordDTO.getAmount();	
						}
					}else{
						cisPrepayment+=paymentRecordDTO.getAmount();
						if(BusinessUtility.isCash(paymentRecordDTO.getMopID())){
							cashPrePayAmount+=paymentRecordDTO.getAmount();	
						}else{
							tokenPrePayAmount+=paymentRecordDTO.getAmount();	
						}
					}
			}
		}
		//���Ԥ��ֿ۵���
		if(preDeductionAdjustList!=null && !preDeductionAdjustList.isEmpty()){
			Iterator deductionIter=preDeductionAdjustList.iterator();
			while(deductionIter.hasNext()){
				PreDeductionAdjustWrapDTO curDeductionAdjustWrap=(PreDeductionAdjustWrapDTO)deductionIter.next();
				PrepaymentDeductionRecordDTO preDeductionDTO=curDeductionAdjustWrap.getPreDeductionDTO();
				totalDeduction+=preDeductionDTO.getAmount();
				if(CommonConstDefinition.PREPAYMENTTYPE_CASH.equals(preDeductionDTO.getPrepaymentType())){
					cashTotalDeduction+=preDeductionDTO.getAmount();
				}else if(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY.equals(preDeductionDTO.getPrepaymentType())){
					tokenTotalDeduction+=preDeductionDTO.getAmount();
				}
			}
		}
		String precashDutionCond=" PREPAYMENTTYPE='"+CommonConstDefinition.PREPAYMENTTYPE_CASH +"' AND CREATINGMETHOD='"+CommonConstDefinition.FTCREATINGMETHOD_B+"'";
		precashDutionCond=precashDutionCond+" AND REFERRECORDTYPE='"+CommonConstDefinition.F_PDR_REFERRECORDTYPE_C+"' AND ReferRecordID="+csiId;
		
		String preTokenDutionCond=" PREPAYMENTTYPE='"+CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY +"' AND CREATINGMETHOD='"+CommonConstDefinition.FTCREATINGMETHOD_B+"'";
		preTokenDutionCond=preTokenDutionCond+" AND REFERRECORDTYPE='"+CommonConstDefinition.F_PDR_REFERRECORDTYPE_C+"' AND REFERRECORDID="+csiId;
		
		//�ֽ�Ԥ��ֿ�
		double preCashDeductionAmount=BusinessUtility.getSumDeductionRecordValueByFeeCond(precashDutionCond);
		//����Ԥ��ֿ�
		double preTokenDeductionAmount=BusinessUtility.getSumDeductionRecordValueByFeeCond(preTokenDutionCond);
		//�жϵ��ʲ�����һ���Է��ú͵��ʲ�����֧�������ʲ�����Ԥ��ֿ��Ƿ����
		//if(csiFee!=csiPayAmount+totalDeduction){
		if(Math.abs(csiFee-(csiPayAmount+totalDeduction))>0.0001){
			throw new  ServiceException("���˲�����֧���������˲�����Ԥ��ֿ۽��ĺϼƲ����ڵ��˲����ķ��ý��");
		}
		//�ϼƸ�������ص�����Ԥ��ϼƽ��
		String prePayCond="'"+CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE+"','"+CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE+"'";
		double haveActPrePayedValue=BusinessUtility.getSumPaymentRecordValueByTypeAndID(csiId,CommonConstDefinition.FTREFERTYPE_M,prePayCond,null);
		
		if(cisPrepayment+haveActPrePayedValue<0){
			throw new  ServiceException("���˲�����Ԥ����������֮ǰ��Ԥ�����֮��Ҫ���ڵ���0");
		}
		AccountDTO accountDTO=BusinessUtility.getAcctDTOByAcctID(accountID);
		if (accountDTO ==null) throw new ServiceException("�ʻ�����Ч���ܵ��ʣ�");
		//����ֽ�Ԥ���˻����
		if(cashPrePayAmount-cashTotalDeduction+accountDTO.getCashDeposit()<0){
			throw new  ServiceException("���˲������ֽ�Ԥ�����������˲������ֽ�Ԥ��ֿ۽����Ͽͻ��ֽ��˻���Ԥ����Ҫ���ڵ���0");
		}
		//����������Ԥ���˻����
		if(tokenPrePayAmount-tokenTotalDeduction +accountDTO.getTokenDeposit()<0){
			throw new  ServiceException("���˲���������Ԥ�����������˲���������Ԥ��ֿ۽����Ͽͻ������˻���Ԥ����Ҫ���ڵ���0");
		}
		//����֮ǰ�����з����ܼ�	
		String allHavedfeeCountCond=" REFERTYPE='"+CommonConstDefinition.FTREFERTYPE_M+"' AND REFERID="+csiId+" AND FORCEDDEPOSITFLAG='"+CommonConstDefinition.YESNOFLAG_NO+"'";
		double allFeeCount=BusinessUtility.getSumFeeValueByCond(allHavedfeeCountCond,null);
		if(allFeeCount+feeTotal<0){
			throw new  ServiceException("���ʲ����ķ��ý��͵���֮ǰ�ķ��ý��֮��Ҫ���ڵ���0");
		}
		
		if(totalDeduction+preCashDeductionAmount+preTokenDeductionAmount<0){
			throw new  ServiceException("���˲�����Ԥ��ֿ۽�����������֮ǰ��Ԥ��ֿ۽��֮��Ҫ���ڵ���0");
		}
	}
	/**
	 * ���ͻ��ײ��Ƿ����ȡ��,
	 * 1,����״̬,
	 * 2,��Чʱ���ڵ�,
	 * 3,�ײ���صĿͻ���Ʒ��������,
	 * @param ccid
	 * @throws ServiceException
	 */
	public Map checkCustomerBundleCancel(int ccid) throws ServiceException{
		if(ccid==0){
			throw new ServiceException("��Ч�Ŀͻ��ײ�!");
		}
		CustomerCampaignDTO ccDto=BusinessUtility.getCustomerCampaignDTOByID(ccid);
		if(!CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL.equals(ccDto.getStatus())){
			throw new ServiceException("ֻ������״̬���ײͿ���ȡ��!");
		}
		Timestamp curDate=TimestampUtility.getCurrentDateWithoutTime();
		if(ccDto.getDateTo().before(curDate)){
			throw new ServiceException("�ײ��Ѿ�����!");
		}
		
		//ȡ������ص�ҵ���ʻ����ͻ���Ʒ
		Map saMap= BusinessUtility.getServiceAccountMapByCustCampaignID(ccid,false);
		//�����������,��ҵ���ʻ�Ϊ��λ, 
		java.util.Iterator sait=saMap.keySet().iterator();
		while(sait.hasNext()){
			Integer said=(Integer) sait.next();
			ArrayList cpList=(ArrayList) saMap.get(said);
			ArrayList psidList=new ArrayList();
			for(int i=0;i<cpList.size();i++){
				int psid=((CustomerProductDTO)cpList.get(i)).getPsID();
				psidList.add(new Integer(psid));
			}
    	    LogUtility.log(clazz,LogLevel.DEBUG,"checkCustomerBundleCancel.psidList:" + psidList + "\nsaid:"+said);
			checkProductOperationDependency(psidList,said.intValue());			
		}
		return saMap;
	}
	/**
     * ���ͻ����Զһ�������
	 * 1.�������Ƿ�;2����û������Ƿ��ʵ� 
	 * @throws ServiceException
	 */
	public UserPointsExchangerCdDTO processExchange() throws ServiceException{
		Integer custId =(Integer)serviceContext.get(Service.CUSTOMER_ID);
		Integer currentPoints =(Integer)serviceContext.get(Service.CURRENT_POINTS);
		Integer tatalPoints =(Integer)serviceContext.get(Service.TOTAL_POINTS); 
		String custType = (String) serviceContext.get(Service.CUSTOMER_TYPE); 
		Integer goodsID =(Integer)serviceContext.get(Service.GOOD_ID); 
		Integer activityID =(Integer)serviceContext.get(Service.ACTIVITY_ID); 
		Integer operatorId = (Integer)serviceContext.get(Service.OPERATIOR_ID); 
		//����Ƿ����custId
		 if (custId == null) 
		   throw new ServiceException("û�пͻ���Ϣ��");
		 //����Ƿ���ڿ��û���
		 if (currentPoints == null) 
			throw new ServiceException("��ǰ�ͻ�û�п��û��֡�");
         //����Ƿ�����ۼƻ���
		 if (tatalPoints == null) 
			throw new ServiceException("��ǰ�ͻ�û�п��û��֡�");
//		����Ƿ���ڿͻ�����
		 if (custType == null) 
			throw new ServiceException("��ǰ�ͻ�û�����͡�");
  //	����Ƿ���ڿͻ�����
		 if (goodsID == null) 
			throw new ServiceException("��ǰ�ͻ�û��ѡ����Ʒ��");
		 //	����Ƿ��������
		 if (activityID == null) 
			throw new ServiceException("��ǰ�ͻ�û��ѡ����");
		 
	  	try{
	  		
	  		 UserPointsExchangerCdDTO  recordDto = new UserPointsExchangerCdDTO();
	  		 recordDto.setActivityId(activityID.intValue());
	  		 recordDto.setCreateOperatorId(operatorId.intValue());
	  		 recordDto.setExchangeGoodsAmount(1);
	  		 recordDto.setUserId(custId.intValue());
	  		 recordDto.setExchangeGoodsTypeId(goodsID.intValue());
	  		 recordDto.setUserPointsBefore(currentPoints.intValue());
//	  		������ڶ�������ʱ����Ҫ���ֵ���Сֵ
	  		int miniPoints = 0;
	  		UserPointsExchangeRuleHome userPointsExchRuleHome =HomeLocater.getUserPointsExchangeRuleHome();
	    	Collection userPointsExchRule=userPointsExchRuleHome.
			                   findByExchangeGoodsTypeIdAndActivityId(goodsID.intValue(),activityID.intValue());
	    	Iterator currentExchRuleIter=userPointsExchRule.iterator(); 
	    	
	    	while(currentExchRuleIter.hasNext()){
	    		UserPointsExchangeRule currentRule=(UserPointsExchangeRule)currentExchRuleIter.next();
	    	    String custTypeStr=currentRule.getCustTypeList();
	    	    LogUtility.log(clazz,LogLevel.DEBUG,"�����CustomerTypeStrΪ��������������" + custTypeStr);
	    	    LogUtility.log(clazz,LogLevel.DEBUG,"�����CustTypeΪ��������������" + custType);
	    	 //get the points according to the customertype
	    	     
	    	 if(custTypeStr!=null && custTypeStr.indexOf(custType)!=-1){ 
	    	 	 
	    	 	int pointValue = currentRule.getExchangePointsValue();
	    	 	if(miniPoints ==0)
	    	 	miniPoints=currentRule.getExchangePointsValue();
	    	 	if(miniPoints >pointValue)
	    	 		miniPoints =pointValue;
	    	 }
	    	 if(custTypeStr == null){
	    	 	int pointValue = currentRule.getExchangePointsValue();
	    	 	if(miniPoints ==0)
	    	 	miniPoints=currentRule.getExchangePointsValue();
	    	 	if(miniPoints >pointValue)
	    	 		miniPoints =pointValue;
	    	 }
	    	
	    	}
	    	if (miniPoints==0)
	    		  throw new ServiceException("�ÿͻ����Ͳ��ܲμ����� ");
	    	  
	    	   
	       else if(currentPoints.intValue()>=miniPoints){
	    	 	CustomerHome custHome = HomeLocater.getCustomerHome();	
	    	 	Customer  cust= custHome.findByPrimaryKey(custId);
	    	 	cust.setCurrentAccumulatePoint(currentPoints.intValue()-miniPoints);
	    	 	recordDto.setExchangePoints(miniPoints);
	    	    recordDto.setCreateTime(new Timestamp(System.currentTimeMillis()));
	    	 	recordDto.setUserPointPost(currentPoints.intValue()-miniPoints);
	    	 	return recordDto;
	    	 	} else 
	    	 	   throw new ServiceException("���ֲ�����");
	    	 
	    	}
	  	     
	    	catch(HomeFactoryException e1) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkPointEnough",e1);
			throw new ServiceException("���ͻ����ֶ�λ����");
		  }catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkPointEnough",e2);
			throw new ServiceException("���ͻ����ֲ��Ҵ���");
		}
		 
	}
	   
	    
	/**
 
	 * ���ȡ��������ԤԼ��/ԤԼ��
	 * @param event
	 * @throws ServiceException
	 */
	public void checkCancelBook(BookEJBEvent event) throws ServiceException{
		try {
			//ȡ��������Ϣ
			CustServiceInteractionDTO csiDTO=event.getCsiDto();
			CustServiceInteractionHome csiHome =HomeLocater.getCustServiceInteractionHome();
			CustServiceInteraction csi=csiHome.findByPrimaryKey(new Integer(csiDTO.getId()));
			//��������ʱԤԼ/������ԤԼ��
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(csi.getType())){
				boolean isAgent=event.isAgent();
			    if (isAgent) {
			    	//����Ǵ����̣�ԤԼ��״̬Ϊ���½���
			    	if(!CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_NEW.equals(csi.getStatus())){
			    		throw new ServiceException("ȡ���Ĵ�����ԤԼ����״̬�������½�");
			    	}
			    }else{
			    	//�����ԤԼ��״̬Ϊ��������
			    	if(!CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_NEW.equals(csi.getStatus())&&
			    		!CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT.equals(csi.getStatus())){
			    		throw new ServiceException("ȡ����ԤԼ����״̬�������½�/������");
			    	}
			    }
			}else{
				throw new ServiceException("����������ԤԼ��");
			}
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("ȡ��ԤԼ��ʱ��λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("ȡ��ԤԼ��ʱ�Ҳ��������Ϣ");
		}
	}
	/**
 
	 * ���ԤԼ��������ԤԼ��ԤԼ�������ŵ꿪����Ϣ����ȷ��
	 * @param event
	 * @throws ServiceException
	 */
	public void checkOpenInfo(BookEJBEvent event) throws ServiceException{
    	//ȡ���¿ͻ���Ϣ
    	NewCustomerInfoDTO newCustInfo = event.getNewCustInfo();
    	//ȡ���¿ͻ����˻���Ϣ
    	NewCustAccountInfoDTO newCustAcctInfo = event.getNewCustAcctInfo();
    	//ȡ��������Ϣ
		CustServiceInteractionDTO csiDTO=event.getCsiDto();
		//ȡ�ÿͻ���ַ��Ϣ
		AddressDTO custAddrDto = event.getCustAddressDTO();
		//ȡ���˻���ַ��Ϣ
		AddressDTO acctAddrDto = event.getAcctAddressDTO();
		//�ͻ����Ź�ȯ��
		String detailNO =event.getDetailNo();
		//ȡ�ÿͻ�����Ĳ�Ʒ����Ϣ
		Collection csiPackageArray = event.getCsiPackageArray();
		if(csiPackageArray==null){
			csiPackageArray=new ArrayList();
			event.setCsiPackageArray(csiPackageArray);
		}
		//ȡ�ÿͻ�ѡ����Ż���Ϣ
		Collection csiCampaignArray = event.getCsiCampaignArray();
		if(csiCampaignArray==null){
			csiCampaignArray=new ArrayList();
			event.setCsiCampaignArray(csiCampaignArray);
		}
		
		//���¼��Ŀͻ��������Ƿ��Ѿ�����
		checkEixtMultCustSerialNo(newCustInfo.getCustomerSerialNo());
		
		//Ӳ���豸��Ϣ
		//Hashtable terminalDeviceTable=event.getDeviceTable();
		HashMap terminalDeviceTable=event.getDeviceTable();
		//����Ź�ȯ�Ƿ���Ч��������Ź�ȯ���Żݵ��ų���
		checkDetailNoValid(detailNO,csiPackageArray,csiCampaignArray,newCustInfo.getOpenSourceType());
		
		//��ʼ���ͻ�
		if (newCustInfo == null) 
			throw new ServiceException("û�пͻ���Ϣ��");
		//��ʼ����˻�
		if (newCustAcctInfo == null)
			throw new ServiceException("û���˻���Ϣ��");
		
		//��ʼ��������ʺ� 2008-2-1,ԤԼ/������ԤԼ����������ʺŸ�ʽ,wangpeng@20080311
		if(!CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(csiDTO.getType())
				&&newCustAcctInfo.getBankAccount()!=null && 
				!"".equals(newCustAcctInfo.getBankAccount())){
			if(newCustAcctInfo.getMopID()!=0){
				String functionResult = BusinessUtility.callFunctionForCheckBankAccount(newCustAcctInfo.getMopID(),newCustAcctInfo.getBankAccount());
				if(!"true".equals(functionResult))
					throw new ServiceException(functionResult);
			}
		}
		//��ʼ�������
		if (csiDTO == null)
			throw new ServiceException("û��������Ϣ��");
		//��ʼ���ͻ���ַ
		if ((custAddrDto == null) && acctAddrDto==null)
			throw new ServiceException("û�пͻ���ַ��Ϣ��");
		//��ʼ���ͻ��˻���ַ		
		if ((custAddrDto == null) && acctAddrDto==null)
			throw new ServiceException("û�пͻ��˻���Ϣ��");
			
		//ֻ���ͻ���Ϣ/�˻���Ϣ/�Ź�ȯ��Ϣʱ���˷���
		if(OpenAccountCheckEJBEvent.CHECK_CUSTOMERINFO==event.getActionType() || 
				OpenAccountCheckEJBEvent.CHECK_OPEN_ACCOUNT_CATV==event.getActionType()||
				OpenAccountCheckEJBEvent.OPEN_CATV_ACCOUNT==event.getActionType()){
			return;
		}
		//ֻ���ڷ��Ź���ʱ�����Ҫ����Ʒ�� ���Żݣ���Ʒ������֮��Ĺ�ϵ
		if(detailNO==null || "".equals(detailNO)){
			//����ײͺ��Żݴ�������
			CommonBusinessParamDTO paramDTO=new CommonBusinessParamDTO(newCustInfo.getType(),newCustInfo.getOpenSourceType(),event.getCsiDto().getType(),event.getOperatorID());
			checkPackageCampaign(csiPackageArray,csiCampaignArray,custAddrDto,true,paramDTO);		
		}
		
		//ȡ�ò�Ʒ����Ӧ�����в�Ʒ��Ϣ
		Collection currentAllProductCol=new ArrayList();
		Iterator currentPackageIter=csiPackageArray.iterator();

		while(currentPackageIter.hasNext()){
			Integer currentPackageID=(Integer)currentPackageIter.next();
			currentAllProductCol.addAll(BusinessUtility.getProductIDsByPackageID(currentPackageID.intValue())) ;
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG,"checkOpenInfo ����Ʒ��������-----------------start-------"+currentAllProductCol);
		
		//ֻ���ڷ��Ź���ʱ�����Ҫ����Ʒ��������
		if(detailNO==null || "".equals(detailNO)){
			//����Ƿ������Ҫ����ҵ���˻��Ĳ�Ʒ
			if(!checkExitCreateServiceAccountProduct(csiPackageArray)){
				throw new ServiceException("ѡ���Ʒ���в��������Դ���ҵ���ʻ��Ĳ�Ʒ��");
			}
			
			//����Ʒ֮���������
			//�Ѳ�Ʒ��ҵ���ʻ��ֿ������ÿ��ҵ���ʻ��µĲ�Ʒ�Ƿ�����������ϵ,modify by jason 2007-3-9
			ServiceContext saContext=new ServiceContext();
			saContext.put(Service.OPERATIOR_ID, new Integer(event.getOperatorID()));
			ServiceAccountService saService=new ServiceAccountService(saContext);
			
			//2008-04-21 hujinpeng ����ͬʱ�����������Ʒ����Ʒ�����ظ� ������鹺��ò�Ʒ�Ƿ��ظ�
			Collection productIDForLis = (ArrayList)((ArrayList)currentAllProductCol).clone();
			Iterator itProductID = productIDForLis.iterator();
			Collection contianCol=new ArrayList();
			while (itProductID.hasNext()) {
				Integer productID = ((Integer) itProductID.next());
				LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkOpenInfo�������  contianCol"+contianCol);
				LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkOpenInfo�������  productID"+productID);
				if(contianCol.contains(productID))
					throw new ServiceException("��Ʒ�������ظ�������ѡ����Ĳ�ƷID��"+BusinessUtility.getProductNameByProductID(productID.intValue()) +" �ظ���");
				contianCol.add(productID);
			}
			
			
			Map productIDOfSAMap=saService.productSplitToSA(currentAllProductCol);
			if(productIDOfSAMap==null || productIDOfSAMap.isEmpty())
				throw new ServiceException("��Ʒ�������������ܴ���ҵ���ʻ���");
			Iterator itProductIDOfSA=productIDOfSAMap.keySet().iterator();
			while(itProductIDOfSA.hasNext()){
				Integer productIDOfSA=(Integer)itProductIDOfSA.next();
				Collection productIDToOneSAList=(Collection)productIDOfSAMap.get(productIDOfSA);
				if(productIDToOneSAList==null)
					productIDToOneSAList=new ArrayList();
				productIDToOneSAList.add(productIDOfSA);
				LogUtility.log(clazz,LogLevel.DEBUG,"����Ʒ��������------------------------"+productIDOfSA);
				checkProductDependency(productIDToOneSAList);
			}
			//checkProductDependency(currentAllProductCol);
			//end
			
		}	
		LogUtility.log(clazz,LogLevel.DEBUG,"checkOpenInfo ����Ʒ��������-----------------end-------");
		
		//���ͻ���Ʒ���Ż���Ϣ���˷���
		if(OpenAccountCheckEJBEvent.CHECK_PRODUCTPG_CAMPAINPG==event.getActionType()){
			return;
		}
		//�����ԤԼ��/������ԤԼ��ʱ���˷���
		if(OpenAccountCheckEJBEvent.BOOKING==event.getActionType()||
		  OpenAccountCheckEJBEvent.ALTERBOOKING==event.getActionType()||
		  OpenAccountCheckEJBEvent.AGENT_BOOKING==event.getActionType()||
		  OpenAccountCheckEJBEvent.CHECK_AGENT_BOOKING==event.getActionType()){
			return;
		}

		//ȡ�ò�Ʒ������Ϣ
		Map productPropertyMap = event.getProductPropertyMap();
		LogUtility.log(clazz, LogLevel.DEBUG, "checkOpenInfo ȡ�ò�Ʒ������Ϣ"+productPropertyMap);
		//����Ʒ����,�û���Ψһ��У�� 2008-03-25 hjp
		if(productPropertyMap!=null){
			Iterator itProductProperty = productPropertyMap.keySet().iterator();
			while(itProductProperty.hasNext()){
				String productID = (String)itProductProperty.next();
				Map tempMap = (Map)productPropertyMap.get(productID);
				String strPropertyValue = (String)tempMap.get("30002");
				String strPropertyName = BusinessUtility.getProductPropertyDTObyPrimaryKey(productID,"30002").getPropertyName();
				if(BusinessUtility.getISExistCpConfigedPropertyByPropertyCodeAndValue(productID,"30002",strPropertyValue))
					throw new ServiceException(strPropertyName+"�ظ�");
					//throw new ServiceException("�ͻ���Ʒ������,�û����ظ�");
			}
		}
		
		//�����ѡ��Ӳ���豸����ѡӲ���豸�Ͳ�Ʒ��ӳ���ϵ�Ƿ���ȷ
		checkSelectTerminalDevice(csiDTO, terminalDeviceTable,currentAllProductCol,true);
		//���Ӳ����Ʒ��Ϣʱ���˷���
		if(OpenAccountCheckEJBEvent.CHECK_HARDPRODUCTINFO==event.getActionType()){
			return;
		}
		
		//��������Ϣʱ���˷���
		if(OpenAccountCheckEJBEvent.CHECK_FREEINFO==event.getActionType()){
			return;
		}
		
		
	}
	
	/**
	 * ����������Ʒ�����
	 */
	public void checkBatchAddCustomerProduct(CustomerProductEJBEvent event)throws ServiceException{
		try {
			//ȡ�ÿͻ�����Ĳ�Ʒ����Ϣ
			Collection csiPackageArray = new ArrayList(event.getCsiPackageArray());
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
			//ȡ�ÿͻ�ѡ����Ż���Ϣ
			Collection csiCampaignArray = event.getCsiCampaignArray();
			//ȡ���¿ͻ���Ϣ
	    	int  customerID = event.getCustomerID();
	    	
	    	//add by david.Yang �����Э��ͻ�ֻ��ѡ��Э��Ĳ�Ʒ��
	    	Map  protocolDtoMap =BusinessUtility.getProtocolDTOColByCustID(customerID);
			if (protocolDtoMap !=null && protocolDtoMap.size() >0){
				Iterator packageItr  = csiPackageArray.iterator();
				while(packageItr.hasNext()){
					Integer packateID=(Integer)packageItr.next();
					if (!protocolDtoMap.keySet().contains(packateID)){
						ProductPackageDTO dto =BusinessUtility.getProductPackageDTOByID(packateID.intValue());
						throw new ServiceException(dto.getPackageName()+"����Э�麭�Ƿ�Χ�ڣ��������Ա��ϵ��");
					}
				}
			}
			//add by david.Yang.�����Э��ͻ�ֻ��ѡ��Э��Ĳ�Ʒ�� end
	    	
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
			//ȡ���˻���Ϣ
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("��ѡ����˻���δ���˵���");
	    	}
	    	//�ͻ���ַ��Ϣ
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());
			
			//����Ƿ������Ҫ����ҵ���˻��Ĳ�Ʒ
			if(checkExitCreateServiceAccountProduct(csiPackageArray)){
				throw new ServiceException("ѡ���Ʒ���в��ܰ������Դ���ҵ���ʻ��Ĳ�Ʒ��");
			}
			
			CommonBusinessParamDTO paramDTO=new CommonBusinessParamDTO(customer.getCustomerType(),customer.getOpenSourceType(),event.getCsiDto().getType(),event.getOperatorID());
			checkPackageCampaign(csiPackageArray,csiCampaignArray,custAddrDto,true,paramDTO);
			BusinessUtility.checkBatchAddPackageInfo(csiPackageArray);
	
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("������Ʒ�ļ���ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("������Ʒ�ļ�����Ҳ��������Ϣ");
		}catch(ServiceException e){
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException(e.toString());
		}
	}
	/**
	 * ������Ʒʱ�ļ��
	 * 
	 * @throws ServiceException
	 */
	public void checkAddCustomerProduct(CustomerProductEJBEvent event)throws ServiceException{
		try {
			//ȡ�ÿͻ�����Ĳ�Ʒ����Ϣ
			Collection csiPackageArray = new ArrayList(event.getCsiPackageArray());
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
			//ȡ�ÿͻ�ѡ����Ż���Ϣ
			Collection csiCampaignArray = event.getCsiCampaignArray();
	    	//ȡ���¿ͻ���Ϣ
	    	int  customerID = event.getCustomerID();
	    	
	    	//add by david.Yang �����Э��ͻ�ֻ��ѡ��Э��Ĳ�Ʒ��
	    	Map  protocolDtoMap =BusinessUtility.getProtocolDTOColByCustID(customerID);
			if (protocolDtoMap !=null && protocolDtoMap.size() >0){
				// �����ԤԼ������Ʒ��������Э���û� 
				if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(event.getCsiDto().getType())){
					throw new ServiceException("Э���û�������ԤԼ������Ʒ���������Ա��ϵ��");
				}
				Iterator packageItr  = csiPackageArray.iterator();
				while(packageItr.hasNext()){
					Integer packateID=(Integer)packageItr.next();
					if (!protocolDtoMap.keySet().contains(packateID)){
						ProductPackageDTO dto =BusinessUtility.getProductPackageDTOByID(packateID.intValue());
						throw new ServiceException(dto.getPackageName()+"����Э�麭�Ƿ�Χ�ڣ��������Ա��ϵ��");
					}
				}
			}
			//add by david.Yang.�����Э��ͻ�ֻ��ѡ��Э��Ĳ�Ʒ�� end
	    	
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("ֻ�������û�����������Ʒ��");
	    	}
	    	//ȡ��ҵ���˻���Ϣ
	    	ServiceAccountHome serviceAccountHome=HomeLocater.getServiceAccountHome();
	    	ServiceAccount serviceAccount =serviceAccountHome.findByPrimaryKey(new Integer(event.getSaID() ));
	    	if(!CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL.equals(serviceAccount.getStatus())){
	    		throw new ServiceException("ֻ������ҵ���˻�����������Ʒ��");
	    	}

	    	//ȡ���˻���Ϣ
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("��ѡ����˻���δ���˵���");
	    	}
	    	
	    	//�ͻ���ַ��Ϣ
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());
			
			//����Ƿ������Ҫ����ҵ���˻��Ĳ�Ʒ
			if(checkExitCreateServiceAccountProduct(csiPackageArray)){
				throw new ServiceException("ѡ���Ʒ���в��ܰ������Դ���ҵ���ʻ��Ĳ�Ʒ��");
			}
			
			// �����ԤԼ������Ʒ��ֻ��ԤԼҵ���˻����Ѿ����ڵĲ�Ʒ
			if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(event.getCsiDto().getType())){
				Collection saProductCol =BusinessUtility.getProductIDListByServiceAccount(event.getSaID()," status <> '" + CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"' ");
				Collection csiProductCol =new ArrayList();
				Iterator   csiPackageItr =csiPackageArray.iterator();
				while (csiPackageItr.hasNext()){
					Integer csiPackageID =(Integer)csiPackageItr.next();
					csiProductCol.addAll(BusinessUtility.getProductIDsByPackageID(csiPackageID.intValue()));
				}	
				Iterator csiProductItr =csiProductCol.iterator();
				while (csiProductItr.hasNext()){
				   Integer csiProductID =(Integer)csiProductItr.next();
				   if (!saProductCol.contains(csiProductID)){
					   String productName =BusinessUtility.getProductNameByProductID(csiProductID.intValue());
					   throw new ServiceException(productName+"���ڸ�ҵ���˻���,����ԤԼ������������Ʒ���ܣ�");
				   }
				}
				return;
			}
			
			//ֻ���ڷ��Ź���ʱ�����Ҫ����Ʒ�� ���Żݣ���Ʒ������֮��Ĺ�ϵ
			//����ײͺ��Żݴ�������
			CommonBusinessParamDTO paramDTO=new CommonBusinessParamDTO(customer.getCustomerType(),customer.getOpenSourceType(),event.getCsiDto().getType(),event.getOperatorID());
			checkPackageCampaign(csiPackageArray,csiCampaignArray,custAddrDto,true,paramDTO);
			
	    	//ȡ��ҵ���˻��µ����еĲ�Ʒ��
	    	//csiPackageArray.addAll(BusinessUtility.getCustProductPackageBySAID(event.getSaID() ));
			
			//ȡ�ò�Ʒ����Ӧ�����в�Ʒ��Ϣ
			Collection currentAllProductCol=new ArrayList();
			Collection eventProductCol=new  ArrayList();
			currentAllProductCol.addAll(BusinessUtility.getProductIDListByServiceAccount(event.getSaID()," status <> '" + CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"' "));
			Iterator currentPackageIter=csiPackageArray.iterator();
			while(currentPackageIter.hasNext()){
				Integer currentPackageID=(Integer)currentPackageIter.next();
				eventProductCol.addAll(BusinessUtility.getProductIDsByPackageID(currentPackageID.intValue())) ;
			}
			currentAllProductCol.addAll(eventProductCol);
			//����Ʒ֮���������
			checkProductDependency(currentAllProductCol);
			
			//����ظ���Ʒ
			checkProductTautology(currentAllProductCol);
			
			//���ͻ���Ʒ���Ż���Ϣ���˷���
			if(CustomerProductEJBEvent.CHECK_PRODUCTPG_CAMPAINPG==event.getActionType()){
				
				return;
			}
			
			//ȡ�ò�Ʒ������Ϣ
			Map productPropertyMap = event.getProductPropertyMap();
			LogUtility.log(clazz, LogLevel.DEBUG, "checkAddCustomerProduct ȡ�ò�Ʒ������Ϣ"+productPropertyMap);
			//����Ʒ����,�û���Ψһ��У�� 2008-03-25 hjp
			if(productPropertyMap!=null){
				Iterator itProductProperty = productPropertyMap.keySet().iterator();
				while(itProductProperty.hasNext()){
					String productID = (String)itProductProperty.next();
					Map tempMap = (Map)productPropertyMap.get(productID);
					String strPropertyValue = (String)tempMap.get("30002");
					String strPropertyName = BusinessUtility.getProductPropertyDTObyPrimaryKey(productID,"30002").getPropertyName();
					if(BusinessUtility.getISExistCpConfigedPropertyByPropertyCodeAndValue(productID,"30002",strPropertyValue))
						throw new ServiceException(strPropertyName+"�ظ�");
						//throw new ServiceException("�ͻ���Ʒ������,�û����ظ�");
				}
			}
			
			//���Ӳ�� 
			if(event.getDeviceMap()!=null){
				checkSelectTerminalDevice(event.getCsiDto(), event.getDeviceMap(),eventProductCol,true);
			}
			//���Ӳ����Ʒ��Ϣʱ���˷���
			if(CustomerProductEJBEvent.CHECK_HARDPRODUCTINFO==event.getActionType()){
				return;
			}
			
			//��������Ϣʱ���˷���
			if(CustomerProductEJBEvent.CHECK_FREEINFO==event.getActionType()){
				return;
			}

		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("������Ʒ�ļ���ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("������Ʒ�ļ�����Ҳ��������Ϣ");
		}
	}
	/**
	 * �����û��Ĳ���
	 * @param event
	 * @throws ServiceException
	 */
	public void checkOperationCustomer(CustomerEJBEvent event)throws ServiceException{
		try {
			//ȡ���¿ͻ���Ϣ
	    	int  customerID = event.getCustomerDTO().getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("ֻ�������û�����ִ�и��������");
	    	}
	    	//ȡ���˻���Ϣ
	    	int accountID=event.getAccountDTO().getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("��ѡ����˻���δ���˵���");
	    	}
			if(!BusinessUtility.isNormalAccount(customerID,0)){
				throw new ServiceException("��ǰ�ͻ��з�����״̬�ʻ���");
			}
			if(!BusinessUtility.isNormalCSI(customerID)){
				throw new ServiceException("����δ��ɵ�������");
			}
			if(!BusinessUtility.isNormalCustomerProblem(customerID,0)){
				throw new ServiceException("����δ��ɵı��޵���");
			}
			if(!BusinessUtility.isNormalCustomerComplain(customerID)){
				throw new ServiceException("����δ��ɵ�Ͷ�ߵ���");
			}
			if(!BusinessUtility.isNormalServiceAccount(customerID,0)){
				throw new ServiceException("����״̬Ϊ��ʼ/ϵͳ��ͣ��ҵ���ʻ���");
			}
			if(!BusinessUtility.isNormalCustomerProduct(customerID,0)){
				throw new ServiceException("����״̬Ϊ��ʼ/ϵͳ��ͣ�Ŀͻ���Ʒ��");
			}
			if(EJBEvent.MOVETODIFFERENTPLACE==event.getActionType()||
				EJBEvent.TRANSFERTODIFFERENTPLACE==event.getActionType()||
				EJBEvent.TRANSFERTOSAMEPLACE==event.getActionType()){
					//���ͻ�����������û��Ƿ�һ�²�һ�������Ƿ��ظ�
					checkEixtMultCustSerialNoForExistCust(customerID,event.getCustomerDTO().getCustomerSerialNo());
			}
	    	Collection serviceAccountCol=BusinessUtility.getCurrentServiceAccountByCustomerID(customerID,"Status='N'");
	    	//ҵ���˻��µĲ�Ʒid
	    	if(serviceAccountCol!=null && !serviceAccountCol.isEmpty()){
	    		Iterator currentServiceAccountIter=serviceAccountCol.iterator();
	    		while(currentServiceAccountIter.hasNext()){
	    			int serviceAccountID=((Integer)currentServiceAccountIter.next()).intValue();
	    			Collection psidList=null;
			    	switch(event.getActionType()){
			    		//�ͻ�Ǩ��
						case EJBEvent.MOVETODIFFERENTPLACE:
							
							//ȡ��ҵ���˻��������еĲ�Ʒ
					    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);	
							iteratorCheck(psidList, 6);
							break;
                        // �ͻ���ع���	
						case EJBEvent.TRANSFERTODIFFERENTPLACE:
							//ȡ��ҵ���˻��������еĲ�Ʒ
					    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);
					    	iteratorCheck(psidList,7);
							break;
                        // �ͻ�ԭ�ع���
						case EJBEvent.TRANSFERTOSAMEPLACE:
							//ȡ��ҵ���˻��������еĲ�Ʒ
					    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);
					    	iteratorCheck(psidList,7);
							break;
				    	//�ͻ�����
			    		case EJBEvent.CLOSE_CUSTOMER:
			    			//ȡ��ҵ���˻��������еĲ�Ʒ
					    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);
					    	iteratorCheck(psidList,8);
			    			break;
			    		//�ͻ��˻�
			    		case EJBEvent.WITHDRAW:
			    			//ȡ��ҵ���˻��������еĲ�Ʒ
					    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);
					    	iteratorCheck(psidList,8);	
			    			break;
			    	}
	    		}
	    	}
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����ͻ��ļ���ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����ͻ��ļ�����Ҳ��������Ϣ");
			
		}
	}
	/**
	 * ����ҵ���˻��Ĳ���
	 * @param event
	 * @throws ServiceException
	 */
	public void checkOperationServiceAccount(ServiceAccountEJBEvent event)throws ServiceException{
		try {
			//ȡ���¿ͻ���Ϣ
	    	int  customerID = event.getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("ֻ�������û�����ִ�и��������");
	    	}
	    	//ȡ���˻���Ϣ
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("��ѡ����˻���δ���˵���");
	    	}
	    	//ȡ��ҵ���˻���Ϣ
	    	int serviceAccountID=event.getServiceAccountID();
	    	ServiceAccountHome serviceAccountHome=HomeLocater.getServiceAccountHome();
	    	ServiceAccount serviceAccount=null;
	    	//ҵ���˻��µĲ�Ʒid
	    	Collection psidList=null;
	    	
			switch(event.getActionType()){
			    //��ͣҵ���ʻ�
				case EJBEvent.SUSPEND:
			    	serviceAccount=serviceAccountHome.findByPrimaryKey(new Integer(serviceAccountID));
			    	if(!CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL.equals(serviceAccount.getStatus())){
			    		throw new ServiceException("ֻ������ҵ���˻�����ִ�и��������");
			    	}
					//ȡ��ҵ���˻��������е������Ʒ
			    	psidList=BusinessUtility.getSoftwareProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);	
					iteratorCheck(psidList,1);
					break;
		    	//��ҵ���ʻ�/Ԥ��ҵ���ʻ�/ʵ��ҵ���˻�
				case EJBEvent.CLOSE:
				case EJBEvent.BEFOREHAND_CLOSE:
				case EJBEvent.REAL_CLOSE:
					if(!BusinessUtility.isNormalCustomerProblem(customerID,event.getServiceAccountID())){
						throw new ServiceException("����δ��ɵı��޵���");
					}
					if(!BusinessUtility.isNormalServiceAccount(customerID,event.getServiceAccountID())){
						throw new ServiceException("��ҵ���ʻ���״̬Ϊ��ʼ/ϵͳ��ͣ��");
					}
                    //ȡ��ҵ���˻��������е������Ʒ
			    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);
			    	iteratorCheck(psidList,5);
					break;
		    	//�ָ�ҵ���ʻ�
				case EJBEvent.RESUME:	
			    	serviceAccount=serviceAccountHome.findByPrimaryKey(new Integer(serviceAccountID));
			    	if(!CommonConstDefinition.SERVICEACCOUNT_STATUS_REQUESTSUSPEND.equals(serviceAccount.getStatus())){
			    		throw new ServiceException("ֻ��������ͣ��ҵ���˻�����ִ�и��������");
			    	}
			    	
				    Collection colPsId =event.getColPsid();
				    if (colPsId==null ||colPsId.isEmpty()){
				    	throw new ServiceException("û��ѡ��Ҫ�ָ��Ĳ�Ʒ��");
				    	
				    }
				    iteratorCheck(colPsId,3);
				    //���²�����������Ʒ����������Ƿ�������Ҫ�ָ��Ĳ�Ʒ������δ�ָ��Ĳ�Ʒ
				    Collection haveExitProductCol=BusinessUtility.getPsIDListByServiceAccount(serviceAccountID," STATUS<>'"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"'");
				    //δ�ָ��Ĳ�Ʒ����
				    Collection unResumeProductCol=null;
				    if(haveExitProductCol!=null && !haveExitProductCol.isEmpty()){
				    	unResumeProductCol=new ArrayList();
				    	Iterator curIter=haveExitProductCol.iterator();
				    	while(curIter.hasNext()){
				    		Integer custPsId=(Integer)curIter.next();
				    		if(!colPsId.contains(custPsId)){
				    			unResumeProductCol.add(custPsId);
				    		}
				    	}
				    }
				    checkProductOperationDependency(unResumeProductCol,serviceAccountID);
					break;
				//����Ȩ
				case EJBEvent.RESEND_EMM_BY_SERVICE_ACCOUNT:	
					break;
	    		//ҵ���ʻ�����
				case EJBEvent.TRANSFER:
					//ȡ��ҵ���˻��������е������Ʒ
					if(event.getCustomerID()!=0&&(event.getCustomerID()==event.getNewCustomerID())){
						throw new ServiceException("�����Լ��������Լ���");
					}
			    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);
					if(!BusinessUtility.isNormalCustomerProblem(customerID,event.getServiceAccountID())){
						throw new ServiceException("����δ��ɵı��޵���");
					}
					if(!BusinessUtility.isNormalServiceAccount(customerID,event.getServiceAccountID())){
						throw new ServiceException("��ҵ���ʻ���״̬Ϊ��ʼ/ϵͳ��ͣ��");
					}
					if(!BusinessUtility.isNormalCustomerProduct(customerID,event.getServiceAccountID())){
						throw new ServiceException("����״̬Ϊ��ʼ/ϵͳ��ͣ�Ŀͻ���Ʒ��");
					}
					iteratorCheck(psidList,4);
					break;
		    	//����ƥ��
				case EJBEvent.PARTNERSHIP:
					break;
	    		//���������
	    		case EJBEvent.CANCEL_PARTNERSHIP:
	    			break;

			}
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ���ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ�����Ҳ��������Ϣ");
			
		}
	}
	
	/**
	 * ���ԶԲ�Ʒ�Ĳ���
	 * @param event
	 * @throws ServiceException
	 */
	public void checkOperationCustomerProduct(CustomerProductEJBEvent event)throws ServiceException{
		try {
			//ȡ���¿ͻ���Ϣ
	    	int  customerID = event.getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(EJBEvent.DEVICESWAP==event.getActionType()){
	    		if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())&&
	    			!CommonConstDefinition.CUSTOMER_STATUS_POTENTIAL.equals(customer.getStatus())){
		    		throw new ServiceException("ֻ��������Ǳ���û�����ִ�и��������");
		    	}
	    		//ȡ��ҵ���˻���Ϣ
		    	ServiceAccountHome serviceAccountHome=HomeLocater.getServiceAccountHome();
		    	ServiceAccount serviceAccount =serviceAccountHome.findByPrimaryKey(new Integer(event.getSaID() ));
		    	if(!CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL.equals(serviceAccount.getStatus())&&
		    		!CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT.equals(serviceAccount.getStatus())){
		    		throw new ServiceException("ֻ�������ͳ�ʼ��ҵ���˻�����ִ�и��������");
		    	}
	    	}else if(EJBEvent.MOVE==event.getActionType()){
	    		if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
		    		throw new ServiceException("ֻ�������û�����ִ�и��������");
		    	}
		    	//ȡ��ҵ���˻���Ϣ
		    	ServiceAccountHome serviceAccountHome=HomeLocater.getServiceAccountHome();
		    	ServiceAccount serviceAccount =serviceAccountHome.findByPrimaryKey(new Integer(event.getSaID() ));
		    	if(!CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL.equals(serviceAccount.getStatus())&&
			    		!CommonConstDefinition.SERVICEACCOUNT_STATUS_BEFOREHANDCLOSE.equals(serviceAccount.getStatus())
		    			){
		    		throw new ServiceException("ֻ����������Ԥ�˵�ҵ���˻�����ִ�и��������");
		    	}
	    	}else{
		    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
		    		throw new ServiceException("ֻ�������û�����ִ�и��������");
		    	}
		    	//ȡ��ҵ���˻���Ϣ
		    	ServiceAccountHome serviceAccountHome=HomeLocater.getServiceAccountHome();
		    	ServiceAccount serviceAccount =serviceAccountHome.findByPrimaryKey(new Integer(event.getSaID() ));
		    	if(!CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL.equals(serviceAccount.getStatus())){
		    		throw new ServiceException("ֻ������ҵ���˻�����ִ�и��������");
		    	}
	    	}
	    	
	    	//ȡ���˻���Ϣ
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("��ѡ����˻���δ���˵���");
	    	}
	    	//ҵ���˻��µĲ�Ʒid
	    	Collection psidList=event.getColPsid();
			switch(event.getActionType()){
    		    //�ͻ���Ʒ��ͣ
			    case EJBEvent.SUSPEND:
					iteratorCheck(psidList,1);
		    	    //�����������Ʒ��������
		    	    checkProductOperationDependency(psidList,event.getSaID() );
					break;
		    	//�ͻ�ȡ����Ʒ
				case EJBEvent.CANCEL:
					iteratorCheck(psidList,2);
		    	    //�����������Ʒ��������
		    	    checkProductOperationDependency(psidList,event.getSaID() );
					break;
				//�ͻ�Ǩ�Ʋ�Ʒ
				case EJBEvent.MOVE:
					iteratorCheck(psidList,2);
					CustomerProductHome customerProductHome=HomeLocater.getCustomerProductHome();
					Collection currentAllProductCol=new ArrayList();
					currentAllProductCol.addAll(BusinessUtility.getProductIDListByServiceAccount(event.getTargetSaID()," status <> '" + CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"' "));
					Iterator it=psidList.iterator();
					while(it.hasNext()){
						Integer currentCustProductID=(Integer)it.next();
						CustomerProduct customerProduct=customerProductHome.findByPrimaryKey(currentCustProductID);
						currentAllProductCol.add(new Integer(customerProduct.getProductID()));
					}
					//����Ŀ���û��Ĳ�Ʒ�Ƿ��ظ�
					checkProductTautology(currentAllProductCol);
					//�����������Ʒ��������
		    	    checkProductOperationDependency(psidList,event.getTargetSaID() );
		    	    
					break;
                //�ͻ���Ʒ�ָ�
				case EJBEvent.RESUME:	
					iteratorCheck(psidList,3);
					break;
		    	//�ͻ���Ʒ����
	    		case EJBEvent.UPGRADE:
	    			checkProductUpdownGrade(event);
	    			break;
                //�ͻ���Ʒ����
	    		case EJBEvent.DOWNGRADE:
	    			checkProductUpdownGrade(event);
	    			break;
	    		//�豸����
	    		case EJBEvent.DEVICESWAP:
	    			//����豸�Ƿ�ƥ��
	    			TerminalDeviceDTO terminalDevice=BusinessUtility.getDeviceBySerialNo(event.getNewSeralNo());
	    			
	    			//wang fang 2008.05.15 begin
	    			/****************************************************/
	    			TerminalDeviceDTO oldTerminalDevice=BusinessUtility.getDeviceBySerialNo(event.getOldSerialNo());
	    			checkDeviceMatchToCsiTypeAndReason(event.getCsiDto(),terminalDevice,oldTerminalDevice);
	    			/****************************************************/
	    			//wang fang 2008.05.15 end
	    			
	    			//����豸�����Ϣ�Ƿ���ȷ 2007-12-18
	    			checkDeviceMatch(event.getNewSeralNo());
	    			if(event.isDoPost()){
	    				checkDeviceSwap(event.getCustomerProductDTO());
	    			}
	    			
	    			//���˫�����������CM add by david.Yang 2009-07-29
	    			if (terminalDevice.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_CM)
	    					&& terminalDevice.getMatchFlag().equals("Y")){
	    				TerminalDeviceDTO dto =BusinessUtility.getDeviceByDeviceID(terminalDevice.getMatchDeviceID());
	    				throw new ServiceException("��CM�豸�Ѿ���"+dto.getSerialNo()+"���,������ѡ��!");
	    			}
	    			
	    			break;
			}
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("������Ʒ�ļ���ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("������Ʒ�ļ�����Ҳ��������Ϣ");
			
		}
	}
	
	
	private void checkProductUpdownGrade(CustomerProductEJBEvent event) throws ServiceException{
		int oldProductID = ((Integer)serviceContext.get("OLDPRODUCTID")).intValue(); 
		String prodDependType ="";
		if (event.getActionType() ==CustomerProductEJBEvent.UPGRADE){
			prodDependType ="A";
		} else if (event.getActionType() ==CustomerProductEJBEvent.DOWNGRADE){
			prodDependType ="F";
		}
		Collection productDependList = BusinessUtility.getProdDependByProdIdAndType(oldProductID,prodDependType);
		
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"productDependList========"+productDependList); 
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"event.getProductID()================"+event.getProductID());
		if (productDependList ==null || productDependList.size() ==0){
			throw new ServiceException("��Ʒ�������Ĺ�ϵ�����ݿ��в����ҵ���");
		}else{
			Iterator productDependIter =productDependList.iterator();
			boolean updownGradeFlag =false;
			while (productDependIter.hasNext()){
				ProductDependencyDTO dto =(ProductDependencyDTO)productDependIter.next();
				String referProductIDList =dto.getReferProductIDList();
				String[] referProductIDs =referProductIDList.split(",");
				for (int i=0; i<referProductIDs.length; i++){
					if (Integer.parseInt(referProductIDs[i]) ==event.getProductID()){
						updownGradeFlag =true;
						break;
					}
				}
			}
			if (!updownGradeFlag){
				throw new ServiceException("��ѡ��Ʒ���������Ʒ�����������ã�");
			}
	    } 
		
	    Collection  productList = BusinessUtility.getCustProductIDListByCustInfo(0,0,event.getSaID());
	    if (productList.contains(new Integer(event.getProductID()))){
		   throw new ServiceException("ҵ���ʻ����Ѿ��������������Ĳ�Ʒ��");
	    }
	
	}
	
	/**
	 * ����豸����
	 * @param dto
	 * @throws ServiceException
	 */
	private void checkDeviceSwap(CustomerProductDTO dto)throws ServiceException{
		try {
			//���ҿͻ���Ʒ
			CustomerProductHome customerProductHome=HomeLocater.getCustomerProductHome();
			CustomerProduct customerProduct=customerProductHome.findByPrimaryKey(new Integer(dto.getPsID()));
			//ȡ���ϲ�Ʒ���豸id
			int oldProductID=customerProduct.getProductID();
			//ȡ���²�Ʒ�������豸ID
			int newProductID=dto.getProductID();
			int newDeviceID=dto.getDeviceID();
			//ȡ�����豸���豸����
			Collection newDeviceModelCol=BusinessUtility.getDeviceModelByProductID(newProductID);
			//ȡ�����豸��Ϣ
			TerminalDeviceHome terminalDeviceHome=HomeLocater.getTerminalDeviceHome();
			TerminalDevice newTerminalDevice=terminalDeviceHome.findByPrimaryKey(new Integer(newDeviceID));
			String newInStoreDeviceModel=newTerminalDevice.getDeviceModel();
			
			//�����CM����,����Ӷ��ձ�����STB��Ӧ��Model add by david.Yang
			if (newTerminalDevice.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_CM)){
				newInStoreDeviceModel =BusinessUtility.getStbModelByCmModel(newInStoreDeviceModel);
			}
			
			if(!newDeviceModelCol.contains(newInStoreDeviceModel)){
				throw new ServiceException("��ѡ����豸���豸�ͺŲ�ƥ�䡣");
			}
			//���ֻ�Ǹ����豸�����˳�����������˲�Ʒ��������
			if(oldProductID==newProductID){
				return;
			}
			//ȡ�ÿͻ���Ӧ�Ĳ�Ʒ,�����²�Ʒ�滻�ϲ�Ʒ
			ArrayList productIDList=BusinessUtility.getProductIDListByServiceAccount(dto.getServiceAccountID()," STATUS<>'"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"'");
			int  productIDIndex=productIDList.indexOf(new Integer(oldProductID));
			productIDList.set(productIDIndex,new  Integer(newProductID));
			
			//����Ʒ֮�������
			checkProductDependency(productIDList);
			
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�豸����ʱ����ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�豸����ʱ�Ҳ��������Ϣ");
			
		}
	}
	
	/**
	 * �����˻�ʱ������ҵ����
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void checkCreateAccount(AccountEJBEvent inEvent)throws ServiceException{
		try {
			CustomerHome customerHome=HomeLocater.getCustomerHome();
			Customer customer=customerHome.findByPrimaryKey(new Integer(inEvent.getCustomerID()));
			if (!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("ֻ�������û�����ִ�и��������");
	    	}
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����˻�ʱ����ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����˻�ʱ�Ҳ��������Ϣ");
			
		}
	}
	/**
	 * ȡ�ÿͻ�����
	 * @param custId
	 * @throws ServiceException
	 */
	public String getCustStyle(int custId)throws ServiceException{
		 
		String custStyle=null;
		try {
			
			CustomerHome custHome=HomeLocater.getCustomerHome();
			Customer cust = custHome.findByPrimaryKey(new Integer(custId));
			custStyle=cust.getCustomerStyle();
	    	}
		    catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		} catch (FinderException e) {
			e.printStackTrace();
		}
		if(custStyle==null)
			custStyle="";
		return custStyle;
	}
	/**
	 * ����ʻ�����ʱ����Ϣ
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void checkAdjustInfo(AdjustEJBEvent inEvent)throws ServiceException{
		try {
			//�˻���Ϣ
			int accountID=  inEvent.getAccountID();
			AccountHome accountHome=HomeLocater.getAccountHome();
			Account account=accountHome.findByPrimaryKey(new Integer(accountID));

			CustomerHome customerHome=HomeLocater.getCustomerHome();
			Customer customer=customerHome.findByPrimaryKey(new Integer(account.getCustomerID()));
			if (!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("ֻ�������û�����ִ�и��������");
	    	}
			switch(inEvent.getActionType()){
				//����
				case EJBEvent.ADJUSTMENT:
					if (CommonConstDefinition.ACCOUNT_STATUS_CLOSE.equals(account.getStatus())){
			    		throw new ServiceException("ֻ�зǹر��˻�������ִ�и��������");
			    	}
				    break;
			}
			
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����˻�ʱ����ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����˻�ʱ�Ҳ��������Ϣ");
			
		}
		
		
		
	}
	/**
	 * ��������˻����в����ļ��
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void checkOperationAccount(AccountEJBEvent inEvent)throws ServiceException{
		try {
			//�˻���Ϣ
			AccountDTO accountDTO=  inEvent.getAccountDTO();
			AccountHome accountHome=HomeLocater.getAccountHome();
			Account account=accountHome.findByPrimaryKey(new Integer(accountDTO.getAccountID()));

			CustomerHome customerHome=HomeLocater.getCustomerHome();
			Customer customer=customerHome.findByPrimaryKey(new Integer(account.getCustomerID()));
			switch(inEvent.getActionType()){
				//ȡ���˻�	 
				case EJBEvent.CLOSECUSTOMERACCOUNT:
					if (!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
			    		throw new ServiceException("ֻ�������û�����ִ�и��������");
			    	}
					if (BusinessUtility.existUnpaidInvoice(accountDTO.getAccountID())){
			    		throw new ServiceException("��ѡ����˻���δ���˵���");
			    	}
					if (!CommonConstDefinition.ACCOUNT_STATUS_NORMAL.equals(account.getStatus())){
			    		throw new ServiceException("ֻ�������˻�����ִ�и��������");
			    	}
	    			if(!BusinessUtility.isExitOneMoreAccount(account.getCustomerID()))
	    				throw new ServiceException("���ʻ��ǿͻ������һ���ʻ���������ȡ����");
					Collection custproductCol=BusinessUtility.getCustProductByAccountID(accountDTO.getAccountID());
					if(custproductCol!=null && !custproductCol.isEmpty()){
						throw new ServiceException("���˻��ж�Ӧ�Ŀͻ���Ʒ���������������");
					}
					break;
				//Ԥ����
				case EJBEvent.PRESAVE:
					if (BusinessUtility.existUnpaidInvoice(accountDTO.getAccountID())){
			    		throw new ServiceException("��ѡ����˻���δ���˵���");
			    	}
					if (!CommonConstDefinition.ACCOUNT_STATUS_NORMAL.equals(account.getStatus())){
			    		throw new ServiceException("ֻ�������˻�����ִ�и��������");
			    	}
					break;
			}
			
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����˻�ʱ����ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����˻�ʱ�Ҳ��������Ϣ");
			
		}
		
	}
	/**
	 * ����ҵ���˻��ļ��
	 * 
	 * @throws ServiceException
	 */
	public void checkCreateServiceAccount(ServiceAccountEJBEvent event)throws ServiceException{
		try {
			//ȡ�ÿͻ�����Ĳ�Ʒ����Ϣ
			Collection csiPackageArray = event.getCsiPackageArray();
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
			//ȡ�ÿͻ�ѡ����Ż���Ϣ
			Collection csiCampaignArray = event.getCsiCampaignArray();
	    	//ȡ���¿ͻ���Ϣ
	    	int  customerID = event.getCustomerID();
	    	
	    	// ��������Э���û����ŵ꿪��
	    	if (event.getCsiDto().getType().equals(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO)){
	    		Map protocolMap =BusinessUtility.getProtocolDTOColByCustID(event.getCsiDto().getCustomerID());
	    		if (protocolMap !=null && !protocolMap.isEmpty()){
	    			throw new ServiceException("Э���û��������ŵ���������ʹ�������ŵ��������ܣ�");
	    		}
	    	}
	    	
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("ֻ�������û����������û���");
	    	}
	    	//ȡ���˻���Ϣ
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("��ѡ����˻���δ���˵���");
	    	}
	    	//�ͻ���ַ��Ϣ
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());
			//Ӳ���豸��Ϣ
			//Hashtable terminalDeviceTable=event.getDeviceTable();
	    	HashMap terminalDeviceTable=event.getDeviceTable();
			//����Ƿ������Ҫ����ҵ���˻��Ĳ�Ʒ
			if(!checkExitCreateServiceAccountProduct(csiPackageArray)){
				throw new ServiceException("ѡ���Ʒ���в��������Դ���ҵ���ʻ��Ĳ�Ʒ��");
			}
			
			//ֻ���ڷ��Ź���ʱ�����Ҫ����Ʒ�� ���Żݣ���Ʒ������֮��Ĺ�ϵ
			//����ײͺ��Żݴ�������
			CommonBusinessParamDTO paramDTO=new CommonBusinessParamDTO(customer.getCustomerType(),customer.getOpenSourceType(),event.getCsiDto().getType(),event.getOperatorID());
			checkPackageCampaign(csiPackageArray,csiCampaignArray,custAddrDto,true,paramDTO);
			
			//ȡ�ò�Ʒ����Ӧ�����в�Ʒ��Ϣ
			Collection currentAllProductCol=new ArrayList();
			Iterator currentPackageIter=csiPackageArray.iterator();

			while(currentPackageIter.hasNext()){
				Integer currentPackageID=(Integer)currentPackageIter.next();
				currentAllProductCol.addAll(BusinessUtility.getProductIDsByPackageID(currentPackageID.intValue())) ;
			}
			//����Ʒ֮���������
			checkProductDependency(currentAllProductCol);
			//���ͻ���Ʒ���Ż���Ϣ���˷���
			if(CustomerProductEJBEvent.CHECK_PRODUCTPG_CAMPAINPG==event.getActionType()){
				return;
			}
			System.out.println("++event.getActionType()="+event.getActionType());
            //���ͻ���Ʒ���Ż���Ϣ���˷���  ����ԤԼ����
			if(CustomerProductEJBEvent.CHECK_BOOKINGUSER_PRODUCTPG_CAMPAINPG==event.getActionType()
					|| CustomerProductEJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER_FORBOOKING ==event.getActionType()|| 
					CustomerProductEJBEvent.CUSTOMER_BOOKINGUSER_UPDATE_SUBSCRIBER ==event.getActionType()){
				return;
			}
			
			//ȡ�ò�Ʒ������Ϣ
			Map productPropertyMap = event.getProductPropertyMap();
			LogUtility.log(clazz, LogLevel.DEBUG, "checkCreateServiceAccount ȡ�ò�Ʒ������Ϣ"+productPropertyMap);
			//����Ʒ����30002,�û���Ψһ��У�� 2008-03-25 hjp
			if(productPropertyMap!=null){
				Iterator itProductProperty = productPropertyMap.keySet().iterator();
				while(itProductProperty.hasNext()){
					String productID = (String)itProductProperty.next();
					Map tempMap = (Map)productPropertyMap.get(productID);
					String strPropertyValue = (String)tempMap.get("30002");
					ProductPropertyDTO prodto = BusinessUtility.getProductPropertyDTOByProductIDAndCode(Integer.parseInt(productID),"30002");
					if(BusinessUtility.getISExistCpConfigedPropertyByPropertyCodeAndValue(productID,"30002",strPropertyValue))
						throw new ServiceException(prodto.getPropertyName()+"�ظ�");
				}
			}
			
			//�����ѡ��Ӳ���豸����ѡӲ���豸�Ͳ�Ʒ��ӳ���ϵ�Ƿ���ȷ
			checkSelectTerminalDevice(event.getCsiDto(), terminalDeviceTable,currentAllProductCol,true);
			//����豸�����Ϣ�Ƿ���ȷ 2007-12-18
			checkDeviceMatch(event.getDeviceTable());
			//���Ӳ����Ʒ��Ϣʱ���˷���
			if(CustomerProductEJBEvent.CHECK_HARDPRODUCTINFO==event.getActionType()){
				return;
			}
			
			//��������Ϣʱ���˷���
			if(CustomerProductEJBEvent.CHECK_FREEINFO==event.getActionType()){
				return;
			}

		
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ���ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ�����Ҳ��������Ϣ");
			
		}
		
	}
/**
 * �ظ����򴴽�ҵ���ʻ� ���,
 * @param event
 * @throws ServiceException
 */
	public void checkBatchCreateServiceAccount(ServiceAccountEJBEvent event)throws ServiceException{
		try {
			//ȡ�ÿͻ�����Ĳ�Ʒ����Ϣ
			Collection csiPackageArray = event.getCsiPackageArray();
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
			//ȡ�ÿͻ�ѡ����Ż���Ϣ
			Collection csiCampaignArray = event.getCsiCampaignArray();
	    	//ȡ���¿ͻ���Ϣ
	    	int  customerID = event.getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("ֻ�������û����������û���");
	    	}
	    	//ȡ���˻���Ϣ
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("��ѡ����˻���δ���˵���");
	    	}
	    	
	    	ArrayList buyProductList=event.getBuyProductList();
	    	//add by david.Yang.�����Э��ͻ�������ֻ������Э��Ĳ�Ʒ�� begin
			Map buyInfo =new HashMap();		
			for (Iterator buyIt = buyProductList.iterator(); buyIt.hasNext();) {
				buyInfo = (Map) buyIt.next();
			}
			ArrayList buyPackageList = (ArrayList) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_MPACKAGE);
			Map  protocolDtoMap =BusinessUtility.getProtocolDTOColByCustID(customerID);
			if (protocolDtoMap !=null && protocolDtoMap.size() >0){
				Iterator buyPackageItr  = buyPackageList.iterator();
				while(buyPackageItr.hasNext()){
					Integer packateID=(Integer)buyPackageItr.next();
					if (!protocolDtoMap.keySet().contains(packateID)){
						ProductPackageDTO dto =BusinessUtility.getProductPackageDTOByID(packateID.intValue());
						throw new ServiceException(dto.getPackageName()+"����Э�麭�Ƿ�Χ�ڣ��������Ա��ϵ��");
					}
				}
			}
			//add by david.Yang.�����Э��ͻ�������ֻ������Э��Ĳ�Ʒ�� end
	    	
	    	//�ͻ���ַ��Ϣ
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());
	    	
			CommonBusinessParamDTO paramDTO=new CommonBusinessParamDTO(customer.getCustomerType(),customer.getOpenSourceType(),event.getCsiDto().getType(),event.getOperatorID());
			
			checkBatchBuyProductInfo(buyProductList, event.getCsiDto(), paramDTO, custAddrDto);
		
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ���ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ�����Ҳ��������Ϣ");
			
		}
		
	}
	/**
	 * �ظ������Ʒ���,
	 * @param buyProductList ��Ʒ�б�,
	 * @param csiDTO ����
	 * @param paramDTO
	 * @param custAddrDto
	 * @throws ServiceException
	 */
	public void checkBatchBuyProductInfo(
			ArrayList buyProductList,
			CustServiceInteractionDTO csiDTO, CommonBusinessParamDTO paramDTO,
			AddressDTO custAddrDto) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "�ظ�����ʼ���");
		if(buyProductList==null||buyProductList.isEmpty()){
			return ;
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "������Ϣ",buyProductList);

		Collection csiProductList=new ArrayList();
		boolean isInitCsiProductInfo=true;
		Collection productDepent= BusinessUtility.getAllProductDepentDefineList();

		for (Iterator buyIt = buyProductList.iterator(); buyIt.hasNext();) {
			Map buyInfo = (Map) buyIt.next();
			Integer buyNum = (Integer) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_BUYNUM);
			Integer buyIndex = (Integer) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_BUYINDEX);
			ArrayList buyPackageList = (ArrayList) buyInfo
					.get(OpenAccountGeneralEJBEvent.KEY_MPACKAGE);
			ArrayList buyCampaignList = (ArrayList) buyInfo
					.get(OpenAccountGeneralEJBEvent.KEY_CAMPAIGN);
			Map buyDeviceMap = (Map) buyInfo
			.get(OpenAccountGeneralEJBEvent.KEY_DEVICES);
			Map propertysMap=(Map) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_PROPERTYS);
			
			LogUtility.log(clazz, LogLevel.DEBUG, buyIndex+"�鵱ǰ��Ʒ��:",buyPackageList);

			// ����ײͺ��Żݴ�������

			checkPackageCampaign(buyPackageList, buyCampaignList,
					custAddrDto, true, paramDTO);

			// ȡ�ò�Ʒ����Ӧ�����в�Ʒ��Ϣ
			Collection currentAllProductCol = new ArrayList();
			Iterator currentPackageIter = buyPackageList.iterator();

			while (currentPackageIter.hasNext()) {
				Integer currentPackageID = (Integer) currentPackageIter
						.next();
				currentAllProductCol.addAll(BusinessUtility
						.getProductIDsByPackageID(currentPackageID
								.intValue()));
			}
			LogUtility.log(clazz, LogLevel.DEBUG, buyIndex+"�鵱ǰ���в�Ʒ:",buyPackageList);

			if (!checkExitCreateServiceAccountProduct(buyPackageList)) {
				throw new ServiceException("ѡ���Ʒ���в��������Դ���ҵ���ʻ��Ĳ�Ʒ��");
			}

			// ����Ʒ֮���������
			// �Ѳ�Ʒ��ҵ���ʻ��ֿ������ÿ��ҵ���ʻ��µĲ�Ʒ�Ƿ�����������ϵ,modify by jason 2007-3-9
			ServiceContext saContext = new ServiceContext();
			saContext.put(Service.OPERATIOR_ID, new Integer(paramDTO.getOperatorID()));
			ServiceAccountService saService = new ServiceAccountService(
					saContext,BusinessUtility.getAllProductDepentDefineList());
			Map productIDOfSAMap = saService
					.productSplitToSA(currentAllProductCol);
			if (productIDOfSAMap == null || productIDOfSAMap.isEmpty())
				throw new ServiceException("��Ʒ�������������ܴ���ҵ���ʻ���");
			Iterator itProductIDOfSA = productIDOfSAMap.keySet().iterator();
			while (itProductIDOfSA.hasNext()) {
				Integer productIDOfSA = (Integer) itProductIDOfSA.next();
				Collection productIDToOneSAList = (Collection) productIDOfSAMap
						.get(productIDOfSA);
				if (productIDToOneSAList == null)
					productIDToOneSAList = new ArrayList();
				productIDToOneSAList.add(productIDOfSA);
				checkProductDependency(productIDToOneSAList,productDepent);
			}

			// �����ѡ��Ӳ���豸����ѡӲ���豸�Ͳ�Ʒ��ӳ���ϵ�Ƿ���ȷ
			if(buyDeviceMap!=null&&!buyDeviceMap.isEmpty()){
				LogUtility.log(clazz, LogLevel.DEBUG, buyIndex+"�����豸��ǰ��Ʒ:",currentAllProductCol);
				for(int di=1;di<=buyNum.intValue();di++){
					Integer sheafNo=new Integer(di);
					HashMap deviceMap=(HashMap)buyDeviceMap.get(sheafNo);
					LogUtility.log(clazz, LogLevel.DEBUG, buyIndex+"��,��"+(di)+"��,�����豸:",deviceMap);
					csiProductList.addAll(checkBatchSelectTerminalDevice(csiDTO, deviceMap,
							currentAllProductCol, isInitCsiProductInfo,buyIndex.intValue(),sheafNo.intValue()));
					//����豸���
					checkDeviceMatch(deviceMap);
				}
			}
			//ȡ�ò�Ʒ������Ϣ
			LogUtility.log(clazz, LogLevel.DEBUG, "checkBatchBuyProductInfo ȡ�ò�Ʒ������Ϣ"+propertysMap);
			//����Ʒ����,�û���Ψһ��У�� 2008-03-25 hjp
			if(propertysMap!=null&&!propertysMap.isEmpty()){
				Iterator ppValues=propertysMap.values().iterator();
				while(ppValues.hasNext()){
					Map ppMap=(Map) ppValues.next();
					Iterator itProductProperty = ppMap.keySet().iterator();
					while(itProductProperty.hasNext()){
						String productID = (String)itProductProperty.next();
						Map tempMap = (Map)ppMap.get(productID);
						String strPropertyValue = (String)tempMap.get("30002");
						if(BusinessUtility.getISExistCpConfigedPropertyByPropertyCodeAndValue(productID,"30002",strPropertyValue))
							//throw new ServiceException("�ͻ���Ʒ������,�û����ظ�");
							throw new ServiceException("�û���("+strPropertyValue+")�ظ�");
					}
				}
			}
		}

		if(isInitCsiProductInfo){
			//��ȡ�õ�Ӳ����Ʒ��Ӧ�Ĳ�Ʒ���豸��Ϣ���õ������Ļ�����
			serviceContext.put(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST,csiProductList);
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"�����Ƿ���ڶ�Ӧ�Ĳ�Ʒ���豸��Ϣ",csiProductList);
		}	
		LogUtility.log(clazz, LogLevel.DEBUG, "�ظ�����������");		
	}
	private Collection checkBatchSelectTerminalDevice(CustServiceInteractionDTO csiDTO,HashMap terminalDeviceTable,Collection productCol,boolean isInitCsiProductInfo,int groupNo,int sheafNo)throws ServiceException {
		Collection csiProdCol=checkBatchSelDeviceAndReturnCsiProdInfo(csiDTO,terminalDeviceTable,productCol,isInitCsiProductInfo,groupNo,sheafNo);
		if(isInitCsiProductInfo){
			//��ȡ�õ�Ӳ����Ʒ��Ӧ�Ĳ�Ʒ���豸��Ϣ���õ������Ļ�����
			serviceContext.put(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST,csiProdCol);
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"�����Ƿ���ڶ�Ӧ�Ĳ�Ʒ���豸��Ϣ",csiProdCol);
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkSelectTerminalDevice���Ӳ���豸����");
		return csiProdCol;
	}
	public Collection checkBatchSelDeviceAndReturnCsiProdInfo(CustServiceInteractionDTO csiDTO,HashMap terminalDeviceTable,Collection productCol,boolean isInitCsiProductInfo,int groupNo,int sheafNo)throws ServiceException {
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"����csiDTO����"+csiDTO);
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"����terminalDeviceTable����"+terminalDeviceTable);
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"����productCol����"+productCol);
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"����isInitCsiProductInfo����"+isInitCsiProductInfo);
		if(productCol==null || productCol.isEmpty()){
			throw new ServiceException("û�в�Ʒ��");
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkSelDeviceAndReturnCsiProdInfo�����ѡ��Ӳ���豸");
		//�½�һ�����������֤����ȷ���豸���ݣ��豸ID��
		Hashtable correctDeviceTable=null;	
		//�½�һ�������洢Ӳ����Ʒ������ص��豸��Ϣ
		Collection initedCsiProductInfoCol=new ArrayList();
		if(terminalDeviceTable!=null && !terminalDeviceTable.isEmpty() ){
			//ȡ������Ӳ���豸���������к�
			Set currentKeySet=terminalDeviceTable.keySet();
			correctDeviceTable=new Hashtable();
			String currntSerialNo=null;
			String currentDeviceClass=null;
			String currentDeviceModel=null;
			Iterator currentDeviceIter=currentKeySet.iterator();
			while(currentDeviceIter.hasNext()){
				//ȡ������Ӳ���豸���������к�
				currntSerialNo=(String)currentDeviceIter.next();
				//ȡ�ø�Ӳ���豸���кŶ�Ӧ���豸����
				currentDeviceClass=(String)terminalDeviceTable.get(currntSerialNo);
				//��ȡ�ն��豸�豸����Ϣ
				TerminalDeviceDTO terminalDevice=BusinessUtility.getDeviceBySerialNo(currntSerialNo);
				if(!terminalDevice.getDeviceClass().equals(currentDeviceClass))
					throw new ServiceException("���кţ�" + currntSerialNo + "����Ӧ�豸���豸���Ͳ���"+HelperCommonUtil.getNameByDeviceClass(currentDeviceClass));
				//�жϸ��豸��Ӧ���豸��״̬�Ƿ���ȷ
				if (!CommonConstDefinition.DEVICE_STATUS_WAITFORSELL.equals(terminalDevice.getStatus()))
					throw new ServiceException("��ѡ���"+HelperCommonUtil.getNameByDeviceClass(currentDeviceClass)
							+"("+currntSerialNo+")"+"��״̬Ϊ"+
								HelperCommonUtil.getCommonNameByCacheNameAndKey("SET_D_DEVICESTATUS",terminalDevice.getStatus())+
								",��ѡ��״̬Ϊ���۵��豸��");
				//ȡ����ѡ�豸���кŶ�Ӧ���豸�ͺ�
				currentDeviceModel=terminalDevice.getDeviceModel();
				//�����������������ͺ�����ԭ�����Ƿ���豸��;ƥ��
				checkDeviceMatchToCsiTypeAndReason(csiDTO,terminalDevice);
				//����һ���豸��Ϣ
				int currentDeviceID=terminalDevice.getDeviceID();
				//������ѡ����豸���кŶ�Ӧ���豸id���豸�ͺ�
				LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"����Ӳ�����豸���к�","currentDeviceID="+currentDeviceID+";currentDeviceModel="+currentDeviceModel);
				correctDeviceTable.put(new Integer(currentDeviceID),currentDeviceModel);
			}
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkSelDeviceAndReturnCsiProdInfo�����ѡ��Ӳ���豸����ѡӲ����Ʒ�Ƿ�ƥ��");
		//��������Ĳ�Ʒ����
		Iterator currentProductIter=productCol.iterator();
		//��������Ѿ������Ĳ�Ʒ��ID
		Collection currentDeviceIDCol=new ArrayList();
		while(currentProductIter.hasNext()){
			Integer currentProductID=(Integer)currentProductIter.next();	
			ProductDTO product=BusinessUtility.getProductDTOByProductID(currentProductID.intValue());
			if(BusinessUtility.getDeviceModelDTOByProductID(product.getProductID()).isEmpty())
				continue;
			//�����Ӳ����Ʒ����Ҫ�ж���ѡ��Ӳ����Ʒ���豸�����к��Ƿ��Ӧ
			if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(product.getProductClass())){
				//�����ѡ��Ӳ����Ʒ���Ƿ���ڶ�Ӧ���豸
				boolean isExit=false;
				//ȡ�������豸����Ʒ��ӳ�����
				Collection deviceToProductCol=BusinessUtility.getDevToProdByProductId(currentProductID.intValue());
				if(deviceToProductCol==null ||deviceToProductCol.isEmpty()){
					throw new ServiceException("û����Ӧ���豸��Ϣ��");
				}
				Iterator deviceToProductIter=deviceToProductCol.iterator();
				//ȡ�øò�Ʒ��Ӧ�������豸�ͺ�
				Collection deviceModelCol=new ArrayList();
				while(deviceToProductIter.hasNext()){
					deviceModelCol.add(((DeviceMatchToProductDTO)deviceToProductIter.next()).getDeviceModel());
				}
				//ȡ�����豸���кŶ�Ӧ���豸��������õ�����
				//��11-23���
				if(correctDeviceTable==null||correctDeviceTable.isEmpty()){
					throw new ServiceException("û����Ч���豸��");
				}
				Iterator checkDeviceIter=correctDeviceTable.keySet().iterator();
				Integer currentDeviceID=null;
				//��������Ѿ��Ͳ�Ʒ������Ӳ���豸��ID
				Collection haveLinkToProductDeviceList=new ArrayList();
				while(checkDeviceIter.hasNext()){
					//ȡ���豸id
					currentDeviceID=(Integer)checkDeviceIter.next();
					//��������豸id��֤��ѡ����ͬ���豸�ͺŵ�Ӳ��
					if(currentDeviceIDCol.contains(currentDeviceID)){
						throw new ServiceException("��ѡ�豸�г������ظ���Ӳ����");
					}
					//����Ƿ��豸�ͺźͲ�Ʒ���ͺ�ƥ��
					if(deviceModelCol.contains((String)correctDeviceTable.get(currentDeviceID))){
						//���úͲ�Ʒ�������豸��ID
						if(!haveLinkToProductDeviceList.contains(currentDeviceID)){
							//�Ƿ���Ҫ����Ӳ����Ʒ��CsiProductInfo��Ϣ
							if(isInitCsiProductInfo){
								//��ʼ���ʹ�������Ĳ�Ʒ��Ӧ�������漰�Ŀͻ���Ʒ�����Ϣ
								CsiCustProductInfoDTO csiCustProductInfoDTO=new CsiCustProductInfoDTO();
								//���ò�Ʒid
								csiCustProductInfoDTO.setProductID(currentProductID.intValue());
								//���ò�Ʒ��Ӧ��Ӳ���豸��id
								csiCustProductInfoDTO.setReferDeviceID(currentDeviceID.intValue());
								//�� ,�����ظ���������
								csiCustProductInfoDTO.setGroupNo(groupNo);
								csiCustProductInfoDTO.setSheafNo(sheafNo);
								initedCsiProductInfoCol.add(csiCustProductInfoDTO);
							}
							haveLinkToProductDeviceList.add(currentDeviceID);
							currentDeviceIDCol.add(currentDeviceID);
							isExit=true;
							break;
						}
					}
				}
				if(!isExit){
					throw new ServiceException("û��Ϊ�����Ӳ����Ʒѡ���Ӧ���豸��");
				}
				if(currentDeviceID!=null){
					correctDeviceTable.remove(currentDeviceID);
				}
			}
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkSelDeviceAndReturnCsiProdInfo���Ӳ���豸����");
		return initedCsiProductInfoCol;
	}
	/**
	 * ������������Ƿ����޸ĺ�ȡ��
	 * @param csiID
	 * @throws ServiceException
	 */
	public void checkUpdateServiceAccount(int csiID) throws ServiceException{
		try{
			CustServiceInteractionHome  custServiceInteractionHome =HomeLocater.getCustServiceInteractionHome();
			CustServiceInteraction custServiceInteraction =custServiceInteractionHome.findByPrimaryKey(new Integer(csiID));
			if (!custServiceInteraction.getStatus().equals(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT))
		        throw new ServiceException("����ԤԼ�������ܱ����º�ȡ��");
		}
		catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ԤԼ�����Ķ�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ԤԼ�������Ҳ��������Ϣ");
			
		}
	}
	
	
	/**
	 * ����Ʒ�Ż�����
	 * @param campaignid
	 * @param cpList
	 * @param customerID
	 * @throws ServiceException
	 */
	public void checkCampaignDependency(int campaignid,Collection cpList,int customerID) 
	   throws ServiceException,CommandException{
		if (campaignid<1)
			return;
		try{
		   CampaignHome campaignHome =HomeLocater.getCampaignHome();
		   Campaign campaign =campaignHome.findByPrimaryKey(new Integer(campaignid));
		   CustomerHome customerHome =HomeLocater.getCustomerHome();
		   Customer customer =customerHome.findByPrimaryKey(new Integer(customerID));
		   AddressHome addressHome =HomeLocater.getAddressHome();
		   Address addr =addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
		   Collection campaignDistinct =BusinessUtility.getCampaignDistinct(campaign.getCampaignID().intValue());
		   if (campaignDistinct !=null && campaignDistinct.size() !=0){
		       if (!campaignDistinct.contains(new Integer(addr.getDistrictID()))){
			      throw new CommandException(ErrorCode.APP_OSS_CAMPAIGNCONDITION_DOESNOT_MATCH);
		       }
		   }
		   if (campaign.getCustTypeList()!=null && !"".equals(campaign.getCustTypeList())){
				if (campaign.getCustTypeList().indexOf(customer.getCustomerType()) == -1)
					throw new ServiceException("�ͻ����Ͳ��������������");
		   }
		   if (campaign.getOpenSourceTypeList()!=null && !"".equals(campaign.getOpenSourceTypeList())){
				if (campaign.getOpenSourceTypeList().indexOf(customer.getOpenSourceType()) == -1)
					throw new ServiceException("�ͻ���������Դ�������������������");
		   }
		   
		   if(cpList==null || cpList.size()==0)
				return;	
		   Iterator itCP = cpList.iterator();
		   ArrayList packageList =new ArrayList();
		   while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
				if (CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL.equals(cp.getStatus()))
					continue;
				packageList.add(new Integer(cp.getReferPackageID()));	
		  }
		   
		  CampaignAgmtPackageHome campaignAgmtPackageHome = HomeLocater.getCampaignAgmtPackageHome();
		  boolean beIncluded = true;
		  Collection colPackage = campaignAgmtPackageHome.findByCampaignID(campaignid);
		  //�����Żݶ�Ӧ�Ĳ�Ʒ��
		  if(colPackage!=null && !colPackage.isEmpty()){
		     Iterator iterator = colPackage.iterator();
		     while(iterator.hasNext()) {
			    CampaignAgmtPackage campaignAgmtPackage=(CampaignAgmtPackage)iterator.next();
				Integer packageID=new Integer(campaignAgmtPackage.getPackageID());
				if (!packageList.contains((packageID))) {
					beIncluded = false;
					break;
				}
			}
		  }else{
			  beIncluded =false;
		  }
		  if (!beIncluded ) throw new ServiceException("����Ĳ�Ʒ�������ϴ�����������"); 
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����Ʒ�Ż������ļ���ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����Ʒ�Ż������ļ�����Ҳ��������Ϣ");
			
		}
		
	}
	/**
	 * ���ҵ���˻��������Ż��ڴ����ص�ʱ������
	 * @param start
	 * @param end
	 * @param campaignID
	 * @param accountID
	 * @param saID
	 * @return
	 */
	public  boolean isCpCampaignInCertainTimestamp(Timestamp start,Timestamp end,int campaignID,int accountID,int saID){
		  boolean result=false;
		  
		  if(start==null || end==null || campaignID==0)
		 		return false;
			
		  Connection conn=null;
		  Statement stmt=null;
		  ResultSet rs=null;
		  StringBuffer sql=new StringBuffer();
		  try{
			  conn= DBUtil.getConnection();
			  stmt=conn.createStatement();
			  sql.append("select count(distinct(cpcMap.id)) as count from T_CPCampaignMap cpcMap,T_CustomerCampaign cc ");
			  sql.append(" where cpcMap.CCID=cc.CCID and (");
			  sql.append(" ( cpcMap.TimeStart <=to_timestamp('").append(start).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			  sql.append(" and cpcMap.TimeEnd >to_timestamp('").append(start).append("', 'YYYY-MM-DD-HH24:MI:SSxff') )");	
				
			  sql.append(" or ( cpcMap.TimeStart <to_timestamp('").append(end).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			  sql.append(" and cpcMap.TimeEnd >=to_timestamp('").append(end).append("', 'YYYY-MM-DD-HH24:MI:SSxff') ))");	
				
			  sql.append(" and cpcMap.Status='" + CommonConstDefinition.GENERALSTATUS_VALIDATE +"' ");
			  sql.append(" and cpcMap.CAMPAIGNID=" + campaignID );
			  if(accountID>0)
					sql.append(" and cc.AccoutID=" + accountID);
			  if(saID>0)
					sql.append(" and cc.ServiceAccountID=" + saID);
				
			  System.out.println("isCpCampaignInCertainTimestamp==="+ sql.toString());
			    
			  rs=stmt.executeQuery(sql.toString());
			  rs.next();
			  if(rs.getInt("count")>0)
					result=true;
			}catch (SQLException e) {
				e.printStackTrace();
				result=false;
			}finally {
				if (rs != null) {
					try{
						rs.close();
					}catch(SQLException e){
						e.printStackTrace();
						result=false;
					}
				}
				if (stmt != null) {
					try{
						stmt.close();
					}catch(SQLException e){
						e.printStackTrace();
						result=false;
					}
				}
				if (conn != null) {
					try{
						conn.close();
					}catch(SQLException e){
						e.printStackTrace();
						result=false;
					}
				}
			}
			return result;
	}
	  

	/**
	 * �����ѡ��Ʒ����Ӧ�Ĳ�Ʒ���Ƿ������Ҫ����ҵ���˻��Ĳ�Ʒ
	 * @param csiPackageArray
	 * @return
	 * @throws ServiceException
	 */
	private boolean checkExitCreateServiceAccountProduct(Collection csiPackageArray )throws ServiceException{
		boolean exitFlag=false;
		if(csiPackageArray==null || csiPackageArray.isEmpty()){
			throw new ServiceException("û��ѡ���κβ�Ʒ��.");
		} 
		LogUtility.log(clazz, LogLevel.WARN, "������Ʒ������",csiPackageArray);
		Iterator currentPackageIter=csiPackageArray.iterator();
		Collection currentAllProductCol=new ArrayList();
		while(currentPackageIter.hasNext()){
			Integer currentPackageID=(Integer)currentPackageIter.next();
			currentAllProductCol.addAll(BusinessUtility.getProductIDsByPackageID(currentPackageID.intValue())) ;
		}
		Iterator productIterator=currentAllProductCol.iterator();
		while(productIterator.hasNext()){
			Integer currentProductID=(Integer)productIterator.next();
			ProductDTO product=BusinessUtility.getProductDTOByProductID(currentProductID.intValue());
			if(CommonConstDefinition.YESNOFLAG_YES.equals(product.getNewsaFlag())){
				exitFlag=true;
				break;
			}
		}
		return exitFlag;
	}

	/**
	 * ����Ź�ȯ�Ƿ���Ч��������Ź�ȯ���Żݵ��ų���
	 * @param detailNO  �Ź�ȯ��
	 * @param csiPackageArray  ѡ��Ĳ�Ʒ��
	 * @param csiCampaignArray ѡ��Ĳ�Ʒ�Ż�
	 * @param openSourceType   ��Դ��������
	 * @throws ServiceException
	 */
	private void checkDetailNoValid( String detailNO,Collection csiPackageArray,Collection csiCampaignArray,String openSourceType)throws ServiceException{
		if (detailNO != null && !"".equals(detailNO))
		{
			Collection packageCol=getValidGroupBargainInfo(detailNO);
							
			if(!CommonConstDefinition.OPENSOURCETYPE_GROUPBARGAIN.equals(openSourceType)){
				throw new ServiceException("�Ź�ʱ��Դ�����������Ź�����");
			}
			if ((csiPackageArray!=null)&&(!csiPackageArray.isEmpty()))
				throw new ServiceException("�Ź��û�����ѡ��������Ʒ����");
			if ((csiCampaignArray!=null)&&(!csiCampaignArray.isEmpty()))
				throw new ServiceException("�Ź��û�����ѡ�������Żݣ�");
			if (csiPackageArray.isEmpty()) {
				//�Ѳ�Ʒ�� ��Ϣ���ã��Ա�������������
				csiPackageArray.addAll(packageCol); 
			}
			if(csiCampaignArray.isEmpty()){
				GroupBargainDetailDTO detailDTO=BusinessUtility.getGroupBargainDetailByNO(detailNO);
				GroupBargainDTO grouplDTO=BusinessUtility.getGroupBargainByID(detailDTO.getGroupBargainID());
				csiCampaignArray.add(new Integer(grouplDTO.getCampaignId()));
			}
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkOpenAccountPara��ȡ���Ź�ȯ�����Ϣ����"); 
		}
	}
	/**
	 * ����Ʒ���ͷ������Żݺͷ������ŻݺͲ�Ʒ��֮���������
	 * @param packageArray ��Ʒ��
	 * @param campaignArray �Ż��б�
	 * @param customerType �ͻ�����
	 * @param opensourcetype ��Դ��������
	 * @param custAddrDto �ͻ���ַ
	 * @param isNewCustomer �Ƿ��¿�����������Ʒ֮�࣬����׷�Ӵ������ж���
	 * @throws ServiceException
	 */
	private  void checkPackageCampaign(Collection packageArray,
			Collection campaignArray,
			AddressDTO custAddrDto,
			boolean isNewCustomer,
			CommonBusinessParamDTO paramDTO) throws ServiceException{
		
		OperatorDTO operatorDTO=BusinessUtility.getOperatorDTOByID(paramDTO.getOperatorID());
		if(operatorDTO==null)
			throw new ServiceException("�Ҳ�������Ա��Ϣ");
		//���ݲ���Ա�ļ������жϵ�ǰ����Ա�ܷ�����ò�Ʒ��
		if(packageArray!=null && !packageArray.isEmpty()){
			Iterator packageIter=packageArray.iterator();
			while(packageIter.hasNext()){
				Integer packageID=(Integer)packageIter.next();
				ProductPackageDTO  productPackageDTO=BusinessUtility.getProductPackageDTOByID(packageID.intValue());
				if(productPackageDTO==null)
					throw new ServiceException("�Ҳ�����Ʒ����Ϣ");
				if(operatorDTO.getLevelID()<productPackageDTO.getGrade())
					throw new ServiceException("��ǰ����Ա�ļ������۳���Ʒ����"+productPackageDTO.getPackageName());
			}
		}
		//��� �ͻ����ڵ�����(district) �Ƿ��ܹ�������Щ��Ʒ��
		checkPackageByDistrict(packageArray, custAddrDto.getDistrictID());
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkPackageCampaign���ͻ����ڵ������ܹ�����ǰ��Ʒ");
		//����Żݺͷ������ŻݺͲ�Ʒ��֮��Ĺ�ϵ
		if ((campaignArray != null) && (campaignArray.isEmpty() == false)) {
			//����Żݺ����������
			LogUtility.log(clazz,LogLevel.DEBUG,"check the campaign");
			checkCampaignByDistrict(campaignArray,custAddrDto.getDistrictID());
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkPackageCampaign���ͻ����ڵ������ܹ�������ѡ�Ż�");

			//����Żݵ�����
			checkCampaignDependency(campaignArray, packageArray, paramDTO);
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkPackageCampaign�����ѡ���Ż�֮�����������");
			//������Դ���,��Ʒ��,��Ʒ��Ӱ��Ĺ����ظ�����
			checkDuplicateCampaign(packageArray,campaignArray,paramDTO);
		}
	}
	/**
	 * ���һ����Ʒ�Ƿ��ж���ײͻ��ߴ���
	 * @param packageArray
	 * @param campaignArray
	 * @param paramDTO
	 * @throws ServiceException
	 */
	private void checkDuplicateCampaign(Collection packageArray,Collection campaignArray,
									CommonBusinessParamDTO paramDTO)throws ServiceException{
		if(campaignArray==null ||campaignArray.isEmpty()) return ;
		Collection bundleCol=BusinessUtility.getBundle2CampaignPackageCol(campaignArray);
		Collection bundleProdCol=new ArrayList();
		if(!bundleCol.isEmpty()){
			Iterator bundleIter=bundleCol.iterator();
			while(bundleIter.hasNext()){
				Integer packageID=(Integer)bundleIter.next();
				bundleProdCol.addAll(BusinessUtility.getProductIDsByPackageID(packageID.intValue()));
			}
		}
		Iterator campaignIter=campaignArray.iterator();
		Collection product2CpCol=new ArrayList();
		Collection package2CpCol=new ArrayList();
		while(campaignIter.hasNext()){
			Integer campaignID=(Integer)campaignIter.next();
			//ȡ�����е�Э���Ʒ��Э���Ʒ����Ӧ�Ĳ�Ʒ
			Map productidList=BusinessUtility.getCampaignAgmtProductList(campaignID.intValue());
			if(!productidList.isEmpty()){
				Iterator productIter=productidList.keySet().iterator();
				while(productIter.hasNext()){
					Integer productID=(Integer)productIter.next();
					if(bundleProdCol.contains(productID)){
						if(product2CpCol.contains(productID))
							throw new ServiceException("һ����Ʒֻ��ӵ��һ���ײͻ����");
						else
							product2CpCol.add(productID);
					}
				}
			}
			//ȡ��Э���Ʒ��
			Collection packageIDList=BusinessUtility.getCampaignAgmtPackageListByCampaignID(campaignID.intValue());
			if(!packageIDList.isEmpty()){
				Iterator packageIDIter=packageIDList.iterator();
				while(packageIDIter.hasNext()){
					Integer packageID=(Integer)packageIDIter.next();
					if(bundleCol.contains(packageID)){
						if(package2CpCol.contains(packageID))
							throw new ServiceException("һ����Ʒ��ֻ��ӵ��һ���ײͻ����");
						else
							package2CpCol.add(packageID);
					}
				}
			}
			productidList.clear();
			packageIDList.clear();
		}
		bundleCol.clear();
		bundleProdCol.clear();
		product2CpCol.clear();
		package2CpCol.clear();
	}
	
	/**
	 * @param psidList����Ʒ�б�
	 * @param checkSwitch  ��������
	 * checkSwitch-- 1����Ʒ��ͣ  / 2:��Ʒȡ��  / 3����Ʒ�ָ� / 4��ҵ���ʻ����� 
	 *               6���ͻ�Ǩ��  / 7:�ͻ�����  / 8���ͻ��������˻�
	 */
	private void iteratorCheck(Collection psidList,int checkSwitch) throws ServiceException{
		if(psidList!=null && !psidList.isEmpty()){
			Iterator currentPsIDIter=psidList.iterator();
			while(currentPsIDIter.hasNext()){
				int currentPsID=((Integer)currentPsIDIter.next()).intValue();
				canAllowThisAction(currentPsID,checkSwitch);
			}
		}
	}
	
	/**
	 * @param customerProductIDCol �ͻ���Ʒid�б�
	 * @param checkAction  ��鶯���������ȡֵ����
	 * 1����Ʒ��ͣ  / 2:��Ʒȡ��  / 3����Ʒ�ָ� / 4��ҵ���ʻ����� 
	 * 6���ͻ�Ǩ��  / 7:�ͻ�����  / 8���ͻ��������˻�
	 * @return
	 * @throws ServiceException
	 */
	private void canAllowThisAction(int customerProductID,int checkAction) throws ServiceException{
		try {
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��canAllowThisAction���ͻ���Ʒ�����Ϣ");
			CustomerProductHome customerProductHome=HomeLocater.getCustomerProductHome();
			ProductHome productHome=HomeLocater.getProductHome();
			CustomerProduct customerProduct=customerProductHome.findByPrimaryKey(new Integer(customerProductID));
			switch(checkAction){
				case 1:  //��Ʒ��ͣ
					if(!CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL.equals(customerProduct.getStatus())){
						throw new ServiceException("ֻ��״̬Ϊ�����Ĳ�Ʒ������ͣ");
					}
					Product product=productHome.findByPrimaryKey(new Integer(customerProduct.getProductID()));
					if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(product.getProductClass())){
						throw new ServiceException("ֻ�������Ʒ������ͣ");
					}
					break;
				case 2:
					break;
				case 3:  //��Ʒ�ָ�
					if(!CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND.equals(customerProduct.getStatus())){
						throw new ServiceException("ֻ��״̬Ϊ������ͣ�Ĳ�Ʒ���ָܻ�");
					}
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				case 8:
					break;
				default :
					break;
			}
			checkCampainByCpId(customerProductID,checkAction);		
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ܷ�ִ�и������ʱ��λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ܷ�ִ�и������ʱ�Ҳ��������Ϣ");
			
		}
	}
	
	
	
	/**
	 *  �жϸÿͻ��Ƿ���δ�����ʵ�
	 * @param customerid���ͻ�ID
	 * @return  false �� û��δ���ʵ�
	 * 			true  �� ��δ���ʵ�
	 */
	private boolean hasUnpaidInvoice(int customerid) throws ServiceException {
		Connection conn = DBUtil.getConnection();
		
		boolean bFlag = false;
		try {
			String strSql = "select invoiceno from t_invoice where status in ('"
				+ CommonConstDefinition.INVOICE_STATUS_OWE + "','"
				+ CommonConstDefinition.INVOICE_STATUS_WAIT + "','"
				+ CommonConstDefinition.INVOICE_STATUS_BAD + "','"
				+ CommonConstDefinition.INVOICE_STATUS_QFZT
				+ "') and custid = " + customerid;
			bFlag = hasValidRecordExist(conn, strSql);
		} catch (SQLException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���ͻ���" + customerid + "�Ƿ���δ���ʵ�ʱ����");
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return bFlag;
	}
	
	/**
	 * ��֤�Ź�ȯ����Ч��
	 * @param detailNo
	 * @return 
	 */
	private Collection getValidGroupBargainInfo(String detailNo) throws ServiceException{
		Collection packageGroupBarginCols =new ArrayList();	
		CsrBusinessUtility.fillPackageColByGroupBargainDetailNo(packageGroupBarginCols,detailNo);
		if (packageGroupBarginCols ==null || packageGroupBarginCols.isEmpty()){
			throw new ServiceException("���Ź�ȯ�Ų��Ϸ��������Ź����ò������������Ա��ϵ��");	
		}
		return packageGroupBarginCols;
	}
	
	private boolean hasValidRecordExist(Connection conn, String strSql)
	throws SQLException {
		Statement stmt = null;
		
		stmt = conn.createStatement();
		
		boolean hasValidRecordExist = stmt.executeQuery(strSql).next();
		stmt.close();
		
		return hasValidRecordExist;
	}
	

	/**
	 * ��鵱ǰ�Ŀͻ��Ƿ������Żݵ���Ч��
	 * @param campaignArray �Ż��б�
	 * @param packageArray  ��Ʒ�б�
	 * @param custtype   �ͻ�����
	 * @param opensourcetype ��Դ����
	 * @throws ServiceException
	 */
	private void  checkCampaignDependency(Collection campaignArray, Collection packageArray,CommonBusinessParamDTO paramDTO) throws ServiceException {
		if ((campaignArray == null) || campaignArray.isEmpty()) return;
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkCampaignDependency����Ż������Ƿ�����");
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"����campaignArray����",campaignArray);
		Iterator iteCampaign = campaignArray.iterator();
		//������Ų�Ʒ��������ӳ��
		Map productToCmpMap=new HashMap();
		while (iteCampaign.hasNext()) {
			Integer campaignid = (Integer) iteCampaign.next();
			CampaignDTO campaign = BusinessUtility.getCampaignDTOByID(campaignid.intValue());
			if(campaign.getCustTypeList()!=null && !"".equals(campaign.getCustTypeList())){
				if (campaign.getCustTypeList().indexOf(paramDTO.getCustomerType()) == -1)
					throw new ServiceException("�ͻ����Ͳ��������������");
			}
			if(campaign.getOpenSourceTypeList()!=null && !"".equals(campaign.getOpenSourceTypeList())){
				if (campaign.getOpenSourceTypeList().indexOf(paramDTO.getOpensourcetype()) == -1)
					throw new ServiceException("�ͻ���������Դ�������������������");
			}
			if(campaign.getCsiTypeList()!=null && !"".equals(campaign.getCsiTypeList())){
				if (campaign.getCsiTypeList().indexOf(paramDTO.getCsiType()) == -1)
					throw new ServiceException("�������Ͳ��������������");
			}
			//����ײ�/�����Բ�Ʒ���������Ƿ�����
			checkCampaignCondPackage(campaignid.intValue(),packageArray,paramDTO);
			//����ײ�/�����Բ�Ʒ�������Ƿ�����
			checkCampaignCondProduct(campaignid.intValue(),packageArray,paramDTO);			
			//����ײ�/�������ײ�/�����������Ƿ�����
			checkCampaignCondCampaign(campaignid.intValue(),campaignArray,paramDTO);
			//����ײͶԲ�Ʒ�Ĺ����ظ�����
			//checkNoExitMultiCampToProduct( campaignid, packageArray, paramDTO, productToCmpMap);
		}
		//���
		productToCmpMap.clear();
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkCampaignDependency������");
	}
	
	/**
	 *  ���������Ĳ�Ʒ���Ƿ���Ͽͻ����ڵ�����
	 * @param packageArray �� �ͻ�����Ĳ�Ʒ��
	 * @param districtID �� �ͻ����ڵ�����
	 * @throws CommandException
	 */
	private void checkPackageByDistrict(Collection packageArray, int districtID) throws ServiceException {
		//1. ����ĳ�������ܹ�������в�Ʒ��set
		String sql = "Select a.PackageID From T_PackageToMarketSegment a ,T_MarketSegmentToDistrict b Where a.MarketSegmentID=b.MarketSegmentID  And b.DistrictID In (Select Id From t_districtSetting Connect By Prior belongto=Id Start With Id=?)";
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"checkPackageByDistrict",sql);
		Connection con = null;
		PreparedStatement stm=null;
		ResultSet rs =null;
		Collection havaConfigPkidCol=BusinessUtility.getPkbyPkFromP2MS(packageArray);
		try {
			con = DBUtil.getConnection();
			stm = con.prepareStatement(sql);
			stm.setInt(1,districtID);
			Collection allowedCollection = new ArrayList();
			rs = stm.executeQuery();
			while (rs.next()) {
				allowedCollection.add(new Integer(rs.getInt("PackageID")));	
			}
			if(havaConfigPkidCol!=null && !havaConfigPkidCol.isEmpty()){
				Iterator  packageIterator=havaConfigPkidCol.iterator();
				while(packageIterator.hasNext()){
					Integer currentPackageID=(Integer)packageIterator.next();
					if(!allowedCollection.contains(currentPackageID))
						throw new ServiceException("�ͻ������["+BusinessUtility.getProductPackageDTOByID(currentPackageID.intValue()).getPackageName()+"]�������ڸ��������ۡ�");
				}
				
			}	
		}catch(SQLException e) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkPackageByDistrict", e);
			throw new ServiceException("ȡ�õ�����" + BusinessUtility.getDistrictNameById(districtID) + "�����ܹ���Ĳ�Ʒ��ʱ���ִ���");
		}finally {
			if (rs !=null){
				try{
				   rs.close();
			    }catch (SQLException e) {
				   LogUtility.log(clazz,LogLevel.ERROR,"checkPackageByDistrict", e);
			    }
			}
			if (stm != null) {
				try{ 
					stm.close();	
				}catch(SQLException ignore){
					LogUtility.log(clazz,LogLevel.ERROR,"checkPackageByDistrict", ignore);
				}
			}
			
			if (con != null) {
				try{ 
					con.close();	
				}catch(SQLException ignore){
					LogUtility.log(clazz,LogLevel.ERROR,"checkPackageByDistrict", ignore);
				}
			}        
		}
	}
	
	/**
	 *  �����ѡ����Ż��Ƿ���Ͽͻ����ڵ�����
	 * @param campaignArray �� �ͻ�ѡ����Ż�
	 * @param districtID �� �ͻ����ڵ�����
	 * @throws CommandException
	 */
	private void checkCampaignByDistrict(Collection campaignArray, int districtID) throws ServiceException {
		//1. ����ĳ�������ܹ�ѡ����Ż�set
		String sql = "Select a.CampaignID From T_CampaignToMarketSegment a ,T_MarketSegmentToDistrict b Where a.MarketSegmentID=b.MarketSegmentID  And b.DistrictID In (Select Id From t_districtSetting Connect By Prior belongto=Id Start With Id=?)";
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"checkCampaignByDistrict",sql);
		Connection con = null;
		PreparedStatement stm=null;
		ResultSet rs =null;
		Collection havaConfigPkidCol=BusinessUtility.getCPbyCPFromC2MS(campaignArray);
		try {
			con = DBUtil.getConnection();
			stm = con.prepareStatement(sql);
			stm.setInt(1,districtID);
			Collection allowedCollection = new ArrayList();
			rs = stm.executeQuery();
			while (rs.next()) {
				allowedCollection.add(new Integer(rs.getInt("CampaignID")));	
			}
			if(havaConfigPkidCol!=null && !havaConfigPkidCol.isEmpty()){
				Iterator  campaignIterator=havaConfigPkidCol.iterator();
				while(campaignIterator.hasNext()){
					Integer currentcampaignID=(Integer)campaignIterator.next();
					if(!allowedCollection.contains(currentcampaignID))
						throw new ServiceException("�ͻ������["+BusinessUtility.getCampaignDTOByID(currentcampaignID.intValue()).getCampaignName()+"]�������ڸ��������ۡ�");
				}
				
			}
		}catch(SQLException e) {
			LogUtility.log(clazz,LogLevel.ERROR, e);
			throw new ServiceException("��������" + BusinessUtility.getDistrictNameById(districtID) + "������ѡ����Ż�ʱ���ִ���");
		}finally {
			if (rs !=null){
				try{
				   rs.close();
			    }catch (SQLException e) {
				   LogUtility.log(clazz,LogLevel.ERROR,"checkPackageByDistrict",  e);
				 
			    }
			}
			if (stm != null) {
				try{ 
					stm.close();	
				}catch(SQLException ignore){
					LogUtility.log(clazz,LogLevel.ERROR,"checkPackageByDistrict", ignore);
				}
			}
			if (con != null) {
				try{ 
					con.close();	
				}catch(SQLException ignore){}
			}        
		}
	}
	/**
	 * ��鹺���Ʒʱ��ѡ���Ӳ���豸�Ƿ���ȷ�������ѡ�Ĳ�Ʒ����ѡ��Ӳ���豸�Ƿ�ƥ��
	 * @param terminalDeviceTable  ��ѡȡ���ն��豸�������Ϣ
	 * @param productCol			��Ʒ���б���Ϣ
	 * @param isInitCsiProductInfo  �Ƿ���Ӳ����Ʒ�Ͷ�ӦӲ���豸�������Ϣ
	 * @throws ServiceException
	 */
	private void checkSelectTerminalDevice(CustServiceInteractionDTO csiDTO,HashMap terminalDeviceTable,Collection productCol,boolean isInitCsiProductInfo)throws ServiceException {
		Collection csiProdCol=checkSelDeviceAndReturnCsiProdInfo(csiDTO,terminalDeviceTable,productCol,isInitCsiProductInfo);
		if(isInitCsiProductInfo){
			//��ȡ�õ�Ӳ����Ʒ��Ӧ�Ĳ�Ʒ���豸��Ϣ���õ������Ļ�����
			serviceContext.put(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST,csiProdCol);
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"�����Ƿ���ڶ�Ӧ�Ĳ�Ʒ���豸��Ϣ",csiProdCol);
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkSelectTerminalDevice���Ӳ���豸����");
	}
	
	/**
	 * ��鹺���Ʒʱ��ѡ���Ӳ���豸�Ƿ���ȷ�������ѡ�Ĳ�Ʒ����ѡ��Ӳ���豸�Ƿ�ƥ��,�����ع����csiProductInfo
	 * @param terminalDeviceTable
	 * @param productCol
	 * @param isInitCsiProductInfo
	 * @return
	 * @throws ServiceException
	 */
	public Collection checkSelDeviceAndReturnCsiProdInfo(CustServiceInteractionDTO csiDTO,HashMap terminalDeviceTable,Collection productCol,boolean isInitCsiProductInfo)throws ServiceException {
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"����csiDTO����"+csiDTO);
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"����terminalDeviceTable����"+terminalDeviceTable);
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"����productCol����"+productCol);
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"����isInitCsiProductInfo����"+isInitCsiProductInfo);
		if(productCol==null || productCol.isEmpty()){
			throw new ServiceException("û�в�Ʒ��");
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkSelDeviceAndReturnCsiProdInfo�����ѡ��Ӳ���豸");
		//�½�һ�����������֤����ȷ���豸���ݣ��豸ID��
		Hashtable correctDeviceTable=null;	
		//�½�һ�������洢Ӳ����Ʒ������ص��豸��Ϣ
		Collection initedCsiProductInfoCol=new ArrayList();
		if(terminalDeviceTable!=null && !terminalDeviceTable.isEmpty() ){
			// ȡ������Ӳ���豸���������к�
			Set currentKeySet = terminalDeviceTable.keySet();
			correctDeviceTable=new Hashtable();
			String currntSerialNo=null;
			String currentDeviceClass=null;
			String currentDeviceModel=null;
			Iterator currentDeviceIter=currentKeySet.iterator();
			while(currentDeviceIter.hasNext()){
				//ȡ������Ӳ���豸���������к�
				currntSerialNo=(String)currentDeviceIter.next();
				//ȡ�ø�Ӳ���豸���кŶ�Ӧ���豸����
				currentDeviceClass=(String)terminalDeviceTable.get(currntSerialNo);
				//��ȡ�ն��豸�豸����Ϣ
				TerminalDeviceDTO terminalDevice=BusinessUtility.getDeviceBySerialNo(currntSerialNo);
				
				if(!terminalDevice.getDeviceClass().equals(currentDeviceClass))
					throw new ServiceException("���кţ�" + currntSerialNo + "����Ӧ�豸���豸���Ͳ���"+HelperCommonUtil.getNameByDeviceClass(currentDeviceClass));
				//�жϸ��豸��Ӧ���豸��״̬�Ƿ���ȷ
				if (!CommonConstDefinition.DEVICE_STATUS_WAITFORSELL.equals(terminalDevice.getStatus()))
					throw new ServiceException("��ѡ���"+HelperCommonUtil.getNameByDeviceClass(currentDeviceClass)+"��״̬Ϊ"+
								HelperCommonUtil.getCommonNameByCacheNameAndKey("SET_D_DEVICESTATUS",terminalDevice.getStatus())+
								",��ѡ��״̬Ϊ���۵��豸��");
				//ȡ����ѡ�豸���кŶ�Ӧ���豸�ͺ�
				currentDeviceModel=terminalDevice.getDeviceModel();
				//�����������������ͺ�����ԭ�����Ƿ���豸��;ƥ��
				checkDeviceMatchToCsiTypeAndReason(csiDTO,terminalDevice);
				//����һ���豸��Ϣ
				int currentDeviceID=terminalDevice.getDeviceID();
				//������ѡ����豸���кŶ�Ӧ���豸id���豸�ͺ�
				LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"����Ӳ�����豸���к�","currentDeviceID="+currentDeviceID+";currentDeviceModel="+currentDeviceModel);
				correctDeviceTable.put(new Integer(currentDeviceID),currentDeviceModel);
			}
			
			//��鵱ǰ�豸�Ƿ����(ֻ���STB,SC) 2007-12-21
			if (terminalDeviceTable.size() == 1) { // ����ʱ��ֻ��һ���豸�������л������ܿ�������豸������δ��Եġ�
				Set checkCurrentKeySet = terminalDeviceTable.keySet();
				Iterator checkCurrentDeviceIter = checkCurrentKeySet.iterator();
				if (checkCurrentDeviceIter.hasNext()) {
					String checkCurrntSerialNo = (String) checkCurrentDeviceIter
							.next();
					TerminalDeviceDTO checkTerminalDevice = BusinessUtility
							.getDeviceBySerialNo(checkCurrntSerialNo);
					if (CommonConstDefinition.DEVICECALSS_STB.equals(checkTerminalDevice.getDeviceClass()) || CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(checkTerminalDevice.getDeviceClass())) {
						if (BusinessUtility.isDeviceMatch(checkCurrntSerialNo)) {
							throw new ServiceException("��ѡ�豸�Ѿ���ԣ���Թ�ϵ����ȷ��");
						}
					}
				}
			}
			if (terminalDeviceTable.size() > 1) { // ����豸������豸��Թ�ϵ
				checkDeviceMatch(terminalDeviceTable);
			}
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkSelDeviceAndReturnCsiProdInfo�����ѡ��Ӳ���豸����ѡӲ����Ʒ�Ƿ�ƥ��");
		//��������Ĳ�Ʒ����
		Iterator currentProductIter=productCol.iterator();
		//��������Ѿ������Ĳ�Ʒ��ID
		Collection currentDeviceIDCol=new ArrayList();
		while(currentProductIter.hasNext()){
			Integer currentProductID=(Integer)currentProductIter.next();	
			ProductDTO product=BusinessUtility.getProductDTOByProductID(currentProductID.intValue());
			if(BusinessUtility.getDeviceModelDTOByProductID(product.getProductID()).isEmpty())
				continue;
			//�����Ӳ����Ʒ����Ҫ�ж���ѡ��Ӳ����Ʒ���豸�����к��Ƿ��Ӧ
			if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(product.getProductClass())){
				//�����ѡ��Ӳ����Ʒ���Ƿ���ڶ�Ӧ���豸
				boolean isExit=false;
				//ȡ�������豸����Ʒ��ӳ�����
				Collection deviceToProductCol=BusinessUtility.getDevToProdByProductId(currentProductID.intValue());
				if(deviceToProductCol==null ||deviceToProductCol.isEmpty()){
					throw new ServiceException("��Ʒ�Ҳ�����Ӧ���豸�ͺš�");
				}
				Iterator deviceToProductIter=deviceToProductCol.iterator();
				//ȡ�øò�Ʒ��Ӧ�������豸�ͺ�
				Collection deviceModelCol=new ArrayList();
				while(deviceToProductIter.hasNext()){
					deviceModelCol.add(((DeviceMatchToProductDTO)deviceToProductIter.next()).getDeviceModel());
				}
				//ȡ�����豸���кŶ�Ӧ���豸��������õ�����
				//��11-23���
				if(correctDeviceTable==null||correctDeviceTable.isEmpty()){
					throw new ServiceException("û����Ч���豸��");
				}
				Iterator checkDeviceIter=correctDeviceTable.keySet().iterator();
				Integer currentDeviceID=null;
				//��������Ѿ��Ͳ�Ʒ������Ӳ���豸��ID
				Collection haveLinkToProductDeviceList=new ArrayList();
				while(checkDeviceIter.hasNext()){
					//ȡ���豸id
					currentDeviceID=(Integer)checkDeviceIter.next();
					//��������豸id��֤��ѡ����ͬ���豸�ͺŵ�Ӳ��
					if(currentDeviceIDCol.contains(currentDeviceID)){
						throw new ServiceException("��ѡ�豸�г������ظ���Ӳ����");
					}
					//����Ƿ��豸�ͺźͲ�Ʒ���ͺ�ƥ��
					if(deviceModelCol.contains((String)correctDeviceTable.get(currentDeviceID))){
						//���úͲ�Ʒ�������豸��ID
						if(!haveLinkToProductDeviceList.contains(currentDeviceID)){
							//�Ƿ���Ҫ����Ӳ����Ʒ��CsiProductInfo��Ϣ
							if(isInitCsiProductInfo){
								//��ʼ���ʹ�������Ĳ�Ʒ��Ӧ�������漰�Ŀͻ���Ʒ�����Ϣ
								CsiCustProductInfoDTO csiCustProductInfoDTO=new CsiCustProductInfoDTO();
								//���ò�Ʒid
								csiCustProductInfoDTO.setProductID(currentProductID.intValue());
								//���ò�Ʒ��Ӧ��Ӳ���豸��id
								csiCustProductInfoDTO.setReferDeviceID(currentDeviceID.intValue());
								
								initedCsiProductInfoCol.add(csiCustProductInfoDTO);
							}
							haveLinkToProductDeviceList.add(currentDeviceID);
							currentDeviceIDCol.add(currentDeviceID);
							isExit=true;
							break;
						}
					}
				}
				if(!isExit){
					throw new ServiceException("û��Ϊ�����Ӳ����Ʒѡ���Ӧ���豸��");
				}
				if(currentDeviceID!=null){
					correctDeviceTable.remove(currentDeviceID);
				}
			}
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkSelDeviceAndReturnCsiProdInfo���Ӳ���豸����");
		return initedCsiProductInfoCol;
	}
	/**
	 * �����������������ͺ�����ԭ�����Ƿ���豸��;ƥ��
	 * @param csiDTO
	 * @param terminalDevice
	 * @throws ServiceException
	 */
	private static void checkDeviceMatchToCsiTypeAndReason(CustServiceInteractionDTO csiDTO,TerminalDeviceDTO terminalDevice)throws ServiceException{
		//ȡ���豸��Ӧ����;���������ԭ��������������Ƿ�ƥ���豸����
		String referpurpose=BusinessUtility.getDevicePurposeStrListByCSICreateReason(csiDTO.getType(),csiDTO.getCreateReason(),terminalDevice.getDeviceModel());
		if(referpurpose!=null && !"".equals(referpurpose)){
			Collection condCol=HelperCommonUtil.splitStringToCol(referpurpose);
			if(terminalDevice.getPurposeStrList()!=null &&!"".equals(terminalDevice.getPurposeStrList())){
				boolean ismatch=false;
				Collection tagetCol=HelperCommonUtil.splitStringToCol(terminalDevice.getPurposeStrList());
				Iterator condIter=condCol.iterator();
				while(condIter.hasNext()){
					if(tagetCol.contains((String)condIter.next())){
						ismatch=true;
						break;
					}
				}
				if(!ismatch)throw new ServiceException("��ѡ���豸��"+terminalDevice.getSerialNo()+"������;�����ϱ����������ͺ�ԭ��");
			}
		}
	}
	
	//wang fang 2008.05.15 begin
	//�豸�����豸��;����߼�����
	/*************************************************************/
	private static void checkDeviceMatchToCsiTypeAndReason(
			CustServiceInteractionDTO csiDTO, TerminalDeviceDTO terminalDevice,
			TerminalDeviceDTO oldterminalDevice) throws ServiceException {
		// ȡ���豸��Ӧ����;���������ԭ��������������Ƿ�ƥ���豸����
		String referpurpose = BusinessUtility
				.getDevicePurposeStrListByCSICreateReason(csiDTO.getType(),
						csiDTO.getCreateReason(), terminalDevice
								.getDeviceModel());
		String deviceswapmatch = BusinessUtility
				.getSystemsettingValueByName("SET_D_DEVICESWAPOLDDEVICENEWDEVICEPURPOSEMATCH");
	
		if (referpurpose != null && !"".equals(referpurpose)) {
			Collection condCol = HelperCommonUtil
					.splitStringToCol(referpurpose);
			if (!"".equals(terminalDevice.getPurposeStrList())
					&& terminalDevice.getPurposeStrList() != null) {
			
				boolean ismatch = false;
				Collection tagetCol = HelperCommonUtil.splitStringToCol(terminalDevice
						.getPurposeStrList());
			
				Iterator condIter = condCol.iterator();
				while (condIter.hasNext()) {
					if (tagetCol.contains((String) condIter.next())) {
						ismatch = true;

						break;
					}
				}
				if (!ismatch)
					throw new ServiceException("��ѡ���豸��"
							+ terminalDevice.getSerialNo() + "������;�����ϱ����������ͺ�ԭ��");
			}
		}
		
		if ((CommonConstDefinition.YESNOFLAG_YES.equals(deviceswapmatch))
				&& (!"".equals(oldterminalDevice.getPurposeStrList()))
				&& (oldterminalDevice.getPurposeStrList() != null)) {
			if (!"".equals(terminalDevice.getPurposeStrList())
					&& terminalDevice.getPurposeStrList() != null) {
			boolean ismatch1 = false;
			Collection tagetCol = HelperCommonUtil.splitStringToCol(terminalDevice
					.getPurposeStrList());
		
			Collection tagetColold = HelperCommonUtil
					.splitStringToCol(oldterminalDevice.getPurposeStrList());

			Iterator oldIter = tagetColold.iterator();

			while (oldIter.hasNext()) {
				String temp1 = (String) oldIter.next();
				if ((temp1 != null) && (!"".equals(temp1)) && tagetCol.contains(temp1)) {
					ismatch1 = true;
					break;
				}
			}
			
			if (!ismatch1)
				throw new ServiceException("�豸������ʱ����ѡ������豸��"
						+ terminalDevice.getSerialNo() + "������;�����豸����;����");

		}
			}
	}
	/*************************************************************/
	//wang fang 2008.05.15 end
	
	/**
	 *  ����Ʒ�����������Ƿ�����
	 *  ��Ʒ֮��������ԣ���Ȩ����/��������/�ų�/����/����
	 *  ˵���� ����ֻ��� ��Ȩ����[D]/��������[P]
	 * @param productSet
	 * @throws CommandException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void checkProductDependency(Collection productCol) throws ServiceException {
		checkProductDependency(productCol,BusinessUtility.getAllProductDepentDefineList(productCol));
	}
	private void checkProductDependency(Collection productCol,Collection productDependency) throws ServiceException {
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkProductDependency���������");
		if ((productCol == null) || productCol.isEmpty()) throw new ServiceException("û�в�Ʒ��");
		
		Iterator iteProduct = productCol.iterator();	
		//������鹺��ò�Ʒ�Ƿ��ظ�
		Collection contianCol=new ArrayList();
		while (iteProduct.hasNext()) {
			Integer productID = ((Integer) iteProduct.next());
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkProductDependency�������  contianCol"+contianCol);
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkProductDependency�������  productID"+productID);
			if(contianCol.contains(productID))
				throw new ServiceException("��Ʒ�������ظ�������ѡ����Ĳ�ƷID��"+BusinessUtility.getProductNameByProductID(productID.intValue()) +" �ظ���");
			contianCol.add(productID);

			//�����Ȩ����
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"�����Ȩ����");
			Collection dependencyOfAuth = getProdDependByProdIdAndType(productID.intValue(),CommonConstDefinition.PRODUCTDEPENDENCYTYPE_P,productDependency);
			if(!(dependencyOfAuth==null || dependencyOfAuth.isEmpty())){
				Iterator itDependencyOfAuth=dependencyOfAuth.iterator();
				while(itDependencyOfAuth.hasNext()){
					ProductDependencyDTO pdDTO = (ProductDependencyDTO) itDependencyOfAuth.next();
					checkProductSingleDependency(pdDTO,productCol,CommonConstDefinition.PRODUCTDEPENDENCYTYPE_D);
				}
			}
			
			//��鹺������
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��鹺������");
			Collection dependencyOfBuy = getProdDependByProdIdAndType(productID.intValue(),CommonConstDefinition.PRODUCTDEPENDENCYTYPE_D,productDependency);
			if(!(dependencyOfBuy==null || dependencyOfBuy.isEmpty())){
				Iterator itDependencyOfBuy=dependencyOfBuy.iterator();
				while(itDependencyOfBuy.hasNext()){
					ProductDependencyDTO pdDTO = (ProductDependencyDTO) itDependencyOfBuy.next();
					checkProductSingleDependency(pdDTO,productCol,CommonConstDefinition.PRODUCTDEPENDENCYTYPE_P);
				}
			}
			
			//����Ʒ�ų��ϵ
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��Ʒ�ų��ϵ���");
			Collection dependencyOfcollision = getProdDependByProdIdAndType(productID.intValue(),CommonConstDefinition.PRODUCTDEPENDENCYTYPE_C,productDependency);
			if(!(dependencyOfcollision==null || dependencyOfcollision.isEmpty())){
				Iterator itDependencyOfCollistion=dependencyOfcollision.iterator();
				while(itDependencyOfCollistion.hasNext()){
					ProductDependencyDTO pdDTO = (ProductDependencyDTO) itDependencyOfCollistion.next();
					checkProductSingleDependency(pdDTO,productCol,CommonConstDefinition.PRODUCTDEPENDENCYTYPE_C);
				}
			}
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��checkProductDependency������");
	}
	
	
	public Collection getNoMatchProductDependency(Collection productCol,Collection productAllCol) throws ServiceException {
		Collection returnCol =new ArrayList();
		Collection productDependency = BusinessUtility.getAllProductDepentDefineList(productCol);
		Iterator iteProduct = productCol.iterator();	
		while (iteProduct.hasNext()) {
			Integer productID = ((Integer) iteProduct.next());
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��getNoMatchProductDependency�������  productID"+productID);

			//�����Ȩ����
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"�����Ȩ����");
			Collection dependencyOfAuth = getProdDependByProdIdAndType(productID.intValue(),CommonConstDefinition.PRODUCTDEPENDENCYTYPE_P,productDependency);
			if(!(dependencyOfAuth==null || dependencyOfAuth.isEmpty())){
				Iterator itDependencyOfAuth=dependencyOfAuth.iterator();
				while(itDependencyOfAuth.hasNext()){
					ProductDependencyDTO pdDTO = (ProductDependencyDTO) itDependencyOfAuth.next();
					try{
					   checkProductSingleDependency(pdDTO,productAllCol,CommonConstDefinition.PRODUCTDEPENDENCYTYPE_D);
					} catch (ServiceException e){
					   returnCol.add(productID);
					   continue;
					}
				}
			}
			
			//��鹺������
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��鹺������");
			Collection dependencyOfBuy = getProdDependByProdIdAndType(productID.intValue(),CommonConstDefinition.PRODUCTDEPENDENCYTYPE_D,productDependency);
			if(!(dependencyOfBuy==null || dependencyOfBuy.isEmpty())){
				Iterator itDependencyOfBuy=dependencyOfBuy.iterator();
				while(itDependencyOfBuy.hasNext()){
					ProductDependencyDTO pdDTO = (ProductDependencyDTO) itDependencyOfBuy.next();
					try{
					   checkProductSingleDependency(pdDTO,productAllCol,CommonConstDefinition.PRODUCTDEPENDENCYTYPE_P);
					} catch (ServiceException e){
						returnCol.add(productID);
						continue;
					}
				}
			}
			
			//����Ʒ�ų��ϵ
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��Ʒ�ų��ϵ���");
			Collection dependencyOfcollision = getProdDependByProdIdAndType(productID.intValue(),CommonConstDefinition.PRODUCTDEPENDENCYTYPE_C,productDependency);
			if(!(dependencyOfcollision==null || dependencyOfcollision.isEmpty())){
				Iterator itDependencyOfCollistion=dependencyOfcollision.iterator();
				while(itDependencyOfCollistion.hasNext()){
					ProductDependencyDTO pdDTO = (ProductDependencyDTO) itDependencyOfCollistion.next();
					try{
					  checkProductSingleDependency(pdDTO,productAllCol,CommonConstDefinition.PRODUCTDEPENDENCYTYPE_C);
				    } catch (ServiceException e){
					  returnCol.add(productID);
					  continue;
				    }
				}
			}
		}
		return returnCol;
	}
	
	/**
	 * ��Ʒ��ϵ���,�÷���ԭ��businessutility��,��jdbcʵ��,�������α곬���쳣,���ڴ˴�,��������,
	 * @param productID
	 * @param type
	 * @return
	 * @throws ServiceException
	 */
	private Collection getProdDependByProdIdAndType(int productID,String type,Collection productDependency) throws ServiceException {
		Collection returnCol = new ArrayList();
		
		Collection typeCol=PublicService.stringSplitToTargetObject(type,",","String");
		if(typeCol==null){
			throw new ServiceException("��Ʒ��ϵ�������δ֪��");
		}
		for(Iterator it=productDependency.iterator();it.hasNext();){
			ProductDependencyDTO dto=(ProductDependencyDTO) it.next();
			if(dto.getProductId()==productID&&typeCol.contains(dto.getType())){
				returnCol.add(dto);
			}
		}
		return returnCol;
	}
	/**
	 * ��Ʒ�����ϵ��飬����type�����ͽ�����Ӧ���
	 * ������ʵ�ֵĲ�Ʒ֮��������Լ���������Ȩ����/��������/�ų�
	 * @param dto
	 * @param productCol
	 * @param type
	 * @throws ServiceException
	 */
	private void checkProductSingleDependency(ProductDependencyDTO  dto,Collection productCol,String type)throws ServiceException{
		
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"dto="+ dto.toString());
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"productCol="+ productCol.toString());
		
		if(dto==null || productCol==null || productCol.isEmpty())return;
		Collection referProductIDList=new ArrayList();
		
		//��ProductDependencyDTO��ReferProductIDList�ָ�Ϊcollection
		String strReferProductIDs=dto.getReferProductIDList();
		if(strReferProductIDs==null || "".equals(strReferProductIDs))return;
		String [] strReferProductID=strReferProductIDs.split(",");
		for(int i=0;i<strReferProductID.length;i++){
			if(!(strReferProductID[i]==null || "".equals(strReferProductID[i]))){
				try{
					referProductIDList.add(new Integer(strReferProductID[i]));
				}
				catch(Exception e){
					LogUtility.log(BusinessRuleService.class,LogLevel.WARN,"��Ʒ������ϵ���ô���ReferProductIDListת���ɲ�ƷID����SEQ="+ dto.getSeqNo());
					throw new ServiceException("��Ʒ������ϵ���ó���");
				}
			}
		}
		
		//�������
		//ȫƥ��
		if(CommonConstDefinition.YESNOFLAG_YES.equals(dto.getReferAllFlag())){
			Iterator itReferProductID=referProductIDList.iterator();
			while(itReferProductID.hasNext()){
				Integer productID=(Integer)itReferProductID.next();
				if(productCol.contains(productID)){
					if(CommonConstDefinition.PRODUCTDEPENDENCYTYPE_C.equals(type))
						throw new ServiceException("���β������ܹ���[" +BusinessUtility.getProductNameByProductID(productID.intValue()) +"]��Ʒ��");
				}
				else{
					if(CommonConstDefinition.PRODUCTDEPENDENCYTYPE_P.equals(type))
						throw new ServiceException("���β������빺��[" +BusinessUtility.getProductNameByProductID(productID.intValue()) +"]��Ʒ��");
				}
			}
		}
		//����ƥ��
		else{
			int hasCount=0;
			Iterator itReferProductID=referProductIDList.iterator();
			while(itReferProductID.hasNext()){
				Integer productID=(Integer)itReferProductID.next();
				if(productCol.contains(productID)){
					//�ų�
					if(CommonConstDefinition.PRODUCTDEPENDENCYTYPE_C.equals(type))
						throw new ServiceException("���β������ܹ���["+BusinessUtility.getProductNameByProductID(productID.intValue())+"]�Ĳ�Ʒ");	
					hasCount++;	
				}
			}
			
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"hasCount="+ hasCount +";dto.getReferProductNum()=" +dto.getReferProductNum());
			if(hasCount<dto.getReferProductNum() && (!CommonConstDefinition.PRODUCTDEPENDENCYTYPE_C.equals(type)))
				throw new ServiceException("��Ʒ������ϵ������");
		}
	}
	/**
	 * ����Ʒ����,���ݴ����psid���ϰ�ҵ���ʻ�����,�ٵ���ԭ�з������.
	 * @param custProductCol
	 * @throws ServiceException
	 */
	private void checkProductOperationDependency(Collection custProductCol) throws ServiceException {
		Map saPsidMap=	BusinessUtility.splitCustomerProductWithServiceAccount(custProductCol);
		for(Iterator sait=saPsidMap.keySet().iterator();sait.hasNext();){
			Integer said=(Integer) sait.next();
			checkProductOperationDependency((ArrayList)saPsidMap.get(said),said.intValue());
		}
	}
	/**
	 * ����Ʒ����ʱ����ѡ��Ʒ�Ƿ��������������
	 * @param custProductCol ѡ����Ҫ����Ĳ�Ʒ����
	 * @throws ServiceException
	 */
	private void checkProductOperationDependency(Collection custProductCol,int serviceAccountID) throws ServiceException {
		try {
			//ȡ��ҵ���˻��µ����е���Ч�Ĳ�Ʒ
			Collection haveExitProductCol=BusinessUtility.getProductIDListByServiceAccount(serviceAccountID," STATUS<>'"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"'");
			// ȡ������ѡ�в�Ʒ��productID�������productCol���������
			Collection productCol=new ArrayList();
			CustomerProductHome customerProductHome=HomeLocater.getCustomerProductHome();
			if(custProductCol!=null && !custProductCol.isEmpty()){
				Iterator custproductIter=custProductCol.iterator();
				while(custproductIter.hasNext()){
					Integer currentCustProductID=(Integer)custproductIter.next();
					CustomerProduct customerProduct=customerProductHome.findByPrimaryKey(currentCustProductID);
					productCol.add(new Integer(customerProduct.getProductID()));
				}
			}
			if(productCol!=null && !productCol.isEmpty()){
				Iterator productIter=productCol.iterator();
				while(productIter.hasNext()){
					//���������������ڵ�ǰҵ���˻��������ڵ�ǰ��Ʒ��������Ч��Ʒ
					Collection currentConditioCol=new ArrayList();
					int productID=((Integer)productIter.next()).intValue();
					//������ǰ��Ʒ�����еĲ�Ʒ
					Collection currentDependenceCol=BusinessUtility.getDependenceProductByProductID(productID);
					if(currentDependenceCol!=null && !currentDependenceCol.isEmpty()){
						Iterator currentDependenceIter=currentDependenceCol.iterator();
						while(currentDependenceIter.hasNext()){
							Integer curDependenceID=(Integer)currentDependenceIter.next();
							if(haveExitProductCol.contains(curDependenceID)){
								currentConditioCol.add(curDependenceID);
							}
						}
					}
					
					System.out.println("currentConditioCol======="+currentConditioCol);
					System.out.println("productCol=========="+productCol);
					
					
					//��ѡ��Ʒ����ȫ������������Ʒ������������ͣȡ������
					if(!productCol.containsAll(currentConditioCol)){
						throw new ServiceException("�ò������²�Ʒ��������ϵ�����㣬����ִ�и��������");
					}
				}
			}
		}catch(HomeFactoryException e1) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkProductDependency",e1);
			throw new ServiceException("���ͻ���Ʒ�������Թ�ϵʱ��λ����");
		}catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkProductDependency",e2);
			throw new ServiceException("���ͻ���Ʒ�������Թ�ϵʱ���Ҵ���");
		}
	} 
	/**
	 * 6-8�����,��Ʒ�����ظ�,
	 * @param productCol
	 * @throws ServiceException
	 */
	private void checkProductTautology(Collection productCol)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "��Ʒ�ظ����");

		Iterator it1 = productCol.iterator();
		Iterator it2 = productCol.iterator();
		LogUtility.log(clazz, LogLevel.DEBUG, "productCol" + productCol);
		while (it1.hasNext()) {
			// Integer proID=(Integer) it1.next();
			// if(productCol.contains(proID))
			int oldProID = ((Integer) it1.next()).intValue();
			LogUtility.log(clazz, LogLevel.DEBUG, "��Ʒ�ظ���鵱ǰ��ƷID:" + oldProID);
			boolean isFirst = false;
			while (it2.hasNext()) {
				int newProID = ((Integer) it2.next()).intValue();
				if (oldProID == newProID) {
					if (!isFirst)
						isFirst = true;
					else{
						throw new ServiceException("��Ʒ�ظ���ӡ�");
					}
				}
			}

		}
	}
	/**
	 * ���һ��ҵ���ʻ�����ͬ����������Ƿ��Ѿ�����
	 * @param inEvent
	 */
	public void checkVoice(VoiceEJBEvent inEvent)throws ServiceException {
			int serviceAccountID=inEvent.getVoiceFriendPhoneNoDTO().getServiceAccountID();
			String phoneNO=inEvent.getVoiceFriendPhoneNoDTO().getPhoneNo();
			try {
				VoiceFriendPhoneNoHome voiceFriendPhoneNoHome=HomeLocater.getVoiceFriendPhoneNoHome();	
				Collection col= voiceFriendPhoneNoHome.findByServiceAccountIDAndPhoneNO(serviceAccountID,phoneNO);
				if(!(col==null||col.isEmpty())){
					throw new ServiceException("��������ظ���ӣ�");
				}
			} catch (FinderException e1) {
				return;
			}catch(HomeFactoryException e2) {
				LogUtility.log(clazz,LogLevel.ERROR,"checkVoice",e2);
				throw new ServiceException("�������������ظ���ʱ��λ����");
			}
	}
	/**
	 * david.Yang
	 * @param customerProductID
	 * @param checkAction
	 * @throws ServiceException
	 */
	public void checkCampainByCpId(int customerProductID,int checkAction) throws ServiceException {
		checkCampainByCpId(customerProductID,checkAction,TimestampUtility.getCurrentDateWithoutTime());
	}
	/**
	 * ���޸�,���ӶԴ���ʱЧ�ж�,Ŀǰ�����ų̼��
	 * @param customerProductID
	 * @param checkAction
	 * @throws ServiceException
	 */
	public void checkCampainByCpId(int customerProductID,int checkAction,Timestamp checkDate) throws ServiceException {
	   LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��canAllowThisAction����Ż������Ƿ��������");
	   //��������ģ�͵ĸ���,��ǰ�����Ѿ�ʧЧ,����ɾ��,�µĴ����׷��
	   try{
		  CPCampaignMapHome cPCampaignMapHome = HomeLocater.getCPCampaignMapHome();
		  CustomerCampaignHome customerCampaignHome =HomeLocater.getCustomerCampaignHome();
	      Collection cPCampaignMapCol=cPCampaignMapHome.findByCustProductID(customerProductID);
	      if (cPCampaignMapCol!=null && !cPCampaignMapCol.isEmpty()){
	     	 Iterator cPCampaignMapIter=cPCampaignMapCol.iterator();
	    	 while(cPCampaignMapIter.hasNext()){
			     String currentAllow="";
			     CPCampaignMap cPCampaignMap=(CPCampaignMap)cPCampaignMapIter.next();
			     if (CommonConstDefinition.GENERALSTATUS_INVALIDATE.equals(cPCampaignMap.getStatus()))
			    	 continue;
			     CustomerCampaign  custCampaign = customerCampaignHome.findByPrimaryKey(new Integer(cPCampaignMap.getCcId()));
			     
			     //��ǰ�������������Ч״̬��
			     if (!CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL.equals(custCampaign.getStatus())
			    	 && !CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_TRANSFE.equals(custCampaign.getStatus()))
			    	 continue;
			     
			     //��ǰ�����ǹ��ڵ�,�����,������ǰѭ��.
			     LogUtility.log(clazz, LogLevel.DEBUG,"\n�ۡۡۡۡ�checkDate:"+ checkDate
			    		  +"\n�ۡۡۡۡ�custCampaign.getDateTo():"+ custCampaign.getDateTo()
			    		  +"\n�ۡۡۡۡ�custCampaign.getCcID():"+ custCampaign.getCcID().intValue());
			      if (checkDate.after(custCampaign.getDateTo())) 
			    	 continue;
			      
			      switch(checkAction){
				     case 1: //��Ʒ��ͣ
					   currentAllow=custCampaign.getAllowPause();
					   break;
				     case 2: //��Ʒȡ��
					   currentAllow=custCampaign.getAllowClose();
					   break;
				     case 3: //��Ʒ�ָ�
					   break;
				     case 4: //ҵ���ʻ�����
					   currentAllow=custCampaign.getAllowTransfer();
					   break;
				     case 5: //��ҵ���ʻ�
				       currentAllow=custCampaign.getAllowClose();
					   break; 
				     case 6: //�ͻ�Ǩ��
				       currentAllow=custCampaign.getAllowTransition();
					   break; 
				     case 7: //�ͻ�����
				       currentAllow=custCampaign.getAllowTransfer();
					   break; 
				     case 8: //�ͻ�����,�˻�
				       currentAllow=custCampaign.getAllowClose();
					   break; 
			      }
			      
			      if (checkAction ==3) continue;
			     			      
			      if (!"".equals(currentAllow) && currentAllow!=null){
					  if (currentAllow.equals(CommonConstDefinition.YESNOFLAG_NO) &&
						  (checkDate.before(custCampaign.getDateTo())||checkDate.equals(custCampaign.getDateTo())))
							throw new ServiceException("��ǰ��Ʒ���Ż����ڲ����������Ĳ���");
				  }
			      
			      if (checkAction !=4 && checkAction !=5) continue;
			      
                  // ֻ����ײʹ�����δ���ںͿ�ҵ���ʻ�
			      CampaignDTO campaignDto =BusinessUtility.getCampaignDTOByID(custCampaign.getCampaignID());
			      if (campaignDto ==null || campaignDto.getCampaignType() ==null) 
			    	  throw new ServiceException("��ǰ�Ż����������������Ա��ϵ��");
			      if (campaignDto.getCampaignType().equals(CommonConstDefinition.CAMPAIGNCAMPAIGNTYPE_NORMAL)) 
			    	  continue;
			      			      
			      if (!BusinessUtility.getCustCampaignAndServiceAccountID(custCampaign.getCustomerID(),custCampaign.getCcID().intValue())){
			    	   throw new ServiceException("��ǰ��Ʒ"+cPCampaignMap.getCustProductID()+"��Ӧ���ײͿ�ҵ���ʻ��������������Ĳ�����");
			      }
	    	   }
	    	}
	   }catch (HomeFactoryException e) {
		   LogUtility.log(clazz, LogLevel.ERROR, e);
		   throw new ServiceException("���CustomerProductHome��λ����");
       }catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���CustomerProduct�ܷ�ִ�и������ʱ�Ҳ��������Ϣ");	
        }
	   
	   
	   LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"��canAllowThisAction������");
	}
	/**
	 * ����Ƿ�����ȡ���ͻ���Ʒ������
	 * @param custCampaignID
	 * @param cutProductNeed
	 * @return 
	 * @throws ServiceException 
	 */
	public void checkCusCampaign(int CCID) throws ServiceException{
		
			if(BusinessUtility.checkCancleCamp(CCID))
				throw new ServiceException("�ͻ������Ѿ����ڻ�ͻ�����������Ч��");
	}
	public void checkCustomerCampaignPrePayment(int ccid) throws ServiceException{
		CustomerCampaignDTO ccDto=BusinessUtility.getCustomerCampaignDTOByID(ccid);
		if(!(CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NEW.equals(ccDto.getStatus())
				||CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL.equals(ccDto.getStatus()))){
			throw new ServiceException("�ͻ�����������Ч��");
		}
	}
	
	public void checkCustProtocolPackage(int customerid,Collection csiPackageList) throws ServiceException{
		Map protocolDtoMap =BusinessUtility.getProtocolDTOColByCustID(customerid);
		if (protocolDtoMap !=null && protocolDtoMap.size() >0){
			Iterator csiPackageItr  = csiPackageList.iterator();
			while(csiPackageItr.hasNext()){
				Integer packateID=(Integer)csiPackageItr.next();
				if (!protocolDtoMap.keySet().contains(packateID)){
					ProductPackageDTO dto =BusinessUtility.getProductPackageDTOByID(packateID.intValue());
					throw new ServiceException(dto.getPackageName()+"����Э�麭�Ƿ�Χ�ڣ��������Ա��ϵ��");
				}
			}
		}else{
			throw new ServiceException("�ÿͻ�����Э��ͻ����뵽�ײ����ѹ��������ѣ�");
		}
	}

	/**
	 * ��������ײͻ�Ƿ��ܹ�ת��
	 * 
	 * @param dto
	 * @throws ServiceException
	 */
	public void checkBundleTransfer(CustServiceInteractionDTO csiDTO,CustomerCampaignDTO ccDto, int campaignID,
			Map addProductMap, Collection cancelCpList,HashMap terminalMap) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "��ʼ���ײ�ת�����,ccDto"+ccDto);

		if (ccDto==null||ccDto.getCcID()==0||!CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL.equals(ccDto
				.getStatus())) {
			throw new ServiceException("��Ч�Ŀͻ��ײ�,��������ת��.");
		}
		Timestamp curDate = TimestampUtility.getCurrentDateWithoutTime();
		if (curDate.before(ccDto.getDateFrom())
				|| curDate.after(ccDto.getDateTo())) {
			throw new ServiceException("�ͻ��ײͲ�����Ч����,��������ת��.");
		}
		// �õ��û��ĵ�ַ
		CustomerDTO customer = BusinessUtility.getCustomerDTOByCustomerID(ccDto
				.getCustomerID());
		if (!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer
				.getStatus())) {
			throw new ServiceException("�ͻ�״̬��Ч.");
		}
		if (BusinessUtility.existUnpaidInvoice(ccDto.getAccountID())) {
			throw new ServiceException("�ײ͸����˻���δ���˵���");
		}
		if (campaignID == 0) {
			return;
		}
		CampaignDTO campaignDTO = BusinessUtility
				.getCampaignDTOByID(campaignID);
		if (campaignDTO.getCustTypeList() != null
				&& !"".equals(campaignDTO.getCustTypeList())) {
			if (campaignDTO.getCustTypeList().indexOf(
					customer.getCustomerType()) == -1)
				throw new ServiceException("�ͻ����Ͳ������ײ�������");
		}
		if (campaignDTO.getOpenSourceTypeList() != null
				&& !"".equals(campaignDTO.getOpenSourceTypeList())) {
			if (campaignDTO.getOpenSourceTypeList().indexOf(
					customer.getOpenSourceType()) == -1)
				throw new ServiceException("�ͻ�����Դ�����������ײ�������");
		}
		// ���ͻ����������Ƿ����ʹ�ø��ײ�
		Collection col = new ArrayList();
		col.add(new Integer(campaignID));
		checkCampaignByDistrict(col, customer.getAddressID());
		CommonBusinessParamDTO paramDTO = new CommonBusinessParamDTO();
		paramDTO.setCustomerID(ccDto.getCustomerID());
		paramDTO.setAccountID(ccDto.getAccountID());
		paramDTO.setServiceaccountID(ccDto.getServiceAccountID());

		if (addProductMap != null && !addProductMap.isEmpty()) {
			// ����ײ�/�����Բ�Ʒ���������Ƿ�����
			checkCampaignCondPackage(campaignID, addProductMap.values(), paramDTO);
			// ����ײ�/�����Բ�Ʒ�������Ƿ�����
			checkCampaignCondProduct(campaignID, addProductMap.keySet(), paramDTO);
			//���Ӳ��ƥ��.
			if(terminalMap!=null&&!terminalMap.isEmpty()){
				checkSelectTerminalDevice(csiDTO,terminalMap,addProductMap.keySet(),true);
			}
		}
		// ����ײ�/�������ײ�/�����������Ƿ�����
		ArrayList campaignArray = new ArrayList();
		campaignArray.add(new Integer(campaignID));
		checkCampaignCondCampaign(campaignID, campaignArray, paramDTO);
		// ����ײͶԲ�Ʒ�Ĺ����ظ�����
		// checkNoExitMultiCampToProduct( campaignID, addProductMap.values(),
		// paramDTO, productToCmpMap);

		LogUtility.log(clazz, LogLevel.DEBUG, "����Ʒ�Ƿ���Ա�ȡ��");
		// ����Ƿ����ȡ����Ʒ.
		// ��Ҫȡ���Ĳ�Ʒ����,���м��.
		if (cancelCpList != null && !cancelCpList.isEmpty()) {
			Map saPsidMap = BusinessUtility
					.splitCustomerProductWithServiceAccount(cancelCpList);
				CustomerProductHome customerProductHome;
				try {
					customerProductHome = HomeLocater.getCustomerProductHome();
				} catch (Exception e) {
					LogUtility.log(clazz, LogLevel.ERROR,e.getMessage());
					throw new ServiceException("������ȡ���Ŀͻ���Ʒ����.");
				}				
				for (Iterator sait = saPsidMap.keySet().iterator(); sait
						.hasNext();) {
					Integer said = (Integer) sait.next();
					ArrayList curCpList = (ArrayList) saPsidMap.get(said);
					checkProductOperationDependency(curCpList, said.intValue());
					//�ײͽ���ʱ������ȡ���ؼ���ҵ���ʻ���Ʒ.
					for (Iterator cpIt = curCpList.iterator(); cpIt.hasNext();) {
						Integer psid = (Integer) cpIt.next();
						CustomerProduct customerProduct;
						try {
							customerProduct = customerProductHome
									.findByPrimaryKey(psid);
						} catch (Exception e) {
							LogUtility.log(clazz, LogLevel.ERROR,e.getMessage());
							throw new ServiceException("������ȡ���Ŀͻ���Ʒ����.");
						}						
						ProductDTO pDto=BusinessUtility.getProductDTOByProductID(
								customerProduct.getProductID());
						if (customerProduct.getDeviceID() != 0
								&&CommonConstDefinition.YESNOFLAG_YES.equals(pDto.getNewsaFlag())) {
							throw new ServiceException("�ͻ���Ʒ("
									+ pDto
											.getProductName() + ")����ȡ��.");
						}
					}
				}
			
		}
	}
	/**
	 * ���ҵ���ʻ����ʻ���Ʒ�Ƿ������������
	 * @param dto
	 */
	public void checkGrantCampaign(CustomerCampaignDTO dto,boolean mustCheckMulti)throws ServiceException{
		try{
			//����û��Ƿ��ڴ�������
			Integer campaignID=new Integer(dto.getCampaignID());
			Collection col=new ArrayList();
			col.add(campaignID);
			//�õ��û��ĵ�ַ
			CustomerHome customerHome=HomeLocater.getCustomerHome();
			Customer customer=customerHome.findByPrimaryKey(new Integer(dto.getCustomerID()));
			CampaignDTO campaign = BusinessUtility.getCampaignDTOByID(campaignID.intValue());
			if(campaign.getCustTypeList()!=null && !"".equals(campaign.getCustTypeList())){
				if (campaign.getCustTypeList().indexOf(customer.getCustomerType()) == -1)
					throw new ServiceException("�ͻ����Ͳ��������������");
			}
			if(campaign.getOpenSourceTypeList()!=null && !"".equals(campaign.getOpenSourceTypeList())){
				if (campaign.getOpenSourceTypeList().indexOf(customer.getOpenSourceType()) == -1)
					throw new ServiceException("�ͻ���������Դ�������������������");
			}
			checkCampaignByDistrict(col,customer.getAddressID());
			
			CommonBusinessParamDTO paramDTO = new CommonBusinessParamDTO();
			paramDTO.setCustomerID(dto.getCustomerID());
			paramDTO.setAccountID(dto.getAccountID());
			paramDTO.setServiceaccountID(dto.getServiceAccountID());
			
			//���ҵ���ʻ��µĲ�Ʒ�Ƿ���������
			CampaignDTO campaignDTO=BusinessUtility.getCampaignDTOByID(campaignID.intValue());
			dto.setDateTo(TimestampUtility.getTimeEnd(dto.getDateFrom(),
					campaignDTO.getTimeLengthUnitType(),
					campaignDTO.getTimeLengthUnitNumber()));
			LogUtility.log(clazz, LogLevel.DEBUG, "startTime:"+dto.getDateFrom());
			LogUtility.log(clazz, LogLevel.DEBUG, "endTime:"+dto.getDateTo());

			//ҵ���ʻ�����Ĵ�������������û�ж���
			if(CommonConstDefinition.YESNOFLAG_NO.equals(campaignDTO.getObligationFlag())){
				if(dto.getServiceAccountID()==0){
					throw new ServiceException("�ô���ֻ������ҵ���ʻ���");
				}
				
				Map campaignArray=BusinessUtility.getCampaignIDByServiceAccountID(dto.getServiceAccountID());

				HashMap custProdMap=BusinessUtility.getProductIDAndPackageIDBySAID(dto.getServiceAccountID());

				checkCampaignCond(dto,paramDTO,campaignArray,custProdMap,mustCheckMulti);

			//�ʻ�����Ĵ���
			}else if(CommonConstDefinition.YESNOFLAG_YES.equals(campaignDTO.getObligationFlag())){
				if(dto.getAccountID()==0){
					throw new ServiceException("�ô���ֻ�������ʻ���");
				}
				Map campaignArray= BusinessUtility.getCampaignIDByAccountID(dto.getAccountID());
				HashMap custProdMap=BusinessUtility.getProductIDAndPackageIDByAccountID(dto.getAccountID());
				checkCampaignCond(dto,paramDTO,campaignArray,custProdMap,mustCheckMulti);
			}
		}catch (HomeFactoryException e) {
			   LogUtility.log(clazz, LogLevel.ERROR, e);
			   throw new ServiceException("���checkGrantCampaign��λ����");
		}catch(FinderException e) {
				LogUtility.log(clazz, LogLevel.ERROR, e);
				throw new ServiceException("���checkGrantCampaign�Ƿ������������Ҵ���");
		}
	}
	private void checkCampaignCond(CustomerCampaignDTO dto,CommonBusinessParamDTO paramDTO,
			Map campaignArray,HashMap custProdMap,
			boolean mustCheckMulti)throws ServiceException{
		dto.setDateFrom(TimestampUtility.ClearTimePart(dto.getDateFrom()));
		dto.setDateTo(TimestampUtility.ClearTimePart(dto.getDateTo()));
		LogUtility.log(clazz, LogLevel.DEBUG, "�µĴ���:",dto);
		if(mustCheckMulti&&campaignArray!=null&&!campaignArray.isEmpty()){
			for (Iterator it = campaignArray.keySet().iterator(); it.hasNext();) {
				CustomerCampaignDTO ccDto = (CustomerCampaignDTO) it.next();
				ccDto.setDateFrom(TimestampUtility.ClearTimePart(ccDto.getDateFrom()));
				ccDto.setDateTo(TimestampUtility.ClearTimePart(ccDto.getDateTo()));
				LogUtility.log(clazz, LogLevel.DEBUG, "�ȽϵĴ���:",ccDto);
				if ((dto.getDateFrom().after(ccDto.getDateFrom()) && dto
						.getDateFrom().before(ccDto.getDateTo()))
						|| (dto.getDateTo().after(ccDto.getDateFrom()) && dto
								.getDateTo().before(ccDto.getDateTo()))
						|| dto.getDateFrom().equals(ccDto.getDateFrom())
						|| dto.getDateTo().equals(ccDto.getDateTo())
						|| dto.getDateFrom().equals(ccDto.getDateTo())
						|| dto.getDateTo().equals(ccDto.getDateFrom())) {
					throw new ServiceException("�ô��������пͻ�����" + ccDto.getCcID()
							+ "��Ч���ص�.");
				}
			}
		}
		//����������.
		checkCampaignCondCampaign(dto.getCampaignID(), null,campaignArray.values(), paramDTO);
		// ����ײ�/�����Բ�Ʒ���������Ƿ�����
		checkCampaignCondPackage(dto.getCampaignID(), null,custProdMap.values(),paramDTO);
		// ����ײ�/�����Բ�Ʒ�������Ƿ�����
		checkCampaignCondProduct(dto.getCampaignID(), null,custProdMap.keySet(),paramDTO);
	} 
	public void checkVOIPProductExist(VOIPProductDTO dto,String checkType)throws ServiceException{
		VOIPProductDTO pdto=BusinessUtility.getVOIPProductDTOByProductIDAndSCode(dto.getProductID(),dto.getSssrvCode());
		if(pdto!=null&&checkType.equals("add"))
			throw new ServiceException("��������Ʒ�Ѵ��ڣ�");
		/*else if(pdto!=null&&checkType.equals("modify"))
			throw new ServiceException("�޸ĺ��������Ʒ�Ѵ��ڣ�");*/
	}
	public void checkVOIPConditionExist(VOIPConditionDTO dto,String checkType)throws ServiceException{
		VOIPConditionDTO pdto=BusinessUtility.getVOIPConditionDTOByConditionID(dto.getConditionID());
		if(pdto!=null&&checkType.equals("add"))
			throw new ServiceException("�����������Ѵ��ڣ�");
		/*else if(pdto!=null&&checkType.equals("modify"))
			throw new ServiceException("�޸ĺ�����������Ѵ��ڣ�");*/
	}
	public void checkVOIPGatewayExist(VOIPGatewayDTO dto,String checkType)throws ServiceException{
		VOIPGatewayDTO gdto=BusinessUtility.getVOIPGatewayDTOByPK(dto.getDeviceModel(),dto.getDevsType());
		if(gdto!=null&&checkType.equals("add"))
			throw new ServiceException("�����������Ѵ��ڣ�");
		/*else if(gdto!=null&&checkType.equals("modify"))
			throw new ServiceException("�޸ĺ�����������Ѵ���");*/
	}
	/**
	 * �����ͻ�Ͷ�ߴ����¼ʱ������ҵ����
	 * @param inEvent
	 * @throws ServiceException
	 */
	public boolean checkCustComplainCanProcess(CustComplainProcessDTO pdto)throws ServiceException{
		String status=BusinessUtility.getCustComplainStatusByPK(pdto.getCustComplainId());
		if(status.equals(CommonConstDefinition.CUSTOMER_COMPLAIN_SUCESS)||status.equals(CommonConstDefinition.CUSTOMER_COMPLAIN_FALI)){
			throw new ServiceException("��Ͷ���Ѵ�����ϣ�");
		}
		return true;
	}
	public static void main(String[] args) {
		try{
			ServiceContext serviceContext =new ServiceContext();
			BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
			Collection currentCol=new java.util.ArrayList();
			
			currentCol.add(new Integer(1));
			businessRuleService.checkPackageByDistrict(currentCol,1);
		}catch(ServiceException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ����ͬ
	 * fiona
	 * @param contractDTO
	 * @throws ServiceException 
	 */
	public  void checkOpenChildContract(ContractDTO contractDTO,int customerCount) throws ServiceException{
		if(contractDTO.getDateto().before(TimestampUtility.getCurrentDateWithoutTime()))
			throw new ServiceException("��ͬ�Ѿ����ڣ�");
		if(contractDTO.getUserCount()<contractDTO.getUsedCount()+customerCount)
			throw new ServiceException("�Ѿ��ﵽ����û��������ܼ���������");
		
	}
	/**
	 * @param contractDTO
	 * @param terminalDeviceTable
	 * @param returnCsiProduct
	 * @return
	 * @throws ServiceException
	 */
	public Collection checkOpenChildDevAndGetCsiProdList(CustServiceInteractionDTO csiDTO,ContractDTO contractDTO,HashMap terminalDeviceTable,boolean returnCsiProduct)throws ServiceException{
		Collection packagecol=BusinessUtility.getPackageIDByContractID(contractDTO.getContractNo());
		//ȡ�ò�Ʒ����Ӧ�����в�Ʒ��Ϣ
		Collection currentAllProductCol=new ArrayList();
		Iterator currentPackageIter=packagecol.iterator();
		while(currentPackageIter.hasNext()){
			Integer currentPackageID=(Integer)currentPackageIter.next();
			currentAllProductCol.addAll(BusinessUtility.getProductIDsByPackageID(currentPackageID.intValue())) ;
		}
		Collection returnCsiProdCol=checkSelDeviceAndReturnCsiProdInfo(csiDTO,terminalDeviceTable,currentAllProductCol,returnCsiProduct);
		return returnCsiProdCol;
	}
	/**
	 * ���ſͻ������ӿͻ�ʱ��Ч�Լ��,
	 * @param event
	 * @throws ServiceException
	 */
	public void checkCreateGroupSubCustomer(GroupCustomerEJBEvent event)throws ServiceException{
		try {
			//ȡ�ÿͻ�����Ĳ�Ʒ����Ϣ
			Collection csiPackageArray = event.getContractToPackageIDCol();
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
	    	//ȡ�ü��ſͻ���Ϣ
	    	int  customerID = event.getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("ֻ�������������ſͻ����������ӿͻ���");
	    	}
	    	//���ͻ�������Ч�� update chaoqiu 2007-04-10 =========
	    	Map allDistrict = BusinessUtility.getAllDistrictMap();
	    	if(!allDistrict.containsKey(event.getAddressDTO().getDistrictID()+""))
	    		throw new ServiceException("����������д����,�����ڸ÷�����");
	    	//���ͻ�������Ч�� update chaoqiu 2007-04-10 =========end
	    	
	    	//ȡ���˻���Ϣ
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("��ѡ����˻���δ���˵���");
	    	}
	    	//�ͻ���ַ��Ϣ
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());
			//Ӳ���豸��Ϣ
			//Hashtable terminalDeviceTable=event.getDeviceTable();
	    	HashMap terminalDeviceTable=event.getDeviceTable();
			//����Ƿ������Ҫ����ҵ���˻��Ĳ�Ʒ
			if(!checkExitCreateServiceAccountProduct(csiPackageArray)){
				throw new ServiceException("ѡ���Ʒ���в��������Դ���ҵ���ʻ��Ĳ�Ʒ��");
			}
			//����ײͺ��Żݴ�������
			CommonBusinessParamDTO paramDTO=new CommonBusinessParamDTO(customer.getCustomerType(),customer.getOpenSourceType(),event.getCsiDto().getType(),event.getOperatorID());
			checkPackageCampaign(csiPackageArray,null,custAddrDto,true,paramDTO);
			
			//ȡ�ò�Ʒ����Ӧ�����в�Ʒ��Ϣ
			Collection currentAllProductCol=new ArrayList();
			Iterator currentPackageIter=csiPackageArray.iterator();

			while(currentPackageIter.hasNext()){
				Integer currentPackageID=(Integer)currentPackageIter.next();
				currentAllProductCol.addAll(BusinessUtility.getProductIDsByPackageID(currentPackageID.intValue())) ;
			}
			//����Ʒ֮���������
			checkProductDependency(currentAllProductCol);
			
			//�����ѡ��Ӳ���豸����ѡӲ���豸�Ͳ�Ʒ��ӳ���ϵ�Ƿ���ȷ
			checkSelectTerminalDevice(event.getCsiDto(), terminalDeviceTable,currentAllProductCol,true);
		
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ���ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ�����Ҳ��������Ϣ");
			
		}
		
	}
	/**
	 * ���ſͻ��ӿͻ�����ʱ����ͬ��Ч��
	 * @param contractNo
	 * @throws ServiceException
	 */
	public void checkGroupCustomerContract(String contractNo,
			boolean isOnlyForGroupCust, int groupCustID, int custCount)
			throws ServiceException {
			ContractDTO dto = BusinessUtility.getContractDTOByContractNo(
					contractNo, true);
			if (isOnlyForGroupCust) {
				if (!CommonConstDefinition.CONTRACTSTATUS_EFFECT.equals(dto
						.getStatus())) {
					throw new ServiceException("ֻ��״̬��Ч�ĺ�ͬ���ܽ��м��ſ���");
				}
				if (TimestampUtility.getCurrentDateWithoutTime().after(
						dto.getDateto())) {
					throw new ServiceException("��ͬ�Ѿ�����ʧЧ");
				}
				if (TimestampUtility.getCurrentDateWithoutTime().before(
						dto.getDatefrom())) {
					throw new ServiceException("��ͬ��δ������ʹ�õ���Ч����");
				}
				if (TimestampUtility.getCurrentDateWithoutTime().after(
						dto.getNormaldate())) {
					throw new ServiceException("��ͬ�Ѿ�ʧЧ,������󿪻���ֹ����");
				}
			} else {
				if (!CommonConstDefinition.CONTRACTSTATUS_EFFECT.equals(dto
						.getStatus())
						&& !CommonConstDefinition.CONTRACTSTATUS_OPEN
								.equals(dto.getStatus())) {
					throw new ServiceException("ֻ��״̬��Ч�Ϳ����ĺ�ͬ���ܽ��м����ӿͻ�����/��������");
				}
				if (CommonConstDefinition.CONTRACTSTATUS_OPEN.equals(dto
						.getStatus())) {
					if (groupCustID != dto.getUsedCustomerID()) {
						throw new ServiceException("�ú�ֻͬ�����ڸü��ſͻ������ſͻ�ID:"
								+ dto.getUsedCustomerID());
					}
				}
				if (custCount + dto.getUsedCount() > dto.getUserCount()) {
					throw new ServiceException("�û����Ѿ�������ͬ�涨����ͬ�涨��:"
							+ dto.getUserCount());
				}
			}

	}
	/**
	 
	 * �ͻ�������Ϣ����ȷ��
	 * @param event
	 * @throws ServiceException
	 */
	public void checkOpenInfoForDirectly(BookEJBEvent event) throws ServiceException{
    	//ȡ���¿ͻ���Ϣ
    	NewCustomerInfoDTO newCustInfo = event.getNewCustInfo();
    	//ȡ���¿ͻ����˻���Ϣ
    	NewCustAccountInfoDTO newCustAcctInfo = event.getNewCustAcctInfo();
    	//ȡ��������Ϣ
		CustServiceInteractionDTO csiDTO=event.getCsiDto();
		//ȡ�ÿͻ���ַ��Ϣ
		AddressDTO custAddrDto = event.getCustAddressDTO();
		//ȡ���˻���ַ��Ϣ
		AddressDTO acctAddrDto = event.getAcctAddressDTO();
		
		//��ʼ���ͻ�
		if (newCustInfo == null) 
			throw new ServiceException("û�пͻ���Ϣ��");
		//��ʼ����˻�
		if (newCustAcctInfo == null)
			throw new ServiceException("û���˻���Ϣ��");
		//��ʼ�������
		if (csiDTO == null)
			throw new ServiceException("û��������Ϣ��");
		//��ʼ���ͻ���ַ
		if ((custAddrDto == null) && acctAddrDto==null)
			throw new ServiceException("û�пͻ���ַ��Ϣ��");
		//��ʼ���ͻ��˻���ַ		
		if ((custAddrDto == null) && acctAddrDto==null)
			throw new ServiceException("û�пͻ��˻���Ϣ��");
	}
	/**
	 * ����˻�
	 * @param accountID
	 * @param futureRightID
	 * @throws ServiceException
	 */
	public void checkGrantFutureRight(int accountID,int futureRightID)throws ServiceException{
		if (accountID ==0 ||futureRightID==0)
			throw new ServiceException("û���˻���Ϣ������Ȩ��Ϣ");
		
		AccountDTO accountDTO=BusinessUtility.getAcctDTOByAcctID(accountID);
		if (accountDTO==null)
			throw new ServiceException("���������˻���");
		if (BusinessUtility.existUnpaidInvoice(accountID))
			throw new ServiceException("����δ���˵���");		
		try{
			FutureRightHome futureRightHome = HomeLocater.getFutureRightHome();
			FutureRight  futureRight =futureRightHome.findByPrimaryKey(new Integer(futureRightID));
			if(futureRight.getLockToDate().after(TimestampUtility.getCurrentTimestamp()))
				throw new ServiceException("����Ȩ���������ڲ�������֣�");
		}catch(HomeFactoryException ex) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create",ex);
		    throw new ServiceException("�����Ȩʱ��λ����");
		 }catch(FinderException e1) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create", e1);
		    throw new ServiceException("�����Ȩʱʱ����");		    
		}
	}
	
	/**
	 * ����������������
	 * @param campaignID
	 * @param campaignArray
	 * @param param
	 * @throws ServiceException
	 */
	private void checkCampaignCondCampaign(int campaignID,Collection campaignArray,CommonBusinessParamDTO param)throws ServiceException{
		//�ü����Ǹ��ݿͻ�id,�ʻ�id,ҵ���ʻ�id����Ϊ�������������ģ�oldcampaignColColΪ���εĶ����б�
		Collection oldcampaignColCol=BusinessUtility.getCustCampaignIDListByCustInfo(
				param.getCustomerID(), param.getAccountID(), param.getServiceaccountID());
		checkCampaignCondCampaign( campaignID, campaignArray, oldcampaignColCol, param);
	}
	private void checkCampaignCondCampaign(int campaignID,Collection newCampaignArray,Collection oldCampaignArray,CommonBusinessParamDTO param)throws ServiceException{
		Collection condCampaignCol=BusinessUtility.getCampaignCondCampaignDTOListByCampaignID(campaignID);
		if(condCampaignCol!=null && !condCampaignCol.isEmpty()){
			Iterator  condCampaignIter=condCampaignCol.iterator();
			while(condCampaignIter.hasNext()){
				CampaignCondCampaignDTO  condCampaignDTO=(CampaignCondCampaignDTO)condCampaignIter.next();
				Collection campaignCol=PublicService.stringSplitToTargetObject(condCampaignDTO.getCampaignIDList(),",","Integer");
				//����¹�����ײ�/��������ײ�/��������
				if(CommonConstDefinition.YESNOFLAG_YES.equals(condCampaignDTO.getNewCaptureFlag())){
					checkIncludeRealation(condCampaignDTO.getCampaignID(),condCampaignDTO.getHasAllFlag(),condCampaignDTO.getCampaignNum(),newCampaignArray,campaignCol,"����");
				}
				//������е��ײ�/��������ײ�/��������
				if(CommonConstDefinition.YESNOFLAG_YES.equals(condCampaignDTO.getExistingFlag())){
					checkIncludeRealation(condCampaignDTO.getCampaignID(),condCampaignDTO.getHasAllFlag(),condCampaignDTO.getCampaignNum(),oldCampaignArray,campaignCol,"����");
				}
			}
		}
	}
	/**
	 * �������Բ�Ʒ����
	 * @param campaignID
	 * @param packageArray
	 * @param param
	 * @throws ServiceException
	 */
	private void checkCampaignCondProduct(int campaignID,Collection newPackageArray,CommonBusinessParamDTO param )throws ServiceException{
		//�ü����Ǹ��ݿͻ�id,�ʻ�id,ҵ���ʻ�id����Ϊ�������������ģ�oldProductColΪ���ζ����б�
		Collection oldProductCol=BusinessUtility.getCustProductIDListByCustInfo(
				param.getCustomerID(), param.getAccountID(), param.getServiceaccountID());
		Collection newProductCol=new ArrayList();
		if(newPackageArray!=null && !newPackageArray.isEmpty()){
			Iterator packageIter=newPackageArray.iterator();
			while(packageIter.hasNext()){
				Integer packageid=(Integer)packageIter.next();
				newProductCol.addAll(BusinessUtility.getProductIDsByPackageID(packageid.intValue()));
			}
		}
		checkCampaignCondProduct( campaignID, newProductCol, oldProductCol, param );
	}
	private void checkCampaignCondProduct(int campaignID,Collection newPackageArray,Collection oldPackageArray,CommonBusinessParamDTO param )throws ServiceException{
		Collection condProductCol=BusinessUtility.getCampaignCondProductDTOListByCampaignID(campaignID);
		if(condProductCol!=null && !condProductCol.isEmpty()){
			Iterator  condProductIter=condProductCol.iterator();
			while(condProductIter.hasNext()){
				CampaignCondProductDTO  condProductDTO=(CampaignCondProductDTO)condProductIter.next();
				Collection productCol=PublicService.stringSplitToTargetObject(condProductDTO.getProductIdList(),",","Integer");
				//����¹���Ĳ�Ʒ����ײ�/��������
				if(CommonConstDefinition.YESNOFLAG_YES.equals(condProductDTO.getNewCaptureFlag())){
					checkIncludeRealation(condProductDTO.getCampaignID(),condProductDTO.getHasAllFlag(),condProductDTO.getProductNum(),newPackageArray,productCol,"��Ʒ");
				}
				//������еĲ�Ʒ����ײ�/��������
				if(CommonConstDefinition.YESNOFLAG_YES.equals(condProductDTO.getExistingFlag())){
					checkIncludeRealation(condProductDTO.getCampaignID(),condProductDTO.getHasAllFlag(),condProductDTO.getProductNum(),oldPackageArray,productCol,"��Ʒ");
				}
			}
		}
		
	}
	
	/**
	 * ����Ʒ�����������Ʒ����İ�����ϵ
	 * @param hasAllFlag
	 * @param num
	 * @param targetArray
	 * @param condArray
	 * @param checkDesc
	 * @throws ServiceException
	 */
	private void checkIncludeRealation(int campaignID,String hasAllFlag,int num,Collection targetArray,Collection condArray,
			String checkDesc)throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"����������"+campaignID); 
		LogUtility.log(clazz,LogLevel.DEBUG,"����Ŀ���Ʒ����"+targetArray); 
		LogUtility.log(clazz,LogLevel.DEBUG,"����������Ʒ����"+condArray); 
		if(CommonConstDefinition.YESNOFLAG_YES.equals(hasAllFlag)){
			 if(targetArray==null || targetArray.isEmpty())
				 targetArray=new ArrayList();
			 if(!targetArray.containsAll(condArray))
				 throw new ServiceException("��ѡ�ײ�/������"+checkDesc+"��������������");
		 }else if(CommonConstDefinition.YESNOFLAG_NO.equals(hasAllFlag)){
			 if(targetArray==null || targetArray.isEmpty())
				 targetArray=new ArrayList();;
			 if(condArray==null && condArray.isEmpty())
				 return;
			 int count=0;
			 Iterator condIter=condArray.iterator();
			 while(condIter.hasNext()){
				 Integer condID=(Integer)condIter.next();
				 if(targetArray.contains(condID))
					 count++;
			 }
			 if(count<num)
				 throw new ServiceException("�ײ�/������"+checkDesc+"��͸�������������");
		 }else{
			 throw new ServiceException("�ײ�/������"+checkDesc+"������������");
		 }
	}
	/**
	 * �������Բ�Ʒ������
	 * @param campaignID
	 * @param packageArray
	 * @param param
	 * @throws ServiceException
	 */
	private void checkCampaignCondPackage(int campaignID,
			Collection packageArray, CommonBusinessParamDTO param)
			throws ServiceException {
		// �ü����Ǹ��ݿͻ�id,�ʻ�id,ҵ���ʻ�id����Ϊ�������������ģ�oldProductColΪ���ζ����б�
		Collection oldPackageCol = BusinessUtility
				.getCustProductPackageIDListByCustInfo(param.getCustomerID(),
						param.getAccountID(), param.getServiceaccountID());
		checkCampaignCondPackage(campaignID, packageArray, oldPackageCol, param);
	}

	private void checkCampaignCondPackage(int campaignID,
			Collection newPackageArray, Collection oldPackageArray,
			CommonBusinessParamDTO param) throws ServiceException {
		Collection condPackageCol = BusinessUtility
				.getCampaignCondPackageDTOListByCampaignID(campaignID);
		if (condPackageCol != null && !condPackageCol.isEmpty()) {
			Iterator condPackageIter = condPackageCol.iterator();
			while (condPackageIter.hasNext()) {
				CampaignCondPackageDTO condPackageDTO = (CampaignCondPackageDTO) condPackageIter
						.next();
				Collection packageCol = PublicService
						.stringSplitToTargetObject(condPackageDTO
								.getPackageIdList(), ",", "Integer");
				// ����¹���Ĳ�Ʒ������ײ�/��������
				if (CommonConstDefinition.YESNOFLAG_YES.equals(condPackageDTO
						.getNewPurchaseFlag())) {
					checkIncludeRealation(condPackageDTO.getCampaignID(),
							condPackageDTO.getHasAllFlag(), condPackageDTO
									.getPackageNum(), newPackageArray,
							packageCol, "��Ʒ��");
				}
				// ������еĲ�Ʒ������ײ�/��������
				if (CommonConstDefinition.YESNOFLAG_YES.equals(condPackageDTO
						.getExistingFlag())) {
					checkIncludeRealation(condPackageDTO.getCampaignID(),
							condPackageDTO.getHasAllFlag(), condPackageDTO
									.getPackageNum(), oldPackageArray,
							packageCol, "��Ʒ��");
				}
			}
		}
	}
	/**
	 * ������鹺���е�һ����Ʒֻ������һ����Բ�Ʒ���ײ�/����
	 * @param campaignID
	 * @param packageArray
	 * @param paramDTO
	 * @param productToCmpMap
	 * @throws ServiceException
	 */
	private void checkNoExitMultiCampToProduct(Integer campaignID,Collection  packageArray, CommonBusinessParamDTO paramDTO,Map productToCmpMap) throws ServiceException{ 
		if(packageArray==null && packageArray.isEmpty())
			return;
		Iterator packageIter=packageArray.iterator();
		while(packageIter.hasNext()){
			Integer packageID=(Integer)packageIter.next();
			Collection productCol=BusinessUtility.getProductIDsByPackageID(packageID.intValue());
			Iterator prodIter=productCol.iterator();
			while(prodIter.hasNext()){
				Integer productID=(Integer)prodIter.next();
				Collection campaignCol=(Collection)productToCmpMap.get(productID);
				if(campaignCol!=null && !campaignCol.isEmpty())
					throw new ServiceException("�����ͬһ��Ʒ����ͬʱ�������������ϵ��ײ�/����");
				campaignCol=new ArrayList();
				campaignCol.add(campaignID);
				productToCmpMap.put(productID, campaignCol);
			}
		}
	}
	/**
	 * �������ͻ��������Ƿ��Ѿ�����
	 * @param custSerialNo
	 * @throws ServiceException
	 */
	public static void checkEixtMultCustSerialNo(String custSerialNo) throws ServiceException{
		if(custSerialNo!=null && !"".equals(custSerialNo)){
			String value=BusinessUtility.getSystemSettingValue("SET_V_CHECK_CUSTSERIALNO");
			if("Y".equals(value)){
				int custCount=BusinessUtility.getCustomerCount(custSerialNo);
				if(custCount!=0)
					throw new ServiceException("�ͻ��������Ѿ����ڣ���ȷ�ϡ�");
			}
		}
	}
	public static void checkEixtMultCustSerialNoForExistCust(int customerID,String custSerialNo)throws ServiceException{
		CustomerDTO customerDTO=BusinessUtility.getCustomerDTOByCustomerID(customerID);
		if(custSerialNo!=null && !"".endsWith(custSerialNo)&& !custSerialNo.equals(customerDTO.getCustomerSerialNo()))
			checkEixtMultCustSerialNo(custSerialNo);
	}
	public static void checkCreateCAWallet(CAWalletEJBEvent inEvent) throws ServiceException{
		CAWalletDTO caDto=inEvent.getCawDto();
		LogUtility.log(clazz,LogLevel.DEBUG,"����caDto��"+caDto); 
		int exist=BusinessUtility.getCAWalletNumInSA(caDto.getServiceAccountId(), caDto.getCaWalletCode());
		if(exist>0){
			throw new ServiceException("��Ǯ���Ѵ���,������ͬһҵ���ʻ����ظ�������ͬǮ��.");
		}
		ArrayList requiredCADList = BusinessUtility.getRequiredCAWalletList();
		LogUtility.log(clazz,LogLevel.DEBUG,"����requiredCADList��"+requiredCADList); 
		for(int i=0;requiredCADList!=null&&i<requiredCADList.size();i++){
			CAWalletDefineDTO cadDto=(CAWalletDefineDTO) requiredCADList.get(i);
			int inexist=BusinessUtility.getCAWalletNumInSA(caDto.getServiceAccountId(), cadDto.getCaWalletCode());
			if(inexist<1&&!cadDto.getCaWalletCode().equals(caDto.getCaWalletCode())){
				throw new ServiceException("�����ȴ��� "+cadDto.getCaWalletName()+" .");
			}
		}
	}
	
	//����豸��Թ�ϵ�����ڿ�����������
	public static void checkDeviceMatch(Map deviceTable)
			throws ServiceException {
		// ϵͳ�Ƿ�����豸���
		if(!BusinessUtility.isSystemManagerDeviceMatch())
			return ;

		LogUtility.log(clazz, LogLevel.DEBUG, "��������豸��Թ�ϵ�����ڿ������������� ��ʼ");
		LogUtility.log(clazz, LogLevel.DEBUG, "����deviceTable��" + deviceTable);
		String serialNoSTB = "";
		String serialNoSC = "";
		if (deviceTable != null && !deviceTable.isEmpty()){
			// ȡ������Ӳ���豸���������к�
			Set currentKeySet = deviceTable.keySet();
			String currntSerialNo = "";
			String currentDeviceClass = "";
			Iterator currentDeviceIter = currentKeySet.iterator();
			while (currentDeviceIter.hasNext()) {
				// ȡ������Ӳ���豸���������к�
				currntSerialNo = (String) currentDeviceIter.next();
				// ȡ�ø�Ӳ���豸���кŶ�Ӧ���豸����
				currentDeviceClass = (String) deviceTable.get(currntSerialNo);
				if (CommonConstDefinition.DEVICECALSS_STB.equals(currentDeviceClass))
					serialNoSTB = currntSerialNo;
				else if (CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(currentDeviceClass))
					serialNoSC = currntSerialNo;
			}
		}
		// ��ǰ�豸�Ƿ����(ֻ���STB,SC)
		if(!BusinessUtility.isDeviceMatch(serialNoSTB,serialNoSC))
			return ;
		// ��Թ�ϵ�Ƿ�ָ��Է�(ֻ���STB,SC)
		if(!BusinessUtility.isDeviceMatchRelationship(serialNoSTB,serialNoSC)){
			throw new ServiceException("��ѡ�豸��Թ�ϵ����ȷ��");
		}
	}
	
	//����豸��Թ�ϵ�������豸������
	public static void checkDeviceMatch(String newSeaialNo)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "����newSeaialNo��" + newSeaialNo);
		// ϵͳ�Ƿ�����豸���
		if (!BusinessUtility.isSystemManagerDeviceMatch()){
			return;
		}
		// ��ǰ�豸�Ƿ����(ֻ���STB,SC)
		if (BusinessUtility.isDeviceMatch(newSeaialNo)) {
			throw new ServiceException("��ѡ�豸��Թ�ϵ����ȷ��");
		} else {
			return;
		}
	}
	

}
