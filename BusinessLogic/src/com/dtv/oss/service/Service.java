/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service;

/**
 * @author Leon Liu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface Service {
    // 购买产品包可以支持的service 数定义
    public static final int NOSERVICE = 0;
    public static final int SINGLESERVICE = 1;
    public static final int MULTISERVICE = 2;
    //用来存放boolean型的对象,表示某次的受理费用已经支付,受理单的付费状态应该变更掉
    public static final String CSI_MUST_PAYFEE="com.dtv.oss.service.component.FeeService.payFeeAction";
    
    //公用的键值定义
    //动作定义
    public static final String ACTION_DEFITION = "com.dtv.oss.service.actionDefition";
    //动作描述
    public static final String ACTION_DESCRTIPION = "com.dtv.oss.service.actionDescription";
    //操作员ID
    public static final String OPERATIOR_ID= "com.dtv.oss.service.operatorID";
    //开户类型
    public static final String OPEN_CUSTOMER_TYPE= "com.dtv.oss.service.openCustomerID";
    //访问服务器的远程客户地址
    public static final String REMOTE_HOST_ADDRESS= "com.dtv.oss.service.remoteHostAddress";
    //开户时的团购券编号
    public static final String GROUP_BARGAIN_DETAIL_NO= "com.dtv.oss.service.groupBargainDetailNo";
    
    //上下文对象ServiceContext 中存储的对象的key 定义
    //受理单类型的KEY
    public static final String CSI_TYPE = "com.dtv.oss.service.csitype";
    //受理单安装类型
    public static final String CSI_INSTALLATION_TYPE="com.dtv.oss.service.installationtype";
    //是否是代理商受理单
    public static final String IS_AGENT = "com.dtv.oss.service.isAgent";
    //是否是创建工单的key
    public static final String IS_CREATE_JOBCARD= "com.dtv.oss.service.isCreateJobCard";
    //账户ejb对象的KEY
	public static final String ACCOUNT = "com.dtv.oss.domain.Account";
	//账户id的KEY
	public static final String ACCOUNT_ID = "com.dtv.oss.service.AccountID";
	//客户ejb对象的KEY
	public static final String CUSTOMER = "com.dtv.oss.domain.Customer";
	//客户id的KEY
	public static final String CUSTOMER_ID ="com.dtv.oss.service.CustomerID";
   //客户当前可用积分的KEY
	public static final String CURRENT_POINTS ="com.dtv.oss.service.currentPoints";
	//客户历史总积分的KEY
	public static final String TOTAL_POINTS ="com.dtv.oss.service.totalPoints";
  //兑换货物的id KEY
	public static final String GOOD_ID ="com.dtv.oss.service.goodId";
	
//	兑换货物的活动id KEY
	public static final String ACTIVITY_ID ="com.dtv.oss.service.activityId";
	//客户类型, add by zhouxushun ,date 2005-10-25
	public static final String CUSTOMER_TYPE="com.dtv.oss.service.CustomerType";
	//districtid ， add by zhouxushun ,date 2005-10-25
	public static final String CUSTOMER_DISTRICT_ID="com.dtv.oss.service.CustomerDistrictID";
	//客户地址ID
	public static final String CUSTOMER_ADDRESS_ID="com.dtv.oss.service.CustomerAddressID";
	//客户帐单地址ID
	public static final String CA_ADDRESS_ID="com.dtv.oss.service.CAADDRESSID";
	//设备序列号与其对应的设备类型
	public static final String DEVICESERIALNO_DEVICECLASS_MAP="com.dtv.oss.service.DeviceSerialAndDeviceClass";
	//他受理单ejb对象的KEY
	public static final String CSI = "com.dtv.oss.domain.CustServiceInteraction";
	//受理单dto对象的KEY
	public static final String CSI_DTO = "com.dtv.oss.dto.CustServiceInteractionDTO";
	//受理单id的KEY
	public static final String CSI_ID = "com.dtv.oss.service.CSIID";
	//新用户信息EJB对象的KEY
	public static final String NEW_CUSTOMER_INFO_EJB = "com.dtv.oss.domain.NewCustomerInfo";
	//新用户信息DTO对象的KEY
	public static final String NEW_CUSTOMER_INFO_DTO = "com.dtv.oss.dto.NewCustomerInfoDTO";
	//新用户帐户信息EJB对象的KEY
	public static final String NEW_CUST_ACCOUNT_INFO_EJB = "com.dtv.oss.domain.NewCustAccountInfo";
	//新用户帐户信息DTO对象的KEY
	public static final String NEW_CUST_ACCOUNT_INFO_DTO = "com.dtv.oss.dto.NewCustAccountInfoDTO";
	//新用户产品信息对象的KEY
	public static final String CSI_PRODUCT_INFO_LIST = "com.dtv.oss.service.CSICustProductInfo_List";
	//业务账户EJB对象的KEY
	public static final String SERVICE_ACCOUNT_EJB = "com.dtv.oss.domain.ServiceAccount";
	//业务账户id的KEY
	public static final String SERVICE_ACCOUNT_ID = "com.dtv.oss.service.ServiceAccountID";
	//客户产品EJB对象的KEY
	public static final String CUSTOMER_PRODUCT = "com.dtv.oss.domain.CustomerProduct";
	//客户产品ID的KEY
	public static final String PSID = "com.dtv.oss.service.CustomerProductID";
    //BatchJob EJB对象的KEY
	public static final String BATCH_JOB = "com.dtv.oss.domain.BatchJob";
	//系统事件EJB对象的KEY
	public static final String SYSTEM_EVENT = "com.dtv.oss.domain.SystemEvent";
	//系统日志EJB对象的KEY
	public static final String SYSTEM_LOG = "com.dtv.oss.domain.SystemLog";
	//客户地址EJB对象的KEY
	public static final String CUSTOMER_ADDRESS_EJB = "com.dtv.oss.domain.Address_Customer";
	//客户地址DTO对象的KEY
	public static final String CUSTOMER_ADDRESS_DTO = "com.dtv.oss.dto.Address_Customer_DTO";
	//账户地址EJB对象的KEY
	public static final String ACCOUNT_ADDRESS_EJB= "com.dtv.oss.domain.Address_Account";
	//账户地址EJB对象的KEY
	public static final String ACCOUNT_ADDRESS_DTO= "com.dtv.oss.dto.Address_Account_DTO";
	//客户优惠列表对象的KEY
	public static final String CUSTOMER_CAMPAIGN_LIST = "com.dtv.oss.dto.CustomerCampaign_List";
	//客户产品列表对象的KEY
	public static final String CUSTOMER_PRODUCT_LIST = "com.dtv.oss.service.CustomerProduct_List";
	//团购明细DTO对象的KEY
	public static final String GROUP_BARGAIN_DETAIL_DTO = "com.dtv.oss.dto.GroupBargainDetailDTO";
	//团购明细EJB对象的KEY
	public static final String GROUP_BARGAIN_DETAIL_EJB="com.dtv.oss.domain.GroupBargainDetail";
	//新客户客户市场信息列表对象的key
	public static final String NEW_CUST_MARKET_INFO_COLLECTION="com.dtv.oss.dto.NewCustomerMarketInfo_List";
	//客户产品包id集合对象
	public static final String PRODUCT_PACKAGE_ID_LIST="com.dtv.oss.dto.ProductPackage_ID_List";
	//客户选择的优惠的id的集合对象
	public static final String CAMPAIGN_CAMPAIGNID_LIST="com.dtv.oss.dto.Campaign_ID_List";
	//客户硬件产品所对应的硬件产品和设备ID的信息列表
	public static final String PRODUCT_MATCH_DEVICE_PRODUCT_LIST="com.dtv.oss.service.productMatchDeviceProductLit";
	//操作结果
	public static final String PROCESS_RESULT="com.dtv.oss.service.process_Result";
	
	//产品ID号
	public static final String PRODUCT_ID="com.dtv.oss.service.product_ID";
	
	public static final String PRODUCT_PACKAGE_ID="com.dtv.oss.service.product_package_ID";
	
	public static final String BRCONDITION_ID="com.dtv.oss.service.brcnt_ID";
	public static final String FEELIST="com.dtv.oss.service.feelist";
	
	public static final String PAYMENTLIST="com.dtv.oss.service.paymentList";
	public static final String TATOLFEE="com.dtv.oss.service.totalFee";
	
	public static final String TATOLPAYMENT="com.dtv.oss.service.totalPayment";
	public static final String NEWSERVICEACCOUNTIDLIST="com.dtv.oss.service.newServiceAccountIDList";
	public static final String CANCELSERVICEACCOUNTIDLIST="com.dtv.oss.service.cancelServiceAccountIDList";
	
	public static final String OPEN_CATV_NEW_CATVID="com.dtv.oss.BookCommand.new_catvid";
	
	public static final String CAWALLETSERIALNOSWAP="com.dtv.oss.service.component.CustomerProductService.updateCAValletDependDevice";
}
