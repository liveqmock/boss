package com.dtv.oss.service.ejbevent.csr;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class MonistatQueryEJBEvent extends QueryEJBEvent {  
   public static final int QUERY_CSI_STAT_FOR_HUAIROU =9; //怀柔受理业务统计
   
   public static final int QUERY_CUSTOMER_INFO_STAT =10; //歌华有线用户情况统计表
   
   public static final int QUERY_FREE_TAXI_REPORT =11; //分公司整转免税数据统计表
   
   public static final int QUERY_WATCH_FEE_MONTH_REPORT =12; //歌华收视费月报表
   
   public static final int QUERY_FINANCIAL_REPORT =13; //收看维护费缴费及结转账收入汇总
   
   public static final int QUERY_FINANCIAL_DETAIL_REPORT =14; //收视费、户数统计
   
   public static final int QUERY_PAYFROMBLANK_REPORT =15; //银行代缴费统计

   public MonistatQueryEJBEvent() {
		super();
	}
	public MonistatQueryEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
	}
}
