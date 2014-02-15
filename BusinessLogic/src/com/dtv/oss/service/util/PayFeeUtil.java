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
 * <p>Title: ���ѽӿڵĹ�����</p>
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
     * ���������ʻ������׷�ӣ�
     * @param paymentCol
     * @throws ServiceException
     */
    public static void updatePrepaymentList(Collection paymentCol)throws ServiceException{
	    //�����ʻ�Ԥ�����
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
     * ���������ʻ���������Ԥ��ֿ��б��׷�ӣ�
     * @param paymentCol
     * @throws ServiceException
     */
    public static void updatePrepaymentDuctionList(Collection paymentCol)throws ServiceException{
	    //�����ʻ�Ԥ�����
	    if(paymentCol!=null && !paymentCol.isEmpty()){
	    	Iterator paymentIter=paymentCol.iterator();
	    	while(paymentIter.hasNext()){
	    		PrepaymentDeductionRecordDTO prePaymentDto=(PrepaymentDeductionRecordDTO)paymentIter.next();
	    		//����Ԥ����˻�
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
    			//���·��õ�״̬
				if(accountItemDto.getValue() == 0.0f)continue;
    			updateAcctItemStatus(accountItemDto,true);
    		}
    	}
    }
    
    
	/**
	 *ֱ��ͨ�������֧������������Ԥ����˻�
	 * @param paymentDto  ֧����¼DTO
	 * @throws SQLException
	 * @throws ServiceException
	 */
	public static void updatePrepayment(PaymentRecordDTO paymentDto)
		throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"updatePrepayment(paymentDto)",paymentDto);
		//ֻ��֧����ʽ�ǡ�Ԥ�桱����Ҫ�����ʻ����
		if(!CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType())&&
		   !CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE.equals(paymentDto.getPayType()))
		    return;
		//����Ԥ����˻�
		updateAccount(paymentDto.getAcctID()
					  ,paymentDto.getAmount()
					  ,paymentDto.getPrepaymentType());
	}
	
	/**
	 * �����ʻ���Ϣ
	 * @param accountID
	 * @param prePayment
	 * @param prePayType
	 * @throws ServiceException
	 */
	public static void updateAccount(int accountID,double prePayment,String prePayType)throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"��������ʻ���Ϣ��");
		if(String.valueOf(accountID) == null ||
			String.valueOf(accountID).trim().length() == 0){
			throw new ServiceException("���������ʻ����к�Ϊ�գ�");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"�����ʻ���Ϣ��������ʻ���Ϊ��" +accountID);
			AccountHome acctHome = HomeLocater.getAccountHome();
			Account acct = acctHome.findByPrimaryKey(new Integer(accountID));
			//�޸��ʻ���Ϣ
			//������ֽ�Ԥ��������ֽ�Ԥ���ʻ����
			if(CommonConstDefinition.PREPAYMENTTYPE_CASH.equals(prePayType))
				acct.setCashDeposit(BusinessUtility.doubleFormat(acct.getCashDeposit() + prePayment));
				//acct.setCashDeposit((double)(Math.floor(acct.getCashDeposit()*100 + prePayment*100+0.01)/100));
			//������������Ԥ��������������Ԥ���ʻ����
			if(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY.equals(prePayType))
				acct.setTokenDeposit(BusinessUtility.doubleFormat(acct.getTokenDeposit() + prePayment));
				//acct.setTokenDeposit((double)(Math.floor(acct.getTokenDeposit()*100 + prePayment*100+0.01)/100));
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"�����ʻ���Ϣ����ԭ���ʻ���λ����");
			throw new ServiceException("�����ʻ���Ϣ����ԭ���ʻ���λ����");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"�����ʻ���Ϣ����ԭ���ʻ����ҳ���");
			throw new ServiceException("�����ʻ���Ϣ����ԭ���ʻ����ҳ���");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"���������ʻ���Ϣ��");
	}

	/**
	 * �����ʻ���Ϣ
	 * @param accountID �˻���
	 * @throws ServiceException
	 */
	public static void updateAccount(int accountID)throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"��������ʻ���Ϣ��");
		if(String.valueOf(accountID) == null ||
			String.valueOf(accountID).trim().length() == 0){
			throw new ServiceException("���������ʻ����к�Ϊ�գ�");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"�����ʻ���Ϣ��������ʻ���Ϊ��" +accountID);
			AccountHome acctHome = HomeLocater.getAccountHome();
			Account acct = acctHome.findByPrimaryKey(new Integer(accountID));
			//�޸��ʻ���Ϣ
			//����˻�״̬��Ϊ���رա�
			acct.setStatus(CommonConstDefinition.ACCOUNT_STATUS_CLOSE);
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"�����ʻ���Ϣ����ԭ���ʻ���λ����");
			throw new ServiceException("�����ʻ���Ϣ����ԭ���ʻ���λ����");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"�����ʻ���Ϣ����ԭ���ʻ����ҳ���");
			throw new ServiceException("�����ʻ���Ϣ����ԭ���ʻ����ҳ���");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"���������ʻ���Ϣ��");
	}

	/*
		���¹��ʱ�־
	*/
	public static void updateAcctItemInvoiced(int aiNo,String invoicedFlag,int invoiceNo)
	throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"������·��ü�¼������Ϣ��");
		if(String.valueOf(aiNo) == null || aiNo == 0){
			throw new ServiceException("�������󣺷��ü�¼��Ϊ�գ�");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"���·��ü�¼��Ϣ������ķ��ü�¼��Ϊ��" +aiNo);
			AccountItemHome accItemHome = HomeLocater.getAccountItemHome();
			AccountItem accItem = accItemHome.findByPrimaryKey(new Integer(aiNo));
            
			accItem.setInvoiceFlag(invoicedFlag);
			accItem.setInvoiceNO(invoiceNo);
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
	/*
		���¹��ʱ�־
	*/
	public static void updatePaymentRecordInvoiced(int prID,String invoicedFlag,int invoiceNo)
	throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"����֧����¼������Ϣ��");
		if(String.valueOf(prID) == null || prID == 0){
			throw new ServiceException("��������֧����¼��Ϊ�գ�");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"����֧����¼��Ϣ�������֧����¼��Ϊ��" +prID);
			PaymentRecordHome paymentHome = HomeLocater.getPaymentRecordHome();
			PaymentRecord paymentRecord = paymentHome.findByPrimaryKey(new Integer(prID));
            
			paymentRecord.setInvoicedFlag(invoicedFlag);
			paymentRecord.setInvoiceNo(invoiceNo);
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"����֧����¼��Ϣ����ԭ��֧����¼��λ����");
			throw new ServiceException("����֧����¼��Ϣ����ԭ��֧����¼��λ����");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"����֧����¼��Ϣ����ԭ��֧����¼���ҳ���");
			throw new ServiceException("����֧����¼��Ϣ����ԭ��֧����¼���ҳ���");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"����֧����¼��Ϣ��");
	}

	/*
		���¹��ʱ�־
	*/
	public static void updateDeductionRecordInvoiced(int deductionID,String invoicedFlag,int invoiceNo)
	throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"����ֿۼ�¼������Ϣ��");
		if(String.valueOf(deductionID) == null || deductionID == 0){
			throw new ServiceException("�������󣺵ֿۼ�¼��Ϊ�գ�");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"���µֿۼ�¼��Ϣ������ĵֿۼ�¼��Ϊ��" +deductionID);
			PrepaymentDeductionRecordHome deductionHome = HomeLocater.getPrepaymentDeductionRecordHome();
			PrepaymentDeductionRecord deduction = deductionHome.findByPrimaryKey(new Integer(deductionID));
            
			deduction.setInvoicedFlag(invoicedFlag);
			deduction.setInvoiceNo(invoiceNo);
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"���ĵֿۼ�¼��Ϣ����ԭ�򣺵ֿۼ�¼��λ����");
			throw new ServiceException("���ĵֿۼ�¼��Ϣ����ԭ�򣺵ֿۼ�¼��λ����");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"���ĵֿۼ�¼��Ϣ����ԭ�򣺵ֿۼ�¼���ҳ���");
			throw new ServiceException("���ĵֿۼ�¼��Ϣ����ԭ�򣺵ֿۼ�¼���ҳ���");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"�����ֿۼ�¼��Ϣ��");
	}
	/**
	 * ���·��ü�¼״̬��Ϣ
	 * @param aiNO ���ü�¼��
	 * @param accountid �˻���
	 * @throws ServiceException
	 */
	public static void updateAcctItemStatus(int aiNO ,int accountid)throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"������·��ü�¼��Ϣ��");
		if(String.valueOf(aiNO) == null || aiNO == 0){
			throw new ServiceException("�������󣺷��ü�¼��Ϊ�գ�");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"���·��ü�¼��Ϣ������ķ��ü�¼��Ϊ��" +aiNO);
			AccountHome accHome = HomeLocater.getAccountHome();
			Account acc = accHome.findByPrimaryKey(new Integer(accountid));
			AccountItemHome accItemHome = HomeLocater.getAccountItemHome();
			AccountItem accItem = accItemHome.findByPrimaryKey(new Integer(aiNO));
            
			//�޸ķ��ü�¼Ϊ����Ч��
			accItem.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			accItem.setAcctID(accountid);
			accItem.setDate1(TimestampUtility.getCurrentDate());

//rem by liudi -- ����Ҫ����Ĭ�ϵļƷ����ڡ�

	//	    getBillingCycleID(accItem);
			//accItem.setBillingCycleID(acc.getBillingCycleTypeID());
			accItem.setSetOffAmount(accItem.getValue());
			accItem.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
			accItem.setDtLastmod(TimestampUtility.getCurrentTimestamp());
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
	 * ���·��ü�¼״̬��Ϣ
	 * @param dto ���ü�¼DTO
	 * @throws ServiceException
	 */
	public static void updateAcctItemStatus(AccountItemDTO dto,boolean statusFlag)throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"������·��ü�¼��Ϣ��SSS");
		if(String.valueOf(dto.getAiNO()) == null || dto.getAiNO() == 0){
			throw new ServiceException("�������󣺷��ü�¼��Ϊ�գ�");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"���·��ü�¼��Ϣ������ķ��ü�¼��Ϊ��SSS" +dto.getAiNO());
			AccountItemHome accItemHome = HomeLocater.getAccountItemHome();
			AccountItem accItem = accItemHome.findByPrimaryKey(new Integer(dto.getAiNO()));
			//ȡ�÷��ü�¼�ļƷ�����
//remby liudi ����Ҫ����Ĭ�ϵļƷ�����ֵ
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
	 * ����֧����¼״̬��Ϣ
	 * @param seqNO ֧����¼��
	 * @throws ServiceException
	 */
	private static void updatePaymentStatus(int seqNO,String status)throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"�������֧����¼��Ϣ��");
		if(String.valueOf(seqNO) == null || seqNO == 0){
			throw new ServiceException("��������֧����¼��Ϊ�գ�");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"����֧����¼��Ϣ�������֧����¼��Ϊ��" +seqNO);
			PaymentRecordHome paymentHome = HomeLocater.getPaymentRecordHome();
			PaymentRecord payment = paymentHome.findByPrimaryKey(new Integer(seqNO));
			//�޸�֧����¼Ϊ����Ч��
			payment.setStatus(status);
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"����֧����¼��Ϣ����ԭ��֧����¼��λ����");
			throw new ServiceException("����֧����¼��Ϣ����ԭ��֧����¼��λ����");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"����֧����¼��Ϣ����ԭ��֧����¼���ҳ���");
			throw new ServiceException("����֧����¼��Ϣ����ԭ��֧����¼���ҳ���");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"��������֧����¼��Ϣ��");
	}

	/**
	 * ����������ϸ��¼״̬��Ϣ
	 * @param seqNO ֧����¼��
	 * @throws ServiceException
	 */
	private static void updateFinanceMapStatus(int seqNO,String status)throws ServiceException
	{
		LogUtility.log(clazz,LogLevel.DEBUG,"�������������ϸ��¼��Ϣ��");
		if(String.valueOf(seqNO) == null || seqNO == 0){
			throw new ServiceException("��������������ϸ��Ӧ��֧����¼��Ϊ�գ�");
		}
		try
		{
			ArrayList finance = null;
			LogUtility.log(clazz,LogLevel.DEBUG,"����������ϸ��¼��Ϣ����Ӧ��֧����¼��Ϊ��" +seqNO);
			FinanceSetOffMapHome financeHome = HomeLocater.getFinanceSetOffMapHome();
			Collection financeColl = financeHome.findByPlusReferId(seqNO);
			LogUtility.log(clazz,LogLevel.DEBUG,"financeColl.size()��" +financeColl.size());
			if(financeColl != null && financeColl.size() > 0)
				finance = (ArrayList)financeColl;
			if(finance == null || finance.size() == 0)
				return;
			LogUtility.log(clazz,LogLevel.DEBUG,"�����б�" +finance);
			for(int i=0; i<finance.size(); i++)
			{
				LogUtility.log(clazz,LogLevel.DEBUG,"finance.size()��" +finance.size());
				FinanceSetOffMap financeMap = (FinanceSetOffMap)finance.get(i);
				LogUtility.log(clazz,LogLevel.DEBUG,"���ʼ�¼��" +financeMap);
				//�޸�������ϸ��¼Ϊ����Ч��
				financeMap.setStatus(status);
				LogUtility.log(clazz,LogLevel.DEBUG,"����״̬��" +financeMap.getStatus());
			}
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"����������ϸ��¼��Ϣ����ԭ��������ϸ��¼��λ����");
			throw new ServiceException("����������ϸ��¼��Ϣ����ԭ��������ϸ��¼��λ����");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"����������ϸ��¼��Ϣ����ԭ��������ϸ��¼���ҳ���");
			throw new ServiceException("����������ϸ��¼��Ϣ����ԭ��������ϸ��¼���ҳ���");
		}
/*		catch(ServiceException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,e);
			throw new ServiceException("����������ϸ��¼��Ϣ����ԭ��������ϸ��¼���ҳ���");
		}*/
		LogUtility.log(clazz,LogLevel.DEBUG,"��������������ϸ��¼��Ϣ��");
	}

	/**
	 * ���ɷ��ü�¼
	 * @throws ServiceException
	 * @return int
	 */
	public static int createAmountItemDTO(AccountItemDTO accItemDto)throws ServiceException
	{
		//���ü�¼���к�
		int aiNO = 0;
		//���ɷ��ü�¼
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"��createAmountItemDTO�������ü�¼��Ϣ��ʼ");
			AccountItemHome accItemHome = HomeLocater.getAccountItemHome();
			LogUtility.log(clazz,LogLevel.DEBUG,"createAmountItemDTO",accItemDto);
			AccountItem accItem = accItemHome.create(accItemDto);
			//����id
			aiNO = accItem.getAiNO().intValue();
			LogUtility.log(clazz,LogLevel.DEBUG,"��createAmountItemDTO�������ü�¼��Ϣ����");
		}catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createAmountItemDTO",e);
			throw new ServiceException("���ɷ��ü�¼��Ϣʱ��λ����");
		}catch(CreateException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createAmountItemDTO",e);
			throw new ServiceException("���ɷ��ü�¼��Ϣ����");
		}
		return aiNO;
	}

	/**
	 * ����֧����¼
	 * @throws ServiceException
	 * @return int
	 */
	public static int createPaymentRecordDTO(PaymentRecordDTO paymentDto)throws ServiceException
	{
		int payNO = 0;
		//����֧����¼
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"��createPaymentRecordDTO����֧����¼��Ϣ��ʼ");
			PaymentRecordHome paymentHome = HomeLocater.getPaymentRecordHome();
			LogUtility.log(clazz,LogLevel.DEBUG,"createPaymentRecordDTO",paymentDto);
			PaymentRecord payment = paymentHome.create(paymentDto);
			//����id
			payNO = payment.getSeqNo().intValue();
			LogUtility.log(clazz,LogLevel.DEBUG,"��createPaymentRecordDTO����֧����¼��Ϣ����");
		}catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createPaymentRecordDTO",e);
			throw new ServiceException("����֧����¼��Ϣʱ��λ����");
		}catch(CreateException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createPaymentRecordDTO",e);
			throw new ServiceException("����֧����¼��Ϣ����");
		}
		return payNO;
	}

	/**
	 * ����Ԥ��ֿۼ�¼
	 * @throws ServiceException
	 */
	public static int createPrePaymentDeductionRecordDTO(PrepaymentDeductionRecordDTO prePaymentDto)throws ServiceException
	{
		int prePayNO = 0;
		//����Ԥ��ֿۼ�¼
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"��createPrePaymentDeductionRecordDTO����Ԥ��ֿۼ�¼��Ϣ��ʼ");
			PrepaymentDeductionRecordHome prePaymentHome = HomeLocater.getPrepaymentDeductionRecordHome();
			LogUtility.log(clazz,LogLevel.DEBUG,"createPrePaymentDeductionRecordDTO",prePaymentDto);
			PrepaymentDeductionRecord prePayment = prePaymentHome.create(prePaymentDto);
			//����id
			prePayNO = prePayment.getSeqNo().intValue();
			LogUtility.log(clazz,LogLevel.DEBUG,"��createPrePaymentDeductionRecordDTO����Ԥ��ֿۼ�¼��Ϣ����");
		}catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createPrePaymentDeductionRecordDTO",e);
			throw new ServiceException("����Ԥ��ֿۼ�¼��Ϣʱ��λ����");
		}catch(CreateException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createPrePaymentDeductionRecordDTO",e);
			throw new ServiceException("����Ԥ��ֿۼ�¼��Ϣ����");
		}
		return prePayNO;
	}

	/**
	 * ����Ԥ���ֿ��б��������������ֵ
	 * @param prePaymentDto Ԥ��ֿۼ�¼
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
		LogUtility.log(clazz,LogLevel.DEBUG,"������·��ü�¼��Ϣ��");
		if(String.valueOf(aiNO) == null || aiNO == 0){
			throw new ServiceException("�������󣺷��ü�¼��Ϊ�գ�");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"���·��ü�¼��Ϣ������ķ��ü�¼��Ϊ��" +aiNO);
			AccountItemHome accItemHome = HomeLocater.getAccountItemHome();
			AccountItem accItem = accItemHome.findByPrimaryKey(new Integer(aiNO));
            
			accItem.setStatus(status);
			accItem.setDtLastmod(TimestampUtility.getCurrentTimestamp());
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
	
	
}
