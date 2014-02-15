/*
 * Created on 2004-10-26
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.util;

import com.dtv.oss.domain.*;
import com.dtv.oss.service.factory.*;

public class HomeLocater {
	public static AccountAdjustmentHome getAccountAdjustmentHome()
			throws HomeFactoryException {
		return (AccountAdjustmentHome) HomeFactory.getFactory().lookUpHome(
				AccountAdjustmentHome.class, "domain/AccountAdjustmentLocal");
	}

	public static DtvMigrationAreaHome getDtvMigrationAreaHome()
			throws HomeFactoryException {
		return (DtvMigrationAreaHome) HomeFactory.getFactory().lookUpHome(
				DtvMigrationAreaHome.class, "domain/DtvMigrationAreaLocal");
	}

	public static AcctItemTypeHome getAcctItemTypeHome()
			throws HomeFactoryException {
		return (AcctItemTypeHome) HomeFactory.getFactory().lookUpHome(
				AcctItemTypeHome.class, "domain/AcctItemTypeLocal");
	}

	public static AccountItemHome getAccountItemHome()
			throws HomeFactoryException {
		return (AccountItemHome) HomeFactory.getFactory().lookUpHome(
				AccountItemHome.class, "domain/AccountItemLocal");
	}

	public static AddressHome getAddressHome() throws HomeFactoryException {
		return (AddressHome) HomeFactory.getFactory().lookUpHome(
				AddressHome.class, "domain/AddressLocal");
	}

	public static CACommandHome getCACommandHome() throws HomeFactoryException {
		return (CACommandHome) HomeFactory.getFactory().lookUpHome(
				CACommandHome.class, "domain/CACommandLocal");
	}

	public static CAHostHome getCAHostHome() throws HomeFactoryException {
		return (CAHostHome) HomeFactory.getFactory().lookUpHome(
				CAHostHome.class, "domain/CAHostLocal");
	}

	public static CAEventCmdMapHome getCAEventCmdMapHome()
			throws HomeFactoryException {
		return (CAEventCmdMapHome) HomeFactory.getFactory().lookUpHome(
				CAEventCmdMapHome.class, "domain/CAEventCmdMapLocal");
	}

	public static CAConditionHome getCAConditionHome()
			throws HomeFactoryException {
		return (CAConditionHome) HomeFactory.getFactory().lookUpHome(
				CAConditionHome.class, "domain/CAConditionLocal");
	}

	public static CACuFormulaHome getCACuFormulaHome()
			throws HomeFactoryException {
		return (CACuFormulaHome) HomeFactory.getFactory().lookUpHome(
				CACuFormulaHome.class, "domain/CACuFormulaLocal");
	}

	public static CAProductHome getCAProductHome() throws HomeFactoryException {
		return (CAProductHome) HomeFactory.getFactory().lookUpHome(
				CAProductHome.class, "domain/CAProductLocal");
	}

	public static CAProductDefHome getCAProductDefHome()
			throws HomeFactoryException {
		return (CAProductDefHome) HomeFactory.getFactory().lookUpHome(
				CAProductDefHome.class, "domain/CAProductDefLocal");
	}

	public static CAQueueHome getCAQueueHome() throws HomeFactoryException {
		return (CAQueueHome) HomeFactory.getFactory().lookUpHome(
				CAQueueHome.class, "domain/CAQueueLocal");
	}

	public static CARecvHome getCARecvHome() throws HomeFactoryException {
		return (CARecvHome) HomeFactory.getFactory().lookUpHome(
				CARecvHome.class, "domain/CARecvLocal");
	}

	public static CASentHome getCASentHome() throws HomeFactoryException {
		return (CASentHome) HomeFactory.getFactory().lookUpHome(
				CASentHome.class, "domain/CASentLocal");
	}

	public static CatvTerminalHome getCatvTerminalHome()
			throws HomeFactoryException {
		return (CatvTerminalHome) HomeFactory.getFactory().lookUpHome(
				CatvTerminalHome.class, "domain/CatvTerminalLocal");
	}

	public static CommonSettingHome getCommonSettingHome()
			throws HomeFactoryException {
		return (CommonSettingHome) HomeFactory.getFactory().lookUpHome(
				CommonSettingHome.class, "domain/CommonSettingLocal");
	}

	public static CsiFeeSettingHome getCsiFeeSettingHome()
			throws HomeFactoryException {
		return (CsiFeeSettingHome) HomeFactory.getFactory().lookUpHome(
				CsiFeeSettingHome.class, "domain/CsiFeeSettingLocal");
	}

	public static CustComplainProcessHome getCustComplainProcessHome()
			throws HomeFactoryException {
		return (CustComplainProcessHome) HomeFactory.getFactory().lookUpHome(
				CustComplainProcessHome.class,
				"domain/CustComplainProcessLocal");
	}

	public static CustAcctCycleCfgHome getCustAcctCycleCfgHome()
			throws HomeFactoryException {
		return (CustAcctCycleCfgHome) HomeFactory.getFactory().lookUpHome(
				CustAcctCycleCfgHome.class, "domain/CustAcctCycleCfgLocal");
	}

	public static BillingCycleTypeHome getBillingCycleTypeHome()
			throws HomeFactoryException {
		return (BillingCycleTypeHome) HomeFactory.getFactory().lookUpHome(
				BillingCycleTypeHome.class, "domain/BillingCycleTypeLocal");
	}

	public static CPCampaignMapHome getCPCampaignMapHome()
			throws HomeFactoryException {
		return (CPCampaignMapHome) HomeFactory.getFactory().lookUpHome(
				CPCampaignMapHome.class, "domain/CPCampaignMapLocal");
	}

	public static BillingBatchLogHome getBillingBatchLogHome()
			throws HomeFactoryException {
		return (BillingBatchLogHome) HomeFactory.getFactory().lookUpHome(
				BillingBatchLogHome.class, "domain/BillingBatchLogLocal");
	}

	public static OperatorHome getOperatorHome() throws HomeFactoryException {
		return (OperatorHome) HomeFactory.getFactory().lookUpHome(
				OperatorHome.class, "domain/OperatorLocal");
	}

	public static CustomerHome getCustomerHome() throws HomeFactoryException {
		return (CustomerHome) HomeFactory.getFactory().lookUpHome(
				CustomerHome.class, "domain/CustomerLocal");
	}

	public static CustMarketInfoHome getCustMarketInfoHome()
			throws HomeFactoryException {
		return (CustMarketInfoHome) HomeFactory.getFactory().lookUpHome(
				CustMarketInfoHome.class, "domain/CustMarketInfoLocal");
	}

	public static AccountHome getAccountHome() throws HomeFactoryException {
		return (AccountHome) HomeFactory.getFactory().lookUpHome(
				AccountHome.class, "domain/AccountLocal");
	}

	public static DepotHome getDepotHome() throws HomeFactoryException {
		return (DepotHome) HomeFactory.getFactory().lookUpHome(DepotHome.class,
				"domain/DepotLocal");
	}

	public static ServiceAccountHome getServiceAccountHome()
			throws HomeFactoryException {
		return (ServiceAccountHome) HomeFactory.getFactory().lookUpHome(
				ServiceAccountHome.class, "domain/ServiceAccountLocal");
	}

	public static CustomerProductHome getCustomerProductHome()
			throws HomeFactoryException {
		return (CustomerProductHome) HomeFactory.getFactory().lookUpHome(
				CustomerProductHome.class, "domain/CustomerProductLocal");
	}

	public static CommonSettingDataHome getCommonSettingDataHome()
			throws HomeFactoryException {
		return (CommonSettingDataHome) HomeFactory.getFactory().lookUpHome(
				CommonSettingDataHome.class, "domain/CommonSettingDataLocal");
	}

	public static BankDeductionDetailHome getBankDeductionDetailHome()
			throws HomeFactoryException {
		return (BankDeductionDetailHome) HomeFactory.getFactory().lookUpHome(
				BankDeductionDetailHome.class,
				"domain/BankDeductionDetailLocal");
	}

	public static CustomerProblemHome getCustomerProblemHome()
			throws HomeFactoryException {
		return (CustomerProblemHome) HomeFactory.getFactory().lookUpHome(
				CustomerProblemHome.class, "domain/CustomerProblemLocal");
	}

	public static AcctItemCacuHome getAcctItemCacuHome()
			throws HomeFactoryException {
		return (AcctItemCacuHome) HomeFactory.getFactory().lookUpHome(
				AcctItemCacuHome.class, "domain/AcctItemCacuLocal");
	}

	public static AitSetOffRuleHome getAcctItemSetOffRuleHome()
			throws HomeFactoryException {
		return (AitSetOffRuleHome) HomeFactory.getFactory().lookUpHome(
				AitSetOffRuleHome.class, "domain/AitSetOffRuleLocal");
	}

	public static ProductHome getProductHome() throws HomeFactoryException {
		return (ProductHome) HomeFactory.getFactory().lookUpHome(
				ProductHome.class, "domain/ProductLocal");
	}

	public static ProductPackageHome getProductPackageHome()
			throws HomeFactoryException {
		return (ProductPackageHome) HomeFactory.getFactory().lookUpHome(
				ProductPackageHome.class, "domain/ProductPackageLocal");
	}

	public static ValidDateChangeHistHome getValidDateChangeHistHome()
			throws HomeFactoryException {
		return (ValidDateChangeHistHome) HomeFactory.getFactory().lookUpHome(
				ValidDateChangeHistHome.class,
				"domain/ValidDateChangeHistLocal");
	}

	public static CustAdditionInfoHome getCustAdditionInfoHome()
			throws HomeFactoryException {
		return (CustAdditionInfoHome) HomeFactory.getFactory().lookUpHome(
				CustAdditionInfoHome.class, "domain/CustAdditionInfoLocal");
	}

	public static SystemLogHome getSystemLogHome() throws HomeFactoryException {
		return (SystemLogHome) HomeFactory.getFactory().lookUpHome(
				SystemLogHome.class, "domain/SystemLogLocal");
	}

	public static OrganizationHome getOrganizationHome()
			throws HomeFactoryException {
		return (OrganizationHome) HomeFactory.getFactory().lookUpHome(
				OrganizationHome.class, "domain/OrganizationLocal");
	}

	public static SystemEventHome getSystemEventHome()
			throws HomeFactoryException {
		return (SystemEventHome) HomeFactory.getFactory().lookUpHome(
				SystemEventHome.class, "domain/SystemEventLocal");
	}

	public static GroupBargainHome getGroupBargainHome()
			throws HomeFactoryException {
		return (GroupBargainHome) HomeFactory.getFactory().lookUpHome(
				GroupBargainHome.class, "domain/GroupBargainLocal");
	}

	public static GroupBargainDetailHome getGroupBargainDetailHome()
			throws HomeFactoryException {
		return (GroupBargainDetailHome) HomeFactory.getFactory().lookUpHome(
				GroupBargainDetailHome.class, "domain/GroupBargainDetailLocal");
	}

	public static PackageLineHome getPackageLineHome()
			throws HomeFactoryException {
		return (PackageLineHome) HomeFactory.getFactory().lookUpHome(
				PackageLineHome.class, "domain/PackageLineLocal");
	}

	public static BackgroundJobHome getBackgroundJobHome()
			throws HomeFactoryException {
		return (BackgroundJobHome) HomeFactory.getFactory().lookUpHome(
				BackgroundJobHome.class, "domain/BackgroundJobLocal");
	}

	public static CustomerBillingRuleHome getCustomerBillingRuleHome()
			throws HomeFactoryException {
		return (CustomerBillingRuleHome) HomeFactory.getFactory().lookUpHome(
				CustomerBillingRuleHome.class,
				"domain/CustomerBillingRuleLocal");
	}

	public static BillingRuleHome getBillingRuleHome()
			throws HomeFactoryException {
		return (BillingRuleHome) HomeFactory.getFactory().lookUpHome(
				BillingRuleHome.class, "domain/BillingRuleLocal");
	}

	public static BillingRuleItemHome getBillingRuleItemHome()
			throws HomeFactoryException {
		return (BillingRuleItemHome) HomeFactory.getFactory().lookUpHome(
				BillingRuleItemHome.class, "domain/BillingRuleItemLocal");
	}

	public static SystemPrivilegeHome getSystemPrivilegeHome()
			throws HomeFactoryException {
		return (SystemPrivilegeHome) HomeFactory.getFactory().lookUpHome(
				SystemPrivilegeHome.class, "domain/SystemPrivilegeLocal");
	}

	public static BillingCycleHome getBillingCycleHome()
			throws HomeFactoryException {
		return (BillingCycleHome) HomeFactory.getFactory().lookUpHome(
				BillingCycleHome.class, "domain/BillingCycleLocal");
	}

	public static BossConfigurationHome getBossConfigurationHome()
			throws HomeFactoryException {
		return (BossConfigurationHome) HomeFactory.getFactory().lookUpHome(
				BossConfigurationHome.class, "domain/BossConfigurationLocal");
	}

	public static PalletHome getPalletHome() throws HomeFactoryException {
		return (PalletHome) HomeFactory.getFactory().lookUpHome(
				PalletHome.class, "domain/PalletLocal");
	}

	public static ServiceDependencyHome getServiceDependencyHome()
			throws HomeFactoryException {
		return (ServiceDependencyHome) HomeFactory.getFactory().lookUpHome(
				ServiceDependencyHome.class, "domain/ServiceDependencyLocal");
	}

	public static ServiceHome getServiceHome() throws HomeFactoryException {
		return (ServiceHome) HomeFactory.getFactory().lookUpHome(
				ServiceHome.class, "domain/ServiceLocal");
	}

	public static ProductDependencyHome getProductDependencyHome()
			throws HomeFactoryException {
		return (ProductDependencyHome) HomeFactory.getFactory().lookUpHome(
				ProductDependencyHome.class, "domain/ProductDependencyLocal");
	}

	public static CampaignHome getCampaignHome() throws HomeFactoryException {
		return (CampaignHome) HomeFactory.getFactory().lookUpHome(
				CampaignHome.class, "domain/CampaignLocal");
	}

	public static CustServiceInteractionHome getCustServiceInteractionHome()
			throws HomeFactoryException {
		return (CustServiceInteractionHome) HomeFactory.getFactory()
				.lookUpHome(CustServiceInteractionHome.class,
						"domain/CustServiceInteractionLocal");
	}

	public static CsiProcessLogHome getCsiProcessLogHome()
			throws HomeFactoryException {
		return (CsiProcessLogHome) HomeFactory.getFactory().lookUpHome(
				CsiProcessLogHome.class, "domain/CsiProcessLogLocal");
	}

	public static NewCustomerInfoHome getNewCustomerInfoHome()
			throws HomeFactoryException {
		return (NewCustomerInfoHome) HomeFactory.getFactory().lookUpHome(
				NewCustomerInfoHome.class, "domain/NewCustomerInfoLocal");
	}

	public static OldCustomerInfoHome getOldCustomerInfoHome()
			throws HomeFactoryException {
		return (OldCustomerInfoHome) HomeFactory.getFactory().lookUpHome(
				OldCustomerInfoHome.class, "domain/OldCustomerInfoLocal");
	}

	public static OldCustomerMarketInfoHome getOldCustomerMarketInfoHome()
			throws HomeFactoryException {
		return (OldCustomerMarketInfoHome) HomeFactory.getFactory().lookUpHome(
				OldCustomerMarketInfoHome.class,
				"domain/OldCustomerMarketInfoLocal");
	}

	public static NewCustomerMarketInfoHome getNewCustomerMarketInfoHome()
			throws HomeFactoryException {
		return (NewCustomerMarketInfoHome) HomeFactory.getFactory().lookUpHome(
				NewCustomerMarketInfoHome.class,
				"domain/NewCustomerMarketInfoLocal");
	}

	public static OpGroupHome getOpGroupHome() throws HomeFactoryException {
		return (OpGroupHome) HomeFactory.getFactory().lookUpHome(
				OpGroupHome.class, "domain/OpGroupLocal");
	}

	public static NewCustAccountInfoHome getNewCustAccountInfoHome()
			throws HomeFactoryException {
		return (NewCustAccountInfoHome) HomeFactory.getFactory().lookUpHome(
				NewCustAccountInfoHome.class, "domain/NewCustAccountInfoLocal");
	}

	public static OldCustAccountInfoHome getOldCustAccountInfoHome()
			throws HomeFactoryException {
		return (OldCustAccountInfoHome) HomeFactory.getFactory().lookUpHome(
				OldCustAccountInfoHome.class, "domain/OldCustAccountInfoLocal");
	}

	public static CsiCustProductInfoHome getCsiCustProductInfoHome()
			throws HomeFactoryException {
		return (CsiCustProductInfoHome) HomeFactory.getFactory().lookUpHome(
				CsiCustProductInfoHome.class, "domain/CsiCustProductInfoLocal");
	}

	public static JobCardHome getJobCardHome() throws HomeFactoryException {
		return (JobCardHome) HomeFactory.getFactory().lookUpHome(
				JobCardHome.class, "domain/JobCardLocal");
	}

	public static TerminalDeviceHome getTerminalDeviceHome()
			throws HomeFactoryException {
		return (TerminalDeviceHome) HomeFactory.getFactory().lookUpHome(
				TerminalDeviceHome.class, "domain/TerminalDeviceLocal");
	}

	public static DeviceModelHome getDeviceModelHome()
			throws HomeFactoryException {
		return (DeviceModelHome) HomeFactory.getFactory().lookUpHome(
				DeviceModelHome.class, "domain/DeviceModelLocal");
	}

	public static DeviceTransitionHome getDeviceTransitionHome()
			throws HomeFactoryException {
		return (DeviceTransitionHome) HomeFactory.getFactory().lookUpHome(
				DeviceTransitionHome.class, "domain/DeviceTransitionLocal");
	}

	public static BankMatchDetailHome getBankMatchDetailHome()
			throws HomeFactoryException {
		return (BankMatchDetailHome) HomeFactory.getFactory().lookUpHome(
				BankMatchDetailHome.class, "domain/BankMatchDetailLocal");
	}

	public static SendMessageHome getSendMessageHome()
			throws HomeFactoryException {
		return (SendMessageHome) HomeFactory.getFactory().lookUpHome(
				SendMessageHome.class, "domain/SendMessageHomeLocal");
	}

	public static DeviceTransitionDetailHome getDeviceTransitionDetailHome()
			throws HomeFactoryException {
		return (DeviceTransitionDetailHome) HomeFactory.getFactory()
				.lookUpHome(DeviceTransitionDetailHome.class,
						"domain/DeviceTransitionDetailLocal");
	}

	public static BankDeductionHeaderHome getBankDeductionHeaderHome()
			throws HomeFactoryException {
		return (BankDeductionHeaderHome) HomeFactory.getFactory().lookUpHome(
				BankDeductionHeaderHome.class,
				"domain/BankDeductionHeaderLocal");
	}

	public static MethodOfPaymentHome getMethodOfPaymentHome()
			throws HomeFactoryException {
		return (MethodOfPaymentHome) HomeFactory.getFactory().lookUpHome(
				MethodOfPaymentHome.class, "domain/MethodOfPaymentLocal");
	}

	public static CustomerCampaignHome getCustomerCampaignHome()
			throws HomeFactoryException {
		return (CustomerCampaignHome) HomeFactory.getFactory().lookUpHome(
				CustomerCampaignHome.class, "domain/CustomerCampaignLocal");
	}

	public static CustomerComplainHome getCustomerComplainHome()
			throws HomeFactoryException {
		return (CustomerComplainHome) HomeFactory.getFactory().lookUpHome(
				CustomerComplainHome.class, "domain/CustomerComplainLocal");
	}

	public static CustProblemProcessHome getCustProblemProcessHome()
			throws HomeFactoryException {
		return (CustProblemProcessHome) HomeFactory.getFactory().lookUpHome(
				CustProblemProcessHome.class, "domain/CustProblemProcessLocal");
	}

	public static FinancialSettingHome getFinancialSettingHome()
			throws HomeFactoryException {
		return (FinancialSettingHome) HomeFactory.getFactory().lookUpHome(
				FinancialSettingHome.class, "domain/FinancialSettingLocal");
	}

	public static ForcedDepositHome getForcedDepositHome()
			throws HomeFactoryException {
		return (ForcedDepositHome) HomeFactory.getFactory().lookUpHome(
				ForcedDepositHome.class, "domain/ForcedDepositLocal");
	}

	public static InvoiceHome getInvoiceHome() throws HomeFactoryException {
		return (InvoiceHome) HomeFactory.getFactory().lookUpHome(
				InvoiceHome.class, "domain/InvoiceLocal");
	}

	public static CallBackInfoHome getCallBackInfoHome()
			throws HomeFactoryException {
		return (CallBackInfoHome) HomeFactory.getFactory().lookUpHome(
				CallBackInfoHome.class, "domain/CallBackInfoLocal");
	}

	public static CampaignToMarketSegmentHome getCampaignToMarketSegmentHome()
			throws HomeFactoryException {
		return (CampaignToMarketSegmentHome) HomeFactory.getFactory()
				.lookUpHome(CampaignToMarketSegmentHome.class,
						"domain/CampaignToMarketSegmentLocal");
	}

	public static CandssubIdHome getCandssubIdHome()
			throws HomeFactoryException {
		return (CandssubIdHome) HomeFactory.getFactory().lookUpHome(
				CandssubIdHome.class, "domain/CandssubIdLocal");
	}

	public static CAParameterHome getCAParameterHome()
			throws HomeFactoryException {
		return (CAParameterHome) HomeFactory.getFactory().lookUpHome(
				CAParameterHome.class, "domain/CAParameterLocal");
	}

	public static CAProcessEventHome getCAProcessEventHome()
			throws HomeFactoryException {
		return (CAProcessEventHome) HomeFactory.getFactory().lookUpHome(
				CAProcessEventHome.class, "domain/CAProcessEventLocal");
	}

	public static CustProductProcessParaHome getCustProductProcessParaHome()
			throws HomeFactoryException {
		return (CustProductProcessParaHome) HomeFactory.getFactory()
				.lookUpHome(CustProductProcessParaHome.class,
						"domain/CustProductProcessParaLocal");
	}

	public static DeviceClassHome getDeviceClassHome()
			throws HomeFactoryException {
		return (DeviceClassHome) HomeFactory.getFactory().lookUpHome(
				DeviceClassHome.class, "domain/DeviceClassLocal");
	}

	public static DeviceMatchToProductHome getDeviceMatchToProductHome()
			throws HomeFactoryException {
		return (DeviceMatchToProductHome) HomeFactory.getFactory().lookUpHome(
				DeviceMatchToProductHome.class,
				"domain/DeviceMatchToProductLocal");
	}

	public static DeviceModelPropertyHome getDeviceModelPropertyHome()
			throws HomeFactoryException {
		return (DeviceModelPropertyHome) HomeFactory.getFactory().lookUpHome(
				DeviceModelPropertyHome.class,
				"domain/DeviceModelPropertyLocal");
	}

	public static DevicePropertyHome getDevicePropertyHome()
			throws HomeFactoryException {
		return (DevicePropertyHome) HomeFactory.getFactory().lookUpHome(
				DevicePropertyHome.class, "domain/DevicePropertyLocal");
	}

	public static DiagnosisInfoHome getDiagnosisInfoHome()
			throws HomeFactoryException {
		return (DiagnosisInfoHome) HomeFactory.getFactory().lookUpHome(
				DiagnosisInfoHome.class, "domain/DiagnosisInfoLocal");
	}

	public static MaterialUsageHome getMaterialUsageHome()
			throws HomeFactoryException {
		return (MaterialUsageHome) HomeFactory.getFactory().lookUpHome(
				MaterialUsageHome.class, "domain/MaterialUsageLocal");
	}

	public static DistrictSettingHome getDistrictSettingHome()
			throws HomeFactoryException {
		return (DistrictSettingHome) HomeFactory.getFactory().lookUpHome(
				DistrictSettingHome.class, "domain/DistrictSettingLocal");
	}

	public static InvoiceMsgHome getInvoiceMsgHome()
			throws HomeFactoryException {
		return (InvoiceMsgHome) HomeFactory.getFactory().lookUpHome(
				InvoiceMsgHome.class, "domain/InvoiceMsgLocal");
	}

	public static InvoicePrintFormatHome getInvoicePrintFormatHome()
			throws HomeFactoryException {
		return (InvoicePrintFormatHome) HomeFactory.getFactory().lookUpHome(
				InvoicePrintFormatHome.class, "domain/InvoicePrintFormatLocal");
	}

	public static InvoicePrintLogHome getInvoicePrintLogHome()
			throws HomeFactoryException {
		return (InvoicePrintLogHome) HomeFactory.getFactory().lookUpHome(
				InvoicePrintLogHome.class, "domain/InvoicePrintLogeLocal");
	}

	public static InvoicePrintRecordHome getInvoicePrintRecordHome()
			throws HomeFactoryException {
		return (InvoicePrintRecordHome) HomeFactory.getFactory().lookUpHome(
				InvoicePrintRecordHome.class, "domain/InvoicePrintRecordLocal");
	}

	public static JobCardProcessHome getJobCardProcessHome()
			throws HomeFactoryException {
		return (JobCardProcessHome) HomeFactory.getFactory().lookUpHome(
				JobCardProcessHome.class, "domain/JobCardProcessLocal");
	}

	public static MarketSegmentHome getMarketSegmentHome()
			throws HomeFactoryException {
		return (MarketSegmentHome) HomeFactory.getFactory().lookUpHome(
				MarketSegmentHome.class, "domain/MarketSegmentLocal");
	}

	public static MarketSegmentToDistrictHome getMarketSegmentToDistrictHome()
			throws HomeFactoryException {
		return (MarketSegmentToDistrictHome) HomeFactory.getFactory()
				.lookUpHome(MarketSegmentToDistrictHome.class,
						"domain/MarketSegmentToDistrictLocal");
	}

	public static OpGroupToPrivilegeHome getOpGroupToPrivilegeHome()
			throws HomeFactoryException {
		return (OpGroupToPrivilegeHome) HomeFactory.getFactory().lookUpHome(
				OpGroupToPrivilegeHome.class, "domain/OpGroupToPrivilegeLocal");
	}

	public static OpToGroupHome getOpToGroupHome() throws HomeFactoryException {
		return (OpToGroupHome) HomeFactory.getFactory().lookUpHome(
				OpToGroupHome.class, "domain/OpToGroupLocal");
	}

	public static OrgGovernedDistrictHome getOrgGovernedDistrictHome()
			throws HomeFactoryException {
		return (OrgGovernedDistrictHome) HomeFactory.getFactory().lookUpHome(
				OrgGovernedDistrictHome.class,
				"domain/OrgGovernedDistrictLocal");
	}

	public static PackageToMarketSegmentHome getPackageToMarketSegmentHome()
			throws HomeFactoryException {
		return (PackageToMarketSegmentHome) HomeFactory.getFactory()
				.lookUpHome(PackageToMarketSegmentHome.class,
						"domain/PackageToMarketSegmentLocal");
	}

	public static BrconditionHome getBrconditionHome()
			throws HomeFactoryException {
		return (BrconditionHome) HomeFactory.getFactory().lookUpHome(
				BrconditionHome.class, "domain/BrconditionLocal");
	}

	public static ProductToServiceHome getProductToServiceHome()
			throws HomeFactoryException {
		return (ProductToServiceHome) HomeFactory.getFactory().lookUpHome(
				ProductToServiceHome.class, "domain/ProductToServiceLocal");
	}

	public static ServiceResourceHome getServiceResourceHome()
			throws HomeFactoryException {
		return (ServiceResourceHome) HomeFactory.getFactory().lookUpHome(
				ServiceResourceHome.class, "domain/ServiceResourceLocal");
	}

	public static SystemEventDefHome getSystemEventDefHome()
			throws HomeFactoryException {
		return (SystemEventDefHome) HomeFactory.getFactory().lookUpHome(
				SystemEventDefHome.class, "domain/SystemEventDefLocal");
	}

	public static SystemEventReasonHome getSystemEventReasonHome()
			throws HomeFactoryException {
		return (SystemEventReasonHome) HomeFactory.getFactory().lookUpHome(
				SystemEventReasonHome.class, "domain/SystemEventReasonLocal");
	}

	public static SystemModuleHome getSystemModuleHome()
			throws HomeFactoryException {
		return (SystemModuleHome) HomeFactory.getFactory().lookUpHome(
				SystemModuleHome.class, "domain/SystemModuleLocal");
	}

	public static UserPointsCumulatedRuleHome getUserPointsCumulatedRuleHome()
			throws HomeFactoryException {
		return (UserPointsCumulatedRuleHome) HomeFactory.getFactory()
				.lookUpHome(UserPointsCumulatedRuleHome.class,
						"domain/UserPointsCumulatedRuleLocal");
	}

	public static UserPointsExchangeActivityHome getUserPointsExchangeActivityHome()
			throws HomeFactoryException {
		return (UserPointsExchangeActivityHome) HomeFactory.getFactory()
				.lookUpHome(UserPointsExchangeActivityHome.class,
						"domain/UserPointsExchangeActivityLocal");
	}

	public static UserPointsExchangeCondHome getUserPointsExchangeCondHome()
			throws HomeFactoryException {
		return (UserPointsExchangeCondHome) HomeFactory.getFactory()
				.lookUpHome(UserPointsExchangeCondHome.class,
						"domain/UserPointsExchangeCondLocal");
	}

	public static UserPointsExchangeGoodsHome getUserPointsExchangeGoodsHome()
			throws HomeFactoryException {
		return (UserPointsExchangeGoodsHome) HomeFactory.getFactory()
				.lookUpHome(UserPointsExchangeGoodsHome.class,
						"domain/UserPointsExchangeGoodsLocal");
	}

	public static UserPointsExchangerCdHome getUserPointsExchangerCdHome()
			throws HomeFactoryException {
		return (UserPointsExchangerCdHome) HomeFactory.getFactory().lookUpHome(
				UserPointsExchangerCdHome.class,
				"domain/UserPointsExchangerCdLocal");
	}

	public static UserPointsExchangeRuleHome getUserPointsExchangeRuleHome()
			throws HomeFactoryException {
		return (UserPointsExchangeRuleHome) HomeFactory.getFactory()
				.lookUpHome(UserPointsExchangeRuleHome.class,
						"domain/UserPointsExchangeRuleLocal");
	}

	public static RoleOrganizationHome getRoleOrganizationHome()
			throws HomeFactoryException {
		return (RoleOrganizationHome) HomeFactory.getFactory().lookUpHome(
				RoleOrganizationHome.class, "domain/RoleOrganizationLocal");
	}

	public static Bc2ICHome getBc2ICHome() throws HomeFactoryException {
		return (Bc2ICHome) HomeFactory.getFactory().lookUpHome(Bc2ICHome.class,
				"domain/Bc2ICHomeLocal");
	}

	public static ActivityToMarketSegmentHome getActivityToMarketSegmentHome()
			throws HomeFactoryException {
		return (ActivityToMarketSegmentHome) HomeFactory.getFactory()
				.lookUpHome(ActivityToMarketSegmentHome.class,
						"domain/ActivityToMarketSegmentLocal");
	}

	public static BankMatchHeaderHome getBankMatchHeaderHome()
			throws HomeFactoryException {
		return (BankMatchHeaderHome) HomeFactory.getFactory().lookUpHome(
				BankMatchHeaderHome.class, "domain/BankMatchHeaderLocal");
	}

	public static FinanceSetOffMapHome getFinanceSetOffMapHome()
			throws HomeFactoryException {
		return (FinanceSetOffMapHome) HomeFactory.getFactory().lookUpHome(
				FinanceSetOffMapHome.class, "domain/FinanceSetOffMapLocal");
	}

	public static InvoicePrintInterfaceHome getInvoicePrintInterfaceHome()
			throws HomeFactoryException {
		return (InvoicePrintInterfaceHome) HomeFactory.getFactory().lookUpHome(
				InvoicePrintInterfaceHome.class,
				"domain/InvoicePrintInterfaceLocal");
	}

	public static PaymentInterfaceHome getPaymentInterfaceHome()
			throws HomeFactoryException {
		return (PaymentInterfaceHome) HomeFactory.getFactory().lookUpHome(
				PaymentInterfaceHome.class, "domain/PaymentInterfaceLocal");
	}

	public static ProductPropertyHome getProductPropertyHome()
			throws HomeFactoryException {
		return (ProductPropertyHome) HomeFactory.getFactory().lookUpHome(
				ProductPropertyHome.class, "domain/ProductPropertyLocal");
	}

	public static PaymentRecordHome getPaymentRecordHome()
			throws HomeFactoryException {
		return (PaymentRecordHome) HomeFactory.getFactory().lookUpHome(
				PaymentRecordHome.class, "domain/PaymentRecordLocal");
	}

	public static PrepaymentDeductionRecordHome getPrepaymentDeductionRecordHome()
			throws HomeFactoryException {
		return (PrepaymentDeductionRecordHome) HomeFactory.getFactory()
				.lookUpHome(PrepaymentDeductionRecordHome.class,
						"domain/PrepaymentDeductionRecordLocal");
	}

	public static BidimConfigSettingHome getBidimConfigSettingHome()
			throws HomeFactoryException {
		return (BidimConfigSettingHome) HomeFactory.getFactory().lookUpHome(
				BidimConfigSettingHome.class, "domain/BidimConfigSettingLocal");
	}

	public static BidimConfigSettingValueHome getBidimConfigSettingValueHome()
			throws HomeFactoryException {
		return (BidimConfigSettingValueHome) HomeFactory.getFactory()
				.lookUpHome(BidimConfigSettingValueHome.class,
						"domain/BidimConfigSettingValueLocal");
	}

	// 排程用
	public static BatchJobHome getBatchJobHome() throws HomeFactoryException {
		return (BatchJobHome) HomeFactory.getFactory().lookUpHome(
				BatchJobHome.class, "domain/BatchJobLocal");
	}

	public static BatchJobItemHome getBatchJobItemHome()
			throws HomeFactoryException {
		return (BatchJobItemHome) HomeFactory.getFactory().lookUpHome(
				BatchJobItemHome.class, "domain/BatchJobItemLocal");
	}

	public static BatchJobProcessLogHome getBatchJobProcessLogHome()
			throws HomeFactoryException {
		return (BatchJobProcessLogHome) HomeFactory.getFactory().lookUpHome(
				BatchJobProcessLogHome.class, "domain/BatchJobProcessLogLocal");
	}

	// 排程结束

	// 批量查询用
	public static QueryHome getQueryHome() throws HomeFactoryException {
		return (QueryHome) HomeFactory.getFactory().lookUpHome(QueryHome.class,
				"domain/QueryLocal");
	}

	public static QueryResultItemHome getQueryResultItemHome()
			throws HomeFactoryException {
		return (QueryResultItemHome) HomeFactory.getFactory().lookUpHome(
				QueryResultItemHome.class, "domain/QueryResultItemLocal");
	}

	// 批量查询结束

	public static LogisticsSettingHome getLogisticsSettingHome()
			throws HomeFactoryException {
		return (LogisticsSettingHome) HomeFactory.getFactory().lookUpHome(
				LogisticsSettingHome.class, "domain/LogisticsSettingLocal");
	}

	// 查询电话号码资源用
	public static ResourcePhoneNoHome getResourcePhoneNoHome()
			throws HomeFactoryException {
		return (ResourcePhoneNoHome) HomeFactory.getFactory().lookUpHome(
				ResourcePhoneNoHome.class, "domain/ResourcePhoneNoLocal");
	}

	// 查询亲情号码
	public static VoiceFriendPhoneNoHome getVoiceFriendPhoneNoHome()
			throws HomeFactoryException {
		return (VoiceFriendPhoneNoHome) HomeFactory.getFactory().lookUpHome(
				VoiceFriendPhoneNoHome.class,
				"domain/VoiceFriendPhoneNoHomeLocal");
	}

	// 客户产品属性
	public static CpConfigedPropertyHome getCpConfigedPropertyHome()
			throws HomeFactoryException {
		return (CpConfigedPropertyHome) HomeFactory.getFactory().lookUpHome(
				CpConfigedPropertyHome.class, "domain/CpConfigedPropertyLocal");
	}

	public static LdapHostHome getLdapHostHome() throws HomeFactoryException {
		return (LdapHostHome) HomeFactory.getFactory().lookUpHome(
				LdapHostHome.class, "domain/LdapHostLocal");
	}

	public static VodHostHome getVodHostHome() throws HomeFactoryException {
		return (VodHostHome) HomeFactory.getFactory().lookUpHome(
				VodHostHome.class, "domain/VodHostLocal");
	}

	public static LdapProductHome getLdapProductHome()
			throws HomeFactoryException {
		return (LdapProductHome) HomeFactory.getFactory().lookUpHome(
				LdapProductHome.class, "domain/LdapProductLocal");

	}

	public static LdapEventCmdMapHome getLdapEventCmdMapHome()
			throws HomeFactoryException {
		return (LdapEventCmdMapHome) HomeFactory.getFactory().lookUpHome(
				LdapEventCmdMapHome.class, "domain/LdapEventCmdMapLocal");
	}

	public static LdapAttrConfigHome getLdapAttrConfigHome()
			throws HomeFactoryException {
		return (LdapAttrConfigHome) HomeFactory.getFactory().lookUpHome(
				LdapAttrConfigHome.class, "domain/LdapAttrConfigLocal");
	}

	public static LdapProdToSmsProdHome getLdapProdToSmsProdHome()
			throws HomeFactoryException {
		return (LdapProdToSmsProdHome) HomeFactory.getFactory().lookUpHome(
				LdapProdToSmsProdHome.class, "domain/LdapProdToSmsProdLocal");
	}

	public static VodProductHome getVodProductHome()
			throws HomeFactoryException {
		return (VodProductHome) HomeFactory.getFactory().lookUpHome(
				VodProductHome.class, "domain/VodProductLocal");
	}

	public static VOIPProductHome getVOIPProductHome()
			throws HomeFactoryException {
		return (VOIPProductHome) HomeFactory.getFactory().lookUpHome(
				VOIPProductHome.class, "domain/VOIPProductLocal");
	}

	public static VOIPConditionHome getVOIPConditionHome()
			throws HomeFactoryException {
		return (VOIPConditionHome) HomeFactory.getFactory().lookUpHome(
				VOIPConditionHome.class, "domain/VOIPConditionLocal");
	}

	public static VOIPGatewayHome getVOIPGatewayHome()
			throws HomeFactoryException {
		return (VOIPGatewayHome) HomeFactory.getFactory().lookUpHome(
				VOIPGatewayHome.class, "domain/VOIPGatewayLocal");
	}

	public static ConstructionBatchHome getConstructionBatchHome()
			throws HomeFactoryException {
		return (ConstructionBatchHome) HomeFactory.getFactory().lookUpHome(
				ConstructionBatchHome.class, "domain/ConstructionBatchLocal");
	}

	public static ConstructionBatchItemHome getConstructionBatchItemHome()
			throws HomeFactoryException {
		return (ConstructionBatchItemHome) HomeFactory.getFactory().lookUpHome(
				ConstructionBatchItemHome.class,
				"domain/ConstructionBatchItemLocal");
	}

	public static ContractHome getContractHome() throws HomeFactoryException {
		return (ContractHome) HomeFactory.getFactory().lookUpHome(
				ContractHome.class, "domain/ContractLocal");
	}

	public static ContractToPackageHome getContractToPackageHome()
			throws HomeFactoryException {
		return (ContractToPackageHome) HomeFactory.getFactory().lookUpHome(
				ContractToPackageHome.class, "domain/ContractToPackageLocal");
	}

	public static ContractProcessLogHome getContractProcessLogHome()
			throws HomeFactoryException {
		return (ContractProcessLogHome) HomeFactory.getFactory().lookUpHome(
				ContractProcessLogHome.class, "domain/ContractProcessLogLocal");
	}

	public static BillBoardHome getBillBoardHome() throws HomeFactoryException {
		return (BillBoardHome) HomeFactory.getFactory().lookUpHome(
				BillBoardHome.class, "domain/BillBoardLocal");
	}

	public static SystemSettingHome getSystemSettingHome()
			throws HomeFactoryException {
		return (SystemSettingHome) HomeFactory.getFactory().lookUpHome(
				SystemSettingHome.class, "domain/SystemSettingLocal");
	}

	public static FutureRightHome getFutureRightHome()
			throws HomeFactoryException {
		return (FutureRightHome) HomeFactory.getFactory().lookUpHome(
				FutureRightHome.class, "domain/FutureRightLocal");
	}

	public static CampaignCondProductHome getCampaignCondProductHome()
			throws HomeFactoryException {
		return (CampaignCondProductHome) HomeFactory.getFactory().lookUpHome(
				CampaignCondProductHome.class,
				"domain/CampaignCondProductLocal");
	}

	public static CampaignCondPackageHome getCampaignCondPackageHome()
			throws HomeFactoryException {
		return (CampaignCondPackageHome) HomeFactory.getFactory().lookUpHome(
				CampaignCondPackageHome.class,
				"domain/CampaignCondPackageLocal");
	}

	public static CampaignCondCampaignHome getCampaignCondCampaignHome()
			throws HomeFactoryException {
		return (CampaignCondCampaignHome) HomeFactory.getFactory().lookUpHome(
				CampaignCondCampaignHome.class,
				"domain/CampaignCondCampaignLocal");
	}

	public static CampaignAgmtProductHome getCampaignAgmtProductHome()
			throws HomeFactoryException {
		return (CampaignAgmtProductHome) HomeFactory.getFactory().lookUpHome(
				CampaignAgmtProductHome.class,
				"domain/CampaignAgmtProductLocal");
	}

	public static CampaignAgmtPackageHome getCampaignAgmtPackageHome()
			throws HomeFactoryException {
		return (CampaignAgmtPackageHome) HomeFactory.getFactory().lookUpHome(
				CampaignAgmtPackageHome.class,
				"domain/CampaignAgmtPackageLocal");
	}

	public static OssAuthorizationHome getOssAuthorizationHome()
			throws HomeFactoryException {
		return (OssAuthorizationHome) HomeFactory.getFactory().lookUpHome(
				OssAuthorizationHome.class, "domain/OssAuthorizationLocal");
	}

	public static BundlePaymentMethodHome getBundlePaymentMethodHome()
			throws HomeFactoryException {
		return (BundlePaymentMethodHome) HomeFactory.getFactory().lookUpHome(
				BundlePaymentMethodHome.class,
				"domain/BundlePaymentMethodLocal");
	}

	public static BundlePrepaymentHome getBundlePrepaymentHome()
			throws HomeFactoryException {
		return (BundlePrepaymentHome) HomeFactory.getFactory().lookUpHome(
				BundlePrepaymentHome.class, "domain/BundlePrepaymentLocal");
	}

	public static CampaignAgmtCampaignHome getCampaignAgmtCampaignHome()
			throws HomeFactoryException {
		return (CampaignAgmtCampaignHome) HomeFactory.getFactory().lookUpHome(
				CampaignAgmtCampaignHome.class,
				"domain/CampaignAgmtCampaignLocal");
	}

	public static ManualTransferSettingHome getManualTransferSettingHome()
			throws HomeFactoryException {
		return (ManualTransferSettingHome) HomeFactory.getFactory().lookUpHome(
				ManualTransferSettingHome.class,
				"domain/ManualTransferSettingLocal");
	}

	public static Bundle2CampaignHome getBundle2CampaignHome()
			throws HomeFactoryException {
		return (Bundle2CampaignHome) HomeFactory.getFactory().lookUpHome(
				Bundle2CampaignHome.class, "domain/Bundle2CampaignLocal");
	}

	public static FeeSplitPlanHome getFeeSplitPlanHome()
			throws HomeFactoryException {
		return (FeeSplitPlanHome) HomeFactory.getFactory().lookUpHome(
				FeeSplitPlanHome.class, "domain/FeeSplitPlanLocal");
	}

	public static FeeSplitPlanItemHome getFeeSplitPlanItemHome()
			throws HomeFactoryException {
		return (FeeSplitPlanItemHome) HomeFactory.getFactory().lookUpHome(
				FeeSplitPlanItemHome.class, "domain/FeeSplitPlanItemLocal");
	}

	public static CsiActionReasonSettingHome getCsiActionReasonSettingHome()
			throws HomeFactoryException {
		return (CsiActionReasonSettingHome) HomeFactory.getFactory()
				.lookUpHome(FeeSplitPlanItemHome.class,
						"domain/CsiActionReasonSettingLocal");
	}

	public static CsiActionReasonDetailHome getCsiActionReasonDetailHome()
			throws HomeFactoryException {
		return (CsiActionReasonDetailHome) HomeFactory.getFactory().lookUpHome(
				CsiActionReasonDetailHome.class,
				"domain/CsiActionReasonDetailLocal");
	}

	public static CsiTypeReason2DeviceHome getCsiTypeReason2DeviceHome()
			throws HomeFactoryException {
		return (CsiTypeReason2DeviceHome) HomeFactory.getFactory().lookUpHome(
				CsiTypeReason2DeviceHome.class,
				"domain/CsiTypeReason2DeviceLocal");
	}

	public static SwapDeviceReason2StatusHome getSwapDeviceReason2StatusHome()
			throws HomeFactoryException {
		return (SwapDeviceReason2StatusHome) HomeFactory.getFactory()
				.lookUpHome(SwapDeviceReason2StatusHome.class,
						"domain/SwapDeviceReason2StatusLocal");
	}

	public static LdapConditionHome getLdapConditionHome()
			throws HomeFactoryException {
		return (LdapConditionHome) HomeFactory.getFactory().lookUpHome(
				LdapConditionHome.class, "domain/LdapConditionLocal");
	}

	public static MachineRoomHome getMachineRoomHome()
			throws HomeFactoryException {
		return (MachineRoomHome) HomeFactory.getFactory().lookUpHome(
				MachineRoomHome.class, "domain/MachineRoomLocal");
	}

	public static FiberTransmitterHome getFiberTransmitterHome()
			throws HomeFactoryException {
		return (FiberTransmitterHome) HomeFactory.getFactory().lookUpHome(
				FiberTransmitterHome.class, "domain/FiberTransmitterLocal");
	}

	public static FiberNodeHome getFiberNodeHome() throws HomeFactoryException {
		return (FiberNodeHome) HomeFactory.getFactory().lookUpHome(
				FiberNodeHome.class, "domain/FiberNodeLocal");
	}

	public static FiberReceiverHome getFiberReceiverHome()
			throws HomeFactoryException {
		return (FiberReceiverHome) HomeFactory.getFactory().lookUpHome(
				FiberReceiverHome.class, "domain/FiberReceiverLocal");
	}

	public static CatvJobCardConfigHome getCatvJobCardConfigHome()
			throws HomeFactoryException {
		return (CatvJobCardConfigHome) HomeFactory.getFactory().lookUpHome(
				CatvJobCardConfigHome.class, "domain/CatvJobCardConfigLocal");
	}

	public static CAWalletHome getCAWalletHome() throws HomeFactoryException {
		return (CAWalletHome) HomeFactory.getFactory().lookUpHome(
				CAWalletHome.class, "domain/CAWalletLocal");
	}

	public static CAWalletChargeRecordHome getCAWalletChargeRecordHome()
			throws HomeFactoryException {
		return (CAWalletChargeRecordHome) HomeFactory.getFactory().lookUpHome(
				CAWalletChargeRecordHome.class,
				"domain/CAWalletChargeRecordLocal");
	}

	public static CAWalletDefineHome getCAWalletDefineHome()
			throws HomeFactoryException {
		return (CAWalletDefineHome) HomeFactory.getFactory().lookUpHome(
				CAWalletDefineHome.class, "domain/CAWalletDefineLocal");
	}

	/**
	 * @return
	 */
	public static CatvTermProcessHome getCatvTermProcessHome()
			throws HomeFactoryException {
		return (CatvTermProcessHome) HomeFactory.getFactory().lookUpHome(
				CatvTermProcessHome.class, "domain/CatvTermProcessLocal");
	}

	public static CatvTermProcessItemHome getCatvTermProcessItemHome()
			throws HomeFactoryException {
		return (CatvTermProcessItemHome) HomeFactory.getFactory().lookUpHome(
				CatvTermProcessItemHome.class,
				"domain/CatvTermProcessItemLocal");
	}

	public static CampaignPaymentAwardHome getCampaignPaymentAwardHome()
			throws HomeFactoryException {
		return (CampaignPaymentAwardHome) HomeFactory.getFactory().lookUpHome(
				CampaignPaymentAwardHome.class,
				"domain/CampaignPaymentAwardLocal");
	}

	public static DevicePrematchRecordHome getDevicePrematchRecordHome()
			throws HomeFactoryException {
		return (DevicePrematchRecordHome) HomeFactory.getFactory().lookUpHome(
				DevicePrematchRecordHome.class,
				"domain/DevicePrematchRecordLocal");
	}

	public static DevicePreauthRecordHome getDevicePreauthRecordHome()
			throws HomeFactoryException {
		return (DevicePreauthRecordHome) HomeFactory.getFactory().lookUpHome(
				DevicePreauthRecordHome.class,
				"domain/DevicePreauthRecordLocal");
	}

	public static DeviceBatchPreauthHome getDeviceBatchPreauthHome()
			throws HomeFactoryException {
		return (DeviceBatchPreauthHome) HomeFactory.getFactory().lookUpHome(
				DeviceBatchPreauthHome.class, "domain/DeviceBatchPreauthLocal");
	}
	
	public static FaPiaoHome getFaPiaoHome()
			throws HomeFactoryException {
		return (FaPiaoHome) HomeFactory.getFactory().lookUpHome(
				FaPiaoHome.class, "domain/FaPiaoLocal");
	}
	
	public static FapiaoDetailHome getFapiaoDetailHome()
			throws HomeFactoryException {
		return (FapiaoDetailHome) HomeFactory.getFactory().lookUpHome(
				FapiaoDetailHome.class, "domain/FapiaoDetailLocal");
	}
	
	public static Fapiao2PaymentHome getFapiao2PaymentHome()
			throws HomeFactoryException {
		return (Fapiao2PaymentHome) HomeFactory.getFactory().lookUpHome(
				Fapiao2PaymentHome.class, "domain/Fapiao2PaymentLocal");
	}
	
	public static FapiaoTransitionHome getFapiaoTransitionHome()
			throws HomeFactoryException {
		return (FapiaoTransitionHome) HomeFactory.getFactory().lookUpHome(
				FapiaoTransitionHome.class, "domain/FapiaoTransitionLocal");
	}
	
	public static FapiaoTransitionDetailHome getFapiaoTransitionDetailHome()
			throws HomeFactoryException {
		return (FapiaoTransitionDetailHome) HomeFactory.getFactory().lookUpHome(
				FapiaoTransitionDetailHome.class, "domain/FapiaoTransitionDetailLocal");
	}
	
	public static FapiaoVolumnHome getFapiaoVolumnHome()
			throws HomeFactoryException {
		return (FapiaoVolumnHome) HomeFactory.getFactory().lookUpHome(
				FapiaoVolumnHome.class, "domain/FapiaoVolumnLocal");
	}
	
	public static FapiaoModuleHome getFapiaoModuleHome()
			throws HomeFactoryException {
		return (FapiaoModuleHome) HomeFactory.getFactory().lookUpHome(
				FapiaoModuleHome.class, "domain/FapiaoModuleLocal");
	}
	
}
