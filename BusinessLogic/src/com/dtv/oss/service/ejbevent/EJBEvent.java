package com.dtv.oss.service.ejbevent;

public interface EJBEvent extends java.io.Serializable {

	// 账户操作有关动作类型-------------开始--------------------------
	public static final int CLOSECUSTOMERACCOUNT = 125; // 客户帐户销户

	public static final int OPENACCOUNTFORCUSTOMER = 203; // 新增帐户

	public static final int PAYCHEK =30 ; //帐单检查
	
	public static final int PRESAVE = 31; // 预存

	public static final int PAYBYBILL = 32; // 支付账单

	public static final int UPDATEACCOUNTINFO = 206; // 修改账户信息
	
	public static final int BATCHALTEACCOUNTINFO =207 ; //批量修改账户信息
	
	public static final int CHECKBANKINFOFORMAT =217 ; //批量修改账户信息_银行信息格式校验
	
	public static final int INVOICE_BATCH_PAY = 208;//批量支付帐单
	
	public static final int MERGEINVOICE_BATCH_PAY =210; //批量支付合并帐单
	
	public static final int SINGLE_ACCOUNT_PAY =300;  //单帐户支付

	// 账户操作有关类型-------------结束---------------------------------
	// 调帐操作有关动作类型-------------------------开始-----------------
	public static final int ADJUSTMENT = 34; // 账户调账

	public static final int CSI_ADJUSTMENT = 39; // 账户调账

	public static final int INVOICE_ADJUSTMENT = 36; // 未付帐单调帐

	public static final int REPAIR_ADJUST_OP = 408; // 报修单调账

	// 调帐操作有关动作类型-------------------------结束-----------------

	// 退费操作有关动作类型-------------开始--------------------------
	public static final int RETURNFEE4FAILINSTALLATION = 105; // 安装不成功退费

	// 退费操作有关动作类型-------------结束--------------------------

	// 回访操作有关动作类型-------------开始-----------------------------
	public static final int CALLFOROPENACCOUNT = 103; // 开户回访

	public static final int SETCALLFLAG4OPENACCOUNT = 110; // 开户回访暂存

	public static final int CALL_CUSTOMER_FOR_REPAIR = 405; // 报修回访

	public static final int SET_CALLFLAG_FOR_REPAIR = 106; // 报修回访暂存

	public static final int CALL_FOR_COMPLAIN = 424; // 投诉回访

	public static final int SET_CALL_FOR_COMPLAIN = 426; // 投诉回访

	// 回访操作有关动作类型-------------结束------------------------------

	// 有关Customer操作--------------开始---------------------------
	public static final int BOOKING = 100; 					// 预约

	public static final int CANCELBOOKING = 201; 			// 取消预约

	public static final int ALTERBOOKING = 202; 			// 改变预约

	public static final int AGENT_BOOKING = 204; 			// 代理商预约

	public static final int CHECK_AGENT_BOOKING = 205; 		// 确认代理商预约

	public static final int OPENACCOUNTINBRANCH = 101; 		// 门店开户

	public static final int OPENACCOUNTFORBOOKING = 102; 	// 预约开户
	
	public static final int OPENACCOUNTDIRECTLY = 103; 		//客户资料单独录入开户

	public static final int ALTERCUSTOMERINFO = 207; 		// 修改客户信息

	public static final int MOVETODIFFERENTPLACE = 107; 	// 迁移

	public static final int TRANSFERTODIFFERENTPLACE = 108; // 异地过户

	public static final int TRANSFERTOSAMEPLACE = 109; 		// 原地过户

	public static final int CLOSE_CUSTOMER = 126; 			// 客户销户

	public static final int WITHDRAW = 124; 				// 退户

	public static final int OPEN_CATV_ACCOUNT = 127; 		// 模拟电视开户


	
	// 有关Customer操作--------------结束---------------------------

	// 客户产品、业务帐户相关操作----------------------开始----------------------------
	public static final int DEVICESWAP = 106; // 设备更换
	public static final int DEVICEUPGRADE = 128; // 设备升级
	public static final int PRODUCTACCOUNTMODIFY =53; // customerProduct(accountID) modify
	public static final int DEVICEPROVIDEMODIFY=54;//

	public static final int PURCHASE = 56; // product(device) purchase

	public static final int SUSPEND = 57; // product、sa suspend

	public static final int RENT = 80; // product、sa rent
	
	public static final int RESUME = 58; // product、sa resume

	public static final int TRANSFER = 60; // product、sa transfer

	public static final int CANCEL = 62; // product、sa cancel

	public static final int UPGRADE = 63; // product upgrade

	public static final int DOWNGRADE = 64; // product downgrade

	public static final int UPDOWNGRADE = 65; // product updowngrade
	
	public static final int SERVICE_ACCOUNT_CODE_UPDATE = 66; // product updowngrade
	
	public static final int BATCH_SUSPEND = 68; // 业务帐户批量暂停
	
	public static final int BATCH_RESUME = 70; // 业务帐户批量恢复
	
	public static final int PROTOCOL_DATE_MODIFY = 71; //协议截止日期修改

	public static final int MOVE = 69; // product、sa move
	// add by Hoarce Lin 2004-11-29
	public static final int RESEND_EMM_BY_SERVICE_ACCOUNT = 900; // 根据业务帐户重授权
	
	public static final int REAUTHORIZE_FOR_SA_BY_CUSTOMER = 908; // 根据客户重授权客户下的业务帐户

	// add by WESLEY 2006/02/23
	public static final int REAUTHORIZEPRODUCT = 901; // 产品重授权

	public static final int CLEARPASSWORD = 902; // 清除密码

	public static final int PARTNERSHIP = 903; // 机卡配对

	public static final int SEND_EMAIL_CA = 904; // 发送EMAIL

	public static final int CANCEL_PARTNERSHIP = 912; // 清除机卡配对

	// 客户产品、业务帐户相关操作----------------------结束----------------------------

	public static final int BATCHPAUSEPRODUCT = 120; // 批量暂停客户产品

	public static final int BATCHRESUMEPRODUCT = 121; // 批量恢复客户产品

	public static final int BATCHASSIGNCAMPAIGN = 123; // 批量授予优惠

	public static final int PAYFEEFOROPENING = 104; // 支付开户费

	public static final int UPDATECSIFEE2PAYMENT = 111; // 受理单调帐

	// public static final int DEVICEEXCHANGE = 9; //device exchange

	public static final int DEVICERETURN = 10; // device return

	public static final int JOBCARDASSIGN = 12; // JobCard Assign

	public static final int CLOSE = 14; // JobCard/CustomerProblem close

	public static final int LOGIN = 21; // operator login
	
	public static final int BEFOREHAND_CLOSE = 25; // JobCard/CustomerProblem beforehand close
	
	public static final int REAL_CLOSE = 26; // JobCard/CustomerProblem beforehand close
	// started by thurm
	public static final int WITHDRAW_FORCED_DEPOSIT = 35; // 退还押金

	public static final int WITHDRAW_CONFISCATE_DEPOSIT = 37; // 没收押金

	// start of CustomerRelation and workflow 400~599
	// CustomerRelation Repair 401~420
	public static final int PROCESS_CUSTOMER_PROBLEM = 401; // 报修

	public static final int MANUAL_TRANSFER_REPAIR_SHEET = 402; // 报修单手工流转

	public static final int MANUAL_CLOSE_REPAIR_SHEET = 403; // 手工结束报修单

	public static final int TERMINATE_REPAIR_INFO = 404; // 终止维修

	// CustomerRelation Other 441~460
	public static final int CREATE_MESSAGE = 441; // 创建消息

	public static final int CREATE_CALLBACKINFOSETTING = 445; // 新增CallbackInfoSetting

	public static final int UPDATE_CALLBACKINFOSETTING = 446; // 修改CallbackInfoSetting

	public static final int DELETE_CALLBACKINFOSETTING = 447; // 删除CallbackInfoSetting

	public static final int CREATE__CALLBACKINFOVALUE = 448; // 新增CallbackInfoValue

	public static final int UPDATE__CALLBACKINFOVALUE = 449; // 修改CallbackInfoValue

	public static final int DELETE__CALLBACKINFOVALUE = 450; // 删除CallbackInfoValue

	public static final int CREATE_DIANOSISPARAMETER = 451; // 新增诊断参数

	public static final int UPDATE_DIANOSISPARAMETER = 452; // 更新诊断参数

	public static final int DELETE_DIANOSISPARAMETER = 453; // 删除诊断参数

	// Workflow Repair 461~480
	public static final int ASSIGN_REPAIR = 461; // 报修派工

	public static final int CONTACT_USER_FOR_REPAIR = 462; // 维修预约

	public static final int REGISTER_REPAIR_INFO = 463; // 录入维修信息

	public static final int CLOSE_REPAIR_INFO = 464; // 结束维修工单
	// public static final int PRINT_REPAIR_INFO = 465; //打印维修工单

	public static final int CANCEL_REPAIR_JOB_CARD = 466; // 取消维修工单,实际上与修改属同一类

	public static final int DIAGNOSIS_REPAIR = 467;// 维修诊断
	// Workflow Installation 481~500
	public static final int CONTACT_USER_FOR_INSTALLATION = 481; // 安装预约

	public static final int REGISTER_INSTALLATION_INFO = 482; // 录入安装信息
	// public static final int PRINT_INSTALLATION_INFO = 483; //打印安装工单

	public static final int CLOSE_INSTALLATION_INFO = 484; // 结束安装工单

	public static final int CANCEL_INSTALLATION_JOB_CARD = 485; // 取消安装工单,实际上与修改属同一类

	public static final int UPDATE_JOB_CARD = 521; // 修改工单

	
	public static final int MANUAL_CREATE_JOBCARD = 522;// 手工创建工单
	
	
	
	public static final int BATCH_CATV_REGISTER_INSTALLATION_INFO = 535;    // catv批量录入施工信息
	
	// Statistic 551~599
	// market about userpoints exchange
	public static final int USERPOINTS_EXCHANGE = 530;
	
	public static final int BATCH_CONTACT_USER_FOR_INSTALLATION = 531; // 批量安装预约
	public static final int BATCH_REGISTER_INSTALLATION_INFO = 532;    // 批量录入安装信息
	public static final int BATCH_CONTACT_USER_FOR_REPAIR = 533;       // 批量维修预约
	public static final int BATCH_CATV_CONTACT_USER = 534;             // 批量模拟工单预约

	public static final int CUSTOMER_ADD_SUBSCRIBER = 1000; // 门店增机提交
	
	public static final int CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER = 1005; // 预约增机提交
	
	public static final int CUSTOMER_BOOKINGUSER_CANCEL_SUBSCRIBER =1006; //预约增机的预约取消
	
	public static final int CUSTOMER_BOOKINGUSER_UPDATE_SUBSCRIBER =1007; //预约增机的预约修改
	
	public static final int CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER_FORBOOKING = 1008; // 预约增机的预约提交

	// ------------针对检查的事件定义开始，检查事件（2000-2999）开始----------------------------
	public static final int CHECK_BOOKINGUSER_PRODUCTPG_CAMPAINPG = 2000; // 检查预约增机预约的产品包和优惠包
	
	public static final int CHECK_CUSTOMERINFO = 2001; // 检查开户信息

	public static final int CHECK_PRODUCTPG_CAMPAINPG = 2002; // 检查开户的产品包和优惠包

	public static final int CHECK_HARDPRODUCTINFO = 2003; // 检查开户的硬件产品

	public static final int CHECK_FREEINFO = 2004; // 检查费用信息

	public static final int CHECK_FOR_MOVE = 2006; // 迁移前检查
	
	public static final int CHECK_OPEN_ACCOUNT_CATV = 2009; //检查模拟开户信息
	
	public static final int CACULATE_OPEN_CATV_FEE = 2010; //检查模拟开户信息
	
	public static final int CHECK_BUY_PRODUCT = 2011; //重复购买,检查选择产品有效性	

	public static final int CHECK_BATCHBUY_PRODUCTINFO = 2012; // 重复购买检查开户的产品包和优惠包
	
	public static final int BATCHADD_PRODUCTPACKAGE =2013; //批量增加产品包

	public static final int CUSTOMER_BATCHBUY_ADD_SUBSCRIBER = 1013; // 重复购买门店增机提交
	// ------------针对检查的事件定义开始，检查事件（2000-2999）结束----------------------------

	// 排程用的事件
	public static final int SCHEDULE_CREATE = 801; // 排程创建

	public static final int SCHEDULE_MODIFY = 802; // 排程修改

	public static final int SCHEDULE_CANCEL = 803; // 排程取消

	// 排程事件结束

	// 批量查询用
	public static final int BATCH_QUERY_CREATE = 901; // 批量查询创建

	public static final int BATCH_QUERY_MODIFY = 902; // 批量查询修改

	public static final int BATCH_QUERY_CANCEL = 903; // 批量查询取消

	public static final int BATCH_QUERY_EXCUTE = 904; // 批量查询执行

	public static final int BATCH_QUERY_RESULT_CHANGE = 905; // 批量查询结果翻转

	// 批量查询用-end

	// 批量任务单事件
	public static final int BATCHJOB_CREATE = 1101; // 批量任务单创建

	public static final int BATCHJOB_MODIFY = 1102; // 批量任务单修改

	public static final int BATCHJOB_CANCEL = 1103; // 批量任务单取消

	// 批量任务单结束
	// 市场活动事件
	public static final int CAMPAIGN_CREATE = 1150; // 促销活动创建

	public static final int CAMPAIGN_UPDATE = 1151; // 促销活动修改

	public static final int GROUPBARGAIN_CREATE = 1552; // 团购 新建

	public static final int GROUPBARGAIN_DELETE = 1553; // 团购 删除

	public static final int GROUPBARGAIN_UPDATE = 1554; // 团购 更新

	public static final int GROUPBARGAIN_SALE = 1555; // 团购 售出

	public static final int CONFIG_ACTIVITY_CREATE = 1557; // 创建活动

	public static final int CONFIG_ACTIVITY_UPDATE = 1558; // 修改活动

	public static final int CONFIG_ACTIVITY_DELETE = 1559; // delete the
															// activity

	public static final int CONFIG_GOODS_DELETE = 1560; // delete the goods

	public static final int CONFIG_GOODS_CREATE = 1561; // create the goods

	public static final int CONFIG_GOODS_EDIT = 1562; // update the goods

	public static final int CONFIG_RULE_DELETE = 1563; // delete the rule

	public static final int CONFIG_RULE_CREATE = 1564; // create the rule

	public static final int CONFIG_RULE_EDIT = 1565; // update the rule
	
	public static final int CONFIG_COND_DELETE = 8000; // delete the rule

	public static final int CONFIG_COND_CREATE = 8001; // create the rule

	public static final int CONFIG_COND_EDIT = 8002; // update the rule

	public static final int CONFIG_BIDIM_DELETE = 1566; // delete the BIDIM

	public static final int CONFIG_BIDIM_CREATE = 1567; // create the BIDIM

	public static final int CONFIG_BIDIM_EDIT = 1568; // update the BIDIM

	public static final int CONFIG_BIDIM_UPDATE = 15681; // update the BIDIM

	public static final int CONFIG_VALUE_DELETE = 1570; // delete the value

	public static final int CONFIG_VALUE_CREATE = 1571; // create the value

	public static final int CONFIG_VALUE_EDIT = 1572; // update the value

	public static final int CONFIG_CUMULATED_RULE_DELETE = 1570; // delete
																	// the value

	public static final int CONFIG_CUMULATED_RULE_CREATE = 1571; // create
																	// the value

	public static final int CONFIG_CUMULATED_RULE_EDIT = 1572; // update the
																// value

	public static final int CONFIG_MARKET_SEGMENT_DELETE = 1580; // delete
																	// the value

	public static final int CONFIG_MARKET_SEGMENT_CREATE = 1581; // create
																	// the value

	public static final int CONFIG_MARKET_SEGMENT_EDIT = 1582; // update the
																// value

	public static final int CONFIG_DISTRICT_CREATE = 1584; // create the value

	// 仓库操作事件start
	public static final int DEPOT_CREATE = 1601; // 仓库创建

	public static final int DEPOT_DELETE = 1602; // 仓库删除

	public static final int DEPOT_UPDATE = 1603; // 仓库更新

	// 仓库操作事件end

	// 业务操作事件
	public static final int SERVICE_CREATE = 3001; // 业务定义创建

	public static final int SERVICE_DELETE = 3002; // 业务定义删除

	public static final int SERVICE_UPDATE = 3003; // 业务定义更新

	// 业务依赖操作事件
	public static final int SERVICEDEPENDCE_CREATE = 3004; // 业务依赖创建

	public static final int SERVICEDEPENDCE_DELETE = 3005; // 业务依赖删除

	// 业务资源操作事件
	public static final int SERVICERESOURCE_CREATE = 3006; // 业务资源创建

	public static final int SERVICERESOURCE_DELETE = 3007; // 业务资源删除

	public static final int SERVICERESOURCE_UPDATE = 3008; // 业务资源更新

	// 业务资源明细操作事件
	public static final int SERVICERESOURCEDETAIL_CREATE = 3009; // 业务资源明细创建

	public static final int SERVICERESOURCEDETAIL_DELETE = 3010; // 业务资源明细删除

	// 产品操作
	public static final int PRODUCT_CREATE = 4001; // 添加产品

	public static final int PRODUCT_MODIFY = 4002; // 修改产品
	
	public static final int PRODUCT_DELETE = 4003; // 删除产品

	// 产品依赖关系操作
	public static final int PRODUCT_DEPENDENCY_CREATE = 4005; // 添加产品关系

	public static final int PRODUCT_DEPENDENCY_MODIFY = 4006; // 修改产品关系

	public static final int PRODUCT_DEPENDENCY_DELETE = 4007; // 删除产品关系

	// 产品属性操作
	public static final int PRODUCT_PROPERTY_CREATE = 4010; // 添加产品属性

	public static final int PRODUCT_PROPERTY_MODFIY = 4011; // 修改产品属性

	public static final int PRODUCT_PROPERTY_DELETE = 4012; // 修改产品属性

	// 产品包操作
	public static final int PRODUCT_PACKAGE_CREATE = 4013; // 添加产品包

	public static final int PRODUCT_PACKAGE_MODIFY = 4014; // 修改产品包

	public static final int CONFIG_PACKAGE_MARKET_SEGMENT = 4016; // 创建packageLine
	
	//产品设备操作
	public static final int DELETE_DEVICE_TO_PRODUCT = 4017;
	
	public static final int ADD_DEVICE_TO_PRODUCT = 4018;

	// 费用支付操作
	public static final int BILLING_RULE_CONDITION_CREATE = 4020; // 创建支付条件

	public static final int BILLING_RULE_CONDITION_MODIFY = 4021; // 修改支付条件

	public static final int BILLING_RULE_CONDITION_DELE = 4022; // 删除支付条件

	// 付费方式操作
	public static final int METHOD_OF_PAYMENT_CREATE = 4025; // 创建付费方式

	public static final int METHOD_OF_PAYMENT_MODIFY = 4026; // 修改付费方式

	public static final int METHOD_OF_PAYMENT_DELE = 4027; // 删除付费方式
	
//	 付费规则操作
	public static final int BILLING_RULE_CREATE = 4030; // 创建付费方式

	public static final int BILLING_RULE_MODIFY = 4031; // 修改付费方式

	public static final int BILLING_RULE_DELE = 4032; // 删除付费方式
	
//	 系统配置操作
	public static final int BOSS_CONFIG_CREATE = 4040; // 创建付费方式

	public static final int BOSS_CONFIG_MODIFY = 4041; // 修改付费方式
	
	public static final int DELETE_GROUP_TO_MEMBER = 4044; // 删除group member
	
	public static final int ADD_GROUP_TO_MEMBER = 4045; // 修改付费方式
	
	public static final int DELETE_GROUP_TO_PRIVILEGE = 4042; // 删除group member
	
	public static final int ADD_GROUP_TO_PRIVILEGE = 4043; // 修改付费方式
		
	public static final int ADD_OPGROUP = 4050; // 修改付费方式
	
	public static final int MODIFY_OPGROUP = 4051; // 修改付费方式
	
	public static final int DELETE_ORG_TO_DISTRICT = 4084; // 删除group member
	
	public static final int ADD_ORG_TO_DISTRICT = 4085; // 修改付费方式
	
	public static final int DELETE_OPTOGROUP = 4086; // 删除group member
	
	public static final int ADD_OPTOGROUP = 4087; // 增加操作员组
	
	public static final int CREATE_OPERATOR = 4090; // 添加操作员
	
	public static final int MODIFY_OPERATOR = 4091; // 修改操作员
	
	public static final int RECACULATE_RULE = 4100; // 重新计费
	
	public static final int DELETE_PRODUCT = 5000; // 删除group member
	
	public static final int ADD_PRODUCT = 5001; // 修改付费方式
	
	public static final int MODIFY_LDAP_HOST = 5010; // 修改ldaphost
	
	public static final int ADD_LDAP_HOST = 5011; // 添加ldaphost
	
	public static final int MODIFY_LDAP_PRODUCT = 5020; // 修改ldaphost
	
	public static final int ADD_LDAP_PRODUCT= 5021; // 添加ldaphost
	
	public static final int MODIFY_LDAP_EVENTCMDMAP = 5022; // 修改ldaphost
	
	public static final int ADD_LDAP_EVENTCMDMAP= 5023; // 添加ldaphost
	
	public static final int MODIFY_LDAPPROSMS = 5030; // 修改ldaphost
	
	public static final int ADD_LDAPPROSMS= 5031; // 添加ldaphost
	
	public static final int MODIFY_LDAPATTR = 5032; // 修改ldaphost
	
	public static final int ADD_LDAPATTR= 5033; // 添加ldaphost
	
	public static final int DEL_SERVICE = 5039; // 删除业务
	
	public static final int ADD_SERVICE= 5040; // 添加业务
	
	public static final int MODIFY_LDAPCOND = 5041; // 修改ldapcond
	
	public static final int ADD_LDAPCOND = 5042; // 添加ldapcond
	//亲情号码
	public static final int FRIEND_PHONENO_CREATE	= 201; //亲情号码新增 	 
	public static final int FRIEND_PHONENO_DELETE	= 202; //亲情号码删除
	
//	 费用支付操作
	public static final int BILLBOARD_CREATE = 6000; // 创建公告信息

	public static final int BILLBOARD_MODIFY = 6001; // 修改公告信息

	public static final int BILLBOARD_DELE = 6002; // 删除公告信息
	
	public static final int SYSTEM_SETTING_MODIFY = 6003; // 修改系统全局配置信息
	
	public static final int CAMPAIGN_COND_CREATE = 6015; // 促销活动条件创建
	
	public static final int CAMPAIGN_COND_UPDATE = 6016; // 促销活动条件修改
	
	public static final int CAMPAIGN_COND_PRODUCT = 6017; // 促销活动条件修改
	
	public static final int CAMPAIGN_COND_PACKAGE = 6018; // 促销活动条件修改
	
	public static final int CAMPAIGN_COND_CAMPAIGN = 6019; // 促销活动条件修改
	
	public static final int CAMPAIGN_PAYMENTMETHOD_DELETE = 6020; // 套餐付费方式删除
	
	public static final int BUNDLE_PREPAYMENT_DELETE = 6023; // 套餐付费方式MODIFY
	
	public static final int AGMT_CAMPAIGN_DELETE = 6024; // 套餐付费方式MODIFY
	
    public static final int MANUAL_TRANSFER_CREATE = 6040; // 手工流转创建
	
	public static final int MANUAL_TRANSFER_MODIFY = 6041; // 手工流转更新
	
	public static final int MANUAL_TRANSFER_DELETE = 6042; // 手工流转删除
	
    public static final int FEE_PLAN_CREATE = 6050; // 支费计划创建
	
	public static final int FEE_PLAN_MODIFY = 6051; // 支费计划更新
	
	public static final int FEE_PLAN_DELETE = 6052; // 支费计划删除
	
    public static final int FEE_PLAN_ITEM_CREATE = 6055; // 支费计划创建
	
	public static final int FEE_PLAN_ITEM_MODIFY = 6056; // 支费计划更新
	 
    public static final int CONFIG_CSI_REASON_NEW = 6070;
	
	public static final int CONFIG_CSI_REASON_UPDATE = 6071; 
	
    public static final int CONFIG_CSI_REASON_DETAIL_NEW = 6072;
	
	public static final int CONFIG_CSI_REASON_DETAIL_UPDATE = 6073; 
	 
	//客户化计费规则维护	add by jason 2007-6-22
	public static final int CUSTOMER_BILLING_RULE_NEW = 7001; 			//新建
	
	public static final int CUSTOMER_BILLING_RULE_UPDATE = 7002; 		//修改
	
	public static final int CONFIG_MACHINEROOM_UPDATE = 7003; 
	
	public static final int CONFIG_MACHINEROOM_CREATE = 7004; 
	
	public static final int CONFIG_MACHINEROOM_REMOVE = 7005; 
	
    public static final int CONFIG_FIBERTRANSMITTER_UPDATE = 7006; 
     
	public static final int CONFIG_FIBERTRANSMITTER_CREATE = 7007; 
	
	public static final int CONFIG_FIBERTRANSMITTER_REMOVE = 7008; 
	
	public static final int CONFIG_FIBERRECEIVER_UPDATE = 7009; 
    
	public static final int CONFIG_FIBERRECEIVER_CREATE = 7010; 
	
	public static final int CONFIG_FIBERRECEIVER_REMOVE = 7011; 
	
    public static final int CONFIG_FIBERNODE_UPDATE = 7012; 
    
	public static final int CONFIG_FIBERNODE_CREATE = 7013; 
	
	public static final int CONFIG_FIBERNODE_REMOVE = 7014; 
	
	public static final int CATV_TERMINAL_NEW = 7015;
	
	public static final int CATV_TERMINAL_UPDATE = 7016;
		
	public static final int CATV_CONTACT_USER_FOR_CONSTRUCTION = 7018;
	
	public static final int CATV_REGISTER_JOBCARD = 7019;
	
	public static final int CATV_CLOSE_JOBCARD = 7020;
	
	public static final int CAMPAIGN_PAYMENT_AWARD_CREATE = 7021;
	
	public static final int CAMPAIGN_PAYMENT_AWARD_DELETE = 7022;
	
	public static final int CATV_CANCEL_JOBCARD = 7023;
	
	public static final int ACCOUNT_MANUAL_FEE = 7026;
	public static final int UPLOAD_FOR_BATCH_MESSAGE = 7027;//用于歌华批量发送消息时客户信息上传
	
	public static final int BATCH_MESSAGE_CREATEJOB = 7028;//用于创建批量发送消息任务单
	
	public static final int IPPV_CREATE = 7124;// IPPV钱包创建

	public static final int IPPV_CHARGE = 7125;

	public static final int IPPV_UPDATE = 7126; // IPPV钱包修改
	
	public static final int IPPV_DELETE = 7127; // IPPV钱包删除
	
	public static final int DTV_MGT_CREATE = 7030;// 平移小区创建

	public static final int DTV_MGT_UPDATE = 7031;// 平移小区修改
	
	public static final int UPLOAD_FOR_BATCH_SUSPEND = 7032;//用于歌华批量关断时客户信息上传
	
	public static final int BATCH_SUSPEND_CREATEJOB = 7033;//用于创建批量关断任务单
	
	public static final int UPLOAD_FOR_BATCH_RESUME = 7034;//用于歌华批量开通时客户信息上传
	
	public static final int BATCH_RESUME_CREATEJOB = 7035;//用于创建批量开通任务单
	
	public static final int CUSTOMER_PROTOCOL =7036; //客户协议创建和维护
	
	public static final int UPLOAD_FOR_FoundCustomer = 7037;    //用于建档客户信息上传
	
	public static final int EXPORT_FOR_CUSTOMER =7038;    //用于整转客户导入
	
	public static final int BATCH_MODIFY_CUST =7128;     //批量修改客户信息
	
	public static final int BATCHPRESAVE = 3191; // 批量预存

	public String getEJBCommandClassName();

	public void setEJBCommandClassName(String ejbCommandClassName);

	public int getOperatorID();

	public void setOperatorID(int i);

	public String getRemoteHostAddress();

	public void setRemoteHostAddress(String remoteHostAddress);
	
	
	/*
	 * P4有的，供参考
	 * 
	 * 
	 * public static final int CANCEL_CUSTOMER_PROBLEM = 107; //取消报修单 public
	 * static final int JOBCARD_TRANSFER = 406; //工单流转 public static final int
	 * REPAIR_ADJUST_OP = 407; //维修单调账 public static final int
	 * QUERY_TYPE_JOBCARD_PROCESS_LOG = 3; public static final int
	 * QUERY_TYPE_CP_TRANSITION_INFO = 4; public static final int
	 * QUERY_TYPE_CSI_PROCESS_LOG = 5; public static final int
	 * QUERY_TYPE_CUSTOMER_PROBLEM2JOBCARD = 6; public static final int
	 * QUERY_TYPE_JOBCARD_PRINT = 8; public static final int
	 * QUERY_TYPE_JOBCARD_WITH_CUSTOMER_PROBLEM = 7; public static final int
	 * QUERY_DIAGNOSTICATE_PARAMETER = 9; //疑难报修单诊断参数
	 */
	// end of CustomerRelation and workflow

	
	/* 删除部分
	 public static final int PAYRIGHTNOW = 33; // 账户立即付费
	 public static final int CREATECUSTADDITIONALINFO = 112; // 添加客户附加信息
	 	public static final int CHANGECUSTADDITIONALINFO = 113; // 修改客户附加信息
	public static final int DELAYCUSTADDITIONALINFO = 114; 	// 客户附加信息--延期
	public static final int ORDER = 55; // product(device) order
	public static final int ACTIVE = 59; // product、sa ACTIVE
	public static final int ALERT = 61; // product、sa alert
	public static final int SEND_SPECIAL_CA = 909; // 发送特殊CA命令
	public static final int JOBCARDCOMPLETE = 11; // JobCard Complete
	public static final int FAIL = 13; // JobCard/CustomerProblem fail
	public static final int BATCHCOMPLETE = 15; // CustomerProblem batch
	public static final int BATCHUPDATEPROBLEMLEVEL = 16;
	public static final int JOBCARDUPDATE = 17; // 修改工单
	public static final int LOGOUT = 22; // operator logout
	// add by wangcx 2004-08-26
	public static final int RESEND_EMM = 41; // account payrightnow
	public static final int QUERY_TYPE_CUSTOMER_PROBLEM = 406; // 查询报修单
	public static final int RECORD_DIANOSIS_INFO = 407; // 登记诊断信息
	// CustomerRelation Complain 421~440
	public static final int ACCEPT_COMPLAIN = 421; // 投诉受理
	public static final int ASSIGN_COMPLAIN = 422; // 投诉分派
	public static final int PROCESS_COMPLAIN = 423; // 投诉处理
	public static final int QUERY_COMPLAIN = 425; // 查询投诉单
	public static final int SEND_MESSAGE = 442; // 发送消息
	public static final int SET_CALL_BACK_INFO = 443; // 设置回访信息
	public static final int SET_DIANOSIS_PARAMETER = 444; // 设置诊断参数
	public static final int PROCESS_INSTALLATION = 486; // 安装
	// Query 501~520
	public static final int QUERY_TYPE_JOBCARD = 501; // 工单查询
	public static final int FIREST_OWED_FEE = 1001; // 一次欠费
	public static final int SECOND_OWED_FEE = 1002; // 二次欠费
    public static final int SECOND_OWED_FEE_AFTER_SUSPEND = 1000; // 二次欠费后暂停
	public static final int CHANGE_PRODUCT_CURRENT_ACCOUNT = 1003; // 改变产品的付费账户
	public static final int CUSTOMER_GROUPBARGAIN_END_RECAMPAIGN = 1004; // 对团购到期用户授予促销
	public static final int CHECK_PRODUCTPG_CAMPAINPG_RETURN_DEVICECLASS = 2005; // 检查开户信息，并返回设备类型
	public static final int CHECK_CUSTOMERINFO_DIRECTLY = 2008; //客户资料单独录入时的检查开户信息（因为没有产品选择）
	public static final int GROUPBARGAIN_DETAIL_UPDATE = 815; // 团购 删除
	public static final int GROUPBARGAIN_DETAIL_DELETE = 816; // 团购 更新
	public static final int CONFIG_SERVICEINFO_QUERY = 1556; // 业务定义查询
    public static final int CONFIG_DISTRICT_DELETE = 1583; // delete the value
	public static final int CONFIG_PACKAGE_LINE = 4015; // 创建packageLine
	public static final int DELETE_OPGROUP = 4049; // 删除group member
	public static final int BUNDLE_PAYMENTMETHOD_CREATE = 6021; // 套餐付费方式CREATE
	public static final int BUNDLE_PAYMENTMETHOD_MODIFY = 6022; // 套餐付费方式MODIFY
	*/
	
}
