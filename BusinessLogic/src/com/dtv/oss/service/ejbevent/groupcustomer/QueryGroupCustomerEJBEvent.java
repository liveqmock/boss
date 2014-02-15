package com.dtv.oss.service.ejbevent.groupcustomer;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;
/**
 * 设备管理配置
 * @author 260327h
 *
 */
public class QueryGroupCustomerEJBEvent extends QueryEJBEvent {
	public static final int QUERY_CONTRACT=1;
 
	 
	public static final int QUERY_GROUP_PRODUCTLIST=2;
	public static final int QUERY_GROUP_PACKAGELIST=3;
	public static final int QUERY_CONTRACT_LOG=4;
 
	public QueryGroupCustomerEJBEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QueryGroupCustomerEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
		// TODO Auto-generated constructor stub
	}

}
