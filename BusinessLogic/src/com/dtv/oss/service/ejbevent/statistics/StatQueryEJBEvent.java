/*
 * Created on 2006-2-14
 *
 * @author Stone Liang
 */
package com.dtv.oss.service.ejbevent.statistics;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class StatQueryEJBEvent extends QueryEJBEvent {

	//definition for stat query
	public static final int QUERY_TYPE_STAT_FEE		= 301;						//费用统计
	public static final int QUERY_TYPE_STAT_PAY		= 302;						//支付统计/预存统计
	public static final int QUERY_TYPE_STAT_SETOFF	= 303;						//销帐记录统计
	public static final int QUERY_TYPE_STAT_PREPAYDEDUCTION		= 305;			//预存抵扣统计
	public static final int QUERY_TYPE_STAT_ADJUSTACCOUNT		= 306;			//调帐统计
	public static final int QUERY_TYPE_STAT_BILL				= 307;			//帐单统计
	public static final int QUERY_TYPE_STAT_FORCEDEPOSIT_FUTURERIGHT_FOR_FINACE = 308; //帐务统计-押金和期权统计
	
	public static final int QUERY_TYPE_STAT_OPEN_CUSTOMER		= 401;			//开户情况统计
	public static final int QUERY_TYPE_STAT_OTHER_CSI			= 402;			//其他受理统计
	public static final int QUERY_TYPE_STAT_FAILED_INSTALLATION	= 403;			//安装失败统计
	public static final int QUERY_TYPE_STAT_BOOKING				= 404;			//预约受理统计
	public static final int QUERY_TYPE_STAT_CANCELED_BOOKING	= 405;			//取消预约统计
	public static final int QUERY_TYPE_STAT_APPLY_FOR_RAIR		= 406;			//报修受理统计
	public static final int QUERY_TYPE_STAT_BUSINESS_ACCT_ITEM  = 408;  	    //营业应收费统计
	public static final int QUERY_TYPE_STAT_BUSINESS_CASH 	    = 409;  		//营业现金统计
	public static final int QUERY_TYPE_STAT_CALL_BACK 	   		= 410;  		//开户回访统计
	public static final int QUERY_TYPE_STAT_BUSINESS_POINT_CASH = 411;  		//营业点收费统计
	
	public static final int QUERY_TYPE_STAT_CUSTOMER 				= 501;		//客户情况统计
	public static final int QUERY_TYPE_STAT_CUSTOMER_PRODUCT 		= 502;		//客户产品统计
	public static final int QUERY_TYPE_STAT_SALED_PRODUCT_PACKAGE 	= 504;		//已售产品包统计
	public static final int QUERY_TYPE_STAT_FAILED_BANK_ACCT_CHECK 	= 505;		//串配失败统计
	
	public static final int QUERY_TYPE_STAT_CUSTOMERSA 	        = 509;		        //订户数统计
	public static final int QUERY_TYPE_STAT_CUSTOMERMEAL 	    = 510;		        //客户套餐统计
	public static final int QUERY_TYPE_STAT_CUSTOMERCAMPAIGN 	= 511;		        //客户促销活动统计
	public static final int QUERY_TYPE_STAT_CUSTOMER_PRODUCT_PACKAGE = 512;		    //客户产品包统计
	
	public static final int QUERY_TYPE_STAT_ACCOUNT_INFO 		= 530;				//客户帐户统计
	public static final int QUERY_TYPE_STAT_ACCOUNT_DEPOSIT 	= 531;		        //客户预存统计
	public static final int QUERY_TYPE_STAT_ACCOUNT_OWE 		= 532;		        //客户帐户欠费统计
	public static final int QUERY_TYPE_STAT_ACCOUNT_BANK_MATCH 	= 533;		        //客户银行帐户匹配统计
	public static final int QUERY_TYPE_STAT_FORCEDEPOSIT_FUTURERIGHT = 534;         //押金和期权统计
	
	public static final int QUERY_TYPE_STAT_DEVICE_INFO     	= 540;              //设备统计
	public static final int QUERY_TYPE_STAT_DEVICE_STORE 	    = 541;   			//库存设备统计
	public static final int QUERY_TYPE_STAT_DEVICE_TRAN 	    = 542;  			//设备流转统计
	public static final int QUERY_TYPE_STAT_ORGANIZATION_INFO 	= 543;  			//组织机构统计
	public static final int QUERY_TYPE_STAT_OPERATOR_INFO 	    = 544;  			//操作员统计
	
	public static final int QUERY_TYPE_STAT_PRODUCTSELL     	= 601;              //产品销售统计
	public static final int QUERY_TYPE_STAT_PRODUCTPACKAGESELL 	= 602;   			//产品包销售统计
	public static final int QUERY_TYPE_STAT_MEAL 	            = 603;  			//套餐统计
	public static final int QUERY_TYPE_STAT_CAMPAIGNCATCH 	    = 604;  			//促销活动捕获统计
	
	public static final int QUERY_TYPE_BOOK_INTERACTION_SUM_STAT= 701;  			//预约受理量统计
	public static final int QUERY_TYPE_CUSTOMER_PROBLEM_SUM_STAT= 702;  			//报修量统计
	
	public static final int QUERY_TYPE_STAT_DEVICE_SWAP= 703;  						//设备更换统计
	public static final int QUERY_TYPE_STAT_DEVICE_SALES= 704;  					//设备销售统计
	
	public static final int QUERY_TYPE_STAT_DEVICE_DAY_SALES= 705;  				//机顶盒销售统计日报表
	
	public StatQueryEJBEvent() {
		super();
	}
	public StatQueryEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
	}
}
