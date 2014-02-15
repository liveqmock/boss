/*
 * Created on 2005-11-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.util.Postern;
import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;


/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestDbuitl {
	private static DataSource ds = null;
	private static DataSource ds_scna = null;
	
	public TestDbuitl(){
	}
	public static Connection getConnection(){
		return getDSConnection();
	}
	
	public static Connection getConnectionScnA(){
		return getDSConnectionScnA();
	}
	
	private static Connection getDSConnection(){
		Connection con = null;
	
		try{
		  synchronized(DBUtil.class){
			if(ds == null){
				 Properties props = new Properties();
				 props.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
				 props.put(Context.PROVIDER_URL, "t3://127.0.0.1:7001");
				 Context ctx = new InitialContext(props);
			   
			 // Context ctx = new InitialContext();
			  ds = (javax.sql.DataSource)ctx.lookup("TXOracleDS/P5");
			}
		  }
		  con = ds.getConnection();
		  con.setAutoCommit(false);
		} catch(Exception e){
			LogUtility.log(DBUtil.class,LogLevel.WARN,e);
			System.out.println(e);
			if(con != null){
	    		try{
	    		  con.close();
	    		} catch(SQLException ex){
	    			LogUtility.log(DBUtil.class,LogLevel.WARN,ex);
	    			System.out.println(ex);
	    		}
	    	}
		}
		return con;
	}

	private static Connection getDSConnectionScnA(){
		Connection con = null;

		try{
		  synchronized(DBUtil.class){
			if(ds_scna == null){
				Properties props = new Properties();
				props.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
				 props.put(Context.PROVIDER_URL, "t3://127.0.0.1:7001");
				 Context ctx = new InitialContext(props);
			  //Context ctx = new InitialContext();
			  ds_scna = (javax.sql.DataSource)ctx.lookup("TXOracleDS/SCNA");
			}
		  }
		  con = ds_scna.getConnection();
		  con.setAutoCommit(false);
		} catch(Exception e){
			LogUtility.log(DBUtil.class,LogLevel.WARN,e);
			System.out.println(e);
			if(con != null){
	    		try{
	    		  con.close();
	    		} catch(SQLException ex){
	    			LogUtility.log(DBUtil.class,LogLevel.WARN,ex);
	    			System.out.println(ex);
	    		}
	    	}
		}
		return con;
	}
	
	public  static boolean hasValidRecordExist(Connection conn, String strSql) throws SQLException{
	    Statement stmt = null;

	    stmt = conn.createStatement();

	    boolean hasValidRecordExist = stmt.executeQuery(strSql).next();
	    stmt.close();

	    return hasValidRecordExist;
	 }

	public static ResultSet executeQuery(Connection conn, String strSql) throws SQLException{
	    Statement stmt = null;

	    stmt = conn.createStatement();

	    return stmt.executeQuery(strSql);
	}

	public static int executeUpdate(Connection conn, String strSql) throws SQLException{
	    Statement stmt = null;

	    stmt = conn.createStatement();

	    int result = stmt.executeUpdate(strSql);
	    stmt.close();
	    return result;
	}
	/**
	 * 根据传入的参数取得对应的时间（DT_LASTMOD）
	 * @param tableName
	 * @param columenName
	 * @param keyName
	 * @param keyValue
	 * @return
	 */
	public static Timestamp getTimeStamp(String tableName,String columenName,String keyName,String keyValue){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql="select "+columenName+"  from "+ tableName +" where "+keyName+" = "+keyValue;
		Timestamp currentTimestamp = null;
		try{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				currentTimestamp = rs.getTimestamp(columenName);
			}
		} catch(Exception e){
			LogUtility.log(Postern.class,LogLevel.WARN,"getTimeStamp",e);
		} finally{
			if(stmt != null){
				try{
					stmt.close();
				} catch(SQLException e){
					LogUtility.log(Postern.class,LogLevel.WARN,"getTimeStamp",e);
				}
			}
			if(con != null){
				try{
					con.close();
				} catch(SQLException e){
					LogUtility.log(Postern.class,LogLevel.WARN,"getTimeStamp",e);
				}
			}
		}
		return currentTimestamp;
	}

}
