package com.dtv.oss.service.component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Collection;
import java.util.Iterator;
import java.util.Calendar;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.BatchJob;
import com.dtv.oss.domain.BatchJobHome;
import com.dtv.oss.domain.BatchJobItem;
import com.dtv.oss.domain.BatchJobItemHome;
import com.dtv.oss.domain.BatchJobProcessLogHome;
import com.dtv.oss.domain.Query;
import com.dtv.oss.domain.QueryHome;
import com.dtv.oss.domain.QueryResultItem;
import com.dtv.oss.domain.QueryResultItemHome;
import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.BatchJobItemDTO;
import com.dtv.oss.dto.BatchJobProcessLogDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactory;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.util.DtoFiller;
import com.dtv.oss.util.TimestampUtility;


public class BatchJobService extends AbstractService {

	private ServiceContext context;
	private static final Class clazz=BatchJobService.class;

	public BatchJobService(ServiceContext inContext){
		this.context=inContext;
	}
	
	/**
	 * 创建多个(3个)批处理任务单(设定执行时间，相隔10分钟)
	 * @param dto
	 * @throws ServiceException
	 */
	public void createBatchJobMuch(BatchJobDTO dto,int scheduleSendNumber,int scheduleSendTimeInterval) throws ServiceException{
		//批量发送消息或者邮件
		if(CommonConstDefinition.BATCH_JOB_TYPE_SM.equals(dto.getJobType()) || 
				CommonConstDefinition.BATCH_JOB_TYPE_SO.equals(dto.getJobType())){
			String scheduleType = dto.getScheduleType();
			if(CommonConstDefinition.SCHEDULE_TYPE_I.equals(scheduleType)){ //立即执行
				createBatchJob(dto);
			}
			else if(CommonConstDefinition.SCHEDULE_TYPE_S.equals(scheduleType)){ //定时执行
				java.sql.Timestamp scheduleTime = dto.getScheduleTime();
				Calendar c = Calendar.getInstance();
				java.sql.Timestamp newScheduleTime=null;
				for(int i=0;i<scheduleSendNumber;i++){
					createBatchJob(dto);
					c.setTime(scheduleTime);
					c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + scheduleSendTimeInterval*(i+1));
					newScheduleTime=new java.sql.Timestamp(c.getTimeInMillis());
					dto.setScheduleTime(newScheduleTime);
				}
				if(scheduleSendNumber==0 && scheduleSendTimeInterval==0){
					createBatchJob(dto);
				}
			}
		}
		else{
			createBatchJob(dto);
		}
	}
	
	/**
	 * 创建批处理任务单
	 * @param dto
	 * @throws ServiceException
	 */
	public void createBatchJob(BatchJobDTO dto) throws ServiceException{
		if(dto==null)
			throw new ServiceException("参数为空，不能创建批处理任务单！");
		LogUtility.log(clazz,LogLevel.DEBUG,"进入创建批处理任务单信息：");
		try{
			LogUtility.log(clazz,LogLevel.DEBUG,"createBatchJob",dto);
			BatchJobHome jobHome=HomeLocater.getBatchJobHome();
			BatchJob job=jobHome.create(dto);
			job.setStatus(CommonConstDefinition.BATCH_JOB_STATUS_WAIT);
			job.setCreateOpId(PublicService.getCurrentOperatorID(this.context));
			job.setCreateTime(TimestampUtility.getCurrentTimestamp());
			dto.setBatchId(job.getBatchId().intValue());
			
			//批量发送消息或者邮件
			if(CommonConstDefinition.BATCH_JOB_TYPE_SM.equals(dto.getJobType()) || 
					CommonConstDefinition.BATCH_JOB_TYPE_SO.equals(dto.getJobType())){
				//保存批量任务单结果集记录
				String description =(dto.getDescription() ==null) ? "" : dto.getDescription();	
				int retFromSP=0;
				String retDescFromSP="";
				description=description.toUpperCase();
				Connection con = null;
				CallableStatement stmt = null;
				try {
					System.out.println("1 param is " + dto.getBatchId());
					System.out.println("2 param is " + dto.getStatus());
					System.out.println("3 param is " + description);
					System.out.println("4 param is " + dto.getReferId());
				
					con = HomeFactory.getFactory().getDataSource().getConnection();
					stmt = con.prepareCall("{call SP_BATCHSENDMSGFORITEM(?,?,?,?,?,?)}");
					stmt.setInt(1, dto.getBatchId());
					stmt.setString(2, dto.getStatus());
					stmt.setString(3, description);
					stmt.setInt(4, dto.getReferId());
					stmt.registerOutParameter(5, Types.INTEGER);
					stmt.registerOutParameter(6, Types.VARCHAR);
					stmt.executeUpdate();
				
					retFromSP = stmt.getInt(5);
					retDescFromSP = stmt.getString(6);
					if (retFromSP < 0) {
						throw new ServiceException(retDescFromSP);
					}
				} 
				catch (SQLException e) {
					e.printStackTrace();
					throw new ServiceException(e);
				} 
				finally {
					if (stmt != null) {
						try {
							stmt.close();
						} 
						catch (SQLException ee) {
						}
					}
					if (con != null) {
						try {
							con.close();
						} 
						catch (SQLException ee) {
						}
					}
				}
			}
			//end
			else{
				//保存批量任务单结果集记录
				QueryResultItemHome qrtHome = HomeLocater.getQueryResultItemHome();
				Collection qrItem = qrtHome.findByQueryID(dto.getReferId());
				createBatchJobItem(qrItem,dto);
			}
			
			//将相应的批量查询记录的状态置为“已引用”
			QueryHome queryHome = HomeLocater.getQueryHome();
			Query query = queryHome.findByPrimaryKey(new Integer(dto.getReferId()));
			query.setStatus(CommonConstDefinition.QUERY_STATUS_REFERED);
			
		    
			//插入相关的日志记录
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
	    			PublicService.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_LOGISTICS, "新建批量任务单", "新建批量任务单:任务单ID="+ job.getBatchId().intValue(),
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "创建批量任务单时定位出错！");
			throw new ServiceException("创建批量任务单时定位出错！");
		}
		catch(FinderException e)
		{
			LogUtility.log(clazz,LogLevel.WARN,"查询批量查询结果集信息出错，原因：批量查询结果集查找出错！");
			throw new ServiceException("查询批量查询结果集信息出错，原因：批量查询结果集查找出错");
		}
		catch(CreateException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "批量任务单创建出错！");
			throw new ServiceException("批量任务单创建出错！");
		}
		catch (ServiceException e) {
			LogUtility.log(clazz,LogLevel.ERROR, "创建批量任务单时创建日志出错！");
			throw new ServiceException("创建批量任务单时创建日志出错！");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束创建批处理任务单信息：");
	}
	/**
	 * 创建批处理任务单结果集
	 * @param dto
	 * @throws ServiceException
	 */
	public void createBatchJobItem(Collection queryResultItem,BatchJobDTO bjDto) throws ServiceException{
		if(queryResultItem==null || queryResultItem.size() == 0)
			throw new ServiceException("参数为空，不能创建批处理任务单结果集！");
		LogUtility.log(clazz,LogLevel.DEBUG,"进入创建批处理任务单结果集信息：");
		
		/**
		try{
			Iterator iter = queryResultItem.iterator();
			while(iter.hasNext())
			{
				BatchJobItemDTO dto = new BatchJobItemDTO();
				QueryResultItem qrItem = (QueryResultItem)iter.next();
				//无效的批量查询记录不生成批处理任务单
				if(CommonConstDefinition.GENERALSTATUS_INVALIDATE.equals(qrItem.getStatus()))
					continue;
				dto.setAccountId(qrItem.getAccountId());
				dto.setCcId(qrItem.getCcId());
				dto.setCustomerId(qrItem.getCustomerId());
				dto.setCustPackageId(qrItem.getPackageId());
				dto.setPsId(qrItem.getPsId());
				dto.setUserId(qrItem.getUserId());
				dto.setBatchId(bjDto.getBatchId());
				dto.setDtCreate(bjDto.getDtCreate());
				dto.setDtLastmod(bjDto.getDtLastmod());
				dto.setStatus(bjDto.getStatus());
				dto.setStatusTime(bjDto.getDtLastmod());
				//批量发送消息或者邮件
				if(CommonConstDefinition.BATCH_JOB_TYPE_SM.equals(bjDto.getJobType()) || 
						CommonConstDefinition.BATCH_JOB_TYPE_SO.equals(bjDto.getJobType()))
					dto.setRcdData(bjDto.getDescription());

				BatchJobItemHome bjtHome = HomeLocater.getBatchJobItemHome();
				BatchJobItem batchJobItem = bjtHome.create(dto);
				//填写itemNO
				dto.setItemNO(batchJobItem.getItemNO().intValue());
				
				//add by chaoqiu 20070806 begin
				//创建排程日志
				createBatchJobProcessLog(bjDto,batchJobItem,CommonConstDefinition.BATCHJOBPROCESSACTION_CREATE);
				//add by chaoqiu 20070806 end
			}
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "创建批量任务单结果集时定位出错！");
			throw new ServiceException("创建批量任务单结果集时定位出错！");
		}
		catch(CreateException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "批量任务单结果集创建出错！");
			throw new ServiceException("批量任务单结果集创建出错！");
		}
		**/
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement preStmt = null;
		PreparedStatement preStmtlog = null;
        int commitnum = 0;
        String sqlJobItem = "";
        
		try{
		String strSql = "select itemno,accountId,ccId,customerId,packageId,psId,userId,status from t_queryresultitem where queryid="+bjDto.getReferId();
		con = DBUtil.getConnection();
		stmt = con.createStatement();
		rs = stmt.executeQuery(strSql);
		
		sqlJobItem = "insert into t_batchjobitem" +
					"(itemno,batchid,customerid,accountid,userid,custpackageid,psid,ccid,rcddata,status,statustime,dt_create,dt_lastmod) "+
					"values(BATCHJOBITEM_ITEMNO.Nextval," +
					"?," +  //1 batchid
					"?," +  //2 customerid
					"?," +  //3 accountid
					"?," +  //4 userid
					"?," +  //5 custpackageid
					"?," +  //6 psid
					"?," +  //7 ccid
					"?," +  //8 rcddata
					"?," +  //9 status
					"?," +  //10 statustime
					"to_timestamp(sysdate)," +
					"to_timestamp(sysdate))";
		preStmt = con.prepareStatement(sqlJobItem);
		
		//记日志
		preStmtlog = con.prepareStatement("insert into t_BatchJobProcessLog" +
					"(seqno,batchid,itemno,action,result,comments,operatorid,orgid,createtime,dt_create,dt_lastmod) "+ 
					"values(BATCHJOBPROCESSLOG_SEQNO.Nextval," +
					"?," +
					"?," +
					"?," +
					"null,"+
					"?," +
					"?," +
					"?," +
					"to_timestamp(sysdate)," +
					"to_timestamp(sysdate)," +
					"to_timestamp(sysdate))");
		
		while(rs.next()){
				if(CommonConstDefinition.GENERALSTATUS_INVALIDATE.equals(rs.getString("status")))
					continue;
				preStmt.setInt(1, bjDto.getBatchId());
				preStmt.setInt(2, rs.getInt("customerid"));
				preStmt.setInt(3, rs.getInt("accountid"));
				preStmt.setInt(4, rs.getInt("userid"));
				preStmt.setInt(5, rs.getInt("packageId"));
				preStmt.setInt(6, rs.getInt("psId"));
				preStmt.setInt(7, rs.getInt("ccId"));
				if(CommonConstDefinition.BATCH_JOB_TYPE_SM.equals(bjDto.getJobType()) || 
						CommonConstDefinition.BATCH_JOB_TYPE_SO.equals(bjDto.getJobType()))
					preStmt.setString(8, bjDto.getDescription());
				else 	preStmt.setString(8, null);
				preStmt.setString(9, bjDto.getStatus());
				preStmt.setTimestamp(10, bjDto.getDtLastmod());
				preStmt.addBatch();
				
				preStmtlog.setInt(1, bjDto.getBatchId());
				preStmtlog.setInt(2, rs.getInt("itemno"));
				preStmtlog.setString(3, CommonConstDefinition.BATCHJOBPROCESSACTION_CREATE);
				preStmtlog.setString(4, bjDto.getDescription());
				preStmtlog.setInt(5, PublicService.getCurrentOperatorID(context));
				preStmtlog.setInt(6, BusinessUtility.getOpOrgIDByOperatorID(PublicService.getCurrentOperatorID(context)));
				preStmtlog.addBatch();

				commitnum = commitnum+1;
				if(commitnum>2000){
			          commitnum=0;
			          preStmt.executeBatch();
			          preStmtlog.executeBatch();
			    }
			} 	
			preStmt.executeBatch();
			preStmtlog.executeBatch();
		}catch(Exception E3){
				E3.printStackTrace();
				LogUtility.log(clazz,LogLevel.ERROR, " 批量任务单结果集创建出错！"+E3.getMessage());
				throw new ServiceException("批量任务单结果集创建出错！");
		}finally {
			    if (rs !=null){
				   try{
					  rs.close();
				   }catch (SQLException e) {
					  LogUtility.log(BatchJobService.class, LogLevel.WARN,
							"createBatchJobItem", e);
				   }
			    }
				if (stmt != null){
					try {
						stmt.close();
					} catch (SQLException e) {
					LogUtility.log(BatchJobService.class, LogLevel.WARN,
							"createBatchJobItem", e);
					}
				}	
				if (preStmt != null){
					try {
						preStmt.close();
					} catch (SQLException e) {
						LogUtility.log(BatchJobService.class, LogLevel.WARN,
								"createBatchJobItem", e);
					}
				}	
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						LogUtility.log(BatchJobService.class, LogLevel.WARN,
								"createBatchJobItem", e);
					}
				}
		}	
		
		LogUtility.log(clazz,LogLevel.DEBUG,"结束创建批处理任务单结果集信息：");
	}
	/**
	 * 修改批处理任务单
	 * @param dto
	 * @throws ServiceException
	 */
	public void modifyBatchJob(BatchJobDTO dto) throws ServiceException{
		if(dto==null || dto.getBatchId()==0)
			throw new ServiceException("参数不满足条件，不能修改批处理任务单！");
		LogUtility.log(clazz,LogLevel.DEBUG,"进入修改批处理任务单信息：");
		try{
			//修改批量任务单记录
			BatchJobHome jobHome=HomeLocater.getBatchJobHome();
			BatchJob job=jobHome.findByPrimaryKey(new Integer(dto.getBatchId()));
			//只有"待执行"状态的批量任务单记录才能被修改
			if(!CommonConstDefinition.BATCH_JOB_STATUS_WAIT.equals(job.getStatus()))
				throw new ServiceException("只有待执行状态下的批量任务单才能修改！");
			if(job.ejbUpdate(dto)==-1)
				throw new ServiceException("修改批量任务单出错！");
			//设置CreateOperatorID
			job.setCreateOpId(PublicService.getCurrentOperatorID(this.context));
			//修改批量任务单结果集记录
			BatchJobItemHome bjtHome = HomeLocater.getBatchJobItemHome();
			Collection bjItem = bjtHome.findByBatchID(dto.getBatchId());
			modifyBatchJobItem(bjItem,dto);
			//插入相关的日志记录
			try{
				SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
						PublicService.getCurrentOperatorID(context), 0,
						SystemLogRecorder.LOGMODULE_LOGISTICS, "修改批量任务单", "修改批量任务单:任务单ID="+ job.getBatchId().intValue(),
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			}
			catch (ServiceException e) {
				LogUtility.log(clazz,LogLevel.ERROR, "修改批量任务单时创建日志出错！");
				throw new ServiceException("修改批量任务单时创建日志出错！");
			}
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "修改批量任务单时定位出错！");
			throw new ServiceException("修改批量任务单时定位出错！");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "修改批量任务单时查找出错！");
			throw new ServiceException("修改批量任务单时查找出错！");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束修改批处理任务单信息：");
	}
	/**
	 * 修改批处理任务单结果集
	 * @param dto
	 * @throws ServiceException
	 */
	public void modifyBatchJobItem(Collection bjItem,BatchJobDTO dto) throws ServiceException{
		if(bjItem==null || bjItem.size() == 0)
			throw new ServiceException("参数不满足条件，不能修改批处理任务单结果集！");
		LogUtility.log(clazz,LogLevel.DEBUG,"进入修改批处理任务单结果集信息：");
		
		/**
		Iterator iter = bjItem.iterator();
		while(iter.hasNext())
		{
			BatchJobItem item = (BatchJobItem)iter.next();
			//只有"待执行"状态的批量任务单结果集记录才能被修改
			if (!CommonConstDefinition.BATCH_JOB_STATUS_WAIT.equals(item.getStatus()))
				throw new ServiceException("只有待执行状态下的批量任务单结果集才能修改！");
			if(!item.getStatus().equals(dto.getStatus()))
			{
				item.setStatus(dto.getStatus());
				item.setStatusTime(dto.getDtLastmod());
			}
			if(CommonConstDefinition.BATCH_JOB_TYPE_SM.equals(dto.getJobType()) || 
					CommonConstDefinition.BATCH_JOB_TYPE_SO.equals(dto.getJobType())){
				
				String strRcdData=dto.getDescription();
				strRcdData=strRcdData.toUpperCase();
				//如果有姓名
				if(strRcdData.indexOf(CommonConstDefinition.SEND_MSG_CUSTOMER_NAME_VARIABLE)>-1){
					String strCustName="";
					strCustName=BusinessUtility.getCustomerNameForBatchItem("select name from t_customer where customerid=" + item.getCustomerId());
					if(strCustName==null)strCustName="";
					strRcdData=strRcdData.replaceAll(CommonConstDefinition.SEND_MSG_CUSTOMER_NAME_VARIABLE_RGE,
							strCustName);
				}
				//如果有帐户金额
				if(strRcdData.indexOf(CommonConstDefinition.SEND_MSG_ACCOUNT_BALANCE_VARIABLE)>-1){
					double acctBalance=0f;
					if(item.getAccountId()>0)
						acctBalance=BusinessUtility.getOwnFeeAmountForBatchItem("select sum(balance) from t_account where ACCOUNTID=" + item.getAccountId());
					else
						acctBalance=BusinessUtility.getOwnFeeAmountForBatchItem("select sum(balance) from t_account where customerid=" + item.getCustomerId());
					strRcdData=strRcdData.replaceAll(CommonConstDefinition.SEND_MSG_ACCOUNT_BALANCE_VARIABLE_RGE,
							String.valueOf(acctBalance));
				}
				//如果有帐单欠费额
				if(strRcdData.indexOf(CommonConstDefinition.SEND_MSG_INVOICEAMOUNT_VARIABLE)>-1){
					double invoiceAmount=0f;
					if(item.getAccountId()>0)
						invoiceAmount=BusinessUtility.getOwnFeeAmountForBatchItem("select SUM(AMOUNT-BCF) from t_invoice where status='" + 
								CommonConstDefinition.INVOICE_STATUS_WAIT +"' and acctid="+item.getAccountId());
					else
						invoiceAmount=BusinessUtility.getOwnFeeAmountForBatchItem("select SUM(AMOUNT-BCF) from t_invoice where status='"+ 
								CommonConstDefinition.INVOICE_STATUS_WAIT + "' and custid=" + item.getCustomerId());
					strRcdData=strRcdData.replaceAll(CommonConstDefinition.SEND_MSG_INVOICEAMOUNT_VARIABLE_RGE,
							String.valueOf(invoiceAmount));
				}
				item.setRcdData(strRcdData);

			}
			item.setDtLastmod(dto.getDtLastmod());
			
			//add by chaoqiu 20070806 begin
			//创建排程日志
			createBatchJobProcessLog(dto,item,CommonConstDefinition.BATCHJOBPROCESSACTION_MODIFY);
			//add by chaoqiu 20070806 end
		}
		**/
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement preStmt = null;
		PreparedStatement preStmtlog = null;
        int commitnum = 0;
        String sqlJobItem = "";
        
		try{
		String strSql = "select itemno,accountId,customerId,status,rcddata from t_batchjobitem where batchid="+dto.getBatchId();
		con = DBUtil.getConnection();
		stmt = con.createStatement();
		rs = stmt.executeQuery(strSql);
		
		sqlJobItem = "update t_batchjobitem " +
					"set status=?, " +
					"statustime=?, " +
					"rcddata=?, " +
					"dt_lastmod=? " +
					" where itemno=?";
		preStmt = con.prepareStatement(sqlJobItem);
		
		//记日志
		preStmtlog = con.prepareStatement("insert into t_BatchJobProcessLog" +
					"(seqno,batchid,itemno,action,result,comments,operatorid,orgid,createtime,dt_create,dt_lastmod) "+ 
					"values(BATCHJOBPROCESSLOG_SEQNO.Nextval," +
					"?," +
					"?," +
					"?," +
					"null,"+
					"?," +
					"?," +
					"?," +
					"to_timestamp(sysdate)," +
					"to_timestamp(sysdate)," +
					"to_timestamp(sysdate))");
		
		LogUtility.log(clazz,LogLevel.DEBUG,"strSql"+strSql);
		while(rs.next()){
				if (!CommonConstDefinition.BATCH_JOB_STATUS_WAIT.equals(rs.getString("status")))
				throw new ServiceException("只有待执行状态下的批量任务单结果集才能修改！");
				
				String strRcdData = "";
				if(CommonConstDefinition.BATCH_JOB_TYPE_SM.equals(dto.getJobType()) || 
						CommonConstDefinition.BATCH_JOB_TYPE_SO.equals(dto.getJobType())){
					strRcdData=dto.getDescription();
					strRcdData=strRcdData.toUpperCase();
					//如果有姓名
					if(strRcdData.indexOf(CommonConstDefinition.SEND_MSG_CUSTOMER_NAME_VARIABLE)>-1){
						String strCustName="";
						strCustName=BusinessUtility.getCustomerNameForBatchItem("select name from t_customer where customerid=" + rs.getInt("customerId"));
						if(strCustName==null)strCustName="";
						strRcdData=strRcdData.replaceAll(CommonConstDefinition.SEND_MSG_CUSTOMER_NAME_VARIABLE_RGE,
								strCustName);
					}
					//如果有帐户金额
					if(strRcdData.indexOf(CommonConstDefinition.SEND_MSG_ACCOUNT_BALANCE_VARIABLE)>-1){
						double acctBalance=0f;
						if(rs.getInt("accountId")>0)
							acctBalance=BusinessUtility.getOwnFeeAmountForBatchItem("select sum(balance) from t_account where ACCOUNTID=" + rs.getInt("accountId"));
						else
							acctBalance=BusinessUtility.getOwnFeeAmountForBatchItem("select sum(balance) from t_account where customerid=" + rs.getInt("customerId"));
						strRcdData=strRcdData.replaceAll(CommonConstDefinition.SEND_MSG_ACCOUNT_BALANCE_VARIABLE_RGE,
								String.valueOf(acctBalance));
					}
					//如果有帐单欠费额
					if(strRcdData.indexOf(CommonConstDefinition.SEND_MSG_INVOICEAMOUNT_VARIABLE)>-1){
						double invoiceAmount=0f;
						if(rs.getInt("accountId")>0)
							invoiceAmount=BusinessUtility.getOwnFeeAmountForBatchItem("select SUM(AMOUNT-BCF) from t_invoice where status='" + 
									CommonConstDefinition.INVOICE_STATUS_WAIT +"' and acctid="+rs.getInt("accountId"));
						else
							invoiceAmount=BusinessUtility.getOwnFeeAmountForBatchItem("select SUM(AMOUNT-BCF) from t_invoice where status='"+ 
									CommonConstDefinition.INVOICE_STATUS_WAIT + "' and custid=" + rs.getInt("customerId"));
						strRcdData=strRcdData.replaceAll(CommonConstDefinition.SEND_MSG_INVOICEAMOUNT_VARIABLE_RGE,
								String.valueOf(invoiceAmount));
					}
				}
				
				preStmt.setString(1, dto.getStatus());
				preStmt.setDate(2, new java.sql.Date(dto.getDtLastmod().getTime()));
				if(CommonConstDefinition.BATCH_JOB_TYPE_SM.equals(dto.getJobType()) || 
						CommonConstDefinition.BATCH_JOB_TYPE_SO.equals(dto.getJobType()))
					preStmt.setString(3, strRcdData);
				else 
					preStmt.setString(3, rs.getString("rcddata"));
				preStmt.setTimestamp(4, dto.getDtLastmod());
				preStmt.setInt(5, rs.getInt("itemno"));
				preStmt.addBatch();
				
				preStmtlog.setInt(1, dto.getBatchId());
				preStmtlog.setInt(2, rs.getInt("itemno"));
				preStmtlog.setString(3, CommonConstDefinition.BATCHJOBPROCESSACTION_MODIFY);
				preStmtlog.setString(4, dto.getDescription());
				preStmtlog.setInt(5, PublicService.getCurrentOperatorID(context));
				preStmtlog.setInt(6, BusinessUtility.getOpOrgIDByOperatorID(PublicService.getCurrentOperatorID(context)));
				preStmtlog.addBatch();

				commitnum = commitnum+1;
				if(commitnum>2000){
			          commitnum=0;
			          preStmt.executeBatch();
			          preStmtlog.executeBatch();
			    }
			} 	
			preStmt.executeBatch();
			preStmtlog.executeBatch();
		}
		catch(Exception E3){
				E3.printStackTrace();
				LogUtility.log(clazz,LogLevel.ERROR, "修改批处理任务单结果集信息出错！");
				throw new ServiceException("修改批处理任务单结果集信息出错！");
		}finally {
			   if (rs !=null){
				   try{
					  rs.close();
				   }catch (SQLException e) {
					  LogUtility.log(BatchJobService.class, LogLevel.WARN,
							"modifyBatchJobItem", e);
				   }
			    }
				if (stmt != null){
					try {
						stmt.close();
					} catch (SQLException e) {
					LogUtility.log(BatchJobService.class, LogLevel.WARN,
							"modifyBatchJobItem", e);
					}
				}	
				if (preStmt != null){
					try {
						preStmt.close();
					} catch (SQLException e) {
						LogUtility.log(BatchJobService.class, LogLevel.WARN,
								"modifyBatchJobItem", e);
					}
				}	
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						LogUtility.log(BatchJobService.class, LogLevel.WARN,
								"modifyBatchJobItem", e);
					}
				}
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG,"结束修改批处理任务单结果集信息：");
	}
	/**
	 * 取消批处理任务单
	 * @param queryIDList
	 * @throws ServiceException
	 */
	public void cancelBatchJob(int batchid) throws ServiceException{
		if(batchid==0)
			throw new ServiceException("参数不满足条件，不能取消批处理任务单！");
		LogUtility.log(clazz,LogLevel.DEBUG,"进入取消批处理任务单信息：");
		
		try{
			//取消批量任务单
			BatchJobHome jobHome = HomeLocater.getBatchJobHome();
			BatchJob job = jobHome.findByPrimaryKey(new Integer(batchid));
			job.setStatus(CommonConstDefinition.BATCH_JOB_STATUS_CANCEL);
			job.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			
			//取消批量任务单结果集
			BatchJobItemHome bjtHome = HomeLocater.getBatchJobItemHome();
			Collection bjItem = bjtHome.findByBatchID(batchid);
			cancelBatchJobItem(bjItem,job);
			
			//插入相关的日志记录
			try{
				SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
						PublicService.getCurrentOperatorID(context), 0,
						SystemLogRecorder.LOGMODULE_LOGISTICS, "取消批量任务单", "取消批量任务单",
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			}
			catch (ServiceException e) {
				LogUtility.log(clazz,LogLevel.ERROR, "取消批量任务单时创建日志出错！");
				throw new ServiceException("取消批量任务单时创建日志出错！");
			}
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "取消批量任务单时定位出错！");
			throw new ServiceException("取消批量任务单时定位出错！");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "取消批量任务单时查找出错！");
			throw new ServiceException("取消批量任务单时查找出错！");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG,"结束取消批处理任务单信息：");
	}
	/**
	 * 取消批处理任务单结果集 createBatchJobProcessLog
	 * @param queryIDList
	 * @throws ServiceException
	 */
	private void cancelBatchJobItem(Collection bjtItem,BatchJob job) throws ServiceException{
		if(bjtItem==null || bjtItem.size() == 0)
			throw new ServiceException("参数不满足条件，不能取消批处理任务单结果集！");
		LogUtility.log(clazz,LogLevel.DEBUG,"进入取消批处理任务单结果集信息：");
		/**
		Iterator iter = bjtItem.iterator();
		while(iter.hasNext())
		{
			BatchJobItem item = (BatchJobItem)iter.next();
			item.setStatus(CommonConstDefinition.BATCH_JOB_STATUS_CANCEL);
			item.setStatusTime(job.getDtLastmod());
			item.setDtLastmod(job.getDtLastmod());
			//add by chaoqiu 20070806 begin
			//创建排程日志
			createBatchJobProcessLog(job,item,CommonConstDefinition.BATCHJOBPROCESSACTION_CANCEL);
			//add by chaoqiu 20070806 end
		}
		**/
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement preStmt = null;
		PreparedStatement preStmtlog = null;
        int commitnum = 0;
        
		try{
		String strSql = "select itemno from t_batchjobitem where batchid="+job.getBatchId().intValue();
		con = DBUtil.getConnection();
		stmt = con.createStatement();
		rs = stmt.executeQuery(strSql);
		
		// 取消批量任务
		preStmt = con.prepareStatement("update t_batchjobitem set " +
							"Status=?," +
							"StatusTime=?," +
							"dt_lastmod=? " +
							"where itemno=?");
			
		//记日志
		preStmtlog = con.prepareStatement("insert into t_BatchJobProcessLog" +
							"(seqno,batchid,itemno,action,result,comments,operatorid,orgid,createtime,dt_create,dt_lastmod) "+ 
							"values(BATCHJOBPROCESSLOG_SEQNO.Nextval," +
							"?," +
							"?," +
							"?," +
							"null,"+
							"?," +
							"?," +
							"?," +
							"to_timestamp(sysdate)," +
							"to_timestamp(sysdate)," +
							"to_timestamp(sysdate))");	
		while(rs.next()){
				preStmt.setString(1, CommonConstDefinition.BATCH_JOB_STATUS_CANCEL);
				preStmt.setTimestamp(2, job.getDtLastmod());
				preStmt.setTimestamp(3, job.getDtLastmod());
				preStmt.setInt(4, rs.getInt("itemno"));
				preStmt.addBatch();
				
				preStmtlog.setInt(1, job.getBatchId().intValue());
				preStmtlog.setInt(2, rs.getInt("itemno"));
				preStmtlog.setString(3, CommonConstDefinition.BATCHJOBPROCESSACTION_CANCEL);
				preStmtlog.setString(4, job.getDescription());
				preStmtlog.setInt(5, PublicService.getCurrentOperatorID(context));
				preStmtlog.setInt(6, BusinessUtility.getOpOrgIDByOperatorID(PublicService.getCurrentOperatorID(context)));
				preStmtlog.addBatch();
				
				commitnum = commitnum+1;
				if(commitnum>2000){
			          commitnum=0;
			          preStmt.executeBatch();
			          preStmtlog.executeBatch();
			    }
			}
		preStmt.executeBatch();
        preStmtlog.executeBatch();
		}catch(Exception E3){
				E3.printStackTrace();
				LogUtility.log(clazz,LogLevel.ERROR, "取消批量任务单时修改任务明细出错！");                    
				throw new ServiceException("取消批量任务单时修改任务明细出错！");
			}finally {
				if (rs !=null){
					try{
						rs.close();
					}catch (SQLException e) {
						LogUtility.log(BatchJobService.class, LogLevel.WARN,
								"cancelBatchJobItem", e);
					}
				}
				if (stmt != null)
					try {
						stmt.close();
					} catch (SQLException e) {
					LogUtility.log(BatchJobService.class, LogLevel.WARN,
							"cancelBatchJobItem", e);
					}
				if (preStmt != null)
						try {
							preStmt.close();
						} catch (SQLException e) {
						LogUtility.log(BatchJobService.class, LogLevel.WARN,
								"cancelBatchJobItem", e);
				}	
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						LogUtility.log(BatchJobService.class, LogLevel.WARN,
								"cancelBatchJobItem", e);
					}
				}
			}	
		LogUtility.log(clazz,LogLevel.DEBUG,"结束取消批处理任务单结果集信息：");
	}
	
	/**
	 * 创建批量处理的排程日志 createBatchJobProcessLog
	 * @param queryIDList
	 * @throws ServiceException
	 */
	public void createBatchJobProcessLog(BatchJobDTO bjDto,BatchJobItem batchJobItem,String action) throws ServiceException{
		try{
		BatchJobProcessLogHome bjplHome=HomeLocater.getBatchJobProcessLogHome();
		BatchJobProcessLogDTO bjplDTO=new BatchJobProcessLogDTO();
		bjplDTO.setAction(action);
		bjplDTO.setComments(bjDto.getDescription());
		bjplDTO.setCreateTime(TimestampUtility.getCurrentDate());
		bjplDTO.setItemNO(batchJobItem.getItemNO().intValue());
		bjplDTO.setBatchId(bjDto.getBatchId());
		bjplDTO.setOperatorId(PublicService.getCurrentOperatorID(context));
		bjplDTO.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(PublicService.getCurrentOperatorID(context)));
		//bjplDTO.setResult(CommonConstDefinition.PROCESSSTATUS_SUCESS);
		bjplHome.create(bjplDTO);
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "创建批量处理的排程日志定位出错！");
			throw new ServiceException("创建批量处理的排程日志定位出错！");
		}
		catch(CreateException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "批量处理的排程日志创建出错！");
			throw new ServiceException("批量处理的排程日志创建出错！");
		}
		
	}
	/**
	 * 创建批量处理的排程日志 createBatchJobProcessLog
	 * @param queryIDList
	 * @throws ServiceException
	 */
	public void createBatchJobProcessLog(BatchJob bj,BatchJobItem batchJobItem,String action) throws ServiceException{
		try{
		BatchJobProcessLogHome bjplHome=HomeLocater.getBatchJobProcessLogHome();
		BatchJobProcessLogDTO bjplDTO=new BatchJobProcessLogDTO();
		bjplDTO.setAction(action);
		bjplDTO.setComments(bj.getDescription());
		bjplDTO.setCreateTime(TimestampUtility.getCurrentDate());
		bjplDTO.setItemNO(batchJobItem.getItemNO().intValue());
		bjplDTO.setBatchId(bj.getBatchId().intValue());
		bjplDTO.setOperatorId(PublicService.getCurrentOperatorID(context));
		bjplDTO.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(PublicService.getCurrentOperatorID(context)));
		//bjplDTO.setResult(CommonConstDefinition.PROCESSSTATUS_SUCESS);
		bjplHome.create(bjplDTO);
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "创建批量处理的排程日志定位出错！");
			throw new ServiceException("创建批量处理的排程日志定位出错！");
		}
		catch(CreateException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "批量处理的排程日志创建出错！");
			throw new ServiceException("批量处理的排程日志创建出错！");
		}
		
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
