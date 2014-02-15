package com.dtv.oss.service.ejbevent.csr;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class MonistatQueryEJBEvent extends QueryEJBEvent {  
   public static final int QUERY_CSI_STAT_FOR_HUAIROU =9; //��������ҵ��ͳ��
   
   public static final int QUERY_CUSTOMER_INFO_STAT =10; //�軪�����û����ͳ�Ʊ�
   
   public static final int QUERY_FREE_TAXI_REPORT =11; //�ֹ�˾��ת��˰����ͳ�Ʊ�
   
   public static final int QUERY_WATCH_FEE_MONTH_REPORT =12; //�軪���ӷ��±���
   
   public static final int QUERY_FINANCIAL_REPORT =13; //�տ�ά���ѽɷѼ���ת���������
   
   public static final int QUERY_FINANCIAL_DETAIL_REPORT =14; //���ӷѡ�����ͳ��
   
   public static final int QUERY_PAYFROMBLANK_REPORT =15; //���д��ɷ�ͳ��

   public MonistatQueryEJBEvent() {
		super();
	}
	public MonistatQueryEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
	}
}
