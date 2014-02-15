/*
 * Created on 2004-9-2
 *
 * @file SystemLogRecorder.java
 */
package com.dtv.oss.service.util;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.Customer;
import com.dtv.oss.domain.Operator;
import com.dtv.oss.domain.SystemLog;
import com.dtv.oss.dto.SystemLogDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.util.Constant;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.util.TimestampUtility;
/**
 * @author Mac Wang
 *
 * @function class to create System Log
 */
public class SystemLogRecorder {

	public static final String LOGCLASS_NORMAL 		= "N";
	public static final String LOGCLASS_CRITICAL 	= "C";
	public static final String LOGCLASS_WARN 		= "W";
	public static final String LOGCLASS_ERROR 		= "E";

	public static final String LOGTYPE_APP 			= "A";
	public static final String LOGTYPE_SYSTEM 		= "S";
	
//	public static final String LOGMODULE_CSR 		= "�ͷ�";
//	public static final String LOGMODULE_GROUPCUSTOMER 		= "��ͻ�����";
//	public static final String LOGMODULE_WORK 		= "WORK";
//	public static final String LOGMODULE_LOGISTICS 	= "��̨����";
//	public static final String LOGMODULE_SYSTEM 		= "Ӫҵ֧��";
//	public static final String LOGMODULE_CONFIG 		= "ϵͳ����";
//	public static final String LOGMODULE_CONSOLE 	= "�ͷ�";
//	public static final String LOGMODULE_BACKOFFICE 	= "�ͷ�";
//	public static final String LOGMODULE_BATCH 		= "�ͷ�";
//	public static final String LOGMODULE_LOGMANAGE 	= "�ͷ�";
	
	public static final String LOGMODULE_CUSTSERV      = "B";//�ͷ�Ӫҵģ��
	public static final String LOGMODULE_LOGISTICS     = "C";//��̨����ģ��
	public static final String LOGMODULE_STAT          = "D";//ͳ�ƺͱ���ģ��
	public static final String LOGMODULE_CONFIG        = "E";//ϵͳ����ģ��
	public static final String LOGMODULE_GROUPCUSTOMER = "F";//��ͻ�ģ��
	public static final String LOGMODULE_NET           = "N";//����ӿ�ģ��
	public static final String LOGMODULE_LOGMANAGE     = "S";//ϵͳ
	public static final String LOGMODULE_CATV  		   = "A";//ģ����ӹ���ģ��
	
	private static Map systemLogModuleCache = new HashMap();//ϵͳ��־ģ�黺��
	//�õ�ϵͳģ������map,���뻺����
	static{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
		   con = DBUtil.getConnection();
		   stmt = con.createStatement();
		
		   // ��ʼ�� systemLogModuleCache ��������Դ��t_commonsettingdata��
		   String sql = "select key,value from t_commonsettingdata" +
				" where Name='SET_G_WEBMODULE' order by key";
		   rs = stmt.executeQuery(sql);
		   while (rs.next()) {
				systemLogModuleCache.put(rs.getString("key"), rs.getString("value"));
		   } 
		 } catch (Exception e) {
			  LogUtility.log(SystemLogRecorder.class, LogLevel.WARN, "init systemLogModuleCache", e);
		 } finally {
			  if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					LogUtility.log(SystemLogRecorder.class, LogLevel.WARN, "init systemLogModuleCache", e);
				}
			  }
			  if (stmt != null) {
				  try {
				 	stmt.close();
				  } catch (SQLException e) {
					LogUtility.log(SystemLogRecorder.class, LogLevel.WARN, "init systemLogModuleCache", e);
				  }
			  }
			  if (con != null) {
				  try {
					con.close();
				  } catch (SQLException e) {
					LogUtility.log(SystemLogRecorder.class, LogLevel.WARN, "init systemLogModuleCache", e);
				  }
			  }
		}
	}
	
	public SystemLogRecorder(){

	}
	public static SystemLog createSystemLog(String machineName,
				int operatorID,
				int customerID,
				int accountID,
				int serviceAccountID,
				int psID,
				String moduleNameKey,
				String operation,
				String logDesc,
				String logClass,
				String logType) 
	throws ServiceException {
		try{
			String moduleNameValue = (String)systemLogModuleCache.get(moduleNameKey);
			SystemLogDTO dto = new SystemLogDTO();
			dto.setOperation(operation);
			dto.setAccountID(accountID);
			dto.setServiceAccountID(serviceAccountID);
			dto.setPsID(psID);
			dto.setLogDesc(logDesc);
			dto.setLogClass(logClass);
			dto.setLogType(logType);
			dto.setMachineName(machineName);
			dto.setOperatorID(operatorID);
			dto.setModuleName(moduleNameValue);
			dto.setCreateDateTime(TimestampUtility.getCurrentDate());
			try {
				Operator operator = EJBCommonUtility.getOperatorByID(operatorID);
				dto.setOperatorName(operator.getOperatorName());
				dto.setLoginID(operator.getLoginID());
				dto.setOrgID(operator.getOrgID());
			} catch (FinderException e) {
				e.printStackTrace();
			}
			if (customerID > 0) {
				try {
					
					Customer customer = EJBCommonUtility.getCustomerByID(customerID);
					dto.setCustomerID(customerID);
					dto.setCustomerName(customer.getName());
				} catch (FinderException e) {
					LogUtility.log(GenericDAO.class, LogLevel.ERROR,"���ҿͻ���Ϣ", e);	
				}
			}
			LogUtility.log(SystemLogRecorder.class, LogLevel.DEBUG, "����SystemLog������"+dto);
			return EJBCommonUtility.createSystemLog(dto);
		}catch(HomeFactoryException e) {
		    LogUtility.log(SystemLogRecorder.class, LogLevel.ERROR, e);
		    throw new ServiceException("����ϵͳ��־ʱ��λ����");		    
		}catch(CreateException e) {
		    LogUtility.log(SystemLogRecorder.class, LogLevel.ERROR, e);
		    throw new ServiceException("����ϵͳ��־����");		    
		}
	}
	public static SystemLog createSystemLog(String machineName,
					int operatorID,
					int customerID,
					String moduleNameKey,
					String operation,
					String logDesc,
					String logClass,
					String logType) 
		throws ServiceException {
		try{
			String moduleNameValue = (String)systemLogModuleCache.get(moduleNameKey);
			SystemLogDTO dto = new SystemLogDTO();
			dto.setOperation(operation);
			dto.setLogDesc(logDesc);
			dto.setLogClass(logClass);
			dto.setLogType(logType);
			dto.setMachineName(machineName);
			dto.setOperatorID(operatorID);
			dto.setModuleName(moduleNameValue);
			dto.setCreateDateTime(TimestampUtility.getCurrentDate());
			try {
				Operator operator = EJBCommonUtility.getOperatorByID(operatorID);
				dto.setOperatorName(operator.getOperatorName());
				dto.setLoginID(operator.getLoginID());
				dto.setOrgID(operator.getOrgID());
			} catch (FinderException e) {
				e.printStackTrace();
			}
			if (customerID > 0) {
				try {
					
					Customer customer = EJBCommonUtility.getCustomerByID(customerID);
					dto.setCustomerID(customerID);
					dto.setCustomerName(customer.getName());
				} catch (FinderException e) {
					LogUtility.log(GenericDAO.class, LogLevel.ERROR,"���ҿͻ���Ϣ", e);	
				}
			}
			LogUtility.log(SystemLogRecorder.class, LogLevel.DEBUG, "����SystemLog������"+dto);
			return EJBCommonUtility.createSystemLog(dto);
		}catch(HomeFactoryException e) {
		    LogUtility.log(SystemLogRecorder.class, LogLevel.ERROR, e);
		    throw new ServiceException("����ϵͳ��־ʱ��λ����");		    
		}catch(CreateException e) {
		    LogUtility.log(SystemLogRecorder.class, LogLevel.ERROR, e);
		    throw new ServiceException("����ϵͳ��־����");		    
		}
	}
	public static SystemLog createSystemLog( String logClass, String logType, String logDesc, String operation, String machineName, String moduleName, int operatorID,int customerID) throws ServiceException {
		return createSystemLog(machineName,operatorID,customerID,moduleName,operation,logDesc,logClass,logType);
	}
	public static SystemLog createSystemLog(String logClass, String logType, String logDesc, String operation, String machineName, String moduleName, int operatorID) throws ServiceException {
		return createSystemLog(logClass, logType, logDesc, operation, machineName, moduleName, operatorID, -1);
	}

	public static void log(String logClass, String logDesc, String operation, String machineName, String moduleName, int operator, int customerID) throws ServiceException {
		createSystemLog(logClass, Constant.SYSTEMLOG_LOGTYPE_APPLICATION, logDesc, operation, machineName, moduleName, operator, customerID);
	}
	public static void warning(String logDesc, String operation, String machineName, String moduleName, int operator, int customerID) throws ServiceException {
		log(Constant.SYSTEMLOG_LOGCLASS_WARNING, logDesc, operation, machineName, moduleName, operator, customerID);
	}
	public static void error(String logDesc, String operation, String machineName, String moduleName, int operator, int customerID) throws ServiceException {
		log(Constant.SYSTEMLOG_LOGCLASS_ERROR, logDesc, operation, machineName, moduleName, operator, customerID);
	}
	public static void normalLog(String logDesc, String operation, String machineName, String moduleName, int operator, int customerID) throws ServiceException{
		log(Constant.SYSTEMLOG_LOGCLASS_NORMAL, logDesc, operation, machineName, moduleName, operator, customerID);
	}
	public static void keyLog(String logDesc, String operation, String machineName, String moduleName, int operator, int customerID) throws ServiceException {
		log(Constant.SYSTEMLOG_LOGCLASS_KEY, logDesc, operation, machineName, moduleName, operator, customerID);
	}

	public static void log(String logClass, String logDesc, String operation, String machineName, String moduleName, int operator) throws ServiceException {
		createSystemLog(logClass, Constant.SYSTEMLOG_LOGTYPE_APPLICATION, logDesc, operation, machineName, moduleName, operator);
	}

	public static void warning(String logDesc, String operation, String machineName, String moduleName, int operator) throws ServiceException {
		log(Constant.SYSTEMLOG_LOGCLASS_WARNING, logDesc, operation, machineName, moduleName, operator);
	}
	public static void error(String logDesc, String operation, String machineName, String moduleName, int operator) throws ServiceException {
		log(Constant.SYSTEMLOG_LOGCLASS_ERROR, logDesc, operation, machineName, moduleName, operator);
	}
	public static void normalLog(String logDesc, String operation, String machineName, String moduleName, int operator) throws ServiceException {
		log(Constant.SYSTEMLOG_LOGCLASS_NORMAL, logDesc, operation, machineName, moduleName, operator);
	}
	public static void keyLog(String logDesc, String operation, String machineName, String moduleName, int operator) throws ServiceException {
		log(Constant.SYSTEMLOG_LOGCLASS_KEY, logDesc, operation, machineName, moduleName, operator);
	}
	public static String appendDescString(String head,String para1,String para2){
		StringBuffer buf=new StringBuffer();
		if(!head.endsWith(":")){
			head=head+":";
		}
		if(para1!=null&&para2!=null&&!para1.equals(para2)){
			buf.append(head);
			buf.append(para1);
			buf.append("-->");
			buf.append(para2);
		}else if((para1==null||para1.equals(""))&&para2!=null&&!"".equals(para2)){
			buf.append(head);
			buf.append("(��ֵ)");
			buf.append("-->");
			buf.append(para2);
		}else if((para2==null||para2.equals(""))&&para1!=null&&!"".equals(para1)){
			buf.append(head);
			buf.append(para1);
			buf.append("-->");
			buf.append("(��ֵ)");
		}
		return buf.toString();
	}

	/**
	* for testing
	*/
	public static void main(String args[]){

	}
}
