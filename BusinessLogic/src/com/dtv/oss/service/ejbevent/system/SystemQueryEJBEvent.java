/*
 * Created on 2004-8-10
 *
 * @author Yan Jian
 */
package com.dtv.oss.service.ejbevent.system;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class SystemQueryEJBEvent extends QueryEJBEvent {
    public static final int QUERY_TYPE_CAMPAIGN = 1;
    public static final int QUERY_TYPE_GROUPBARGAIN = 2;
    public static final int QUERY_TYPE_GROUPBARGAIN_DETAIL = 3;
    public static final int QUERY_TYPE_GROUPBARGAIN_CLASS = 4;
    public static final int QUERY_CONTRACT = 10;
    public static final int QUERY_CONTRACT_DETAIL = 11;
    public static final int QUERY_CONTRACT_PROCESS_LOG = 12;
    public static final int QUERY_FUTURERIGHT =13;

    public static final int QUERY_CUSTOMER_CAMPAIGN=20;
    public static final int QUERY_CUSTOMER_CAMPAIGN_MAP=21;
    public static final int QUERY_CUSTOMER_BUNDLE=22;
    public static final int QUERY_CUSTOMER_BUNDLE_MAP=23;
    
    public static final int QUERY_BILLBOARD =30;
    
    public SystemQueryEJBEvent() {
        super();
    }

    public SystemQueryEJBEvent(Object dto, int from, int to, int querytype) {
        super(dto, from, to, querytype);
    }
}