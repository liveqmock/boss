/*
 * Created on 2006-2-14
 *
 * @author Stone Liang
 */
package com.dtv.oss.service.ejbevent.statistics;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class StatQueryEJBEvent extends QueryEJBEvent {

	//definition for stat query
	public static final int QUERY_TYPE_STAT_FEE		= 301;						//����ͳ��
	public static final int QUERY_TYPE_STAT_PAY		= 302;						//֧��ͳ��/Ԥ��ͳ��
	public static final int QUERY_TYPE_STAT_SETOFF	= 303;						//���ʼ�¼ͳ��
	public static final int QUERY_TYPE_STAT_PREPAYDEDUCTION		= 305;			//Ԥ��ֿ�ͳ��
	public static final int QUERY_TYPE_STAT_ADJUSTACCOUNT		= 306;			//����ͳ��
	public static final int QUERY_TYPE_STAT_BILL				= 307;			//�ʵ�ͳ��
	public static final int QUERY_TYPE_STAT_FORCEDEPOSIT_FUTURERIGHT_FOR_FINACE = 308; //����ͳ��-Ѻ�����Ȩͳ��
	
	public static final int QUERY_TYPE_STAT_OPEN_CUSTOMER		= 401;			//�������ͳ��
	public static final int QUERY_TYPE_STAT_OTHER_CSI			= 402;			//��������ͳ��
	public static final int QUERY_TYPE_STAT_FAILED_INSTALLATION	= 403;			//��װʧ��ͳ��
	public static final int QUERY_TYPE_STAT_BOOKING				= 404;			//ԤԼ����ͳ��
	public static final int QUERY_TYPE_STAT_CANCELED_BOOKING	= 405;			//ȡ��ԤԼͳ��
	public static final int QUERY_TYPE_STAT_APPLY_FOR_RAIR		= 406;			//��������ͳ��
	public static final int QUERY_TYPE_STAT_BUSINESS_ACCT_ITEM  = 408;  	    //ӪҵӦ�շ�ͳ��
	public static final int QUERY_TYPE_STAT_BUSINESS_CASH 	    = 409;  		//Ӫҵ�ֽ�ͳ��
	public static final int QUERY_TYPE_STAT_CALL_BACK 	   		= 410;  		//�����ط�ͳ��
	public static final int QUERY_TYPE_STAT_BUSINESS_POINT_CASH = 411;  		//Ӫҵ���շ�ͳ��
	
	public static final int QUERY_TYPE_STAT_CUSTOMER 				= 501;		//�ͻ����ͳ��
	public static final int QUERY_TYPE_STAT_CUSTOMER_PRODUCT 		= 502;		//�ͻ���Ʒͳ��
	public static final int QUERY_TYPE_STAT_SALED_PRODUCT_PACKAGE 	= 504;		//���۲�Ʒ��ͳ��
	public static final int QUERY_TYPE_STAT_FAILED_BANK_ACCT_CHECK 	= 505;		//����ʧ��ͳ��
	
	public static final int QUERY_TYPE_STAT_CUSTOMERSA 	        = 509;		        //������ͳ��
	public static final int QUERY_TYPE_STAT_CUSTOMERMEAL 	    = 510;		        //�ͻ��ײ�ͳ��
	public static final int QUERY_TYPE_STAT_CUSTOMERCAMPAIGN 	= 511;		        //�ͻ������ͳ��
	public static final int QUERY_TYPE_STAT_CUSTOMER_PRODUCT_PACKAGE = 512;		    //�ͻ���Ʒ��ͳ��
	
	public static final int QUERY_TYPE_STAT_ACCOUNT_INFO 		= 530;				//�ͻ��ʻ�ͳ��
	public static final int QUERY_TYPE_STAT_ACCOUNT_DEPOSIT 	= 531;		        //�ͻ�Ԥ��ͳ��
	public static final int QUERY_TYPE_STAT_ACCOUNT_OWE 		= 532;		        //�ͻ��ʻ�Ƿ��ͳ��
	public static final int QUERY_TYPE_STAT_ACCOUNT_BANK_MATCH 	= 533;		        //�ͻ������ʻ�ƥ��ͳ��
	public static final int QUERY_TYPE_STAT_FORCEDEPOSIT_FUTURERIGHT = 534;         //Ѻ�����Ȩͳ��
	
	public static final int QUERY_TYPE_STAT_DEVICE_INFO     	= 540;              //�豸ͳ��
	public static final int QUERY_TYPE_STAT_DEVICE_STORE 	    = 541;   			//����豸ͳ��
	public static final int QUERY_TYPE_STAT_DEVICE_TRAN 	    = 542;  			//�豸��תͳ��
	public static final int QUERY_TYPE_STAT_ORGANIZATION_INFO 	= 543;  			//��֯����ͳ��
	public static final int QUERY_TYPE_STAT_OPERATOR_INFO 	    = 544;  			//����Աͳ��
	
	public static final int QUERY_TYPE_STAT_PRODUCTSELL     	= 601;              //��Ʒ����ͳ��
	public static final int QUERY_TYPE_STAT_PRODUCTPACKAGESELL 	= 602;   			//��Ʒ������ͳ��
	public static final int QUERY_TYPE_STAT_MEAL 	            = 603;  			//�ײ�ͳ��
	public static final int QUERY_TYPE_STAT_CAMPAIGNCATCH 	    = 604;  			//���������ͳ��
	
	public static final int QUERY_TYPE_BOOK_INTERACTION_SUM_STAT= 701;  			//ԤԼ������ͳ��
	public static final int QUERY_TYPE_CUSTOMER_PROBLEM_SUM_STAT= 702;  			//������ͳ��
	
	public static final int QUERY_TYPE_STAT_DEVICE_SWAP= 703;  						//�豸����ͳ��
	public static final int QUERY_TYPE_STAT_DEVICE_SALES= 704;  					//�豸����ͳ��
	
	public static final int QUERY_TYPE_STAT_DEVICE_DAY_SALES= 705;  				//����������ͳ���ձ���
	
	public StatQueryEJBEvent() {
		super();
	}
	public StatQueryEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
	}
}
