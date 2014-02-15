package com.dtv.oss.service.ejbevent.batch;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class QueryBatchJobEJBEvent extends QueryEJBEvent {

	//查找批量任务单
	public static final int BATCHJOB_QUERY=11;
	//查找批量任务单记录集
	public static final int BATCHJOB_ITEM_QUERY=12;
	//查找批量任务单记录集LOG
	public static final int QUERY_TYPE_BATCH_QUERY_LOG=13;
	
	 public QueryBatchJobEJBEvent() {
		 super();
	 }
	 public QueryBatchJobEJBEvent(Object dto, int from, int to, int querytype) {
		 super(dto, from, to, querytype);
	 }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
