package com.dtv.oss.service.component;

import java.util.ArrayList;
import java.util.Collection;
import com.dtv.oss.dto.*;
import com.dtv.oss.domain.MethodOfPayment;
import com.dtv.oss.domain.MethodOfPaymentHome;
import com.dtv.oss.domain.FinanceSetOffMap;
import com.dtv.oss.domain.FinanceSetOffMapHome;
import com.dtv.oss.domain.PaymentRecord;
import com.dtv.oss.domain.PaymentRecordHome;
import com.dtv.oss.service.util.PayFeeUtil;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import javax.ejb.CreateException;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.domain.PrepaymentDeductionRecordHome;
import com.dtv.oss.domain.PrepaymentDeductionRecord;
import javax.ejb.FinderException;

/**
 * <p>Title: �������ʽӿ�</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: DTV</p>
 * @author Wesley
 * @version 1.0
 */

//���ฺ������һ���Է��õĸ������ʣ�������Ϣ��ǰ̨����
public class ImmediatePayFeeService extends AbstractService
{
    private final static int FEE_FLAG = 1;       //���ñ�ʶ
    private final static int PAY_FLAG = 2;       //֧����ʶ
	private final static int PREPAY_FLAG = 3;    //Ԥ��ֿ۱�ʶ
	private final static Class clazz = ImmediatePayFeeService.class;
    //�����б�(ArrayList of AccountItemDTO)
    private ArrayList feeList;
    //֧���б�(ArrayList of PaymentRecordDTO)
    private ArrayList paymentList;
	//�����б�(ArrayList of FinanceSetoffMapDTO)
	private ArrayList financeMapList;
    //Ԥ���ֿ�(ArrayList of  PrePaymentDeductionRecordDTO)
    private ArrayList prePaymentList;
    
    private ArrayList pAcctItem = new ArrayList();
    private ArrayList mAcctItem = new ArrayList();
    private ArrayList sAcctItem = new ArrayList();
    
    private ArrayList pPrePay = new ArrayList();
    private ArrayList mPrePay = new ArrayList();
    private ArrayList sPrePay = new ArrayList();
    
    private ArrayList pPayment = new ArrayList();
    private ArrayList mPayment = new ArrayList();
    private ArrayList sPayment = new ArrayList();
    
    

    private String type;
    private int id;
    
   
    //���췽��
    public ImmediatePayFeeService()
    {
		feeList = new ArrayList();
		paymentList = new ArrayList();
		financeMapList = new ArrayList();
		prePaymentList = new ArrayList();
	}
    public ImmediatePayFeeService(Collection feeList,Collection paymentList
								  ,Collection prePaymentList,Collection financeMapList)
    {
		this.feeList = (ArrayList)feeList;
		this.paymentList = (ArrayList)paymentList;
		this.financeMapList = (ArrayList)financeMapList;
		if(this.financeMapList == null)
			this.financeMapList = new ArrayList();
		this.prePaymentList = (ArrayList)prePaymentList;
		if(this.prePaymentList == null)
			this.prePaymentList = new ArrayList();
    }

	public void setFeeList(ArrayList feeList)
	{
		this.feeList = feeList;
	}
	public ArrayList getFeeList()
	{
		return feeList;
	}
	public void setPaymentList(ArrayList paymentList)
	{
		this.paymentList = paymentList;
	}
	public ArrayList getPaymentList()
	{
		return paymentList;
	}
	public void setFinanceMapList(ArrayList financeMapList)
	{
		this.financeMapList = financeMapList;
	}
	public ArrayList getFinanceMapList()
	{
		return financeMapList;
	}
	public void setPrePaymentList(ArrayList prePaymentList) {
		this.prePaymentList = prePaymentList;
	}
	public ArrayList getPrePaymentList() {
		return prePaymentList;
	}



        /*
		 * Command���ô˷���֧�ֵ���ʱ�������˹�ϵ return parameter @param ArrayList
		 * financeMapList //���á�֧�������б� @throws ServiceException
		 */
	public ArrayList AdjustSetOff() throws ServiceException {
/*		double amountFeeList[] = new double[2];
		if (feeList != null)
			amountFeeList = checkInfo(FEE_FLAG);
		double amountPayList[] = new double[2];
		if (paymentList != null)
			amountPayList = checkInfo(PAY_FLAG);
		double amountPrePayList[] = new double[2];
		    amountPrePayList = checkInfo(PREPAY_FLAG);

		if(prePaymentList.isEmpty())
		    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~there is no prepayment List ID = " + String.valueOf(id));

		double preFeeAmount = amountFeeList[0];
		double csiFeeAmount = amountFeeList[1];
		double prePayAmount = amountPrePayList[0];
		double csiPayAmount = amountPayList[1];
		
System.out.println("preFeeAmount = " + String.valueOf(preFeeAmount) + " csiFeeAmount = " + String.valueOf(csiFeeAmount) + 
		"prePayAmount = " + String.valueOf(prePayAmount) + "csiPayAmount = " + String.valueOf(csiPayAmount));		
		ArrayList financeMapList = new ArrayList();
		if (csiFeeAmount < -0.0002 || csiPayAmount >=0.0002
				|| prePayAmount >= 0.0002)
			financeMapList = createFinanceMapList();
		return financeMapList;*/
		return null;
	}

	private long getSumFee()
	{
		if( feeList == null ) return 0;
		long feeAmount = 0;
		for(int i=0; i<feeList.size(); i++)
		{
			AccountItemDTO accItemDto = (AccountItemDTO)feeList.get(i);	
			if(accItemDto.getFeeType().compareTo("P") != 0)
				feeAmount = feeAmount + double2long(accItemDto.getValue());
		}
		return feeAmount;
	}

	private long getSumForceFee()
	{
		if( feeList == null ) return 0;
		long feeAmount = 0;
		for(int i=0; i<feeList.size(); i++)
		{
			AccountItemDTO accItemDto = (AccountItemDTO)feeList.get(i);	
			if(accItemDto.getFeeType().compareTo("P") == 0)
				feeAmount = feeAmount + double2long(accItemDto.getValue());
		}
		return feeAmount;
	}

	private long getSumPrePayment()
	{
		if( paymentList == null ) return 0;
		long paymentAmount = 0;
		for(int i=0; i<paymentList.size(); i++)
		{
			PaymentRecordDTO paymentDto = (PaymentRecordDTO)paymentList.get(i);
			if( paymentDto.getPayType().compareTo("P")==0 || paymentDto.getPayType().compareTo("RR")==0)
				paymentAmount = paymentAmount + double2long(paymentDto.getAmount());
		}
		return paymentAmount;
	}

	private long getSumPaymentRecord()
	{
		if( paymentList == null ) return 0;
		long paymentAmount = 0;
		for(int i=0; i<paymentList.size(); i++)
		{
			PaymentRecordDTO paymentDto = (PaymentRecordDTO)paymentList.get(i);
			if( paymentDto.getPayType().compareTo("N")==0 || paymentDto.getPayType().compareTo("C")==0 || paymentDto.getPayType().compareTo("RF")==0)
				paymentAmount = paymentAmount + double2long(paymentDto.getAmount());
		}
		return paymentAmount;
	}
	private long getSumDeduction()
	{
		
		if( prePaymentList == null ) return 0;
		long deductionAmount = 0;
		for(int i=0; i<prePaymentList.size(); i++)
		{
			PrepaymentDeductionRecordDTO PrepaymentDto = (PrepaymentDeductionRecordDTO)prePaymentList.get(i);
			deductionAmount = deductionAmount + double2long(PrepaymentDto.getAmount());
		}
		return deductionAmount;
	}
	/*
	�������˱�֤����ƽ�⣺���� = ֧�� + �ֿ�
	*/
	public ArrayList payFee() throws ServiceException
	{
//2007-04-06����ǰ��ʱӦ�Բ���
//����ǰ̨ҳ�涪ʧ�˷�����Ϣ���������˽ӿ�У�鲿�ֲ����κ��ж�

if (true) return null;

System.out.println("feelist=" + feeList);
System.out.println("paymentList=" + paymentList);
System.out.println("deductionList=" + prePaymentList);
		//ȡ�÷�������
		long fee = getSumFee();
		//ȡ��֧������
		long payment = getSumPaymentRecord();
		//ȡ�õֿ�����
		long deduction = getSumDeduction();
		//У������ƽ��
		//if(fee != payment + deduction) throw new ServiceException("��������������֧������+�ֿ�����!");
		if(Math.abs(fee - (payment + deduction))>0.0001) throw new ServiceException("��������������֧������+�ֿ�����!");

		//У��Ԥ�桵ǿ��Ԥ��
		long forceFee = getSumForceFee();
		long prePay = getSumPrePayment();
System.out.println("fee =" + fee);
System.out.println("payment =" + payment);
System.out.println("deduction =" + deduction);
System.out.println("forceFee =" + forceFee);
System.out.println("prePay =" + prePay);
		if( forceFee > 0 ) //��ǿ��Ԥ��Ѵ���0������½���У��
			if(prePay < forceFee ) throw new ServiceException("Ԥ�������>=ǿ��Ԥ��");

		//���������˹�ϵ
		return null;
	}
	/**
	 * Command���ô˷���ʵ�ָ������ʹ�ϵ�Ľ��� return parameter
	 * 
	 * @param ArrayList
	 *            financeMapList //���á�֧�������б�
	 * @throws ServiceException
	 */
	public ArrayList payFeeDe()throws ServiceException
	{
		//У������б��Ƿ�Ϊ��
		if(feeList == null)
			throw new ServiceException("�����б���Ϊ�գ�");
		//У��֧���б��Ƿ�Ϊ��
		if(paymentList == null)
			throw new ServiceException("֧���б���Ϊ�գ�");
		/*
		 ���������б��ǿ��Ԥ��Ѻ�������
		 amountFeeList[0] = �ܵ�ǿ��Ԥ���
	     amountFeeList[1] = �ܵ���������
		*/
		double amountFeeList[] = new double[2];
		/*
		 ����֧���б��ǿ��Ԥ�渶�Ѻ���������
		 amountPayList[0] = �ܵ�Ԥ��֧����
	     amountPayList[1] = �ܵ�����֧��
		*/
		double amountPayList[] = new double[2];
		/*
		 ����Ԥ��ֿ��б������Ԥ��ֿ۷�
		 amountPayList[0] = �ܵ�Ԥ��ֿ�֧����
		*/
		double amountPrePayList[] = new double[2];
		/*
		 �Է����б��еĿͻ����ʻ���������Ϣ����У��
		 ���У��ͨ���������Ԥ���־�ֱ�õ������б��ܵ�ǿ��Ԥ��Ѻ�������
		*/
		amountFeeList = checkInfo(FEE_FLAG);
		/*
		 ��֧���б��еĿͻ����ʻ���������Ϣ����У��
		 ���У��ͨ���������֧�����ͷֱ�õ�֧���б��ܵ�Ԥ�渶�Ѻ���������
		*/
		amountPayList = checkInfo(PAY_FLAG);
		/*
		 ���Ԥ��ֿ��б�Ϊ�գ��Դ�ֿ��б��е���Ϣ����У��
		 ���У��ͨ������õ�Ԥ��ֿ��б��ܵ�Ԥ��ֿ���������
		*/
		amountPrePayList = checkInfo(PREPAY_FLAG);
		/*
		 �������б��е�ǿ��Ԥ���ܷ��á��������ú�
		 ֧���б��е�Ԥ��֧���ܶ����֧���ܶ���бȽ�
		*/
		double preFeeAmount = amountFeeList[0];
		double csiFeeAmount = amountFeeList[1];
		double prePayAmount = amountPayList[0];
		double csiPayAmount = amountPayList[1];
		//�ܵ�Ԥ��ֿ���������
		double csiPrePayment = amountPrePayList[0];
		//֧���б�Ԥ��ֿ��б���ܽ��֮��(Added by shuwei 2006-10-8)
		double csiTotalPayAmount = BusinessUtility.doubleFormat(csiPayAmount + csiPrePayment);
		//double csiTotalPayAmount = (double) Math.floor(csiPayAmount * 100 + csiPrePayment * 100+0.01) / 100;
		if(preFeeAmount > prePayAmount)
			throw new ServiceException("ǿ��Ԥ�������");
		
		if(prePaymentList.isEmpty())
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~there is no prepayment List");
		if(paymentList.isEmpty())
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~there is no payment List");
		
		//if(csiFeeAmount != csiTotalPayAmount)
		if(Math.abs(csiFeeAmount - csiTotalPayAmount)>0.0001)
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~there is csiFeeAmount != csiTotalPayAmount");
		
		System.out.println("csiFeeAmount=" + csiFeeAmount);
		System.out.println("csiTotalPayAmount=" + csiTotalPayAmount);
		
		
		
		if((!(paymentList.isEmpty()) || !(prePaymentList.isEmpty()) )
		    //&& csiFeeAmount != csiTotalPayAmount)
				&& Math.abs(csiFeeAmount - csiTotalPayAmount)>0.0001)
			throw new ServiceException("����֧�����ԣ�");
		//��������ǰ���Է����б�֧���б�����С��������
//		listArrange();
		//����У�鶼û����Ļ����������á�֧�������ʹ�ϵ��ϸ
		
		//�������־û���������ν������ʹ�ϵ
//	���ã�֧���־û�������������ɡ�
//		createAmountItem(); 
		//����֧����¼
//		createPaymentRecord();
		//����Ԥ��ֿۼ�¼,ͬʱ�����ʻ���Ϣ
//		if(prePaymentList != null)
	//		createPrePaymentDeductionRecord();
		
		ArrayList financeMapList = createFinanceMapList();
		return financeMapList;
	}

	/**
	 * �Է��á�֧���б��еĿͻ����ʻ���������Ϣ����У��
	 * input parameter
	 * @param int flag  ���á�֧����ʶ
	 *
	 * return parameter
	 * @param double[]
	 * @throws ServiceException
	 */
	private double[] checkInfo(int flag)throws ServiceException
	{
		int preCustomerID = 0;  //ǰһ����ϸ�Ŀͻ���
		int preAccountID = 0;   //ǰһ����ϸ���ʻ���
//		int preCSIID = 0;       //ǰһ����ϸ��������
		double preFeeAmount = 0.0f; //ǿ��Ԥ���ܷ���
		double csiFeeAmount = 0.0f; //���������ܶ�
		double prePayAmount = 0.0f; //Ԥ��֧���ܶ�		
		double csiPayAmount = 0.0f; //����֧���ܶ�
		double csiPrePayAmount = 0.0f; //����Ԥ��ֿ��ܶ�
		
		long lpreFeeAmount = 0;
		long lcsiFeeAmount = 0;
		long lprePayAmount = 0;
		long lcsiPayAmount = 0;
		long lcsiPrePayAmount = 0;
		
		/*
		 ���flag=FEE_FLAG������������б��ǿ��Ԥ��Ѻ�������
		 ���flag=PAY_FLAG�������֧���б��Ԥ�渶�Ѻ���������
		 ���flag=PREPAY_FLAG�������Ԥ��ֿ��б�ĵֿ۽��
		*/
		double amountList[] = new double[2];
		//У������б�
		if(flag == FEE_FLAG)
		{
			for(int i=0; i<feeList.size(); i++)
			{
				AccountItemDTO accItemDto = (AccountItemDTO)feeList.get(i);
				//���ý��Ϊ��ļ�¼�����м��
				if (accItemDto.getValue() == 0)
					continue;
				if(String.valueOf(accItemDto.getCustID()) == null ||
					accItemDto.getCustID() <= 0)
					throw new ServiceException("���ü�¼�пͻ��Ų���Ϊ�գ�");
				if(String.valueOf(accItemDto.getAcctID()) == null ||
					accItemDto.getAcctID() <= 0)
					throw new ServiceException("���ü�¼���ʻ��Ų���Ϊ�գ�");
//				if(String.valueOf(accItemDto.getReferID()) == null ||
//					accItemDto.getReferID() <= 0)
//					throw new ServiceException("���ü�¼�������Ų���Ϊ�գ�");
				if(i > 0)
				{
					if (preCustomerID != accItemDto.getCustID() && preCustomerID != 0)
						throw new ServiceException("�����б��еļ�¼����ͬһ���ͻ��ģ�");
					if (preAccountID != accItemDto.getAcctID() && preAccountID != 0)
						throw new ServiceException("�����б��еļ�¼����ͬһ���ʻ��ģ�");
//					if (preCSIID != accItemDto.getReferID() && preCSIID != 0)
//						throw new ServiceException("�����б��еļ�¼����ͬһ�������ģ�");
				}
				//У��ǿ��Ԥ���־
				if(accItemDto.getForcedDepositFlag() == null ||
					accItemDto.getForcedDepositFlag().trim().length() == 0)
					throw new ServiceException("���봫��ǿ��Ԥ���־��");
				LogUtility.log(clazz,LogLevel.DEBUG,"checkInfo(feeList)",accItemDto);
				//�õ�ǿ��Ԥ��Ѻ���������(���������˺Ͳ������˵ķ��ü�¼ 2006-9-28 shuwei modified)
				if(CommonConstDefinition.YESNOFLAG_YES.equals(accItemDto.getForcedDepositFlag()))
				{
					lpreFeeAmount = lpreFeeAmount + double2long(accItemDto.getValue())-double2long(accItemDto.getSetOffAmount());
/*					preFeeAmount = (double) Math.floor(preFeeAmount * 100
						+ (accItemDto.getValue() * 100
						   - accItemDto.getSetOffAmount() * 100)) / 100;*/
				}
				else
				{
					lcsiFeeAmount = lcsiFeeAmount + double2long(accItemDto.getValue())-double2long(accItemDto.getSetOffAmount());					
/*					csiFeeAmount = (double) Math.floor(csiFeeAmount * 100
						+ (accItemDto.getValue() * 100
						   - accItemDto.getSetOffAmount() * 100)) / 100;*/
				}
				preCustomerID = accItemDto.getCustID();
				preAccountID = accItemDto.getAcctID();
//				preCSIID = accItemDto.getReferID();
			}
//			amountList[0] = preFeeAmount;
//			amountList[1] = csiFeeAmount;
			amountList[0] = (double)lpreFeeAmount/100;
			amountList[1] = (double)lcsiFeeAmount/100;
		}
		//У��֧���б�
		if(flag == PAY_FLAG)
		{
			for(int i=0; i<paymentList.size(); i++)
			{
				PaymentRecordDTO paymentDto = (PaymentRecordDTO)paymentList.get(i);
				//֧�����Ϊ��ļ�¼�����м��
				//if (paymentDto.getAmount() == 0)
				if (Math.abs(paymentDto.getAmount())<0.0001)
					continue;
				if(String.valueOf(paymentDto.getCustID()) == null ||
					paymentDto.getCustID() <= 0)
					throw new ServiceException("֧����¼�пͻ��Ų���Ϊ�գ�");
				if(String.valueOf(paymentDto.getAcctID()) == null ||
					paymentDto.getAcctID() <= 0)
					throw new ServiceException("֧����¼���ʻ��Ų���Ϊ�գ�");
//				if(String.valueOf(paymentDto.getPaidTo()) == null ||
//					paymentDto.getPaidTo() <= 0)
//					throw new ServiceException("֧����¼�������Ų���Ϊ�գ�");
				if(i > 0)
				{
					if (preCustomerID != paymentDto.getCustID() && preCustomerID != 0)
						throw new ServiceException("֧���б��еļ�¼����ͬһ���ͻ��ģ�");
					if (preAccountID != paymentDto.getAcctID() && preAccountID != 0)
						throw new ServiceException("֧���б��еļ�¼����ͬһ���ʻ��ģ�");
//					if (preCSIID != paymentDto.getPaidTo() && preCSIID != 0)
//						throw new ServiceException("֧���б��еļ�¼����ͬһ�������ģ�");
				}
				//У��֧������
				if(paymentDto.getPayType() == null ||
					paymentDto.getPayType().trim().length() == 0)
					throw new ServiceException("���봫��֧�����ͣ�");
				LogUtility.log(clazz,LogLevel.DEBUG,"checkInfo(paymentList)",paymentDto);
				//Ԥ���˷Ѳ���������
				if(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE.equals(paymentDto.getPayType()))
					continue;
				//�õ�Ԥ�渶��
				if(CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType()))
				{
					if(paymentDto.getPrepaymentType() == null ||
						paymentDto.getPrepaymentType().trim().length() == 0)
						throw new ServiceException("����ָ�����ֽ�Ԥ�滹���������Ԥ�棡");
					
					lprePayAmount = lprePayAmount + double2long(paymentDto.getAmount());
				/*	prePayAmount = (double) Math.floor(prePayAmount * 100
						+ paymentDto.getAmount() * 100) / 100;*/
				}
				//�õ�����Ԥ�渶��
/*				else if(CommonConstDefinition.PAYMENTRECORD_TYPE_PASSIVITYPRESAVE.equals(paymentDto.getPayType()))
				{
					if(paymentDto.getPrepaymentType() == null ||
						paymentDto.getPrepaymentType().trim().length() == 0)
						throw new ServiceException("����ָ�����ֽ�Ԥ�滹���������Ԥ�棡");
				}*/
				else//�õ���������
				{
					lcsiPayAmount = lcsiPayAmount + double2long(paymentDto.getAmount());
/*					csiPayAmount = (double) Math.floor(csiPayAmount * 100
						+ paymentDto.getAmount() * 100+0.01) / 100;*/
				}
				preCustomerID = paymentDto.getCustID();
				preAccountID = paymentDto.getAcctID();
//				preCSIID = paymentDto.getPaidTo();
			}
/*			amountList[0] = prePayAmount;
			amountList[1] = csiPayAmount;*/
			amountList[0] = (double)lprePayAmount/100;
			amountList[1] = (double)lcsiPayAmount/100;
		}
		//У��Ԥ��ֿ��б�
		if(flag == PREPAY_FLAG)
		{
			for(int i=0; i<prePaymentList.size(); i++)
			{
				PrepaymentDeductionRecordDTO PrepaymentDto = (PrepaymentDeductionRecordDTO)prePaymentList.get(i);
				//Ԥ��ֿ۽��Ϊ��ļ�¼�����м��
				//if (PrepaymentDto.getAmount() == 0)
				if (Math.abs(PrepaymentDto.getAmount())<0.0001)
					continue;
				if(String.valueOf(PrepaymentDto.getCustId()) == null ||
					PrepaymentDto.getCustId() <= 0)
					throw new ServiceException("Ԥ��ֿۼ�¼�пͻ��Ų���Ϊ�գ�");
				if(String.valueOf(PrepaymentDto.getAcctId()) == null ||
					PrepaymentDto.getAcctId() <= 0)
					throw new ServiceException("Ԥ��ֿۼ�¼���ʻ��Ų���Ϊ�գ�");
//				if(String.valueOf(PrepaymentDto.getReferRecordId()) == null ||
//					PrepaymentDto.getReferRecordId() <= 0)
//					throw new ServiceException("Ԥ��ֿۼ�¼�������Ų���Ϊ�գ�");
				if(i > 0)
				{
					if (preCustomerID != PrepaymentDto.getCustId() && preCustomerID != 0)
						throw new ServiceException("Ԥ��ֿ��б��еļ�¼����ͬһ���ͻ��ģ�");
					if (preAccountID != PrepaymentDto.getAcctId() && preAccountID != 0)
						throw new ServiceException("Ԥ��ֿ��б��еļ�¼����ͬһ���ʻ��ģ�");
//					if (preCSIID != PrepaymentDto.getReferRecordId() && preCSIID != 0)
//						throw new ServiceException("Ԥ��ֿ��б��еļ�¼����ͬһ�������ģ�");
				}
				//У��Ԥ��ֿ�����
				if(PrepaymentDto.getPrepaymentType() == null ||
					PrepaymentDto.getPrepaymentType().trim().length() == 0)
					throw new ServiceException("����ָ�����ֽ�Ԥ��ֿۻ����������Ԥ��ֿۣ�");
				LogUtility.log(clazz,LogLevel.DEBUG,"checkInfo(prePaymentList)",PrepaymentDto);
				//�õ�Ԥ����ܿ۽��
				lcsiPrePayAmount = lcsiPrePayAmount + double2long(PrepaymentDto.getAmount());
//				csiPrePayAmount = (double) Math.floor(csiPrePayAmount * 100
//						+ PrepaymentDto.getAmount() * 100) / 100;

				preCustomerID = PrepaymentDto.getCustId();
				preAccountID = PrepaymentDto.getAcctId();
//				preCSIID = PrepaymentDto.getReferRecordId();
			}
			amountList[0] = (double)lcsiPrePayAmount/100;
//			amountList[0] = csiPrePayAmount;
		}
		return amountList;
	}
	
    /*private ArrayList pAcctItem;
    private ArrayList mAcctItem;
    
    private ArrayList pPrePay;
    private ArrayList mPrePay;
    
    private ArrayList pPayment;
    private ArrayList mPayment;*/
	
	private void buildSetOffList()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + "buildSetOffList");		
		for(int i = 0;i<feeList.size();i++)
		{
			AccountItemDTO acctItem = (AccountItemDTO)feeList.get(i);
			if(CommonConstDefinition.SETOFFFLAG_D.equals(acctItem.getSetOffFlag()))continue;
			if(acctItem.getValue() >0)
				pAcctItem.add(acctItem);
			else
				mAcctItem.add(acctItem);				
		}
		
		for(int i = 0;i<prePaymentList.size();i++)
		{
			PrepaymentDeductionRecordDTO prePay = (PrepaymentDeductionRecordDTO)prePaymentList.get(i);
			if(Math.abs(prePay.getAmount())<0.0002) continue;
			if(prePay.getAmount()>0)
				pPrePay.add(prePay);
			else
				mPrePay.add(prePay);
		}
		
		for(int i=0;i<paymentList.size();i++)
		{
			PaymentRecordDTO payment = (PaymentRecordDTO) paymentList.get(i);
			if(Math.abs(payment.getAmount())<0.0002) continue;
			if(payment.getAmount()>0)
				pPayment.add(payment);
			else
				mPayment.add(payment);
		}
	}
	
	private void buildSetOffLevel(AccountItemDTO acctItem)
	{
		sAcctItem.clear();
		for(int i = 0;i<mAcctItem.size();i++)
		{
			AccountItemDTO acctItem2 = (AccountItemDTO)mAcctItem.get(i);
			if(Math.abs(acctItem2.getValue())<0.0002) continue;
			if(CommonConstDefinition.SETOFFFLAG_D.equals(acctItem2.getSetOffFlag())) continue;
			sAcctItem.add(acctItem2);
		}
		sPrePay.clear();
		for(int i = 0;i < pPrePay.size();i++)
		{
			PrepaymentDeductionRecordDTO  prePay = (PrepaymentDeductionRecordDTO)pPrePay.get(i);
			if(Math.abs(prePay.getAmount())<0.0002) continue;
			sPrePay.add(prePay);
		}
		sPayment.clear();
		for(int i=0;i<pPayment.size();i++)
		{
			PaymentRecordDTO payment = (PaymentRecordDTO)pPayment.get(i);
			if(Math.abs(payment.getAmount())<0.0002) continue;
			sPayment.add(payment);			
		}
	}
	
	private void getElseSetOffItem()
	{
		sAcctItem.clear();
		for(int i = 0;i<mAcctItem.size();i++)
		{
			AccountItemDTO acctItem = (AccountItemDTO)mAcctItem.get(i);			
			if(Math.abs(acctItem.getValue())<0.0002) continue;
			if(CommonConstDefinition.SETOFFFLAG_D.equals(acctItem.getSetOffFlag())) continue;
			sAcctItem.add(mAcctItem.get(i));
		}
		sPrePay.clear();
		for(int i = 0;i < pPrePay.size();i++)
		{
			PrepaymentDeductionRecordDTO  prePay = (PrepaymentDeductionRecordDTO)pPrePay.get(i);
			if(Math.abs(prePay.getAmount())<0.0002) continue;
			sPrePay.add(prePay);
		}
		sPayment.clear();
		for(int i=0;i<pPayment.size();i++)
		{
			PaymentRecordDTO payment = (PaymentRecordDTO)pPayment.get(i);
			if(Math.abs(payment.getAmount())<0.0002) continue;
			sPayment.add(payment);			
		}
	}
	
	private void appendFeeList(AccountItemDTO acctItem)
	{
		for(int i = 0;i<feeList.size();i++)
		{
			AccountItemDTO ai = (AccountItemDTO)feeList.get(i);
			if(ai.getAiNO() == acctItem.getAiNO())
			{
				feeList.remove(i);
				feeList.add(i,acctItem);
				return;
			}
		}
	}
	private void appendPrePayList(PrepaymentDeductionRecordDTO prePay)
	{
		for(int i = 0;i< prePaymentList.size();i++)
		{
			PrepaymentDeductionRecordDTO prePayDTO = (PrepaymentDeductionRecordDTO)prePaymentList.get(i);
			if(prePay.getSeqNo() == prePayDTO.getSeqNo())
			{
				prePaymentList.remove(i);
				prePaymentList.add(i, prePay);
				return;
			}			
		}		
	}

	private void appendPaymentList(PaymentRecordDTO payment)
	{
		for(int i = 0;i< paymentList.size();i++)
		{
			PaymentRecordDTO paymentDTO = (PaymentRecordDTO)paymentList.get(i);
			if(payment.getSeqNo() == paymentDTO.getSeqNo())
			{
				paymentList.remove(i);
				paymentList.add(i, payment);
				return;
			}			
		}		
	}
	
	private static double fixeddouble(double f)
	{
		if(Math.abs(f)<0.00002) return 0.0f;
		
		if(f<0)
			return f - 0.00002f;
		
		return f + 0.00002f;
	}
	
	public static long double2long(double f)
	{
		if(Math.abs(f)<0.00002) return 0;
		
		if(f<0)
			return(long)((double)((f - 0.00002f)*100));
		
		return (long)((double)((f + 0.00002f)*100));
	}

	private void setOffFee()
	{
		boolean bSetOff1 = false;
		boolean bSetOff2 = false;
		//�������˹�ϵ
		loop1:for(int i=0;i <pAcctItem.size();i++)
		{
			bSetOff1 = false;
			AccountItemDTO acctItem1 = (AccountItemDTO)pAcctItem.get(i);
			//�����������ȼ�
			buildSetOffLevel(acctItem1);
			//������
			long lvalue1 = double2long(acctItem1.getValue()) - double2long(acctItem1.getSetOffAmount());
			

			loop2:for(int j=0;j<sAcctItem.size();j++)
			{
				bSetOff2 = false;
				AccountItemDTO acctItem2 = (AccountItemDTO)sAcctItem.get(j);
				long lvalue2 = double2long(acctItem2.getValue()) - double2long(acctItem2.getSetOffAmount());

				
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				if(lvalue1+lvalue2 == 0)
				{
					//��ȫ����
					acctItem1.setSetOffAmount(acctItem1.getValue());
					acctItem2.setSetOffAmount(acctItem2.getValue());
					acctItem1.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
					acctItem2.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
					bSetOff1 = true;
					bSetOff2 = true;
					financeMapDto.setValue((double)lvalue1/100);
				}
				else
				{
					if((lvalue1+lvalue2 )> 0)
					{
						acctItem2.setSetOffAmount(acctItem2.getValue());
						acctItem1.setSetOffAmount((double)(double2long(acctItem1.getSetOffAmount()) - lvalue2)/100);
						acctItem2.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						bSetOff2 = true;
						lvalue1 = lvalue1 + lvalue2;
						financeMapDto.setValue((double)Math.abs(lvalue2)/100);
					}
					else
					{
						bSetOff1 = true;
						acctItem1.setSetOffAmount(acctItem1.getValue());
						acctItem2.setSetOffAmount((double)((double2long(acctItem2.getSetOffAmount()) - lvalue1)/100));
						acctItem1.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						financeMapDto.setValue((double)lvalue1/100);
					}
				}
				appendFeeList(acctItem1);
				appendFeeList(acctItem2);
				
				
				//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
				financeMapDto.setAcctId(acctItem1.getAcctID());
				financeMapDto.setCustId(acctItem1.getCustID());
				financeMapDto.setOpId(acctItem1.getOperatorID());
				financeMapDto.setOrgId(acctItem1.getOrgID());
				//������ϸ����������Ϊ��ֱ�����ʡ�
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//������ϸ��״̬Ϊ��������
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				
				financeMapDto.setPlusReferId(acctItem1.getAiNO());
				financeMapDto.setMinusReferId(acctItem2.getAiNO());
				financeMapList.add(financeMapDto);
				
				if(bSetOff1) continue loop1;
				if(bSetOff2) continue loop2;
			}
			//���ֿ�
			loop3:for(int j=0;j<sPrePay.size();j++)
			{
				bSetOff2 = false;
				PrepaymentDeductionRecordDTO prePay = (PrepaymentDeductionRecordDTO)sPrePay.get(j);
				long lvalue2 = double2long(prePay.getAmount());
//				double value2 = prePay.getAmount(); 
				if(lvalue1*lvalue2 < 0) continue loop3;
				
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				
				if(lvalue1 == lvalue2)
				{
					//��ȫ������
					prePay.setAmount(0.0f);
					acctItem1.setSetOffAmount(acctItem1.getValue());
					acctItem1.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
					bSetOff1 = true;
					bSetOff2 = true;
					financeMapDto.setValue((double)lvalue1/100);
				}
				else
				{
					if(lvalue1 > lvalue2)
					{
						bSetOff2 = true;
						acctItem1.setSetOffAmount(acctItem1.getSetOffAmount()+(double)lvalue2/100);						
						prePay.setAmount(0.0f);
						lvalue1 = lvalue1 - lvalue2;
						financeMapDto.setValue((double)Math.abs(lvalue2)/100);
					}
					else
					{
						bSetOff1 = true;
						acctItem1.setSetOffAmount(acctItem1.getValue());
						acctItem1.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						prePay.setAmount((double)(double2long(prePay.getAmount()) - lvalue1)/100);
						financeMapDto.setValue((double)lvalue1/100);
					}
				}
				appendFeeList(acctItem1);
				appendPrePayList(prePay);
				
				//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
				financeMapDto.setAcctId(acctItem1.getAcctID());
				financeMapDto.setCustId(acctItem1.getCustID());
				financeMapDto.setOpId(acctItem1.getOperatorID());
				financeMapDto.setOrgId(acctItem1.getOrgID());
				//������ϸ����������Ϊ��ֱ�����ʡ�
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//������ϸ��״̬Ϊ��������
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_D);
				
				financeMapDto.setPlusReferId(acctItem1.getAiNO());
				financeMapDto.setMinusReferId(prePay.getSeqNo());
				financeMapList.add(financeMapDto);
				
				if(bSetOff1)continue loop1;
				if(bSetOff2)continue loop3;
			}
			
			//��֧��
			loop4:for(int j=0;j<sPayment.size();j++)
			{
				bSetOff2 = false;
				PaymentRecordDTO payment = (PaymentRecordDTO) sPayment.get(j);
				long lvalue2 = double2long(payment.getAmount());
//				double value2 = payment.getAmount();
				
				if(lvalue1*lvalue2<0) continue loop4;
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				
				if(lvalue1 == lvalue2)
				{
					bSetOff1 = true;
					bSetOff2 = true;
					acctItem1.setSetOffAmount(acctItem1.getValue());
					acctItem1.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
					financeMapDto.setValue((double)lvalue1/100);
				}
				else
				{
					if(lvalue1>lvalue2)
					{
						bSetOff2 = true;
						payment.setAmount(0.0f);
						acctItem1.setSetOffAmount((double)(double2long(acctItem1.getSetOffAmount()) + lvalue2)/100);
						financeMapDto.setValue((double)Math.abs(lvalue2)/100);
						lvalue1 = lvalue1 - lvalue2;
					}
					else
					{
						bSetOff1 = true;
						acctItem1.setSetOffAmount(acctItem1.getValue());
						acctItem1.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						payment.setAmount((double)(double2long(payment.getAmount()) - lvalue1)/100);
						financeMapDto.setValue((double)lvalue1/100);
					}
				}
				appendFeeList(acctItem1);
				appendPaymentList(payment);
				
				//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
				financeMapDto.setAcctId(acctItem1.getAcctID());
				financeMapDto.setCustId(acctItem1.getCustID());
				financeMapDto.setOpId(acctItem1.getOperatorID());
				financeMapDto.setOrgId(acctItem1.getOrgID());
				//������ϸ����������Ϊ��ֱ�����ʡ�
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//������ϸ��״̬Ϊ��������
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_P);
				
				financeMapDto.setPlusReferId(acctItem1.getAiNO());
				financeMapDto.setMinusReferId(payment.getSeqNo());
				financeMapList.add(financeMapDto);
				
				if(bSetOff1) continue loop1;
				if(bSetOff2) continue loop4;
			}
		}
	}
	
	private void setOffPrePayment()
	{
		//buildSetOffList();
		//�������������
	}
	
	private void setOffPayment()
	{
		buildSetOffList();
		boolean bSetOff1 = false;
		boolean bSetOff2 = false;
		loop1:for(int i=0;i<mPayment.size();i++)
		{
			bSetOff1 = false;
			PaymentRecordDTO payment = (PaymentRecordDTO)mPayment.get(i);
			long value1 = double2long(payment.getAmount());
			loop2:for(int j=0;j<mAcctItem.size();j++)
			{
				bSetOff2 = false;
				AccountItemDTO acctItem = (AccountItemDTO)mAcctItem.get(j);
				if(CommonConstDefinition.SETOFFFLAG_D.equals(acctItem.getSetOffFlag())) continue loop2;
				long value2 = double2long(acctItem.getValue()) - double2long(acctItem.getSetOffAmount());
				
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				//if(Math.abs(payment.getAmount())==Math.abs(acctItem.getValue()) )
				if(Math.abs(payment.getAmount()-acctItem.getValue())<0.0001)
				{
					//���õ���
					acctItem.setSetOffAmount(acctItem.getValue());
					acctItem.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
					payment.setAmount(0.0f);
					bSetOff1 = true;
					bSetOff2 = true;
					financeMapDto.setValue(payment.getAmount());
				}
				else
				{
					if(Math.abs(value1)>Math.abs(value2))
					{
						//����Ϊ����
						bSetOff2 = true;
						acctItem.setSetOffAmount(acctItem.getValue());
						acctItem.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						payment.setAmount((double)(double2long(payment.getAmount()) - value2)/100);
						financeMapDto.setValue((double)value2/100);
						value1 =value1-value2;
					}
					else
					{
						//֧��Ϊ����
						bSetOff1 = true;
						payment.setAmount(0.0f);
						acctItem.setSetOffAmount((double)(double2long(acctItem.getSetOffAmount()) + value1)/100);
					}
				}				
				appendFeeList(acctItem);
				appendPaymentList(payment);				
				//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
				financeMapDto.setAcctId(acctItem.getAcctID());
				financeMapDto.setCustId(acctItem.getCustID());
				financeMapDto.setOpId(acctItem.getOperatorID());
				financeMapDto.setOrgId(acctItem.getOrgID());
				//������ϸ����������Ϊ��ֱ�����ʡ�
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//������ϸ��״̬Ϊ��������
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_P);
				
				financeMapDto.setPlusReferId(acctItem.getAiNO());
				financeMapDto.setMinusReferId(payment.getSeqNo());
				financeMapList.add(financeMapDto);
				
				if(bSetOff1) continue loop1;
				if(bSetOff2) continue loop2;				
			}
			
			loop3:for(int j=0;j<pPayment.size();j++)
			{
				bSetOff2 = false;
				PaymentRecordDTO pr = (PaymentRecordDTO)pPayment.get(i);
				long value2 = double2long(pr.getAmount());
				
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				if(Math.abs(value1)==Math.abs(value2))
				{
					bSetOff1 = true;
					bSetOff2 = true;
					payment.setAmount(0.0f);
					pr.setAmount(0.0f);
					financeMapDto.setValue((double)value1/100);
				}
				else
				{
					if(Math.abs(value1)>Math.abs(value2))
					{
						bSetOff2 = true;
						financeMapDto.setValue(value2);
						value1 = value1 + value2;
						payment.setAmount((double)value1/100);
						pr.setAmount(0.0f);
					}
					else
					{
						bSetOff1 = true;
						financeMapDto.setValue((double)Math.abs(value1)/100);
						pr.setAmount((double)(double2long(pr.getAmount())+value1)/100);
						payment.setAmount(0.0f);
					}					
				}
				appendPaymentList(pr);
				appendPaymentList(payment);				
				//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
				financeMapDto.setAcctId(payment.getAcctID());
				financeMapDto.setCustId(payment.getCustID());
				financeMapDto.setOpId(payment.getOpID());
				financeMapDto.setOrgId(payment.getOrgID());
				//������ϸ����������Ϊ��ֱ�����ʡ�
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//������ϸ��״̬Ϊ��������
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_P);
				
				financeMapDto.setPlusReferId(pr.getSeqNo());
				financeMapDto.setMinusReferId(payment.getSeqNo());
				financeMapList.add(financeMapDto);
				if(bSetOff1) continue loop1;
				if(bSetOff2) continue loop3;
			}
		}		
	}
	
	/*
	 * ��������ģʽ.
	 * �������Ӧ�����˵�����������
	 * */
	private void setOffWithIMode(int csi,String csiType)
	{
		for(int i = 0;i<feeList.size();i++)
		{
			AccountItemDTO acctItem = (AccountItemDTO)feeList.get(i);
			if(CommonConstDefinition.SETOFFFLAG_D.equals(acctItem.getSetOffFlag()))
				continue;
			FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
			//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
			financeMapDto.setAcctId(acctItem.getAcctID());
			financeMapDto.setCustId(acctItem.getCustID());
			financeMapDto.setOpId(acctItem.getOperatorID());
			financeMapDto.setOrgId(acctItem.getOrgID());
			//������ϸ����������Ϊ��ֱ�����ʡ�
			financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
			//������ϸ��״̬Ϊ��������
			financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			//�������ʹ�����¼����Ϊ"����"
			financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
			//�������ʹ�����¼����Ϊ"����"
			financeMapDto.setMinusReferType(csiType);
//System.out.println("####acctItem.getValue(),acctItem.getSetOffAmount()###" + String.valueOf(acctItem.getValue()) + ",,," + String.valueOf(acctItem.getSetOffAmount()));		
			financeMapDto.setValue(acctItem.getValue()-acctItem.getSetOffAmount());
			financeMapDto.setPlusReferId(acctItem.getAiNO());
			financeMapDto.setMinusReferId(csi);
			financeMapList.add(financeMapDto);

			acctItem.setSetOffAmount(acctItem.getValue());
			acctItem.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
			feeList.remove(i);
			feeList.add(i, acctItem);
			
		}
System.out.println("$$$$$$$$$$$$$$$$$$$$$prePaymentList.size()" + String.valueOf(prePaymentList.size()));		
		for(int i = 0;i<prePaymentList.size();i++)
		{
			System.out.println("$$$$$$$$$$$$$$$$$$$$$i" + String.valueOf(i));		
			
			PrepaymentDeductionRecordDTO prePay = (PrepaymentDeductionRecordDTO)prePaymentList.get(i);
			FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
			//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
			financeMapDto.setAcctId(prePay.getAcctId());
			financeMapDto.setCustId(prePay.getCustId());
			financeMapDto.setOpId(prePay.getOpId());
			financeMapDto.setOrgId(prePay.getOrgId());
			//������ϸ����������Ϊ��ֱ�����ʡ�
			financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
			//������ϸ��״̬Ϊ��������
			financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			//�������ʹ�����¼����Ϊ"����"
			financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_D);
			//�������ʹ�����¼����Ϊ"����"
			financeMapDto.setMinusReferType(csiType);
			financeMapDto.setValue(prePay.getAmount());
			financeMapDto.setPlusReferId(prePay.getSeqNo());
			financeMapDto.setMinusReferId(csi);
			financeMapList.add(financeMapDto);
		}
		
		for(int i=0;i<paymentList.size();i++)
		{
			PaymentRecordDTO payment = (PaymentRecordDTO)paymentList.get(i);
			FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
			//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
			financeMapDto.setAcctId(payment.getAcctID());
			financeMapDto.setCustId(payment.getCustID());
			financeMapDto.setOpId(payment.getOpID());
			financeMapDto.setOrgId(payment.getOrgID());
			//������ϸ����������Ϊ��ֱ�����ʡ�
			financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
			//������ϸ��״̬Ϊ��������
			financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			//�������ʹ�����¼����Ϊ"����"
			financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_P);
			//�������ʹ�����¼����Ϊ"����"
			financeMapDto.setMinusReferType(csiType);
			
			financeMapDto.setValue(payment.getAmount());
			
			financeMapDto.setPlusReferId(payment.getSeqNo());
			financeMapDto.setMinusReferId(csi);

System.out.println("###############PlusID =" + String.valueOf(payment.getSeqNo()));
			financeMapList.add(financeMapDto);
			
		}
		
	}
	private ArrayList createFinanceMapList()throws ServiceException
	{
		//�������ʴ�����߼�
		/*�ж����ʼ���,�ֲ��账��
		 * 1 �Ƚ�����ͬ��Ŀ���ͷ��õ�����
		 * 2 Ԥ��ֿۼ�,֧����������
		 * 3 ������������
		 *    a ��������(+)Ԥ��ֿ�(-)֧��(-)
		 *    b �������¼����ɸѡ,����
		 *    c ������Ӧ���ʹ�ϵ.
		 * */
		String setOffLevel = BusinessUtility.getSetOffLevel();
		
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + setOffLevel);

		if(CommonConstDefinition.SETOFF_LEVEL_I.equals(setOffLevel)) 
		{
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + "�������� + ID = " + String.valueOf(id));
			setOffWithIMode(id,type);
			return financeMapList;//������ʼ���Ϊ��������
		}
		
		//ͬ����Ԥ����
		buildSameAcctItemSetOffMap();
		
		//���������ʷ���,Ԥ��ֿ�,֧���б� �� �����ʷ���,Ԥ��ֿ�,֧���б�
		buildSetOffList();
		
		setOffFee();
		
		setOffPrePayment();
		
		setOffPayment();
		return financeMapList;
	}
	/**
	 * ���ɷ��ú�֧����������ϸ
	 * return parameter
	 * @param ArrayList financeMapList //���á�֧�������б�
	 * @throws ServiceException
	 */
	private ArrayList createFinanceMapList2()throws ServiceException
	{
		//�����б�
		ArrayList accItemList = new ArrayList();
		//֧���б�
		ArrayList payRecordList = new ArrayList();
		//Ԥ��ֿ��б�
		ArrayList prePayList = new ArrayList();
		//�����б�
//		ArrayList financeMapList = new ArrayList();
		//��ǰ���ü�¼���к�(����ĸ���)
		int aiNO = 0;
		//��ǰ֧����¼���к�(����ĸ���)
		int payNO = 0;
		//��ǰԤ��ֿۼ�¼���к�(����ĸ���)
		int prePayNO = 0;
		//���������б�
		outer:for(int i=0; i<feeList.size(); i++)
		{
			AccountItemDTO accItemDto = (AccountItemDTO)feeList.get(i);
			//���ý�������ʵļ�¼���������� 2006-9-28 modified by shuwei
			if(CommonConstDefinition.SETOFFFLAG_D.equals(accItemDto.getSetOffFlag()))
				continue outer;
			//���ý��Ϊ��ļ�¼����������
			//if(accItemDto.getValue() == 0)
			if(Math.abs(accItemDto.getValue())<0.0001)
				continue outer;
			//��������Ч�ķ��ü�¼
			if(CommonConstDefinition.AISTATUS_INVALIDATE.equals(accItemDto.getStatus()))
				continue outer;
			
			
			//zyg 2006.11.10 begin
			/**********************************************************************************
			//������Żݷ��û���Է��ã������õĵ��ˣ�������ѭ�������ڷ��öԷ��õ�����
			if((CommonConstDefinition.FTCREATINGMETHOD_B.equals(accItemDto.getCreatingMethod())
				&& accItemDto.getValue() < 0)
				|| (CommonConstDefinition.FTCREATINGMETHOD_T.equals(accItemDto.getCreatingMethod())
				&& paymentList.isEmpty() && prePaymentList.isEmpty()))
				continue outer;
			/**********************************************************************************/
			//zyg 2006.11.10 end

			//zyg 2006.11.20 begin
			//������ַ��ü�¼�Ľ�� < 0 , �����������һ����¼
			/**********************************************************************************
			//���Ϸ��˷ѵ����ƣ������˷��޷����� modify  by david.Yang 2006-11-26
			if(accItemDto.getValue() < 0 && !CommonConstDefinition.FTCREATINGMETHOD_R.equals(accItemDto.getCreatingMethod()))
				continue outer;
			/**********************************************************************************/

			double value =0;

                        //����һ�βμ����ʵķ��ü�¼������һ�������б���
                        //zyg 2006.11.20 begin
                        /*****************************************
                        if (accItemDto.getValue() >0){   //ֻ�����ķ��ü�¼ modify by david.Yang  2006-11-26
                        /*****************************************/
                        //zyg 2006.11.10 end

			   if (String.valueOf(accItemDto.getAiNO()) == null ||
				   accItemDto.getAiNO() == 0) {
				   aiNO--;
				   /*
				    Ϊ�˸�������ϸ������ϵ������������ü�¼���кţ�
				    ���ݳ־û�ʱ�����������ķ��ü�¼���кš�
				   */
				  accItemDto.setAiNO(aiNO);
			      AccountItemDTO accItemDto1 = getAccItemDto(accItemDto);
			      accItemList.add(accItemDto1);
			   }

                        //zyg 2006.11.20 begin
                        /*****************************************
                        }
                        /*****************************************/


			//ÿ�ʷ���ʵ����Ҫ���˵Ľ�� modified by shuwei 2006-9-28
			value = BusinessUtility.doubleFormat(accItemDto.getValue() - accItemDto.getSetOffAmount());
			//value = (double) Math.floor(accItemDto.getValue() * 100 - accItemDto.getSetOffAmount() * 100+0.01) / 100;

			System.out.println("value==============="+value);
			System.out.println("accItemDto.getCreatingMethod()============="+accItemDto.getCreatingMethod());

			//�Ƚ����Żݷ��õ�����
			for(int j=0; j<feeList.size(); j++)
			{
				AccountItemDTO favourableAccItemDto = (AccountItemDTO)feeList.get(j);
				//�Żݽ��Ϊ��ļ�¼����������
				//if (favourableAccItemDto.getValue() == 0)
				if (Math.abs(favourableAccItemDto.getValue())<0.0001)
					continue;
				//��������Ч���Żݷ��ü�¼
				if (CommonConstDefinition.AISTATUS_INVALIDATE.equals(
					favourableAccItemDto.getStatus()))
					continue;

                                //zyg 2006.11.22 begin
				//���ý�������ʵļ�¼���������� 
				/**********************************/
				if(CommonConstDefinition.SETOFFFLAG_D.equals(favourableAccItemDto.getSetOffFlag()))
					continue ;
				/**********************************/
				//zyg 2006.11.22 end


				//zyg 2006.11.10 begin
				//��������Żݷ��û���Է��ã������õĵ��ˣ�������ѭ��
				/**********************************************************************************
				if(!(CommonConstDefinition.FTCREATINGMETHOD_B.equals(favourableAccItemDto.getCreatingMethod())
				   && favourableAccItemDto.getValue() < 0)
				   && !(CommonConstDefinition.FTCREATINGMETHOD_T.equals(favourableAccItemDto.getCreatingMethod())
				   && paymentList.isEmpty() && prePaymentList.isEmpty()))
					continue;
				/**********************************************************************************/
				//zyg 2006.11.10 end

                                //zyg 2006.11.10 begin
                                //ֻ�� ���ķ��� �� ���ķ���֮���������
                                /**********************************************************************************
                                if(favourableAccItemDto.getValue() > 0)
                                  continue;
                                /**********************************************************************************/
				//zyg 2006.11.10 end

                               //zyg 2006.11.20 begin
                               //��� ���ʷ��� �Ľ�����������ͬ, ������
                               /*****************************************/
                               if (favourableAccItemDto.getValue() * value  > 0)
                                 continue;
                               /*****************************************/

                               //zyg 2006.11.20 begin
                               /*****************************************
                               //�˷Ѳ������������õ����ʣ�ֻ��֧�� modify by david.Yang
                               if (CommonConstDefinition.FTCREATINGMETHOD_R.equals(accItemDto.getCreatingMethod()))
                                 continue;
                               /*****************************************/

				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
				financeMapDto.setAcctId(accItemDto.getAcctID());
				financeMapDto.setCustId(accItemDto.getCustID());
				financeMapDto.setOpId(accItemDto.getOperatorID());
				financeMapDto.setOrgId(accItemDto.getOrgID());
				//������ϸ����������Ϊ��ֱ�����ʡ�
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//������ϸ��״̬Ϊ��������
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				/*
				 ֻ�е�ѭ�������еķ��ü�¼��ǿ��Ԥ�����ǡ�NO��ʱ
				 �ſ��Խ����Żݷ��õķ���������ϸ��
				*/
				if(CommonConstDefinition.YESNOFLAG_NO.equals(accItemDto.getForcedDepositFlag()))
				{
					//����һ�βμ����ʵ��Żݷ��ü�¼������һ�������б���
					if(String.valueOf(favourableAccItemDto.getAiNO()) == null ||
						favourableAccItemDto.getAiNO() == 0)
					{
						aiNO--;
						/*
						Ϊ�˸�������ϸ������ϵ�����������Żݷ������кţ�
						���ݳ־û�ʱ�������������Żݷ������кš�
						*/
						favourableAccItemDto.setAiNO(aiNO);
						AccountItemDTO accItemDto1 = getAccItemDto(favourableAccItemDto);
						accItemList.add(accItemDto1);
					}
					/*
					 ������ý�����õ����Żݷ��ý�����һ��һ�����ʼ�¼��
					 ���ʽ����ڷ��ý�
					 ������ý��С���Żݷ��ý����Żݷ��ý���ȥ���ý�����
					 ������һ��ѭ���������ʡ�
					*/
				   //zyg 2006.11.22 begin
				   /********************************************/
				   if(Math.abs(value) <= Math.abs(favourableAccItemDto.getValue() - favourableAccItemDto.getSetOffAmount()))
				   /********************************************/
				   //zyg 2006.11.22 end
					{
						//������ϸ�����ʽ��Ϊ������ϸ�ķ��ý��
						financeMapDto.setValue(Math.abs(value));
						//�������ʵ��Żݷ��ü�¼ɾ��
						feeList.remove(j);

						

						if(Math.abs(value) < Math.abs(favourableAccItemDto.getValue() - favourableAccItemDto.getSetOffAmount()))
						{
							//zyg 2006.11.22 begin 
							//Ӧ�ø������˽��
							/**********************************************************
							//���Żݷ��ý���ȥ���ý����������Ϊ���µġ��Żݷ��ý��
							favourableAccItemDto.setValue((double) Math.floor(favourableAccItemDto.getValue()*100 + value*100+0.01)/100);
							/**********************************************************/
							//zyg 2006.11.22 end
													
							//zyg 2006.11.22 begin
							//��������״̬
							/********************************/				
							favourableAccItemDto.setSetOffAmount(BusinessUtility.doubleFormat(favourableAccItemDto.getSetOffAmount() - value));
							//favourableAccItemDto.setSetOffAmount((double) Math.floor(favourableAccItemDto.getSetOffAmount()*100 - value*100+0.01)/100);
							favourableAccItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_P);
							/********************************/													
							//zyg 2006.11.22 end
							
							/**
							 * �����µġ��Żݷ��ý����Żݷ��ü�¼���²�������б���
							 * ������һ��ѭ��������
							 */
							feeList.add(j, favourableAccItemDto);
						}
						//zyg 2006.11.22 begin
						/*********************************/
						else
						{
							favourableAccItemDto.setSetOffAmount((double) Math.floor(favourableAccItemDto.getValue()));
							favourableAccItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						}
						/*********************************/
						//zyg 2006.11.22 end

						accItemDto.setSetOffAmount(accItemDto.getValue());
						accItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						//������ϸ�ж�Ӧ�ķ��ü�¼���к�(�����)
						financeMapDto.setMinusReferId(accItemDto.getAiNO());
						//������ϸ�ж�Ӧ���Żݷ��ü�¼���к�(�����)
						financeMapDto.setPlusReferId(favourableAccItemDto.getAiNO());
						//����������ϸ�б�
						financeMapList.add(financeMapDto);

						System.out.println("i===================="+i);
						System.out.println("feeList.size()==========="+feeList.size());

						//�����һ�δ��룬�����������˷ѵ�����˳�ѭ�� begin:
						//����ѭ��������������ϸ
						/*
						if(i == feeList.size()-1 && paymentList.size() > 0)
							continue;
						*/
						//end

						continue outer;
					}
					/*
					 ������ý������Żݷ��ý������ʽ������Żݷ��ý�
					 ���ý���ȥ�Żݷ��ý�����������һ��ѭ���������ʡ�
					*/
					//zyg 2006.11.22 begin
					/***************************************************************/
					if(Math.abs(value) > Math.abs(favourableAccItemDto.getValue() - favourableAccItemDto.getSetOffAmount()))
					/***************************************************************/
					//zyg 2006.11.22 end
					{
						//������ϸ�����ʽ��Ϊ�Żݷ��ý��
						//zyg 2006.11.22 begin
						/*****************************************************************/
						financeMapDto.setValue(Math.abs(favourableAccItemDto.getValue() - favourableAccItemDto.getSetOffAmount()));
						/*****************************************************************/
						//zyg 2006.11.22 end
						
						//�������ʵ��Żݷ��ü�¼ɾ��
						feeList.remove(j);
						//�����ý���ȥ�Żݷ��ý����������Ϊ���µġ����ý��
//						accItemDto.setValue((double) Math.floor(value*100+
//							favourableAccItemDto.getValue()*100)/100);
                                                //zyg 2006.11.22 begin
                                                /**************************************************/
						accItemDto.setSetOffAmount(BusinessUtility.doubleFormat(accItemDto.getSetOffAmount() - (favourableAccItemDto.getValue()-favourableAccItemDto.getSetOffAmount())));
						//accItemDto.setSetOffAmount((double) Math.floor(accItemDto.getSetOffAmount()*100 - (favourableAccItemDto.getValue()-favourableAccItemDto.getSetOffAmount())*100+0.01)/100);
						/**************************************************/
						//zyg 2006.11.22 end
						accItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_P);
						/**
						 * �����µġ����ý��ķ��ü�¼���²�������б���
						 * ������һ��ѭ��������
						 */

                                                //zyg 2006.11.20 begin
                                                /************************
                                                feeList.add(i,accItemDto);
                                                /************************/
                                                //zyg 2006.11.20 end

                                                //zyg 2006.11.22 begin
                                                //���·��ü�¼�����˽�������״̬
                                                /************************/
                                                favourableAccItemDto.setSetOffAmount(favourableAccItemDto.getValue());
                                                favourableAccItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
                                                /************************/
                                                //zyg 2006.11.22 end


						i--;

                                                //������ϸ�ж�Ӧ�ķ��ü�¼���к�(�����)
						financeMapDto.setMinusReferId(accItemDto.getAiNO());
						//������ϸ�ж�Ӧ���Żݷ��ü�¼���к�(�����)
						financeMapDto.setPlusReferId(favourableAccItemDto.getAiNO());
						//����������ϸ�б�
						financeMapList.add(financeMapDto);

						//����ѭ��������������ϸ
						continue outer;
					}
				}
			}
			//Ȼ�����Ԥ��ֿ۵�����
			for (int j = 0; j < prePaymentList.size(); j++)
			{
                          //zyg 2006.11.20 begin
                          /**************************************************
                          //�����˷Ѳ���Ԥ��ֿ�����
                          if(CommonConstDefinition.FTCREATINGMETHOD_R.equals(accItemDto.getCreatingMethod()))
                              break;
                          /**************************************************/

				PrepaymentDeductionRecordDTO prePaymentDto = (
					PrepaymentDeductionRecordDTO) prePaymentList.get(j);
				//Ԥ��ֿ۽��Ϊ��ļ�¼����������
				//if (prePaymentDto.getAmount() == 0)
				if (Math.abs(prePaymentDto.getAmount())<0.0001)	
					continue;
				//��������Ч��Ԥ��ֿۼ�¼
				if (CommonConstDefinition.AISTATUS_INVALIDATE.equals(
					prePaymentDto.getStatus()))
					continue;
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
				financeMapDto.setAcctId(accItemDto.getAcctID());
				financeMapDto.setCustId(accItemDto.getCustID());
				financeMapDto.setOpId(accItemDto.getOperatorID());
				financeMapDto.setOrgId(accItemDto.getOrgID());
				//������ϸ����������Ϊ��Ԥ�����ʡ�
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_D);
				//������ϸ��״̬Ϊ��������
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//�������ʹ�����¼����Ϊ"Ԥ��ֿ�"
				financeMapDto.setPlusReferType(CommonConstDefinition.
					SETOFFREFERTYPE_D);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setMinusReferType(CommonConstDefinition.
					SETOFFREFERTYPE_F);
				/*
				 ֻ�е�ѭ�������еķ��ü�¼��ǿ��Ԥ�����ǡ�NO��ʱ
				 �ſ��Խ���Ԥ��ֿ۵ķ���������ϸ��
				 */

                                //zyg 2006.11.20 begin
                                //���Ƿ������˵���������
                                //if (CommonConstDefinition.YESNOFLAG_NO.equals(accItemDto.getForcedDepositFlag()))
                                if (CommonConstDefinition.YESNOFLAG_NO.equals(accItemDto.getForcedDepositFlag()) && value*prePaymentDto.getAmount() > 0)
                                //zyg 2006.11.20 end

				{
					//����һ�βμ����ʵ�Ԥ��ֿۼ�¼������һ��Ԥ��ֿ��б���
					if (String.valueOf(prePaymentDto.getSeqNo()) == null ||
						prePaymentDto.getSeqNo() == 0)
					{
						prePayNO--;
						/*
					    Ϊ�˸�������ϸ������ϵ����������Ԥ��ֿۼ�¼���кţ�
					    ���ݳ־û�ʱ������������Ԥ��ֿۼ�¼���кš�
					    */
					    prePaymentDto.setSeqNo(prePayNO);
						PrepaymentDeductionRecordDTO prePaymentDto1 = PayFeeUtil.getPrePaymentDto(prePaymentDto);
						prePayList.add(prePaymentDto1);
					}
					/*
					 ������ý�����õ���Ԥ��ֿ۽�����һ��һ�����ʼ�¼��
					 ���ʽ����ڷ��ý�
					 ������ý��С��Ԥ��ֿ۽���Ԥ��ֿ۽���ȥ���ý�����
					 ������һ��ѭ���������ʡ�
					 */
					if (Math.abs(value) <= Math.abs(prePaymentDto.getAmount()))
					{
                                          //������ϸ�����ʽ��Ϊ������ϸ�ķ��ý��
                                          //zyg 2006.11.20 begin
                                          //���˽������⣬��һ����������
                                          //financeMapDto.setValue(Math.abs(value));
                                          financeMapDto.setValue(value);
                                          //zyg 2006.11.20 end

						//�������ʵ�Ԥ��ֿۼ�¼ɾ��
						prePaymentList.remove(j);
						if (Math.abs(value) < Math.abs(prePaymentDto.getAmount()))
						{
							//��Ԥ��ֿ۽���ȥ���ý����������Ϊ���µġ�Ԥ��ֿ۽��
							prePaymentDto.setAmount(BusinessUtility.doubleFormat(prePaymentDto.getAmount() - value));
							//prePaymentDto.setAmount((double) Math.floor(prePaymentDto.getAmount()*100 - value*100+0.01)/100);
							/**
							 * �����µġ�Ԥ��ֿ۽���Ԥ��ֿۼ�¼���²���Ԥ��ֿ��б���
							 * ������һ��ѭ��������
							 */
							prePaymentList.add(j, prePaymentDto);
						}
						accItemDto.setSetOffAmount(accItemDto.getValue());
						accItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						//������ϸ�ж�Ӧ�ķ��ü�¼���к�(�����)
						financeMapDto.setMinusReferId(accItemDto.getAiNO());
						//������ϸ�ж�Ӧ��֧����¼���к�(�����)
						financeMapDto.setPlusReferId(prePaymentDto.getSeqNo());
						//����������ϸ�б�
						financeMapList.add(financeMapDto);

                                                //zyg 2006.11.20 begin
                                                /******************************************************
                                                //����ѭ��������������ϸ
                                                if(i == feeList.size()-1 && prePaymentList.size() == 0
                                                   && paymentList.size() > 0)
                                                        continue;
                                                /******************************************************/

						continue outer;
					}
					/*
					 ������ý�����Ԥ��ֿ۽������ʽ�����Ԥ��ֿ۽�
					 ���ý���ȥԤ��ֿ۽�����������һ��ѭ���������ʡ�
					 */
					if (Math.abs(value) > Math.abs(prePaymentDto.getAmount()))
					{
                                          //������ϸ�����ʽ��Ϊ֧����ϸ��֧�����
                                          //zyg 2006.11.20 begin
                                          //financeMapDto.setValue(Math.abs(prePaymentDto.getAmount()));
                                          financeMapDto.setValue(prePaymentDto.getAmount());
                                          //zyg 2006.11.20 end

						//�������ʵ�Ԥ��ֿۼ�¼ɾ��
						prePaymentList.remove(j);
						//�����ý���ȥԤ��ֿ۽����������Ϊ���µġ����ý��
//						accItemDto.setValue(accItemDto.getValue() -
//											prePaymentDto.getAmount());
						accItemDto.setSetOffAmount(BusinessUtility.doubleFormat(accItemDto.getSetOffAmount() + prePaymentDto.getAmount()));
						//accItemDto.setSetOffAmount((double) Math.floor(accItemDto.getSetOffAmount()*100 + prePaymentDto.getAmount()*100+0.01)/100);
						accItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_P);
						/**
						 * �����µġ����ý��ķ��ü�¼���²�������б���
						 * ������һ��ѭ��������
						 */

				//		feeList.add(i + 1, accItemDto);  modify by david.Yang
						i--;                             //add by david.Yang
						//������ϸ�ж�Ӧ�ķ��ü�¼���к�(�����)
						financeMapDto.setMinusReferId(accItemDto.getAiNO());
						//������ϸ�ж�Ӧ��֧����¼���к�(�����)
						financeMapDto.setPlusReferId(prePaymentDto.getSeqNo());
						//����������ϸ�б�
						financeMapList.add(financeMapDto);
						//����ѭ��������������ϸ

                                                //zyg 2006.11.20 begin
                                                /********************************************************
                                                if(i == feeList.size()-1 && prePaymentList.size() == 0
                                                   && paymentList.size() > 0)
                                                        continue;
                                                /********************************************************/

						continue outer;
					}
				}
			}
			//�����з���/֧����¼������
			for(int j=0; j<paymentList.size(); j++)
			{
				PaymentRecordDTO paymentDto = (PaymentRecordDTO)paymentList.get(j);
				LogUtility.log(clazz,LogLevel.DEBUG,"createFinanceMapList(paymentList)",paymentDto);

				//֧�����Ϊ��ļ�¼����������
				//if (paymentDto.getAmount() == 0)
				if (Math.abs(paymentDto.getAmount())<0.0001)
					continue;

				//��������Ч��֧����¼
				if (CommonConstDefinition.AISTATUS_INVALIDATE.equals(
					paymentDto.getStatus()))
					continue;

                                //zyg 2006.11.20 begin
                                //���ý��������ű�����֧��������������ͬ
                                /******************************************************/
                                if(value*paymentDto.getAmount() < 0)
                                  continue;
                                /******************************************************/
                                //zyg 2006.11.20 end

				//����Ԥ�治��������
/*				if(CommonConstDefinition.PAYMENTRECORD_TYPE_INITIATIVEPRESAVE.equals(paymentDto.getPayType()))
					continue;*/
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
				financeMapDto.setAcctId(accItemDto.getAcctID());
				financeMapDto.setCustId(accItemDto.getCustID());
				financeMapDto.setOpId(accItemDto.getOperatorID());
				financeMapDto.setOrgId(accItemDto.getOrgID());
				//������ϸ��״̬Ϊ��������
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//�������ʹ�����¼����Ϊ"֧��"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_P);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);

				System.out.println("accItemDto.getForcedDepositFlag()============="+accItemDto.getForcedDepositFlag());
				System.out.println("paymentDto.getPayType()========="+paymentDto.getPayType());

                                //��֧���б��е�Ԥ��ȫ��������Ԥ��ʱ
                                //zyg 2006.11.20 begin
                                /*******************************************************************************
				if(CommonConstDefinition.YESNOFLAG_NO.equals(accItemDto.getForcedDepositFlag())
					&& CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType()))
				{
					//ȷ��������ǿ��Ԥ���ǿ��Ԥ��������
					if(i != feeList.size()-1)
						continue;
//					LogUtility.log(clazz,LogLevel.DEBUG,"j: "+j);
//					LogUtility.log(clazz,LogLevel.DEBUG,"paymentDto.getSeqNo(): "+paymentDto.getSeqNo());
					if(String.valueOf(paymentDto.getSeqNo()) == null ||
						paymentDto.getSeqNo() == 0)
					{
						payNO--;
						paymentList.remove(j);
						paymentDto.setSeqNo(payNO);
						paymentList.add(j,paymentDto);
						PaymentRecordDTO paymentDto1 = getPaymentDto(paymentDto);
						payRecordList.add(paymentDto1);
						LogUtility.log(clazz,LogLevel.DEBUG,"j: "+j);
						LogUtility.log(clazz,LogLevel.DEBUG,"paymentList.size(): "+paymentList.size());
					}
				}
                                /*******************************************************************************/
                                //zyg 2006.11.20 end

				/*
				 ֻ�е�ѭ�������еķ��ü�¼��ǿ��Ԥ���ǡ�֧����¼��֧������
				 ��ָ��ͬ�������ͣ����磺��ָ��Ԥ�桱����ָ������֧������ʱ
				 �ſ��Խ���������ϸ��
				*/


				System.out.println("accItemDto.getForcedDepositFlag()=========="+accItemDto.getForcedDepositFlag());
				System.out.println("paymentDto.getPayType()============"+paymentDto.getPayType());
				System.out.println((CommonConstDefinition.YESNOFLAG_YES.equals(accItemDto.getForcedDepositFlag())
					&& CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType()))
					|| (CommonConstDefinition.YESNOFLAG_NO.equals(accItemDto.getForcedDepositFlag())
					&& !CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType())));


				if((CommonConstDefinition.YESNOFLAG_YES.equals(accItemDto.getForcedDepositFlag())
					&& CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType()))
					|| (CommonConstDefinition.YESNOFLAG_NO.equals(accItemDto.getForcedDepositFlag())
					&& !CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType())))
				{
					//������ʷ�����ǿ��Ԥ�棬��������ϸ����������Ϊ��ǿ��Ԥ�桱
					if(CommonConstDefinition.YESNOFLAG_YES.equals(accItemDto.getForcedDepositFlag()))
						financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_F);
					//������ʷ����Ƿ�Ԥ�棬��������ϸ����������Ϊ��ֱ�����ʡ�
					if(CommonConstDefinition.YESNOFLAG_NO.equals(accItemDto.getForcedDepositFlag()))
						financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
					//����һ�βμ����ʵ�֧����¼������һ��֧���б���
					if(String.valueOf(paymentDto.getSeqNo()) == null ||
						paymentDto.getSeqNo() == 0)
					{
						payNO--;
						/*
					     Ϊ�˸�������ϸ������ϵ����������֧����¼���кţ�
					     ���ݳ־û�ʱ������������֧����¼���кš�
					    */
						paymentDto.setSeqNo(payNO);
						PaymentRecordDTO paymentDto1 = getPaymentDto(paymentDto);
						payRecordList.add(paymentDto1);
					}

					//Ԥ���˷Ѳ���������
					if (CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE.equals(paymentDto.getPayType()))
						continue;
					//������ü�¼�Ѿ����ʣ����ٽ�������
					if (CommonConstDefinition.SETOFFFLAG_D.equals(accItemDto.getSetOffFlag()))
						continue outer;
					/*
					 ������ý�����õ���֧��������һ��һ�����ʼ�¼��
					 ���ʽ����ڷ��ý�
					 ������ý��С��֧������֧������ȥ���ý�����
					 ������һ��ѭ���������ʡ�
					*/

					System.out.println("paymentDto.getAmount()========"+paymentDto.getAmount());

					if(Math.abs(value) <= Math.abs(paymentDto.getAmount()))
					{
                                          //������ϸ�����ʽ��Ϊ������ϸ�ķ��ý��
                                          //zyg 2006.11.20 begin
                                          //ע�����˽�����������
                                          //financeMapDto.setValue(Math.abs(value));
                                          financeMapDto.setValue(value);
                                          //zyg 2006.11.20 end

						paymentList.remove(j);
						if(Math.abs(value) < Math.abs(paymentDto.getAmount()))
						{
							//��Ԥ��/֧������ȥ���ý����������Ϊ���µġ�Ԥ��/֧�����
							paymentDto.setAmount(BusinessUtility.doubleFormat(paymentDto.getAmount() - value));   //modify by david.yang
							//paymentDto.setAmount((double) Math.floor(paymentDto.getAmount()*100 - value*100+0.01)/100);   //modify by david.yang
									//			 accItemDto.getValue()*100)/100);
							/**
							 * �����µġ�Ԥ��/֧������֧����¼���²���֧���б���
							 * ������һ��ѭ��������
							 */
							paymentList.add(j, paymentDto);
						}
						accItemDto.setSetOffAmount(accItemDto.getValue());
						accItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						//������ϸ�ж�Ӧ�ķ��ü�¼���к�
						financeMapDto.setMinusReferId(accItemDto.getAiNO());
						//������ϸ�ж�Ӧ��֧����¼���к�
						financeMapDto.setPlusReferId(paymentDto.getSeqNo());
						//����������ϸ�б�
						financeMapList.add(financeMapDto);
						//�����µ�֧���б�����©����Ԥ��

						System.out.println("********accItemDto.getValue()*********"+accItemDto.getValue());
						System.out.println("********paymentDto.getAmount()********"+paymentDto.getAmount());
						System.out.println("***********"+i+"***********");
						System.out.println("feeList.size()**************"+feeList.size());

                                                //zyg 2006.11.20 begin
                                                /*****************************************************
                                                if(accItemDto.getValue() == paymentDto.getAmount()
                                                      && i == feeList.size()-1)
                                                {
                                                  LogUtility.log(clazz,LogLevel.DEBUG,"j: "+j);
                                                  LogUtility.log(clazz,LogLevel.DEBUG,"paymentList.size(): "+paymentList.size());
                                                  j--;
                                                  //���˷��ü�¼��Ϊ�������ʡ�
                                                  accItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
                                                  continue;
                                                }
                                                /*****************************************************/
                                                //zyg 2006.11.20 end

						//����ѭ��������������ϸ
						continue outer;
					}
					/*
					 ������ý�����֧���������ʽ�����֧����
					 ���ü�¼�е����ʱ����Ϊ���������ʡ���
					 ���ü�¼�е����ʽ�����֧����
					 ���ý���ȥ֧��������������һ��ѭ���������ʡ�
					*/
					if(Math.abs(value) > Math.abs(paymentDto.getAmount()))
					{
						//������ϸ�����ʽ��Ϊ֧����ϸ��֧�����
                                                //zyg 2006.11.20 begin
                                                //ע�����˽�����������
                                                /***********************************************/
                                                //financeMapDto.setValue(Math.abs(paymentDto.getAmount()));
                                                financeMapDto.setValue(paymentDto.getAmount());
                                                /***********************************************/
                                                //zyg 2006.11.20 end


						//�������ʵ�֧����¼ɾ��
						paymentList.remove(j);
						//�����ý���ȥԤ��/֧�������������Ϊ���µġ����ý��
//						accItemDto.setValue(accItemDto.getValue()-paymentDto.getAmount());
						accItemDto.setSetOffAmount(BusinessUtility.doubleFormat(accItemDto.getSetOffAmount() + paymentDto.getAmount()));
						//accItemDto.setSetOffAmount((double) Math.floor(accItemDto.getSetOffAmount()*100 + paymentDto.getAmount()*100+0.01)/100);
						accItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_P);
						/**
						 * �����µġ����ý��ķ��ü�¼���²�������б���
						 * ������һ��ѭ��������
						 */
					//	feeList.add(i+1,accItemDto);      modify by david.Yang
						i--;                            //�����Է��ý�������  add by david.Yang
						//������ϸ�ж�Ӧ�ķ��ü�¼���к�
						financeMapDto.setMinusReferId(accItemDto.getAiNO());
						//������ϸ�ж�Ӧ��֧����¼���к�
						financeMapDto.setPlusReferId(paymentDto.getSeqNo());
						//����������ϸ�б�
						financeMapList.add(financeMapDto);
						//����ѭ��������������ϸ
						continue outer;
					}
				}
			}
		}


                //zyg 2006.11.20 begin
                //����ʣ���֧����¼�����䴦�������Ԥ�����Ԥ���˷ѣ���Ϊ����Ԥ�����Ԥ���˷Ѳ��ý������˹�ϵ
                //��һ��ֻ��Ϊ�˽����ĳ־û���׼��
                /*******************************************/
                for(int j=0; j<paymentList.size(); j++)
                {
                  PaymentRecordDTO paymentDto = (PaymentRecordDTO) paymentList.get(j);
                  LogUtility.log(clazz, LogLevel.DEBUG,"createFinanceMapList(paymentList)",paymentDto);

                  if(String.valueOf(paymentDto.getSeqNo()) == null ||
                          paymentDto.getSeqNo() == 0)
                  {
                          payNO--;
                          paymentList.remove(j);
                          paymentDto.setSeqNo(payNO);
                          paymentList.add(j,paymentDto);
                          PaymentRecordDTO paymentDto1 = getPaymentDto(paymentDto);
                          payRecordList.add(paymentDto1);

                          LogUtility.log(clazz,LogLevel.DEBUG,"j: "+j);
                          LogUtility.log(clazz,LogLevel.DEBUG,"paymentList.size(): "+paymentList.size());
                  }

                }
                /*******************************************/
                //zyg 2006.11.20 end


                //zyg 2006.11.20 begin
                //�������δ����ķ��ü�¼/֧����¼/Ԥ��ֿۼ�¼
                /*******************************************/
                if(!(feeList.size() == 0))
                  LogUtility.log(clazz,LogLevel.WARN, "feeList size != 0 after process completed : "+feeList.size());

                if(!(paymentList.size() == 0))
                  LogUtility.log(clazz,LogLevel.WARN, "paymentList size != 0 after process completed : "+paymentList.size());

                if(!(prePaymentList.size() == 0))
                  LogUtility.log(clazz,LogLevel.WARN, "prepaymentList size != 0 after process completed : "+prePaymentList.size());
                /*******************************************/
                //zyg 2006.11.20 end


                //���ʺ󣬵õ��µķ����б�
                if(!(accItemList.size() == 0))
                        feeList = accItemList;
                //���ʺ󣬵õ��µ�֧���б�
                if(!(payRecordList.size() == 0))
                        paymentList = payRecordList;
                //���ʺ󣬵õ��µ�Ԥ��ֿ��б�
                if(!(prePayList.size() == 0))
                        prePaymentList = prePayList;

/*		for(int i=0; i<financeMapList.size(); i++)
		{
			FinanceSetOffMapDTO financeMapDto = (FinanceSetOffMapDTO)financeMapList.get(i);
			LogUtility.log(clazz,LogLevel.DEBUG,"financeMapList",financeMapDto);
		}*/
		return financeMapList;
	}

	/**
	 * ������ͬ��Ŀ����֮������ʹ�ϵ
	 * ��������Ԥ��ֿ�֮������ʹ�ϵ
	 * ��������֧��������ʹ�ϵ.
	 */
	private void buildSameAcctItemSetOffMap()
	{
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + "buildSameAcctItemSetOffMap");		
		ArrayList accItemList = new ArrayList();
		//ͬ��Ŀ���ͷ������ʺ�ķ����б�.
		
		//����Ŀ��������
		AccountItemDTO[] accItemDto3 = new AccountItemDTO[feeList.size()];
		for(int i=0; i<feeList.size(); i++)
		{
			accItemDto3[i] = (AccountItemDTO)feeList.get(i);
		}
		
		AccountItemDTO accItemDto1 = new AccountItemDTO();
		AccountItemDTO accItemDto2 = new AccountItemDTO();
		AccountItemDTO accItemDto4 = new AccountItemDTO();
		
		for(int i=0;i<feeList.size();i++)
		{
			for(int j=1;j<i-1;j++)
			{
				accItemDto1 = (AccountItemDTO) accItemDto3[j];
				accItemDto2 = (AccountItemDTO) accItemDto3[j + 1];
				
				String value1=accItemDto1.getAcctItemTypeID();
				String value2=accItemDto2.getAcctItemTypeID();
				
				if (value1.compareTo(value2)>0)
				{
					accItemDto4 = accItemDto1;
					accItemDto1 = accItemDto2;
					accItemDto2 = accItemDto4;
				}
				accItemDto3[j] = accItemDto1;
				accItemDto3[j+1] = accItemDto2;
			}			
		}
		//	�õ������ķ����б�
		for(int i=0; i<feeList.size(); i++)
		{
			accItemList.add(accItemDto3[i]);
		}
		
		String acctItem1;
		String acctItem2;
		//������ͬ��Ŀ���͵ķ��õ�����
		loop1:for(int i = 0;i<accItemList.size();i++)
		{
			accItemDto1 = (AccountItemDTO)accItemList.get(i);
			acctItem1 = accItemDto1.getAcctItemTypeID();
			
			long lsetOffAmount1 = double2long(accItemDto1.getValue()) - double2long(accItemDto1.getSetOffAmount());
			loop2:for(int j = 0;j<accItemList.size();j++)
			{
				accItemDto2 = (AccountItemDTO)accItemList.get(i);
				acctItem2 = accItemDto2.getAcctItemTypeID();
				
				if(CommonConstDefinition.SETOFFFLAG_D.equals(accItemDto1.getSetOffFlag()))
					continue loop1;//������������Ѿ�����,��ô�˳�ѭ��1

				if(CommonConstDefinition.SETOFFFLAG_D.equals(accItemDto2.getSetOffFlag()))
					continue loop2;//������������Ѿ�����,��ô�˳�ѭ��2

				if(acctItem2.compareTo(acctItem1)!=0) 
					continue loop2;//����ͬ��Ŀ���͵Ĳ�������

				long lsetOffAmount2 = double2long(accItemDto2.getValue()) - double2long(accItemDto2.getSetOffAmount());
				
				if(lsetOffAmount1 * lsetOffAmount2 >= 0 )
					continue loop2;//ͬ�Ų���������
				
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
				financeMapDto.setAcctId(accItemDto1.getAcctID());
				financeMapDto.setCustId(accItemDto1.getCustID());
				financeMapDto.setOpId(accItemDto1.getOperatorID());
				financeMapDto.setOrgId(accItemDto1.getOrgID());
				//������ϸ����������Ϊ��ֱ�����ʡ�
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//������ϸ��״̬Ϊ��������
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);					

				if( lsetOffAmount1 >0 ) //������������д����DTO
				{
					financeMapDto.setPlusReferId(accItemDto1.getAcctID());
					financeMapDto.setMinusReferId(accItemDto2.getAcctID());
				}
				else
				{	
					financeMapDto.setMinusReferId(accItemDto1.getAcctID());
					financeMapDto.setPlusReferId(accItemDto2.getAcctID());
				}
				
				if(Math.abs(lsetOffAmount1) > Math.abs(lsetOffAmount2))
				{
					//����2��ȫ����
					accItemList.remove(j);
					accItemDto2.setSetOffAmount(accItemDto2.getValue());
					accItemDto2.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
					accItemList.add(j,accItemDto2);

					//����1��������				
					accItemList.remove(i);
					accItemDto1.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_P);
					accItemDto1.setSetOffAmount((double)(double2long(accItemDto1.getSetOffAmount()) - lsetOffAmount2)/100);
					accItemList.add(i,accItemDto1);
					
					financeMapDto.setValue((double)Math.abs(lsetOffAmount2)/100);
					financeMapList.add(financeMapDto); //������ϸ����
					
					continue loop1; //����1������ȫ����ѭ��
				}
				else
				{
					//����1��ȫ����
					accItemList.remove(i);
					accItemDto1.setSetOffAmount(accItemDto1.getValue());
					accItemDto1.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
					accItemList.add(i,accItemDto1);
					
					if((lsetOffAmount1 + lsetOffAmount2) == 0)
					{
						//���������ͬ����ô����2Ҳ��ȫ����
						accItemList.remove(j);
						accItemDto2.setSetOffAmount(accItemDto2.getValue());
						accItemDto2.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						accItemList.add(j,accItemDto1);
					}
					else
					{
						//����2��������
						accItemList.remove(j);
						accItemDto1.setSetOffAmount((double)(double2long(accItemDto2.getSetOffAmount()) - lsetOffAmount1)/100);
						accItemDto2.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_P);
						accItemList.add(j,accItemDto1);						
					}
					
					financeMapDto.setValue((double)Math.abs(lsetOffAmount1)/100);
					financeMapList.add(financeMapDto); //������ϸ����
				}
			}
		}
		feeList.clear();
		feeList.addAll(accItemList);//�ؽ������б�
		
		
		/** ע���:
		 * �����������ʺ�Ԥ��ֿ��б����г־û�����,���Կ�����������ʱ����Amount,
		 * ������һ������.
		*/
		loop4:for (int i =  0 ;i < prePaymentList.size();i++)
		{	
			PrepaymentDeductionRecordDTO prePaymentDto1 = (
					PrepaymentDeductionRecordDTO) prePaymentList.get(i);
			long lValue1 = double2long(prePaymentDto1.getAmount());
			loop3:for(int j = 0;j<prePaymentList.size();j++)
			{
				PrepaymentDeductionRecordDTO prePaymentDto2 = (
						PrepaymentDeductionRecordDTO) prePaymentList.get(j);
				long lValue2 = double2long(prePaymentDto2.getAmount());
				
				if(lValue1*lValue2>0) continue loop3; 
				//ͬ�Ų���������
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
				financeMapDto.setAcctId(prePaymentDto1.getAcctId());
				financeMapDto.setCustId(prePaymentDto1.getCustId());
				financeMapDto.setOpId(prePaymentDto1.getOpId());
				financeMapDto.setOrgId(prePaymentDto1.getOrgId());
				//������ϸ����������Ϊ��ֱ�����ʡ�
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//������ϸ��״̬Ϊ��������
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_D);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_D);					
				
				if(lValue1 > 0)
				{
					financeMapDto.setPlusReferId(prePaymentDto1.getSeqNo());
					financeMapDto.setMinusReferId(prePaymentDto2.getSeqNo());
				}
				else
				{
					financeMapDto.setPlusReferId(prePaymentDto2.getSeqNo());
					financeMapDto.setMinusReferId(prePaymentDto1.getSeqNo());
				}
				
				if(Math.abs(lValue1)>Math.abs(lValue2))
				{
					//Ԥ��ֿ�2 ��ȫ�ֿ�
					//Ԥ��ֿ�1 ����ȫ�ֿ�
					prePaymentDto1.setAmount((double)(double2long(prePaymentDto1.getAmount()) + lValue2 )/100);
					prePaymentDto2.setAmount( 0.0f );
					prePaymentList.remove(i);
					prePaymentList.add(i,prePaymentDto1);
					
					financeMapDto.setValue((double)Math.abs(lValue2)/100);
					financeMapList.add(financeMapDto);
					continue loop3;
				}
				else
				{
					//Ԥ��ֿ�1 ��ȫ�ֿ�
					if(Math.abs(Math.abs(lValue1)-Math.abs(lValue2))==0)
					{
						//Ԥ��ֿ�2��ȫ�ֿ�
						prePaymentDto1.setAmount(0.0f);
						prePaymentDto2.setAmount(0.0f);
					}
					else
					{
						//Ԥ��ֿ�2����ȫ�ֿ�
						prePaymentDto1.setAmount(0.0f);
						prePaymentDto2.setAmount((double)(double2long(prePaymentDto2.getAmount()) + lValue1));
					}
					
					prePaymentList.remove(i);
					prePaymentList.add(i,prePaymentDto1);
					prePaymentList.remove(j);
					prePaymentList.add(j,prePaymentDto2);
					financeMapDto.setValue((double)Math.abs(lValue1)/100);
					financeMapList.add(financeMapDto);
					continue loop4;
				}
			}
		}
		
		/**
		 * ע�� 
		 * ֧����֧�������ʹ�ϵ������,֧����¼����Ҫ�־û�,
		 * ���ڴ˻����֧����¼�Ľ��,���Ժ����������Ӱ��.
		 * */
		
		loop5:for(int i=0;i<paymentList.size();i++)
		{
			PaymentRecordDTO paymentRecordDTO1 = (PaymentRecordDTO)paymentList.get(i);
			long lValue1 = double2long(paymentRecordDTO1.getAmount());

			loop6:for(int j=0;j<paymentList.size();j++)
			{
				PaymentRecordDTO paymentRecordDTO2 = (PaymentRecordDTO)paymentList.get(j);
				long lValue2 = double2long(paymentRecordDTO2.getAmount());
				
				if(lValue1*lValue2 >= 0) continue loop6;
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				//����������ϸ�Ŀͻ����ʻ�������Ա��������������Ϣ
				financeMapDto.setAcctId(paymentRecordDTO1.getAcctID());
				financeMapDto.setCustId(paymentRecordDTO1.getCustID());
				financeMapDto.setOpId(paymentRecordDTO1.getOpID());
				financeMapDto.setOrgId(paymentRecordDTO1.getOrgID());
				//������ϸ����������Ϊ��ֱ�����ʡ�
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//������ϸ��״̬Ϊ��������
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_P);
				//�������ʹ�����¼����Ϊ"����"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_P);					

				if(lValue1 > 0)
				{
					financeMapDto.setPlusReferId(paymentRecordDTO1.getSeqNo());
					financeMapDto.setMinusReferId(paymentRecordDTO2.getSeqNo());
				}
				else
				{
					financeMapDto.setPlusReferId(paymentRecordDTO1.getSeqNo());
					financeMapDto.setMinusReferId(paymentRecordDTO2.getSeqNo());
					
				}
				
				if(Math.abs(lValue1)>Math.abs(lValue2))
				{
					paymentRecordDTO1.setAmount( (double)(double2long(paymentRecordDTO1.getAmount()) + lValue2)/100);
					paymentRecordDTO2.setAmount( 0 );
					financeMapDto.setValue((double)Math.abs(lValue2)/100);
					
					paymentList.remove(i);
					paymentList.add(i,paymentRecordDTO1);
					paymentList.remove(j);
					paymentList.add(j,paymentRecordDTO2);				
					financeMapList.add(financeMapDto);
					continue loop5;
				}
				else
				{	
					paymentRecordDTO1.setAmount(0);
					financeMapDto.setValue((double)Math.abs(lValue1/100));
					if(Math.abs(lValue1)==Math.abs(lValue2))
					{						
						paymentRecordDTO2.setAmount(0.0f);
					}
					else
					{
						paymentRecordDTO2.setAmount((double)(double2long(paymentRecordDTO2.getAmount()) + lValue1)/100 );
					}
				}
				paymentList.remove(i);
				paymentList.add(i,paymentRecordDTO1);
				paymentList.remove(j);
				paymentList.add(j,paymentRecordDTO2);				
				financeMapList.add(financeMapDto);
			}
		}		
	}
	/**
	 * �Է����б�֧���б�Ԥ��ֿ��б�����С��������
	 * ����ð�������㷨
	 */
	private void listArrange()
	{
		//�����ķ����б�
		ArrayList accItemList = new ArrayList();
		AccountItemDTO[] accItemDto3 = new AccountItemDTO[feeList.size()];
		for(int i=0; i<feeList.size(); i++)
		{
			accItemDto3[i] = (AccountItemDTO)feeList.get(i);
		}
		//�������б�����С��������
		AccountItemDTO accItemDto1 = new AccountItemDTO();
		AccountItemDTO accItemDto2 = new AccountItemDTO();
		AccountItemDTO accItemDto4 = new AccountItemDTO();
		if(feeList.size() > 1)//��������б���ֻ��һ����ϸ����������
		{
			for (int i = feeList.size(); i > 1; i--)
			{
				for (int j = 0; j < i - 1; j++)
				{
					accItemDto1 = (AccountItemDTO) accItemDto3[j];
					accItemDto2 = (AccountItemDTO) accItemDto3[j + 1];
					double value1 = 0.0f;
					double value2 = 0.0f;
					/*
					 �������б���ǰһ�����ý���ֵ���һ�����ý���ֵ���бȽ�
					 ���ǰ�ߴ��ں��ߣ�������û���
					*/
					value1 = accItemDto1.getValue();
					value2 = accItemDto2.getValue();
					if (value1 > value2)
					{
						accItemDto4 = accItemDto1;
						accItemDto1 = accItemDto2;
						accItemDto2 = accItemDto4;
					}
					accItemDto3[j] = accItemDto1;
					accItemDto3[j+1] = accItemDto2;
				}
			}
			//�õ������ķ����б�
			for(int i=0; i<feeList.size(); i++)
			{
				accItemList.add(accItemDto3[i]);
			}
			feeList = accItemList;
		}
		//������֧���б�
		ArrayList payRecordList = new ArrayList();
		PaymentRecordDTO[] paymentDto3 = new PaymentRecordDTO[paymentList.size()];
		for(int i=0; i<paymentList.size(); i++)
		{
			paymentDto3[i] = (PaymentRecordDTO)paymentList.get(i);
		}
		//��֧���б�����С��������
		PaymentRecordDTO paymentDto1 = new PaymentRecordDTO();
		PaymentRecordDTO paymentDto2 = new PaymentRecordDTO();
		PaymentRecordDTO paymentDto4 = new PaymentRecordDTO();
		if(paymentList.size() > 1)//���֧���б���ֻ��һ����ϸ����������
		{
			for (int i = paymentList.size(); i > 1; i--)
			{
				for (int j = 0; j < i - 1; j++)
				{
					paymentDto1 = (PaymentRecordDTO) paymentDto3[j];
					paymentDto2 = (PaymentRecordDTO) paymentDto3[j + 1];
					int mopID1 = 0;
					int mopID2 = 0;
					double amount1 = 0.0f;
					double amount2 = 0.0f;
					/*
					 ��֧���б����ѷ�ʽ�ı�ŴӴ�С����
					 ������ͬ���ѷ�ʽ��֧���б�����С��������
					*/
					mopID1 = paymentDto1.getMopID();
					mopID2 = paymentDto2.getMopID();
					amount1 = paymentDto1.getAmount();
					amount2 = paymentDto2.getAmount();
					if (mopID1 < mopID2)
					{
						paymentDto4 = paymentDto1;
						paymentDto1 = paymentDto2;
						paymentDto2 = paymentDto4;
					}
					if(mopID1 == mopID2)
					{
						if(amount1 > amount2)
						{
							paymentDto4 = paymentDto1;
							paymentDto1 = paymentDto2;
							paymentDto2 = paymentDto4;
						}
					}
					paymentDto3[j] = paymentDto1;
					paymentDto3[j+1] = paymentDto2;
				}
			}
			//�õ�������֧���б�
			for(int i=0; i<paymentList.size(); i++)
			{
				payRecordList.add(paymentDto3[i]);
			}
			paymentList = payRecordList;
		}
		//������Ԥ��ֿ��б�
		ArrayList payPayList = new ArrayList();
		PrepaymentDeductionRecordDTO[] prePaymentDto3 = new PrepaymentDeductionRecordDTO[prePaymentList.size()];
		for(int i=0; i<prePaymentList.size(); i++)
		{
			prePaymentDto3[i] = (PrepaymentDeductionRecordDTO)prePaymentList.get(i);
		}
		//��Ԥ��ֿ��б�����С��������
		PrepaymentDeductionRecordDTO prePaymentDto1 = new PrepaymentDeductionRecordDTO();
		PrepaymentDeductionRecordDTO prePaymentDto2 = new PrepaymentDeductionRecordDTO();
		PrepaymentDeductionRecordDTO prePaymentDto4 = new PrepaymentDeductionRecordDTO();
		if(prePaymentList.size() > 1)//���Ԥ��ֿ��б���ֻ��һ����ϸ����������
		{
			for (int i = prePaymentList.size(); i > 1; i--)
			{
				for (int j = 0; j < i - 1; j++)
				{
					prePaymentDto1 = (PrepaymentDeductionRecordDTO) prePaymentDto3[j];
					prePaymentDto2 = (PrepaymentDeductionRecordDTO) prePaymentDto3[j + 1];
					double amount1 = 0.0f;
					double amount2 = 0.0f;
					/*
					 ��Ԥ��ֿ��б���ǰһ��֧������ֵ���һ��֧������ֵ���бȽ�
					 ���ǰ�ߴ��ں��ߣ�������û���
					*/
					amount1 = prePaymentDto1.getAmount();
					amount2 = prePaymentDto2.getAmount();
					if (amount1 > amount2)
					{
						prePaymentDto4 = prePaymentDto1;
						prePaymentDto1 = prePaymentDto2;
						prePaymentDto2 = prePaymentDto4;
					}
					prePaymentDto3[j] = prePaymentDto1;
					prePaymentDto3[j+1] = prePaymentDto2;
				}
			}
			//�õ�������Ԥ��ֿ��б�
			for(int i=0; i<prePaymentList.size(); i++)
			{
				payPayList.add(prePaymentDto3[i]);
			}
			prePaymentList = payPayList;
		}
	}

	/**
	 * ���ڷ��á�֧����Ԥ��ֿۡ����ʱ����ݵĳ־û�
	 * @param financeMapList
	 * @throws ServiceException
	 */
	public void payFeeDB(Collection financeMapList)throws ServiceException
	{
/*		System.out.println("financeMapList.size: "+financeMapList.size());
		for(int i=0; i<financeMapList.size(); i++)
		{
			FinanceSetOffMapDTO financeMapDto = (FinanceSetOffMapDTO)((ArrayList)financeMapList).get(i);
			LogUtility.log(clazz,LogLevel.DEBUG,"financeMapList",financeMapDto);
		}
*/
//		this.financeMapList = (ArrayList)financeMapList;
//		this.setFinanceMapList((ArrayList)financeMapList);
		//�������ü�¼
//		createAmountItem();
		//����֧����¼
//		createPaymentRecord();
		//����Ԥ��ֿۼ�¼,ͬʱ�����ʻ���Ϣ
//		if(prePaymentList != null)
//			createPrePaymentDeductionRecord();
/*		System.out.println("financeMapList1.size: "+this.financeMapList.size());
		for(int i=0; i<this.financeMapList.size(); i++)
		{
			FinanceSetOffMapDTO financeMapDto = (FinanceSetOffMapDTO)this.financeMapList.get(i);
			LogUtility.log(clazz,LogLevel.DEBUG,"financeMapList1",financeMapDto);
		}*/
		//�������ʼ�¼
		updateAcctItem();
		updatePayment();
		
//		createFinanceSetoffMap(financeMapList);
//		createFinanceSetoffMap();
	}

	private void updateAcctItem()
	{
		try
		{
			for(int i=0; i<feeList.size(); i++)
			{
				AccountItemDTO accItemDto = (AccountItemDTO)feeList.get(i);
				accItemDto.setSetOffAmount(accItemDto.getValue());
				accItemDto.setSetOffFlag("D");
				PayFeeUtil.updateAcctItemStatus(accItemDto,false);
			}
		}
		catch(ServiceException ex)
		{
			System.out.println("�������ʽ��,���ʱ��ʱ��������");
			ex.printStackTrace();
		}
	}
	
	private void updatePayment()
	{
		try
		{
			if(paymentList!=null){
				PaymentRecordHome paymentHome = HomeLocater.getPaymentRecordHome();
				for(int i=0; i<paymentList.size(); i++)
				{
					PaymentRecordDTO paymentDto = (PaymentRecordDTO)paymentList.get(i);
					PaymentRecord paymentRecord=paymentHome.findByPrimaryKey(new Integer(paymentDto.getSeqNo()));
					paymentRecord.setFaPiaoNo(paymentRecord.getOperatorId()+"--"+this.id);
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println("���÷�Ʊ����ʱ��������");
			ex.printStackTrace();
		}
	}
	/**
	 * �������ü�¼
	 * @throws ServiceException
	 */
	private void createAmountItem()throws ServiceException
	{
		//���ü�¼���к�
		int aiNO = 0;
		//������ϸ�б�
		ArrayList mapList = new ArrayList();
		//�������ü�¼��������������������ϸ��Ӧ�ķ��ü�¼���к�
		for(int i=0; i<feeList.size(); i++)
		{
			AccountItemDTO accItemDto = (AccountItemDTO)feeList.get(i);
			
			//�жϷ��ü�¼�Ƿ��Ѿ��־û�
			if(accItemDto.getAiNO() > 0)
			{
				//  ��createFinanceSetoffMap��������ͳһ��ת״̬ modify by david.Yang 2006-11-9
				//  PayFeeUtil.updateAcctItemStatus(accItemDto);
				continue;  //���ü�¼�Ѿ��־û�
			}
			//if(accItemDto.getSetOffAmount() != accItemDto.getValue())
			if(Math.abs(accItemDto.getSetOffAmount() - accItemDto.getValue())>0.0001)	
			{
				accItemDto.setSetOffAmount(accItemDto.getValue());
				accItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
			}
			accItemDto.setCreateTime(TimestampUtility.getCurrentDate());
			accItemDto.setInvoiceFlag(CommonConstDefinition.YESNOFLAG_NO);
//			if(CommonConstDefinition.YESNOFLAG_YES.equals(accItemDto.getForcedDepositFlag()))
				accItemDto.setStatus(CommonConstDefinition.AISTATUS_POTENTIAL);
			//�������ü�¼
			aiNO = PayFeeUtil.createAmountItemDTO(accItemDto);

			for(int j=0; j<financeMapList.size(); j++)
			{
				FinanceSetOffMapDTO financeMapDto = (FinanceSetOffMapDTO)financeMapList.get(j);

				if(accItemDto.getAiNO() == financeMapDto.getMinusReferId()&&
						CommonConstDefinition.SETOFFREFERTYPE_F.equals(financeMapDto.getMinusReferType()))
				{
					financeMapDto.setMinusReferId(aiNO);
//					mapList.add(financeMapDto);
				}
				//�������Żݷ���ʱ
				//zyg 2006.11.10 begin
				/**********************************************************************************
				if(CommonConstDefinition.FTCREATINGMETHOD_B.equals(accItemDto.getCreatingMethod())
				   && accItemDto.getAiNO() == financeMapDto.getPlusReferId()
				   && accItemDto.getValue() < 0)
				{
					financeMapDto.setPlusReferId(aiNO);
//					mapList.add(financeMapDto);
				}
				/**********************************************************************************/

				//zyg 2006.11.10 begin
				//����¼�Ĳ�����ԴΪ �Ʒ� �� ���ʣ��ҽ��С��0 ��ʱ�򣬽������˹�ϵ
				/**********************************************************************************/

				if((CommonConstDefinition.FTCREATINGMETHOD_B.equals(accItemDto.getCreatingMethod()) ||
						   CommonConstDefinition.FTCREATINGMETHOD_T.equals(accItemDto.getCreatingMethod()))
					 &&	accItemDto.getAiNO() == financeMapDto.getPlusReferId()
				   && accItemDto.getValue() < 0)
				{
					financeMapDto.setPlusReferId(aiNO);
//					mapList.add(financeMapDto);
				}
				/**********************************************************************************/

				mapList.add(financeMapDto);
			}
		}
		if(!(mapList.size() == 0))
			financeMapList = mapList;
	}

	/**
	 * ����֧����¼,ͬʱ�����ʻ���Ϣ��������Ϣ
	 * @throws ServiceException
	 */
	private void createPaymentRecord()throws ServiceException
	{
		//֧����¼���к�
		int payNO = 0;
		//������ϸ�б�
		ArrayList mapList = new ArrayList();
		//����֧����¼��������������������ϸ��Ӧ��֧����¼���к�
		for(int i=0; i<paymentList.size(); i++)
		{
			PaymentRecordDTO paymentDto = (PaymentRecordDTO)paymentList.get(i);
			//�жϷ��ü�¼�Ƿ��Ѿ��־û�
			if(paymentDto.getSeqNo() > 0)
				continue;  //֧����¼�Ѿ��־û�
			if(paymentDto.getInvoicedFlag() == null)
				paymentDto.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
			//Ԥ��֧����¼����֧��״̬Ϊ��Ԥ���ɡ�
			if(CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType()))
				paymentDto.setStatus(CommonConstDefinition.AISTATUS_POTENTIAL);
			//��Ԥ��֧����¼������֧������֧��״̬Ϊ����Ч��
//			if(!CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType()))
//				paymentDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			paymentDto.setCreateTime(TimestampUtility.getCurrentDate());
			paymentDto.setPaymentTime(TimestampUtility.getCurrentDate());
			paymentDto.setPrepaymentType(getPrepaymentType(paymentDto.getMopID()));
			//����֧����¼
			payNO = PayFeeUtil.createPaymentRecordDTO(paymentDto);
			LogUtility.log(clazz,LogLevel.DEBUG,"֧����¼���к�: "+payNO);
			for(int j=0; j<financeMapList.size(); j++)
			{
				FinanceSetOffMapDTO financeMapDto = (FinanceSetOffMapDTO)financeMapList.get(j);
				LogUtility.log(clazz,LogLevel.DEBUG,"paymentDto.getSeqNo(): "+paymentDto.getSeqNo());
				LogUtility.log(clazz,LogLevel.DEBUG,"financeMapDto.getPlusReferId(): "+financeMapDto.getPlusReferId());
				if(paymentDto.getSeqNo() == financeMapDto.getPlusReferId()&&
						CommonConstDefinition.SETOFFREFERTYPE_P.equals(financeMapDto.getPlusReferType()))
				{
					if(!CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE.equals(paymentDto.getPayType()))
						financeMapDto.setPlusReferId(payNO);
					financeMapDto.setStatus(paymentDto.getStatus());
					//֧����¼������֧�������״̬Ϊ����Ч��������Ӧ�ķ��ü�¼״̬����Ϊ����Ч��
					if (CommonConstDefinition.AISTATUS_VALIDATE.equals(paymentDto.getStatus()))
						PayFeeUtil.updateAcctItemStatus(financeMapDto.getMinusReferId(),financeMapDto.getAcctId());
				}
				mapList.add(financeMapDto);
			}
			//�����ʻ���Ϣ
			if(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE.equals(paymentDto.getPayType()))
			{
				paymentDto.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_CASH);
				PayFeeUtil.updateAccount(paymentDto.getAcctID()
										 , paymentDto.getAmount()
										 , paymentDto.getPrepaymentType());
			}
		}
		if(!(mapList.size() == 0))
			financeMapList = mapList;
	}

	/**
	 * @param mopID
	 * @return
	 */
	private String getPrepaymentType(int mopID) throws ServiceException {
		String cashFlag;
		 try{
		 	MethodOfPaymentHome mePayHome = HomeLocater.getMethodOfPaymentHome();

		 	MethodOfPayment mp = mePayHome.findByPrimaryKey(new Integer(mopID));

		 	cashFlag = mp.getCashFlag();



		 }catch(HomeFactoryException e)
			{
			LogUtility.log(clazz,LogLevel.WARN,"���ķ��ü�¼��Ϣ����ԭ�򣺷��ü�¼��λ����");
			throw new ServiceException("���ķ��ü�¼��Ϣ����ԭ�򣺷��ü�¼��λ����");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"���ķ��ü�¼��Ϣ����ԭ�򣺷��ü�¼���ҳ���");
			throw new ServiceException("���ķ��ü�¼��Ϣ����ԭ�򣺷��ü�¼���ҳ���");
		}
		if("Y".equalsIgnoreCase(cashFlag))
	 		return "C";
		else
			return "T";
	}
	/**
	 * ����Ԥ��ֿۼ�¼,ͬʱ�����ʻ���Ϣ��������Ϣ
	 * @throws ServiceException
	 */
	private void createPrePaymentDeductionRecord()throws ServiceException
	{
		//Ԥ��ֿۼ�¼���к�
		int prePayNO = 0;
		//������ϸ�б�
		ArrayList mapList = new ArrayList();
		//����Ԥ��ֿۼ�¼��������������������ϸ��Ӧ��Ԥ��ֿۼ�¼���к�
		for(int i=0; i<prePaymentList.size(); i++)
		{
			PrepaymentDeductionRecordDTO prePaymentDto = (PrepaymentDeductionRecordDTO)prePaymentList.get(i);
			//�ж�Ԥ��ֿۼ�¼�Ƿ��Ѿ��־û�
			if(prePaymentDto.getSeqNo() > 0)
				continue;  //Ԥ��ֿۼ�¼�Ѿ��־û�
			if(prePaymentDto.getInvoicedFlag() == null)
				prePaymentDto.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
			//Ԥ��ֿۼ�¼״̬Ϊ����Ч��
			prePaymentDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			prePaymentDto.setCreateTime(TimestampUtility.getCurrentDateWithoutTime());
			//����Ԥ��ֿۼ�¼
			prePayNO = PayFeeUtil.createPrePaymentDeductionRecordDTO(prePaymentDto);
			LogUtility.log(clazz,LogLevel.DEBUG,"Ԥ��ֿۼ�¼���к�: "+prePayNO);
			for(int j=0; j<financeMapList.size(); j++)
			{
				FinanceSetOffMapDTO financeMapDto = (FinanceSetOffMapDTO)financeMapList.get(j);
				LogUtility.log(clazz,LogLevel.DEBUG,"prePaymentDto.getSeqNo(): "+prePaymentDto.getSeqNo());
				LogUtility.log(clazz,LogLevel.DEBUG,"financeMapDto.getPlusReferId(): "+financeMapDto.getPlusReferId());
				if(prePaymentDto.getSeqNo() == financeMapDto.getPlusReferId()&&
						CommonConstDefinition.SETOFFREFERTYPE_D.equals(financeMapDto.getPlusReferType()))
				{
					financeMapDto.setPlusReferId(prePayNO);
					financeMapDto.setStatus(prePaymentDto.getStatus());
					//������Ӧ�ķ��ü�¼״̬Ϊ����Ч��
					PayFeeUtil.updateAcctItemStatus(financeMapDto.getMinusReferId(),financeMapDto.getAcctId());
				}
				mapList.add(financeMapDto);
			}
			//�����ʻ���Ϣ
			PayFeeUtil.updateAccount(prePaymentDto.getAcctId()
						  ,-prePaymentDto.getAmount()
						  ,prePaymentDto.getPrepaymentType());
		}
		if(!(mapList.size() == 0))
			financeMapList = mapList;
	}

	/**
	 * �������ʼ�¼
	 * @param financeMapList ������ϸ�б�
	 * @throws ServiceException
	 */
	private void createFinanceSetoffMap()throws ServiceException
	{
if (true) return;
/*		System.out.println("financeMapList2.size: "+this.financeMapList.size());
		for(int i=0; i<this.financeMapList.size(); i++)
		{
		    FinanceSetOffMapDTO financeMapDto = (FinanceSetOffMapDTO)this.financeMapList.get(i);
		    LogUtility.log(clazz,LogLevel.DEBUG,"financeMapList2",financeMapDto);
		}
*/
	    //�������ʼ�¼
		try
		{
			for(int i=0; i<financeMapList.size(); i++)
			{
				FinanceSetOffMapDTO financeMapDto = (FinanceSetOffMapDTO)((ArrayList)financeMapList).get(i);
				financeMapDto.setCreateTime(TimestampUtility.getCurrentDateWithoutTime());
				LogUtility.log(clazz, LogLevel.DEBUG,"��createFinanceSetoffMap����������ϸ��Ϣ��ʼ");
				FinanceSetOffMapHome financeMapHome = HomeLocater.getFinanceSetOffMapHome();
				LogUtility.log(clazz, LogLevel.DEBUG, "createFinanceSetoffMap",financeMapDto);
				FinanceSetOffMap financeMap = financeMapHome.create(financeMapDto);
				LogUtility.log(clazz, LogLevel.DEBUG,"��createFinanceSetoffMap����������ϸ��Ϣ����");
			}
		}catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createFinanceSetoffMap",e);
			throw new ServiceException("����������ϸ��Ϣʱ��λ����");
		}catch(CreateException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createFinanceSetoffMap",e);
			throw new ServiceException("����������ϸ��Ϣ����");
		}
	}

	/**
	 * �������ʼ�¼
	 * @param financeMapList ������ϸ�б�
	 * @throws ServiceException
	 */
	private void createFinanceSetoffMap(Collection financeMapList)throws ServiceException
	{
/*		System.out.println("financeMapList2.size: "+this.financeMapList.size());
		for(int i=0; i<this.financeMapList.size(); i++)
		{
			FinanceSetOffMapDTO financeMapDto = (FinanceSetOffMapDTO)this.financeMapList.get(i);
			LogUtility.log(clazz,LogLevel.DEBUG,"financeMapList2",financeMapDto);
		}
*/
		//�������ʼ�¼
		if(financeMapList == null) return;
		try
		{
			for(int i=0; i<financeMapList.size(); i++)
			{
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~" + i);				
				FinanceSetOffMapDTO financeMapDto = (FinanceSetOffMapDTO)((ArrayList)financeMapList).get(i);
                // ���ü�¼�������� add by david.Yang date: 2006-11-9
				/*if (CommonConstDefinition.SETOFFREFERTYPE_F.equals(financeMapDto.getPlusReferType())){
					PayFeeUtil.updateAcctItemStatus(financeMapDto.getMinusReferId(),financeMapDto.getAcctId());
   				    PayFeeUtil.updateAcctItemStatus(financeMapDto.getPlusReferId(),financeMapDto.getAcctId());
				}*/

				//֧����¼��������
/*				if(CommonConstDefinition.SETOFFREFERTYPE_P.equals(financeMapDto.getPlusReferType())
				   && this.getPaymentList().size() == 0)
				{
					PayFeeUtil.updateAcctItemStatus(financeMapDto.getMinusReferId(),financeMapDto.getAcctId());
					PayFeeUtil.updatePaymentStatus(financeMapDto.getPlusReferId());
				}*/
				//�ֿۼ�¼��������
/*				if(CommonConstDefinition.SETOFFREFERTYPE_D.equals(financeMapDto.getPlusReferType())
				   && this.getPrePaymentList().size() == 0)
				{
					PayFeeUtil.updateAcctItemStatus(financeMapDto.getMinusReferId(),financeMapDto.getAcctId());
					//�����ʻ���Ϣ
					PrepaymentDeductionRecordHome prePaymentHome = HomeLocater.getPrepaymentDeductionRecordHome();
					PrepaymentDeductionRecord prePayment = prePaymentHome.findByPrimaryKey(new Integer(financeMapDto.getPlusReferId()));
					PayFeeUtil.updateAccount(financeMapDto.getAcctId()
											 , -financeMapDto.getValue()
											 , prePayment.getPrepaymentType());
				}*/
				financeMapDto.setCreateTime(TimestampUtility.getCurrentDateWithoutTime());
				LogUtility.log(clazz, LogLevel.DEBUG,"��createFinanceSetoffMap����������ϸ��Ϣ��ʼ");
				FinanceSetOffMapHome financeMapHome = HomeLocater.getFinanceSetOffMapHome();
				LogUtility.log(clazz, LogLevel.DEBUG, "createFinanceSetoffMap",financeMapDto);
				FinanceSetOffMap financeMap = financeMapHome.create(financeMapDto);
				LogUtility.log(clazz, LogLevel.DEBUG,"��createFinanceSetoffMap����������ϸ��Ϣ����");
			}
		}catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createFinanceSetoffMap",e);
			throw new ServiceException("����������ϸ��Ϣʱ��λ����");
		}/*catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"���ĵֿۼ�¼��Ϣ����ԭ�򣺵ֿۼ�¼���ҳ���");
			throw new ServiceException("���ĵֿۼ�¼��Ϣ����ԭ�򣺵ֿۼ�¼���ҳ���");
		}*/catch(CreateException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createFinanceSetoffMap",e);
			throw new ServiceException("����������ϸ��Ϣ����");
		}
	}

	/**
	 * ���������б��������������ֵ
	 * @param accItemDto ������ϸ
	 * @return AccountItemDTO
	 */
	private AccountItemDTO getAccItemDto(AccountItemDTO accItemDto)
	{
		AccountItemDTO accItemDto1 = new AccountItemDTO();
		accItemDto1.setAiNO(accItemDto.getAiNO());
		accItemDto1.setAcctID(accItemDto.getAcctID());
		accItemDto1.setValue(accItemDto.getValue());
		accItemDto1.setCreatingMethod(accItemDto.getCreatingMethod());
		accItemDto1.setCreateTime(accItemDto.getCreateTime());
		accItemDto1.setCustID(accItemDto.getCustID());
		accItemDto1.setDtCreate(accItemDto.getDtLastmod());
		accItemDto1.setDtLastmod(accItemDto.getDtLastmod());
		accItemDto1.setInvoiceFlag(accItemDto.getInvoiceFlag());
		accItemDto1.setInvoiceNO(accItemDto.getInvoiceNO());
		accItemDto1.setForcedDepositFlag(accItemDto.getForcedDepositFlag());
		accItemDto1.setOperatorID(accItemDto.getOperatorID());
		accItemDto1.setOrgID(accItemDto.getOrgID());
		accItemDto1.setReferID(accItemDto.getReferID());
		accItemDto1.setReferType(accItemDto.getReferType());
		accItemDto1.setAcctItemTypeID(accItemDto.getAcctItemTypeID());
		accItemDto1.setDate1(accItemDto.getDate1());
		accItemDto1.setDate2(accItemDto.getDate2());
		accItemDto1.setProductID(accItemDto.getProductID());
		accItemDto1.setStatus(accItemDto.getStatus());
		accItemDto1.setPsID(accItemDto.getPsID());
		accItemDto1.setServiceAccountID(accItemDto.getServiceAccountID());
		accItemDto1.setSetOffAmount(accItemDto.getSetOffAmount());
		accItemDto1.setSetOffFlag(accItemDto.getSetOffFlag());
		accItemDto1.setBatchNO(accItemDto.getBatchNO());
		accItemDto1.setBillingCycleID(accItemDto.getBillingCycleID());
		accItemDto1.setSourceRecordID(accItemDto.getSourceRecordID());
		LogUtility.log(clazz,LogLevel.DEBUG,"getAccItemDto",accItemDto1);
		return accItemDto1;
	}

	/**
	 * ����֧���б��������������ֵ
	 * @param paymentDto ֧����¼
	 * @return PaymentRecordDTO
	 */
	private PaymentRecordDTO getPaymentDto(PaymentRecordDTO paymentDto)
	{
		PaymentRecordDTO paymentDto1 = new PaymentRecordDTO();
		paymentDto1.setSeqNo(paymentDto.getSeqNo());
		paymentDto1.setAcctID(paymentDto.getAcctID());
		paymentDto1.setAmount(paymentDto.getAmount());
//		paymentDto1.setCreatingMethod(paymentDto.getCreatingMethod());
		paymentDto1.setCreateTime(paymentDto.getCreateTime());
		paymentDto1.setCustID(paymentDto.getCustID());
		paymentDto1.setDtCreate(paymentDto.getDtLastmod());
		paymentDto1.setDtLastmod(paymentDto.getDtLastmod());
		paymentDto1.setInvoicedFlag(paymentDto.getInvoicedFlag());
		paymentDto1.setInvoiceNo(paymentDto.getInvoiceNo());
		paymentDto1.setMopID(paymentDto.getMopID());
		paymentDto1.setOpID(paymentDto.getOpID());
		paymentDto1.setOrgID(paymentDto.getOrgID());
		paymentDto1.setPaidTo(paymentDto.getPaidTo());
		paymentDto1.setPaymentTime(paymentDto.getPaymentTime());
		paymentDto1.setPayType(paymentDto.getPayType());
		paymentDto1.setPrepaymentType(paymentDto.getPrepaymentType());
		paymentDto1.setSourceRecordID(paymentDto.getSourceRecordID());
		paymentDto1.setSourceType(paymentDto.getSourceType());
		paymentDto1.setStatus(paymentDto.getStatus());
		paymentDto1.setTicketNo(paymentDto.getTicketNo());
		paymentDto1.setTicketType(paymentDto.getTicketType());
		paymentDto1.setReferID(paymentDto.getReferID());
		paymentDto1.setReferType(paymentDto.getReferType());
		LogUtility.log(clazz,LogLevel.DEBUG,"getPaymentDto",paymentDto1);
		return paymentDto1;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

/*
	public static void main(String[] args)
	{
	}
*/
}
