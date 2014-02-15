/*
 * Created on 2004-8-9
 *
 * @author Mac Wang
 */
package com.dtv.oss.service.command.csr;
import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.csr.AccountItemListHandler;
import com.dtv.oss.service.listhandler.csr.AccountListHandler;
import com.dtv.oss.service.listhandler.csr.AccountPrepaymentDeductionListHandler;
import com.dtv.oss.service.listhandler.csr.AdjustAccountListHandler;
import com.dtv.oss.service.listhandler.csr.BatchInvoiceListHandler;
import com.dtv.oss.service.listhandler.csr.BookProductCSIListHandler;
import com.dtv.oss.service.listhandler.csr.CATVListHandler;
import com.dtv.oss.service.listhandler.csr.CampaignListhandler;
import com.dtv.oss.service.listhandler.csr.CatvJobCardListHandler;
import com.dtv.oss.service.listhandler.csr.CatvTerminalListHandler;
import com.dtv.oss.service.listhandler.csr.CsiProcessLogListHandler;
import com.dtv.oss.service.listhandler.csr.CustComplainListhandler;
import com.dtv.oss.service.listhandler.csr.CustComplainProcessListHandler;
import com.dtv.oss.service.listhandler.csr.CustServiceAccountListHandler;
import com.dtv.oss.service.listhandler.csr.CustServiceInteractionListHandler;
import com.dtv.oss.service.listhandler.csr.CustServiceInteractionOnlyListHandler;
import com.dtv.oss.service.listhandler.csr.CustomerBillingRuleListHandler;
import com.dtv.oss.service.listhandler.csr.CustomerListHandler;
import com.dtv.oss.service.listhandler.csr.CustomerPhoneNoListHandler;
import com.dtv.oss.service.listhandler.csr.CustomerProductListHandler;
import com.dtv.oss.service.listhandler.csr.FeeRecordAllListHandler;
import com.dtv.oss.service.listhandler.csr.FiberNodeListHandler;
import com.dtv.oss.service.listhandler.csr.ForcedDepositListHandler;
import com.dtv.oss.service.listhandler.csr.GroupCustListhandler;
import com.dtv.oss.service.listhandler.csr.InvoiceListHandler;
import com.dtv.oss.service.listhandler.csr.OneCustomerListHandler;
import com.dtv.oss.service.listhandler.csr.PackageLineListhandler;
import com.dtv.oss.service.listhandler.csr.PaymentRecordAllListHandler;
import com.dtv.oss.service.listhandler.csr.PaymentRecordListHandler;
import com.dtv.oss.service.listhandler.csr.PhoneNoListHandler;
import com.dtv.oss.service.listhandler.csr.PointsActivityListHandler;
import com.dtv.oss.service.listhandler.csr.PointsGoodsListHandler;
import com.dtv.oss.service.listhandler.csr.PointsRecordListHandler;
import com.dtv.oss.service.listhandler.csr.PrepaymentDeductionRecordListHandler;
import com.dtv.oss.service.listhandler.csr.ProdcutListHandler;
import com.dtv.oss.service.listhandler.csr.ProductPackageListhandler;
import com.dtv.oss.service.listhandler.csr.QueryCustomerBillingRuleListHandler;
import com.dtv.oss.service.listhandler.csr.RealIncomeListHandler;
import com.dtv.oss.service.listhandler.csr.SATerminalDeviceListHandler;
import com.dtv.oss.service.listhandler.csr.ServiceAccountForScheduleListHandler;
import com.dtv.oss.service.listhandler.csr.ServiceAccountListHandler;
import com.dtv.oss.service.listhandler.csr.SetoffRecordListHandler;
import com.dtv.oss.service.listhandler.csr.SimpleCustServiceInteractionListHandler;
import com.dtv.oss.service.listhandler.csr.SimpleCustServiceInteractionViewListHandler;
import com.dtv.oss.service.listhandler.csr.TerminateDeviceListhandler;
import com.dtv.oss.service.listhandler.csr.CAWalletChargeRecordListHandler;
import com.dtv.oss.service.listhandler.csr.CAWalletServiceInteractionListHandler;
import com.dtv.oss.service.listhandler.csr.CAWalletListHandler;
import com.dtv.oss.service.listhandler.csr.BatchPrepayListHandler;
import com.dtv.oss.service.listhandler.csr.BatchMergeInvoiceListHandler;
import com.dtv.oss.service.listhandler.csr.OweSuspendListHandler;

public class CsrQueryCommand extends QueryCommand {
	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		switch(event.getType()) {
			//用户查询
			case CsrQueryEJBEvent.QUERY_TYPE_CUSTOMER:
				handler = new CustomerListHandler();
				break;
            //客户电话号码查询
			case CsrQueryEJBEvent.QUERY_TYPE_CUSTOMER_PHONENO:
				handler = new CustomerPhoneNoListHandler();
				break;	
			//查询单一用户信息	
			case CsrQueryEJBEvent.QUERY_TYPE_ONECUSTOMER:
				handler = new OneCustomerListHandler();
				break;
			//客户投诉
			case CsrQueryEJBEvent.QUERY_TYPE_CUSTCOMPLAIN:
	        	handler=new CustComplainListhandler();
	        	break;	
	        //查询集团用户的子客户
			case CsrQueryEJBEvent.QUERY_GROUP_CUST:
				handler = new GroupCustListhandler();
				break;
            //查询业务帐户信息根据客户
			case CsrQueryEJBEvent.QUERY_TYPE_SERVICE_ACCOUNT_BY_CUST:
				handler = new CustServiceAccountListHandler();
				break;				
			//查询业务帐户信息
			case CsrQueryEJBEvent.QUERY_TYPE_SERVICE_ACCOUNT:
				handler = new ServiceAccountListHandler();
				break;			
			//lookup info of accout add by chen jiang ,date 2005/10/28
			case CsrQueryEJBEvent.QUERY_TYPE_ACCOUNT:
				handler = new AccountListHandler();
			    break;
			
			//查找可购买的产品包， add by zhouxushun ,date 2005-10-10
			case CsrQueryEJBEvent.QUERY_TYPE_CAN_PURCHASE_PRODUCTPACKAGE:
				handler=new  ProductPackageListhandler();
				break;
			//查找产品包的产品信息， add by zhouxushun , date :2005-10-10
			case CsrQueryEJBEvent.QUERY_TYPE_CURRENT_PACKAGE:
				handler=new PackageLineListhandler();
				break;
			//查找优惠信息， add by zhouxushun , date 2005-10-11 
			case CsrQueryEJBEvent.QUERY_TYPE_CAMPAIGN:
				handler=new CampaignListhandler();
				break;
			//查找终端设备， add by zhouxushun , date :2005-10-11
			case CsrQueryEJBEvent.QUERY_TYPE_TERMINALDEVICE:
				handler=new TerminateDeviceListhandler();
				break;
			//通过用户地址和catvid查找终端用户 add by david.Yang
			case CsrQueryEJBEvent.QUERY_TYPE_CATV:
	            handler =new CATVListHandler();
			    break;               
			//通过通用的CommonQueryConditionDTO查找预约单 add by david.Yang
			case CsrQueryEJBEvent.QUERY_TYPE_CUSTSERVICEINTERACTION:
				handler = new CustServiceInteractionListHandler();
				break;
		    //通过通用的CommonQueryConditionDTO查找受理单的日志信息 add by david.Yang
            case CsrQueryEJBEvent.QUERY_TYPE_CSI_PROCESS_LOG:
            	handler =new CsiProcessLogListHandler();
                break;
            //通过通用的CommonQueryConditionDTO查找受理单记录 add by david.Yang
            case CsrQueryEJBEvent.QUERY_TYPE_SIMPLECUSTSERVICEINTERACTION:
				handler = new SimpleCustServiceInteractionListHandler();
				break;
			//查找帐单记录 add by david.Yang
            case CsrQueryEJBEvent.QUERY_TYPE_CUSTINVOICE:
				handler = new InvoiceListHandler();
				break;
		    //查找帐单支出记录 add by david.Yang
            case CsrQueryEJBEvent.QUERY_TYPE_PAYMENT_RECORD:
				handler = new PaymentRecordListHandler();
				break;	
			//查找帐单费用记录 add by david.Yang
            case CsrQueryEJBEvent.QUERY_TYPE_CUSTACCOUNTITEM_RECORD:
            	handler =new AccountItemListHandler();
            	break;
            //查找客户产品 add by david.Yang
			case CsrQueryEJBEvent.QUERY_TYPE_CUSTPRODUCT:
				handler = new CustomerProductListHandler();
				break;
			//查找帐户的预存抵扣 add by david.yang
			case CsrQueryEJBEvent.QUERY_TYPE_PREPAYMENTDEDUCTION:
				handler = new AccountPrepaymentDeductionListHandler();
				break;
    		//查找押金记录	
            case CsrQueryEJBEvent.QUERY_TYPE_FORCED_DEPOSIT:
				handler = new ForcedDepositListHandler();
				break;
            //积分活动查询	
            case CsrQueryEJBEvent.QUERY_POINTS_ACTIVITY:
				handler = new PointsActivityListHandler();
				break;
            case CsrQueryEJBEvent.QUERY_POINTS_GOODS:
				handler = new PointsGoodsListHandler();
				break;	
            case CsrQueryEJBEvent.QUERY_POINTS_RECORD:
            	handler = new PointsRecordListHandler();
            	break;		
            	
		    //查询调帐记录 add by jason.zhou
	        case CsrQueryEJBEvent.QUERY_ADJUST_ACCOUNT:
	        	handler=new AdjustAccountListHandler();
	        	break;
			//查询费用记录 add by jason.zhou
	        case CsrQueryEJBEvent.QUERY_FEE_RECORD:
	        	handler=new FeeRecordAllListHandler();
	        	break;
	        //查询支付记录 add by jason.zhou
	        case CsrQueryEJBEvent.QUERY_PAMENTT_RECORD:
	        	handler=new PaymentRecordAllListHandler();
	        	break;
	        //预存抵扣记录查询 add by jason.zhou
	        case CsrQueryEJBEvent.QUERY_PREPAYMENT_DEDUCTION_RECORD:
	        	handler=new PrepaymentDeductionRecordListHandler();
	        	break;
	        //销帐记录查询 add by jason.zhou
	        case CsrQueryEJBEvent.QUERY_SETOFF_RECORD:
	        	handler=new SetoffRecordListHandler();
	        	break;
	        	  //销帐记录查询 add by jason.zhou
	        case CsrQueryEJBEvent.QUERY_PRODUCT:
	        	handler=new ProdcutListHandler();
	        	break; 	
	        case CsrQueryEJBEvent.QUERY_TYPE_PHONENO:
	        	handler=new PhoneNoListHandler();
	        	break; 
	        case CsrQueryEJBEvent.QUERY_SERVICEACCOUNT_TERMINALDEVICE:
	        	handler=new SATerminalDeviceListHandler();
	        	break;	
	        case CsrQueryEJBEvent.QUERY_COMPLAIN_PROCESS:
	        	handler=new CustComplainProcessListHandler();
	        	break;
	        //安装工单打印
	        case CsrQueryEJBEvent.QUERY_TYPE_CUSTSERVICEINTERACTION_ONLY:
	        	handler=new CustServiceInteractionOnlyListHandler();
	        	break;
	        //批量帐单查询
	        case CsrQueryEJBEvent.QUERY_BATCH_INVOICE:
	        	handler=new BatchInvoiceListHandler();
	        	break;
	       	 //帐单合并批量查询
	        case CsrQueryEJBEvent.QUERY_MERGE_BATCH_INVOICE:
	        	handler=new BatchMergeInvoiceListHandler();
	        	break;
	        //新增客户产品受理单查询
	        case CsrQueryEJBEvent.QUERY_BOOK_PRODUCT_CUSTSERVICEINTERACTION:
	        	handler=new BookProductCSIListHandler();
	        	break;
	        //客户个性化费率查询
	        case CsrQueryEJBEvent.QUERY_CUSTOMER_BILLINGRULE:
	        	handler=new CustomerBillingRuleListHandler();
	        	break;
	        //客户个性化费率查询 2(从客户树：特殊财务处理-->个性化费率 入口)
	        case CsrQueryEJBEvent.QUERY_CUSTOMER_BILLING_RULE:
	        	handler=new QueryCustomerBillingRuleListHandler();
	        	break;
	        //模拟终端查询
	        case CsrQueryEJBEvent.QUERY_TYPE_CatvTerminal:
	        	handler=new CatvTerminalListHandler();
	        	break;
	        //光节点查询
	        case CsrQueryEJBEvent.QUERY_TYPE_FiberNode:
	        	handler=new FiberNodeListHandler();
	        	break;
	        //模拟工单查询
	        case CsrQueryEJBEvent.QUERY_TYPE_JOBCARD_CATV:
	        	handler=new CatvJobCardListHandler();
	        	break;
	        //查询业务帐户信息 for 排程
			case CsrQueryEJBEvent.QUERY_TYPE_SERVICE_ACCOUNT_FOR_SCHEDULE:
				handler = new ServiceAccountForScheduleListHandler();
				break;
			//小钱包充值记录查询
			case CsrQueryEJBEvent.QUERY_TYPE_CA_WALLET_CHARGE:
				handler = new CAWalletChargeRecordListHandler();
				break;
			//小钱包业务帐户查询
			case CsrQueryEJBEvent.QUERY_TYPE_CA_WALLET_Service_Interaction:
				handler = new CAWalletServiceInteractionListHandler();
				break;
			//小钱包业务帐户查询
			case CsrQueryEJBEvent.QUERY_TYPE_CA_WALLET:
				handler = new CAWalletListHandler();
				break;	
			//受理单查询（通过视图）优化 
            case CsrQueryEJBEvent.QUERY_TYPE_SIMPLECUSTSERVICEINTERACTIONVIEW:
				handler = new SimpleCustServiceInteractionViewListHandler();	
				break;
			//现金流明细及其合计
			case CsrQueryEJBEvent.QUERY_CASH_FLOW_COUNT:
				handler = new RealIncomeListHandler();
				break;
			//停机,欠费查询
			case CsrQueryEJBEvent.QUERY_OWE_SUSPEND_CUSTOMER:
				handler =new OweSuspendListHandler();
				break;
				//批量预存查询
			case CsrQueryEJBEvent.QUERY_BATCH_PREPAY_ACCOUNT:
				handler =new BatchPrepayListHandler();
				break;		
			default:
				break;
		}
		return handler;
	}
}
