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
			LogUtility.log(WebQueryUtility.class, LogLevel.DEBUG, "���SQL=" + sql);
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
	
	/** �öη���ʵ��������ϣ����ʱ����ɾ������д��������jspҳ��
	 * 
	 * ͳһ�ĵ����ѯ,���ڵ�����excel
	 * ֻ�������ڵ����ѯ������ѯ�뽨view,�ٵ��ñ���ѯ
	 * ����ֻ��Ҫ�����ֶ������ַ�����
	 * 		1 ��ѯʱ��ǰ̨���ݲ���{��ȡ���ֶ�λ��}����̨����ѯ��Ȼ��ѯ���У����� temList.add(rs.getObject(i));ʱ��add��һ��
	 * 		  [Ŀǰ�������ַ�ʽ�����ڽ�������ͨ�����ò�ѯ�������ֵ䣬ָ����Щ�ֶ��������Щ�����]
	 *      2 ��ѯȫ���ֶΣ���ҳ��Ĳ�ѯ�����ȡ��ÿһ�����ݣ�ɾ����Ӧ����Ҫ����
	 * @param tableName:Ҫ��ѯ�ı��view������
	 * @param condition:��ѯ������
	 * @param txtTo:��ҳ�ļ�¼����
	 * @param pageNumber:��ҳ��ѯ��ҳ��,��1��ʼ
	 * @param notReturnList:ָ������Ҫ���ص���,�ԷֺŸ���,ע���Էֺſ�ʼ���ԷֺŽ���,��1��ʼ,ע��:1���к�
	 *        ���û�в���Ҫ�����ֶ�,����null��""������
	 * @return List �����List�ķ�ʽ����,List�е�һ������Ϊ�����ı��ֶ�ͷ,�ṩ��ҳ�����滻,���һ����¼Ϊ�ܼ�¼��
	 */
	public static List queryResult(String tableName,String condition,int txtTo,int pageNumber,String notReturnList) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List dataList = new ArrayList();
		List temList = new ArrayList();
		dataList.add("��ͷ");
		String sql="SELECT ROWNUM AS MY_ROWNUM , MY_TABLE.* FROM (select * from "+tableName+" "+condition+") MY_TABLE";
		
		//�õ������������
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
