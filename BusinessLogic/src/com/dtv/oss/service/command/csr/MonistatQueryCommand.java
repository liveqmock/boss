package com.dtv.oss.service.command.csr;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.csr.MonistatQueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.monistat.CommanyFreeTaxiHandler;
import com.dtv.oss.service.listhandler.monistat.CsiStatForHuairouHandler;
import com.dtv.oss.service.listhandler.monistat.CustomerInfoHandler;
import com.dtv.oss.service.listhandler.monistat.FinancialDetailReportHandler;
import com.dtv.oss.service.listhandler.monistat.FinancialReportHandler;
import com.dtv.oss.service.listhandler.monistat.PayFromBlankReportHandler;
import com.dtv.oss.service.listhandler.monistat.WatchFeeMonthReportHandler;

public class MonistatQueryCommand extends QueryCommand {
	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		switch(event.getType()) {
	    //怀柔业务受理统计
		case MonistatQueryEJBEvent.QUERY_CSI_STAT_FOR_HUAIROU:
			handler =new CsiStatForHuairouHandler();
			break;
        // 歌华有线用户情况统计表
		case MonistatQueryEJBEvent.QUERY_CUSTOMER_INFO_STAT:
			handler =new CustomerInfoHandler();
			break;
	    // 分公司整转免税数据统计表
		case MonistatQueryEJBEvent.QUERY_FREE_TAXI_REPORT:
			handler =new CommanyFreeTaxiHandler();
			break;
		// 歌华收视费月报表
		case MonistatQueryEJBEvent.QUERY_WATCH_FEE_MONTH_REPORT:
			handler =new WatchFeeMonthReportHandler();
			break;
		// 收看维护费缴费及结转账收入汇总
		case MonistatQueryEJBEvent.QUERY_FINANCIAL_REPORT:
			handler =new FinancialReportHandler();
			break;
	    // 收视费、户数统计
		case MonistatQueryEJBEvent.QUERY_FINANCIAL_DETAIL_REPORT:
			handler =new FinancialDetailReportHandler();
			break;
		case MonistatQueryEJBEvent.QUERY_PAYFROMBLANK_REPORT:
			handler =new PayFromBlankReportHandler();
			break;
		default:
			break;
		}
		return handler;
	}
}
