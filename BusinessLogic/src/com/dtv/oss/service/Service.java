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
    // �����Ʒ������֧�ֵ�service ������
    public static final int NOSERVICE = 0;
    public static final int SINGLESERVICE = 1;
    public static final int MULTISERVICE = 2;
    //�������boolean�͵Ķ���,��ʾĳ�ε���������Ѿ�֧��,�����ĸ���״̬Ӧ�ñ����
    public static final String CSI_MUST_PAYFEE="com.dtv.oss.service.component.FeeService.payFeeAction";
    
    //���õļ�ֵ����
    //��������
    public static final String ACTION_DEFITION = "com.dtv.oss.service.actionDefition";
    //��������
    public static final String ACTION_DESCRTIPION = "com.dtv.oss.service.actionDescription";
    //����ԱID
    public static final String OPERATIOR_ID= "com.dtv.oss.service.operatorID";
    //��������
    public static final String OPEN_CUSTOMER_TYPE= "com.dtv.oss.service.openCustomerID";
    //���ʷ�������Զ�̿ͻ���ַ
    public static final String REMOTE_HOST_ADDRESS= "com.dtv.oss.service.remoteHostAddress";
    //����ʱ���Ź�ȯ���
    public static final String GROUP_BARGAIN_DETAIL_NO= "com.dtv.oss.service.groupBargainDetailNo";
    
    //�����Ķ���ServiceContext �д洢�Ķ����key ����
    //�������͵�KEY
    public static final String CSI_TYPE = "com.dtv.oss.service.csitype";
    //������װ����
    public static final String CSI_INSTALLATION_TYPE="com.dtv.oss.service.installationtype";
    //�Ƿ��Ǵ���������
    public static final String IS_AGENT = "com.dtv.oss.service.isAgent";
    //�Ƿ��Ǵ���������key
    public static final String IS_CREATE_JOBCARD= "com.dtv.oss.service.isCreateJobCard";
    //�˻�ejb�����KEY
	public static final String ACCOUNT = "com.dtv.oss.domain.Account";
	//�˻�id��KEY
	public static final String ACCOUNT_ID = "com.dtv.oss.service.AccountID";
	//�ͻ�ejb�����KEY
	public static final String CUSTOMER = "com.dtv.oss.domain.Customer";
	//�ͻ�id��KEY
	public static final String CUSTOMER_ID ="com.dtv.oss.service.CustomerID";
   //�ͻ���ǰ���û��ֵ�KEY
	public static final String CURRENT_POINTS ="com.dtv.oss.service.currentPoints";
	//�ͻ���ʷ�ܻ��ֵ�KEY
	public static final String TOTAL_POINTS ="com.dtv.oss.service.totalPoints";
  //�һ������id KEY
	public static final String GOOD_ID ="com.dtv.oss.service.goodId";
	
//	�һ�����Ļid KEY
	public static final String ACTIVITY_ID ="com.dtv.oss.service.activityId";
	//�ͻ�����, add by zhouxushun ,date 2005-10-25
	public static final String CUSTOMER_TYPE="com.dtv.oss.service.CustomerType";
	//districtid �� add by zhouxushun ,date 2005-10-25
	public static final String CUSTOMER_DISTRICT_ID="com.dtv.oss.service.CustomerDistrictID";
	//�ͻ���ַID
	public static final String CUSTOMER_ADDRESS_ID="com.dtv.oss.service.CustomerAddressID";
	//�ͻ��ʵ���ַID
	public static final String CA_ADDRESS_ID="com.dtv.oss.service.CAADDRESSID";
	//�豸���к������Ӧ���豸����
	public static final String DEVICESERIALNO_DEVICECLASS_MAP="com.dtv.oss.service.DeviceSerialAndDeviceClass";
	//������ejb�����KEY
	public static final String CSI = "com.dtv.oss.domain.CustServiceInteraction";
	//����dto�����KEY
	public static final String CSI_DTO = "com.dtv.oss.dto.CustServiceInteractionDTO";
	//����id��KEY
	public static final String CSI_ID = "com.dtv.oss.service.CSIID";
	//���û���ϢEJB�����KEY
	public static final String NEW_CUSTOMER_INFO_EJB = "com.dtv.oss.domain.NewCustomerInfo";
	//���û���ϢDTO�����KEY
	public static final String NEW_CUSTOMER_INFO_DTO = "com.dtv.oss.dto.NewCustomerInfoDTO";
	//���û��ʻ���ϢEJB�����KEY
	public static final String NEW_CUST_ACCOUNT_INFO_EJB = "com.dtv.oss.domain.NewCustAccountInfo";
	//���û��ʻ���ϢDTO�����KEY
	public static final String NEW_CUST_ACCOUNT_INFO_DTO = "com.dtv.oss.dto.NewCustAccountInfoDTO";
	//���û���Ʒ��Ϣ�����KEY
	public static final String CSI_PRODUCT_INFO_LIST = "com.dtv.oss.service.CSICustProductInfo_List";
	//ҵ���˻�EJB�����KEY
	public static final String SERVICE_ACCOUNT_EJB = "com.dtv.oss.domain.ServiceAccount";
	//ҵ���˻�id��KEY
	public static final String SERVICE_ACCOUNT_ID = "com.dtv.oss.service.ServiceAccountID";
	//�ͻ���ƷEJB�����KEY
	public static final String CUSTOMER_PRODUCT = "com.dtv.oss.domain.CustomerProduct";
	//�ͻ���ƷID��KEY
	public static final String PSID = "com.dtv.oss.service.CustomerProductID";
    //BatchJob EJB�����KEY
	public static final String BATCH_JOB = "com.dtv.oss.domain.BatchJob";
	//ϵͳ�¼�EJB�����KEY
	public static final String SYSTEM_EVENT = "com.dtv.oss.domain.SystemEvent";
	//ϵͳ��־EJB�����KEY
	public static final String SYSTEM_LOG = "com.dtv.oss.domain.SystemLog";
	//�ͻ���ַEJB�����KEY
	public static final String CUSTOMER_ADDRESS_EJB = "com.dtv.oss.domain.Address_Customer";
	//�ͻ���ַDTO�����KEY
	public static final String CUSTOMER_ADDRESS_DTO = "com.dtv.oss.dto.Address_Customer_DTO";
	//�˻���ַEJB�����KEY
	public static final String ACCOUNT_ADDRESS_EJB= "com.dtv.oss.domain.Address_Account";
	//�˻���ַEJB�����KEY
	public static final String ACCOUNT_ADDRESS_DTO= "com.dtv.oss.dto.Address_Account_DTO";
	//�ͻ��Ż��б�����KEY
	public static final String CUSTOMER_CAMPAIGN_LIST = "com.dtv.oss.dto.CustomerCampaign_List";
	//�ͻ���Ʒ�б�����KEY
	public static final String CUSTOMER_PRODUCT_LIST = "com.dtv.oss.service.CustomerProduct_List";
	//�Ź���ϸDTO�����KEY
	public static final String GROUP_BARGAIN_DETAIL_DTO = "com.dtv.oss.dto.GroupBargainDetailDTO";
	//�Ź���ϸEJB�����KEY
	public static final String GROUP_BARGAIN_DETAIL_EJB="com.dtv.oss.domain.GroupBargainDetail";
	//�¿ͻ��ͻ��г���Ϣ�б�����key
	public static final String NEW_CUST_MARKET_INFO_COLLECTION="com.dtv.oss.dto.NewCustomerMarketInfo_List";
	//�ͻ���Ʒ��id���϶���
	public static final String PRODUCT_PACKAGE_ID_LIST="com.dtv.oss.dto.ProductPackage_ID_List";
	//�ͻ�ѡ����Żݵ�id�ļ��϶���
	public static final String CAMPAIGN_CAMPAIGNID_LIST="com.dtv.oss.dto.Campaign_ID_List";
	//�ͻ�Ӳ����Ʒ����Ӧ��Ӳ����Ʒ���豸ID����Ϣ�б�
	public static final String PRODUCT_MATCH_DEVICE_PRODUCT_LIST="com.dtv.oss.service.productMatchDeviceProductLit";
	//�������
	public static final String PROCESS_RESULT="com.dtv.oss.service.process_Result";
	
	//��ƷID��
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
