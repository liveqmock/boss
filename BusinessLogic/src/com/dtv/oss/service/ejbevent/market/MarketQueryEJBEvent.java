/*
 * Created on 2006-3-20
 *
 * @author Chen Jiang
 */
package com.dtv.oss.service.ejbevent.market;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class MarketQueryEJBEvent extends QueryEJBEvent {
    public static final int QUERY_TYPE_CAMPAIGN = 1;
    public static final int QUERY_TYPE_GROUPBARGAIN = 2;
    public static final int QUERY_TYPE_GROUPBARGAIN_DETAIL = 3;
    public static final int QUERY_TYPE_GROUPBARGAIN_CLASS = 4;
    

    public MarketQueryEJBEvent() {
        super();
    }

    public MarketQueryEJBEvent(Object dto, int from, int to, int querytype) {
        super(dto, from, to, querytype);
    }
}