package com.dtv.oss.util;

import java.sql.*;
import java.util.Properties;

import javax.sql.*;
import javax.naming.*;
import com.dtv.oss.log.*;

public class DBUtil{
	private static DataSource ds = null;
	private static DataSource ds_scna = null;
	
	public DBUtil(){
	}
	public static Connection getConnection(){
		return getDSConnection();
//		return getJdbcConnection();
	}
	
	public static Connection getConnectionScnA(){
		return getDSConnectionScnA();
//		return getJdbcConnection();
	}
	
	private static Connection getDSConnection(){
		Connection con = null;
	
		try{
		  synchronized(DBUtil.class){
			if(ds == null){
			  /*
				 Properties props = new Properties();
				 props.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
				 props.put(Context.PROVIDER_URL, "t3://192.168.0.230:8002");
				 Context ctx = new InitialContext(props);
			  */ 
			  Context ctx = new InitialContext();
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
			  Context ctx = new InitialContext();
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
	
	
	private static Connection getJdbcConnection(){
		Connection con = null;
		
		try{
		  Class.forName("oracle.jdbc.driver.OracleDriver");
		  String url = "jdbc:oracle:thin:@192.168.0.18:1521:cms";
		  con = DriverManager.getConnection(url, "background", "background");
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
}