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
 * <p>Title: 付费销帐接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: DTV</p>
 * @author Wesley
 * @version 1.0
 */

//此类负责受理单一次性费用的付费销帐，所需信息从前台输入
public class ImmediatePayFeeService extends AbstractService
{
    private final static int FEE_FLAG = 1;       //费用标识
    private final static int PAY_FLAG = 2;       //支付标识
	private final static int PREPAY_FLAG = 3;    //预存抵扣标识
	private final static Class clazz = ImmediatePayFeeService.class;
    //费用列表(ArrayList of AccountItemDTO)
    private ArrayList feeList;
    //支付列表(ArrayList of PaymentRecordDTO)
    private ArrayList paymentList;
	//销帐列表(ArrayList of FinanceSetoffMapDTO)
	private ArrayList financeMapList;
    //预存款抵扣(ArrayList of  PrePaymentDeductionRecordDTO)
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
    
   
    //构造方法
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
		 * Command调用此方法支持调帐时建立销账关系 return parameter @param ArrayList
		 * financeMapList //费用、支付销帐列表 @throws ServiceException
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
	总量销账保证总量平衡：费用 = 支付 + 抵扣
	*/
	public ArrayList payFee() throws ServiceException
	{
//2007-04-06上线前临时应对策略
//由于前台页面丢失了费用信息，所以销账接口校验部分不做任何判断

if (true) return null;

System.out.println("feelist=" + feeList);
System.out.println("paymentList=" + paymentList);
System.out.println("deductionList=" + prePaymentList);
		//取得费用总量
		long fee = getSumFee();
		//取得支付总量
		long payment = getSumPaymentRecord();
		//取得抵扣总量
		long deduction = getSumDeduction();
		//校验总量平衡
		//if(fee != payment + deduction) throw new ServiceException("费用总量不等于支付总量+抵扣总量!");
		if(Math.abs(fee - (payment + deduction))>0.0001) throw new ServiceException("费用总量不等于支付总量+抵扣总量!");

		//校验预存〉强制预存
		long forceFee = getSumForceFee();
		long prePay = getSumPrePayment();
System.out.println("fee =" + fee);
System.out.println("payment =" + payment);
System.out.println("deduction =" + deduction);
System.out.println("forceFee =" + forceFee);
System.out.println("prePay =" + prePay);
		if( forceFee > 0 ) //在强制预存费大于0的情况下进行校验
			if(prePay < forceFee ) throw new ServiceException("预存金额必须>=强制预存");

		//不建立销账关系
		return null;
	}
	/**
	 * Command调用此方法实现付费销帐关系的建立 return parameter
	 * 
	 * @param ArrayList
	 *            financeMapList //费用、支付销帐列表
	 * @throws ServiceException
	 */
	public ArrayList payFeeDe()throws ServiceException
	{
		//校验费用列表是否为空
		if(feeList == null)
			throw new ServiceException("费用列表不能为空！");
		//校验支付列表是否为空
		if(paymentList == null)
			throw new ServiceException("支付列表不能为空！");
		/*
		 包含费用列表的强制预存费和受理单费
		 amountFeeList[0] = 总的强制预存费
	     amountFeeList[1] = 总的受理单费用
		*/
		double amountFeeList[] = new double[2];
		/*
		 包含支付列表的强制预存付费和受理单付费
		 amountPayList[0] = 总的预存支付费
	     amountPayList[1] = 总的受理单支付
		*/
		double amountPayList[] = new double[2];
		/*
		 包含预存抵扣列表的受理单预存抵扣费
		 amountPayList[0] = 总的预存抵扣支付费
		*/
		double amountPrePayList[] = new double[2];
		/*
		 对费用列表中的客户、帐户、受理单信息进行校验
		 如果校验通过，则根据预存标志分别得到费用列表总的强制预存费和受理单费
		*/
		amountFeeList = checkInfo(FEE_FLAG);
		/*
		 对支付列表中的客户、帐户、受理单信息进行校验
		 如果校验通过，则根据支付类型分别得到支付列表总的预存付费和受理单付费
		*/
		amountPayList = checkInfo(PAY_FLAG);
		/*
		 如果预存抵扣列表不为空，对存抵扣列表中的信息进行校验
		 如果校验通过，则得到预存抵扣列表总的预存抵扣受理单付费
		*/
		amountPrePayList = checkInfo(PREPAY_FLAG);
		/*
		 将费用列表中的强制预存总费用、受理单费用和
		 支付列表中的预存支付总额、受理单支付总额进行比较
		*/
		double preFeeAmount = amountFeeList[0];
		double csiFeeAmount = amountFeeList[1];
		double prePayAmount = amountPayList[0];
		double csiPayAmount = amountPayList[1];
		//总的预存抵扣受理单付费
		double csiPrePayment = amountPrePayList[0];
		//支付列表、预存抵扣列表的总金额之和(Added by shuwei 2006-10-8)
		double csiTotalPayAmount = BusinessUtility.doubleFormat(csiPayAmount + csiPrePayment);
		//double csiTotalPayAmount = (double) Math.floor(csiPayAmount * 100 + csiPrePayment * 100+0.01) / 100;
		if(preFeeAmount > prePayAmount)
			throw new ServiceException("强制预存金额不够！");
		
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
			throw new ServiceException("受理单支付金额不对！");
		//产生销帐前，对费用列表、支付列表按金额从小到大排序
//		listArrange();
		//以上校验都没问题的话，产生费用、支付的销帐关系明细
		
		//首先做持久化工作，其次建立销帐关系
//	费用，支付持久化不在销账中完成。
//		createAmountItem(); 
		//创建支付记录
//		createPaymentRecord();
		//创建预存抵扣记录,同时更新帐户信息
//		if(prePaymentList != null)
	//		createPrePaymentDeductionRecord();
		
		ArrayList financeMapList = createFinanceMapList();
		return financeMapList;
	}

	/**
	 * 对费用、支付列表中的客户、帐户、受理单信息进行校验
	 * input parameter
	 * @param int flag  费用、支付标识
	 *
	 * return parameter
	 * @param double[]
	 * @throws ServiceException
	 */
	private double[] checkInfo(int flag)throws ServiceException
	{
		int preCustomerID = 0;  //前一个明细的客户号
		int preAccountID = 0;   //前一个明细的帐户号
//		int preCSIID = 0;       //前一个明细的受理单号
		double preFeeAmount = 0.0f; //强制预存总费用
		double csiFeeAmount = 0.0f; //受理单费用总额
		double prePayAmount = 0.0f; //预存支付总额		
		double csiPayAmount = 0.0f; //受理单支付总额
		double csiPrePayAmount = 0.0f; //受理单预存抵扣总额
		
		long lpreFeeAmount = 0;
		long lcsiFeeAmount = 0;
		long lprePayAmount = 0;
		long lcsiPayAmount = 0;
		long lcsiPrePayAmount = 0;
		
		/*
		 如果flag=FEE_FLAG，则包含费用列表的强制预存费和受理单费
		 如果flag=PAY_FLAG，则包含支付列表的预存付费和受理单付费
		 如果flag=PREPAY_FLAG，则包含预存抵扣列表的抵扣金额
		*/
		double amountList[] = new double[2];
		//校验费用列表
		if(flag == FEE_FLAG)
		{
			for(int i=0; i<feeList.size(); i++)
			{
				AccountItemDTO accItemDto = (AccountItemDTO)feeList.get(i);
				//费用金额为零的记录不进行检查
				if (accItemDto.getValue() == 0)
					continue;
				if(String.valueOf(accItemDto.getCustID()) == null ||
					accItemDto.getCustID() <= 0)
					throw new ServiceException("费用记录中客户号不能为空！");
				if(String.valueOf(accItemDto.getAcctID()) == null ||
					accItemDto.getAcctID() <= 0)
					throw new ServiceException("费用记录中帐户号不能为空！");
//				if(String.valueOf(accItemDto.getReferID()) == null ||
//					accItemDto.getReferID() <= 0)
//					throw new ServiceException("费用记录中受理单号不能为空！");
				if(i > 0)
				{
					if (preCustomerID != accItemDto.getCustID() && preCustomerID != 0)
						throw new ServiceException("费用列表中的记录不是同一个客户的！");
					if (preAccountID != accItemDto.getAcctID() && preAccountID != 0)
						throw new ServiceException("费用列表中的记录不是同一个帐户的！");
//					if (preCSIID != accItemDto.getReferID() && preCSIID != 0)
//						throw new ServiceException("费用列表中的记录不是同一个受理单的！");
				}
				//校验强制预存标志
				if(accItemDto.getForcedDepositFlag() == null ||
					accItemDto.getForcedDepositFlag().trim().length() == 0)
					throw new ServiceException("必须传入强制预存标志！");
				LogUtility.log(clazz,LogLevel.DEBUG,"checkInfo(feeList)",accItemDto);
				//得到强制预存费和受理单费用(考虑已销账和部分销账的费用记录 2006-9-28 shuwei modified)
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
		//校验支付列表
		if(flag == PAY_FLAG)
		{
			for(int i=0; i<paymentList.size(); i++)
			{
				PaymentRecordDTO paymentDto = (PaymentRecordDTO)paymentList.get(i);
				//支付金额为零的记录不进行检查
				//if (paymentDto.getAmount() == 0)
				if (Math.abs(paymentDto.getAmount())<0.0001)
					continue;
				if(String.valueOf(paymentDto.getCustID()) == null ||
					paymentDto.getCustID() <= 0)
					throw new ServiceException("支付记录中客户号不能为空！");
				if(String.valueOf(paymentDto.getAcctID()) == null ||
					paymentDto.getAcctID() <= 0)
					throw new ServiceException("支付记录中帐户号不能为空！");
//				if(String.valueOf(paymentDto.getPaidTo()) == null ||
//					paymentDto.getPaidTo() <= 0)
//					throw new ServiceException("支付记录中受理单号不能为空！");
				if(i > 0)
				{
					if (preCustomerID != paymentDto.getCustID() && preCustomerID != 0)
						throw new ServiceException("支付列表中的记录不是同一个客户的！");
					if (preAccountID != paymentDto.getAcctID() && preAccountID != 0)
						throw new ServiceException("支付列表中的记录不是同一个帐户的！");
//					if (preCSIID != paymentDto.getPaidTo() && preCSIID != 0)
//						throw new ServiceException("支付列表中的记录不是同一个受理单的！");
				}
				//校验支付类型
				if(paymentDto.getPayType() == null ||
					paymentDto.getPayType().trim().length() == 0)
					throw new ServiceException("必须传入支付类型！");
				LogUtility.log(clazz,LogLevel.DEBUG,"checkInfo(paymentList)",paymentDto);
				//预存退费不参与销帐
				if(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE.equals(paymentDto.getPayType()))
					continue;
				//得到预存付费
				if(CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType()))
				{
					if(paymentDto.getPrepaymentType() == null ||
						paymentDto.getPrepaymentType().trim().length() == 0)
						throw new ServiceException("必须指明是现金预存还是虚拟货币预存！");
					
					lprePayAmount = lprePayAmount + double2long(paymentDto.getAmount());
				/*	prePayAmount = (double) Math.floor(prePayAmount * 100
						+ paymentDto.getAmount() * 100) / 100;*/
				}
				//得到主动预存付费
/*				else if(CommonConstDefinition.PAYMENTRECORD_TYPE_PASSIVITYPRESAVE.equals(paymentDto.getPayType()))
				{
					if(paymentDto.getPrepaymentType() == null ||
						paymentDto.getPrepaymentType().trim().length() == 0)
						throw new ServiceException("必须指明是现金预存还是虚拟货币预存！");
				}*/
				else//得到受理单付费
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
		//校验预存抵扣列表
		if(flag == PREPAY_FLAG)
		{
			for(int i=0; i<prePaymentList.size(); i++)
			{
				PrepaymentDeductionRecordDTO PrepaymentDto = (PrepaymentDeductionRecordDTO)prePaymentList.get(i);
				//预存抵扣金额为零的记录不进行检查
				//if (PrepaymentDto.getAmount() == 0)
				if (Math.abs(PrepaymentDto.getAmount())<0.0001)
					continue;
				if(String.valueOf(PrepaymentDto.getCustId()) == null ||
					PrepaymentDto.getCustId() <= 0)
					throw new ServiceException("预存抵扣记录中客户号不能为空！");
				if(String.valueOf(PrepaymentDto.getAcctId()) == null ||
					PrepaymentDto.getAcctId() <= 0)
					throw new ServiceException("预存抵扣记录中帐户号不能为空！");
//				if(String.valueOf(PrepaymentDto.getReferRecordId()) == null ||
//					PrepaymentDto.getReferRecordId() <= 0)
//					throw new ServiceException("预存抵扣记录中受理单号不能为空！");
				if(i > 0)
				{
					if (preCustomerID != PrepaymentDto.getCustId() && preCustomerID != 0)
						throw new ServiceException("预存抵扣列表中的记录不是同一个客户的！");
					if (preAccountID != PrepaymentDto.getAcctId() && preAccountID != 0)
						throw new ServiceException("预存抵扣列表中的记录不是同一个帐户的！");
//					if (preCSIID != PrepaymentDto.getReferRecordId() && preCSIID != 0)
//						throw new ServiceException("预存抵扣列表中的记录不是同一个受理单的！");
				}
				//校验预存抵扣类型
				if(PrepaymentDto.getPrepaymentType() == null ||
					PrepaymentDto.getPrepaymentType().trim().length() == 0)
					throw new ServiceException("必须指明是现金预存抵扣还是虚拟货币预存抵扣！");
				LogUtility.log(clazz,LogLevel.DEBUG,"checkInfo(prePaymentList)",PrepaymentDto);
				//得到预存抵总扣金额
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
		//建立销账关系
		loop1:for(int i=0;i <pAcctItem.size();i++)
		{
			bSetOff1 = false;
			AccountItemDTO acctItem1 = (AccountItemDTO)pAcctItem.get(i);
			//建立销账优先级
			buildSetOffLevel(acctItem1);
			//销费用
			long lvalue1 = double2long(acctItem1.getValue()) - double2long(acctItem1.getSetOffAmount());
			

			loop2:for(int j=0;j<sAcctItem.size();j++)
			{
				bSetOff2 = false;
				AccountItemDTO acctItem2 = (AccountItemDTO)sAcctItem.get(j);
				long lvalue2 = double2long(acctItem2.getValue()) - double2long(acctItem2.getSetOffAmount());

				
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				if(lvalue1+lvalue2 == 0)
				{
					//完全销账
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
				
				
				//设置销帐明细的客户、帐户、操作员、所属机构号信息
				financeMapDto.setAcctId(acctItem1.getAcctID());
				financeMapDto.setCustId(acctItem1.getCustID());
				financeMapDto.setOpId(acctItem1.getOperatorID());
				financeMapDto.setOrgId(acctItem1.getOrgID());
				//销帐明细的销帐类型为“直接销帐”
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//销帐明细的状态为“正常”
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//正的销帐关联记录类型为"费用"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//负的销帐关联记录类型为"费用"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				
				financeMapDto.setPlusReferId(acctItem1.getAiNO());
				financeMapDto.setMinusReferId(acctItem2.getAiNO());
				financeMapList.add(financeMapDto);
				
				if(bSetOff1) continue loop1;
				if(bSetOff2) continue loop2;
			}
			//销抵扣
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
					//完全销帐了
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
				
				//设置销帐明细的客户、帐户、操作员、所属机构号信息
				financeMapDto.setAcctId(acctItem1.getAcctID());
				financeMapDto.setCustId(acctItem1.getCustID());
				financeMapDto.setOpId(acctItem1.getOperatorID());
				financeMapDto.setOrgId(acctItem1.getOrgID());
				//销帐明细的销帐类型为“直接销帐”
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//销帐明细的状态为“正常”
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//正的销帐关联记录类型为"费用"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//负的销帐关联记录类型为"费用"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_D);
				
				financeMapDto.setPlusReferId(acctItem1.getAiNO());
				financeMapDto.setMinusReferId(prePay.getSeqNo());
				financeMapList.add(financeMapDto);
				
				if(bSetOff1)continue loop1;
				if(bSetOff2)continue loop3;
			}
			
			//销支付
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
				
				//设置销帐明细的客户、帐户、操作员、所属机构号信息
				financeMapDto.setAcctId(acctItem1.getAcctID());
				financeMapDto.setCustId(acctItem1.getCustID());
				financeMapDto.setOpId(acctItem1.getOperatorID());
				financeMapDto.setOrgId(acctItem1.getOrgID());
				//销帐明细的销帐类型为“直接销帐”
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//销帐明细的状态为“正常”
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//正的销帐关联记录类型为"费用"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//负的销帐关联记录类型为"费用"
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
		//不存在这种情况
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
					//正好抵消
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
						//费用为销账
						bSetOff2 = true;
						acctItem.setSetOffAmount(acctItem.getValue());
						acctItem.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						payment.setAmount((double)(double2long(payment.getAmount()) - value2)/100);
						financeMapDto.setValue((double)value2/100);
						value1 =value1-value2;
					}
					else
					{
						//支付为销账
						bSetOff1 = true;
						payment.setAmount(0.0f);
						acctItem.setSetOffAmount((double)(double2long(acctItem.getSetOffAmount()) + value1)/100);
					}
				}				
				appendFeeList(acctItem);
				appendPaymentList(payment);				
				//设置销帐明细的客户、帐户、操作员、所属机构号信息
				financeMapDto.setAcctId(acctItem.getAcctID());
				financeMapDto.setCustId(acctItem.getCustID());
				financeMapDto.setOpId(acctItem.getOperatorID());
				financeMapDto.setOrgId(acctItem.getOrgID());
				//销帐明细的销帐类型为“直接销帐”
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//销帐明细的状态为“正常”
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//正的销帐关联记录类型为"费用"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//负的销帐关联记录类型为"费用"
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
				//设置销帐明细的客户、帐户、操作员、所属机构号信息
				financeMapDto.setAcctId(payment.getAcctID());
				financeMapDto.setCustId(payment.getCustID());
				financeMapDto.setOpId(payment.getOpID());
				financeMapDto.setOrgId(payment.getOrgID());
				//销帐明细的销帐类型为“直接销帐”
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//销帐明细的状态为“正常”
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//正的销帐关联记录类型为"费用"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//负的销帐关联记录类型为"费用"
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
	 * 总量销账模式.
	 * 传入参数应该是账单或者受理单号
	 * */
	private void setOffWithIMode(int csi,String csiType)
	{
		for(int i = 0;i<feeList.size();i++)
		{
			AccountItemDTO acctItem = (AccountItemDTO)feeList.get(i);
			if(CommonConstDefinition.SETOFFFLAG_D.equals(acctItem.getSetOffFlag()))
				continue;
			FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
			//设置销帐明细的客户、帐户、操作员、所属机构号信息
			financeMapDto.setAcctId(acctItem.getAcctID());
			financeMapDto.setCustId(acctItem.getCustID());
			financeMapDto.setOpId(acctItem.getOperatorID());
			financeMapDto.setOrgId(acctItem.getOrgID());
			//销帐明细的销帐类型为“直接销帐”
			financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
			//销帐明细的状态为“正常”
			financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			//正的销帐关联记录类型为"费用"
			financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
			//负的销帐关联记录类型为"费用"
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
			//设置销帐明细的客户、帐户、操作员、所属机构号信息
			financeMapDto.setAcctId(prePay.getAcctId());
			financeMapDto.setCustId(prePay.getCustId());
			financeMapDto.setOpId(prePay.getOpId());
			financeMapDto.setOrgId(prePay.getOrgId());
			//销帐明细的销帐类型为“直接销帐”
			financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
			//销帐明细的状态为“正常”
			financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			//正的销帐关联记录类型为"费用"
			financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_D);
			//负的销帐关联记录类型为"费用"
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
			//设置销帐明细的客户、帐户、操作员、所属机构号信息
			financeMapDto.setAcctId(payment.getAcctID());
			financeMapDto.setCustId(payment.getCustID());
			financeMapDto.setOpId(payment.getOpID());
			financeMapDto.setOrgId(payment.getOrgID());
			//销帐明细的销帐类型为“直接销帐”
			financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
			//销帐明细的状态为“正常”
			financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			//正的销帐关联记录类型为"费用"
			financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_P);
			//负的销帐关联记录类型为"费用"
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
		//进行销帐处理的逻辑
		/*判断销帐级别,分步骤处理
		 * 1 先进行相同帐目类型费用的销帐
		 * 2 预存抵扣间,支付进行销帐
		 * 3 进入销帐流程
		 *    a 遍历费用(+)预存抵扣(-)支付(-)
		 *    b 对其余记录进行筛选,排序
		 *    c 建立对应销帐关系.
		 * */
		String setOffLevel = BusinessUtility.getSetOffLevel();
		
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + setOffLevel);

		if(CommonConstDefinition.SETOFF_LEVEL_I.equals(setOffLevel)) 
		{
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + "总量销帐 + ID = " + String.valueOf(id));
			setOffWithIMode(id,type);
			return financeMapList;//如果销帐级别为总量销帐
		}
		
		//同类型预处理
		buildSameAcctItemSetOffMap();
		
		//建立待销帐费用,预存抵扣,支付列表 与 可销帐费用,预存抵扣,支付列表
		buildSetOffList();
		
		setOffFee();
		
		setOffPrePayment();
		
		setOffPayment();
		return financeMapList;
	}
	/**
	 * 生成费用和支付的销帐明细
	 * return parameter
	 * @param ArrayList financeMapList //费用、支付销帐列表
	 * @throws ServiceException
	 */
	private ArrayList createFinanceMapList2()throws ServiceException
	{
		//费用列表
		ArrayList accItemList = new ArrayList();
		//支付列表
		ArrayList payRecordList = new ArrayList();
		//预存抵扣列表
		ArrayList prePayList = new ArrayList();
		//销帐列表
//		ArrayList financeMapList = new ArrayList();
		//当前费用记录序列号(虚拟的负的)
		int aiNO = 0;
		//当前支付记录序列号(虚拟的负的)
		int payNO = 0;
		//当前预存抵扣记录序列号(虚拟的负的)
		int prePayNO = 0;
		//创建销帐列表
		outer:for(int i=0; i<feeList.size(); i++)
		{
			AccountItemDTO accItemDto = (AccountItemDTO)feeList.get(i);
			//费用金额已销帐的记录不进行销账 2006-9-28 modified by shuwei
			if(CommonConstDefinition.SETOFFFLAG_D.equals(accItemDto.getSetOffFlag()))
				continue outer;
			//费用金额为零的记录不进行销帐
			//if(accItemDto.getValue() == 0)
			if(Math.abs(accItemDto.getValue())<0.0001)
				continue outer;
			//不处理无效的费用记录
			if(CommonConstDefinition.AISTATUS_INVALIDATE.equals(accItemDto.getStatus()))
				continue outer;
			
			
			//zyg 2006.11.10 begin
			/**********************************************************************************
			//如果是优惠费用或针对费用－》费用的调账，则跳出循环，用于费用对费用的销帐
			if((CommonConstDefinition.FTCREATINGMETHOD_B.equals(accItemDto.getCreatingMethod())
				&& accItemDto.getValue() < 0)
				|| (CommonConstDefinition.FTCREATINGMETHOD_T.equals(accItemDto.getCreatingMethod())
				&& paymentList.isEmpty() && prePaymentList.isEmpty()))
				continue outer;
			/**********************************************************************************/
			//zyg 2006.11.10 end

			//zyg 2006.11.20 begin
			//如果发现费用记录的金额 < 0 , 则继续处理下一条记录
			/**********************************************************************************
			//加上非退费的限制，否则退费无法进行 modify  by david.Yang 2006-11-26
			if(accItemDto.getValue() < 0 && !CommonConstDefinition.FTCREATINGMETHOD_R.equals(accItemDto.getCreatingMethod()))
				continue outer;
			/**********************************************************************************/

			double value =0;

                        //将第一次参加销帐的费用记录加入另一个费用列表中
                        //zyg 2006.11.20 begin
                        /*****************************************
                        if (accItemDto.getValue() >0){   //只放正的费用记录 modify by david.Yang  2006-11-26
                        /*****************************************/
                        //zyg 2006.11.10 end

			   if (String.valueOf(accItemDto.getAiNO()) == null ||
				   accItemDto.getAiNO() == 0) {
				   aiNO--;
				   /*
				    为了跟销帐明细建立联系，设置虚拟费用记录序列号，
				    数据持久化时再生成真正的费用记录序列号。
				   */
				  accItemDto.setAiNO(aiNO);
			      AccountItemDTO accItemDto1 = getAccItemDto(accItemDto);
			      accItemList.add(accItemDto1);
			   }

                        //zyg 2006.11.20 begin
                        /*****************************************
                        }
                        /*****************************************/


			//每笔费用实际需要销账的金额 modified by shuwei 2006-9-28
			value = BusinessUtility.doubleFormat(accItemDto.getValue() - accItemDto.getSetOffAmount());
			//value = (double) Math.floor(accItemDto.getValue() * 100 - accItemDto.getSetOffAmount() * 100+0.01) / 100;

			System.out.println("value==============="+value);
			System.out.println("accItemDto.getCreatingMethod()============="+accItemDto.getCreatingMethod());

			//先进行优惠费用的销帐
			for(int j=0; j<feeList.size(); j++)
			{
				AccountItemDTO favourableAccItemDto = (AccountItemDTO)feeList.get(j);
				//优惠金额为零的记录不进行销帐
				//if (favourableAccItemDto.getValue() == 0)
				if (Math.abs(favourableAccItemDto.getValue())<0.0001)
					continue;
				//不处理无效的优惠费用记录
				if (CommonConstDefinition.AISTATUS_INVALIDATE.equals(
					favourableAccItemDto.getStatus()))
					continue;

                                //zyg 2006.11.22 begin
				//费用金额已销帐的记录不进行销账 
				/**********************************/
				if(CommonConstDefinition.SETOFFFLAG_D.equals(favourableAccItemDto.getSetOffFlag()))
					continue ;
				/**********************************/
				//zyg 2006.11.22 end


				//zyg 2006.11.10 begin
				//如果不是优惠费用或针对费用－》费用的调账，则跳出循环
				/**********************************************************************************
				if(!(CommonConstDefinition.FTCREATINGMETHOD_B.equals(favourableAccItemDto.getCreatingMethod())
				   && favourableAccItemDto.getValue() < 0)
				   && !(CommonConstDefinition.FTCREATINGMETHOD_T.equals(favourableAccItemDto.getCreatingMethod())
				   && paymentList.isEmpty() && prePaymentList.isEmpty()))
					continue;
				/**********************************************************************************/
				//zyg 2006.11.10 end

                                //zyg 2006.11.10 begin
                                //只有 正的费用 与 负的费用之间才能销账
                                /**********************************************************************************
                                if(favourableAccItemDto.getValue() > 0)
                                  continue;
                                /**********************************************************************************/
				//zyg 2006.11.10 end

                               //zyg 2006.11.20 begin
                               //如果 两笔费用 的金额的正负号相同, 则跳过
                               /*****************************************/
                               if (favourableAccItemDto.getValue() * value  > 0)
                                 continue;
                               /*****************************************/

                               //zyg 2006.11.20 begin
                               /*****************************************
                               //退费不参与费用与费用的销帐，只对支付 modify by david.Yang
                               if (CommonConstDefinition.FTCREATINGMETHOD_R.equals(accItemDto.getCreatingMethod()))
                                 continue;
                               /*****************************************/

				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				//设置销帐明细的客户、帐户、操作员、所属机构号信息
				financeMapDto.setAcctId(accItemDto.getAcctID());
				financeMapDto.setCustId(accItemDto.getCustID());
				financeMapDto.setOpId(accItemDto.getOperatorID());
				financeMapDto.setOrgId(accItemDto.getOrgID());
				//销帐明细的销帐类型为“直接销帐”
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//销帐明细的状态为“正常”
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//正的销帐关联记录类型为"费用"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//负的销帐关联记录类型为"费用"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				/*
				 只有当循环处理中的费用记录的强制预存标记是“NO”时
				 才可以建立优惠费用的费用销帐明细。
				*/
				if(CommonConstDefinition.YESNOFLAG_NO.equals(accItemDto.getForcedDepositFlag()))
				{
					//将第一次参加销帐的优惠费用记录加入另一个费用列表中
					if(String.valueOf(favourableAccItemDto.getAiNO()) == null ||
						favourableAccItemDto.getAiNO() == 0)
					{
						aiNO--;
						/*
						为了跟销帐明细建立联系，设置虚拟优惠费用序列号，
						数据持久化时再生成真正的优惠费用序列号。
						*/
						favourableAccItemDto.setAiNO(aiNO);
						AccountItemDTO accItemDto1 = getAccItemDto(favourableAccItemDto);
						accItemList.add(accItemDto1);
					}
					/*
					 如果费用金额正好等于优惠费用金额，则建立一对一的销帐记录，
					 销帐金额等于费用金额；
					 如果费用金额小于优惠费用金额，则优惠费用金额减去费用金额的余额，
					 留做下一个循环继续销帐。
					*/
				   //zyg 2006.11.22 begin
				   /********************************************/
				   if(Math.abs(value) <= Math.abs(favourableAccItemDto.getValue() - favourableAccItemDto.getSetOffAmount()))
				   /********************************************/
				   //zyg 2006.11.22 end
					{
						//销帐明细的销帐金额为费用明细的费用金额
						financeMapDto.setValue(Math.abs(value));
						//将已销帐的优惠费用记录删除
						feeList.remove(j);

						

						if(Math.abs(value) < Math.abs(favourableAccItemDto.getValue() - favourableAccItemDto.getSetOffAmount()))
						{
							//zyg 2006.11.22 begin 
							//应该更新销账金额
							/**********************************************************
							//将优惠费用金额减去费用金额的余额设置为“新的”优惠费用金额
							favourableAccItemDto.setValue((double) Math.floor(favourableAccItemDto.getValue()*100 + value*100+0.01)/100);
							/**********************************************************/
							//zyg 2006.11.22 end
													
							//zyg 2006.11.22 begin
							//更新销账状态
							/********************************/				
							favourableAccItemDto.setSetOffAmount(BusinessUtility.doubleFormat(favourableAccItemDto.getSetOffAmount() - value));
							//favourableAccItemDto.setSetOffAmount((double) Math.floor(favourableAccItemDto.getSetOffAmount()*100 - value*100+0.01)/100);
							favourableAccItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_P);
							/********************************/													
							//zyg 2006.11.22 end
							
							/**
							 * 将“新的”优惠费用金额的优惠费用记录重新插入费用列表中
							 * 进行下一轮循环的销帐
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
						//销帐明细中对应的费用记录序列号(虚拟的)
						financeMapDto.setMinusReferId(accItemDto.getAiNO());
						//销帐明细中对应的优惠费用记录序列号(虚拟的)
						financeMapDto.setPlusReferId(favourableAccItemDto.getAiNO());
						//创建销帐明细列表
						financeMapList.add(financeMapDto);

						System.out.println("i===================="+i);
						System.out.println("feeList.size()==========="+feeList.size());

						//下面的一段代码，本意是让是退费的情况退出循环 begin:
						//继续循环，建立销帐明细
						/*
						if(i == feeList.size()-1 && paymentList.size() > 0)
							continue;
						*/
						//end

						continue outer;
					}
					/*
					 如果费用金额大于优惠费用金额，则销帐金额等于优惠费用金额；
					 费用金额减去优惠费用金额的余额，留做下一个循环继续销帐。
					*/
					//zyg 2006.11.22 begin
					/***************************************************************/
					if(Math.abs(value) > Math.abs(favourableAccItemDto.getValue() - favourableAccItemDto.getSetOffAmount()))
					/***************************************************************/
					//zyg 2006.11.22 end
					{
						//销帐明细的销帐金额为优惠费用金额
						//zyg 2006.11.22 begin
						/*****************************************************************/
						financeMapDto.setValue(Math.abs(favourableAccItemDto.getValue() - favourableAccItemDto.getSetOffAmount()));
						/*****************************************************************/
						//zyg 2006.11.22 end
						
						//将已销帐的优惠费用记录删除
						feeList.remove(j);
						//将费用金额减去优惠费用金额的余额设置为“新的”费用金额
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
						 * 将“新的”费用金额的费用记录重新插入费用列表中
						 * 进行下一轮循环的销帐
						 */

                                                //zyg 2006.11.20 begin
                                                /************************
                                                feeList.add(i,accItemDto);
                                                /************************/
                                                //zyg 2006.11.20 end

                                                //zyg 2006.11.22 begin
                                                //更新费用记录的销账金额和销账状态
                                                /************************/
                                                favourableAccItemDto.setSetOffAmount(favourableAccItemDto.getValue());
                                                favourableAccItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
                                                /************************/
                                                //zyg 2006.11.22 end


						i--;

                                                //销帐明细中对应的费用记录序列号(虚拟的)
						financeMapDto.setMinusReferId(accItemDto.getAiNO());
						//销帐明细中对应的优惠费用记录序列号(虚拟的)
						financeMapDto.setPlusReferId(favourableAccItemDto.getAiNO());
						//创建销帐明细列表
						financeMapList.add(financeMapDto);

						//继续循环，建立销帐明细
						continue outer;
					}
				}
			}
			//然后进行预存抵扣的销帐
			for (int j = 0; j < prePaymentList.size(); j++)
			{
                          //zyg 2006.11.20 begin
                          /**************************************************
                          //受理单退费不和预存抵扣销帐
                          if(CommonConstDefinition.FTCREATINGMETHOD_R.equals(accItemDto.getCreatingMethod()))
                              break;
                          /**************************************************/

				PrepaymentDeductionRecordDTO prePaymentDto = (
					PrepaymentDeductionRecordDTO) prePaymentList.get(j);
				//预存抵扣金额为零的记录不进行销帐
				//if (prePaymentDto.getAmount() == 0)
				if (Math.abs(prePaymentDto.getAmount())<0.0001)	
					continue;
				//不处理无效的预存抵扣记录
				if (CommonConstDefinition.AISTATUS_INVALIDATE.equals(
					prePaymentDto.getStatus()))
					continue;
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				//设置销帐明细的客户、帐户、操作员、所属机构号信息
				financeMapDto.setAcctId(accItemDto.getAcctID());
				financeMapDto.setCustId(accItemDto.getCustID());
				financeMapDto.setOpId(accItemDto.getOperatorID());
				financeMapDto.setOrgId(accItemDto.getOrgID());
				//销帐明细的销帐类型为“预存销帐”
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_D);
				//销帐明细的状态为“正常”
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//正的销帐关联记录类型为"预存抵扣"
				financeMapDto.setPlusReferType(CommonConstDefinition.
					SETOFFREFERTYPE_D);
				//负的销帐关联记录类型为"费用"
				financeMapDto.setMinusReferType(CommonConstDefinition.
					SETOFFREFERTYPE_F);
				/*
				 只有当循环处理中的费用记录的强制预存标记是“NO”时
				 才可以建立预存抵扣的费用销帐明细。
				 */

                                //zyg 2006.11.20 begin
                                //考虑费用销账的正负问题
                                //if (CommonConstDefinition.YESNOFLAG_NO.equals(accItemDto.getForcedDepositFlag()))
                                if (CommonConstDefinition.YESNOFLAG_NO.equals(accItemDto.getForcedDepositFlag()) && value*prePaymentDto.getAmount() > 0)
                                //zyg 2006.11.20 end

				{
					//将第一次参加销帐的预存抵扣记录加入另一个预存抵扣列表中
					if (String.valueOf(prePaymentDto.getSeqNo()) == null ||
						prePaymentDto.getSeqNo() == 0)
					{
						prePayNO--;
						/*
					    为了跟销帐明细建立联系，设置虚拟预存抵扣记录序列号，
					    数据持久化时再生成真正的预存抵扣记录序列号。
					    */
					    prePaymentDto.setSeqNo(prePayNO);
						PrepaymentDeductionRecordDTO prePaymentDto1 = PayFeeUtil.getPrePaymentDto(prePaymentDto);
						prePayList.add(prePaymentDto1);
					}
					/*
					 如果费用金额正好等于预存抵扣金额，则建立一对一的销帐记录，
					 销帐金额等于费用金额；
					 如果费用金额小于预存抵扣金额，则预存抵扣金额减去费用金额的余额，
					 留做下一个循环继续销帐。
					 */
					if (Math.abs(value) <= Math.abs(prePaymentDto.getAmount()))
					{
                                          //销帐明细的销帐金额为费用明细的费用金额
                                          //zyg 2006.11.20 begin
                                          //销账金额的问题，不一定都是正的
                                          //financeMapDto.setValue(Math.abs(value));
                                          financeMapDto.setValue(value);
                                          //zyg 2006.11.20 end

						//将已销帐的预存抵扣记录删除
						prePaymentList.remove(j);
						if (Math.abs(value) < Math.abs(prePaymentDto.getAmount()))
						{
							//将预存抵扣金额减去费用金额的余额设置为“新的”预存抵扣金额
							prePaymentDto.setAmount(BusinessUtility.doubleFormat(prePaymentDto.getAmount() - value));
							//prePaymentDto.setAmount((double) Math.floor(prePaymentDto.getAmount()*100 - value*100+0.01)/100);
							/**
							 * 将“新的”预存抵扣金额的预存抵扣记录重新插入预存抵扣列表中
							 * 进行下一轮循环的销帐
							 */
							prePaymentList.add(j, prePaymentDto);
						}
						accItemDto.setSetOffAmount(accItemDto.getValue());
						accItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						//销帐明细中对应的费用记录序列号(虚拟的)
						financeMapDto.setMinusReferId(accItemDto.getAiNO());
						//销帐明细中对应的支付记录序列号(虚拟的)
						financeMapDto.setPlusReferId(prePaymentDto.getSeqNo());
						//创建销帐明细列表
						financeMapList.add(financeMapDto);

                                                //zyg 2006.11.20 begin
                                                /******************************************************
                                                //继续循环，建立销帐明细
                                                if(i == feeList.size()-1 && prePaymentList.size() == 0
                                                   && paymentList.size() > 0)
                                                        continue;
                                                /******************************************************/

						continue outer;
					}
					/*
					 如果费用金额大于预存抵扣金额，则销帐金额等于预存抵扣金额；
					 费用金额减去预存抵扣金额的余额，留做下一个循环继续销帐。
					 */
					if (Math.abs(value) > Math.abs(prePaymentDto.getAmount()))
					{
                                          //销帐明细的销帐金额为支付明细的支付金额
                                          //zyg 2006.11.20 begin
                                          //financeMapDto.setValue(Math.abs(prePaymentDto.getAmount()));
                                          financeMapDto.setValue(prePaymentDto.getAmount());
                                          //zyg 2006.11.20 end

						//将已销帐的预存抵扣记录删除
						prePaymentList.remove(j);
						//将费用金额减去预存抵扣金额的余额设置为“新的”费用金额
//						accItemDto.setValue(accItemDto.getValue() -
//											prePaymentDto.getAmount());
						accItemDto.setSetOffAmount(BusinessUtility.doubleFormat(accItemDto.getSetOffAmount() + prePaymentDto.getAmount()));
						//accItemDto.setSetOffAmount((double) Math.floor(accItemDto.getSetOffAmount()*100 + prePaymentDto.getAmount()*100+0.01)/100);
						accItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_P);
						/**
						 * 将“新的”费用金额的费用记录重新插入费用列表中
						 * 进行下一轮循环的销帐
						 */

				//		feeList.add(i + 1, accItemDto);  modify by david.Yang
						i--;                             //add by david.Yang
						//销帐明细中对应的费用记录序列号(虚拟的)
						financeMapDto.setMinusReferId(accItemDto.getAiNO());
						//销帐明细中对应的支付记录序列号(虚拟的)
						financeMapDto.setPlusReferId(prePaymentDto.getSeqNo());
						//创建销帐明细列表
						financeMapList.add(financeMapDto);
						//继续循环，建立销帐明细

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
			//最后进行费用/支付记录的销帐
			for(int j=0; j<paymentList.size(); j++)
			{
				PaymentRecordDTO paymentDto = (PaymentRecordDTO)paymentList.get(j);
				LogUtility.log(clazz,LogLevel.DEBUG,"createFinanceMapList(paymentList)",paymentDto);

				//支付金额为零的记录不进行销帐
				//if (paymentDto.getAmount() == 0)
				if (Math.abs(paymentDto.getAmount())<0.0001)
					continue;

				//不处理无效的支付记录
				if (CommonConstDefinition.AISTATUS_INVALIDATE.equals(
					paymentDto.getStatus()))
					continue;

                                //zyg 2006.11.20 begin
                                //费用金额的正负号必须与支付金额的正负号相同
                                /******************************************************/
                                if(value*paymentDto.getAmount() < 0)
                                  continue;
                                /******************************************************/
                                //zyg 2006.11.20 end

				//主动预存不进行销帐
/*				if(CommonConstDefinition.PAYMENTRECORD_TYPE_INITIATIVEPRESAVE.equals(paymentDto.getPayType()))
					continue;*/
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				//设置销帐明细的客户、帐户、操作员、所属机构号信息
				financeMapDto.setAcctId(accItemDto.getAcctID());
				financeMapDto.setCustId(accItemDto.getCustID());
				financeMapDto.setOpId(accItemDto.getOperatorID());
				financeMapDto.setOrgId(accItemDto.getOrgID());
				//销帐明细的状态为“正常”
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//正的销帐关联记录类型为"支付"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_P);
				//负的销帐关联记录类型为"费用"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);

				System.out.println("accItemDto.getForcedDepositFlag()============="+accItemDto.getForcedDepositFlag());
				System.out.println("paymentDto.getPayType()========="+paymentDto.getPayType());

                                //当支付列表中的预存全部是主动预存时
                                //zyg 2006.11.20 begin
                                /*******************************************************************************
				if(CommonConstDefinition.YESNOFLAG_NO.equals(accItemDto.getForcedDepositFlag())
					&& CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType()))
				{
					//确定不存在强制预存或强制预存已销帐
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
				 只有当循环处理中的费用记录的强制预存标记、支付记录的支付类型
				 都指向同样的类型（比如：都指向“预存”、都指向“受理单支付”）时
				 才可以建立销帐明细。
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
					//如果销帐费用是强制预存，则销帐明细的销帐类型为“强制预存”
					if(CommonConstDefinition.YESNOFLAG_YES.equals(accItemDto.getForcedDepositFlag()))
						financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_F);
					//如果销帐费用是非预存，则销帐明细的销帐类型为“直接销帐”
					if(CommonConstDefinition.YESNOFLAG_NO.equals(accItemDto.getForcedDepositFlag()))
						financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
					//将第一次参加销帐的支付记录加入另一个支付列表中
					if(String.valueOf(paymentDto.getSeqNo()) == null ||
						paymentDto.getSeqNo() == 0)
					{
						payNO--;
						/*
					     为了跟销帐明细建立联系，设置虚拟支付记录序列号，
					     数据持久化时再生成真正的支付记录序列号。
					    */
						paymentDto.setSeqNo(payNO);
						PaymentRecordDTO paymentDto1 = getPaymentDto(paymentDto);
						payRecordList.add(paymentDto1);
					}

					//预存退费不参与销帐
					if (CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE.equals(paymentDto.getPayType()))
						continue;
					//如果费用记录已经销帐，则不再进行销帐
					if (CommonConstDefinition.SETOFFFLAG_D.equals(accItemDto.getSetOffFlag()))
						continue outer;
					/*
					 如果费用金额正好等于支付金额，则建立一对一的销帐记录，
					 销帐金额等于费用金额；
					 如果费用金额小于支付金额，则支付金额减去费用金额的余额，
					 留做下一个循环继续销帐。
					*/

					System.out.println("paymentDto.getAmount()========"+paymentDto.getAmount());

					if(Math.abs(value) <= Math.abs(paymentDto.getAmount()))
					{
                                          //销帐明细的销帐金额为费用明细的费用金额
                                          //zyg 2006.11.20 begin
                                          //注意销账金额的正负问题
                                          //financeMapDto.setValue(Math.abs(value));
                                          financeMapDto.setValue(value);
                                          //zyg 2006.11.20 end

						paymentList.remove(j);
						if(Math.abs(value) < Math.abs(paymentDto.getAmount()))
						{
							//将预存/支付金额减去费用金额的余额设置为“新的”预存/支付金额
							paymentDto.setAmount(BusinessUtility.doubleFormat(paymentDto.getAmount() - value));   //modify by david.yang
							//paymentDto.setAmount((double) Math.floor(paymentDto.getAmount()*100 - value*100+0.01)/100);   //modify by david.yang
									//			 accItemDto.getValue()*100)/100);
							/**
							 * 将“新的”预存/支付金额的支付记录重新插入支付列表中
							 * 进行下一轮循环的销帐
							 */
							paymentList.add(j, paymentDto);
						}
						accItemDto.setSetOffAmount(accItemDto.getValue());
						accItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						//销帐明细中对应的费用记录序列号
						financeMapDto.setMinusReferId(accItemDto.getAiNO());
						//销帐明细中对应的支付记录序列号
						financeMapDto.setPlusReferId(paymentDto.getSeqNo());
						//创建销帐明细列表
						financeMapList.add(financeMapDto);
						//避免新的支付列表中遗漏主动预存

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
                                                  //将此费用记录置为“已销帐”
                                                  accItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
                                                  continue;
                                                }
                                                /*****************************************************/
                                                //zyg 2006.11.20 end

						//继续循环，建立销帐明细
						continue outer;
					}
					/*
					 如果费用金额大于支付金额，则销帐金额等于支付金额；
					 费用记录中的销帐标记置为“部分销帐”；
					 费用记录中的销帐金额等于支付金额；
					 费用金额减去支付金额的余额，留做下一个循环继续销帐。
					*/
					if(Math.abs(value) > Math.abs(paymentDto.getAmount()))
					{
						//销帐明细的销帐金额为支付明细的支付金额
                                                //zyg 2006.11.20 begin
                                                //注意销账金额的正负问题
                                                /***********************************************/
                                                //financeMapDto.setValue(Math.abs(paymentDto.getAmount()));
                                                financeMapDto.setValue(paymentDto.getAmount());
                                                /***********************************************/
                                                //zyg 2006.11.20 end


						//将已销帐的支付记录删除
						paymentList.remove(j);
						//将费用金额减去预存/支付金额的余额设置为“新的”费用金额
//						accItemDto.setValue(accItemDto.getValue()-paymentDto.getAmount());
						accItemDto.setSetOffAmount(BusinessUtility.doubleFormat(accItemDto.getSetOffAmount() + paymentDto.getAmount()));
						//accItemDto.setSetOffAmount((double) Math.floor(accItemDto.getSetOffAmount()*100 + paymentDto.getAmount()*100+0.01)/100);
						accItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_P);
						/**
						 * 将“新的”费用金额的费用记录重新插入费用列表中
						 * 进行下一轮循环的销帐
						 */
					//	feeList.add(i+1,accItemDto);      modify by david.Yang
						i--;                            //继续对费用进行销帐  add by david.Yang
						//销帐明细中对应的费用记录序列号
						financeMapDto.setMinusReferId(accItemDto.getAiNO());
						//销帐明细中对应的支付记录序列号
						financeMapDto.setPlusReferId(paymentDto.getSeqNo());
						//创建销帐明细列表
						financeMapList.add(financeMapDto);
						//继续循环，建立销帐明细
						continue outer;
					}
				}
			}
		}


                //zyg 2006.11.20 begin
                //处理剩余的支付记录，将其处理成主动预存或者预存退费，因为主动预存或者预存退费不用建立销账关系
                //这一步只是为了将来的持久化做准备
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
                //检查有无未处理的费用记录/支付记录/预存抵扣记录
                /*******************************************/
                if(!(feeList.size() == 0))
                  LogUtility.log(clazz,LogLevel.WARN, "feeList size != 0 after process completed : "+feeList.size());

                if(!(paymentList.size() == 0))
                  LogUtility.log(clazz,LogLevel.WARN, "paymentList size != 0 after process completed : "+paymentList.size());

                if(!(prePaymentList.size() == 0))
                  LogUtility.log(clazz,LogLevel.WARN, "prepaymentList size != 0 after process completed : "+prePaymentList.size());
                /*******************************************/
                //zyg 2006.11.20 end


                //销帐后，得到新的费用列表
                if(!(accItemList.size() == 0))
                        feeList = accItemList;
                //销帐后，得到新的支付列表
                if(!(payRecordList.size() == 0))
                        paymentList = payRecordList;
                //销帐后，得到新的预存抵扣列表
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
	 * 建立相同帐目类型之间的销帐关系
	 * 建立反号预存抵扣之间的销帐关系
	 * 建立反号支付间的销帐关系.
	 */
	private void buildSameAcctItemSetOffMap()
	{
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + "buildSameAcctItemSetOffMap");		
		ArrayList accItemList = new ArrayList();
		//同帐目类型费用销帐后的费用列表.
		
		//按帐目类型排序
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
		//	得到排序后的费用列表
		for(int i=0; i<feeList.size(); i++)
		{
			accItemList.add(accItemDto3[i]);
		}
		
		String acctItem1;
		String acctItem2;
		//进行相同帐目类型的费用的销账
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
					continue loop1;//如果待销费用已经销账,那么退出循环1

				if(CommonConstDefinition.SETOFFFLAG_D.equals(accItemDto2.getSetOffFlag()))
					continue loop2;//如果待销费用已经销账,那么退出循环2

				if(acctItem2.compareTo(acctItem1)!=0) 
					continue loop2;//非相同帐目类型的不能销账

				long lsetOffAmount2 = double2long(accItemDto2.getValue()) - double2long(accItemDto2.getSetOffAmount());
				
				if(lsetOffAmount1 * lsetOffAmount2 >= 0 )
					continue loop2;//同号不进行销帐
				
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				//设置销帐明细的客户、帐户、操作员、所属机构号信息
				financeMapDto.setAcctId(accItemDto1.getAcctID());
				financeMapDto.setCustId(accItemDto1.getCustID());
				financeMapDto.setOpId(accItemDto1.getOperatorID());
				financeMapDto.setOrgId(accItemDto1.getOrgID());
				//销帐明细的销帐类型为“直接销帐”
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//销帐明细的状态为“正常”
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//正的销帐关联记录类型为"费用"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);
				//负的销帐关联记录类型为"费用"
				financeMapDto.setMinusReferType(CommonConstDefinition.SETOFFREFERTYPE_F);					

				if( lsetOffAmount1 >0 ) //根据正负号填写销帐DTO
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
					//费用2完全销帐
					accItemList.remove(j);
					accItemDto2.setSetOffAmount(accItemDto2.getValue());
					accItemDto2.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
					accItemList.add(j,accItemDto2);

					//费用1部分销帐				
					accItemList.remove(i);
					accItemDto1.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_P);
					accItemDto1.setSetOffAmount((double)(double2long(accItemDto1.getSetOffAmount()) - lsetOffAmount2)/100);
					accItemList.add(i,accItemDto1);
					
					financeMapDto.setValue((double)Math.abs(lsetOffAmount2)/100);
					financeMapList.add(financeMapDto); //销帐明细建立
					
					continue loop1; //费用1销帐完全跳出循环
				}
				else
				{
					//费用1完全销帐
					accItemList.remove(i);
					accItemDto1.setSetOffAmount(accItemDto1.getValue());
					accItemDto1.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
					accItemList.add(i,accItemDto1);
					
					if((lsetOffAmount1 + lsetOffAmount2) == 0)
					{
						//如果正好相同，那么费用2也完全销账
						accItemList.remove(j);
						accItemDto2.setSetOffAmount(accItemDto2.getValue());
						accItemDto2.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_D);
						accItemList.add(j,accItemDto1);
					}
					else
					{
						//费用2部分销账
						accItemList.remove(j);
						accItemDto1.setSetOffAmount((double)(double2long(accItemDto2.getSetOffAmount()) - lsetOffAmount1)/100);
						accItemDto2.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_P);
						accItemList.add(j,accItemDto1);						
					}
					
					financeMapDto.setValue((double)Math.abs(lsetOffAmount1)/100);
					financeMapList.add(financeMapDto); //销帐明细建立
				}
			}
		}
		feeList.clear();
		feeList.addAll(accItemList);//重建费用列表
		
		
		/** 注意点:
		 * 由于做完销帐后预存抵扣列表不进行持久化操作,所以可以在这里临时更改Amount,
		 * 用来进一步销帐.
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
				//同号不进行销帐
				FinanceSetOffMapDTO financeMapDto = new FinanceSetOffMapDTO();
				//设置销帐明细的客户、帐户、操作员、所属机构号信息
				financeMapDto.setAcctId(prePaymentDto1.getAcctId());
				financeMapDto.setCustId(prePaymentDto1.getCustId());
				financeMapDto.setOpId(prePaymentDto1.getOpId());
				financeMapDto.setOrgId(prePaymentDto1.getOrgId());
				//销帐明细的销帐类型为“直接销帐”
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//销帐明细的状态为“正常”
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//正的销帐关联记录类型为"费用"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_D);
				//负的销帐关联记录类型为"费用"
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
					//预存抵扣2 完全抵扣
					//预存抵扣1 不完全抵扣
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
					//预存抵扣1 完全抵扣
					if(Math.abs(Math.abs(lValue1)-Math.abs(lValue2))==0)
					{
						//预存抵扣2完全抵扣
						prePaymentDto1.setAmount(0.0f);
						prePaymentDto2.setAmount(0.0f);
					}
					else
					{
						//预存抵扣2不完全抵扣
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
		 * 注意 
		 * 支付与支付间销帐关系建立后,支付记录不需要持久化,
		 * 故在此会更改支付记录的金额,并对后续代码产生影响.
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
				//设置销帐明细的客户、帐户、操作员、所属机构号信息
				financeMapDto.setAcctId(paymentRecordDTO1.getAcctID());
				financeMapDto.setCustId(paymentRecordDTO1.getCustID());
				financeMapDto.setOpId(paymentRecordDTO1.getOpID());
				financeMapDto.setOrgId(paymentRecordDTO1.getOrgID());
				//销帐明细的销帐类型为“直接销帐”
				financeMapDto.setSType(CommonConstDefinition.SETOFFTYPE_P);
				//销帐明细的状态为“正常”
				financeMapDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
				//正的销帐关联记录类型为"费用"
				financeMapDto.setPlusReferType(CommonConstDefinition.SETOFFREFERTYPE_P);
				//负的销帐关联记录类型为"费用"
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
	 * 对费用列表、支付列表、预存抵扣列表按金额从小到大排序
	 * 采用冒泡排序算法
	 */
	private void listArrange()
	{
		//排序后的费用列表
		ArrayList accItemList = new ArrayList();
		AccountItemDTO[] accItemDto3 = new AccountItemDTO[feeList.size()];
		for(int i=0; i<feeList.size(); i++)
		{
			accItemDto3[i] = (AccountItemDTO)feeList.get(i);
		}
		//将费用列表按金额从小到大排序
		AccountItemDTO accItemDto1 = new AccountItemDTO();
		AccountItemDTO accItemDto2 = new AccountItemDTO();
		AccountItemDTO accItemDto4 = new AccountItemDTO();
		if(feeList.size() > 1)//如果费用列表中只有一条明细，则不用排序。
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
					 将费用列表中前一个费用金额的值与后一个费用金额的值进行比较
					 如果前者大于后者，则进行置换。
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
			//得到排序后的费用列表
			for(int i=0; i<feeList.size(); i++)
			{
				accItemList.add(accItemDto3[i]);
			}
			feeList = accItemList;
		}
		//排序后的支付列表
		ArrayList payRecordList = new ArrayList();
		PaymentRecordDTO[] paymentDto3 = new PaymentRecordDTO[paymentList.size()];
		for(int i=0; i<paymentList.size(); i++)
		{
			paymentDto3[i] = (PaymentRecordDTO)paymentList.get(i);
		}
		//将支付列表按金额从小到大排序
		PaymentRecordDTO paymentDto1 = new PaymentRecordDTO();
		PaymentRecordDTO paymentDto2 = new PaymentRecordDTO();
		PaymentRecordDTO paymentDto4 = new PaymentRecordDTO();
		if(paymentList.size() > 1)//如果支付列表中只有一条明细，则不用排序。
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
					 将支付列表按付费方式的编号从大到小排序。
					 对于相同付费方式的支付列表按金额从小到大排序
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
			//得到排序后的支付列表
			for(int i=0; i<paymentList.size(); i++)
			{
				payRecordList.add(paymentDto3[i]);
			}
			paymentList = payRecordList;
		}
		//排序后的预存抵扣列表
		ArrayList payPayList = new ArrayList();
		PrepaymentDeductionRecordDTO[] prePaymentDto3 = new PrepaymentDeductionRecordDTO[prePaymentList.size()];
		for(int i=0; i<prePaymentList.size(); i++)
		{
			prePaymentDto3[i] = (PrepaymentDeductionRecordDTO)prePaymentList.get(i);
		}
		//将预存抵扣列表按金额从小到大排序
		PrepaymentDeductionRecordDTO prePaymentDto1 = new PrepaymentDeductionRecordDTO();
		PrepaymentDeductionRecordDTO prePaymentDto2 = new PrepaymentDeductionRecordDTO();
		PrepaymentDeductionRecordDTO prePaymentDto4 = new PrepaymentDeductionRecordDTO();
		if(prePaymentList.size() > 1)//如果预存抵扣列表中只有一条明细，则不用排序。
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
					 将预存抵扣列表中前一个支付金额的值与后一个支付金额的值进行比较
					 如果前者大于后者，则进行置换。
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
			//得到排序后的预存抵扣列表
			for(int i=0; i<prePaymentList.size(); i++)
			{
				payPayList.add(prePaymentDto3[i]);
			}
			prePaymentList = payPayList;
		}
	}

	/**
	 * 用于费用、支付、预存抵扣、销帐表数据的持久化
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
		//创建费用记录
//		createAmountItem();
		//创建支付记录
//		createPaymentRecord();
		//创建预存抵扣记录,同时更新帐户信息
//		if(prePaymentList != null)
//			createPrePaymentDeductionRecord();
/*		System.out.println("financeMapList1.size: "+this.financeMapList.size());
		for(int i=0; i<this.financeMapList.size(); i++)
		{
			FinanceSetOffMapDTO financeMapDto = (FinanceSetOffMapDTO)this.financeMapList.get(i);
			LogUtility.log(clazz,LogLevel.DEBUG,"financeMapList1",financeMapDto);
		}*/
		//创建销帐记录
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
			System.out.println("更新销帐金额,销帐标记时发生错误");
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
			System.out.println("设置发票号码时发生错误");
			ex.printStackTrace();
		}
	}
	/**
	 * 创建费用记录
	 * @throws ServiceException
	 */
	private void createAmountItem()throws ServiceException
	{
		//费用记录序列号
		int aiNO = 0;
		//销帐明细列表
		ArrayList mapList = new ArrayList();
		//创建费用记录，并且重新设置销帐明细对应的费用记录序列号
		for(int i=0; i<feeList.size(); i++)
		{
			AccountItemDTO accItemDto = (AccountItemDTO)feeList.get(i);
			
			//判断费用记录是否已经持久化
			if(accItemDto.getAiNO() > 0)
			{
				//  到createFinanceSetoffMap方法里面统一翻转状态 modify by david.Yang 2006-11-9
				//  PayFeeUtil.updateAcctItemStatus(accItemDto);
				continue;  //费用记录已经持久化
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
			//创建费用记录
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
				//当存在优惠费用时
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
				//当记录的产生来源为 计费 或 调帐，且金额小于0 的时候，建立销账关系
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
	 * 生成支付记录,同时更新帐户信息、受理单信息
	 * @throws ServiceException
	 */
	private void createPaymentRecord()throws ServiceException
	{
		//支付记录序列号
		int payNO = 0;
		//销帐明细列表
		ArrayList mapList = new ArrayList();
		//创建支付记录，并且重新设置销帐明细对应的支付记录序列号
		for(int i=0; i<paymentList.size(); i++)
		{
			PaymentRecordDTO paymentDto = (PaymentRecordDTO)paymentList.get(i);
			//判断费用记录是否已经持久化
			if(paymentDto.getSeqNo() > 0)
				continue;  //支付记录已经持久化
			if(paymentDto.getInvoicedFlag() == null)
				paymentDto.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
			//预存支付记录，其支付状态为“预生成”
			if(CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType()))
				paymentDto.setStatus(CommonConstDefinition.AISTATUS_POTENTIAL);
			//非预存支付记录在生成支付后，其支付状态为“有效”
//			if(!CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE.equals(paymentDto.getPayType()))
//				paymentDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			paymentDto.setCreateTime(TimestampUtility.getCurrentDate());
			paymentDto.setPaymentTime(TimestampUtility.getCurrentDate());
			paymentDto.setPrepaymentType(getPrepaymentType(paymentDto.getMopID()));
			//创建支付记录
			payNO = PayFeeUtil.createPaymentRecordDTO(paymentDto);
			LogUtility.log(clazz,LogLevel.DEBUG,"支付记录序列号: "+payNO);
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
					//支付记录在生成支付后如果状态为“有效”，其相应的费用记录状态更新为“有效”
					if (CommonConstDefinition.AISTATUS_VALIDATE.equals(paymentDto.getStatus()))
						PayFeeUtil.updateAcctItemStatus(financeMapDto.getMinusReferId(),financeMapDto.getAcctId());
				}
				mapList.add(financeMapDto);
			}
			//更新帐户信息
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
			LogUtility.log(clazz,LogLevel.WARN,"更改费用记录信息出错，原因：费用记录定位出错！");
			throw new ServiceException("更改费用记录信息出错，原因：费用记录定位出错！");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改费用记录信息出错，原因：费用记录查找出错！");
			throw new ServiceException("更改费用记录信息出错，原因：费用记录查找出错");
		}
		if("Y".equalsIgnoreCase(cashFlag))
	 		return "C";
		else
			return "T";
	}
	/**
	 * 生成预存抵扣记录,同时更新帐户信息、受理单信息
	 * @throws ServiceException
	 */
	private void createPrePaymentDeductionRecord()throws ServiceException
	{
		//预存抵扣记录序列号
		int prePayNO = 0;
		//销帐明细列表
		ArrayList mapList = new ArrayList();
		//创建预存抵扣记录，并且重新设置销帐明细对应的预存抵扣记录序列号
		for(int i=0; i<prePaymentList.size(); i++)
		{
			PrepaymentDeductionRecordDTO prePaymentDto = (PrepaymentDeductionRecordDTO)prePaymentList.get(i);
			//判断预存抵扣记录是否已经持久化
			if(prePaymentDto.getSeqNo() > 0)
				continue;  //预存抵扣记录已经持久化
			if(prePaymentDto.getInvoicedFlag() == null)
				prePaymentDto.setInvoicedFlag(CommonConstDefinition.YESNOFLAG_NO);
			//预存抵扣记录状态为“有效”
			prePaymentDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			prePaymentDto.setCreateTime(TimestampUtility.getCurrentDateWithoutTime());
			//创建预存抵扣记录
			prePayNO = PayFeeUtil.createPrePaymentDeductionRecordDTO(prePaymentDto);
			LogUtility.log(clazz,LogLevel.DEBUG,"预存抵扣记录序列号: "+prePayNO);
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
					//更新相应的费用记录状态为“有效”
					PayFeeUtil.updateAcctItemStatus(financeMapDto.getMinusReferId(),financeMapDto.getAcctId());
				}
				mapList.add(financeMapDto);
			}
			//更新帐户信息
			PayFeeUtil.updateAccount(prePaymentDto.getAcctId()
						  ,-prePaymentDto.getAmount()
						  ,prePaymentDto.getPrepaymentType());
		}
		if(!(mapList.size() == 0))
			financeMapList = mapList;
	}

	/**
	 * 生成销帐记录
	 * @param financeMapList 销帐明细列表
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
	    //生成销帐记录
		try
		{
			for(int i=0; i<financeMapList.size(); i++)
			{
				FinanceSetOffMapDTO financeMapDto = (FinanceSetOffMapDTO)((ArrayList)financeMapList).get(i);
				financeMapDto.setCreateTime(TimestampUtility.getCurrentDateWithoutTime());
				LogUtility.log(clazz, LogLevel.DEBUG,"在createFinanceSetoffMap创建销帐明细信息开始");
				FinanceSetOffMapHome financeMapHome = HomeLocater.getFinanceSetOffMapHome();
				LogUtility.log(clazz, LogLevel.DEBUG, "createFinanceSetoffMap",financeMapDto);
				FinanceSetOffMap financeMap = financeMapHome.create(financeMapDto);
				LogUtility.log(clazz, LogLevel.DEBUG,"在createFinanceSetoffMap创建销帐明细信息结束");
			}
		}catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createFinanceSetoffMap",e);
			throw new ServiceException("生成销帐明细信息时定位错误");
		}catch(CreateException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createFinanceSetoffMap",e);
			throw new ServiceException("生成销帐明细信息错误");
		}
	}

	/**
	 * 生成销帐记录
	 * @param financeMapList 销帐明细列表
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
		//生成销帐记录
		if(financeMapList == null) return;
		try
		{
			for(int i=0; i<financeMapList.size(); i++)
			{
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~" + i);				
				FinanceSetOffMapDTO financeMapDto = (FinanceSetOffMapDTO)((ArrayList)financeMapList).get(i);
                // 费用记录都已生成 add by david.Yang date: 2006-11-9
				/*if (CommonConstDefinition.SETOFFREFERTYPE_F.equals(financeMapDto.getPlusReferType())){
					PayFeeUtil.updateAcctItemStatus(financeMapDto.getMinusReferId(),financeMapDto.getAcctId());
   				    PayFeeUtil.updateAcctItemStatus(financeMapDto.getPlusReferId(),financeMapDto.getAcctId());
				}*/

				//支付记录都已生成
/*				if(CommonConstDefinition.SETOFFREFERTYPE_P.equals(financeMapDto.getPlusReferType())
				   && this.getPaymentList().size() == 0)
				{
					PayFeeUtil.updateAcctItemStatus(financeMapDto.getMinusReferId(),financeMapDto.getAcctId());
					PayFeeUtil.updatePaymentStatus(financeMapDto.getPlusReferId());
				}*/
				//抵扣记录都已生成
/*				if(CommonConstDefinition.SETOFFREFERTYPE_D.equals(financeMapDto.getPlusReferType())
				   && this.getPrePaymentList().size() == 0)
				{
					PayFeeUtil.updateAcctItemStatus(financeMapDto.getMinusReferId(),financeMapDto.getAcctId());
					//更新帐户信息
					PrepaymentDeductionRecordHome prePaymentHome = HomeLocater.getPrepaymentDeductionRecordHome();
					PrepaymentDeductionRecord prePayment = prePaymentHome.findByPrimaryKey(new Integer(financeMapDto.getPlusReferId()));
					PayFeeUtil.updateAccount(financeMapDto.getAcctId()
											 , -financeMapDto.getValue()
											 , prePayment.getPrepaymentType());
				}*/
				financeMapDto.setCreateTime(TimestampUtility.getCurrentDateWithoutTime());
				LogUtility.log(clazz, LogLevel.DEBUG,"在createFinanceSetoffMap创建销帐明细信息开始");
				FinanceSetOffMapHome financeMapHome = HomeLocater.getFinanceSetOffMapHome();
				LogUtility.log(clazz, LogLevel.DEBUG, "createFinanceSetoffMap",financeMapDto);
				FinanceSetOffMap financeMap = financeMapHome.create(financeMapDto);
				LogUtility.log(clazz, LogLevel.DEBUG,"在createFinanceSetoffMap创建销帐明细信息结束");
			}
		}catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createFinanceSetoffMap",e);
			throw new ServiceException("生成销帐明细信息时定位错误");
		}/*catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"更改抵扣记录信息出错，原因：抵扣记录查找出错！");
			throw new ServiceException("更改抵扣记录信息出错，原因：抵扣记录查找出错");
		}*/catch(CreateException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR,"createFinanceSetoffMap",e);
			throw new ServiceException("生成销帐明细信息错误");
		}
	}

	/**
	 * 拷贝费用列表，并添加虚拟主键值
	 * @param accItemDto 费用明细
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
	 * 拷贝支付列表，并添加虚拟主键值
	 * @param paymentDto 支付记录
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
