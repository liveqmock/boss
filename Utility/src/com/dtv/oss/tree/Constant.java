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
	public static final String 	DISTRICTSETTING_TYPE_DISTRICT	= "T";		//������
	public static final String COMMAND_DEFAULT_STRING="";
	//SET_S_LOGCLASS����־����; ��T_SYSTEMLOG��LOGCLASS
	public static final String LOGCLASS_ERROR = "E"; //������Ϣ
	public static final String LOGCLASS_WARNING = "W"; //������Ϣ
	public static final String LOGCLASS_NORMAL = "N"; //һ�������Ϣ
	public static final String LOGCLASS_KEYOPERATION = "C"; //�ؼ�������Ϣ
	
	//SET_S_LOGTYPE����־��Ϣ; ��T_SYSTEMLOG��LOGTYPE
	public static final String LOGTYPE_APPLICATION = "A"; //Ӧ�ó���
	public static final String LOGTYPE_SYSTEM = "S"; //ϵͳ
	public static final String LOGTYPE_SELFSERVICE = "C"; //�Է���

}