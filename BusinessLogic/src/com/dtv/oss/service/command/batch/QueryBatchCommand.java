package com.dtv.oss.service.command.batch;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.batch.QueryBatchEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.batch.BankDeductionDetailListHandler;
import com.dtv.oss.service.listhandler.batch.BankDeductionHeaderListHandler;
import com.dtv.oss.service.listhandler.batch.BatchListHandler;
import com.dtv.oss.service.listhandler.batch.BatchResultListHandler;
import com.dtv.oss.service.listhandler.batch.BatchSendMessageQueryListHandler;
import com.dtv.oss.service.listhandler.batch.ExportCustomerDetailListHandler;
import com.dtv.oss.service.listhandler.batch.FoundCustomerDetailListHandler;


public class QueryBatchCommand extends QueryCommand {

	private int operatorID = 0;
	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		 this.operatorID = event.getOperatorID();
		switch (event.getType()) {
			case QueryBatchEJBEvent.QUERY_TYPE_BATCH_QUERY:
				handler = new BatchListHandler();
				break;
			case QueryBatchEJBEvent.QUERY_TYPE_BATCH_RESULT_ITEM:
				handler = new BatchResultListHandler();
				break;
			case QueryBatchEJBEvent.QUERY_TYPE_BANK_DEDUCTION_HEADER:
				handler = new BankDeductionHeaderListHandler();
				break;
			case QueryBatchEJBEvent.QUERY_TYPE_BANK_DEDUCTION_DETAIL:
				handler = new BankDeductionDetailListHandler();
				break;
			//批量发送消息信息查询
			case QueryBatchEJBEvent.QUERY_TYPE_BATCH_SEND_MESSAGE:
				handler = new BatchSendMessageQueryListHandler(operatorID);
				break;
			case QueryBatchEJBEvent.QUERY_FOUNDCUSTOMER_DETAIL:
				handler = new FoundCustomerDetailListHandler(); 
				break;
			case QueryBatchEJBEvent.QUERY_EXPORTCUSTOMER_DETAIL:
				handler = new ExportCustomerDetailListHandler();
				break;
			 default :
				break;
		}
		return handler;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
