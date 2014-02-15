/**
 * 
 */
package com.dtv.oss.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.*;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;

/**
 * @author lancelot
 *
 */
public class WebQueryUtility {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static Map getReportCustomerInfo(int districtid){
		String sql = "select t.columnname,t.productstr from report_customerinfo t where t.districtid ="+districtid +"  order by t.priority ";
		return getMapBySQL(sql,true,true);
	}

	private static Map getMapBySQL(String sql) {
		return getMapBySQL(sql, true);
	}

	private static Map getMapBySQL(String sql, boolean flag) {
		return getMapBySQL(sql, flag, false);
	}

	private static Map getMapBySQL(String sql, boolean flag, boolean order) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map map = null;
         
		if (order)
			map = new LinkedHashMap();
		else
			map = new HashMap();

		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (flag) {
					map.put(rs.getString(1), rs.getString(2));
				} else {
					map.put(new Integer(rs.getInt(1)), rs.getString(2));
				}

			}
		} catch (Exception e) {
			LogUtility.log(WebQueryUtility.class, LogLevel.WARN, "getMapBySQL", e);
			LogUtility.log(WebQueryUtility.class, LogLevel.DEBUG, "相关SQL=" + sql);
			map.clear();
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
					LogUtility.log(WebQueryUtility.class, LogLevel.WARN, "getMapBySQL",
							e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(WebQueryUtility.class, LogLevel.WARN, "getMapBySQL",
							e);
				}
			}
		}
		return map;
	}
	
	/** 该段方法实属垃圾，希望有时间能删除，重写调用它的jsp页面
	 * 
	 * 统一的单表查询,便于导出到excel
	 * 只适用用于单表查询，多表查询请建view,再调用本查询
	 * 处理只需要部分字段有两种方法：
	 * 		1 查询时由前台传递参数{不取的字段位数}给后台，查询仍然查询所有，但在 temList.add(rs.getObject(i));时不add这一列
	 * 		  [目前采用这种方式，便于将来可以通过配置查询的数据字典，指定那些字段输出，那些不输出]
	 *      2 查询全部字段，在页面的查询结果中取出每一条数据，删除对应不需要的列
	 * @param tableName:要查询的表或view的名字
	 * @param condition:查询的条件
	 * @param txtTo:分页的记录条数
	 * @param pageNumber:分页查询的页码,从1开始
	 * @param notReturnList:指定不需要返回的列,以分号隔开,注意以分号开始并以分号结束,从1开始,注意:1是行号
	 *        如果没有不需要返回字段,给个null或""都可以
	 * @return List 结果以List的方式返回,List中第一条数据为保留的表字段头,提供在页面上替换,最后一条记录为总记录数
	 */
	public static List queryResult(String tableName,String condition,int txtTo,int pageNumber,String notReturnList) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List dataList = new ArrayList();
		List temList = new ArrayList();
		dataList.add("表头");
		String sql="SELECT ROWNUM AS MY_ROWNUM , MY_TABLE.* FROM (select * from "+tableName+" "+condition+") MY_TABLE";
		
		//得到结果集的列数
		int cols = getIntBySQL("SELECT COUNT(*) FROM USER_TAB_COLUMNS WHERE TABLE_NAME='"+
				tableName.toUpperCase()+"'");
		
		String querySql = "SELECT * FROM " +
				"("+sql +
				" WHERE ROWNUM <=" +txtTo*pageNumber +")" +
				" WHERE MY_ROWNUM>" +txtTo*(pageNumber-1);
		
		LogUtility.log(Postern.class, LogLevel.DEBUG, "querySql=\n"+querySql);
		
		int allRecordCount =getIntBySQL("SELECT COUNT(*) FROM ("+sql+")");;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(querySql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				for(int i=1;i<=cols+1;i++)
				{
					if(notReturnList != null && notReturnList.indexOf(";"+i+";")>-1)
						continue;
					temList.add(rs.getObject(i));
				}
				
				dataList.add(new ArrayList(temList));
				temList.clear();
			}		
		} catch (Exception e) {
			LogUtility.log(WebQueryUtility.class, LogLevel.WARN, "queryResult", e);
		} finally {
			if (rs !=null) {
				try{
				   rs.close();
				}catch(SQLException e){	
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(WebQueryUtility.class, LogLevel.WARN,
							"queryResult", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(WebQueryUtility.class, LogLevel.WARN,
							"queryResult", e);
				}
			}
		}
		dataList.add(new Integer(allRecordCount));
		return dataList;
	}
	
	private static int getIntBySQL(String sql) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		int i = Integer.MIN_VALUE;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				i = rs.getInt(1);
			}
			if (rs == null)
				return -1;
		} catch (Exception e) {
			LogUtility.log(WebQueryUtility.class, LogLevel.WARN, "getIntBySQL", e);
			i = Integer.MIN_VALUE;
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
					LogUtility.log(WebQueryUtility.class, LogLevel.WARN, "getIntBySQL",
							e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(WebQueryUtility.class, LogLevel.WARN, "getIntBySQL",
							e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(WebQueryUtility.class, LogLevel.WARN, "getIntBySQL",
							e);
				}
			}
		}
		return i;
	}

}
