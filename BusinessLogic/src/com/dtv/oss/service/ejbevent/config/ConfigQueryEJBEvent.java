package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class ConfigQueryEJBEvent extends QueryEJBEvent {

	public static final int CONFIG_SERVICEINFO_QUERY = 1555; // 业务定义查询

 
	
	
 
	public static final int CONFIG_SERVICERELATION_QUERY = 1556; // 业务的自依赖关系查询

	public static final int CONFIG_SERVICERESOURCE_QUERY = 1557; // 业务资源查询

	public static final int CONFIG_SERVICERESOURCEDETAIL_QUERY = 1558; // 业务资源明细查询

	public static final int CONFIG_PRODUCT_BASE_INFO_QUERY = 1600; // 产品基本信息查找

	public static final int CONFIG_PRODUCT_PACKAGE_BASE_INFO_QUERY = 1602; // 产品基本信息查找

	public static final int CONFIG_PRODUCT_DEPENDENCY_QUERY = 1605; // 产品依赖关系查找

	public static final int CONFIG_PRODUCT_PROPERTY_QUERY = 1610; // 产品属性查找

	public static final int QUERY_POINTS_ACTIVITY = 305; // 积分活动

	public static final int QUERY_POINTS_GOODS = 306;// 积分物品

	public static final int QUERY_POINTS_RULE = 307;// 积分规则
	
	public static final int QUERY_POINTS_COND = 4000;// 积分条件

	public static final int QUERY_BIDIM_CONFIG = 308;// 2维配置查询

	public static final int QUERY_BIDIM_CONFIG_WITH_VALUES = 3081;// 2维配置查询

	public static final int QUERY_BIDIM_CONFIG_VALUE = 309;// 2维配置value查询

	public static final int QUERY_DISTRICT_SETTING = 101;

	public static final int QUERY_ORGANIZATION = 102;

	public static final int QUERY_CUMU_RULE = 310;// 查询累加规则

	public static final int QUERY_SEGMENT = 311;// 查询累加规则

	public static final int QUERY_SEGMENT_DISTRICT = 312;// 查询市场分区所对应的行政区域

	public static final int CONFIG_PAYMENT_TYPE_QUERY_LIST = 313;// 查询抵扣券类型

	public static final int CONFIG_BRCONDITION_QUERY = 314;// 查询付费条件

	public static final int CONFIG_METHOD_OF_PAYMENT_QUERY = 315;// 查询付费方式
	
	public static final int CONFIG_BILLING_RULE_QUERY = 316;//查询支付规则
	
	public static final int CONFIG_OPGROUP_QUERY = 317;//查询操作员组
	
	public static final int OPERATOR_SUB_GROUP_QUERY = 318;//查询该组织下的操作员
	
	public static final int MANUAL_TRANSFER_QUERY = 319;//手工流转查询
	
	public static final int CAMPAIGN_QUERY = 325;//查询促销活动
	
	 
	
	public static final int ORLE_ORGANIZATION_QUERY = 336;//查询组织角色
	
	public static final int Bill_BOARD_QUERY = 337;//查询历史公告信息
	
	public static final int SYSTEM_SETTING_QUERY = 338;//系统全局配置查询
	
	public static final int CSI_REASON_QUERY = 340;//受理原因查询
	
    public static final int CSI_TYPE_REASON2DEVICE = 341;//设备用途 
	
	public static final int SWAP_DEVICE_REASON = 342;//设备更换
	
	public static final int IPPV_QUERY = 326;// 查询IPPV钱包
	
	public static final int DTV_MIGRATION_QUERY = 327;// 查询平移小区
	
	 
	 
 
	public ConfigQueryEJBEvent() {
		super();
	}

	public ConfigQueryEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
	}
}
