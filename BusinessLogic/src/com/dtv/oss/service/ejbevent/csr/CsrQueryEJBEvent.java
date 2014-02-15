/*
 * Created on 2004-8-9
 *
 * @author Mac Wang
 */
package com.dtv.oss.service.ejbevent.csr;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;


public class CsrQueryEJBEvent extends QueryEJBEvent {
	//Now, all csr query use the same CsrQueryEJBEvent
	//and you can distinguish them with the following constant
	public static final int QUERY_TYPE_CUSTOMER 		= 0;
	public static final int QUERY_TYPE_ONECUSTOMER 		= 20;
	public static final int QUERY_TYPE_ACCOUNT			= 1;
	//�ͻ�����
	public static final int QUERY_TYPE_CUSTPROBLEM 		= 2;
	//�ͻ�Ͷ��
	public static final int QUERY_TYPE_CUSTCOMPLAIN		= 3;
	//���ſͻ��ӿͻ���ѯ
	public static final int QUERY_GROUP_CUST		= 22;
	//����
	public static final int QUERY_TYPE_JOBCARD	 		= 4;
	//Customer Products
	public static final int QUERY_TYPE_CUSTPRODUCT 		= 5;
	//Customer Invoice  
    
	public static final int QUERY_TYPE_CUSTINVOICE 		= 6;
	public static final int QUERY_TYPE_FINANCIALTRANS 	= 7;
	public static final int QUERY_TYPE_TERMINALDEVICE 	= 8;
	public static final int QUERY_TYPE_RESIDENCEINFO 	= 9;
	public static final int QUERY_TYPE_CATV			 	= 10;
	public static final int QUERY_TYPE_CUSTOMERMATCHING	= 11;
	public static final int QUERY_TYPE_CUSTOMERPROBLEMTOPROCESS				= 12;
	public static final int QUERY_TYPE_CUSTOMERPROBLEMWITHRETURNEDJOBCARD	= 13;
	public static final int QUERY_TYPE_JOBCARDTOPROCESS	= 14;
	public static final int QUERY_TYPE_CUSTOMERPROBLEMWITHFAILEDJOBCARD		= 15;
	public static final int QUERY_TYPE_SERVICE_ACCOUNT		= 16;
	public static final int QUERY_TYPE_SERVICE_ACCOUNT_BY_CUST		= 17;
	public static final int QUERY_TYPE_CUSTOMER_PHONENO		= 18;
	public static final int QUERY_SERVICEACCOUNT_TERMINALDEVICE = 19;
	public static final int QUERY_CUSTOMER_BILLING_RULE = 21;  
	public static final int QUERY_TYPE_SERVICE_ACCOUNT_FOR_SCHEDULE = 23;
	//query for scn
	//return FullJobCard_Wrap collection
	public static final int QUERY_TYPE_FULLJOBCARD	 		= 50;
	public static final int QUERY_TYPE_CATVStatusProc		= 51;
	public static final int QUERY_TYPE_CATVTermProc	 		= 52;
	public static final int QUERY_TYPE_UnregInCustomer 		= 53;
	public static final int QUERY_TYPE_TRANSCUSTINFO 		= 54;
	public static final int QUERY_TYPE_FULLTRANSCUSTINFO	= 55;
	public static final int QUERY_TYPE_FULLCATV			 	= 56;
	public static final int QUERY_TYPE_CUSTCATVTERMINALMap 	= 57;
	public static final int QUERY_TYPE_CATVTermProcItem	 			= 58;
	public static final int QUERY_TYPE_JOBCARD2CSI	 				= 59;
	public static final int QUERY_TYPE_STREETSTATIONCFG	 			= 60;
	public static final int QUERY_TYPE_CONSTRUCTIONBATCH	 		= 61;
	public static final int QUERY_TYPE_CONSTRUCTIONBATCHITEM		= 62;
	public static final int QUERY_TYPE_JOBCARD2CATV	 				= 63;
	public static final int QUERY_TYPE_DUALCATV					 	= 64;
	public static final int QUERY_TYPE_CUSTSERVICEINTERACTION_STATIS= 65;
	public static final int QUERY_TYPE_CATVTERMINAL_STAT			= 66;
	public static final int QUERY_TYPE_CATVStatusProc2Catv			= 67;
	public static final int QUERY_TYPE_CATVTERMPROC2CATV	 		= 68;
	public static final int QUERY_TYPE_CATVTERMPROCITEM2CATV 		= 69;
	public static final int QUERY_TYPE_CUSTCATVTERMINALMAPSTAT		= 70;
	public static final int QUERY_TYPE_CATVWITHDESTROYSTATUS		= 71;
	public static final int QUERY_TYPE_INTERACTIONFEEITEM			= 72;
	public static final int QUERY_TYPE_ZONESTATIONCFG				= 73;
	public static final int QUERY_TYPE_INVOICESTAT					= 74;
	public static final int QUERY_TYPE_INTERACTIONFEEITEMSTAT		= 75;
	public static final int QUERY_TYPE_JOBCARDSTAT					= 76;
	public static final int QUERY_TYPE_CONSTRUCTIONBATCHWITHNEWITEM	= 77;
	public static final int QUERY_TYPE_OPERATOR						= 78;
	public static final int QUERY_TYPE_CATVTERMPROCWITHNEWITEM		= 79;
	public static final int QUERY_TYPE_MULTICATVSTATUSPROC			= 80;
	public static final int QUERY_TYPE_JOBCARD2TRANSCUSTINFO		= 81;
	public static final int QUERY_TYPE_CONSTRUCTIONBATCHSTAT		= 82;
	public static final int QUERY_TYPE_FULLTRANSCUSTINFO_FROMORG	= 83;
	public static final int QUERY_TYPE_TERMINALMANAGESTAT			= 84;
	public static final int QUERY_TYPE_PHONENO			= 85;
	
	public static final int QUERY_TYPE_CAMPAIGN						= 101;
	public static final int QUERY_TYPE_PRODUCTPACKAGE				= 102;
	public static final int QUERY_TYPE_PRODUCT						= 103;
	public static final int QUERY_TYPE_CUSTSERVICEINTERACTION		 = 104;
	public static final int QUERY_TYPE_SIMPLECUSTSERVICEINTERACTION		 = 105;
	public static final int QUERY_TYPE_CSI2CUSTOMER2ACCOUNT			 = 106;
	public static final int QUERY_TYPE_CUSTOMER_AND_ACCOUNT			 = 107;
	
	public static final int QUERY_TYPE_PAYMENT_RECORD			 			= 109;

	public static final int QUERY_TYPE_CSI_PAYMENT				= 110;
	public static final int QUERY_TYPE_CSI_FEE						= 111;
	public static final int QUERY_FT_CUSTACCOUNT 		= 112;
	public static final int QUERY_INVOICE_FT_CUSTACCOUNT 		= 113;
	public static final int QUERY_FT_DETAIL_CUSTACCOUNT 		= 114;
	public static final int QUERY_INVOICE_FT_DETAIL_CUSTACCOUNT 		= 115;
	public static final int QUERY_TYPE_STOP_CUSTPRODUCT =116;
    public static final int QUERY_TYPE_CUST_ACC_ADDR = 117;
    
    public static final int QUERY_TYPE_CUSTACCOUNTITEM_RECORD   =126;
    
    public static final int QUERY_TYPE_CUST_CAMPAIGN = 118;        
    public static final int QUERY_TYPE_FORCED_DEPOSIT = 120;
    public static final int QUERY_TYPE_CP_PAYMENT				= 121;
	public static final int QUERY_TYPE_CP_FEE						= 122;
    public static final int QUERY_VALID_DATE_CHANGE_LIST = 123;  //�ͻ�������Ϣ��ʷ��¼
    public static final int QUERY_CUST_ADDITIONAL_INFO = 124;
    public static final int QUERY_AGENT_CSI = 125;            //������������ѯ
    public static final int QUERY_TYPE_CSI_PROCESS_LOG = 127;
    public static final int QUERY_BOOK_PRODUCT_CUSTSERVICEINTERACTION = 128;	//�����ͻ���Ʒ������ѯ

	//�ò�ѯϣ������ĳ���ض��ͻ����е�������Ż�
	public static final int QUERY_TYPE_CUSTOMER_BILLING_RULE = 201;
	//�ɹ���Ĳ�Ʒ(ֻ�������Ʒ����ǰ��Ч��
	public static final int QUERY_TYPE_CAN_PURCHASE_PRODUCTPACKAGE				= 202;
	//�ɹ���Ĳ�Ʒ(Ӳ�����������ǰʱ�䷶Χ�ڣ�
	public static final int QUERY_TYPE_CURRENT_PACKAGE			= 204;
	
	public static final int QUERY_TYPE_CSI2CUSTOMER2ACCOUNT2JOBCARD		 = 203;

    public static final int QUERY_TYPE_BACKGROUNDJOB = 205;
    
    public static final int QUERY_CUSTOMER_BILLINGRULE = 206;
    
    public static final int QUERY_TYPE_CUSTOMER_GROUPBARGAIN_END = 300;
    
    public static final int QUERY_TYPE_CAMPAIGN_OTHER = 301;
 
    //���ֻ
    public static final int QUERY_POINTS_ACTIVITY = 305;
    
    public static final int QUERY_POINTS_GOODS = 303;

    public static final int QUERY_POINTS_RECORD = 304;
    
    public static final int QUERY_TYPE_PREPAYMENTDEDUCTION =302;

    
    

    //add by zhouxushun , date :2006-2-9  ��������ѯ  401 ~~ 450
    //���ʼ�¼��ѯ
    public static final int QUERY_ADJUST_ACCOUNT = 401;
    //���Ѽ�¼��ѯ
    public static final int QUERY_PAMENTT_RECORD = 402;
   //���ü�¼��ѯ
    public static final int QUERY_FEE_RECORD = 403;
    //���ʼ�¼��ѯ
    public static final int QUERY_HAND_DEBT_RECORD = 404;
    //���ʼ�¼��ѯ
    public static final int QUERY_BAD_DEBT_RECORD = 405;
    //Ԥ��ֿۼ�¼��ѯ
    public static final int QUERY_PREPAYMENT_DEDUCTION_RECORD=406;
    //���˼�¼��ѯ
    public static final int QUERY_SETOFF_RECORD=407;
    // end 
    
//    Products
	public static final int QUERY_PRODUCT 		= 408;
	
	public static final int QUERY_COMPLAIN_PROCESS =409;
	
	public static final int QUERY_TYPE_CUSTSERVICEINTERACTION_ONLY=410;
	
	public static final int QUERY_BATCH_INVOICE = 411;
	public static final int QUERY_CAMPAIGN_LIST = 412;
	
	public static final int QUERY_TYPE_CatvTerminal = 413;
	
	public static final int QUERY_TYPE_FiberNode = 414;
	
	public static final int QUERY_TYPE_JOBCARD_CATV = 415;  //ģ�⹤����ѯ
	
	public static final int QUERY_TYPE_CA_WALLET_CHARGE = 7023;
	
	public static final int QUERY_TYPE_CA_WALLET = 7024;
	
	public static final int QUERY_TYPE_CA_WALLET_Service_Interaction = 7025;
	
	public static final int QUERY_TYPE_SIMPLECUSTSERVICEINTERACTIONVIEW = 7026;
	
	public static final int QUERY_CASH_FLOW_COUNT=418  ; //�ֽ�����ϸ����ϼ�
	
	public static final int QUERY_OWE_SUSPEND_CUSTOMER =419; //ͣ��-Ƿ�Ѳ�ѯ
	
    public static final int QUERY_BATCH_PREPAY_ACCOUNT =420; //����Ԥ���ѯ
	
	public static final int QUERY_MERGE_BATCH_INVOICE =421; //�ʵ��ϲ�������ѯ
	
	
	
	public CsrQueryEJBEvent() {
		super();
	}
	public CsrQueryEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
	}

}
