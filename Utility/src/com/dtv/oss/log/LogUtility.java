/*
 * Created on 2005-9-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.log;
import org.apache.log4j.*;
/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LogUtility {
	/**
	 * 
	 */
	public LogUtility() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param className 类
	 * @param logLevel  日志级别
	 * @param currentMsg 日志信息
	 */
	public static void log(Class  className,int logLevel, String currentMsg){
		Logger curentLog=Logger.getLogger(className);
		switch(logLevel){
			case LogLevel.ALL:
				break;
			case LogLevel.DEBUG:
				curentLog.debug(currentMsg);
				break;
			case LogLevel.ERROR:
				curentLog.error(currentMsg);
				break;
			case LogLevel.FATAL:
				curentLog.equals(currentMsg);
				break;
			case LogLevel.INFO:
				curentLog.info(currentMsg);
				break;
			case LogLevel.OFF:
				break;
			case LogLevel.WARN:
				curentLog.warn(currentMsg);
				break;
			default:
		}
	}
	/**
	 * @param className 类
	 * @param logLevel 日志级别
	 * @param methodName 纪录日志所在的方法名
	 * @param currentMsg 日志信息
	 */
	public static void log(Class  className,int logLevel, String methodName,String currentMsg){
		Logger curentLog=Logger.getLogger(className);
		StringBuffer currentBuffer=new StringBuffer();
		currentBuffer.append("■"+methodName+"○start■ ");
		currentBuffer.append("\r\n");
		currentBuffer.append(currentMsg);
		switch(logLevel){
			case LogLevel.ALL:
				break;
			case LogLevel.DEBUG:
				curentLog.debug(currentBuffer.toString());
				curentLog.debug("■"+methodName+"○end■");
				break;
			case LogLevel.ERROR:
				curentLog.error(currentBuffer.toString());
				curentLog.debug("■"+methodName+"○end■ ");
				break;
			case LogLevel.FATAL:
				curentLog.equals(currentBuffer.toString());
				curentLog.debug("■"+methodName+"○end■");
				break;
			case LogLevel.INFO:
				curentLog.info(currentBuffer.toString());
				curentLog.debug("■"+methodName+"○end■");
				break;
			case LogLevel.OFF:
				break;
			case LogLevel.WARN:
				curentLog.warn(currentBuffer.toString());
				curentLog.debug("■"+methodName+"○end■ ");
				break;
			default:
		}
	}
	/**
	 * @param className 类
	 * @param logLevel 日志级别
	 * @param currentMsg 日志信息
	 * @param error  异常
	 */
	public static void log(Class  className,int logLevel,String currentMsg, Throwable error){
		Logger curentLog=Logger.getLogger(className);
		switch(logLevel){
			case LogLevel.ALL:
				break;
			case LogLevel.DEBUG:
				curentLog.debug(currentMsg,error);
				break;
			case LogLevel.ERROR:
				curentLog.error(currentMsg,error);
				break;
			case LogLevel.FATAL:
				curentLog.error(currentMsg,error);
				break;
			case LogLevel.INFO:
				curentLog.info(currentMsg,error);
				break;
			case LogLevel.OFF:
				break;
			case LogLevel.WARN:
				curentLog.warn(currentMsg,error);
				break;
			default:
		}
	}
	/**
	 * @param className 类
	 * @param logLevel 日志级别
	 * @param methodName 纪录日志所在的方法名
	 * @param currentObj 纪录对象
	 */
	public static void log(Class  className,int logLevel,String methodName,Object currentObj){
		Logger curentLog=Logger.getLogger(className);
		StringBuffer currentBuffer=new StringBuffer();
		currentBuffer.append("■"+methodName+"○start■");
		currentBuffer.append("\r\n");
		currentBuffer.append(currentObj);
		switch(logLevel){
			case LogLevel.ALL:
				break;
			case LogLevel.DEBUG:
				curentLog.debug(currentBuffer.toString());
				curentLog.debug("■"+methodName+"○end■");
				break;
			case LogLevel.ERROR:
				curentLog.error(currentBuffer.toString());
				curentLog.debug("■"+methodName+"○end■");
				break;
			case LogLevel.FATAL:
				curentLog.equals(currentBuffer.toString());
				curentLog.debug("■"+methodName+"○end■");
				break;
			case LogLevel.INFO:
				curentLog.info(currentBuffer.toString());
				curentLog.debug("■"+methodName+"○end■");
				break;
			case LogLevel.OFF:
				break;
			case LogLevel.WARN:
				curentLog.warn(currentBuffer.toString());
				curentLog.debug("■"+methodName+"○end■");
				break;
			default:
		}
	}
	/**
	 * @param className 类
	 * @param logLevel 日志级别
	 * @param currentObj 纪录对象
	 */ 
	public static void log(Class  className,int logLevel,Object currentObj){
		Logger curentLog=Logger.getLogger(className);
		switch(logLevel){
			case LogLevel.ALL:
				break;
			case LogLevel.DEBUG:
				curentLog.debug(currentObj);
				break;
			case LogLevel.ERROR:
				curentLog.error(currentObj);
				break;
			case LogLevel.FATAL:
				curentLog.equals(currentObj);
				break;
			case LogLevel.INFO:
				curentLog.info(currentObj);
				break;
			case LogLevel.OFF:
				break;
			case LogLevel.WARN:
				curentLog.warn(currentObj);
				break;
			default:
		}
	}
	/**
	 * @param className 类
	 * @param logLevel 日志级别
	 * @param currentObj 信息对象
	 * @param error 异常
	 */
	public static void log(Class  className,int logLevel,Object currentObj, Throwable error){
		Logger curentLog=Logger.getLogger(className);
		switch(logLevel){
			case LogLevel.ALL:
				break;
			case LogLevel.DEBUG:
				curentLog.debug(currentObj,error);
				break;
			case LogLevel.ERROR:
				curentLog.error(currentObj,error);
				break;
			case LogLevel.FATAL:
				curentLog.error(currentObj,error);
				break;
			case LogLevel.INFO:
				curentLog.info(currentObj,error);
				break;
			case LogLevel.OFF:
				break;
			case LogLevel.WARN:
				curentLog.warn(currentObj,error);
				break;
			default:
		}
	}
	public static void main(String[] args) {
		
	}
}
