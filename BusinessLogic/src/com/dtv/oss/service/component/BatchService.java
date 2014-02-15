package com.dtv.oss.service.component;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.Query;
import com.dtv.oss.domain.QueryHome;
import com.dtv.oss.domain.QueryResultItem;
import com.dtv.oss.domain.QueryResultItemHome;
import com.dtv.oss.service.Service;
import com.dtv.oss.dto.QueryDTO;
import com.dtv.oss.dto.QueryResultItemDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.util.TimestampUtility;

public class BatchService extends AbstractService {

	private ServiceContext context;
	private static final Class clazz=BatchService.class;
	
	public BatchService(ServiceContext inContext){
		this.context=inContext;
	}
	
	/**
	 * �����������ѯ
	 * @param dto
	 * @throws ServiceException 
	 */
	public void createBatchQuery(QueryDTO dto) throws ServiceException{
		
		if(dto==null)
			throw new ServiceException("����Ϊ�գ����ܴ����������ѯ��");
		
		try{
			//1. ����������ѯ��¼
			QueryHome qHome=HomeLocater.getQueryHome();
			Query q=qHome.create(dto);
			
			//����Status��CreateOperatorID��CreateTime
			q.setStatus(CommonConstDefinition.QUERY_STATUS_NEW);
			q.setCreateOperatorId(PublicService.getCurrentOperatorID(this.context));
			q.setCreateTime(TimestampUtility.getCurrentTimestamp());
			
			dto.setQueryId(q.getQueryId().intValue());
			
			//�ѸղŴ�����ID����context��
			context.put(Service.PROCESS_RESULT,q.getQueryId());
			String desc = "����������ѯ";
			if("Y".equals(dto.getTemplateFlag()))
				desc += "ģ��";
			//2. ������ص���־��¼
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_LOGISTICS, desc, desc+":��ѯ����="+ q.getQueryId().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "����������ѯʱ��λ����");                    
			throw new ServiceException("����������ѯʱ��λ����");
		}
		catch(CreateException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "������ѯ��������");                    
			throw new ServiceException("������ѯ��������");
		} 
		catch (ServiceException e) {
			LogUtility.log(clazz,LogLevel.ERROR, "����������ѯʱ������־����");                    
			throw new ServiceException("����������ѯʱ������־����");
		}
		
	}
	
	/**
	 * �޸��������ѯ
	 * @param dto
	 * @throws ServiceException 
	 */
	public void modifyBatchQuery(QueryDTO dto) throws ServiceException{
		if(dto==null || dto.getQueryId()==0)
			throw new ServiceException("���������������������޸��������ѯ��");
		
		try{
			//1. �޸�������ѯ��¼
			QueryHome qHome=HomeLocater.getQueryHome();
			Query q=qHome.findByPrimaryKey(new Integer(dto.getQueryId()));
			
			//ֻ�д��ڡ����á�״̬�Ĳ�ѯ�����Ų������޸�
			if(CommonConstDefinition.QUERY_STATUS_REFERED.equals(q.getStatus()))
				throw new ServiceException("��������ѯ���ڱ�����״̬�������޸ģ�");
	
			dto.setStatus(CommonConstDefinition.QUERY_STATUS_NEW);
			dto.setPerformTime(null);
			//2. ɾ����ѯ�Ľ����
			try{
				BusinessUtility.deleteQueryResultItemIDsByQueryID(dto.getQueryId());
			}
			catch(SQLException ee){
				LogUtility.log(clazz,LogLevel.ERROR, "ɾ��������ѯ�����¼����",ee);                    
				throw new ServiceException("ɾ��������ѯ�����¼����");
			}
			
			try {
				if(q.ejbUpdate(dto)==-1){
					throw new ServiceException("�޸�������ѯ����");
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//����CreateOperatorID
			q.setCreateOperatorId(PublicService.getCurrentOperatorID(this.context));
			
			//3. ������ص���־��¼
			try{
				String desc = "�޸�������ѯ";
				if("Y".equals(dto.getTemplateFlag()))
					desc += "ģ��";
				SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
										PublicService.getCurrentOperatorID(context), 0, 
										SystemLogRecorder.LOGMODULE_LOGISTICS, desc, desc+":��ѯ����="+ q.getQueryId().intValue(), 
										SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			}
			catch (ServiceException e) {
				LogUtility.log(clazz,LogLevel.ERROR, "�޸�������ѯʱ������־����");                    
				throw new ServiceException("�޸�������ѯʱ������־����");
			}
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "�޸�������ѯʱ��λ����");                    
			throw new ServiceException("�޸�������ѯʱ��λ����");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "�޸�������ѯʱ���ҳ���");                    
			throw new ServiceException("�޸�������ѯʱ���ҳ���");
		} 
	}
	
	/**
	 * ȡ��������
	 * @param queryIDList
	 * @throws ServiceException 
	 */
	public void cancelBatchQuery(Collection queryIDList) throws ServiceException{
		
		if(queryIDList==null || queryIDList.size()==0)
			throw new ServiceException("��������������������ɾ���������ѯ��");
		
		String strQueryIDs="";
		try{
			QueryHome qHome=null;
			Query q=null;
			
			Iterator itQueryID=queryIDList.iterator();
			while(itQueryID.hasNext()){
				Integer queryID=(Integer)itQueryID.next();
				
				//1. ɾ��������ѯ���õĽ������¼
				qHome=HomeLocater.getQueryHome();
				q=qHome.findByPrimaryKey(queryID);
				//�޸�q��״̬
				q.setStatus(CommonConstDefinition.QUERY_STATUS_CANCEL);
				//q.remove();
				
				//2. ɾ��������ѯ���õĽ������¼
				try{
					BusinessUtility.deleteQueryResultItemIDsByQueryID(queryID.intValue());
				}
				catch(SQLException ee){
					LogUtility.log(clazz,LogLevel.ERROR, "ɾ��������ѯ�����¼����",ee);                    
					throw new ServiceException("ɾ��������ѯ�����¼����");
				}
				
				if(!"".equals(strQueryIDs))strQueryIDs=strQueryIDs +",";
				strQueryIDs=strQueryIDs + queryID.intValue();
			}

			//3. ������ص���־��¼
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
					PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_LOGISTICS, "ɾ��������ѯ", "ɾ��������ѯ����ѯ����="+ strQueryIDs, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "ɾ��������ѯʱ��λ����");                    
			throw new ServiceException("ɾ��������ѯʱ��λ����");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "ɾ��������ѯʱ���ҳ���");                    
			throw new ServiceException("ɾ��������ѯʱ���ҳ���");
		} 
	}
	
	/**
	 * ִ��������
	 * @param queryIDList
	 * @throws ServiceException 
	 */
	public void excuteBatchQuery(Collection queryIDList) throws ServiceException{
		if(queryIDList==null || queryIDList.size()==0)
			throw new ServiceException("��������������������ִ���������ѯ��");
		
		QueryHome qHome=null;
		Query q=null;
		StringBuffer logres=new StringBuffer();
		
		Iterator itQueryID=queryIDList.iterator();
		while(itQueryID.hasNext()){
			try{
				String sql="";			
				Integer queryID=(Integer)itQueryID.next();
				logres.append(queryID).append(",");
				
				qHome=HomeLocater.getQueryHome();
				q=qHome.findByPrimaryKey(queryID);
				
				//1. ��鵱ǰ��״̬�Ƿ�Ϊ���½�����ֻ���½������������ִ��
				if(!CommonConstDefinition.QUERY_STATUS_NEW.equals(q.getStatus()))
					throw new ServiceException("������״̬�����½�������ִ�У�");
				
				//2. ���ĸ�������ѯ��¼��״̬Ϊ�����ڴ���
				q.setStatus(CommonConstDefinition.QUERY_STATUS_PROCESSING);
				
				//3. �ݸ�������ѯ��¼����Ľ�������ͺͲ�ѯ������ƴ�ӳ���ѯ���
				sql=constructQuerySQL(q);
								
				//5. ����������¼
				BusinessUtility.excuteSQL("insert into T_QueryResultItem select QUERYRESULTITEM_ITEMNO.nextval,t.* from (" + sql +" ) t");
				
				//6. ���ĸ�������ѯ��¼��״̬Ϊ������ɹ�������ִ��ʱ��
				q.setPerformTime(TimestampUtility.getCurrentTimestamp());
				q.setStatus(CommonConstDefinition.QUERY_STATUS_SUCCESS);
			}
			catch(HomeFactoryException e1){
				LogUtility.log(clazz,LogLevel.ERROR, "ִ��������ѯʱ��λ����",e1);                    
				throw new ServiceException("ִ��ɾ��������ѯʱ��λ����");
			}
			catch(FinderException e2){
				LogUtility.log(clazz,LogLevel.ERROR, "ִ��������ѯʱ���ҳ�����",e2);                    
				throw new ServiceException("ִ��������ѯʱ���ҳ�����");
			}
			catch(ServiceException e3){
				//7. ���ĸ�������ѯ��¼��״̬Ϊ������ʧ�ܡ�����ִ��ʱ��
				q.setPerformTime(TimestampUtility.getCurrentTimestamp());
				q.setStatus(CommonConstDefinition.QUERY_STATUS_FAIL);
				
				LogUtility.log(clazz,LogLevel.ERROR, "ִ��������ѯ������",e3);                    
				throw new ServiceException("ִ��������ѯ����," + e3.getMessage());
			}
			catch(SQLException e4){
				q.setPerformTime(TimestampUtility.getCurrentTimestamp());
				q.setStatus(CommonConstDefinition.QUERY_STATUS_SUCCESS);
				
				LogUtility.log(clazz,LogLevel.ERROR, "������ѯ���������������",e4);                    
				throw new ServiceException("������ѯ���������������");
			}
		}

		//8. ������ص���־��¼
		try{
			String logInfo = "";
			if(logres.toString().length()>1)logInfo = logres.toString().substring(0, logres.toString().length()-1);
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
					PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_LOGISTICS, "ִ��������ѯ", "ִ��������ѯ:��ѯ����="+logInfo, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch (ServiceException e) {
			LogUtility.log(clazz,LogLevel.ERROR, "ִ��������ѯʱ������־����");                    
			throw new ServiceException("ִ��������ѯʱ������־����");
		}
	}

	
	/**
	 * �޸Ľ������¼״̬
	 * @param resultItemIDList
	 * @throws ServiceException 
	 */
	public void changeResultItemBatchQuery(Collection resultItemIDList,int queryId) throws ServiceException{
		if(resultItemIDList==null || resultItemIDList.size()==0)
			throw new ServiceException("���������������������޸��޽������¼��");
		StringBuffer logres=new StringBuffer();
		logres.append("�޸ĵĲ�ѯ����="+queryId+".");

		QueryResultItemHome qriHome=null;
		QueryResultItem qri=null;
		try{
			Iterator itResultItemID=resultItemIDList.iterator();
			
			//1. �޸Ľ��������ؼ�¼��״̬
			while(itResultItemID.hasNext()){
				boolean add = true;
				Integer itemNO=(Integer)itResultItemID.next();
				if((logres.toString()+"��ϸ��¼���:"+itemNO.intValue()).length()>=399)
				{
					add = false;
					logres.append("��");
				}
				if(add)
				logres.append("��ϸ��¼���:"+itemNO.intValue());
				
				qriHome=HomeLocater.getQueryResultItemHome();
				qri=qriHome.findByPrimaryKey(itemNO);
				
				//�޸�������ѯ�������õĽ�����еĽ����¼��״̬����������"��Ч"��Ϊ"��Ч"�����ߴ�"��Ч"���"��Ч"��
				if(CommonConstDefinition.GENERALSTATUS_VALIDATE.equals(qri.getStatus()))
				{
					qri.setStatus(CommonConstDefinition.GENERALSTATUS_INVALIDATE);
					if(add)
					logres.append("״̬��\"��Ч\"��Ϊ\"��Ч\",");
				}
				else if(CommonConstDefinition.GENERALSTATUS_INVALIDATE.equals(qri.getStatus()))
				{
					qri.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
					if(add)
					logres.append("״̬��\"��Ч\"��Ϊ\"��Ч\",");
				}
				else
					throw new ServiceException("�������¼״̬δ֪���޷�������");
			}
			logres.deleteCharAt(logres.length()-1);
			//2. ������ص���־��¼
			try{
				SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
						PublicService.getCurrentOperatorID(context), 0, 
						SystemLogRecorder.LOGMODULE_LOGISTICS, "�޸Ľ������¼״̬", logres.toString(), 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			}
			catch (ServiceException e) {
				LogUtility.log(clazz,LogLevel.ERROR, "�޸Ľ������¼״̬ʱ������־����");                    
				throw new ServiceException("�޸Ľ������¼״̬ʱ������־����");
			}
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "�޸Ľ������¼״̬ʱ��λ����");                    
			throw new ServiceException("�޸Ľ������¼״̬ʱ��λ����");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "�޸Ľ������¼״̬ʱ���ҳ���");                    
			throw new ServiceException("�޸Ľ������¼״̬ʱ���ҳ���");
		}
	}

	/**
	 * �����ѯ���
	 * 
	 * @param queryID
	 * @return
	 * @throws ServiceException
	 */
	private String constructQuerySQL(Query q) throws ServiceException{			

		StringBuffer showBuff=new StringBuffer();
		StringBuffer tableBuff=new StringBuffer();
		StringBuffer whereBuff=new StringBuffer();
		
		showBuff.append("select distinct " + q.getQueryId().intValue() +" as QUERYID ,customer.customerID as CUSTOMERID ");
		tableBuff.append(" from t_customer customer ");
		whereBuff.append(" where 1=1 ");
		// �����ֻ����ͻ���Ϣ
		if(CommonConstDefinition.QUERY_TYPE_CUSTOMER.equals(q.getQueryType())){
			showBuff.append(", 0 as ACCOUNTID, 0 as USERID, 0 as PACKAGEID, 0 as PRODUCTID, 0 as PSID, 0 as CCID ");
		}
		// �����ֻ�����ʻ���Ϣ
		else if(CommonConstDefinition.QUERY_TYPE_ACCOUNT.equals(q.getQueryType())){
			showBuff.append(", account.ACCOUNTID as ACCOUNTID,0 as USERID, 0 as PACKAGEID, 0 as PRODUCTID, " +
					"0 as PSID, 0 as CCID ");
			tableBuff.append(", t_account account ");
			whereBuff.append(" and account.customerid=customer.customerid ");
		}
		// �����ֻ�����Ʒ��Ϣ
		else if(CommonConstDefinition.QUERY_TYPE_PRODUCT.equals(q.getQueryType())){
			showBuff.append(", account.ACCOUNTID as ACCOUNTID, cp.SERVICEACCOUNTID as USERID," +
					" cp.REFERPACKAGEID as PACKAGEID, cp.PRODUCTID as PRODUCTID,cp.PSID as PSID ");

			tableBuff.append(", t_account account, t_customerproduct cp ");
			whereBuff.append(" and account.customerid=customer.customerid ");
			whereBuff.append(" and account.AccountID=cp.AccountID ");
			
			// ���Ҫ�����Ż��жϣ���Ҫ����Żݵı�
			if((!(q.getCpCampaignIdList()==null || "".equals(q.getCpCampaignIdList()))) || 
					q.getCpCampaignStartTime1()!=null || q.getCpCampaignStartTime2()!=null ||
					q.getCpCampaignEndTime1()!=null || q.getCpCampaignEndTime2()!=null ){
				
				showBuff.append(", cc.CCID as CCID ");
				tableBuff.append(", t_cpcampaignmap ccmap,t_customercampaign cc ");
				whereBuff.append(" and ccmap.CustProductID=cp.PSID and ccmap.ccid= cc.ccid ");
			}
			else
				showBuff.append(", 0 as CCID ");
		}
		//�����ֻ����ͻ��Ż���Ϣ
		else if(CommonConstDefinition.QUERY_TYPE_CUTOMER_CAMPAING.equals(q.getQueryType())){
			showBuff.append(", account.ACCOUNTID as ACCOUNTID, cp.SERVICEACCOUNTID as USERID," +
					" cp.REFERPACKAGEID as PACKAGEID, cp.PRODUCTID as PRODUCTID,cp.PSID as PSID ");

			tableBuff.append(", t_account account, t_customerproduct cp ");
			whereBuff.append(" and account.customerid=customer.customerid ");
			whereBuff.append(" and account.AccountID=cp.AccountID ");
			
			showBuff.append(", ccmap.CCID as CCID ");
			tableBuff.append(", t_cpcampaignmap ccmap ,t_customercampaign cc ");
			whereBuff.append(" and ccmap.CustProductID=cp.PSID and ccmap.ccid= cc.ccid and ccmap.CCID>0 ");			
		}
		else 
			throw new ServiceException("���������δ֪�����ܽ��в�����");
		
		// ���ʣ�µ��ֶ�
		showBuff.append(", '" + CommonConstDefinition.GENERALSTATUS_VALIDATE + "' as STATUS ");
		showBuff.append(", to_timestamp('").append(TimestampUtility.getCurrentTimestamp().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff') as DT_CREATE ");
		showBuff.append(", to_timestamp('").append(TimestampUtility.getCurrentTimestamp().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff') as DT_LASTMOD ");
		
		// 1. ���ݿͻ������������������Ŀͻ���¼
		// CUSTSTATUSLIST
		if(!(q.getCustStatusList()==null || "".equals(q.getCustStatusList())))
			whereBuff.append(" and customer.STATUS in (" + q.getCustStatusList() +") ");
		// CUSTTYPELIST
		if(!(q.getCustTypeList()==null || "".equals(q.getCustTypeList())))
			whereBuff.append(" and customer.CUSTOMERTYPE in (" + q.getCustTypeList() +") ");
		// CUSTCREATETIME1
		if(q.getCustCreateTime1()!=null)
			whereBuff.append(" and customer.CREATETIME>=to_timestamp('").append(q.getCustCreateTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		// CUSTCREATETIME2
		if(q.getCustCreateTime2()!=null)
			whereBuff.append(" and customer.CREATETIME<=to_timestamp('").append(q.getCustCreateTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		// CUSTOPENSOURCETYPELIST
		if(!(q.getCustOpenSourceTypeList()==null || "".equals(q.getCustOpenSourceTypeList())))
			whereBuff.append(" and customer.OPENSOURCETYPE in (" + q.getCustOpenSourceTypeList() +") ");
		// CUSTOPENSOURCEIDLIST
		if(!(q.getCustOpenSourceIdList()==null || "".equals(q.getCustOpenSourceIdList())))
			whereBuff.append(" and customer.OPENSOURCETYPEID in (" + q.getCustOpenSourceIdList() +") ");
		// CUSTCURRENTPOINTS1
		if(q.getCustCurrentPoints1()>0)
			whereBuff.append(" and customer.CurAccumulatePoint >=" + q.getCustCurrentPoints1());
		// CUSTCURRENTPOINTS2
		if(q.getCustCurrentPoints2()>0)
			whereBuff.append(" and customer.CurAccumulatePoint <=" + q.getCustCurrentPoints2());
		// CUSTTOTALPOINTS1
		if(q.getCustTotalPoints1()>0)
			whereBuff.append(" and customer.TOTALACCUMULATEPOINT >=" + q.getCustTotalPoints1());
		// CUSTTOTALPOINTS2
		if(q.getCustTotalPoints2()>0)
			whereBuff.append(" and customer.TOTALACCUMULATEPOINT <=" + q.getCustTotalPoints2());
		// CUSTOMERID
		if(q.getCustomerId()>0)
			whereBuff.append(" and customer.customerid like '%"+ q.getCustomerId() + "%'");
		// CUSTNAME
		if(!(q.getCustName()==null || "".equals(q.getCustName())))
			whereBuff.append(" and customer.NAME like '%"+ q.getCustName() + "%'");
		// CUSTADDRESS
		if(!(q.getCustAddress()==null || "".equals(q.getCustAddress()))){
			if(tableBuff.indexOf("t_address addr")<0){
				tableBuff.append(", t_address addr ");
				whereBuff.append(" and customer.AddressID = addr.Addressid ");
			}
			whereBuff.append(" and addr.detailaddress like '%" + q.getCustAddress() + "%'");
		}
		// CUSTDISTRICTIDLIST
		if(!(q.getCustDistrictIdList()==null || "".equals(q.getCustDistrictIdList()))){
			if(tableBuff.indexOf("t_address addr")<0){
				tableBuff.append(", t_address addr ");
				whereBuff.append(" and customer.AddressID = addr.Addressid ");
			}
			whereBuff.append(" and addr.DistrictID in (" + getSubDistrictSQLByDistrictStrList(q.getCustDistrictIdList()) +") ");
		}
	
		//�ʻ���Ϣ
		// ACCOUNTSTATUSLIST
		if(!(q.getAccountStatusList()==null || "".equals(q.getAccountStatusList()))){
			checkAccount(tableBuff,whereBuff);
			
			whereBuff.append(" and account.STATUS in (" + q.getAccountStatusList() +")");
		}
		// ACCOUNTTYPELIST
		if(!(q.getAccountTypeList()==null || "".equals(q.getAccountTypeList()))){
			checkAccount(tableBuff,whereBuff);
			
			whereBuff.append(" and account.ACCOUNTTYPE in (" + q.getAccountTypeList() +")");
		}
		// ACCOUNTCASHBALANCE1
		if(q.getAccountCashBalance1()>0.0){
			checkAccount(tableBuff,whereBuff);
			
			whereBuff.append(" and account.CASHDEPOSIT >=" + q.getAccountCashBalance1() +" ");
		}
		// ACCOUNTCASHBALANCE2
		if(q.getAccountCashBalance2()>0.0){
			checkAccount(tableBuff,whereBuff);
			
			whereBuff.append(" and account.CASHDEPOSIT <=" + q.getAccountCashBalance2() +" ");
		}
		// ACCOUNTTOKENBALANCE1
		if(q.getAccountTokenBalance1()>0.0){
			checkAccount(tableBuff,whereBuff);
			
			whereBuff.append(" and account.TOKENDEPOSIT >=" + q.getAccountTokenBalance1() +" ");
		}
		// ACCOUNTTOKENBALANCE2
		if(q.getAccountTokenBalance2()>0.0){
			checkAccount(tableBuff,whereBuff);
			
			whereBuff.append(" and account.TOKENDEPOSIT <=" + q.getAccountTokenBalance2() +" ");
		}
		// ACCOUNTMOPIDLIST
		if(!(q.getAccountMopIdList()==null || "".equals(q.getAccountMopIdList()))){
			checkAccount(tableBuff,whereBuff);
			
			whereBuff.append(" and account.MOPID in (" + q.getAccountMopIdList() + ")");	
		}
		// bankaccountstatuslist//��8-05���,�������ʺŴ���״̬��������`
   		if(!(q.getBankAccountStatusList()==null || "".equals(q.getBankAccountStatusList()))){
 			checkAccount(tableBuff,whereBuff);
			
			whereBuff.append(" and account.BANKACCOUNTSTATUS in (" + q.getBankAccountStatusList() + ")");	
		}
		//ACCOUNTADDRESS
		if(!(q.getCustAddress()==null || "".equals(q.getCustAddress()))){
			checkAccount(tableBuff,whereBuff);
			
			if(tableBuff.indexOf("t_address addracc")<0){
				tableBuff.append(", t_address addracc ");
				whereBuff.append(" and account.AddressID = addracc.Addressid ");
			}
			whereBuff.append(" and addracc.detailaddress like '%" + q.getCustAddress() + "%'");
		}
		// ACCTDISTRICTIDLIST
		if(!(q.getAccountDistrictIdList()==null || "".equals(q.getAccountDistrictIdList()))){
			if(tableBuff.indexOf("t_account")<0){
				tableBuff.append(", t_account account ");
				whereBuff.append(" and account.customerid=customer.customerid ");
			}
			
			if(tableBuff.indexOf("t_address addracc")<0){
				tableBuff.append(", t_address addracc ");
				whereBuff.append(" and account.AddressID = addracc.Addressid ");
			}
			whereBuff.append(" and addracc.DistrictID in (" + getSubDistrictSQLByDistrictStrList(q.getAccountDistrictIdList()) +") ");
		}
		// ACCOUNTCREATETIME1
		if(q.getAccountCreateTime1()!=null){
			checkAccount(tableBuff,whereBuff);
			
			whereBuff.append(" and account.CREATETIME>=to_timestamp('").append(q.getAccountCreateTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// ACCOUNTCREATETIME2
		if(q.getAccountCreateTime2()!=null){
			checkAccount(tableBuff,whereBuff);
			
			whereBuff.append(" and account.CREATETIME<=to_timestamp('").append(q.getAccountCreateTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}

		// ACCOUNTINVOICECREATETIME1
		if(q.getAccountInvoiceCreateTime1()!=null){
			checkAccount(tableBuff,whereBuff);
			
			if(tableBuff.indexOf("t_invoice")<0){
				tableBuff.append(", t_invoice invoice ");
				whereBuff.append(" and invoice.ACCTID=account.ACCOUNTID ");
			}
			whereBuff.append(" and invoice.CREATEDATE>=to_timestamp('").append(q.getAccountInvoiceCreateTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// ACCOUNTINVOICECREATETIME2
		if(q.getAccountInvoiceCreateTime2()!=null){
			checkAccount(tableBuff,whereBuff);
			
			if(tableBuff.indexOf("t_invoice")<0){
				tableBuff.append(", t_invoice invoice ");
				whereBuff.append(" and invoice.ACCTID=account.ACCOUNTID ");
			}
			whereBuff.append(" and invoice.CREATEDATE<=to_timestamp('").append(q.getAccountInvoiceCreateTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// ACCOUNTINVOICESTATUSLIST
		if(!(q.getAccountInvoiceStatusList()==null || "".equals(q.getAccountInvoiceStatusList()))){
			checkAccount(tableBuff,whereBuff);
			
			if(tableBuff.indexOf("t_invoice")<0){
				tableBuff.append(", t_invoice invoice ");
				whereBuff.append(" and invoice.ACCTID=account.ACCOUNTID ");
			}
			whereBuff.append(" and invoice.STATUS in (" + q.getAccountInvoiceStatusList() + ")");
		}
		
		// 3. ���ݿͻ���Ʒ�����������������Ŀͻ���Ʒ��¼,������ҽ��Ϊ���ͻ���Ʒ�� ���߰����ͻ���Ʒ����Ϣ����Ҫ���пͻ���Ʒ�Ĳ���
		// CPSTATUSLIST
		if(!(q.getCpStatusList()==null ||"".equals(q.getCpStatusList()))){
			checkCP(tableBuff,whereBuff);
			
			whereBuff.append(" and cp.STATUS in (" + q.getCpStatusList() +")");
		}
		// CPPRODUCTIDLIST
		if(!(q.getCpProductIdList()==null || "".equals(q.getCpProductIdList()))){
			checkCP(tableBuff,whereBuff);
			
			whereBuff.append(" and cp.PRODUCTID in (" + q.getCpProductIdList() + ")");
		}
		// CPCREATETIME1
		if(q.getCpCreateTime1()!=null){
			checkCP(tableBuff,whereBuff);
			
			whereBuff.append(" and cp.CREATETIME>=to_timestamp('").append(q.getCpCreateTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// CPCREATETIME2
		if(q.getCpCreateTime2()!=null){
			checkCP(tableBuff,whereBuff);
			
			whereBuff.append(" and cp.CREATETIME<=to_timestamp('").append(q.getCpCreateTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// CPCAMPAIGNIDLIST
		if(!(q.getCpCampaignIdList()==null || "".equals(q.getCpCampaignIdList()))){
			checkCPPMap(tableBuff,whereBuff);
			whereBuff.append(" and cc.CAMPAIGNID in (" + q.getCpCampaignIdList() + ")");
		}
		// CPCAMPAIGNSTARTTIME1
		if(q.getCpCampaignStartTime1()!=null){
			checkCPPMap(tableBuff,whereBuff);
			whereBuff.append(" and cc.datefrom>=to_timestamp('").append(q.getCpCampaignStartTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// CPCAMPAIGNSTARTTIME2
		if(q.getCpCampaignStartTime2()!=null){
			checkCPPMap(tableBuff,whereBuff);
			
			whereBuff.append(" and cc.datefrom<=to_timestamp('").append(q.getCpCampaignStartTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// CPCAMPAIGNENDTIME1
		if(q.getCpCampaignEndTime1()!=null){
			checkCPPMap(tableBuff,whereBuff);
			
			whereBuff.append(" and cc.dateto>=to_timestamp('").append(q.getCpCampaignEndTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// CPCAMPAIGNENDTIME2
		if(q.getCpCampaignEndTime2()!=null){
			checkCPPMap(tableBuff,whereBuff);
			whereBuff.append(" and cc.dateto<=to_timestamp('").append(q.getCpCampaignEndTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		//��8-05��Ӷ�productclass������
		if(!(q.getProductClassList()==null || "".equals(q.getProductClassList()))){
			checkAccount(tableBuff,whereBuff);
			
			if(tableBuff.indexOf("t_product product")<0){
				tableBuff.append(", t_product product ");
				whereBuff.append(" and cp.productid = product.productid ");
			}
			whereBuff.append(" and product.productclass in (" + q.getProductClassList() + ")");
		}		
		
		LogUtility.log(clazz,LogLevel.DEBUG,"�õ��Ĳ�ѯ�ͻ���Ʒ��Ϣ��SQLΪ��" + showBuff.toString() +tableBuff.toString() + whereBuff.toString());

		return showBuff.toString() +tableBuff.toString() + whereBuff.toString();
	}

	private void checkAccount(StringBuffer tableBuff,StringBuffer whereBuff){
		if(tableBuff.indexOf("t_account")<0){
			tableBuff.append(", t_account account ");
			whereBuff.append(" and account.customerid=customer.customerid ");
		}
	}
	
	private void checkCP(StringBuffer tableBuff,StringBuffer whereBuff){
		if(tableBuff.indexOf("t_account")<0){
			tableBuff.append(", t_account account ");
			whereBuff.append(" and account.customerid=customer.customerid ");
		}
		if(tableBuff.indexOf("t_customerproduct")<0){
			tableBuff.append(", t_customerproduct cp ");
			whereBuff.append(" and account.AccountID=cp.AccountID ");
		}
	}
	
	private void checkCPPMap(StringBuffer tableBuff,StringBuffer whereBuff){
		if(tableBuff.indexOf("t_account")<0){
			tableBuff.append(", t_account account ");
			whereBuff.append(" and account.customerid=customer.customerid ");
		}
		if(tableBuff.indexOf("t_customerproduct")<0){
			tableBuff.append(", t_customerproduct cp ");
			whereBuff.append(" and account.AccountID=cp.AccountID ");
		}
		if(tableBuff.indexOf("t_cpcampaignmap")<0){
			tableBuff.append(", t_cpcampaignmap ccmap,t_customercampaign cc ");
			whereBuff.append(" and ccmap.CustProductID=cp.PSID and ccmap.ccid= cc.ccid ");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private String getSubDistrictSQLByDistrictStrList(String str){
		if(str==null || str.length()==0)
			return "";
		else
			return " select ID from T_DISTRICTSETTING WHERE STATUS='V' connect by prior " +
					"ID=BELONGTO start with id in (" + str + ") ";
		
	}

}
