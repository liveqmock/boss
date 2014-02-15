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
	 * 创建批处理查询
	 * @param dto
	 * @throws ServiceException 
	 */
	public void createBatchQuery(QueryDTO dto) throws ServiceException{
		
		if(dto==null)
			throw new ServiceException("参数为空，不能创建批处理查询！");
		
		try{
			//1. 保存批量查询记录
			QueryHome qHome=HomeLocater.getQueryHome();
			Query q=qHome.create(dto);
			
			//设置Status、CreateOperatorID、CreateTime
			q.setStatus(CommonConstDefinition.QUERY_STATUS_NEW);
			q.setCreateOperatorId(PublicService.getCurrentOperatorID(this.context));
			q.setCreateTime(TimestampUtility.getCurrentTimestamp());
			
			dto.setQueryId(q.getQueryId().intValue());
			
			//把刚才创建的ID放入context里
			context.put(Service.PROCESS_RESULT,q.getQueryId());
			String desc = "创建批量查询";
			if("Y".equals(dto.getTemplateFlag()))
				desc += "模板";
			//2. 插入相关的日志记录
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_LOGISTICS, desc, desc+":查询批号="+ q.getQueryId().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "创建批量查询时定位出错！");                    
			throw new ServiceException("创建批量查询时定位出错！");
		}
		catch(CreateException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "批量查询创建出错！");                    
			throw new ServiceException("批量查询创建出错！");
		} 
		catch (ServiceException e) {
			LogUtility.log(clazz,LogLevel.ERROR, "创建批量查询时创建日志出错！");                    
			throw new ServiceException("创建批量查询时创建日志出错！");
		}
		
	}
	
	/**
	 * 修改批处理查询
	 * @param dto
	 * @throws ServiceException 
	 */
	public void modifyBatchQuery(QueryDTO dto) throws ServiceException{
		if(dto==null || dto.getQueryId()==0)
			throw new ServiceException("参数不满足条件，不能修改批处理查询！");
		
		try{
			//1. 修改批量查询记录
			QueryHome qHome=HomeLocater.getQueryHome();
			Query q=qHome.findByPrimaryKey(new Integer(dto.getQueryId()));
			
			//只有处于“引用”状态的查询操做才不允许修改
			if(CommonConstDefinition.QUERY_STATUS_REFERED.equals(q.getStatus()))
				throw new ServiceException("该批量查询处于被引用状态，不能修改！");
	
			dto.setStatus(CommonConstDefinition.QUERY_STATUS_NEW);
			dto.setPerformTime(null);
			//2. 删除查询的结果集
			try{
				BusinessUtility.deleteQueryResultItemIDsByQueryID(dto.getQueryId());
			}
			catch(SQLException ee){
				LogUtility.log(clazz,LogLevel.ERROR, "删除批量查询结果记录出错！",ee);                    
				throw new ServiceException("删除批量查询结果记录出错！");
			}
			
			try {
				if(q.ejbUpdate(dto)==-1){
					throw new ServiceException("修改批量查询出错！");
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//设置CreateOperatorID
			q.setCreateOperatorId(PublicService.getCurrentOperatorID(this.context));
			
			//3. 插入相关的日志记录
			try{
				String desc = "修改批量查询";
				if("Y".equals(dto.getTemplateFlag()))
					desc += "模板";
				SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
										PublicService.getCurrentOperatorID(context), 0, 
										SystemLogRecorder.LOGMODULE_LOGISTICS, desc, desc+":查询批号="+ q.getQueryId().intValue(), 
										SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			}
			catch (ServiceException e) {
				LogUtility.log(clazz,LogLevel.ERROR, "修改批量查询时创建日志出错！");                    
				throw new ServiceException("修改批量查询时创建日志出错！");
			}
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "修改批量查询时定位出错！");                    
			throw new ServiceException("修改批量查询时定位出错！");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "修改批量查询时查找出错！");                    
			throw new ServiceException("修改批量查询时查找出错！");
		} 
	}
	
	/**
	 * 取消批处理
	 * @param queryIDList
	 * @throws ServiceException 
	 */
	public void cancelBatchQuery(Collection queryIDList) throws ServiceException{
		
		if(queryIDList==null || queryIDList.size()==0)
			throw new ServiceException("参数不满足条件，不能删除批处理查询！");
		
		String strQueryIDs="";
		try{
			QueryHome qHome=null;
			Query q=null;
			
			Iterator itQueryID=queryIDList.iterator();
			while(itQueryID.hasNext()){
				Integer queryID=(Integer)itQueryID.next();
				
				//1. 删除批量查询所得的结果集记录
				qHome=HomeLocater.getQueryHome();
				q=qHome.findByPrimaryKey(queryID);
				//修改q的状态
				q.setStatus(CommonConstDefinition.QUERY_STATUS_CANCEL);
				//q.remove();
				
				//2. 删除批量查询所得的结果集记录
				try{
					BusinessUtility.deleteQueryResultItemIDsByQueryID(queryID.intValue());
				}
				catch(SQLException ee){
					LogUtility.log(clazz,LogLevel.ERROR, "删除批量查询结果记录出错！",ee);                    
					throw new ServiceException("删除批量查询结果记录出错！");
				}
				
				if(!"".equals(strQueryIDs))strQueryIDs=strQueryIDs +",";
				strQueryIDs=strQueryIDs + queryID.intValue();
			}

			//3. 插入相关的日志记录
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
					PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_LOGISTICS, "删除批量查询", "删除批量查询，查询批号="+ strQueryIDs, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "删除批量查询时定位出错！");                    
			throw new ServiceException("删除批量查询时定位出错！");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "删除批量查询时查找出错！");                    
			throw new ServiceException("删除批量查询时查找出错！");
		} 
	}
	
	/**
	 * 执行批处理
	 * @param queryIDList
	 * @throws ServiceException 
	 */
	public void excuteBatchQuery(Collection queryIDList) throws ServiceException{
		if(queryIDList==null || queryIDList.size()==0)
			throw new ServiceException("参数不满足条件，不能执行批处理查询！");
		
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
				
				//1. 检查当前的状态是否为“新建”，只有新建的批处理才能执行
				if(!CommonConstDefinition.QUERY_STATUS_NEW.equals(q.getStatus()))
					throw new ServiceException("批处理状态不是新建，不能执行！");
				
				//2. 更改该批量查询记录的状态为“正在处理”
				q.setStatus(CommonConstDefinition.QUERY_STATUS_PROCESSING);
				
				//3. 据该批量查询记录定义的结果集类型和查询条件，拼接出查询语句
				sql=constructQuerySQL(q);
								
				//5. 插入结果集记录
				BusinessUtility.excuteSQL("insert into T_QueryResultItem select QUERYRESULTITEM_ITEMNO.nextval,t.* from (" + sql +" ) t");
				
				//6. 更改该批量查询记录的状态为“处理成功“，和执行时间
				q.setPerformTime(TimestampUtility.getCurrentTimestamp());
				q.setStatus(CommonConstDefinition.QUERY_STATUS_SUCCESS);
			}
			catch(HomeFactoryException e1){
				LogUtility.log(clazz,LogLevel.ERROR, "执行批量查询时定位出错！",e1);                    
				throw new ServiceException("执行删除批量查询时定位出错！");
			}
			catch(FinderException e2){
				LogUtility.log(clazz,LogLevel.ERROR, "执行批量查询时查找出错！！",e2);                    
				throw new ServiceException("执行批量查询时查找出错！！");
			}
			catch(ServiceException e3){
				//7. 更改该批量查询记录的状态为“处理失败“，和执行时间
				q.setPerformTime(TimestampUtility.getCurrentTimestamp());
				q.setStatus(CommonConstDefinition.QUERY_STATUS_FAIL);
				
				LogUtility.log(clazz,LogLevel.ERROR, "执行批量查询出错！！",e3);                    
				throw new ServiceException("执行批量查询出错," + e3.getMessage());
			}
			catch(SQLException e4){
				q.setPerformTime(TimestampUtility.getCurrentTimestamp());
				q.setStatus(CommonConstDefinition.QUERY_STATUS_SUCCESS);
				
				LogUtility.log(clazz,LogLevel.ERROR, "批量查询创建结果集出错！！",e4);                    
				throw new ServiceException("批量查询创建结果集出错！！");
			}
		}

		//8. 插入相关的日志记录
		try{
			String logInfo = "";
			if(logres.toString().length()>1)logInfo = logres.toString().substring(0, logres.toString().length()-1);
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
					PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_LOGISTICS, "执行批量查询", "执行批量查询:查询批号="+logInfo, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch (ServiceException e) {
			LogUtility.log(clazz,LogLevel.ERROR, "执行批量查询时创建日志出错！");                    
			throw new ServiceException("执行批量查询时创建日志出错！");
		}
	}

	
	/**
	 * 修改结果集记录状态
	 * @param resultItemIDList
	 * @throws ServiceException 
	 */
	public void changeResultItemBatchQuery(Collection resultItemIDList,int queryId) throws ServiceException{
		if(resultItemIDList==null || resultItemIDList.size()==0)
			throw new ServiceException("参数不满足条件，不能修改修结果集记录！");
		StringBuffer logres=new StringBuffer();
		logres.append("修改的查询批号="+queryId+".");

		QueryResultItemHome qriHome=null;
		QueryResultItem qri=null;
		try{
			Iterator itResultItemID=resultItemIDList.iterator();
			
			//1. 修改结果集中相关记录的状态
			while(itResultItemID.hasNext()){
				boolean add = true;
				Integer itemNO=(Integer)itResultItemID.next();
				if((logres.toString()+"明细记录编号:"+itemNO.intValue()).length()>=399)
				{
					add = false;
					logres.append("等");
				}
				if(add)
				logres.append("明细记录编号:"+itemNO.intValue());
				
				qriHome=HomeLocater.getQueryResultItemHome();
				qri=qriHome.findByPrimaryKey(itemNO);
				
				//修改批量查询操作所得的结果集中的结果记录的状态，包括：从"有效"变为"无效"，或者从"无效"变成"有效"。
				if(CommonConstDefinition.GENERALSTATUS_VALIDATE.equals(qri.getStatus()))
				{
					qri.setStatus(CommonConstDefinition.GENERALSTATUS_INVALIDATE);
					if(add)
					logres.append("状态从\"有效\"变为\"无效\",");
				}
				else if(CommonConstDefinition.GENERALSTATUS_INVALIDATE.equals(qri.getStatus()))
				{
					qri.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
					if(add)
					logres.append("状态从\"无效\"变为\"有效\",");
				}
				else
					throw new ServiceException("结果集记录状态未知，无法操作！");
			}
			logres.deleteCharAt(logres.length()-1);
			//2. 插入相关的日志记录
			try{
				SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
						PublicService.getCurrentOperatorID(context), 0, 
						SystemLogRecorder.LOGMODULE_LOGISTICS, "修改结果集记录状态", logres.toString(), 
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			}
			catch (ServiceException e) {
				LogUtility.log(clazz,LogLevel.ERROR, "修改结果集记录状态时创建日志出错！");                    
				throw new ServiceException("修改结果集记录状态时创建日志出错！");
			}
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "修改结果集记录状态时定位出错！");                    
			throw new ServiceException("修改结果集记录状态时定位出错！");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "修改结果集记录状态时查找出错！");                    
			throw new ServiceException("修改结果集记录状态时查找出错！");
		}
	}

	/**
	 * 构造查询语句
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
		// 结果集只保存客户信息
		if(CommonConstDefinition.QUERY_TYPE_CUSTOMER.equals(q.getQueryType())){
			showBuff.append(", 0 as ACCOUNTID, 0 as USERID, 0 as PACKAGEID, 0 as PRODUCTID, 0 as PSID, 0 as CCID ");
		}
		// 结果集只保存帐户信息
		else if(CommonConstDefinition.QUERY_TYPE_ACCOUNT.equals(q.getQueryType())){
			showBuff.append(", account.ACCOUNTID as ACCOUNTID,0 as USERID, 0 as PACKAGEID, 0 as PRODUCTID, " +
					"0 as PSID, 0 as CCID ");
			tableBuff.append(", t_account account ");
			whereBuff.append(" and account.customerid=customer.customerid ");
		}
		// 结果集只保存产品信息
		else if(CommonConstDefinition.QUERY_TYPE_PRODUCT.equals(q.getQueryType())){
			showBuff.append(", account.ACCOUNTID as ACCOUNTID, cp.SERVICEACCOUNTID as USERID," +
					" cp.REFERPACKAGEID as PACKAGEID, cp.PRODUCTID as PRODUCTID,cp.PSID as PSID ");

			tableBuff.append(", t_account account, t_customerproduct cp ");
			whereBuff.append(" and account.customerid=customer.customerid ");
			whereBuff.append(" and account.AccountID=cp.AccountID ");
			
			// 如果要进行优惠判断，还要添加优惠的表
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
		//结果集只保存客户优惠信息
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
			throw new ServiceException("结果集类型未知，不能进行操作！");
		
		// 填充剩下的字段
		showBuff.append(", '" + CommonConstDefinition.GENERALSTATUS_VALIDATE + "' as STATUS ");
		showBuff.append(", to_timestamp('").append(TimestampUtility.getCurrentTimestamp().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff') as DT_CREATE ");
		showBuff.append(", to_timestamp('").append(TimestampUtility.getCurrentTimestamp().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff') as DT_LASTMOD ");
		
		// 1. 根据客户条件搜索满足条件的客户记录
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
	
		//帐户信息
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
		// bankaccountstatuslist//侯8-05添加,对银行帐号串配状态进行搜索`
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
		
		// 3. 根据客户产品条件搜索满足条件的客户产品记录,如果查找结果为“客户产品” 或者包含客户产品的信息，则还要进行客户产品的查找
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
		//侯8-05添加对productclass的搜索
		if(!(q.getProductClassList()==null || "".equals(q.getProductClassList()))){
			checkAccount(tableBuff,whereBuff);
			
			if(tableBuff.indexOf("t_product product")<0){
				tableBuff.append(", t_product product ");
				whereBuff.append(" and cp.productid = product.productid ");
			}
			whereBuff.append(" and product.productclass in (" + q.getProductClassList() + ")");
		}		
		
		LogUtility.log(clazz,LogLevel.DEBUG,"得到的查询客户产品信息的SQL为：" + showBuff.toString() +tableBuff.toString() + whereBuff.toString());

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
