/*
 * Created on 2005-12-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.util;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.util.BusinessUtility;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.*;
import com.dtv.oss.dto.*;
import com.dtv.oss.dto.custom.BatchNoDTO;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.util.DtoFiller;
/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InfoFeeImmediateBilling {
	private static Collection callImmediateBillingFee(String psIDList,String destStatus,int operatorID,BatchNoDTO iBatchNo)throws ServiceException{
		Collection feeList=callImmediateBillingFee(psIDList,destStatus,operatorID,"N",iBatchNo);
		return feeList;
	}
	/**
	 * 用来计算传入的客户产品列表的信息费,并取得计算后的信息费
	 * @param psIDList
	 * @param destStatus
	 * @throws ServiceException
	 */
	public static Collection callImmediateBillingFee(String psIDList,String destStatus,int operatorID,String commitFlag, BatchNoDTO iBatchNo)throws ServiceException{
		LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,"■■in InfoFeeImmediateBilling.callImmediateBillingFee start■■");
		LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,psIDList);
		LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,destStatus);
		LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,commitFlag);
		if( (psIDList ==null) || (psIDList.length() < 1)) return null;
		Collection accountItemDTOList=new ArrayList();
		Connection con = null;
		CallableStatement stmt = null;
		Statement sqlStmt = null;
		ResultSet rs =null;
		try {
			con = DBUtil.getConnection();
			LogUtility.log(BusinessUtility.class,LogLevel.DEBUG, "■■开始进行信息费计费■■");
			stmt=con.prepareCall("{call Sp_f_ImmediateBilling(?,?,?,?,?,?,?)}");
			stmt.setString(1, psIDList);
			stmt.setString(2, destStatus);
			stmt.setInt(3, operatorID);
			stmt.setString(4, commitFlag);
			stmt.registerOutParameter(5,Types.INTEGER);
			stmt.registerOutParameter(6,Types.INTEGER);
			stmt.registerOutParameter(7,Types.VARCHAR);
			stmt.executeUpdate();
			int batchNo= stmt.getInt(5);
			int resInt = stmt.getInt(6);
			String resStr = stmt.getString(7);
			if (resInt != 0) {
				throw new ServiceException(resStr);
			}
			LogUtility.log(BusinessUtility.class,LogLevel.DEBUG, "■■batchNo■■"+batchNo);
			LogUtility.log(BusinessUtility.class,LogLevel.DEBUG, "■■开始取得计算结果■■");
			iBatchNo.setBatchNo(batchNo);
			String querySQL="select * from T_ACCOUNTITEM_IMMEDIATE where BATCHNO="+batchNo;
			sqlStmt =con.createStatement();
			rs =sqlStmt.executeQuery(querySQL);
			while(rs.next()){
				accountItemDTOList.add(DtoFiller.fillAccountItemNotIncludeAiNoDTO(rs));
			}
			LogUtility.log(BusinessUtility.class,LogLevel.DEBUG, "■■accountItemDTOList size ■■"+accountItemDTOList.size());
		}catch (SQLException e) {
			LogUtility.log(InfoFeeImmediateBilling.class, LogLevel.ERROR, e);
			throw new ServiceException("计算客户产品的信息费的过程中发生错误");
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException ee) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ee) {
				}
			}
			if (sqlStmt != null) {
				try {
					sqlStmt.close();
				} catch (SQLException ee) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ee) {
				}
			}
		}
		
		LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,"■■in InfoFeeImmediateBilling.callImmediateBillingFee end■■");
		return accountItemDTOList;
	}
	
	//对套餐立即计费 状态转换立即计费	
	//ccidlist 是ccid的列表用逗号分割
	public static Collection callImmediateBillingCampaign(String ccidlist,String destStatus,int operatorID,int orgID,BatchNoDTO iBatchNo)
	throws ServiceException
	{
	    LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,"■■in InfoFeeImmediateBilling.callImmediateBillingCampaign start■■");
	    LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,ccidlist);
	    LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,destStatus);
		if( (ccidlist ==null) || (ccidlist.length() < 1)) return null;
        Connection con = null;
	    CallableStatement stmt = null;
	    Collection accountItemDTOList=new ArrayList();
	    Statement sqlStmt = null;
		ResultSet rs =null;
	    try {
    	    con = DBUtil.getConnection();
    	    LogUtility.log(BusinessUtility.class,LogLevel.DEBUG, "■■开始进行套餐立即计费■■");
    	    stmt=con.prepareCall("{call sp_f_immediateBilling_pk(?,?,?,?,?,?,?,?)}");
    	    stmt.setString(1,ccidlist);
    	    stmt.setString(2,destStatus);
    	    stmt.setInt(3,operatorID);
    	    stmt.setString(4,"N");
    	    stmt.registerOutParameter(5,Types.INTEGER);
    	    stmt.registerOutParameter(6,Types.INTEGER);
    	    stmt.registerOutParameter(7,Types.VARCHAR);
    	    stmt.setString(8,"N");
    	    stmt.executeUpdate();
    	    int batchNo= stmt.getInt(5);
			int resInt = stmt.getInt(6);
			String resStr = stmt.getString(7);
			if (resInt != 0) {
				throw new ServiceException(resStr);
			}
			iBatchNo.setBatchNo2(batchNo);
			LogUtility.log(BusinessUtility.class,LogLevel.DEBUG, "■■batchNo■■"+batchNo);
			LogUtility.log(BusinessUtility.class,LogLevel.DEBUG, "■■开始取得计算结果■■");
			String querySQL="select * from T_ACCOUNTITEM_IMMEDIATE where BATCHNO="+batchNo;
			sqlStmt =con.createStatement();
			rs =sqlStmt.executeQuery(querySQL);
			while(rs.next()){
				accountItemDTOList.add(DtoFiller.fillAccountItemNotIncludeAiNoDTO(rs));
			}
			rs.getStatement().close();
			LogUtility.log(BusinessUtility.class,LogLevel.DEBUG, "■■accountItemDTOList size ■■"+accountItemDTOList.size());
	    }
	    catch(SQLException ex)
	    {
	        LogUtility.log(InfoFeeImmediateBilling.class, LogLevel.ERROR, ex);
			throw new ServiceException("计算套餐立即计费过程中发生错误");
	    }
	    finally{
	        if (stmt != null) {
				try 
				{
					stmt.close();
				} catch (SQLException ee) 
				{
				}
			}
	        if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ee) {
				}
			}
			if (sqlStmt != null) {
				try {
					sqlStmt.close();
				} catch (SQLException ee) {
				}
			}
			if (con != null) 
			{
				try 
				{
					con.close();
				} 
				catch (SQLException ee) 
				{
				}
			}
	    }
	    LogUtility.log(BusinessUtility.class,LogLevel.DEBUG, "■■套餐立即计费结束!■■");
	    return accountItemDTOList;
	}
	
	public static Collection callCloseCustomer(int custID,int defaultAccountID, String destStatus,int operatorID, BatchNoDTO iBatchNo)
	throws ServiceException
	{
	    return callCloseAccount(defaultAccountID,destStatus,operatorID, iBatchNo);
	}
	/*
		在前台业务逻辑层已经校验了账户销户退户的可行性，在计费接口不再对次校验
	*/
	public static Collection callCloseAccount(int accountID,String destStatus,int operatorID, BatchNoDTO iBatchNo)
	throws ServiceException{
		Collection acctItemCol = new ArrayList();
		//取得套餐进行计费
		ArrayList ccidCol = (ArrayList)BusinessUtility.getValidCustCampaign4Account(accountID);
		String strccid = "";
		if(ccidCol.size() > 0)
			strccid = String.valueOf(ccidCol.get(0));
		for(int i = 1;i<ccidCol.size();i++)
			strccid = strccid + "," + (Integer)ccidCol.get(i);
		Collection acctItemCol_campaign = callImmediateBillingCampaign(strccid,"T",operatorID,0,iBatchNo);

		//对不属于套餐的客户产品进行计费
		ArrayList psidCol = (ArrayList)BusinessUtility.getValidPSIDNotRelateCampaign(accountID);
		String strpsid = "";
		if(psidCol.size() > 0)
			strpsid = String.valueOf(psidCol.get(0));
		for(int i = 1;i<psidCol.size();i++)
			strpsid = strpsid + "," + (Integer)psidCol.get(i);
		Collection acctItemCol_psid = callImmediateBillingFee(strpsid,destStatus,operatorID,iBatchNo);

		//针对账户优惠
		Collection acctItemCol_discount = account_discount(accountID,destStatus,operatorID);

		if(acctItemCol_campaign != null)
    		acctItemCol.addAll(acctItemCol_campaign);
		if(acctItemCol_psid != null)
			acctItemCol.addAll(acctItemCol_psid);
		if(acctItemCol_discount != null)
			acctItemCol.addAll(acctItemCol_discount);
		//合并费用
	    return acctItemCol;
	}
	
	private static Collection account_discount(int accountID,String destStatus,int operatorID){
  	    LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,"■■in InfoFeeImmediateBilling.account_discount start■■");
	    LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,String.valueOf(accountID));
	    LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,destStatus);
		Collection accountItemDTOList=new ArrayList();
		//调用存储过程
		return accountItemDTOList;
	}

	/*
		
	*/
	public static Collection callCloseServiceAccount(int saID,String destStatus,int operatorID,BatchNoDTO iBatchNo)
	throws ServiceException{
		Collection acctItemCol = new ArrayList();
		ArrayList ccidCol = (ArrayList)BusinessUtility.getValidCustCampaign4SA(saID);
		String strccid = "";
		if(ccidCol.size() > 0)
			strccid = String.valueOf(ccidCol.get(0));
		for(int i = 1;i<ccidCol.size();i++)
			strccid = strccid + "," + (Integer)ccidCol.get(i);
		
		Collection acctItemCol_campaign = callImmediateBillingCampaign(strccid,"T",operatorID,0,iBatchNo);
		
		ArrayList psidCol = (ArrayList)BusinessUtility.getValidPSIDNotRelateCampaignBySA(saID);
		String strpsid = "";
		if(psidCol.size() > 0)
			strpsid = String.valueOf(psidCol.get(0));
		for(int i = 1;i<psidCol.size();i++)
			strpsid = strpsid + "," + (Integer)psidCol.get(i);
		Collection acctItemCol_psid = callImmediateBillingFee(strpsid,destStatus,operatorID,iBatchNo);

		Collection acctItemCol_discount = sa_discount(saID,destStatus,operatorID);
		//针对用户优惠

		if(acctItemCol_campaign != null)
    		acctItemCol.addAll(acctItemCol_campaign);
		if(acctItemCol_psid != null)
			acctItemCol.addAll(acctItemCol_psid);
		if(acctItemCol_discount != null)
			acctItemCol.addAll(acctItemCol_discount);
	    return acctItemCol;
	}
	
	private static Collection sa_discount(int saID,String destStatus,int operatorID)
	{
  	    LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,"■■in InfoFeeImmediateBilling.sa_discount start■■");
	    LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,String.valueOf(saID));
	    LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,destStatus);
		Collection accountItemDTOList=new ArrayList();
		//调用存储过程
		return accountItemDTOList;
	}
	
	public static Collection callCustomerCampaignResume(int custCampaignID,String rfType,int operatorID,int orgID)
	throws ServiceException{
  	    LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,"■■in InfoFeeImmediateBilling.CallcustCampaignID start■■");
	    LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,String.valueOf(custCampaignID));
	    LogUtility.log(InfoFeeImmediateBilling.class,LogLevel.DEBUG,rfType);
        Connection con = null;
	    Collection accountItemDTOList=new ArrayList();
	    Statement sqlStmt = null;
		ResultSet rs =null;
	    try{
	        con = DBUtil.getConnection();
	        String querySQL = "select a.accountID,br.acctitemtypeid,c.customerid,br.feeSplitPlanID,br.value,br.feetype,br.accttype,br.custtype from T_BillingRuleItem br,t_Customercampaign cc ,T_Account a,t_Customer c "
                            + " where cc.campaignid = br.campaignid and br.eventClass = 13 "
                            + " and cc.customerid = c.customerid"
                            + " and cc.accountid = a.accountid"
                            + " and br.status = 'V'"
                            + " and (br.acctType is null or br.acctType = a.AccountType)"
                            + " and (br.CustType is null or br.custType = c.CustomerType)"
                            + " and sysdate between validfrom and validto"
                            + " and cc.ccid = " + custCampaignID
                            + " and rfBillingCycleFlag = '" + rfType + "'"
                            + " order by Nvl(br.acctType,'0'),nvl(br.custTYpe,'0')";
	        sqlStmt =con.createStatement();
			rs =sqlStmt.executeQuery(querySQL);
			if(rs.next()){
			    AccountItemDTO acctItemDTO = new AccountItemDTO();
			    acctItemDTO.setCustID(rs.getInt("CustomerID"));
			    acctItemDTO.setAcctID(rs.getInt("AccountID"));
			    acctItemDTO.setFeeType(rs.getString("FeeType"));
			    acctItemDTO.setAcctItemTypeID(rs.getString("AcctItemTypeID"));
			    acctItemDTO.setValue(rs.getDouble("Value"));
			    acctItemDTO.setStatus("W");
			    acctItemDTO.setOrgID(orgID);
			    acctItemDTO.setOperatorID(operatorID);
			    acctItemDTO.setFeeSplitPlanID(rs.getInt("feeSplitPlanID"));
			    if (acctItemDTO.getFeeType().equals("P"))
                    acctItemDTO.setForcedDepositFlag("Y");
                else
                    acctItemDTO.setForcedDepositFlag("N");
                accountItemDTOList.add(acctItemDTO);
			}
	    }
	    catch(SQLException ex)
	    {
	    }
	    finally{
	        if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ee) {
				}
			}
			if (sqlStmt != null) {
				try {
					sqlStmt.close();
				} catch (SQLException ee) {
				}
			}
			if (con != null) 
			{
				try 
				{
					con.close();
				} 
				catch (SQLException ee) 
				{
				}
			}   
	    }

	    return accountItemDTOList;
/*	    
	    return null;*/
	}
}
