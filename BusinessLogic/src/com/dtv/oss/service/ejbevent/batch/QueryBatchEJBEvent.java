package com.dtv.oss.service.ejbevent.batch;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class QueryBatchEJBEvent extends QueryEJBEvent {
	  
	//�����������Ҽ�¼��
	public static final int QUERY_TYPE_BATCH_QUERY=11;
	//�������������¼��
	public static final int QUERY_TYPE_BATCH_RESULT_ITEM=12;
	
	//������������ͷ��¼
	public static final int QUERY_TYPE_BANK_DEDUCTION_HEADER=21;
	//��������������ϸ��¼
	public static final int QUERY_TYPE_BANK_DEDUCTION_DETAIL=22;
	//��������������Ϣ
	public static final int QUERY_TYPE_BATCH_SEND_MESSAGE=28;
	//ģ�⽨���û���ѯ
	public static final int QUERY_FOUNDCUSTOMER_DETAIL =23;
	//��ת�û���ѯ
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
