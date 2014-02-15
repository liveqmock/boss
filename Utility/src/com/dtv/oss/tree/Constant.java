package com.dtv.oss.tree;

public class Constant{
  /**
   * common constant
   */
	public static final boolean DEBUGMODE = true;
	public static final int SUCCESS = 0;
	public static final int FAIL = -1;
	public static final Integer SUCCESS_OBJECT = new Integer(0);
	public static final Integer FAIL_OBJECT = new Integer(-1);
	public static final int PARENT_COMPANY_ID = 1;
	public static final String 	DISTRICTSETTING_TYPE_DISTRICT	= "T";		//区、县
	public static final String COMMAND_DEFAULT_STRING="";
	//SET_S_LOGCLASS：日志级别; 表：T_SYSTEMLOG：LOGCLASS
	public static final String LOGCLASS_ERROR = "E"; //错误信息
	public static final String LOGCLASS_WARNING = "W"; //警告信息
	public static final String LOGCLASS_NORMAL = "N"; //一般操作信息
	public static final String LOGCLASS_KEYOPERATION = "C"; //关键操作信息
	
	//SET_S_LOGTYPE：日志信息; 表：T_SYSTEMLOG：LOGTYPE
	public static final String LOGTYPE_APPLICATION = "A"; //应用程序
	public static final String LOGTYPE_SYSTEM = "S"; //系统
	public static final String LOGTYPE_SELFSERVICE = "C"; //自服务

}