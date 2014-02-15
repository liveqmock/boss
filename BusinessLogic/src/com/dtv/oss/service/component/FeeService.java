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
	 * 计算新增产品/增机时发生的一次性费用
	 * @param csiDto 受理单对象
	 * @param packageCol   购买的产品包集合
	 * @param campaignCol  购买的套餐或者促销的集合
	 * @param commonObj 公用费用参数对象
	 * @return
	 * @throws ServiceException
	 */
	public static Collection calculateImmediateFee(CustServiceInteractionDTO csiDto,Collection packageCol,Collection campaignCol,CommonFeeAndPayObject commonObj)throws  ServiceException{
		Collection feeCol=new ArrayList();
		//封装计费参数对象
		Collection billingObjectCol=PublicService.encapsulateBillingObject(commonObj, packageCol, campaignCol);
		LogUtility.log(clazz,LogLevel.DEBUG,"■billingObjectCol■"+billingObjectCol);
		//调用公用接口计算一次性费用
		ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
		LogUtility.log(clazz,LogLevel.DEBUG,"■ImmediateFeeList■"+currentFeeList);
		//把计算的一次性费用转换为公用的计费对象(里面可以包含团购时已经支付的支付列表)
		ImmediateCountFeeInfo immediateCountFeeInfo= encapsulateFeeInfoAndAccountInfo(csiDto.getAccountID() ,csiDto.getType(),null,null);
		immediateCountFeeInfo.setAccountName(commonObj.getAccountName());
		immediateCountFeeInfo.setAccountid(csiDto.getAccountID());
		immediateCountFeeInfo.setAcctItemList(currentFeeList.getAcctItemList());
		//用来把押金合并到费用之中
		//if(commonObj.getDeposit()!=0.0f)
		if(Math.abs(commonObj.getDeposit())>0.0001)
			immediateCountFeeInfo.getAcctItemList().add(encapsulateDeposit(commonObj.getDeposit(),csiDto.getAccountID()));
		immediateCountFeeInfo.setPaymentRecordList(currentFeeList.getPaymentList());
		double countFee=countFee(currentFeeList.getAcctItemList());
		double countPay=countPayment(currentFeeList.getPaymentList());
		if(countFee>countPay)
			immediateCountFeeInfo.setTotalValueAccountItem(BusinessUtility.doubleFormat(countFee-countPay));
			//immediateCountFeeInfo.setTotalValueAccountItem((double)(Math.floor(countFee*100-countPay*100+0.01)/100));
		//把公用的费用对象放到费用集合中
		feeCol.add(immediateCountFeeInfo);
		return feeCol;
	}
	/**
	 * 计算重复 购买的一次性费用.
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
				//封装计费参数对象
				Collection billingObjectCol=PublicService.encapsulateBillingObject(commonObj, buyPackageList, buyCampaignList);
				LogUtility.log(clazz,LogLevel.DEBUG,"■billingObjectCol■"+billingObjectCol);
				//调用公用接口计算一次性费用
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
				LogUtility.log(clazz,LogLevel.DEBUG,"■ImmediateFeeList■"+currentFeeList);
				//把计算的一次性费用转换为公用的计费对象(里面可以包含团购时已经支付的支付列表)
				immediateCountFeeInfo.getAcctItemList().addAll(currentFeeList.getAcctItemList());
				immediateCountFeeInfo.getPaymentRecordList().addAll(currentFeeList.getPaymentList());
			}
		}
		//用来把押金合并到费用之中
		//if(commonObj.getDeposit()!=0.0f)
		if(Math.abs(commonObj.getDeposit())>0.0001)
			immediateCountFeeInfo.getAcctItemList().add(encapsulateDeposit(commonObj.getDeposit(),csiDto.getAccountID()));

		double countFee=countFee(immediateCountFeeInfo.getAcctItemList());
		double countPay=countPayment(immediateCountFeeInfo.getPaymentRecordList());
		if(countFee>countPay)
			immediateCountFeeInfo.setTotalValueAccountItem(BusinessUtility.doubleFormat(countFee-countPay));
			//immediateCountFeeInfo.setTotalValueAccountItem((double)(Math.floor(countFee*100-countPay*100+0.01)/100));
		//把公用的费用对象放到费用集合中
		feeCol.add(immediateCountFeeInfo);

		return feeCol;
	}
	/**
	 * 计算开户时发生的一次性费用
	 * @param csiDto 受理单对象
	 * @param packageCol   购买的产品包集合
	 * @param campaignCol  购买的套餐或者促销的集合
	 * @param commonObj 公用费用参数对象
	 * @return
	 * @throws ServiceException
	 */
	public static Collection calculateImmediateFeeForOpen(CustServiceInteractionDTO csiDto,Collection packageCol,Collection campaignCol,CommonFeeAndPayObject commonObj)throws  ServiceException{
		Collection feeCol=new ArrayList();
		//封装计费参数对象
		Collection billingObjectCol=PublicService.encapsulateBillingObject(commonObj, packageCol, campaignCol);
		LogUtility.log(clazz,LogLevel.DEBUG,"■billingObjectCol■"+billingObjectCol);
		//调用公用接口计算一次性费用
		ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
		LogUtility.log(clazz,LogLevel.DEBUG,"■ImmediateFeeList■"+currentFeeList);
		//把计算的一次性费用转换为公用的计费对象(里面可以包含团购时已经支付的支付列表)
		ImmediateCountFeeInfo immediateCountFeeInfo= new ImmediateCountFeeInfo();
		immediateCountFeeInfo.setAccountName(commonObj.getAccountName());
		immediateCountFeeInfo.setAccountid(csiDto.getAccountID());
		immediateCountFeeInfo.setAcctItemList(currentFeeList.getAcctItemList());
		//用于团购的时候从团购中取得已经支付的支付列表
		immediateCountFeeInfo.setGropBargainPayedList(currentFeeList.getPaymentList());
		//用来把押金合并到费用之中
		//if(commonObj.getDeposit()!=0.0f)
		if(Math.abs(commonObj.getDeposit())>0.0001)
			immediateCountFeeInfo.getAcctItemList().add(encapsulateDeposit(commonObj.getDeposit(),csiDto.getAccountID()));
		immediateCountFeeInfo.setPaymentRecordList(currentFeeList.getPaymentList());
		double countFee=countFee(currentFeeList.getAcctItemList());
		double countPay=countPayment(currentFeeList.getPaymentList());
		if(countFee>countPay)
			immediateCountFeeInfo.setTotalValueAccountItem(BusinessUtility.doubleFormat(countFee-countPay));
			//immediateCountFeeInfo.setTotalValueAccountItem((double)(Math.floor(countFee*100-countPay*100+0.01)/100));
		//把公用的费用对象放到费用集合中
		feeCol.add(immediateCountFeeInfo);
		return feeCol;
	}
	
	/**
	 * 手工开工单时计费
	 * @param jobCardDTO
	 * @return
	 * @throws ServiceException
	 */
	public static Collection calculateImmediateFeeForJobCard(JobCardDTO jobCardDTO,String customizeValue)throws  ServiceException{
		Collection feeCol=new ArrayList();
		AccountDTO accountDTO=BusinessUtility.getAcctDTOByAcctID(jobCardDTO.getAccountID());
		//把计算的一次性费用转换为公用的计费对象(里面可以包含团购时已经支付的支付列表)
		ImmediateCountFeeInfo immediateCountFeeInfo= new ImmediateCountFeeInfo();
		immediateCountFeeInfo.setAccountName(accountDTO.getAccountName());
		immediateCountFeeInfo.setAccountid(jobCardDTO.getAccountID());
		if(customizeValue!=null && !"".equals(customizeValue)){
			AccountItemDTO acctItemDTO=new AccountItemDTO();
			acctItemDTO.setAcctID(jobCardDTO.getAccountID());
			acctItemDTO.setAcctItemTypeID(BusinessUtility.getAcctItemTypeIDyConfig("SET_W_CUSTOMIZEFEECONFIGURATION",jobCardDTO.getSubType(),customizeValue));
			//增加feeType
			acctItemDTO.setFeeType(BusinessUtility.getFeeTypeByItemTypeID(acctItemDTO.getAcctItemTypeID()));
			acctItemDTO.setValue(Double.valueOf(customizeValue).floatValue()*jobCardDTO.getAddPortNumber());
			acctItemDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			acctItemDTO.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_NO);
			Collection acctItemList=new ArrayList();
			acctItemList.add(acctItemDTO);
			immediateCountFeeInfo.setAcctItemList(acctItemList);
		}
		//把公用的费用对象放到费用集合中
		feeCol.add(immediateCountFeeInfo);
		return  feeCol;
	}
	/**
	 * 封装针对工单的费用集合
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
	 * 封装针对工单的单条费用
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
		//这里需要设置的是对工单的计费，这里目前没有配置，暂时如此处理
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
	 * 封装针对工的支付集合
	 * @param jobCardDto
	 * @param paymentList
	 * @param operatorID
	 * @return
	 */
	public static Collection  reencapsulatePaymentInfoForJobcard(JobCardDTO jobCardDto,Collection paymentList,int operatorID){
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■reencapsulatePaymentInfo  jobCardDto■■"+jobCardDto);
		Collection payList=new ArrayList();
		if(paymentList!=null && !paymentList.isEmpty()){
			Iterator paymentIter=paymentList.iterator();
			while(paymentIter.hasNext()){
				PaymentRecordDTO paymentRecordDTO=(PaymentRecordDTO)paymentIter.next();
				payList.add(reencapsulatePaymentInfoForJobcard(jobCardDto, paymentRecordDTO,operatorID));
			}
		}
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■reencapsulatePaymentInfo  payList■■"+payList);
		return payList;
	}
	/**
	 * 封装针对工单的单条支付
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
	 *封装对手工创建工单时对预存抵扣的封装
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
				//这里如此设置为了满足杨勇的要求
				prePaymentDRDTO.setComments("");
				prePaymentCol.add(prePaymentDRDTO);
			}
		}
		return prePaymentCol;
	}
	/**
	 * 对手工创建工单时费用的支付
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
    			// 持久化费用、支付、预存抵扣
 			    createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList(),immediateCountFeeInfo.getSpCache());
 			   if(commonObj!=null){
  				  boolean mustPay=mustCreateFinancesetOffMap("KHGD",false,feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList());
  				  LogUtility.log(FeeService.class,LogLevel.DEBUG,"■FeeService payFeeOperation mustPay■"+mustPay); 
  				  if(!mustPay)
  					   return;
  				  //这个方法在这里的用法是根据支付的结果来判断是否支付了，但是在其他地方的MustPay是由前面控制传入的
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
	 * 计算系统中已存在得客户针对购买得业务做受理时计费(serviceAccountLit和psIDList只传一个)
	 * @param csiDto 受理单对象
	 * @param serviceAccountLit 操作得业务帐户集合
	 * @param psIDList   操作得客户产品id集合
	 * @param commonObj   公用费用参数对象
	 * @return
	 * @throws ServiceException
	 */
	public static Collection calculateImmediateFeeForCustService(CustServiceInteractionDTO csiDto,Collection serviceAccountLit,Collection  psIDList,CommonFeeAndPayObject commonObj)throws  ServiceException{
		Collection feeCol=new ArrayList();
		//封装计费参数对象
		Collection billingObjectCol=PublicService.encapsulateBillingObjectForCust(commonObj, serviceAccountLit, psIDList);
		LogUtility.log(clazz,LogLevel.DEBUG,"■billingObjectCol■"+billingObjectCol);
		//调用公用接口计算一次性费用
		ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
		LogUtility.log(clazz,LogLevel.DEBUG,"■ImmediateFeeList■"+currentFeeList);
		//把计算的一次性费用转换为公用的计费对象(里面可以包含团购时已经支付的支付列表)
		ImmediateCountFeeInfo immediateCountFeeInfo= encapsulateFeeInfoAndAccountInfo(csiDto.getAccountID() ,csiDto.getType(),null,null);
		immediateCountFeeInfo.setAccountid(csiDto.getAccountID());
		immediateCountFeeInfo.setAccountName(commonObj.getAccountName());
		immediateCountFeeInfo.setAcctItemList(currentFeeList.getAcctItemList());
		//用来把押金合并到费用之中
		//if(commonObj.getDeposit()!=0.0f)
		if(Math.abs(commonObj.getDeposit())>0.0001)
			immediateCountFeeInfo.getAcctItemList().add(encapsulateDeposit(commonObj.getDeposit(),csiDto.getAccountID()));
		immediateCountFeeInfo.setPaymentRecordList(currentFeeList.getPaymentList());
		double countFee=countFee(currentFeeList.getAcctItemList());
		double countPay=countPayment(currentFeeList.getPaymentList());
		if(countFee>countPay)
			immediateCountFeeInfo.setTotalValueAccountItem(BusinessUtility.doubleFormat(countFee-countPay));
			//immediateCountFeeInfo.setTotalValueAccountItem((double)(Math.floor(countFee*100-countPay*100+0.01)/100));
		//把公用的费用对象放到费用集合中
		feeCol.add(immediateCountFeeInfo);
		return feeCol;
	}
    /**
     * 调帐
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
    	
    	//处理费用调帐
    	if(adjustFeeList!=null && !adjustFeeList.isEmpty()){
			Iterator feeIter=adjustFeeList.iterator();
			while(feeIter.hasNext()){
				FeeAdjustmentWrapDTO currentFeeWrap=(FeeAdjustmentWrapDTO)feeIter.next();
				AccountItemDTO accountItemDTO=encapsulateAdjustFeeInfo(adjustType,id,customerID,accountID,currentFeeWrap.getAccountItemDTO(),operatorID);
				AccountItem accountItem = createAccountItem(accountItemDTO);
				LogUtility.log(clazz,LogLevel.DEBUG,"■■■adjustCSIFeeAndPayment accountItemDTO■■■" + accountItemDTO);
				
				//调帐记录
				AccountAdjustmentDTO accountAdjustmentDTO=currentFeeWrap.getAccountAdjustmentDTO();
				accountAdjustmentDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				accountAdjustmentDTO.setReferRecordType(CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_F);
				accountAdjustmentDTO.setReferRecordID(accountItemDTO.getAiNO());
				accountAdjustmentDTO.setAdjustmentType(adjustType);
				
				LogUtility.log(clazz,LogLevel.DEBUG,"■■■adjustCSIFeeAndPayment feeAdjustmentDTO■■■" + accountAdjustmentDTO);
				//创建
				AccountAdjustment accountAdjustMent = createAccountAdjustMent(accountAdjustmentDTO,operatorID);
				accountItem.setSourceRecordID(accountAdjustMent.getSeqNo().intValue());
				accountItemDTO.setSourceRecordID(accountAdjustMent.getSeqNo().intValue());
				//侯增加,为了写日志方便
				Object logArray[]=new Object[]{accountAdjustMent,accountItemDTO};
				logCol.add(logArray);
				feeCol.add(accountItemDTO);
			}
		}

		//处理支付调帐
		if(adjustPaymentList!=null && !adjustPaymentList.isEmpty()){
			Iterator paymentIter=adjustPaymentList.iterator();
			while(paymentIter.hasNext()){
				PaymentAdjustWrapDTO currentpaymentWrap=(PaymentAdjustWrapDTO)paymentIter.next();
				PaymentRecordDTO paymentRecordDTO=encapsulateAdjustPaymentInfo(adjustType,id,customerID,accountID,currentpaymentWrap.getPaymentRecordDTO(),operatorID);
				PaymentRecord  paymentRecod =createPaymentRecord(paymentRecordDTO);
				//回置生成的支付id
				LogUtility.log(clazz,LogLevel.DEBUG,"■■■adjustCSIFeeAndPayment paymentRecordDTO■■■" + paymentRecordDTO);
				//调帐记录
				AccountAdjustmentDTO accountAdjustmentDTO=currentpaymentWrap.getAccountAdjustmentDTO();
				accountAdjustmentDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
				accountAdjustmentDTO.setReferRecordType(CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_P);
				accountAdjustmentDTO.setReferRecordID(paymentRecordDTO.getSeqNo());
				accountAdjustmentDTO.setAdjustmentType(adjustType);
				
				LogUtility.log(clazz,LogLevel.DEBUG,"■■■adjustCSIFeeAndPayment  payAdjustmentDTO■■■" + accountAdjustmentDTO);
				//创建
				AccountAdjustment accountAdjustMent = createAccountAdjustMent(accountAdjustmentDTO,operatorID);
				paymentRecod.setSourceRecordId(accountAdjustMent.getSeqNo().intValue());
				paymentRecordDTO.setSourceRecordID(accountAdjustMent.getSeqNo().intValue());
				
				Object logArray[]=new Object[]{accountAdjustMent,paymentRecordDTO};
				logCol.add(logArray);
				paymentCol.add(paymentRecordDTO);
			}
		}
		//处理预存抵扣调帐
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
				
				LogUtility.log(clazz,LogLevel.DEBUG,"■■■adjustCSIFeeAndPayment  payAdjustmentDTO■■■" + accountAdjustmentDTO);
				//创建
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
		
		//更新帐户预存余额
	    PayFeeUtil.updatePrepaymentList(paymentCol);
	    PayFeeUtil.updatePrepaymentDuctionList(deductionCol);
		if(cloneFeeList!=null && !cloneFeeList.isEmpty() && ((clonePayList!=null && !clonePayList.isEmpty())||(clonePrepayList!=null && !clonePrepayList.isEmpty()))){
			//建立销账关系
		    ImmediatePayFeeService feeService=new ImmediatePayFeeService(cloneFeeList,clonePayList,clonePrepayList,null);
		    //持久化费用、支付、预存抵扣
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
			   // 持久化费用、支付、预存抵扣
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
     * 根据调帐的结果更新帐单和账户信息
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
		    //总金额
			double totalPay=0;
			//预存总额
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
			//抵扣总额
			double totalDeduction=0.0f;
			//针对预存抵扣调帐
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
			    //帐单的应付金额等于0的时候，修改帐单的状态，账户状态，产品的状态
			    //if(invoice.getAmount()==0){
			    if(Math.abs(invoice.getAmount())<0.0001){
				    invoice.setPayDate(TimestampUtility.getCurrentDateWithoutTime());
				    invoice.setPayOpId(operatorID);
				    invoice.setPayOrgId(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
				    invoice.setStatus(CommonConstDefinition.INVOICE_STATUS_PAID);
				    //如果当前的账单是最后的一张账单的话，修改业务账户的状态，产品的状态，账户的状态
			    	if(!BusinessUtility.existNopaidInvoice(accountID)){
					    LogUtility.log(FeeService.class,LogLevel.ERROR,"开始更新帐户,客户产品,业务帐户状态,并生成相关事件.");
					    AccountDTO accountDTO=new AccountDTO();
			    		accountDTO.setAccountID(accountID);
			    		AccountService.updateAccountInfo(accountDTO,actionType);
						Collection psIDCol=BusinessUtility.getPsIDListByCond(0,accountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_SYSTEMSUSPEND);
				    	//更新业务账户和产品的状态
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
		    throw new ServiceException("账单调帐时定位错误");	            		
		}catch(FinderException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("账单调帐时查找错误");
		}
    	
    }
    /**
     * 支付账单
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

    		//从费用列表取得对应的费用
	    	Collection feeList=BusinessUtility.getAllFeeListByInvoice(invoiceDTO.getInvoiceNo());
	    	//合并封装所有费用
	    	feeList.addAll(FeeService.reencapsulateFeeInfo(csiDto,csifeeList,operatorID,invoiceDTO.getInvoiceNo()));
	    	//根据帐单取得所有的支付
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
	    	//合并所有的支付
	    	paymentList.addAll(csiPaymentList);
	    	//根据帐单取得所有的预存抵扣记录
	    	Collection prePaymentList=BusinessUtility.getAllPrePaymentListByInvoice(invoiceDTO.getInvoiceNo());
	    	
	    	Collection newPrePaymentList=FeeService.reecapsulatePrePaymentInfo(csiDto, csiPreDeductionList,invoiceDTO, operatorID);
	    	prePaymentList.addAll(newPrePaymentList);
	    	//修改客户产品的状态
		    ImmediatePayFeeService feeService=new ImmediatePayFeeService(feeList,paymentList,prePaymentList,null);
			// 持久化费用、支付、预存抵扣
			createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList(),null);
			feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_I);
			feeService.setId(invoiceDTO.getInvoiceNo());
			/*******************yangchen modify start 2008/08/25**************************************/
			//由于所有的针对帐户余额的更新都是针对当前支付或者预存抵扣所产生的，所以在这里给以修正
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
		    //如果当前的账单是最后的一张账单的话，修改业务账户的状态，产品的状态，账户的状态
	    	if(!BusinessUtility.existNopaidInvoice(accountDTO.getAccountID())){
			    LogUtility.log(FeeService.class,LogLevel.ERROR,"开始更新帐户,客户产品,业务帐户状态,并生成相关事件.");
	    		Collection psIDCol=BusinessUtility.getPsIDListByCond(0,accountDTO.getAccountID(),CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_SYSTEMSUSPEND);
	    		/*******************yangchen add start 2008/10/8**************************************/
	    		//计算产品恢复的时候预计费发生的费用
	    		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
	    		BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    		Collection infoFeeList=FeeService.calculateInfoFee(psIDCol,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL,batchNoDTO);
	    		//持久化产品恢复时预计费发生的费用
	    		createFeeAndPaymentAndPreDuciton(infoFeeList,null,null,commonObj.getSpCache());
	    		/*******************yangchen add end 2008/10/8**************************************/
	    		//更新帐户的状态
	    		AccountService.updateAccountInfo(accountDTO,actionType);
	    		//更新业务账户和产品的状态
		    	CustomerProductService.updateCustomerProduct(psIDCol,actionType,operatorID);
	    	}
	    	return invoice;
    	}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("支付账单时定位错误");	            		
		}catch(FinderException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("支付账单时查找错误");
		}
    }
    /**
     * 安装不成功退费
     * @param csiDto
     * @param operatorID
     * @throws ServiceException
     */
    public static void returnFeeForFailureInInstallation(CustServiceInteractionDTO csiDto,int operatorID) throws  ServiceException{

    	Collection csiFeeList=BusinessUtility.getFeeListByTypeAndID(csiDto.getId(),CommonConstDefinition.FTREFERTYPE_M,true);
    	if(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY.equals(csiDto.getPaymentStatus())){
    		//持久化费用、支付、预存抵扣
            createFeeAndPaymentAndPreDuciton(FeeService.reencapsulateReturnFeeInfo(csiDto,csiFeeList,operatorID),null,null,null);
    	}else{
    	   //处理团购券的回收
 		   if(csiDto.getGroupCampaignID()>0)
			   PublicService.cancelOpenAccountRealationGroupBargain(csiDto);
    		AccountDTO accountDTO=BusinessUtility.getAcctDTOByAcctID(csiDto.getAccountID());
    		Collection conditionPayList=BusinessUtility.getPaymentListByTypeAndID(csiDto.getId(),CommonConstDefinition.PAYMENTRECORDSOURCETYPE_ACCEPT,false);
    		Collection retrnPaymentList=	BusinessUtility.getPaymentListByTypeAndID(csiDto.getId(),CommonConstDefinition.PAYMENTRECORDSOURCETYPE_ACCEPT,true);
    		
    		double cashBalance=accountDTO.getCashDeposit();
    		double tokenBalance=accountDTO.getTokenDeposit();
    		if(countPayment(conditionPayList,true)>cashBalance|| countPayment(conditionPayList,false)>tokenBalance)
    			throw new ServiceException("本受理的预存金额被使用,不能进行退费");
	    	Collection csiPreDeductionList=BusinessUtility.getPreDeductionRecordListByTypeAndID(csiDto.getId(), CommonConstDefinition.FTREFERTYPE_M, true); 	
	    	//调用FeeService.reencapsulateReturnFeeInfo填充费用必要信息
	    	//调用FeeService.reencapsulateReturnPaymentInfo填充支付必要信息
	    	//实例化ImmediatePayFeeService feeService；
	    	//调用createFeeAndPaymentAndPreDuciton实例化费用，支付，预存抵扣
	    	ImmediatePayFeeService feeService=new ImmediatePayFeeService(FeeService.reencapsulateReturnFeeInfo(csiDto,csiFeeList,operatorID),FeeService.reencapsulateReturnPaymentInfo(csiDto,retrnPaymentList,operatorID),csiPreDeductionList,null);
	        //持久化费用、支付、预存抵扣
	    	createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList(),null);
			//已支付建立销账关系
	    	feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
	    	feeService.setId(csiDto.getId());
	    	PayFeeUtil.updatePrepaymentList(feeService.getPaymentList());
	    	PayFeeUtil.updatePrepaymentDuctionList(feeService.getPrePaymentList());
	    	feeService.payFeeDB(feeService.payFee());
    	}
    }
    /**
     * 账户预存
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
    		//经过这里后需要把受理单的状态设置为已付
    		csiDto.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
    		Account account=accountHome.findByPrimaryKey(new Integer(csiDto.getAccountID()));
    		account.setCashDeposit(BusinessUtility.doubleFormat(account.getCashDeposit()+cashBalance));
    		account.setTokenDeposit(BusinessUtility.doubleFormat(account.getTokenDeposit()+tokenBalance));
    		//account.setCashDeposit((double)(Math.floor(cashBalance*100+account.getCashDeposit()*100+0.01)/100));
    		//account.setTokenDeposit((double)(Math.floor(tokenBalance*100+account.getTokenDeposit()*100+0.01)/100));
    		return account;
	    }catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("预存时定位错误");	            		
		}catch(FinderException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("预存时查找错误");
		}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("预存时创建记录错误");
		}
    }
    /**
     * 处理单个帐户费用结算的问题
     * @param csiDto
     * @param csiFeeList
     * @param csiPaymentList
     * @param preductiionList
     * @param commonObj
     * @param operatorID
     * @throws ServiceException
     */
    public static void dealAccountFeeAndPayment(CustServiceInteractionDTO csiDto,Collection csiFeeList,Collection csiPaymentList,Collection preductiionList,CommonFeeAndPayObject commonObj,int operatorID)throws  ServiceException{
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■payFeeOperation  dealAccountFeeAndPayment■■"+csiFeeList);
    	if(csiFeeList!=null & !csiFeeList.isEmpty()){
    		if(csiFeeList.size()>1)
    			throw new ServiceException("目前只能处理单帐户.");
    		ImmediateCountFeeInfo immediateCountFeeInfo=(ImmediateCountFeeInfo)csiFeeList.iterator().next();
    		immediateCountFeeInfo.setNewPaymentRecordList(csiPaymentList);
    		immediateCountFeeInfo.setNewPrepayRecordList(preductiionList);
    		//getReturnDeposit(immediateCountFeeInfo);
    	}	
       LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■payFeeOperation  dealAccountFeeAndPayment■■"+csiFeeList);
    }
    /**
     * 处理客户销户/客户退户/销帐户
     * @param csiDto
     * @param csiFeeList
     */
    public static void payFeeOperationForReturnFee(CustServiceInteractionDTO csiDto,Collection csiFeeList,Collection csiPaymentList,Collection preductiionList,CommonFeeAndPayObject commonObj,int operatorID)throws  ServiceException{
    	if(csiFeeList!=null & !csiFeeList.isEmpty()){
    		if(csiFeeList.size()>1)
    			throw new ServiceException("目前只能处理单帐户.");
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
    		//处理费用支付问
    		PayFee4Account(operatorID,BusinessUtility.getOpOrgIDByOperatorID(operatorID),csiFeeList,true);
    	}
    }
    /**
     * 销户/退户/销账户时的费用支付(目前不使用了)
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
    			LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■payFeeOperation  TotalPreReturnFee■■"+immediateCountFeeInfo.getTotalPreReturnFee());
    			LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■payFeeOperation  TotalValueAccountItem■■"+immediateCountFeeInfo.getTotalValueAccountItem());
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
	    				LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■payFeeOperation  prePaymentRecordDTO■■"+prePaymentRecordDTO);
    				}
    				if(onceAndInfoFee>0){
    					PaymentRecordDTO paymentRecordDTO=new PaymentRecordDTO();
    	    			paymentRecordDTO.setAcctID(immediateCountFeeInfo.getAccountid());
    	    			paymentRecordDTO.setMopID(mopID);
    	    			paymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
    	    			paymentRecordDTO.setAmount(onceAndInfoFee);
    	    			currentAccountRecordList.add(paymentRecordDTO);
    	    			LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■payFeeOperation  paymentRecordDTO 收费■■"+paymentRecordDTO);
    				}else if(onceAndInfoFee<0){
    					PaymentRecordDTO paymentRecordDTO=new PaymentRecordDTO();
    	    			paymentRecordDTO.setAcctID(immediateCountFeeInfo.getAccountid());
    	    			paymentRecordDTO.setMopID(mopID);
    	    			paymentRecordDTO.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_FEE);
    	    			paymentRecordDTO.setAmount(onceAndInfoFee);
    	    			currentAccountRecordList.add(paymentRecordDTO);
    	    			LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■payFeeOperation  paymentRecordDTO 退费■■"+paymentRecordDTO);
    				}
    				feeTotal=BusinessUtility.doubleFormat(feeTotal-immediateCountFeeInfo.getTotalValueAccountItem());
    				//feeTotal=(double)(Math.floor(feeTotal*100-immediateCountFeeInfo.getTotalValueAccountItem()*100+0.01)/100);
    				
    			}
    		    ImmediatePayFeeService feeService=new ImmediatePayFeeService(FeeService.reencapsulateFeeInfo(csiDto,immediateCountFeeInfo.getAcctItemList(),operatorID),FeeService.reencapsulatePaymentInfo(csiDto,currentAccountRecordList,operatorID),reecapsulatePrePaymentInfo(csiDto,immediateCountFeeInfo.getPrePaymentRecordList(),null,operatorID),null);
 			    // 持久化费用、支付、预存抵扣
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
    		throw new ServiceException("费用和支付不一致。");
    	}
    }
    /**
     * 受理费用支付
     * @param csiDto
     * @param csiFeeList  费用记录
     * @param csiPaymentList  支付记录
     * @param infoFeeList     需要持久化但是不需要支付的产生的信息费记录
     * @param preductiionList   预存抵扣记录
     * @param commonObj  通用的参数对象
     * @param operatorID  操作员id
     * @throws ServiceException
     */
    public  static void payFeeOperation(CustServiceInteractionDTO csiDto,Collection csiFeeList,Collection csiPaymentList,Collection infoFeeList, Collection preductiionList,CommonFeeAndPayObject commonObj,int operatorID)throws  ServiceException{
    	//由于以后对产品的操作都不涉及到对信息费进行抵扣和支付的问题,在这里单独处理信息费的创建
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■■■■■SpCache■■■■■"+commonObj.getSpCache()); 
    	createFeeAndPaymentAndPreDuciton(infoFeeList,null,null,commonObj.getSpCache());
    	if(csiFeeList!=null && !csiFeeList.isEmpty()){
    		Iterator feeIter=csiFeeList.iterator();
    		while(feeIter.hasNext()){
    			ImmediateCountFeeInfo immediateCountFeeInfo=(ImmediateCountFeeInfo)feeIter.next();
    			//下面的这个处理是用来把预存抵扣记录和对应的帐户建立关系
    			if(immediateCountFeeInfo.getAccountid()==csiDto.getAccountID())
    				immediateCountFeeInfo.setPrePaymentRecordList(reecapsulatePrePaymentInfo(csiDto,preductiionList,null,operatorID));
    			Collection currentAccountRecordList=operationOneAccountFee(immediateCountFeeInfo,csiPaymentList,operatorID);
    			ImmediatePayFeeService feeService=new ImmediatePayFeeService(FeeService.reencapsulateFeeInfo(csiDto,immediateCountFeeInfo.getAcctItemList(),operatorID),FeeService.reencapsulatePaymentInfo(csiDto,currentAccountRecordList,operatorID),reecapsulatePrePaymentInfo(csiDto,immediateCountFeeInfo.getPrePaymentRecordList(),null,operatorID),null);
    			// 持久化费用、支付、预存抵扣
 			    createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList(),null);
 			   if(commonObj!=null){
 				  boolean mustPay=mustCreateFinancesetOffMap(csiDto.getType(),false,feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList());
 				  LogUtility.log(FeeService.class,LogLevel.DEBUG,"■FeeService payFeeOperation mustPay■"+mustPay); 
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
     * 受理费用支付(用于开户/增机/新增产品)
     * @param csiDto
     * @param csiFeeList  费用记录
     * @param csiPaymentList  支付记录
     * @param preductiionList   预存抵扣记录
     * @param commonObj  通用的参数对象
     * @param operatorID  操作员id
     * @throws ServiceException
     */
    public static void payFeeOperationForOpen(CustServiceInteractionDTO csiDto, Collection csiFeeList, Collection csiPaymentList,
			Collection preductiionList, CommonFeeAndPayObject commonObj, int operatorID) throws ServiceException {
		// 由于以后对产品的操作都不涉及到对信息费进行抵扣和支付的问题,在这里单独处理信息费的创建
		String payOption = BusinessUtility.getCsiPaymentOption(csiDto.getType());
		if (csiFeeList != null && !csiFeeList.isEmpty()) {
			Iterator feeIter = csiFeeList.iterator();
			while (feeIter.hasNext()) {
				ImmediateCountFeeInfo immediateCountFeeInfo = (ImmediateCountFeeInfo) feeIter.next();
				// 下面的这个处理是用来把预存抵扣记录和对应的帐户建立关系
				if (immediateCountFeeInfo.getAccountid() == csiDto.getAccountID())
					immediateCountFeeInfo.setPrePaymentRecordList(reecapsulatePrePaymentInfo(csiDto, preductiionList, null,
							operatorID));
				ImmediatePayFeeService feeService = new ImmediatePayFeeService(FeeService.reencapsulateFeeInfo(csiDto,
						immediateCountFeeInfo.getAcctItemList(), operatorID), FeeService.reencapsulatePaymentInfo(csiDto,
						csiPaymentList, operatorID), reecapsulatePrePaymentInfo(csiDto, immediateCountFeeInfo
						.getPrePaymentRecordList(), null, operatorID), null);
				// 在受理单配置是立即支付的时候,需要取出团购中的预付支付
				if (commonObj.isMustPay()
						|| CommonConstDefinition.CSI_PAYMENT_OPTION_IM.equals(payOption)
						|| (CommonConstDefinition.CSI_PAYMENT_OPTION_OP.equals(payOption) && feeService.getPaymentList() != null && !feeService
								.getPaymentList().isEmpty())) {
					// 把团购对应的内容放到支付中去
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
						// 持久化费用、支付、预存抵扣
						createFeeAndPaymentAndPreDuciton(feeService.getFeeList(), null, null, null);
						/*
						 * //此处主要用于处理有团购券预付的情况,在月底归入帐单,而且有团购券的时候,受理成功的时候,只生成记录不改变受理单的付费状态
						 * if(CommonConstDefinition.CSI_PAYMENT_OPTION_IV.equals(payOption)&&
						 * csiDto.getGroupCampaignID()>0&&commonObj.isMustPay()){
						 * Collection
						 * groupBargainPayCol=FeeService.reencapsulatePaymentInfo(csiDto,getGroupBargainPayList(csiDto.getId()),operatorID);
						 * createFeeAndPaymentAndPreDuciton(null,groupBargainPayCol,null);
						 * PayFeeUtil.updatePrepaymentList(groupBargainPayCol);
						 * LogUtility.log(FeeService.class,LogLevel.DEBUG,"■FeeService
						 * payFeeOperation
						 * groupBargainPayCol■"+groupBargainPayCol); }
						 */
						return;
					} else {
						// 持久化费用、支付、预存抵扣
						createFeeAndPaymentAndPreDuciton(feeService.getFeeList(), feeService.getPaymentList(), feeService
								.getPrePaymentList(), null);
						csiDto.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
					}
				}
				// 修改团购券的状态
				if (csiDto.getGroupCampaignID() > 0)
					PublicService.dealOpenAccountRealationGroupBargain(csiDto, false,
							CommonConstDefinition.GROUPBARGAINDETAILSTATUS_RETURN);
				// 处理费用的支付和销帐
				feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
				feeService.setId(csiDto.getId());
				PayFeeUtil.updatePrepaymentList(feeService.getPaymentList());
				PayFeeUtil.updatePrepaymentDuctionList(feeService.getPrePaymentList());
				feeService.payFeeDB(feeService.payFee());
			}
		}
		// 处理归入帐单的时候,在录入安装信息的时候创建团购的已预付的支付
		if (CommonConstDefinition.CSI_PAYMENT_OPTION_IV.equals(payOption) && csiDto.getGroupCampaignID() > 0
				&& commonObj.isMustPay()) {
			Collection groupBargainPayCol = FeeService.reencapsulatePaymentInfo(csiDto, getGroupBargainPayList(csiDto.getId()),
					operatorID);
			createFeeAndPaymentAndPreDuciton(null, groupBargainPayCol, null, null);
			PayFeeUtil.updatePrepaymentList(groupBargainPayCol);
			LogUtility.log(FeeService.class, LogLevel.DEBUG, "■FeeService payFeeOperation groupBargainPayCol■"
					+ groupBargainPayCol);
		}
	}
    /**
     * 报修费用支付
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
    			System.out.println("====================包装后的feelist为："+feeService.getFeeList());
    			// 持久化费用、支付、预存抵扣
 			    createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList(),immediateCountFeeInfo.getSpCache());
 			   if(commonObj!=null){
  				  boolean mustPay=mustCreateFinancesetOffMap("KHBX",false,feeService.getFeeList(),feeService.getPaymentList(),feeService.getPrePaymentList());
  				  LogUtility.log(FeeService.class,LogLevel.DEBUG,"■FeeService payFeeOperation mustPay■"+mustPay); 
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
     * 对单个账户的支付的封装
     * @param csiDto
     * @param immediateCountFeeInfo
     * @param csiPaymentList
     * @param operatorID
     * @return
     * @throws ServiceException
     */
    private static  Collection operationOneAccountFee(ImmediateCountFeeInfo immediateCountFeeInfo,Collection csiPaymentList,int operatorID)throws  ServiceException{
    	double valueAccountItem=immediateCountFeeInfo.getTotalValueAccountItem();
    	//下面的一步处理用来把页面上使用内部帐户支付的预存抵扣从费用中扣除掉
    	double preDecutionValue=countPreDeduction(immediateCountFeeInfo.getPrePaymentRecordList());
    	valueAccountItem=BusinessUtility.doubleFormat(valueAccountItem-preDecutionValue);
    	//valueAccountItem=(double)(Math.floor(valueAccountItem*100-preDecutionValue*100+0.01)/100);
    	//如果待支付的金额为0，则直接返回null
    	//if(valueAccountItem==0.0f){
    	if(Math.abs(valueAccountItem)<0.0001){
    		return null;
    	}
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■operationOneAccountFee  accountID ■■"+immediateCountFeeInfo.getAccountid());
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
    				//修改费用列表中的金额
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
    				//删除掉支付中金额为0的支付
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
    				//修改费用列表中的金额
    				((ArrayList)csiPaymentList).remove(i);
    				currentAccountRecordList.add(paymentRecordDTO);
    			}
    		}
    	}
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■operationOneAccountFee  currentAccountRecordList■■"+currentAccountRecordList);
    	return currentAccountRecordList;
    }
    /**
     * 集团客户子客户新增业务账户/新增产品时计算费用
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
     * 计算新开账户时计算一次性费用
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
     * 针对客户操作的计费和账户信息费用的计算
     * @param csiDto
     * @param serviceAccountID
     * @param operatorID
     * @param deviceFeeList 设备费 （退户时使用）
     * @return
     * @throws ServiceException
*liudi start fiexd
     */
    public static Collection customerOpImmediateFeeCalculator(CustServiceInteractionDTO csiDto,int serviceAccountID,int operatorID,Collection deviceFeeList) throws  ServiceException{
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"■ customerOpImmediateFeeCalculator deviceFeeList■"+deviceFeeList);    	
    	Collection feeCol=new ArrayList();
    	//取得客户信息
    	CustomerDTO  custoemrDTO=BusinessUtility.getCustomerDTOByCustomerID(csiDto.getCustomerID());
    	//取得账户信息
    	AccountDTO   accountDTO=BusinessUtility.getAcctDTOByAcctID(csiDto.getAccountID());
    	//业务账户信息
    	Collection serviceAccountList=new ArrayList();
    	if(deviceFeeList!=null &&!deviceFeeList.isEmpty()){
    		if(((AccountItemDTO)((ArrayList)deviceFeeList).get(0)).getValue()>0){
    			((AccountItemDTO)((ArrayList)deviceFeeList).get(0)).setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_B);
    		}else{
    			((AccountItemDTO)((ArrayList)deviceFeeList).get(0)).setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_R);
    		}
    	}
    	//销账户
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OC.equals(csiDto.getType())){
			//取得帐户信息
			//取得账户下未入账的费用。
	    	Collection feeList =  BusinessUtility.getValidNoInvoicedFeeList4Account(csiDto.getAccountID());
			//取得账户下未入账的抵扣
			Collection deductionList = BusinessUtility.getValidNoInvoicedDeduction(csiDto.getAccountID());
			//取得账户下未入账的支付			
	    	Collection paymentList = BusinessUtility.getValidNoInvoicedPayment(csiDto.getAccountID());
			//取得账户下未入账的预存纪录
			Collection prePaymentList = BusinessUtility.getValidNoInvoicedPrePaymentRecord(csiDto.getAccountID());
			//一次性费用
			CommonFeeAndPayObject paramObj=PublicService.encapsulateBaseParam(csiDto, custoemrDTO, accountDTO);
			Collection billingObjectCol=PublicService.encapsulateBillingObjectForCust(paramObj,null,null);
			ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
			//计算信息费 返回AccountItemDTO List
			BatchNoDTO iBatchNo = new BatchNoDTO(0);
			Collection infoFeeList = InfoFeeImmediateBilling.callCloseAccount(accountDTO.getAccountID(),"C",operatorID,iBatchNo);	
			//一次性费，历史费用，信息费，抵扣记录，支付记录
			feeCol = encapsulateFee4Account(csiDto,currentFeeList,feeList,infoFeeList,deductionList,paymentList,prePaymentList,operatorID,iBatchNo);
			
		//业务账户（暂停/恢复/取消）用户（销户/退回）
		}else{
			//取得业务账户信息
			serviceAccountList.addAll(BusinessUtility.getSAIDListByCustIDAndSAID(csiDto.getCustomerID(),serviceAccountID));
			
			CommonFeeAndPayObject paramObj=PublicService.encapsulateBaseParam(csiDto, custoemrDTO, accountDTO);
			Collection billingObjectCol=PublicService.encapsulateBillingObjectForCust(paramObj,serviceAccountList,null);
			ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
			//把传近来的设备费计算进去
			if(deviceFeeList!=null && !deviceFeeList.isEmpty()){
				Iterator itDF = deviceFeeList.iterator();
				while(itDF.hasNext())
				{
					currentFeeList.getAcctItemList().add(FeeService.reencapsulateFeeInfo(csiDto,(AccountItemDTO)itDF.next(),operatorID));
				}
			}
			LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■customerOpImmediateFeeCalculator currentFeeList■■"+currentFeeList.getAcctItemList());

			//销户和退户的时候,需要合计所有帐户的费用及余额信息(需要合计客户帐户中所有未支付的信息,包括新产生的一次性费用和设备费之类的费用)
			//返回的费用是采用了客户预存抵扣过后的信息,返回的就是需要支付或者退还的信息
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CC.equals(csiDto.getType())||
			   CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_WC.equals(csiDto.getType())){
				Collection acctCol=BusinessUtility.getAcctDTOListByCustIDAndAcctID(csiDto.getCustomerID(), 0);
				if(acctCol!=null && !acctCol.isEmpty() && acctCol.size()>1)
					throw new ServiceException("该客户存在多个资金账户,不允许取消.");
				//取得帐户信息
				//取得账户下未入账的费用。
		    	Collection feeList =  BusinessUtility.getValidNoInvoicedFeeList4Account(csiDto.getAccountID());
				//取得账户下未入账的抵扣
				Collection deductionList = BusinessUtility.getValidNoInvoicedDeduction(csiDto.getAccountID());
				//取得账户下未入账的支付记录
		    	Collection paymentList = BusinessUtility.getValidNoInvoicedPayment(csiDto.getAccountID());
				
				//取得账户下未入账的预存纪录
				Collection prePaymentList = BusinessUtility.getValidNoInvoicedPrePaymentRecord(csiDto.getAccountID());

				//计算产生的信息费
				BatchNoDTO iBatchNo = new BatchNoDTO(0);
				Collection infoFeeList=InfoFeeImmediateBilling.callCloseCustomer(csiDto.getCustomerID(),csiDto.getAccountID(),CommonConstDefinition.ACCOUNT_STATUS_CLOSE,operatorID,iBatchNo);
				//一次性费，历史费用，信息费，抵扣记录，支付记录
				feeCol = encapsulateFee4Account(csiDto,currentFeeList,feeList,infoFeeList,deductionList,paymentList,prePaymentList,operatorID,iBatchNo);
			}else
				feeCol=encapsulateFeeInfo(csiDto,currentFeeList,operatorID);
			
		}
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■customerOpImmediateFeeCalculator feeCol■■"+feeCol);
    	return feeCol;
    }
    /**
     * 针对客户产品操作的计费和账户信息计算
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
     * 侯2007-2-26改写的一个重载的方法,旧的方法用于客户产品取消暂停等计费,费用直接由计费接口计算得来,现前台传入一个设备费用,需要合并进去,增加一个参数deviceFeeList
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
    	//取得客户信息
    	CustomerDTO  custoemrDTO=BusinessUtility.getCustomerDTOByCustomerID(csiDto.getCustomerID());
    	//取得账户信息
    	AccountDTO   accountDTO=BusinessUtility.getAcctDTOByAcctID(csiDto.getAccountID());
		CommonFeeAndPayObject paramObj=PublicService.encapsulateBaseParam(csiDto, custoemrDTO, accountDTO);
		paramObj.setDestProductID(destProductID);
		Collection billingObjectCol=PublicService.encapsulateBillingObjectForCust(paramObj,null,psIDCol);
		ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDto,billingObjectCol);
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■customerProductOpImmediateFeeCalculator.totalValueMustPay■■"+currentFeeList.getTotalValueMustPay());
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■customerProductOpImmediateFeeCalculator.currentFeeList■■"+currentFeeList.getAcctItemList());
		
		//加入设备费用到一次性费用中去.
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
		//计算相关账户的费用信息
		//feeCol=encapsulateFeeInfo(csiDto,currentFeeList,infoFeeList,operatorID);
		feeCol=encapsulateFeeInfo(csiDto,currentFeeList,operatorID);
    	return feeCol;
    } 
	/**
	 * 用来计算账户的合计费用信息
	 * @param csiDto 受理单信息
	 * @param immediateFeeList 计费后的一次性费
	 * @param infoFeeList   立即计费后的信息费
	 * @return  ImmediateCountFeeInfo的列表
	 * @throws ServiceException
	 */
	public static Collection encapsulateFeeInfo(CustServiceInteractionDTO csiDto,ImmediateFeeList immediateFeeList,Collection infoFeeList,int operatorID)throws  ServiceException{
		Collection feeCol=new ArrayList();
		boolean isIncludeInCurrent=false;
		//一次性费
		Collection oneOffFeeList=immediateFeeList.getAcctItemList();
		Hashtable currentTable=new Hashtable();
		//把计算后的信息费用按照账户进行分类
		if(infoFeeList!=null && !infoFeeList.isEmpty()){
			Iterator infoFeeIter=infoFeeList.iterator();
			while(infoFeeIter.hasNext()){
				AccountItemDTO accountItemDTO=(AccountItemDTO)infoFeeIter.next();
				//检查一次性费用指定的账户是否包含在信息费账户中
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
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■encapsulateFeeInfo.currentTable■■"+currentTable.size());
		//针对没有账户进行循环来计算该账户的相关费用
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
		//只有在销户/退户的情况下才需要结清所有帐户的费用
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CC.equals(csiDto.getType())||
		   CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_WC.equals(csiDto.getType())	){
			//检查并操作所有的账户信息
			Collection allAccountCol=BusinessUtility.getAcctDTOListByCustIDAndAcctID(csiDto.getCustomerID(),0);
			LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■encapsulateFeeInfo.allAccountCol■■"+allAccountCol.size());
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
		//一次性费用指定的账户不包含在信息费账户中时，对该帐户单独计算
		if(!isIncludeInCurrent){
			LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■encapsulateFeeInfo.isIncludeInCurrent  fee■■"+oneOffFeeList.toString());
			ImmediateCountFeeInfo immediateCountFeeInfo=encapsulateFeeInfoAndAccountInfo(csiDto.getAccountID() ,csiDto.getType(),reencapsulateFeeInfo(csiDto,oneOffFeeList,operatorID),null);
			feeCol.add(immediateCountFeeInfo);
		}
		return feeCol;
	}
	
	/**
	 * 用来把产生的一次性费用合并到对应的帐户中去
	 * @param csiDto
	 * @param immediateFeeList
	 * @param operatorID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection encapsulateFeeInfo(CustServiceInteractionDTO csiDto,ImmediateFeeList immediateFeeList,int operatorID)throws  ServiceException{
		Collection feeCol=new ArrayList();
		ImmediateCountFeeInfo immediateCountFeeInfo=encapsulateFeeInfoAndAccountInfo(csiDto.getAccountID() ,csiDto.getType(),null,null);
		//把一次性费一次性费用和对应的帐户绑定起来
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
	 * 封装费用计算及账户余额合计(用于业务受理的时候)
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
		//销户
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CC.equals(type)){
			operatorType=ImmediateCountFeeService.CANCEL_CUSTOMER;
			returnFee=ImmediateCountFeeService.RETURN_CASHACCOUNT;
			
		//退户
		} else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_WC.equals(type)){
			operatorType=ImmediateCountFeeService.CANCEL_CUSTOMER;
			returnFee=ImmediateCountFeeService.RETURN_CASHACCOUNT;
		//取消帐户
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OC.equals(type)){
			operatorType=ImmediateCountFeeService.CANCEL_ACCOUNT;
			returnFee=ImmediateCountFeeService.RETURN_CASHACCOUNT;
		}
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■reencapsulateFeeInfo  type■■"+type);
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■reencapsulateFeeInfo  operatorType■■"+operatorType);
		if(onceFeeList!=null &&!onceFeeList.isEmpty()){
			immediateCountFeeInfo=ImmediateCountFeeService.countFee(accountid,operatorType,returnFee,onceFeeList,infoFeeList);
		}else{
			immediateCountFeeInfo=ImmediateCountFeeService.countFee(accountid,operatorType,returnFee,infoFeeList);
		}
		return immediateCountFeeInfo;
	}
	
	/**
	 * 用于录入安装/维修成功的时候,用来把已经产生的费用封装用来支付
	 * @param accountID
	 * @param referType
	 * @param referID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getFeeInfoForRegisterInfo(int accountID,String referType,int referID) throws  ServiceException{
		Collection feeCol=new ArrayList();
		ImmediateCountFeeInfo immediateCountFeeInfo=encapsulateFeeInfoAndAccountInfo(accountID);
		//把一次性费一次性费用和对应的帐户绑定起来
		immediateCountFeeInfo.getAcctItemList().addAll(BusinessUtility.getFeeListByReferInfo(referType,referID));
		immediateCountFeeInfo.setTotalValueAccountItem(countFee(immediateCountFeeInfo.getAcctItemList()));
		//此处主要是用于团购的时候,在录入安装信息的时候需要把团购的支付先取出来
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
	 * 根据受理单点取得受理单关联得团购预付的支付记录
	 * @param csiid
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getGroupBargainPayList(int csiid)throws ServiceException{
		Collection groupBargainPayList=new ArrayList();
		CustServiceInteractionDTO csiDTO=BusinessUtility.getCSIDTOByID(csiid);
		if(csiDTO.getGroupCampaignID()>0){
			CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForExitCust(csiDTO,csiDTO.getCustomerID(),csiDTO.getAccountID());
			//根据是否有团购信息设置团购id和团购券关联的套餐id
			commonObj.setGroupBargainID(BusinessUtility.getGroupBargainDetailByID(csiDTO.getGroupCampaignID()).getGroupBargainID());
			Collection billingObjectCol=PublicService.encapsulateBillingObject(commonObj, null, null);
			LogUtility.log(clazz,LogLevel.DEBUG,"■billingObjectCol■"+billingObjectCol);
			//调用公用接口计算一次性费用
			ImmediateFeeList currentFeeList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csiDTO,billingObjectCol);
			if(currentFeeList.getPaymentList()!=null) 
				groupBargainPayList.addAll(currentFeeList.getPaymentList());
		}
		
		return groupBargainPayList;
	}
	/**
	 * 封装费用计算及账户余额合计(适用于受理和报修)
	 * @param accountid
	 * @return
	 * @throws ServiceException
	 */
	public static ImmediateCountFeeInfo encapsulateFeeInfoAndAccountInfo(int accountid) throws  ServiceException{
		ImmediateCountFeeInfo immediateCountFeeInfo=ImmediateCountFeeService.countFee(accountid,"","",null,null);
		return immediateCountFeeInfo;
	}
	
	/**
	 *封装预存抵扣列表(用于受理费用)
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
				//这里如此设置为了满足杨勇的要求
				prePaymentDRDTO.setComments("");
				prePaymentCol.add(prePaymentDRDTO);
			}
		}
		return prePaymentCol;
	}
	/**
	 *封装预存抵扣列表(用于报修费用)
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
				//这里如此设置为了满足杨勇的要求
				prePaymentDRDTO.setComments("");
				prePaymentCol.add(prePaymentDRDTO);
			}
		}
		return prePaymentCol;
	}
	/**
	 * 重新封装一下安装不成功退费费用信息
	 * @param csiDto
	 * @param accountItemList
	 */
	public static Collection  reencapsulateReturnFeeInfo(CustServiceInteractionDTO csiDto,Collection accountItemList,int operatorID)throws  ServiceException{
		Collection feeInfoList=new ArrayList();
		int customerID=csiDto.getCustomerID();
		int accountID=csiDto.getAccountID();
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■reencapsulateFeeInfo  csiDto■■"+csiDto);
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
						throw new ServiceException("重新封装费用信息定位错误");
					}catch(FinderException e) {
						LogUtility.log(clazz, LogLevel.ERROR, e);
						throw new ServiceException("重新封装费用信息中找不到相关信息");
						
					}
				feeInfoList.add(accountItemDTO);
			}
		}
		return feeInfoList;
	}
	
	/**
	 * 重新封装一下费用信息（受理费用）
	 * @param csiDto
	 * @param accountItemList
	 */
	public static Collection  reencapsulateFeeInfo(CustServiceInteractionDTO csiDto,Collection accountItemList,int operatorID)throws  ServiceException{
		Collection feeInfoList=new ArrayList();
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■reencapsulateFeeInfo  csiDto■■"+csiDto);
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
	 *   封装单条费用的信息
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
		// 用于支付帐单的情况
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
			
			// 用于重复购买设置 PSID,这里有些问题,这个方法和上面这一段代码作用重复了,为了照顾大部分流程稳定,先留着上面的代码.
			setPsidToAccountItemDTO(csiDto.getId(), accountItemDTO);
			// 用于设置预付的时候费用中的date1和date2
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
				}else if (csiDto.getPoint() !=0){  //add by david.Yang 协议用户购买时，point字段不会为0.为了打印需要设置date1和date2
					accountItemDTO.setDate1(TimestampUtility.getCurrentDateWithoutTime());
					accountItemDTO.setDate2(TimestampUtility.getTimeEndWithoutTime(accountItemDTO.getDate1(), "M",csiDto.getPoint()));
				}
			}

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("重新封装费用信息定位错误");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("重新封装费用信息中找不到相关信息");

		} catch (Throwable e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("非预期");

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
						LogUtility.log(clazz, LogLevel.ERROR, "本次受理不只一个CKID: "+psid +"<>"+csiCustProductInfo.getCustProductID());
						break;
					}
					if(productId==csiCustProductInfo.getProductID()){
						psid=csiCustProductInfo.getCustProductID();
						said=csiCustProductInfo.getReferServiceAccountID();
					}
				}
			}
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR, "重复购买费用封装异常");
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
	 * 在有套餐的时候,录入安装信息时更新费用的Date1和Date2
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
    					//取得现在到初始化时的时间长度,进行时间平移
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
		    throw new ServiceException("费用查找定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"updateAccountItemDate",e);
		    throw new ServiceException("费用查找错误");
    	}
		
		
		
	}
	
	
	
	/**
	 * 重新封装一下费用信息（报修费用）
	 * @param csiDto
	 * @param accountItemList
	 */
	public static Collection  reencapsulateFeeInfo(CustomerProblemDTO cpDto,int accountID,Collection accountItemList,int operatorID)throws  ServiceException{
		Collection feeInfoList=new ArrayList();
		int customerID=cpDto.getCustID();
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■reencapsulateFeeInfo  cpDto■■"+cpDto);
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
	 * 重新封装一下支付信息（报修费用支付）
	 * @param csiDto
	 * @param accountItemList
	 */
	public static Collection  reencapsulatePaymentInfo(CustomerProblemDTO cpDto,int accountID,Collection paymentList,int operatorID){
		int customerID=cpDto.getCustID();
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■reencapsulatePaymentInfo  cpDto■■"+cpDto);
		Collection payList=new ArrayList();
		if(paymentList!=null && !paymentList.isEmpty()){
			Iterator paymentIter=paymentList.iterator();
			while(paymentIter.hasNext()){				
				PaymentRecordDTO paymentRecordDTO=(PaymentRecordDTO)paymentIter.next();				
				LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■reencapsulatePaymentInfo one paymentRecordDTO■■"+paymentRecordDTO);
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
				LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■reencapsulatePaymentInfo two  paymentRecordDTO■■"+paymentRecordDTO);
				payList.add(paymentRecordDTO);
			}
		}
		return payList;
	}
	/**
	 * 重新封装一下支付信息（受理费用支付）
	 * @param csiDto
	 * @param accountItemList
	 */
	public static Collection  reencapsulatePaymentInfo(CustServiceInteractionDTO csiDto,Collection paymentList,int operatorID){
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■reencapsulatePaymentInfo  csiDto■■"+csiDto);
		Collection payList=new ArrayList();
		if(paymentList!=null && !paymentList.isEmpty()){
			Iterator paymentIter=paymentList.iterator();
			while(paymentIter.hasNext()){
				PaymentRecordDTO paymentRecordDTO=(PaymentRecordDTO)paymentIter.next();
				payList.add(reencapsulatePaymentInfo(csiDto, paymentRecordDTO,operatorID));
			}
		}
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■reencapsulatePaymentInfo  payList■■"+payList);
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
			throw new ServiceException("调帐类型不对");
		}
		*/
		return  preDeductionDTO;
	}
	/**
	 * 封装调帐费用信息
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
			throw new ServiceException("调帐类型不对");
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
				throw new ServiceException("该状态的报修单不允许调帐");
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
	 * 封装调帐的支付信息
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
			throw new ServiceException("调帐类型不对");
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
	 * 针对单个费用支付的封装
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
	 * 重新封装一下安装不成功支付信息
	 * @param csiDto
	 * @param accountItemList
	 */
	public static Collection  reencapsulateReturnPaymentInfo(CustServiceInteractionDTO csiDto,Collection paymentList,int operatorID){
		int customerID=csiDto.getCustomerID();
		int accountID=csiDto.getAccountID();
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■reencapsulatePaymentInfo  csiDto■■"+csiDto);
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
	 * 立即计算当前客户产品列表的信息费
	 * @param colPsid 客户产品列表
	 * @param operatorID 操作员id
	 * @param destStatus 目标状态
	 * @return AccountItemDTO的list
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
	 * 立即计算当前业务帐户下面的客户产品所产生的信息费
	 * @param said   业务帐户id
	 * @param operatorID  操作员id
	 * @param destStatus 目标状态
	 * @return
	 * @throws ServiceException
	 */
	public static Collection calculateInfoFeeForServiceAccountClose(int said,int operatorID,String destStatus,BatchNoDTO iBatchNo)throws  ServiceException{
		Collection feeCol=InfoFeeImmediateBilling.callCloseServiceAccount(said,destStatus,operatorID,iBatchNo);
		return feeCol;
	}
	/**
	 * 根据产品的ID列表封装数据，只有在设备更换时才使用到destProductID，其他时候直接传0就可以了
	 * @param colPsid
	 * @param destProductID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection encapsulateProductInfo(Collection colPsid ,int destProductID)throws  ServiceException{
		Collection productInfoCol=new ArrayList();
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■encapsulateProductInfo  start■■");
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■colPsid■"+colPsid);
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■destProductID■"+new Integer(destProductID));
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
					LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■ProductInfo■■",productInfo);
				}
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("封装产品列表时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("封装产品列表时时查找错误");
    	}
		
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■encapsulateProductInfo  end■■");
		return productInfoCol;
	}
	/**
	 * 计算报修所产生的费用
	 * @param cpDto
	 * @param accountID
	 * @param operatorID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection customerProblemOpImmediateFeeCalculator(CustomerProblemDTO cpDto,int accountID,int operatorID) throws  ServiceException{
	    //取得客户信息
    	CustomerDTO  custoemrDTO=BusinessUtility.getCustomerDTOByCustomerID(cpDto.getCustID());
    	Collection accountItemDTOList=ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee4CP(custoemrDTO,cpDto);
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"■accountItemDTOList■"+ accountItemDTOList);
    	Collection feeCol=new ArrayList();
		ImmediateCountFeeInfo immediateCountFeeInfo=encapsulateFeeInfoAndAccountInfo(accountID);
		//把一次性费一次性费用和对应的帐户绑定起来
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
	 * 创建费用信息
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
    			//封装费用信息
    	    	reencapsulateFeeInfo(cpDto,accountID,accountItemCol,operatorID);
    	    	createAccountItemList(accountItemCol);
    		}
    	}
	}
	

	/**
	 * 持久化费用，支付，预存抵扣
	 * @param accountItemList
	 * @param paymentRecordList
	 * @param prePaymentDeductionRecordList
	 * @throws ServiceException
	 */
	public  static void createFeeAndPaymentAndPreDuciton(Collection accountItemList,Collection paymentRecordList,Collection prePaymentDeductionRecordList,Collection spCache)throws  ServiceException{
	    //持久化未持久化的费用
	    createAccountItemList(accountItemList);
	    //持久化未持久化的支付
	    createPaymentRecordList(paymentRecordList);
	    //持久化未持久化的预存抵扣
	    createPrePaymentDeductionRecordList(prePaymentDeductionRecordList);
		//执行缓存的SQL语句
		processSpCache(spCache);
	}
	/**
	 * 持久化费用列表
	 * @param accountItemList
	 * @throws ServiceException
	 */
	private  static void createAccountItemList(Collection accountItemList)throws  ServiceException{
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■FeeService createAccountItemList■"+accountItemList);
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
							throw new ServiceException("生成反向费用时定位错误");	            		
						}catch(FinderException e) {
							LogUtility.log(FeeService.class,LogLevel.ERROR,"deleteFeeAlready",e);
							throw new ServiceException("查找费用时错误");
						}catch(CreateException e){
							LogUtility.log(FeeService.class,LogLevel.ERROR,"deleteFeeAlready",e);
							throw new ServiceException("创建费用时错误");
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
	 *创建费用
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
		    throw new ServiceException("创建费用时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"createAccountItem",e);
		    throw new ServiceException("创建费用时错误");
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
			LogUtility.log(clazz,LogLevel.WARN,"更改费用记录信息出错，原因：费用记录定位出错！");
			throw new ServiceException("更改费用记录信息出错，原因：费用记录定位出错！");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改费用记录信息出错，原因：费用记录查找出错！");
			throw new ServiceException("更改费用记录信息出错，原因：费用记录查找出错");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束更新费用记录信息！");
	}
	
	/**
	 * 持久化支付列表
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
	 * 创建支付
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
		    throw new ServiceException("创建支付时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"createPaymentRecord",e);
		    throw new ServiceException("创建支付时错误");
    	}
	}
	
	/**
	 * 持久化预存抵扣列表
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
	 * 创建预存抵扣
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
		    throw new ServiceException("创建预存抵扣时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"createPrePaymentDeductionRecord",e);
		    throw new ServiceException("创建预存抵扣时错误");
    	}

	}
	/**
	 * 持久化帐单
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
		    throw new ServiceException("创建帐单时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"createInvoice",e);
		    throw new ServiceException("创建帐单时错误");
    	}
	}
	/**
	 * 把某张受理单或者报修单的所关联的费用的状态变为无效变为无效
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
					accountItem.setComments("受理/报修取消或者失败时，费用取消的反向记录");
					accountItem.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					accountItemHome.create(accountItem);
				}
			}			
		}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"colseFeeInfo",e);
		    throw new ServiceException("费用取消时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"colseFeeInfo",e);
		    throw new ServiceException("费用取消时创建错误");
    	}
		
	}
	/**
	 * 创建一条调帐记录
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
			LogUtility.log(clazz,LogLevel.DEBUG,"■■■createAccountAdjustMent■■■"+accountAdjustmentDto);
			return accountAdjustHome.create(accountAdjustmentDto);
		}catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("调帐时定位错误");	            		
		}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("调帐时创建记录错误");
		}
	}
	
	/**
	 * 判断是否是一次性费用
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
				throw new   ServiceException("支付类型没有对应的定义");
			}
		
		return isCsiOnceFee;
	}
	
	/**
	 * 是否强制预存
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
			throw new   ServiceException("费用类别没有定义");
		}
		return isForceDeposit;
	}
	/**
	 * 是否是预存支付
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
	 * 增加期权
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
		    throw new ServiceException("创建期权时定位错误");
		 }catch(CreateException e) {
			    LogUtility.log(clazz, LogLevel.ERROR, "create",e);
			    throw new ServiceException("创建期权时时错误");		    			
		 }	
	}
/**
 * 取消期权
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
		    throw new ServiceException("取消期权时定位错误");
		 }catch(FinderException e1) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create", e1);
		    throw new ServiceException("取消期权时时错误");		    
		}
	}
	/**
	 * 兑现
	 * @param dto
	 * @throws ServiceException
	 */
	public static FutureRight grantFutureRight(FutureRightDTO dto) throws ServiceException{

		try{
			//兑现期权
			FutureRightHome futureRightHome = HomeLocater.getFutureRightHome();
			FutureRight  futureRight =futureRightHome.findByPrimaryKey(new Integer(dto.getSeqNo()));
			futureRight.setAccountId(dto.getAccountID());
			futureRight.setExcuteOpId(dto.getExcuteOpID());
			futureRight.setExcuteOrgId(dto.getExcuteOrgID());
			futureRight.setStatus(CommonConstDefinition.FUTURERIGHT_RESULT_X);
			futureRight.setExcuteDate(TimestampUtility.getCurrentTimestamp());
			futureRight.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			dto.setValue(futureRight.getValue());
			
			//创建费用支付
			PaymentRecordDTO prDTO = new PaymentRecordDTO();
			prePaymentForFutureRight(prDTO,dto);
			
			return futureRight;
		}catch(HomeFactoryException ex) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create",ex);
		    throw new ServiceException("取消期权时定位错误");
		 }catch(FinderException e1) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create", e1);
		    throw new ServiceException("取消期权时时错误");		    
		 }
	}
	
    /**
     * 创建费用支付
     * @param csiDto
     * @param csiPaymentList
     * @param operatorID
     * @throws ServiceException
     */
    public static void prePaymentForFutureRight(PaymentRecordDTO paymentRecordDTO,FutureRightDTO dto)throws  ServiceException{
    	try{
    		PaymentRecordHome paymentRecordHome=HomeLocater.getPaymentRecordHome();
    		AccountHome accountHome=HomeLocater.getAccountHome();

			paymentRecordDTO.setMopID(3);   //待定
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
			//更新账户
    		Account account=accountHome.findByPrimaryKey(new Integer(dto.getAccountID()));
    		account.setTokenDeposit(BusinessUtility.doubleFormat(dto.getValue()+account.getTokenDeposit()));
    		//account.setTokenDeposit((double)(Math.floor(dto.getValue()*100+account.getTokenDeposit()*100+0.01)/100));
    		
	    }catch(HomeFactoryException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("为期权创建费用支付时定位错误");	            		
		}catch(FinderException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("为期权创建费用支付时查找错误");
		}catch(CreateException e) {
		    LogUtility.log(FeeService.class,LogLevel.ERROR,"encapsulateProductInfo",e);
		    throw new ServiceException("为期权创建费用支付时创建记录错误");
		}
    }
    
    /**
     * 用来处理对费用是否立即支付的问题
     * @param type
     * @param payFlag
     * @param feeLlist
     * @param paymentList
     * @return
     * @throws ServiceException
     */
    public static boolean mustCreateFinancesetOffMap(String type,boolean mustPay,Collection  feeLlist,Collection   paymentList ,Collection preductiionList)throws ServiceException{
    	String payOption=BusinessUtility.getCsiPaymentOption(type);
    	//立即支付
    	if(CommonConstDefinition.CSI_PAYMENT_OPTION_IM.equals(payOption)){
    		//受理单费用支付
    		double totoalPay=BusinessUtility.doubleFormat(countPayment(paymentList)+countPreDeduction(preductiionList));
    		//double totoalPay=(double)(Math.floor(countPayment(paymentList)*100+countPreDeduction(preductiionList)*100+0.01)/100);
    		//预存支付
    		double totalPrePay=countPrePayment(paymentList);
    		if(feeLlist==null||feeLlist.isEmpty()){
    			//if(isExitPaymentForBusniess(paymentList,preductiionList)&& totoalPay!=0.0f)
    			if(isExitPaymentForBusniess(paymentList,preductiionList)&& Math.abs(totoalPay)>0.0001)
    				throw new ServiceException("没有需要支付的费用记录");	
    		}else{
    			if(!isExitPaymentForBusniess(paymentList,preductiionList)&& (countFeeByFeeType(feeLlist,false)!=0))
    				throw new ServiceException("费用必须支付");
   			
    			if(countFeeByFeeType(feeLlist,false)!=totoalPay )
    				throw new ServiceException("支付和抵扣总额不等于费用总额");
    			if(countFeeByFeeType(feeLlist,true)>totalPrePay )
    				throw new ServiceException("预存总额必须大于强制预存费用总额");
    		}
    		
    	//可选择
    	}else if(CommonConstDefinition.CSI_PAYMENT_OPTION_OP.equals(payOption)){
    		double totoalPay=BusinessUtility.doubleFormat(countPayment(paymentList)+countPreDeduction(preductiionList));
    		//double totoalPay=(double)(Math.floor(countPayment(paymentList)*100+countPreDeduction(preductiionList)*100+0.01)/100);
    		//预存支付
    		double totalPrePay=countPrePayment(paymentList);
    		if(feeLlist==null||feeLlist.isEmpty()){
    			//if(isExitPaymentForBusniess(paymentList,preductiionList)&& totoalPay!=0.0f)
    			if(isExitPaymentForBusniess(paymentList,preductiionList)&& Math.abs(totoalPay)>0.0001)
    				throw new ServiceException("没有需要支付的费用记录");	
    		}else{
    			if(!isExitPaymentForBusniess(paymentList,preductiionList))
    				return false;
    			if(countFeeByFeeType(feeLlist,false)!=totoalPay )
    				throw new ServiceException("支付和抵扣总额不等于费用总额");
    			if(countFeeByFeeType(feeLlist,true)>totalPrePay )
    				throw new ServiceException("预存总额必须大于强制预存费用总额");
    		}
    	//在月度帐单中支付
    	}else if(CommonConstDefinition.CSI_PAYMENT_OPTION_IV.equals(payOption)){
    		if(isExitPaymentForBusniess(paymentList,preductiionList))
				throw new ServiceException("本次在月度帐单中支付,不需要支付");
    		return false;
    	//上门安装成功后收取
    	}else if(CommonConstDefinition.CSI_PAYMENT_OPTION_SP.equals(payOption)){
    		double totoalPay=BusinessUtility.doubleFormat(countPayment(paymentList)+countPreDeduction(preductiionList));
    		//double totoalPay=(double)(Math.floor(countPayment(paymentList)*100+countPreDeduction(preductiionList)*100+0.01)/100);
    		//预存支付
    		double totalPrePay=countPrePayment(paymentList);
    		if(feeLlist==null||feeLlist.isEmpty()){
    			//if(isExitPaymentForBusniess(paymentList,preductiionList)&& totoalPay!=0.0f)
    			if(isExitPaymentForBusniess(paymentList,preductiionList)&& Math.abs(totoalPay)>0.0001)
    				throw new ServiceException("没有需要支付的费用记录");	
    		}else{
    			if(mustPay){
	    			if(!isExitPaymentForBusniess(paymentList,preductiionList)&& (countFeeByFeeType(feeLlist,false)!=0))
	    				throw new ServiceException("费用必须支付");
	    			//if(countFeeByFeeType(feeLlist,false)!=totoalPay )
	    			if(Math.abs(countFeeByFeeType(feeLlist,false) - totoalPay) > 0.0001)
	    				throw new ServiceException("支付和抵扣总额不等于费用总额");
	    			if(countFeeByFeeType(feeLlist,true)>totalPrePay )
	    				throw new ServiceException("预存总额必须大于强制预存费用总额");
    			}else{
    				return false;
    			}
    		}
    	}else{
    		throw new ServiceException("取支付配置错误");
    	}
    	return true;
    }
    /**
     * 用来判断是否存在支付或者是预存抵扣
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
     * 合计费用的总额
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
     * 区分费用和强制预存的费用合计总额
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
     * 合计支付的总额(只合计支付受理费用和支付帐单的费用综合)
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
     * 合计支付的总额(只合计支付受理费用和支付帐单的费用综合)
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
     * 合计支付中的现金或者虚拟货币预存金额
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
    			//合计现金预约金额或者合计虚拟货币预存金额
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
     * 合计预存抵扣总额
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
     * 根据受理单id检索到关联的客户促销和费用列表,根据客户促销的DateFrom和NbrDate来更新费用的Date1和Date2
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
			throw new ServiceException("检索客户促销和费用定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("检索客户促销信息");
		}catch(Throwable e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("未知错误");
			
		}
    }
    /**
     * 重新封装客户套餐续费时的费用列表.
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
     * 计算套餐取消相关费用.
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
    	//计算一次性费,
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
     * 把所有费用封装在同一格式中返回给前台
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
		//处理所有的费用加入列表中返回给前台处理。
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
System.out.println("取得缓存，保存在ImmediateCountFeeInfo中");

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
		支付退户、销户费用
		传入immediateCountFeeInfo集合
		liudi start
	*/
	public static void PayFee4Account(int operatorID,int orgID,Collection feeCol,boolean bCreateInvoice) throws ServiceException
	{
		if(feeCol == null) throw new ServiceException("传入的帐务数据为空");
		Iterator itObj = feeCol.iterator();
		while(itObj.hasNext())
		{
			ImmediateCountFeeInfo cntFee = (ImmediateCountFeeInfo) itObj.next();
			//校验逻辑			

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
			//if(fee != payment + deduction) throw new ServiceException("费用无法做平，请校验传入的支付与抵扣记录");
			if(Math.abs(fee - (payment + deduction))>0.0001) throw new ServiceException("费用无法做平，请校验传入的支付与抵扣记录");

			long cPrepay = getSumNewPrepayment(cntFee.getNewPaymentRecordList(),"C");
			long tPrepay = getSumNewPrepayment(cntFee.getNewPaymentRecordList(),"T");

System.out.println("cPrepay = " + cPrepay + ", tPrepay = " + tPrepay);

			long acDeduction = (long)(BusinessUtility.getPreDeposit(cntFee.getAccountid(),"C")*100);
			long atDeduction = (long)(BusinessUtility.getPreDeposit(cntFee.getAccountid(),"T")*100);

			if( cPrepay > acDeduction ) throw new ServiceException("内部帐户没有足够的现金用于支付");
			if( tPrepay > atDeduction ) throw new ServiceException("内部帐户没有足够的虚拟货币用于支付");

			long tDeduction = getNewDeduction(cntFee.getNewPrepayRecordList(),"T");
			long cDeduction = getNewDeduction(cntFee.getNewPrepayRecordList(),"C");

			update_account(cntFee.getAccountid(),cPrepay - cDeduction , tPrepay - tDeduction);
			save_all_data(feeCol);
			//产生账单
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
		//持久化记录	
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

System.out.println("开始执行SQL");
				//执行SQL语句
				if(oc.getSqlStmt() != null)
					if(!BusinessUtility.ExecuteSqlstmt(oc.getSqlStmt()))
						iExecuteStatus =2;
System.out.println("开始执行SQL"+oc.getSqlStmt());
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
		    			throw new ServiceException("费用查找定位错误");	            		
    				}catch(FinderException e) {
		    			LogUtility.log(FeeService.class,LogLevel.ERROR,"processSpCache",e);
		    			throw new ServiceException("费用查找错误");
    				}
					//更改refer费用状态
				}
			}
			String sql = "Update t_Sp_Operationcache Set DT_LASTMOD = SysTimeStamp,";
			if(iExecuteStatus == 1)
			{
				sql = sql + "ProcessStatus = 'S'";
				//执行成功
			}
			else if( (iExecuteStatus ==2) || (iExecuteStatus == 3) )
			{
				//SQL执行失败
				sql = sql + "ProcessStatus = 'F'";
			}
			sql = sql + " where seqNo =" +oc.getSeqNo();
			if(!BusinessUtility.ExecuteSqlstmt(sql))
				throw new ServiceException("更新失败");
			// oc 状态翻转,DT_LASTMOD更改
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
		前台传入抵扣记录,支付记录后,进行校验,并且返回校验后的预存款
	*/
	public static void getReturnDeposit(ImmediateCountFeeInfo icf) throws ServiceException
	{
		//校验 费用账户平
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

		//if(fee != payment + deduction) throw new ServiceException("费用无法做平，请校验传入的支付与抵扣记录");
		if(Math.abs(fee - (payment + deduction))>0.0001) throw new ServiceException("费用无法做平，请校验传入的支付与抵扣记录");
		//设置新Deposit返回给前台

		long tDeduction = getNewDeduction(icf.getNewPrepayRecordList(),"T");
		long cDeduction = getNewDeduction(icf.getNewPrepayRecordList(),"C");

		long acDeduction = (long)(icf.getPreCashDoposit()*100);
		long atDeduction = (long)(icf.getPreTokenDoposit()*100);
		
		if(tDeduction > atDeduction) throw new ServiceException("内部帐户没有足够的虚拟货币用于支付");
		if(cDeduction > acDeduction) throw new ServiceException("内部帐户没有足够的现金用于支付");

		icf.setPreCashDoposit( ((double)(acDeduction - cDeduction))/100.0f );
		icf.setPreTokenDoposit( ((double)(atDeduction - tDeduction))/100.0f );
	}
	/*
		持久化记录
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
		//持久化费用记录
		//持久化支付含预存
		//持久化抵扣记录
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
		立即产生帐单
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
	 * 用来把押金封装为费用
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
		//增加feeType
		acctItemDTO.setFeeType(BusinessUtility.getFeeTypeByItemTypeID(acctItemDTO.getAcctItemTypeID()));
		acctItemDTO.setValue(depositValue);
		acctItemDTO.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
		acctItemDTO.setForcedDepositFlag(CommonConstDefinition.YESNOFLAG_NO);
		return acctItemDTO;
	}


	/**
     * 计算套餐转换费用.
     * @param csiDto	//受理单对象
     * @param CustType	//客户类型
     * @param AcctType	//帐户类型
     * @param oldccid	//原套餐
     * @param newBoundleID	//新套餐
     * @param oldPackageID	//老产品包
     * @param newPackageID	//新产品包
	 * @param newProductCol	//新增的产品
	 * @param opID			//操作员ID
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

		if(oldccid <= 0) throw new ServiceException("客户套餐ID不允许为空！");
		if(newBundleID <= 0) throw new ServiceException("新套餐ID不允许为空");
		if(oldPackageID <= 0) throw new ServiceException("原产品包ID不允许为空");
		if(newPackageID <= 0) throw new ServiceException("目标产品包ID不允许为空");

		//旧套餐暂停
		String ccids = String.valueOf(oldccid);
		BatchNoDTO iBatchNo = new BatchNoDTO(0);
		Collection feeCol = InfoFeeImmediateBilling.callImmediateBillingCampaign(ccids,"T",opID,0,iBatchNo);
		Collection spCache = BusinessUtility.getSpProcessCache(iBatchNo);
		//新套餐预付
		//新产品费用
		//手续费
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
		//包装为ImmediateCountFeeInfo返回
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
	 * 计算手工授予促销时候产生的费用.
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
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■calculateManualGrantCampaignFeeInfo  product2packageMap■■"+product2packageMap);

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
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■calculateManualGrantCampaignFeeInfo  package2ProductMap■■"+package2ProductMap);
		if(package2ProductMap==null||package2ProductMap.isEmpty()){
			bo.setSHasPackage("N");
			bo.setPackage2ProductMap(null);	
		}else{
			bo.setSHasPackage("Y");
			bo.setPackage2ProductMap(package2ProductMap);	
		}
		Collection billingObjectCol	= new ArrayList();
		billingObjectCol.add(bo);
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■calculateManualGrantCampaignFeeInfo  billingObjectCol■■"+billingObjectCol);
		ImmediateFeeList imf;
		imf = ImmediateFeeCalculator.getDefaultImmediateFeeCalculator().calculateImmediateFee(csi,billingObjectCol);

		
		ImmediateCountFeeInfo icf = new ImmediateCountFeeInfo();
		icf.setAcctItemList(imf.getAcctItemList());
		//包装为ImmediateCountFeeInfo返回
		double countFee=countFee(icf.getAcctItemList());
		icf.setTotalValueAccountItem(countFee);

		AccountDTO   accountDTO=BusinessUtility.getAcctDTOByAcctID(csi.getAccountID());
		icf.setAccountid(csi.getAccountID());
		icf.setAccountName(accountDTO.getAccountName());
		icf.setPreCashDoposit(accountDTO.getCashDeposit());
		icf.setPreTokenDoposit(accountDTO.getTokenDepositID());
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■calculateManualGrantCampaignFeeInfo  ImmediateCountFeeInfo■■"+icf);

		return icf;
	}
	/**
	 * 封装一笔设备退费记录
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
	 * 预计费（这里用来给前台调用的）
	 * @param csiid
	 * @param operatorID
	 * @throws ServiceException
	 */
	public static void preCalculateFeeInfo(int csiid ,int operatorID)throws  ServiceException{
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■preCalculateFeeInfo  csiid■■"+csiid);
		LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■preCalculateFeeInfo  operatorID■■"+operatorID);
		//进行预计费处理，计算费用
        Collection psidList=BusinessUtility.getPsIDListbyCsiID(csiid);
        LogUtility.log(FeeService.class,LogLevel.DEBUG,"■■preCalculateFeeInfo  psidList■■"+psidList);
		BatchNoDTO iBatchNo = new BatchNoDTO(0); //这里不需要补退费，所以预计后没有必要执行存储过程缓存的数据
        Collection infoFeeList=FeeService.calculateInfoFee(psidList,operatorID,"X","Y",iBatchNo);
	}

}
