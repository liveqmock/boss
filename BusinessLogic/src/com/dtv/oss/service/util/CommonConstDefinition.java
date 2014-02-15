package com.dtv.oss.service.util;

/**
 * common constant
 */
public class CommonConstDefinition {

	public static final String COMMAND_DEFAULT_STRING = " ";
	public static final double NEAR_ZERO = (double) Math.pow(Math.E, -10);

    //t_financialsetting
	public static final String FORCED_DEPOSIT_ACCTITEMTYPEID ="A000000";
	public static final String RETURN_DEVICE_ACCTITEMTYPEID  ="D000000";
	public static final String ACCTITEMTYPEID_PUNISHMENT = "N000000";

	//Separator of Customer information, used by Web to get CustInfoTree
	public static final String WEB_COMMON_SEPARATOR = ":";
	public static final boolean DEBUGMODE = true;
	public static final int SUCCESS = 0;
	public static final int FAIL = -1;
	public static final String STATUS_CANCELED = "C";
	//SET_W_JOBCARDCREATEMETHOD 工单创建来源
	public static final String  JOBCARDCREATEMETHOD_A="A";   //自动开单
	public static final String  JOBCARDCREATEMETHOD_B="B";   //批量开单
	public static final String  JOBCARDCREATEMETHOD_M="M";   //手动开单
	//SET_W_JOBCARDPAYSTATUS 工单付费状态
	public static final String  JOBCARDPAYSTATUS_D="D";   //已付
	public static final String  JOBCARDPAYSTATUS_R="R";   //退费
	public static final String  JOBCARDPAYSTATUS_W="W";   //未付
	
	//SET_F_ADJUSTMENTREFERRECORDTYPE

	public static final String  ADJUSTMENT_REFERRE_CORD_TYPE_F="F";  //费用记录
	public static final String  ADJUSTMENT_REFERRE_CORD_TYPE_P="P";  //预存记录
	public static final String  ADJUSTMENT_REFERRE_CORD_TYPE_C="C";  //支付记录
	public static final String  ADJUSTMENT_REFERRE_CORD_TYPE_D="D";  //抵扣记录

	//SET_F_ACCOUNTADJUSTMENTDIRECTION
	public static final String  ACCOUNT_ADJUSTMENTD_IRECTION_R="R";  //应收
	public static final String  ACCOUNT_ADJUSTMENTD_IRECTION_P="P";  //实收

	//SET_F_ACCOUNTADJUSTTYPE
	public static final String ACCOUNT_ADJUST_TYPE_S="S";  //受理单调帐
	public static final String ACCOUNT_ADJUST_TYPE_M="M";  //手工调帐
	public static final String ACCOUNT_ADJUST_TYPE_I="I";  //对帐单调帐
	public static final String ACCOUNT_ADJUST_TYPE_C="C";  //维修单调帐

	//SET_A_CATVTERMINALSTATUS
	public static final String CATVTERMINAL_STATUS_DESTROY = "D";	 //销号
	public static final String CATVTERMINAL_STATUS_SJ = "I"; 		//封端后私接
	public static final String CATVTERMINAL_STATUS_NORMAL = "N"; 	//正常
	public static final String CATVTERMINAL_STATUS_OUT = "O"; 		//迁出停用
	public static final String CATVTERMINAL_STATUS_KG = "R"; 		//空关
	public static final String CATVTERMINAL_STATUS_SUSPEND = "S"; 	//停用
	public static final String CATVTERMINAL_STATUS_UNREG = "U"; 	//不在册
/*
	//SET_F_FTSTATUS
	public static final String FTSOURCE_STATUS_NORMAL = "N";		//正常
	public static final String FTSOURCE_STATUS_CANCEL = "C";		//取消
	public static final String FTSOURCE_STATUS_LOCK="L";			//锁定
*/
	//SET_F_CURRENCY
	public static final String CURRENCY_RMB = "RMB";

	//definition for t_deviceclass
	public static final String DEVICECALSS_SMARTCARD = "SC";
	public static final String DEVICECALSS_STB = "STB";
	public static final String DEVICECALSS_CM = "CM";
	public static final String DEVICECALSS_IPP = "IPP";

	//SET_F_BRFEETYPE
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


	//SET_F_ACCOUNTADJUSTMENTREFRECORDTYPE
	public static final String ACCOUNTADJUSTMENTREFRECORDTYPE_F = "F";  //财务交易

	//SET_V_CSIPAYMENTTYPE
	public static final String CSIPAYMENTTYPE_C = "C"; 					//现金
	public static final String CSIPAYMENTTYPE_V = "V";					//抵扣券
	public static final String CSIPAYMENTTYPE_X = "X"; 					//转入(团购等)

	//SET_F_PAYMENTRECORSOURCETYPE
	public static final String PAYMENTRECORSOURCETYPE_O = "O"; 			//付费纪录的来源类型：其他
	public static final String PAYMENTRECORSOURCETYPE_M = "M";			//付费纪录的来源类型：受理单
	public static final String PAYMENTRECORSOURCETYPE_P = "P";			//付费纪录的来源类型：报修单

	//付费方式PAYMENT MOPID
	public static final int PAYMENT_MOPID_CASH = 1; 					//现金付费
	public static final int PAYMENT_MOPID_TOKEN = 1001; 				//抵扣券
	public static final int PAYMENT_MOPID_GROUP = 1002; 				//团购券转入
	public static final int PAYMENT_MOPID_POS = 1003; 					//POS机付费
	public static final int PAYMENT_MOPID_CHEQUE = 1004; 				//支票

	//t_methodofpayment(手工预缴)
	public static final int METHOD_OF_PAYMENT_PREPAY = 7;


	//工商银行
	public static final int METHOD_OF_PAYMENT_ICBC = 102;



	//通用数据定义,包括：时间长度类型、日期格式、通用状态、是否标记、行政区类型； add & modify by zhouxushun , 2005-9-28
	//SET_G_TIMEUNITTYPE
	public static final String TIMEUNITTYPE_MONTH="M";              		//时间长度类型:月
	public static final String TIMEUNITTYPE_QUARTER="Q";					//时间长度类型:季度
	public static final String TIMEUNITTYPE_HALF_YEAR="H";					//时间长度类型:半年
	public static final String TIMEUNITTYPE_YEAR="Y";						//时间长度类型:年
	//SET_G_DATEFORMAT
	public static final String DATEFORMAT_COLON="1";						//日期格式:YYYY:MM:DD
	public static final String DATEFORMAT_DASH="2";							//日期格式:YYYY-MM-DD
	public static final String DATEFORMAT_NO_SIGN="2";						//日期格式:YYYYMMDD
	//SET_G_GENERALSTATUS
	public static final String GENERALSTATUS_VALIDATE="V";					//通用状态:有效
	public static final String GENERALSTATUS_INVALIDATE="I";				//通用状态:无效
	//SET_G_PROCESSSTATUS
	public static final String PROCESSSTATUS_SUCESS="S";					//通用处理结果状态:成功
	public static final String PROCESSSTATUS_FAIL="F";						//通用状态:失败
	//SET_G_YESNOFLAG
	public static final String YESNOFLAG_YES="Y";							//是否标记:是
	public static final String YESNOFLAG_NO="N";							//是否标记：否
	//SET_G_DISTRICTTYPE
	public static final String DISTRICTTYPE_PROVINCE="P";					//行政区类型:省、直辖市
	public static final String DISTRICTTYPE_CITY="C";						//行政区类型:地、市
	public static final String DISTRICTTYPE_TOWN="T";						//行政区类型:区、县
	public static final String DISTRICTTYPE_STATION="S";					//行政区类型:街道站


	//客户基本信息,包括:客户性别、客户状态、客户来源类型、账户地址信息是否正确、客户类型、
	//支付类型（仅对集团客户有效）；                                           add & modify by zhouxushun,2005-9-28
	//SET_C_CUSTOMERGENDER
	public static final String CUSTOMERGENDER_MALE="M";						//客户性别:男
	public static final String CUSTOMERGENDER_FEMALE="F";					//客户性别：女
	//SET_C_CUSTOMERSTATUS
	public static final String CUSTOMER_STATUS_POTENTIAL = "P";				//客户状态：潜在
	public static final String CUSTOMER_STATUS_NORMAL = "N";				//客户状态：正常
	public static final String CUSTOMER_STATUS_CANCEL = "C";				//客户状态：取消
	//SET_C_CUSTOPENSOURCETYPE
	public static final String OPENSOURCETYPE_BRANCH = "O"; 				//客户来源类型：门店
	public static final String OPENSOURCETYPE_STREETSTATION = "S"; 			//客户来源类型：街道站
	public static final String OPENSOURCETYPE_PROXY = "P"; 					//客户来源类型：分公司代理商
	public static final String OPENSOURCETYPE_DEPARTMENT = "D"; 			//客户来源类型：部门
	public static final String OPENSOURCETYPE_TELEPHONESALE = "T"; 			//客户来源类型：电话销售
	public static final String OPENSOURCETYPE_CALLCENTER = "C"; 			//客户来源类型：呼叫中心受理
	public static final String OPENSOURCETYPE_GROUPBARGAIN = "G"; 			//客户来源类型：团购购买
	public static final String OPENSOURCETYPE_SUPERMARKET = "M"; 			//客户来源类型：卖场受理
	public static final String OPENSOURCETYPE_RETAIL = "X"; 				//客户来源类型：现场设摊代理
	public static final String OPENSOURCETYPE_CABLEPROXY = "Y"; 			//客户来源类型：有线通代理
	public static final String OPENSOURCETYPE_JOBPROXY = "H"; 				//客户来源类型：行业代理
	public static final String OPENSOURCETYPE_HOUSEMATCHED = "F"; 			//客户来源类型：房产配套
	public static final String OPENSOURCETYPE_COMPANYSEND = "R"; 			//客户来源类型：厂商预授权
	public static final String OPENSOURCETYPE_A = "A"; 						//客户来源类型：代理商
	//SET_C_BILLADDRESSFLAG
	public static final String BILLADDRESSFLAG_YES= "Y";		 			//用户的账户地址信息是否正确：是
	public static final String BILLADDRESSFLAG_NO= "N"; 					//用户的账户地址信息是否正确：否
	public static final String BILLADDRESSFLAG_UNSURE= "U"; 				//用户的账户地址信息是否正确：待确定
	//SET_C_CUSTOMERTYPE
	public static final String CUSTOMERTYPE_BRANCH = "B"; 					//客户类型：门店用户
	public static final String CUSTOMERTYPE_ENTERPRISE = "E"; 				//客户类型：企业客户
	public static final String CUSTOMERTYPE_FOREIGN = "F"; 					//客户类型：外籍用户
	public static final String CUSTOMERTYPE_MONITOR = "M"; 					//客户类型：监测客户
	public static final String CUSTOMERTYPE_NORMAL = "N"; 					//客户类型：普通客户
	public static final String CUSTOMERTYPE_SURVEY = "Q"; 					//客户类型：市场调查
	public static final String CUSTOMERTYPE_SERVICE = "S"; 					//客户类型：公务用户
	public static final String CUSTOMERTYPE_TEST = "T"; 					//客户类型：体验客户
	public static final String CUSTOMERTYPE_VIP = "V"; 						//客户类型：VIP客户
	public static final String CUSTOMERTYPE_GROUP = "G"; 					//客户类型：集团客户
	
	//SET_C_CUSTOMERSTYLE
	public static final String CUSTOMERSTYLE_SINGLE = "S"; 					//客户类型：个人客户
	public static final String CUSTOMERSTYLE_GROUP = "G"; 					//客户类型：集团客户
	//SET_C_CUSTOMERPAYTYPE
	public static final String CUSTOMERPAYTYPE_PERSON = "P"; 				//支付类型（仅对集团客户有效）：个人支付
	public static final String CUSTOMERPAYTYPE_COMBINE= "C"; 				//支付类型（仅对集团客户有效）：集体支付


	//客户产品，包括：状态、客户产品状态、客户业务帐号状态、是否按照约定时间上门；    add & modify by zhouxushun ,2005-9-28
	//SET_C_CPCMSTATUS
	//废除掉了,改通用状态 有效/无效 侯 2007-4-18
//	public static final String CPCM_STATUS_NEW = "N";						//CPCAMPAIGNMAP产品优惠映射状态:新建
//	public static final String CPCM_STATUS_CANCEL = "C";					//CPCAMPAIGNMAP产品优惠映射状态:取消
//	public static final String CPCM_STATUS_VALID = "V";						//CPCAMPAIGNMAP产品优惠映射状态：生效
//	public static final String CPCM_STATUS_INVALID = "I";					//CPCAMPAIGNMAP产品优惠映射状态：失败
	//SET_M_CUSTOMERCAMPAIGNSTATUS
	public static final String CUSTOMERCAMPAIGNSTATUS_CANCEL = "C";			//客户促销活动状态：取消
	public static final String CUSTOMERCAMPAIGNSTATUS_NEW = "N";			//客户促销活动状态：创建
	public static final String CUSTOMERCAMPAIGNSTATUS_NORMAL = "V";			//客户促销活动状态：有效
	public static final String CUSTOMERCAMPAIGNSTATUS_INVALID = "I";		//客户促销活动状态：失效
	public static final String CUSTOMERCAMPAIGNSTATUS_TRANSFE = "T";		//客户促销活动状态：已转换
	//SET_C_CUSTOMERPRODUCTPSTATUS
	public static final String CUSTOMERPRODUCTP_STATUS_INIT="I";			//客户产品状态：初始
	public static final String CUSTOMERPRODUCTP_STATUS_NORMAL="N";			//客户产品状态：正常
	public static final String CUSTOMERPRODUCTP_STATUS_SYSTEMSUSPEND="S";	//客户产品状态：系统暂停
	public static final String CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND="H";	//客户产品状态：主动暂停
	public static final String CUSTOMERPRODUCTP_STATUS_CANCEL = "C";		//客户产品状态：取消
	public static final String CUSTOMERPRODUCTP_STATUS_MOVE = "M";		    //客户产品状态：临时状态，迁移
	public static final String CUSTOMERPRODUCTP_STATUS_BEFOREHANDCLOSE = "P";//客户产品状态：预退户
	//SET_C_CP_DEVICEPROVIDEFLAG
	public static final String CUSTOMERPRODUCT_DEVICEPROVIDEFLAG_B="B";			//设备来源：购买
	public static final String CUSTOMERPRODUCT_DEVICEPROVIDEFLAG_R="R";			//设备来源：租用
	public static final String CUSTOMERPRODUCT_DEVICEPROVIDEFLAG_F="F";			//设备来源：赠送
	public static final String CUSTOMERPRODUCT_DEVICEPROVIDEFLAG_S="S";			//设备来源：自带
	
	//SET_C_SERVICEACCOUNTSTATUS
	public static final String SERVICEACCOUNT_STATUS_INIT = "I";			//客户业务帐号状态：初始
	public static final String SERVICEACCOUNT_STATUS_NORMAL="N";			//客户业务帐号状态：正常
	public static final String SERVICEACCOUNT_STATUS_SYSTEMSUSPEND = "S";	//客户业务帐号状态：系统暂停
	public static final String SERVICEACCOUNT_STATUS_REQUESTSUSPEND = "H";	//客户业务帐号状态：主动暂停
	public static final String SERVICEACCOUNT_STATUS_CANCEL = "C";			//客户业务帐号状态：取消
	public static final String SERVICEACCOUNT_STATUS_BEFOREHANDCLOSE = "P";	//客户业务帐号状态：预退户
	//SET_C_CPONTIMEORNOT
	public static final String CPONTIMEORNOT_YES = "Y";						//约定时间上门：是
	public static final String CPONTIMEORNOT_NO= "N";						//约定时间上门：否
	public static final String CPONTIMEORNOT_OHTER= "O";					//约定时间上门：不需要
	//SET_R_PHONENOSTATUS 电话号码状态
	public static final String PHONENO_STATUS_NEW= "N";							//新建
	public static final String PHONENO_STATUS_AVAILABLE= "A";					//可用
	public static final String PHONENO_STATUS_USED= "U";						//已用
	public static final String PHONENO_STATUS_INVALID= "I";						//作废
	public static final String PHONENO_STATUS_LOCKED= "L";						//锁定
	//SET_R_PHONENOUESLOGACTION 电话号码使用记录
	public static final String PHONENOUSELOG_ACTION_CREATE= "C";					//创建
	public static final String PHONENOUSELOG_ACTION_MODIFY= "M";					//修改
	public static final String PHONENOUSELOG_ACTION_USED= "U";						//占用
	public static final String PHONENOUSELOG_ACTION_REUSE= "R";						//回收
	public static final String PHONENOUSELOG_ACTION_XIAO= "X";						//注销
	
	//客户关系，包括：报修单状态、报修类型、故障级别、报修单处理的动作类型、
	//收费列表、报修单流转的原因、回访标志、诊断类型、回访类型                      add & modify by zhouxushun , 2005-9-28
	//SET_C_PROBLEMSTATUS
	public static final String CUSTOMERPROBLEM_STATUS_WAIT="W";					//报修单状态：待处理
	public static final String CUSTOMERPROBLEM_STATUS_PROCESSING="C";				//报修单状态：正在处理
	public static final String CUSTOMERPROBLEM_STATUS_SUCCESS="D";					//报修单状态：已排障
	//public static final String CUSTOMERPROBLEM_STATUS_UPGRADE="U";				//报修单状态：已升级
	public static final String CUSTOMERPROBLEM_STATUS_FAIL="S";					//报修单状态：维修不成功
	public static final String CUSTOMERPROBLEM_STATUS_TERMINAL="T";				//报修单状态：无法继续维修
	public static final String CUSTOMERPROBLEM_STATUS_CANCEL="CN";				//报修单状态：取消
	//SET_C_CPPROBLEMCATEGORY
	public static final String CPPROBLEMCATEGORY_SELF_INSTALL_FAIL="S";		//报修类型：自安装失败
	public static final String CPPROBLEMCATEGORY_INSTALL_FAIL="W";			//报修类型：上门安装一周之内
	public static final String CPPROBLEMCATEGORY_NORMAL="N";				//报修类型：普通报修
	//SET_C_CPPROBLEMLEVEL
	public static final String CPPROBLEMLEVEL_SINGLE="S";					//故障级别：单点故障
	public static final String CPPROBLEMLEVEL_MUCH_AREA="M";				//故障级别：大面积故障
	public static final String CPPROBLEMLEVEL_PUZZLED="P";					//故障级别：疑难杂症
	//SET_C_CPPROCESSACTION 报修单处理的动作类型
	public static final String CUSTPROBLEMPROCESS_ACTION_CREATE	= "W";	         //创建 创建报修单---->待处理
	public static final String CUSTPROBLEMPROCESS_ACTION_ASSIGN = "A";	         //派工 待处理---->正在处理
	public static final String CUSTPROBLEMPROCESS_ACTION_SUCCESS = "E";	         //录入维修信息 正在处理---->已排障
	public static final String CUSTPROBLEMPROCESS_ACTION_FAIL = "F";	         //结束维修工单 正在处理---->维修不成功
	public static final String CUSTPROBLEMPROCESS_ACTION_END = "S";	             //终止维修 维修不成功---->无法继续维修
	public static final String CUSTPROBLEMPROCESS_ACTION_TRANSFER = "WT";        //待处理流转 待处理---->待处理
	public static final String CUSTPROBLEMPROCESS_ACTION_CANCEL = "R";           //取消维修工单 正在处理---->待处理 待处理---->取消
	public static final String CUSTPROBLEMPROCESS_ACTION_STOP = "M";             //手工结束报修单 待处理---->已排障
	public static final String CUSTPROBLEMPROCESS_ACTION_RETRANSFER = "T";       //维修不成功流转 维修不成功---->待处理
	public static final String CUSTPROBLEMPROCESS_ACTION_CALLBACK = "B";         //回访
	public static final String CUSTPROBLEMPROCESS_ACTION_CALLBACK_CACHE = "C";   //回访暂存
	public static final String CUSTPROBLEMPROCESS_ACTION_DIAGNOSIS = "D";
//	T_JOBCARD 工单预约结果 CONTACTRESULT
	public static final String JOBCARD_CONTACT_RESULT_RESOLVED_BY_PHONE 	= "G";		//电话支持故障排除
	public static final String JOBCARD_CONTACT_RESULT_HOUSE_REAPIR 				= "R";		//上门维修
	public static final String JOBCARD_CONTACT_RESULT_CANT_CONTACT_USER		= "C";		//无法联系用户
	public static final String JOBCARD_CONTACT_RESULT_RESOLVED_BYSLEFPHONE		= "T";		//电话联系已经好
	public static final String JOBCARD_CONTACT_RESULT_REFUSE_PAYMENT	= "U";		//用户不愿意支付费用取消上门
	//SET_C_CPFEECLA
	public static final String CPFEECLA_NORMAL="N";							//收费列表：常规维修
	public static final String CPFEECLA_FEE="F";							//收费列表：收费维修
	public static final String CPFEECLA_A="A";								//收费列表：7天内已有报修
	//SET_C_CPCALLBACKFLAG
	public static final String CCPCALLBACKFLAG_YES="Y";						//回访标志：已回访
	public static final String CPCALLBACKFLAG_NO="N";						//回访标志：未回访
	public static final String CPCALLBACKFLAG_T="T";						//回访标志：回访暂存
	//SET_C_DIAGNOSISINFOREFERSOURCETYPE
	public static final String DIAGNOSISINFOTYPE_EARLY="E";					//诊断类型：初步诊断
	public static final String DIAGNOSISINFOTYPE_A="A";						//诊断类型：专业诊断
	//SET_C_CALLBACKINFOTYPE
	public static final String CALLBACKINFOTYPE_OPEN="O";					//回访类型：开户回访
	public static final String CALLBACKINFOTYPE_REPAIR="R";					//回访类型：报修回访
	public static final String CALLBACKINFOTYPE_C="C";						//回访类型：投诉回访
	//SET_C_CALLBACKINFOSETTINGVALUETYPE
	public static final String CALLBACKINFOSETTINGVALUETYPE_MANU="M";		//回访设置值类型：手工输入
	public static final String CALLBACKINFOSETTINGVALUETYPE_SYSTEM="S";		//回访设置值类型：系统定义
	//SET_C_SENDMESSAGESENDTYPE
	public static final String SENDMESSAGESENDTYPE_SYSTEM="S";				//发送消息类型：通过机顶盒发送
	public static final String SENDMESSAGESENDTYPE_EMAIL="E";				//发送消息类型：通过Email发送
	//SET_C_SENDMESSAGESOURCETYPE
	public static final String SENDMESSAGESOURCETYPE_MANU="M";				//消息产生来源：手工创建信息
	public static final String SENDMESSAGESOURCETYPE_SYSTEM="S";			//消息产生来源：系统产生信息
	//SET_C_SENDMESSAGESTATUS
	public static final String SENDMESSAGESTATUS_NEW="N";					//消息状态：新建
	public static final String SENDMESSAGESTATUS_CANCEL="C";				//消息状态：取消
	public static final String SENDMESSAGESTATUS_PROCESSED="P";				//消息状态：已处理


	//业务受理，包括：客户产品变更动作、业务受理单的类型、状态变化原因、安装类型、
	//费用的支付状态、业务受理单的状态、受理单处理操作、客户销户的原因、押金的状态、
	//押金的金额、上门安装/维修的时间段                                           add & modify by zhouxushun , 2005-9-28
	//SET_V_CSICUSTPRODUCTINFOACTION
	public static final String CSICUSTPRODUCTINFO_ACTION_OPEN = "O";			//客户产品变更动作：新开户
	public static final String CSICUSTPRODUCTINFO_ACTION_NEWPRODUCT = "N";	//客户产品变更动作：产品新增
	public static final String CSICUSTPRODUCTINFO_ACTION_CANCEL = "C";		//客户产品变更动作：产品取消
	public static final String CSICUSTPRODUCTINFO_ACTION_ASCEND = "A";		//客户产品变更动作：产品升级
	public static final String CSICUSTPRODUCTINFO_ACTION_DESCEND = "D";		//客户产品变更动作：产品降级
	public static final String CSICUSTPRODUCTINFO_ACTION_CHANGEACCOUNT = "F";//客户产品变更动作：改变产品的付费账户
	public static final String CSICUSTPRODUCTINFO_ACTION_PAUSE = "P";		//客户产品变更动作：产品暂停
	public static final String CSICUSTPRODUCTINFO_ACTION_RESUME = "R";		//客户产品变更动作：产品恢复
	public static final String CSICUSTPRODUCTINFO_ACTION_S = "S";			//客户产品变更动作：设备更换
	//SET_V_CUSTSERVICEINTERACTIONTYPE
	
	public static final String CUSTSERVICEINTERACTIONTYPE_GO = "GO"; 		//业务受理单的类型：集团客户开户
	public static final String CUSTSERVICEINTERACTIONTYPE_GS = "GS"; 		//业务受理单的类型：子客户开户
	public static final String CUSTSERVICEINTERACTIONTYPE_GM = "GM"; 		//业务受理单的类型：子客户转个人客户	

	public static final String CUSTSERVICEINTERACTIONTYPE_CO = "CO";        //业务受理单的类型：客户开户（资料单独录入）
	public static final String CUSTSERVICEINTERACTIONTYPE_BK = "BK"; 		//业务受理单的类型：预约
	public static final String CUSTSERVICEINTERACTIONTYPE_OS = "OS"; 		//业务受理单的类型：门店开户
	public static final String CUSTSERVICEINTERACTIONTYPE_OB = "OB"; 		//业务受理单的类型：预约开户
//	public static final String CUSTSERVICEINTERACTIONTYPE_OD = "OD"; 		//业务受理单的类型：集团开户
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
	public static final String CUSTSERVICEINTERACTIONTYPE_OSA = "OSA";		//业务受理单的类型：用户销户
	public static final String CUSTSERVICEINTERACTIONTYPE_DS = "DS"; 		//业务受理单的类型：设备更换
	public static final String CUSTSERVICEINTERACTIONTYPE_DU = "DU"; 		//业务受理单的类型：设备升级
	public static final String CUSTSERVICEINTERACTIONTYPE_CP = "CP";		//业务受理单的类型：取消客户产品
	public static final String CUSTSERVICEINTERACTIONTYPE_PM = "PM";		//业务受理单的类型：迁移客户产品
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
	public static final String CUSTSERVICEINTERACTIONTYPE_UR = "UR";		//业务受理单的类型：恢复用户
	public static final String CUSTSERVICEINTERACTIONTYPE_UC = "UC";		//业务受理单的类型：用户销户
	public static final String CUSTSERVICEINTERACTIONTYPE_SP = "SP";		//业务受理单的类型：用户预退户
	public static final String CUSTSERVICEINTERACTIONTYPE_RC = "RC";		//业务受理单的类型：用户实退户
	public static final String CUSTSERVICEINTERACTIONTYPE_UT = "UT";		//业务受理单的类型：用户过户
	public static final String CUSTSERVICEINTERACTIONTYPE_BP = "BP";		//业务受理单的类型：预约新增客户产品
	public static final String CUSTSERVICEINTERACTIONTYPE_BDS = "BDS";		//业务受理单的类型：套餐转换    
	public static final String CUSTSERVICEINTERACTIONTYPE_BDP = "BDP";		//业务受理单的类型：套餐预付
	public static final String CUSTSERVICEINTERACTIONTYPE_BDE = "BDE";		//业务受理单的类型：套餐延期
	public static final String CUSTSERVICEINTERACTIONTYPE_BDC = "BDC";		//业务受理单的类型：套餐取消
	public static final String CUSTSERVICEINTERACTIONTYPE_CAA = "CAA";		//业务受理单的类型：模拟业务增端
	public static final String CUSTSERVICEINTERACTIONTYPE_CAO = "CAO";		//业务受理单的类型：模拟业务开户
	public static final String CUSTSERVICEINTERACTIONTYPE_CAS = "CAS";		//业务受理单的类型：模拟电视业务停机
	public static final String CUSTSERVICEINTERACTIONTYPE_CAR = "CAR";		//业务受理单的类型：模拟电视业务恢复
	public static final String CUSTSERVICEINTERACTIONTYPE_CAM = "CAM";		//业务受理单的类型: 钱包建立
	public static final String CUSTSERVICEINTERACTIONTYPE_CAC = "CAC";		//业务受理单的类型：钱包充值
	public static final String CUSTSERVICEINTERACTIONTYPE_MAC = "MAC";		//业务受理单的类型：手工授予促销
	public static final String CUSTSERVICEINTERACTIONTYPE_MB = "MB";		//业务受理单的类型：手工收费
	
	
	//SET_V_CUSTSERVICEINTERACTIONSTATUSREASON
	public static final String CSI_STATUSREASON_M = "M"; 					//状态变化原因：主动暂停
	public static final String CSI_STATUSREASON_P = "P"; 					//状态变化原因：用户投诉暂停
	public static final String CSI_STATUSREASON_O = "O"; 					//状态变化原因：迁往网外
	public static final String CSI_STATUSREASON_A = "A"; 					//状态变化原因：双向网络未改造
	public static final String CSI_STATUSREASON_B = "B"; 					//状态变化原因：重复预约
	public static final String CSI_STATUSREASON_C = "C"; 					//状态变化原因：对节目内容不满放弃安装
	public static final String CSI_STATUSREASON_D = "D"; 					//状态变化原因：受理材料不全
	public static final String CSI_STATUSREASON_E = "E"; 					//状态变化原因：临时改变主意放弃安装
	public static final String CSI_STATUSREASON_F = "F"; 					//状态变化原因：其他
	public static final String CSI_STATUSREASON_U = "U"; 					//状态变化原因：卫星干扰
	//SET_V_CUSTSERVICEINTERACTIONINSTYPE
	public static final String CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL = "S";    //安装类型：自安装
	public static final String CUSTSERVICEINTERACTIONIN_STYPE_INSTALL = "C";        //安装类型：上门安装
	
	//业务账户过户类型
	public static final String SA_TRANFER_TYPE_I="I";						//系统内业务帐户过户
	public static final String SA_TRANFER_TYPE_O="O";						//系统外业务帐户过户
	//业务帐户状态
	public static final String SA_STATUS_NORMAL="N";						//正常
	public static final String SA_STATUS_INITIAL="I";						//初始
	public static final String SA_STATUS_CANCEL="C";						//取消
	public static final String SA_STATUS_SYSPAUSE="S";						//系统暂停
	public static final String SA_STATUS_PAUSE="H";						    //主动暂停

	//SET_V_CUSTSERVICEINTERACTIONPAYSTATUS
	public static final String CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY = "W";   //费用的支付状态：未付
	public static final String CUSTSERVICEINTERACTION_PAYSTATUS_PAYED = "D";        //费用的支付状态：已付
	public static final String CUSTSERVICEINTERACTION_PAYSTATUS_RETURNMONEY = "R";  //费用的支付状态：退费
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
	//SET_V_CSIPROCESSLOGACTION
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
	public static final String CSIPROCESSLOG_ACTION_INSTALL = "IB";           		//受理单处理操作：安装预约
	public static final String CSIPRPCESSLOG_ACTION_PROCESS ="P";                   //受理单处理操作：正在进行
	

	//SET_V_CC_CUSTSERVICEINTERACTIONSTATUSREASON
	public static final String CSI_CC_STATUSREASON_CA ="CA";						//客户销户的原因：收视质量不稳定
	public static final String CSI_CC_STATUSREASON_CB ="CB";						//客户销户的原因：搬迁到双向网未改造地区
	public static final String CSI_CC_STATUSREASON_CC ="CC";						//客户销户的原因：家中无人使用
	public static final String CSI_CC_STATUSREASON_CD ="CD";						//客户销户的原因：当前资费不合理
	public static final String CSI_CC_STATUSREASON_CE ="CE";						//客户销户的原因：服务质量不满意
	public static final String CSI_CC_STATUSREASON_CF ="CF";						//客户销户的原因：房屋出售
	public static final String CSI_CC_STATUSREASON_CG ="CG";						//客户销户的原因：内容缺乏吸引力
	public static final String CSI_CC_STATUSREASON_CI ="CI";						//客户销户的原因：安装地址无人居住
	public static final String CSI_CC_STATUSREASON_CN ="CN";						//客户销户的原因：其它
	//SET_V_FDSTATUS
	public static final String FDSTATUS_CREATE = "N"; 								//押金的状态：未退
	public static final String FDSTATUS_BACK = "Y"; 								//押金的状态：已退
	public static final String FDSTATUS_CONFISCATE = "S"; 							//押金的状态：没收
	public static final String FDSTATUS_PART_CONFISCATE = "P"; 						//押金的状态：部分没收
	//SET_V_FDVALUE
	public static final String FDVALUE_ZERO= "0";							//押金的金额：0
	public static final String FDVALUE_ONEONEOO="1100";						//押金的金额：1100
	//SET_C_CSIPREFEREDTIME
	public static final String CSIPREFEREDTIME_AM ="A";						//上门安装/维修的时间段：上午
	public static final String CSIPREFEREDTIME_PM ="P";						//上门安装/维修的时间段：下午
	public static final String CSIPREFEREDTIME_ALL_DAY ="E";				//上门安装/维修的时间段：全天
	//SET_V_CSISCHEDULEACTION
	public static final String CSISCHEDULEACTION_PAUSE= "P";				//排程任务类型：暂停
	public static final String CSISCHEDULEACTION_RESUME="R";				//排程任务类型：恢复


	//帐户资料，包括：帐户状态、帐户类别、帐户类型、串配状态、预存款类型、付费类型。    add & modify by zhouxushun , 2005-9-29
	//SET_F_ACCOUNTSTATUS
	public static final String ACCOUNT_STATUS_NORMAL = "N";					//帐户状态：正常
	public static final String ACCOUNT_STATUS_CLOSE = "C"; 					//帐户状态：关闭
	public static final String ACCOUNT_STATUS_OWNED = "O"; 					//帐户状态：一次欠费
	public static final String ACCOUNT_STATUS_OWNEDAGAIN = "T"; 			//帐户状态：二次欠费
	//SET_F_ACCOUNTCLASS
	public static final String ACCOUNTCLASS_CUSTOMER = "C";					//帐户类别：客户帐户
	public static final String ACCOUNTCLASS_PARTNER = "P";					//帐户类别：合作伙伴帐户
	public static final String ACCOUNTCLASS_SMS = "S";						//帐户类别：SMS帐户
	//SET_F_ACCOUNTTYPE
	public static final String ACCOUNTTYPE_NORMAL = "N";					//帐户类型：普通帐户
	public static final String ACCOUNTTYPE_PREPAID = "P";					//帐户类型：预付费帐户
	//SET_F_ACCOUNTBANKACCOUNTSTATUS
	public static final String ACCOUNTBANKACCOUNT_STATUS_CANCEL = "C"; 		//串配状态：串配取消
	public static final String ACCOUNTBANKACCOUNT_STATUS_SUCCESS = "S"; 	//串配状态：串配成功
	public static final String ACCOUNTBANKACCOUNT_STATUS_UNSUCCESS = "U"; 	//串配状态：串配不成功
	public static final String ACCOUNTBANKACCOUNT_STATUS_WAIT = "W"; 		//串配状态：待串配
	//SET_F_PREPAYMENTTYPE
	public static final String PREPAYMENTTYPE_CASH = "C";					//预存款类型：现金
	public static final String PREPAYMENTTYPE_TRANSLUNARY="T";				//预存款类型：虚拟货币
	//SET_F_MOPPAYTYPE
	public static final String MOPPAYTYPE_CREDITCARD = "CR";				//付费类型：信用卡
	public static final String MOPPAYTYPE_ONLINE = "OL";					//付费类型：在线付费
	public static final String MOPPAYTYPE_PAYBYBANK = "BP";					//付费类型：银行扣款
	public static final String MOPPAYTYPE_CASH = "CH";						//付费类型：现金
	public static final String MOPPAYTYPE_OTHER = "OT";						//付费类型：其他
	public static final String MOPPAYTYPE_CD = "CD";						//付费类型：折旧抵扣


	//帐务数据，包括：帐单状态、帐单来源、财务交易-费用状态、财务交易-FT来源、
	//财务交易-FT帐单标记、财务交易-FT计帐标记、抵扣记录、支付记录-支付券类型、
	//支付记录-付费纪录的类型、支付记录-支付记录来源类型、费用-状态、
	//费用-综合总帐来源类型、销帐记录-销帐标记、销帐记录-销帐记录类型、
	//销帐记录-销帐关联记录类型。                                                   add & modify by zhouxushun , 2005-9-29
	//SET_F_INVOICESTATUS
	public static final String INVOICE_STATUS_WAIT = "W";					//帐单状态：待付费
	public static final String INVOICE_STATUS_CANCEL = "C";					//帐单状态：取消
	public static final String INVOICE_STATUS_OWE = "O";					//帐单状态：逾期
	public static final String INVOICE_STATUS_PAID = "D";					//帐单状态：已经付费
	public static final String INVOICE_STATUS_BAD = "B";					//帐单状态：坏帐注销
	public static final String INVOICE_STATUS_BADLOCK = "L";				//帐单状态：呆坏帐锁定

	public static final String INVOICE_STATUS_RETURNMONEY = "R";			//帐单状态：退款
	public static final String INVOICE_STATUS_QFZT = "T";					//帐单状态：欠费追讨
	public static final String INVOICE_STATUS_NORMAL = "N";					//帐单状态：正常
	//SET_F_INVOICESOURCETYPE
	public static final String INVOICESOURCETYPE_M = "M"; 					//帐单来源：受理单产生
	public static final String INVOICESOURCETYPE_A = "A"; 					//帐单来源：出帐产生
	public static final String INVOICESOURCETYPE_O = "O"; 					//帐单来源：订单产生
	public static final String INVOICESOURCETYPE_P = "P"; 					//帐单来源：报修单产生
	//SET_F_FTSTATUS
	public static final String AISTATUS_VALIDATE = "V"; 					//财务交易-费用状态：有效
	public static final String AISTATUS_INVALIDATE = "I"; 					//财务交易-费用状态：无效
	public static final String AISTATUS_LOCK = "L"; 						//财务交易-费用状态：锁定
	public static final String AISTATUS_POTENTIAL = "P"; 					//财务交易-费用状态：预生成
	public static final String AISTATUS_RETURN = "R"; 					    //财务交易-费用状态：退还
	public static final String AISTATUS_DELETE = "D"; 					    //财务交易-费用状态：没收
	public static final String AISTATUS_VALIDATE1 = "1"; 					    //财务交易-费用状态：退还
	public static final String AISTATUS_VALIDATE2 = "3"; 					    //财务交易-费用状态：没收
	
//SET_F_FTSOURCETYPE
	public static final String FTSOURCETYPE_PAYBYCUSTOMER = "P";			//财务交易-FT来源：客户付费
	public static final String FTSOURCETYPE_INSTANTFEE = "D";				//财务交易-FT来源：一次性费用
	public static final String FTSOURCETYPE_PERIODACCOUNTING = "A";			//财务交易-FT来源：周期性出帐产生
	public static final String FTSOURCETYPE_TIAOZHANG = "T";				//财务交易-FT来源：人工帐务调整
	public static final String FTSOURCETYPE_PUNISHMENT = "O";				//财务交易-FT来源：滞纳金
	public static final String FTSOURCETYPE_OPENACCOUNT = "S";				//财务交易-FT来源：开户预付
	public static final String FTSOURCETYPE_NEXT = "N";						//财务交易-FT来源：余额递转
	public static final String FTSOURCETYPE_CANCEL = "C";					//财务交易-FT来源：退户费
	//SET_F_FTINVOICEDFLAG
	public static final String FTINVOICEDFLAG_YES = "Y";					//财务交易-FT帐单标记：已出现在帐单中
	public static final String FTINVOICEDFLAG_NO = "N";						//财务交易-FT帐单标记：未出现在帐单中
	//ET_F_FTACCOUNTEDFLAG
	public static final String FTACCOUNTEDFLAG_YES = "Y";					//财务交易-FT计帐标记：已计入帐户余额
	public static final String FTACCOUNTEDFLAG_NO = "N";					//财务交易-FT计帐标记：未计入帐户余额
	//SET_F_DEDUCTIONRECORDTYPE
	public static final String DEDUCTIONRECORDTYPE_CASH = "C";				//抵扣记录：预付代用币帐单扣款
	public static final String DEDUCTIONRECORDTYPE_TRANSLUNARY = "T";		//抵扣记录：预付现金帐单扣款
	//SET_F_PAYMENTTICKETTY
	public static final String PAYMENTTICKETTY_DK = "DK";					//支付记录-支付券类型：抵扣券
	public static final String PAYMENTTICKETTY_TG = "TG";					//支付记录-支付券类型：团购券
	//SET_F_PAYMENTRECORDTYPE
	public static final String PAYMENTRECORD_TYPE_PRESAVE = "P";            //支付记录-付费纪录的类型：客户预付费
	public static final String PAYMENTRECORD_TYPE_ACCEPT_CASE = "C";		//支付记录-付费纪录的类型：支付受理费用
	public static final String PAYMENTRECORD_TYPE_BILLING = "N"; 			//支付记录-付费纪录的类型：客户帐单付费
	public static final String PAYMENTRECORD_TYPE_RETURN_FEE = "RF";		//支付记录-付费纪录的类型：受理单退费
	public static final String PAYMENTRECORD_TYPE_RETURN_RR = "RR";			//支付记录-付费纪录的类型：预存退费
	//2005-12-30 Start modified by Wesley Shu
	public static final String PAYMENTRECORD_TYPE_RETURN_PREFEE = "RR";     //支付记录-付费纪录的类型：预存退费
	//end

	/*
	public static final String PAYMENTRECORD_TYPE_ONINVOICE = "N"; 			//支付记录-付费纪录的类型：现金付帐
	public static final String PAYMENTRECORD_TYPE_TPRESAVE = "TP"; 			//支付记录-付费纪录的类型：虚拟货币预付费
	public static final String PAYMENTRECORD_TYPE_PUTINTO = "X"; 			//支付记录-付费纪录的类型：转入(团购等)
	public static final String PAYMENTRECORD_TYPE_TONINVOICE = "TN"; 		//支付记录-付费纪录的类型：虚拟货币付帐单
	public static final String PAYMENTRECORD_TYPE_RETURNMONEY = "D"; 		//支付记录-付费纪录的类型：提款
	public static final String PAYMENTRECORD_TYPE_TOKENINVOICE = "CN"; 		//支付记录-付费纪录的类型：抵扣券付帐单
	public static final String PAYMENTRECORD_TYPE_TOKENPRESAVE = "CP"; 		//支付记录-付费纪录的类型：抵扣券预付费
	*/
	//SET_F_PAYMENTRECORDSOURCETYPE
	public static final String PAYMENTRECORDSOURCETYPE_OTHER = "O";			//支付记录-支付记录来源类型：其它
	public static final String PAYMENTRECORDSOURCETYPE_REPAIR = "P";		//支付记录-支付记录来源类型：报修单
	public static final String PAYMENTRECORDSOURCETYPE_ACCEPT = "M"; 		//支付记录-支付记录来源类型：受理单
	public static final String PAYMENTRECORDSOURCETYPE_ADJUST = "T";        //支付记录-支付记录来源类型：调帐
	public static final String PAYMENTRECORDSOURCETYPE_JOBCARD = "J";        //支付记录-支付记录来源类型：工单
	public static final String PAYMENTRECORDSOURCETYPE_FUTURERIGHT = "R";        //期权冲值
	//SET_F_AASTATUS
	public static final String AASTATUS_UNSTAT = "0";						//费用-状态：未统计
	public static final String AASTATUS_STAT = "1";							//费用-状态：已统计
	public static final String AASTATUS_CANCLE = "2"; 						//费用-状态：取消
	//SET_F_FTREFERTYPE
	public static final String FTREFERTYPE_M = "M"; 						//费用-综合总帐来源类型：受理单产生
	public static final String FTREFERTYPE_A = "A"; 						//费用-综合总帐来源类型：出帐产生
	public static final String FTREFERTYPE_P = "P"; 						//费用-综合总帐来源类型：报修单
	public static final String FTREFERTYPE_F = "F"; 						//期权冲值
	public static final String FTREFERTYPE_J = "J"; 						//费用-综合总帐来源类型：工单
	//SET_F_SETOFFFLAG
	public static final String SETOFFFLAG_W = "W"; 							//销帐记录-销帐标记：待销帐
	public static final String SETOFFFLAG_P = "P"; 							//销帐记录-销帐标记：部分销帐
	public static final String SETOFFFLAG_D = "D"; 							//销帐记录-销帐标记：已销帐
	//SET_F_SETOFFTYPE
	public static final String SETOFFTYPE_P = "P"; 							//销帐记录-销帐记录类型：直接销帐
	public static final String SETOFFTYPE_D = "D"; 							//销帐记录-销帐记录类型：预存销帐
	//2005-12-27 start added by Wesley Shu
	public static final String SETOFFTYPE_F = "F"; 					        //销帐记录-销帐记录类型：强制预存
	//2005-12-27 end added by Wesley Shu

	//SET_F_SETOFFREFERTYPE
	public static final String SETOFFREFERTYPE_M = "M"; 					//销帐记录-销帐关联记录类型：报修单
	public static final String SETOFFREFERTYPE_I = "I"; 					//销帐记录-销帐关联记录类型：帐单
	public static final String SETOFFREFERTYPE_C = "C"; 					//销帐记录-销帐关联记录类型：受理单
	public static final String SETOFFREFERTYPE_F = "F"; 					//销帐记录-销帐关联记录类型：费用
	public static final String SETOFFREFERTYPE_D = "D"; 					//销帐记录-销帐关联记录类型：抵扣记录
	public static final String SETOFFREFERTYPE_P = "P"; 					//销帐记录-销帐关联记录类型：支付
	public static final String SETOFFREFERTYPE_J = "J"; 					//销帐记录-销帐关联记录类型：工单
	//2005-12-16 start added by Wesley Shu
	//SET_F_FTCREATINGMETHOD
	public static final String FTCREATINGMETHOD_A = "A"; 					//费用创建来源类型：批量计费
	public static final String FTCREATINGMETHOD_B = "B"; 					//费用创建来源类型：受理单计费
	public static final String FTCREATINGMETHOD_I = "I"; 					//费用创建来源类型：接口产生
	public static final String FTCREATINGMETHOD_R = "R"; 					//费用创建来源类型：受理单退费
	public static final String FTCREATINGMETHOD_X = "X"; 					//费用创建来源类型：数据库导入
	public static final String FTCREATINGMETHOD_T = "T";                    //费用创建来源类型：调帐产生
	public static final String FTCREATINGMETHOD_M = "M";					//费用创建来源类型: 手工
	//2005-12-16 end added by Wesley Shu

	//2005-12-21 start added by Wesley Shu
	//SET_F_PREPAYMENTDEDUCTIONMODE
	public static final String PREPAYMENTDEDUCTIONMODE_C = "C"; 			//预存抵扣方式：现金优先
	public static final String PREPAYMENTDEDUCTIONMODE_P = "P"; 			//预存抵扣方式：按比例抵扣
	public static final String PREPAYMENTDEDUCTIONMODE_T = "T"; 			//预存抵扣方式：虚拟货币优先
	//2005-12-21 end added by Wesley Shu

	//SET_F_PDR_REFERRECORDTYPE
	public static final String F_PDR_REFERRECORDTYPE_C="C";              //来源记录类型：受理单
	public static final String F_PDR_REFERRECORDTYPE_F="F";              //来源记录类型：财务交易
	public static final String F_PDR_REFERRECORDTYPE_I="I";              //来源记录类型：帐单
	public static final String F_PDR_REFERRECORDTYPE_P="P";              //来源记录类型：报修单
	public static final String F_PDR_REFERRECORDTYPE_J="J";              //来源记录类型：工单
	//*************************************************
	//* 暂时没有定义的公用数据模块是：
	//* 3.3.3	帐务处理、
	//* 3.3.4	计费帐务配置参数、
	//* 3.3.5	历史遗留配置参数
	//*************************************************/


	//产品，包括：业务状态、产品状态、产品类别、产品包状态、产品参数类型、
	//产品属性取值方式、业务依赖关系、产品依赖关系类型、产品计费模式、
	//产品属性取值来源类型、业务资源取值类型、业务资源明细记录状态。                 add & modify by zhouxushun , 2005-9-29
	//SET_P_SERVICESTATUS
	public static final String SERVICESTATUS_NEW = "R";						//业务状态：创建
	public static final String SERVICESTATUS_NORMAL = "N";					//业务状态：正常
	public static final String SERVICESTATUS_CANCEL = "C";					//业务状态：停止
	public static final String SERVICESTATUS_TERMINAL = "T";				//业务状态：终止
	//SET_P_PRODUCTSTATUS
	public static final String PRODUCTSTATUS_NEW = "R";						//产品状态:创建
	public static final String PRODUCTSTATUS_NORMAL = "N";					//产品状态:正常
	public static final String PRODUCTSTATUS_CANCEL = "C";					//产品状态:停止
	public static final String PRODUCTSTATUS_TERMINAL = "T";				//产品状态:终止
	//SET_P_PRODUCTCLASS
	public static final String PRODUCTCLASS_HARD = "H";						//产品类别：硬件产品
	public static final String PRODUCTCLASS_SOFTWARE = "S";					//产品类别：软件产品
	//SET_P_PACKAGESTATUS
	public static final String PACKAGESTATUS_NEW = "R";						//产品包状态：创建
	public static final String PACKAGESTATUS_NORMAL = "N";					//产品包状态：正常
	public static final String PACKAGESTATUS_CANCEL = "C";					//产品包状态：停止
	public static final String PACKAGESTATUS_TERMINAL = "T";				//产品包状态：终止
	//SET_P_PRODUCTPROPERTYTYPE
	public static final String PRODUCTPROPERTYTYPE_INTEGER = "I";			//产品参数类型：整数
	public static final String PRODUCTPROPERTYTYPE_STRING = "S";			//产品参数类型：字符串
	public static final String PRODUCTPROPERTYTYPE_OTHER = "O";				//产品参数类型：其它
	public static final String PRODUCTPROPERTYTYPE_DOUBLE = "F";				//产品参数类型：浮点数
	//SET_P_PRODUCTPROPERTYMODE
	public static final String PRODUCTPROPERTYMODE_F = "F";					//产品属性取值方式：固定
	public static final String PRODUCTPROPERTYMODE_C = "C";					//产品属性取值方式：可配置
	//SET_P_SERVICEDEPENDENCYTYPE
	public static final String SERVICEDEPENDENCYTYPE_DEPENDENCE = "D";		//业务依赖关系：依赖
	public static final String SERVICEDEPENDENCYTYPE_EXCLUDE = "C";			//业务依赖关系：排斥
	//SET_P_PRODUCTDEPENDENCYTYPE
	public static final String PRODUCTDEPENDENCYTYPE_D = "D";				//产品依赖关系类型 ：授权依赖
	public static final String PRODUCTDEPENDENCYTYPE_P = "P";				//产品依赖关系类型 ：购买依赖
	public static final String PRODUCTDEPENDENCYTYPE_C = "C";				//产品依赖关系类型 ：排斥
	public static final String PRODUCTDEPENDENCYTYPE_A = "A";				//产品依赖关系类型 ：升级
	public static final String PRODUCTDEPENDENCYTYPE_F = "F";				//产品依赖关系类型 ：降级
	//SET_P_PRODUCTBILLINGMODE
	public static final String PRODUCTBILLINGMODE_U = "U";					//产品计费模式：用量计费
	public static final String PRODUCTBILLINGMODE_F = "F";					//产品计费模式：固定费
	//SET_P_PRODUCTPROPERTYVALUESOURCETYPE
	public static final String PRODUCTPROPERTYVALUESOURCETYPE_P = "P";		//产品属性取值来源类型：公用数据
	public static final String PRODUCTPROPERTYVALUESOURCETYPE_R = "R";		//产品属性取值来源类型：业务资源
	//SET_P_SERVICERESOURCEVALUETYPE
	public static final String SERVICERESOURCEVALUETYPE_STRING = "S";		//产品属性取值来源类型：公用数据
	public static final String SERVICERESOURCEVALUETYPE_INTEGER = "I";		//产品属性取值来源类型：业务资源
	//SET_P_SERVICERESOURCEDETAILSTATUS
	public static final String SERVICERESOURCEDETAILSTATUS_N = "N";			//业务资源明细记录状态：新建
	public static final String SERVICERESOURCEDETAILSTATUS_A = "A";			//业务资源明细记录状态：可用
	public static final String SERVICERESOURCEDETAILSTATUS_U = "U";			//业务资源明细记录状态：已用
	public static final String SERVICERESOURCEDETAILSTATUS_I = "I";			//业务资源明细记录状态：作废


	//工作流程，包括：工单类型、工单的状态、故障类型、故障原因、解决手段、用料情况、
	//工单预约结果、工作结果、处理不成功的原因、未能按照规定即时响应上门的原因、处理。  add & modify by zhouxushun , 2005-9-29
	//SET_W_JOBCARDTYPE
	public static final String JOBCARD_TYPE_REPAIR = "R";					//工单类型：维修
	public static final String JOBCARD_TYPE_INSTALLATION = "I";	            //工单类型：安装
	public static final String JOBCARD_TYPE_CATV = "C";                     //工单类型: 模拟电视网络施工
	//SET_W_JOBCARDSTATUS
	public static final String JOBCARD_STATUS_WAIT = "W";					//工单的状态：待处理
	public static final String JOBCARD_STATUS_BOOKED = "B";					//工单的状态：已预约
	public static final String JOBCARD_STATUS_SUCCESS = "S";					//工单的状态：处理成功
	public static final String JOBCARD_STATUS_FAIL = "F";					//工单的状态：处理不成功
	public static final String JOBCARD_STATUS_TERMINAL = "T";				//工单的状态：无法继续处理
	public static final String JOBCARD_STATUS_CANCEL = "C";					//工单的状态：取消
	public static final String JOBCARD_STATUS_CANCEL_ACCEPT = "C1";			//工单的状态：受理取消
	public static final String JOBCARD_STATUS_C_INSTALL = "C2";				//工单的状态：安装取消
	//SET_W_JOBCARDTROUBLETYPE
	public static final String JOBCARDTROUBLETYPE_A = "A";					//故障类型：设备故障
	public static final String JOBCARDTROUBLETYPE_B = "B";					//故障类型：室内信号故障
	public static final String JOBCARDTROUBLETYPE_C = "C";					//故障类型：室外信号故障
	public static final String JOBCARDTROUBLETYPE_D = "D";					//故障类型：用户来电取消
	public static final String JOBCARDTROUBLETYPE_E = "E";					//故障类型：自动恢复
	public static final String JOBCARDTROUBLETYPE_F = "F";					//故障类型：用户操作问题
	public static final String JOBCARDTROUBLETYPE_G = "G";					//故障类型：其他
	//SET_W_JOBCARDTROUBLESUBTYPE
	public static final String JOBCARDTROUBLESUBTYPE_A = "A";				//故障原因：智能卡
	public static final String JOBCARDTROUBLESUBTYPE_B = "B";				//故障原因：机顶盒
	public static final String JOBCARDTROUBLESUBTYPE_C = "C";				//故障原因：遥控器
	public static final String JOBCARDTROUBLESUBTYPE_D = "D";				//故障原因：室内线路
	public static final String JOBCARDTROUBLESUBTYPE_E = "E";				//故障原因：室外线路
	public static final String JOBCARDTROUBLESUBTYPE_F = "F";				//故障原因：操作问题
	//SET_W_JOBCARDRESOLUTIONTYPE
	public static final String JOBCARDRESOLUTIONTYPE_A = "A";				//解决手段：调换设备
	public static final String JOBCARDRESOLUTIONTYPE_B = "B";				//解决手段：整改线路
	public static final String JOBCARDRESOLUTIONTYPE_C = "C";				//解决手段：安装副端
	public static final String JOBCARDRESOLUTIONTYPE_D = "D";				//解决手段：野外设备更换
	public static final String JOBCARDRESOLUTIONTYPE_E = "E";				//解决手段：自动恢复
	public static final String JOBCARDRESOLUTIONTYPE_F = "F";				//解决手段：取消报修
	public static final String JOBCARDRESOLUTIONTYPE_G = "G";				//解决手段：其他
	//SET_W_JOBCARDMATERIALUSAGE
	public static final String JOBCARDMATERIALUSAGE_A = "A";				//用料情况：无
	public static final String JOBCARDMATERIALUSAGE_B = "B";				//用料情况：室内整改
	public static final String JOBCARDMATERIALUSAGE_C = "C";				//用料情况：安装付端
	//SET_W_JOBCARDCONTACTRESULT
	public static final String JOBCARDCONTACTRESULT_G = "G";				//工单预约结果：电话支持故障排除
	public static final String JOBCARDCONTACTRESULT_R = "R";				//工单预约结果：上门维修
	public static final String JOBCARDCONTACTRESULT_C = "C";				//工单预约结果：无法联系用户
	public static final String JOBCARDCONTACTRESULT_T = "T";				//工单预约结果：电话联系已好
	public static final String JOBCARDCONTACTRESULT_U = "U";				//工单预约结果：用户不愿支付费用取消上门
	//SET_W_JOBWORKRESULT
	public static final String JOBCARD_WORKRESULT_SUCCESS = "S";					//工作结果：处理成功
	public static final String JOBCARD_WORKRESULT_FAIL = "F";					//工作结果：处理失败
	//SET_W_JOBRESULTREASON
	public static final String JOBRESULTREASON_A = "A";						//处理不成功的原因：不愿安装付端
	public static final String JOBRESULTREASON_B = "B";						//处理不成功的原因：不愿走明线
	public static final String JOBRESULTREASON_C = "C";						//处理不成功的原因：双向网络未改造
	public static final String JOBRESULTREASON_D = "D";						//处理不成功的原因：已申请安装
	public static final String JOBRESULTREASON_E = "E";						//处理不成功的原因：上门无人
	public static final String JOBRESULTREASON_F = "F";						//处理不成功的原因：受理材料不全
	public static final String JOBRESULTREASON_G = "G";						//处理不成功的原因：网络故障
	public static final String JOBRESULTREASON_H = "H";						//处理不成功的原因：改期
	public static final String JOBRESULTREASON_O = "O";						//处理不成功的原因：用户放弃安装
	public static final String JOBRESULTREASON_P = "P";						//处理不成功的原因：对设备质量不满意
	public static final String JOBRESULTREASON_Q = "Q";						//处理不成功的原因：对节目内容不满意
	public static final String JOBRESULTREASON_R = "R";						//处理不成功的原因：对活动资费政策不满意
	public static final String JOBRESULTREASON_S = "S";						//处理不成功的原因：其他
	public static final String JOBRESULTREASON_A1 = "A1";					//处理不成功的原因：用户不愿安装副端
	public static final String JOBRESULTREASON_B1 = "B1";					//处理不成功的原因：非公司责任范围内故障
	public static final String JOBRESULTREASON_C1 = "C1";					//处理不成功的原因：用户不愿维修
	public static final String JOBRESULTREASON_D1 = "D1";					//处理不成功的原因：设备问题无法当天解决
	public static final String JOBRESULTREASON_E1 = "E1";					//处理不成功的原因：小区卫星信号干扰无法解决
	public static final String JOBRESULTREASON_F1 = "F1";					//处理不成功的原因：故障反复发生
	public static final String JOBRESULTREASON_G1 = "G1";					//处理不成功的原因：转专家组
	public static final String JOBRESULTREASON_H1 = "H1";					//处理不成功的原因：用户提出特殊要求无法满足
	//SET_W_JOBOUTOFDATEREASON
	public static final String JOBOUTOFDATEREASON_A = "A";					//未能按照规定即时响应上门的原因：用户改期
	public static final String JOBOUTOFDATEREASON_B = "B";					//未能按照规定即时响应上门的原因：无法联系用户
	public static final String JOBOUTOFDATEREASON_C = "C";					//未能按照规定即时响应上门的原因：上门无人
	public static final String JOBOUTOFDATEREASON_D = "D";					//未能按照规定即时响应上门的原因：因维修量过多而无法安排
	public static final String JOBOUTOFDATEREASON_E = "E";					//未能按照规定即时响应上门的原因：转专家组
	public static final String JOBOUTOFDATEREASON_F = "F";					//未能按照规定即时响应上门的原因：其他
	//SET_W_JOBCARDLOGTYPE
	public static final String JOBCARDLOGTYPE_BOOKING = "B";				//工单处理：预约
	public static final String JOBCARDLOGTYPE_DROPIN_WORK = "W";			//工单处理：上门工作
	public static final String JOBCARDLOGTYPE_END = "C";					//工单处理：结束
	public static final String JOBCARDLOGTYPE_ABORT = "A";					//工单处理：取消
	public static final String JOBCARDLOGTYPE_NEW = "N";					//工单处理：创建
	//暂定
	public static final String JOBCARDPROCESS_ACTION_CREATE ="W";		//创建工单---->待处理
	public static final String JOBCARDPROCESS_ACTION_CANCEL ="A";		//待处理---->取消
	public static final String JOBCARDPROCESS_ACTION_MODIFY ="M";       //待处理---->修改
	
	//public static final String JOBCARDPROCESS_ACTION_TELEPHONE ="G";		//待处理---->处理成功 适用于电话排障
	public static final String JOBCARDPROCESS_ACTION_BOOKING ="B";		//待处理---->已预约
	public static final String JOBCARDPROCESS_ACTION_TRANSFER ="T";		//待处理---->待处理
	public static final String JOBCARDPROCESS_ACTION_SUCCESS ="S";		//已预约---->处理成功
	//public static final String JOBCARDPROCESS_ACTION_FAIL ="F";			//已预约---->处理失败
	public static final String JOBCARDPROCESS_ACTION_END ="C";			//处理失败---->无法继续处理
	public static final String JOBCARDPROCESS_ACTION_REBOOKING ="RB";	//处理失败---->已预约

	//设备管理，包括：硬件设备状态、硬件地址类型、硬件出售方式、硬件是否为二手货、
	//设备流转的动作类型、设备型号状态、网络速度、设备流转原因、设备重新入库的原因、
	//设备型号参数类型。                                                       add & modify by zhouxushun , 2005-9-29
	//SET_D_DEVICESTATUS
	public static final String DEVICE_STATUS_STOCK = "S";					//硬件设备状态：库存
	public static final String DEVICE_STATUS_WAITFORSELL = "W";				//硬件设备状态：待售
	public static final String DEVICE_STATUS_LOCK = "L";					//硬件设备状态：锁定
	public static final String DEVICE_STATUS_SOLD = "C";					//硬件设备状态：已售（最新修改为在网）
	public static final String DEVICE_STATUS_WAIT4REPAIR = "R";				//硬件设备状态：待修
	public static final String DEVICE_STATUS_REPAIRING = "M";				//硬件设备状态：送修
	public static final String DEVICE_STATUS_DISCARD = "O";					//硬件设备状态：报废
	public static final String DEVICE_STATUS_LOST = "I";					//硬件设备状态：丢失
	public static final String DEVICE_STATUS_OFFLINE = "X";					//硬件设备状态：退网
	//SET_D_DEVICEADDRESSTYPE
	public static final String DEVICE_ADDRESSTYPE_DEPOT = "D";				//硬件地址类型：仓库
	public static final String DEVICE_ADDRESSTYPE_WITHUSER = "B";			//硬件地址类型：用户
	public static final String DEVICE_ADDRESSTYPE_INORGANIZATION = "T";		//硬件地址类型：组织机构
	//SET_D_DEVICESELLMETHOD
	public static final String DEVICESELLMETHOD_LEASEHOLD = "L";			//硬件出售方式：租赁
	public static final String DEVICESELLMETHOD_SALE = "B";					//硬件出售方式：出售
	//SET_D_USEFLAG
	public static final String DEVICE_USEFLAG_USED = "U";					//硬件是否为二手货：旧设备
	public static final String DEVICE_USEFLAG_NEW = "N";					//硬件是否为二手货：新设备
	//SET_D_DTACTION
	public static final String DEVICE_TRANSACTION_ACTION_N = "N";			//设备流转的动作类型：新设备入库
	public static final String DEVICE_TRANSACTION_ACTION_R = "R";			//设备流转的动作类型：设备送修
	public static final String DEVICE_TRANSACTION_ACTION_B = "B";			//设备流转的动作类型：设备调拨
	public static final String DEVICE_TRANSACTION_ACTION_O = "O";			//设备流转的动作类型：设备出库
	public static final String DEVICE_TRANSACTION_ACTION_T = "T";			//设备流转的动作类型：设备重新入库
	public static final String DEVICE_TRANSACTION_ACTION_H = "H";			//设备流转的动作类型：设备退还
	public static final String DEVICE_TRANSACTION_ACTION_G = "G";			//设备流转的动作类型：设备更换
	public static final String DEVICE_TRANSACTION_ACTION_I = "I";			//设备流转的动作类型：报废
	public static final String DEVICE_TRANSACTION_ACTION_L = "L";			//设备流转的动作类型：丢失
	public static final String DEVICE_TRANSACTION_ACTION_M = "M";			//设备流转的动作类型：手工修改设备状态
	public static final String DEVICE_TRANSACTION_ACTION_A = "A";			//设备流转的动作类型：异地过户
	public static final String DEVICE_TRANSACTION_ACTION_E = "E";			//设备流转的动作类型：同地址过户
	public static final String DEVICE_TRANSACTION_ACTION_P = "P";			//设备流转的动作类型：设备购买
	public static final String DEVICE_TRANSACTION_ACTION_X = "X";			//设备流转的动作类型：设备信息修改
	public static final String DEVICE_TRANSACTION_ACTION_J = "J";			//设备流转的动作类型：设备退网
	
	public static final String DEVICE_TRANSACTION_ACTION_DM = "DM";			//设备流转的动作类型：设备配对
	public static final String DEVICE_TRANSACTION_ACTION_DP = "DP";			//设备流转的动作类型：设备预授权
	public static final String DEVICE_TRANSACTION_ACTION_DU = "DU";			//设备流转的动作类型：设备解配对
	
	//SET_D_DEVICEMODELSTATUS
	public static final String DEVICEMODELSTATUS_NORMAL = "N";				//设备型号状态：正常
	public static final String DEVICEMODELSTATUS_NEW = "R";					//设备型号状态：创建
	public static final String DEVICEMODELSTATUS_CANCEL = "C";				//设备型号状态：取消
	public static final String DEVICEMODELSTATUS_TERMINATE = "T";			//设备型号状态：停止
	//SET_D_CABLETYPE
	public static final String CABLETYPE_C8 = "C8";							//网络速度：860M
	public static final String CABLETYPE_C7 = "C7";							//网络速度：750M
	//SET_D_TRANSFERREASON
	public static final String DEVICE_TRANSFERREASON_STOCK = "S";			//设备流转原因：库存
	public static final String DEVICE_TRANSFERREASON_MEND = "M";			//设备流转原因：维修
	//SET_D_STOCKINREASON
	public static final String DEVICE_STOCKINREASON_DAMNIFY = "D";			//设备重新入库的原因：损坏
	public static final String DEVICE_STOCKINREASON_BACK = "B";				//设备重新入库的原因：退回
	public static final String DEVICE_STOCKINREASON_REPAIR = "R";			//设备重新入库的原因：修复
	//SET_D_DEVICEMODELPROPERTYVALUETYPE
	public static final String DEVICEMODELPROPERTYVALUETYPE_INTEGER = "I";	//设备型号参数类型：整数
	public static final String DEVICEMODELPROPERTYVALUETYPE_STRING = "S";	//设备型号参数类型：字符串
	public static final String DEVICEMODELPROPERTYVALUETYPE_OTHER = "O";	//设备型号参数类型：其它
	public static final String DEVICEMODELPROPERTYVALUETYPE_DOUBLE = "F";	//设备型号参数类型：浮点数


	//市场，包括：团购券头信息状态、团购卷明细信息状态、客户促销活动状态、
	//促销活动时间长度类型、促销活动类型                                     add & modify by zhouxushun , 2005-9-29
	//SET_M_GROUPBARGAINSTATUS
	public static final String GROUPBARGAINSTATUS_NEW = "N";				//团购券头信息状态：新建
	public static final String GROUPBARGAINSTATUS_SOLD = "S";				//团购券头信息状态：售出
	public static final String GROUPBARGAINSTATUS_CANCEL = "C";				//团购券头信息状态：取消
	//SET_M_GROUPBARGAINDETAILSTATUS
	public static final String GROUPBARGAINDETAILSTATUS_WAIT = "W";			//团购卷明细信息状态：待售
	public static final String GROUPBARGAINDETAILSTATUS_SOLD = "S";			//团购卷明细信息状态：售出
	public static final String GROUPBARGAINDETAILSTATUS_RETURN = "R";		//团购卷明细信息状态：回收
	public static final String GROUPBARGAINDETAILSTATUS_CANCEL = "C";		//团购卷明细信息状态：作废
	public static final String GROUPBARGAINDETAILSTATUS_LOCK = "L";			//团购卷明细信息状态：锁定
	//SET_M_CAMPAIGNTIMELENGTHUNITTYPE
	public static final String CAMPAIGNTIMELENGTHUNITTYPE_YEAR = "Y";		//促销活动时间长度类型：年
	public static final String CAMPAIGNTIMELENGTHUNITTYPE_MONTH = "M";		//促销活动时间长度类型：月
	public static final String CAMPAIGNTIMELENGTHUNITTYPE_DAY = "D";		//促销活动时间长度类型：日
	//SET_M_CAMPAIGNCAMPAIGNTYPE
	public static final String CAMPAIGNCAMPAIGNTYPE_OPEN = "B";					//促销活动类型：套餐
	public static final String CAMPAIGNCAMPAIGNTYPE_NORMAL = "A";				//促销活动类型：普通促销
	//public static final String CAMPAIGNCAMPAIGNTYPE_GROUPBARGAIN = "T";			//促销活动类型：团购套餐
	//public static final String CAMPAIGNCAMPAIGNTYPE_GROUPBARGAIN_OR_OPEN = "O";	//促销活动类型：团购或开户套餐

	//系统，包括：操作员组对应的级别、节点类型、日志级别、日志信息、组织角色      add & modify by zhouxushun , 2005-9-29
	//SET_S_OPGROUPLEVEL
	public static final String OPGROUPLEVEL_COMPANY = "C";					//操作员组对应的级别：总公司操作员组
	public static final String OPGROUPLEVEL_FILIALE = "B";					//操作员组对应的级别：分公司操作员组
	public static final String OPGROUPLEVEL_STATICON = "S";					//操作员组对应的级别：街道站操作员组
	//SET_S_ORGANIZATIONORGTYPE
	public static final String ORGANIZATIONORGTYPE_GENERALCOMPANY = "C"; 	//节点类型：总公司
	public static final String ORGANIZATIONORGTYPE_DEPARTMENT = "D"; 		//节点类型：部门
	public static final String ORGANIZATIONORGTYPE_SUBCOMPANY = "B"; 		//节点类型：分公司
	public static final String ORGANIZATIONORGTYPE_PARTNER = "P"; 			//节点类型：合作伙伴
	public static final String ORGANIZATIONORGTYPE_BRANCH = "O"; 			//节点类型：门店
	public static final String ORGANIZATIONORGTYPE_STREETSTATION = "S"; 	//节点类型：街道站
	//暂时没有配置这个参数
	//public static final String ORGANIZATIONORGSUBTYPE_AGENT = "S"; 			//节点类型：代理商

	//SET_S_LOGCLASS
	public static final String LOGCLASS_NORMAL = "N"; 						//日志级别：一般操作信息
	public static final String LOGCLASS_CRUX = "C"; 						//日志级别：关键操作信息
	public static final String LOGCLASS_WARN = "W"; 						//日志级别：警告信息
	public static final String LOGCLASS_ERROR = "E"; 						//日志级别：错误信息
	//SET_S_LOGTYPE
	public static final String LOGTYPE_SYSTEM = "S"; 						//日志信息：系统
	public static final String LOGTYPE_APPLICATION = "A"; 					//日志信息：应用程序
	//SET_S_ROLEORGNIZATIONORGROLE
	
	public static final String ROLEORGNIZATIONORGROLE_SERVICE = "S"; 		//组织角色：维修
	public static final String ROLEORGNIZATIONORGROLE_INSTALL = "I"; 		//组织角色：安装
	public static final String ROLEORGNIZATIONORGROLE_TS = "C"; 			//组织角色：投诉处理
	public static final String ROLEORGANIZATIONORGROLE_REPAIRE = "R";		//组织角色: 报修
	public static final String ROLEORGANIZATIONORGROLE_M = "M";		        //组织角色: 模拟电视施工单处理


	//后台进程，包括：后台任务执行状态、排程任务状态、排程任务状态、排程任务状态。   add & modify by zhouxushun , 2005-9-29
	//SET_B_BGJOBSTATUS
	public static final String BGJOBSTATUS_NORMAL = "N"; 					//后台任务执行状态：等待执行
	public static final String BGJOBSTATUS_END = "D"; 						//后台任务执行状态：执行结束
	public static final String BGJOBSTATUS_ERROR = "E"; 					//后台任务执行状态：执行错误
	public static final String BGJOBSTATUS_PROCESS = "B"; 					//后台任务执行状态：正在执行
	//SET_B_SCHEDULESTATUS
	public static final String SCHEDULESTATUS_NORMAL = "N"; 				//排程任务状态：待处理
	public static final String SCHEDULESTATUS_CANCEL = "C"; 				//排程任务状态：已取消
	public static final String SCHEDULESTATUS_SUCCESS = "S"; 				//排程任务状态：处理失败
	public static final String SCHEDULESTATUS_FAIL = "F"; 					//排程任务状态：处理成功
	//SET_B_BATCHJOBPROCESSACTION
	public static final String BATCHJOBPROCESSACTION_CREATE = "C"; 				//排程任务动作状态：创建
	public static final String BATCHJOBPROCESSACTION_MODIFY = "M"; 				//排程任务动作状态：修改
	public static final String BATCHJOBPROCESSACTION_EXCUTE = "E"; 				//排程任务动作状态：执行
	public static final String BATCHJOBPROCESSACTION_CANCEL = "D"; 				//排程任务动作状态：取消
	//SET_B_SCHEDULERESULT
	public static final String SCHEDULERESULT_SUCCESS = "S"; 				//排程任务执行结果：成功
	public static final String SCHEDULERESULT_FAIL = "F"; 					//排程任务执行结果：失败


	//接口，包括：银行接口处理类型、帐单打印接口处理类型、银行扣款返回结果类型。   add & modify by zhouxushun , 2005-9-29
	//SET_I_PIFROCESSTYPE
	public static final String PIFROCESSTYPE_INPUT = "I"; 					//银行接口处理类型：银行扣款结果倒入
	public static final String PIFROCESSTYPE_OUTPUT = "O"; 					//银行接口处理类型：银行扣款文件倒出
	public static final String PIFROCESSTYPE_AO = "AO"; 					//银行接口处理类型：银行帐号核查文件倒出
	public static final String PIFROCESSTYPE_AI = "AI"; 					//银行接口处理类型：银行帐号核查结果倒入
	//SET_I_IPPROCESSTYPE
	public static final String IPPROCESSTYPE_INVOICE = "I"; 				//帐单打印接口处理类型：打印帐单
	public static final String IPPROCESSTYPE_PRESS_INVOICE = "D"; 			//帐单打印接口处理类型：打印催帐单
	//SET_F_BDRSTATUS
	public static final String BDRSTATUS_E = "E"; 							//银行扣款返回结果类型：已送托
	public static final String BDRSTATUS_F = "F"; 							//银行扣款返回结果类型：银行扣款成功
	public static final String BDRSTATUS_S = "S"; 							//银行扣款返回结果类型：银行扣款失败
	public static final String BDRSTATUS_SF = "SF"; 						//银行扣款返回结果类型：银行口款成功但数据校验失败
	public static final String BDRSTATUS_FF = "FF"; 						//银行扣款返回结果类型：银行扣款失败且数据校验失败


	//*************************************************
	//* 暂时没有定义的公用数据模块是：
	//* 3.11	网络、
	//* 3.12	模拟终端管理、
	//* 3.3.5	历史遗留配置参数
	//*************************************************/
	public static final String GENERALSTATUS_INVALID="I";                   //add by david.Yang

	//机卡匹配操作   ,add by zhouxushun
	public static final String MATCH_SC_TO_STB_M="M";						//机卡配对

	public static final String MATCH_SC_TO_STB_D="D";						//机卡解配对
	//消除密码 2006-2-16 add by wesley
	public static final String MATCH_SE_CA_CLEARPWD="C";					//消除密码
	public static final String MATCH_SE_CA_SENDMAIL="S";					//发送消息

	//后台批处理模块，add by zhouxushun 2005-12-27
	//SET_B_BATCHJOBREFERTYPE
	public static final String BATCH_JOB_REFER_TYPE_C="C";					//受理单
	public static final String BATCH_JOB_REFER_TYPE_Q="Q";					//批量查询
	public static final String BATCH_JOB_REFER_TYPE_U="U";					//从模拟导入
	//SET_B_BATCHJOBTYPE
	public static final String BATCH_JOB_TYPE_AL1="AL1";					//批量一次升级
	public static final String BATCH_JOB_TYPE_AL2="AL2";					//批量二次升级
	public static final String BATCH_JOB_TYPE_AS="AS";						//批量欠费暂停
	public static final String BATCH_JOB_TYPE_PC="PC";						//产品取消
	public static final String BATCH_JOB_TYPE_PR="PR";						//产品恢复
	public static final String BATCH_JOB_TYPE_PS="PS";						//产品暂停
	public static final String BATCH_JOB_TYPE_SM="SM";						//批量发送邮件
	public static final String BATCH_JOB_TYPE_SO="SO";						//批量发送信息
	public static final String BATCH_JOB_TYPE_UC="UC";						//用户取消
	public static final String BATCH_JOB_TYPE_UR="UR";						//用户恢复
	//SET_B_SCHEDULETYPE
	public static final String SCHEDULE_TYPE_I="I";							//立即执行
	public static final String SCHEDULE_TYPE_S="S";							//定时执行
	//SET_B_BATCHJOBSTATUS
	public static final String BATCH_JOB_STATUS_CANCEL="C";					//取消
	public static final String BATCH_JOB_STATUS_FAIL="F";					//执行失败
	public static final String BATCH_JOB_STATUS_WAIT="N";					//等待执行
	public static final String BATCH_JOB_STATUS_SUCESS="S";					//执行成功
	//SET_B_BATCHJOBACTION----------->SET_B_BATCHJOBTYPE
	//SET_G_PROCESSSTATUS ----------->SET_B_BATCHJOBSTATUS
	//结束后台批处理模块


	//批量查询用，add by zhouxushun 2006-1-9
	//SET_B_QUERYTYPE
	public static final String QUERY_TYPE_CUSTOMER="C";						//客户
	public static final String QUERY_TYPE_ACCOUNT="A";						//帐户
	public static final String QUERY_TYPE_PRODUCT="P";						//客户产品
	public static final String QUERY_TYPE_CUTOMER_CAMPAING="M";				//客户优惠
	
	//SET_B_QUERYSTATUS
	public static final String QUERY_STATUS_NEW="N";						//新建
	public static final String QUERY_STATUS_PROCESSING="W";					//正在处理
	public static final String QUERY_STATUS_SUCCESS="S";					//处理成功
	public static final String QUERY_STATUS_FAIL="F";						//处理失败
	public static final String QUERY_STATUS_CANCEL="C";						//取消
	//2006-1-24 BY WESLEY
	public static final String QUERY_STATUS_REFERED="I";					//已引用


	//SET_F_ACCOUNTADJUSTMENTSTATUS
	public static final String ADJUST_STATUS_CREATE ="W";                   //创建
	public static final String ADJUST_STATUS_VALIDATION  ="V" ;             //有效
	public static final String ADJUST_STATUS_INVALIDATION ="I" ;             //无效

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
//	T_GroupBargain SET_M_GROUPBARGAINSTATUS团购券头信息状态 Status
	public static final String GROUPBARGAIN_STATUS_NEW				= "N";	//新建		
	public static final String GROUPBARGAIN_STATUS_SALED			= "S";	//售出		
	public static final String GROUPBARGAIN_STATUS_CANCEL			= "C";	//取消
	public static final String GROUPBARGAIN_STATUS_FINISH			= "F";	//完成
//	SET_M_GROUPBARGAINDETAILSTATUS团购卷明细信息状态 T_GroupBargainDetail	Status
	
	public static final String GROUPBARGAINDETAIL_STATUS_WAIT 		="W";	//待售
	public static final String GROUPBARGAINDETAIL_STATUS_SALED 		="S";	//售出
	public static final String GROUPBARGAINDETAIL_STATUS_RECYCLE	="R";	//回收
	public static final String GROUPBARGAINDETAIL_STATUS_CANCEL		="C";	//作废
	public static final String GROUPBARGAINDETAIL_STATUS_LOCKED		="L";	//锁定
	public static final String SERVICEACCOUNTTRANSFER		="ST";	//业务帐户过户
	//客户投诉
	//SET_C_CUSTOMERCOMPLAINPROCESSSTATUS
	public static final String CUSTCOMPLAIN_PROCESS_SUCESS 		="V";	//处理成功
	public static final String CUSTCOMPLAIN_PROCESS_FALI 		="F";	//处理失败
	public static final String CUSTCOMPLAIN_PROCESS_WAIT 		="W";	//待处理
	//SET_C_CUSTCOMPLAINPROCESSACTION
	public static final String CUSTCOMPLAIN_PROCESS_TR		="TR";	//流转
	public static final String CUSTCOMPLAIN_PROCESS_CL		="CL";	//手工结束
	public static final String CUSTCOMPLAIN_PROCESS_US 		="US";	//上门维修
	public static final String CUSTCOMPLAIN_PROCESS_AF		="AF";	//调帐
	public static final String CUSTCOMPLAIN_PROCESS_PS		="PS";	//电话支持
	public static final String CUSTCOMPLAIN_PROCESS_CD		="CD";	//更换设备
	public static final String CUSTCOMPLAIN_PROCESS_PA		="PA";	//电话致谦
	public static final String CUSTCOMPLAIN_PROCESS_UA		="UA";	//上门致谦
	public static final String CUSTCOMPLAIN_PROCESS_CB		="CB";	//投诉回访
	public static final String CUSTCOMPLAIN_PROCESS_CR		="CR";	//新建
	
	//SET_C_CUSTOMERCOMPLAINSTATUS
	public static final String CUSTOMER_COMPLAIN_SUCESS 		="V";	//处理成功
	public static final String CUSTOMER_COMPLAIN_FALI 		="F";	//处理失败
	public static final String CUSTOMER_COMPLAIN_WAIT 		="D";	//正在处理
	
	//合同状态:
	public static final String CONTRACTSTATUS_NEW="N"; 		//新建
	public static final String CONTRACTSTATUS_EFFECT="E";	//生效
	public static final String CONTRACTSTATUS_OPEN="U";		//开户
	public static final String CONTRACTSTATUS_CANCEL="C"; 	//取消
	 
	public static final String CONTRACT_USED="U"; 	//使用合同
	public static final String CONTRACT_CREATE="N"; 	//创建合同
	public static final String CONTRACT_MODIFY="M";//修改

/*
*增加销账级别公用数据定义
*
*/
	public static final String SETOFF_LEVEL_I = "I"; 	//单据销账
	public static final String SETOFF_LEVEL_F = "F";        //费用销账
	
	public static final String BIDIM_STATUS_AVA = "V"; 	//2维配置数据状态
	public static final String BIDIM_DEFALT_VALUE_YES = "Y";        //是否默认值
	
	//期权
	public static final String FUTURERIGHT_RESULT_V = "V"; 	//正常
	public static final String FUTURERIGHT_RESULT_C = "C"; 	//取消
	public static final String FUTURERIGHT_RESULT_X = "X" ;  //已使用
	
	//SET_S_ORGANIZATIONPARTNERCATEGORY        T_Organization表的 OrgSubType 字段
	public static final String ORG_ORGSUBTYPE_A ="A";						//合作伙伴分类：产品提供商
	public static final String ORG_ORGSUBTYPE_G ="G";						//合作伙伴分类：设备提供商
	public static final String ORG_ORGSUBTYPE_P ="P";						//合作伙伴分类：付费渠道
	public static final String ORG_ORGSUBTYPE_S ="S";						//合作伙伴分类：代理商
	public static final String ORG_ORGSUBTYPE_Z ="Z";						//合作伙伴分类：安装服务提供商
	
	public static final String OSS_AUTHORIZATION_OPERATE_A ="A";			//预授权
	public static final String OSS_AUTHORIZATION_OPERATE_D ="D";//解预授权
//	SET_V_CSIPAYMENTSETTINGOPTION
	public static final String CSI_PAYMENT_OPTION_IM ="IM";   //立即支付
	public static final String CSI_PAYMENT_OPTION_OP ="OP";   //可选择
	public static final String CSI_PAYMENT_OPTION_IV ="IV";   //在月度帐单中支付
	public static final String CSI_PAYMENT_OPTION_SP ="SP";   //上门安装成功后收取
	
//前台页面传入的用来确定是否立即支付的配置值
	public static final String CSI_PAYMENT_OPTION_PAY="P";  //立即支付费用
	public static final String CSI_PAYMENT_OPTION_FINISH="F";//费用延迟支付,归入帐单
	//用于command返回给webAction 返回值的使用标志：
	public static final String COMMAND_ID_DEVICE="D";  //返回结果为设备
	public static final String COMMAND_ID_IMMEDIATEFEE="I";//返回结果为费用
	
	
    //定制发送消息所用到的变量命名variable
    public static final String SEND_MSG_CUSTOMER_NAME_VARIABLE="{$CUSTOMERNAME}";				//客户姓名
    public static final String SEND_MSG_CUSTOMER_NAME_VARIABLE_RGE="\\{\\$CUSTOMERNAME\\}";	
    public static final String SEND_MSG_ACCOUNT_BALANCE_VARIABLE="{$ACCOUNTBALANCE}";			//帐户余额
    public static final String SEND_MSG_ACCOUNT_BALANCE_VARIABLE_RGE="\\{\\$ACCOUNTBALANCE\\}";	
    public static final String SEND_MSG_INVOICEAMOUNT_VARIABLE="{$INVOICEAMOUNT}";				//帐单金额
    public static final String SEND_MSG_INVOICEAMOUNT_VARIABLE_RGE="\\{\\$INVOICEAMOUNT\\}";	

	public static final String SP_OPERATETYPE_F001 = "F001";

	//SET_F_BRRATETYPE
	public static final String BILLING_RULE_RATE_TYPE_V="V";			//数值
	public static final String BILLING_RULE_RATE_TYPE_P="P";			//比例
	//SET_A_CATVTERMINALRECORDSOURCE
	public static final String CATV_TERMINAL_RECORDSOURCE_E="E";	//扩建
	public static final String CATV_TERMINAL_RECORDSOURCE_S="S";	//补装
	public static final String CATV_TERMINAL_RECORDSOURCE_K="K";	//空关
	public static final String CATV_TERMINAL_RECORDSOURCE_I="I";	//数据导入

	//SET_A_CONSTRUCTIONSTATUS
	public static final String CONSTRUCTION_STATUS_NEW="I";	//新建
	public static final String CONSTRUCTION_STATUS_CONFIRM="F";	//确认
	public static final String CONSTRUCTION_STATUS_CANCEL="C";	//取消
	
	//见SET_A_CATVTERMINALSTATUS
	public static final String CATV_TERMINAL_STATUS_N="N";	//开通
	public static final String CATV_TERMINAL_STATUS_S="S";	//关断
	public static final String CATV_TERMINAL_STATUS_D="D";	//销号
	public static final String CATV_TERMINAL_STATUS_I="I";	//新建
	public static final String CATV_TERMINAL_STATUS_J="J";	//封端后私接
	public static final String CATV_TERMINAL_STATUS_U="U";	//不在册
	
	
	//终端类型：SET_A_CATVTERMTYPE
	public static final String CATV_TERMINAL_TYPE_N="N";	//普通住户
	public static final String CATV_TERMINAL_TYPE_E="E";	//企业单位
	
	//SET_W_JOBCARDSUBTYPE
	public static final String CATV_JOBCARD_SUBTYPE_F = "F";   //封端单
	public static final String CATV_JOBCARD_SUBTYPE_H = "H";   //恢复开通作业单
	public static final String CATV_JOBCARD_SUBTYPE_T = "T";   //停用作业单
	public static final String CATV_JOBCARD_SUBTYPE_Q = "Q";   //迁出停用作业单
	public static final String CATV_JOBCARD_SUBTYPE_R = "R";   //迁入开通作业单
	public static final String CATV_JOBCARD_SUBTYPE_Z = "Z";   //增端安装作业单
	public static final String CATV_JOBCARD_SUBTYPE_W = "W";   //网络施工单
	//SET_A_CATVTERMPROCESSTYPE
	public static final String CATV_TERMINAL_PROCESSTYPE_A = "A";  //房产配套导入
	public static final String CATV_TERMINAL_PROCESSTYPE_B = "B";  //补装
	public static final String CATV_TERMINAL_PROCESSTYPE_C = "C";  //信息修改
	public static final String CATV_TERMINAL_PROCESSTYPE_D = "D";  //开户
	public static final String CATV_TERMINAL_PROCESSTYPE_E = "E";  //迁入
	public static final String CATV_TERMINAL_PROCESSTYPE_F = "F";  //迁出
	public static final String CATV_TERMINAL_PROCESSTYPE_G = "G";  //主动封端
	public static final String CATV_TERMINAL_PROCESSTYPE_H = "H";  //主动封端复用
	public static final String CATV_TERMINAL_PROCESSTYPE_I = "I";  //欠费封端
	public static final String CATV_TERMINAL_PROCESSTYPE_J = "J";  //欠费封端复用
	public static final String CATV_TERMINAL_PROCESSTYPE_K = "K";  //销户
	
	//SET_A_CATVOPENTYPE
	public static final String CATV_OPEN_TYPE_NEW="N";				//新增终端(N)
	public static final String CATV_OPEN_TYPE_A="A";				//新增端口(A)
	public static final String CATV_OPEN_TYPE_O="O";				//恢复开通(O)

	//SET_W_JOBCARDREFERTYPE工单关联类型
	public static final String JOBCARD_REFERTYPE_P="P";				//报修单
	public static final String JOBCARD_REFERTYPE_S="S";				//受理单
	
	
	//T_DEVICEPREMATCHRECORD操作类型(OperationType) 
	public static final String SET_D_DEVICEPREAUTH_OPERATIONTYPE_AM = "AM"; 		//记录设备配对情况-操作类型：配对
	public static final String SET_D_DEVICEPREAUTH_OPERATIONTYPE_AP = "AP"; 		//记录设备配对情况-操作类型：预授权
	public static final String SET_D_DEVICEPREAUTH_OPERATIONTYPE_DA = "DA"; 		//记录设备配对情况-操作类型：解所有授权
	public static final String SET_D_DEVICEPREAUTH_OPERATIONTYPE_DM = "DM"; 		//记录设备配对情况-操作类型：解配对
	public static final String SET_D_DEVICEPREAUTH_OPERATIONTYPE_DP = "DP"; 		//记录设备配对情况-操作类型：解授权
	
	
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
	
	public static final int EXPORT_MAX =65000 ;                         //Excel导出最大总数

}	
