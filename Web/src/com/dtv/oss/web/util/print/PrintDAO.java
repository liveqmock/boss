package com.dtv.oss.web.util.print;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.exception.WebActionException;

public class PrintDAO {

	private String querySql="";
	private long count;
	private static final int maxRecordSize=60000;
	private Connection connection = null;
	private java.sql.PreparedStatement statement=null;
	private ResultSet resultSet=null;

	private List columnLabel=null;
	
	public String getQuerySql() {
		return querySql;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	
	private PrintDAO(){}
	
	private String countSQL(String strSql) {
		String strCountSql = "";
		strCountSql = "select count(*) from ( "
				+ strSql + " )";
		return strCountSql;
	}
	
	private Connection getConnection(){
		return DBUtil.getConnection();
	}
	private int executeCountQuery(String sql,Object para) {
		System.out.println("executeCountQuery::"+sql);
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int i = -1;
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			if(para!=null&&sql.indexOf("?")!=-1){
				if(para instanceof Object[]){
					Object[]arr=(Object[]) para;
					for(int j=0;j<arr.length;j++){
						Object pa=arr[j];
						if(pa!=null){
							stmt.setObject(j+1, pa);
						}
					}
				}else{
					stmt.setObject(1, para);
				}
			}
			rs = stmt.executeQuery();
			if (rs.next()) {
				i = rs.getInt(1);
			}
			if(rs==null)
				return -1;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtility.log(Postern.class, LogLevel.WARN, "executeCountQuery", e);
			i = -1;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN, "executeCountQuery",
							e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN, "executeCountQuery",
							e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN, "executeCountQuery",
							e);
				}
			}
		}
		return i;
	}
	private List getColumns(ResultSet rs){
		List list=new ArrayList();
		try {
			ResultSetMetaData meta = rs.getMetaData();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String columnLabel=meta.getColumnLabel(i);
//				String columnName=meta.getColumnName(i);
//				int columnType=meta.getColumnType(i);
				list.add(columnLabel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return list;
	}
	
	public static PrintDAO init(String sql,Object para) throws WebActionException{
		if(sql==null||"".equals(sql)){
			throw new WebActionException("没有有效查询条件.");
		}
		PrintDAO qu=new PrintDAO();
		qu.setQuerySql(sql);
		qu.setCount(qu.executeCountQuery(qu.countSQL(sql),para));
		
		if(qu.getCount()<=0){
			//throw new WebActionException("没有有效记录.");
		}
		//if(qu.getCount()>maxRecordSize){
		//	throw new WebActionException("查询的记录太多,大于"+maxRecordSize+",请设置有效的条件,分批导出.");
		//}
		LogUtility.log(PrintDAO.class, LogLevel.DEBUG, "PrintDAO★★★★总记录数★★★★"+qu.getCount());
		qu.initQuery(sql,para);
//		System.out.println("sql::"+sql);
//		System.out.println("qu.getCount()::"+qu.getCount());
//		System.out.println("from::"+qu.getFrom());
//		System.out.println("to::"+qu.getTo());
		return qu;
	}
	
	private void initQuery(String sql,Object para) throws WebActionException{
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			if(para!=null&&sql.indexOf("?")!=-1){
				if(para instanceof Object[]){
					Object[]arr=(Object[]) para;
					for(int i=0;i<arr.length;i++){
						Object pa=arr[i];
						if(pa!=null){
							statement.setObject(i+1, pa);
						}
					}
				}else{
					statement.setObject(1, para);
				}
			}
			resultSet = statement.executeQuery();
			columnLabel = getColumns(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException("查询错误."+e.getMessage());
		}
	}
	
	public boolean hasNext(){
		boolean hasNext=false;
		try {
			hasNext = resultSet.next();
			if(!hasNext){
				finalize();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return hasNext;
	}
	
	public Map next() throws SQLException {
		Map map = new HashMap();
		for (Iterator it = columnLabel.iterator(); it.hasNext();) {
			String columnLabel = (String) it.next();
			Object val = resultSet.getObject(columnLabel);
//			if(val instanceof oracle.sql.DATE){}
			if(val!=null&&"oracle.sql.DATE".equals(val.getClass().getName())){
				val=resultSet.getDate(columnLabel);
			}
			if(val instanceof BigDecimal){
				BigDecimal bv=(BigDecimal)val;
				String str=val.toString();
//				System.out.println(columnLabel+">>BigDecimal:::"+str+" indexOf "+str.indexOf("."));
				if(str.indexOf(".")!=-1){
					val=new Double(str);
				}else{
					val=new Integer(str);
				}
			}
			map.put(columnLabel, val);
		}
		return map;
	}
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	protected void finalize()throws Throwable{
		super.finalize();
		try {
			resultSet.close();
			resultSet=null;
			statement.close();
			statement=null;
			connection.close();
			connection=null;
		} catch (Exception e) {
			LogUtility.log(PrintDAO.class, LogLevel.WARN,e.getMessage());
		}
	}
}
