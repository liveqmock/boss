package com.dtv.oss.service.ejbevent.batch;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class QueryBatchEJBEvent extends QueryEJBEvent {
	  
	//查找批量查找记录集
	public static final int QUERY_TYPE_BATCH_QUERY=11;
	//查找批量结果记录集
	public static final int QUERY_TYPE_BATCH_RESULT_ITEM=12;
	
	//查找银行托收头记录
	public static final int QUERY_TYPE_BANK_DEDUCTION_HEADER=21;
	//查找银行托收明细记录
	public static final int QUERY_TYPE_BANK_DEDUCTION_DETAIL=22;
	//查找批量发送消息
	public static final int QUERY_TYPE_BATCH_SEND_MESSAGE=28;
	//模拟建档用户查询
	public static final int QUERY_FOUNDCUSTOMER_DETAIL =23;
	//整转用户查询
	public static final int QUERY_EXPORTCUSTOMER_DETAIL =24;
	
	 public QueryBatchEJBEvent() {
		 super();
	 }
	 public QueryBatchEJBEvent(Object dto, int from, int to, int querytype) {
		 super(dto, from, to, querytype);
	 }  
	  
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
