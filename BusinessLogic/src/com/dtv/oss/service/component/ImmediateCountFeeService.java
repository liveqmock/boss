package com.dtv.oss.service.component;

import java.util.ArrayList;
import com.dtv.oss.dto.*;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.log.LogLevel;
import javax.ejb.FinderException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.domain.AccountItemHome;
import com.dtv.oss.domain.AccountItem;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.domain.Account;
import com.dtv.oss.domain.AccountHome;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.dtv.oss.util.DBUtil;
import java.sql.Connection;
import com.dtv.oss.service.component.ImmediateCountFeeInfo;
import java.util.Collection;
import com.dtv.oss.domain.AcctItemTypeHome;
import com.dtv.oss.domain.AcctItemType;
import com.dtv.oss.service.util.PayFeeUtil;
import com.dtv.oss.domain.CustomerProductHome;
import com.dtv.oss.domain.CustomerProduct;
import com.dtv.oss.service.util.BusinessUtility;
/**
 * <p>Title: 销户退户计费处理接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: DTV</p>
 * @author Wesley
 * @version 1.0
 */

//此类负责销户退户立即计费汇总，最终决定是支付还是退费
public class ImmediateCountFeeService extends AbstractService
{
	private final static Class clazz = ImmediateCountFeeService.class;
	//客户的销户/退户
	public final static String CANCEL_CUSTOMER = "cancel_cust";
	//账户的销户
	public final static String CANCEL_ACCOUNT = "cancel_acct";
	//业务账户的取消
	public final static String CANCEL_SERVICEACCT = "cancel_serviceacct";
	//客户产品的取消
	public final static String CANCEL_CUSTPRODUCT = "cancel_custproduct";
	//需要退账户余额
	public final static String RETURN_CASHACCOUNT = "Y";
	//不需要退帐户余额
	public final static String NORETURN_CASHACCOUNT = "N";
	//需付的一次性费用总金额
	private double totalOnceFee;
	//需付的信息费用总金额
	private double totalInfoFee;
	//需付的未销帐费用总金额
	private double totalNoSetoffFee;
	//费用列表(ArrayList of AccountItemDTO)
	private ArrayList feeList;
	//计费处理列表(ImmediateCountFeeInfo)
	private ImmediateCountFeeInfo immediateFeeList;
	//需付的一次性费用明细, Collection of AccountItemDTO
	private ArrayList onceFeeList;
	//需付信息费用明细, Collection of AccountItemDTO
	private ArrayList infoFeeList;
	//需付的未销帐费用明细, Collection of AccountItemDTO
	private ArrayList noSetoffFeeList;
	//用来存放信息费的优惠－－－add by  杨晨
	private double favourableTotalValue;
	//账户号
	private int accountid;
	//受理标识
	private String cancelType;
	//账户退费标识
	private String returnType;

	//构造方法
	public ImmediateCountFeeService()
	{}
	public ImmediateCountFeeService(int accountid ,String cancelType ,String returnType
		                            ,Collection onceFeeList,Collection infoFeeList
		                            ,Collection feeList)
	{
		this.accountid = accountid;
		this.cancelType = cancelType;
		this.returnType = returnType;
		this.onceFeeList = (ArrayList)onceFeeList;
		if(onceFeeList == null)
			this.onceFeeList = new ArrayList();
		this.infoFeeList = (ArrayList)infoFeeList;
		if(infoFeeList == null)
			this.infoFeeList = new ArrayList();
		this.feeList = (ArrayList)feeList;
		if(feeList == null)
			this.feeList = new ArrayList();
		this.immediateFeeList = new ImmediateCountFeeInfo();
	}

	public void setAccountid(int accountid)
	{
		this.accountid = accountid;
	}
	public int getAccountid()
	{
		return this.accountid;
	}
	public void setTotalOnceFee(double totalOnceFee)
	{
		this.totalOnceFee = totalOnceFee;
	}
	public double getTotalOnceFee()
	{
		return this.totalOnceFee;
	}
	public void setTotalInfoFee(double totalInfoFee)
	{
		this.totalInfoFee = totalInfoFee;
	}
	public double getTotalInfoFee()
	{
		return this.totalInfoFee;
	}
	public void setTotalNoSetoffFee(double totalNoSetoffFee)
	{
		this.totalNoSetoffFee = totalNoSetoffFee;
	}
	public double getTotalNoSetoffFee()
	{
		return this.totalNoSetoffFee;
	}
	public void setInfoFeeList(ArrayList infoFeeList)
	{
		this.infoFeeList = infoFeeList;
	}
	public ArrayList getInfoFeeList()
	{
		return infoFeeList;
	}
	public void setOnceFeeList(ArrayList onceFeeList)
	{
		this.onceFeeList = onceFeeList;
	}
	public ArrayList getOnceFeeList()
	{
		return onceFeeList;
	}
	public void setNoSetoffFeeList(ArrayList noSetoffFeeList)
	{
		this.noSetoffFeeList = noSetoffFeeList;
	}
	public ArrayList getNoSetoffFeeList()
	{
		//侯加,为了不出现空指针
		if(noSetoffFeeList==null)noSetoffFeeList=new ArrayList();
		return noSetoffFeeList;
	}
	public void setImmediateFeeList(ImmediateCountFeeInfo immediateFeeList)
	{
		this.immediateFeeList = immediateFeeList;
	}
	public ImmediateCountFeeInfo getImmediateFeeList()
	{
		return immediateFeeList;
	}
	public void setFeeList(ArrayList feeList)
	{
		this.feeList = feeList;
	}
	public ArrayList getFeeList()
	{
		return feeList;
	}
	public void setCancelType(String cancelType)
	{
		this.cancelType = cancelType;
	}
	public String getCancelType()
	{
		return cancelType;
	}
	public void setReturnType(String returnType)
	{
		this.returnType = returnType;
	}
	public String getReturnType()
	{
		return returnType;
	}


	/**
	 * Command调用此方法实现立即计费汇总
	 * @param accountid int 账户号
	 * @param cancelType String 销户退户类型
	 * @param onceFeeList Collection 一次性费用列表
	 * @param infoFeeList Collection 信息费用列表
	 * @return ImmediateCountFeeInfo 立即计费处理信息
	 * @throws ServiceException
	 */
	public static ImmediateCountFeeInfo countFee(int accountid ,String cancelType ,String returnType
		                            ,Collection onceFeeList,Collection infoFeeList)throws ServiceException
	{
		if(onceFeeList != null)
			System.out.println("onceFeeList.size(): "+onceFeeList.size());
		if(infoFeeList != null)
			System.out.println("infoFeeList.size(): "+infoFeeList.size());
		ImmediateCountFeeService countFeeList = new ImmediateCountFeeService(accountid,cancelType,returnType
												                         ,onceFeeList,infoFeeList,null);
		ImmediateCountFeeInfo immediateFeeList = null;
		//归并一次性费和信息费
		countFeeList.uniteList();
		//针对不同的销户退户类型，包含相应的客户、账户、客户产品编号
		int id = 0;
		/*
		 对费用列表中的信息进行校验
		 如果校验通过，则返回相应的客户、账户、客户产品编号
		*/
		id = countFeeList.checkInfo();
		/*
		 以上校验都没问题的话，查询出费用表中未完成销帐的费用加上Command传入的费用,
		 在包括预存抵扣记录、费用总金额、帐户余额产生新的ImmediateCountFeeInfo列表
		*/
	    //利用客户号/账户号/客户产品号获得未完成销帐的费用记录，并产生新的计费列表feeList
	    countFeeList.getCountFeeList(id);
	    //获得现金预存账户金额和虚拟货币预存账户金额
	    countFeeList.getDeposit();
	    //获得新的计费列表
	    immediateFeeList = countFeeList.setoffFinance();
		return immediateFeeList;
	}

	/**
	 * Command调用此方法实现立即计费汇总
	 * @param accountid int 账户号
	 * @param cancelType String 销户退户类型
	 * @param feeList Collection 销帐费用列表
	 * @return ImmediateCountFeeInfo 立即计费处理信息
	 * @throws ServiceException
	 */
	public static ImmediateCountFeeInfo countFee(int accountid ,String cancelType
									,String returnType,Collection feeList)throws ServiceException
	{
		ImmediateCountFeeService countFeeList = new ImmediateCountFeeService(accountid,cancelType
			                                                       ,returnType,null,null,feeList);
		ImmediateCountFeeInfo immediateFeeList = null;
		//把销帐费用列表拆分成一次性费用列表、信息费用列表
		countFeeList.splitList();
		/*
		 对费用列表中的信息进行校验
		 如果校验通过，则返回相应的客户、账户、客户产品编号
		*/
		countFeeList.checkInfo();
		//获得现金预存账户金额和虚拟货币预存账户金额
		countFeeList.getDeposit();
		//获得新的计费列表
		immediateFeeList = countFeeList.setoffFinance();
		return immediateFeeList;
	}

	/**
	 * Command调用此方法实现立即计费汇总
	 * @return ImmediateCountFeeInfo the Collection of ImmediateCountFeeInfo  费用、预存抵扣、总金额、账户余额
	 * @throws ServiceException
	 */
	public ImmediateCountFeeInfo countFee()throws ServiceException
	{
		//归并一次性费和信息费
		uniteList();
		//针对不同的销户退户类型，包含相应的客户、账户、客户产品编号
		int id = 0;
		/*
		 对费用列表中的信息进行校验
		 如果校验通过，则返回相应的客户、账户、客户产品编号
		*/
		id = checkInfo();
		/*
		 以上校验都没问题的话，查询出费用表中未完成销帐的费用加上Command传入的费用,
		 在包括预存抵扣记录、费用总金额、帐户余额产生新的ImmediateCountFeeInfo列表
		*/
		//利用客户号/账户号/客户产品号获得未完成销帐的费用记录，并产生新的计费列表feeList
		getCountFeeList(id);
		//获得现金预存账户金额和虚拟货币预存账户金额
		getDeposit();
		//获得新的计费列表
		setoffFinance();
		return immediateFeeList;
	}

	/**
	 * 归并一次性费和信息费
	 */
	private void uniteList()
	{
		double totalOnceFee = 0.0f;
		double totalInfoFee = 0.0f;
		for(int i=0; i<onceFeeList.size(); i++)
		{
			AccountItemDTO accItemDto = (AccountItemDTO)onceFeeList.get(i);
			totalOnceFee = BusinessUtility.doubleFormat(totalOnceFee +accItemDto.getValue());
			//totalOnceFee =(double) Math.floor(totalOnceFee*100+ accItemDto.getValue()*100+0.01)/100;
			feeList.add(accItemDto);
		}
		for(int i=0; i<infoFeeList.size(); i++)
		{
			AccountItemDTO accItemDto = (AccountItemDTO)infoFeeList.get(i);
			totalInfoFee = BusinessUtility.doubleFormat(totalInfoFee +accItemDto.getValue());
			//totalInfoFee =(double) Math.floor(totalInfoFee*100 + accItemDto.getValue()*100+0.01)/100;
			feeList.add(accItemDto);
			//用来统计一下优惠的费用总额－－－add by  杨晨  2006/11/23--start
			if(accItemDto.getValue()<0){
				favourableTotalValue= BusinessUtility.doubleFormat(favourableTotalValue + accItemDto.getValue());
				//favourableTotalValue=(double) Math.floor(favourableTotalValue*100 + accItemDto.getValue()*100+0.01)/100;
			}
			//用来统计一下优惠的费用总额－－－add by  杨晨  2006/11/23--end
		}
		this.setTotalOnceFee(totalOnceFee);
		this.setTotalInfoFee(totalInfoFee);
	}

	/**
	 * 把销帐费用列表拆分成一次性费用列表、信息费用列表
	 * @throws ServiceException
	 */
	private void splitList()throws ServiceException
	{
		this.onceFeeList = new ArrayList();
		this.infoFeeList = new ArrayList();
		double totalOnceFee = 0.0f;
		double totalInfoFee = 0.0f;
		for(int i=0; i<feeList.size(); i++)
		{
			AccountItemDTO accItemDto = (AccountItemDTO)feeList.get(i);
			//通过费用帐目类型得到费用类型以区分一次性费和信息费
			String feeType = getFeeType(accItemDto.getAcctItemTypeID());
			//信息费
			if("M".equals(feeType))
			{
				totalInfoFee = BusinessUtility.doubleFormat(totalInfoFee +accItemDto.getValue());
				//totalInfoFee =(double) Math.floor(totalInfoFee*100 +accItemDto.getValue()*100+0.01)/100;
				infoFeeList.add(accItemDto);
			}
			else//一次性费
			{
				totalOnceFee = BusinessUtility.doubleFormat(totalOnceFee +accItemDto.getValue());
				//totalOnceFee =(double) Math.floor(totalOnceFee*100 +accItemDto.getValue()*100+0.01)/100;
				onceFeeList.add(accItemDto);
			}
		}
		this.setTotalOnceFee(totalOnceFee);
		this.setTotalInfoFee(totalInfoFee);
	}

	/**
	 * 通过费用帐目类型得到费用类型以区分一次性费和信息费
	 * @param accountItemTypeID String
	 * @return String
	 * @throws ServiceException
	 */
	private String getFeeType(String accountItemTypeID)throws ServiceException
	{
		String feeType = null;
		LogUtility.log(clazz,LogLevel.DEBUG,"进入查询帐目类型信息：");
		if(accountItemTypeID == null || accountItemTypeID.trim().length() == 0)
			throw new ServiceException("参数错误：帐目类型序列号为空！");
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"查询帐目类型信息，传入的帐目类型号为：" +accountItemTypeID);
			AcctItemTypeHome acctTypeHome = HomeLocater.getAcctItemTypeHome();
			AcctItemType acctType = acctTypeHome.findByPrimaryKey(accountItemTypeID);
			if(CommonConstDefinition.AISTATUS_INVALIDATE.equals(acctType.getFeeType()))
				throw new ServiceException("该帐目类型是无效帐目类型！");
			feeType = acctType.getFeeType();
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"查询帐目类型信息出错，原因：帐目类型定位出错！");
			throw new ServiceException("查询帐目类型信息出错，原因：帐目类型定位出错！");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"查询帐目类型信息出错，原因：帐目类型查找出错！");
			throw new ServiceException("查询帐目类型信息出错，原因：帐目类型查找出错");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束查询帐目类型信息！");
		return feeType;
	}

	/**
	 * 对费用列表中的信息进行校验
	 * @return int  客户号/账户号/客户产品号
	 * @throws ServiceException
	 */
	private int checkInfo()throws ServiceException
	{
		//校验费用列表是否为空
		if(feeList == null)
			throw new ServiceException("费用列表不能为空！");
//		if(cancelType == null || cancelType.trim().length() == 0)
//			throw new ServiceException("必须指明销户/退户类型！");

		int preCustomerID = 0;  //前一个明细的客户号
		int preAccountID = 0;   //前一个明细的帐户号
		int prePSID = 0;       //前一个明细的客户产品号
		int preSerAccountid = 0; //前一个明细的业务账户号
		//针对不同的销户退户类型，包含相应的客户、账户、客户产品编号
		int id = 0;
		//校验费用列表
		for(int i=0; i<feeList.size(); i++)
		{
			AccountItemDTO accItemDto = (AccountItemDTO)feeList.get(i);
			if(String.valueOf(accItemDto.getCustID()) == null ||
				accItemDto.getCustID() <= 0)
				throw new ServiceException("费用记录中客户号不能为空！");
			if(String.valueOf(accItemDto.getAcctID()) == null ||
				accItemDto.getAcctID() <= 0)
				throw new ServiceException("费用记录中帐户号不能为空！");
			//如果是客户产品的取消，则费用记录中客户产品号不能为空
/*			if(CANCEL_CUSTPRODUCT.equals(cancelType) && (accItemDto.getPsID() <= 0 ||
				String.valueOf(accItemDto.getPsID()) == null))
				throw new ServiceException("费用记录中客户产品号不能为空！");
			//如果是业务账户的取消，则费用记录中业务账户号不能为空
			if(CANCEL_SERVICEACCT.equals(cancelType) && (accItemDto.getServiceAccountID() <= 0 ||
				String.valueOf(accItemDto.getServiceAccountID()) == null))
				throw new ServiceException("费用记录中业务账户号不能为空！");
*/
			if(i > 0)
			{
				if (preCustomerID != accItemDto.getCustID())
					throw new ServiceException("费用列表中的记录不是同一个客户的！");
				if (preAccountID != accItemDto.getAcctID())
					throw new ServiceException("费用列表中的记录不是同一个帐户的！");
				//如果是客户产品的取消，则费用列表必须是针对同一客户产品
/*				if(CANCEL_CUSTPRODUCT.equals(cancelType) &&
				   prePSID != accItemDto.getPsID())
					throw new ServiceException("费用列表中的记录不是同一个客户产品的！");
				//如果是业务账户的取消，则费用列表必须是针对同一业务账户
				if(CANCEL_SERVICEACCT.equals(cancelType) &&
				   preSerAccountid != accItemDto.getServiceAccountID())
					throw new ServiceException("费用列表中的记录不是同一个业务账户的！");*/
			}
			preCustomerID = accItemDto.getCustID();
			preAccountID = accItemDto.getAcctID();
			prePSID = accItemDto.getPsID();
			preSerAccountid = accItemDto.getServiceAccountID();
		}
		//客户销户退户
		if(CANCEL_CUSTOMER.equals(cancelType))
			id = preCustomerID;
		//账户销户退户
		if(CANCEL_ACCOUNT.equals(cancelType))
			id = preAccountID;
		//业务账户取消
		if(CANCEL_SERVICEACCT.equals(cancelType))
			id = preSerAccountid;
		//客户产品取消
		if(CANCEL_CUSTPRODUCT.equals(cancelType))
			id = prePSID;
		return id;
	}

	/**
	 * 获得销户退户时，未完成销帐的费用列表，并产生新的计费列表
	 * @param id int  客户号/账户号/客户产品号
	 * @throws ServiceException
	 */
	private void getCountFeeList(int id)throws ServiceException
	{
		//计费列表
		ArrayList countFeeList = null;
		//需付的未销帐费用明细, Collection of AccountItemDTO
//		ArrayList noSetoffFeeList = new ArrayList();
		//需付的未销帐费用总金额
//		double totalNoSetoffFee = 0.0f;
		LogUtility.log(clazz,LogLevel.DEBUG,"进入查询费用记录信息：");
		try
		{
			CustomerProductHome custProductHome = HomeLocater.getCustomerProductHome();
			//通过账户号查询客户产品表信息
			ArrayList custProductInfo = (ArrayList)custProductHome.findByAccountID(this.getAccountid());
			AccountItemHome accItemHome = HomeLocater.getAccountItemHome();
			//客户销户退户
			if(CANCEL_CUSTOMER.equals(cancelType))
			{
				LogUtility.log(clazz,LogLevel.DEBUG,"查询费用记录信息，传入的客户号为：" +id);
				countFeeList = (ArrayList) accItemHome.findByCustomerID(id);
				getAccItemDto(countFeeList);
			}
			//账户销户
			if(CANCEL_ACCOUNT.equals(cancelType))
			{
				LogUtility.log(clazz,LogLevel.DEBUG,"查询费用记录信息，传入的账户号为：" +id);
				countFeeList = (ArrayList) accItemHome.findByAccountID(id);
				getAccItemDto(countFeeList);
			}
			//业务账户取消
			if(CANCEL_SERVICEACCT.equals(cancelType))
			{
				for(int i=0; i<custProductInfo.size(); i++)
				{
					int serviceAccountid = ((CustomerProduct)custProductInfo.get(i)).getServiceAccountID();
					LogUtility.log(clazz, LogLevel.DEBUG,"查询费用记录信息，传入的业务账户号为：" + id);
					countFeeList = (ArrayList) accItemHome.findByServiceAccountID(serviceAccountid);
					getAccItemDto(countFeeList);
				}
			}
			//客户产品取消
			if(CANCEL_CUSTPRODUCT.equals(cancelType))
			{
				for(int i=0; i<custProductInfo.size(); i++)
				{
					int psid = ((CustomerProduct) custProductInfo.get(i)).getPsID().intValue();
					LogUtility.log(clazz, LogLevel.DEBUG,"查询费用记录信息，传入的客户产品号为：" + id);
					countFeeList = (ArrayList) accItemHome.findByPSID(psid);
					getAccItemDto(countFeeList);
				}
			}
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"查询费用记录信息出错，原因：费用记录定位出错！");
			throw new ServiceException("查询费用记录信息出错，原因：费用记录定位出错！");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"查询费用记录信息出错，原因：费用记录查找出错！");
			throw new ServiceException("查询费用记录信息出错，原因：费用记录查找出错");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束查询费用记录信息！");
	}

	/**
	 * 对费用记录金额与预存款账户余额相互抵扣，得到新的费用列表
	 * @throws ServiceException
	 */
	private void getDeposit()throws ServiceException
	{
		int accountID = this.getAccountid();
		LogUtility.log(clazz,LogLevel.DEBUG,"进入查询帐户信息：");
		if(String.valueOf(accountID) == null || accountID == 0){
			throw new ServiceException("参数错误：帐户序列号为空！");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"查询帐户信息，传入的帐户号为：" +accountID);
			AccountHome acctHome = HomeLocater.getAccountHome();
			Account acct = acctHome.findByPrimaryKey(new Integer(accountID));
			if(CommonConstDefinition.AISTATUS_INVALIDATE.equals(acct.getStatus()))
				throw new ServiceException("该账户是无效账户！");
			immediateFeeList.setPreCashDoposit(acct.getCashDeposit());
			immediateFeeList.setPreTokenDoposit(acct.getTokenDeposit());
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"查询帐户信息出错，原因：帐户定位出错！");
			throw new ServiceException("查询帐户信息出错，原因：帐户定位出错！");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"查询帐户信息出错，原因：帐户查找出错！");
			throw new ServiceException("查询帐户信息出错，原因：帐户查找出错");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束查询帐户信息！");
	}

	/**
	 * 对费用记录金额与预存款账户余额相互抵扣，建立预存抵扣记录
	 * 同时判断是要退费还是要支付或者刚好持平，返回新的计费列表
	 * @return ImmediateCountFeeInfo 计费处理列表
	 * @throws ServiceException
	 */
	private ImmediateCountFeeInfo setoffFinance()throws ServiceException
	{
		//新生成信息费和未销帐信息费的合并
		ArrayList newInfoFeeList = getNewInfoFeeList();
		LogUtility.log(clazz,LogLevel.DEBUG,"newInfoFeeList.size() "+newInfoFeeList.size());
		//将FeeList赋给新的费用列表
		ArrayList newFeeList = getNewFeeList();
		//获得预存抵扣方式
		String preSetoffType = getPreSetoffType();
		LogUtility.log(clazz,LogLevel.DEBUG,"预存抵扣方式: "+preSetoffType);
		//现金预存款金额
		double cashDeposit = immediateFeeList.getPreCashDoposit();
		LogUtility.log(clazz,LogLevel.DEBUG,"现金预存款金额: "+cashDeposit);
		//虚拟货币预存款金额
		double tokenDeposit = immediateFeeList.getPreTokenDoposit();
		LogUtility.log(clazz,LogLevel.DEBUG,"虚拟货币预存款金额: "+tokenDeposit);
		//需要退费或补费的费用总金额
		double amountFee = 0.0f;
		//预存抵扣明细, Collection of PrePaymentDeductionRecordDTO
		ArrayList prePaymentRecordList = new ArrayList();
		//如果预存账户中没有余额，则传回费用列表和费用总金额
		//if(cashDeposit == 0 && tokenDeposit == 0)
		if(Math.abs(cashDeposit)<0.0001 && Math.abs(tokenDeposit)<0.0001)
		{
			amountFee = BusinessUtility.doubleFormat(this.getTotalInfoFee() + this.getTotalNoSetoffFee() + this.getTotalOnceFee());
			//amountFee = (double)(Math.floor(this.getTotalInfoFee()*100 + this.getTotalNoSetoffFee()*100 + this.getTotalOnceFee()*100+0.01)/100);
			LogUtility.log(clazz,LogLevel.DEBUG,"费用总额："+amountFee);
			immediateFeeList.setTotalValueAccountItem(amountFee);
			//预存款退费
			immediateFeeList.setTotalPreReturnFee(0);
			//补费
			if(amountFee > 0)
				immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
			//退费
			if(amountFee < 0)
				immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_FEE);
		}
		else//建立预存抵扣记录
		{
			//信息费跟预存款抵扣
			for(int i=0; i<newInfoFeeList.size(); i++)
			{
				
				AccountItemDTO accItemDto = (AccountItemDTO)newInfoFeeList.get(i);
				
				LogUtility.log(clazz,LogLevel.DEBUG,"信息费记录: ",accItemDto);
				//预存抵扣记录
				PrepaymentDeductionRecordDTO preSetoffInfo = new PrepaymentDeductionRecordDTO();
				//对费用列表中的产品费、信息费先用预存账户余额抵扣
				if(accItemDto.getValue() > 0)
				{
					LogUtility.log(clazz,LogLevel.DEBUG,"开始优惠："+favourableTotalValue);
					//用来存放信息费的优惠 Add by  杨晨   2006/11/23----start----
					if((favourableTotalValue+accItemDto.getValue())<0){
						favourableTotalValue=BusinessUtility.doubleFormat(favourableTotalValue+accItemDto.getValue());
						//favourableTotalValue=(double)Math.floor(favourableTotalValue*100+accItemDto.getValue()*100+0.01)/100;
						LogUtility.log(clazz,LogLevel.DEBUG,"计算后剩余优惠："+favourableTotalValue);
						continue;
					}else if(favourableTotalValue<0.0f){
						accItemDto.setValue(BusinessUtility.doubleFormat(favourableTotalValue+accItemDto.getValue()));
						//accItemDto.setValue((double)Math.floor(favourableTotalValue*100+accItemDto.getValue()*100+0.01)/100);
						favourableTotalValue=0.0f;
					}
					
					LogUtility.log(clazz,LogLevel.DEBUG,"计算后剩余优惠："+favourableTotalValue);
					//用来存放信息费的优惠 Add by  杨晨   2006/11/23----end----
					preSetoffInfo.setAcctId(accItemDto.getAcctID());
					preSetoffInfo.setCreatingMethod(accItemDto.getCreatingMethod());
					preSetoffInfo.setCustId(accItemDto.getCustID());
					preSetoffInfo.setInvoicedFlag(accItemDto.getInvoiceFlag());
					preSetoffInfo.setInvoiceNo(accItemDto.getInvoiceNO());
					preSetoffInfo.setOpId(accItemDto.getOperatorID());
					preSetoffInfo.setOrgId(accItemDto.getOrgID());
					preSetoffInfo.setReferRecordId(accItemDto.getReferID());
					preSetoffInfo.setReferRecordType(accItemDto.getReferType());
					preSetoffInfo.setStatus(accItemDto.getStatus());
					//现金优先
					if(CommonConstDefinition.PREPAYMENTDEDUCTIONMODE_C.equals(preSetoffType))
					{
						if(cashDeposit > 0)
						{
							if(cashDeposit >= accItemDto.getValue())
								preSetoffInfo.setAmount(accItemDto.getValue());
							else
							{
								preSetoffInfo.setAmount(cashDeposit);
								accItemDto.setValue(BusinessUtility.doubleFormat(accItemDto.getValue() - cashDeposit));
								//accItemDto.setValue((double)Math.floor(accItemDto.getValue() * 100 - cashDeposit * 100+0.01)/100);
								newInfoFeeList.add(i+1,accItemDto);
							}
							cashDeposit = BusinessUtility.doubleFormat(cashDeposit - preSetoffInfo.getAmount());
							//cashDeposit = (double)Math.floor(cashDeposit * 100 - preSetoffInfo.getAmount() * 100+0.01)/100;
							//现金抵扣
							preSetoffInfo.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_CASH);
							prePaymentRecordList.add(preSetoffInfo);
						}
						else if(tokenDeposit > 0)
						{
							if(tokenDeposit >= accItemDto.getValue())
								preSetoffInfo.setAmount(accItemDto.getValue());
							else
							{
								preSetoffInfo.setAmount(tokenDeposit);
								accItemDto.setValue(BusinessUtility.doubleFormat(accItemDto.getValue() - tokenDeposit));
								//accItemDto.setValue((double)Math.floor(accItemDto.getValue()*100 - tokenDeposit*100+0.01)/100);
								newInfoFeeList.add(i+1,accItemDto);
							}
							tokenDeposit = BusinessUtility.doubleFormat(tokenDeposit - preSetoffInfo.getAmount());
							//tokenDeposit = (double)Math.floor(tokenDeposit * 100 - preSetoffInfo.getAmount() * 100+0.01)/100;
							//虚拟货币抵扣
							preSetoffInfo.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY);
							prePaymentRecordList.add(preSetoffInfo);
						}
						else//如果账户余额为零，计算信息费的补费总金额
							amountFee = BusinessUtility.doubleFormat(amountFee + accItemDto.getValue());
							//amountFee=(double)Math.floor(amountFee * 100 + accItemDto.getValue() * 100+0.01)/100;
						
						LogUtility.log(clazz,LogLevel.DEBUG,"费用："+accItemDto.getValue());
						
					}
					//虚拟货币优先
					if(CommonConstDefinition.PREPAYMENTDEDUCTIONMODE_T.equals(preSetoffType))
					{
						if(tokenDeposit > 0)
						{
							if(tokenDeposit >= accItemDto.getValue())
								preSetoffInfo.setAmount(accItemDto.getValue());
							else
							{
								preSetoffInfo.setAmount(tokenDeposit);
								accItemDto.setValue(BusinessUtility.doubleFormat(accItemDto.getValue() - tokenDeposit));
								//accItemDto.setValue((double)Math.floor(accItemDto.getValue() * 100 - tokenDeposit * 100+0.01)/100);
								newInfoFeeList.add(i+1,accItemDto);
							}
							tokenDeposit = BusinessUtility.doubleFormat(tokenDeposit - preSetoffInfo.getAmount());
							//tokenDeposit = (double)Math.floor(tokenDeposit * 100 - preSetoffInfo.getAmount() * 100+0.01)/100;
							//虚拟货币抵扣
							preSetoffInfo.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY);
							prePaymentRecordList.add(preSetoffInfo);
						}
						else if(cashDeposit > 0)
						{
							if(cashDeposit >= accItemDto.getValue())
								preSetoffInfo.setAmount(accItemDto.getValue());
							else
							{
								preSetoffInfo.setAmount(cashDeposit);
								accItemDto.setValue(BusinessUtility.doubleFormat(accItemDto.getValue() - cashDeposit));
								//accItemDto.setValue((double)Math.floor(accItemDto.getValue() * 100 - cashDeposit * 100+0.01)/100);
								newInfoFeeList.add(i+1,accItemDto);
							}
							cashDeposit = BusinessUtility.doubleFormat(cashDeposit - preSetoffInfo.getAmount());
							//cashDeposit = (double)Math.floor(cashDeposit * 100 - preSetoffInfo.getAmount() * 100+0.01)/100;
							//现金抵扣
							preSetoffInfo.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_CASH);
							prePaymentRecordList.add(preSetoffInfo);
						}
						else//如果账户余额为零，计算信息费的补费总金额
							amountFee = BusinessUtility.doubleFormat(amountFee + accItemDto.getValue());
							//amountFee = (double)Math.floor(amountFee * 100 + accItemDto.getValue() * 100+0.01)/100;
						LogUtility.log(clazz,LogLevel.DEBUG,"费用："+accItemDto.getValue());
					}
					//按比例抵扣
					if(CommonConstDefinition.PREPAYMENTDEDUCTIONMODE_P.equals(preSetoffType))
					{
						PrepaymentDeductionRecordDTO preSetoffInfo1 = PayFeeUtil.getPrePaymentDto(preSetoffInfo);
						//现金或虚拟货币预存款小于一分钱的话，停止按比例抵扣
						if(cashDeposit > 0.01 || tokenDeposit > 0.01)
						{
							if ((cashDeposit + tokenDeposit) >= accItemDto.getValue())
							{
								LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(信息费)"+accItemDto.getValue());
								//按照比例计算预存抵扣的金额 Add by  杨晨   2006/11/23----start----
								//double cashpercent= (double) Math.floor((accItemDto.getValue()*100*(cashDeposit / (cashDeposit + tokenDeposit)))*100+0.01)/100;
								double cashpercent=BusinessUtility.doubleFormat(accItemDto.getValue()*(cashDeposit / (cashDeposit + tokenDeposit)));
								double leaveValue=BusinessUtility.doubleFormat(tokenDeposit-(accItemDto.getValue()-cashpercent));
								//double cashpercent=(double) Math.floor((double) Math.floor((accItemDto.getValue()*100*(cashDeposit / (cashDeposit + tokenDeposit)))*100+0.01)/100 +0.01)/100;
								//double leaveValue=(double) Math.floor(tokenDeposit*100-(accItemDto.getValue()*100-cashpercent*100))/100;
								double tokenpercent=0.0f;
								if(leaveValue<0.0f){
									tokenpercent= BusinessUtility.doubleFormat(accItemDto.getValue()*(tokenDeposit / (cashDeposit + tokenDeposit)));
									//tokenpercent= (double) Math.floor((accItemDto.getValue()*100*(tokenDeposit / (cashDeposit + tokenDeposit)))*100+0.01)/100;
								}else{
									tokenpercent= BusinessUtility.doubleFormat(accItemDto.getValue()-cashpercent);
									//tokenpercent=(double) Math.floor(accItemDto.getValue()*100-cashpercent*100+0.01)/100;
								}
								//按照比例计算预存抵扣的金额 Add by  杨晨   2006/11/23----end----
								//preSetoffInfo.setAmount(cashpercent);
								//preSetoffInfo1.setAmount(tokenpercent);
								preSetoffInfo.setAmount(BusinessUtility.doubleFormat(cashpercent));
								preSetoffInfo1.setAmount(BusinessUtility.doubleFormat(tokenpercent));
								//preSetoffInfo.setAmount((double) Math.floor(cashpercent*100+0.0001)/100);
								//preSetoffInfo1.setAmount((double) Math.floor(tokenpercent*100+0.0001)/100);
								LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(现金预存抵扣费)"+preSetoffInfo.getAmount());
								LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(虚拟货币预存抵扣费)"+preSetoffInfo1.getAmount());
							}
							else
							{
								preSetoffInfo.setAmount(cashDeposit);
								preSetoffInfo1.setAmount(tokenDeposit);
								accItemDto.setValue(BusinessUtility.doubleFormat(accItemDto.getValue() - (cashDeposit + tokenDeposit)));
								//accItemDto.setValue((double)Math.floor(accItemDto.getValue()*100 - (cashDeposit*100 + tokenDeposit*100)+0.01)/100);
								
								
								newInfoFeeList.add(i+1,accItemDto);
							}
							cashDeposit = BusinessUtility.doubleFormat(cashDeposit - preSetoffInfo.getAmount());
							tokenDeposit = BusinessUtility.doubleFormat(tokenDeposit - preSetoffInfo1.getAmount());
							//cashDeposit = (double)Math.floor(cashDeposit * 100 - preSetoffInfo.getAmount() * 100+0.01)/100;
							//tokenDeposit = (double)Math.floor(tokenDeposit * 100 - preSetoffInfo1.getAmount() * 100+0.01)/100;
							LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(现金预存款)"+cashDeposit);
							LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(虚拟货币预存款)"+tokenDeposit);
							//现金抵扣
							preSetoffInfo.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_CASH);
							//虚拟货币抵扣
							preSetoffInfo1.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY);
							prePaymentRecordList.add(preSetoffInfo);
							prePaymentRecordList.add(preSetoffInfo1);
						}
						else//如果现金或虚拟货币账户余额小于一分，计算信息费的补费总金额
							amountFee = BusinessUtility.doubleFormat(amountFee + accItemDto.getValue());
							//amountFee = (double)Math.floor(amountFee * 100 + accItemDto.getValue() * 100+0.01)/100;
						LogUtility.log(clazz,LogLevel.DEBUG,"费用："+accItemDto.getValue());
						LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(preSetoffInfo)",preSetoffInfo);
						LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(preSetoffInfo1)",preSetoffInfo1);
					}
				}
				LogUtility.log(clazz,LogLevel.DEBUG,"费用合计总额："+amountFee);
			}
			if(amountFee > 0)
			{
				immediateFeeList.setTotalValueAccountItem(BusinessUtility.doubleFormat(amountFee + this.getTotalOnceFee()));
				//immediateFeeList.setTotalValueAccountItem((double)Math.floor(amountFee*100 + this.getTotalOnceFee()*100 +0.01)/100);
				//预存款退费
				immediateFeeList.setTotalPreReturnFee(0);
				//补费
				if((amountFee + this.getTotalOnceFee()) > 0)
					immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
				//退费
				if((amountFee + this.getTotalOnceFee()) < 0)
					immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_FEE);
			}
			else if(cashDeposit > 0)//一次性费跟现金预存款抵扣
			{
				double totalOnceReturnFee = 0.0f;
				for(int i=0; i<onceFeeList.size(); i++)
				{
					AccountItemDTO accItemDto = (AccountItemDTO)onceFeeList.get(i);
					//退费不参与抵扣
					if(accItemDto.getValue() <= 0)
					{
						totalOnceReturnFee = BusinessUtility.doubleFormat(totalOnceReturnFee +accItemDto.getValue());
						//totalOnceReturnFee = (double)Math.floor(totalOnceReturnFee * 100 + accItemDto.getValue() * 100+0.01)/100;
						continue;
					}
					LogUtility.log(clazz,LogLevel.DEBUG,"onceFeeList(accItemDto)",accItemDto);
					//预存抵扣记录
					PrepaymentDeductionRecordDTO preSetoffInfo = new PrepaymentDeductionRecordDTO();
					preSetoffInfo.setAcctId(accItemDto.getAcctID());
					preSetoffInfo.setCreatingMethod(accItemDto.getCreatingMethod());
					preSetoffInfo.setCustId(accItemDto.getCustID());
					preSetoffInfo.setInvoicedFlag(accItemDto.getInvoiceFlag());
					preSetoffInfo.setInvoiceNo(accItemDto.getInvoiceNO());
					preSetoffInfo.setOpId(accItemDto.getOperatorID());
					preSetoffInfo.setOrgId(accItemDto.getOrgID());
					preSetoffInfo.setReferRecordId(accItemDto.getReferID());
					preSetoffInfo.setReferRecordType(accItemDto.getReferType());
					preSetoffInfo.setStatus(accItemDto.getStatus());
					if(cashDeposit > 0)
					{
						if(cashDeposit >= accItemDto.getValue())
							preSetoffInfo.setAmount(accItemDto.getValue());
						else
						{
							preSetoffInfo.setAmount(cashDeposit);
							accItemDto.setValue(BusinessUtility.doubleFormat(accItemDto.getValue() - cashDeposit));
							//accItemDto.setValue((double)Math.floor(accItemDto.getValue()*100 - cashDeposit*100+0.01)/100);
							onceFeeList.remove(i);
							onceFeeList.add(i,accItemDto);
							i--;
						}
						cashDeposit = BusinessUtility.doubleFormat(cashDeposit - preSetoffInfo.getAmount());
						//cashDeposit = (double)Math.floor(cashDeposit * 100 - preSetoffInfo.getAmount() * 100+0.01)/100;
						
						
						LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(现金预存款)"+cashDeposit);
						//现金抵扣
						preSetoffInfo.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_CASH);
						LogUtility.log(clazz,LogLevel.DEBUG,"onceFeeList(preSetoffInfo)",preSetoffInfo);
						prePaymentRecordList.add(preSetoffInfo);
					}
					else//如果账户余额为零，计算信息费的补费总金额
						amountFee = BusinessUtility.doubleFormat(amountFee +accItemDto.getValue());
						//amountFee = (double)Math.floor(amountFee * 100 + accItemDto.getValue() * 100+0.01)/100;
					
					
				}
				if(amountFee > 0)
				{
					immediateFeeList.setTotalValueAccountItem(BusinessUtility.doubleFormat(amountFee + totalOnceReturnFee));
					//immediateFeeList.setTotalValueAccountItem((double)Math.floor(amountFee*100 + totalOnceReturnFee*100+0.01)/100);
					//预存款退费
					immediateFeeList.setTotalPreReturnFee(0);
					//补费
					if((amountFee + totalOnceReturnFee) > 0)
						immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
					//退费
					if((amountFee + totalOnceReturnFee) < 0)
						immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_FEE);
				}
				else//退费
				{
					//if(cashDeposit == 0)
					if(Math.abs(cashDeposit)<0.0001)
					{
						immediateFeeList.setTotalValueAccountItem(0);
						//预存款退费
						immediateFeeList.setTotalPreReturnFee(0);
					}
					else
					{
						LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(ReturnType)"+this.getReturnType());
						//预存款退费
						if(RETURN_CASHACCOUNT.equals(this.getReturnType()))
						{
							immediateFeeList.setTotalValueAccountItem(BusinessUtility.doubleFormat(totalOnceReturnFee - cashDeposit));
							//immediateFeeList.setTotalValueAccountItem((double)Math.floor(totalOnceReturnFee*100 - cashDeposit*100+0.01)/100);
							immediateFeeList.setTotalPreReturnFee( -cashDeposit);
						}
					}
					immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_PREFEE);
				}
			}
			else//收取一次性费
			{
				immediateFeeList.setTotalValueAccountItem(this.getTotalOnceFee());
				//预存款退费
				immediateFeeList.setTotalPreReturnFee(0);
				//补费
				if(this.getTotalOnceFee() > 0)
					immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
				//退费
				if(this.getTotalOnceFee() < 0)
					immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_FEE);
			}
		}
		immediateFeeList.setAcctItemList(newFeeList);
		immediateFeeList.setPrePaymentRecordList(prePaymentRecordList);
		immediateFeeList.setAccountid(this.getAccountid());
		immediateFeeList.setAccountName(getAccountName(this.getAccountid()));
		return immediateFeeList;
	}

	/**
	 * 获得账户名称
	 * @param accountid int
	 * @return String
	 * @throws ServiceException
	 */
	private String getAccountName(int accountid)throws ServiceException
	{
		String accountName = null;
		LogUtility.log(clazz,LogLevel.DEBUG,"进入查询帐户信息：");
		if(String.valueOf(accountid) == null ||
			String.valueOf(accountid).trim().length() == 0){
			throw new ServiceException("参数错误：帐户序列号为空！");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"查询帐户信息，传入的帐户号为：" +accountid);
			AccountHome acctHome = HomeLocater.getAccountHome();
			Account acct = acctHome.findByPrimaryKey(new Integer(accountid));
			accountName = acct.getAccountName();
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"查询帐户信息出错，原因：帐户定位出错！");
			throw new ServiceException("查询帐户信息出错，原因：帐户定位出错！");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"查询帐户信息出错，原因：帐户查找出错！");
			throw new ServiceException("查询帐户信息出错，原因：帐户查找出错");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束查询帐户信息！");
		return accountName;
	}

	/**
	 * 新生成信息费和未销帐信息费的合并
	 * @return ArrayList
	 */
	private ArrayList getNewInfoFeeList()
	{
		ArrayList newInfoFeeList = new ArrayList();
		ArrayList infoFeeList = (ArrayList)this.getInfoFeeList();
		ArrayList noSetoffFeeList = (ArrayList)this.getNoSetoffFeeList();
		if(noSetoffFeeList == null)
			noSetoffFeeList = new ArrayList();
		for(int i=0; i<noSetoffFeeList.size(); i++)
		{
			AccountItemDTO accItemDto = (AccountItemDTO)noSetoffFeeList.get(i);
			newInfoFeeList.add(accItemDto);
		}
		if(infoFeeList == null)
			infoFeeList = new ArrayList();
		for(int i=0; i<infoFeeList.size(); i++)
		{
			AccountItemDTO accItemDto = (AccountItemDTO)infoFeeList.get(i);
			newInfoFeeList.add(accItemDto);
		}
		return newInfoFeeList;
	}
	/**
	 * 将FeeList赋给新的费用列表
	 * @return ArrayList
	 */
	private ArrayList getNewFeeList()
	{
		ArrayList newFeeList = new ArrayList();
		for(int i=0; i<this.getFeeList().size(); i++)
		{
			AccountItemDTO accItemDto = getAccItemDto((AccountItemDTO)this.getFeeList().get(i));
			newFeeList.add(accItemDto);
		}
		return newFeeList;
	}
	/**
	 * 获取预存抵扣方式
	 * @return String 预存抵扣方式：现金优先、按比例抵扣、虚拟货币优先
	 * @throws ServiceException
	 */
	private String getPreSetoffType()throws ServiceException
	{
		//获得数据库连接
		Connection dbConn = DBUtil.getConnection();
		if (dbConn == null)
			throw new RuntimeException("DB Connection error.");

		Statement sqlStrStmt = null;
		ResultSet rs = null;
		String preSetoffType = null; //预存抵扣方式
		//查询预存抵扣方式
		String sqlStr = "select PrePaymentDeductionMode from T_FINANCIALSETTING";
		LogUtility.log(clazz,LogLevel.DEBUG,sqlStr);
		try
		{
			sqlStrStmt = dbConn.createStatement();
			rs = sqlStrStmt.executeQuery(sqlStr);
			if(rs == null)
				preSetoffType = CommonConstDefinition.PREPAYMENTDEDUCTIONMODE_P;
//			rs.beforeFirst();
			while(rs.next())
			{
				preSetoffType = rs.getString("PrePaymentDeductionMode");
			}
		}
		catch(SQLException sqle)
		{
			LogUtility.log(clazz,LogLevel.WARN,sqle);
			sqle.printStackTrace();
			throw new ServiceException("预存抵扣方式查询出错！");
		}
		finally{
			try{
				if (rs !=null)
					rs.close();
				if(sqlStrStmt != null)
					sqlStrStmt.close();
				if(dbConn != null)
					dbConn.close();
			}
			catch(SQLException e){}
		}
		return preSetoffType;
	}

	/**
	 * 将未完成销帐的费用记录加入费用列表feeList中
	 * 计算出需付的未销帐费用总金额
	 * @param accItemList 费用列表
	 */
	private void getAccItemDto(ArrayList accItemList)
	{
		for(int i=0; i<accItemList.size(); i++)
		{
			AccountItem accItem = (AccountItem)accItemList.get(i);
			//将未完成销帐的费用记录加入费用列表feeList中
			if (!CommonConstDefinition.SETOFFFLAG_D.equals(accItem.getSetOffFlag()))
			{
				AccountItemDTO accItemDto1 = new AccountItemDTO();
				accItemDto1.setAiNO(accItem.getAiNO().intValue());
				accItemDto1.setAcctID(accItem.getAcctID());
				accItemDto1.setValue(accItem.getValue());
				accItemDto1.setCreatingMethod(accItem.getCreatingMethod());
				accItemDto1.setCreateTime(accItem.getCreateTime());
				accItemDto1.setCustID(accItem.getCustID());
				accItemDto1.setDtCreate(accItem.getDtLastmod());
				accItemDto1.setDtLastmod(accItem.getDtLastmod());
				accItemDto1.setInvoiceFlag(accItem.getInvoiceFlag());
				accItemDto1.setInvoiceNO(accItem.getInvoiceNO());
				accItemDto1.setForcedDepositFlag(accItem.getForcedDepositFlag());
				accItemDto1.setOperatorID(accItem.getOperatorID());
				accItemDto1.setOrgID(accItem.getOrgID());
				accItemDto1.setReferID(accItem.getReferID());
				accItemDto1.setReferType(accItem.getReferType());
				accItemDto1.setAcctItemTypeID(accItem.getAcctItemTypeID());
				accItemDto1.setDate1(accItem.getDate1());
				accItemDto1.setDate2(accItem.getDate2());
				accItemDto1.setProductID(accItem.getProductID());
				accItemDto1.setStatus(accItem.getStatus());
				accItemDto1.setPsID(accItem.getPsID());
				accItemDto1.setServiceAccountID(accItem.getServiceAccountID());
				accItemDto1.setSetOffAmount(accItem.getSetOffAmount());
				accItemDto1.setSetOffFlag(accItem.getSetOffFlag());
				accItemDto1.setBatchNO(accItem.getBatchNO());
				accItemDto1.setBillingCycleID(accItem.getBillingCycleID());
				accItemDto1.setSourceRecordID(accItem.getSourceRecordID());
				totalNoSetoffFee = BusinessUtility.doubleFormat(totalNoSetoffFee +accItemDto1.getValue());
				//totalNoSetoffFee =(double) Math.floor(totalNoSetoffFee*100 +accItemDto1.getValue()*100+0.01)/100;
				//noSetoffFeeList.add(accItemDto1);
				getNoSetoffFeeList().add(accItemDto1);
				feeList.add(accItemDto1);
				LogUtility.log(clazz, LogLevel.DEBUG, "getAccItemDto",accItemDto1);
			}
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
		//补漏
		accItemDto1.setFeeSplitPlanID(accItemDto.getFeeSplitPlanID());
		accItemDto1.setRfBillingCycleFlag(accItemDto.getRfBillingCycleFlag());
		accItemDto1.setCcID(accItemDto.getCcID());
		accItemDto1.setAdjustmentFlag(accItemDto.getAdjustmentFlag());
		accItemDto1.setAdjustmentNo(accItemDto.getAdjustmentNo());
		accItemDto1.setComments(accItemDto.getComments());
		accItemDto1.setFeeType(accItemDto1.getFeeType());
		accItemDto1.setBrID(accItemDto1.getBrID());
		LogUtility.log(clazz,LogLevel.DEBUG,"getAccItemDto",accItemDto1);
		return accItemDto1;
	}
/*	public static void main(String[] args)
	{
		java.util.ArrayList list = new ArrayList();
		String str = null;
//		list.add(str);
		list.add(str);
		int n = 90;
		int m = 100;
		int j = 0;
		try
		{
			for (int i = 0; i < list.size(); i++) {
				if (n > m)
					j = m;
				else {
					j = n;
					m = m - n;
//					list.ensureCapacity(i+2);
					list.remove(i);
					list.add(i, str);
				}
				n = n - j;
			}
			System.out.println("n: " + n);
			System.out.println("m: " + m);
			System.out.println("size: " + list.size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}*/
}
