package com.dtv.oss.service.commandresponse;

/**  This class is used to define error code in BOSS system
	 should be deployed in WEB layer and in EJB layer both
 */
public final class ErrorCode {
    //System level error: -50 ~ -1
    public static final int NETWORK_ERROR = -1;
    public static final int ENTITYBEAN_CANNT_CREATE = -2;
    public static final int ENTITYBEAN_CANNT_REMOVE = -3;
    public static final int ENTITYBEAN_CANNT_UPDATE = -4;
    public static final int SYSTEM_ERROR = -5; //such as out of memory, etc

    //Configuration error: -51 ~ -199
    public static final int HOMEFAC_CANNT_LOOKUP = -51;
    public static final int ENTITYBEAN_HOMEINTERFACE_CANNTFIND = -52;

    //Applicaton error: -200 ~
    public static final int APP_GENERAL_ERROR 						= -200;
    public static final int APP_NOENOUGHDEVICE 						= -201;
    public static final int APP_FINDERROR      						= -202;
    public static final int APP_ILL_PARAMETER 						= -203;
    public static final int APP_ERROR_PACKAGEITEMRELATION_PRODUCT  	= -204;
    public static final int APP_DEVICESWOP_ERROR 					= -205;
    public static final int APP_NOTENOUGHMONEY_FORBILL 				= -206;
    public static final int APP_ENTITYBENA_UPDATEDBYOTHER 			= -207;
    public static final int APP_NULL_POINTER 						= -208;
    public static final int APP_QUERY_PARA_ERROR 					= -209;
    public static final int APP_QUERY_LIST_ERROR 					= -210;
    
 	public static final int APP_CUSTOMER_STATUS_ISNOT_VALID		 		= -301;
    public static final int APP_CUSTOMER_LOGINID_ISNOT_DISTINCT 		= -302;
	public static final int APP_CUSTOMER_HAS_VALID_ACCOUNT		 		= -303;
	//没有输入客户号
	public static final int APP_CUSTOMER_ISNOT_VALID			 		= -304;
	
    public static final int APP_PRODUCT_ACCOUNT_ISNOT_VALID 			= -350;
    public static final int APP_PRODUCT_REFERPRODUCT_NOTEXIST 			= -351;
    public static final int APP_PRODUCT_SERVICEACCOUNT_NOTEXIST 		= -352;
	public static final int APP_PRODUCT_STATUS_ISNOT_VALID		 		= -353;
	public static final int APP_PRODUCT_HAS_VALID_REFERPRODUCT			= -354;
	public static final int APP_PRODUCT_REFERPRODUCT_STATUS_ISNOT_VALID	= -355;
	//在进行产品active，pause，resume操作时，如果是硬件产品，不允许进行该操作
	public static final int APP_PRODUCT_HARDWARE_CANNOTDO_THISOPERATION = -356;
	//同样产品不能在同一个业务帐户上出现,
	//在购买产品时，如果使用的业务帐户已经存在该产品，则购买失败
	public static final int APP_PRODUCT_SERVICEACCOUNT_OWN_THISPRODUCT  = -357;
	public static final int APP_PRODUCT_INVALID_RECEIVER_ACCOUNTSTATUS	= -358;
	public static final int APP_PRODUCT_INVALID_DEVICEID				= -359;
	public static final int APP_PRODUCT_CANNOTTRANSFER_TOSELF			= -360;
	public static final int APP_PRODUCT_HAVE_DUPLICATEPRODUCT			= -361;
	public static final int APP_PRODUCT_MUSTBUY_INOPENACCOUNT			= -362;
	public static final int APP_PRODUCT_NOTBELONGTO_ONESERVICE			= -363;
	public static final int APP_PRODUCT_NEWPRODUCT_NOTBELONG_THISSERVICEACCT			= -364;
	public static final int APP_PRODUCT_CANNOT_SWAP_WITH_ONESELF			= -365;
	public static final int APP_PRODUCT_DEVICE_STATUS_ISNOT_VALID		= -366;
	public static final int APP_PRODUCT_CANNOT_DO_THISOPERATION			= -367;
	public static final int APP_PRODUCT_DESTPRODUCT_ISNOT_VALID			= -368;
	public static final int APP_PRODUCT_UNMATCHED_DEVICEMODEL_WITHPACKAGE	= -369;
	public static final int APP_PRODUCT_UNMATCHED_DEVICEMODEL				= -370;
	
	public static final int APP_ACCOUNT_CUSTSTATUS_ISNOT_VALID	 	= -400;
	public static final int APP_ACCOUNT_STATUS_ISNOT_VALID	 		= -401;
	public static final int APP_SERVICEACCOUNT_STATUS_ISNOT_VALID	= -402;
	//销户或者预付费时存在未付清的帐单
	public static final int APP_ACCOUNT_WITH_UNPAID_INVOICE	 		= -403;
   
    public static final int APP_INVOICE_STATUS_ISNOT_VALID	 		= -450;
    public static final int APP_INVOICE_ISNOT_VALID	 				= -451;
    public static final int APP_INVOICE_AMOUNT_ISNOT_VALID	 		= -452;
    
    //customer contact
    public static final int APP_JOBCARD_STATUS_ISNOT_VALID	 		= -500;
    public static final int APP_PROBLEM_INVALID_ORGIDTRANSITION		= -501;
    public static final int APP_JOBCARD_REFERID_NOTEXIST			= -502;
    public static final int APP_PROBLEM_STATUS_ISNOT_VALID			= -503;
    
    //for operator login
    public static final int APP_OPERATOR_NOT_EXIST 					= -211;
    public static final int APP_OPERATOR_PASSWORD_ERROR 			= -212;
    public static final int APP_OPERATOR_ISNOT_VALID				= -213;
    public static final int APP_OPERATOR_STATUS_ISNOT_VALID			= -214;
    
    //error code definition for OSS
    public static final int APP_OSS_CATV_WITHOUT_CUSTOMER			= -1101;
    public static final int APP_OSS_REFERSHEETID_ISNOT_UNIQUE		= -1102;
    public static final int APP_STATUS_ISNOT_VALID					= -1103;
    public static final int APP_CATVTERMINALSTATUS_ISNOT_VALID		= -1104;
    public static final int APP_CATVTERMINALADDRESS_ISNOT_VALID		= -1105;
    public static final int APP_BATCHNO_ISNOT_UNIQUE				= -1106;
    
    //error code definition for OSS
	public static final int APP_OSS_INVALID_GROUPBARGAIGN				= -1107;
	public static final int APP_OSS_CAMPAIGNCONDITION_DOESNOT_MATCH				= -1108;
	public static final int APP_OSS_FEEANDPAYMENT_DOESNOT_MATCH				= -1109;
	public static final int APP_OSS_CSI_STATUS_ISNOT_VALID				= -1110;
	public static final int APP_OSS_OPERATOR_DOESNOT_MATHC				= -1111;
	public static final int APP_OSS_CSI_PAYMENTSTATUS_ISNOT_VALID				= -1112;
	
	public static final int APP_OSS_CAMPAIGN_DOESNOT_ALLOW_TRANSITION				= -1113;
	public static final int APP_OSS_CAMPAIGN_DOESNOT_ALLOW_TRANSFER				= -1114;
    
}