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
 * <p>Title: �����˻��ƷѴ���ӿ�</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: DTV</p>
 * @author Wesley
 * @version 1.0
 */

//���ฺ�������˻������Ʒѻ��ܣ����վ�����֧�������˷�
public class ImmediateCountFeeService extends AbstractService
{
	private final static Class clazz = ImmediateCountFeeService.class;
	//�ͻ�������/�˻�
	public final static String CANCEL_CUSTOMER = "cancel_cust";
	//�˻�������
	public final static String CANCEL_ACCOUNT = "cancel_acct";
	//ҵ���˻���ȡ��
	public final static String CANCEL_SERVICEACCT = "cancel_serviceacct";
	//�ͻ���Ʒ��ȡ��
	public final static String CANCEL_CUSTPRODUCT = "cancel_custproduct";
	//��Ҫ���˻����
	public final static String RETURN_CASHACCOUNT = "Y";
	//����Ҫ���ʻ����
	public final static String NORETURN_CASHACCOUNT = "N";
	//�踶��һ���Է����ܽ��
	private double totalOnceFee;
	//�踶����Ϣ�����ܽ��
	private double totalInfoFee;
	//�踶��δ���ʷ����ܽ��
	private double totalNoSetoffFee;
	//�����б�(ArrayList of AccountItemDTO)
	private ArrayList feeList;
	//�ƷѴ����б�(ImmediateCountFeeInfo)
	private ImmediateCountFeeInfo immediateFeeList;
	//�踶��һ���Է�����ϸ, Collection of AccountItemDTO
	private ArrayList onceFeeList;
	//�踶��Ϣ������ϸ, Collection of AccountItemDTO
	private ArrayList infoFeeList;
	//�踶��δ���ʷ�����ϸ, Collection of AccountItemDTO
	private ArrayList noSetoffFeeList;
	//���������Ϣ�ѵ��Żݣ�����add by  �
	private double favourableTotalValue;
	//�˻���
	private int accountid;
	//�����ʶ
	private String cancelType;
	//�˻��˷ѱ�ʶ
	private String returnType;

	//���췽��
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
		//���,Ϊ�˲����ֿ�ָ��
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
	 * Command���ô˷���ʵ�������Ʒѻ���
	 * @param accountid int �˻���
	 * @param cancelType String �����˻�����
	 * @param onceFeeList Collection һ���Է����б�
	 * @param infoFeeList Collection ��Ϣ�����б�
	 * @return ImmediateCountFeeInfo �����ƷѴ�����Ϣ
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
		//�鲢һ���ԷѺ���Ϣ��
		countFeeList.uniteList();
		//��Բ�ͬ�������˻����ͣ�������Ӧ�Ŀͻ����˻����ͻ���Ʒ���
		int id = 0;
		/*
		 �Է����б��е���Ϣ����У��
		 ���У��ͨ�����򷵻���Ӧ�Ŀͻ����˻����ͻ���Ʒ���
		*/
		id = countFeeList.checkInfo();
		/*
		 ����У�鶼û����Ļ�����ѯ�����ñ���δ������ʵķ��ü���Command����ķ���,
		 �ڰ���Ԥ��ֿۼ�¼�������ܽ��ʻ��������µ�ImmediateCountFeeInfo�б�
		*/
	    //���ÿͻ���/�˻���/�ͻ���Ʒ�Ż��δ������ʵķ��ü�¼���������µļƷ��б�feeList
	    countFeeList.getCountFeeList(id);
	    //����ֽ�Ԥ���˻������������Ԥ���˻����
	    countFeeList.getDeposit();
	    //����µļƷ��б�
	    immediateFeeList = countFeeList.setoffFinance();
		return immediateFeeList;
	}

	/**
	 * Command���ô˷���ʵ�������Ʒѻ���
	 * @param accountid int �˻���
	 * @param cancelType String �����˻�����
	 * @param feeList Collection ���ʷ����б�
	 * @return ImmediateCountFeeInfo �����ƷѴ�����Ϣ
	 * @throws ServiceException
	 */
	public static ImmediateCountFeeInfo countFee(int accountid ,String cancelType
									,String returnType,Collection feeList)throws ServiceException
	{
		ImmediateCountFeeService countFeeList = new ImmediateCountFeeService(accountid,cancelType
			                                                       ,returnType,null,null,feeList);
		ImmediateCountFeeInfo immediateFeeList = null;
		//�����ʷ����б��ֳ�һ���Է����б���Ϣ�����б�
		countFeeList.splitList();
		/*
		 �Է����б��е���Ϣ����У��
		 ���У��ͨ�����򷵻���Ӧ�Ŀͻ����˻����ͻ���Ʒ���
		*/
		countFeeList.checkInfo();
		//����ֽ�Ԥ���˻������������Ԥ���˻����
		countFeeList.getDeposit();
		//����µļƷ��б�
		immediateFeeList = countFeeList.setoffFinance();
		return immediateFeeList;
	}

	/**
	 * Command���ô˷���ʵ�������Ʒѻ���
	 * @return ImmediateCountFeeInfo the Collection of ImmediateCountFeeInfo  ���á�Ԥ��ֿۡ��ܽ��˻����
	 * @throws ServiceException
	 */
	public ImmediateCountFeeInfo countFee()throws ServiceException
	{
		//�鲢һ���ԷѺ���Ϣ��
		uniteList();
		//��Բ�ͬ�������˻����ͣ�������Ӧ�Ŀͻ����˻����ͻ���Ʒ���
		int id = 0;
		/*
		 �Է����б��е���Ϣ����У��
		 ���У��ͨ�����򷵻���Ӧ�Ŀͻ����˻����ͻ���Ʒ���
		*/
		id = checkInfo();
		/*
		 ����У�鶼û����Ļ�����ѯ�����ñ���δ������ʵķ��ü���Command����ķ���,
		 �ڰ���Ԥ��ֿۼ�¼�������ܽ��ʻ��������µ�ImmediateCountFeeInfo�б�
		*/
		//���ÿͻ���/�˻���/�ͻ���Ʒ�Ż��δ������ʵķ��ü�¼���������µļƷ��б�feeList
		getCountFeeList(id);
		//����ֽ�Ԥ���˻������������Ԥ���˻����
		getDeposit();
		//����µļƷ��б�
		setoffFinance();
		return immediateFeeList;
	}

	/**
	 * �鲢һ���ԷѺ���Ϣ��
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
			//����ͳ��һ���Żݵķ����ܶ����add by  �  2006/11/23--start
			if(accItemDto.getValue()<0){
				favourableTotalValue= BusinessUtility.doubleFormat(favourableTotalValue + accItemDto.getValue());
				//favourableTotalValue=(double) Math.floor(favourableTotalValue*100 + accItemDto.getValue()*100+0.01)/100;
			}
			//����ͳ��һ���Żݵķ����ܶ����add by  �  2006/11/23--end
		}
		this.setTotalOnceFee(totalOnceFee);
		this.setTotalInfoFee(totalInfoFee);
	}

	/**
	 * �����ʷ����б��ֳ�һ���Է����б���Ϣ�����б�
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
			//ͨ��������Ŀ���͵õ���������������һ���ԷѺ���Ϣ��
			String feeType = getFeeType(accItemDto.getAcctItemTypeID());
			//��Ϣ��
			if("M".equals(feeType))
			{
				totalInfoFee = BusinessUtility.doubleFormat(totalInfoFee +accItemDto.getValue());
				//totalInfoFee =(double) Math.floor(totalInfoFee*100 +accItemDto.getValue()*100+0.01)/100;
				infoFeeList.add(accItemDto);
			}
			else//һ���Է�
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
	 * ͨ��������Ŀ���͵õ���������������һ���ԷѺ���Ϣ��
	 * @param accountItemTypeID String
	 * @return String
	 * @throws ServiceException
	 */
	private String getFeeType(String accountItemTypeID)throws ServiceException
	{
		String feeType = null;
		LogUtility.log(clazz,LogLevel.DEBUG,"�����ѯ��Ŀ������Ϣ��");
		if(accountItemTypeID == null || accountItemTypeID.trim().length() == 0)
			throw new ServiceException("����������Ŀ�������к�Ϊ�գ�");
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"��ѯ��Ŀ������Ϣ���������Ŀ���ͺ�Ϊ��" +accountItemTypeID);
			AcctItemTypeHome acctTypeHome = HomeLocater.getAcctItemTypeHome();
			AcctItemType acctType = acctTypeHome.findByPrimaryKey(accountItemTypeID);
			if(CommonConstDefinition.AISTATUS_INVALIDATE.equals(acctType.getFeeType()))
				throw new ServiceException("����Ŀ��������Ч��Ŀ���ͣ�");
			feeType = acctType.getFeeType();
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"��ѯ��Ŀ������Ϣ����ԭ����Ŀ���Ͷ�λ����");
			throw new ServiceException("��ѯ��Ŀ������Ϣ����ԭ����Ŀ���Ͷ�λ����");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"��ѯ��Ŀ������Ϣ����ԭ����Ŀ���Ͳ��ҳ���");
			throw new ServiceException("��ѯ��Ŀ������Ϣ����ԭ����Ŀ���Ͳ��ҳ���");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"������ѯ��Ŀ������Ϣ��");
		return feeType;
	}

	/**
	 * �Է����б��е���Ϣ����У��
	 * @return int  �ͻ���/�˻���/�ͻ���Ʒ��
	 * @throws ServiceException
	 */
	private int checkInfo()throws ServiceException
	{
		//У������б��Ƿ�Ϊ��
		if(feeList == null)
			throw new ServiceException("�����б���Ϊ�գ�");
//		if(cancelType == null || cancelType.trim().length() == 0)
//			throw new ServiceException("����ָ������/�˻����ͣ�");

		int preCustomerID = 0;  //ǰһ����ϸ�Ŀͻ���
		int preAccountID = 0;   //ǰһ����ϸ���ʻ���
		int prePSID = 0;       //ǰһ����ϸ�Ŀͻ���Ʒ��
		int preSerAccountid = 0; //ǰһ����ϸ��ҵ���˻���
		//��Բ�ͬ�������˻����ͣ�������Ӧ�Ŀͻ����˻����ͻ���Ʒ���
		int id = 0;
		//У������б�
		for(int i=0; i<feeList.size(); i++)
		{
			AccountItemDTO accItemDto = (AccountItemDTO)feeList.get(i);
			if(String.valueOf(accItemDto.getCustID()) == null ||
				accItemDto.getCustID() <= 0)
				throw new ServiceException("���ü�¼�пͻ��Ų���Ϊ�գ�");
			if(String.valueOf(accItemDto.getAcctID()) == null ||
				accItemDto.getAcctID() <= 0)
				throw new ServiceException("���ü�¼���ʻ��Ų���Ϊ�գ�");
			//����ǿͻ���Ʒ��ȡ��������ü�¼�пͻ���Ʒ�Ų���Ϊ��
/*			if(CANCEL_CUSTPRODUCT.equals(cancelType) && (accItemDto.getPsID() <= 0 ||
				String.valueOf(accItemDto.getPsID()) == null))
				throw new ServiceException("���ü�¼�пͻ���Ʒ�Ų���Ϊ�գ�");
			//�����ҵ���˻���ȡ��������ü�¼��ҵ���˻��Ų���Ϊ��
			if(CANCEL_SERVICEACCT.equals(cancelType) && (accItemDto.getServiceAccountID() <= 0 ||
				String.valueOf(accItemDto.getServiceAccountID()) == null))
				throw new ServiceException("���ü�¼��ҵ���˻��Ų���Ϊ�գ�");
*/
			if(i > 0)
			{
				if (preCustomerID != accItemDto.getCustID())
					throw new ServiceException("�����б��еļ�¼����ͬһ���ͻ��ģ�");
				if (preAccountID != accItemDto.getAcctID())
					throw new ServiceException("�����б��еļ�¼����ͬһ���ʻ��ģ�");
				//����ǿͻ���Ʒ��ȡ����������б���������ͬһ�ͻ���Ʒ
/*				if(CANCEL_CUSTPRODUCT.equals(cancelType) &&
				   prePSID != accItemDto.getPsID())
					throw new ServiceException("�����б��еļ�¼����ͬһ���ͻ���Ʒ�ģ�");
				//�����ҵ���˻���ȡ����������б���������ͬһҵ���˻�
				if(CANCEL_SERVICEACCT.equals(cancelType) &&
				   preSerAccountid != accItemDto.getServiceAccountID())
					throw new ServiceException("�����б��еļ�¼����ͬһ��ҵ���˻��ģ�");*/
			}
			preCustomerID = accItemDto.getCustID();
			preAccountID = accItemDto.getAcctID();
			prePSID = accItemDto.getPsID();
			preSerAccountid = accItemDto.getServiceAccountID();
		}
		//�ͻ������˻�
		if(CANCEL_CUSTOMER.equals(cancelType))
			id = preCustomerID;
		//�˻������˻�
		if(CANCEL_ACCOUNT.equals(cancelType))
			id = preAccountID;
		//ҵ���˻�ȡ��
		if(CANCEL_SERVICEACCT.equals(cancelType))
			id = preSerAccountid;
		//�ͻ���Ʒȡ��
		if(CANCEL_CUSTPRODUCT.equals(cancelType))
			id = prePSID;
		return id;
	}

	/**
	 * ��������˻�ʱ��δ������ʵķ����б��������µļƷ��б�
	 * @param id int  �ͻ���/�˻���/�ͻ���Ʒ��
	 * @throws ServiceException
	 */
	private void getCountFeeList(int id)throws ServiceException
	{
		//�Ʒ��б�
		ArrayList countFeeList = null;
		//�踶��δ���ʷ�����ϸ, Collection of AccountItemDTO
//		ArrayList noSetoffFeeList = new ArrayList();
		//�踶��δ���ʷ����ܽ��
//		double totalNoSetoffFee = 0.0f;
		LogUtility.log(clazz,LogLevel.DEBUG,"�����ѯ���ü�¼��Ϣ��");
		try
		{
			CustomerProductHome custProductHome = HomeLocater.getCustomerProductHome();
			//ͨ���˻��Ų�ѯ�ͻ���Ʒ����Ϣ
			ArrayList custProductInfo = (ArrayList)custProductHome.findByAccountID(this.getAccountid());
			AccountItemHome accItemHome = HomeLocater.getAccountItemHome();
			//�ͻ������˻�
			if(CANCEL_CUSTOMER.equals(cancelType))
			{
				LogUtility.log(clazz,LogLevel.DEBUG,"��ѯ���ü�¼��Ϣ������Ŀͻ���Ϊ��" +id);
				countFeeList = (ArrayList) accItemHome.findByCustomerID(id);
				getAccItemDto(countFeeList);
			}
			//�˻�����
			if(CANCEL_ACCOUNT.equals(cancelType))
			{
				LogUtility.log(clazz,LogLevel.DEBUG,"��ѯ���ü�¼��Ϣ��������˻���Ϊ��" +id);
				countFeeList = (ArrayList) accItemHome.findByAccountID(id);
				getAccItemDto(countFeeList);
			}
			//ҵ���˻�ȡ��
			if(CANCEL_SERVICEACCT.equals(cancelType))
			{
				for(int i=0; i<custProductInfo.size(); i++)
				{
					int serviceAccountid = ((CustomerProduct)custProductInfo.get(i)).getServiceAccountID();
					LogUtility.log(clazz, LogLevel.DEBUG,"��ѯ���ü�¼��Ϣ�������ҵ���˻���Ϊ��" + id);
					countFeeList = (ArrayList) accItemHome.findByServiceAccountID(serviceAccountid);
					getAccItemDto(countFeeList);
				}
			}
			//�ͻ���Ʒȡ��
			if(CANCEL_CUSTPRODUCT.equals(cancelType))
			{
				for(int i=0; i<custProductInfo.size(); i++)
				{
					int psid = ((CustomerProduct) custProductInfo.get(i)).getPsID().intValue();
					LogUtility.log(clazz, LogLevel.DEBUG,"��ѯ���ü�¼��Ϣ������Ŀͻ���Ʒ��Ϊ��" + id);
					countFeeList = (ArrayList) accItemHome.findByPSID(psid);
					getAccItemDto(countFeeList);
				}
			}
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"��ѯ���ü�¼��Ϣ����ԭ�򣺷��ü�¼��λ����");
			throw new ServiceException("��ѯ���ü�¼��Ϣ����ԭ�򣺷��ü�¼��λ����");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"��ѯ���ü�¼��Ϣ����ԭ�򣺷��ü�¼���ҳ���");
			throw new ServiceException("��ѯ���ü�¼��Ϣ����ԭ�򣺷��ü�¼���ҳ���");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"������ѯ���ü�¼��Ϣ��");
	}

	/**
	 * �Է��ü�¼�����Ԥ����˻�����໥�ֿۣ��õ��µķ����б�
	 * @throws ServiceException
	 */
	private void getDeposit()throws ServiceException
	{
		int accountID = this.getAccountid();
		LogUtility.log(clazz,LogLevel.DEBUG,"�����ѯ�ʻ���Ϣ��");
		if(String.valueOf(accountID) == null || accountID == 0){
			throw new ServiceException("���������ʻ����к�Ϊ�գ�");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"��ѯ�ʻ���Ϣ��������ʻ���Ϊ��" +accountID);
			AccountHome acctHome = HomeLocater.getAccountHome();
			Account acct = acctHome.findByPrimaryKey(new Integer(accountID));
			if(CommonConstDefinition.AISTATUS_INVALIDATE.equals(acct.getStatus()))
				throw new ServiceException("���˻�����Ч�˻���");
			immediateFeeList.setPreCashDoposit(acct.getCashDeposit());
			immediateFeeList.setPreTokenDoposit(acct.getTokenDeposit());
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"��ѯ�ʻ���Ϣ����ԭ���ʻ���λ����");
			throw new ServiceException("��ѯ�ʻ���Ϣ����ԭ���ʻ���λ����");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"��ѯ�ʻ���Ϣ����ԭ���ʻ����ҳ���");
			throw new ServiceException("��ѯ�ʻ���Ϣ����ԭ���ʻ����ҳ���");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"������ѯ�ʻ���Ϣ��");
	}

	/**
	 * �Է��ü�¼�����Ԥ����˻�����໥�ֿۣ�����Ԥ��ֿۼ�¼
	 * ͬʱ�ж���Ҫ�˷ѻ���Ҫ֧�����߸պó�ƽ�������µļƷ��б�
	 * @return ImmediateCountFeeInfo �ƷѴ����б�
	 * @throws ServiceException
	 */
	private ImmediateCountFeeInfo setoffFinance()throws ServiceException
	{
		//��������Ϣ�Ѻ�δ������Ϣ�ѵĺϲ�
		ArrayList newInfoFeeList = getNewInfoFeeList();
		LogUtility.log(clazz,LogLevel.DEBUG,"newInfoFeeList.size() "+newInfoFeeList.size());
		//��FeeList�����µķ����б�
		ArrayList newFeeList = getNewFeeList();
		//���Ԥ��ֿ۷�ʽ
		String preSetoffType = getPreSetoffType();
		LogUtility.log(clazz,LogLevel.DEBUG,"Ԥ��ֿ۷�ʽ: "+preSetoffType);
		//�ֽ�Ԥ�����
		double cashDeposit = immediateFeeList.getPreCashDoposit();
		LogUtility.log(clazz,LogLevel.DEBUG,"�ֽ�Ԥ�����: "+cashDeposit);
		//�������Ԥ�����
		double tokenDeposit = immediateFeeList.getPreTokenDoposit();
		LogUtility.log(clazz,LogLevel.DEBUG,"�������Ԥ�����: "+tokenDeposit);
		//��Ҫ�˷ѻ򲹷ѵķ����ܽ��
		double amountFee = 0.0f;
		//Ԥ��ֿ���ϸ, Collection of PrePaymentDeductionRecordDTO
		ArrayList prePaymentRecordList = new ArrayList();
		//���Ԥ���˻���û�����򴫻ط����б�ͷ����ܽ��
		//if(cashDeposit == 0 && tokenDeposit == 0)
		if(Math.abs(cashDeposit)<0.0001 && Math.abs(tokenDeposit)<0.0001)
		{
			amountFee = BusinessUtility.doubleFormat(this.getTotalInfoFee() + this.getTotalNoSetoffFee() + this.getTotalOnceFee());
			//amountFee = (double)(Math.floor(this.getTotalInfoFee()*100 + this.getTotalNoSetoffFee()*100 + this.getTotalOnceFee()*100+0.01)/100);
			LogUtility.log(clazz,LogLevel.DEBUG,"�����ܶ"+amountFee);
			immediateFeeList.setTotalValueAccountItem(amountFee);
			//Ԥ����˷�
			immediateFeeList.setTotalPreReturnFee(0);
			//����
			if(amountFee > 0)
				immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
			//�˷�
			if(amountFee < 0)
				immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_FEE);
		}
		else//����Ԥ��ֿۼ�¼
		{
			//��Ϣ�Ѹ�Ԥ���ֿ�
			for(int i=0; i<newInfoFeeList.size(); i++)
			{
				
				AccountItemDTO accItemDto = (AccountItemDTO)newInfoFeeList.get(i);
				
				LogUtility.log(clazz,LogLevel.DEBUG,"��Ϣ�Ѽ�¼: ",accItemDto);
				//Ԥ��ֿۼ�¼
				PrepaymentDeductionRecordDTO preSetoffInfo = new PrepaymentDeductionRecordDTO();
				//�Է����б��еĲ�Ʒ�ѡ���Ϣ������Ԥ���˻����ֿ�
				if(accItemDto.getValue() > 0)
				{
					LogUtility.log(clazz,LogLevel.DEBUG,"��ʼ�Żݣ�"+favourableTotalValue);
					//���������Ϣ�ѵ��Ż� Add by  �   2006/11/23----start----
					if((favourableTotalValue+accItemDto.getValue())<0){
						favourableTotalValue=BusinessUtility.doubleFormat(favourableTotalValue+accItemDto.getValue());
						//favourableTotalValue=(double)Math.floor(favourableTotalValue*100+accItemDto.getValue()*100+0.01)/100;
						LogUtility.log(clazz,LogLevel.DEBUG,"�����ʣ���Żݣ�"+favourableTotalValue);
						continue;
					}else if(favourableTotalValue<0.0f){
						accItemDto.setValue(BusinessUtility.doubleFormat(favourableTotalValue+accItemDto.getValue()));
						//accItemDto.setValue((double)Math.floor(favourableTotalValue*100+accItemDto.getValue()*100+0.01)/100);
						favourableTotalValue=0.0f;
					}
					
					LogUtility.log(clazz,LogLevel.DEBUG,"�����ʣ���Żݣ�"+favourableTotalValue);
					//���������Ϣ�ѵ��Ż� Add by  �   2006/11/23----end----
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
					//�ֽ�����
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
							//�ֽ�ֿ�
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
							//������ҵֿ�
							preSetoffInfo.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY);
							prePaymentRecordList.add(preSetoffInfo);
						}
						else//����˻����Ϊ�㣬������Ϣ�ѵĲ����ܽ��
							amountFee = BusinessUtility.doubleFormat(amountFee + accItemDto.getValue());
							//amountFee=(double)Math.floor(amountFee * 100 + accItemDto.getValue() * 100+0.01)/100;
						
						LogUtility.log(clazz,LogLevel.DEBUG,"���ã�"+accItemDto.getValue());
						
					}
					//�����������
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
							//������ҵֿ�
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
							//�ֽ�ֿ�
							preSetoffInfo.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_CASH);
							prePaymentRecordList.add(preSetoffInfo);
						}
						else//����˻����Ϊ�㣬������Ϣ�ѵĲ����ܽ��
							amountFee = BusinessUtility.doubleFormat(amountFee + accItemDto.getValue());
							//amountFee = (double)Math.floor(amountFee * 100 + accItemDto.getValue() * 100+0.01)/100;
						LogUtility.log(clazz,LogLevel.DEBUG,"���ã�"+accItemDto.getValue());
					}
					//�������ֿ�
					if(CommonConstDefinition.PREPAYMENTDEDUCTIONMODE_P.equals(preSetoffType))
					{
						PrepaymentDeductionRecordDTO preSetoffInfo1 = PayFeeUtil.getPrePaymentDto(preSetoffInfo);
						//�ֽ���������Ԥ���С��һ��Ǯ�Ļ���ֹͣ�������ֿ�
						if(cashDeposit > 0.01 || tokenDeposit > 0.01)
						{
							if ((cashDeposit + tokenDeposit) >= accItemDto.getValue())
							{
								LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(��Ϣ��)"+accItemDto.getValue());
								//���ձ�������Ԥ��ֿ۵Ľ�� Add by  �   2006/11/23----start----
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
								//���ձ�������Ԥ��ֿ۵Ľ�� Add by  �   2006/11/23----end----
								//preSetoffInfo.setAmount(cashpercent);
								//preSetoffInfo1.setAmount(tokenpercent);
								preSetoffInfo.setAmount(BusinessUtility.doubleFormat(cashpercent));
								preSetoffInfo1.setAmount(BusinessUtility.doubleFormat(tokenpercent));
								//preSetoffInfo.setAmount((double) Math.floor(cashpercent*100+0.0001)/100);
								//preSetoffInfo1.setAmount((double) Math.floor(tokenpercent*100+0.0001)/100);
								LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(�ֽ�Ԥ��ֿ۷�)"+preSetoffInfo.getAmount());
								LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(�������Ԥ��ֿ۷�)"+preSetoffInfo1.getAmount());
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
							LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(�ֽ�Ԥ���)"+cashDeposit);
							LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(�������Ԥ���)"+tokenDeposit);
							//�ֽ�ֿ�
							preSetoffInfo.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_CASH);
							//������ҵֿ�
							preSetoffInfo1.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_TRANSLUNARY);
							prePaymentRecordList.add(preSetoffInfo);
							prePaymentRecordList.add(preSetoffInfo1);
						}
						else//����ֽ����������˻����С��һ�֣�������Ϣ�ѵĲ����ܽ��
							amountFee = BusinessUtility.doubleFormat(amountFee + accItemDto.getValue());
							//amountFee = (double)Math.floor(amountFee * 100 + accItemDto.getValue() * 100+0.01)/100;
						LogUtility.log(clazz,LogLevel.DEBUG,"���ã�"+accItemDto.getValue());
						LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(preSetoffInfo)",preSetoffInfo);
						LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(preSetoffInfo1)",preSetoffInfo1);
					}
				}
				LogUtility.log(clazz,LogLevel.DEBUG,"���úϼ��ܶ"+amountFee);
			}
			if(amountFee > 0)
			{
				immediateFeeList.setTotalValueAccountItem(BusinessUtility.doubleFormat(amountFee + this.getTotalOnceFee()));
				//immediateFeeList.setTotalValueAccountItem((double)Math.floor(amountFee*100 + this.getTotalOnceFee()*100 +0.01)/100);
				//Ԥ����˷�
				immediateFeeList.setTotalPreReturnFee(0);
				//����
				if((amountFee + this.getTotalOnceFee()) > 0)
					immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
				//�˷�
				if((amountFee + this.getTotalOnceFee()) < 0)
					immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_FEE);
			}
			else if(cashDeposit > 0)//һ���ԷѸ��ֽ�Ԥ���ֿ�
			{
				double totalOnceReturnFee = 0.0f;
				for(int i=0; i<onceFeeList.size(); i++)
				{
					AccountItemDTO accItemDto = (AccountItemDTO)onceFeeList.get(i);
					//�˷Ѳ�����ֿ�
					if(accItemDto.getValue() <= 0)
					{
						totalOnceReturnFee = BusinessUtility.doubleFormat(totalOnceReturnFee +accItemDto.getValue());
						//totalOnceReturnFee = (double)Math.floor(totalOnceReturnFee * 100 + accItemDto.getValue() * 100+0.01)/100;
						continue;
					}
					LogUtility.log(clazz,LogLevel.DEBUG,"onceFeeList(accItemDto)",accItemDto);
					//Ԥ��ֿۼ�¼
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
						
						
						LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(�ֽ�Ԥ���)"+cashDeposit);
						//�ֽ�ֿ�
						preSetoffInfo.setPrepaymentType(CommonConstDefinition.PREPAYMENTTYPE_CASH);
						LogUtility.log(clazz,LogLevel.DEBUG,"onceFeeList(preSetoffInfo)",preSetoffInfo);
						prePaymentRecordList.add(preSetoffInfo);
					}
					else//����˻����Ϊ�㣬������Ϣ�ѵĲ����ܽ��
						amountFee = BusinessUtility.doubleFormat(amountFee +accItemDto.getValue());
						//amountFee = (double)Math.floor(amountFee * 100 + accItemDto.getValue() * 100+0.01)/100;
					
					
				}
				if(amountFee > 0)
				{
					immediateFeeList.setTotalValueAccountItem(BusinessUtility.doubleFormat(amountFee + totalOnceReturnFee));
					//immediateFeeList.setTotalValueAccountItem((double)Math.floor(amountFee*100 + totalOnceReturnFee*100+0.01)/100);
					//Ԥ����˷�
					immediateFeeList.setTotalPreReturnFee(0);
					//����
					if((amountFee + totalOnceReturnFee) > 0)
						immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
					//�˷�
					if((amountFee + totalOnceReturnFee) < 0)
						immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_FEE);
				}
				else//�˷�
				{
					//if(cashDeposit == 0)
					if(Math.abs(cashDeposit)<0.0001)
					{
						immediateFeeList.setTotalValueAccountItem(0);
						//Ԥ����˷�
						immediateFeeList.setTotalPreReturnFee(0);
					}
					else
					{
						LogUtility.log(clazz,LogLevel.DEBUG,"setoffFinance(ReturnType)"+this.getReturnType());
						//Ԥ����˷�
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
			else//��ȡһ���Է�
			{
				immediateFeeList.setTotalValueAccountItem(this.getTotalOnceFee());
				//Ԥ����˷�
				immediateFeeList.setTotalPreReturnFee(0);
				//����
				if(this.getTotalOnceFee() > 0)
					immediateFeeList.setPayType(CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE);
				//�˷�
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
	 * ����˻�����
	 * @param accountid int
	 * @return String
	 * @throws ServiceException
	 */
	private String getAccountName(int accountid)throws ServiceException
	{
		String accountName = null;
		LogUtility.log(clazz,LogLevel.DEBUG,"�����ѯ�ʻ���Ϣ��");
		if(String.valueOf(accountid) == null ||
			String.valueOf(accountid).trim().length() == 0){
			throw new ServiceException("���������ʻ����к�Ϊ�գ�");
		}
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"��ѯ�ʻ���Ϣ��������ʻ���Ϊ��" +accountid);
			AccountHome acctHome = HomeLocater.getAccountHome();
			Account acct = acctHome.findByPrimaryKey(new Integer(accountid));
			accountName = acct.getAccountName();
		}
		catch(HomeFactoryException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"��ѯ�ʻ���Ϣ����ԭ���ʻ���λ����");
			throw new ServiceException("��ѯ�ʻ���Ϣ����ԭ���ʻ���λ����");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"��ѯ�ʻ���Ϣ����ԭ���ʻ����ҳ���");
			throw new ServiceException("��ѯ�ʻ���Ϣ����ԭ���ʻ����ҳ���");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"������ѯ�ʻ���Ϣ��");
		return accountName;
	}

	/**
	 * ��������Ϣ�Ѻ�δ������Ϣ�ѵĺϲ�
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
	 * ��FeeList�����µķ����б�
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
	 * ��ȡԤ��ֿ۷�ʽ
	 * @return String Ԥ��ֿ۷�ʽ���ֽ����ȡ��������ֿۡ������������
	 * @throws ServiceException
	 */
	private String getPreSetoffType()throws ServiceException
	{
		//������ݿ�����
		Connection dbConn = DBUtil.getConnection();
		if (dbConn == null)
			throw new RuntimeException("DB Connection error.");

		Statement sqlStrStmt = null;
		ResultSet rs = null;
		String preSetoffType = null; //Ԥ��ֿ۷�ʽ
		//��ѯԤ��ֿ۷�ʽ
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
			throw new ServiceException("Ԥ��ֿ۷�ʽ��ѯ����");
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
	 * ��δ������ʵķ��ü�¼��������б�feeList��
	 * ������踶��δ���ʷ����ܽ��
	 * @param accItemList �����б�
	 */
	private void getAccItemDto(ArrayList accItemList)
	{
		for(int i=0; i<accItemList.size(); i++)
		{
			AccountItem accItem = (AccountItem)accItemList.get(i);
			//��δ������ʵķ��ü�¼��������б�feeList��
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
		//��©
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
