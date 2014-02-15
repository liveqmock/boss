package com.dtv.oss.service.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.Account;
import com.dtv.oss.domain.AccountHome;
import com.dtv.oss.domain.AccountItem;
import com.dtv.oss.domain.AccountItemHome;
import com.dtv.oss.domain.FinanceSetOffMap;
import com.dtv.oss.domain.FinanceSetOffMapHome;
import com.dtv.oss.domain.PaymentRecord;
import com.dtv.oss.domain.PaymentRecordHome;
import com.dtv.oss.domain.PrepaymentDeductionRecord;
import com.dtv.oss.domain.PrepaymentDeductionRecordHome;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.util.TimestampUtility;

/**
 * <p>Title: 付费接口的公共类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: DTV</p>
 * @author Wesley
 * @version 1.0
 */
public class PayFeeUtil
{
	private final static Class clazz = PayFeeUtil.class;
	public PayFeeUtil()
	{
	}
    /**
     * 批量更新帐户的余额（杨晨追加）
     * @param paymentCol
     * @throws ServiceException
     */
    public static void updatePrepaymentList(Collection paymentCol)throws ServiceException{
	    //更新帐户预存余额
	    if(paymentCol!=null && !paymentCol.isEmpty()){
	    	Iterator paymentIter=paymentCol.iterator();
	    	while(paymentIter.hasNext()){
	    		PaymentRecordDTO PayDTO=(PaymentRecordDTO)paymentIter.next();
				if(PayDTO.getAmount() == 0.0f)continue;
				updatePrepayment(PayDTO);
	    	}
	    }
    }
    /**
     * 批量更新帐户的余额根据预存抵扣列表（杨晨追加）
     * @param paymentCol
     * @throws ServiceException
     */
    public static void updatePrepaymentDuctionList(Collection paymentCol)throws ServiceException{
	    //更新帐户预存余额
	    if(paymentCol!=null && !paymentCol.isEmpty()){
	    	Iterator paymentIter=paymentCol.iterator();
	    	while(paymentIter.hasNext()){
	    		PrepaymentDeductionRecordDTO prePaymentDto=(PrepaymentDeductionRecordDTO)paymentIter.next();
	    		//更新预存款账户
				if(prePaymentDto.getAmount() == 0.0f)continue;
	    		updateAccount(prePaymentDto.getAcctId()
	    					  ,-prePaymentDto.getAmount()
	    					  ,prePaymentDto.getPrepaymentType());
	    	}
	    }
    }
    
    public static void updateAccountItemStatusList(Collection accountItemCol)throws ServiceException{
    	if (accountItemCol!=null && !accountItemCol.isEmpty()){
    		Iterator accountItemIter =accountItemCol.iterator();
    		while(accountItemIter.hasNext()){
    			AccountItemDTO accountItemDto =(AccountItemDTO)accountItemIter.next();
    			//更新费用的状态
				if(accountItemDto.getValue() == 0.0f)continue;
    			updateAcctItemStatus(accountItemDto,true);
    		}
    	}
    }
    
    
	/**
	 *直接通过传入的支付对象来更新预存款账户
	 * @param paymentDto  支付记录DTO
	 * @throws SQLException
	 * @throws ServiceException
	 */
	public static void updatePrepayment(PaymentRecordDTO paymentDto)
		throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"updatePrepayment(paymentDto)",paymentDto);
		//只有支付方式是“预存”才需要更新帐户余额
		if(!CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType())&&
		   !CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE.equals(paymentDto.getPayType()))
		    return;
		//更新预存款账户
		updateAccount(paymentDto.getAcctID()
					  ,paymentDto.getAmount()
					  ,paymentDto.getPrepaymentType());
	}
	
	/**
	 * 更新帐户信息
	 * @param accountID
	 * @param prePayment
	 * @param prePayType
	 * @throws ServiceException
	 */
	public static void updateAccount(int accountID,double prePayment,String prePayType)throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"进入更新帐户信息：");
		if(String.valueOf(accountID) == null ||
			String.valueOf(accountID).trim().length() == 0){
			throw new ServiceException("参数错误：帐户序列号为空！");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"更新帐户信息，传入的帐户号为：" +accountID);
			AccountHome acctHome = HomeLocater.getAccountHome();
			Account acct = acctHome.findByPrimaryKey(new Integer(accountID));
			//修改帐户信息
			//如果是现金预存款，则更新现金预存帐户余额
			if(CommonConstDefinition.PREPAYMENTTYPE_CASH.equals(prePayType))
				acct.setCashDeposit(BusinessUtility.doubleFormat(acct.getCashDeposit() + prePayment));
				//acct.setCashDeposit((double)(Math.floor(acct.getCashDeposit()*100 + prePayment*100+0.01)/100));
			//如果是虚拟货币预存款，则更新虚拟货币预存帐户余额
			if(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY.equals(prePayType))
				acct.setTokenDeposit(BusinessUtility.doubleFormat(acct.getTokenDeposit() + prePayment));
				//acct.setTokenDeposit((double)(Math.floor(acct.getTokenDeposit()*100 + prePayment*100+0.01)/100));
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改帐户信息出错，原因：帐户定位出错！");
			throw new ServiceException("更改帐户信息出错，原因：帐户定位出错！");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改帐户信息出错，原因：帐户查找出错！");
			throw new ServiceException("更改帐户信息出错，原因：帐户查找出错");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束更新帐户信息！");
	}

	/**
	 * 更新帐户信息
	 * @param accountID 账户号
	 * @throws ServiceException
	 */
	public static void updateAccount(int accountID)throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"进入更新帐户信息：");
		if(String.valueOf(accountID) == null ||
			String.valueOf(accountID).trim().length() == 0){
			throw new ServiceException("参数错误：帐户序列号为空！");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"更新帐户信息，传入的帐户号为：" +accountID);
			AccountHome acctHome = HomeLocater.getAccountHome();
			Account acct = acctHome.findByPrimaryKey(new Integer(accountID));
			//修改帐户信息
			//如果账户状态置为“关闭”
			acct.setStatus(CommonConstDefinition.ACCOUNT_STATUS_CLOSE);
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改帐户信息出错，原因：帐户定位出错！");
			throw new ServiceException("更改帐户信息出错，原因：帐户定位出错！");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改帐户信息出错，原因：帐户查找出错！");
			throw new ServiceException("更改帐户信息出错，原因：帐户查找出错");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束更新帐户信息！");
	}

	/*
		更新归帐标志
	*/
	public static void updateAcctItemInvoiced(int aiNo,String invoicedFlag,int invoiceNo)
	throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"进入更新费用记录入账信息：");
		if(String.valueOf(aiNo) == null || aiNo == 0){
			throw new ServiceException("参数错误：费用记录号为空！");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"更新费用记录信息，传入的费用记录号为：" +aiNo);
			AccountItemHome accItemHome = HomeLocater.getAccountItemHome();
			AccountItem accItem = accItemHome.findByPrimaryKey(new Integer(aiNo));
            
			accItem.setInvoiceFlag(invoicedFlag);
			accItem.setInvoiceNO(invoiceNo);
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
	/*
		更新归帐标志
	*/
	public static void updatePaymentRecordInvoiced(int prID,String invoicedFlag,int invoiceNo)
	throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"进入支付记录入账信息：");
		if(String.valueOf(prID) == null || prID == 0){
			throw new ServiceException("参数错误：支付记录号为空！");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"更新支付记录信息，传入的支付记录号为：" +prID);
			PaymentRecordHome paymentHome = HomeLocater.getPaymentRecordHome();
			PaymentRecord paymentRecord = paymentHome.findByPrimaryKey(new Integer(prID));
            
			paymentRecord.setInvoicedFlag(invoicedFlag);
			paymentRecord.setInvoiceNo(invoiceNo);
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改支付记录信息出错，原因：支付记录定位出错！");
			throw new ServiceException("更改支付记录信息出错，原因：支付记录定位出错！");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改支付记录信息出错，原因：支付记录查找出错！");
			throw new ServiceException("更改支付记录信息出错，原因：支付记录查找出错");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束支付记录信息！");
	}

	/*
		更新归帐标志
	*/
	public static void updateDeductionRecordInvoiced(int deductionID,String invoicedFlag,int invoiceNo)
	throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"进入抵扣记录入账信息：");
		if(String.valueOf(deductionID) == null || deductionID == 0){
			throw new ServiceException("参数错误：抵扣记录号为空！");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"更新抵扣记录信息，传入的抵扣记录号为：" +deductionID);
			PrepaymentDeductionRecordHome deductionHome = HomeLocater.getPrepaymentDeductionRecordHome();
			PrepaymentDeductionRecord deduction = deductionHome.findByPrimaryKey(new Integer(deductionID));
            
			deduction.setInvoicedFlag(invoicedFlag);
			deduction.setInvoiceNo(invoiceNo);
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改抵扣记录信息出错，原因：抵扣记录定位出错！");
			throw new ServiceException("更改抵扣记录信息出错，原因：抵扣记录定位出错！");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改抵扣记录信息出错，原因：抵扣记录查找出错！");
			throw new ServiceException("更改抵扣记录信息出错，原因：抵扣记录查找出错");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束抵扣记录信息！");
	}
	/**
	 * 更新费用记录状态信息
	 * @param aiNO 费用记录号
	 * @param accountid 账户号
	 * @throws ServiceException
	 */
	public static void updateAcctItemStatus(int aiNO ,int accountid)throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"进入更新费用记录信息：");
		if(String.valueOf(aiNO) == null || aiNO == 0){
			throw new ServiceException("参数错误：费用记录号为空！");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"更新费用记录信息，传入的费用记录号为：" +aiNO);
			AccountHome accHome = HomeLocater.getAccountHome();
			Account acc = accHome.findByPrimaryKey(new Integer(accountid));
			AccountItemHome accItemHome = HomeLocater.getAccountItemHome();
			AccountItem accItem = accItemHome.findByPrimaryKey(new Integer(aiNO));
            
			//修改费用记录为“有效”
			accItem.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			accItem.setAcctID(accountid);
			accItem.setDate1(TimestampUtility.getCurrentDate());

//rem by liudi -- 不需要设置默认的计费周期。

	//	    getBillingCycleID(accItem);
			//accItem.setBillingCycleID(acc.getBillingCycleTypeID());
			accItem.setSetOffAmount(accItem.getValue());
			accItem.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
			accItem.setDtLastmod(TimestampUtility.getCurrentTimestamp());
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
	 * 更新费用记录状态信息
	 * @param dto 费用记录DTO
	 * @throws ServiceException
	 */
	public static void updateAcctItemStatus(AccountItemDTO dto,boolean statusFlag)throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"进入更新费用记录信息：SSS");
		if(String.valueOf(dto.getAiNO()) == null || dto.getAiNO() == 0){
			throw new ServiceException("参数错误：费用记录号为空！");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"更新费用记录信息，传入的费用记录号为：SSS" +dto.getAiNO());
			AccountItemHome accItemHome = HomeLocater.getAccountItemHome();
			AccountItem accItem = accItemHome.findByPrimaryKey(new Integer(dto.getAiNO()));
			//取得费用记录的计费周期
//remby liudi 不需要设置默认的计费周期值
//            getBillingCycleID(accItem);
			 
            if (statusFlag) accItem.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
            accItem.setSetOffAmount(dto.getSetOffAmount());
			accItem.setSetOffFlag(dto.getSetOffFlag());
            if(dto.getSetOffAmount() == 0)
				accItem.setSetOffAmount(accItem.getValue());
			if(dto.getSetOffFlag() == null || "".equals(dto.getSetOffFlag()))
				accItem.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);		
			accItem.setDtLastmod(TimestampUtility.getCurrentTimestamp());
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
	 * 更新支付记录状态信息
	 * @param seqNO 支付记录号
	 * @throws ServiceException
	 */
	private static void updatePaymentStatus(int seqNO,String status)throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"进入更新支付记录信息：");
		if(String.valueOf(seqNO) == null || seqNO == 0){
			throw new ServiceException("参数错误：支付记录号为空！");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"更新支付记录信息，传入的支付记录号为：" +seqNO);
			PaymentRecordHome paymentHome = HomeLocater.getPaymentRecordHome();
			PaymentRecord payment = paymentHome.findByPrimaryKey(new Integer(seqNO));
			//修改支付记录为“有效”
			payment.setStatus(status);
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改支付记录信息出错，原因：支付记录定位出错！");
			throw new ServiceException("更改支付记录信息出错，原因：支付记录定位出错！");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改支付记录信息出错，原因：支付记录查找出错！");
			throw new ServiceException("更改支付记录信息出错，原因：支付记录查找出错");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束更新支付记录信息！");
	}

	/**
	 * 更新销帐明细记录状态信息
	 * @param seqNO 支付记录号
	 * @throws ServiceException
	 */
	private static void updateFinanceMapStatus(int seqNO,String status)throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"进入更新销帐明细记录信息：");
		if(String.valueOf(seqNO) == null || seqNO == 0){
			throw new ServiceException("参数错误：销帐明细对应的支付记录号为空！");
		}
		try
		{
			ArrayList finance = null;
			LogUtility.log(clazz,LogLevel.DEBUG,"更新销帐明细记录信息，对应的支付记录号为：" +seqNO);
			FinanceSetOffMapHome financeHome = HomeLocater.getFinanceSetOffMapHome();
			Collection financeColl = financeHome.findByPlusReferId(seqNO);
			LogUtility.log(clazz,LogLevel.DEBUG,"financeColl.size()：" +financeColl.size());
			if(financeColl != null && financeColl.size() > 0)
				finance = (ArrayList)financeColl;
			if(finance == null || finance.size() == 0)
				return;
			LogUtility.log(clazz,LogLevel.DEBUG,"销帐列表：" +finance);
			for(int i=0; i<finance.size(); i++)
			{
				LogUtility.log(clazz,LogLevel.DEBUG,"finance.size()：" +finance.size());
				FinanceSetOffMap financeMap = (FinanceSetOffMap)finance.get(i);
				LogUtility.log(clazz,LogLevel.DEBUG,"销帐记录：" +financeMap);
				//修改销帐明细记录为“有效”
				financeMap.setStatus(status);
				LogUtility.log(clazz,LogLevel.DEBUG,"销帐状态：" +financeMap.getStatus());
			}
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改销帐明细记录信息出错，原因：销帐明细记录定位出错！");
			throw new ServiceException("更改销帐明细记录信息出错，原因：销帐明细记录定位出错！");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改销帐明细记录信息出错，原因：销帐明细记录查找出错！");
			throw new ServiceException("更改销帐明细记录信息出错，原因：销帐明细记录查找出错");
		}
/*		catch(ServiceException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,e);
			throw new ServiceException("更改销帐明细记录信息出错，原因：销帐明细记录查找出错");
		}*/
		LogUtility.log(clazz,LogLevel.DEBUG,"结束更新销帐明细记录信息！");
	}

	/**
	 * 生成费用记录
	 * @throws ServiceException
	 * @return int
	 */
	public static int createAmountItemDTO(AccountItemDTO accItemDto)throws ServiceException
	{
		//费用记录序列号
		int aiNO = 0;
		//生成费用记录
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"在createAmountItemDTO创建费用记录信息开始");
			AccountItemHome accItemHome = HomeLocater.getAccountItemHome();
			LogUtility.log(clazz,LogLevel.DEBUG,"createAmountItemDTO",accItemDto);
			AccountItem accItem = accItemHome.create(accItemDto);
			//回置id
			aiNO = accItem.getAiNO().intValue();
			LogUtility.log(clazz,LogLevel.DEBUG,"在createAmountItemDTO创建费用记录信息结束");
		}catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createAmountItemDTO",e);
			throw new ServiceException("生成费用记录信息时定位错误");
		}catch(CreateException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createAmountItemDTO",e);
			throw new ServiceException("生成费用记录信息错误");
		}
		return aiNO;
	}

	/**
	 * 生成支付记录
	 * @throws ServiceException
	 * @return int
	 */
	public static int createPaymentRecordDTO(PaymentRecordDTO paymentDto)throws ServiceException
	{
		int payNO = 0;
		//生成支付记录
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"在createPaymentRecordDTO创建支付记录信息开始");
			PaymentRecordHome paymentHome = HomeLocater.getPaymentRecordHome();
			LogUtility.log(clazz,LogLevel.DEBUG,"createPaymentRecordDTO",paymentDto);
			PaymentRecord payment = paymentHome.create(paymentDto);
			//回置id
			payNO = payment.getSeqNo().intValue();
			LogUtility.log(clazz,LogLevel.DEBUG,"在createPaymentRecordDTO创建支付记录信息结束");
		}catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createPaymentRecordDTO",e);
			throw new ServiceException("生成支付记录信息时定位错误");
		}catch(CreateException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createPaymentRecordDTO",e);
			throw new ServiceException("生成支付记录信息错误");
		}
		return payNO;
	}

	/**
	 * 生成预存抵扣记录
	 * @throws ServiceException
	 */
	public static int createPrePaymentDeductionRecordDTO(PrepaymentDeductionRecordDTO prePaymentDto)throws ServiceException
	{
		int prePayNO = 0;
		//生成预存抵扣记录
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"在createPrePaymentDeductionRecordDTO创建预存抵扣记录信息开始");
			PrepaymentDeductionRecordHome prePaymentHome = HomeLocater.getPrepaymentDeductionRecordHome();
			LogUtility.log(clazz,LogLevel.DEBUG,"createPrePaymentDeductionRecordDTO",prePaymentDto);
			PrepaymentDeductionRecord prePayment = prePaymentHome.create(prePaymentDto);
			//回置id
			prePayNO = prePayment.getSeqNo().intValue();
			LogUtility.log(clazz,LogLevel.DEBUG,"在createPrePaymentDeductionRecordDTO创建预存抵扣记录信息结束");
		}catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createPrePaymentDeductionRecordDTO",e);
			throw new ServiceException("生成预存抵扣记录信息时定位错误");
		}catch(CreateException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createPrePaymentDeductionRecordDTO",e);
			throw new ServiceException("生成预存抵扣记录信息错误");
		}
		return prePayNO;
	}

	/**
	 * 拷贝预存款抵扣列表，并添加虚拟主键值
	 * @param prePaymentDto 预存抵扣记录
	 * @return PrepaymentDeductionRecordDTO
	 */
	public static PrepaymentDeductionRecordDTO getPrePaymentDto(PrepaymentDeductionRecordDTO prePaymentDto)
	{
		PrepaymentDeductionRecordDTO prePaymentDto1 = new PrepaymentDeductionRecordDTO();
		prePaymentDto1.setSeqNo(prePaymentDto.getSeqNo());
		prePaymentDto1.setAcctId(prePaymentDto.getAcctId());
		prePaymentDto1.setAmount(prePaymentDto.getAmount());
		prePaymentDto1.setCreatingMethod(prePaymentDto.getCreatingMethod());
		prePaymentDto1.setCreateTime(prePaymentDto.getCreateTime());
		prePaymentDto1.setCustId(prePaymentDto.getCustId());
		prePaymentDto1.setDtCreate(prePaymentDto.getDtLastmod());
		prePaymentDto1.setDtLastmod(prePaymentDto.getDtLastmod());
		prePaymentDto1.setInvoicedFlag(prePaymentDto.getInvoicedFlag());
		prePaymentDto1.setInvoiceNo(prePaymentDto.getInvoiceNo());
		prePaymentDto1.setOpId(prePaymentDto.getOpId());
		prePaymentDto1.setOrgId(prePaymentDto.getOrgId());
		prePaymentDto1.setReferRecordId(prePaymentDto.getReferRecordId());
		prePaymentDto1.setReferRecordType(prePaymentDto.getReferRecordType());
		prePaymentDto1.setPrepaymentType(prePaymentDto.getPrepaymentType());
		prePaymentDto1.setStatus(prePaymentDto.getStatus());
		LogUtility.log(clazz,LogLevel.DEBUG,"getPrePaymentDto",prePaymentDto1);
		return prePaymentDto1;
	}
	
	//add by david.Yang
	private static void updateAcctItemStatus(int aiNO ,String status)throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"进入更新费用记录信息：");
		if(String.valueOf(aiNO) == null || aiNO == 0){
			throw new ServiceException("参数错误：费用记录号为空！");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"更新费用记录信息，传入的费用记录号为：" +aiNO);
			AccountItemHome accItemHome = HomeLocater.getAccountItemHome();
			AccountItem accItem = accItemHome.findByPrimaryKey(new Integer(aiNO));
            
			accItem.setStatus(status);
			accItem.setDtLastmod(TimestampUtility.getCurrentTimestamp());
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
	
	
}
