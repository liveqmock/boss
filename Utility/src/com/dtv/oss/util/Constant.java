package com.dtv.oss.util;

public class Constant{
  /**
   * common constant
   */
	
//	------Posten重构代码从这里开始 可重的代码拷贝到这个区域里面--------START----
	
	
//	------Posten重构代码到这里结束 可重的代码拷贝到这个区域里面--------END-------
  public static final boolean DEBUGMODE = true;
  public static final int SUCCESS = 0;
  public static final int FAIL = -1;
  public static final int PARENT_COMPANY_ID = 1;

	//T_OpGroup	OpGroupLevel            SET_S_OPGROUPLEVEL操作员组对应的级别
	public static final String OPGROUP_LEVEL_PARENT_COMPANY	= "C";		//总公司操作员组
	public static final String OPGROUP_LEVEL_FILIALE		= "B";		//分公司操作员组
	public static final String OPGROUP_LEVEL_STREET			= "S";		//街道站操作员组

	//T_Organization	OrgType         SET_S_ORGANIZATIONORGTYPE节点类型
	public static final String ORGANIZATION_TYPE_PARENT_COMPANY	= "C";		//总公司
	public static final String ORGANIZATION_TYPE_DEPARTMENT		= "D";		//部门
	public static final String ORGANIZATION_TYPE_FILIALE		= "B";		//分公司
	public static final String ORGANIZATION_TYPE_PARTENER		= "P";		//合作伙伴
	public static final String ORGANIZATION_TYPE_SHOP			= "O";		//门店
	public static final String ORGANIZATION_TYPE_STREET			= "S";		//街道站

	//T_Organization	OrgSubType             SET_S_ORGANIZATIONORGCATEGORY组织机构节点类型的细分信息
	public static final String ORGANIZATION_ORGSUBTYPE_ENGINEER			= "E";		//总师办
	public static final String ORGANIZATION_ORGSUBTYPE_MARKET			= "M";		//市场部
	public static final String ORGANIZATION_ORGSUBTYPE_MATERIAL			= "L";		//物资部
	public static final String ORGANIZATION_ORGSUBTYPE_CUSTOMER_SERVICE	= "C";		//客服中心
	public static final String ORGANIZATION_ORGSUBTYPE_CALL_CENTER		= "T";		//电话中心
	public static final String ORGANIZATION_ORGSUBTYPE_FINANCIAL		= "F";		//营帐中心
	public static final String ORGANIZATION_ORGSUBTYPE_FRONT_MAINTAIN	= "D";		//前端运维
	public static final String ORGANIZATION_ORGSUBTYPE_OPERATION_ACCEPT	= "I";		//业务受理部
	public static final String ORGANIZATION_ORGSUBTYPE_FILIALE_MAINTAIN	= "B";		//分公司运维部

    //T_Organization	OrgSubType             SET_S_ORGANIZATIONPARTNERCATEGORY
	public static final String ORGANIZATION_ORGSUBTYPE_PROXY		= "S";		//代理商
	public static final String ORGANIZATION_ORGSUBTYPE_INSTALL		= "Z";		//安装服务提供商
	public static final String ORGANIZATION_ORGSUBTYPE_PAYMENT		= "P";		//付费渠道
	public static final String ORGANIZATION_ORGSUBTYPE_PRODUCT		= "A";		//产品提供商
	public static final String ORGANIZATION_ORGSUBTYPE_DEVICE		= "G";		//设备提供商

	//T_SystemLog	LogClass                SET_S_LOGCLASS日志级别
	public static final String SYSTEMLOG_LOGCLASS_NORMAL	= "N";		//一般操作信息
	public static final String SYSTEMLOG_LOGCLASS_KEY		= "C";		//关键操作信息
	public static final String SYSTEMLOG_LOGCLASS_WARNING	= "W";		//警告信息
	public static final String SYSTEMLOG_LOGCLASS_ERROR		= "E";		//错误信息

	//T_SystemLog	LogType                 SET_S_LOGTYPE日志信息
	public static final String SYSTEMLOG_LOGTYPE_SYSTEM			= "S";		//系统
	public static final String SYSTEMLOG_LOGTYPE_APPLICATION	= "A";		//应用程序

	//T_OrgGovernedDistrict	Flag            SET_S_ORGGOVERNEDDISTRICTFLAG组织机构类型标记
	public static final String ORGGOVERNEDDISTRICT_FLAG_FILIALE		= "S";		//分公司管辖的区
	public static final String ORGGOVERNEDDISTRICT_FLAG_DISTRICT	= "T";		//区管辖的街道
	//T_DistrictSetting SET_G_DISTRICTTYPE行政区类型	P	省、直辖市
	public static final String 	DISTRICTSETTING_TYPE_CITY		= "C";		//地、市
	public static final String 	DISTRICTSETTING_TYPE_DISTRICT	= "T";		//区、县
	
	
	//以下为调查用的 ，add by zhouxushun , 2005-12-09
	//SET_C_BIDIMCONFIGSETTINGCONFIGTYPE
	public static final String SERVEY_CALL_BACK="C";						//回访
	public static final String SERVEY_DIAGNOSIS="D";						//诊断
	public static final String SERVEY_MARKET_INFO="M";						//市场
	//SET_C_BIDIMCONFIGSETTINGCONFIGSUBTYPE
	public static final String SERVEY_CALL_BACK_SUB_C="C";					//投诉回访
	public static final String SERVEY_CALL_BACK_SUB_N="N";					//普通诊断
	public static final String SERVEY_CALL_BACK_SUB_O="O";					//开户回访
	public static final String SERVEY_CALL_BACK_SUB_P="P";					//专业诊断
	public static final String SERVEY_CALL_BACK_SUB_R="R";					//报修回访
	//SET_C_BIDIMCONFIGSETTINGVALUETYPE
	public static final String SERVEY_TAGTYPE_SELECT="S";				//下拉选择框
	public static final String SERVEY_TAGTYPE_CHECKBOX="C";			//多选框
	public static final String SERVEY_TAGTYPE_TEXT="M";                  //文本框
	public static final String SERVEY_TAGTYPE_RADIO="R";                //单选框
	public static final String SERVEY_TAGTYPE_DATE="D";                //日期框
	
	//SET_G_GENERALSTATUS
	public static final String GENERAL_STATUS_VALIDATE="V";					//有效
	
	//SET_G_YESNOFLAG
	public static final String GENERAL_FLAG_Y="Y";							//是
	public static final String GENERAL_FLAG_N="N";							//否
	
	//SET_V_CUSTSERVICEINTERACTIONSTATUS
	public static final String CUSTSERVICEINTERACTION_STATUS_NEW = "N";             //业务受理单的状态：创建
	
    //	SET_F_AIREFERTYPE
	public static final String AIREFERTYPE_M = "M"; 						//费用-综合总帐来源类型：受理单产生
	public static final String AIREFERTYPE_A = "A"; 						//费用-综合总帐来源类型：出帐产生
	public static final String AIREFERTYPE_P = "P"; 						//费用-综合总帐来源类型：报修单

    //	SET_F_PAYMENTRECORDSOURCETYPE
	public static final String PAYMENTRECORDSOURCETYPE_OTHER = "O";			//支付记录-支付记录来源类型：其它
	public static final String PAYMENTRECORDSOURCETYPE_REPAIR = "P";		//支付记录-支付记录来源类型：报修单
	public static final String PAYMENTRECORDSOURCETYPE_ACCEPT = "M"; 		//支付记录-支付记录来源类型：受理单
     
//	SET_F_BRFEETYPE
	public static final String BRFEETYPE_PREPAY = "P"; 					//营业费用类别：预收的收视费
}