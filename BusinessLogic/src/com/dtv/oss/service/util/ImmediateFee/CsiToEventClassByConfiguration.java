/*
 * Created on 2005-12-6
 *
 * @author whq
 * 
 * 通过配置（表）来管理受理类型/事件类型的对应关系
 */
package com.dtv.oss.service.util.ImmediateFee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.util.DBUtil;

public class CsiToEventClassByConfiguration implements CsiToEventClass {
    private static Class clazz = CsiToEventClassByConfiguration.class;
    /*
     * get Collection of EventClass by CsiType and installType(if exist)
     */
    public Collection getEventClass(String csiType, String installType,String anOSType) {
        String strSql = "select eventClass from T_CSIFEESETTING where CSIType='"+csiType + "' and Status = 'V' ";
	    if ((installType != null) && (installType.trim().equals("") == false))
	        strSql = strSql + " and (INSTALLATIONTYPE is null or INSTALLATIONTYPE='"+installType+"')";
	    else 
	        strSql = strSql + " and installationtype is null";
		
		if((anOSType !=null) && (anOSType.trim().equals("")==false))
	        strSql = strSql + " and (INSTALLONORGPORT is null or INSTALLONORGPORT='"+anOSType+"')";
	    else 
	        strSql = strSql + " and INSTALLONORGPORT is null";
System.out.println("事件取得语句："+strSql);
	    return getEventClassImpl(strSql);
    }
    
    
    public Collection getEventClass4CP(String problemLevel, String problemCategory) {
        String strSql = "select eventClass from t_custproblemfeesetting where problemlevel='"+problemLevel + "' and Status = 'V' ";
	    if ((problemCategory != null) && (problemCategory.trim().equals("") == false))
	        strSql = strSql + " and (problemCategory is null or problemCategory='"+problemCategory+"')";
	    else 
	        strSql = strSql + " and problemCategory is null";
	        
	        
	    return getEventClassImpl(strSql);
    }
    
    public Collection getEventClassImpl(String strSql) {
        Collection colEventClass = new ArrayList();
		Connection dbConn = null;
		Statement stmt =null;
		ResultSet rs =null;
		if (ImmediateFeeCalculator.DEBUGMODE) {
		    dbConn = ImmediateFeeCalculator.getDirectJdbcConnection();
		} else {
		    dbConn = DBUtil.getConnection();
		}
		if (dbConn == null) throw new RuntimeException("DB Connection error.");
		try {
			stmt = dbConn.createStatement();
		    if (ImmediateFeeCalculator.DEBUGMODE)
		        System.out.println("strSql in getEventClass():"+strSql);
		    rs= stmt.executeQuery(strSql);
		    while (rs.next()) {
		        colEventClass.add(rs.getString("eventClass"));
		    }
        } catch (SQLException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new RuntimeException("DB error");
        } finally {
        	if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                    LogUtility.log(clazz, LogLevel.FATAL, e1);
                }
            }
        	if (stmt != null) {
                try {
                	stmt.close();
                } catch (SQLException e1) {
                    LogUtility.log(clazz, LogLevel.FATAL, e1);
                }
            }
            if (dbConn != null) {
                try {
                    dbConn.close();
                } catch (SQLException e1) {
                    LogUtility.log(clazz, LogLevel.FATAL, e1);
                }
            }
        }
        return colEventClass;
    }
    
	
}
