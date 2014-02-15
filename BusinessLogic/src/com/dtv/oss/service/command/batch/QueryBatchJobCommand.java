package com.dtv.oss.service.command.batch;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.batch.QueryBatchJobEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.batch.BatchJobListHandler;
import com.dtv.oss.service.listhandler.batch.BatchJobProcessLogListHandler;
import com.dtv.oss.service.listhandler.batch.BatchJobResultListHandler;


public class QueryBatchJobCommand extends QueryCommand {
	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;

		switch (event.getType()) {
			case QueryBatchJobEJBEvent.BATCHJOB_QUERY:
				handler = new BatchJobListHandler();
				break;
			case QueryBatchJobEJBEvent.BATCHJOB_ITEM_QUERY:
				handler = new BatchJobResultListHandler();
				break;
			case QueryBatchJobEJBEvent.QUERY_TYPE_BATCH_QUERY_LOG:
				handler = new BatchJobProcessLogListHandler();
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
