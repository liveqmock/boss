/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.component;

import javax.ejb.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.log.*;
import com.dtv.oss.dto.*;
import com.dtv.oss.domain.*;
import com.dtv.oss.service.util.*;
import com.dtv.oss.service.factory.*;
import com.dtv.oss.util.*;
public class CatvService extends AbstractService {
    private static final Class clazz = CatvService.class;
    private ServiceContext serviceContext = null;
    
    public CatvService() {
    }
    public CatvService(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
        setOperatorID(PublicService.getCurrentOperatorID(serviceContext));
    }

    public CatvTerminal createCatvTerminal(CatvTerminalDTO ctDto) throws ServiceException{
    	CatvTerminal terminal=null;
    	CatvTerminalHome ctHome;
    	try {
			ctHome = HomeLocater.getCatvTerminalHome();
			terminal= ctHome.create(ctDto);
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.DEBUG, "创建终端环境错误:" + e.getMessage());
			throw new ServiceException("创建终端环境错误.");
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.DEBUG, "创建终端环境错误:" + e.getMessage());
			throw new ServiceException("创建终端环境错误.");
		}
    	return terminal;
    }
    public long constructionBatchImportWithJDBC(
			ConstructionBatchDTO cbDto, Collection ConstructionBatchItems) throws ServiceException{
    	Timestamp curDate=TimestampUtility.getCurrentTimestamp();
		cbDto.setCreateOperatorId(getOperatorID());
		cbDto.setCreateOrgId(BusinessUtility.getOpOrgIDByOperatorID(getOperatorID()));
		cbDto.setStatus(CommonConstDefinition.CONSTRUCTION_STATUS_NEW);
		cbDto.setCreateDate(curDate);
		cbDto.setNewHouseNumber(ConstructionBatchItems.size());
		
		//hu jinpeng begin for ConstructionBatchDTO parameter 2007.08.13
		cbDto.setDtCreate(new Timestamp(System.currentTimeMillis()));
		cbDto.setDtLastmod(new Timestamp(System.currentTimeMillis()));
		//hu jinpeng end for ConstructionBatchDTO parameter
		
    	Connection conn=DBUtil.getConnection();
    	PreparedStatement cbPs=null,cbiPs=null,terPs=null;
    	ResultSet idRs=null;
    	long cbId=0;
		try {
			java.sql.Statement s=conn.createStatement();
	    	idRs=s.executeQuery("select constructionbatch_batchid.nextval from dual");
	    	if(idRs.next()){
	    		cbId=idRs.getLong(1);
	    	}
			String cbSql = "INSERT INTO T_CONSTRUCTIONBATCH ( BATCHID,"
					+ " REFERSHEETNO, INPUTFILENAME, CREATEDATE, CREATEOPERATORID,"
					+ " CREATEORGID, BUILDERNAME, POSTCODE, CONSTRUCTIONNAME,"
					+ " DISTRICTID, ADDRESSPREFIX, TOTALHOUSENUMBER, NEWHOUSENUMBER,"
					+ " NEWPORTNUMBER, CATVTERMSTATUS, STATUS, DESCRIPTION,"
					+ " DT_CREATE, DT_LASTMOD, CABLETYPE, BIDIRECTIONFLAG,"
					+ " CATVTERMTYPE, FIBERNODEID )"
					+ " VALUES (?,"
					+"?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?)";
			cbPs = conn.prepareStatement(cbSql);
			cbPs.setLong(1, cbId);
			cbPs.setString(2, cbDto.getReferSheetNo());
			cbPs.setString(3, cbDto.getInputFileName());
			cbPs.setTimestamp(4, curDate);
			cbPs.setInt(5, cbDto.getCreateOperatorId());
			cbPs.setInt(6, cbDto.getCreateOrgId());
			cbPs.setString(7, cbDto.getBuilderName());
			cbPs.setString(8, cbDto.getPostCode());
			cbPs.setString(9, cbDto.getConstructionName());
			cbPs.setInt(10, cbDto.getDistrictId());
			cbPs.setString(11, cbDto.getAddressPrefix());
			cbPs.setInt(12, cbDto.getTotalHouseNumber());
			cbPs.setInt(13, cbDto.getNewHouseNumber());
//			cbPs.setInt(14, cbDto.getNewPortNumber());
			cbPs.setString(15, cbDto.getCatvTermStatus());
			cbPs.setString(16, cbDto.getStatus());
			cbPs.setString(17, cbDto.getDescription());
			cbPs.setTimestamp(18, cbDto.getDtCreate());
			cbPs.setTimestamp(19, cbDto.getDtLastmod());
			cbPs.setString(20, cbDto.getCableType());
			cbPs.setString(21, cbDto.getBiDirectionFlag());
			cbPs.setString(22, cbDto.getCatvTermType());
			cbPs.setInt(23, cbDto.getFiberNodeId());

			String terSql="insert into T_CATVTerminal ("
				+"ID,ConBatchID,RecordSource,Status,"
				+"PostCode,DistrictID,DetailAddress,PortNo,"
				+"FiberNodeID,CableType,BiDirectionFlag,CreateDate,"
				+"Comments,CatvTermType,dt_create,dt_lastmod,"
				+"ACTIVEDATE"
				+")values(?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?)";
			terPs=conn.prepareStatement(terSql);
			
			String cbiSql="insert into T_ConstructionBatchItem ("
				+"ItemNo,BatchID,DetailAddress,PortNumber,"
				+"CreateDate,CreateOperatorID,CreateOrgID,Status,"
				+"CATVID,Comments,DT_Create,DT_LastMod)"
				+"values(ConstructionBatchItem_ID.nextval," 
				+"?,?,?, ?,?,?,?, ?,?,?,?)";
			
			cbiPs=conn.prepareStatement(cbiSql);

			Iterator cbiIt=ConstructionBatchItems.iterator();
			int newPortNumber=0;
			while(cbiIt.hasNext()){
				ConstructionBatchItemDTO cbiDto=(ConstructionBatchItemDTO) cbiIt.next();
				newPortNumber+=cbiDto.getPortNumber();
				cbiDto.setBatchId((int)cbId);
				cbiDto.setCreatedate(curDate);
				cbiDto.setCreateOperatorId(cbDto.getCreateOperatorId());
				cbiDto.setCreateOrgId(cbDto.getCreateOrgId());
				cbiDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
				
		    	String terId=cbiDto.getCatvId();
		    	
		    	if(terId==null||"".equals(terId.trim())){
		    		terId=BusinessUtility.getCatvID(cbDto.getDistrictId());
		    	}
//		    	LogUtility.log(clazz,LogLevel.DEBUG,"T_CATVTerminal.id:",terId);
		    	
		    	terPs.clearParameters();
				terPs.setString(1, terId);
				terPs.setLong(2, (int)cbId);
				terPs.setString(3, CommonConstDefinition.CATV_TERMINAL_RECORDSOURCE_I);
				terPs.setString(4,cbDto.getCatvTermStatus());
				terPs.setString(5, cbDto.getPostCode());
				terPs.setInt(6, cbDto.getDistrictId());
				terPs.setString(7,cbiDto.getDetailAddress());
				terPs.setInt(8,cbiDto.getPortNumber());
				terPs.setInt(9,cbDto.getFiberNodeId());
				terPs.setString(10,cbDto.getCableType());
				terPs.setString(11,cbDto.getBiDirectionFlag());
				terPs.setTimestamp(12,curDate);
				terPs.setString(13,cbiDto.getComments());
				terPs.setString(14,cbDto.getCatvTermType());
				terPs.setTimestamp(15,curDate);
				terPs.setTimestamp(16,curDate);
				if(CommonConstDefinition.CATV_TERMINAL_STATUS_N.equals(cbDto.getCatvTermStatus())){
					terPs.setTimestamp(17, curDate);
				}else{
					terPs.setTimestamp(17, null);
				}
				terPs.addBatch();
				
				cbiPs.clearParameters();
				cbiPs.setLong(1,cbId);
				cbiPs.setString(2,cbiDto.getDetailAddress());
				cbiPs.setInt(3,cbiDto.getPortNumber());
				cbiPs.setTimestamp(4,curDate);
				cbiPs.setInt(5,cbDto.getCreateOperatorId());
				cbiPs.setInt(6,cbDto.getCreateOrgId());
				cbiPs.setString(7,CommonConstDefinition.GENERALSTATUS_VALIDATE);
				cbiPs.setString(8,terId);
				cbiPs.setString(9,cbiDto.getComments());
				cbiPs.setTimestamp(10,curDate);
				cbiPs.setTimestamp(11,curDate);
				cbiPs.addBatch();
			}
			cbDto.setNewPortNumber(newPortNumber);
			cbPs.setInt(14, cbDto.getNewPortNumber());
			cbPs.execute();
			terPs.executeBatch();
			cbiPs.executeBatch();
			LogUtility.log(clazz,LogLevel.DEBUG,"ConstructionBatchDTO:",cbDto);
			
		} catch (Exception e) {
			LogUtility.log(this.getClass(), LogLevel.DEBUG, e.getMessage());
			throw new ServiceException(e.getMessage());
		}finally{
				LogUtility.log(this.getClass(), LogLevel.DEBUG, "close>>>>>>>>>>>>>>>>>>>>>>");
			try {
				cbiPs.close();
				terPs.close();
				cbPs.close();
				idRs.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
				LogUtility.log(this.getClass(), LogLevel.DEBUG, e.getMessage());
			}	    	
		}
		return cbId;
	}
    
    
    public void createCatvProcess(CatvTermProcessDTO catvTermProcessDTO,Collection catvTermProcessItemDTOList) throws ServiceException{
    	LogUtility.log(this.getClass(), LogLevel.DEBUG, "createCatvProcess,创建终端处理记录");
    	if(catvTermProcessDTO==null || catvTermProcessItemDTOList==null || catvTermProcessItemDTOList.size()==0)
    		return;
    	
    	try {
    		//创建头记录
			CatvTermProcessHome catvTermProcessHome=HomeLocater.getCatvTermProcessHome();
			CatvTermProcess catvTermProcess=catvTermProcessHome.create(catvTermProcessDTO);
			
			CatvTermProcessItemHome catvTermProcessItemHome=HomeLocater.getCatvTermProcessItemHome();
			//创建明细记录
			Iterator itItem=catvTermProcessItemDTOList.iterator();
			while(itItem.hasNext()){
				CatvTermProcessItemDTO catvTermProcessItemDTO=(CatvTermProcessItemDTO)itItem.next();
				catvTermProcessItemDTO.setBatchId(catvTermProcess.getBatchId().intValue());
				catvTermProcessItemHome.create(catvTermProcessItemDTO);
			}
		} 
    	catch (Exception e) {	
			LogUtility.log(this.getClass(), LogLevel.DEBUG, e);
			throw new ServiceException("创建终端处理记录信息出错");
		}	
    }

}
