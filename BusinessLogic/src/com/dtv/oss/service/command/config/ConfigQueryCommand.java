package com.dtv.oss.service.command.config;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigQueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.config.BidimConfigListHandler;
import com.dtv.oss.service.listhandler.config.BidimConfigValueListHandler;
import com.dtv.oss.service.listhandler.config.BidimConfigWithValuesListHandler;
import com.dtv.oss.service.listhandler.config.BillBoardListHandler;
import com.dtv.oss.service.listhandler.config.BillingRuleListHandler;
import com.dtv.oss.service.listhandler.config.BrconditionListHandler;
import com.dtv.oss.service.listhandler.config.CampaignListhandler;
import com.dtv.oss.service.listhandler.config.ConfigPaymentTypeQueryListHandler;
import com.dtv.oss.service.listhandler.config.DistrictSettingListHandler;

import com.dtv.oss.service.listhandler.config.CsiReasonListHandler;
import com.dtv.oss.service.listhandler.config.CsiTypeReason2DeviceListHandler;
import com.dtv.oss.service.listhandler.config.DtvMigrationListhandler;
import com.dtv.oss.service.listhandler.config.IppvListhandler;
import com.dtv.oss.service.listhandler.config.ManualTransferSettingListHandler;
import com.dtv.oss.service.listhandler.config.MarketSegmentListHandler;
import com.dtv.oss.service.listhandler.config.MarketSegmentToDistrictListHandler;
import com.dtv.oss.service.listhandler.config.MethodOfPaymentListHandler;
import com.dtv.oss.service.listhandler.config.OpGroupListHandler;
import com.dtv.oss.service.listhandler.config.OperatorListHandler;
import com.dtv.oss.service.listhandler.config.OrganizationListHandler;
import com.dtv.oss.service.listhandler.config.PointsActivityListHandler;
import com.dtv.oss.service.listhandler.config.PointsCondListHandler;
import com.dtv.oss.service.listhandler.config.PointsCumulateRuleListHandler;
import com.dtv.oss.service.listhandler.config.PointsGoodsListHandler;
import com.dtv.oss.service.listhandler.config.PointsRuleListHandler;
import com.dtv.oss.service.listhandler.config.ProductBaseInfoListHandler;
import com.dtv.oss.service.listhandler.config.ProductDependencyListHandler;
import com.dtv.oss.service.listhandler.config.ProductPropertyListHandler;
import com.dtv.oss.service.listhandler.config.RoleOrgListHandler;
import com.dtv.oss.service.listhandler.config.ServiceInfoListHandler;
import com.dtv.oss.service.listhandler.config.ServiceRelationListHandler;
import com.dtv.oss.service.listhandler.config.SwapDeviceReason2StatusListHandler;
import com.dtv.oss.service.listhandler.config.SystemSettingListHandler;
import com.dtv.oss.service.listhandler.csr.ProductPackageListhandler;

public class ConfigQueryCommand extends QueryCommand {

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		switch (event.getType()) {
		// 业务定义查询
		case ConfigQueryEJBEvent.CONFIG_SERVICEINFO_QUERY:
			handler = new ServiceInfoListHandler();
			break;
		// 业务的自依赖关系查询
		case ConfigQueryEJBEvent.CONFIG_SERVICERELATION_QUERY:
			handler = new ServiceRelationListHandler();
			break;

		// 产品查询 add by jason.zhou
		case ConfigQueryEJBEvent.CONFIG_PRODUCT_BASE_INFO_QUERY:
			handler = new ProductBaseInfoListHandler();
			break;
		// 产品包查询 add by jason.zhou
		case ConfigQueryEJBEvent.CONFIG_PRODUCT_PACKAGE_BASE_INFO_QUERY:
			handler = new ProductPackageListhandler();
			break;
		// 产品关系查找
		case ConfigQueryEJBEvent.CONFIG_PRODUCT_DEPENDENCY_QUERY:
			handler = new ProductDependencyListHandler();
			break;

		// 业务资源定义查询
		case ConfigQueryEJBEvent.CONFIG_SERVICERESOURCE_QUERY:
			// handler = new ServiceResourceListHandler();
			break;

		case ConfigQueryEJBEvent.QUERY_POINTS_ACTIVITY:
			handler = new PointsActivityListHandler();
			break;
		// 积分兑换货物查询
		case ConfigQueryEJBEvent.QUERY_POINTS_GOODS:
			handler = new PointsGoodsListHandler();
			break;
		case ConfigQueryEJBEvent.QUERY_POINTS_COND:
			handler = new PointsCondListHandler();
			break;
		case ConfigQueryEJBEvent.QUERY_POINTS_RULE:
			handler = new PointsRuleListHandler();
			break;
		case ConfigQueryEJBEvent.QUERY_BIDIM_CONFIG:
			handler = new BidimConfigListHandler();
			break;
		case ConfigQueryEJBEvent.QUERY_BIDIM_CONFIG_WITH_VALUES:
			handler = new BidimConfigWithValuesListHandler();
			break;
		case ConfigQueryEJBEvent.QUERY_BIDIM_CONFIG_VALUE:
			handler = new BidimConfigValueListHandler();
			break;
		case ConfigQueryEJBEvent.QUERY_DISTRICT_SETTING:
			handler = new DistrictSettingListHandler();
			break;
		case ConfigQueryEJBEvent.QUERY_CUMU_RULE:
			handler = new PointsCumulateRuleListHandler();
			break;
		case ConfigQueryEJBEvent.QUERY_SEGMENT:
			handler = new MarketSegmentListHandler();
			break;
		case ConfigQueryEJBEvent.QUERY_SEGMENT_DISTRICT:
			handler = new MarketSegmentToDistrictListHandler();
			break;
		case ConfigQueryEJBEvent.CONFIG_PAYMENT_TYPE_QUERY_LIST:
			handler = new ConfigPaymentTypeQueryListHandler();
			break;
		case ConfigQueryEJBEvent.CONFIG_BRCONDITION_QUERY:
			handler = new BrconditionListHandler();
			break;
		case ConfigQueryEJBEvent.CONFIG_METHOD_OF_PAYMENT_QUERY:
			handler = new MethodOfPaymentListHandler();
			break;
		case ConfigQueryEJBEvent.QUERY_ORGANIZATION:
			handler = new OrganizationListHandler();
			break;
		case ConfigQueryEJBEvent.CONFIG_BILLING_RULE_QUERY:
			handler = new BillingRuleListHandler();
			break;
		case ConfigQueryEJBEvent.CONFIG_OPGROUP_QUERY:
			handler = new OpGroupListHandler();
			break;
		case ConfigQueryEJBEvent.OPERATOR_SUB_GROUP_QUERY:
			handler = new OperatorListHandler();
			break;
		// 产品属性查询
		case ConfigQueryEJBEvent.CONFIG_PRODUCT_PROPERTY_QUERY:
			handler = new ProductPropertyListHandler();
			break;
		case ConfigQueryEJBEvent.CAMPAIGN_QUERY:
			handler = new CampaignListhandler();
			break;
		case ConfigQueryEJBEvent.ORLE_ORGANIZATION_QUERY:
			handler = new RoleOrgListHandler();
			break;
		case ConfigQueryEJBEvent.Bill_BOARD_QUERY:
			handler = new BillBoardListHandler();
			break;
		case ConfigQueryEJBEvent.SYSTEM_SETTING_QUERY:
			handler = new SystemSettingListHandler();
			break;
		case ConfigQueryEJBEvent.MANUAL_TRANSFER_QUERY:
			handler = new ManualTransferSettingListHandler();
			break;
		case ConfigQueryEJBEvent.CSI_REASON_QUERY:
			handler = new CsiReasonListHandler();
			break;
		case ConfigQueryEJBEvent.CSI_TYPE_REASON2DEVICE:
			handler = new CsiTypeReason2DeviceListHandler();
			break;
		case ConfigQueryEJBEvent.SWAP_DEVICE_REASON:
			handler = new SwapDeviceReason2StatusListHandler();
			break;
		case ConfigQueryEJBEvent.IPPV_QUERY:
			handler = new IppvListhandler();
			break;

		case ConfigQueryEJBEvent.DTV_MIGRATION_QUERY:
			handler = new DtvMigrationListhandler();
			break;
		default:
			break;
		}
		return handler;
	}
}