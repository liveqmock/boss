package com.dtv.oss.web.util;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */

public class CommonKeys {


	public static final String SYSTEM_CONFIG_MENU_KEY="com.dtv.oss.dto.SystemConfigMenu";
	//预约的参数
    public static final String ACTION_OF_BOOKING ="0";
    //预约开户的参数
    public static final String ACTION_OF_BOOKINGACCOUNT ="1";
    //门店开户的参数
    public static final String ACTION_OF_SHOPACCOUNT ="2";
    //代理商预约开户的参数
    public static final String ACTION_OF_BOOKINGAGENT ="3";
    //客户资料单独录入
    public static final String ACTION_OF_BOOK_DIRECTLY ="4";
    //模拟电视开户的参数
    public static final String ACTION_OF_CATVSHOPACCOUNT ="5";
    
    //代理商预约确认
    public static final String CONFIRM_OF_BOOKING ="0";

    public static final String SA_TRANFER_NEW_USER="4";
    
    //预约，代理商预约取消
    public static final String CANCEL_OF_BOOKING ="1";

    //预约，代理商预约修改
    public static final String MODIFY_OF_BOOKING ="2";

    //费用存储变量
    public static final String FEE_SESSION_NAME="FeeCommandResponse";

    //调查显示的类型
    //控件格式
    public static final String SERVEY_SHOW_TYPE_TEXT="text";
    //文本输出+隐藏
    public static final String SERVEY_SHOW_TYPE_LABEL="label";
    //隐藏
    public static final String SERVEY_SHOW_TYPE_HIDE="hide";
    //单远
    public static final String SERVEY_SHOW_TYPE_RADIO="radio";
    //远
    public static final String SERVEY_SHOW_TYPE_CHECKBOX="checkbox";
    //列表
    public static final String SERVEY_SHOW_TYPE_SELECT="select";
    //密码
    public static final String SERVEY_SHOW_TYPE_PASSWORD="password";


    // 以上是com.dtv.oss.service.util.CommonConstDefinition没有的字段；
    // 如果以后要把CommonConstDefinition 的内容覆盖CommonKeys内容，这些字段一定要有

    //current customer session name
    public static final String CURRENT_CUSTOMER_SESSION_NAME = "ScndKeys.CurrentCustomer";
    
    //客户树上是否并列显示所有业务帐户
    public static final String CURRENT_CUSTOMER_ISSHOWALLSA_SESSION_NAME = "ScndKeys.IsShowNumberSA";
    
    //客户树,客户下业务帐户的个数
    public static final String CURRENT_CUSTOMER_SACOUNT_SESSION_NAME = "ScndKeys.SACOUNT";
    
    //客户树,客户下业务帐户ID
    public static final String CURRENT_CUSTOMER_SAID_SESSION_NAME = "SAIDSession";
    
    //客户树,客户ID
    public static final String CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME = "PID";
    
    //t_methodofpayment
    //手工预缴
    public static final int METHOD_OF_PAYMENT_PREPAY = 7;
    //工商银行
    public static final int METHOD_OF_PAYMENT_ICBC = 102;
    //招商银行
    public static final int METHOD_OF_PAYMENT_CMB = 308;
    //上海银行
    public static final int METHOD_OF_PAYMENT_BSH = 311;

    //客户资料单独录入时的新增客户帐户信息提交
    public static final int OPEN_SERVICE_ACCOUNT_DIRECTLY = 9;
    //	新增业务帐户 检查 产品包、优惠和帐户是否有未付帐单
	public static final int CHECK_BOOKINGUSER_PRODUCTPG_AND_INVOICE = 10 ;
    //检查客户信息
    public static final int CHECK_CUSTOMERINFO= 11;
    //检查开户的产品包和优惠包
	public static final int CHECK_PRODUCTPG_CAMPAINPG=12 ;
    //检查开户的硬件产品
	public static final int CHECK_HARDPRODUCTINFO =13 ;
	public static final int CHECK_BATCHBUY_PRODUCTINFO =132 ;
    //检查费用信息
	public static final int CHECK_FREEINFO  =14;
	//	新增业务帐户 检查 产品包、优惠和帐户是否有未付帐单
	public static final int CHECK_PRODUCTPG_AND_INVOICE = 15 ;
	//	新增业务帐户 提交 用于门店增机
	public static final int OPEN_SERVICE_ACCOUNT = 16 ;	
	public static final int OPEN_BATCHBUY_SERVICE_ACCOUNT = 162 ;	
	//检查模拟电视开户
	public static final int CHECK_CATV_CUSTOMERINOF =17 ;
	//计算模拟开户费用
	public static final int CACULATE_OPEN_CATV_FEE =18 ;
    //	新增业务帐户 提交 用于预约增机
	public static final int OPEN_BOOKINGUSER_SERVICE_ACCOUNT = 33 ;
	//  新增业务帐户 预约提交 用于预约增机
	public static final int OPEN_BOOKINGUSER_SERVICE_ACCOUNT_FORBOOKING =34;
	
	//  新增业务帐户 预约提交 用于预约增机修改
	public static final int OPEN_BOOKINGUSER_SERVICE_ACCOUNT_FORMODIFY =35;
	//  新增业务帐户 预约提交 用于预约增机修改
	public static final int OPEN_BOOKINGUSER_SERVICE_ACCOUNT_FORCANCLE =37;
	
	//	业务帐户 批量暂停
	public static final int BATCH_SERVICE_ACCOUNT_PAUSE =38;
    //	业务帐户 批量恢复
	public static final int BATCH_SERVICE_ACCOUNT_RESUME =39;
	
	//修改客户设备来源
	public static final int CUSTOMER_DEVICE_PROVIDE_MODIFY=40;
	//修改客户产品付费帐户
	public static final int CUSTOMER_PRODUCT_ACCOUNT_MODIFY =41;
	
	//	新增产品 提交
	public static final int ADD_PRODUCT = 17 ;
	//	业务帐户 暂停
	public static final int SERVICE_ACCOUNT_PAUSE = 18 ;	
    //	业务帐户 恢复
	public static final int SERVICE_ACCOUNT_RENT = 50 ;
	//	业务帐户 恢复
	public static final int SERVICE_ACCOUNT_RESUME = 19 ;
	//	业务帐户 销户
	public static final int SERVICE_ACCOUNT_CLOSE = 20 ;
	//	业务帐户 过户
	public static final int SERVICE_ACCOUNT_TRANSFER = 21 ;
	//	业务帐户  重授权
	public static final int SERVICE_ACCOUNT_RESEND = 42 ;
	//模拟业务 停机
	public static final int CATV_SERVICE_PAUSE = 43 ;
	//模拟业务 复机
	public static final int CATV_SERVICE_RESUME = 44 ;
	//	产品 暂停
	public static final int CUSTOMER_PRODUCT_PAUSE = 22;
	//	产品 恢复
	public static final int CUSTOMER_PRODUCT_RESUME = 23 ;
	//	产品 取消
	public static final int CUSTOMER_PRODUCT_CANCEL = 24 ;
	//	产品 升级
	public static final int CUSTOMER_PRODUCT_UPGRADE = 25 ;
	//	产品 降级
	public static final int CUSTOMER_PRODUCT_DOWNGRADE = 26 ;
	//	重发授权
	public static final int CUSTOMER_PRODUCT_RESEND = 27 ;
	//	机卡解配对
	public static final int CUSTOMER_PRODUCT_CANCELPARTNERSHIP = 28 ;
	//	机卡配对
	public static final int CUSTOMER_PRODUCT_PARTNERSHIP = 29 ;
	//	消除密码
	public static final int CUSTOMER_PRODUCT_CLEARPASSWORD = 30 ;
	//	发送消息
	public static final int CUSTOMER_PRODUCT_SENDMAIL = 31 ;
	//	发送消息
	public static final int SERVICE_ACCOUNT_CODE_UPDATE = 32 ;
	//  预约产品修改
	public static final int BOOK_PRODUCT_MODIFY = 33;
    //	产品 迁移
	public static final int CUSTOMER_PRODUCT_MOVE = 55;	
    //	业务帐户预退户
	public static final int SERVICE_ACCOUNT_BEFOREHAND_CLOSE = 56 ;
    //	业务帐户实退户
	public static final int SERVICE_ACCOUNT_REAL_CLOSE = 57 ;	
    //	设备更换
	public static final int DEVICE_SWAP =58 ;
    //	设备升级
	public static final int DEVICE_UPGRADE=59 ;

    //安装类型：自安装
	public static final String CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL = "S";
    //安装类型：上门安装
	public static final String CUSTSERVICEINTERACTIONIN_STYPE_INSTALL = "C";
    
	//	SET_M_CAMPAIGNCAMPAIGNTYPE
	//促销活动类型：套餐
	public static final String CAMPAIGNCAMPAIGNTYPE_OPEN = "B";
    //促销活动类型：普通促销
	public static final String CAMPAIGNCAMPAIGNTYPE_NORMAL = "A";
//    //促销活动类型：团购套餐
//	public static final String CAMPAIGNCAMPAIGNTYPE_GROUPBARGAIN = "T";
//    //促销活动类型：团购或开户套餐
//	public static final String CAMPAIGNCAMPAIGNTYPE_GROUPBARGAIN_OR_OPEN = "O";

    /************************	SET_C_CUSTOPENSOURCETYPE  */
    //	客户来源类型：门店
	public static final String OPENSOURCETYPE_BRANCH = "O";
    //	客户来源类型：街道站
	public static final String OPENSOURCETYPE_STREETSTATION = "S";
    //	客户来源类型：分公司代理商
	public static final String OPENSOURCETYPE_PROXY = "P";
    //	客户来源类型：部门
	public static final String OPENSOURCETYPE_DEPARTMENT = "D";
    //	客户来源类型：电话销售
	public static final String OPENSOURCETYPE_TELEPHONESALE = "T";
    //	客户来源类型：呼叫中心受理
	public static final String OPENSOURCETYPE_CALLCENTER = "C";
    //	客户来源类型：团购购买
	public static final String OPENSOURCETYPE_GROUPBARGAIN = "G";
    //	客户来源类型：卖场受理
	public static final String OPENSOURCETYPE_SUPERMARKET = "M";
    //	客户来源类型：现场设摊代理
	public static final String OPENSOURCETYPE_RETAIL = "X";
    //	客户来源类型：有线通代理
	public static final String OPENSOURCETYPE_CABLEPROXY = "Y";
    //	客户来源类型：行业代理
	public static final String OPENSOURCETYPE_JOBPROXY = "H";
    //	客户来源类型：房产配套
	public static final String OPENSOURCETYPE_HOUSEMATCHED = "F";
    //	客户来源类型：厂商预授权
	public static final String OPENSOURCETYPE_COMPANYSEND = "R";
	public static final String OPENSOURCETYPE_A = "A";

    //SET_S_ORGANIZATIONORGTYPE
	public static final String ORGANIZATIONORGTYPE_GENERALCOMPANY = "C"; 	//节点类型：总公司
	public static final String ORGANIZATIONORGTYPE_DEPARTMENT = "D"; 		//节点类型：部门
	public static final String ORGANIZATIONORGTYPE_SUBCOMPANY = "B"; 		//节点类型：分公司
	public static final String ORGANIZATIONORGTYPE_PARTNER = "P"; 			//节点类型：合作伙伴
	public static final String ORGANIZATIONORGTYPE_BRANCH = "O"; 			//节点类型：门店
	public static final String ORGANIZATIONORGTYPE_STREETSTATION = "S"; 	//节点类型：街道站

   //	SET_V_CSIPROCESSLOGACTION
	public static final String CSIPROCESSLOG_ACTION_NEW = "N"; 					    //受理单处理操作：新建
	public static final String CSIPROCESSLOG_ACTION_APPLY = "A";   				    //受理单处理操作：确认
	public static final String CSIPROCESSLOG_ACTION_CANCEL = "C";                   //受理单处理操作：取消
	public static final String CSIPROCESSLOG_ACTION_RETURNMONEY = "W";              //受理单处理操作：退款
	public static final String CSIPROCESSLOG_ACTION_SUCCESS = "S";                  //受理单处理操作：受理成功
	public static final String CSIPROCESSLOG_ACTION_FAIL = "F";                     //受理单处理操作：受理失败
	public static final String CSIPROCESSLOG_ACTION_PAYFEE4BOOKING = "B";           //受理单处理操作：预约开户费用支付
	public static final String CSIPROCESSLOG_ACTION_CALLBACK = "L";                 //受理单处理操作：回访
	public static final String CSIPROCESSLOG_ACTION_UPDATE = "M";                   //受理单处理操作：修改
	public static final String CSIPROCESSLOG_ACTION_SETCALLBACKFLAG = "O";          //受理单处理操作：回访暂存
	public static final String CSIPROCESSLOG_ACTION_UPDATEFEE2PAYMENT = "D";        //受理单处理操作：调帐
	public static final String CSIPROCESSLOG_ACTION_BOOKINGCANCEL = "C1";           //受理单处理操作：受理预约取消
	public static final String CSIPROCESSLOG_ACTION_INSTALLCANCEL = "C2";           //受理单处理操作：安装预约取消
	public static final String CSIPRPCESSLOG_ACTION_PROCESS ="P";                   //受理单处理操作：正在进行

    //	SET_V_CUSTSERVICEINTERACTIONTYPE
	public static final String CUSTSERVICEINTERACTIONTYPE_GO = "GO"; 		//业务受理单的类型：集团客户开户
	public static final String CUSTSERVICEINTERACTIONTYPE_GS = "GS"; 		//业务受理单的类型：子客户开户
	public static final String CUSTSERVICEINTERACTIONTYPE_GM = "GM"; 		//业务受理单的类型：子客户转个人客户	

	public static final String CUSTSERVICEINTERACTIONTYPE_CO = "CO";        //业务受理单的类型：客户开户（资料单独录入）
	public static final String CUSTSERVICEINTERACTIONTYPE_BK = "BK"; 		//业务受理单的类型：预约
	public static final String CUSTSERVICEINTERACTIONTYPE_OS = "OS"; 		//业务受理单的类型：门店开户
	public static final String CUSTSERVICEINTERACTIONTYPE_OB = "OB"; 		//业务受理单的类型：预约开户
	public static final String CUSTSERVICEINTERACTIONTYPE_PS = "PS";		//业务受理单的类型：暂停客户产品
	public static final String CUSTSERVICEINTERACTIONTYPE_RM = "RM";		//业务受理单的类型：恢复客户产品
	public static final String CUSTSERVICEINTERACTIONTYPE_TS = "TS";		//业务受理单的类型：原地过户
	public static final String CUSTSERVICEINTERACTIONTYPE_TD = "TD";		//业务受理单的类型：异地过户
	public static final String CUSTSERVICEINTERACTIONTYPE_MD = "MD";		//业务受理单的类型：迁移
	public static final String CUSTSERVICEINTERACTIONTYPE_AD = "AD";		//业务受理单的类型：预存
	public static final String CUSTSERVICEINTERACTIONTYPE_PI = "PI";		//业务受理单的类型：支付帐单
//	public static final String CUSTSERVICEINTERACTIONTYPE_CU = "CU";		//业务受理单的类型：客户信息变更 //2005-11-24 by Wesley Shu
	public static final String CUSTSERVICEINTERACTIONTYPE_PA = "PA";		//业务受理单的类型：客户产品变更
	public static final String CUSTSERVICEINTERACTIONTYPE_PN = "PN";		//业务受理单的类型：客户产品新增
	public static final String CUSTSERVICEINTERACTIONTYPE_IA = "IA";		//业务受理单的类型：信息变更
	public static final String CUSTSERVICEINTERACTIONTYPE_CC = "CC";		//业务受理单的类型：销户
	public static final String CUSTSERVICEINTERACTIONTYPE_WC = "WC";		//业务受理单的类型：退户
	public static final String CUSTSERVICEINTERACTIONTYPE_FR = "FR";		//业务受理单的类型：退还押金
	public static final String CUSTSERVICEINTERACTIONTYPE_FS = "FS";		//业务受理单的类型：没收押金
	public static final String CUSTSERVICEINTERACTIONTYPE_OC = "OC";		//业务受理单的类型：取消帐户
	public static final String CUSTSERVICEINTERACTIONTYPE_DS = "DS"; 		//业务受理单的类型：设备更换
	public static final String CUSTSERVICEINTERACTIONTYPE_CP = "CP";		//业务受理单的类型：取消客户产品
	public static final String CUSTSERVICEINTERACTIONTYPE_AP = "AP";		//业务受理单的类型：变更客户产品属性
	public static final String CUSTSERVICEINTERACTIONTYPE_AC = "AC";		//业务受理单的类型：变更产品优惠
	public static final String CUSTSERVICEINTERACTIONTYPE_PU = "PU";		//业务受理单的类型：产品升级
	public static final String CUSTSERVICEINTERACTIONTYPE_PD = "PD";		//业务受理单的类型：产品降级
	public static final String CUSTSERVICEINTERACTIONTYPE_CS = "CS";		//业务受理单的类型：创建排程
	public static final String CUSTSERVICEINTERACTIONTYPE_UO = "UO";		//业务受理单的类型：新开用户
	public static final String CUSTSERVICEINTERACTIONTYPE_OA = "OA";		//业务受理单的类型：新增账户
	public static final String CUSTSERVICEINTERACTIONTYPE_BU = "BU";		//业务受理单的类型：预约增机
	public static final String CUSTSERVICEINTERACTIONTYPE_UP = "UP";		//业务受理单的类型：暂停用户
	public static final String CUSTSERVICEINTERACTIONTYPE_RT = "RT";		//业务受理单的类型：设备转租
	public static final String CUSTSERVICEINTERACTIONTYPE_SP = "SP";		//业务受理单的类型：用户预退
	public static final String CUSTSERVICEINTERACTIONTYPE_UR = "UR";		//业务受理单的类型：恢复用户
	public static final String CUSTSERVICEINTERACTIONTYPE_UC = "UC";		//业务受理单的类型：用户销户
	public static final String CUSTSERVICEINTERACTIONTYPE_UT = "UT";		//业务受理单的类型：用户过户
	public static final String CUSTSERVICEINTERACTIONTYPE_BP = "BP";		//业务受理单的类型：预约新增客户产品
	public static final String CUSTSERVICEINTERACTIONTYPE_BDS = "BDS";		//业务受理单的类型：套餐转换    
	public static final String CUSTSERVICEINTERACTIONTYPE_BDP = "BDP";		//业务受理单的类型：套餐预付
	public static final String CUSTSERVICEINTERACTIONTYPE_BDE = "BDE";		//业务受理单的类型：套餐延期
	public static final String CUSTSERVICEINTERACTIONTYPE_BDC = "BDC";		//业务受理单的类型：套餐取消
	public static final String CUSTSERVICEINTERACTIONTYPE_CAO = "CAO";		//业务受理单的类型：模拟业务开户
	public static final String CUSTSERVICEINTERACTIONTYPE_CAA = "CAA";		//业务受理单的类型：模拟业务增端
	public static final String CUSTSERVICEINTERACTIONTYPE_CAS = "CAS";		//业务受理单的类型：模拟电视业务停机
	public static final String CUSTSERVICEINTERACTIONTYPE_CAR = "CAR";		//业务受理单的类型：模拟电视业务复机
	public static final String CUSTSERVICEINTERACTIONTYPE_MB = "MB";		//业务受理单的类型：手工收费
	public static final String CUSTSERVICEINTERACTIONTYPE_MAC = "MAC";		//业务受理单的类型：手工授予促销	
	
	public static final String METHODOFPAYMENT_KHBX ="KHBX"   ;             //支付方式--- 报修单
	public static final String METHODOFPAYMENT_SLTZ ="SLTZ"   ;             //支付方式--- 受理单调帐
	public static final String METHODOFPAYMENT_BXTZ ="BXTZ"   ;             //支付方式--- 报修单调帐
	public static final String METHODOFPAYMENT_ZHTZ ="ZHTZ"   ;             //支付方式--- 帐户调帐
	public static final String METHODOFPAYMENT_ZDTZ ="ZDTZ"   ;             //支付方式--- 帐单调帐 
	public static final String METHODOFPAYMENT_KHGD ="KHGD"   ;             //支付方式--工单
	
	//SET_V_CUSTSERVICEINTERACTIONSTATUS
	public static final String CUSTSERVICEINTERACTION_STATUS_NEW = "N";             //业务受理单的状态：创建
	public static final String CUSTSERVICEINTERACTION_STATUS_WAIT = "W";		    //业务受理单的状态：待处理
	public static final String CUSTSERVICEINTERACTION_STATUS_CANCEL = "C";          //业务受理单的状态：取消
	public static final String CUSTSERVICEINTERACTION_STATUS_SUCCESS = "S";         //业务受理单的状态：受理成功
	public static final String CUSTSERVICEINTERACTION_STATUS_FAIL = "F";            //业务受理单的状态：受理不成功
	public static final String CUSTSERVICEINTERACTION_STATUS_RETURNMONEY = "R";     //业务受理单的状态：已退费
	public static final String CUSTSERVICEINTERACTION_STATUS_PROCESS = "P";         //业务受理单的状态：正在处理
	public static final String CUSTSERVICEINTERACTION_STATUS_BOOKINGCANCEL = "C1";  //业务受理单的状态：受理预约取消
	public static final String CUSTSERVICEINTERACTION_STATUS_INSTALLCANCEL = "C2";  //业务受理单的状态：安装预约取消

	//SET_C_CUSTOMERSTATUS
	public static final String CUSTOMER_STATUS_POTENTIAL = "P";				//客户状态：潜在
	public static final String CUSTOMER_STATUS_NORMAL = "N";				//客户状态：正常
	public static final String CUSTOMER_STATUS_CANCEL = "C";				//客户状态：取消

	//SET_C_SERVICEACCOUNTSTATUS
	public static final String SERVICE_ACCOUNT_STATUS_INITIAL = "I";			//业务帐户状态：初始
	public static final String SERVICE_ACCOUNT_STATUS_HALT = "H";				//业务帐户状态：主动暂停
	public static final String SERVICE_ACCOUNT_STATUS_SUSPEND = "S";			//业务帐户状态：系统暂停
	public static final String SERVICE_ACCOUNT_STATUS_NORMAL = "N";				//业务帐户状态：正常
	public static final String SERVICE_ACCOUNT_STATUS_CANCEL = "C";				//业务帐户状态：取消

    //SET_F_PAYMENTRECORDTYPE
	public static final String PAYMENTRECORD_TYPE_PRESAVE = "P"; 			//支付记录-付费纪录的类型：预付费
	public static final String PAYMENTRECORD_TYPE_ACCEPT_CASE = "C";		//支付记录-付费纪录的类型：支付受理费用
	public static final String PAYMENTRECORD_TYPE_RETURN_FEE = "RF";		//支付记录-付费纪录的类型：退费
	public static final String PAYMENTRECORD_TYPE_BILLING = "N"; 			//支付记录-付费纪录的类型：客户帐单付费
	public static final String PAYMENTRECORD_TYPE_RETURN_RR = "RR";			//支付记录-付费纪录的类型：预存退费
	
	//SET_C_CUSTOMERSTYLE
	public static final String CUSTOMERSTYLE_SINGLE = "S"; 					//客户类型：个人客户
	public static final String CUSTOMERSTYLE_GROUP = "G"; 					//客户类型：集团客户
	
	public static final String PAYMENTRECORD_TYPE_ONINVOICE = "N"; 			//支付记录-付费纪录的类型：现金付帐
	public static final String PAYMENTRECORD_TYPE_TPRESAVE = "TP"; 			//支付记录-付费纪录的类型：虚拟货币预付费
	public static final String PAYMENTRECORD_TYPE_PUTINTO = "X"; 			//支付记录-付费纪录的类型：转入(团购等)
	public static final String PAYMENTRECORD_TYPE_TONINVOICE = "TN"; 		//支付记录-付费纪录的类型：虚拟货币付帐单
	public static final String PAYMENTRECORD_TYPE_RETURNMONEY = "D"; 		//支付记录-付费纪录的类型：提款
	public static final String PAYMENTRECORD_TYPE_TOKENINVOICE = "CN"; 		//支付记录-付费纪录的类型：抵扣券付帐单
	public static final String PAYMENTRECORD_TYPE_TOKENPRESAVE = "CP"; 		//支付记录-付费纪录的类型：抵扣券预付费

	//SET_F_PAYMENTRECORDSOURCETYPE
	public static final String PAYMENTRECORDSOURCETYPE_OTHER = "O";			//支付记录-支付记录来源类型：其它
	public static final String PAYMENTRECORDSOURCETYPE_REPAIR = "P";		//支付记录-支付记录来源类型：报修单
	public static final String PAYMENTRECORDSOURCETYPE_ACCEPT = "M"; 		//支付记录-支付记录来源类型：受理单
	public static final String PAYMENTRECORDSOURCETYPE_ADJUST = "T"; 		//支付记录-支付记录来源类型：调帐

    //	SET_F_AIREFERTYPE
	public static final String AIREFERTYPE_M = "M"; 						//费用-综合总帐来源类型：受理单产生
	public static final String AIREFERTYPE_A = "A"; 						//费用-综合总帐来源类型：出帐产生
	public static final String AIREFERTYPE_P = "P"; 						//费用-综合总帐来源类型：报修单

    //	SET_F_PREPAYMENTTYPE
	public static final String PREPAYMENTTYPE_CASH = "C";					//预存款类型：现金
	public static final String PREPAYMENTTYPE_TRANSLUNARY="T";				//预存款类型：虚拟货币
    //	SET_F_PAYMENTTICKETTY
	public static final String PAYMENTTICKETTY_DK = "DK";					//支付记录-支付券类型：抵扣券
	public static final String PAYMENTTICKETTY_TG = "TG";					//支付记录-支付券类型：团购券

	//SET_F_MOPPAYTYPE
	public static final String MOPPAYTYPE_BP = "BP";					//银行代扣款
	public static final String MOPPAYTYPE_CH = "CH";					//柜面付费
	public static final String MOPPAYTYPE_CR = "CR";					//信用卡
	public static final String MOPPAYTYPE_DK = "DK";					//抵扣券支付
	public static final String MOPPAYTYPE_OL = "OL";					//在线付费
	public static final String MOPPAYTYPE_OT = "OT";					//其他
	public static final String MOPPAYTYPE_MB = "MB";					//手工预缴

	//SET_F_FTCREATINGMETHOD
	public static final String FTCREATINGMETHOD_A = "A"; 					//费用创建来源类型：批量计费
	public static final String FTCREATINGMETHOD_M = "M";					//费用创建来源类型: 手工

    //	SET_F_SETOFFFLAG
	public static final String SETOFFFLAG_W = "W"; 							//销帐记录-销帐标记：待销帐
	public static final String SETOFFFLAG_P = "P"; 							//销帐记录-销帐标记：部分销帐
	public static final String SETOFFFLAG_D = "D"; 							//销帐记录-销帐标记：已销帐
	//SET_G_YESNOFLAG
	public static final String FORCEDDEPOSITFLAG_Y = "Y";					//是强制预存
	public static final String FORCEDDEPOSITFLAG_N = "N";					//不是强制预存

	//SET_B_BATCHJOBSTATUS
	public static final String BATCH_JOB_STATUS_CANCEL="C";					//取消
	public static final String BATCH_JOB_STATUS_FAIL="F";					//执行失败
	public static final String BATCH_JOB_STATUS_WAIT="N";					//等待执行
	public static final String BATCH_JOB_STATUS_SUCESS="S";					//执行成功


    //	SET_V_CUSTSERVICEINTERACTIONPAYSTATUS
	public static final String CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY = "W";   //费用的支付状态：未付
	public static final String CUSTSERVICEINTERACTION_PAYSTATUS_PAYED = "D";        //费用的支付状态：已付
	public static final String CUSTSERVICEINTERACTION_PAYSTATUS_RETURNMONEY = "R";  //费用的支付状态：退费

	//SET_B_QUERYRSTATUS
	public static final String QUERY_STATUS_NEW="N";								//新建
	public static final String QUERY_STATUS_PROCESSING="W";							//正在处理
	public static final String QUERY_STATUS_SUCCESS="S";							//处理成功
	public static final String QUERY_STATUS_FAIL="F";								//处理失败
	public static final String QUERY_STATUS_CANCEL="C";								//取消
	public static final String QUERY_STATUS_REFERED="I";							//被引用

	//SET_B_SCHEDULETYPE
	public static final String SCHEDULE_TYPE_I="I";									//立即执行
	public static final String SCHEDULE_TYPE_S="S";									//定时执行

    //	SET_F_PDR_REFERRECORDTYPE
	public static final String F_PDR_REFERRECORDTYPE_C="C";              //来源记录类型：受理单
	public static final String F_PDR_REFERRECORDTYPE_F="F";              //来源记录类型：财务交易
	public static final String F_PDR_REFERRECORDTYPE_I="I";              //来源记录类型：帐单
	public static final String F_PDR_REFERRECORDTYPE_P="P";              //来源记录类型：报修单

	//SET_F_INVOICESTATUS 帐单状态
//	SET_F_INVOICESTATUS
	public static final String INVOICE_STATUS_WAIT = "W";					//帐单状态：待付费
	public static final String INVOICE_STATUS_OWE = "O";					//帐单状态：逾期
	public static final String INVOICE_STATUS_PAID = "D";					//帐单状态：已经付费
	public static final String INVOICE_STATUS_BAD = "B";					//帐单状态：坏帐注销
	public static final String INVOICE_STATUS_CANCEL = "C";					//帐单状态：取消
	public static final String INVOICE_STATUS_RETURNMONEY = "R";			//帐单状态：退款
	public static final String INVOICE_STATUS_QFZT = "T";					//帐单状态：欠费追讨
	public static final String INVOICE_STATUS_NORMAL = "N";					//帐单状态：正常
	
    //	SET_C_PROBLEMSTATUS
	public static final String CUSTOMERPROBLEM_STATUS_WAIT="W";					//报修单状态：待处理
	public static final String CUSTOMERPROBLEM_STATUS_PROCESSING="C";				//报修单状态：正在处理
	public static final String CUSTOMERPROBLEM_STATUS_SUCCESS="D";					//报修单状态：已排障
	//public static final String CUSTOMERPROBLEM_STATUS_UPGRADE="U";				//报修单状态：已升级
	public static final String CUSTOMERPROBLEM_STATUS_FAIL="S";					//报修单状态：维修不成功
	public static final String CUSTOMERPROBLEM_STATUS_TERMINAL="T";				//报修单状态：无法继续维修

	//SET_F_FTSTATUS
	public static final String SET_F_FTSTATUS_I="I";              //无效
	public static final String SET_F_FTSTATUS_L="L";              //锁定
	public static final String SET_F_FTSTATUS_P="P";              //预生成
	public static final String SET_F_FTSTATUS_V="V";              //有效


	//SET_F_ACCOUNTADJUSTMENTDIRECTION
	public static final String ADJUST_DIRECTION_R ="R"  ;                   //应收
	public static final String ADJUST_DIRECTION_P ="P" ;                     //实收

	//SET_F_ACCOUNTADJUSTMENTTYPE
	public static final String ADJUST_TYPE_C="C";    //维修单调帐
	public static final String ADJUST_TYPE_I="I";    //对帐单调帐
	public static final String ADJUST_TYPE_M="M";    //手工调帐
	public static final String ADJUST_TYPE_S="S";    //受理单调帐

	//SET_F_ADJUSTMENTREFERRECORDTYPE
	public static final String ADJUST_REFERCODETYPE_D ="D" ;   //抵扣记录
	public static final String ADJUST_REFERCODETYPE_F ="F" ;   //费用记录
	public static final String ADJUST_REFERCODETYPE_P ="P" ;   //预存记录
	public static final String ADJUST_REFERCODETYPE_C ="C" ;   //支付记录

    //	SET_F_BRFEETYPE
	public static final String BRFEETYPE_PREPAY = "P"; 					//营业费用类别：预收的收视费
	public static final String BRFEETYPE_DOWNGRADE = "B"; 				//营业费用类别：降级费
	public static final String BRFEETYPE_LOGOUT = "C"; 					//营业费用类别：销户费
	public static final String BRFEETYPE_DEVICE = "D"; 					//营业费用类别：设备费
	public static final String BRFEETYPE_TRANSFER = "F"; 				//营业费用类别：过户费
	public static final String BRFEETYPE_INSTALL = "I";					//营业费用类别：安装调试费
	public static final String BRFEETYPE_INFORMATION = "M"; 			//营业费用类别：信息费
	public static final String BRFEETYPE_OPENACCOUNT = "O"; 			//营业费用类别：开户费
	public static final String BRFEETYPE_RESUME = "R"; 					//营业费用类别：恢复手续费
	public static final String BRFEETYPE_SUSPEND = "S"; 				//营业费用类别：暂停费
	public static final String BRFEETYPE_MOVE = "T"; 					//营业费用类别：迁移费
	public static final String BRFEETYPE_UPGRADE = "U"; 				//营业费用类别：升级费
	public static final String BRFEETYPE_FORCEDDEPOSIT = "A"; 			//营业费用类别：押金
	public static final String BRFEETYPE_SERVICE = "E"; 				//营业费用类别：上门服务费

	
	//业务账户过户类型
	public static final String SA_TRANFER_TYPE_I="I";
	public static final String SA_TRANFER_TYPE_O="O";
	
	//产品属性取值方式
	public static final String PRODUCT_PROPERTY_MODE_FIXED="F";			//固定值
	public static final String PRODUCT_PROPERTY_MODE_SETTING="S";		//从属性配置表选择
	public static final String PRODUCT_PROPERTY_MODE_INPUT="I";			//输入
	public static final String PRODUCT_PROPERTY_MODE_RESOURCE="R";		//从系统资源分配
	
	//亲情号码
	public static final int FRIEND_PHONENO_CREATE=101;			//亲情号码增加
	public static final int FRIEND_PHONENO_DELETE=102;			//亲情号码删除
	
	//VOIP配置类型
	public static final String VOIP_QUERY_PRODUCT = "P";
	public static final String VOIP_QUERY_CONDITION = "C";
	public static final String VOIP_QUERY_GATEWAY = "G";
	//回访
	public static final String CALLBACKINFOTYPE_C="C";		
	
	//回访类型：投诉回访
	//合同状态:
	public static final String CONTRACTSTATUS_NEW="N"; 		//新建
	public static final String CONTRACTSTATUS_EFFECT="E";	//生效
	public static final String CONTRACTSTATUS_OPEN="U";		//开户
	public static final String CONTRACTSTATUS_CANCEL="C"; 	//取消
	//SET_P_PRODUCTCLASS
	public static final String PRODUCTCLASS_S="S" ;		//软件
	public static final String PRODUCTCLASS_H="H" ;		//硬件
	
	//文件上传保存路径
	public static final String FILE_UPLOAD_PATH="userUpload";
	//pageContext在request中的对象名称
	public static final String FILE_UPLOAD_PAGE_CONTEXT="FILE_UPLOAD_PAGE_CONTEXT";
	//集团大客户
	public static final int GET_DEVICE_LIST = 11 ;                               //得到硬件列表
	public static final int GET_FEE_LIST = 12 ;                                  //得到费用列表
	public static final int OPEN_CHILD_CUSTOMER = 16 ;                           //子客户开户
	
	public static final String BILLBOARD_GRADE_I="I";    	//紧急
	public static final String BILLBOARD_GRADE_N="N";    	//普通
	
	//SET_V_CSIPAYMENTSETTINGOPTION
	public static final String CSI_PAYMENT_OPTION_IM ="IM";   //立即支付
	public static final String CSI_PAYMENT_OPTION_OP ="OP";   //可选择
	public static final String CSI_PAYMENT_OPTION_IV ="IV";   //在月度帐单中支付
	public static final String CSI_PAYMENT_OPTION_SP ="SP";   //上门安装成功后收取
	
	//SET_C_DIAGNOSISINFOREFERSOURCETYPE 诊断信息来源的单据的类型
	public static final String DIAGNOSIS_INFOR_SOURCE_TYPE_A ="A";   //维修工单
	public static final String DIAGNOSIS_INFOR_SOURCE_TYPE_E ="E";   //报修单
	//SET_C_CONTRACTSTATUS  集团客户开户时的合同状态
	public static final String CONTRACTSTATUS_C ="C";   //作废
	public static final String CONTRACTSTATUS_E ="E";   //生效
	public static final String CONTRACTSTATUS_N ="N";   //创建
	public static final String CONTRACTSTATUS_U ="U";   //开户
	
	
	public static final String TERMINALDEVICESELECT="com.dtv.oss.web.flow.DeviceClassMap";
	public static final String SERVICEID="com.dtv.oss.web.flow.ServiceID";

	 //用于command返回给webAction 返回值的使用标志：
	public static final String COMMAND_ID_DEVICE="D";  //返回结果为设备
	public static final String COMMAND_ID_IMMEDIATEFEE="I";//返回结果为费用
//	SET_S_ROLEORGNIZATIONORGROLE组织角色
	public static final String ORGANIZATION_ROLE_REPAIR = "R";      //报修
	public static final String ORGANIZATION_ROLE_INSTALL = "I";     //安装
	public static final String ORGANIZATION_ROLE_COMPLAIN = "C";    //投诉
	public static final String ORGANIZATION_ROLE_SERVICE = "S";     //维修
	public static final String ORGANIZATION_ROLE_M ="M";            //模拟
	
    //	SET_W_JOBCARDTYPE
	public static final String JOBCARD_TYPE_REPAIR = "R";					//工单类型：维修
	public static final String JOBCARD_TYPE_INSTALLATION = "I";				//工单类型：安装
	public static final String JOBCARD_TYPE_CATVNETWORK = "C"; 				//工单类型: 模拟电视网络施工单
	
	//SET_W_JOBCARDSUBTYPE  工单子类型
	public static final String JOBCARD_SUBTYPE_F = "F";			//工单子类型：封端单
	public static final String JOBCARD_SUBTYPE_H = "H";			//工单子类型：恢复开通作业单
	public static final String JOBCARD_SUBTYPE_Q = "Q"; 		//工单子类型: 迁出停用作业单
	public static final String JOBCARD_SUBTYPE_R = "R";			//工单子类型：迁入开通作业单
	public static final String JOBCARD_SUBTYPE_T = "T";			//工单子类型：停用作业单
	public static final String JOBCARD_SUBTYPE_Z = "Z"; 		//工单子类型: 增端安装作业单
	public static final String JOBCARD_SUBTYPE_W = "W";			//工单子类型: 网络施工单
	
	//SET_V_PRINTSHEETTYPE  打印单据类型
	public static final String SET_V_PRINTSHEETTYPE_A = "A";			//打印单据类型：安装工单
	public static final String SET_V_PRINTSHEETTYPE_B = "B";			//打印单据类型：恢复开通作业单
	public static final String SET_V_PRINTSHEETTYPE_C = "C";			//打印单据类型：客户信息补打
	public static final String SET_V_PRINTSHEETTYPE_M = "M";			//打印单据类型：模拟电视工单
	public static final String SET_V_PRINTSHEETTYPE_S = "S";			//打印单据类型：受理单
	public static final String SET_V_PRINTSHEETTYPE_W = "W";			//打印单据类型：维修工单
	
	//SET_V_PRINTSHEETREASON  打印单据原因（只用于打印单据类型是受理单的情况）
	public static final String SET_V_PRINTSHEETREASON_F = "F";			//打印单据原因：收费单打印
	public static final String SET_V_PRINTSHEETREASON_R = "R";			//打印单据原因：登记单打印
	
	//SET_V_PRINTBLOCKDETAILTYPE  打印明细配置的字段取值方式
	public static final String SET_V_PRINTBLOCKDETAILTYPE_D = "D";			//字段取值方式：数据库SQL字段取值
	public static final String SET_V_PRINTBLOCKDETAILTYPE_T = "T";			//字段取值方式：常量，提示信息
	
	//发票地址类型 SET_FP_FAPIAOADDRESSTYPE  
	public static final String FAPIAO_ADDRESSTYPE_D = "D"; 		//发票地址类型：仓库
	public static final String FAPIAO_ADDRESSTYPE_O = "O"; 		//发票地址类型：组织机构
	//发票类型: SET_FP_FPTYPE
	public static final String FAPIAO_TYPE_NOR = "NOR"; 		//普通发票
	public static final String FAPIAO_TYPE_INC = "INC";         //增值税发票
	//发票/发票册状态：SET_FP_STATUS 
	public static final String FAPIAO_STATUS_SAV = "SAV"; 		//库存
	public static final String FAPIAO_STATUS_REC = "REC"; 		//已领取
	public static final String FAPIAO_STATUS_DRA = "DRA"; 		//已填开
	public static final String FAPIAO_STATUS_CAN = "CAN"; 		//已作废
	public static final String FAPIAO_STATUS_DIS = "DIS"; 		//已报废

	//发票流转动作：SET_FP_ACTION 
	public static final String FAPIAOTRANSITION_ACTION_IN = "IN"; 		//发票运入
	public static final String FAPIAOTRANSITION_ACTION_OUT = "OUT"; 	//发票领用
	public static final String FAPIAOTRANSITION_ACTION_BACK = "BACK";   //发票回库
	public static final String FAPIAOTRANSITION_ACTION_CANN = "CANN"; 	//发票调拨
	public static final String FAPIAOTRANSITION_ACTION_INOUT = "INOUT"; //发票运入即领用
	public static final String FAPIAOTRANSITION_ACTION_CANC = "CANC";   //发票作废
	public static final String FAPIAOTRANSITION_ACTION_DISCA = "DISCA"; //发票报废
	public static final String FAPIAOTRANSITION_ACTION_MO = "MO";		//发票填开
	public static final String FAPIAOTRANSITION_ACTION_PRNT = "PRNT";	//发票打印
	public static final String FAPIAOTRANSITION_ACTION_REDR = "REDR";	//发票重开
	public static final String FAPIAOTRANSITION_ACTION_REPR = "REPR";	//发票重打
	public static final String FAPIAOTRANSITION_ACTION_RESP = "RESP";	//发票关系修改
	//发票付费记录类型 SET_FP_SRCTYPE
	public static final String FAPIAO_PSTYPE_D = "D";	//发票打印
	public static final String FAPIAO_PSTYPE_P = "P";	//预存
	public static final String FAPIAO_PSTYPE_C = "C";	//支付
	public static final String FAPIAO_PSTYPE_CSIP = "CSIP";	//受理单支付
	//发票打印识别码类型 SET_FP_PINTYPE
	public static final String FAPIAO_PINTYPE_ID = "ID";	//身份证号
	public static final String FAPIAO_PINTYPE_ORG = "ORG";	//组织机构代码
	public static final String FAPIAO_PINTYPE_TAX = "TAX";	//纳税人识别号
	
	//Cabel Model的deviceClass
	public static final String DeviceClass_CM ="CM";
	
}
