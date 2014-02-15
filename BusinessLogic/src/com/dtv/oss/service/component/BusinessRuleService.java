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
 *  业务逻辑检查的组件类
 */
public class BusinessRuleService {
	private static final Class clazz = BusinessRuleService.class; 
	private ServiceContext serviceContext=null;

	public BusinessRuleService(ServiceContext serviceContext){
		this.serviceContext=serviceContext;
	}
	/**
	 * 检查帐户调帐的有效性,
	 * @param accountAdjustmentDTO 调帐记录,必须封装有效的ReferRecordType信息,方法内依赖这个属性来区分调帐类型.
	 * @param accountItemDTO 费用记录,
	 * @param paymentRecordDTO
	 * @param prepaymentDeductionRecordDTO
	 * @throws ServiceException
	 */
	public void checkAccountAdjustment(
			AccountAdjustmentDTO accountAdjustmentDTO,
			AccountItemDTO accountItemDTO, PaymentRecordDTO paymentRecordDTO,
			PrepaymentDeductionRecordDTO prepaymentDeductionRecordDTO) throws ServiceException {
		String type =accountAdjustmentDTO.getReferRecordType();
		LogUtility.log(clazz,LogLevel.DEBUG,"调帐类型:"+type);

		int accountID=accountAdjustmentDTO.getAcctID();
		AccountDTO account=BusinessUtility.getAcctDTOByAcctID(accountID);
		if(CommonConstDefinition.ACCOUNT_STATUS_CLOSE.equals(account.getStatus())){
			throw new ServiceException("关闭的帐户不能进行调帐!");
		}
		if(CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_F.equals(type)){
			double sumFee=BusinessUtility.getNoInvoicedAccountItemByAccountID(accountID);
			LogUtility.log(clazz,LogLevel.DEBUG,"帐户:"+accountID+",未销帐的总的费用:"+sumFee);
			double sumPayment=BusinessUtility.getNoInvoicedPaymentRecordByAccountID(accountID);
			LogUtility.log(clazz,LogLevel.DEBUG,"帐户:"+accountID+",未销帐的总的支付:"+sumPayment);
			double sumPrePaymentDeduction=BusinessUtility.getNoInvoicedPrePaymentDeductionRecordByAccountID(accountID);
			LogUtility.log(clazz,LogLevel.DEBUG,"帐户:"+accountID+",未销帐的总的抵扣:"+sumPrePaymentDeduction);
			if(accountItemDTO.getValue()+sumFee-sumPayment-sumPrePaymentDeduction<0){
				LogUtility.log(clazz,LogLevel.DEBUG,"帐户:"+accountID+",检查的值:"+(accountItemDTO.getValue()+sumFee-sumPayment-sumPrePaymentDeduction));
				throw new ServiceException("调帐金额 + 未入账的费用 - 未入账的支付（非预存）- 未入账的预存抵扣记录必须大于等于零!");
			}
		}else if (CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_P.equals(type)){
			double adjustAmount=paymentRecordDTO.getAmount();
			double cashDeposit=account.getCashDeposit();
			double tokenDeposit=account.getTokenDeposit();
			String prePaymentType=paymentRecordDTO.getPrepaymentType();
			if(CommonConstDefinition.PREPAYMENTTYPE_CASH.equals(prePaymentType)){
				if(cashDeposit+adjustAmount<0)throw new ServiceException("调帐后帐户的现金预存不能小于零!");
			}else if(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY.equals(prePaymentType)){
				if(tokenDeposit+adjustAmount<0)throw new ServiceException("调帐后帐户的虚拟货币预存不能小于零!");
			}else{
				throw new ServiceException("无效的支付方式");
			}

		}else if (CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_C.equals(type)){
			double sumFee=BusinessUtility.getNoInvoicedAccountItemByAccountID(accountID);
			double sumPayment=BusinessUtility.getNoInvoicedPaymentRecordByAccountID(accountID);
			double sumPrePaymentDeduction=BusinessUtility.getNoInvoicedPrePaymentDeductionRecordByAccountID(accountID);
			if(sumFee-sumPayment-sumPrePaymentDeduction-paymentRecordDTO.getAmount()<0){
				throw new ServiceException("未入账的费用-未入账的支付（非预存）- 未入账的预存抵扣记录 – 调帐金额必须大于等于零!");
			}
		}else if (CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_D.equals(type)){
			
		}else{
			throw new ServiceException("未知的调帐类型!!");
		}
	}
/**
 * 检查帐单调帐的有效性,
 * @param invoiceNo 帐单号,
 * @param amount 调帐金额
 * @param accountAdjustmentDTO 调帐记录,必须封装有效的ReferRecordType信息,方法内依赖这个属性来区分调帐类型.
 * @throws ServiceException
 */
	public void checkBillAdjustment(int invoiceNo,
			double amount,
			AccountAdjustmentDTO accountAdjustmentDTO
			) throws ServiceException {
		String type =accountAdjustmentDTO.getReferRecordType();
		LogUtility.log(clazz,LogLevel.DEBUG,"调帐类型:"+type);

		int accountID=accountAdjustmentDTO.getAcctID();
		AccountDTO account=BusinessUtility.getAcctDTOByAcctID(accountID);
		if(CommonConstDefinition.ACCOUNT_STATUS_CLOSE.equals(account.getStatus())){
			throw new ServiceException("关闭的帐户不能进行调帐!");
		}
		InvoiceDTO invoiceDto=BusinessUtility.getInvoiceDTOByInvoiceNO(invoiceNo);
		if(CommonConstDefinition.INVOICE_STATUS_PAID.equals(invoiceDto.getStatus())){
			throw new ServiceException("已经支付的帐单不能调帐!");
		}
		if(CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_F.equals(type)){
			if(invoiceDto.getAmount()+amount<0){
				throw new ServiceException("账单待付金额加调帐金额必须大于等于零!");
			}
		}else if (CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_P.equals(type)||
				CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_C.equals(type)){
			if(invoiceDto.getAmount()-amount<0){
				throw new ServiceException("账单待付金额减调帐金额必须大于等于零!");
			}
		}else if (CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_D.equals(type)){
			
		}else{
			throw new ServiceException("未知的调帐类型!!");
		}
	}
	/**
	 * 约束: 调帐金额(费用) + 费用 – 支付 – 调帐金额(支付) >= 0. 调帐方向只允许”应收费用、实收支付”
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
		//如果调应收
		if(CommonConstDefinition.ADJUST_REFERCODETYPE_F.equals(accountAdjustmentDTO.getReferRecordType())){
			if(amount+feeSum-paymentSum<0){
				throw new ServiceException("调帐金额(费用) + 费用 – 支付 必须大于等于零!");
			}
		}else if(CommonConstDefinition.ADJUST_REFERCODETYPE_C.equals(accountAdjustmentDTO.getReferRecordType())){
			if(feeSum-paymentSum-amount<0){
				throw new ServiceException("费用 – 支付 - 调帐金额(费用) 必须大于等于零!");
			}
		}else{
			throw new ServiceException("未知的操作!");
		}
	}
	public void checkCSIAdjustment(int csiID,int accountID,double amount,
			AccountAdjustmentDTO accountAdjustmentDTO
			) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "checkCSIAdjustment>>>>>>>>>>>");
		
		CustServiceInteractionDTO csiDto=BusinessUtility.getCSIDTOByID(csiID);
//		if(!CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT.equals(csiDto.getStatus())){
//			throw new ServiceException("只有待处理的受理单才可以调帐!");
//		}
		if(!CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY.equals(csiDto.getPaymentStatus())){
			throw new ServiceException("只有未付费的受理单才可以调帐!");
		}
		//费用和支付只检验当前选择帐户的
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
		
		//如果调应收
		if(CommonConstDefinition.ADJUST_REFERCODETYPE_F.equals(accountAdjustmentDTO.getReferRecordType())){
			if(amount+feeSum-paymentSum<0){
				throw new ServiceException("调帐金额(费用) + 费用 – 支付 必须大于等于零!");
			}
		}else if(CommonConstDefinition.ADJUST_REFERCODETYPE_C.equals(accountAdjustmentDTO.getReferRecordType())){
			if(feeSum-paymentSum-amount<0){
				throw new ServiceException("费用 – 支付 - 调帐金额(费用) 必须大于等于零!");
			}
		}else{
			throw new ServiceException("未知的操作!");
		}
	}	
	/**
	 * 检查受理单调帐
	 * 
	 * 这个方法准备删除掉,没用了,
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
			throw new ServiceException("该状态下的报修单不能进行调帐操作");
		}
		String unCondition=" SETOFFFLAG ='"+CommonConstDefinition.SETOFFFLAG_W+"' AND ACCTID="+accountID;
		String haveCondition=" SETOFFFLAG ='"+CommonConstDefinition.SETOFFFLAG_D+"' AND ACCTID="+accountID;
		String cpCond= "  AND  REFERTYPE='"+CommonConstDefinition.FTREFERTYPE_P+"' AND REFERID="+id;
		//未销账的费用总额
		double unSetFeeAmont=BusinessUtility.getSumFeeValueByCond(unCondition+cpCond,null);
		//已销账费用总额
		double haveSetFeeAmont=BusinessUtility.getSumFeeValueByCond(haveCondition+cpCond,null);
		
		if(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT.equals(cpDTO.getStatus())){
			if(unSetFeeAmont<0){
				throw new ServiceException("未销账的费用总额必须大于等于0");
			}
		}else if(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS.equals(cpDTO.getStatus())){ 
			if(haveSetFeeAmont<0){
				throw new ServiceException("已销账的费用总额必须大于等于0");
			}
		}
		//费用总和
		double feeTotal=0;
		if(feeAdjustlist!=null && !feeAdjustlist.isEmpty()){
			Iterator feeAdjustIter=feeAdjustlist.iterator();
			while(feeAdjustIter.hasNext()){
				FeeAdjustmentWrapDTO currentFeeWrap=(FeeAdjustmentWrapDTO)feeAdjustIter.next();
				AccountItemDTO accountItemDTO=currentFeeWrap.getAccountItemDTO();
				feeTotal+=accountItemDTO.getValue();
			}
		}
		//总金额
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
				throw new ServiceException("调账产生的费用金额和报修单上未销账的费用的待销账金额之和必须大于等于0");
			}
		}else if(CommonConstDefinition.SETOFFFLAG_D.equals(type)){
			if(feeTotal+haveSetFeeAmont<0){
				throw new ServiceException("调账产生的费用金额和报修单上已销账的费用的已销账金额之和必须大于等于0");
			}
			//if(totalPay!=feeTotal){
			if(Math.abs(totalPay - feeTotal)>0.0001){
				throw new ServiceException("调账产生的费用金额和调账产生的支付金额之和必须大于等于0");
			}
		}
	}
	
	
	/**
	 * 检查帐单调帐
	 * * 这个方法准备删除掉,没用了,
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
				throw new ServiceException("该状态下的帐单不能进行调帐操作");
			}
		}else if(CommonConstDefinition.SETOFFFLAG_D.equals(type)){
			if(!CommonConstDefinition.INVOICE_STATUS_PAID.equals(invoiceDTO.getStatus())&&
					!CommonConstDefinition.INVOICE_STATUS_WAIT.equals(invoiceDTO.getStatus())){
				throw new ServiceException("该状态下的帐单不能进行调帐操作");
			}
			if(CommonConstDefinition.INVOICE_STATUS_WAIT.equals(invoiceDTO.getStatus())){
				//账单的支付状态是 已付 或者 待付但存在已经部分支付的费用
				if(invoiceDTO.getFeeTotal()<=invoiceDTO.getPaymentTotal()){
					throw new ServiceException("该帐单没有已支付的费用");
				}
			}
		}
		String unCondition=" SETOFFFLAG ='"+CommonConstDefinition.SETOFFFLAG_W+"' AND ACCTID="+accountID+ " AND INVOICEDFLAG='"+CommonConstDefinition.YESNOFLAG_YES+"' AND INVOICENO="+id;
		String haveCondition=" SETOFFFLAG ='"+CommonConstDefinition.SETOFFFLAG_D+"' AND ACCTID="+accountID+" AND INVOICEDFLAG='"+CommonConstDefinition.YESNOFLAG_YES+"' AND INVOICENO="+id;
		String invoiceCond= " INVOICEDFLAG='"+CommonConstDefinition.YESNOFLAG_YES+"' AND INVOICENO="+id+" AND ACCTID="+accountID;
		//未销账的费用总额
		double unSetFeeAmont=BusinessUtility.getSumFeeValueByCond(unCondition,null);
		//已销账费用总额
		double haveSetFeeAmont=BusinessUtility.getSumFeeValueByCond(haveCondition,null);
		//部分销账未销账费用
		double partunSetFeeAmont=BusinessUtility.getSumFeeValueByCond(invoiceCond,CommonConstDefinition.SETOFFFLAG_W);
		//部分销账以销账费用
		double parthaveSetFeeAmont=BusinessUtility.getSumFeeValueByCond(invoiceCond,CommonConstDefinition.SETOFFFLAG_D);
		//费用总和
		double feeTotal=0;
		//总金额
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
					throw new  ServiceException("帐单上面的未销账的费用总额之和不能小于０");
				}
				if(unSetFeeAmont+partunSetFeeAmont+feeTotal<0){
					throw new  ServiceException("未销账的费用总额和调帐费用总额之和不能小于０");
				}
				
			}else if(CommonConstDefinition.SETOFFFLAG_D.equals(type)){
				if(haveSetFeeAmont+parthaveSetFeeAmont<0){
					throw new  ServiceException("帐单上面的已销账的费用总额之和不能小于０");
				}
				if(haveSetFeeAmont+parthaveSetFeeAmont+feeTotal<0){
					throw new  ServiceException("已销账的费用总额和调帐费用总额之和不能小于０");
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
		//抵扣总额
		double totalDeduction=0;
		//现金抵扣总额
		double cashTotalDeduction=0;
		//虚拟抵扣总额
		double tokenTotalDeduction=0;
		//针对预存抵扣调帐
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
				throw new  ServiceException("调帐的费用总额必须等于调帐支付总额和调帐预存抵扣金额之和");
			}
			//现金预存抵扣
			String preDutionCond=" INVOICEDFLAG='"+CommonConstDefinition.YESNOFLAG_YES+"' AND INVOICENO="+id ;
			double preDeductionAmount=BusinessUtility.getSumDeductionRecordValueByFeeCond(preDutionCond);
			if(preDeductionAmount+totalDeduction<0){
				throw new  ServiceException("调账产生的预存抵扣金额和账单调账之前的预存抵扣金额之和必须大于等于0");
			}
			AccountDTO accountDTO= BusinessUtility.getAcctDTOByAcctID(accountID);
			if(accountDTO.getCashDeposit()-cashTotalDeduction<0){
				throw new  ServiceException("帐户现金预付金额减去调账产生的预存抵扣金额不能小于０");
			}
			if(accountDTO.getTokenDeposit()-tokenTotalDeduction<0){
				throw new  ServiceException("帐户虚拟预付金额减去调账产生的预存抵扣金额不能小于０");
			}
		}
	}
	/**
	 * 检查帐户调帐
	 * * 这个方法准备删除掉,没用了,
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
		//未销账的费用总额
		double unSetFeeAmont=BusinessUtility.getSumFeeValueByCond(unCondition,null);
		//已销账费用总额
		double haveSetFeeAmont=BusinessUtility.getSumFeeValueByCond(haveCondition,null);
		//部分销账未销账费用
		double partunSetFeeAmont=BusinessUtility.getSumFeeValueByCond(partCond,CommonConstDefinition.SETOFFFLAG_W);
		//部分销账以销账费用
		double parthaveSetFeeAmont=BusinessUtility.getSumFeeValueByCond(partCond,CommonConstDefinition.SETOFFFLAG_D);
		//费用总和
		double feeTotal=0;
		if(feeAdjustlist!=null && !feeAdjustlist.isEmpty()){

			Iterator feeAdjustIter=feeAdjustlist.iterator();
			while(feeAdjustIter.hasNext()){
				FeeAdjustmentWrapDTO currentFeeWrap=(FeeAdjustmentWrapDTO)feeAdjustIter.next();
				AccountItemDTO accountItemDTO=currentFeeWrap.getAccountItemDTO();
				feeTotal+=accountItemDTO.getValue();
			}
			//未销账进行检查
			if(CommonConstDefinition.SETOFFFLAG_W.equals(type)){
				if(unSetFeeAmont+partunSetFeeAmont<0){
					throw new  ServiceException("未销账的费用总额必须大于等于０");
				}
				if(unSetFeeAmont+partunSetFeeAmont+feeTotal<0){
					throw new  ServiceException("未销账的费用总额和调帐费用总额之和必须大于等于０");
				}
			//已销账进行检查	
			}else if(CommonConstDefinition.SETOFFFLAG_D.equals(type)){
				if(haveSetFeeAmont+parthaveSetFeeAmont<0){
					throw new  ServiceException("已销账的费用总额必须大于等于０");
				}
				if(haveSetFeeAmont+parthaveSetFeeAmont+feeTotal<0){
					throw new  ServiceException("已销账的费用总额和调帐费用总额之和必须大于等于０");
				}
			}
		}
		//现金支付
		double cashPayAmount=0;
		//虚拟货币支付
		double tokenPayAmount=0;
		//总金额
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
		//抵扣总额
		double totalDeduction=0;
		//现金抵扣总额
		double cashTotalDeduction=0;
		//虚拟抵扣总额
		double tokenTotalDeduction=0;
		//针对预存抵扣调帐
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
		//已销账费用调帐进行的检查
		if(CommonConstDefinition.SETOFFFLAG_D.equals(type)){
			//if(feeTotal!=totalPay+totalDeduction){
			if(Math.abs(feeTotal-(totalPay+totalDeduction))>0.0001){
				throw new  ServiceException("调帐的费用总额必须等于调帐的支付综合和预存抵扣金额之和");
			}
			AccountDTO accountDTO= BusinessUtility.getAcctDTOByAcctID(accountID);
			if(accountDTO.getCashDeposit()-cashTotalDeduction<0){
				throw new  ServiceException("帐户现金预付金额减去调账产生的预存抵扣金额不能小于０");
			}
			if(accountDTO.getTokenDeposit()-tokenTotalDeduction<0){
				throw new  ServiceException("帐户虚拟预付金额减去调账产生的预存抵扣金额不能小于０");
			}
		//帐户调帐进行的检查
		}else{
			AccountDTO accountDTO= BusinessUtility.getAcctDTOByAcctID(accountID);
			if(accountDTO.getCashDeposit()+cashPayAmount<0){
				throw new  ServiceException("调帐产生的现金预付金额和帐户现金预付金额之和不能小于０");
			}
			if(accountDTO.getTokenDeposit()+tokenPayAmount<0){
				throw new  ServiceException("调帐产生的虚拟预付金额和帐户虚拟预付金额之和不能小于０");
			}
		}
		
	}
	/**
	 * 检查受理单调帐时的信息
	 * * 这个方法准备删除掉,没用了,
	 * @param csiId
	 * @param feeAdjustlist
	 * @param paymentAdjustList
	 * @throws ServiceException
	 */
	public  void checkCsiAjustment(int csiId,int accountID,Collection feeAdjustlist,Collection paymentAdjustList,Collection preDeductionAdjustList) throws ServiceException{
		//受理费用金额
		double csiPayAmount=0;
		//预付金额
		double cisPrepayment=0;
		//总的费用支付
		double totalPayAmount=0;
		//现金支付
		double cashPayAmount=0;
		//虚拟货币支付
		double tokenPayAmount=0;
		
		//现金强制支付预存
		double cashPrePayAmount=0;
		//虚拟货币强制支付预存
		double tokenPrePayAmount=0;
		//强制预存费用
		double forceDepositAmount=0;
		//受理费用
		double csiFee=0;
		//费用总和
		double feeTotal=0;
		
		
		//抵扣总额
		double totalDeduction=0;
		//现金抵扣总额
		double cashTotalDeduction=0;
		//虚拟抵扣总额
		double tokenTotalDeduction=0;
		//处理费用调帐
		if(feeAdjustlist!=null && !feeAdjustlist.isEmpty()){
			Iterator feeIter=feeAdjustlist.iterator();
			while(feeIter.hasNext()){
				FeeAdjustmentWrapDTO currentFeeWrap=(FeeAdjustmentWrapDTO)feeIter.next();
				AccountItemDTO accountItemDTO=currentFeeWrap.getAccountItemDTO();
				LogUtility.log(clazz,LogLevel.DEBUG,"■■■checkCsiAjustment accountItemDTO■■■" + accountItemDTO);
				feeTotal+=accountItemDTO.getValue();
				if(FeeService.isForceDeposit(accountItemDTO)){
					forceDepositAmount+=accountItemDTO.getValue();
				}else{
					csiFee+=accountItemDTO.getValue();
				}
			}
		}
		//检查支付调帐
		if(paymentAdjustList!=null && !paymentAdjustList.isEmpty()){
			Iterator paymentIter=paymentAdjustList.iterator();
			while(paymentIter.hasNext()){
				PaymentAdjustWrapDTO currentpaymentWrap=(PaymentAdjustWrapDTO)paymentIter.next();
				PaymentRecordDTO paymentRecordDTO=currentpaymentWrap.getPaymentRecordDTO();
				LogUtility.log(clazz,LogLevel.DEBUG,"■■■checkCsiAjustment paymentRecordDTO■■■" + paymentRecordDTO);
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
		//针对预存抵扣调帐
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
		
		//现金预存抵扣
		double preCashDeductionAmount=BusinessUtility.getSumDeductionRecordValueByFeeCond(precashDutionCond);
		//虚拟预存抵扣
		double preTokenDeductionAmount=BusinessUtility.getSumDeductionRecordValueByFeeCond(preTokenDutionCond);
		//判断调帐产生的一次性费用和调帐产生的支付及调帐产生的预存抵扣是否相等
		//if(csiFee!=csiPayAmount+totalDeduction){
		if(Math.abs(csiFee-(csiPayAmount+totalDeduction))>0.0001){
			throw new  ServiceException("调账产生的支付金额与调账产生的预存抵扣金额的合计不等于调账产生的费用金额");
		}
		//合计该受理单相关的所有预存合计金额
		String prePayCond="'"+CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE+"','"+CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE+"'";
		double haveActPrePayedValue=BusinessUtility.getSumPaymentRecordValueByTypeAndID(csiId,CommonConstDefinition.FTREFERTYPE_M,prePayCond,null);
		
		if(cisPrepayment+haveActPrePayedValue<0){
			throw new  ServiceException("调账产生的预付金额与调帐之前的预付金额之和要大于等于0");
		}
		AccountDTO accountDTO=BusinessUtility.getAcctDTOByAcctID(accountID);
		if (accountDTO ==null) throw new ServiceException("帐户号无效不能调帐！");
		//针对现金预存账户检查
		if(cashPrePayAmount-cashTotalDeduction+accountDTO.getCashDeposit()<0){
			throw new  ServiceException("调账产生的现金预付金额减掉调账产生的现金预存抵扣金额加上客户现金账户的预存金额要大于等于0");
		}
		//针对虚拟货币预存账户检查
		if(tokenPrePayAmount-tokenTotalDeduction +accountDTO.getTokenDeposit()<0){
			throw new  ServiceException("调账产生的虚拟预付金额减掉调账产生的虚拟预存抵扣金额加上客户虚拟账户的预存金额要大于等于0");
		}
		//调帐之前的所有费用总计	
		String allHavedfeeCountCond=" REFERTYPE='"+CommonConstDefinition.FTREFERTYPE_M+"' AND REFERID="+csiId+" AND FORCEDDEPOSITFLAG='"+CommonConstDefinition.YESNOFLAG_NO+"'";
		double allFeeCount=BusinessUtility.getSumFeeValueByCond(allHavedfeeCountCond,null);
		if(allFeeCount+feeTotal<0){
			throw new  ServiceException("调帐产生的费用金额和调帐之前的费用金额之和要大于等于0");
		}
		
		if(totalDeduction+preCashDeductionAmount+preTokenDeductionAmount<0){
			throw new  ServiceException("调账产生的预存抵扣金额和受理单调账之前的预存抵扣金额之和要大于等于0");
		}
	}
	/**
	 * 检查客户套餐是否可以取消,
	 * 1,正常状态,
	 * 2,有效时间内的,
	 * 3,套餐相关的客户产品不被依赖,
	 * @param ccid
	 * @throws ServiceException
	 */
	public Map checkCustomerBundleCancel(int ccid) throws ServiceException{
		if(ccid==0){
			throw new ServiceException("无效的客户套餐!");
		}
		CustomerCampaignDTO ccDto=BusinessUtility.getCustomerCampaignDTOByID(ccid);
		if(!CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL.equals(ccDto.getStatus())){
			throw new ServiceException("只有正常状态的套餐可以取消!");
		}
		Timestamp curDate=TimestampUtility.getCurrentDateWithoutTime();
		if(ccDto.getDateTo().before(curDate)){
			throw new ServiceException("套餐已经过期!");
		}
		
		//取促销相关的业务帐户及客户产品
		Map saMap= BusinessUtility.getServiceAccountMapByCustCampaignID(ccid,false);
		//进行依赖检查,以业务帐户为单位, 
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
     * 检查客户可以兑换的条件
	 * 1.检查积分是否够;2检查用户类型是否适当 
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
		//检查是否存在custId
		 if (custId == null) 
		   throw new ServiceException("没有客户信息。");
		 //检查是否存在可用积分
		 if (currentPoints == null) 
			throw new ServiceException("当前客户没有可用积分。");
         //检查是否存在累计积分
		 if (tatalPoints == null) 
			throw new ServiceException("当前客户没有可用积分。");
//		检查是否存在客户类型
		 if (custType == null) 
			throw new ServiceException("当前客户没有类型。");
  //	检查是否存在客户类型
		 if (goodsID == null) 
			throw new ServiceException("当前客户没有选择物品。");
		 //	检查是否存在这个活动
		 if (activityID == null) 
			throw new ServiceException("当前客户没有选择活动。");
		 
	  	try{
	  		
	  		 UserPointsExchangerCdDTO  recordDto = new UserPointsExchangerCdDTO();
	  		 recordDto.setActivityId(activityID.intValue());
	  		 recordDto.setCreateOperatorId(operatorId.intValue());
	  		 recordDto.setExchangeGoodsAmount(1);
	  		 recordDto.setUserId(custId.intValue());
	  		 recordDto.setExchangeGoodsTypeId(goodsID.intValue());
	  		 recordDto.setUserPointsBefore(currentPoints.intValue());
//	  		定义存在多个规则的时候需要积分的最小值
	  		int miniPoints = 0;
	  		UserPointsExchangeRuleHome userPointsExchRuleHome =HomeLocater.getUserPointsExchangeRuleHome();
	    	Collection userPointsExchRule=userPointsExchRuleHome.
			                   findByExchangeGoodsTypeIdAndActivityId(goodsID.intValue(),activityID.intValue());
	    	Iterator currentExchRuleIter=userPointsExchRule.iterator(); 
	    	
	    	while(currentExchRuleIter.hasNext()){
	    		UserPointsExchangeRule currentRule=(UserPointsExchangeRule)currentExchRuleIter.next();
	    	    String custTypeStr=currentRule.getCustTypeList();
	    	    LogUtility.log(clazz,LogLevel.DEBUG,"传入的CustomerTypeStr为：■■■■■■" + custTypeStr);
	    	    LogUtility.log(clazz,LogLevel.DEBUG,"传入的CustType为：■■■■■■" + custType);
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
	    		  throw new ServiceException("该客户类型不能参加这个活动 ");
	    	  
	    	   
	       else if(currentPoints.intValue()>=miniPoints){
	    	 	CustomerHome custHome = HomeLocater.getCustomerHome();	
	    	 	Customer  cust= custHome.findByPrimaryKey(custId);
	    	 	cust.setCurrentAccumulatePoint(currentPoints.intValue()-miniPoints);
	    	 	recordDto.setExchangePoints(miniPoints);
	    	    recordDto.setCreateTime(new Timestamp(System.currentTimeMillis()));
	    	 	recordDto.setUserPointPost(currentPoints.intValue()-miniPoints);
	    	 	return recordDto;
	    	 	} else 
	    	 	   throw new ServiceException("积分不够。");
	    	 
	    	}
	  	     
	    	catch(HomeFactoryException e1) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkPointEnough",e1);
			throw new ServiceException("检查客户积分定位错误。");
		  }catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkPointEnough",e2);
			throw new ServiceException("检查客户积分查找错误。");
		}
		 
	}
	   
	    
	/**
 
	 * 检查取消代理商预约单/预约单
	 * @param event
	 * @throws ServiceException
	 */
	public void checkCancelBook(BookEJBEvent event) throws ServiceException{
		try {
			//取得受理单信息
			CustServiceInteractionDTO csiDTO=event.getCsiDto();
			CustServiceInteractionHome csiHome =HomeLocater.getCustServiceInteractionHome();
			CustServiceInteraction csi=csiHome.findByPrimaryKey(new Integer(csiDTO.getId()));
			//受理单类型时预约/代理商预约单
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(csi.getType())){
				boolean isAgent=event.isAgent();
			    if (isAgent) {
			    	//如果是代理商，预约单状态为‘新建’
			    	if(!CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_NEW.equals(csi.getStatus())){
			    		throw new ServiceException("取消的代理商预约单的状态必须是新建");
			    	}
			    }else{
			    	//如果是预约单状态为‘待处理’
			    	if(!CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_NEW.equals(csi.getStatus())&&
			    		!CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT.equals(csi.getStatus())){
			    		throw new ServiceException("取消的预约单的状态必须是新建/待处理");
			    	}
			    }
			}else{
				throw new ServiceException("该受理单不是预约单");
			}
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("取消预约单时定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("取消预约单时找不到相关信息");
		}
	}
	/**
 
	 * 检查预约，代理商预约，预约开户，门店开户信息的正确性
	 * @param event
	 * @throws ServiceException
	 */
	public void checkOpenInfo(BookEJBEvent event) throws ServiceException{
    	//取得新客户信息
    	NewCustomerInfoDTO newCustInfo = event.getNewCustInfo();
    	//取得新客户新账户信息
    	NewCustAccountInfoDTO newCustAcctInfo = event.getNewCustAcctInfo();
    	//取得受理单信息
		CustServiceInteractionDTO csiDTO=event.getCsiDto();
		//取得客户地址信息
		AddressDTO custAddrDto = event.getCustAddressDTO();
		//取得账户地址信息
		AddressDTO acctAddrDto = event.getAcctAddressDTO();
		//客户的团购券号
		String detailNO =event.getDetailNo();
		//取得客户购买的产品包信息
		Collection csiPackageArray = event.getCsiPackageArray();
		if(csiPackageArray==null){
			csiPackageArray=new ArrayList();
			event.setCsiPackageArray(csiPackageArray);
		}
		//取得客户选择的优惠信息
		Collection csiCampaignArray = event.getCsiCampaignArray();
		if(csiCampaignArray==null){
			csiCampaignArray=new ArrayList();
			event.setCsiCampaignArray(csiCampaignArray);
		}
		
		//检查录入的客户条形码是否已经存在
		checkEixtMultCustSerialNo(newCustInfo.getCustomerSerialNo());
		
		//硬件设备信息
		//Hashtable terminalDeviceTable=event.getDeviceTable();
		HashMap terminalDeviceTable=event.getDeviceTable();
		//检查团购券是否有效，并检查团购券和优惠的排斥性
		checkDetailNoValid(detailNO,csiPackageArray,csiCampaignArray,newCustInfo.getOpenSourceType());
		
		//开始检查客户
		if (newCustInfo == null) 
			throw new ServiceException("没有客户信息。");
		//开始检查账户
		if (newCustAcctInfo == null)
			throw new ServiceException("没有账户信息。");
		
		//开始检查银行帐号 2008-2-1,预约/代理商预约不检查银行帐号格式,wangpeng@20080311
		if(!CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(csiDTO.getType())
				&&newCustAcctInfo.getBankAccount()!=null && 
				!"".equals(newCustAcctInfo.getBankAccount())){
			if(newCustAcctInfo.getMopID()!=0){
				String functionResult = BusinessUtility.callFunctionForCheckBankAccount(newCustAcctInfo.getMopID(),newCustAcctInfo.getBankAccount());
				if(!"true".equals(functionResult))
					throw new ServiceException(functionResult);
			}
		}
		//开始检查受理单
		if (csiDTO == null)
			throw new ServiceException("没有受理单信息。");
		//开始检查客户地址
		if ((custAddrDto == null) && acctAddrDto==null)
			throw new ServiceException("没有客户地址信息。");
		//开始检查客户账户地址		
		if ((custAddrDto == null) && acctAddrDto==null)
			throw new ServiceException("没有客户账户信息。");
			
		//只检查客户信息/账户信息/团购券信息时到此返回
		if(OpenAccountCheckEJBEvent.CHECK_CUSTOMERINFO==event.getActionType() || 
				OpenAccountCheckEJBEvent.CHECK_OPEN_ACCOUNT_CATV==event.getActionType()||
				OpenAccountCheckEJBEvent.OPEN_CATV_ACCOUNT==event.getActionType()){
			return;
		}
		//只有在非团购的时候才需要检查产品包 ，优惠，产品，地区之间的关系
		if(detailNO==null || "".equals(detailNO)){
			//检查套餐和优惠促销条件
			CommonBusinessParamDTO paramDTO=new CommonBusinessParamDTO(newCustInfo.getType(),newCustInfo.getOpenSourceType(),event.getCsiDto().getType(),event.getOperatorID());
			checkPackageCampaign(csiPackageArray,csiCampaignArray,custAddrDto,true,paramDTO);		
		}
		
		//取得产品包对应的所有产品信息
		Collection currentAllProductCol=new ArrayList();
		Iterator currentPackageIter=csiPackageArray.iterator();

		while(currentPackageIter.hasNext()){
			Integer currentPackageID=(Integer)currentPackageIter.next();
			currentAllProductCol.addAll(BusinessUtility.getProductIDsByPackageID(currentPackageID.intValue())) ;
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG,"checkOpenInfo 检查产品的依赖性-----------------start-------"+currentAllProductCol);
		
		//只有在非团购的时候才需要检查产品的依赖性
		if(detailNO==null || "".equals(detailNO)){
			//检查是否存在需要创建业务账户的产品
			if(!checkExitCreateServiceAccountProduct(csiPackageArray)){
				throw new ServiceException("选择产品包中不包含可以创建业务帐户的产品。");
			}
			
			//检查产品之间的依赖性
			//把产品按业务帐户分开，检查每个业务帐户下的产品是否满足依赖关系,modify by jason 2007-3-9
			ServiceContext saContext=new ServiceContext();
			saContext.put(Service.OPERATIOR_ID, new Integer(event.getOperatorID()));
			ServiceAccountService saService=new ServiceAccountService(saContext);
			
			//2008-04-21 hujinpeng 不能同时购买多个宽带产品。产品购买重复 用来检查购买得产品是否重复
			Collection productIDForLis = (ArrayList)((ArrayList)currentAllProductCol).clone();
			Iterator itProductID = productIDForLis.iterator();
			Collection contianCol=new ArrayList();
			while (itProductID.hasNext()) {
				Integer productID = ((Integer) itProductID.next());
				LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkOpenInfo检查依赖  contianCol"+contianCol);
				LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkOpenInfo检查依赖  productID"+productID);
				if(contianCol.contains(productID))
					throw new ServiceException("产品购买不能重复，本次选择购买的产品ID："+BusinessUtility.getProductNameByProductID(productID.intValue()) +" 重复！");
				contianCol.add(productID);
			}
			
			
			Map productIDOfSAMap=saService.productSplitToSA(currentAllProductCol);
			if(productIDOfSAMap==null || productIDOfSAMap.isEmpty())
				throw new ServiceException("产品购买不完整，不能创建业务帐户！");
			Iterator itProductIDOfSA=productIDOfSAMap.keySet().iterator();
			while(itProductIDOfSA.hasNext()){
				Integer productIDOfSA=(Integer)itProductIDOfSA.next();
				Collection productIDToOneSAList=(Collection)productIDOfSAMap.get(productIDOfSA);
				if(productIDToOneSAList==null)
					productIDToOneSAList=new ArrayList();
				productIDToOneSAList.add(productIDOfSA);
				LogUtility.log(clazz,LogLevel.DEBUG,"检查产品的依赖性------------------------"+productIDOfSA);
				checkProductDependency(productIDToOneSAList);
			}
			//checkProductDependency(currentAllProductCol);
			//end
			
		}	
		LogUtility.log(clazz,LogLevel.DEBUG,"checkOpenInfo 检查产品的依赖性-----------------end-------");
		
		//检查客户产品和优惠信息到此返回
		if(OpenAccountCheckEJBEvent.CHECK_PRODUCTPG_CAMPAINPG==event.getActionType()){
			return;
		}
		//如果是预约单/代理商预约单时到此返回
		if(OpenAccountCheckEJBEvent.BOOKING==event.getActionType()||
		  OpenAccountCheckEJBEvent.ALTERBOOKING==event.getActionType()||
		  OpenAccountCheckEJBEvent.AGENT_BOOKING==event.getActionType()||
		  OpenAccountCheckEJBEvent.CHECK_AGENT_BOOKING==event.getActionType()){
			return;
		}

		//取得产品属性信息
		Map productPropertyMap = event.getProductPropertyMap();
		LogUtility.log(clazz, LogLevel.DEBUG, "checkOpenInfo 取得产品属性信息"+productPropertyMap);
		//检查产品属性,用户名唯一性校验 2008-03-25 hjp
		if(productPropertyMap!=null){
			Iterator itProductProperty = productPropertyMap.keySet().iterator();
			while(itProductProperty.hasNext()){
				String productID = (String)itProductProperty.next();
				Map tempMap = (Map)productPropertyMap.get(productID);
				String strPropertyValue = (String)tempMap.get("30002");
				String strPropertyName = BusinessUtility.getProductPropertyDTObyPrimaryKey(productID,"30002").getPropertyName();
				if(BusinessUtility.getISExistCpConfigedPropertyByPropertyCodeAndValue(productID,"30002",strPropertyValue))
					throw new ServiceException(strPropertyName+"重复");
					//throw new ServiceException("客户产品的属性,用户名重复");
			}
		}
		
		//检查所选的硬件设备和所选硬件设备和产品的映射关系是否正确
		checkSelectTerminalDevice(csiDTO, terminalDeviceTable,currentAllProductCol,true);
		//检查硬件产品信息时到此返回
		if(OpenAccountCheckEJBEvent.CHECK_HARDPRODUCTINFO==event.getActionType()){
			return;
		}
		
		//检查费用信息时到此返回
		if(OpenAccountCheckEJBEvent.CHECK_FREEINFO==event.getActionType()){
			return;
		}
		
		
	}
	
	/**
	 * 批量新增产品包检查
	 */
	public void checkBatchAddCustomerProduct(CustomerProductEJBEvent event)throws ServiceException{
		try {
			//取得客户购买的产品包信息
			Collection csiPackageArray = new ArrayList(event.getCsiPackageArray());
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
			//取得客户选择的优惠信息
			Collection csiCampaignArray = event.getCsiCampaignArray();
			//取得新客户信息
	    	int  customerID = event.getCustomerID();
	    	
	    	//add by david.Yang 如果是协议客户只能选择协议的产品包
	    	Map  protocolDtoMap =BusinessUtility.getProtocolDTOColByCustID(customerID);
			if (protocolDtoMap !=null && protocolDtoMap.size() >0){
				Iterator packageItr  = csiPackageArray.iterator();
				while(packageItr.hasNext()){
					Integer packateID=(Integer)packageItr.next();
					if (!protocolDtoMap.keySet().contains(packateID)){
						ProductPackageDTO dto =BusinessUtility.getProductPackageDTOByID(packateID.intValue());
						throw new ServiceException(dto.getPackageName()+"不在协议涵盖范围内，请与管理员联系！");
					}
				}
			}
			//add by david.Yang.如果是协议客户只能选择协议的产品包 end
	    	
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
			//取得账户信息
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("所选择的账户有未付账单。");
	    	}
	    	//客户地址信息
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());
			
			//检查是否存在需要创建业务账户的产品
			if(checkExitCreateServiceAccountProduct(csiPackageArray)){
				throw new ServiceException("选择产品包中不能包含可以创建业务帐户的产品。");
			}
			
			CommonBusinessParamDTO paramDTO=new CommonBusinessParamDTO(customer.getCustomerType(),customer.getOpenSourceType(),event.getCsiDto().getType(),event.getOperatorID());
			checkPackageCampaign(csiPackageArray,csiCampaignArray,custAddrDto,true,paramDTO);
			BusinessUtility.checkBatchAddPackageInfo(csiPackageArray);
	
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("新增产品的检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("新增产品的检查中找不到相关信息");
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
	 * 新增产品时的检查
	 * 
	 * @throws ServiceException
	 */
	public void checkAddCustomerProduct(CustomerProductEJBEvent event)throws ServiceException{
		try {
			//取得客户购买的产品包信息
			Collection csiPackageArray = new ArrayList(event.getCsiPackageArray());
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
			//取得客户选择的优惠信息
			Collection csiCampaignArray = event.getCsiCampaignArray();
	    	//取得新客户信息
	    	int  customerID = event.getCustomerID();
	    	
	    	//add by david.Yang 如果是协议客户只能选择协议的产品包
	    	Map  protocolDtoMap =BusinessUtility.getProtocolDTOColByCustID(customerID);
			if (protocolDtoMap !=null && protocolDtoMap.size() >0){
				// 如果是预约新增产品，不能是协议用户 
				if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(event.getCsiDto().getType())){
					throw new ServiceException("协议用户不能做预约新增产品！请与管理员联系！");
				}
				Iterator packageItr  = csiPackageArray.iterator();
				while(packageItr.hasNext()){
					Integer packateID=(Integer)packageItr.next();
					if (!protocolDtoMap.keySet().contains(packateID)){
						ProductPackageDTO dto =BusinessUtility.getProductPackageDTOByID(packateID.intValue());
						throw new ServiceException(dto.getPackageName()+"不在协议涵盖范围内，请与管理员联系！");
					}
				}
			}
			//add by david.Yang.如果是协议客户只能选择协议的产品包 end
	    	
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("只有正常用户才能新增产品。");
	    	}
	    	//取得业务账户信息
	    	ServiceAccountHome serviceAccountHome=HomeLocater.getServiceAccountHome();
	    	ServiceAccount serviceAccount =serviceAccountHome.findByPrimaryKey(new Integer(event.getSaID() ));
	    	if(!CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL.equals(serviceAccount.getStatus())){
	    		throw new ServiceException("只有正常业务账户才能新增产品。");
	    	}

	    	//取得账户信息
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("所选择的账户有未付账单。");
	    	}
	    	
	    	//客户地址信息
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());
			
			//检查是否存在需要创建业务账户的产品
			if(checkExitCreateServiceAccountProduct(csiPackageArray)){
				throw new ServiceException("选择产品包中不能包含可以创建业务帐户的产品。");
			}
			
			// 如果是预约新增产品，只能预约业务账户下已经存在的产品
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
					   throw new ServiceException(productName+"不在该业务账户下,不能预约，请走新增产品功能！");
				   }
				}
				return;
			}
			
			//只有在非团购的时候才需要检查产品包 ，优惠，产品，地区之间的关系
			//检查套餐和优惠促销条件
			CommonBusinessParamDTO paramDTO=new CommonBusinessParamDTO(customer.getCustomerType(),customer.getOpenSourceType(),event.getCsiDto().getType(),event.getOperatorID());
			checkPackageCampaign(csiPackageArray,csiCampaignArray,custAddrDto,true,paramDTO);
			
	    	//取得业务账户下的已有的产品包
	    	//csiPackageArray.addAll(BusinessUtility.getCustProductPackageBySAID(event.getSaID() ));
			
			//取得产品包对应的所有产品信息
			Collection currentAllProductCol=new ArrayList();
			Collection eventProductCol=new  ArrayList();
			currentAllProductCol.addAll(BusinessUtility.getProductIDListByServiceAccount(event.getSaID()," status <> '" + CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"' "));
			Iterator currentPackageIter=csiPackageArray.iterator();
			while(currentPackageIter.hasNext()){
				Integer currentPackageID=(Integer)currentPackageIter.next();
				eventProductCol.addAll(BusinessUtility.getProductIDsByPackageID(currentPackageID.intValue())) ;
			}
			currentAllProductCol.addAll(eventProductCol);
			//检查产品之间的依赖性
			checkProductDependency(currentAllProductCol);
			
			//检查重复产品
			checkProductTautology(currentAllProductCol);
			
			//检查客户产品和优惠信息到此返回
			if(CustomerProductEJBEvent.CHECK_PRODUCTPG_CAMPAINPG==event.getActionType()){
				
				return;
			}
			
			//取得产品属性信息
			Map productPropertyMap = event.getProductPropertyMap();
			LogUtility.log(clazz, LogLevel.DEBUG, "checkAddCustomerProduct 取得产品属性信息"+productPropertyMap);
			//检查产品属性,用户名唯一性校验 2008-03-25 hjp
			if(productPropertyMap!=null){
				Iterator itProductProperty = productPropertyMap.keySet().iterator();
				while(itProductProperty.hasNext()){
					String productID = (String)itProductProperty.next();
					Map tempMap = (Map)productPropertyMap.get(productID);
					String strPropertyValue = (String)tempMap.get("30002");
					String strPropertyName = BusinessUtility.getProductPropertyDTObyPrimaryKey(productID,"30002").getPropertyName();
					if(BusinessUtility.getISExistCpConfigedPropertyByPropertyCodeAndValue(productID,"30002",strPropertyValue))
						throw new ServiceException(strPropertyName+"重复");
						//throw new ServiceException("客户产品的属性,用户名重复");
				}
			}
			
			//检查硬件 
			if(event.getDeviceMap()!=null){
				checkSelectTerminalDevice(event.getCsiDto(), event.getDeviceMap(),eventProductCol,true);
			}
			//检查硬件产品信息时到此返回
			if(CustomerProductEJBEvent.CHECK_HARDPRODUCTINFO==event.getActionType()){
				return;
			}
			
			//检查费用信息时到此返回
			if(CustomerProductEJBEvent.CHECK_FREEINFO==event.getActionType()){
				return;
			}

		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("新增产品的检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("新增产品的检查中找不到相关信息");
		}
	}
	/**
	 * 检查对用户的操作
	 * @param event
	 * @throws ServiceException
	 */
	public void checkOperationCustomer(CustomerEJBEvent event)throws ServiceException{
		try {
			//取得新客户信息
	    	int  customerID = event.getCustomerDTO().getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("只有正常用户才能执行该项操作。");
	    	}
	    	//取得账户信息
	    	int accountID=event.getAccountDTO().getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("所选择的账户有未付账单。");
	    	}
			if(!BusinessUtility.isNormalAccount(customerID,0)){
				throw new ServiceException("当前客户有非正常状态帐户。");
			}
			if(!BusinessUtility.isNormalCSI(customerID)){
				throw new ServiceException("存在未完成的受理单。");
			}
			if(!BusinessUtility.isNormalCustomerProblem(customerID,0)){
				throw new ServiceException("存在未完成的报修单。");
			}
			if(!BusinessUtility.isNormalCustomerComplain(customerID)){
				throw new ServiceException("存在未完成的投诉单。");
			}
			if(!BusinessUtility.isNormalServiceAccount(customerID,0)){
				throw new ServiceException("存在状态为初始/系统暂停的业务帐户。");
			}
			if(!BusinessUtility.isNormalCustomerProduct(customerID,0)){
				throw new ServiceException("存在状态为初始/系统暂停的客户产品。");
			}
			if(EJBEvent.MOVETODIFFERENTPLACE==event.getActionType()||
				EJBEvent.TRANSFERTODIFFERENTPLACE==event.getActionType()||
				EJBEvent.TRANSFERTOSAMEPLACE==event.getActionType()){
					//检查客户条形码和老用户是否一致不一致则检查是否重复
					checkEixtMultCustSerialNoForExistCust(customerID,event.getCustomerDTO().getCustomerSerialNo());
			}
	    	Collection serviceAccountCol=BusinessUtility.getCurrentServiceAccountByCustomerID(customerID,"Status='N'");
	    	//业务账户下的产品id
	    	if(serviceAccountCol!=null && !serviceAccountCol.isEmpty()){
	    		Iterator currentServiceAccountIter=serviceAccountCol.iterator();
	    		while(currentServiceAccountIter.hasNext()){
	    			int serviceAccountID=((Integer)currentServiceAccountIter.next()).intValue();
	    			Collection psidList=null;
			    	switch(event.getActionType()){
			    		//客户迁移
						case EJBEvent.MOVETODIFFERENTPLACE:
							
							//取得业务账户下面所有的产品
					    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);	
							iteratorCheck(psidList, 6);
							break;
                        // 客户异地过户	
						case EJBEvent.TRANSFERTODIFFERENTPLACE:
							//取得业务账户下面所有的产品
					    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);
					    	iteratorCheck(psidList,7);
							break;
                        // 客户原地过户
						case EJBEvent.TRANSFERTOSAMEPLACE:
							//取得业务账户下面所有的产品
					    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);
					    	iteratorCheck(psidList,7);
							break;
				    	//客户销户
			    		case EJBEvent.CLOSE_CUSTOMER:
			    			//取得业务账户下面所有的产品
					    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);
					    	iteratorCheck(psidList,8);
			    			break;
			    		//客户退户
			    		case EJBEvent.WITHDRAW:
			    			//取得业务账户下面所有的产品
					    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);
					    	iteratorCheck(psidList,8);	
			    			break;
			    	}
	    		}
	    	}
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("操作客户的检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("操作客户的检查中找不到相关信息");
			
		}
	}
	/**
	 * 检查对业务账户的操作
	 * @param event
	 * @throws ServiceException
	 */
	public void checkOperationServiceAccount(ServiceAccountEJBEvent event)throws ServiceException{
		try {
			//取得新客户信息
	    	int  customerID = event.getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("只有正常用户才能执行该项操作。");
	    	}
	    	//取得账户信息
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("所选择的账户有未付账单。");
	    	}
	    	//取得业务账户信息
	    	int serviceAccountID=event.getServiceAccountID();
	    	ServiceAccountHome serviceAccountHome=HomeLocater.getServiceAccountHome();
	    	ServiceAccount serviceAccount=null;
	    	//业务账户下的产品id
	    	Collection psidList=null;
	    	
			switch(event.getActionType()){
			    //暂停业务帐户
				case EJBEvent.SUSPEND:
			    	serviceAccount=serviceAccountHome.findByPrimaryKey(new Integer(serviceAccountID));
			    	if(!CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL.equals(serviceAccount.getStatus())){
			    		throw new ServiceException("只有正常业务账户才能执行该项操作。");
			    	}
					//取得业务账户下面所有的软件产品
			    	psidList=BusinessUtility.getSoftwareProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);	
					iteratorCheck(psidList,1);
					break;
		    	//销业务帐户/预退业务帐户/实退业务账户
				case EJBEvent.CLOSE:
				case EJBEvent.BEFOREHAND_CLOSE:
				case EJBEvent.REAL_CLOSE:
					if(!BusinessUtility.isNormalCustomerProblem(customerID,event.getServiceAccountID())){
						throw new ServiceException("存在未完成的报修单。");
					}
					if(!BusinessUtility.isNormalServiceAccount(customerID,event.getServiceAccountID())){
						throw new ServiceException("该业务帐户的状态为初始/系统暂停。");
					}
                    //取得业务账户下面所有的软件产品
			    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);
			    	iteratorCheck(psidList,5);
					break;
		    	//恢复业务帐户
				case EJBEvent.RESUME:	
			    	serviceAccount=serviceAccountHome.findByPrimaryKey(new Integer(serviceAccountID));
			    	if(!CommonConstDefinition.SERVICEACCOUNT_STATUS_REQUESTSUSPEND.equals(serviceAccount.getStatus())){
			    		throw new ServiceException("只有主动暂停的业务账户才能执行该项操作。");
			    	}
			    	
				    Collection colPsId =event.getColPsid();
				    if (colPsId==null ||colPsId.isEmpty()){
				    	throw new ServiceException("没有选择要恢复的产品！");
				    	
				    }
				    iteratorCheck(colPsId,3);
				    //以下操作用来检查产品依赖，检查是否有其它要恢复的产品依赖于未恢复的产品
				    Collection haveExitProductCol=BusinessUtility.getPsIDListByServiceAccount(serviceAccountID," STATUS<>'"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"'");
				    //未恢复的产品集合
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
				//重授权
				case EJBEvent.RESEND_EMM_BY_SERVICE_ACCOUNT:	
					break;
	    		//业务帐户过户
				case EJBEvent.TRANSFER:
					//取得业务账户下面所有的软件产品
					if(event.getCustomerID()!=0&&(event.getCustomerID()==event.getNewCustomerID())){
						throw new ServiceException("不能自己过户给自己。");
					}
			    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,null);
					if(!BusinessUtility.isNormalCustomerProblem(customerID,event.getServiceAccountID())){
						throw new ServiceException("存在未完成的报修单。");
					}
					if(!BusinessUtility.isNormalServiceAccount(customerID,event.getServiceAccountID())){
						throw new ServiceException("该业务帐户的状态为初始/系统暂停。");
					}
					if(!BusinessUtility.isNormalCustomerProduct(customerID,event.getServiceAccountID())){
						throw new ServiceException("存在状态为初始/系统暂停的客户产品。");
					}
					iteratorCheck(psidList,4);
					break;
		    	//机卡匹配
				case EJBEvent.PARTNERSHIP:
					break;
	    		//机卡解配对
	    		case EJBEvent.CANCEL_PARTNERSHIP:
	    			break;

			}
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("操作业务账户的检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("操作业务账户的检查中找不到相关信息");
			
		}
	}
	
	/**
	 * 检查对对产品的操作
	 * @param event
	 * @throws ServiceException
	 */
	public void checkOperationCustomerProduct(CustomerProductEJBEvent event)throws ServiceException{
		try {
			//取得新客户信息
	    	int  customerID = event.getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(EJBEvent.DEVICESWAP==event.getActionType()){
	    		if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())&&
	    			!CommonConstDefinition.CUSTOMER_STATUS_POTENTIAL.equals(customer.getStatus())){
		    		throw new ServiceException("只有正常和潜在用户才能执行该项操作。");
		    	}
	    		//取得业务账户信息
		    	ServiceAccountHome serviceAccountHome=HomeLocater.getServiceAccountHome();
		    	ServiceAccount serviceAccount =serviceAccountHome.findByPrimaryKey(new Integer(event.getSaID() ));
		    	if(!CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL.equals(serviceAccount.getStatus())&&
		    		!CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT.equals(serviceAccount.getStatus())){
		    		throw new ServiceException("只有正常和初始的业务账户才能执行该项操作。");
		    	}
	    	}else if(EJBEvent.MOVE==event.getActionType()){
	    		if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
		    		throw new ServiceException("只有正常用户才能执行该项操作。");
		    	}
		    	//取得业务账户信息
		    	ServiceAccountHome serviceAccountHome=HomeLocater.getServiceAccountHome();
		    	ServiceAccount serviceAccount =serviceAccountHome.findByPrimaryKey(new Integer(event.getSaID() ));
		    	if(!CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL.equals(serviceAccount.getStatus())&&
			    		!CommonConstDefinition.SERVICEACCOUNT_STATUS_BEFOREHANDCLOSE.equals(serviceAccount.getStatus())
		    			){
		    		throw new ServiceException("只有正常和已预退的业务账户才能执行该项操作。");
		    	}
	    	}else{
		    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
		    		throw new ServiceException("只有正常用户才能执行该项操作。");
		    	}
		    	//取得业务账户信息
		    	ServiceAccountHome serviceAccountHome=HomeLocater.getServiceAccountHome();
		    	ServiceAccount serviceAccount =serviceAccountHome.findByPrimaryKey(new Integer(event.getSaID() ));
		    	if(!CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL.equals(serviceAccount.getStatus())){
		    		throw new ServiceException("只有正常业务账户才能执行该项操作。");
		    	}
	    	}
	    	
	    	//取得账户信息
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("所选择的账户有未付账单。");
	    	}
	    	//业务账户下的产品id
	    	Collection psidList=event.getColPsid();
			switch(event.getActionType()){
    		    //客户产品暂停
			    case EJBEvent.SUSPEND:
					iteratorCheck(psidList,1);
		    	    //检查所操作产品的依赖性
		    	    checkProductOperationDependency(psidList,event.getSaID() );
					break;
		    	//客户取消产品
				case EJBEvent.CANCEL:
					iteratorCheck(psidList,2);
		    	    //检查所操作产品的依赖性
		    	    checkProductOperationDependency(psidList,event.getSaID() );
					break;
				//客户迁移产品
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
					//检查和目标用户的产品是否重复
					checkProductTautology(currentAllProductCol);
					//检查所操作产品的依赖性
		    	    checkProductOperationDependency(psidList,event.getTargetSaID() );
		    	    
					break;
                //客户产品恢复
				case EJBEvent.RESUME:	
					iteratorCheck(psidList,3);
					break;
		    	//客户产品升级
	    		case EJBEvent.UPGRADE:
	    			checkProductUpdownGrade(event);
	    			break;
                //客户产品降级
	    		case EJBEvent.DOWNGRADE:
	    			checkProductUpdownGrade(event);
	    			break;
	    		//设备更换
	    		case EJBEvent.DEVICESWAP:
	    			//检查设备是否匹配
	    			TerminalDeviceDTO terminalDevice=BusinessUtility.getDeviceBySerialNo(event.getNewSeralNo());
	    			
	    			//wang fang 2008.05.15 begin
	    			/****************************************************/
	    			TerminalDeviceDTO oldTerminalDevice=BusinessUtility.getDeviceBySerialNo(event.getOldSerialNo());
	    			checkDeviceMatchToCsiTypeAndReason(event.getCsiDto(),terminalDevice,oldTerminalDevice);
	    			/****************************************************/
	    			//wang fang 2008.05.15 end
	    			
	    			//检查设备配对信息是否正确 2007-12-18
	    			checkDeviceMatch(event.getNewSeralNo());
	    			if(event.isDoPost()){
	    				checkDeviceSwap(event.getCustomerProductDTO());
	    			}
	    			
	    			//检查双向机顶盒内置CM add by david.Yang 2009-07-29
	    			if (terminalDevice.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_CM)
	    					&& terminalDevice.getMatchFlag().equals("Y")){
	    				TerminalDeviceDTO dto =BusinessUtility.getDeviceByDeviceID(terminalDevice.getMatchDeviceID());
	    				throw new ServiceException("该CM设备已经与"+dto.getSerialNo()+"配对,请重新选择!");
	    			}
	    			
	    			break;
			}
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("操作产品的检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("操作产品的检查中找不到相关信息");
			
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
			throw new ServiceException("产品升降级的关系在数据库中不能找到！");
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
				throw new ServiceException("所选产品不能满足产品升降级的配置！");
			}
	    } 
		
	    Collection  productList = BusinessUtility.getCustProductIDListByCustInfo(0,0,event.getSaID());
	    if (productList.contains(new Integer(event.getProductID()))){
		   throw new ServiceException("业务帐户中已经包含了升降级的产品！");
	    }
	
	}
	
	/**
	 * 检查设备更换
	 * @param dto
	 * @throws ServiceException
	 */
	private void checkDeviceSwap(CustomerProductDTO dto)throws ServiceException{
		try {
			//查找客户产品
			CustomerProductHome customerProductHome=HomeLocater.getCustomerProductHome();
			CustomerProduct customerProduct=customerProductHome.findByPrimaryKey(new Integer(dto.getPsID()));
			//取得老产品和设备id
			int oldProductID=customerProduct.getProductID();
			//取得新产品或者新设备ID
			int newProductID=dto.getProductID();
			int newDeviceID=dto.getDeviceID();
			//取得新设备的设备类型
			Collection newDeviceModelCol=BusinessUtility.getDeviceModelByProductID(newProductID);
			//取得新设备信息
			TerminalDeviceHome terminalDeviceHome=HomeLocater.getTerminalDeviceHome();
			TerminalDevice newTerminalDevice=terminalDeviceHome.findByPrimaryKey(new Integer(newDeviceID));
			String newInStoreDeviceModel=newTerminalDevice.getDeviceModel();
			
			//如果是CM更换,则需从对照表中找STB对应的Model add by david.Yang
			if (newTerminalDevice.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_CM)){
				newInStoreDeviceModel =BusinessUtility.getStbModelByCmModel(newInStoreDeviceModel);
			}
			
			if(!newDeviceModelCol.contains(newInStoreDeviceModel)){
				throw new ServiceException("所选择的设备和设备型号不匹配。");
			}
			//如果只是更换设备，就退出，如果更换了产品则继续检查
			if(oldProductID==newProductID){
				return;
			}
			//取得客户对应的产品,并用新产品替换老产品
			ArrayList productIDList=BusinessUtility.getProductIDListByServiceAccount(dto.getServiceAccountID()," STATUS<>'"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"'");
			int  productIDIndex=productIDList.indexOf(new Integer(oldProductID));
			productIDList.set(productIDIndex,new  Integer(newProductID));
			
			//检查产品之间的依赖
			checkProductDependency(productIDList);
			
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备更换时检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备更换时找不到相关信息");
			
		}
	}
	
	/**
	 * 创建账户时所作的业务检查
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void checkCreateAccount(AccountEJBEvent inEvent)throws ServiceException{
		try {
			CustomerHome customerHome=HomeLocater.getCustomerHome();
			Customer customer=customerHome.findByPrimaryKey(new Integer(inEvent.getCustomerID()));
			if (!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("只有正常用户才能执行该项操作。");
	    	}
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建账户时检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建账户时找不到相关信息");
			
		}
	}
	/**
	 * 取得客户类型
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
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		} catch (FinderException e) {
			e.printStackTrace();
		}
		if(custStyle==null)
			custStyle="";
		return custStyle;
	}
	/**
	 * 检查帐户调帐时的信息
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void checkAdjustInfo(AdjustEJBEvent inEvent)throws ServiceException{
		try {
			//账户信息
			int accountID=  inEvent.getAccountID();
			AccountHome accountHome=HomeLocater.getAccountHome();
			Account account=accountHome.findByPrimaryKey(new Integer(accountID));

			CustomerHome customerHome=HomeLocater.getCustomerHome();
			Customer customer=customerHome.findByPrimaryKey(new Integer(account.getCustomerID()));
			if (!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("只有正常用户才能执行该项操作。");
	    	}
			switch(inEvent.getActionType()){
				//调账
				case EJBEvent.ADJUSTMENT:
					if (CommonConstDefinition.ACCOUNT_STATUS_CLOSE.equals(account.getStatus())){
			    		throw new ServiceException("只有非关闭账户户才能执行该项操作。");
			    	}
				    break;
			}
			
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("操作账户时检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("操作账户时找不到相关信息");
			
		}
		
		
		
	}
	/**
	 * 用于针对账户进行操作的检查
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void checkOperationAccount(AccountEJBEvent inEvent)throws ServiceException{
		try {
			//账户信息
			AccountDTO accountDTO=  inEvent.getAccountDTO();
			AccountHome accountHome=HomeLocater.getAccountHome();
			Account account=accountHome.findByPrimaryKey(new Integer(accountDTO.getAccountID()));

			CustomerHome customerHome=HomeLocater.getCustomerHome();
			Customer customer=customerHome.findByPrimaryKey(new Integer(account.getCustomerID()));
			switch(inEvent.getActionType()){
				//取消账户	 
				case EJBEvent.CLOSECUSTOMERACCOUNT:
					if (!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
			    		throw new ServiceException("只有正常用户才能执行该项操作。");
			    	}
					if (BusinessUtility.existUnpaidInvoice(accountDTO.getAccountID())){
			    		throw new ServiceException("所选择的账户有未付账单。");
			    	}
					if (!CommonConstDefinition.ACCOUNT_STATUS_NORMAL.equals(account.getStatus())){
			    		throw new ServiceException("只有正常账户才能执行该项操作。");
			    	}
	    			if(!BusinessUtility.isExitOneMoreAccount(account.getCustomerID()))
	    				throw new ServiceException("该帐户是客户的最后一个帐户，不允许取消。");
					Collection custproductCol=BusinessUtility.getCustProductByAccountID(accountDTO.getAccountID());
					if(custproductCol!=null && !custproductCol.isEmpty()){
						throw new ServiceException("该账户有对应的客户产品不能做该项操作。");
					}
					break;
				//预付费
				case EJBEvent.PRESAVE:
					if (BusinessUtility.existUnpaidInvoice(accountDTO.getAccountID())){
			    		throw new ServiceException("所选择的账户有未付账单。");
			    	}
					if (!CommonConstDefinition.ACCOUNT_STATUS_NORMAL.equals(account.getStatus())){
			    		throw new ServiceException("只有正常账户才能执行该项操作。");
			    	}
					break;
			}
			
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("操作账户时检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("操作账户时找不到相关信息");
			
		}
		
	}
	/**
	 * 创建业务账户的检查
	 * 
	 * @throws ServiceException
	 */
	public void checkCreateServiceAccount(ServiceAccountEJBEvent event)throws ServiceException{
		try {
			//取得客户购买的产品包信息
			Collection csiPackageArray = event.getCsiPackageArray();
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
			//取得客户选择的优惠信息
			Collection csiCampaignArray = event.getCsiCampaignArray();
	    	//取得新客户信息
	    	int  customerID = event.getCustomerID();
	    	
	    	// 不允许是协议用户做门店开户
	    	if (event.getCsiDto().getType().equals(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO)){
	    		Map protocolMap =BusinessUtility.getProtocolDTOColByCustID(event.getCsiDto().getCustomerID());
	    		if (protocolMap !=null && !protocolMap.isEmpty()){
	    			throw new ServiceException("协议用户不能做门店增机，请使用批量门店增机功能！");
	    		}
	    	}
	    	
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("只有正常用户才能新增用户。");
	    	}
	    	//取得账户信息
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("所选择的账户有未付账单。");
	    	}
	    	//客户地址信息
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());
			//硬件设备信息
			//Hashtable terminalDeviceTable=event.getDeviceTable();
	    	HashMap terminalDeviceTable=event.getDeviceTable();
			//检查是否存在需要创建业务账户的产品
			if(!checkExitCreateServiceAccountProduct(csiPackageArray)){
				throw new ServiceException("选择产品包中不包含可以创建业务帐户的产品。");
			}
			
			//只有在非团购的时候才需要检查产品包 ，优惠，产品，地区之间的关系
			//检查套餐和优惠促销条件
			CommonBusinessParamDTO paramDTO=new CommonBusinessParamDTO(customer.getCustomerType(),customer.getOpenSourceType(),event.getCsiDto().getType(),event.getOperatorID());
			checkPackageCampaign(csiPackageArray,csiCampaignArray,custAddrDto,true,paramDTO);
			
			//取得产品包对应的所有产品信息
			Collection currentAllProductCol=new ArrayList();
			Iterator currentPackageIter=csiPackageArray.iterator();

			while(currentPackageIter.hasNext()){
				Integer currentPackageID=(Integer)currentPackageIter.next();
				currentAllProductCol.addAll(BusinessUtility.getProductIDsByPackageID(currentPackageID.intValue())) ;
			}
			//检查产品之间的依赖性
			checkProductDependency(currentAllProductCol);
			//检查客户产品和优惠信息到此返回
			if(CustomerProductEJBEvent.CHECK_PRODUCTPG_CAMPAINPG==event.getActionType()){
				return;
			}
			System.out.println("++event.getActionType()="+event.getActionType());
            //检查客户产品和优惠信息到此返回  用于预约增机
			if(CustomerProductEJBEvent.CHECK_BOOKINGUSER_PRODUCTPG_CAMPAINPG==event.getActionType()
					|| CustomerProductEJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER_FORBOOKING ==event.getActionType()|| 
					CustomerProductEJBEvent.CUSTOMER_BOOKINGUSER_UPDATE_SUBSCRIBER ==event.getActionType()){
				return;
			}
			
			//取得产品属性信息
			Map productPropertyMap = event.getProductPropertyMap();
			LogUtility.log(clazz, LogLevel.DEBUG, "checkCreateServiceAccount 取得产品属性信息"+productPropertyMap);
			//检查产品属性30002,用户名唯一性校验 2008-03-25 hjp
			if(productPropertyMap!=null){
				Iterator itProductProperty = productPropertyMap.keySet().iterator();
				while(itProductProperty.hasNext()){
					String productID = (String)itProductProperty.next();
					Map tempMap = (Map)productPropertyMap.get(productID);
					String strPropertyValue = (String)tempMap.get("30002");
					ProductPropertyDTO prodto = BusinessUtility.getProductPropertyDTOByProductIDAndCode(Integer.parseInt(productID),"30002");
					if(BusinessUtility.getISExistCpConfigedPropertyByPropertyCodeAndValue(productID,"30002",strPropertyValue))
						throw new ServiceException(prodto.getPropertyName()+"重复");
				}
			}
			
			//检查所选的硬件设备和所选硬件设备和产品的映射关系是否正确
			checkSelectTerminalDevice(event.getCsiDto(), terminalDeviceTable,currentAllProductCol,true);
			//检查设备配对信息是否正确 2007-12-18
			checkDeviceMatch(event.getDeviceTable());
			//检查硬件产品信息时到此返回
			if(CustomerProductEJBEvent.CHECK_HARDPRODUCTINFO==event.getActionType()){
				return;
			}
			
			//检查费用信息时到此返回
			if(CustomerProductEJBEvent.CHECK_FREEINFO==event.getActionType()){
				return;
			}

		
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建业务账户的检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建业务账户的检查中找不到相关信息");
			
		}
		
	}
/**
 * 重复购买创建业务帐户 检查,
 * @param event
 * @throws ServiceException
 */
	public void checkBatchCreateServiceAccount(ServiceAccountEJBEvent event)throws ServiceException{
		try {
			//取得客户购买的产品包信息
			Collection csiPackageArray = event.getCsiPackageArray();
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
			//取得客户选择的优惠信息
			Collection csiCampaignArray = event.getCsiCampaignArray();
	    	//取得新客户信息
	    	int  customerID = event.getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("只有正常用户才能新增用户。");
	    	}
	    	//取得账户信息
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("所选择的账户有未付账单。");
	    	}
	    	
	    	ArrayList buyProductList=event.getBuyProductList();
	    	//add by david.Yang.如果是协议客户，增机只能增加协议的产品包 begin
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
						throw new ServiceException(dto.getPackageName()+"不在协议涵盖范围内，请与管理员联系！");
					}
				}
			}
			//add by david.Yang.如果是协议客户，增机只能增加协议的产品包 end
	    	
	    	//客户地址信息
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());
	    	
			CommonBusinessParamDTO paramDTO=new CommonBusinessParamDTO(customer.getCustomerType(),customer.getOpenSourceType(),event.getCsiDto().getType(),event.getOperatorID());
			
			checkBatchBuyProductInfo(buyProductList, event.getCsiDto(), paramDTO, custAddrDto);
		
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建业务账户的检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建业务账户的检查中找不到相关信息");
			
		}
		
	}
	/**
	 * 重复购买产品检查,
	 * @param buyProductList 产品列表,
	 * @param csiDTO 受理单
	 * @param paramDTO
	 * @param custAddrDto
	 * @throws ServiceException
	 */
	public void checkBatchBuyProductInfo(
			ArrayList buyProductList,
			CustServiceInteractionDTO csiDTO, CommonBusinessParamDTO paramDTO,
			AddressDTO custAddrDto) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "重复购买开始检查");
		if(buyProductList==null||buyProductList.isEmpty()){
			return ;
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "购买信息",buyProductList);

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
			
			LogUtility.log(clazz, LogLevel.DEBUG, buyIndex+"组当前产品包:",buyPackageList);

			// 检查套餐和优惠促销条件

			checkPackageCampaign(buyPackageList, buyCampaignList,
					custAddrDto, true, paramDTO);

			// 取得产品包对应的所有产品信息
			Collection currentAllProductCol = new ArrayList();
			Iterator currentPackageIter = buyPackageList.iterator();

			while (currentPackageIter.hasNext()) {
				Integer currentPackageID = (Integer) currentPackageIter
						.next();
				currentAllProductCol.addAll(BusinessUtility
						.getProductIDsByPackageID(currentPackageID
								.intValue()));
			}
			LogUtility.log(clazz, LogLevel.DEBUG, buyIndex+"组当前所有产品:",buyPackageList);

			if (!checkExitCreateServiceAccountProduct(buyPackageList)) {
				throw new ServiceException("选择产品包中不包含可以创建业务帐户的产品。");
			}

			// 检查产品之间的依赖性
			// 把产品按业务帐户分开，检查每个业务帐户下的产品是否满足依赖关系,modify by jason 2007-3-9
			ServiceContext saContext = new ServiceContext();
			saContext.put(Service.OPERATIOR_ID, new Integer(paramDTO.getOperatorID()));
			ServiceAccountService saService = new ServiceAccountService(
					saContext,BusinessUtility.getAllProductDepentDefineList());
			Map productIDOfSAMap = saService
					.productSplitToSA(currentAllProductCol);
			if (productIDOfSAMap == null || productIDOfSAMap.isEmpty())
				throw new ServiceException("产品购买不完整，不能创建业务帐户！");
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

			// 检查所选的硬件设备和所选硬件设备和产品的映射关系是否正确
			if(buyDeviceMap!=null&&!buyDeviceMap.isEmpty()){
				LogUtility.log(clazz, LogLevel.DEBUG, buyIndex+"组检查设备当前产品:",currentAllProductCol);
				for(int di=1;di<=buyNum.intValue();di++){
					Integer sheafNo=new Integer(di);
					HashMap deviceMap=(HashMap)buyDeviceMap.get(sheafNo);
					LogUtility.log(clazz, LogLevel.DEBUG, buyIndex+"组,第"+(di)+"套,购买设备:",deviceMap);
					csiProductList.addAll(checkBatchSelectTerminalDevice(csiDTO, deviceMap,
							currentAllProductCol, isInitCsiProductInfo,buyIndex.intValue(),sheafNo.intValue()));
					//检查设备配对
					checkDeviceMatch(deviceMap);
				}
			}
			//取得产品属性信息
			LogUtility.log(clazz, LogLevel.DEBUG, "checkBatchBuyProductInfo 取得产品属性信息"+propertysMap);
			//检查产品属性,用户名唯一性校验 2008-03-25 hjp
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
							//throw new ServiceException("客户产品的属性,用户名重复");
							throw new ServiceException("用户名("+strPropertyValue+")重复");
					}
				}
			}
		}

		if(isInitCsiProductInfo){
			//把取得的硬件产品对应的产品和设备信息设置到上下文环境中
			serviceContext.put(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST,csiProductList);
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"调试是否存在对应的产品和设备信息",csiProductList);
		}	
		LogUtility.log(clazz, LogLevel.DEBUG, "重复购买结束检查");		
	}
	private Collection checkBatchSelectTerminalDevice(CustServiceInteractionDTO csiDTO,HashMap terminalDeviceTable,Collection productCol,boolean isInitCsiProductInfo,int groupNo,int sheafNo)throws ServiceException {
		Collection csiProdCol=checkBatchSelDeviceAndReturnCsiProdInfo(csiDTO,terminalDeviceTable,productCol,isInitCsiProductInfo,groupNo,sheafNo);
		if(isInitCsiProductInfo){
			//把取得的硬件产品对应的产品和设备信息设置到上下文环境中
			serviceContext.put(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST,csiProdCol);
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"调试是否存在对应的产品和设备信息",csiProdCol);
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkSelectTerminalDevice检查硬件设备结束");
		return csiProdCol;
	}
	public Collection checkBatchSelDeviceAndReturnCsiProdInfo(CustServiceInteractionDTO csiDTO,HashMap terminalDeviceTable,Collection productCol,boolean isInitCsiProductInfo,int groupNo,int sheafNo)throws ServiceException {
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"■■csiDTO■■"+csiDTO);
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"■■terminalDeviceTable■■"+terminalDeviceTable);
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"■■productCol■■"+productCol);
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"■■isInitCsiProductInfo■■"+isInitCsiProductInfo);
		if(productCol==null || productCol.isEmpty()){
			throw new ServiceException("没有产品。");
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkSelDeviceAndReturnCsiProdInfo检查所选的硬件设备");
		//新建一个用来存放验证后正确的设备内容（设备ID）
		Hashtable correctDeviceTable=null;	
		//新建一个用来存储硬件产品和其相关的设备信息
		Collection initedCsiProductInfoCol=new ArrayList();
		if(terminalDeviceTable!=null && !terminalDeviceTable.isEmpty() ){
			//取得所有硬件设备的所有序列号
			Set currentKeySet=terminalDeviceTable.keySet();
			correctDeviceTable=new Hashtable();
			String currntSerialNo=null;
			String currentDeviceClass=null;
			String currentDeviceModel=null;
			Iterator currentDeviceIter=currentKeySet.iterator();
			while(currentDeviceIter.hasNext()){
				//取得所有硬件设备的所有序列号
				currntSerialNo=(String)currentDeviceIter.next();
				//取得该硬件设备序列号对应的设备类型
				currentDeviceClass=(String)terminalDeviceTable.get(currntSerialNo);
				//获取终端设备设备的信息
				TerminalDeviceDTO terminalDevice=BusinessUtility.getDeviceBySerialNo(currntSerialNo);
				if(!terminalDevice.getDeviceClass().equals(currentDeviceClass))
					throw new ServiceException("序列号（" + currntSerialNo + "）对应设备的设备类型不是"+HelperCommonUtil.getNameByDeviceClass(currentDeviceClass));
				//判断该设备对应的设备的状态是否正确
				if (!CommonConstDefinition.DEVICE_STATUS_WAITFORSELL.equals(terminalDevice.getStatus()))
					throw new ServiceException("所选择的"+HelperCommonUtil.getNameByDeviceClass(currentDeviceClass)
							+"("+currntSerialNo+")"+"的状态为"+
								HelperCommonUtil.getCommonNameByCacheNameAndKey("SET_D_DEVICESTATUS",terminalDevice.getStatus())+
								",请选择状态为待售的设备！");
				//取得所选设备序列号对应的设备型号
				currentDeviceModel=terminalDevice.getDeviceModel();
				//根据受理单的受理类型和受理原因检查是否和设备用途匹配
				checkDeviceMatchToCsiTypeAndReason(csiDTO,terminalDevice);
				//回置一下设备信息
				int currentDeviceID=terminalDevice.getDeviceID();
				//设置所选择的设备序列号对应的设备id和设备型号
				LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"调试硬件和设备序列号","currentDeviceID="+currentDeviceID+";currentDeviceModel="+currentDeviceModel);
				correctDeviceTable.put(new Integer(currentDeviceID),currentDeviceModel);
			}
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkSelDeviceAndReturnCsiProdInfo检查所选的硬件设备和所选硬件产品是否匹配");
		//传入进来的产品参数
		Iterator currentProductIter=productCol.iterator();
		//用来存放已经检查过的产品的ID
		Collection currentDeviceIDCol=new ArrayList();
		while(currentProductIter.hasNext()){
			Integer currentProductID=(Integer)currentProductIter.next();	
			ProductDTO product=BusinessUtility.getProductDTOByProductID(currentProductID.intValue());
			if(BusinessUtility.getDeviceModelDTOByProductID(product.getProductID()).isEmpty())
				continue;
			//如果是硬件产品，需要判断所选的硬件产品和设备的序列号是否对应
			if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(product.getProductClass())){
				//检查所选的硬件产品中是否存在对应的设备
				boolean isExit=false;
				//取得所有设备到产品的映射对象
				Collection deviceToProductCol=BusinessUtility.getDevToProdByProductId(currentProductID.intValue());
				if(deviceToProductCol==null ||deviceToProductCol.isEmpty()){
					throw new ServiceException("没有相应的设备信息。");
				}
				Iterator deviceToProductIter=deviceToProductCol.iterator();
				//取得该产品对应的所有设备型号
				Collection deviceModelCol=new ArrayList();
				while(deviceToProductIter.hasNext()){
					deviceModelCol.add(((DeviceMatchToProductDTO)deviceToProductIter.next()).getDeviceModel());
				}
				//取得在设备序列号对应的设备检查中设置的内容
				//侯11-23添加
				if(correctDeviceTable==null||correctDeviceTable.isEmpty()){
					throw new ServiceException("没有有效的设备。");
				}
				Iterator checkDeviceIter=correctDeviceTable.keySet().iterator();
				Integer currentDeviceID=null;
				//用来存放已经和产品关联的硬件设备的ID
				Collection haveLinkToProductDeviceList=new ArrayList();
				while(checkDeviceIter.hasNext()){
					//取得设备id
					currentDeviceID=(Integer)checkDeviceIter.next();
					//如果包含设备id，证明选择了同种设备型号的硬件
					if(currentDeviceIDCol.contains(currentDeviceID)){
						throw new ServiceException("所选设备中出现了重复的硬件。");
					}
					//检查是否设备型号和产品的型号匹配
					if(deviceModelCol.contains((String)correctDeviceTable.get(currentDeviceID))){
						//设置和产品关联的设备的ID
						if(!haveLinkToProductDeviceList.contains(currentDeviceID)){
							//是否需要建立硬件产品的CsiProductInfo信息
							if(isInitCsiProductInfo){
								//初始化和传入进来的产品对应的受理单涉及的客户产品相关信息
								CsiCustProductInfoDTO csiCustProductInfoDTO=new CsiCustProductInfoDTO();
								//设置产品id
								csiCustProductInfoDTO.setProductID(currentProductID.intValue());
								//设置产品对应的硬件设备的id
								csiCustProductInfoDTO.setReferDeviceID(currentDeviceID.intValue());
								//侯 ,处理重复购买增加
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
					throw new ServiceException("没有为购买的硬件产品选择对应的设备。");
				}
				if(currentDeviceID!=null){
					correctDeviceTable.remove(currentDeviceID);
				}
			}
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkSelDeviceAndReturnCsiProdInfo检查硬件设备结束");
		return initedCsiProductInfoCol;
	}
	/**
	 * 检查增机受理单是否能修改和取消
	 * @param csiID
	 * @throws ServiceException
	 */
	public void checkUpdateServiceAccount(int csiID) throws ServiceException{
		try{
			CustServiceInteractionHome  custServiceInteractionHome =HomeLocater.getCustServiceInteractionHome();
			CustServiceInteraction custServiceInteraction =custServiceInteractionHome.findByPrimaryKey(new Integer(csiID));
			if (!custServiceInteraction.getStatus().equals(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT))
		        throw new ServiceException("增机预约受理单不能被更新和取消");
		}
		catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("增机预约受理单的定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("增机预约受理单中找不到相关信息");
			
		}
	}
	
	
	/**
	 * 检查产品优惠依赖
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
					throw new ServiceException("客户类型不满足促销条件。");
		   }
		   if (campaign.getOpenSourceTypeList()!=null && !"".equals(campaign.getOpenSourceTypeList())){
				if (campaign.getOpenSourceTypeList().indexOf(customer.getOpenSourceType()) == -1)
					throw new ServiceException("客户开户的来源渠道不满足促销条件。");
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
		  //检查该优惠对应的产品包
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
		  if (!beIncluded ) throw new ServiceException("购买的产品包不符合促销的条件。"); 
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("检查产品优惠依赖的检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("检查产品优惠依赖的检查中找不到相关信息");
			
		}
		
	}
	/**
	 * 检查业务账户促销的优惠期存在重叠时间区间
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
	 * 检查所选产品包对应的产品中是否存在需要创建业务账户的产品
	 * @param csiPackageArray
	 * @return
	 * @throws ServiceException
	 */
	private boolean checkExitCreateServiceAccountProduct(Collection csiPackageArray )throws ServiceException{
		boolean exitFlag=false;
		if(csiPackageArray==null || csiPackageArray.isEmpty()){
			throw new ServiceException("没有选择任何产品包.");
		} 
		LogUtility.log(clazz, LogLevel.WARN, "■■产品包■■",csiPackageArray);
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
	 * 检查团购券是否有效，并检查团购券和优惠的排斥性
	 * @param detailNO  团购券号
	 * @param csiPackageArray  选择的产品包
	 * @param csiCampaignArray 选择的产品优惠
	 * @param openSourceType   来源渠道类型
	 * @throws ServiceException
	 */
	private void checkDetailNoValid( String detailNO,Collection csiPackageArray,Collection csiCampaignArray,String openSourceType)throws ServiceException{
		if (detailNO != null && !"".equals(detailNO))
		{
			Collection packageCol=getValidGroupBargainInfo(detailNO);
							
			if(!CommonConstDefinition.OPENSOURCETYPE_GROUPBARGAIN.equals(openSourceType)){
				throw new ServiceException("团购时来源渠道必须是团购购买。");
			}
			if ((csiPackageArray!=null)&&(!csiPackageArray.isEmpty()))
				throw new ServiceException("团购用户不能选择其它产品包！");
			if ((csiCampaignArray!=null)&&(!csiCampaignArray.isEmpty()))
				throw new ServiceException("团购用户不能选择其它优惠！");
			if (csiPackageArray.isEmpty()) {
				//把产品包 信息回置，以避免后续处理错误
				csiPackageArray.addAll(packageCol); 
			}
			if(csiCampaignArray.isEmpty()){
				GroupBargainDetailDTO detailDTO=BusinessUtility.getGroupBargainDetailByNO(detailNO);
				GroupBargainDTO grouplDTO=BusinessUtility.getGroupBargainByID(detailDTO.getGroupBargainID());
				csiCampaignArray.add(new Integer(grouplDTO.getCampaignId()));
			}
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkOpenAccountPara中取得团购券相关信息结束"); 
		}
	}
	/**
	 * 检查产品包和分区，优惠和分区，优惠和产品包之间的依赖性
	 * @param packageArray 产品包
	 * @param campaignArray 优惠列表
	 * @param customerType 客户类型
	 * @param opensourcetype 来源渠道类型
	 * @param custAddrDto 客户地址
	 * @param isNewCustomer 是否新开户，新增产品之类，由于追加促销是判断用
	 * @throws ServiceException
	 */
	private  void checkPackageCampaign(Collection packageArray,
			Collection campaignArray,
			AddressDTO custAddrDto,
			boolean isNewCustomer,
			CommonBusinessParamDTO paramDTO) throws ServiceException{
		
		OperatorDTO operatorDTO=BusinessUtility.getOperatorDTOByID(paramDTO.getOperatorID());
		if(operatorDTO==null)
			throw new ServiceException("找不到操作员信息");
		//根据操作员的级别来判断当前操作员能否操作该产品包
		if(packageArray!=null && !packageArray.isEmpty()){
			Iterator packageIter=packageArray.iterator();
			while(packageIter.hasNext()){
				Integer packageID=(Integer)packageIter.next();
				ProductPackageDTO  productPackageDTO=BusinessUtility.getProductPackageDTOByID(packageID.intValue());
				if(productPackageDTO==null)
					throw new ServiceException("找不到产品包信息");
				if(operatorDTO.getLevelID()<productPackageDTO.getGrade())
					throw new ServiceException("当前操作员的级别不能售出产品包："+productPackageDTO.getPackageName());
			}
		}
		//检查 客户所在的区域(district) 是否能够购买这些产品包
		checkPackageByDistrict(packageArray, custAddrDto.getDistrictID());
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkPackageCampaign检查客户所在的区域能够购买当前产品");
		//检查优惠和分区，优惠和产品包之间的关系
		if ((campaignArray != null) && (campaignArray.isEmpty() == false)) {
			//检查优惠和区域的依赖
			LogUtility.log(clazz,LogLevel.DEBUG,"check the campaign");
			checkCampaignByDistrict(campaignArray,custAddrDto.getDistrictID());
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkPackageCampaign检查客户所在的区域能够享受所选优惠");

			//检查优惠的依赖
			checkCampaignDependency(campaignArray, packageArray, paramDTO);
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkPackageCampaign检查所选择优惠之间的依赖正常");
			//检查正对促销,产品包,产品有影响的购买重复问题
			checkDuplicateCampaign(packageArray,campaignArray,paramDTO);
		}
	}
	/**
	 * 检查一个产品是否有多个套餐或者促销
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
			//取得所有的协议产品和协议产品包对应的产品
			Map productidList=BusinessUtility.getCampaignAgmtProductList(campaignID.intValue());
			if(!productidList.isEmpty()){
				Iterator productIter=productidList.keySet().iterator();
				while(productIter.hasNext()){
					Integer productID=(Integer)productIter.next();
					if(bundleProdCol.contains(productID)){
						if(product2CpCol.contains(productID))
							throw new ServiceException("一个产品只能拥有一个套餐或促销");
						else
							product2CpCol.add(productID);
					}
				}
			}
			//取得协议产品包
			Collection packageIDList=BusinessUtility.getCampaignAgmtPackageListByCampaignID(campaignID.intValue());
			if(!packageIDList.isEmpty()){
				Iterator packageIDIter=packageIDList.iterator();
				while(packageIDIter.hasNext()){
					Integer packageID=(Integer)packageIDIter.next();
					if(bundleCol.contains(packageID)){
						if(package2CpCol.contains(packageID))
							throw new ServiceException("一个产品包只能拥有一个套餐或促销");
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
	 * @param psidList：产品列表
	 * @param checkSwitch  操作开关
	 * checkSwitch-- 1：产品暂停  / 2:产品取消  / 3：产品恢复 / 4：业务帐户过户 
	 *               6：客户迁移  / 7:客户过户  / 8：客户销户，退户
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
	 * @param customerProductIDCol 客户产品id列表
	 * @param checkAction  检查动作，允许的取值如下
	 * 1：产品暂停  / 2:产品取消  / 3：产品恢复 / 4：业务帐户过户 
	 * 6：客户迁移  / 7:客户过户  / 8：客户销户，退户
	 * @return
	 * @throws ServiceException
	 */
	private void canAllowThisAction(int customerProductID,int checkAction) throws ServiceException{
		try {
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在canAllowThisAction检查客户产品相关信息");
			CustomerProductHome customerProductHome=HomeLocater.getCustomerProductHome();
			ProductHome productHome=HomeLocater.getProductHome();
			CustomerProduct customerProduct=customerProductHome.findByPrimaryKey(new Integer(customerProductID));
			switch(checkAction){
				case 1:  //产品暂停
					if(!CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL.equals(customerProduct.getStatus())){
						throw new ServiceException("只有状态为正常的产品才能暂停");
					}
					Product product=productHome.findByPrimaryKey(new Integer(customerProduct.getProductID()));
					if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(product.getProductClass())){
						throw new ServiceException("只有软件产品才能暂停");
					}
					break;
				case 2:
					break;
				case 3:  //产品恢复
					if(!CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND.equals(customerProduct.getStatus())){
						throw new ServiceException("只有状态为主动暂停的产品才能恢复");
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
			throw new ServiceException("检查能否执行该项操作时定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("检查能否执行该项操作时找不到相关信息");
			
		}
	}
	
	
	
	/**
	 *  判断该客户是否有未付的帐单
	 * @param customerid：客户ID
	 * @return  false － 没有未付帐单
	 * 			true  － 有未付帐单
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
			throw new ServiceException("检查客户－" + customerid + "是否有未付帐单时出错。");
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
	 * 验证团购券的有效性
	 * @param detailNo
	 * @return 
	 */
	private Collection getValidGroupBargainInfo(String detailNo) throws ServiceException{
		Collection packageGroupBarginCols =new ArrayList();	
		CsrBusinessUtility.fillPackageColByGroupBargainDetailNo(packageGroupBarginCols,detailNo);
		if (packageGroupBarginCols ==null || packageGroupBarginCols.isEmpty()){
			throw new ServiceException("此团购券号不合法，或者团购配置不合理，请与管理员联系！");	
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
	 * 检查当前的客户是否满足优惠的有效性
	 * @param campaignArray 优惠列表
	 * @param packageArray  产品列表
	 * @param custtype   客户类型
	 * @param opensourcetype 来源渠道
	 * @throws ServiceException
	 */
	private void  checkCampaignDependency(Collection campaignArray, Collection packageArray,CommonBusinessParamDTO paramDTO) throws ServiceException {
		if ((campaignArray == null) || campaignArray.isEmpty()) return;
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkCampaignDependency检查优惠条件是否满足");
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"■■campaignArray■■",campaignArray);
		Iterator iteCampaign = campaignArray.iterator();
		//用来存放产品到促销的映射
		Map productToCmpMap=new HashMap();
		while (iteCampaign.hasNext()) {
			Integer campaignid = (Integer) iteCampaign.next();
			CampaignDTO campaign = BusinessUtility.getCampaignDTOByID(campaignid.intValue());
			if(campaign.getCustTypeList()!=null && !"".equals(campaign.getCustTypeList())){
				if (campaign.getCustTypeList().indexOf(paramDTO.getCustomerType()) == -1)
					throw new ServiceException("客户类型不满足促销条件。");
			}
			if(campaign.getOpenSourceTypeList()!=null && !"".equals(campaign.getOpenSourceTypeList())){
				if (campaign.getOpenSourceTypeList().indexOf(paramDTO.getOpensourcetype()) == -1)
					throw new ServiceException("客户开户的来源渠道不满足促销条件。");
			}
			if(campaign.getCsiTypeList()!=null && !"".equals(campaign.getCsiTypeList())){
				if (campaign.getCsiTypeList().indexOf(paramDTO.getCsiType()) == -1)
					throw new ServiceException("受理单类型不满足促销条件。");
			}
			//检查套餐/促销对产品包的条件是否满足
			checkCampaignCondPackage(campaignid.intValue(),packageArray,paramDTO);
			//检查套餐/促销对产品的条件是否满促
			checkCampaignCondProduct(campaignid.intValue(),packageArray,paramDTO);			
			//检查套餐/促销对套餐/促销的条件是否满足
			checkCampaignCondCampaign(campaignid.intValue(),campaignArray,paramDTO);
			//检查套餐对产品的购买重复问题
			//checkNoExitMultiCampToProduct( campaignid, packageArray, paramDTO, productToCmpMap);
		}
		//清空
		productToCmpMap.clear();
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkCampaignDependency检查结束");
	}
	
	/**
	 *  检查所购买的产品包是否符合客户所在的区域
	 * @param packageArray ： 客户购买的产品包
	 * @param districtID ： 客户所在的区域
	 * @throws CommandException
	 */
	private void checkPackageByDistrict(Collection packageArray, int districtID) throws ServiceException {
		//1. 构建某地区所能够买的所有产品包set
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
						throw new ServiceException("客户购买的["+BusinessUtility.getProductPackageDTOByID(currentPackageID.intValue()).getPackageName()+"]不允许在该区域销售。");
				}
				
			}	
		}catch(SQLException e) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkPackageByDistrict", e);
			throw new ServiceException("取得地区（" + BusinessUtility.getDistrictNameById(districtID) + "）所能购买的产品包时出现错误。");
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
	 *  检查所选择的优惠是否符合客户所在的区域
	 * @param campaignArray ： 客户选择的优惠
	 * @param districtID ： 客户所在的区域
	 * @throws CommandException
	 */
	private void checkCampaignByDistrict(Collection campaignArray, int districtID) throws ServiceException {
		//1. 构建某地区所能够选择的优惠set
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
						throw new ServiceException("客户购买的["+BusinessUtility.getCampaignDTOByID(currentcampaignID.intValue()).getCampaignName()+"]不允许在该区域销售。");
				}
				
			}
		}catch(SQLException e) {
			LogUtility.log(clazz,LogLevel.ERROR, e);
			throw new ServiceException("检查地区（" + BusinessUtility.getDistrictNameById(districtID) + "）所能选择的优惠时出现错误。");
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
	 * 检查购买产品时所选择的硬件设备是否正确，检查所选的产品和所选的硬件设备是否匹配
	 * @param terminalDeviceTable  所选取的终端设备的相关信息
	 * @param productCol			产品的列表信息
	 * @param isInitCsiProductInfo  是否建立硬件产品和对应硬件设备的相关信息
	 * @throws ServiceException
	 */
	private void checkSelectTerminalDevice(CustServiceInteractionDTO csiDTO,HashMap terminalDeviceTable,Collection productCol,boolean isInitCsiProductInfo)throws ServiceException {
		Collection csiProdCol=checkSelDeviceAndReturnCsiProdInfo(csiDTO,terminalDeviceTable,productCol,isInitCsiProductInfo);
		if(isInitCsiProductInfo){
			//把取得的硬件产品对应的产品和设备信息设置到上下文环境中
			serviceContext.put(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST,csiProdCol);
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"调试是否存在对应的产品和设备信息",csiProdCol);
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkSelectTerminalDevice检查硬件设备结束");
	}
	
	/**
	 * 检查购买产品时所选择的硬件设备是否正确，检查所选的产品和所选的硬件设备是否匹配,并返回构造的csiProductInfo
	 * @param terminalDeviceTable
	 * @param productCol
	 * @param isInitCsiProductInfo
	 * @return
	 * @throws ServiceException
	 */
	public Collection checkSelDeviceAndReturnCsiProdInfo(CustServiceInteractionDTO csiDTO,HashMap terminalDeviceTable,Collection productCol,boolean isInitCsiProductInfo)throws ServiceException {
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"■■csiDTO■■"+csiDTO);
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"■■terminalDeviceTable■■"+terminalDeviceTable);
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"■■productCol■■"+productCol);
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"■■isInitCsiProductInfo■■"+isInitCsiProductInfo);
		if(productCol==null || productCol.isEmpty()){
			throw new ServiceException("没有产品。");
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkSelDeviceAndReturnCsiProdInfo检查所选的硬件设备");
		//新建一个用来存放验证后正确的设备内容（设备ID）
		Hashtable correctDeviceTable=null;	
		//新建一个用来存储硬件产品和其相关的设备信息
		Collection initedCsiProductInfoCol=new ArrayList();
		if(terminalDeviceTable!=null && !terminalDeviceTable.isEmpty() ){
			// 取得所有硬件设备的所有序列号
			Set currentKeySet = terminalDeviceTable.keySet();
			correctDeviceTable=new Hashtable();
			String currntSerialNo=null;
			String currentDeviceClass=null;
			String currentDeviceModel=null;
			Iterator currentDeviceIter=currentKeySet.iterator();
			while(currentDeviceIter.hasNext()){
				//取得所有硬件设备的所有序列号
				currntSerialNo=(String)currentDeviceIter.next();
				//取得该硬件设备序列号对应的设备类型
				currentDeviceClass=(String)terminalDeviceTable.get(currntSerialNo);
				//获取终端设备设备的信息
				TerminalDeviceDTO terminalDevice=BusinessUtility.getDeviceBySerialNo(currntSerialNo);
				
				if(!terminalDevice.getDeviceClass().equals(currentDeviceClass))
					throw new ServiceException("序列号（" + currntSerialNo + "）对应设备的设备类型不是"+HelperCommonUtil.getNameByDeviceClass(currentDeviceClass));
				//判断该设备对应的设备的状态是否正确
				if (!CommonConstDefinition.DEVICE_STATUS_WAITFORSELL.equals(terminalDevice.getStatus()))
					throw new ServiceException("所选择的"+HelperCommonUtil.getNameByDeviceClass(currentDeviceClass)+"的状态为"+
								HelperCommonUtil.getCommonNameByCacheNameAndKey("SET_D_DEVICESTATUS",terminalDevice.getStatus())+
								",请选择状态为待售的设备！");
				//取得所选设备序列号对应的设备型号
				currentDeviceModel=terminalDevice.getDeviceModel();
				//根据受理单的受理类型和受理原因检查是否和设备用途匹配
				checkDeviceMatchToCsiTypeAndReason(csiDTO,terminalDevice);
				//回置一下设备信息
				int currentDeviceID=terminalDevice.getDeviceID();
				//设置所选择的设备序列号对应的设备id和设备型号
				LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"调试硬件和设备序列号","currentDeviceID="+currentDeviceID+";currentDeviceModel="+currentDeviceModel);
				correctDeviceTable.put(new Integer(currentDeviceID),currentDeviceModel);
			}
			
			//检查当前设备是否配对(只检查STB,SC) 2007-12-21
			if (terminalDeviceTable.size() == 1) { // 补卡时，只有一个设备：机顶盒或者智能卡。检查设备必须是未配对的。
				Set checkCurrentKeySet = terminalDeviceTable.keySet();
				Iterator checkCurrentDeviceIter = checkCurrentKeySet.iterator();
				if (checkCurrentDeviceIter.hasNext()) {
					String checkCurrntSerialNo = (String) checkCurrentDeviceIter
							.next();
					TerminalDeviceDTO checkTerminalDevice = BusinessUtility
							.getDeviceBySerialNo(checkCurrntSerialNo);
					if (CommonConstDefinition.DEVICECALSS_STB.equals(checkTerminalDevice.getDeviceClass()) || CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(checkTerminalDevice.getDeviceClass())) {
						if (BusinessUtility.isDeviceMatch(checkCurrntSerialNo)) {
							throw new ServiceException("所选设备已经配对，配对关系不正确。");
						}
					}
				}
			}
			if (terminalDeviceTable.size() > 1) { // 多个设备，检查设备配对关系
				checkDeviceMatch(terminalDeviceTable);
			}
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkSelDeviceAndReturnCsiProdInfo检查所选的硬件设备和所选硬件产品是否匹配");
		//传入进来的产品参数
		Iterator currentProductIter=productCol.iterator();
		//用来存放已经检查过的产品的ID
		Collection currentDeviceIDCol=new ArrayList();
		while(currentProductIter.hasNext()){
			Integer currentProductID=(Integer)currentProductIter.next();	
			ProductDTO product=BusinessUtility.getProductDTOByProductID(currentProductID.intValue());
			if(BusinessUtility.getDeviceModelDTOByProductID(product.getProductID()).isEmpty())
				continue;
			//如果是硬件产品，需要判断所选的硬件产品和设备的序列号是否对应
			if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(product.getProductClass())){
				//检查所选的硬件产品中是否存在对应的设备
				boolean isExit=false;
				//取得所有设备到产品的映射对象
				Collection deviceToProductCol=BusinessUtility.getDevToProdByProductId(currentProductID.intValue());
				if(deviceToProductCol==null ||deviceToProductCol.isEmpty()){
					throw new ServiceException("产品找不到对应得设备型号。");
				}
				Iterator deviceToProductIter=deviceToProductCol.iterator();
				//取得该产品对应的所有设备型号
				Collection deviceModelCol=new ArrayList();
				while(deviceToProductIter.hasNext()){
					deviceModelCol.add(((DeviceMatchToProductDTO)deviceToProductIter.next()).getDeviceModel());
				}
				//取得在设备序列号对应的设备检查中设置的内容
				//侯11-23添加
				if(correctDeviceTable==null||correctDeviceTable.isEmpty()){
					throw new ServiceException("没有有效的设备。");
				}
				Iterator checkDeviceIter=correctDeviceTable.keySet().iterator();
				Integer currentDeviceID=null;
				//用来存放已经和产品关联的硬件设备的ID
				Collection haveLinkToProductDeviceList=new ArrayList();
				while(checkDeviceIter.hasNext()){
					//取得设备id
					currentDeviceID=(Integer)checkDeviceIter.next();
					//如果包含设备id，证明选择了同种设备型号的硬件
					if(currentDeviceIDCol.contains(currentDeviceID)){
						throw new ServiceException("所选设备中出现了重复的硬件。");
					}
					//检查是否设备型号和产品的型号匹配
					if(deviceModelCol.contains((String)correctDeviceTable.get(currentDeviceID))){
						//设置和产品关联的设备的ID
						if(!haveLinkToProductDeviceList.contains(currentDeviceID)){
							//是否需要建立硬件产品的CsiProductInfo信息
							if(isInitCsiProductInfo){
								//初始化和传入进来的产品对应的受理单涉及的客户产品相关信息
								CsiCustProductInfoDTO csiCustProductInfoDTO=new CsiCustProductInfoDTO();
								//设置产品id
								csiCustProductInfoDTO.setProductID(currentProductID.intValue());
								//设置产品对应的硬件设备的id
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
					throw new ServiceException("没有为购买的硬件产品选择对应的设备。");
				}
				if(currentDeviceID!=null){
					correctDeviceTable.remove(currentDeviceID);
				}
			}
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkSelDeviceAndReturnCsiProdInfo检查硬件设备结束");
		return initedCsiProductInfoCol;
	}
	/**
	 * 根据受理单的受理类型和受理原因检查是否和设备用途匹配
	 * @param csiDTO
	 * @param terminalDevice
	 * @throws ServiceException
	 */
	private static void checkDeviceMatchToCsiTypeAndReason(CustServiceInteractionDTO csiDTO,TerminalDeviceDTO terminalDevice)throws ServiceException{
		//取得设备对应的用途，检查受理原因和受理理类型是否匹配设备类型
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
				if(!ismatch)throw new ServiceException("所选择设备（"+terminalDevice.getSerialNo()+"）的用途不符合本次受理类型和原因");
			}
		}
	}
	
	//wang fang 2008.05.15 begin
	//设备更换设备用途检查逻辑更改
	/*************************************************************/
	private static void checkDeviceMatchToCsiTypeAndReason(
			CustServiceInteractionDTO csiDTO, TerminalDeviceDTO terminalDevice,
			TerminalDeviceDTO oldterminalDevice) throws ServiceException {
		// 取得设备对应的用途，检查受理原因和受理理类型是否匹配设备类型
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
					throw new ServiceException("所选择设备（"
							+ terminalDevice.getSerialNo() + "）的用途不符合本次受理类型和原因");
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
				throw new ServiceException("设备更换的时候所选择的新设备（"
						+ terminalDevice.getSerialNo() + "）的用途与老设备的用途不符");

		}
			}
	}
	/*************************************************************/
	//wang fang 2008.05.15 end
	
	/**
	 *  检查产品依赖性条件是否满足
	 *  产品之间的依赖性：授权依赖/购买依赖/排斥/升级/降级
	 *  说明： 这里只检查 授权依赖[D]/购买依赖[P]
	 * @param productSet
	 * @throws CommandException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void checkProductDependency(Collection productCol) throws ServiceException {
		checkProductDependency(productCol,BusinessUtility.getAllProductDepentDefineList(productCol));
	}
	private void checkProductDependency(Collection productCol,Collection productDependency) throws ServiceException {
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkProductDependency检查依赖！");
		if ((productCol == null) || productCol.isEmpty()) throw new ServiceException("没有产品！");
		
		Iterator iteProduct = productCol.iterator();	
		//用来检查购买得产品是否重复
		Collection contianCol=new ArrayList();
		while (iteProduct.hasNext()) {
			Integer productID = ((Integer) iteProduct.next());
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkProductDependency检查依赖  contianCol"+contianCol);
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkProductDependency检查依赖  productID"+productID);
			if(contianCol.contains(productID))
				throw new ServiceException("产品购买不能重复，本次选择购买的产品ID："+BusinessUtility.getProductNameByProductID(productID.intValue()) +" 重复！");
			contianCol.add(productID);

			//检查授权依赖
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"检查授权依赖");
			Collection dependencyOfAuth = getProdDependByProdIdAndType(productID.intValue(),CommonConstDefinition.PRODUCTDEPENDENCYTYPE_P,productDependency);
			if(!(dependencyOfAuth==null || dependencyOfAuth.isEmpty())){
				Iterator itDependencyOfAuth=dependencyOfAuth.iterator();
				while(itDependencyOfAuth.hasNext()){
					ProductDependencyDTO pdDTO = (ProductDependencyDTO) itDependencyOfAuth.next();
					checkProductSingleDependency(pdDTO,productCol,CommonConstDefinition.PRODUCTDEPENDENCYTYPE_D);
				}
			}
			
			//检查购买依赖
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"检查购买依赖");
			Collection dependencyOfBuy = getProdDependByProdIdAndType(productID.intValue(),CommonConstDefinition.PRODUCTDEPENDENCYTYPE_D,productDependency);
			if(!(dependencyOfBuy==null || dependencyOfBuy.isEmpty())){
				Iterator itDependencyOfBuy=dependencyOfBuy.iterator();
				while(itDependencyOfBuy.hasNext()){
					ProductDependencyDTO pdDTO = (ProductDependencyDTO) itDependencyOfBuy.next();
					checkProductSingleDependency(pdDTO,productCol,CommonConstDefinition.PRODUCTDEPENDENCYTYPE_P);
				}
			}
			
			//检查产品排斥关系
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"产品排斥关系检查");
			Collection dependencyOfcollision = getProdDependByProdIdAndType(productID.intValue(),CommonConstDefinition.PRODUCTDEPENDENCYTYPE_C,productDependency);
			if(!(dependencyOfcollision==null || dependencyOfcollision.isEmpty())){
				Iterator itDependencyOfCollistion=dependencyOfcollision.iterator();
				while(itDependencyOfCollistion.hasNext()){
					ProductDependencyDTO pdDTO = (ProductDependencyDTO) itDependencyOfCollistion.next();
					checkProductSingleDependency(pdDTO,productCol,CommonConstDefinition.PRODUCTDEPENDENCYTYPE_C);
				}
			}
		}
		LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在checkProductDependency检查结束");
	}
	
	
	public Collection getNoMatchProductDependency(Collection productCol,Collection productAllCol) throws ServiceException {
		Collection returnCol =new ArrayList();
		Collection productDependency = BusinessUtility.getAllProductDepentDefineList(productCol);
		Iterator iteProduct = productCol.iterator();	
		while (iteProduct.hasNext()) {
			Integer productID = ((Integer) iteProduct.next());
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在getNoMatchProductDependency检查依赖  productID"+productID);

			//检查授权依赖
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"检查授权依赖");
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
			
			//检查购买依赖
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"检查购买依赖");
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
			
			//检查产品排斥关系
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"产品排斥关系检查");
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
	 * 产品关系检查,该方法原在businessutility中,用jdbc实现,会引用游标超出异常,现在此处,操作集合,
	 * @param productID
	 * @param type
	 * @return
	 * @throws ServiceException
	 */
	private Collection getProdDependByProdIdAndType(int productID,String type,Collection productDependency) throws ServiceException {
		Collection returnCol = new ArrayList();
		
		Collection typeCol=PublicService.stringSplitToTargetObject(type,",","String");
		if(typeCol==null){
			throw new ServiceException("产品关系检查类型未知！");
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
	 * 产品购买关系检查，根据type的类型进行相应检查
	 * 本方法实现的产品之间的依赖性检查包括：授权依赖/购买依赖/排斥
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
		
		//把ProductDependencyDTO的ReferProductIDList分割为collection
		String strReferProductIDs=dto.getReferProductIDList();
		if(strReferProductIDs==null || "".equals(strReferProductIDs))return;
		String [] strReferProductID=strReferProductIDs.split(",");
		for(int i=0;i<strReferProductID.length;i++){
			if(!(strReferProductID[i]==null || "".equals(strReferProductID[i]))){
				try{
					referProductIDList.add(new Integer(strReferProductID[i]));
				}
				catch(Exception e){
					LogUtility.log(BusinessRuleService.class,LogLevel.WARN,"产品依赖关系配置错误：ReferProductIDList转化成产品ID错误，SEQ="+ dto.getSeqNo());
					throw new ServiceException("产品依赖关系配置出错！");
				}
			}
		}
		
		//检查依赖
		//全匹配
		if(CommonConstDefinition.YESNOFLAG_YES.equals(dto.getReferAllFlag())){
			Iterator itReferProductID=referProductIDList.iterator();
			while(itReferProductID.hasNext()){
				Integer productID=(Integer)itReferProductID.next();
				if(productCol.contains(productID)){
					if(CommonConstDefinition.PRODUCTDEPENDENCYTYPE_C.equals(type))
						throw new ServiceException("本次操作不能购买[" +BusinessUtility.getProductNameByProductID(productID.intValue()) +"]产品！");
				}
				else{
					if(CommonConstDefinition.PRODUCTDEPENDENCYTYPE_P.equals(type))
						throw new ServiceException("本次操作必须购买[" +BusinessUtility.getProductNameByProductID(productID.intValue()) +"]产品！");
				}
			}
		}
		//部分匹配
		else{
			int hasCount=0;
			Iterator itReferProductID=referProductIDList.iterator();
			while(itReferProductID.hasNext()){
				Integer productID=(Integer)itReferProductID.next();
				if(productCol.contains(productID)){
					//排斥
					if(CommonConstDefinition.PRODUCTDEPENDENCYTYPE_C.equals(type))
						throw new ServiceException("本次操作不能购买["+BusinessUtility.getProductNameByProductID(productID.intValue())+"]的产品");	
					hasCount++;	
				}
			}
			
			LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"hasCount="+ hasCount +";dto.getReferProductNum()=" +dto.getReferProductNum());
			if(hasCount<dto.getReferProductNum() && (!CommonConstDefinition.PRODUCTDEPENDENCYTYPE_C.equals(type)))
				throw new ServiceException("产品依赖关系不满足");
		}
	}
	/**
	 * 检查产品依赖,根据传入的psid集合按业务帐户分组,再调用原有方法检查.
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
	 * 检查产品操作时的所选产品是否满足操作的条件
	 * @param custProductCol 选中需要处理的产品集合
	 * @throws ServiceException
	 */
	private void checkProductOperationDependency(Collection custProductCol,int serviceAccountID) throws ServiceException {
		try {
			//取得业务账户下的已有的有效的产品
			Collection haveExitProductCol=BusinessUtility.getProductIDListByServiceAccount(serviceAccountID," STATUS<>'"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"'");
			// 取得所有选中产品的productID并存放在productCol这个集合中
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
					//这个变量用来存放在当前业务账户下依赖于当前产品的所有有效产品
					Collection currentConditioCol=new ArrayList();
					int productID=((Integer)productIter.next()).intValue();
					//依赖当前产品的所有的产品
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
					
					
					//所选产品必须全部包含依赖产品，否则不满足暂停取消条件
					if(!productCol.containsAll(currentConditioCol)){
						throw new ServiceException("该操作导致产品的依赖关系不满足，不能执行该项操作。");
					}
				}
			}
		}catch(HomeFactoryException e1) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkProductDependency",e1);
			throw new ServiceException("检查客户产品的依赖性关系时定位错误。");
		}catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkProductDependency",e2);
			throw new ServiceException("检查客户产品的依赖性关系时查找错误。");
		}
	} 
	/**
	 * 6-8侯添加,产品不可重复,
	 * @param productCol
	 * @throws ServiceException
	 */
	private void checkProductTautology(Collection productCol)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "产品重复检查");

		Iterator it1 = productCol.iterator();
		Iterator it2 = productCol.iterator();
		LogUtility.log(clazz, LogLevel.DEBUG, "productCol" + productCol);
		while (it1.hasNext()) {
			// Integer proID=(Integer) it1.next();
			// if(productCol.contains(proID))
			int oldProID = ((Integer) it1.next()).intValue();
			LogUtility.log(clazz, LogLevel.DEBUG, "产品重复检查当前产品ID:" + oldProID);
			boolean isFirst = false;
			while (it2.hasNext()) {
				int newProID = ((Integer) it2.next()).intValue();
				if (oldProID == newProID) {
					if (!isFirst)
						isFirst = true;
					else{
						throw new ServiceException("产品重复添加。");
					}
				}
			}

		}
	}
	/**
	 * 检查一个业务帐户下相同的亲情号码是否已经增加
	 * @param inEvent
	 */
	public void checkVoice(VoiceEJBEvent inEvent)throws ServiceException {
			int serviceAccountID=inEvent.getVoiceFriendPhoneNoDTO().getServiceAccountID();
			String phoneNO=inEvent.getVoiceFriendPhoneNoDTO().getPhoneNo();
			try {
				VoiceFriendPhoneNoHome voiceFriendPhoneNoHome=HomeLocater.getVoiceFriendPhoneNoHome();	
				Collection col= voiceFriendPhoneNoHome.findByServiceAccountIDAndPhoneNO(serviceAccountID,phoneNO);
				if(!(col==null||col.isEmpty())){
					throw new ServiceException("亲情号码重复添加！");
				}
			} catch (FinderException e1) {
				return;
			}catch(HomeFactoryException e2) {
				LogUtility.log(clazz,LogLevel.ERROR,"checkVoice",e2);
				throw new ServiceException("检查语音号码的重复性时定位错误。");
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
	 * 侯修改,增加对促销时效判断,目前用于排程检查
	 * @param customerProductID
	 * @param checkAction
	 * @throws ServiceException
	 */
	public void checkCampainByCpId(int customerProductID,int checkAction,Timestamp checkDate) throws ServiceException {
	   LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在canAllowThisAction检查优惠期内是否允许操作");
	   //由于数据模型的更改,以前处理已经失效,所以删除,新的处理待追加
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
			     
			     //当前促销如果不是有效状态的
			     if (!CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL.equals(custCampaign.getStatus())
			    	 && !CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_TRANSFE.equals(custCampaign.getStatus()))
			    	 continue;
			     
			     //当前促销是过期的,不检查,跳过当前循环.
			     LogUtility.log(clazz, LogLevel.DEBUG,"\n≯≯≯≯≯checkDate:"+ checkDate
			    		  +"\n≯≯≯≯≯custCampaign.getDateTo():"+ custCampaign.getDateTo()
			    		  +"\n≯≯≯≯≯custCampaign.getCcID():"+ custCampaign.getCcID().intValue());
			      if (checkDate.after(custCampaign.getDateTo())) 
			    	 continue;
			      
			      switch(checkAction){
				     case 1: //产品暂停
					   currentAllow=custCampaign.getAllowPause();
					   break;
				     case 2: //产品取消
					   currentAllow=custCampaign.getAllowClose();
					   break;
				     case 3: //产品恢复
					   break;
				     case 4: //业务帐户过户
					   currentAllow=custCampaign.getAllowTransfer();
					   break;
				     case 5: //销业务帐户
				       currentAllow=custCampaign.getAllowClose();
					   break; 
				     case 6: //客户迁移
				       currentAllow=custCampaign.getAllowTransition();
					   break; 
				     case 7: //客户过户
				       currentAllow=custCampaign.getAllowTransfer();
					   break; 
				     case 8: //客户销户,退户
				       currentAllow=custCampaign.getAllowClose();
					   break; 
			      }
			      
			      if (checkAction ==3) continue;
			     			      
			      if (!"".equals(currentAllow) && currentAllow!=null){
					  if (currentAllow.equals(CommonConstDefinition.YESNOFLAG_NO) &&
						  (checkDate.before(custCampaign.getDateTo())||checkDate.equals(custCampaign.getDateTo())))
							throw new ServiceException("当前产品在优惠期内不允许这样的操作");
				  }
			      
			      if (checkAction !=4 && checkAction !=5) continue;
			      
                  // 只检查套餐存在尚未到期和跨业务帐户
			      CampaignDTO campaignDto =BusinessUtility.getCampaignDTOByID(custCampaign.getCampaignID());
			      if (campaignDto ==null || campaignDto.getCampaignType() ==null) 
			    	  throw new ServiceException("当前优惠数据有误，请与管理员联系！");
			      if (campaignDto.getCampaignType().equals(CommonConstDefinition.CAMPAIGNCAMPAIGNTYPE_NORMAL)) 
			    	  continue;
			      			      
			      if (!BusinessUtility.getCustCampaignAndServiceAccountID(custCampaign.getCustomerID(),custCampaign.getCcID().intValue())){
			    	   throw new ServiceException("当前产品"+cPCampaignMap.getCustProductID()+"对应的套餐跨业务帐户，不能做这样的操作！");
			      }
	    	   }
	    	}
	   }catch (HomeFactoryException e) {
		   LogUtility.log(clazz, LogLevel.ERROR, e);
		   throw new ServiceException("检查CustomerProductHome定位错误");
       }catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("检查CustomerProduct能否执行该项操作时找不到相关信息");	
        }
	   
	   
	   LogUtility.log(BusinessRuleService.class,LogLevel.DEBUG,"在canAllowThisAction检查结束");
	}
	/**
	 * 检查是否满足取消客户产品的条件
	 * @param custCampaignID
	 * @param cutProductNeed
	 * @return 
	 * @throws ServiceException 
	 */
	public void checkCusCampaign(int CCID) throws ServiceException{
		
			if(BusinessUtility.checkCancleCamp(CCID))
				throw new ServiceException("客户促销已经过期或客户促销不是有效！");
	}
	public void checkCustomerCampaignPrePayment(int ccid) throws ServiceException{
		CustomerCampaignDTO ccDto=BusinessUtility.getCustomerCampaignDTOByID(ccid);
		if(!(CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NEW.equals(ccDto.getStatus())
				||CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL.equals(ccDto.getStatus()))){
			throw new ServiceException("客户促销不是有效！");
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
					throw new ServiceException(dto.getPackageName()+"不在协议涵盖范围内，请与管理员联系！");
				}
			}
		}else{
			throw new ServiceException("该客户不是协议客户，请到套餐续费功能中续费！");
		}
	}

	/**
	 * 检查两个套餐活动是否能够转换
	 * 
	 * @param dto
	 * @throws ServiceException
	 */
	public void checkBundleTransfer(CustServiceInteractionDTO csiDTO,CustomerCampaignDTO ccDto, int campaignID,
			Map addProductMap, Collection cancelCpList,HashMap terminalMap) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "开始进套餐转换检查,ccDto"+ccDto);

		if (ccDto==null||ccDto.getCcID()==0||!CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL.equals(ccDto
				.getStatus())) {
			throw new ServiceException("无效的客户套餐,不能用于转换.");
		}
		Timestamp curDate = TimestampUtility.getCurrentDateWithoutTime();
		if (curDate.before(ccDto.getDateFrom())
				|| curDate.after(ccDto.getDateTo())) {
			throw new ServiceException("客户套餐不在有效期内,不能用于转换.");
		}
		// 得到用户的地址
		CustomerDTO customer = BusinessUtility.getCustomerDTOByCustomerID(ccDto
				.getCustomerID());
		if (!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer
				.getStatus())) {
			throw new ServiceException("客户状态无效.");
		}
		if (BusinessUtility.existUnpaidInvoice(ccDto.getAccountID())) {
			throw new ServiceException("套餐付费账户有未付账单。");
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
				throw new ServiceException("客户类型不满足套餐条件。");
		}
		if (campaignDTO.getOpenSourceTypeList() != null
				&& !"".equals(campaignDTO.getOpenSourceTypeList())) {
			if (campaignDTO.getOpenSourceTypeList().indexOf(
					customer.getOpenSourceType()) == -1)
				throw new ServiceException("客户的来源渠道不满足套餐条件。");
		}
		// 检查客户所在区域是否可以使用该套餐
		Collection col = new ArrayList();
		col.add(new Integer(campaignID));
		checkCampaignByDistrict(col, customer.getAddressID());
		CommonBusinessParamDTO paramDTO = new CommonBusinessParamDTO();
		paramDTO.setCustomerID(ccDto.getCustomerID());
		paramDTO.setAccountID(ccDto.getAccountID());
		paramDTO.setServiceaccountID(ccDto.getServiceAccountID());

		if (addProductMap != null && !addProductMap.isEmpty()) {
			// 检查套餐/促销对产品包的条件是否满足
			checkCampaignCondPackage(campaignID, addProductMap.values(), paramDTO);
			// 检查套餐/促销对产品的条件是否满促
			checkCampaignCondProduct(campaignID, addProductMap.keySet(), paramDTO);
			//检查硬件匹配.
			if(terminalMap!=null&&!terminalMap.isEmpty()){
				checkSelectTerminalDevice(csiDTO,terminalMap,addProductMap.keySet(),true);
			}
		}
		// 检查套餐/促销对套餐/促销的条件是否满足
		ArrayList campaignArray = new ArrayList();
		campaignArray.add(new Integer(campaignID));
		checkCampaignCondCampaign(campaignID, campaignArray, paramDTO);
		// 检查套餐对产品的购买重复问题
		// checkNoExitMultiCampToProduct( campaignID, addProductMap.values(),
		// paramDTO, productToCmpMap);

		LogUtility.log(clazz, LogLevel.DEBUG, "检查产品是否可以被取消");
		// 检查是否可以取消产品.
		// 把要取消的产品分组,进行检查.
		if (cancelCpList != null && !cancelCpList.isEmpty()) {
			Map saPsidMap = BusinessUtility
					.splitCustomerProductWithServiceAccount(cancelCpList);
				CustomerProductHome customerProductHome;
				try {
					customerProductHome = HomeLocater.getCustomerProductHome();
				} catch (Exception e) {
					LogUtility.log(clazz, LogLevel.ERROR,e.getMessage());
					throw new ServiceException("检查可以取消的客户产品出错.");
				}				
				for (Iterator sait = saPsidMap.keySet().iterator(); sait
						.hasNext();) {
					Integer said = (Integer) sait.next();
					ArrayList curCpList = (ArrayList) saPsidMap.get(said);
					checkProductOperationDependency(curCpList, said.intValue());
					//套餐降级时不可以取消关键的业务帐户产品.
					for (Iterator cpIt = curCpList.iterator(); cpIt.hasNext();) {
						Integer psid = (Integer) cpIt.next();
						CustomerProduct customerProduct;
						try {
							customerProduct = customerProductHome
									.findByPrimaryKey(psid);
						} catch (Exception e) {
							LogUtility.log(clazz, LogLevel.ERROR,e.getMessage());
							throw new ServiceException("检查可以取消的客户产品出错.");
						}						
						ProductDTO pDto=BusinessUtility.getProductDTOByProductID(
								customerProduct.getProductID());
						if (customerProduct.getDeviceID() != 0
								&&CommonConstDefinition.YESNOFLAG_YES.equals(pDto.getNewsaFlag())) {
							throw new ServiceException("客户产品("
									+ pDto
											.getProductName() + ")不能取消.");
						}
					}
				}
			
		}
	}
	/**
	 * 检查业务帐户、帐户产品是否满足促销条件
	 * @param dto
	 */
	public void checkGrantCampaign(CustomerCampaignDTO dto,boolean mustCheckMulti)throws ServiceException{
		try{
			//检查用户是否在促销区内
			Integer campaignID=new Integer(dto.getCampaignID());
			Collection col=new ArrayList();
			col.add(campaignID);
			//得到用户的地址
			CustomerHome customerHome=HomeLocater.getCustomerHome();
			Customer customer=customerHome.findByPrimaryKey(new Integer(dto.getCustomerID()));
			CampaignDTO campaign = BusinessUtility.getCampaignDTOByID(campaignID.intValue());
			if(campaign.getCustTypeList()!=null && !"".equals(campaign.getCustTypeList())){
				if (campaign.getCustTypeList().indexOf(customer.getCustomerType()) == -1)
					throw new ServiceException("客户类型不满足促销条件。");
			}
			if(campaign.getOpenSourceTypeList()!=null && !"".equals(campaign.getOpenSourceTypeList())){
				if (campaign.getOpenSourceTypeList().indexOf(customer.getOpenSourceType()) == -1)
					throw new ServiceException("客户开户的来源渠道不满足促销条件。");
			}
			checkCampaignByDistrict(col,customer.getAddressID());
			
			CommonBusinessParamDTO paramDTO = new CommonBusinessParamDTO();
			paramDTO.setCustomerID(dto.getCustomerID());
			paramDTO.setAccountID(dto.getAccountID());
			paramDTO.setServiceaccountID(dto.getServiceAccountID());
			
			//检查业务帐户下的产品是否满足条件
			CampaignDTO campaignDTO=BusinessUtility.getCampaignDTOByID(campaignID.intValue());
			dto.setDateTo(TimestampUtility.getTimeEnd(dto.getDateFrom(),
					campaignDTO.getTimeLengthUnitType(),
					campaignDTO.getTimeLengthUnitNumber()));
			LogUtility.log(clazz, LogLevel.DEBUG, "startTime:"+dto.getDateFrom());
			LogUtility.log(clazz, LogLevel.DEBUG, "endTime:"+dto.getDateTo());

			//业务帐户级别的促销，公用数据没有定义
			if(CommonConstDefinition.YESNOFLAG_NO.equals(campaignDTO.getObligationFlag())){
				if(dto.getServiceAccountID()==0){
					throw new ServiceException("该促销只能授予业务帐户。");
				}
				
				Map campaignArray=BusinessUtility.getCampaignIDByServiceAccountID(dto.getServiceAccountID());

				HashMap custProdMap=BusinessUtility.getProductIDAndPackageIDBySAID(dto.getServiceAccountID());

				checkCampaignCond(dto,paramDTO,campaignArray,custProdMap,mustCheckMulti);

			//帐户级别的促销
			}else if(CommonConstDefinition.YESNOFLAG_YES.equals(campaignDTO.getObligationFlag())){
				if(dto.getAccountID()==0){
					throw new ServiceException("该促销只能授予帐户。");
				}
				Map campaignArray= BusinessUtility.getCampaignIDByAccountID(dto.getAccountID());
				HashMap custProdMap=BusinessUtility.getProductIDAndPackageIDByAccountID(dto.getAccountID());
				checkCampaignCond(dto,paramDTO,campaignArray,custProdMap,mustCheckMulti);
			}
		}catch (HomeFactoryException e) {
			   LogUtility.log(clazz, LogLevel.ERROR, e);
			   throw new ServiceException("检查checkGrantCampaign定位错误");
		}catch(FinderException e) {
				LogUtility.log(clazz, LogLevel.ERROR, e);
				throw new ServiceException("检查checkGrantCampaign是否授予促销活动查找错误");
		}
	}
	private void checkCampaignCond(CustomerCampaignDTO dto,CommonBusinessParamDTO paramDTO,
			Map campaignArray,HashMap custProdMap,
			boolean mustCheckMulti)throws ServiceException{
		dto.setDateFrom(TimestampUtility.ClearTimePart(dto.getDateFrom()));
		dto.setDateTo(TimestampUtility.ClearTimePart(dto.getDateTo()));
		LogUtility.log(clazz, LogLevel.DEBUG, "新的促销:",dto);
		if(mustCheckMulti&&campaignArray!=null&&!campaignArray.isEmpty()){
			for (Iterator it = campaignArray.keySet().iterator(); it.hasNext();) {
				CustomerCampaignDTO ccDto = (CustomerCampaignDTO) it.next();
				ccDto.setDateFrom(TimestampUtility.ClearTimePart(ccDto.getDateFrom()));
				ccDto.setDateTo(TimestampUtility.ClearTimePart(ccDto.getDateTo()));
				LogUtility.log(clazz, LogLevel.DEBUG, "比较的促销:",ccDto);
				if ((dto.getDateFrom().after(ccDto.getDateFrom()) && dto
						.getDateFrom().before(ccDto.getDateTo()))
						|| (dto.getDateTo().after(ccDto.getDateFrom()) && dto
								.getDateTo().before(ccDto.getDateTo()))
						|| dto.getDateFrom().equals(ccDto.getDateFrom())
						|| dto.getDateTo().equals(ccDto.getDateTo())
						|| dto.getDateFrom().equals(ccDto.getDateTo())
						|| dto.getDateTo().equals(ccDto.getDateFrom())) {
					throw new ServiceException("该促销和已有客户促销" + ccDto.getCcID()
							+ "有效期重叠.");
				}
			}
		}
		//检查促销条件.
		checkCampaignCondCampaign(dto.getCampaignID(), null,campaignArray.values(), paramDTO);
		// 检查套餐/促销对产品包的条件是否满足
		checkCampaignCondPackage(dto.getCampaignID(), null,custProdMap.values(),paramDTO);
		// 检查套餐/促销对产品的条件是否满促
		checkCampaignCondProduct(dto.getCampaignID(), null,custProdMap.keySet(),paramDTO);
	} 
	public void checkVOIPProductExist(VOIPProductDTO dto,String checkType)throws ServiceException{
		VOIPProductDTO pdto=BusinessUtility.getVOIPProductDTOByProductIDAndSCode(dto.getProductID(),dto.getSssrvCode());
		if(pdto!=null&&checkType.equals("add"))
			throw new ServiceException("该语音产品已存在！");
		/*else if(pdto!=null&&checkType.equals("modify"))
			throw new ServiceException("修改后的语音产品已存在！");*/
	}
	public void checkVOIPConditionExist(VOIPConditionDTO dto,String checkType)throws ServiceException{
		VOIPConditionDTO pdto=BusinessUtility.getVOIPConditionDTOByConditionID(dto.getConditionID());
		if(pdto!=null&&checkType.equals("add"))
			throw new ServiceException("该语音条件已存在！");
		/*else if(pdto!=null&&checkType.equals("modify"))
			throw new ServiceException("修改后的语音条件已存在！");*/
	}
	public void checkVOIPGatewayExist(VOIPGatewayDTO dto,String checkType)throws ServiceException{
		VOIPGatewayDTO gdto=BusinessUtility.getVOIPGatewayDTOByPK(dto.getDeviceModel(),dto.getDevsType());
		if(gdto!=null&&checkType.equals("add"))
			throw new ServiceException("该网关配置已存在！");
		/*else if(gdto!=null&&checkType.equals("modify"))
			throw new ServiceException("修改后的网关配置已存在");*/
	}
	/**
	 * 创建客户投诉处理记录时所作的业务检查
	 * @param inEvent
	 * @throws ServiceException
	 */
	public boolean checkCustComplainCanProcess(CustComplainProcessDTO pdto)throws ServiceException{
		String status=BusinessUtility.getCustComplainStatusByPK(pdto.getCustComplainId());
		if(status.equals(CommonConstDefinition.CUSTOMER_COMPLAIN_SUCESS)||status.equals(CommonConstDefinition.CUSTOMER_COMPLAIN_FALI)){
			throw new ServiceException("该投诉已处理完毕！");
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
	 * 检查合同
	 * fiona
	 * @param contractDTO
	 * @throws ServiceException 
	 */
	public  void checkOpenChildContract(ContractDTO contractDTO,int customerCount) throws ServiceException{
		if(contractDTO.getDateto().before(TimestampUtility.getCurrentDateWithoutTime()))
			throw new ServiceException("合同已经过期！");
		if(contractDTO.getUserCount()<contractDTO.getUsedCount()+customerCount)
			throw new ServiceException("已经达到最大用户数，不能继续开户！");
		
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
		//取得产品包对应的所有产品信息
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
	 * 集团客户新增子客户时有效性检查,
	 * @param event
	 * @throws ServiceException
	 */
	public void checkCreateGroupSubCustomer(GroupCustomerEJBEvent event)throws ServiceException{
		try {
			//取得客户购买的产品包信息
			Collection csiPackageArray = event.getContractToPackageIDCol();
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
	    	//取得集团客户信息
	    	int  customerID = event.getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	if(!CommonConstDefinition.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())){
	    		throw new ServiceException("只有正常正常集团客户才能增加子客户。");
	    	}
	    	//检查客户分区有效性 update chaoqiu 2007-04-10 =========
	    	Map allDistrict = BusinessUtility.getAllDistrictMap();
	    	if(!allDistrict.containsKey(event.getAddressDTO().getDistrictID()+""))
	    		throw new ServiceException("所属分区填写有误,不存在该分区。");
	    	//检查客户分区有效性 update chaoqiu 2007-04-10 =========end
	    	
	    	//取得账户信息
	    	int accountID=event.getAccountID();
	    	if (BusinessUtility.existUnpaidInvoice(accountID)){
	    		throw new ServiceException("所选择的账户有未付账单。");
	    	}
	    	//客户地址信息
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());
			//硬件设备信息
			//Hashtable terminalDeviceTable=event.getDeviceTable();
	    	HashMap terminalDeviceTable=event.getDeviceTable();
			//检查是否存在需要创建业务账户的产品
			if(!checkExitCreateServiceAccountProduct(csiPackageArray)){
				throw new ServiceException("选择产品包中不包含可以创建业务帐户的产品。");
			}
			//检查套餐和优惠促销条件
			CommonBusinessParamDTO paramDTO=new CommonBusinessParamDTO(customer.getCustomerType(),customer.getOpenSourceType(),event.getCsiDto().getType(),event.getOperatorID());
			checkPackageCampaign(csiPackageArray,null,custAddrDto,true,paramDTO);
			
			//取得产品包对应的所有产品信息
			Collection currentAllProductCol=new ArrayList();
			Iterator currentPackageIter=csiPackageArray.iterator();

			while(currentPackageIter.hasNext()){
				Integer currentPackageID=(Integer)currentPackageIter.next();
				currentAllProductCol.addAll(BusinessUtility.getProductIDsByPackageID(currentPackageID.intValue())) ;
			}
			//检查产品之间的依赖性
			checkProductDependency(currentAllProductCol);
			
			//检查所选的硬件设备和所选硬件设备和产品的映射关系是否正确
			checkSelectTerminalDevice(event.getCsiDto(), terminalDeviceTable,currentAllProductCol,true);
		
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建业务账户的检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建业务账户的检查中找不到相关信息");
			
		}
		
	}
	/**
	 * 集团客户子客户开户时检查合同有效性
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
					throw new ServiceException("只有状态生效的合同才能进行集团开户");
				}
				if (TimestampUtility.getCurrentDateWithoutTime().after(
						dto.getDateto())) {
					throw new ServiceException("合同已经过期失效");
				}
				if (TimestampUtility.getCurrentDateWithoutTime().before(
						dto.getDatefrom())) {
					throw new ServiceException("合同尚未到可以使用的有效日期");
				}
				if (TimestampUtility.getCurrentDateWithoutTime().after(
						dto.getNormaldate())) {
					throw new ServiceException("合同已经失效,过了最后开户截止日期");
				}
			} else {
				if (!CommonConstDefinition.CONTRACTSTATUS_EFFECT.equals(dto
						.getStatus())
						&& !CommonConstDefinition.CONTRACTSTATUS_OPEN
								.equals(dto.getStatus())) {
					throw new ServiceException("只有状态生效和开户的合同才能进行集团子客户开户/新增订户");
				}
				if (CommonConstDefinition.CONTRACTSTATUS_OPEN.equals(dto
						.getStatus())) {
					if (groupCustID != dto.getUsedCustomerID()) {
						throw new ServiceException("该合同只能用于该集团客户，集团客户ID:"
								+ dto.getUsedCustomerID());
					}
				}
				if (custCount + dto.getUsedCount() > dto.getUserCount()) {
					throw new ServiceException("用户数已经超过合同规定，合同规定数:"
							+ dto.getUserCount());
				}
			}

	}
	/**
	 
	 * 客户开户信息的正确性
	 * @param event
	 * @throws ServiceException
	 */
	public void checkOpenInfoForDirectly(BookEJBEvent event) throws ServiceException{
    	//取得新客户信息
    	NewCustomerInfoDTO newCustInfo = event.getNewCustInfo();
    	//取得新客户新账户信息
    	NewCustAccountInfoDTO newCustAcctInfo = event.getNewCustAcctInfo();
    	//取得受理单信息
		CustServiceInteractionDTO csiDTO=event.getCsiDto();
		//取得客户地址信息
		AddressDTO custAddrDto = event.getCustAddressDTO();
		//取得账户地址信息
		AddressDTO acctAddrDto = event.getAcctAddressDTO();
		
		//开始检查客户
		if (newCustInfo == null) 
			throw new ServiceException("没有客户信息。");
		//开始检查账户
		if (newCustAcctInfo == null)
			throw new ServiceException("没有账户信息。");
		//开始检查受理单
		if (csiDTO == null)
			throw new ServiceException("没有受理单信息。");
		//开始检查客户地址
		if ((custAddrDto == null) && acctAddrDto==null)
			throw new ServiceException("没有客户地址信息。");
		//开始检查客户账户地址		
		if ((custAddrDto == null) && acctAddrDto==null)
			throw new ServiceException("没有客户账户信息。");
	}
	/**
	 * 检查账户
	 * @param accountID
	 * @param futureRightID
	 * @throws ServiceException
	 */
	public void checkGrantFutureRight(int accountID,int futureRightID)throws ServiceException{
		if (accountID ==0 ||futureRightID==0)
			throw new ServiceException("没有账户信息或者期权信息");
		
		AccountDTO accountDTO=BusinessUtility.getAcctDTOByAcctID(accountID);
		if (accountDTO==null)
			throw new ServiceException("不是正常账户！");
		if (BusinessUtility.existUnpaidInvoice(accountID))
			throw new ServiceException("存在未付账单！");		
		try{
			FutureRightHome futureRightHome = HomeLocater.getFutureRightHome();
			FutureRight  futureRight =futureRightHome.findByPrimaryKey(new Integer(futureRightID));
			if(futureRight.getLockToDate().after(TimestampUtility.getCurrentTimestamp()))
				throw new ServiceException("在期权锁定期限内不允许兑现！");
		}catch(HomeFactoryException ex) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create",ex);
		    throw new ServiceException("检查期权时定位错误");
		 }catch(FinderException e1) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create", e1);
		    throw new ServiceException("检查期权时时错误");		    
		}
	}
	
	/**
	 * 检查促销促销的条件
	 * @param campaignID
	 * @param campaignArray
	 * @param param
	 * @throws ServiceException
	 */
	private void checkCampaignCondCampaign(int campaignID,Collection campaignArray,CommonBusinessParamDTO param)throws ServiceException{
		//该集合是根据客户id,帐户id,业务帐户id等作为条件检索出来的，oldcampaignColCol为整形的对象列表
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
				//针对新购买的套餐/促销检查套餐/促销条件
				if(CommonConstDefinition.YESNOFLAG_YES.equals(condCampaignDTO.getNewCaptureFlag())){
					checkIncludeRealation(condCampaignDTO.getCampaignID(),condCampaignDTO.getHasAllFlag(),condCampaignDTO.getCampaignNum(),newCampaignArray,campaignCol,"促销");
				}
				//针对已有的套餐/促销检查套餐/促销条件
				if(CommonConstDefinition.YESNOFLAG_YES.equals(condCampaignDTO.getExistingFlag())){
					checkIncludeRealation(condCampaignDTO.getCampaignID(),condCampaignDTO.getHasAllFlag(),condCampaignDTO.getCampaignNum(),oldCampaignArray,campaignCol,"促销");
				}
			}
		}
	}
	/**
	 * 检查促销对产品条件
	 * @param campaignID
	 * @param packageArray
	 * @param param
	 * @throws ServiceException
	 */
	private void checkCampaignCondProduct(int campaignID,Collection newPackageArray,CommonBusinessParamDTO param )throws ServiceException{
		//该集合是根据客户id,帐户id,业务帐户id等作为条件检索出来的，oldProductCol为整形对象列表
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
				//针对新购买的产品检查套餐/促销条件
				if(CommonConstDefinition.YESNOFLAG_YES.equals(condProductDTO.getNewCaptureFlag())){
					checkIncludeRealation(condProductDTO.getCampaignID(),condProductDTO.getHasAllFlag(),condProductDTO.getProductNum(),newPackageArray,productCol,"产品");
				}
				//针对已有的产品检查套餐/促销条件
				if(CommonConstDefinition.YESNOFLAG_YES.equals(condProductDTO.getExistingFlag())){
					checkIncludeRealation(condProductDTO.getCampaignID(),condProductDTO.getHasAllFlag(),condProductDTO.getProductNum(),oldPackageArray,productCol,"产品");
				}
			}
		}
		
	}
	
	/**
	 * 检查产品或包与条件产品或包的包含关系
	 * @param hasAllFlag
	 * @param num
	 * @param targetArray
	 * @param condArray
	 * @param checkDesc
	 * @throws ServiceException
	 */
	private void checkIncludeRealation(int campaignID,String hasAllFlag,int num,Collection targetArray,Collection condArray,
			String checkDesc)throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"■■促销■"+campaignID); 
		LogUtility.log(clazz,LogLevel.DEBUG,"■■目标产品包■"+targetArray); 
		LogUtility.log(clazz,LogLevel.DEBUG,"■■条件产品包■"+condArray); 
		if(CommonConstDefinition.YESNOFLAG_YES.equals(hasAllFlag)){
			 if(targetArray==null || targetArray.isEmpty())
				 targetArray=new ArrayList();
			 if(!targetArray.containsAll(condArray))
				 throw new ServiceException("所选套餐/促销的"+checkDesc+"购买条件不满足");
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
				 throw new ServiceException("套餐/促销对"+checkDesc+"最低个数条件不满足");
		 }else{
			 throw new ServiceException("套餐/促销对"+checkDesc+"配置条件不对");
		 }
	}
	/**
	 * 检查促销对产品包条件
	 * @param campaignID
	 * @param packageArray
	 * @param param
	 * @throws ServiceException
	 */
	private void checkCampaignCondPackage(int campaignID,
			Collection packageArray, CommonBusinessParamDTO param)
			throws ServiceException {
		// 该集合是根据客户id,帐户id,业务帐户id等作为条件检索出来的，oldProductCol为整形对象列表
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
				// 针对新购买的产品包检查套餐/促销条件
				if (CommonConstDefinition.YESNOFLAG_YES.equals(condPackageDTO
						.getNewPurchaseFlag())) {
					checkIncludeRealation(condPackageDTO.getCampaignID(),
							condPackageDTO.getHasAllFlag(), condPackageDTO
									.getPackageNum(), newPackageArray,
							packageCol, "产品包");
				}
				// 针对已有的产品包检查套餐/促销条件
				if (CommonConstDefinition.YESNOFLAG_YES.equals(condPackageDTO
						.getExistingFlag())) {
					checkIncludeRealation(condPackageDTO.getCampaignID(),
							condPackageDTO.getHasAllFlag(), condPackageDTO
									.getPackageNum(), oldPackageArray,
							packageCol, "产品包");
				}
			}
		}
	}
	/**
	 * 用来检查购买中的一个参品只能享有一个针对产品的套餐/促销
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
					throw new ServiceException("购买的同一产品不能同时参与两个及以上的套餐/促销");
				campaignCol=new ArrayList();
				campaignCol.add(campaignID);
				productToCmpMap.put(productID, campaignCol);
			}
		}
	}
	/**
	 * 用来检查客户条形码是否已经存在
	 * @param custSerialNo
	 * @throws ServiceException
	 */
	public static void checkEixtMultCustSerialNo(String custSerialNo) throws ServiceException{
		if(custSerialNo!=null && !"".equals(custSerialNo)){
			String value=BusinessUtility.getSystemSettingValue("SET_V_CHECK_CUSTSERIALNO");
			if("Y".equals(value)){
				int custCount=BusinessUtility.getCustomerCount(custSerialNo);
				if(custCount!=0)
					throw new ServiceException("客户条形码已经存在，请确认。");
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
		LogUtility.log(clazz,LogLevel.DEBUG,"■■caDto■"+caDto); 
		int exist=BusinessUtility.getCAWalletNumInSA(caDto.getServiceAccountId(), caDto.getCaWalletCode());
		if(exist>0){
			throw new ServiceException("该钱包已存在,不能在同一业务帐户下重复创建相同钱包.");
		}
		ArrayList requiredCADList = BusinessUtility.getRequiredCAWalletList();
		LogUtility.log(clazz,LogLevel.DEBUG,"■■requiredCADList■"+requiredCADList); 
		for(int i=0;requiredCADList!=null&&i<requiredCADList.size();i++){
			CAWalletDefineDTO cadDto=(CAWalletDefineDTO) requiredCADList.get(i);
			int inexist=BusinessUtility.getCAWalletNumInSA(caDto.getServiceAccountId(), cadDto.getCaWalletCode());
			if(inexist<1&&!cadDto.getCaWalletCode().equals(caDto.getCaWalletCode())){
				throw new ServiceException("必须先创建 "+cadDto.getCaWalletName()+" .");
			}
		}
	}
	
	//检查设备配对关系（用于开户，增机）
	public static void checkDeviceMatch(Map deviceTable)
			throws ServiceException {
		// 系统是否管理设备配对
		if(!BusinessUtility.isSystemManagerDeviceMatch())
			return ;

		LogUtility.log(clazz, LogLevel.DEBUG, "■■检查设备配对关系（用于开户，增机）■ 开始");
		LogUtility.log(clazz, LogLevel.DEBUG, "■■deviceTable■" + deviceTable);
		String serialNoSTB = "";
		String serialNoSC = "";
		if (deviceTable != null && !deviceTable.isEmpty()){
			// 取得所有硬件设备的所有序列号
			Set currentKeySet = deviceTable.keySet();
			String currntSerialNo = "";
			String currentDeviceClass = "";
			Iterator currentDeviceIter = currentKeySet.iterator();
			while (currentDeviceIter.hasNext()) {
				// 取得所有硬件设备的所有序列号
				currntSerialNo = (String) currentDeviceIter.next();
				// 取得该硬件设备序列号对应的设备类型
				currentDeviceClass = (String) deviceTable.get(currntSerialNo);
				if (CommonConstDefinition.DEVICECALSS_STB.equals(currentDeviceClass))
					serialNoSTB = currntSerialNo;
				else if (CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(currentDeviceClass))
					serialNoSC = currntSerialNo;
			}
		}
		// 当前设备是否配对(只检查STB,SC)
		if(!BusinessUtility.isDeviceMatch(serialNoSTB,serialNoSC))
			return ;
		// 配对关系是否指向对方(只检查STB,SC)
		if(!BusinessUtility.isDeviceMatchRelationship(serialNoSTB,serialNoSC)){
			throw new ServiceException("所选设备配对关系不正确。");
		}
	}
	
	//检查设备配对关系（用于设备更换）
	public static void checkDeviceMatch(String newSeaialNo)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "■■newSeaialNo■" + newSeaialNo);
		// 系统是否管理设备配对
		if (!BusinessUtility.isSystemManagerDeviceMatch()){
			return;
		}
		// 当前设备是否配对(只检查STB,SC)
		if (BusinessUtility.isDeviceMatch(newSeaialNo)) {
			throw new ServiceException("所选设备配对关系不正确。");
		} else {
			return;
		}
	}
	

}
