package com.dtv.oss.web.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.dtv.oss.util.DBUtil;
import com.dtv.oss.web.exception.WebActionException;


public class MonistatDataProcedure {
	
	//�û������ͳ��
	public static String userDeptStat(String beginTime,String endTime,int opId,String txtTotalBeginTime ,String txtTotalEndTime) throws  WebActionException{
		Connection con = DBUtil.getConnection();
		java.sql.CallableStatement cs=null;
		String retDescFromSP="";
		try {
			cs=con.prepareCall("{call SP_REPORT_USER_DEPT_FEE(?,?,?,?,?,?)}");
			cs.setString(1, beginTime);
			cs.setString(2, endTime);
			cs.setInt(3, opId);
			cs.setString(4, txtTotalBeginTime);
			cs.setString(5, txtTotalEndTime);
			cs.registerOutParameter(6, Types.VARCHAR);
			cs.execute();
			retDescFromSP = cs.getString(6);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WebActionException(e.toString());
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
		return retDescFromSP;
	}
	
	//���������շ����ͳ��
	public static String singleMonthStat(String beginTime,String endTime) throws  WebActionException{
		Connection con = DBUtil.getConnection();
		java.sql.CallableStatement cs=null;
		String retDescFromSP="";
		try {
			cs=con.prepareCall("{call SP_REPORT_SINGLEMONTHFEE(?,?,?)}");
			cs.setString(1, beginTime);
			cs.setString(2, endTime);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.execute();
			retDescFromSP = cs.getString(3);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WebActionException(e.toString());
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
		return retDescFromSP;
	}
	
	//��������,�����������ͳ��
	public static String countryAnalysisInfoStat(String beginTime,String endTime) throws  WebActionException{
		Connection con = DBUtil.getConnection();
		java.sql.CallableStatement cs=null;
		String retDescFromSP="";
		try {
			cs=con.prepareCall("{call SP_REPORT_LISTIN_GETDATA(?,?,?)}");
			cs.setString(1, beginTime);
			cs.setString(2, endTime);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.execute();
			retDescFromSP = cs.getString(3);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WebActionException(e.toString());
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
		return retDescFromSP;
	}
	
	//�շ����������
	public static String feeCondtionStat(String beginTime,String endTime,int opId,String regionIds) 
	  throws  WebActionException{
		 Connection con = DBUtil.getConnection();
		 java.sql.CallableStatement cs=null;
		 String retDescFromSP="";
		 try {
			cs=con.prepareCall("{call SP_REPORT_USEDFEE_GETDATA(?,?,?,?,?)}");
			cs.setString(1, beginTime);
			cs.setString(2, endTime);
			cs.setInt(3,opId);
			cs.setString(4, regionIds);
			cs.registerOutParameter(5, Types.VARCHAR);
			cs.execute();
			retDescFromSP = cs.getString(5);
		 } catch (SQLException e) {
			e.printStackTrace();
			throw new WebActionException(e.toString());
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
		return retDescFromSP;
	}
	
	//˳�����������ߵ��Ӳ������������ͳ�Ʊ�
	public static String cityCablelistInfoStat(String beginTime,String endTime) throws  WebActionException{
		Connection con = DBUtil.getConnection();
		java.sql.CallableStatement cs=null;
		String retDescFromSP="";
		try {  
			cs=con.prepareCall("{call SP_REPORT_CITY_CHANGE(?,?,?)}");
			cs.setString(1, beginTime);
			cs.setString(2, endTime);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.execute();
			retDescFromSP = cs.getString(3);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WebActionException(e.toString());
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
		return retDescFromSP;
	}
	
	//�������ߵ�����������������
	public static String cityInChangeInfoStat(String beginTime,String endTime)throws  WebActionException{
		Connection con = DBUtil.getConnection();
		java.sql.CallableStatement cs=null;
		String retDescFromSP="";
		try {  
			cs=con.prepareCall("{call SP_REPORT_CITYDETAIL_CHANGE(?,?,?)}");
			cs.setString(1, beginTime);
			cs.setString(2, endTime);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.execute();
			retDescFromSP = cs.getString(3);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WebActionException(e.toString());
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
		return retDescFromSP;
	}
	
	//ά���ѽ����
	public static String  maintenanceFeeStat(String regionIds)throws  WebActionException{
		Connection con = DBUtil.getConnection();
		java.sql.CallableStatement cs=null;
		String retDescFromSP="";
		try {  
			cs=con.prepareCall("{call SP_REPORT_MAINTENACEFEE(?,?)}");
			cs.setString(1, regionIds);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.execute();
			retDescFromSP = cs.getString(2);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WebActionException(e.toString());
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
		return retDescFromSP;
	}
	
   //	����ҵ������ͳ��
	public static String csiInfoStatForHuairou(String beginTime,String endTime,String paramdistId) throws  WebActionException{
		Connection con = DBUtil.getConnection();
		java.sql.CallableStatement cs=null;
		String retDescFromSP="";
		try {  
			cs=con.prepareCall("{call HR_STAT(?,?,?,?)}");
			cs.setString(1, beginTime);
			cs.setString(2, endTime);
			cs.setString(3, paramdistId);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			retDescFromSP = cs.getString(4);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WebActionException(e.toString());
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
		return retDescFromSP;
	}
}
