/*
 * Created on 2006-2-14
 *
 * @author Stone Liang
 * this QueryCommand is for statistics query only
 */
package com.dtv.oss.service.command.statistics;

import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.stat.*;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.service.listhandler.stat.DeviceDaySalesStatListHandler;

import com.dtv.oss.service.commandresponse.ErrorCode;

public class StatQueryCommand extends QueryCommand {
	
	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		
		ValueListHandler handler = null;
		switch(event.getType()) {
			//费用统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_FEE:
				handler = new FeeStatListHandler();
				break;
			//支付统计/预存统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_PAY:
				handler=new PayStatListHandler();
				break;
			//预存抵扣统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_PREPAYDEDUCTION:
				handler=new PrepayDeductionStatListHandler();
				break;
			//调帐统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_ADJUSTACCOUNT:
				handler=new AdjustAccountStatListHandler();
				break;
			//帐单统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_BILL:
				handler=new BillStatListHandler();
				break;
			//销帐统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_SETOFF:
				handler=new SetOffListHandler();
				break;
			//开户情况统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_OPEN_CUSTOMER:
				handler=new OpenCustomerListHandler();
				break;
			//其他受理统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_OTHER_CSI:
				handler=new OtherCSIStatListHandler();
				break;
            //安装失败统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_FAILED_INSTALLATION:
				handler=new FailedInstallationStatListHandler();
				break;
	        //预约统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_BOOKING:
				handler=new BookingStatListHandler();
				break;
		    //预约取消统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CANCELED_BOOKING:
				handler=new CancelledBookingStatListHandler();
				break;
            //报修受理统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_APPLY_FOR_RAIR:
				handler=new ApplyForRepairStatListHandler();
				break;
			//营业现金流统计（营业收费统计）
			case StatQueryEJBEvent.QUERY_TYPE_STAT_BUSINESS_CASH:
				handler=new BusinessCashStatListHandler();
				break;
			//营业点收费统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_BUSINESS_POINT_CASH:
				handler=new BusinessPointCashStatListHandler();
				break;
             //客户数统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMER:
				handler=new CustomerStatListHandler();
				break;
	        //客户产品数统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMER_PRODUCT:
				handler=new CustomerProductStatListHandler();
				break;
			//客户产品包统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMER_PRODUCT_PACKAGE:
				handler=new CustomerProductPackageStatListHandler();
				break;
			//非成功匹配帐户数统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_FAILED_BANK_ACCT_CHECK:
				handler=new FailedBankAcctCheckstatListHandler();
				break;
			//产品包出售情况统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_SALED_PRODUCT_PACKAGE:
				handler=new SaledProductPackageStatListHandler();
				break;
			//客户套餐统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERMEAL:
				handler=new CustomerCampaignStatListHandler(StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERMEAL);
				break;
			//订户数统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERSA:
				handler=new CustomerSAStatListHandler();
				break;
			//客户促销活动统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERCAMPAIGN:
				handler=new CustomerCampaignStatListHandler(StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERCAMPAIGN);
				break;
				
			//客户帐户数统计    add by jason 2007-1-12
			case StatQueryEJBEvent.QUERY_TYPE_STAT_ACCOUNT_INFO:
				handler=new AccountInfoStatListHandler();
				break;
			//帐户预存统计      add by jason 2007-1-12
			case StatQueryEJBEvent.QUERY_TYPE_STAT_ACCOUNT_DEPOSIT:
				handler=new AccountDepositStatListHandler();
				break;
			//帐户欠费统计      add by jason 2007-1-12
			case StatQueryEJBEvent.QUERY_TYPE_STAT_ACCOUNT_OWE:
				handler=new AccountOweStatListHandler();
				break;
			//银行帐户串配统计  add by jason 2007-1-12
			case StatQueryEJBEvent.QUERY_TYPE_STAT_ACCOUNT_BANK_MATCH:
				handler=new AcctBankMatchStatListHandler();
				break;
			//押金和期权统计   add by jason 2007-1-16
			case StatQueryEJBEvent.QUERY_TYPE_STAT_FORCEDEPOSIT_FUTURERIGHT:
				handler=new ForceRightFutureRightStatListHandler();
				break;
			//设备统计   add by jason 2007-1-16
			case StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_INFO:
				handler=new DeviceInfoStatListHandler();
				break;
			//设备库存统计   add by jason 2007-1-16
			case StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_STORE:
				handler=new DeviceStoreStatListHandler();
				break;
			//设备流转统计   add by jason 2007-1-16
			case StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_TRAN:
				handler=new DeviceTranStatListHandler();
				break;
			//产品销售统计  
			case StatQueryEJBEvent.QUERY_TYPE_STAT_PRODUCTSELL:
				handler=new ProductSellStatListHandler();
				break;
			//产品包销售统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_PRODUCTPACKAGESELL:
				handler=new ProductpackageSellStatListHandler();
				break;
			//套餐统计   
			case StatQueryEJBEvent.QUERY_TYPE_STAT_MEAL:
				handler=new MealStatListHandler();
				break;
			//促销活动捕获统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CAMPAIGNCATCH:
				handler=new CampaignCatchStatListHandler();
				break;
			//帐务统计-押金和期权统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_FORCEDEPOSIT_FUTURERIGHT_FOR_FINACE:
				handler=new ForceRightFutureRightForFinaceStatListHandler();
				break;
			//营业应收费统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_BUSINESS_ACCT_ITEM:
				handler=new BusinessAccountItemStatListHandler();
				break;
			//营业应收费统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CALL_BACK:
				handler=new CallBackStatListHandler();
				break;
			//预约受理量统计
			case StatQueryEJBEvent.QUERY_TYPE_BOOK_INTERACTION_SUM_STAT:
				handler=new BookInteractionSumStatListHandler();
				break;
			//报修量统计
			case StatQueryEJBEvent.QUERY_TYPE_CUSTOMER_PROBLEM_SUM_STAT:
				handler=new CustomerProblemSumStatListHandler();
				break;
			//设备置换统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_SWAP:
				handler=new DeviceSwapStatListHandler();
				break;
			//设备销售统计
			case StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_SALES:
				handler=new DeviceSalesStatListHandler();
				break;
			//机顶盒销售统计日报表
			case StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_DAY_SALES:
				handler=new DeviceDaySalesStatListHandler();
				break;
			default:
		}
		return handler;
	}

	private void checkFrom2To(int from, int to) throws CommandException {
	  if ( (from < 0) || (to < 0) ||
		  (to - from > 500) || (to - from < 0)) {
		throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	  }
	}
}
