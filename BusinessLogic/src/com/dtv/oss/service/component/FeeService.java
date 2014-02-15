/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import com.dtv.oss.dto.*;
import com.dtv.oss.dto.custom.CommonFeeAndPayObject;
import com.dtv.oss.dto.custom.FeeAdjustmentWrapDTO;
import com.dtv.oss.dto.custom.PaymentAdjustWrapDTO;
import com.dtv.oss.dto.custom.PreDeductionAdjustWrapDTO;
import com.dtv.oss.dto.custom.BatchNoDTO;
import com.dtv.oss.domain.CsiCustProductInfo;
import com.dtv.oss.domain.CsiCustProductInfoHome;
import com.dtv.oss.domain.CustomerCampaign;
import com.dtv.oss.domain.FutureRight;
import com.dtv.oss.domain.FutureRightHome;

import javax.ejb.*;

import com.dtv.oss.domain.*;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.csr.OpenAccountGeneralEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.InfoFeeImmediateBilling;
import com.dtv.oss.service.util.PayFeeUtil;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.service.util.ImmediateFee.BillingObject;
import com.dtv.oss.service.util.ImmediateFee.ImmediateFeeCalculator;
import com.dtv.oss.service.util.ImmediateFee.ImmediateFeeList;
import com.dtv.oss.service.util.ImmediateFee.ProductInfo;
import com.dtv.oss.util.*;
/**
 * @author 220506l
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FeeService extends AbstractService{
	private static final Class clazz = FeeService.class;
	
	/**
	 * ����������Ʒ/����ʱ������һ���Է���
	 * @param csiDto ��������
	 * @param packageCol   ����Ĳ�Ʒ������
	 * @param campaignCol  ������ײͻ��ߴ����ļ���
	 * @param commonObj ���÷��ò�������
	 * @return
	 * @throws ServiceException
	 */
	public static Collection calculateImmediateFee(CustServiceInteractionDTO csiDto,Collection packageCol,Collection campaignCol,CommonFeeAndPayObject commonObj)throws  ServiceException{
		Collection feeCol=new ArrayList();
		//��װ�ƷѲ�������
		Collection billingObjectCol=PublicService.encapsulateBillingObject(commonObj, packageCol, campaignCol);
		LogUtility.log(clazz,LogLevel.DEBUG,"��billingObjectCol��"+billingObjectCol);
		//���ù��ýӿڼ���һ���Է���
		ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
		LogUtility.log(clazz,LogLevel.DEBUG,"��ImmediateFeeList��"+currentFeeList);
		//�Ѽ����һ���Է���ת��Ϊ���õļƷѶ���(������԰����Ź�ʱ�Ѿ�֧����֧���б�)
		ImmediateCountFeeInfo immediateCountFeeInfo= encapsulateFeeInfoAndAccountInfo(csiDto.getAccountID() ,csiDto.getType(),null,null);
		immediateCountFeeInfo.setAccountName(commonObj.getAccountName());
		immediateCountFeeInfo.setAccountid(csiDto.getAccountID());
		immediateCountFeeInfo.setAcctItemList(currentFeeList.getAcctItemList());
		//������Ѻ��ϲ�������֮��
		//if(commonObj.getDeposit()!=0.0f)
		if(Math.abs(commonObj.getDeposit())>0.0001)
			immediateCountFeeInfo.getAcctItemList().add(encapsulateDeposit(commonObj.getDeposit(),csiDto.getAccountID()));
		immediateCountFeeInfo.setPaymentRecordList(currentFeeList.getPaymentList());
		double countFee=countFee(currentFeeList.getAcctItemList());
		double countPay=countPayment(currentFeeList.getPaymentList());
		if(countFee>countPay)
			immediateCountFeeInfo.setTotalValueAccountItem(BusinessUtility.doubleFormat(countFee-countPay));
			//immediateCountFeeInfo.setTotalValueAccountItem((double)(Math.floor(countFee*100-countPay*100+0.01)/100));
		//�ѹ��õķ��ö���ŵ����ü�����
		feeCol.add(immediateCountFeeInfo);
		return feeCol;
	}
	/**
	 * �����ظ� �����һ���Է���.
	 * @param csiDto
	 * @param buyProductList
	 * @param commonObj
	 * @return
	 * @throws ServiceException
	 */
	public static Collection calculateImmediateFeeWithBatchBuy(CustServiceInteractionDTO csiDto,ArrayList buyProductList,CommonFeeAndPayObject commonObj)throws  ServiceException{
		Collection feeCol=new ArrayList();
		ImmediateCountFeeInfo immediateCountFeeInfo= encapsulateFeeInfoAndAccountInfo(csiDto.getAccountID() ,csiDto.getType(),null,null);
		immediateCountFeeInfo.setAccountName(commonObj.getAccountName());
		immediateCountFeeInfo.setAccountid(csiDto.getAccountID());

		for (Iterator buyIt = buyProductList.iterator(); buyIt.hasNext();) {
			Map buyInfo = (Map) buyIt.next();
			Integer buyNum = (Integer) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_BUYNUM);
			Integer buyIndex = (Integer) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_BUYINDEX);
			ArrayList buyPackageList = (ArrayList) buyInfo
					.get(OpenAccountGeneralEJBEvent.KEY_MPACKAGE);
			ArrayList buyCampaignList = (ArrayList) buyInfo
					.get(OpenAccountGeneralEJBEvent.KEY_CAMPAIGN);
			if(buyPackageList==null)buyPackageList=new ArrayList();
			if(buyCampaignList==null)buyCampaignList=new ArrayList();
			for (int i = 1; i <= buyNum.intValue(); i++) {
				//��װ�ƷѲ�������
				Collection billingObjectCol=PublicService.encapsulateBillingObject(commonObj, buyPackageList, buyCampaignList);
				LogUtility.log(clazz,LogLevel.DEBUG,"��billingObjectCol��"+billingObjectCol);
				//���ù��ýӿڼ���һ���Է���
				ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
				if(currentFeeList!=null){
					Collection curFeeList=currentFeeList.getAcctItemList();
					if(curFeeList!=null&&!curFeeList.isEmpty()){
						for(Iterator it=curFeeList.iterator();it.hasNext();){
							AccountItemDTO curFeeDto=(AccountItemDTO) it.next();
							curFeeDto.setGroupNo(buyIndex.intValue());
							curFeeDto.setSheafNo(i);
						}
					}
				}
				LogUtility.log(clazz,LogLevel.DEBUG,"��ImmediateFeeList��"+currentFeeList);
				//�Ѽ����һ���Է���ת��Ϊ���õļƷѶ���(������԰����Ź�ʱ�Ѿ�֧����֧���б�)
				immediateCountFeeInfo.getAcctItemList().addAll(currentFeeList.getAcctItemList());
				immediateCountFeeInfo.getPaymentRecordList().addAll(currentFeeList.getPaymentList());
			}
		}
		//������Ѻ��ϲ�������֮��
		//if(commonObj.getDeposit()!=0.0f)
		if(Math.abs(commonObj.getDeposit())>0.0001)
			immediateCountFeeInfo.getAcctItemList().add(encapsulateDeposit(commonObj.getDeposit(),csiDto.getAccountID()));

		double countFee=countFee(immediateCountFeeInfo.getAcctItemList());
		double countPay=countPayment(immediateCountFeeInfo.getPaymentRecordList());
		if(countFee>countPay)
			immediateCountFeeInfo.setTotalValueAccountItem(BusinessUtility.doubleFormat(countFee-countPay));
			//immediateCountFeeInfo.setTotalValueAccountItem((double)(Math.floor(countFee*100-countPay*100+0.01)/100));
		//�ѹ��õķ��ö���ŵ����ü�����
		feeCol.add(immediateCountFeeInfo);

		return feeCol;
	}
	/**
	 * ���㿪��ʱ������һ���Է���
	 * @param csiDto ��������
	 * @param packageCol   ����Ĳ�Ʒ������
	 * @param campaignCol  ������ײͻ��ߴ����ļ���
	 * @param commonObj ���÷��ò�������
	 * @return
	 * @throws ServiceException
	 */
	public static Collection calculateImmediateFeeForOpen(CustServiceInteractionDTO csiDto,Collection packageCol,Collection campaignCol,CommonFeeAndPayObject commonObj)throws  ServiceException{
		Collection feeCol=new ArrayList();
		//��װ�ƷѲ�������
		Collection billingObjectCol=PublicService.encapsulateBillingObject(commonObj, packageCol, campaignCol);
		LogUtility.log(clazz,LogLevel.DEBUG,"��billingObjectCol��"+billingObjectCol);
		//���ù��ýӿڼ���һ���Է���
		ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
		LogUtility.log(clazz,LogLevel.DEBUG,"��ImmediateFeeList��"+currentFeeList);
		//�Ѽ����һ���Է���ת��Ϊ���õļƷѶ���(������԰����Ź�ʱ�Ѿ�֧����֧���б�)
		ImmediateCountFeeInfo immediateCountFeeInfo= new ImmediateCountFeeInfo();
		immediateCountFeeInfo.setAccountName(commonObj.getAccountName());
		immediateCountFeeInfo.setAccountid(csiDto.getAccountID());
		immediateCountFeeInfo.setAcctItemList(currentFeeList.getAcctItemList());
		//�����Ź���ʱ����Ź���ȡ���Ѿ�֧����֧���б�
		immediateCountFeeInfo.setGropBargainPayedList(currentFeeList.getPaymentList());
		//������Ѻ��ϲ�������֮��
		//if(commonObj.getDeposit()!=0.0f)
		if(Math.abs(commonObj.getDeposit())>0.0001)
			immediateCountFeeInfo.getAcctItemList().add(encapsulateDeposit(commonObj.getDeposit(),csiDto.getAccountID()));
		immediateCountFeeInfo.setPaymentRecordList(currentFeeList.getPaymentList());
		double countFee=countFee(currentFeeList.getAcctItemList());
		double countPay=countPayment(currentFeeList.getPaymentList());
		if(countFee>countPay)
			immediateCountFeeInfo.setTotalValueAccountItem(BusinessUtility.doubleFormat(countFee-countPay));
			//immediateCountFeeInfo.setTotalValueAccountItem((double)(Math.floor(countFee*100-countPay*100+0.01)/100));
		//�ѹ��õķ��ö���ŵ����ü�����
		feeCol.add(immediateCountFeeInfo);
		return feeCol;
	}
	
	/**
	 * �ֹ�������ʱ�Ʒ�
	 * @param jobCardDTO
	 * @return
	 * @throws ServiceException
	 */
	public static Collection calculateImmediateFeeForJobCard(JobCardDTO jobCardDTO,String customizeValue)throws  ServiceException{
		Collection feeCol=new ArrayList();
		AccountDTO accountDTO=BusinessUtility.getAcctDTOByAcctID(jobCardDTO.getAccountID());
		//�Ѽ����һ���Է���ת��Ϊ���õļƷѶ���(������԰����Ź�ʱ�Ѿ�֧����֧���б�)
		ImmediateCountFeeInfo immediateCountFeeInfo= new ImmediateCountFeeInfo();
		immediateCountFeeInfo.setAccountName(accountDTO.getAccountName());
		immediateCountFeeInfo.setAccountid(jobCardDTO.getAccountID());
		if(customizeValue!=null && !"".equals(customizeValue)){
			AccountItemDTO acctItemDTO=new AccountItemDTO();
			acctItemDTO.setAcctID(jobCardDTO.getAccountID());
			acctItemDTO.setAcctItemTypeID(BusinessUtility.getAcctItemTypeIDyConfig("SET_W_CUSTOMIZEFEECONFIGURATION",jobCardDTO.getSubType(),customizeValue));
			//����feeType
			acctItemDTO.setFeeType(BusinessUtility.getFeeTypeByItemTypeID(acctItemDTO.getAcctItemTypeID()));
			acctItemDTO.setValue(Double.valueOf(customizeValue).floatValue()*jobCardDTO.getAddPortNumber());
			acctItemDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			acctItemDTO.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_NO);
			Collection acctItemList=new ArrayList();
			acctItemList.add(acctItemDTO);
			immediateCountFeeInfo.setAcctItemList(acctItemList);
		}
		//�ѹ��õķ��ö���ŵ����ü�����
		feeCol.add(immediateCountFeeInfo);
		return  feeCol;
	}
	/**
	 * ��װ��Թ����ķ��ü���
	 * @param jobCardDto
	 * @param accountItemList
	 * @param operatorID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection  reencapsulateFeeInfoForJobCard(JobCardDTO jobCardDto,Collection accountItemList,int operatorID)throws  ServiceException{
		Collection feeInfoList=new ArrayList();
		if(accountItemList!=null && !accountItemList.isEmpty()){
			Iterator accountItemIter=accountItemList.iterator();
			while(accountItemIter.hasNext()){
				AccountItemDTO accountItemDTO=(AccountItemDTO)accountItemIter.next();
				feeInfoList.add(reencapsulateFeeInfoForJobCard(jobCardDto,accountItemDTO,operatorID));
			}
		}
		return feeInfoList;
	}
	/**
	 * ��װ��Թ����ĵ�������
	 * @param jobCardDto
	 * @param accountItemDTO
	 * @param operatorID
	 * @return
	 * @throws ServiceException
	 */
	public static AccountItemDTO  reencapsulateFeeInfoForJobCard(JobCardDTO jobCardDto,AccountItemDTO accountItemDTO,int operatorID)throws  ServiceException{
		int customerID=jobCardDto.getCustId();
		int accountID=jobCardDto.getAccountID();
		if(accountItemDTO.getAcctID()==0)
			accountItemDTO.setAcctID(accountID);
		accountItemDTO.setCustID(customerID);
		//������Ҫ���õ��ǶԹ����ļƷѣ�����Ŀǰû�����ã���ʱ��˴���
		if(accountItemDTO.getCreatingMethod()==null ||  "".equals(accountItemDTO.getCreatingMethod().trim())){
			accountItemDTO.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_B);
		}
		accountItemDTO.setReferType(CommonConstDefinition.FTREFERTYPE_J);
		accountItemDTO.setReferID(jobCardDto.getJobCardId());
		accountItemDTO.setInvoiceFlag(CommonConstDefinition.FTINVOICEDFLAG_NO);
		accountItemDTO.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_W);
		accountItemDTO.setOperatorID(operatorID);
		accountItemDTO.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
		accountItemDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
		accountItemDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
		if(accountItemDTO.getBillingCycleID()==0){
			accountItemDTO.setBillingCycleID(BusinessUtility.getBillingCycleIDByTypeID());
		}
		accountItemDTO.setFeeType(BusinessUtility.getFeeTypeByItemTypeID(accountItemDTO.getAcctItemTypeID()));
		return accountItemDTO;
	}
	/**
	 * ��װ��Թ���֧������
	 * @param jobCardDto
	 * @param paymentList
	 * @param operatorID
	 * @return
	 */
	public static Collection  reencapsulatePaymentInfoForJobcard(JobCardDTO jobCardDto,Collection paymentList,int operatorID){
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����reencapsulatePaymentInfo  jobCardDto����"+jobCardDto);
		Collection payList=new ArrayList();
		if(paymentList!=null && !paymentList.isEmpty()){
			Iterator paymentIter=paymentList.iterator();
			while(paymentIter.hasNext()){
				PaymentRecordDTO paymentRecordDTO=(PaymentRecordDTO)paymentIter.next();
				payList.add(reencapsulatePaymentInfoForJobcard(jobCardDto, paymentRecordDTO,operatorID));
			}
		}
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����reencapsulatePaymentInfo  payList����"+payList);
		return payList;
	}
	/**
	 * ��װ��Թ����ĵ���֧��
	 * @param jobCardDto
	 * @param paymentRecordDTO
	 * @param operatorID
	 * @return
	 */
	public static PaymentRecordDTO  reencapsulatePaymentInfoForJobcard(JobCardDTO jobCardDto,PaymentRecordDTO paymentRecordDTO,int operatorID){
		int customerID=jobCardDto.getCustId();
		int accountID=jobCardDto.getAccountID();
		if(paymentRecordDTO.getAcctID()==0)
			paymentRecordDTO.setAcctID(accountID);
		paymentRecordDTO.setCustID(customerID);
		paymentRecordDTO.setPaidTo(jobCardDto.getJobCardId());
		paymentRecordDTO.setSourceType(CommonConstDefinition.PAYMENTRECORDSOURCETYPE_JOBCARD);
		paymentRecordDTO.setSourceRecordID(jobCardDto.getJobCardId());
		paymentRecordDTO.setReferType(CommonConstDefinition.FTREFERTYPE_J);
		paymentRecordDTO.setReferID(jobCardDto.getJobCardId());
		paymentRecordDTO.setOpID(operatorID);
		paymentRecordDTO.setPaymentTime(TimestampUtility.getCurrentTimestamp());
		paymentRecordDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
		paymentRecordDTO.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
		paymentRecordDTO.setInvoicedFlag(CommonConstDefinition.FTINVOICEDFLAG_NO);
		paymentRecordDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
		return paymentRecordDTO;
	}
	/**
	 *��װ���ֹ���������ʱ��Ԥ��ֿ۵ķ�װ
	 * @param csiDto
	 * @param prePaymentList
	 * @param operatorID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection reecapsulatePrePaymentInfoFoJobCard(JobCardDTO jobCardDto,Collection prePaymentList,int operatorID)throws  ServiceException{
		Collection prePaymentCol=new ArrayList();
		if(prePaymentList!=null &&!prePaymentList.isEmpty()){
			Iterator  prePaymentIter=prePaymentList.iterator();
			while(prePaymentIter.hasNext()){
				PrepaymentDeductionRecordDTO prePaymentDRDTO=(PrepaymentDeductionRecordDTO)prePaymentIter.next();
				//if(prePaymentDRDTO.getAmount()==0.0f){
				if(Math.abs(prePaymentDRDTO.getAmount())<0.0001){
					prePaymentIter.remove();
					continue;
				}
				prePaymentDRDTO.setReferRecordType(CommonConstDefinition.F_PDR_REFERRECORDTYPE_J);
				prePaymentDRDTO.setReferRecordId(jobCardDto.getJobCardId());
				prePaymentDRDTO.setOpId(operatorID);
				prePaymentDRDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				prePaymentDRDTO.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
				prePaymentDRDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				prePaymentDRDTO.setAdjustmentFlag(CommonConstDefinition.YESNOFLAG_NO);
				prePaymentDRDTO.setReferSheetType(CommonConstDefinition.FTREFERTYPE_J);
				prePaymentDRDTO.setReferSheetID(jobCardDto.getJobCardId());
				prePaymentDRDTO.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_B);
				prePaymentDRDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
				//�����������Ϊ���������µ�Ҫ��
				prePaymentDRDTO.setComments("");
				prePaymentCol.add(prePaymentDRDTO);
			}
		}
		return prePaymentCol;
	}
	/**
	 * ���ֹ���������ʱ���õ�֧��
	 * @param jobCardDto
	 * @param csiFeeList
	 * @param csiPaymentList
	 * @param preductiionList
	 * @param commonObj
	 * @param operatorID
	 * @throws ServiceException
	 */
	public static void payFeeForJobCard(JobCardDTO jobCardDto,Collection csiFeeList,Collection csiPaymentList,Collection preductiionList,CommonFeeAndPayObject commonObj,int operatorID)throws  ServiceException{
		if(csiFeeList!=null && !csiFeeList.isEmpty()){
    		Iterator feeIter=csiFeeList.iterator();
    		if(feeIter.hasNext()){
    			ImmediateCountFeeInfo immediateCountFeeInfo=(ImmediateCountFeeInfo)feeIter.next();
    			immediateCountFeeInfo.setPrePaymentRecordList(reecapsulatePrePaymentInfoFoJobCard(jobCardDto,preductiionList,operatorID));
    			ImmediatePayFeeService feeService=new ImmediatePayFeeService(reencapsulateFeeInfoForJobCard(jobCardDto,immediateCountFeeInfo.getAcctItemList(),operatorID),FeeService.reencapsulatePaymentInfoForJobcard(jobCardDto,csiPaymentList,operatorID),reecapsulatePrePaymentInfoFoJobCard(jobCardDto,immediateCountFeeInfo.getPrePaymentRecordList(),operatorID),null);
    			// �־û����á�֧����Ԥ��ֿ�
 			    createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList(),immediateCountFeeInfo.getSpCache());
 			   if(commonObj!=null){
  				  boolean mustPay=mustCreateFinancesetOffMap("KHGD",false,feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList());
  				  LogUtility.log(FeeService.class,LogLevel.DEBUG,"��FeeService payFeeOperation mustPay��"+mustPay); 
  				  if(!mustPay)
  					   return;
  				  //���������������÷��Ǹ���֧���Ľ�����ж��Ƿ�֧���ˣ������������ط���MustPay����ǰ����ƴ����
  				  commonObj.setMustPay(true);
  			   }
 			   feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_J);
			   feeService.setId(jobCardDto.getJobCardId());
			   PayFeeUtil.updatePrepaymentList(feeService.getPaymentList());
			   PayFeeUtil.updatePrepaymentDuctionList(feeService.getPrePaymentList());
			   PayFeeUtil.updateAccountItemStatusList(feeService.getFeeList());
			   feeService.payFeeDB(feeService.payFee());
    		}
    	}
	}
	/**
	 * ����ϵͳ���Ѵ��ڵÿͻ���Թ����ҵ��������ʱ�Ʒ�(serviceAccountLit��psIDListֻ��һ��)
	 * @param csiDto ��������
	 * @param serviceAccountLit ������ҵ���ʻ�����
	 * @param psIDList   �����ÿͻ���Ʒid����
	 * @param commonObj   ���÷��ò�������
	 * @return
	 * @throws ServiceException
	 */
	public static Collection calculateImmediateFeeForCustService(CustServiceInteractionDTO csiDto,Collection serviceAccountLit,Collection  psIDList,CommonFeeAndPayObject commonObj)throws  ServiceException{
		Collection feeCol=new ArrayList();
		//��װ�ƷѲ�������
		Collection billingObjectCol=PublicService.encapsulateBillingObjectForCust(commonObj, serviceAccountLit, psIDList);
		LogUtility.log(clazz,LogLevel.DEBUG,"��billingObjectCol��"+billingObjectCol);
		//���ù��ýӿڼ���һ���Է���
		ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
		LogUtility.log(clazz,LogLevel.DEBUG,"��ImmediateFeeList��"+currentFeeList);
		//�Ѽ����һ���Է���ת��Ϊ���õļƷѶ���(������԰����Ź�ʱ�Ѿ�֧����֧���б�)
		ImmediateCountFeeInfo immediateCountFeeInfo= encapsulateFeeInfoAndAccountInfo(csiDto.getAccountID() ,csiDto.getType(),null,null);
		immediateCountFeeInfo.setAccountid(csiDto.getAccountID());
		immediateCountFeeInfo.setAccountName(commonObj.getAccountName());
		immediateCountFeeInfo.setAcctItemList(currentFeeList.getAcctItemList());
		//������Ѻ��ϲ�������֮��
		//if(commonObj.getDeposit()!=0.0f)
		if(Math.abs(commonObj.getDeposit())>0.0001)
			immediateCountFeeInfo.getAcctItemList().add(encapsulateDeposit(commonObj.getDeposit(),csiDto.getAccountID()));
		immediateCountFeeInfo.setPaymentRecordList(currentFeeList.getPaymentList());
		double countFee=countFee(currentFeeList.getAcctItemList());
		double countPay=countPayment(currentFeeList.getPaymentList());
		if(countFee>countPay)
			immediateCountFeeInfo.setTotalValueAccountItem(BusinessUtility.doubleFormat(countFee-countPay));
			//immediateCountFeeInfo.setTotalValueAccountItem((double)(Math.floor(countFee*100-countPay*100+0.01)/100));
		//�ѹ��õķ��ö���ŵ����ü�����
		feeCol.add(immediateCountFeeInfo);
		return feeCol;
	}
    /**
     * ����
     * @param adjustType
     * @param id
     * @param customerID
     * @param accountID
     * @param adjustFeeList
     * @param adjustPaymentList
     * @param operatorID
     * @throws ServiceException
     */
    public static Collection  adjustFeeAndPayment(String adjustType,int id, 
    		int customerID,int accountID,Collection adjustFeeList ,
    		Collection adjustPaymentList,Collection adjustDeductionist,
    		int operatorID ) throws  ServiceException{
    	Collection feeCol=new ArrayList();
    	Collection paymentCol=new ArrayList();
    	Collection deductionCol=new ArrayList();
    	Collection logCol =new ArrayList();
    	
    	//������õ���
    	if(adjustFeeList!=null && !adjustFeeList.isEmpty()){
			Iterator feeIter=adjustFeeList.iterator();
			while(feeIter.hasNext()){
				FeeAdjustmentWrapDTO currentFeeWrap=(FeeAdjustmentWrapDTO)feeIter.next();
				AccountItemDTO accountItemDTO=encapsulateAdjustFeeInfo(adjustType,id,customerID,accountID,currentFeeWrap.getAccountItemDTO(),operatorID);
				AccountItem accountItem = createAccountItem(accountItemDTO);
				LogUtility.log(clazz,LogLevel.DEBUG,"������adjustCSIFeeAndPayment accountItemDTO������" + accountItemDTO);
				
				//���ʼ�¼
				AccountAdjustmentDTO accountAdjustmentDTO=currentFeeWrap.getAccountAdjustmentDTO();
				accountAdjustmentDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				accountAdjustmentDTO.setReferRecordType(CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_F);
				accountAdjustmentDTO.setReferRecordID(accountItemDTO.getAiNO());
				accountAdjustmentDTO.setAdjustmentType(adjustType);
				
				LogUtility.log(clazz,LogLevel.DEBUG,"������adjustCSIFeeAndPayment feeAdjustmentDTO������" + accountAdjustmentDTO);
				//����
				AccountAdjustment accountAdjustMent = createAccountAdjustMent(accountAdjustmentDTO,operatorID);
				accountItem.setSourceRecordID(accountAdjustMent.getSeqNo().intValue());
				accountItemDTO.setSourceRecordID(accountAdjustMent.getSeqNo().intValue());
				//������,Ϊ��д��־����
				Object logArray[]=new Object[]{accountAdjustMent,accountItemDTO};
				logCol.add(logArray);
				feeCol.add(accountItemDTO);
			}
		}

		//����֧������
		if(adjustPaymentList!=null && !adjustPaymentList.isEmpty()){
			Iterator paymentIter=adjustPaymentList.iterator();
			while(paymentIter.hasNext()){
				PaymentAdjustWrapDTO currentpaymentWrap=(PaymentAdjustWrapDTO)paymentIter.next();
				PaymentRecordDTO paymentRecordDTO=encapsulateAdjustPaymentInfo(adjustType,id,customerID,accountID,currentpaymentWrap.getPaymentRecordDTO(),operatorID);
				PaymentRecord  paymentRecod =createPaymentRecord(paymentRecordDTO);
				//�������ɵ�֧��id
				LogUtility.log(clazz,LogLevel.DEBUG,"������adjustCSIFeeAndPayment paymentRecordDTO������" + paymentRecordDTO);
				//���ʼ�¼
				AccountAdjustmentDTO accountAdjustmentDTO=currentpaymentWrap.getAccountAdjustmentDTO();
				accountAdjustmentDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				accountAdjustmentDTO.setReferRecordType(CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_P);
				accountAdjustmentDTO.setReferRecordID(paymentRecordDTO.getSeqNo());
				accountAdjustmentDTO.setAdjustmentType(adjustType);
				
				LogUtility.log(clazz,LogLevel.DEBUG,"������adjustCSIFeeAndPayment  payAdjustmentDTO������" + accountAdjustmentDTO);
				//����
				AccountAdjustment accountAdjustMent = createAccountAdjustMent(accountAdjustmentDTO,operatorID);
				paymentRecod.setSourceRecordId(accountAdjustMent.getSeqNo().intValue());
				paymentRecordDTO.setSourceRecordID(accountAdjustMent.getSeqNo().intValue());
				
				Object logArray[]=new Object[]{accountAdjustMent,paymentRecordDTO};
				logCol.add(logArray);
				paymentCol.add(paymentRecordDTO);
			}
		}
		//����Ԥ��ֿ۵���
		if(adjustDeductionist!=null && !adjustDeductionist.isEmpty()){
			Iterator deductionIter=adjustDeductionist.iterator();
			while(deductionIter.hasNext()){
				PreDeductionAdjustWrapDTO deductionWrap=(PreDeductionAdjustWrapDTO)deductionIter.next();
				PrepaymentDeductionRecordDTO preDeductionDTO=encapsulateAdjustDeduction(adjustType,id,customerID,accountID,deductionWrap.getPreDeductionDTO(),operatorID);
				PrepaymentDeductionRecord deuctionRecord =  createPrePaymentDeductionRecord(preDeductionDTO);
				
				AccountAdjustmentDTO accountAdjustmentDTO=deductionWrap.getAccountAdjustmentDTO();
				accountAdjustmentDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				accountAdjustmentDTO.setReferRecordType(CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_D);
				accountAdjustmentDTO.setReferRecordID(preDeductionDTO.getSeqNo());
				accountAdjustmentDTO.setAdjustmentType(adjustType);
				
				LogUtility.log(clazz,LogLevel.DEBUG,"������adjustCSIFeeAndPayment  payAdjustmentDTO������" + accountAdjustmentDTO);
				//����
				AccountAdjustment accountAdjustMent = createAccountAdjustMent(accountAdjustmentDTO,operatorID);
				deuctionRecord.setReferRecordId(accountAdjustMent.getSeqNo().intValue());
				preDeductionDTO.setReferRecordId(accountAdjustMent.getSeqNo().intValue());
				
				Object logArray[]=new Object[]{accountAdjustMent,preDeductionDTO};
				logCol.add(logArray);
				deductionCol.add(preDeductionDTO);
			}
		}
		
		Collection cloneFeeList =new ArrayList();
		Collection clonePayList =new ArrayList();
		Collection clonePrepayList =new ArrayList();
		if (feeCol!=null && !feeCol.isEmpty()){
			Iterator feeIter=feeCol.iterator();
			while (feeIter.hasNext()){
				AccountItemDTO currentFee =(AccountItemDTO)feeIter.next();
				AccountItemDTO cloneFeeDto =PublicService.getCloneAccountItemDTO(currentFee);
				cloneFeeList.add(cloneFeeDto);
			}	
		}
		if (paymentCol!=null && !paymentCol.isEmpty()){
			Iterator payIter =paymentCol.iterator();
			while (payIter.hasNext()){
				PaymentRecordDTO currentPay =(PaymentRecordDTO)payIter.next();
				PaymentRecordDTO clonePayDto =PublicService.getClonePaymentDTO(currentPay);
				clonePayList.add(clonePayDto);
			}
		}
		if (deductionCol!=null && !deductionCol.isEmpty()){
			Iterator prepayIter =deductionCol.iterator();
			while(prepayIter.hasNext()){
				PrepaymentDeductionRecordDTO currentPrepay =(PrepaymentDeductionRecordDTO)prepayIter.next();
				PrepaymentDeductionRecordDTO clonePrepayDto =PublicService.getClonePrepaymentDeductionRecordDTO(currentPrepay);
				clonePrepayList.add(clonePrepayDto);
			}
		}
		
		//�����ʻ�Ԥ�����
	    PayFeeUtil.updatePrepaymentList(paymentCol);
	    PayFeeUtil.updatePrepaymentDuctionList(deductionCol);
		if(cloneFeeList!=null && !cloneFeeList.isEmpty() && ((clonePayList!=null && !clonePayList.isEmpty())||(clonePrepayList!=null && !clonePrepayList.isEmpty()))){
			//�������˹�ϵ
		    ImmediatePayFeeService feeService=new ImmediatePayFeeService(cloneFeeList,clonePayList,clonePrepayList,null);
		    //�־û����á�֧����Ԥ��ֿ�
		    createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList(),null);
		    if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_S.equals(adjustType)){
		    	feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
				feeService.setId(id);
			}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_M.equals(adjustType)){
				feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
				feeService.setId(id);
			}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_I.equals(adjustType)){
				feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_I);
				feeService.setId(id);
			}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_C.equals(adjustType)){
				feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_M);
				feeService.setId(id);
			}
		    feeService.payFeeDB(feeService.payFee());
		} else if (cloneFeeList!=null && !cloneFeeList.isEmpty() && adjustType.equals(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_I)){
			cloneFeeList.addAll(BusinessUtility.getFeeListByInvoice(id));
				
			double feeValue =0.0f;
			Iterator feeIter =cloneFeeList.iterator();
			while (feeIter.hasNext()){
				feeValue =BusinessUtility.doubleFormat(feeValue +((AccountItemDTO)feeIter.next()).getValue());
				//feeValue =(double)(Math.floor(feeValue*100 +((AccountItemDTO)feeIter.next()).getValue()*100 +0.01))/100;
			}
			//if (feeValue ==0){
			if (Math.abs(feeValue)<0.0001){	
			   ImmediatePayFeeService feeService=new ImmediatePayFeeService(cloneFeeList,new ArrayList(),null,null);
			   // �־û����á�֧����Ԥ��ֿ�
			   createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList(),null);
			   if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_S.equals(adjustType)){
			    	feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
					feeService.setId(id);
				}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_M.equals(adjustType)){
					feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
					feeService.setId(id);
				}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_I.equals(adjustType)){
					feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_I);
					feeService.setId(id);
				}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_C.equals(adjustType)){
					feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_M);
					feeService.setId(id);
				}
			   feeService.payFeeDB(feeService.payFee());
			}
		}
	    
	    return logCol;
	    
    }
    /**
     * ���ݵ��ʵĽ�������ʵ����˻���Ϣ
     * @param actionType
     * @param invoiceNO
     * @param customerID
     * @param accountID
     * @param operatorID
     * @throws ServiceException
     */
    public static void changInvoiceAccordAdjust(int actionType,int invoiceNO,String adjustMethod,Collection adjustFeeList,Collection paymentAdjustList,Collection preDeductionAdjustList,int customerID,int accountID,int operatorID)throws  ServiceException{
    	try{
    		InvoiceHome invoiceHome=HomeLocater.getInvoiceHome();
		    Invoice invoice=invoiceHome.findByPrimaryKey(new Integer(invoiceNO));
		    double feeTotal=0.0f;
		    if(adjustFeeList!=null && !adjustFeeList.isEmpty()){
				Iterator feeIter=adjustFeeList.iterator();
				while(feeIter.hasNext()){
					FeeAdjustmentWrapDTO currentFeeWrap=(FeeAdjustmentWrapDTO)feeIter.next();
					feeTotal = BusinessUtility.doubleFormat(feeTotal+currentFeeWrap.getAccountItemDTO().getValue());
					//feeTotal=(double)(Math.floor(feeTotal*100+currentFeeWrap.getAccountItemDTO().getValue()*100+0.01)/100);
				}
			}
		    //�ܽ��
			double totalPay=0;
			//Ԥ���ܶ�
			double preTotalPay=0;
			if(paymentAdjustList!=null && !paymentAdjustList.isEmpty()){
				Iterator paymentIter=paymentAdjustList.iterator();
				while(paymentIter.hasNext()){
					PaymentAdjustWrapDTO currentpaymentWrap=(PaymentAdjustWrapDTO)paymentIter.next();
					PaymentRecordDTO paymentRecordDTO=currentpaymentWrap.getPaymentRecordDTO();
					if(isInitiativePrePay(paymentRecordDTO)){
						preTotalPay = BusinessUtility.doubleFormat(preTotalPay+paymentRecordDTO.getAmount());
						//preTotalPay=(double)(Math.floor(preTotalPay*100+paymentRecordDTO.getAmount()*100+0.01)/100);
					}else{
						totalPay=BusinessUtility.doubleFormat(totalPay+paymentRecordDTO.getAmount());
						//totalPay=(double)(Math.floor(totalPay*100+paymentRecordDTO.getAmount()*100+0.01)/100);
					}
				}
			}
			//�ֿ��ܶ�
			double totalDeduction=0.0f;
			//���Ԥ��ֿ۵���
			if(preDeductionAdjustList!=null && !preDeductionAdjustList.isEmpty()){
				Iterator deductionIter=preDeductionAdjustList.iterator();
				while(deductionIter.hasNext()){
					PreDeductionAdjustWrapDTO curDeductionAdjustWrap=(PreDeductionAdjustWrapDTO)deductionIter.next();
					PrepaymentDeductionRecordDTO preDeductionDTO=curDeductionAdjustWrap.getPreDeductionDTO();
					totalDeduction = BusinessUtility.doubleFormat(totalDeduction+preDeductionDTO.getAmount());
					//totalDeduction=(double)(Math.floor(totalDeduction*100+preDeductionDTO.getAmount()*100+0.01)/100);
				}
			}
		    if(CommonConstDefinition.SETOFFFLAG_W.equals(adjustMethod)){
			    invoice.setAmount(BusinessUtility.doubleFormat(invoice.getAmount()+feeTotal));
			    invoice.setFeeTotal(BusinessUtility.doubleFormat(invoice.getFeeTotal()+feeTotal));
			    //invoice.setAmount((double)(Math.floor(feeTotal*100+invoice.getAmount()*100+0.01)/100));
			    //invoice.setFeeTotal((double)(Math.floor(feeTotal*100+invoice.getFeeTotal()*100+0.01)/100));
			    //�ʵ���Ӧ��������0��ʱ���޸��ʵ���״̬���˻�״̬����Ʒ��״̬
			    //if(invoice.getAmount()==0){
			    if(Math.abs(invoice.getAmount())<0.0001){
				    invoice.setPayDate(TimestampUtility.getCurrentDateWithoutTime());
				    invoice.setPayOpId(operatorID);
				    invoice.setPayOrgId(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
				    invoice.setStatus(CommonConstDefinition.INVOICE_STATUS_PAID);
				    //�����ǰ���˵�������һ���˵��Ļ����޸�ҵ���˻���״̬����Ʒ��״̬���˻���״̬
			    	if(!BusinessUtility.existNopaidInvoice(accountID)){
					    LogUtility.log(FeeService.class,LogLevel.ERROR,"��ʼ�����ʻ�,�ͻ���Ʒ,ҵ���ʻ�״̬,����������¼�.");
					    AccountDTO accountDTO=new AccountDTO();
			    		accountDTO.setAccountID(accountID);
			    		AccountService.updateAccountInfo(accountDTO,actionType);
						Collection psIDCol=BusinessUtility.getPsIDListByCond(0,accountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_SYSTEMSUSPEND);
				    	//����ҵ���˻��Ͳ�Ʒ��״̬
				    	CustomerProductService.updateCustomerProduct(psIDCol,actionType,operatorID);
			    	}
			    }
		    }else if(CommonConstDefinition.SETOFFFLAG_D.equals(adjustMethod)){
		    	invoice.setAmount(BusinessUtility.doubleFormat(feeTotal+invoice.getAmount()));
			    invoice.setFeeTotal(BusinessUtility.doubleFormat(invoice.getFeeTotal()+feeTotal));
			    invoice.setPaymentTotal(BusinessUtility.doubleFormat(invoice.getPaymentTotal()-totalDeduction+totalPay));
		    	invoice.setPrepaymentDeductionTotal(BusinessUtility.doubleFormat(invoice.getPrepaymentDeductionTotal()+totalDeduction));
		    	invoice.setPrepaymentTotal(BusinessUtility.doubleFormat(preTotalPay+invoice.getPrepaymentTotal()));
		    	
			    //invoice.setAmount((double)(Math.floor(feeTotal*100+invoice.getAmount()*100+0.01)/100));
		    	//invoice.setFeeTotal((double)(Math.floor(feeTotal*100+invoice.getFeeTotal()*100+0.01)/100));
			    //invoice.setPaymentTotal((double)(Math.floor(totalPay*100-totalDeduction*100+invoice.getPaymentTotal()*100+0.01)/100));
		    	//invoice.setPrepaymentDeductionTotal((double)(Math.floor(totalDeduction*100+invoice.getPrepaymentDeductionTotal()*100+0.01)/100));
		    	//invoice.setPrepaymentTotal((double)(Math.floor(preTotalPay*100+invoice.getPrepaymentTotal()*100+0.01)/100));
		    	
		    }
		    
    	}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("�˵�����ʱ��λ����");	            		
		}catch(FinderException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("�˵�����ʱ���Ҵ���");
		}
    	
    }
    /**
     * ֧���˵�
     * @param csiDto
     * @param accountDTO
     * @param invoiceDTO
     * @param actionType
     * @param csiPaymentList
     * @param operatorID
     * @throws ServiceException
     */
    public static Invoice payInvoice(CustServiceInteractionDTO csiDto,AccountDTO accountDTO,InvoiceDTO invoiceDTO,int actionType,Collection csifeeList,Collection csiPaymentList,Collection csiPreDeductionList,int operatorID)throws  ServiceException{
    	try{

    		//�ӷ����б�ȡ�ö�Ӧ�ķ���
	    	Collection feeList=BusinessUtility.getAllFeeListByInvoice(invoiceDTO.getInvoiceNo());
	    	//�ϲ���װ���з���
	    	feeList.addAll(FeeService.reencapsulateFeeInfo(csiDto,csifeeList,operatorID,invoiceDTO.getInvoiceNo()));
	    	//�����ʵ�ȡ�����е�֧��
	    	Collection paymentList=BusinessUtility.getAllPaymentListByInvoice(invoiceDTO.getInvoiceNo());
	    	
	    	FeeService.reencapsulatePaymentInfo(csiDto,csiPaymentList,operatorID);
	    	if(csiPaymentList!=null && !csiPaymentList.isEmpty()){
	    		Iterator csiPayIter=csiPaymentList.iterator();
	    		while(csiPayIter.hasNext()){
	    			PaymentRecordDTO paymentRecordDTO =(PaymentRecordDTO)csiPayIter.next();
	    			paymentRecordDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_YES);
	    			paymentRecordDTO.setInvoiceNo(invoiceDTO.getInvoiceNo());
	    		}
	    	}
	    	//�ϲ����е�֧��
	    	paymentList.addAll(csiPaymentList);
	    	//�����ʵ�ȡ�����е�Ԥ��ֿۼ�¼
	    	Collection prePaymentList=BusinessUtility.getAllPrePaymentListByInvoice(invoiceDTO.getInvoiceNo());
	    	
	    	Collection newPrePaymentList=FeeService.reecapsulatePrePaymentInfo(csiDto, csiPreDeductionList,invoiceDTO, operatorID);
	    	prePaymentList.addAll(newPrePaymentList);
	    	//�޸Ŀͻ���Ʒ��״̬
		    ImmediatePayFeeService feeService=new ImmediatePayFeeService(feeList,paymentList,prePaymentList,null);
			// �־û����á�֧����Ԥ��ֿ�
			createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList(),null);
			feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_I);
			feeService.setId(invoiceDTO.getInvoiceNo());
			/*******************yangchen modify start 2008/08/25**************************************/
			//�������е�����ʻ����ĸ��¶�����Ե�ǰ֧������Ԥ��ֿ��������ģ������������������
			//PayFeeUtil.updatePrepaymentList(feeService.getPaymentList());
			//PayFeeUtil.updatePrepaymentDuctionList(prePaymentList);
			PayFeeUtil.updatePrepaymentList(csiPaymentList);
			PayFeeUtil.updatePrepaymentDuctionList(newPrePaymentList);
			/*******************yangchen modify end 2008/08/25**************************************/
			feeService.payFeeDB(feeService.payFee());
		    
		    InvoiceHome invoiceHome=HomeLocater.getInvoiceHome();
		    Invoice invoice=invoiceHome.findByPrimaryKey(new Integer(invoiceDTO.getInvoiceNo()));
		    invoice.setPayAmount(invoiceDTO.getPayAmount());
		    invoice.setPayDate(TimestampUtility.getCurrentDateWithoutTime());
		    invoice.setPayOpId(operatorID);
		    invoice.setPayOrgId(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
		    invoice.setPunishment(invoiceDTO.getPunishment());
		    invoice.setPrepaymentTotal(invoiceDTO.getPrepaymentTotal());
		    invoice.setPaymentTotal(BusinessUtility.doubleFormat(invoice.getPaymentTotal()+invoiceDTO.getPayAmount()));
		    //invoice.setPaymentTotal((double)(Math.floor(invoice.getPaymentTotal()*100+invoiceDTO.getPayAmount()*100+0.01)/100));
		    invoice.setStatus(CommonConstDefinition.INVOICE_STATUS_PAID);
		    //�����ǰ���˵�������һ���˵��Ļ����޸�ҵ���˻���״̬����Ʒ��״̬���˻���״̬
	    	if(!BusinessUtility.existNopaidInvoice(accountDTO.getAccountID())){
			    LogUtility.log(FeeService.class,LogLevel.ERROR,"��ʼ�����ʻ�,�ͻ���Ʒ,ҵ���ʻ�״̬,����������¼�.");
	    		Collection psIDCol=BusinessUtility.getPsIDListByCond(0,accountDTO.getAccountID(),CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_SYSTEMSUSPEND);
	    		/*******************yangchen add start 2008/10/8**************************************/
	    		//�����Ʒ�ָ���ʱ��Ԥ�Ʒѷ����ķ���
	    		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
	    		BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    		Collection infoFeeList=FeeService.calculateInfoFee(psIDCol,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL,batchNoDTO);
	    		//�־û���Ʒ�ָ�ʱԤ�Ʒѷ����ķ���
	    		createFeeAndPaymentAndPreDuciton(infoFeeList,null,null,commonObj.getSpCache());
	    		/*******************yangchen add end 2008/10/8**************************************/
	    		//�����ʻ���״̬
	    		AccountService.updateAccountInfo(accountDTO,actionType);
	    		//����ҵ���˻��Ͳ�Ʒ��״̬
		    	CustomerProductService.updateCustomerProduct(psIDCol,actionType,operatorID);
	    	}
	    	return invoice;
    	}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("֧���˵�ʱ��λ����");	            		
		}catch(FinderException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("֧���˵�ʱ���Ҵ���");
		}
    }
    /**
     * ��װ���ɹ��˷�
     * @param csiDto
     * @param operatorID
     * @throws ServiceException
     */
    public static void returnFeeForFailureInInstallation(CustServiceInteractionDTO csiDto,int operatorID) throws  ServiceException{

    	Collection csiFeeList=BusinessUtility.getFeeListByTypeAndID(csiDto.getId(),CommonConstDefinition.FTREFERTYPE_M,true);
    	if(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY.equals(csiDto.getPaymentStatus())){
    		//�־û����á�֧����Ԥ��ֿ�
            createFeeAndPaymentAndPreDuciton(FeeService.reencapsulateReturnFeeInfo(csiDto,csiFeeList,operatorID),null,null,null);
    	}else{
    	   //�����Ź�ȯ�Ļ���
 		   if(csiDto.getGroupCampaignID()>0)
			   PublicService.cancelOpenAccountRealationGroupBargain(csiDto);
    		AccountDTO accountDTO=BusinessUtility.getAcctDTOByAcctID(csiDto.getAccountID());
    		Collection conditionPayList=BusinessUtility.getPaymentListByTypeAndID(csiDto.getId(),CommonConstDefinition.PAYMENTRECORDSOURCETYPE_ACCEPT,false);
    		Collection retrnPaymentList=	BusinessUtility.getPaymentListByTypeAndID(csiDto.getId(),CommonConstDefinition.PAYMENTRECORDSOURCETYPE_ACCEPT,true);
    		
    		double cashBalance=accountDTO.getCashDeposit();
    		double tokenBalance=accountDTO.getTokenDeposit();
    		if(countPayment(conditionPayList,true)>cashBalance|| countPayment(conditionPayList,false)>tokenBalance)
    			throw new ServiceException("�������Ԥ���ʹ��,���ܽ����˷�");
	    	Collection csiPreDeductionList=BusinessUtility.getPreDeductionRecordListByTypeAndID(csiDto.getId(), CommonConstDefinition.FTREFERTYPE_M, true); 	
	    	//����FeeService.reencapsulateReturnFeeInfo�����ñ�Ҫ��Ϣ
	    	//����FeeService.reencapsulateReturnPaymentInfo���֧����Ҫ��Ϣ
	    	//ʵ����ImmediatePayFeeService feeService��
	    	//����createFeeAndPaymentAndPreDucitonʵ�������ã�֧����Ԥ��ֿ�
	    	ImmediatePayFeeService feeService=new ImmediatePayFeeService(FeeService.reencapsulateReturnFeeInfo(csiDto,csiFeeList,operatorID),FeeService.reencapsulateReturnPaymentInfo(csiDto,retrnPaymentList,operatorID),csiPreDeductionList,null);
	        //�־û����á�֧����Ԥ��ֿ�
	    	createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList(),null);
			//��֧���������˹�ϵ
	    	feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
	    	feeService.setId(csiDto.getId());
	    	PayFeeUtil.updatePrepaymentList(feeService.getPaymentList());
	    	PayFeeUtil.updatePrepaymentDuctionList(feeService.getPrePaymentList());
	    	feeService.payFeeDB(feeService.payFee());
    	}
    }
    /**
     * �˻�Ԥ��
     * @param csiDto
     * @param csiPaymentList
     * @param operatorID
     * @throws ServiceException
     */
    public static Account prePayment(CustServiceInteractionDTO csiDto,Collection csiPaymentList,int operatorID)throws  ServiceException{
    	try{
    		PaymentRecordHome paymentRecordHome=HomeLocater.getPaymentRecordHome();
    		AccountHome accountHome=HomeLocater.getAccountHome();
    		double cashBalance=0.0f;
    		double tokenBalance=0.0f;
    		
    		if(csiPaymentList!=null && !csiPaymentList.isEmpty()){
    			Iterator paymentIter=csiPaymentList.iterator();
    			while(paymentIter.hasNext()){
    				PaymentRecordDTO paymentRecordDTO=(PaymentRecordDTO)paymentIter.next();
					if(BusinessUtility.isCash(paymentRecordDTO.getMopID())){
						cashBalance = BusinessUtility.doubleFormat(cashBalance+paymentRecordDTO.getAmount());
						//cashBalance=(double)(Math.floor(cashBalance*100+paymentRecordDTO.getAmount()*100+0.01)/100);
					}else{
						tokenBalance = BusinessUtility.doubleFormat(tokenBalance+paymentRecordDTO.getAmount());
						//tokenBalance=(double)(Math.floor(tokenBalance*100+paymentRecordDTO.getAmount()*100+0.01)/100);
					}
					paymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE);
					paymentRecordDTO.setPaidTo(csiDto.getId());
					paymentRecordDTO.setOpID(operatorID);
					paymentRecordDTO.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
					paymentRecordDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
					paymentRecordDTO.setPaymentTime(TimestampUtility.getCurrentTimestamp());
					paymentRecordDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
					paymentRecordDTO.setInvoicedFlag(CommonConstDefinition.FTINVOICEDFLAG_NO);
					paymentRecordDTO.setSourceType(CommonConstDefinition.PAYMENTRECORDSOURCETYPE_ACCEPT);
					paymentRecordDTO.setSourceRecordID(csiDto.getId());
					paymentRecordDTO.setReferType(CommonConstDefinition.FTREFERTYPE_M);
					paymentRecordDTO.setReferID(csiDto.getId());
					paymentRecordDTO.setFaPiaoNo(operatorID+"--"+csiDto.getId());
					paymentRecordHome.create(paymentRecordDTO);
    			}
    		}
    		//�����������Ҫ��������״̬����Ϊ�Ѹ�
    		csiDto.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
    		Account account=accountHome.findByPrimaryKey(new Integer(csiDto.getAccountID()));
    		account.setCashDeposit(BusinessUtility.doubleFormat(account.getCashDeposit()+cashBalance));
    		account.setTokenDeposit(BusinessUtility.doubleFormat(account.getTokenDeposit()+tokenBalance));
    		//account.setCashDeposit((double)(Math.floor(cashBalance*100+account.getCashDeposit()*100+0.01)/100));
    		//account.setTokenDeposit((double)(Math.floor(tokenBalance*100+account.getTokenDeposit()*100+0.01)/100));
    		return account;
	    }catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("Ԥ��ʱ��λ����");	            		
		}catch(FinderException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("Ԥ��ʱ���Ҵ���");
		}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("Ԥ��ʱ������¼����");
		}
    }
    /**
     * �������ʻ����ý��������
     * @param csiDto
     * @param csiFeeList
     * @param csiPaymentList
     * @param preductiionList
     * @param commonObj
     * @param operatorID
     * @throws ServiceException
     */
    public static void dealAccountFeeAndPayment(CustServiceInteractionDTO csiDto,Collection csiFeeList,Collection csiPaymentList,Collection preductiionList,CommonFeeAndPayObject commonObj,int operatorID)throws  ServiceException{
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"����payFeeOperation  dealAccountFeeAndPayment����"+csiFeeList);
    	if(csiFeeList!=null & !csiFeeList.isEmpty()){
    		if(csiFeeList.size()>1)
    			throw new ServiceException("Ŀǰֻ�ܴ����ʻ�.");
    		ImmediateCountFeeInfo immediateCountFeeInfo=(ImmediateCountFeeInfo)csiFeeList.iterator().next();
    		immediateCountFeeInfo.setNewPaymentRecordList(csiPaymentList);
    		immediateCountFeeInfo.setNewPrepayRecordList(preductiionList);
    		//getReturnDeposit(immediateCountFeeInfo);
    	}	
       LogUtility.log(FeeService.class,LogLevel.DEBUG,"����payFeeOperation  dealAccountFeeAndPayment����"+csiFeeList);
    }
    /**
     * ����ͻ�����/�ͻ��˻�/���ʻ�
     * @param csiDto
     * @param csiFeeList
     */
    public static void payFeeOperationForReturnFee(CustServiceInteractionDTO csiDto,Collection csiFeeList,Collection csiPaymentList,Collection preductiionList,CommonFeeAndPayObject commonObj,int operatorID)throws  ServiceException{
    	if(csiFeeList!=null & !csiFeeList.isEmpty()){
    		if(csiFeeList.size()>1)
    			throw new ServiceException("Ŀǰֻ�ܴ����ʻ�.");
    		ImmediateCountFeeInfo immediateCountFeeInfo=(ImmediateCountFeeInfo)csiFeeList.iterator().next();
    		if(csiPaymentList!=null && !csiPaymentList.isEmpty())
    			immediateCountFeeInfo.addNewPaymentRecord(csiPaymentList);
    		if(preductiionList!=null && !preductiionList.isEmpty())
    			immediateCountFeeInfo.addNewPrePaymentRecord(preductiionList);
    		immediateCountFeeInfo.setAcctItemList(FeeService.reencapsulateFeeInfo(csiDto,immediateCountFeeInfo.getAcctItemList(),operatorID));
    		immediateCountFeeInfo.setNewAccountItem(FeeService.reencapsulateFeeInfo(csiDto,immediateCountFeeInfo.getNewAccountItem(),operatorID));
    		immediateCountFeeInfo.setPaymentRecordList(FeeService.reencapsulatePaymentInfo(csiDto,immediateCountFeeInfo.getPaymentRecordList(),operatorID));
    		immediateCountFeeInfo.setNewPaymentRecordList(FeeService.reencapsulatePaymentInfo(csiDto,immediateCountFeeInfo.getNewPaymentRecordList(),operatorID));
    		immediateCountFeeInfo.setPrePaymentRecordList(reecapsulatePrePaymentInfo(csiDto,immediateCountFeeInfo.getPrePaymentRecordList(),null,operatorID));
    		immediateCountFeeInfo.setNewPrepayRecordList(reecapsulatePrePaymentInfo(csiDto,immediateCountFeeInfo.getNewPrepayRecordList(),null,operatorID));
    		//�������֧����
    		PayFee4Account(operatorID,BusinessUtility.getOpOrgIDByOperatorID(operatorID),csiFeeList,true);
    	}
    }
    /**
     * ����/�˻�/���˻�ʱ�ķ���֧��(Ŀǰ��ʹ����)
     * @param csiDto
     * @param csiFeeList
     * @param csiPaymentList
     * @throws ServiceException
     */
    public static void payFeeOperationForReturnFee(CustServiceInteractionDTO csiDto,Collection csiFeeList,Collection csiPaymentList,int operatorID)throws  ServiceException{
    	double feeTotal=0.0f;
    	PaymentRecordDTO payFeeRecord=null;
    	int mopID=1;
    	if(csiPaymentList!=null && !csiPaymentList.isEmpty()){
    		payFeeRecord=(PaymentRecordDTO)((ArrayList)csiPaymentList).get(0);
    		feeTotal=payFeeRecord.getAmount();
    		mopID=payFeeRecord.getMopID();
    	}
    	
    	if(csiFeeList!=null && !csiFeeList.isEmpty()){
    		Iterator feeIter=csiFeeList.iterator();
    		while(feeIter.hasNext()){
    			ImmediateCountFeeInfo immediateCountFeeInfo=(ImmediateCountFeeInfo)feeIter.next();
    			Collection currentAccountRecordList=new ArrayList();
    			LogUtility.log(FeeService.class,LogLevel.DEBUG,"����payFeeOperation  TotalPreReturnFee����"+immediateCountFeeInfo.getTotalPreReturnFee());
    			LogUtility.log(FeeService.class,LogLevel.DEBUG,"����payFeeOperation  TotalValueAccountItem����"+immediateCountFeeInfo.getTotalValueAccountItem());
    			//if(immediateCountFeeInfo.getTotalPreReturnFee()==immediateCountFeeInfo.getTotalValueAccountItem()){
    			if(Math.abs(immediateCountFeeInfo.getTotalPreReturnFee()-immediateCountFeeInfo.getTotalValueAccountItem())<0.0001){
    				PaymentRecordDTO paymentRecordDTO=new PaymentRecordDTO();
	    			paymentRecordDTO.setAcctID(immediateCountFeeInfo.getAccountid());
	    			paymentRecordDTO.setMopID(mopID);
	    			paymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE);
	    			paymentRecordDTO.setAmount(immediateCountFeeInfo.getTotalValueAccountItem());
	    			feeTotal=BusinessUtility.doubleFormat(feeTotal-immediateCountFeeInfo.getTotalValueAccountItem());
	    			//feeTotal=(double)(Math.floor(feeTotal*100-immediateCountFeeInfo.getTotalValueAccountItem()*100+0.01)/100);
	    			currentAccountRecordList.add(paymentRecordDTO);
    			}else{
    				double onceAndInfoFee=BusinessUtility.doubleFormat(immediateCountFeeInfo.getTotalValueAccountItem()-immediateCountFeeInfo.getTotalPreReturnFee());
    				//double onceAndInfoFee=(double)(Math.floor(immediateCountFeeInfo.getTotalValueAccountItem()*100-immediateCountFeeInfo.getTotalPreReturnFee()*100+0.01)/100);
    				//if(immediateCountFeeInfo.getTotalPreReturnFee()!=0){
    				if(Math.abs(immediateCountFeeInfo.getTotalPreReturnFee())>0.0001){
	    				PaymentRecordDTO prePaymentRecordDTO=new PaymentRecordDTO();
	    				prePaymentRecordDTO.setAcctID(immediateCountFeeInfo.getAccountid());
	    				prePaymentRecordDTO.setMopID(mopID);
	    				prePaymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE);
	    				//prePaymentRecordDTO.setAmount(immediateCountFeeInfo.getTotalValueAccountItem());
	    				prePaymentRecordDTO.setAmount(immediateCountFeeInfo.getTotalPreReturnFee());
	    				currentAccountRecordList.add(prePaymentRecordDTO);
	    				LogUtility.log(FeeService.class,LogLevel.DEBUG,"����payFeeOperation  prePaymentRecordDTO����"+prePaymentRecordDTO);
    				}
    				if(onceAndInfoFee>0){
    					PaymentRecordDTO paymentRecordDTO=new PaymentRecordDTO();
    	    			paymentRecordDTO.setAcctID(immediateCountFeeInfo.getAccountid());
    	    			paymentRecordDTO.setMopID(mopID);
    	    			paymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
    	    			paymentRecordDTO.setAmount(onceAndInfoFee);
    	    			currentAccountRecordList.add(paymentRecordDTO);
    	    			LogUtility.log(FeeService.class,LogLevel.DEBUG,"����payFeeOperation  paymentRecordDTO �շѡ���"+paymentRecordDTO);
    				}else if(onceAndInfoFee<0){
    					PaymentRecordDTO paymentRecordDTO=new PaymentRecordDTO();
    	    			paymentRecordDTO.setAcctID(immediateCountFeeInfo.getAccountid());
    	    			paymentRecordDTO.setMopID(mopID);
    	    			paymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_FEE);
    	    			paymentRecordDTO.setAmount(onceAndInfoFee);
    	    			currentAccountRecordList.add(paymentRecordDTO);
    	    			LogUtility.log(FeeService.class,LogLevel.DEBUG,"����payFeeOperation  paymentRecordDTO �˷ѡ���"+paymentRecordDTO);
    				}
    				feeTotal=BusinessUtility.doubleFormat(feeTotal-immediateCountFeeInfo.getTotalValueAccountItem());
    				//feeTotal=(double)(Math.floor(feeTotal*100-immediateCountFeeInfo.getTotalValueAccountItem()*100+0.01)/100);
    				
    			}
    		    ImmediatePayFeeService feeService=new ImmediatePayFeeService(FeeService.reencapsulateFeeInfo(csiDto,immediateCountFeeInfo.getAcctItemList(),operatorID),FeeService.reencapsulatePaymentInfo(csiDto,currentAccountRecordList,operatorID),reecapsulatePrePaymentInfo(csiDto,immediateCountFeeInfo.getPrePaymentRecordList(),null,operatorID),null);
 			    // �־û����á�֧����Ԥ��ֿ�
 			    createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList(),immediateCountFeeInfo.getSpCache());
 			    feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
 				feeService.setId(csiDto.getId());
 				PayFeeUtil.updatePrepaymentList(feeService.getPaymentList());
 				PayFeeUtil.updatePrepaymentDuctionList(feeService.getPrePaymentList());
 				feeService.payFeeDB(feeService.payFee());
 			    
    		}
    	}
    	//if(feeTotal!=0){
    	if(Math.abs(feeTotal)>0.0001){
    		throw new ServiceException("���ú�֧����һ�¡�");
    	}
    }
    /**
     * �������֧��
     * @param csiDto
     * @param csiFeeList  ���ü�¼
     * @param csiPaymentList  ֧����¼
     * @param infoFeeList     ��Ҫ�־û����ǲ���Ҫ֧���Ĳ�������Ϣ�Ѽ�¼
     * @param preductiionList   Ԥ��ֿۼ�¼
     * @param commonObj  ͨ�õĲ�������
     * @param operatorID  ����Աid
     * @throws ServiceException
     */
    public  static void payFeeOperation(CustServiceInteractionDTO csiDto,Collection csiFeeList,Collection csiPaymentList,Collection infoFeeList, Collection preductiionList,CommonFeeAndPayObject commonObj,int operatorID)throws  ServiceException{
    	//�����Ժ�Բ�Ʒ�Ĳ��������漰������Ϣ�ѽ��еֿۺ�֧��������,�����ﵥ��������Ϣ�ѵĴ���
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"������������SpCache����������"+commonObj.getSpCache()); 
    	createFeeAndPaymentAndPreDuciton(infoFeeList,null,null,commonObj.getSpCache());
    	if(csiFeeList!=null && !csiFeeList.isEmpty()){
    		Iterator feeIter=csiFeeList.iterator();
    		while(feeIter.hasNext()){
    			ImmediateCountFeeInfo immediateCountFeeInfo=(ImmediateCountFeeInfo)feeIter.next();
    			//��������������������Ԥ��ֿۼ�¼�Ͷ�Ӧ���ʻ�������ϵ
    			if(immediateCountFeeInfo.getAccountid()==csiDto.getAccountID())
    				immediateCountFeeInfo.setPrePaymentRecordList(reecapsulatePrePaymentInfo(csiDto,preductiionList,null,operatorID));
    			Collection currentAccountRecordList=operationOneAccountFee(immediateCountFeeInfo,csiPaymentList,operatorID);
    			ImmediatePayFeeService feeService=new ImmediatePayFeeService(FeeService.reencapsulateFeeInfo(csiDto,immediateCountFeeInfo.getAcctItemList(),operatorID),FeeService.reencapsulatePaymentInfo(csiDto,currentAccountRecordList,operatorID),reecapsulatePrePaymentInfo(csiDto,immediateCountFeeInfo.getPrePaymentRecordList(),null,operatorID),null);
    			// �־û����á�֧����Ԥ��ֿ�
 			    createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList(),null);
 			   if(commonObj!=null){
 				  boolean mustPay=mustCreateFinancesetOffMap(csiDto.getType(),false,feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList());
 				  LogUtility.log(FeeService.class,LogLevel.DEBUG,"��FeeService payFeeOperation mustPay��"+mustPay); 
 				  if(!mustPay)
 					   return;
 				   else
 					  csiDto.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
 			   }
 			    feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
				feeService.setId(csiDto.getId());
				PayFeeUtil.updatePrepaymentList(feeService.getPaymentList());
				PayFeeUtil.updatePrepaymentDuctionList(feeService.getPrePaymentList());
				feeService.payFeeDB(feeService.payFee());
    		}
    	}
    }
    /**
     * �������֧��(���ڿ���/����/������Ʒ)
     * @param csiDto
     * @param csiFeeList  ���ü�¼
     * @param csiPaymentList  ֧����¼
     * @param preductiionList   Ԥ��ֿۼ�¼
     * @param commonObj  ͨ�õĲ�������
     * @param operatorID  ����Աid
     * @throws ServiceException
     */
    public static void payFeeOperationForOpen(CustServiceInteractionDTO csiDto, Collection csiFeeList, Collection csiPaymentList,
			Collection preductiionList, CommonFeeAndPayObject commonObj, int operatorID) throws ServiceException {
		// �����Ժ�Բ�Ʒ�Ĳ��������漰������Ϣ�ѽ��еֿۺ�֧��������,�����ﵥ��������Ϣ�ѵĴ���
		String payOption = BusinessUtility.getCsiPaymentOption(csiDto.getType());
		if (csiFeeList != null && !csiFeeList.isEmpty()) {
			Iterator feeIter = csiFeeList.iterator();
			while (feeIter.hasNext()) {
				ImmediateCountFeeInfo immediateCountFeeInfo = (ImmediateCountFeeInfo) feeIter.next();
				// ��������������������Ԥ��ֿۼ�¼�Ͷ�Ӧ���ʻ�������ϵ
				if (immediateCountFeeInfo.getAccountid() == csiDto.getAccountID())
					immediateCountFeeInfo.setPrePaymentRecordList(reecapsulatePrePaymentInfo(csiDto, preductiionList, null,
							operatorID));
				ImmediatePayFeeService feeService = new ImmediatePayFeeService(FeeService.reencapsulateFeeInfo(csiDto,
						immediateCountFeeInfo.getAcctItemList(), operatorID), FeeService.reencapsulatePaymentInfo(csiDto,
						csiPaymentList, operatorID), reecapsulatePrePaymentInfo(csiDto, immediateCountFeeInfo
						.getPrePaymentRecordList(), null, operatorID), null);
				// ����������������֧����ʱ��,��Ҫȡ���Ź��е�Ԥ��֧��
				if (commonObj.isMustPay()
						|| CommonConstDefinition.CSI_PAYMENT_OPTION_IM.equals(payOption)
						|| (CommonConstDefinition.CSI_PAYMENT_OPTION_OP.equals(payOption) && feeService.getPaymentList() != null && !feeService
								.getPaymentList().isEmpty())) {
					// ���Ź���Ӧ�����ݷŵ�֧����ȥ
					if (immediateCountFeeInfo.getGropBargainPayedList() != null) {
						Collection groupBargainPayCol = FeeService.reencapsulatePaymentInfo(csiDto, immediateCountFeeInfo
								.getGropBargainPayedList(), operatorID);
						feeService.getPaymentList().addAll(groupBargainPayCol);
					}
				}
				if (commonObj != null) {
					boolean mustPay = mustCreateFinancesetOffMap(csiDto.getType(), commonObj.isMustPay(),
							feeService.getFeeList(), feeService.getPaymentList(), feeService.getPrePaymentList());
					if (!mustPay) {
						// �־û����á�֧����Ԥ��ֿ�
						createFeeAndPaymentAndPreDuciton(feeService.getFeeList(), null, null, null);
						/*
						 * //�˴���Ҫ���ڴ������Ź�ȯԤ�������,���µ׹����ʵ�,�������Ź�ȯ��ʱ��,����ɹ���ʱ��,ֻ���ɼ�¼���ı������ĸ���״̬
						 * if(CommonConstDefinition.CSI_PAYMENT_OPTION_IV.equals(payOption)&&
						 * csiDto.getGroupCampaignID()>0&&commonObj.isMustPay()){
						 * Collection
						 * groupBargainPayCol=FeeService.reencapsulatePaymentInfo(csiDto,getGroupBargainPayList(csiDto.getId()),operatorID);
						 * createFeeAndPaymentAndPreDuciton(null,groupBargainPayCol,null);
						 * PayFeeUtil.updatePrepaymentList(groupBargainPayCol);
						 * LogUtility.log(FeeService.class,LogLevel.DEBUG,"��FeeService
						 * payFeeOperation
						 * groupBargainPayCol��"+groupBargainPayCol); }
						 */
						return;
					} else {
						// �־û����á�֧����Ԥ��ֿ�
						createFeeAndPaymentAndPreDuciton(feeService.getFeeList(), feeService.getPaymentList(), feeService
								.getPrePaymentList(), null);
						csiDto.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
					}
				}
				// �޸��Ź�ȯ��״̬
				if (csiDto.getGroupCampaignID() > 0)
					PublicService.dealOpenAccountRealationGroupBargain(csiDto, false,
							CommonConstDefinition.GROUPBARGAINDETAILSTATUS_RETURN);
				// ������õ�֧��������
				feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
				feeService.setId(csiDto.getId());
				PayFeeUtil.updatePrepaymentList(feeService.getPaymentList());
				PayFeeUtil.updatePrepaymentDuctionList(feeService.getPrePaymentList());
				feeService.payFeeDB(feeService.payFee());
			}
		}
		// ��������ʵ���ʱ��,��¼�밲װ��Ϣ��ʱ�򴴽��Ź�����Ԥ����֧��
		if (CommonConstDefinition.CSI_PAYMENT_OPTION_IV.equals(payOption) && csiDto.getGroupCampaignID() > 0
				&& commonObj.isMustPay()) {
			Collection groupBargainPayCol = FeeService.reencapsulatePaymentInfo(csiDto, getGroupBargainPayList(csiDto.getId()),
					operatorID);
			createFeeAndPaymentAndPreDuciton(null, groupBargainPayCol, null, null);
			PayFeeUtil.updatePrepaymentList(groupBargainPayCol);
			LogUtility.log(FeeService.class, LogLevel.DEBUG, "��FeeService payFeeOperation groupBargainPayCol��"
					+ groupBargainPayCol);
		}
	}
    /**
     * ���޷���֧��
     * @param csiDto
     * @param csiFeeList
     * @param csiPaymentList
     * @param operatorID
     * @throws ServiceException
     */
    public  static void payFeeOperation(CustomerProblemDTO cpDto,Collection csiFeeList,Collection csiPaymentList,Collection preductiionList,CommonFeeAndPayObject commonObj,int operatorID)throws  ServiceException{
    	if(csiFeeList!=null && !csiFeeList.isEmpty()){
    		Iterator feeIter=csiFeeList.iterator();
    		if(feeIter.hasNext()){
    			ImmediateCountFeeInfo immediateCountFeeInfo=(ImmediateCountFeeInfo)feeIter.next();
    			immediateCountFeeInfo.setPrePaymentRecordList(reecapsulatePrePaymentInfo(cpDto,preductiionList,operatorID));
    			ImmediatePayFeeService feeService=new ImmediatePayFeeService(reencapsulateFeeInfo(cpDto,cpDto.getCustAccountID(),immediateCountFeeInfo.getAcctItemList(),operatorID),FeeService.reencapsulatePaymentInfo(cpDto,cpDto.getCustAccountID(),csiPaymentList,operatorID),reecapsulatePrePaymentInfo(cpDto,immediateCountFeeInfo.getPrePaymentRecordList(),operatorID),null);
    			System.out.println("====================��װ���feelistΪ��"+feeService.getFeeList());
    			// �־û����á�֧����Ԥ��ֿ�
 			    createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList(),immediateCountFeeInfo.getSpCache());
 			   if(commonObj!=null){
  				  boolean mustPay=mustCreateFinancesetOffMap("KHBX",false,feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList());
  				  LogUtility.log(FeeService.class,LogLevel.DEBUG,"��FeeService payFeeOperation mustPay��"+mustPay); 
  				  if(!mustPay)
  					   return;
  			   }
 			    
 			    feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_M);
				feeService.setId(cpDto.getId());
				PayFeeUtil.updatePrepaymentList(feeService.getPaymentList());
				PayFeeUtil.updatePrepaymentDuctionList(feeService.getPrePaymentList());
				PayFeeUtil.updateAccountItemStatusList(feeService.getFeeList());
				feeService.payFeeDB(feeService.payFee());
 			    
    		}
    	}
    }
    /**
     * �Ե����˻���֧���ķ�װ
     * @param csiDto
     * @param immediateCountFeeInfo
     * @param csiPaymentList
     * @param operatorID
     * @return
     * @throws ServiceException
     */
    private static  Collection operationOneAccountFee(ImmediateCountFeeInfo immediateCountFeeInfo,Collection csiPaymentList,int operatorID)throws  ServiceException{
    	double valueAccountItem=immediateCountFeeInfo.getTotalValueAccountItem();
    	//�����һ������������ҳ����ʹ���ڲ��ʻ�֧����Ԥ��ֿ۴ӷ����п۳���
    	double preDecutionValue=countPreDeduction(immediateCountFeeInfo.getPrePaymentRecordList());
    	valueAccountItem=BusinessUtility.doubleFormat(valueAccountItem-preDecutionValue);
    	//valueAccountItem=(double)(Math.floor(valueAccountItem*100-preDecutionValue*100+0.01)/100);
    	//�����֧���Ľ��Ϊ0����ֱ�ӷ���null
    	//if(valueAccountItem==0.0f){
    	if(Math.abs(valueAccountItem)<0.0001){
    		return null;
    	}
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"����operationOneAccountFee  accountID ����"+immediateCountFeeInfo.getAccountid());
    	Collection currentAccountRecordList=new ArrayList();
    	double unPayedValue=valueAccountItem;
    	
    	
    	if(csiPaymentList!=null &&!csiPaymentList.isEmpty()){
    		for(int i=0;i<csiPaymentList.size();){
    			PaymentRecordDTO payFeeRecord=(PaymentRecordDTO)((ArrayList)csiPaymentList).get(i);
    			if(payFeeRecord.getAmount()>unPayedValue){
    				PaymentRecordDTO paymentRecordDTO=new PaymentRecordDTO();
	    			paymentRecordDTO.setAcctID(immediateCountFeeInfo.getAccountid());
	    			paymentRecordDTO.setMopID(payFeeRecord.getMopID());
	    			paymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
	    			paymentRecordDTO.setAmount(unPayedValue);
    				//�޸ķ����б��еĽ��
    				payFeeRecord.setAmount(BusinessUtility.doubleFormat(payFeeRecord.getAmount()-valueAccountItem));
	    			//payFeeRecord.setAmount((double)(Math.floor(payFeeRecord.getAmount()*100-valueAccountItem*100+0.01)/100));
    				currentAccountRecordList.add(paymentRecordDTO);
    				break;
    			//}else if(payFeeRecord.getAmount()==unPayedValue){
    			}else if(Math.abs(payFeeRecord.getAmount()-unPayedValue)<0.0001){
    				PaymentRecordDTO paymentRecordDTO=new PaymentRecordDTO();
	    			paymentRecordDTO.setAcctID(immediateCountFeeInfo.getAccountid());
	    			paymentRecordDTO.setMopID(payFeeRecord.getMopID());
	    			paymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
	    			paymentRecordDTO.setAmount(unPayedValue);
    				//ɾ����֧���н��Ϊ0��֧��
    				((ArrayList)csiPaymentList).remove(i);
    				currentAccountRecordList.add(paymentRecordDTO);
    				break;
    			}else{
    				PaymentRecordDTO paymentRecordDTO=new PaymentRecordDTO();
	    			paymentRecordDTO.setAcctID(immediateCountFeeInfo.getAccountid());
	    			paymentRecordDTO.setMopID(payFeeRecord.getMopID());
	    			paymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
	    			paymentRecordDTO.setAmount(payFeeRecord.getAmount());
	    			unPayedValue=BusinessUtility.doubleFormat(unPayedValue-payFeeRecord.getAmount());
	    			//unPayedValue=(double)(Math.floor(unPayedValue*100-payFeeRecord.getAmount()*100+0.01)/100);
    				//�޸ķ����б��еĽ��
    				((ArrayList)csiPaymentList).remove(i);
    				currentAccountRecordList.add(paymentRecordDTO);
    			}
    		}
    	}
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"����operationOneAccountFee  currentAccountRecordList����"+currentAccountRecordList);
    	return currentAccountRecordList;
    }
    /**
     * ���ſͻ��ӿͻ�����ҵ���˻�/������Ʒʱ�������
     * @param csiDto
     * @param csiPackageArray
     * @param csiCampaignArray
     * @param detailNo
     * @param contractNo
     * @return
     * @throws ServiceException
     */
    public static ImmediateFeeList groupChildCustAddSaAndAddProdcut(CustServiceInteractionDTO csiDto,Collection csiPackageArray,Collection csiCampaignArray,String detailNo, String contractNo)throws  ServiceException{
    	CustomerDTO customerDTO=BusinessUtility.getCustomerDTOByCustomerID(csiDto.getCustomerID());
		AccountDTO accountDTO=BusinessUtility.getAcctDTOByAcctID(csiDto.getAccountID());
		CommonFeeAndPayObject paramObj=PublicService.encapsulateBaseParam(csiDto, customerDTO, accountDTO);
		Collection billingObjectCol=PublicService.encapsulateBillingObject(paramObj, csiPackageArray, csiCampaignArray);
		ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
		return currentFeeList;
    }

    /**
     * �����¿��˻�ʱ����һ���Է���
     * @param csiDto
     * @param custoemrID
     * @param accountDTO
     * @param operatorID
     * @return
     * @throws ServiceException
     */
    public static ImmediateFeeList customerOpImmediateFeeCalculator(CustServiceInteractionDTO csiDto,int  custoemrID,AccountDTO accountDTO,int operatorID) throws  ServiceException{
    	
    	CustomerDTO  custoemrDTO=BusinessUtility.getCustomerDTOByCustomerID(custoemrID);
		CommonFeeAndPayObject paramObj=PublicService.encapsulateBaseParam(csiDto, custoemrDTO, accountDTO);
		Collection billingObjectCol=PublicService.encapsulateBillingObject(paramObj,null,null);
		ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
		
    	return currentFeeList;
    }
    
    /**
     * ��Կͻ������ļƷѺ��˻���Ϣ���õļ���
     * @param csiDto
     * @param serviceAccountID
     * @param operatorID
     * @param deviceFeeList �豸�� ���˻�ʱʹ�ã�
     * @return
     * @throws ServiceException
*liudi start fiexd
     */
    public static Collection customerOpImmediateFeeCalculator(CustServiceInteractionDTO csiDto,int serviceAccountID,int operatorID,Collection deviceFeeList) throws  ServiceException{
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"�� customerOpImmediateFeeCalculator deviceFeeList��"+deviceFeeList);    	
    	Collection feeCol=new ArrayList();
    	//ȡ�ÿͻ���Ϣ
    	CustomerDTO  custoemrDTO=BusinessUtility.getCustomerDTOByCustomerID(csiDto.getCustomerID());
    	//ȡ���˻���Ϣ
    	AccountDTO   accountDTO=BusinessUtility.getAcctDTOByAcctID(csiDto.getAccountID());
    	//ҵ���˻���Ϣ
    	Collection serviceAccountList=new ArrayList();
    	if(deviceFeeList!=null &&!deviceFeeList.isEmpty()){
    		if(((AccountItemDTO)((ArrayList)deviceFeeList).get(0)).getValue()>0){
    			((AccountItemDTO)((ArrayList)deviceFeeList).get(0)).setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_B);
    		}else{
    			((AccountItemDTO)((ArrayList)deviceFeeList).get(0)).setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_R);
    		}
    	}
    	//���˻�
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OC.equals(csiDto.getType())){
			//ȡ���ʻ���Ϣ
			//ȡ���˻���δ���˵ķ��á�
	    	Collection feeList =  BusinessUtility.getValidNoInvoicedFeeList4Account(csiDto.getAccountID());
			//ȡ���˻���δ���˵ĵֿ�
			Collection deductionList = BusinessUtility.getValidNoInvoicedDeduction(csiDto.getAccountID());
			//ȡ���˻���δ���˵�֧��			
	    	Collection paymentList = BusinessUtility.getValidNoInvoicedPayment(csiDto.getAccountID());
			//ȡ���˻���δ���˵�Ԥ���¼
			Collection prePaymentList = BusinessUtility.getValidNoInvoicedPrePaymentRecord(csiDto.getAccountID());
			//һ���Է���
			CommonFeeAndPayObject paramObj=PublicService.encapsulateBaseParam(csiDto, custoemrDTO, accountDTO);
			Collection billingObjectCol=PublicService.encapsulateBillingObjectForCust(paramObj,null,null);
			ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
			//������Ϣ�� ����AccountItemDTO List
			BatchNoDTO iBatchNo = new BatchNoDTO(0);
			Collection infoFeeList = InfoFeeImmediateBilling.callCloseAccount(accountDTO.getAccountID(),"C",operatorID,iBatchNo);	
			//һ���Էѣ���ʷ���ã���Ϣ�ѣ��ֿۼ�¼��֧����¼
			feeCol = encapsulateFee4Account(csiDto,currentFeeList,feeList,infoFeeList,deductionList,paymentList,prePaymentList,operatorID,iBatchNo);
			
		//ҵ���˻�����ͣ/�ָ�/ȡ�����û�������/�˻أ�
		}else{
			//ȡ��ҵ���˻���Ϣ
			serviceAccountList.addAll(BusinessUtility.getSAIDListByCustIDAndSAID(csiDto.getCustomerID(),serviceAccountID));
			
			CommonFeeAndPayObject paramObj=PublicService.encapsulateBaseParam(csiDto, custoemrDTO, accountDTO);
			Collection billingObjectCol=PublicService.encapsulateBillingObjectForCust(paramObj,serviceAccountList,null);
			ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
			//�Ѵ��������豸�Ѽ����ȥ
			if(deviceFeeList!=null && !deviceFeeList.isEmpty()){
				Iterator itDF = deviceFeeList.iterator();
				while(itDF.hasNext())
				{
					currentFeeList.getAcctItemList().add(FeeService.reencapsulateFeeInfo(csiDto,(AccountItemDTO)itDF.next(),operatorID));
				}
			}
			LogUtility.log(FeeService.class,LogLevel.DEBUG,"����customerOpImmediateFeeCalculator currentFeeList����"+currentFeeList.getAcctItemList());

			//�������˻���ʱ��,��Ҫ�ϼ������ʻ��ķ��ü������Ϣ(��Ҫ�ϼƿͻ��ʻ�������δ֧������Ϣ,�����²�����һ���Է��ú��豸��֮��ķ���)
			//���صķ����ǲ����˿ͻ�Ԥ��ֿ۹������Ϣ,���صľ�����Ҫ֧�������˻�����Ϣ
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CC.equals(csiDto.getType())||
			   CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_WC.equals(csiDto.getType())){
				Collection acctCol=BusinessUtility.getAcctDTOListByCustIDAndAcctID(csiDto.getCustomerID(), 0);
				if(acctCol!=null && !acctCol.isEmpty() && acctCol.size()>1)
					throw new ServiceException("�ÿͻ����ڶ���ʽ��˻�,������ȡ��.");
				//ȡ���ʻ���Ϣ
				//ȡ���˻���δ���˵ķ��á�
		    	Collection feeList =  BusinessUtility.getValidNoInvoicedFeeList4Account(csiDto.getAccountID());
				//ȡ���˻���δ���˵ĵֿ�
				Collection deductionList = BusinessUtility.getValidNoInvoicedDeduction(csiDto.getAccountID());
				//ȡ���˻���δ���˵�֧����¼
		    	Collection paymentList = BusinessUtility.getValidNoInvoicedPayment(csiDto.getAccountID());
				
				//ȡ���˻���δ���˵�Ԥ���¼
				Collection prePaymentList = BusinessUtility.getValidNoInvoicedPrePaymentRecord(csiDto.getAccountID());

				//�����������Ϣ��
				BatchNoDTO iBatchNo = new BatchNoDTO(0);
				Collection infoFeeList=InfoFeeImmediateBilling.callCloseCustomer(csiDto.getCustomerID(),csiDto.getAccountID(),CommonConstDefinition.ACCOUNT_STATUS_CLOSE,operatorID,iBatchNo);
				//һ���Էѣ���ʷ���ã���Ϣ�ѣ��ֿۼ�¼��֧����¼
				feeCol = encapsulateFee4Account(csiDto,currentFeeList,feeList,infoFeeList,deductionList,paymentList,prePaymentList,operatorID,iBatchNo);
			}else
				feeCol=encapsulateFeeInfo(csiDto,currentFeeList,operatorID);
			
		}
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����customerOpImmediateFeeCalculator feeCol����"+feeCol);
    	return feeCol;
    }
    /**
     * ��Կͻ���Ʒ�����ļƷѺ��˻���Ϣ����
     * @param csiDto
     * @param psIDCol
     * @param destProductID
     * @param serviceAccountID
     * @param operatorID
     * @return
     * @throws ServiceException
     */
    public static Collection customerProductOpImmediateFeeCalculator(
			CustServiceInteractionDTO csiDto, Collection psIDCol,
			int destProductID, int serviceAccountID, int operatorID)
			throws ServiceException {
		return customerProductOpImmediateFeeCalculator(csiDto, psIDCol, null,
				destProductID, serviceAccountID, operatorID);
	}
    /**
     * ��2007-2-26��д��һ�����صķ���,�ɵķ������ڿͻ���Ʒȡ����ͣ�ȼƷ�,����ֱ���ɼƷѽӿڼ������,��ǰ̨����һ���豸����,��Ҫ�ϲ���ȥ,����һ������deviceFeeList
     * @param csiDto
     * @param psIDCol
     * @param deviceFeeList
     * @param destProductID
     * @param serviceAccountID
     * @param operatorID
     * @return
     * @throws ServiceException
     */
    public static Collection customerProductOpImmediateFeeCalculator(
			CustServiceInteractionDTO csiDto, Collection psIDCol,
			Collection deviceFeeList, int destProductID, int serviceAccountID,
			int operatorID) throws ServiceException {
    	Collection feeCol=new ArrayList();	
    	//ȡ�ÿͻ���Ϣ
    	CustomerDTO  custoemrDTO=BusinessUtility.getCustomerDTOByCustomerID(csiDto.getCustomerID());
    	//ȡ���˻���Ϣ
    	AccountDTO   accountDTO=BusinessUtility.getAcctDTOByAcctID(csiDto.getAccountID());
		CommonFeeAndPayObject paramObj=PublicService.encapsulateBaseParam(csiDto, custoemrDTO, accountDTO);
		paramObj.setDestProductID(destProductID);
		Collection billingObjectCol=PublicService.encapsulateBillingObjectForCust(paramObj,null,psIDCol);
		ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����customerProductOpImmediateFeeCalculator.totalValueMustPay����"+currentFeeList.getTotalValueMustPay());
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����customerProductOpImmediateFeeCalculator.currentFeeList����"+currentFeeList.getAcctItemList());
		
		//�����豸���õ�һ���Է�����ȥ.
		if(deviceFeeList!=null&&!deviceFeeList.isEmpty())
			currentFeeList.getAcctItemList().addAll(deviceFeeList);
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_DS.equals(csiDto.getType())){
			String value=BusinessUtility.getCommonNameByKey("SET_F_DEVICESWAP_FEETOPRODUCT", String.valueOf(destProductID));
			if(value!=null && !"".equals(value)){
				AccountItemDTO acctItemDto = new AccountItemDTO();
				acctItemDto.setValue(Double.valueOf(value).doubleValue());
				acctItemDto.setAcctItemTypeID("D000000");
				acctItemDto.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_NO);
				acctItemDto.setAcctID(csiDto.getAccountID());
				acctItemDto.setCustID(csiDto.getCustomerID());
				acctItemDto.setServiceAccountID(serviceAccountID);
				currentFeeList.getAcctItemList().add(acctItemDto);
			}
		}
		//��������˻��ķ�����Ϣ
		//feeCol=encapsulateFeeInfo(csiDto,currentFeeList,infoFeeList,operatorID);
		feeCol=encapsulateFeeInfo(csiDto,currentFeeList,operatorID);
    	return feeCol;
    } 
	/**
	 * ���������˻��ĺϼƷ�����Ϣ
	 * @param csiDto ������Ϣ
	 * @param immediateFeeList �ƷѺ��һ���Է�
	 * @param infoFeeList   �����ƷѺ����Ϣ��
	 * @return  ImmediateCountFeeInfo���б�
	 * @throws ServiceException
	 */
	public static Collection encapsulateFeeInfo(CustServiceInteractionDTO csiDto,ImmediateFeeList immediateFeeList,Collection infoFeeList,int operatorID)throws  ServiceException{
		Collection feeCol=new ArrayList();
		boolean isIncludeInCurrent=false;
		//һ���Է�
		Collection oneOffFeeList=immediateFeeList.getAcctItemList();
		Hashtable currentTable=new Hashtable();
		//�Ѽ�������Ϣ���ð����˻����з���
		if(infoFeeList!=null && !infoFeeList.isEmpty()){
			Iterator infoFeeIter=infoFeeList.iterator();
			while(infoFeeIter.hasNext()){
				AccountItemDTO accountItemDTO=(AccountItemDTO)infoFeeIter.next();
				//���һ���Է���ָ�����˻��Ƿ��������Ϣ���˻���
				if(accountItemDTO.getAcctID()==csiDto.getAccountID()){
					if(!isIncludeInCurrent){
						isIncludeInCurrent=true;
					}
				}
				if(currentTable.containsKey(new Integer(accountItemDTO.getAcctID()))){
					ArrayList currentList=(ArrayList)currentTable.get(new Integer(accountItemDTO.getAcctID()));
					currentList.add(accountItemDTO);
				}else{
					ArrayList oneArrayList=new ArrayList();
					oneArrayList.add(accountItemDTO);
					currentTable.put(new Integer(accountItemDTO.getAcctID()),oneArrayList);
				}
			}	
		}
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����encapsulateFeeInfo.currentTable����"+currentTable.size());
		//���û���˻�����ѭ����������˻�����ط���
		Iterator accountIter=currentTable.keySet().iterator();
		while(accountIter.hasNext()){
			ImmediateCountFeeInfo immediateCountFeeInfo=null;
			Integer keyAccountID=(Integer)accountIter.next();
			ArrayList OnlyInfoFeeList=(ArrayList)currentTable.get(keyAccountID);
			if(keyAccountID.intValue()==csiDto.getAccountID()){
				immediateCountFeeInfo=encapsulateFeeInfoAndAccountInfo(keyAccountID.intValue() ,csiDto.getType(),reencapsulateFeeInfo(csiDto,oneOffFeeList,operatorID),reencapsulateFeeInfo(csiDto,OnlyInfoFeeList,operatorID));
			}else{
				immediateCountFeeInfo=encapsulateFeeInfoAndAccountInfo(keyAccountID.intValue() ,csiDto.getType(),null,reencapsulateFeeInfo(csiDto,OnlyInfoFeeList,operatorID));
			}
			feeCol.add(immediateCountFeeInfo);
		}
		//ֻ��������/�˻�������²���Ҫ���������ʻ��ķ���
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CC.equals(csiDto.getType())||
		   CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_WC.equals(csiDto.getType())	){
			//��鲢�������е��˻���Ϣ
			Collection allAccountCol=BusinessUtility.getAcctDTOListByCustIDAndAcctID(csiDto.getCustomerID(),0);
			LogUtility.log(FeeService.class,LogLevel.DEBUG,"����encapsulateFeeInfo.allAccountCol����"+allAccountCol.size());
			if(allAccountCol!=null && !allAccountCol.isEmpty()){
				Iterator allAccountIter=allAccountCol.iterator();
				while(allAccountIter.hasNext()){
					AccountDTO accountDTO=(AccountDTO)allAccountIter.next();
					if(currentTable.containsKey(new Integer(accountDTO.getAccountID()))){
						continue;
					}
					if(accountDTO.getAccountID()==csiDto.getAccountID()){
						continue;
					}
					ImmediateCountFeeInfo immediateCountFeeInfo=encapsulateFeeInfoAndAccountInfo(accountDTO.getAccountID(),csiDto.getType(),null,null);
					feeCol.add(immediateCountFeeInfo);
				}
			}
		}
		//һ���Է���ָ�����˻�����������Ϣ���˻���ʱ���Ը��ʻ���������
		if(!isIncludeInCurrent){
			LogUtility.log(FeeService.class,LogLevel.DEBUG,"����encapsulateFeeInfo.isIncludeInCurrent  fee����"+oneOffFeeList.toString());
			ImmediateCountFeeInfo immediateCountFeeInfo=encapsulateFeeInfoAndAccountInfo(csiDto.getAccountID() ,csiDto.getType(),reencapsulateFeeInfo(csiDto,oneOffFeeList,operatorID),null);
			feeCol.add(immediateCountFeeInfo);
		}
		return feeCol;
	}
	
	/**
	 * �����Ѳ�����һ���Է��úϲ�����Ӧ���ʻ���ȥ
	 * @param csiDto
	 * @param immediateFeeList
	 * @param operatorID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection encapsulateFeeInfo(CustServiceInteractionDTO csiDto,ImmediateFeeList immediateFeeList,int operatorID)throws  ServiceException{
		Collection feeCol=new ArrayList();
		ImmediateCountFeeInfo immediateCountFeeInfo=encapsulateFeeInfoAndAccountInfo(csiDto.getAccountID() ,csiDto.getType(),null,null);
		//��һ���Է�һ���Է��úͶ�Ӧ���ʻ�������
		immediateCountFeeInfo.getAcctItemList().addAll(reencapsulateFeeInfo(csiDto,immediateFeeList.getAcctItemList(),operatorID));
		immediateCountFeeInfo.setTotalValueAccountItem(countFee(immediateCountFeeInfo.getAcctItemList()));
		double feeTotal=countFee(immediateCountFeeInfo.getAcctItemList());
		immediateCountFeeInfo.setTotalValueAccountItem(feeTotal);
		immediateCountFeeInfo.setAccountid(csiDto.getAccountID());
		AccountDTO accountDTO =BusinessUtility.getAcctDTOByAcctID(csiDto.getAccountID());
		immediateCountFeeInfo.setAccountName(accountDTO.getAccountName());
		feeCol.add(immediateCountFeeInfo);
		return feeCol;
	}
	/**
	 * ��װ���ü��㼰�˻����ϼ�(����ҵ�������ʱ��)
	 * @param accountid
	 * @param type
	 * @param onceFeeList
	 * @param infoFeeList
	 * @return
	 * @throws ServiceException
	 */
	public static ImmediateCountFeeInfo encapsulateFeeInfoAndAccountInfo(int accountid ,String type,Collection onceFeeList,Collection infoFeeList ) throws  ServiceException{
		ImmediateCountFeeInfo immediateCountFeeInfo=null;
		String operatorType="";
		String returnFee="";
		//����
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CC.equals(type)){
			operatorType=ImmediateCountFeeService.CANCEL_CUSTOMER;
			returnFee=ImmediateCountFeeService.RETURN_CASHACCOUNT;
			
		//�˻�
		} else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_WC.equals(type)){
			operatorType=ImmediateCountFeeService.CANCEL_CUSTOMER;
			returnFee=ImmediateCountFeeService.RETURN_CASHACCOUNT;
		//ȡ���ʻ�
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OC.equals(type)){
			operatorType=ImmediateCountFeeService.CANCEL_ACCOUNT;
			returnFee=ImmediateCountFeeService.RETURN_CASHACCOUNT;
		}
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����reencapsulateFeeInfo  type����"+type);
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����reencapsulateFeeInfo  operatorType����"+operatorType);
		if(onceFeeList!=null &&!onceFeeList.isEmpty()){
			immediateCountFeeInfo=ImmediateCountFeeService.countFee(accountid,operatorType,returnFee,onceFeeList,infoFeeList);
		}else{
			immediateCountFeeInfo=ImmediateCountFeeService.countFee(accountid,operatorType,returnFee,infoFeeList);
		}
		return immediateCountFeeInfo;
	}
	
	/**
	 * ����¼�밲װ/ά�޳ɹ���ʱ��,�������Ѿ������ķ��÷�װ����֧��
	 * @param accountID
	 * @param referType
	 * @param referID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getFeeInfoForRegisterInfo(int accountID,String referType,int referID) throws  ServiceException{
		Collection feeCol=new ArrayList();
		ImmediateCountFeeInfo immediateCountFeeInfo=encapsulateFeeInfoAndAccountInfo(accountID);
		//��һ���Է�һ���Է��úͶ�Ӧ���ʻ�������
		immediateCountFeeInfo.getAcctItemList().addAll(BusinessUtility.getFeeListByReferInfo(referType,referID));
		immediateCountFeeInfo.setTotalValueAccountItem(countFee(immediateCountFeeInfo.getAcctItemList()));
		//�˴���Ҫ�������Ź���ʱ��,��¼�밲װ��Ϣ��ʱ����Ҫ���Ź���֧����ȡ����
		if(CommonConstDefinition.FTREFERTYPE_M.equals(referType)){
			immediateCountFeeInfo.setGropBargainPayedList(getGroupBargainPayList(referID));
		}
		double feeTotal=countFee(immediateCountFeeInfo.getAcctItemList());
		immediateCountFeeInfo.setTotalValueAccountItem(feeTotal);
		immediateCountFeeInfo.setAccountid(accountID);
		AccountDTO accountDTO =BusinessUtility.getAcctDTOByAcctID(accountID);
		immediateCountFeeInfo.setAccountName(accountDTO.getAccountName());
		feeCol.add(immediateCountFeeInfo);
		return feeCol;
		
	}
	
	/**
	 * ����������ȡ�������������Ź�Ԥ����֧����¼
	 * @param csiid
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getGroupBargainPayList(int csiid)throws ServiceException{
		Collection groupBargainPayList=new ArrayList();
		CustServiceInteractionDTO csiDTO=BusinessUtility.getCSIDTOByID(csiid);
		if(csiDTO.getGroupCampaignID()>0){
			CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForExitCust(csiDTO,csiDTO.getCustomerID(),csiDTO.getAccountID());
			//�����Ƿ����Ź���Ϣ�����Ź�id���Ź�ȯ�������ײ�id
			commonObj.setGroupBargainID(BusinessUtility.getGroupBargainDetailByID(csiDTO.getGroupCampaignID()).getGroupBargainID());
			Collection billingObjectCol=PublicService.encapsulateBillingObject(commonObj, null, null);
			LogUtility.log(clazz,LogLevel.DEBUG,"��billingObjectCol��"+billingObjectCol);
			//���ù��ýӿڼ���һ���Է���
			ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDTO,billingObjectCol);
			if(currentFeeList.getPaymentList()!=null) 
				groupBargainPayList.addAll(currentFeeList.getPaymentList());
		}
		
		return groupBargainPayList;
	}
	/**
	 * ��װ���ü��㼰�˻����ϼ�(����������ͱ���)
	 * @param accountid
	 * @return
	 * @throws ServiceException
	 */
	public static ImmediateCountFeeInfo encapsulateFeeInfoAndAccountInfo(int accountid) throws  ServiceException{
		ImmediateCountFeeInfo immediateCountFeeInfo=ImmediateCountFeeService.countFee(accountid,"","",null,null);
		return immediateCountFeeInfo;
	}
	
	/**
	 *��װԤ��ֿ��б�(�����������)
	 * @param csiDto
	 * @param prePaymentList
	 * @param operatorID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection reecapsulatePrePaymentInfo(CustServiceInteractionDTO csiDto,Collection prePaymentList,InvoiceDTO invoiceDTO,int operatorID)throws  ServiceException{
		Collection prePaymentCol=new ArrayList();
		if(prePaymentList!=null &&!prePaymentList.isEmpty()){
			Iterator  prePaymentIter=prePaymentList.iterator();
			while(prePaymentIter.hasNext()){
				PrepaymentDeductionRecordDTO prePaymentDRDTO=(PrepaymentDeductionRecordDTO)prePaymentIter.next();
				//if(prePaymentDRDTO.getAmount()==0.0f){
				if(Math.abs(prePaymentDRDTO.getAmount())<0.0001){
					prePaymentIter.remove();
					continue;
				}
				prePaymentDRDTO.setReferRecordType(CommonConstDefinition.F_PDR_REFERRECORDTYPE_C);
				prePaymentDRDTO.setReferRecordId(csiDto.getId());
				prePaymentDRDTO.setOpId(operatorID);
				prePaymentDRDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				prePaymentDRDTO.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
				prePaymentDRDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				prePaymentDRDTO.setAdjustmentFlag(CommonConstDefinition.YESNOFLAG_NO);
				prePaymentDRDTO.setReferSheetType(CommonConstDefinition.FTREFERTYPE_M);
				prePaymentDRDTO.setReferSheetID(csiDto.getId());
				prePaymentDRDTO.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_B);
				if(invoiceDTO!=null){
					prePaymentDRDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_YES);
					prePaymentDRDTO.setInvoiceNo(invoiceDTO.getInvoiceNo());
				}else{
					prePaymentDRDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
				}
				//�����������Ϊ���������µ�Ҫ��
				prePaymentDRDTO.setComments("");
				prePaymentCol.add(prePaymentDRDTO);
			}
		}
		return prePaymentCol;
	}
	/**
	 *��װԤ��ֿ��б�(���ڱ��޷���)
	 * @param csiDto
	 * @param prePaymentList
	 * @param operatorID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection reecapsulatePrePaymentInfo(CustomerProblemDTO cpDto,Collection prePaymentList,int operatorID)throws  ServiceException{
		Collection prePaymentCol=new ArrayList();
		if(prePaymentList!=null &&!prePaymentList.isEmpty()){
			Iterator  prePaymentIter=prePaymentList.iterator();
			while(prePaymentIter.hasNext()){
				PrepaymentDeductionRecordDTO prePaymentDRDTO=(PrepaymentDeductionRecordDTO)prePaymentIter.next();
				//if(prePaymentDRDTO.getAmount()==0.0f){
				if(Math.abs(prePaymentDRDTO.getAmount())<0.0001){
					prePaymentIter.remove();
					continue;
				}
				prePaymentDRDTO.setReferRecordType(CommonConstDefinition.F_PDR_REFERRECORDTYPE_P);
				prePaymentDRDTO.setReferRecordId(cpDto.getId());
				prePaymentDRDTO.setOpId(operatorID);
				prePaymentDRDTO.setInvoicedFlag(CommonConstDefinition.FTACCOUNTEDFLAG_NO);
				prePaymentDRDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				prePaymentDRDTO.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
				prePaymentDRDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				prePaymentDRDTO.setAdjustmentFlag(CommonConstDefinition.YESNOFLAG_NO);
				prePaymentDRDTO.setReferSheetType(CommonConstDefinition.FTREFERTYPE_M);
				prePaymentDRDTO.setReferSheetID(cpDto.getId());
				prePaymentDRDTO.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_B);
				//�����������Ϊ���������µ�Ҫ��
				prePaymentDRDTO.setComments("");
				prePaymentCol.add(prePaymentDRDTO);
			}
		}
		return prePaymentCol;
	}
	/**
	 * ���·�װһ�°�װ���ɹ��˷ѷ�����Ϣ
	 * @param csiDto
	 * @param accountItemList
	 */
	public static Collection  reencapsulateReturnFeeInfo(CustServiceInteractionDTO csiDto,Collection accountItemList,int operatorID)throws  ServiceException{
		Collection feeInfoList=new ArrayList();
		int customerID=csiDto.getCustomerID();
		int accountID=csiDto.getAccountID();
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����reencapsulateFeeInfo  csiDto����"+csiDto);
		if(accountItemList!=null && !accountItemList.isEmpty()){
			Iterator accountItemIter=accountItemList.iterator();
			while(accountItemIter.hasNext()){
				AccountItemDTO accountItemDTO=(AccountItemDTO)accountItemIter.next();
				accountItemDTO.setAcctID(accountID);
				accountItemDTO.setCustID(customerID);
				accountItemDTO.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_R);
				accountItemDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				accountItemDTO.setReferType(CommonConstDefinition.FTREFERTYPE_M);
				accountItemDTO.setReferID(csiDto.getId());
				accountItemDTO.setOperatorID(operatorID);
				accountItemDTO.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_W);
				accountItemDTO.setSetOffAmount(0.0f);
				accountItemDTO.setInvoiceFlag(CommonConstDefinition.FTINVOICEDFLAG_NO);
				accountItemDTO.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
				try {
						CsiCustProductInfoHome csiCustProductInfoHome =HomeLocater.getCsiCustProductInfoHome();
						Collection currentCol=csiCustProductInfoHome.findByCSIIDAndCustProductID(csiDto.getId(),accountItemDTO.getProductID());
						if(currentCol!=null && !currentCol.isEmpty()){
							CsiCustProductInfo csiCustProductInfo=(CsiCustProductInfo)currentCol.iterator().next();
							accountItemDTO.setServiceAccountID(csiCustProductInfo.getReferServiceAccountID());
							accountItemDTO.setPsID(csiCustProductInfo.getCustProductID());
						}
					}catch(HomeFactoryException e) {
						LogUtility.log(clazz, LogLevel.ERROR, e);
						throw new ServiceException("���·�װ������Ϣ��λ����");
					}catch(FinderException e) {
						LogUtility.log(clazz, LogLevel.ERROR, e);
						throw new ServiceException("���·�װ������Ϣ���Ҳ��������Ϣ");
						
					}
				feeInfoList.add(accountItemDTO);
			}
		}
		return feeInfoList;
	}
	
	/**
	 * ���·�װһ�·�����Ϣ��������ã�
	 * @param csiDto
	 * @param accountItemList
	 */
	public static Collection  reencapsulateFeeInfo(CustServiceInteractionDTO csiDto,Collection accountItemList,int operatorID)throws  ServiceException{
		Collection feeInfoList=new ArrayList();
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����reencapsulateFeeInfo  csiDto����"+csiDto);
		if(accountItemList!=null && !accountItemList.isEmpty()){
			Iterator accountItemIter=accountItemList.iterator();
			while(accountItemIter.hasNext()){
				AccountItemDTO accountItemDTO=(AccountItemDTO)accountItemIter.next();
				feeInfoList.add(reencapsulateFeeInfo(csiDto,accountItemDTO,operatorID));
			}
		}
		return feeInfoList;		
	}
	public static Collection  reencapsulateFeeInfo(CustServiceInteractionDTO csiDto,Collection accountItemList,int operatorID,int invoiceNo)throws  ServiceException{
		Collection feeInfoList=new ArrayList();
		if(accountItemList!=null && !accountItemList.isEmpty()){
			Iterator accountItemIter=accountItemList.iterator();
			while(accountItemIter.hasNext()){
				AccountItemDTO accountItemDTO=(AccountItemDTO)accountItemIter.next();
				accountItemDTO.setInvoiceNO(invoiceNo);
				feeInfoList.add(reencapsulateFeeInfo(csiDto,accountItemDTO,operatorID));
			}
		}
		return feeInfoList;
	}
	/**
	 *   ��װ�������õ���Ϣ
	 * @param csiDto
	 * @param accountItemDTO
	 * @param operatorID
	 * @return
	 * @throws ServiceException
	 */
	public static AccountItemDTO reencapsulateFeeInfo(CustServiceInteractionDTO csiDto, AccountItemDTO accountItemDTO,
			int operatorID) throws ServiceException {
		int customerID = csiDto.getCustomerID();
		int accountID = csiDto.getAccountID();
		if (accountItemDTO.getAcctID() == 0)
			accountItemDTO.setAcctID(accountID);

		accountItemDTO.setCustID(customerID);
		if (accountItemDTO.getCreatingMethod() == null || "".equals(accountItemDTO.getCreatingMethod().trim())) {
			accountItemDTO.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_B);
		}
		accountItemDTO.setReferType(CommonConstDefinition.FTREFERTYPE_M);
		accountItemDTO.setReferID(csiDto.getId());
		// ����֧���ʵ������
		if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PI.equals(csiDto.getType()))
			accountItemDTO.setInvoiceFlag(CommonConstDefinition.FTINVOICEDFLAG_YES);
		else
			accountItemDTO.setInvoiceFlag(CommonConstDefinition.FTINVOICEDFLAG_NO);
		accountItemDTO.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_W);
		accountItemDTO.setOperatorID(operatorID);
		accountItemDTO.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
		accountItemDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
		accountItemDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
		if (accountItemDTO.getBillingCycleID() == 0) {
			accountItemDTO.setBillingCycleID(BusinessUtility.getBillingCycleIDByTypeID());
		}

		accountItemDTO.setFeeType(BusinessUtility.getFeeTypeByItemTypeID(accountItemDTO.getAcctItemTypeID()));
		try {
			CsiCustProductInfoHome csiCustProductInfoHome = HomeLocater.getCsiCustProductInfoHome();
			Collection currentCol = csiCustProductInfoHome.findByCSIIDAndCustProductID(csiDto.getId(), accountItemDTO
					.getProductID());
			if (currentCol != null && !currentCol.isEmpty()) {
				CsiCustProductInfo csiCustProductInfo = (CsiCustProductInfo) currentCol.iterator().next();
				accountItemDTO.setServiceAccountID(csiCustProductInfo.getReferServiceAccountID());
				accountItemDTO.setPsID(csiCustProductInfo.getCustProductID());
			}
			
			// �����ظ��������� PSID,������Щ����,���������������һ�δ��������ظ���,Ϊ���չ˴󲿷������ȶ�,����������Ĵ���.
			setPsidToAccountItemDTO(csiDto.getId(), accountItemDTO);
			// ��������Ԥ����ʱ������е�date1��date2
			if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiDto.getType())
					|| CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiDto.getType())
					|| CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO.equals(csiDto.getType())
					|| CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csiDto.getType())
					|| CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(csiDto.getType())
					|| CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN.equals(csiDto.getType())
					|| CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BDS.equals(csiDto.getType())) {
				int campaignid = accountItemDTO.getCcID();
				if (campaignid != 0) {
					CustomerCampaignHome custcampaignHome = HomeLocater.getCustomerCampaignHome();
					Collection custCampCol = custcampaignHome.findByCsiIDAndCampaignID(csiDto.getId(), campaignid);
					if (custCampCol != null && !custCampCol.isEmpty()) {
						CustomerCampaign customerCampaign = (CustomerCampaign) custCampCol.iterator().next();
						accountItemDTO.setDate1(customerCampaign.getDateFrom());
						accountItemDTO.setDate2(customerCampaign.getNbrDate());
						accountItemDTO.setCcID(customerCampaign.getCcID().intValue());
					}
				}else if (csiDto.getPoint() !=0){  //add by david.Yang Э���û�����ʱ��point�ֶβ���Ϊ0.Ϊ�˴�ӡ��Ҫ����date1��date2
					accountItemDTO.setDate1(TimestampUtility.getCurrentDateWithoutTime());
					accountItemDTO.setDate2(TimestampUtility.getTimeEndWithoutTime(accountItemDTO.getDate1(), "M",csiDto.getPoint()));
				}
			}

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���·�װ������Ϣ��λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���·�װ������Ϣ���Ҳ��������Ϣ");

		} catch (Throwable e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("��Ԥ��");

		}

		return accountItemDTO;
	}
	private static int setPsidToAccountItemDTO(int csiid,AccountItemDTO aiDto){
		if(aiDto==null)return 0;
		
		int psid=0,said=0;
		int productId=aiDto.getProductID();
		int groupNo=aiDto.getGroupNo();
		int sheafNo=aiDto.getSheafNo();
		if(groupNo==0&&sheafNo==0){
			return 0;
		}
		try {
			CsiCustProductInfoHome csiCustProductInfoHome = HomeLocater
					.getCsiCustProductInfoHome();
			Collection currentCol = csiCustProductInfoHome.findByCSIIDAndBuyGroup(csiid, groupNo, sheafNo);
			if(currentCol!=null && !currentCol.isEmpty()){
				for(Iterator it=currentCol.iterator();it.hasNext();){
					CsiCustProductInfo csiCustProductInfo=(CsiCustProductInfo)it.next();
					if(psid!=0&&psid!=csiCustProductInfo.getCustProductID()){
						LogUtility.log(clazz, LogLevel.ERROR, "��������ֻһ��CKID: "+psid +"<>"+csiCustProductInfo.getCustProductID());
						break;
					}
					if(productId==csiCustProductInfo.getProductID()){
						psid=csiCustProductInfo.getCustProductID();
						said=csiCustProductInfo.getReferServiceAccountID();
					}
				}
			}
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR, "�ظ�������÷�װ�쳣");
			e.printStackTrace();
		}
		if(psid!=0){
			aiDto.setPsID(psid);
		}
		if(said!=0){
			aiDto.setServiceAccountID(said);
		}
		return psid;
	}
	/**
	 * �����ײ͵�ʱ��,¼�밲װ��Ϣʱ���·��õ�Date1��Date2
	 * @param csiID
	 * @throws ServiceException
	 */
	public static void updateAccountItemDate(int csiID)throws  ServiceException{
		try{
    		AccountItemHome accountItemHome=HomeLocater.getAccountItemHome();
//    		CustomerCampaignHome custcampaignHome= HomeLocater.getCustomerCampaignHome();
    		Collection acctItemList=accountItemHome.findByReferTypeAndReferID(CommonConstDefinition.FTREFERTYPE_M, csiID);
    		if(acctItemList!=null && !acctItemList.isEmpty()){
    			Iterator acctItemIter=acctItemList.iterator();
				Timestamp curDate=TimestampUtility.getCurrentDateWithoutTime();
    			while(acctItemIter.hasNext()){
    				AccountItem accountItem=(AccountItem)acctItemIter.next();
    				if(accountItem.getCcID()!=0){
    					//ȡ�����ڵ���ʼ��ʱ��ʱ�䳤��,����ʱ��ƽ��
    					int length=TimestampUtility.getDaysBetweenTwoDay(curDate,accountItem.getDate1())-1;
//    					CustomerCampaign  customerCampaign=custcampaignHome.findByPrimaryKey(new Integer(accountItem.getCcID()));
    					accountItem.setDate1(curDate);
    					accountItem.setDate2(TimestampUtility.getDateEnd(accountItem.getDate2(),
    							"D",length));
					}
    			}
    		}
		}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"updateAccountItemDate",e);
		    throw new ServiceException("���ò��Ҷ�λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"updateAccountItemDate",e);
		    throw new ServiceException("���ò��Ҵ���");
    	}
		
		
		
	}
	
	
	
	/**
	 * ���·�װһ�·�����Ϣ�����޷��ã�
	 * @param csiDto
	 * @param accountItemList
	 */
	public static Collection  reencapsulateFeeInfo(CustomerProblemDTO cpDto,int accountID,Collection accountItemList,int operatorID)throws  ServiceException{
		Collection feeInfoList=new ArrayList();
		int customerID=cpDto.getCustID();
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����reencapsulateFeeInfo  cpDto����"+cpDto);
		if(accountItemList!=null && !accountItemList.isEmpty()){
			Iterator accountItemIter=accountItemList.iterator();
			while(accountItemIter.hasNext()){
				AccountItemDTO accountItemDTO=(AccountItemDTO)accountItemIter.next();
				accountItemDTO.setAcctID(accountID);
				accountItemDTO.setCustID(customerID);
				accountItemDTO.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_B);
				accountItemDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				accountItemDTO.setReferType(CommonConstDefinition.FTREFERTYPE_P);
				accountItemDTO.setReferID(cpDto.getId());
				accountItemDTO.setOperatorID(operatorID);
				accountItemDTO.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
				accountItemDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				accountItemDTO.setServiceAccountID(cpDto.getCustServiceAccountID());
				accountItemDTO.setInvoiceFlag(CommonConstDefinition.YESNOFLAG_NO);
				accountItemDTO.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_W);
				accountItemDTO.setServiceAccountID(cpDto.getCustServiceAccountID());
				if(accountItemDTO.getBillingCycleID()==0){
					accountItemDTO.setBillingCycleID(BusinessUtility.getBillingCycleIDByTypeID());
				}
				feeInfoList.add(accountItemDTO);
			}
		}
		return feeInfoList;
	}
	/**
	 * ���·�װһ��֧����Ϣ�����޷���֧����
	 * @param csiDto
	 * @param accountItemList
	 */
	public static Collection  reencapsulatePaymentInfo(CustomerProblemDTO cpDto,int accountID,Collection paymentList,int operatorID){
		int customerID=cpDto.getCustID();
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����reencapsulatePaymentInfo  cpDto����"+cpDto);
		Collection payList=new ArrayList();
		if(paymentList!=null && !paymentList.isEmpty()){
			Iterator paymentIter=paymentList.iterator();
			while(paymentIter.hasNext()){				
				PaymentRecordDTO paymentRecordDTO=(PaymentRecordDTO)paymentIter.next();				
				LogUtility.log(FeeService.class,LogLevel.DEBUG,"����reencapsulatePaymentInfo one paymentRecordDTO����"+paymentRecordDTO);
				//if(paymentRecordDTO.getAmount() == 0.0f ) continue;
				if(Math.abs(paymentRecordDTO.getAmount())<0.0001) continue;
				paymentRecordDTO.setAcctID(accountID);
				paymentRecordDTO.setCustID(customerID);
				paymentRecordDTO.setPaidTo(cpDto.getId());
				paymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
				paymentRecordDTO.setSourceType(CommonConstDefinition.PAYMENTRECORDSOURCETYPE_REPAIR);
				paymentRecordDTO.setSourceRecordID(cpDto.getId());
				paymentRecordDTO.setReferType(CommonConstDefinition.FTREFERTYPE_P);
				paymentRecordDTO.setReferID(cpDto.getId());
				paymentRecordDTO.setOpID(operatorID);
				paymentRecordDTO.setPaymentTime(TimestampUtility.getCurrentTimestamp());
				paymentRecordDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				paymentRecordDTO.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
				paymentRecordDTO.setInvoicedFlag(CommonConstDefinition.FTINVOICEDFLAG_NO);
				paymentRecordDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				LogUtility.log(FeeService.class,LogLevel.DEBUG,"����reencapsulatePaymentInfo two  paymentRecordDTO����"+paymentRecordDTO);
				payList.add(paymentRecordDTO);
			}
		}
		return payList;
	}
	/**
	 * ���·�װһ��֧����Ϣ���������֧����
	 * @param csiDto
	 * @param accountItemList
	 */
	public static Collection  reencapsulatePaymentInfo(CustServiceInteractionDTO csiDto,Collection paymentList,int operatorID){
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����reencapsulatePaymentInfo  csiDto����"+csiDto);
		Collection payList=new ArrayList();
		if(paymentList!=null && !paymentList.isEmpty()){
			Iterator paymentIter=paymentList.iterator();
			while(paymentIter.hasNext()){
				PaymentRecordDTO paymentRecordDTO=(PaymentRecordDTO)paymentIter.next();
				payList.add(reencapsulatePaymentInfo(csiDto, paymentRecordDTO,operatorID));
			}
		}
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����reencapsulatePaymentInfo  payList����"+payList);
		return payList;
	}
	/**
	 * @param adjustType
	 * @param id
	 * @param customerID
	 * @param accountID
	 * @param preDeductionDTO
	 * @param operatorID
	 * @return
	 * @throws ServiceException
	 */
	public static PrepaymentDeductionRecordDTO  encapsulateAdjustDeduction(String adjustType,
							int id, int customerID,int accountID,
							PrepaymentDeductionRecordDTO preDeductionDTO,
							int operatorID) throws ServiceException{
		preDeductionDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
		preDeductionDTO.setAcctId(accountID);
		preDeductionDTO.setCustId(customerID);
		preDeductionDTO.setOpId(operatorID);
		preDeductionDTO.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
		preDeductionDTO.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_T);
		preDeductionDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
		preDeductionDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
		
		/*
		if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_S.equals(adjustType)){
			preDeductionDTO.setReferRecordType(CommonConstDefinition.F_PDR_REFERRECORDTYPE_C);
			preDeductionDTO.setReferRecordId(id);
			preDeductionDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
		}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_M.equals(adjustType)){
			preDeductionDTO.setReferRecordType(CommonConstDefinition.F_PDR_REFERRECORDTYPE_F);
			preDeductionDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
		}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_I.equals(adjustType)){
			preDeductionDTO.setReferRecordType(CommonConstDefinition.F_PDR_REFERRECORDTYPE_I);
			preDeductionDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_YES);
			preDeductionDTO.setInvoiceNo(id);
		}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_C.equals(adjustType)){
			preDeductionDTO.setReferRecordType(CommonConstDefinition.F_PDR_REFERRECORDTYPE_P);
			preDeductionDTO.setReferRecordId(id);
			preDeductionDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
		}else{
			throw new ServiceException("�������Ͳ���");
		}
		*/
		return  preDeductionDTO;
	}
	/**
	 * ��װ���ʷ�����Ϣ
	 * @param adjustType
	 * @param id
	 * @param customerID
	 * @param accountID
	 * @param accountItemDTO
	 * @param operatorID
	 * @return
	 * @throws ServiceException
	 */
	public static AccountItemDTO  encapsulateAdjustFeeInfo(String adjustType,int id, int customerID,int accountID,AccountItemDTO accountItemDTO,int operatorID) throws ServiceException{
		accountItemDTO.setAcctID(accountID);
		accountItemDTO.setCustID(customerID);
		accountItemDTO.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_T);
		accountItemDTO.setInvoiceFlag(CommonConstDefinition.YESNOFLAG_NO);
		
		/*
		if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_S.equals(adjustType)){
			accountItemDTO.setReferType(CommonConstDefinition.FTREFERTYPE_M);
			accountItemDTO.setReferID(id);
			accountItemDTO.setInvoiceFlag(CommonConstDefinition.YESNOFLAG_NO);
		}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_M.equals(adjustType)){
			accountItemDTO.setInvoiceFlag(CommonConstDefinition.YESNOFLAG_NO);
		}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_I.equals(adjustType)){
			accountItemDTO.setInvoiceFlag(CommonConstDefinition.YESNOFLAG_YES);
			accountItemDTO.setInvoiceNO(id);
		}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_C.equals(adjustType)){
			accountItemDTO.setReferType(CommonConstDefinition.FTREFERTYPE_P);
			accountItemDTO.setReferID(id);
			accountItemDTO.setInvoiceFlag(CommonConstDefinition.YESNOFLAG_NO);
		}else{
			throw new ServiceException("�������Ͳ���");
		}
		*/
		
		accountItemDTO.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_W);
		accountItemDTO.setOperatorID(operatorID);
		accountItemDTO.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
		if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_C.equals(adjustType)){
			CustomerProblemDTO cpDTO=BusinessUtility.getCustomerProblemDTOByID(id);
			if(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT.equals(cpDTO.getStatus())){
				accountItemDTO.setStatus(CommonConstDefinition.AISTATUS_POTENTIAL);
			}else if(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS.equals(cpDTO.getStatus())){
				accountItemDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			}else{
				throw new ServiceException("��״̬�ı��޵����������");
			}
		}else{
			accountItemDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
		}
		if(accountItemDTO.getBillingCycleID()==0){
			accountItemDTO.setBillingCycleID(BusinessUtility.getBillingCycleIDByTypeID());
		}
		accountItemDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
		accountItemDTO.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_W);
		return accountItemDTO;
	}
	/**
	 * ��װ���ʵ�֧����Ϣ
	 * @param adjustType
	 * @param id
	 * @param customerID
	 * @param accountID
	 * @param paymentRecordDTO
	 * @param operatorID
	 * @return
	 * @throws ServiceException
	 */
	public static PaymentRecordDTO  encapsulateAdjustPaymentInfo(String adjustType,int id, int customerID,int accountID,PaymentRecordDTO paymentRecordDTO,int operatorID) throws ServiceException{
		paymentRecordDTO.setAcctID(accountID);
		paymentRecordDTO.setCustID(customerID);
		paymentRecordDTO.setSourceType(CommonConstDefinition.PAYMENTRECORDSOURCETYPE_ADJUST);
		paymentRecordDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
		
		/*
		if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_S.equals(adjustType)){
			paymentRecordDTO.setReferType(CommonConstDefinition.FTREFERTYPE_M);
			paymentRecordDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
		}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_M.equals(adjustType)){
			paymentRecordDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
		}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_I.equals(adjustType)){
			paymentRecordDTO.setReferType(CommonConstDefinition.FTREFERTYPE_A);
			paymentRecordDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_YES);
			paymentRecordDTO.setInvoiceNo(id);
		}else if(CommonConstDefinition.ACCOUNT_ADJUST_TYPE_C.equals(adjustType)){
			paymentRecordDTO.setReferType(CommonConstDefinition.FTREFERTYPE_P);
			paymentRecordDTO.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
		}else{
			throw new ServiceException("�������Ͳ���");
		}
		paymentRecordDTO.setReferID(id);
		*/
		
		paymentRecordDTO.setOpID(operatorID);
		paymentRecordDTO.setPaymentTime(TimestampUtility.getCurrentTimestamp());
		paymentRecordDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
		paymentRecordDTO.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
		paymentRecordDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
		return paymentRecordDTO;
	}
	
	/**
	 * ��Ե�������֧���ķ�װ
	 * @param csiDto
	 * @param paymentRecordDTO
	 * @param operatorID
	 * @return
	 */
	public static PaymentRecordDTO  reencapsulatePaymentInfo(CustServiceInteractionDTO csiDto,PaymentRecordDTO paymentRecordDTO,int operatorID){
		int customerID=csiDto.getCustomerID();
		int accountID=csiDto.getAccountID();
		if(paymentRecordDTO.getAcctID()==0)
			paymentRecordDTO.setAcctID(accountID);
		paymentRecordDTO.setCustID(customerID);
		paymentRecordDTO.setPaidTo(csiDto.getId());
		paymentRecordDTO.setSourceType(CommonConstDefinition.PAYMENTRECORDSOURCETYPE_ACCEPT);
		paymentRecordDTO.setSourceRecordID(csiDto.getId());
		paymentRecordDTO.setReferType(CommonConstDefinition.FTREFERTYPE_M);
		paymentRecordDTO.setReferID(csiDto.getId());
		paymentRecordDTO.setOpID(operatorID);
		paymentRecordDTO.setPaymentTime(TimestampUtility.getCurrentTimestamp());
		paymentRecordDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
		paymentRecordDTO.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PI.equals(csiDto.getType()))
			paymentRecordDTO.setInvoicedFlag(CommonConstDefinition.FTINVOICEDFLAG_YES);
		else
			paymentRecordDTO.setInvoicedFlag(CommonConstDefinition.FTINVOICEDFLAG_NO);
		paymentRecordDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
		return paymentRecordDTO;
	}
	
	
	/**
	 * ���·�װһ�°�װ���ɹ�֧����Ϣ
	 * @param csiDto
	 * @param accountItemList
	 */
	public static Collection  reencapsulateReturnPaymentInfo(CustServiceInteractionDTO csiDto,Collection paymentList,int operatorID){
		int customerID=csiDto.getCustomerID();
		int accountID=csiDto.getAccountID();
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����reencapsulatePaymentInfo  csiDto����"+csiDto);
		Collection payList=new ArrayList();
		if(paymentList!=null && !paymentList.isEmpty()){
			Iterator paymentIter=paymentList.iterator();
			while(paymentIter.hasNext()){
				PaymentRecordDTO paymentRecordDTO=(PaymentRecordDTO)paymentIter.next();
				paymentRecordDTO.setAcctID(accountID);
				paymentRecordDTO.setCustID(customerID);
				paymentRecordDTO.setPaidTo(csiDto.getId());
				if(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE.equals(paymentRecordDTO.getPayType())){
					paymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_FEE);
				}else if(CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentRecordDTO.getPayType())){ 
					paymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE);
				}	
				paymentRecordDTO.setSourceType(CommonConstDefinition.PAYMENTRECORDSOURCETYPE_ACCEPT);
				paymentRecordDTO.setSourceRecordID(csiDto.getId());
				paymentRecordDTO.setReferType(CommonConstDefinition.FTREFERTYPE_M);
				paymentRecordDTO.setReferID(csiDto.getId());
				paymentRecordDTO.setOpID(operatorID);
				paymentRecordDTO.setInvoicedFlag(CommonConstDefinition.FTINVOICEDFLAG_NO);
				paymentRecordDTO.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
				paymentRecordDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				paymentRecordDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				payList.add(paymentRecordDTO);
			}
		}
		return payList;
	}
	public static Collection calculateInfoFee(Collection colPsid,int operatorID,String destStatus,BatchNoDTO  batchNoDTO)throws  ServiceException{
		Collection feeList=calculateInfoFee(colPsid ,operatorID, destStatus,"N",batchNoDTO);
		return feeList;
	}
	/**
	 * �������㵱ǰ�ͻ���Ʒ�б����Ϣ��
	 * @param colPsid �ͻ���Ʒ�б�
	 * @param operatorID ����Աid
	 * @param destStatus Ŀ��״̬
	 * @return AccountItemDTO��list
	 * @throws ServiceException
	 */
	public static Collection calculateInfoFee(Collection colPsid,int operatorID,String destStatus,String commitFlag,BatchNoDTO iBatchNo )throws  ServiceException{
		Collection feeCol=new ArrayList();
		if(colPsid!=null && !colPsid.isEmpty()){
			String currentPsIDs="";
			Iterator currentIter=colPsid.iterator();
			while(currentIter.hasNext()){
				Integer psID=(Integer)currentIter.next();
				if("".equals(currentPsIDs)){
					currentPsIDs=psID.toString();
				}else{
					currentPsIDs=currentPsIDs+","+psID.toString();
				}
			}
			feeCol=InfoFeeImmediateBilling.callImmediateBillingFee(currentPsIDs,destStatus,operatorID,commitFlag,iBatchNo);
		}
		return feeCol;
	}
	/**
	 * �������㵱ǰҵ���ʻ�����Ŀͻ���Ʒ����������Ϣ��
	 * @param said   ҵ���ʻ�id
	 * @param operatorID  ����Աid
	 * @param destStatus Ŀ��״̬
	 * @return
	 * @throws ServiceException
	 */
	public static Collection calculateInfoFeeForServiceAccountClose(int said,int operatorID,String destStatus,BatchNoDTO iBatchNo)throws  ServiceException{
		Collection feeCol=InfoFeeImmediateBilling.callCloseServiceAccount(said,destStatus,operatorID,iBatchNo);
		return feeCol;
	}
	/**
	 * ���ݲ�Ʒ��ID�б��װ���ݣ�ֻ�����豸����ʱ��ʹ�õ�destProductID������ʱ��ֱ�Ӵ�0�Ϳ�����
	 * @param colPsid
	 * @param destProductID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection encapsulateProductInfo(Collection colPsid ,int destProductID)throws  ServiceException{
		Collection productInfoCol=new ArrayList();
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����encapsulateProductInfo  start����");
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"��colPsid��"+colPsid);
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"��destProductID��"+new Integer(destProductID));
		try {
			if(colPsid!=null && !colPsid.isEmpty()){
				CustomerProductHome customerProductHome=HomeLocater.getCustomerProductHome();
				Iterator psIDIter=colPsid.iterator();
				while(psIDIter.hasNext()){
					ProductInfo productInfo=new ProductInfo();
					Integer currentPsID=(Integer)psIDIter.next();
					CustomerProduct customerProduct=customerProductHome.findByPrimaryKey(currentPsID);
					CustomerCampaignDTO customerCampaignDTO=BusinessUtility.getCustomerCampaignByPsID(currentPsID.intValue());
					if(customerCampaignDTO!=null){
						productInfo.setCampaignID(customerCampaignDTO.getCampaignID());
						productInfo.setContractNo(customerCampaignDTO.getContractNo());
						productInfo.setGroupBargainID(customerCampaignDTO.getGroupBargainID());
					}
					productInfo.setDestProductID(destProductID);
					productInfo.setPackageID(customerProduct.getReferPackageID());
					productInfo.setProductID(customerProduct.getProductID());
					productInfoCol.add(productInfo);
					LogUtility.log(FeeService.class,LogLevel.DEBUG,"����ProductInfo����",productInfo);
				}
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("��װ��Ʒ�б�ʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("��װ��Ʒ�б�ʱʱ���Ҵ���");
    	}
		
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"����encapsulateProductInfo  end����");
		return productInfoCol;
	}
	/**
	 * ���㱨���������ķ���
	 * @param cpDto
	 * @param accountID
	 * @param operatorID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection customerProblemOpImmediateFeeCalculator(CustomerProblemDTO cpDto,int accountID,int operatorID) throws  ServiceException{
	    //ȡ�ÿͻ���Ϣ
    	CustomerDTO  custoemrDTO=BusinessUtility.getCustomerDTOByCustomerID(cpDto.getCustID());
    	Collection accountItemDTOList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee4CP(custoemrDTO,cpDto);
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"��accountItemDTOList��"+ accountItemDTOList);
    	Collection feeCol=new ArrayList();
		ImmediateCountFeeInfo immediateCountFeeInfo=encapsulateFeeInfoAndAccountInfo(accountID);
		//��һ���Է�һ���Է��úͶ�Ӧ���ʻ�������
		immediateCountFeeInfo.getAcctItemList().addAll(accountItemDTOList);
		immediateCountFeeInfo.setTotalValueAccountItem(countFee(immediateCountFeeInfo.getAcctItemList()));
		double feeTotal=countFee(immediateCountFeeInfo.getAcctItemList());
		immediateCountFeeInfo.setTotalValueAccountItem(feeTotal);
		immediateCountFeeInfo.setAccountid(accountID);
		AccountDTO accountDTO =BusinessUtility.getAcctDTOByAcctID(accountID);
		immediateCountFeeInfo.setAccountName(accountDTO.getAccountName());
		feeCol.add(immediateCountFeeInfo);
    	return feeCol;
	}
	
	/**
	 * ����������Ϣ
	 * @param customerID
	 * @param custProblemID
	 * @param accountID
	 * @param accountItemDTOList
	 * @param operatorID
	 * @throws ServiceException
	 */
	public static void createProblemOpFee(CustomerProblemDTO cpDto,int accountID,Collection  feeCol,int operatorID)throws  ServiceException{
    	if(feeCol!=null && !feeCol.isEmpty()){
    		Iterator feeIter=feeCol.iterator();
    		while(feeIter.hasNext()){
    			ImmediateCountFeeInfo immediateCountFeeInfo=(ImmediateCountFeeInfo)feeIter.next();
    			Collection accountItemCol=immediateCountFeeInfo.getAcctItemList();
    			//��װ������Ϣ
    	    	reencapsulateFeeInfo(cpDto,accountID,accountItemCol,operatorID);
    	    	createAccountItemList(accountItemCol);
    		}
    	}
	}
	

	/**
	 * �־û����ã�֧����Ԥ��ֿ�
	 * @param accountItemList
	 * @param paymentRecordList
	 * @param prePaymentDeductionRecordList
	 * @throws ServiceException
	 */
	public  static void createFeeAndPaymentAndPreDuciton(Collection accountItemList,Collection paymentRecordList,Collection prePaymentDeductionRecordList,Collection spCache)throws  ServiceException{
	    //�־û�δ�־û��ķ���
	    createAccountItemList(accountItemList);
	    //�־û�δ�־û���֧��
	    createPaymentRecordList(paymentRecordList);
	    //�־û�δ�־û���Ԥ��ֿ�
	    createPrePaymentDeductionRecordList(prePaymentDeductionRecordList);
		//ִ�л����SQL���
		processSpCache(spCache);
	}
	/**
	 * �־û������б�
	 * @param accountItemList
	 * @throws ServiceException
	 */
	private  static void createAccountItemList(Collection accountItemList)throws  ServiceException{
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"��FeeService createAccountItemList��"+accountItemList);
		if(accountItemList!=null && !accountItemList.isEmpty()){
			Iterator  accountItemIter=accountItemList.iterator();
			while(accountItemIter.hasNext()){
				
				AccountItemDTO accountItemDTO=(AccountItemDTO)accountItemIter.next();
				accountItemDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());

				if( accountItemDTO.getValue() == 0.0f ) continue;				

				//if( accountItemDTO.getValue() == 0.0f ) continue;
				if(Math.abs(accountItemDTO.getValue())<0.0001) continue;
				if(accountItemDTO.getAiNO()>0)

					continue;
				else
					
				createAccountItem(accountItemDTO);
				
			}
		}
	}
	/**
	 * @param accountItemList
	 */
	public static void createReversedFee(CustomerProblemDTO cpDto,Collection feeList,Collection csiPaymentList,Collection preductiionList,String referType,int referID,int operatorID)throws ServiceException {
		if(feeList!=null && !feeList.isEmpty()){
			Iterator feeIter=feeList.iterator();
		if(feeIter.hasNext()){
			ImmediateCountFeeInfo immediateCountFeeInfo=(ImmediateCountFeeInfo)feeIter.next();
			immediateCountFeeInfo.setPrePaymentRecordList(reecapsulatePrePaymentInfo(cpDto,preductiionList,operatorID));
			ImmediatePayFeeService feeService=new ImmediatePayFeeService(reencapsulateFeeInfo(cpDto,cpDto.getCustAccountID(),immediateCountFeeInfo.getAcctItemList(),operatorID),FeeService.reencapsulatePaymentInfo(cpDto,cpDto.getCustAccountID(),csiPaymentList,operatorID),reecapsulatePrePaymentInfo(cpDto,immediateCountFeeInfo.getPrePaymentRecordList(),operatorID),null);
		
			Collection accountItemList =feeService.getFeeList();
			createReversedRecord(accountItemList,referType,referID);
		}
		}		
	}
	private static void createReversedRecord(Collection accountItemList,String referType,int referID)throws ServiceException{
		if(accountItemList!=null && !accountItemList.isEmpty()){
			Iterator  accountItemIter=accountItemList.iterator();
				if(accountItemIter.hasNext()){
					AccountItemDTO accountItemDTO=(AccountItemDTO)accountItemIter.next();
					if(accountItemDTO.getAiNO()==0){
						try{
							AccountItemHome accountItemHome=HomeLocater.getAccountItemHome();
							Collection deCol = accountItemHome.findByReferTypeAndReferID(referType,referID);
							for(Iterator itr = deCol.iterator();itr.hasNext();){
								AccountItem accountItem = (AccountItem)itr.next();
								System.out.println("$$$$$$$$$$$$$$$$$$"+accountItem.getAiNO());
								AccountItemDTO accItemDto = BusinessUtility.getAcctItemDTOByAiNO(accountItem.getAiNO().intValue());
								
								accItemDto.setValue(-accountItem.getValue());
								accItemDto.setCreateTime(new Timestamp(System.currentTimeMillis()));
								accItemDto.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_M);
								accountItemHome.create(accItemDto);
							}
						}catch(HomeFactoryException e) {
							LogUtility.log(FeeService.class,LogLevel.ERROR,"deleteFeeAlready",e);
							throw new ServiceException("���ɷ������ʱ��λ����");	            		
						}catch(FinderException e) {
							LogUtility.log(FeeService.class,LogLevel.ERROR,"deleteFeeAlready",e);
							throw new ServiceException("���ҷ���ʱ����");
						}catch(CreateException e){
							LogUtility.log(FeeService.class,LogLevel.ERROR,"deleteFeeAlready",e);
							throw new ServiceException("��������ʱ����");
						}
					}
				}
		}
	}
	public static void createReversedFee(CustServiceInteractionDTO csiDto,Collection feeList,Collection csiPaymentList,Collection preductiionList,String referType,int referID,int operatorID)throws ServiceException{
			if(feeList!=null && !feeList.isEmpty()){
				Iterator feeIter=feeList.iterator();
			if(feeIter.hasNext()){
				ImmediateCountFeeInfo immediateCountFeeInfo=(ImmediateCountFeeInfo)feeIter.next();
				
				ImmediatePayFeeService feeService=new ImmediatePayFeeService(FeeService.reencapsulateFeeInfo(csiDto,immediateCountFeeInfo.getAcctItemList(),operatorID),FeeService.reencapsulatePaymentInfo(csiDto,csiPaymentList,operatorID),reecapsulatePrePaymentInfo(csiDto,immediateCountFeeInfo.getPrePaymentRecordList(),null,operatorID),null);
				
				Collection accountItemList =feeService.getFeeList();
				createReversedRecord(accountItemList,referType,referID);
			}
			}		
	}

	/**
	 *��������
	 * @param accountItemDTO
	 * @return
	 * @throws ServiceException
	 */
	private static AccountItem createAccountItem(AccountItemDTO accountItemDTO)throws  ServiceException{
		try{
System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + accountItemDTO.getSourceRecordID());
System.out.println(accountItemDTO);
    		AccountItemHome accountItemHome=HomeLocater.getAccountItemHome();
//			if(accountItemDTO.getStatus().compareTo(CommonConstDefinition.AISTATUS_VALIDATE2)==0)
	//			updateSourceAccountItem(accountItemDTO.getSourceRecordID());
    		AccountItem accountItem=accountItemHome.create(accountItemDTO);
    		accountItemDTO.setAiNO(accountItem.getAiNO().intValue());
    		return accountItem;
		}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"createAccountItem",e);
		    throw new ServiceException("��������ʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"createAccountItem",e);
		    throw new ServiceException("��������ʱ����");
    	}
	}

	private static void updateSourceAccountItem(int aiNo) throws ServiceException{
		try{
    		AccountItemHome accountItemHome=HomeLocater.getAccountItemHome();
			AccountItem accItem = accountItemHome.findByPrimaryKey(new Integer(aiNo));
			if(accItem.getStatus().compareTo(CommonConstDefinition.AISTATUS_VALIDATE) == 0 ||
			   accItem.getStatus().compareTo(CommonConstDefinition.AISTATUS_VALIDATE1) == 0 )
			{
				accItem.setStatus(CommonConstDefinition.AISTATUS_VALIDATE2);
			}
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"���ķ��ü�¼��Ϣ����ԭ�򣺷��ü�¼��λ����");
			throw new ServiceException("���ķ��ü�¼��Ϣ����ԭ�򣺷��ü�¼��λ����");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"���ķ��ü�¼��Ϣ����ԭ�򣺷��ü�¼���ҳ���");
			throw new ServiceException("���ķ��ü�¼��Ϣ����ԭ�򣺷��ü�¼���ҳ���");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"�������·��ü�¼��Ϣ��");
	}
	
	/**
	 * �־û�֧���б�
	 * @param accountItemList
	 * @throws ServiceException
	 */
	private  static void createPaymentRecordList(Collection paymentRecordList)throws  ServiceException{
		if(paymentRecordList!=null && !paymentRecordList.isEmpty()){
			Iterator  paymentRecordIter=paymentRecordList.iterator();
			while(paymentRecordIter.hasNext()){
				PaymentRecordDTO paymentRecordDTO=(PaymentRecordDTO)paymentRecordIter.next();
				if(paymentRecordDTO.getSeqNo()>0)
					continue;
				paymentRecordDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				createPaymentRecord(paymentRecordDTO);
				
			}
		}
	}	
	
	/**
	 * ����֧��
	 * @param accountItemDTO
	 * @return
	 * @throws ServiceException
	 */
	private static PaymentRecord createPaymentRecord(PaymentRecordDTO paymentRecordDTO)throws  ServiceException{

		try{
			PaymentRecordHome paymentRecordHome=HomeLocater.getPaymentRecordHome();
			PaymentRecord paymentRecord=paymentRecordHome.create(paymentRecordDTO);
			paymentRecordDTO.setSeqNo(paymentRecord.getSeqNo().intValue());
			return paymentRecord;
		}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"createPaymentRecord",e);
		    throw new ServiceException("����֧��ʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"createPaymentRecord",e);
		    throw new ServiceException("����֧��ʱ����");
    	}
	}
	
	/**
	 * �־û�Ԥ��ֿ��б�
	 * @param accountItemList
	 * @throws ServiceException
	 */
	private  static void createPrePaymentDeductionRecordList(Collection prePaymentDeductionRecordList)throws  ServiceException{
		if(prePaymentDeductionRecordList!=null && !prePaymentDeductionRecordList.isEmpty()){
			Iterator  prePaymentDeductionRecordIter=prePaymentDeductionRecordList.iterator();
			while(prePaymentDeductionRecordIter.hasNext()){
				PrepaymentDeductionRecordDTO preRecordDTO=(PrepaymentDeductionRecordDTO)prePaymentDeductionRecordIter.next();
				if(preRecordDTO.getSeqNo()>0)
					continue;
				preRecordDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				createPrePaymentDeductionRecord(preRecordDTO);
				
			}
		}
	}	
	
	/**
	 * ����Ԥ��ֿ�
	 * @param deductionDTO
	 * @throws ServiceException
	 */
	private static PrepaymentDeductionRecord createPrePaymentDeductionRecord(PrepaymentDeductionRecordDTO deductionDTO)throws  ServiceException{

		try{
			PrepaymentDeductionRecordHome home=HomeLocater.getPrepaymentDeductionRecordHome();
			PrepaymentDeductionRecord deduction=home.create(deductionDTO);
			deductionDTO.setSeqNo(deduction.getSeqNo().intValue());
            return deduction;
		}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"createPrePaymentDeductionRecord",e);
		    throw new ServiceException("����Ԥ��ֿ�ʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"createPrePaymentDeductionRecord",e);
		    throw new ServiceException("����Ԥ��ֿ�ʱ����");
    	}

	}
	/**
	 * �־û��ʵ�
	 * @param accountItemList
	 * @throws ServiceException
	 */
	private  static Invoice createInvoice(InvoiceDTO inv)throws  ServiceException{
		try{
			InvoiceHome home=HomeLocater.getInvoiceHome();
			Invoice invoice = home.create(inv);
			inv.setInvoiceNo(invoice.getInvoiceNo().intValue());
            return invoice;
		}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"createInvoice",e);
		    throw new ServiceException("�����ʵ�ʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"createInvoice",e);
		    throw new ServiceException("�����ʵ�ʱ����");
    	}
	}
	/**
	 * ��ĳ���������߱��޵����������ķ��õ�״̬��Ϊ��Ч��Ϊ��Ч
	 * @param referType
	 * @param referID
	 * @throws ServiceException
	 */
	public static void closeFeeInfo(String referType,int referID)throws ServiceException{
		try{
			Collection feeCol=BusinessUtility.getFeeListByTypeAndID(referID,referType,true);
			AccountItemHome accountItemHome=HomeLocater.getAccountItemHome();
			if(feeCol!=null && !feeCol.isEmpty()){
				Iterator accountItemIter=feeCol.iterator();
				while(accountItemIter.hasNext()){
					AccountItemDTO accountItem=(AccountItemDTO)accountItemIter.next();
					accountItem.setCreateTime(TimestampUtility.getCurrentTimestamp());
					accountItem.setComments("����/����ȡ������ʧ��ʱ������ȡ���ķ����¼");
					accountItem.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					accountItemHome.create(accountItem);
				}
			}			
		}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"colseFeeInfo",e);
		    throw new ServiceException("����ȡ��ʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"colseFeeInfo",e);
		    throw new ServiceException("����ȡ��ʱ��������");
    	}
		
	}
	/**
	 * ����һ�����ʼ�¼
	 * @param accountAdjustmentDto
	 * @param operatorID
	 * @throws ServiceException
	 */
	public static AccountAdjustment createAccountAdjustMent(AccountAdjustmentDTO accountAdjustmentDto,int operatorID)throws  ServiceException{
		accountAdjustmentDto.setCreateOpID(operatorID);
		accountAdjustmentDto.setCreateOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
		accountAdjustmentDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
		accountAdjustmentDto.setStatus(CommonConstDefinition.ADJUST_STATUS_VALIDATION);
		accountAdjustmentDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
		accountAdjustmentDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		try{
			AccountAdjustmentHome accountAdjustHome=HomeLocater.getAccountAdjustmentHome();
			LogUtility.log(clazz,LogLevel.DEBUG,"������createAccountAdjustMent������"+accountAdjustmentDto);
			return accountAdjustHome.create(accountAdjustmentDto);
		}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("����ʱ��λ����");	            		
		}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("����ʱ������¼����");
		}
	}
	
	/**
	 * �ж��Ƿ���һ���Է���
	 * @param paymentRecordDTO
	 * @return
	 * @throws ServiceException
	 */
	public static boolean isCsiOnceFee(PaymentRecordDTO paymentRecordDTO)throws  ServiceException{
		boolean isCsiOnceFee=false;
			if(CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentRecordDTO.getPayType())||	
					CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_FEE.equals(paymentRecordDTO.getPayType())||
					CommonConstDefinition.PAYMENTRECORD_TYPE_BILLING.equals(paymentRecordDTO.getPayType())){			
				isCsiOnceFee=false;
			}else if(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE.equals(paymentRecordDTO.getPayType())){
				isCsiOnceFee=true;
			}else{
				throw new   ServiceException("֧������û�ж�Ӧ�Ķ���");
			}
		
		return isCsiOnceFee;
	}
	
	/**
	 * �Ƿ�ǿ��Ԥ��
	 * @param accountItemDTO
	 * @return
	 * @throws ServiceException
	 */
	public static boolean isForceDeposit(AccountItemDTO accountItemDTO) throws  ServiceException{
		boolean isForceDeposit=false;
		if(CommonConstDefinition.YESNOFLAG_YES.equals(accountItemDTO.getForcedDepositFlag())){
			isForceDeposit=true;
		}else if (CommonConstDefinition.YESNOFLAG_NO.equals(accountItemDTO.getForcedDepositFlag())){
			isForceDeposit=false;		
		}else{
			throw new   ServiceException("�������û�ж���");
		}
		return isForceDeposit;
	}
	/**
	 * �Ƿ���Ԥ��֧��
	 * @param accountItemDTO
	 * @return
	 * @throws ServiceException
	 */
	public static boolean isInitiativePrePay(PaymentRecordDTO paymentRecordDTO) throws  ServiceException{
		boolean isInitiativePrePay=false;
		if(CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentRecordDTO.getPayType())||
			CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE.equals(paymentRecordDTO.getPayType())){
			isInitiativePrePay=true;
		}else{
			isInitiativePrePay=false;		
		}
		return isInitiativePrePay;
	}
	/**
	 * ������Ȩ
	 * @param dto
	 * @return
	 * @throws ServiceException
	 */
	public static FutureRight createFutureRight(FutureRightDTO dto) throws ServiceException{
		try{
			FutureRightHome futureRightHome = HomeLocater.getFutureRightHome();			
			FutureRight futureRight =futureRightHome.create(dto);
			return futureRight;
			
		}catch(HomeFactoryException ex) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create",ex);
		    throw new ServiceException("������Ȩʱ��λ����");
		 }catch(CreateException e) {
			    LogUtility.log(clazz, LogLevel.ERROR, "create",e);
			    throw new ServiceException("������Ȩʱʱ����");		    			
		 }	
	}
/**
 * ȡ����Ȩ
 * @param dto
 * @throws ServiceException
 */
	public static FutureRight cancelFutureRight(FutureRightDTO dto) throws ServiceException{
		try{
			FutureRightHome futureRightHome = HomeLocater.getFutureRightHome();
			FutureRight  futureRight =futureRightHome.findByPrimaryKey(new Integer(dto.getSeqNo()));
			futureRight.setCancelDate(TimestampUtility.getCurrentTimestamp());
			futureRight.setCancelOpId(dto.getCancelOpID());
			futureRight.setCancelOrgId(dto.getCancelOrgID());
			futureRight.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			futureRight.setStatus(CommonConstDefinition.FUTURERIGHT_RESULT_C);
			return futureRight;
		}catch(HomeFactoryException ex) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create",ex);
		    throw new ServiceException("ȡ����Ȩʱ��λ����");
		 }catch(FinderException e1) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create", e1);
		    throw new ServiceException("ȡ����Ȩʱʱ����");		    
		}
	}
	/**
	 * ����
	 * @param dto
	 * @throws ServiceException
	 */
	public static FutureRight grantFutureRight(FutureRightDTO dto) throws ServiceException{

		try{
			//������Ȩ
			FutureRightHome futureRightHome = HomeLocater.getFutureRightHome();
			FutureRight  futureRight =futureRightHome.findByPrimaryKey(new Integer(dto.getSeqNo()));
			futureRight.setAccountId(dto.getAccountID());
			futureRight.setExcuteOpId(dto.getExcuteOpID());
			futureRight.setExcuteOrgId(dto.getExcuteOrgID());
			futureRight.setStatus(CommonConstDefinition.FUTURERIGHT_RESULT_X);
			futureRight.setExcuteDate(TimestampUtility.getCurrentTimestamp());
			futureRight.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			dto.setValue(futureRight.getValue());
			
			//��������֧��
			PaymentRecordDTO prDTO = new PaymentRecordDTO();
			prePaymentForFutureRight(prDTO,dto);
			
			return futureRight;
		}catch(HomeFactoryException ex) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create",ex);
		    throw new ServiceException("ȡ����Ȩʱ��λ����");
		 }catch(FinderException e1) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create", e1);
		    throw new ServiceException("ȡ����Ȩʱʱ����");		    
		 }
	}
	
    /**
     * ��������֧��
     * @param csiDto
     * @param csiPaymentList
     * @param operatorID
     * @throws ServiceException
     */
    public static void prePaymentForFutureRight(PaymentRecordDTO paymentRecordDTO,FutureRightDTO dto)throws  ServiceException{
    	try{
    		PaymentRecordHome paymentRecordHome=HomeLocater.getPaymentRecordHome();
    		AccountHome accountHome=HomeLocater.getAccountHome();

			paymentRecordDTO.setMopID(3);   //����
			paymentRecordDTO.setReferType(CommonConstDefinition.FTREFERTYPE_F);
			paymentRecordDTO.setReferID(dto.getSeqNo());
			paymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE);
			paymentRecordDTO.setSourceType(CommonConstDefinition.PAYMENTRECORDSOURCETYPE_FUTURERIGHT);
			paymentRecordDTO.setSourceRecordID(dto.getSeqNo());
			paymentRecordDTO.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY);
			paymentRecordDTO.setPaidTo(0);
			paymentRecordDTO.setOpID(dto.getExcuteOpID());
			paymentRecordDTO.setOrgID(dto.getExcuteOrgID());
			paymentRecordDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
			paymentRecordDTO.setPaymentTime(TimestampUtility.getCurrentTimestamp());
			paymentRecordDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			paymentRecordDTO.setInvoicedFlag(CommonConstDefinition.FTINVOICEDFLAG_NO);
			paymentRecordDTO.setAcctID(dto.getAccountID());
			paymentRecordDTO.setCustID(dto.getCustomerID());
			paymentRecordDTO.setAmount(dto.getValue());
			paymentRecordHome.create(paymentRecordDTO);
			//�����˻�
    		Account account=accountHome.findByPrimaryKey(new Integer(dto.getAccountID()));
    		account.setTokenDeposit(BusinessUtility.doubleFormat(dto.getValue()+account.getTokenDeposit()));
    		//account.setTokenDeposit((double)(Math.floor(dto.getValue()*100+account.getTokenDeposit()*100+0.01)/100));
    		
	    }catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("Ϊ��Ȩ��������֧��ʱ��λ����");	            		
		}catch(FinderException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("Ϊ��Ȩ��������֧��ʱ���Ҵ���");
		}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("Ϊ��Ȩ��������֧��ʱ������¼����");
		}
    }
    
    /**
     * ��������Է����Ƿ�����֧��������
     * @param type
     * @param payFlag
     * @param feeLlist
     * @param paymentList
     * @return
     * @throws ServiceException
     */
    public static boolean mustCreateFinancesetOffMap(String type,boolean mustPay,Collection  feeLlist,Collection   paymentList ,Collection preductiionList)throws ServiceException{
    	String payOption=BusinessUtility.getCsiPaymentOption(type);
    	//����֧��
    	if(CommonConstDefinition.CSI_PAYMENT_OPTION_IM.equals(payOption)){
    		//��������֧��
    		double totoalPay=BusinessUtility.doubleFormat(countPayment(paymentList)+countPreDeduction(preductiionList));
    		//double totoalPay=(double)(Math.floor(countPayment(paymentList)*100+countPreDeduction(preductiionList)*100+0.01)/100);
    		//Ԥ��֧��
    		double totalPrePay=countPrePayment(paymentList);
    		if(feeLlist==null||feeLlist.isEmpty()){
    			//if(isExitPaymentForBusniess(paymentList,preductiionList)&& totoalPay!=0.0f)
    			if(isExitPaymentForBusniess(paymentList,preductiionList)&& Math.abs(totoalPay)>0.0001)
    				throw new ServiceException("û����Ҫ֧���ķ��ü�¼");	
    		}else{
    			if(!isExitPaymentForBusniess(paymentList,preductiionList)&& (countFeeByFeeType(feeLlist,false)!=0))
    				throw new ServiceException("���ñ���֧��");
   			
    			if(countFeeByFeeType(feeLlist,false)!=totoalPay )
    				throw new ServiceException("֧���͵ֿ��ܶ���ڷ����ܶ�");
    			if(countFeeByFeeType(feeLlist,true)>totalPrePay )
    				throw new ServiceException("Ԥ���ܶ�������ǿ��Ԥ������ܶ�");
    		}
    		
    	//��ѡ��
    	}else if(CommonConstDefinition.CSI_PAYMENT_OPTION_OP.equals(payOption)){
    		double totoalPay=BusinessUtility.doubleFormat(countPayment(paymentList)+countPreDeduction(preductiionList));
    		//double totoalPay=(double)(Math.floor(countPayment(paymentList)*100+countPreDeduction(preductiionList)*100+0.01)/100);
    		//Ԥ��֧��
    		double totalPrePay=countPrePayment(paymentList);
    		if(feeLlist==null||feeLlist.isEmpty()){
    			//if(isExitPaymentForBusniess(paymentList,preductiionList)&& totoalPay!=0.0f)
    			if(isExitPaymentForBusniess(paymentList,preductiionList)&& Math.abs(totoalPay)>0.0001)
    				throw new ServiceException("û����Ҫ֧���ķ��ü�¼");	
    		}else{
    			if(!isExitPaymentForBusniess(paymentList,preductiionList))
    				return false;
    			if(countFeeByFeeType(feeLlist,false)!=totoalPay )
    				throw new ServiceException("֧���͵ֿ��ܶ���ڷ����ܶ�");
    			if(countFeeByFeeType(feeLlist,true)>totalPrePay )
    				throw new ServiceException("Ԥ���ܶ�������ǿ��Ԥ������ܶ�");
    		}
    	//���¶��ʵ���֧��
    	}else if(CommonConstDefinition.CSI_PAYMENT_OPTION_IV.equals(payOption)){
    		if(isExitPaymentForBusniess(paymentList,preductiionList))
				throw new ServiceException("�������¶��ʵ���֧��,����Ҫ֧��");
    		return false;
    	//���Ű�װ�ɹ�����ȡ
    	}else if(CommonConstDefinition.CSI_PAYMENT_OPTION_SP.equals(payOption)){
    		double totoalPay=BusinessUtility.doubleFormat(countPayment(paymentList)+countPreDeduction(preductiionList));
    		//double totoalPay=(double)(Math.floor(countPayment(paymentList)*100+countPreDeduction(preductiionList)*100+0.01)/100);
    		//Ԥ��֧��
    		double totalPrePay=countPrePayment(paymentList);
    		if(feeLlist==null||feeLlist.isEmpty()){
    			//if(isExitPaymentForBusniess(paymentList,preductiionList)&& totoalPay!=0.0f)
    			if(isExitPaymentForBusniess(paymentList,preductiionList)&& Math.abs(totoalPay)>0.0001)
    				throw new ServiceException("û����Ҫ֧���ķ��ü�¼");	
    		}else{
    			if(mustPay){
	    			if(!isExitPaymentForBusniess(paymentList,preductiionList)&& (countFeeByFeeType(feeLlist,false)!=0))
	    				throw new ServiceException("���ñ���֧��");
	    			//if(countFeeByFeeType(feeLlist,false)!=totoalPay )
	    			if(Math.abs(countFeeByFeeType(feeLlist,false) - totoalPay) > 0.0001)
	    				throw new ServiceException("֧���͵ֿ��ܶ���ڷ����ܶ�");
	    			if(countFeeByFeeType(feeLlist,true)>totalPrePay )
	    				throw new ServiceException("Ԥ���ܶ�������ǿ��Ԥ������ܶ�");
    			}else{
    				return false;
    			}
    		}
    	}else{
    		throw new ServiceException("ȡ֧�����ô���");
    	}
    	return true;
    }
    /**
     * �����ж��Ƿ����֧��������Ԥ��ֿ�
     * @param paymentList
     * @param preductiionList
     * @return
     */
    private static boolean isExitPaymentForBusniess(Collection   paymentList ,Collection preductiionList){
    	if((paymentList==null||paymentList.isEmpty())&&(preductiionList==null||preductiionList.isEmpty()))
    			return false;
    	return true;
    	
    }
    
    /**
     * �ϼƷ��õ��ܶ�
     * @param feeLlist
     * @return
     */
    public static double countFee(Collection  feeLlist){
    	double totalFee=0.0f;
    	if(feeLlist!=null&& !feeLlist.isEmpty()){
    		Iterator feeIter=feeLlist.iterator();
    		while(feeIter.hasNext()){
    			AccountItemDTO accountItemDTO=(AccountItemDTO)feeIter.next();
    			totalFee=BusinessUtility.doubleFormat(totalFee+accountItemDTO.getValue());
    			//totalFee=(double)(Math.floor(totalFee*100+accountItemDTO.getValue()*100+0.01)/100);
    		}
    	}
    	return totalFee;
    }
    
    /**
     * ���ַ��ú�ǿ��Ԥ��ķ��úϼ��ܶ�
     * @param feeLlist
     * @param isForceDeposit
     * @return
     */
    private static double countFeeByFeeType(Collection  feeLlist,boolean isForceDeposit){
    	double totalFee=0.0f;
    	if(feeLlist!=null&& !feeLlist.isEmpty()){
    		Iterator feeIter=feeLlist.iterator();
    		while(feeIter.hasNext()){
    			AccountItemDTO accountItemDTO=(AccountItemDTO)feeIter.next();
    			if(CommonConstDefinition.YESNOFLAG_YES.equals(accountItemDTO.getForcedDepositFlag())&& isForceDeposit)
    				totalFee=BusinessUtility.doubleFormat(totalFee+accountItemDTO.getValue());
    				//totalFee=(double)(Math.floor(totalFee*100+accountItemDTO.getValue()*100+0.01)/100);
    			else if(CommonConstDefinition.YESNOFLAG_NO.equals(accountItemDTO.getForcedDepositFlag())&& !isForceDeposit)
    				totalFee=BusinessUtility.doubleFormat(totalFee+accountItemDTO.getValue());
    				//totalFee=(double)(Math.floor(totalFee*100+accountItemDTO.getValue()*100+0.01)/100);
    		}
    	}
    	return totalFee;
    }
    /**
     * �ϼ�֧�����ܶ�(ֻ�ϼ�֧��������ú�֧���ʵ��ķ����ۺ�)
     * @param payLlist
     * @return
     */
    private static double countPayment(Collection  payLlist){
    	double totalPay=0.0f;
    	if(payLlist!=null&& !payLlist.isEmpty()){
    		Iterator feeIter=payLlist.iterator();
    		while(feeIter.hasNext()){
    			PaymentRecordDTO paymentRecordDTO=(PaymentRecordDTO)feeIter.next();
    			if(!CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE.equals(paymentRecordDTO.getPayType())&&
    			!CommonConstDefinition.PAYMENTRECORD_TYPE_BILLING.equals(paymentRecordDTO.getPayType()))
    				continue;
    			totalPay=BusinessUtility.doubleFormat(totalPay+paymentRecordDTO.getAmount());
    			//totalPay=(double)(Math.floor(totalPay*100+paymentRecordDTO.getAmount()*100+0.01)/100);
    		}
    	}
    	return totalPay;
    }
    /**
     * �ϼ�֧�����ܶ�(ֻ�ϼ�֧��������ú�֧���ʵ��ķ����ۺ�)
     * @param payLlist
     * @return
     */
    private static double countPrePayment(Collection  payLlist){
    	double totalPay=0.0f;
    	if(payLlist!=null&& !payLlist.isEmpty()){
    		Iterator feeIter=payLlist.iterator();
    		while(feeIter.hasNext()){
    			PaymentRecordDTO paymentRecordDTO=(PaymentRecordDTO)feeIter.next();
    			if(!CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentRecordDTO.getPayType())&&
    			   !CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_RR.equals(paymentRecordDTO.getPayType()))
    				continue;
    			totalPay=BusinessUtility.doubleFormat(totalPay+paymentRecordDTO.getAmount());
    			//totalPay=(double)(Math.floor(totalPay*100+paymentRecordDTO.getAmount()*100+0.01)/100);
    		}
    	}
    	return totalPay;
    }
    /**
     * �ϼ�֧���е��ֽ�����������Ԥ����
     * @param payLlist
     * @param isCash
     * @return
     */
    private static double countPayment(Collection  payLlist,boolean  isCash){
    	double totalPay=0.0f;
    	if(payLlist!=null&& !payLlist.isEmpty()){
    		Iterator feeIter=payLlist.iterator();
    		while(feeIter.hasNext()){
    			PaymentRecordDTO paymentRecordDTO=(PaymentRecordDTO)feeIter.next();
    			if(!CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentRecordDTO.getPayType())&&
    			   !CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_RR.equals(paymentRecordDTO.getPayType()))
    				continue;
    			String cashFlag=BusinessUtility.getCashFlagByMopID(paymentRecordDTO.getMopID());
    			//�ϼ��ֽ�ԤԼ�����ߺϼ��������Ԥ����
    			if(CommonConstDefinition.YESNOFLAG_YES.equals(cashFlag)&&isCash)
    				totalPay=BusinessUtility.doubleFormat(totalPay+paymentRecordDTO.getAmount());
    				//totalPay=(double)(Math.floor(totalPay*100+paymentRecordDTO.getAmount()*100+0.01)/100);
    			else if(CommonConstDefinition.YESNOFLAG_NO.equals(cashFlag)&&!isCash) 
    				totalPay=BusinessUtility.doubleFormat(totalPay+paymentRecordDTO.getAmount());
    				//totalPay=(double)(Math.floor(totalPay*100+paymentRecordDTO.getAmount()*100+0.01)/100);
    		}
    	}
    	return totalPay;
    }
    
    /**
     * �ϼ�Ԥ��ֿ��ܶ�
     * @param payLlist
     * @return
     */
    private static double countPreDeduction(Collection  preDeductionLlist){
    	double totalPay=0.0f;
    	if(preDeductionLlist!=null&& !preDeductionLlist.isEmpty()){
    		Iterator preDeductionIter=preDeductionLlist.iterator();
    		while(preDeductionIter.hasNext()){
    			PrepaymentDeductionRecordDTO deductionDTO=(PrepaymentDeductionRecordDTO)preDeductionIter.next();
    			totalPay=BusinessUtility.doubleFormat(totalPay+deductionDTO.getAmount());
    			//totalPay=(double)(Math.floor(totalPay*100+deductionDTO.getAmount()*100+0.01)/100);
    		}
    	}
    	return totalPay;
    }
    /**
     * ��������id�����������Ŀͻ������ͷ����б�,���ݿͻ�������DateFrom��NbrDate�����·��õ�Date1��Date2
     * @param csiid
     * @throws ServiceException
     */
    public static void updatePrePayAccountItem(int csiid)throws ServiceException{
    	try{
	    	Collection ccidCol=BusinessUtility.getCCIDByCsiID(csiid);
	    	if(!ccidCol.isEmpty()){
	    		AccountItemHome acctItemHome=HomeLocater.getAccountItemHome();
	    		Collection acctItemCol=acctItemHome.findByReferTypeAndReferID(CommonConstDefinition.FTREFERTYPE_M, csiid);
	    		if(acctItemCol!=null && !acctItemCol.isEmpty()){
	    			Iterator acctitemIter=acctItemCol.iterator();
	    			while(acctitemIter.hasNext()){
	    				AccountItem accountItem=(AccountItem)acctitemIter.next();
	    				if(ccidCol.contains(new Integer(accountItem.getCcID()))){
	    					CustomerCampaignHome custcampaignHome= HomeLocater.getCustomerCampaignHome();
	    					CustomerCampaign custCamp=custcampaignHome.findByPrimaryKey(new Integer(accountItem.getCcID()));
	    					accountItem.setDate1(custCamp.getDateFrom());
	    					accountItem.setDate2(custCamp.getNbrDate());
	    					accountItem.setDtLastmod(TimestampUtility.getCurrentTimestamp());
	    				}
	    			}
	    		}
	    			
	    	}
	    }catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����ͻ������ͷ��ö�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����ͻ�������Ϣ");
		}catch(Throwable e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("δ֪����");
			
		}
    }
    /**
     * ���·�װ�ͻ��ײ�����ʱ�ķ����б�.
     * @param csiDto
     * @param customerCampaign
     * @param rfType
     * @param feeList
     * @return
     * @throws ServiceException 
     */
    public static Collection reencapsulateFeeInfo(
			CustServiceInteractionDTO csiDto,
			CustomerCampaign customerCampaign,CustomerCampaignDTO ccDto, String rfType, Collection feeList)
			throws ServiceException {
		ArrayList result = new ArrayList();
    	if(feeList==null||feeList.isEmpty()){
    		return result;
    	}
//		LogUtility.log(clazz, LogLevel.DEBUG,
//				"feeserviece.reencapsulateFeeInfo.feeList:>>>>>"
//						+ feeList);
		String csiType = csiDto.getType();
		int ccid = customerCampaign.getCcID().intValue();
		int operatorID = csiDto.getCreateOperatorId();
		Iterator feeIt = feeList.iterator();
		while (feeIt.hasNext()) {
			AccountItemDTO aiDto = reencapsulateFeeInfo(csiDto,
					(AccountItemDTO) feeIt.next(), operatorID);
			aiDto.setCcID(ccid);
			if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BDP
					.equals(csiType)) {
				Timestamp newNbrDate = customerCampaign.getNbrDate();
				Timestamp oldNbrDate = ccDto.getNbrDate();
				aiDto.setDate1(oldNbrDate);
				aiDto.setDate2(newNbrDate);
				aiDto.setRfBillingCycleFlag(rfType);
			} else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BDC
					.equals(csiType)){
				aiDto.setDate1(customerCampaign.getDateFrom());
				aiDto.setDate2(customerCampaign.getDateTo());
			}
			result.add(aiDto);
		}
//		LogUtility.log(clazz, LogLevel.DEBUG,
//				"feeserviece.reencapsulateFeeInfo.result:>>>>>"
//						+ result);
		return result;
	}
    /**
     * �����ײ�ȡ����ط���.
     * @param csiDto
     * @param serviceAccountIDList
     * @param customerProductList
     * @param operatorID
     * @param deviceFeeList
     * @return
     * @throws ServiceException
     */
    public static Collection customerBundleCancelImmediateFeeCalculator(
			CustServiceInteractionDTO csiDto,int ccid, Map saMap,
			 Collection deviceFeeList) throws ServiceException {
    	Collection feeList=new ArrayList();
    	//����һ���Է�,
    	AccountDTO acctDto=BusinessUtility.getAcctDTOByAcctID(csiDto.getAccountID());
    	CustomerDTO custDto=BusinessUtility.getCustomerDTOByCustomerID(csiDto.getCustomerID());
    	java.util.Iterator sait=saMap.keySet().iterator();
		Collection billingObjectCol = new ArrayList();
    	while(sait.hasNext()){
    		Integer said=(Integer) sait.next();
    		BillingObject billingObj=new BillingObject();
    		billingObj.setAccountID(csiDto.getAccountID());
    		billingObj.setCustomerID(csiDto.getCustomerID());
    		billingObj.setServiceAccountID(said.intValue());
    		billingObj.setCustType(custDto.getCustomerType());
    		billingObj.setAcctType(acctDto.getAccountType());
    		billingObj.setSHasPackage("N");
    		billingObjectCol.add(billingObj);
    	}
		ImmediateFeeList onceFeeList = ImmediateFeeCalculator
				.getDefaultImmediateFeeCalculator().calculateImmediateFee(
						csiDto, billingObjectCol);
		if(deviceFeeList!=null&&!deviceFeeList.isEmpty()){
			onceFeeList.getAcctItemList().addAll(deviceFeeList);
		}
//		onceFeeList.getAcctItemList().addAll(infoFeeList);
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"onceFeeList>>>>"+onceFeeList);

		feeList=encapsulateFeeInfo(csiDto,onceFeeList,csiDto.getCreateOperatorId());
		return feeList;
	}
	/**
	 * 
     * �����з��÷�װ��ͬһ��ʽ�з��ظ�ǰ̨
     * CSIDTO
     * ImmediateFeeList
     * InfoFeeList
	 * immFeeList
	 * operatorID
     */
	private static Collection encapsulateFee4Account(CustServiceInteractionDTO csiDTO, 
													ImmediateFeeList immediateFeeList,
													Collection hisFeeList, 
													Collection infoFeeList,
													Collection deductionList,Collection paymentList,Collection prePaymentList,int opID,BatchNoDTO iBatchNo)throws ServiceException
    {
		Collection feeCol = new ArrayList();
    	ImmediateCountFeeInfo immediateCountFeeInfo = new ImmediateCountFeeInfo();
		//�������еķ��ü����б��з��ظ�ǰ̨����
		Collection acctItemCol = new ArrayList();
		Collection newAcctItemCol = new ArrayList();

		if(hisFeeList != null)
		{
			Iterator itAcctItem = hisFeeList.iterator();
			while(itAcctItem.hasNext())
			{
				AccountItemDTO acctItem = (AccountItemDTO)itAcctItem.next();
				if(acctItem.getFeeType().compareTo("P") != 0)
					acctItemCol.add(acctItem); 
			}
		}

		if((immediateFeeList != null) && (immediateFeeList.getAcctItemList() != null))
		{
			Iterator itImAcctItem =immediateFeeList.getAcctItemList().iterator();
			while(itImAcctItem.hasNext())
			{
				AccountItemDTO acctItem = (AccountItemDTO)itImAcctItem.next();
				acctItem.setReferType(CommonConstDefinition.PAYMENTRECORSOURCETYPE_M);
				acctItem.setReferID(csiDTO.getId());
				if(acctItem.getFeeType().compareTo("P") != 0)
					newAcctItemCol.add(acctItem);
			}
		}
		if(infoFeeList != null)
		{
			Iterator itInfoAcctItem = infoFeeList.iterator();
			while(itInfoAcctItem.hasNext())
			{
				AccountItemDTO acctItem = (AccountItemDTO)itInfoAcctItem.next();
				acctItem.setReferType(CommonConstDefinition.PAYMENTRECORSOURCETYPE_M);
				acctItem.setReferID(csiDTO.getId());

				if(acctItem.getFeeType().compareTo("P") != 0)
					newAcctItemCol.add(acctItem);
			}
		}
		if(iBatchNo != null)
		{
System.out.println("ȡ�û��棬������ImmediateCountFeeInfo��");

			Collection spCache = BusinessUtility.getSpProcessCache(iBatchNo);
			immediateCountFeeInfo.setSpCache(spCache);
System.out.println(spCache);
		}
		double cash = BusinessUtility.getPreDeposit(csiDTO.getAccountID(),"C");
		double token = BusinessUtility.getPreDeposit(csiDTO.getAccountID(),"T");
		
		immediateCountFeeInfo.setAccountName(BusinessUtility.getAcctDTOByAcctID(csiDTO.getAccountID()).getAccountName());
		immediateCountFeeInfo.setAccountid(csiDTO.getAccountID());
		immediateCountFeeInfo.setPreCashDoposit(cash);
		immediateCountFeeInfo.setPreTokenDoposit(token);
		immediateCountFeeInfo.setNewAccountItem(newAcctItemCol);
		immediateCountFeeInfo.setAcctItemList(acctItemCol);
		immediateCountFeeInfo.setPaymentRecordList(paymentList);
		immediateCountFeeInfo.setPrePaymentRecordList(deductionList);
		immediateCountFeeInfo.setPrePayList(prePaymentList);
		feeCol.add(immediateCountFeeInfo);
		return feeCol;
	}

	/*
		֧���˻�����������
		����immediateCountFeeInfo����
		liudi start
	*/
	public static void PayFee4Account(int operatorID,int orgID,Collection feeCol,boolean bCreateInvoice) throws ServiceException
	{
		if(feeCol == null) throw new ServiceException("�������������Ϊ��");
		Iterator itObj = feeCol.iterator();
		while(itObj.hasNext())
		{
			ImmediateCountFeeInfo cntFee = (ImmediateCountFeeInfo) itObj.next();
			//У���߼�			

			Collection fCol = new ArrayList();
			fCol.addAll(cntFee.getAcctItemList());
			fCol.addAll(cntFee.getNewAccountItem());
System.out.println("PayFee4Account");
System.out.println(cntFee);
			long fee = getSumFee(fCol);

			Collection payCol = new ArrayList();
			payCol.addAll(cntFee.getPaymentRecordList());
			payCol.addAll(cntFee.getNewPaymentRecordList());			
			long payment = getSumPaymentRecord(payCol);
			
			Collection deductionCol = new ArrayList();
			deductionCol.addAll(cntFee.getPrePaymentRecordList());
			deductionCol.addAll(cntFee.getNewPrepayRecordList());
			long deduction = getSumDeduction(deductionCol);
System.out.println("fee = " + fee + ", payment = " + payment + ",deduction =" + deduction);
			//if(fee != payment + deduction) throw new ServiceException("�����޷���ƽ����У�鴫���֧����ֿۼ�¼");
			if(Math.abs(fee - (payment + deduction))>0.0001) throw new ServiceException("�����޷���ƽ����У�鴫���֧����ֿۼ�¼");

			long cPrepay = getSumNewPrepayment(cntFee.getNewPaymentRecordList(),"C");
			long tPrepay = getSumNewPrepayment(cntFee.getNewPaymentRecordList(),"T");

System.out.println("cPrepay = " + cPrepay + ", tPrepay = " + tPrepay);

			long acDeduction = (long)(BusinessUtility.getPreDeposit(cntFee.getAccountid(),"C")*100);
			long atDeduction = (long)(BusinessUtility.getPreDeposit(cntFee.getAccountid(),"T")*100);

			if( cPrepay > acDeduction ) throw new ServiceException("�ڲ��ʻ�û���㹻���ֽ�����֧��");
			if( tPrepay > atDeduction ) throw new ServiceException("�ڲ��ʻ�û���㹻�������������֧��");

			long tDeduction = getNewDeduction(cntFee.getNewPrepayRecordList(),"T");
			long cDeduction = getNewDeduction(cntFee.getNewPrepayRecordList(),"C");

			update_account(cntFee.getAccountid(),cPrepay - cDeduction , tPrepay - tDeduction);
			save_all_data(feeCol);
			//�����˵�
			if( bCreateInvoice )
			{
				InvoiceDTO inv = createInvoice4Account(operatorID,orgID,cntFee);
				Invoice ejbInv = createInvoice(inv);
				inv.setInvoiceNo(inv.getInvoiceNo());
				ImmediatePayFeeService ipf = new ImmediatePayFeeService(fCol,payCol,deductionCol,null);				
				set_all_invoicedFlag(cntFee,inv);
				ipf.payFeeDB(ipf.payFee());
			}

			
			
		}
		//�־û���¼	
	} 

	private static void processSpCache(Collection spCache) throws ServiceException
	{
		if(spCache == null) return;

		Iterator itObj = spCache.iterator();
		while(itObj.hasNext())
		{

			SpOperationCacheDTO oc = (SpOperationCacheDTO)itObj.next();
			int iExecuteStatus = 1;
			if(oc.getIssqlStmtFlag().compareTo(CommonConstDefinition.YESNOFLAG_YES) == 0 )
			{

System.out.println("��ʼִ��SQL");
				//ִ��SQL���
				if(oc.getSqlStmt() != null)
					if(!BusinessUtility.ExecuteSqlstmt(oc.getSqlStmt()))
						iExecuteStatus =2;
System.out.println("��ʼִ��SQL"+oc.getSqlStmt());
			}
			else
			{
				if(oc.getOperationType().compareTo(CommonConstDefinition.SP_OPERATETYPE_F001)==0)
				{
					try
					{
			    		AccountItemHome accountItemHome=HomeLocater.getAccountItemHome();
						AccountItem accItem = accountItemHome.findByPrimaryKey(new Integer(oc.getReferRecordId()));
						if(accItem.getStatus().compareTo(CommonConstDefinition.AISTATUS_VALIDATE1)==0
							|| accItem.getStatus().compareTo(CommonConstDefinition.AISTATUS_VALIDATE)==0)
							
						{
							accItem.setStatus(CommonConstDefinition.AISTATUS_VALIDATE2);
							accItem.setDtLastmod(TimestampUtility.getCurrentTimestamp());
						}
						else
							iExecuteStatus = 3;
					}catch(HomeFactoryException e) {
		    			LogUtility.log(FeeService.class,LogLevel.ERROR,"processSpCache",e);
		    			throw new ServiceException("���ò��Ҷ�λ����");	            		
    				}catch(FinderException e) {
		    			LogUtility.log(FeeService.class,LogLevel.ERROR,"processSpCache",e);
		    			throw new ServiceException("���ò��Ҵ���");
    				}
					//����refer����״̬
				}
			}
			String sql = "Update t_Sp_Operationcache Set DT_LASTMOD = SysTimeStamp,";
			if(iExecuteStatus == 1)
			{
				sql = sql + "ProcessStatus = 'S'";
				//ִ�гɹ�
			}
			else if( (iExecuteStatus ==2) || (iExecuteStatus == 3) )
			{
				//SQLִ��ʧ��
				sql = sql + "ProcessStatus = 'F'";
			}
			sql = sql + " where seqNo =" +oc.getSeqNo();
			if(!BusinessUtility.ExecuteSqlstmt(sql))
				throw new ServiceException("����ʧ��");
			// oc ״̬��ת,DT_LASTMOD����
		}
	}

	private static void set_all_invoicedFlag(ImmediateCountFeeInfo icf,InvoiceDTO inv)
	throws ServiceException
	{
		Iterator iter1 = icf.getAcctItemList().iterator();
		while(iter1.hasNext())
		{
			AccountItemDTO act = (AccountItemDTO)iter1.next();
			PayFeeUtil.updateAcctItemInvoiced(act.getAiNO(),"Y",inv.getInvoiceNo());
		}
		
		Iterator iter2 = icf.getNewAccountItem().iterator();
		while(iter2.hasNext())
		{
			AccountItemDTO act = (AccountItemDTO)iter2.next();
			PayFeeUtil.updateAcctItemInvoiced(act.getAiNO(),"Y",inv.getInvoiceNo());
		}

		Iterator iter3 = icf.getNewPaymentRecordList().iterator();
		while(iter3.hasNext())
		{
			PaymentRecordDTO pr = (PaymentRecordDTO)iter3.next();
			PayFeeUtil.updatePaymentRecordInvoiced(pr.getSeqNo(),"Y",inv.getInvoiceNo());
		}

		Iterator iter4 = icf.getPaymentRecordList().iterator();
		while(iter4.hasNext())
		{
			PaymentRecordDTO pr = (PaymentRecordDTO)iter4.next();
			PayFeeUtil.updatePaymentRecordInvoiced(pr.getSeqNo(),"Y",inv.getInvoiceNo());
		}

		Iterator iter5 = icf.getPrePaymentRecordList().iterator();
		while(iter5.hasNext())
		{
			PrepaymentDeductionRecordDTO pd = (PrepaymentDeductionRecordDTO)iter5.next();
			PayFeeUtil.updateDeductionRecordInvoiced(pd.getSeqNo(),"Y",inv.getInvoiceNo());
		}

		Iterator iter6 = icf.getNewPrepayRecordList().iterator();
		while(iter6.hasNext())
		{
			PrepaymentDeductionRecordDTO pd = (PrepaymentDeductionRecordDTO)iter6.next();
			PayFeeUtil.updateDeductionRecordInvoiced(pd.getSeqNo(),"Y",inv.getInvoiceNo());
		}
		if(icf.getPrePayList() != null)
		{
			Iterator iter7 = icf.getPrePayList().iterator();
			while(iter7.hasNext())
			{
				PaymentRecordDTO pr = (PaymentRecordDTO)iter7.next();
				PayFeeUtil.updatePaymentRecordInvoiced(pr.getSeqNo(),"Y",inv.getInvoiceNo());
			}
		
		}
		String sql = "Update T_PaymentRecord Set InvoicedFlag = 'Y' , InvoiceNo = " + inv.getInvoiceNo() + " Where Status = 'V' AND AcctID = " + inv.getAcctID();
		BusinessUtility.ExecuteSqlstmt(sql);

	}



	private static long getSumNewPrepayment(Collection paymentList,String sType)
	{
		if( paymentList == null ) return 0;
		long paymentAmount = 0;
		Iterator itPayment = paymentList.iterator();
		while(itPayment.hasNext())
		{
			PaymentRecordDTO paymentDto = (PaymentRecordDTO)itPayment.next();
	System.out.println("**************************************---" + BusinessUtility.getDepositFlagByMopID(paymentDto.getMopID()));
			if(BusinessUtility.getDepositFlagByMopID(paymentDto.getMopID()).compareTo(sType) != 0 ) continue;
			if( paymentDto.getPayType().compareTo("P")==0 ||paymentDto.getPayType().compareTo("RR")==0)
				paymentAmount = paymentAmount + ImmediatePayFeeService.double2long(paymentDto.getAmount());
		}
		return paymentAmount;
	}
	/*
		ǰ̨����ֿۼ�¼,֧����¼��,����У��,���ҷ���У����Ԥ���
	*/
	public static void getReturnDeposit(ImmediateCountFeeInfo icf) throws ServiceException
	{
		//У�� �����˻�ƽ
		Collection feeCol = new ArrayList();
		feeCol.addAll(icf.getAcctItemList());
		feeCol.addAll(icf.getNewAccountItem());
		long fee = getSumFee(feeCol);

		Collection payCol = new ArrayList();
		payCol.addAll(icf.getPaymentRecordList());
		payCol.addAll(icf.getNewPaymentRecordList());			
		long payment = getSumPaymentRecord(payCol);
			
		Collection deductionCol = new ArrayList();
		deductionCol.addAll(icf.getPrePaymentRecordList());
		deductionCol.addAll(icf.getNewPrepayRecordList());
		long deduction = getSumDeduction(deductionCol);

		//if(fee != payment + deduction) throw new ServiceException("�����޷���ƽ����У�鴫���֧����ֿۼ�¼");
		if(Math.abs(fee - (payment + deduction))>0.0001) throw new ServiceException("�����޷���ƽ����У�鴫���֧����ֿۼ�¼");
		//������Deposit���ظ�ǰ̨

		long tDeduction = getNewDeduction(icf.getNewPrepayRecordList(),"T");
		long cDeduction = getNewDeduction(icf.getNewPrepayRecordList(),"C");

		long acDeduction = (long)(icf.getPreCashDoposit()*100);
		long atDeduction = (long)(icf.getPreTokenDoposit()*100);
		
		if(tDeduction > atDeduction) throw new ServiceException("�ڲ��ʻ�û���㹻�������������֧��");
		if(cDeduction > acDeduction) throw new ServiceException("�ڲ��ʻ�û���㹻���ֽ�����֧��");

		icf.setPreCashDoposit( ((double)(acDeduction - cDeduction))/100.0f );
		icf.setPreTokenDoposit( ((double)(atDeduction - tDeduction))/100.0f );
	}
	/*
		�־û���¼
	*/
	private static long getNewDeduction(Collection deductionList,String sType)
	{	
		if( deductionList == null ) return 0;
		long deductionAmount = 0;
		Iterator itDeduction = deductionList.iterator();
		while(itDeduction.hasNext())
		{
			PrepaymentDeductionRecordDTO PrepaymentDto = (PrepaymentDeductionRecordDTO)itDeduction.next();
			if(PrepaymentDto.getPrepaymentType().compareTo(sType)!=0) continue;
			deductionAmount = deductionAmount + ImmediatePayFeeService.double2long(PrepaymentDto.getAmount());
		}
		return deductionAmount;
	}
	private static void save_all_data(Collection feeCol) throws ServiceException
	{
		//�־û����ü�¼
		//�־û�֧����Ԥ��
		//�־û��ֿۼ�¼
		if(feeCol != null)
		{
			Iterator itCol = feeCol.iterator();
			while(itCol.hasNext())
			{
				ImmediateCountFeeInfo icf = (ImmediateCountFeeInfo)itCol.next();
				Collection fCol = new ArrayList();
				fCol.addAll(icf.getAcctItemList());
				fCol.addAll(icf.getNewAccountItem());

				Collection payCol = new ArrayList();
				payCol.addAll(icf.getPaymentRecordList());
				payCol.addAll(icf.getNewPaymentRecordList());			


				Collection deductionCol = new ArrayList();
				deductionCol.addAll(icf.getPrePaymentRecordList());
				deductionCol.addAll(icf.getNewPrepayRecordList());

				Collection spCache = icf.getSpCache();				
				createFeeAndPaymentAndPreDuciton(fCol,payCol,deductionCol,spCache);
			}
		}
	}

	private static void update_account(int acctID,long cash,long token)throws ServiceException
	{
		PayFeeUtil.updateAccount(acctID,((double)cash)/100,CommonConstDefinition.PREPAYMENTTYPE_CASH);
		PayFeeUtil.updateAccount(acctID,((double)token)/100,CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY);
		PayFeeUtil.updateAccount(acctID);
	}

	/*
		���������ʵ�
	*/
	private static InvoiceDTO createInvoice4Account( int opID,int orgID,ImmediateCountFeeInfo icf)
	{	
		InvoiceDTO inv = new InvoiceDTO();
		inv.setInvoiceCycleId(BusinessUtility.getMinCreateInvoiceCycle());
		inv.setCreateOrgId(orgID);
		inv.setCreateOpId(opID);
		inv.setPayOrgId(orgID);
		inv.setPayOpId(opID);
		inv.setComments("");
		inv.setBatchNo( 0 );
		inv.setAmount(0.0f);
		inv.setPayAmount(0.0f);
		inv.setStatus("D");
		inv.setCustID(BusinessUtility.getCustIDByAccountID(icf.getAccountid()));
		inv.setAcctID(icf.getAccountid());
		inv.setCreateDate(TimestampUtility.getCurrentTimestamp());
		inv.setDueDate(TimestampUtility.TimestampMove(TimestampUtility.getCurrentTimestamp(),0,1,0));
		inv.setPayDate(TimestampUtility.getCurrentTimestamp());
		inv.setDtCreate(TimestampUtility.getCurrentTimestamp());
		inv.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		
		Collection fCol = new ArrayList();
		fCol.addAll(icf.getAcctItemList());
		fCol.addAll(icf.getNewAccountItem());

		Collection payCol = new ArrayList();
		payCol.addAll(icf.getPaymentRecordList());
		payCol.addAll(icf.getNewPaymentRecordList());			


		Collection deductionCol = new ArrayList();
		deductionCol.addAll(icf.getPrePaymentRecordList());
		deductionCol.addAll(icf.getNewPrepayRecordList());

		inv.setFeeTotal(((double)getSumFee(fCol)) /100);
		inv.setPaymentTotal( ((double)getSumPaymentRecord(payCol))  /100 );
		inv.setPrepaymentDeductionTotal(((double)getSumDeduction(deductionCol)) /100);
		inv.setPrepaymentTotal(((double)getSumPrepayment(payCol)) /100);
		return inv;
	}

	private static long getSumFee(Collection feeList)
	{
		if( feeList == null ) return 0;
		long feeAmount = 0;
		Iterator itFee = feeList.iterator();
		while(itFee.hasNext())
		{
			AccountItemDTO accItemDto = (AccountItemDTO)itFee.next();
			if( accItemDto.getFeeType().compareTo("P") == 0 ) continue;
			feeAmount = feeAmount + ImmediatePayFeeService.double2long(accItemDto.getValue());
		}
		return feeAmount;
	}

	private static long getSumPrepayment(Collection paymentList)
	{
		if( paymentList == null ) return 0;
		long paymentAmount = 0;
		Iterator itPayment = paymentList.iterator();
		while(itPayment.hasNext())
		{
			PaymentRecordDTO paymentDto = (PaymentRecordDTO)itPayment.next();
			if( paymentDto.getPayType().compareTo("P")==0 ||paymentDto.getPayType().compareTo("RR")==0)
				paymentAmount = paymentAmount + ImmediatePayFeeService.double2long(paymentDto.getAmount());
		}
		return paymentAmount;
	}

	private static long getSumPaymentRecord(Collection paymentList)
	{
		if( paymentList == null ) return 0;
		long paymentAmount = 0;
		Iterator itPayment = paymentList.iterator();
		while(itPayment.hasNext())
		{
			PaymentRecordDTO paymentDto = (PaymentRecordDTO)itPayment.next();
			if( paymentDto.getPayType().compareTo("N")==0 || paymentDto.getPayType().compareTo("C")==0 ||paymentDto.getPayType().compareTo("RF")==0)
				paymentAmount = paymentAmount + ImmediatePayFeeService.double2long(paymentDto.getAmount());
		}
		return paymentAmount;
	}

	private static long getSumDeduction(Collection prePaymentList)
	{
		
		if( prePaymentList == null ) return 0;
		long deductionAmount = 0;
		Iterator itDeduction = prePaymentList.iterator();
		while(itDeduction.hasNext())
		{
			PrepaymentDeductionRecordDTO PrepaymentDto = (PrepaymentDeductionRecordDTO)itDeduction.next();
			deductionAmount = deductionAmount + ImmediatePayFeeService.double2long(PrepaymentDto.getAmount());
		}
		return deductionAmount;
	}
	/**
	 * ������Ѻ���װΪ����
	 * @param depositValue
	 * @param accountID
	 * @return
	 * @throws ServiceException
	 */
	private static  AccountItemDTO encapsulateDeposit(double depositValue,int accountID)throws ServiceException{
		//if(depositValue==0.0f)
		if(Math.abs(depositValue)<0.0001)
			return null;
		AccountItemDTO acctItemDTO=new AccountItemDTO();
		acctItemDTO.setAcctID(accountID);
		acctItemDTO.setAcctItemTypeID(BusinessUtility.getAcctItemTypeIDyFieldName("ForcedDepositAcctItemTypeID"));
		//����feeType
		acctItemDTO.setFeeType(BusinessUtility.getFeeTypeByItemTypeID(acctItemDTO.getAcctItemTypeID()));
		acctItemDTO.setValue(depositValue);
		acctItemDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
		acctItemDTO.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_NO);
		return acctItemDTO;
	}


	/**
     * �����ײ�ת������.
     * @param csiDto	//��������
     * @param CustType	//�ͻ�����
     * @param AcctType	//�ʻ�����
     * @param oldccid	//ԭ�ײ�
     * @param newBoundleID	//���ײ�
     * @param oldPackageID	//�ϲ�Ʒ��
     * @param newPackageID	//�²�Ʒ��
	 * @param newProductCol	//�����Ĳ�Ʒ
	 * @param opID			//����ԱID
     * @return icf ImmediateCountFeeInfo
     * @throws ServiceException
     */
	public static  ImmediateCountFeeInfo boundleTrans(CustServiceInteractionDTO csi,
														String custType,String acctType,
														int oldccid,int newBundleID,
														int oldPackageID,int newPackageID,
														Collection newProductCol,int opID)
	throws ServiceException
	{

		if(oldccid <= 0) throw new ServiceException("�ͻ��ײ�ID������Ϊ�գ�");
		if(newBundleID <= 0) throw new ServiceException("���ײ�ID������Ϊ��");
		if(oldPackageID <= 0) throw new ServiceException("ԭ��Ʒ��ID������Ϊ��");
		if(newPackageID <= 0) throw new ServiceException("Ŀ���Ʒ��ID������Ϊ��");

		//���ײ���ͣ
		String ccids = String.valueOf(oldccid);
		BatchNoDTO iBatchNo = new BatchNoDTO(0);
		Collection feeCol = InfoFeeImmediateBilling.callImmediateBillingCampaign(ccids,"T",opID,0,iBatchNo);
		Collection spCache = BusinessUtility.getSpProcessCache(iBatchNo);
		//���ײ�Ԥ��
		//�²�Ʒ����
		//������
		ArrayList campaignCol = new ArrayList();
		campaignCol.add(new Integer(newBundleID));
		BillingObject bo = new BillingObject();
		bo.setCustType(custType);
		bo.setAcctType(acctType);
		bo.setCampaignCol(campaignCol);
		HashMap package2ProductMap=new HashMap();		
		package2ProductMap.put(new Integer(newPackageID),newProductCol);
		bo.setPackage2ProductMap(package2ProductMap);	
		bo.setSHasPackage("Y");
		bo.setOrgPackageID(oldPackageID);
		bo.setDestPackageID(newPackageID);
		Collection billingObjectCol	= new ArrayList();
		billingObjectCol.add(bo);
		ImmediateFeeList imf = ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csi,billingObjectCol);

		feeCol.addAll(imf.getAcctItemList());
		
		
		ImmediateCountFeeInfo icf = new ImmediateCountFeeInfo();
		icf.setAcctItemList(feeCol);
		icf.setSpCache(spCache);
		//��װΪImmediateCountFeeInfo����
		double countFee=countFee(icf.getAcctItemList());
		icf.setTotalValueAccountItem(countFee);

		AccountDTO   accountDTO=BusinessUtility.getAcctDTOByAcctID(csi.getAccountID());
		icf.setAccountid(csi.getAccountID());
		icf.setAccountName(accountDTO.getAccountName());
		icf.setPreCashDoposit(accountDTO.getCashDeposit());
		icf.setPreTokenDoposit(accountDTO.getTokenDepositID());
		return icf;
	}
	/**
	 * �����ֹ��������ʱ������ķ���.
	 * @param csi
	 * @param ccDto
	 * @return
	 * @throws ServiceException
	 */
	public static ImmediateCountFeeInfo calculateManualGrantCampaignFeeInfo(CustServiceInteractionDTO csi,
			CustomerCampaignDTO ccDto) throws ServiceException{
		CustomerDTO custDto=BusinessUtility.getCustomerDTOByCustomerID(ccDto.getCustomerID());
		AccountDTO acctDto=BusinessUtility.getAcctDTOByAcctID(ccDto.getAccountID());
		ArrayList campaignCol = new ArrayList();
		campaignCol.add(new Integer(ccDto.getCampaignID()));
		BillingObject bo = new BillingObject();
		bo.setCustType(custDto.getCustomerType());
		bo.setAcctType(acctDto.getAccountType());
		bo.setCampaignCol(campaignCol);
		HashMap package2ProductMap=new HashMap();
		Map product2packageMap=BusinessUtility.getCampaignAgmtProductList(ccDto.getCampaignID());
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����calculateManualGrantCampaignFeeInfo  product2packageMap����"+product2packageMap);

		if(product2packageMap!=null&&!product2packageMap.isEmpty()){
			for(Iterator it=product2packageMap.entrySet().iterator();it.hasNext();){
				Entry en=(Entry) it.next();
				Integer productID=(Integer) en.getKey();
				Integer packageID=(Integer) en.getValue();
				Collection productCol=null;
				if(package2ProductMap.containsKey(packageID)){
					productCol=(Collection) package2ProductMap.get(packageID);
				}else{
					productCol=new ArrayList();
					package2ProductMap.put(packageID, productCol);
				}
				productCol.add(productID);
			}
		}
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����calculateManualGrantCampaignFeeInfo  package2ProductMap����"+package2ProductMap);
		if(package2ProductMap==null||package2ProductMap.isEmpty()){
			bo.setSHasPackage("N");
			bo.setPackage2ProductMap(null);	
		}else{
			bo.setSHasPackage("Y");
			bo.setPackage2ProductMap(package2ProductMap);	
		}
		Collection billingObjectCol	= new ArrayList();
		billingObjectCol.add(bo);
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����calculateManualGrantCampaignFeeInfo  billingObjectCol����"+billingObjectCol);
		ImmediateFeeList imf;
		imf = ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csi,billingObjectCol);

		
		ImmediateCountFeeInfo icf = new ImmediateCountFeeInfo();
		icf.setAcctItemList(imf.getAcctItemList());
		//��װΪImmediateCountFeeInfo����
		double countFee=countFee(icf.getAcctItemList());
		icf.setTotalValueAccountItem(countFee);

		AccountDTO   accountDTO=BusinessUtility.getAcctDTOByAcctID(csi.getAccountID());
		icf.setAccountid(csi.getAccountID());
		icf.setAccountName(accountDTO.getAccountName());
		icf.setPreCashDoposit(accountDTO.getCashDeposit());
		icf.setPreTokenDoposit(accountDTO.getTokenDepositID());
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����calculateManualGrantCampaignFeeInfo  ImmediateCountFeeInfo����"+icf);

		return icf;
	}
	/**
	 * ��װһ���豸�˷Ѽ�¼
	 * @param deviceFee
	 * @param customerID
	 * @param accountID
	 * @param saID
	 * @return
	 */
	public static AccountItemDTO encapsulateDeviceReturnFee(double deviceFee,
			int customerID, int accountID, int saID) {
		//if (deviceFee == 0.0f || customerID == 0 || accountID == 0)
		if (Math.abs(deviceFee)<0.0001 || customerID == 0 || accountID == 0)
			return null;
		AccountItemDTO acctItemDto = new AccountItemDTO();
		acctItemDto.setValue(deviceFee);
		acctItemDto.setAcctItemTypeID("D000000");
		acctItemDto.setForcedDepositFlag("N");
		acctItemDto.setAcctID(accountID);
		acctItemDto.setCustID(customerID);
		acctItemDto.setServiceAccountID(saID);
		return acctItemDto;
	}
	
	/**
	 * Ԥ�Ʒѣ�����������ǰ̨���õģ�
	 * @param csiid
	 * @param operatorID
	 * @throws ServiceException
	 */
	public static void preCalculateFeeInfo(int csiid ,int operatorID)throws  ServiceException{
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����preCalculateFeeInfo  csiid����"+csiid);
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"����preCalculateFeeInfo  operatorID����"+operatorID);
		//����Ԥ�ƷѴ����������
        Collection psidList=BusinessUtility.getPsIDListbyCsiID(csiid);
        LogUtility.log(FeeService.class,LogLevel.DEBUG,"����preCalculateFeeInfo  psidList����"+psidList);
		BatchNoDTO iBatchNo = new BatchNoDTO(0); //���ﲻ��Ҫ���˷ѣ�����Ԥ�ƺ�û�б�Ҫִ�д洢���̻��������
        Collection infoFeeList=FeeService.calculateInfoFee(psidList,operatorID,"X","Y",iBatchNo);
	}

}
