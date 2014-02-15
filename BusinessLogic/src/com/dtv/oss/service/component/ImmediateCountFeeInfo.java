package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * <p>Title: 计费处理信息</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: DTV</p>
 * @author Wesley
 * @version 1.0
 */
public class ImmediateCountFeeInfo implements Serializable
{
    //用于存储原有的预存纪录。
	private Collection prePaymentList;
	//用于销帐的归并费用明细, Collection of AccountItemDTO
	private Collection acctItemList;
	//新产生的费用
	private Collection newAcctItemList;

	//预存抵扣明细, Collection of PrePaymentDeductionRecordDTO	
	private Collection prePaymentRecordList;
	private Collection newPrepayRecordList;

	private Collection newPaymentRecordList;
	//支付列表
	private Collection paymentRecordList;
	
	//团购时的预先支付的支付列表(这个变量给前台使用,其他处理不要使用这个变量)
	private Collection gropBargainPayedList;
	
	//预存款现金账户余额
	private double preCashDoposit;
	//预存款虚拟货币账户余额
	private double preTokenDoposit;
	//需要退费或收费的费用总金额
	private double totalValueAccountItem;
	//账户号
	private int accountid;
	//账户名称
	private String accountName;
	//支付类型
	private String payType;
	//预存款退费金额
	private double totalPreReturnFee;

	private Collection spCache;

	public ImmediateCountFeeInfo()
	{
		this.preCashDoposit = 0.0f;
		this.preTokenDoposit = 0.0f;
		this.totalPreReturnFee = 0.0f;
		this.totalValueAccountItem = 0.0f;
		this.acctItemList = new ArrayList();
		this.prePaymentRecordList = new ArrayList();
		this.paymentRecordList = new ArrayList();
		this.accountid = 0;
		this.accountName = null;
		this.payType = null;
		this.newAcctItemList = new ArrayList();
		this.newPaymentRecordList = new ArrayList();
		this.newPrepayRecordList = new ArrayList();
		this.spCache = null;
		this.prePaymentList = null;
	}
	public ImmediateCountFeeInfo(double preCashDoposit ,double preTokenDoposit
								 ,double totalValueAccountItem ,Collection acctItemList
								 ,Collection prePaymentRecordList,Collection paymentRecordList,int accountid
		                         ,String accountName,String payType,double totalPreReturnFee)
	{
		this.preCashDoposit = preCashDoposit;
		this.preTokenDoposit = preTokenDoposit;
		this.totalValueAccountItem = totalValueAccountItem;
		this.acctItemList = acctItemList;
		this.prePaymentRecordList = prePaymentRecordList;
		this.paymentRecordList = paymentRecordList;
		this.accountid = accountid;
		this.accountName = accountName;
		this.payType = payType;
		this.totalPreReturnFee = totalPreReturnFee;
		spCache = null;
	}

	public Collection getSpCache()
	{
		return spCache;
	}

	public void setSpCache(Collection cache)
	{
		this.spCache = cache;
	}

	public Collection addNewAcctItem(Collection newAcctItemList)
	{
		this.newAcctItemList.addAll(newAcctItemList);
		return this.newAcctItemList;
	}

	public Collection addNewPaymentRecord(Collection newPaymentRecordList)
	{
		this.newPaymentRecordList.addAll(newPaymentRecordList);
		return this.newPaymentRecordList;
	}

	public Collection addNewPrePaymentRecord(Collection newPrepayRecordList)
	{
		this.newPrepayRecordList.addAll(newPrepayRecordList);
		return this.newPrepayRecordList;
	}

	public Collection getNewPaymentRecordList()
	{
		return newPaymentRecordList;
	}
	
	public void setNewPaymentRecordList(Collection paymentList)
	{
		this.newPaymentRecordList = paymentList;
	}	

	public void setNewPrepayRecordList(Collection newPrepay)
	{
		this.newPrepayRecordList = newPrepay;		
	}
	
	public Collection getNewPrepayRecordList()
	{
		return newPrepayRecordList;
	}

	public void setNewAccountItem(Collection newAcctItemCol)
	{
		this.newAcctItemList = newAcctItemCol;
	}
	public Collection getNewAccountItem()
	{
		return newAcctItemList;
	}

	public void setTotalPreReturnFee(double totalPreReturnFee)
	{
		this.totalPreReturnFee = totalPreReturnFee;
	}
	public double getTotalPreReturnFee()
	{
		return this.totalPreReturnFee;
	}
	public void setPreCashDoposit(double preCashDoposit)
	{
		this.preCashDoposit = preCashDoposit;
	}
	public double getPreCashDoposit()
	{
		return this.preCashDoposit;
	}
	public void setPreTokenDoposit(double preTokenDoposit)
	{
		this.preTokenDoposit = preTokenDoposit;
	}
	public double getPreTokenDoposit()
	{
		return this.preTokenDoposit;
	}
	public void setTotalValueAccountItem(double totalValueAccountItem)
	{
		this.totalValueAccountItem = totalValueAccountItem;
	}
	public double getTotalValueAccountItem()
	{
		return this.totalValueAccountItem;
	}
	public void setAcctItemList(Collection acctItemList)
	{
		this.acctItemList = acctItemList;
	}
	public Collection getAcctItemList()
	{
		return acctItemList;
	}
	public void setPrePaymentRecordList(Collection prePaymentRecordList)
	{
		this.prePaymentRecordList = prePaymentRecordList;
	}
	public Collection getPrePaymentRecordList()
	{
		return prePaymentRecordList;
	}
	public void setAccountid(int accountid)
	{
		this.accountid = accountid;
	}
	public int getAccountid()
	{
		return this.accountid;
	}

	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}

	public String getAccountName()
	{
		return this.accountName;
	}

	public void setPayType(String payType)
	{
		this.payType = payType;
	}

	public String getPayType()
	{
		return this.payType;
	}

	public Collection getPaymentRecordList()
	{
		return paymentRecordList;
	}

	public void setPaymentRecordList(Collection paymentList)
	{
		this.paymentRecordList = paymentList;
	}
	public String toString()
	{
		StringBuffer content=new StringBuffer();
		content.append("{preCashDoposit=").append(preCashDoposit).append("}").append("■");
		content.append("{preTokenDoposit=").append(preTokenDoposit).append("}").append("■");
		content.append("{totalValueAccountItem=").append(totalValueAccountItem).append("}").append("■");
		content.append("{acctItemList=").append(acctItemList).append("}").append("■");
		content.append("{newAcctItemList=").append(newAcctItemList).append("}").append("■");
		content.append("{paymentRecordList=").append(paymentRecordList).append("}").append("■");
		content.append("{newPaymentRecordList=").append(newPaymentRecordList).append("}").append("■");
		content.append("{prePaymentRecordList=").append(prePaymentRecordList).append("}").append("■");
		content.append("{newPrepayRecordList=").append(newPrepayRecordList).append("}").append("■");
		content.append("{gropBargainPayedList=").append(gropBargainPayedList).append("}").append("■");
		return content.toString();
	}
	/**
	 * @return the gropBargainPayedList
	 */
	public Collection getGropBargainPayedList() {
		return gropBargainPayedList;
	}
	/**
	 * @param gropBargainPayedList the gropBargainPayedList to set
	 */
	public void setGropBargainPayedList(Collection gropBargainPayedList) {
		this.gropBargainPayedList = gropBargainPayedList;
	}
	public void setPrePayList(Collection prePayList)
	{
		this.prePaymentList = prePayList;
	}
	public Collection getPrePayList()
	{
		return prePaymentList;
	}
}
