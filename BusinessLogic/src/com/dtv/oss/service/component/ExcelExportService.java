/**
 * 
 */
package com.dtv.oss.service.component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Iterator;

import com.dtv.oss.dto.ExportCustomerDetailDTO;
import com.dtv.oss.dto.ExportCustomerHeadDTO;
import com.dtv.oss.dto.FoundCustomerDetaiDTO;
import com.dtv.oss.dto.FoundCustomerHeadDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.util.TimestampUtility;

/**
 * @author 240910y
 *
 */
public class ExcelExportService extends AbstractService {

	/**
	 * 
	 */
	public ExcelExportService() {
		// TODO Auto-generated constructor stub
	}
    private static final Class clazz = ExcelExportService.class;
    private ServiceContext serviceContext = null;
    
    /**
     * constructer method
     * @param context
     */
    public ExcelExportService(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
        setOperatorID(PublicService.getCurrentOperatorID(serviceContext));
    }
    
    /**
     * 用于上传文件时处理模拟建档客户信息持久化
     * @param custBatchHeaderDTO
     * @param custInfoCol
     * @throws ServiceException
     */
    public  int uploadForFoundCustomer(FoundCustomerHeadDTO headDto,Collection detailCol) throws ServiceException {
    	int batchNo=getSequenceKey("FOUNDCUSTOMER_BATCHNO");
    	headDto.setBatchNo(batchNo);
    	headDto.setCreateopid(getOperatorID());
    	int orgID=BusinessUtility.getParentHasCustomerOrgID(BusinessUtility.getOpOrgIDByOperatorID(getOperatorID()));
    	headDto.setCreateorgid(orgID) ;
    	headDto.setStatus("初始");
    	checkRepeatFoundCustomerSubmit(detailCol);
    	//创建模拟建档客户头信息
    	createFoundCustomerHead(headDto);
    	//批量模拟建档客户明细
    	createFoundCustomerDetail(batchNo,detailCol);
    	return batchNo;
    }
    
    public int uploadForEexportCustomer(ExportCustomerHeadDTO headDto,Collection detailCol) throws ServiceException {
    	int batchNo=getSequenceKey("EXPORTCUSTOMER_BATCHNO");
    	headDto.setBatchNo(batchNo);
    	headDto.setCreateOpid(getOperatorID());
    	int orgID=BusinessUtility.getParentHasCustomerOrgID(BusinessUtility.getOpOrgIDByOperatorID(getOperatorID()));
    	headDto.setCreateOrgid(orgID) ;
    	headDto.setStatus("N");
    	//创建整转客户头信息
    	createExportCustomerHead(headDto); 
    	//创建整转客户明细
    	createExportCustomerDetail(batchNo,detailCol);
    	return batchNo;
    }
    
    
    private void checkRepeatFoundCustomerSubmit(Collection detailCol) throws ServiceException{
    	Iterator detailItr =detailCol.iterator();
    	String repeatContext ="";
        int repeatCount =0;
    	while (detailItr.hasNext()){
    		repeatCount =repeatCount+1;
    		FoundCustomerDetaiDTO detailDto =(FoundCustomerDetaiDTO)detailItr.next();
    		if (repeatContext.equals("")){
    		   repeatContext ="'"+detailDto.getName()
    		                 +"--"+detailDto.getDistrinctId()
    		                 +"--"+detailDto.getDetailAddress()+"'";
    		}else{
    			repeatContext =repeatContext+","+"'"+detailDto.getName()
                                                    +"--"+detailDto.getDistrinctId()
                                                    +"--"+detailDto.getDetailAddress()+"'";
    		}
    		if (repeatCount >=20) break;
    	}
    	
    	String sql =" select count(*) existCount from t_foundcustomerdetail t "
                   +" where (t.name || '--' || t.distrinctid || '--' || t.detailaddress) "
                   +" in ("+repeatContext+") and t.status !='初始'" ;
    	
    	Statement stmt = null;
		Connection con=null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt=con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()){
				int existCount =rs.getInt("existCount");
				System.out.println("existCount=========="+existCount);
				System.out.println("repeatCount========="+repeatCount);
				if (existCount >=repeatCount)
					throw new ServiceException("该文件可能已经导入过，请检查！");
			}
		} catch (SQLException e) {
			    e.printStackTrace();
				LogUtility.log(clazz, LogLevel.ERROR, sql);
				throw new ServiceException("批量导入建档用户检查重复项发生错误。");
	    } finally {
	    	if (rs !=null ){
	    		try{
	    			rs.close();
	    		} catch(SQLException e){
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
    }
    
    private void createExportCustomerHead(ExportCustomerHeadDTO headDto) throws ServiceException {
    	PreparedStatement batchHeaderStat=null;
		Connection con=null;
		String ctreateSql=" insert into T_EXPORTCUSTOMERHEAD( "
                         +" BATCHNO ,COMMENTS ,CREATEOPID , CREATEORGID ,CREATETIME ,STATUS "
                         +" ) values (?,?,?,?,?,?)";
		try {
			con = DBUtil.getConnection();
			batchHeaderStat=con.prepareStatement(ctreateSql);
			batchHeaderStat.setInt(1, headDto.getBatchNo());
			batchHeaderStat.setString(2,headDto.getComments());
			batchHeaderStat.setInt(3, headDto.getCreateOpid());
			batchHeaderStat.setInt(4, headDto.getCreateOrgid());
			batchHeaderStat.setTimestamp(5, TimestampUtility.getCurrentTimestamp());
			batchHeaderStat.setString(6, headDto.getStatus());
			batchHeaderStat.executeUpdate();
		} catch (Exception e) {
			LogUtility.log(CustomerService.class, LogLevel.WARN,"createBatchHeaderInfo", e);
			LogUtility.log(clazz, LogLevel.ERROR,"getCustInfo", ctreateSql);
			throw new ServiceException("批量导入整转用户头信息发生错误。");
		} finally {
			if (batchHeaderStat != null) {
				try {
					batchHeaderStat.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN, e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN, e);
				}
			}
		}
	}
    
    private void createFoundCustomerHead(FoundCustomerHeadDTO headDto) throws ServiceException {
    	PreparedStatement batchHeaderStat=null;
		Connection con=null;
		String ctreateSql=" insert into T_FOUNDCUSTOMERHEAD( "
                         +" BATCHNO ,COMMENTS ,CREATEOPID , CREATEORGID ,CREATETIME ,STATUS "
                         +" ) values (?,?,?,?,?,?)";
		try {
			con = DBUtil.getConnection();
			batchHeaderStat=con.prepareStatement(ctreateSql);
			batchHeaderStat.setInt(1, headDto.getBatchNo());
			batchHeaderStat.setString(2,headDto.getComments());
			batchHeaderStat.setInt(3, headDto.getCreateopid());
			batchHeaderStat.setInt(4, headDto.getCreateorgid());
			batchHeaderStat.setTimestamp(5, TimestampUtility.getCurrentTimestamp());
			batchHeaderStat.setString(6, headDto.getStatus());
			batchHeaderStat.executeUpdate();
		} catch (Exception e) {
			LogUtility.log(CustomerService.class, LogLevel.WARN,"createBatchHeaderInfo", e);
			LogUtility.log(clazz, LogLevel.ERROR,"getCustInfo", ctreateSql);
			throw new ServiceException("批量导入建档用户头信息发生错误。");
		} finally {
			if (batchHeaderStat != null) {
				try {
					batchHeaderStat.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN, e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN, e);
				}
			}
		}
	}
    
    private void createExportCustomerDetail(int batchNo,Collection detailCol) throws ServiceException {
    	PreparedStatement preStmt =null;
		Connection con=null;
		String ctreateSql=" insert into T_EXPORTCUSTOMERDETAIL( "
				         +" BATCHNO , "
				         +" CATVID , "
				         +" NAME , "
				         +" DISTRINCTID , "
				         +" DETAILADDRESS , "
				         +" CUSTOMERTYPE , "
				         +" SERIALNO , "
				         +" FFCREATETIME , "
				         +" TEL , "
				         +" CARDID , "
				         +" COMMENTS, "
				         +" DT_CREATETIME, "
				         +" DT_LASTMOD) "
				         +" values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	    try {	
		    con = DBUtil.getConnection();
			preStmt =con.prepareStatement(ctreateSql);
			Iterator detailItr =detailCol.iterator();
			while (detailItr.hasNext()){
				ExportCustomerDetailDTO  detailDto =(ExportCustomerDetailDTO)detailItr.next();
				preStmt.setInt(1, batchNo);
				preStmt.setString(2, detailDto.getCatvid());
				preStmt.setString(3, detailDto.getName());
				preStmt.setInt(4, detailDto.getDistrinctId());
				preStmt.setString(5, detailDto.getDetailAddress());
				preStmt.setString(6, detailDto.getCustomerType());
				preStmt.setString(7, detailDto.getSerialNo());
				preStmt.setDate(8,java.sql.Date.valueOf(f.format(detailDto.getFfcreateTime())));
				preStmt.setString(9, detailDto.getTel());
				preStmt.setString(10,detailDto.getCardID());
				preStmt.setString(11,detailDto.getComments());
				preStmt.setTimestamp(12, TimestampUtility.getCurrentTimestamp());
				preStmt.setTimestamp(13, TimestampUtility.getCurrentTimestamp());
				preStmt.addBatch();
			}
			preStmt.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
			LogUtility.log(CustomerService.class, LogLevel.WARN,"createMessageCustInfo", e);
			throw new ServiceException("批量导入整转客户明细发生错误。");
		} finally {
			if (preStmt != null) {
				try {
					preStmt.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN, e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN, e);
				}
			}
		}
    }
    
    /**
     * 持久化模拟建档客户信息
     * @param messageDTO
     * @throws ServiceException
     */
    private void createFoundCustomerDetail(int batchNo,Collection detailCol) throws ServiceException {
    	PreparedStatement preStmt =null;
		Connection con=null;
		String ctreateSql=" insert into T_FOUNDCUSTOMERDETAIL( "
				         +" BATCHNO , "
				         +" NAME  , "
				         +" DISTRINCTID ,"
				         +" DETAILADDRESS ,"
				         +" SERVICEACCOUNTCOUNT ,"
				         +" CUSTOMERTYPE ,"
				         +" PRODUCTID , "
				         +" INSTALLTIME, "
			       	     +" CREATETIME ,"
				         +" ONCEPAYMONEY ,"
				         +" PREPAYMONEY ,"
				         +" TEL ,"
				         +" CARDID ,"
				         +" POSTERCODE ,"
				         +" STATUS ,"
				         +" COMMENTS ,"
				         +" DT_CREATETIME ) "
				         +" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		try {	
			con = DBUtil.getConnection();
			preStmt =con.prepareStatement(ctreateSql);
			Iterator detailItr =detailCol.iterator();
			while (detailItr.hasNext()){
				FoundCustomerDetaiDTO detailDto =(FoundCustomerDetaiDTO)detailItr.next();
				preStmt.setInt(1, batchNo);
				preStmt.setString(2, detailDto.getName());
				preStmt.setInt(3, detailDto.getDistrinctId());
				preStmt.setString(4, detailDto.getDetailAddress());
				preStmt.setInt(5, detailDto.getServiceAccountCount());
				preStmt.setString(6, detailDto.getCustomerType());
				preStmt.setInt(7, detailDto.getProductId());
				preStmt.setDate(8,java.sql.Date.valueOf(f.format(detailDto.getInstallTime())));
				preStmt.setDate(9,java.sql.Date.valueOf(f.format(detailDto.getCreateTime())));
				preStmt.setFloat(10, detailDto.getOncepaymoney());
				preStmt.setFloat(11, detailDto.getPrePaymoney());
				preStmt.setString(12, detailDto.getTel());
				preStmt.setString(13, detailDto.getCardId());
				preStmt.setString(14, detailDto.getPosterCode());
				preStmt.setString(15, detailDto.getStatus());
				preStmt.setString(16, detailDto.getComments());
				preStmt.setTimestamp(17, TimestampUtility.getCurrentTimestamp());
				preStmt.addBatch();
			}
			preStmt.executeBatch();
		} catch (Exception e) {
			LogUtility.log(CustomerService.class, LogLevel.WARN,"createMessageCustInfo", e);
			LogUtility.log(clazz, LogLevel.ERROR,"getCustInfo", ctreateSql);
			throw new ServiceException("导入建档客户明细发生错误。");
		} finally {
			if (preStmt != null) {
				try {
					preStmt.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN, e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(CustomerService.class, LogLevel.WARN, e);
				}
			}
		}
	}
  
	/**
	 * @return
	 * @throws ServiceException
	 */
	private  int getSequenceKey(String sequence) throws ServiceException{
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
			throw new ServiceException("取ID时发生错误");
		} finally {
			if (rs !=null ){
	    		try{
	    			rs.close();
	    		} catch(SQLException e){
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
	
	public void createFoundCustomerAmsData(int batchNo)  throws ServiceException{
		Connection con = DBUtil.getConnection();
		java.sql.CallableStatement cs=null;
		int retFromSP=0;
		String retDescFromSP="";
		try {
			cs=con.prepareCall("{call foundCustomertoAmsData(?,?,?)}");
			cs.setInt(1, batchNo);
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			
			retFromSP = cs.getInt(2);
			retDescFromSP = cs.getString(3);
			if (retFromSP !=0) {
				throw new ServiceException(retDescFromSP);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}finally{
			if (cs != null) {
				try {
					cs.close();
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
	
	public void createExportCustomerData(int batchNo)  throws ServiceException{
		Connection con = DBUtil.getConnection();
		java.sql.CallableStatement cs=null;
		int retFromSP=0;
		String retDescFromSP="";
		try {
			cs=con.prepareCall("{call exportCustomertoData(?,?,?)}");
			cs.setInt(1, batchNo);
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			
			retFromSP = cs.getInt(2);
			retDescFromSP = cs.getString(3);
			if (retFromSP !=0) {
				throw new ServiceException(retDescFromSP);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}finally{
			if (cs != null) {
				try {
					cs.close();
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
}
