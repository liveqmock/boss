package com.dtv.oss.service.ejbevent.groupcustomer;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class GroupCustomerQueryEJBEvent extends QueryEJBEvent{
	public static final int QUERY_GROUP_CONTRACT_INFO=11;
	public static final int QUERY_GROUP_PRODUCTLIST=12;
	public static final int QUERY_GROUP_PACKAGELIST = 13;
	
	public GroupCustomerQueryEJBEvent(){
		super();
	}
	public GroupCustomerQueryEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
	}
}
