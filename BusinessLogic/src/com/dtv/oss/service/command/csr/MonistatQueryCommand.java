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
	    //����ҵ������ͳ��
		case MonistatQueryEJBEvent.QUERY_CSI_STAT_FOR_HUAIROU:
			handler =new CsiStatForHuairouHandler();
			break;
        // �軪�����û����ͳ�Ʊ�
		case MonistatQueryEJBEvent.QUERY_CUSTOMER_INFO_STAT:
			handler =new CustomerInfoHandler();
			break;
	    // �ֹ�˾��ת��˰����ͳ�Ʊ�
		case MonistatQueryEJBEvent.QUERY_FREE_TAXI_REPORT:
			handler =new CommanyFreeTaxiHandler();
			break;
		// �軪���ӷ��±���
		case MonistatQueryEJBEvent.QUERY_WATCH_FEE_MONTH_REPORT:
			handler =new WatchFeeMonthReportHandler();
			break;
		// �տ�ά���ѽɷѼ���ת���������
		case MonistatQueryEJBEvent.QUERY_FINANCIAL_REPORT:
			handler =new FinancialReportHandler();
			break;
	    // ���ӷѡ�����ͳ��
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
