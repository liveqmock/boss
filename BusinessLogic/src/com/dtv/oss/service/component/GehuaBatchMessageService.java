/**
 * 
 */
package com.dtv.oss.service.component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.CreateException;

import com.dtv.oss.domain.BatchJob;
import com.dtv.oss.domain.BatchJobHome;
import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.dto.custom.GeHuaUploadCustBatchHeaderDTO;
import com.dtv.oss.dto.custom.GehuaCustInfoForBatchMessageDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.util.DtoFiller;
import com.dtv.oss.util.TimestampUtility;

/**
 * @author 240910y
 *
 */
public class GehuaBatchMessageService extends AbstractService {

	/**
	 * 
	 */
	public GehuaBatchMessageService() {
		// TODO Auto-generated constructor stub
	}
    private static final Class clazz = GehuaBatchMessageService.class;
    private ServiceContext serviceContext = null;
    
    /**
     * constructer method
     * @param context
     */
    public GehuaBatchMessageService(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
        setOperatorID(PublicService.getCurrentOperatorID(serviceContext));
    }
    
    /**
     * ���ڷ�����Ϣ�ϴ��ļ�ʱ����ͻ���Ϣ�־û�
     * @param custBatchHeaderDTO
     * @param custInfoCol
     * @throws ServiceException
     */
    public  void uploadForBatchMessage(GeHuaUploadCustBatchHeaderDTO custBatchHeaderDTO,Collection custInfoCol) throws ServiceException {
    	int batchNo=getSequenceKey("GEHUA_CUST_BATCHHEADER_BATCHNO");
    	custBatchHeaderDTO.setBatchNo(batchNo);
    	custBatchHeaderDTO.setCreateOpID(getOperatorID());
    	int orgID=BusinessUtility.getOpOrgIDByOperatorID(getOperatorID());
    	custBatchHeaderDTO.setCreateOrgID(orgID) ;
    	//����ͷ��Ϣ
    	createBatchHeaderInfo(custBatchHeaderDTO);
    	//���������ͻ���Ϣ
    	int hasCustOrgID=BusinessUtility.getParentHasCustomerOrgID(orgID);
    	createBathMessageCustInfo(batchNo,custInfoCol,hasCustOrgID);
    }
    /**
     * ���������ض��ϴ��ļ�ʱ����ͻ���Ϣ�־û�
     * @param custBatchHeaderDTO
     * @param custInfoCol
     * @throws ServiceException
     */
    public  void uploadForBatchSuspend(GeHuaUploadCustBatchHeaderDTO custBatchHeaderDTO,Collection custInfoCol) throws ServiceException {
    	int batchNo=getSequenceKey("GEHUA_CUST_BATCHHEADER_BATCHNO");
    	custBatchHeaderDTO.setBatchNo(batchNo);
    	custBatchHeaderDTO.setCreateOpID(getOperatorID());
    	int orgID=BusinessUtility.getOpOrgIDByOperatorID(getOperatorID());
    	custBatchHeaderDTO.setCreateOrgID(orgID) ;
    	//����ͷ��Ϣ
    	createBatchHeaderInfo(custBatchHeaderDTO);
    	//���������ͻ���Ϣ
    	int hasCustOrgID=BusinessUtility.getParentHasCustomerOrgID(orgID);
    	createBathSuspendCustInfo(batchNo,custInfoCol,hasCustOrgID);
    }
    /**
     * ����������ͨ�ϴ��ļ�ʱ����ͻ���Ϣ�־û�
     * @param custBatchHeaderDTO
     * @param custInfoCol
     * @throws ServiceException
     */
    public  void uploadForBatchResume(GeHuaUploadCustBatchHeaderDTO custBatchHeaderDTO,Collection custInfoCol) throws ServiceException {
    	int batchNo=getSequenceKey("GEHUA_CUST_BATCHHEADER_BATCHNO");
    	custBatchHeaderDTO.setBatchNo(batchNo);
    	custBatchHeaderDTO.setCreateOpID(getOperatorID());
    	int orgID=BusinessUtility.getOpOrgIDByOperatorID(getOperatorID());
    	custBatchHeaderDTO.setCreateOrgID(orgID) ;
    	//����ͷ��Ϣ
    	createBatchHeaderInfo(custBatchHeaderDTO);
    	//���������ͻ���Ϣ
    	int hasCustOrgID=BusinessUtility.getParentHasCustomerOrgID(orgID);
    	createBathResumeCustInfo(batchNo,custInfoCol,hasCustOrgID);
    }
    /**
     * ������������
     * @param custBatchHeaderDTO
     * @param jobDTO
     * @throws ServiceException
     */
    public  void createBatchJobForBatchMessage(GeHuaUploadCustBatchHeaderDTO custBatchHeaderDTO,BatchJobDTO jobDTO) throws ServiceException {
		try{
			jobDTO.setStatus(CommonConstDefinition.BATCH_JOB_STATUS_WAIT);
			jobDTO.setCreateOpId(getOperatorID());
			jobDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
			
			BatchJobHome jobHome=HomeLocater.getBatchJobHome();
			BatchJob job=jobHome.create(jobDTO);
			jobDTO.setBatchId(job.getBatchId().intValue());
			int recordCount=0;
			//ȡ��ָ�����Ŷ�Ӧ��cp��Ϣ��
	    	Collection custInfoCol=getMessageCustCpInfo(custBatchHeaderDTO.getBatchNo());
	    	if(!custInfoCol.isEmpty()){
	    		int orgID=BusinessUtility.getOpOrgIDByOperatorID(getOperatorID());
	    		Iterator custInfoIter=custInfoCol.iterator();
	    		while(custInfoIter.hasNext()){
	    			GehuaCustInfoForBatchMessageDTO dto=(GehuaCustInfoForBatchMessageDTO)custInfoIter.next();
	    			recordCount+=createJobItemAndLog(dto,jobDTO,orgID);
	    		}
	    	}
	    	job.setTotoalRecordNo(recordCount);
	    	updateBatchHeadInfo(custBatchHeaderDTO.getBatchNo(),jobDTO.getBatchId());
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.ERROR,e);
			throw new ServiceException("������������ʱ��λ����");
		}catch(CreateException e){
			LogUtility.log(clazz,LogLevel.ERROR, e);
			throw new ServiceException("�������񵥴�������");
		}
    }
    /**
     * ���������ػ�����
     * @param custBatchHeaderDTO
     * @param jobDTO
     * @throws ServiceException
     */
    public  void createBatchJobForBatchSuspend(GeHuaUploadCustBatchHeaderDTO custBatchHeaderDTO,BatchJobDTO jobDTO) throws ServiceException {
		try{
			jobDTO.setStatus(CommonConstDefinition.BATCH_JOB_STATUS_WAIT);
			jobDTO.setCreateOpId(getOperatorID());
			jobDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
			
			BatchJobHome jobHome=HomeLocater.getBatchJobHome();
			BatchJob job=jobHome.create(jobDTO);
			jobDTO.setBatchId(job.getBatchId().intValue());
			int recordCount=0;
			//ȡ��ָ�����Ŷ�Ӧ��cp��Ϣ��
	    	Collection custInfoCol=getMessageCustCpInfo(custBatchHeaderDTO.getBatchNo());
	    	if(!custInfoCol.isEmpty()){
	    		int orgID=BusinessUtility.getOpOrgIDByOperatorID(getOperatorID());
	    		Iterator custInfoIter=custInfoCol.iterator();
	    		while(custInfoIter.hasNext()){
	    			GehuaCustInfoForBatchMessageDTO dto=(GehuaCustInfoForBatchMessageDTO)custInfoIter.next();
	    			recordCount+=createJobItemAndLogForSuspendAndResume(dto,jobDTO,orgID);
	    		}
	    	}
	    	job.setTotoalRecordNo(recordCount);
	    	updateBatchHeadInfo(custBatchHeaderDTO.getBatchNo(),jobDTO.getBatchId());
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.ERROR,e);
			throw new ServiceException("������������ʱ��λ����");
		}catch(CreateException e){
			LogUtility.log(clazz,LogLevel.ERROR, e);
			throw new ServiceException("�������񵥴�������");
		}
    }
    /**
     * ����������ͨ����
     * @param custBatchHeaderDTO
     * @param jobDTO
     * @throws ServiceException
     */
    public  void createBatchJobForBatchResume(GeHuaUploadCustBatchHeaderDTO custBatchHeaderDTO,BatchJobDTO jobDTO) throws ServiceException {
		try{
			jobDTO.setStatus(CommonConstDefinition.BATCH_JOB_STATUS_WAIT);
			jobDTO.setCreateOpId(getOperatorID());
			jobDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
			
			BatchJobHome jobHome=HomeLocater.getBatchJobHome();
			BatchJob job=jobHome.create(jobDTO);
			jobDTO.setBatchId(job.getBatchId().intValue());
			int recordCount=0;
			//ȡ��ָ�����Ŷ�Ӧ��cp��Ϣ��
	    	Collection custInfoCol=getMessageCustCpInfo(custBatchHeaderDTO.getBatchNo());
	    	if(!custInfoCol.isEmpty()){
	    		int orgID=BusinessUtility.getOpOrgIDByOperatorID(getOperatorID());
	    		Iterator custInfoIter=custInfoCol.iterator();
	    		while(custInfoIter.hasNext()){
	    			GehuaCustInfoForBatchMessageDTO dto=(GehuaCustInfoForBatchMessageDTO)custInfoIter.next();
	    			recordCount+=createJobItemAndLogForSuspendAndResume(dto,jobDTO,orgID);
	    		}
	    	}
	    	job.setTotoalRecordNo(recordCount);
	    	updateBatchHeadInfo(custBatchHeaderDTO.getBatchNo(),jobDTO.getBatchId());
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.ERROR,e);
			throw new ServiceException("������������ʱ��λ����");
		}catch(CreateException e){
			LogUtility.log(clazz,LogLevel.ERROR, e);
			throw new ServiceException("�������񵥴�������");
		}
    }
    /**
     * ����ͷ��Ϣ
     * @param batchHeadNo
     * @param bathJobID
     * @throws ServiceException
     */
    public  void updateBatchHeadInfo(int batchHeadNo,int bathJobID) throws ServiceException {
		PreparedStatement doStat=null;
		Connection con=null;
		try {	
			String ctreateSql="Update gehua_uploadcust_batchheader Set batchid=?,dt_lastmod=SYSTIMESTAMP Where batchno=? ";
			con = DBUtil.getConnection();
			//����������
			doStat=con.prepareStatement(ctreateSql);
			doStat.setInt(1, bathJobID);
			doStat.setInt(2, batchHeadNo);
			doStat.executeUpdate();
		} catch (Exception e) {
			LogUtility.log(CustomerService.class, LogLevel.WARN,"updateBatchHeadInfo", e);
			throw new ServiceException("���������ͻ���Ϣͷ��Ϣ��������");
		} finally {
			if (doStat != null) {
				try {
					doStat.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN,
							"updateBatchHeadInfo", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN,
							"updateBatchHeadInfo", e);
				}
			}
		}
    }
    /**
     * ��������������ϸ��������־
     * @param batchID
     * @param cpList
     * @param bjDto
     * @param orgID
     * @throws ServiceException
     */
    public  int  createJobItemAndLog(GehuaCustInfoForBatchMessageDTO dto,BatchJobDTO bjDto,int orgID ) throws ServiceException {
    	Connection con = null;
		PreparedStatement preStmt = null;
		PreparedStatement preStmtlog = null;
		int recordCount=0;
		String description =bjDto.getDescription();
		if(description!=null)description=description.toUpperCase();
		//�־û�jobItem
		String sqlJobItem = "insert into t_batchjobitem" +
		"(itemno,batchid,customerid,accountid,userid,custpackageid,psid,ccid,rcddata,status,statustime,dt_create,dt_lastmod) "+
		"values(?,?,?,?,?,?,?,?,F_GETBATCHSENDMSGDESC(?,?,?),?,SYSTIMESTAMP,SYSTIMESTAMP,SYSTIMESTAMP)";
		//�־û���־
		String sqlPreStmtlog = "insert into t_BatchJobProcessLog(seqno,batchid,itemno,action,result,comments,operatorid,orgid,createtime,dt_create,dt_lastmod) "+ 
				"values(BATCHJOBPROCESSLOG_SEQNO.Nextval,?,?,?,?,?,?,?,SYSTIMESTAMP,SYSTIMESTAMP,SYSTIMESTAMP)";
    	try {
    		Collection cpDTOCol=getCPInfoByCpList(dto.getCpList());
			if(!cpDTOCol.isEmpty()){
				con = DBUtil.getConnection();
				preStmt = con.prepareStatement(sqlJobItem);
	    		preStmtlog = con.prepareStatement(sqlPreStmtlog);
				Iterator cpDTOIter=cpDTOCol.iterator();
	    		while(cpDTOIter.hasNext()){
	    			CustomerProductDTO cpdto=(CustomerProductDTO)cpDTOIter.next();
	    			int jobItemSequence=getSequenceKey("BATCHJOBITEM_ITEMNO");
					//������ϸ
	    			preStmt.setInt(1, jobItemSequence);
	    			preStmt.setInt(2, bjDto.getBatchId());
					preStmt.setInt(3, cpdto.getCustomerID());
					preStmt.setInt(4, cpdto.getAccountID());
					preStmt.setInt(5, cpdto.getServiceAccountID());
					preStmt.setInt(6, cpdto.getReferPackageID());
					preStmt.setInt(7, cpdto.getPsID());
					preStmt.setInt(8, 0);
					preStmt.setString(9, description);
					preStmt.setInt(10, cpdto.getCustomerID());
					preStmt.setInt(11, cpdto.getAccountID());
					preStmt.setString(12, bjDto.getStatus());
					preStmt.addBatch();
	    			//������־
					preStmtlog.setInt(1, bjDto.getBatchId());
					preStmtlog.setInt(2, jobItemSequence);
					preStmtlog.setString(3, CommonConstDefinition.BATCHJOBPROCESSACTION_CREATE);
					preStmtlog.setString(4, null);
					preStmtlog.setString(5, description);
					preStmtlog.setInt(6, getOperatorID());
					preStmtlog.setInt(7, orgID);
					preStmtlog.addBatch();
					recordCount++;
	    		}
				preStmt.executeBatch();
				preStmtlog.executeBatch();
			}
    	} catch (Exception e) {
			LogUtility.log(CustomerService.class, LogLevel.WARN,"createJobItemAndLog", e);
			throw new ServiceException("��������ͻ���Ϣ��������");
		} finally {
			if (preStmt != null) {
				try {
					preStmt.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN,
							"createJobItemAndLog", e);
				}
			}
			if (preStmtlog != null) {
				try {
					preStmtlog.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN,
							"createJobItemAndLog", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN,
							"createJobItemAndLog", e);
				}
			}
		}
		return recordCount;
    }
    /**
     * ��������������ϸ��������־(�����ضϺ�������ͨ)
     * @param batchID
     * @param cpList
     * @param bjDto
     * @param orgID
     * @throws ServiceException
     */
    public  int  createJobItemAndLogForSuspendAndResume(GehuaCustInfoForBatchMessageDTO dto,BatchJobDTO bjDto,int orgID ) throws ServiceException {
    	Connection con = null;
		PreparedStatement preStmt = null;
		PreparedStatement preStmtlog = null;
		int recordCount=0;
		//�־û�jobItem
		String sqlJobItem = "insert into t_batchjobitem" +
		"(itemno,batchid,customerid,accountid,userid,custpackageid,psid,ccid,rcddata,status,statustime,dt_create,dt_lastmod) "+
		"values(?,?,?,?,?,?,?,?,?,?,SYSTIMESTAMP,SYSTIMESTAMP,SYSTIMESTAMP)";
		//�־û���־
		String sqlPreStmtlog = "insert into t_BatchJobProcessLog(seqno,batchid,itemno,action,result,comments,operatorid,orgid,createtime,dt_create,dt_lastmod) "+ 
				"values(BATCHJOBPROCESSLOG_SEQNO.Nextval,?,?,?,?,?,?,?,SYSTIMESTAMP,SYSTIMESTAMP,SYSTIMESTAMP)";
    	try {
    		String[] saIDList=	dto.getServiceAccountIdList().split(",");
    		if(saIDList!=null){
				con = DBUtil.getConnection();
				preStmt = con.prepareStatement(sqlJobItem);
	    		preStmtlog = con.prepareStatement(sqlPreStmtlog);
    			for(int i=0;i<saIDList.length;i++){
    				String saID=saIDList[i];
	    			int jobItemSequence=getSequenceKey("BATCHJOBITEM_ITEMNO");
					//������ϸ
	    			preStmt.setInt(1, jobItemSequence);
	    			preStmt.setInt(2, bjDto.getBatchId());
					preStmt.setInt(3, dto.getCustomerID());
					preStmt.setInt(4, 0);
					preStmt.setInt(5, Integer.parseInt(saID));
					preStmt.setInt(6, 0);
					preStmt.setInt(7, 0);
					preStmt.setInt(8, 0);
					preStmt.setString(9, bjDto.getDescription());
					preStmt.setString(10, bjDto.getStatus());
					preStmt.addBatch();
					//������־
					preStmtlog.setInt(1, bjDto.getBatchId());
					preStmtlog.setInt(2, jobItemSequence);
					preStmtlog.setString(3, CommonConstDefinition.BATCHJOBPROCESSACTION_CREATE);
					preStmtlog.setString(4, null);
					preStmtlog.setString(5, bjDto.getDescription());
					preStmtlog.setInt(6, getOperatorID());
					preStmtlog.setInt(7, orgID);
					preStmtlog.addBatch();
					recordCount++;
    			}
				preStmt.executeBatch();
				preStmtlog.executeBatch();
			}
    	} catch (Exception e) {
			LogUtility.log(CustomerService.class, LogLevel.WARN,"createJobItemAndLog", e);
			throw new ServiceException("��������ͻ���Ϣ��������");
		} finally {
			if (preStmt != null) {
				try {
					preStmt.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN,
							"createJobItemAndLog", e);
				}
			}
			if (preStmtlog != null) {
				try {
					preStmtlog.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN,
							"createJobItemAndLog", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN,
							"createJobItemAndLog", e);
				}
			}
		}
		return recordCount;
    }
    /**
     * @param custBatchHeaderDTO
     * @throws ServiceException
     */
    public void createBatchHeaderInfo(GeHuaUploadCustBatchHeaderDTO custBatchHeaderDTO) throws ServiceException {
    	PreparedStatement batchHeaderStat=null;
		Connection con=null;
		String ctreateSql="insert into gehua_UploadCust_BatchHeader (BATCHNO, CREATEOPID, CREATEORGID, CREATETIME, BATCHID, COMMENTS,JobType, DT_CREATE, DT_LASTMOD)"+
		"values (?,?,?,?,?,?,?,?,?)";
		try {
			con = DBUtil.getConnection();
			batchHeaderStat=con.prepareStatement(ctreateSql);
			batchHeaderStat.setInt(1, custBatchHeaderDTO.getBatchNo());
			batchHeaderStat.setInt(2,custBatchHeaderDTO.getCreateOpID());
			batchHeaderStat.setInt(3, custBatchHeaderDTO.getCreateOrgID());
			batchHeaderStat.setTimestamp(4, TimestampUtility.getCurrentTimestamp());
			batchHeaderStat.setInt(5, 0);
			batchHeaderStat.setString(6, custBatchHeaderDTO.getComments());
			batchHeaderStat.setString(7, custBatchHeaderDTO.getJobType());
			batchHeaderStat.setTimestamp(8, TimestampUtility.getCurrentTimestamp());
			batchHeaderStat.setTimestamp(9, TimestampUtility.getCurrentTimestamp());
			batchHeaderStat.executeUpdate();
		} catch (Exception e) {
			LogUtility.log(CustomerService.class, LogLevel.WARN,"createBatchHeaderInfo", e);
			LogUtility.log(clazz, LogLevel.ERROR,"getCustInfo", ctreateSql);
			throw new ServiceException("��������ͻ���Ϣ��������");
		} finally {
			if (batchHeaderStat != null) {
				try {
					batchHeaderStat.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN,
							"createBatchHeaderInfo", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN,
							"createBatchHeaderInfo", e);
				}
			}
		}
	}
    /**
     * �־û��ͻ���Ϣ
     * @param messageDTO
     * @throws ServiceException
     */
    public void createMessageCustInfo(GehuaCustInfoForBatchMessageDTO messageDTO) throws ServiceException {
    	PreparedStatement messageStat=null;
		Connection con=null;
		String ctreateSql="insert into Gehua_CustInfoForBatchMessage (SEQNO, BATCHNO, CUSTOMERID, CATVID, NAME, COMMENTS, MATCHSTATUS, DEVICESERIALNOLIST, CPLIST,SERVICEACCOUNTIDLIST, DT_CREATE, DT_LASTMOD)"+
		"values (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {	

			con = DBUtil.getConnection();
			messageStat=con.prepareStatement(ctreateSql);
			messageStat.setInt(1, messageDTO.getSeqNo());
			messageStat.setInt(2, messageDTO.getBatchNo());
			messageStat.setInt(3, messageDTO.getCustomerID());
			messageStat.setString(4, messageDTO.getCATVID());
			messageStat.setString(5, messageDTO.getName());
			messageStat.setString(6, messageDTO.getComments());
			messageStat.setString(7, messageDTO.getMatchStatus());
			messageStat.setString(8, messageDTO.getDeviceSerialNoList());
			messageStat.setString(9, messageDTO.getCpList());
			messageStat.setString(10, messageDTO.getServiceAccountIdList());
			messageStat.setTimestamp(11, messageDTO.getDtCreate());
			messageStat.setTimestamp(12, messageDTO.getDtLastmod());
			messageStat.executeUpdate();
		} catch (Exception e) {
			LogUtility.log(CustomerService.class, LogLevel.WARN,"createMessageCustInfo", e);
			LogUtility.log(clazz, LogLevel.ERROR,"getCustInfo", ctreateSql);
			throw new ServiceException("����ͻ���Ϣ��������");
		} finally {
			if (messageStat != null) {
				try {
					messageStat.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN,
							"createMessageCustInfo", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN,
							"createMessageCustInfo", e);
				}
			}
		}
	}
    /**
     * �����־û������ضϵĿͻ���Ϣ
     * @param batchNo
     * @param custInfoCol
     * @param orgID
     * @throws ServiceException
     */
    public  void createBathSuspendCustInfo(int batchNo,Collection custInfoCol,int orgID) throws ServiceException {
		if(custInfoCol!=null && !custInfoCol.isEmpty()){
			Iterator custInfoIter=custInfoCol.iterator();
			while(custInfoIter.hasNext()){
				GehuaCustInfoForBatchMessageDTO dto=(GehuaCustInfoForBatchMessageDTO)custInfoIter.next();
				Collection custIDCol=getNormalCustInfo( dto, orgID);
				if(!custIDCol.isEmpty()){
					Iterator custIDIter=custIDCol.iterator();
					while(custIDIter.hasNext()){
						Integer customerID=(Integer)custIDIter.next();
						GehuaCustInfoForBatchMessageDTO newdto=copyDTO(dto);
						newdto.setCustomerID(customerID.intValue());
						List saList=getSAInfoList(customerID.intValue(),"N");
						String deviceSerialNoList="";
						String saListStr="";
						String prodDesc="";
						if(!saList.isEmpty()){
							Iterator saIterator=saList.iterator();
							boolean isFirst=true;
							while(saIterator.hasNext()){
								Integer saID=(Integer)saIterator.next();
								
								String scSerialno=getSCSerialnoBySaID(saID.intValue());	
								if(scSerialno!=null && !"".equals(scSerialno)){
									if(isFirst){
										deviceSerialNoList=scSerialno;
										saListStr=saID.toString();
										prodDesc=getCPDescInfo(saID.intValue(),"N");
										isFirst=false;
									}else{
										deviceSerialNoList+=","+scSerialno;
										saListStr+=","+saID.toString();
										prodDesc+=","+getCPDescInfo(saID.intValue(),"N");
									}
								}
							}
							if(deviceSerialNoList!=null && !"".equals(deviceSerialNoList)){
								newdto.setComments("ƥ������,�û������ƷΪ��"+prodDesc);
								newdto.setMatchStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
								newdto.setServiceAccountIdList(saListStr);
								newdto.setDeviceSerialNoList(deviceSerialNoList);
							}else{
								newdto.setComments("�ͻ�ҵ���ʻ���û�д�������״̬�����ܿ�");
								newdto.setMatchStatus(CommonConstDefinition.GENERALSTATUS_INVALIDATE);
							}
						}else{
							newdto.setComments("û�д�������״̬��ҵ���ʻ�");
							newdto.setMatchStatus(CommonConstDefinition.GENERALSTATUS_INVALIDATE);
						}
						newdto.setBatchNo(batchNo);
						newdto.setDtCreate(TimestampUtility.getCurrentTimestamp());
						newdto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
						newdto.setSeqNo(getSequenceKey("GEHUA_CUSTBATCHMESSAGE_SEQNO"));
						createMessageCustInfo(newdto);
					}
				}else{
					dto.setBatchNo(batchNo);
					dto.setComments("����ģ���û����������û��ƥ�䵽�����ͻ�");
					dto.setDtCreate(TimestampUtility.getCurrentTimestamp());
					dto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					dto.setMatchStatus(CommonConstDefinition.GENERALSTATUS_INVALIDATE);
					dto.setSeqNo(getSequenceKey("GEHUA_CUSTBATCHMESSAGE_SEQNO"));
					createMessageCustInfo(dto);
				}
			}
		}
	}
    /**
     * �����־û�������ͨ�Ŀͻ���Ϣ
     * @param batchNo
     * @param custInfoCol
     * @param orgID
     * @throws ServiceException
     */
    public  void createBathResumeCustInfo(int batchNo,Collection custInfoCol,int orgID) throws ServiceException {
		if(custInfoCol!=null && !custInfoCol.isEmpty()){
			Iterator custInfoIter=custInfoCol.iterator();
			while(custInfoIter.hasNext()){
				GehuaCustInfoForBatchMessageDTO dto=(GehuaCustInfoForBatchMessageDTO)custInfoIter.next();
				Collection custIDCol=getNormalCustInfo( dto, orgID);
				if(!custIDCol.isEmpty()){
					Iterator custIDIter=custIDCol.iterator();
					while(custIDIter.hasNext()){
						Integer customerID=(Integer)custIDIter.next();
						GehuaCustInfoForBatchMessageDTO newdto=copyDTO(dto);
						newdto.setCustomerID(customerID.intValue());
						List saList=getSAInfoList(customerID.intValue(),"H","Ƿ����ͣ");
						String deviceSerialNoList="";
						String saListStr="";
						String prodDesc="";
						if(!saList.isEmpty()){
							Iterator saIterator=saList.iterator();
							boolean isFirst=true;
							while(saIterator.hasNext()){
								Integer saID=(Integer)saIterator.next();
								String scSerialno=getSCSerialnoBySaID(saID.intValue());	
								if(scSerialno!=null && !"".equals(scSerialno)){
									if(isFirst){
										deviceSerialNoList=scSerialno;
										saListStr=saID.toString();
										prodDesc=getCPDescInfo(saID.intValue(),"H");
										isFirst=false;
									}else{
										deviceSerialNoList+=","+scSerialno;
										saListStr+=","+saID.toString();
										prodDesc+=","+getCPDescInfo(saID.intValue(),"H");
									}
								}
							}
							if(deviceSerialNoList!=null && !"".equals(deviceSerialNoList)){
								newdto.setComments("ƥ������,�û������ƷΪ��"+prodDesc);
								newdto.setMatchStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
								newdto.setServiceAccountIdList(saListStr);
								newdto.setDeviceSerialNoList(deviceSerialNoList);
							}else{
								newdto.setComments("�ͻ�ҵ���ʻ���û���ڴ�������״̬�����ܿ�");
								newdto.setMatchStatus(CommonConstDefinition.GENERALSTATUS_INVALIDATE);
							}
						}else{
							
							newdto.setComments("û�д���Ƿ�ѹض�״̬��ҵ���ʻ�");
							newdto.setMatchStatus(CommonConstDefinition.GENERALSTATUS_INVALIDATE);
						}
						newdto.setBatchNo(batchNo);
						newdto.setDtCreate(TimestampUtility.getCurrentTimestamp());
						newdto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
						newdto.setSeqNo(getSequenceKey("GEHUA_CUSTBATCHMESSAGE_SEQNO"));
						createMessageCustInfo(newdto);
					}
				}else{
					dto.setBatchNo(batchNo);
					dto.setComments("����ģ���û����������û��ƥ�䵽�����ͻ�");
					dto.setDtCreate(TimestampUtility.getCurrentTimestamp());
					dto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					dto.setMatchStatus(CommonConstDefinition.GENERALSTATUS_INVALIDATE);
					dto.setSeqNo(getSequenceKey("GEHUA_CUSTBATCHMESSAGE_SEQNO"));
					createMessageCustInfo(dto);
				}
			}
		}
	}
    /**
     * �����־û��ͻ���Ϣ
     * @param batchNo
     * @param custInfoCol
     * @param orgID
     * @throws ServiceException
     */
    public  void createBathMessageCustInfo(int batchNo,Collection custInfoCol,int orgID) throws ServiceException {
		if(custInfoCol!=null && !custInfoCol.isEmpty()){
			Iterator custInfoIter=custInfoCol.iterator();
			while(custInfoIter.hasNext()){
				GehuaCustInfoForBatchMessageDTO dto=(GehuaCustInfoForBatchMessageDTO)custInfoIter.next();
				Collection custIDCol=getCustInfo( dto, orgID);
				if(!custIDCol.isEmpty()){
					Iterator custIDIter=custIDCol.iterator();
					while(custIDIter.hasNext()){
						Integer customerID=(Integer)custIDIter.next();
						GehuaCustInfoForBatchMessageDTO newdto=copyDTO(dto);
						newdto.setCustomerID(customerID.intValue());
						HashMap cpMap= getCPInfo(customerID.intValue());
						if(!cpMap.isEmpty()){
							String deviceSerialNoList="";
							String  cpList="";
							Iterator cpIter=cpMap.keySet().iterator();
							boolean isFirst=true;
							while(cpIter.hasNext()){
								String serialno=(String)cpIter.next();
								if(isFirst){
									deviceSerialNoList=serialno;
									cpList=cpMap.get(serialno).toString();
									isFirst=false;
								}else{
									deviceSerialNoList+=","+serialno;
									cpList+=","+cpMap.get(serialno).toString();
								}
							}
							newdto.setComments("ƥ������");
							newdto.setMatchStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
							newdto.setCpList(cpList);
							newdto.setDeviceSerialNoList(deviceSerialNoList);
						}else{
							newdto.setComments("û�д�������״̬�����ܿ�");
							newdto.setMatchStatus(CommonConstDefinition.GENERALSTATUS_INVALIDATE);
						}
						newdto.setBatchNo(batchNo);
						newdto.setDtCreate(TimestampUtility.getCurrentTimestamp());
						newdto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
						newdto.setSeqNo(getSequenceKey("GEHUA_CUSTBATCHMESSAGE_SEQNO"));
						createMessageCustInfo(newdto);
					}
				}else{
					dto.setBatchNo(batchNo);
					dto.setComments("����ģ���û����������û��ƥ�䵽�����ͻ�");
					dto.setDtCreate(TimestampUtility.getCurrentTimestamp());
					dto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					dto.setMatchStatus(CommonConstDefinition.GENERALSTATUS_INVALIDATE);
					dto.setSeqNo(getSequenceKey("GEHUA_CUSTBATCHMESSAGE_SEQNO"));
					createMessageCustInfo(dto);
				}
			}
		}
			
	}
    /**
     * ����GehuaCustInfoForBatchMessageDTO��Ϣ����
     * @param dto
     * @return
     */
    private GehuaCustInfoForBatchMessageDTO copyDTO(GehuaCustInfoForBatchMessageDTO dto){
    	GehuaCustInfoForBatchMessageDTO newDTO=new GehuaCustInfoForBatchMessageDTO();
    	newDTO.setBatchNo(dto.getBatchNo());
    	newDTO.setCATVID(dto.getCATVID());
    	newDTO.setCustomerID(dto.getCustomerID());
    	newDTO.setName(dto.getName());
    	//newDTO.setDtCreate(dto.getDtCreate());
    	//newDTO.setDtLastmod(dto.getDtLastmod());
    	//newDTO.setComments(dto.getComments());
    	//newDTO.setCpList(dto.getCpList());
    	//newDTO.setDeviceSerialNoList(dto.getDeviceSerialNoList());
    	//newDTO.setMatchStatus(dto.getMatchStatus());
    	//newDTO.setSeqNo(dto.getSeqNo());
    	return newDTO;
    	
    }
    
	/**
	 * ����������ģ���û����룬����Ա�����ɹ���ͻ�����֯����id
	 * @param dto
	 * @param pareHasCustOrgID
	 * @return
	 * @throws ServiceException
	 */
	public  Collection  getCustInfo(GehuaCustInfoForBatchMessageDTO dto,int pareHasCustOrgID) throws ServiceException {
		Collection custIdCol=new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "Select cust.customerid From t_customer cust ,t_address addr ,(select Id from T_DISTRICTSETTING connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid= ?)) districtview Where  cust.addressid=addr.addressid  And addr.districtid=districtview.Id    And cust.status<>'C' and  cust.catvid=? And cust.name=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, pareHasCustOrgID);
			stmt.setString(2, dto.getCATVID());
			stmt.setString(3, dto.getName());
			rs = stmt.executeQuery();
			while (rs.next()) {
				custIdCol.add(new Integer(rs.getInt("customerid")));
			}
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR,"getCustInfo", e);
			LogUtility.log(clazz, LogLevel.ERROR,"getCustInfo", sql);
			throw new  ServiceException("ȡ���ݴ���");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return custIdCol;
	}
	
	public  Collection  getNormalCustInfo(GehuaCustInfoForBatchMessageDTO dto,int pareHasCustOrgID) throws ServiceException {
		Collection custIdCol=new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "Select cust.customerid From t_customer cust ,t_address addr ,(select Id from T_DISTRICTSETTING connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid= ?)) districtview Where  cust.addressid=addr.addressid  And addr.districtid=districtview.Id    And cust.status='N' and  cust.catvid=? And cust.name=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, pareHasCustOrgID);
			stmt.setString(2, dto.getCATVID());
			stmt.setString(3, dto.getName());
			rs = stmt.executeQuery();
			while (rs.next()) {
				custIdCol.add(new Integer(rs.getInt("customerid")));
			}
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR,"getCustInfo", e);
			LogUtility.log(clazz, LogLevel.ERROR,"getCustInfo", sql);
			throw new  ServiceException("ȡ���ݴ���");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return custIdCol;
	}
	
	/**
	 * ���ݿͻ�id
	 * @param dto
	 * @param pareHasCustOrgID
	 * @return
	 * @throws ServiceException
	 */
	public HashMap  getCPInfo(int customerID) throws ServiceException {
		HashMap map =new LinkedHashMap();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "Select cp.psid ,device.serialno From  t_customerproduct  cp  ,t_terminaldevice  device Where cp.status='N' And  cp.deviceid=device.deviceid And device.deviceclass='SC'  And  cp.customerid= ?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, customerID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				map.put(rs.getString("serialno"),new Integer(rs.getInt("psid")));
			}
			rs.close();
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR,"getCPInfo", e);
			throw new  ServiceException("ȡ���ݴ���");
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return map;
	}
	/**
	 * ����ҵ���ʻ�id�Ϳͻ���Ʒ��״̬ȡ�ò�Ʒ������Ϣ
	 * @param dto
	 * @param pareHasCustOrgID
	 * @return
	 * @throws ServiceException
	 */
	public String  getCPDescInfo(int saID,String status) throws ServiceException {
		StringBuffer infoBuffer =new StringBuffer();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = " Select prod.productname From t_product prod ,t_customerproduct cp Where prod.productid=cp.productid And  cp.serviceaccountid=? And cp.status=? And cp.DEVICEID=0";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, saID);
			stmt.setString(2, status);
			rs = stmt.executeQuery();
			infoBuffer.append(saID).append("{");
			boolean isFirest=true;
			while (rs.next()) {
				if(isFirest){
					infoBuffer.append(rs.getString("productname"));
					isFirest=false;
				}else{
					infoBuffer.append(",");
					infoBuffer.append(rs.getString("productname"));
				}
			}
			infoBuffer.append("}");
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR,"getCPDescInfo", e);
			throw new  ServiceException("ȡ���ݴ���");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return infoBuffer.toString();
	}
	/**
	 * ����ҵ���ʻ�idȡ�����ܿ���
	 * @param dto
	 * @param pareHasCustOrgID
	 * @return
	 * @throws ServiceException
	 */
	public String  getSCSerialnoBySaID(int SaID) throws ServiceException {
		String scSerialno =null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "Select device.serialno From  t_customerproduct  cp  ,t_terminaldevice  device Where cp.status='N' And  cp.deviceid=device.deviceid And device.deviceclass='SC'  And  cp.serviceaccountid= ?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, SaID);
			rs = stmt.executeQuery();
			if (rs.next()) {
				scSerialno=rs.getString("serialno");
			}
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR,"getCPInfo", e);
			throw new  ServiceException("ȡ���ݴ���");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return scSerialno;
	}
	/**
	 * ���ݿͻ�id��״̬ȡ�ö�Ӧ���û�����
	 * @param dto
	 * @param pareHasCustOrgID
	 * @return
	 * @throws ServiceException
	 */
	public List  getSAInfoList(int customerID,String status) throws ServiceException {
		List saList =new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "Select Serviceaccountid From t_serviceaccount  Where customerid=? And status=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, customerID);
			stmt.setString(2, status);
			rs = stmt.executeQuery();
			while (rs.next()) {
				saList.add(new Integer(rs.getInt("Serviceaccountid")));
			}
			rs.close();
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR,"getCPInfo", e);
			throw new  ServiceException("ȡ���ݴ���");
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return saList;
	}
	/**
	 * ���ݿͻ�id��״̬ȡ�ö�Ӧ���û�����
	 * @param dto
	 * @param pareHasCustOrgID
	 * @return
	 * @throws ServiceException
	 */
	public List  getSAInfoList(int customerID,String status,String desc) throws ServiceException {
		List saList =new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "Select Serviceaccountid From t_serviceaccount  Where customerid=? And status=? and DESCRIPTION=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, customerID);
			stmt.setString(2, status);
			stmt.setString(3, desc);
			rs = stmt.executeQuery();
			while (rs.next()) {
				saList.add(new Integer(rs.getInt("Serviceaccountid")));
			}
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR,"getCPInfo", e);
			throw new  ServiceException("ȡ���ݴ���");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return saList;
	}
	/**
	 * @param cpList
	 * @return
	 * @throws ServiceException
	 */
	public Collection  getCPInfoByCpList(String cpList) throws ServiceException {
		Collection custProdCol=new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "Select cp.*  From  t_customerproduct  cp  Where cp.psid in("+cpList+")";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				custProdCol.add(DtoFiller.fillCustomerProductDTO(rs));
			}
			
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR,"getCPInfoByCpList", e);
			throw new  ServiceException("ȡ���ݴ���");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return custProdCol;
	}
	/**
	 * ��������ȡ�ö�Ӧ�ͻ���Ϣ�еĲ�Ʒ�����û���Ϣ
	 * @param dto
	 * @param pareHasCustOrgID
	 * @return
	 * @throws ServiceException
	 */
	public Collection  getMessageCustCpInfo(int batchNo) throws ServiceException {
		Collection recordCol=new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "Select * From gehua_CustInfoForBatchMessage custInfo Where Matchstatus='V' and  custInfo.Batchno=? ";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, batchNo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				GehuaCustInfoForBatchMessageDTO dto=new GehuaCustInfoForBatchMessageDTO();
				dto.setSeqNo(rs.getInt("Seqno"));
				dto.setCpList(rs.getString("Cplist"));
				dto.setDeviceSerialNoList(rs.getString("DEVICESERIALNOLIST"));
				dto.setServiceAccountIdList(rs.getString("SERVICEACCOUNTIDLIST"));
				dto.setCustomerID(rs.getInt("CUSTOMERID"));
				recordCol.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR,"getMessageCustCpInfo", e);
			throw new  ServiceException("ȡ���ݴ���");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return recordCol;
	}
	/**
	 * @return
	 * @throws ServiceException
	 */
	public  int getSequenceKey(String sequence) throws ServiceException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int ID=0;
		String sql = "Select "+sequence+".Nextval  ID From dual";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				ID=rs.getInt("ID");
			}
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR,"getSequenceKey", e);
			LogUtility.log(clazz, LogLevel.ERROR,"getSequenceKey", sql);
			throw new ServiceException("ȡIDʱ��������");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return ID;
	}
}
