package com.dtv.oss.web.util;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */

public class CommonKeys {


	public static final String SYSTEM_CONFIG_MENU_KEY="com.dtv.oss.dto.SystemConfigMenu";
	//ԤԼ�Ĳ���
    public static final String ACTION_OF_BOOKING ="0";
    //ԤԼ�����Ĳ���
    public static final String ACTION_OF_BOOKINGACCOUNT ="1";
    //�ŵ꿪���Ĳ���
    public static final String ACTION_OF_SHOPACCOUNT ="2";
    //������ԤԼ�����Ĳ���
    public static final String ACTION_OF_BOOKINGAGENT ="3";
    //�ͻ����ϵ���¼��
    public static final String ACTION_OF_BOOK_DIRECTLY ="4";
    //ģ����ӿ����Ĳ���
    public static final String ACTION_OF_CATVSHOPACCOUNT ="5";
    
    //������ԤԼȷ��
    public static final String CONFIRM_OF_BOOKING ="0";

    public static final String SA_TRANFER_NEW_USER="4";
    
    //ԤԼ��������ԤԼȡ��
    public static final String CANCEL_OF_BOOKING ="1";

    //ԤԼ��������ԤԼ�޸�
    public static final String MODIFY_OF_BOOKING ="2";

    //���ô洢����
    public static final String FEE_SESSION_NAME="FeeCommandResponse";

    //������ʾ������
    //�ؼ���ʽ
    public static final String SERVEY_SHOW_TYPE_TEXT="text";
    //�ı����+����
    public static final String SERVEY_SHOW_TYPE_LABEL="label";
    //����
    public static final String SERVEY_SHOW_TYPE_HIDE="hide";
    //��Զ
    public static final String SERVEY_SHOW_TYPE_RADIO="radio";
    //Զ
    public static final String SERVEY_SHOW_TYPE_CHECKBOX="checkbox";
    //�б�
    public static final String SERVEY_SHOW_TYPE_SELECT="select";
    //����
    public static final String SERVEY_SHOW_TYPE_PASSWORD="password";


    // ������com.dtv.oss.service.util.CommonConstDefinitionû�е��ֶΣ�
    // ����Ժ�Ҫ��CommonConstDefinition �����ݸ���CommonKeys���ݣ���Щ�ֶ�һ��Ҫ��

    //current customer session name
    public static final String CURRENT_CUSTOMER_SESSION_NAME = "ScndKeys.CurrentCustomer";
    
    //�ͻ������Ƿ�����ʾ����ҵ���ʻ�
    public static final String CURRENT_CUSTOMER_ISSHOWALLSA_SESSION_NAME = "ScndKeys.IsShowNumberSA";
    
    //�ͻ���,�ͻ���ҵ���ʻ��ĸ���
    public static final String CURRENT_CUSTOMER_SACOUNT_SESSION_NAME = "ScndKeys.SACOUNT";
    
    //�ͻ���,�ͻ���ҵ���ʻ�ID
    public static final String CURRENT_CUSTOMER_SAID_SESSION_NAME = "SAIDSession";
    
    //�ͻ���,�ͻ�ID
    public static final String CURRENT_CUSTOMER_CUSTOMERID_SESSION_NAME = "PID";
    
    //t_methodofpayment
    //�ֹ�Ԥ��
    public static final int METHOD_OF_PAYMENT_PREPAY = 7;
    //��������
    public static final int METHOD_OF_PAYMENT_ICBC = 102;
    //��������
    public static final int METHOD_OF_PAYMENT_CMB = 308;
    //�Ϻ�����
    public static final int METHOD_OF_PAYMENT_BSH = 311;

    //�ͻ����ϵ���¼��ʱ�������ͻ��ʻ���Ϣ�ύ
    public static final int OPEN_SERVICE_ACCOUNT_DIRECTLY = 9;
    //	����ҵ���ʻ� ��� ��Ʒ�����Żݺ��ʻ��Ƿ���δ���ʵ�
	public static final int CHECK_BOOKINGUSER_PRODUCTPG_AND_INVOICE = 10 ;
    //���ͻ���Ϣ
    public static final int CHECK_CUSTOMERINFO= 11;
    //��鿪���Ĳ�Ʒ�����Żݰ�
	public static final int CHECK_PRODUCTPG_CAMPAINPG=12 ;
    //��鿪����Ӳ����Ʒ
	public static final int CHECK_HARDPRODUCTINFO =13 ;
	public static final int CHECK_BATCHBUY_PRODUCTINFO =132 ;
    //��������Ϣ
	public static final int CHECK_FREEINFO  =14;
	//	����ҵ���ʻ� ��� ��Ʒ�����Żݺ��ʻ��Ƿ���δ���ʵ�
	public static final int CHECK_PRODUCTPG_AND_INVOICE = 15 ;
	//	����ҵ���ʻ� �ύ �����ŵ�����
	public static final int OPEN_SERVICE_ACCOUNT = 16 ;	
	public static final int OPEN_BATCHBUY_SERVICE_ACCOUNT = 162 ;	
	//���ģ����ӿ���
	public static final int CHECK_CATV_CUSTOMERINOF =17 ;
	//����ģ�⿪������
	public static final int CACULATE_OPEN_CATV_FEE =18 ;
    //	����ҵ���ʻ� �ύ ����ԤԼ����
	public static final int OPEN_BOOKINGUSER_SERVICE_ACCOUNT = 33 ;
	//  ����ҵ���ʻ� ԤԼ�ύ ����ԤԼ����
	public static final int OPEN_BOOKINGUSER_SERVICE_ACCOUNT_FORBOOKING =34;
	
	//  ����ҵ���ʻ� ԤԼ�ύ ����ԤԼ�����޸�
	public static final int OPEN_BOOKINGUSER_SERVICE_ACCOUNT_FORMODIFY =35;
	//  ����ҵ���ʻ� ԤԼ�ύ ����ԤԼ�����޸�
	public static final int OPEN_BOOKINGUSER_SERVICE_ACCOUNT_FORCANCLE =37;
	
	//	ҵ���ʻ� ������ͣ
	public static final int BATCH_SERVICE_ACCOUNT_PAUSE =38;
    //	ҵ���ʻ� �����ָ�
	public static final int BATCH_SERVICE_ACCOUNT_RESUME =39;
	
	//�޸Ŀͻ��豸��Դ
	public static final int CUSTOMER_DEVICE_PROVIDE_MODIFY=40;
	//�޸Ŀͻ���Ʒ�����ʻ�
	public static final int CUSTOMER_PRODUCT_ACCOUNT_MODIFY =41;
	
	//	������Ʒ �ύ
	public static final int ADD_PRODUCT = 17 ;
	//	ҵ���ʻ� ��ͣ
	public static final int SERVICE_ACCOUNT_PAUSE = 18 ;	
    //	ҵ���ʻ� �ָ�
	public static final int SERVICE_ACCOUNT_RENT = 50 ;
	//	ҵ���ʻ� �ָ�
	public static final int SERVICE_ACCOUNT_RESUME = 19 ;
	//	ҵ���ʻ� ����
	public static final int SERVICE_ACCOUNT_CLOSE = 20 ;
	//	ҵ���ʻ� ����
	public static final int SERVICE_ACCOUNT_TRANSFER = 21 ;
	//	ҵ���ʻ�  ����Ȩ
	public static final int SERVICE_ACCOUNT_RESEND = 42 ;
	//ģ��ҵ�� ͣ��
	public static final int CATV_SERVICE_PAUSE = 43 ;
	//ģ��ҵ�� ����
	public static final int CATV_SERVICE_RESUME = 44 ;
	//	��Ʒ ��ͣ
	public static final int CUSTOMER_PRODUCT_PAUSE = 22;
	//	��Ʒ �ָ�
	public static final int CUSTOMER_PRODUCT_RESUME = 23 ;
	//	��Ʒ ȡ��
	public static final int CUSTOMER_PRODUCT_CANCEL = 24 ;
	//	��Ʒ ����
	public static final int CUSTOMER_PRODUCT_UPGRADE = 25 ;
	//	��Ʒ ����
	public static final int CUSTOMER_PRODUCT_DOWNGRADE = 26 ;
	//	�ط���Ȩ
	public static final int CUSTOMER_PRODUCT_RESEND = 27 ;
	//	���������
	public static final int CUSTOMER_PRODUCT_CANCELPARTNERSHIP = 28 ;
	//	�������
	public static final int CUSTOMER_PRODUCT_PARTNERSHIP = 29 ;
	//	��������
	public static final int CUSTOMER_PRODUCT_CLEARPASSWORD = 30 ;
	//	������Ϣ
	public static final int CUSTOMER_PRODUCT_SENDMAIL = 31 ;
	//	������Ϣ
	public static final int SERVICE_ACCOUNT_CODE_UPDATE = 32 ;
	//  ԤԼ��Ʒ�޸�
	public static final int BOOK_PRODUCT_MODIFY = 33;
    //	��Ʒ Ǩ��
	public static final int CUSTOMER_PRODUCT_MOVE = 55;	
    //	ҵ���ʻ�Ԥ�˻�
	public static final int SERVICE_ACCOUNT_BEFOREHAND_CLOSE = 56 ;
    //	ҵ���ʻ�ʵ�˻�
	public static final int SERVICE_ACCOUNT_REAL_CLOSE = 57 ;	
    //	�豸����
	public static final int DEVICE_SWAP =58 ;
    //	�豸����
	public static final int DEVICE_UPGRADE=59 ;

    //��װ���ͣ��԰�װ
	public static final String CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL = "S";
    //��װ���ͣ����Ű�װ
	public static final String CUSTSERVICEINTERACTIONIN_STYPE_INSTALL = "C";
    
	//	SET_M_CAMPAIGNCAMPAIGNTYPE
	//��������ͣ��ײ�
	public static final String CAMPAIGNCAMPAIGNTYPE_OPEN = "B";
    //��������ͣ���ͨ����
	public static final String CAMPAIGNCAMPAIGNTYPE_NORMAL = "A";
//    //��������ͣ��Ź��ײ�
//	public static final String CAMPAIGNCAMPAIGNTYPE_GROUPBARGAIN = "T";
//    //��������ͣ��Ź��򿪻��ײ�
//	public static final String CAMPAIGNCAMPAIGNTYPE_GROUPBARGAIN_OR_OPEN = "O";

    /************************	SET_C_CUSTOPENSOURCETYPE  */
    //	�ͻ���Դ���ͣ��ŵ�
	public static final String OPENSOURCETYPE_BRANCH = "O";
    //	�ͻ���Դ���ͣ��ֵ�վ
	public static final String OPENSOURCETYPE_STREETSTATION = "S";
    //	�ͻ���Դ���ͣ��ֹ�˾������
	public static final String OPENSOURCETYPE_PROXY = "P";
    //	�ͻ���Դ���ͣ�����
	public static final String OPENSOURCETYPE_DEPARTMENT = "D";
    //	�ͻ���Դ���ͣ��绰����
	public static final String OPENSOURCETYPE_TELEPHONESALE = "T";
    //	�ͻ���Դ���ͣ�������������
	public static final String OPENSOURCETYPE_CALLCENTER = "C";
    //	�ͻ���Դ���ͣ��Ź�����
	public static final String OPENSOURCETYPE_GROUPBARGAIN = "G";
    //	�ͻ���Դ���ͣ���������
	public static final String OPENSOURCETYPE_SUPERMARKET = "M";
    //	�ͻ���Դ���ͣ��ֳ���̯����
	public static final String OPENSOURCETYPE_RETAIL = "X";
    //	�ͻ���Դ���ͣ�����ͨ����
	public static final String OPENSOURCETYPE_CABLEPROXY = "Y";
    //	�ͻ���Դ���ͣ���ҵ����
	public static final String OPENSOURCETYPE_JOBPROXY = "H";
    //	�ͻ���Դ���ͣ���������
	public static final String OPENSOURCETYPE_HOUSEMATCHED = "F";
    //	�ͻ���Դ���ͣ�����Ԥ��Ȩ
	public static final String OPENSOURCETYPE_COMPANYSEND = "R";
	public static final String OPENSOURCETYPE_A = "A";

    //SET_S_ORGANIZATIONORGTYPE
	public static final String ORGANIZATIONORGTYPE_GENERALCOMPANY = "C"; 	//�ڵ����ͣ��ܹ�˾
	public static final String ORGANIZATIONORGTYPE_DEPARTMENT = "D"; 		//�ڵ����ͣ�����
	public static final String ORGANIZATIONORGTYPE_SUBCOMPANY = "B"; 		//�ڵ����ͣ��ֹ�˾
	public static final String ORGANIZATIONORGTYPE_PARTNER = "P"; 			//�ڵ����ͣ��������
	public static final String ORGANIZATIONORGTYPE_BRANCH = "O"; 			//�ڵ����ͣ��ŵ�
	public static final String ORGANIZATIONORGTYPE_STREETSTATION = "S"; 	//�ڵ����ͣ��ֵ�վ

   //	SET_V_CSIPROCESSLOGACTION
	public static final String CSIPROCESSLOG_ACTION_NEW = "N"; 					    //��������������½�
	public static final String CSIPROCESSLOG_ACTION_APPLY = "A";   				    //�������������ȷ��
	public static final String CSIPROCESSLOG_ACTION_CANCEL = "C";                   //�������������ȡ��
	public static final String CSIPROCESSLOG_ACTION_RETURNMONEY = "W";              //��������������˿�
	public static final String CSIPROCESSLOG_ACTION_SUCCESS = "S";                  //�����������������ɹ�
	public static final String CSIPROCESSLOG_ACTION_FAIL = "F";                     //�����������������ʧ��
	public static final String CSIPROCESSLOG_ACTION_PAYFEE4BOOKING = "B";           //�������������ԤԼ��������֧��
	public static final String CSIPROCESSLOG_ACTION_CALLBACK = "L";                 //��������������ط�
	public static final String CSIPROCESSLOG_ACTION_UPDATE = "M";                   //��������������޸�
	public static final String CSIPROCESSLOG_ACTION_SETCALLBACKFLAG = "O";          //��������������ط��ݴ�
	public static final String CSIPROCESSLOG_ACTION_UPDATEFEE2PAYMENT = "D";        //�����������������
	public static final String CSIPROCESSLOG_ACTION_BOOKINGCANCEL = "C1";           //�����������������ԤԼȡ��
	public static final String CSIPROCESSLOG_ACTION_INSTALLCANCEL = "C2";           //���������������װԤԼȡ��
	public static final String CSIPRPCESSLOG_ACTION_PROCESS ="P";                   //����������������ڽ���

    //	SET_V_CUSTSERVICEINTERACTIONTYPE
	public static final String CUSTSERVICEINTERACTIONTYPE_GO = "GO"; 		//ҵ�����������ͣ����ſͻ�����
	public static final String CUSTSERVICEINTERACTIONTYPE_GS = "GS"; 		//ҵ�����������ͣ��ӿͻ�����
	public static final String CUSTSERVICEINTERACTIONTYPE_GM = "GM"; 		//ҵ�����������ͣ��ӿͻ�ת���˿ͻ�	

	public static final String CUSTSERVICEINTERACTIONTYPE_CO = "CO";        //ҵ�����������ͣ��ͻ����������ϵ���¼�룩
	public static final String CUSTSERVICEINTERACTIONTYPE_BK = "BK"; 		//ҵ�����������ͣ�ԤԼ
	public static final String CUSTSERVICEINTERACTIONTYPE_OS = "OS"; 		//ҵ�����������ͣ��ŵ꿪��
	public static final String CUSTSERVICEINTERACTIONTYPE_OB = "OB"; 		//ҵ�����������ͣ�ԤԼ����
	public static final String CUSTSERVICEINTERACTIONTYPE_PS = "PS";		//ҵ�����������ͣ���ͣ�ͻ���Ʒ
	public static final String CUSTSERVICEINTERACTIONTYPE_RM = "RM";		//ҵ�����������ͣ��ָ��ͻ���Ʒ
	public static final String CUSTSERVICEINTERACTIONTYPE_TS = "TS";		//ҵ�����������ͣ�ԭ�ع���
	public static final String CUSTSERVICEINTERACTIONTYPE_TD = "TD";		//ҵ�����������ͣ���ع���
	public static final String CUSTSERVICEINTERACTIONTYPE_MD = "MD";		//ҵ�����������ͣ�Ǩ��
	public static final String CUSTSERVICEINTERACTIONTYPE_AD = "AD";		//ҵ�����������ͣ�Ԥ��
	public static final String CUSTSERVICEINTERACTIONTYPE_PI = "PI";		//ҵ�����������ͣ�֧���ʵ�
//	public static final String CUSTSERVICEINTERACTIONTYPE_CU = "CU";		//ҵ�����������ͣ��ͻ���Ϣ��� //2005-11-24 by Wesley Shu
	public static final String CUSTSERVICEINTERACTIONTYPE_PA = "PA";		//ҵ�����������ͣ��ͻ���Ʒ���
	public static final String CUSTSERVICEINTERACTIONTYPE_PN = "PN";		//ҵ�����������ͣ��ͻ���Ʒ����
	public static final String CUSTSERVICEINTERACTIONTYPE_IA = "IA";		//ҵ�����������ͣ���Ϣ���
	public static final String CUSTSERVICEINTERACTIONTYPE_CC = "CC";		//ҵ�����������ͣ�����
	public static final String CUSTSERVICEINTERACTIONTYPE_WC = "WC";		//ҵ�����������ͣ��˻�
	public static final String CUSTSERVICEINTERACTIONTYPE_FR = "FR";		//ҵ�����������ͣ��˻�Ѻ��
	public static final String CUSTSERVICEINTERACTIONTYPE_FS = "FS";		//ҵ�����������ͣ�û��Ѻ��
	public static final String CUSTSERVICEINTERACTIONTYPE_OC = "OC";		//ҵ�����������ͣ�ȡ���ʻ�
	public static final String CUSTSERVICEINTERACTIONTYPE_DS = "DS"; 		//ҵ�����������ͣ��豸����
	public static final String CUSTSERVICEINTERACTIONTYPE_CP = "CP";		//ҵ�����������ͣ�ȡ���ͻ���Ʒ
	public static final String CUSTSERVICEINTERACTIONTYPE_AP = "AP";		//ҵ�����������ͣ�����ͻ���Ʒ����
	public static final String CUSTSERVICEINTERACTIONTYPE_AC = "AC";		//ҵ�����������ͣ������Ʒ�Ż�
	public static final String CUSTSERVICEINTERACTIONTYPE_PU = "PU";		//ҵ�����������ͣ���Ʒ����
	public static final String CUSTSERVICEINTERACTIONTYPE_PD = "PD";		//ҵ�����������ͣ���Ʒ����
	public static final String CUSTSERVICEINTERACTIONTYPE_CS = "CS";		//ҵ�����������ͣ������ų�
	public static final String CUSTSERVICEINTERACTIONTYPE_UO = "UO";		//ҵ�����������ͣ��¿��û�
	public static final String CUSTSERVICEINTERACTIONTYPE_OA = "OA";		//ҵ�����������ͣ������˻�
	public static final String CUSTSERVICEINTERACTIONTYPE_BU = "BU";		//ҵ�����������ͣ�ԤԼ����
	public static final String CUSTSERVICEINTERACTIONTYPE_UP = "UP";		//ҵ�����������ͣ���ͣ�û�
	public static final String CUSTSERVICEINTERACTIONTYPE_RT = "RT";		//ҵ�����������ͣ��豸ת��
	public static final String CUSTSERVICEINTERACTIONTYPE_SP = "SP";		//ҵ�����������ͣ��û�Ԥ��
	public static final String CUSTSERVICEINTERACTIONTYPE_UR = "UR";		//ҵ�����������ͣ��ָ��û�
	public static final String CUSTSERVICEINTERACTIONTYPE_UC = "UC";		//ҵ�����������ͣ��û�����
	public static final String CUSTSERVICEINTERACTIONTYPE_UT = "UT";		//ҵ�����������ͣ��û�����
	public static final String CUSTSERVICEINTERACTIONTYPE_BP = "BP";		//ҵ�����������ͣ�ԤԼ�����ͻ���Ʒ
	public static final String CUSTSERVICEINTERACTIONTYPE_BDS = "BDS";		//ҵ�����������ͣ��ײ�ת��    
	public static final String CUSTSERVICEINTERACTIONTYPE_BDP = "BDP";		//ҵ�����������ͣ��ײ�Ԥ��
	public static final String CUSTSERVICEINTERACTIONTYPE_BDE = "BDE";		//ҵ�����������ͣ��ײ�����
	public static final String CUSTSERVICEINTERACTIONTYPE_BDC = "BDC";		//ҵ�����������ͣ��ײ�ȡ��
	public static final String CUSTSERVICEINTERACTIONTYPE_CAO = "CAO";		//ҵ�����������ͣ�ģ��ҵ�񿪻�
	public static final String CUSTSERVICEINTERACTIONTYPE_CAA = "CAA";		//ҵ�����������ͣ�ģ��ҵ������
	public static final String CUSTSERVICEINTERACTIONTYPE_CAS = "CAS";		//ҵ�����������ͣ�ģ�����ҵ��ͣ��
	public static final String CUSTSERVICEINTERACTIONTYPE_CAR = "CAR";		//ҵ�����������ͣ�ģ�����ҵ�񸴻�
	public static final String CUSTSERVICEINTERACTIONTYPE_MB = "MB";		//ҵ�����������ͣ��ֹ��շ�
	public static final String CUSTSERVICEINTERACTIONTYPE_MAC = "MAC";		//ҵ�����������ͣ��ֹ��������	
	
	public static final String METHODOFPAYMENT_KHBX ="KHBX"   ;             //֧����ʽ--- ���޵�
	public static final String METHODOFPAYMENT_SLTZ ="SLTZ"   ;             //֧����ʽ--- ��������
	public static final String METHODOFPAYMENT_BXTZ ="BXTZ"   ;             //֧����ʽ--- ���޵�����
	public static final String METHODOFPAYMENT_ZHTZ ="ZHTZ"   ;             //֧����ʽ--- �ʻ�����
	public static final String METHODOFPAYMENT_ZDTZ ="ZDTZ"   ;             //֧����ʽ--- �ʵ����� 
	public static final String METHODOFPAYMENT_KHGD ="KHGD"   ;             //֧����ʽ--����
	
	//SET_V_CUSTSERVICEINTERACTIONSTATUS
	public static final String CUSTSERVICEINTERACTION_STATUS_NEW = "N";             //ҵ��������״̬������
	public static final String CUSTSERVICEINTERACTION_STATUS_WAIT = "W";		    //ҵ��������״̬��������
	public static final String CUSTSERVICEINTERACTION_STATUS_CANCEL = "C";          //ҵ��������״̬��ȡ��
	public static final String CUSTSERVICEINTERACTION_STATUS_SUCCESS = "S";         //ҵ��������״̬������ɹ�
	public static final String CUSTSERVICEINTERACTION_STATUS_FAIL = "F";            //ҵ��������״̬�������ɹ�
	public static final String CUSTSERVICEINTERACTION_STATUS_RETURNMONEY = "R";     //ҵ��������״̬�����˷�
	public static final String CUSTSERVICEINTERACTION_STATUS_PROCESS = "P";         //ҵ��������״̬�����ڴ���
	public static final String CUSTSERVICEINTERACTION_STATUS_BOOKINGCANCEL = "C1";  //ҵ��������״̬������ԤԼȡ��
	public static final String CUSTSERVICEINTERACTION_STATUS_INSTALLCANCEL = "C2";  //ҵ��������״̬����װԤԼȡ��

	//SET_C_CUSTOMERSTATUS
	public static final String CUSTOMER_STATUS_POTENTIAL = "P";				//�ͻ�״̬��Ǳ��
	public static final String CUSTOMER_STATUS_NORMAL = "N";				//�ͻ�״̬������
	public static final String CUSTOMER_STATUS_CANCEL = "C";				//�ͻ�״̬��ȡ��

	//SET_C_SERVICEACCOUNTSTATUS
	public static final String SERVICE_ACCOUNT_STATUS_INITIAL = "I";			//ҵ���ʻ�״̬����ʼ
	public static final String SERVICE_ACCOUNT_STATUS_HALT = "H";				//ҵ���ʻ�״̬��������ͣ
	public static final String SERVICE_ACCOUNT_STATUS_SUSPEND = "S";			//ҵ���ʻ�״̬��ϵͳ��ͣ
	public static final String SERVICE_ACCOUNT_STATUS_NORMAL = "N";				//ҵ���ʻ�״̬������
	public static final String SERVICE_ACCOUNT_STATUS_CANCEL = "C";				//ҵ���ʻ�״̬��ȡ��

    //SET_F_PAYMENTRECORDTYPE
	public static final String PAYMENTRECORD_TYPE_PRESAVE = "P"; 			//֧����¼-���Ѽ�¼�����ͣ�Ԥ����
	public static final String PAYMENTRECORD_TYPE_ACCEPT_CASE = "C";		//֧����¼-���Ѽ�¼�����ͣ�֧���������
	public static final String PAYMENTRECORD_TYPE_RETURN_FEE = "RF";		//֧����¼-���Ѽ�¼�����ͣ��˷�
	public static final String PAYMENTRECORD_TYPE_BILLING = "N"; 			//֧����¼-���Ѽ�¼�����ͣ��ͻ��ʵ�����
	public static final String PAYMENTRECORD_TYPE_RETURN_RR = "RR";			//֧����¼-���Ѽ�¼�����ͣ�Ԥ���˷�
	
	//SET_C_CUSTOMERSTYLE
	public static final String CUSTOMERSTYLE_SINGLE = "S"; 					//�ͻ����ͣ����˿ͻ�
	public static final String CUSTOMERSTYLE_GROUP = "G"; 					//�ͻ����ͣ����ſͻ�
	
	public static final String PAYMENTRECORD_TYPE_ONINVOICE = "N"; 			//֧����¼-���Ѽ�¼�����ͣ��ֽ���
	public static final String PAYMENTRECORD_TYPE_TPRESAVE = "TP"; 			//֧����¼-���Ѽ�¼�����ͣ��������Ԥ����
	public static final String PAYMENTRECORD_TYPE_PUTINTO = "X"; 			//֧����¼-���Ѽ�¼�����ͣ�ת��(�Ź���)
	public static final String PAYMENTRECORD_TYPE_TONINVOICE = "TN"; 		//֧����¼-���Ѽ�¼�����ͣ�������Ҹ��ʵ�
	public static final String PAYMENTRECORD_TYPE_RETURNMONEY = "D"; 		//֧����¼-���Ѽ�¼�����ͣ����
	public static final String PAYMENTRECORD_TYPE_TOKENINVOICE = "CN"; 		//֧����¼-���Ѽ�¼�����ͣ��ֿ�ȯ���ʵ�
	public static final String PAYMENTRECORD_TYPE_TOKENPRESAVE = "CP"; 		//֧����¼-���Ѽ�¼�����ͣ��ֿ�ȯԤ����

	//SET_F_PAYMENTRECORDSOURCETYPE
	public static final String PAYMENTRECORDSOURCETYPE_OTHER = "O";			//֧����¼-֧����¼��Դ���ͣ�����
	public static final String PAYMENTRECORDSOURCETYPE_REPAIR = "P";		//֧����¼-֧����¼��Դ���ͣ����޵�
	public static final String PAYMENTRECORDSOURCETYPE_ACCEPT = "M"; 		//֧����¼-֧����¼��Դ���ͣ�����
	public static final String PAYMENTRECORDSOURCETYPE_ADJUST = "T"; 		//֧����¼-֧����¼��Դ���ͣ�����

    //	SET_F_AIREFERTYPE
	public static final String AIREFERTYPE_M = "M"; 						//����-�ۺ�������Դ���ͣ���������
	public static final String AIREFERTYPE_A = "A"; 						//����-�ۺ�������Դ���ͣ����ʲ���
	public static final String AIREFERTYPE_P = "P"; 						//����-�ۺ�������Դ���ͣ����޵�

    //	SET_F_PREPAYMENTTYPE
	public static final String PREPAYMENTTYPE_CASH = "C";					//Ԥ������ͣ��ֽ�
	public static final String PREPAYMENTTYPE_TRANSLUNARY="T";				//Ԥ������ͣ��������
    //	SET_F_PAYMENTTICKETTY
	public static final String PAYMENTTICKETTY_DK = "DK";					//֧����¼-֧��ȯ���ͣ��ֿ�ȯ
	public static final String PAYMENTTICKETTY_TG = "TG";					//֧����¼-֧��ȯ���ͣ��Ź�ȯ

	//SET_F_MOPPAYTYPE
	public static final String MOPPAYTYPE_BP = "BP";					//���д��ۿ�
	public static final String MOPPAYTYPE_CH = "CH";					//���渶��
	public static final String MOPPAYTYPE_CR = "CR";					//���ÿ�
	public static final String MOPPAYTYPE_DK = "DK";					//�ֿ�ȯ֧��
	public static final String MOPPAYTYPE_OL = "OL";					//���߸���
	public static final String MOPPAYTYPE_OT = "OT";					//����
	public static final String MOPPAYTYPE_MB = "MB";					//�ֹ�Ԥ��

	//SET_F_FTCREATINGMETHOD
	public static final String FTCREATINGMETHOD_A = "A"; 					//���ô�����Դ���ͣ������Ʒ�
	public static final String FTCREATINGMETHOD_M = "M";					//���ô�����Դ����: �ֹ�

    //	SET_F_SETOFFFLAG
	public static final String SETOFFFLAG_W = "W"; 							//���ʼ�¼-���ʱ�ǣ�������
	public static final String SETOFFFLAG_P = "P"; 							//���ʼ�¼-���ʱ�ǣ���������
	public static final String SETOFFFLAG_D = "D"; 							//���ʼ�¼-���ʱ�ǣ�������
	//SET_G_YESNOFLAG
	public static final String FORCEDDEPOSITFLAG_Y = "Y";					//��ǿ��Ԥ��
	public static final String FORCEDDEPOSITFLAG_N = "N";					//����ǿ��Ԥ��

	//SET_B_BATCHJOBSTATUS
	public static final String BATCH_JOB_STATUS_CANCEL="C";					//ȡ��
	public static final String BATCH_JOB_STATUS_FAIL="F";					//ִ��ʧ��
	public static final String BATCH_JOB_STATUS_WAIT="N";					//�ȴ�ִ��
	public static final String BATCH_JOB_STATUS_SUCESS="S";					//ִ�гɹ�


    //	SET_V_CUSTSERVICEINTERACTIONPAYSTATUS
	public static final String CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY = "W";   //���õ�֧��״̬��δ��
	public static final String CUSTSERVICEINTERACTION_PAYSTATUS_PAYED = "D";        //���õ�֧��״̬���Ѹ�
	public static final String CUSTSERVICEINTERACTION_PAYSTATUS_RETURNMONEY = "R";  //���õ�֧��״̬���˷�

	//SET_B_QUERYRSTATUS
	public static final String QUERY_STATUS_NEW="N";								//�½�
	public static final String QUERY_STATUS_PROCESSING="W";							//���ڴ���
	public static final String QUERY_STATUS_SUCCESS="S";							//����ɹ�
	public static final String QUERY_STATUS_FAIL="F";								//����ʧ��
	public static final String QUERY_STATUS_CANCEL="C";								//ȡ��
	public static final String QUERY_STATUS_REFERED="I";							//������

	//SET_B_SCHEDULETYPE
	public static final String SCHEDULE_TYPE_I="I";									//����ִ��
	public static final String SCHEDULE_TYPE_S="S";									//��ʱִ��

    //	SET_F_PDR_REFERRECORDTYPE
	public static final String F_PDR_REFERRECORDTYPE_C="C";              //��Դ��¼���ͣ�����
	public static final String F_PDR_REFERRECORDTYPE_F="F";              //��Դ��¼���ͣ�������
	public static final String F_PDR_REFERRECORDTYPE_I="I";              //��Դ��¼���ͣ��ʵ�
	public static final String F_PDR_REFERRECORDTYPE_P="P";              //��Դ��¼���ͣ����޵�

	//SET_F_INVOICESTATUS �ʵ�״̬
//	SET_F_INVOICESTATUS
	public static final String INVOICE_STATUS_WAIT = "W";					//�ʵ�״̬��������
	public static final String INVOICE_STATUS_OWE = "O";					//�ʵ�״̬������
	public static final String INVOICE_STATUS_PAID = "D";					//�ʵ�״̬���Ѿ�����
	public static final String INVOICE_STATUS_BAD = "B";					//�ʵ�״̬������ע��
	public static final String INVOICE_STATUS_CANCEL = "C";					//�ʵ�״̬��ȡ��
	public static final String INVOICE_STATUS_RETURNMONEY = "R";			//�ʵ�״̬���˿�
	public static final String INVOICE_STATUS_QFZT = "T";					//�ʵ�״̬��Ƿ��׷��
	public static final String INVOICE_STATUS_NORMAL = "N";					//�ʵ�״̬������
	
    //	SET_C_PROBLEMSTATUS
	public static final String CUSTOMERPROBLEM_STATUS_WAIT="W";					//���޵�״̬��������
	public static final String CUSTOMERPROBLEM_STATUS_PROCESSING="C";				//���޵�״̬�����ڴ���
	public static final String CUSTOMERPROBLEM_STATUS_SUCCESS="D";					//���޵�״̬��������
	//public static final String CUSTOMERPROBLEM_STATUS_UPGRADE="U";				//���޵�״̬��������
	public static final String CUSTOMERPROBLEM_STATUS_FAIL="S";					//���޵�״̬��ά�޲��ɹ�
	public static final String CUSTOMERPROBLEM_STATUS_TERMINAL="T";				//���޵�״̬���޷�����ά��

	//SET_F_FTSTATUS
	public static final String SET_F_FTSTATUS_I="I";              //��Ч
	public static final String SET_F_FTSTATUS_L="L";              //����
	public static final String SET_F_FTSTATUS_P="P";              //Ԥ����
	public static final String SET_F_FTSTATUS_V="V";              //��Ч


	//SET_F_ACCOUNTADJUSTMENTDIRECTION
	public static final String ADJUST_DIRECTION_R ="R"  ;                   //Ӧ��
	public static final String ADJUST_DIRECTION_P ="P" ;                     //ʵ��

	//SET_F_ACCOUNTADJUSTMENTTYPE
	public static final String ADJUST_TYPE_C="C";    //ά�޵�����
	public static final String ADJUST_TYPE_I="I";    //���ʵ�����
	public static final String ADJUST_TYPE_M="M";    //�ֹ�����
	public static final String ADJUST_TYPE_S="S";    //��������

	//SET_F_ADJUSTMENTREFERRECORDTYPE
	public static final String ADJUST_REFERCODETYPE_D ="D" ;   //�ֿۼ�¼
	public static final String ADJUST_REFERCODETYPE_F ="F" ;   //���ü�¼
	public static final String ADJUST_REFERCODETYPE_P ="P" ;   //Ԥ���¼
	public static final String ADJUST_REFERCODETYPE_C ="C" ;   //֧����¼

    //	SET_F_BRFEETYPE
	public static final String BRFEETYPE_PREPAY = "P"; 					//Ӫҵ�������Ԥ�յ����ӷ�
	public static final String BRFEETYPE_DOWNGRADE = "B"; 				//Ӫҵ������𣺽�����
	public static final String BRFEETYPE_LOGOUT = "C"; 					//Ӫҵ�������������
	public static final String BRFEETYPE_DEVICE = "D"; 					//Ӫҵ��������豸��
	public static final String BRFEETYPE_TRANSFER = "F"; 				//Ӫҵ������𣺹�����
	public static final String BRFEETYPE_INSTALL = "I";					//Ӫҵ������𣺰�װ���Է�
	public static final String BRFEETYPE_INFORMATION = "M"; 			//Ӫҵ���������Ϣ��
	public static final String BRFEETYPE_OPENACCOUNT = "O"; 			//Ӫҵ������𣺿�����
	public static final String BRFEETYPE_RESUME = "R"; 					//Ӫҵ������𣺻ָ�������
	public static final String BRFEETYPE_SUSPEND = "S"; 				//Ӫҵ���������ͣ��
	public static final String BRFEETYPE_MOVE = "T"; 					//Ӫҵ�������Ǩ�Ʒ�
	public static final String BRFEETYPE_UPGRADE = "U"; 				//Ӫҵ�������������
	public static final String BRFEETYPE_FORCEDDEPOSIT = "A"; 			//Ӫҵ�������Ѻ��
	public static final String BRFEETYPE_SERVICE = "E"; 				//Ӫҵ����������ŷ����

	
	//ҵ���˻���������
	public static final String SA_TRANFER_TYPE_I="I";
	public static final String SA_TRANFER_TYPE_O="O";
	
	//��Ʒ����ȡֵ��ʽ
	public static final String PRODUCT_PROPERTY_MODE_FIXED="F";			//�̶�ֵ
	public static final String PRODUCT_PROPERTY_MODE_SETTING="S";		//���������ñ�ѡ��
	public static final String PRODUCT_PROPERTY_MODE_INPUT="I";			//����
	public static final String PRODUCT_PROPERTY_MODE_RESOURCE="R";		//��ϵͳ��Դ����
	
	//�������
	public static final int FRIEND_PHONENO_CREATE=101;			//�����������
	public static final int FRIEND_PHONENO_DELETE=102;			//�������ɾ��
	
	//VOIP��������
	public static final String VOIP_QUERY_PRODUCT = "P";
	public static final String VOIP_QUERY_CONDITION = "C";
	public static final String VOIP_QUERY_GATEWAY = "G";
	//�ط�
	public static final String CALLBACKINFOTYPE_C="C";		
	
	//�ط����ͣ�Ͷ�߻ط�
	//��ͬ״̬:
	public static final String CONTRACTSTATUS_NEW="N"; 		//�½�
	public static final String CONTRACTSTATUS_EFFECT="E";	//��Ч
	public static final String CONTRACTSTATUS_OPEN="U";		//����
	public static final String CONTRACTSTATUS_CANCEL="C"; 	//ȡ��
	//SET_P_PRODUCTCLASS
	public static final String PRODUCTCLASS_S="S" ;		//���
	public static final String PRODUCTCLASS_H="H" ;		//Ӳ��
	
	//�ļ��ϴ�����·��
	public static final String FILE_UPLOAD_PATH="userUpload";
	//pageContext��request�еĶ�������
	public static final String FILE_UPLOAD_PAGE_CONTEXT="FILE_UPLOAD_PAGE_CONTEXT";
	//���Ŵ�ͻ�
	public static final int GET_DEVICE_LIST = 11 ;                               //�õ�Ӳ���б�
	public static final int GET_FEE_LIST = 12 ;                                  //�õ������б�
	public static final int OPEN_CHILD_CUSTOMER = 16 ;                           //�ӿͻ�����
	
	public static final String BILLBOARD_GRADE_I="I";    	//����
	public static final String BILLBOARD_GRADE_N="N";    	//��ͨ
	
	//SET_V_CSIPAYMENTSETTINGOPTION
	public static final String CSI_PAYMENT_OPTION_IM ="IM";   //����֧��
	public static final String CSI_PAYMENT_OPTION_OP ="OP";   //��ѡ��
	public static final String CSI_PAYMENT_OPTION_IV ="IV";   //���¶��ʵ���֧��
	public static final String CSI_PAYMENT_OPTION_SP ="SP";   //���Ű�װ�ɹ�����ȡ
	
	//SET_C_DIAGNOSISINFOREFERSOURCETYPE �����Ϣ��Դ�ĵ��ݵ�����
	public static final String DIAGNOSIS_INFOR_SOURCE_TYPE_A ="A";   //ά�޹���
	public static final String DIAGNOSIS_INFOR_SOURCE_TYPE_E ="E";   //���޵�
	//SET_C_CONTRACTSTATUS  ���ſͻ�����ʱ�ĺ�ͬ״̬
	public static final String CONTRACTSTATUS_C ="C";   //����
	public static final String CONTRACTSTATUS_E ="E";   //��Ч
	public static final String CONTRACTSTATUS_N ="N";   //����
	public static final String CONTRACTSTATUS_U ="U";   //����
	
	
	public static final String TERMINALDEVICESELECT="com.dtv.oss.web.flow.DeviceClassMap";
	public static final String SERVICEID="com.dtv.oss.web.flow.ServiceID";

	 //����command���ظ�webAction ����ֵ��ʹ�ñ�־��
	public static final String COMMAND_ID_DEVICE="D";  //���ؽ��Ϊ�豸
	public static final String COMMAND_ID_IMMEDIATEFEE="I";//���ؽ��Ϊ����
//	SET_S_ROLEORGNIZATIONORGROLE��֯��ɫ
	public static final String ORGANIZATION_ROLE_REPAIR = "R";      //����
	public static final String ORGANIZATION_ROLE_INSTALL = "I";     //��װ
	public static final String ORGANIZATION_ROLE_COMPLAIN = "C";    //Ͷ��
	public static final String ORGANIZATION_ROLE_SERVICE = "S";     //ά��
	public static final String ORGANIZATION_ROLE_M ="M";            //ģ��
	
    //	SET_W_JOBCARDTYPE
	public static final String JOBCARD_TYPE_REPAIR = "R";					//�������ͣ�ά��
	public static final String JOBCARD_TYPE_INSTALLATION = "I";				//�������ͣ���װ
	public static final String JOBCARD_TYPE_CATVNETWORK = "C"; 				//��������: ģ���������ʩ����
	
	//SET_W_JOBCARDSUBTYPE  ����������
	public static final String JOBCARD_SUBTYPE_F = "F";			//���������ͣ���˵�
	public static final String JOBCARD_SUBTYPE_H = "H";			//���������ͣ��ָ���ͨ��ҵ��
	public static final String JOBCARD_SUBTYPE_Q = "Q"; 		//����������: Ǩ��ͣ����ҵ��
	public static final String JOBCARD_SUBTYPE_R = "R";			//���������ͣ�Ǩ�뿪ͨ��ҵ��
	public static final String JOBCARD_SUBTYPE_T = "T";			//���������ͣ�ͣ����ҵ��
	public static final String JOBCARD_SUBTYPE_Z = "Z"; 		//����������: ���˰�װ��ҵ��
	public static final String JOBCARD_SUBTYPE_W = "W";			//����������: ����ʩ����
	
	//SET_V_PRINTSHEETTYPE  ��ӡ��������
	public static final String SET_V_PRINTSHEETTYPE_A = "A";			//��ӡ�������ͣ���װ����
	public static final String SET_V_PRINTSHEETTYPE_B = "B";			//��ӡ�������ͣ��ָ���ͨ��ҵ��
	public static final String SET_V_PRINTSHEETTYPE_C = "C";			//��ӡ�������ͣ��ͻ���Ϣ����
	public static final String SET_V_PRINTSHEETTYPE_M = "M";			//��ӡ�������ͣ�ģ����ӹ���
	public static final String SET_V_PRINTSHEETTYPE_S = "S";			//��ӡ�������ͣ�����
	public static final String SET_V_PRINTSHEETTYPE_W = "W";			//��ӡ�������ͣ�ά�޹���
	
	//SET_V_PRINTSHEETREASON  ��ӡ����ԭ��ֻ���ڴ�ӡ���������������������
	public static final String SET_V_PRINTSHEETREASON_F = "F";			//��ӡ����ԭ���շѵ���ӡ
	public static final String SET_V_PRINTSHEETREASON_R = "R";			//��ӡ����ԭ�򣺵Ǽǵ���ӡ
	
	//SET_V_PRINTBLOCKDETAILTYPE  ��ӡ��ϸ���õ��ֶ�ȡֵ��ʽ
	public static final String SET_V_PRINTBLOCKDETAILTYPE_D = "D";			//�ֶ�ȡֵ��ʽ�����ݿ�SQL�ֶ�ȡֵ
	public static final String SET_V_PRINTBLOCKDETAILTYPE_T = "T";			//�ֶ�ȡֵ��ʽ����������ʾ��Ϣ
	
	//��Ʊ��ַ���� SET_FP_FAPIAOADDRESSTYPE  
	public static final String FAPIAO_ADDRESSTYPE_D = "D"; 		//��Ʊ��ַ���ͣ��ֿ�
	public static final String FAPIAO_ADDRESSTYPE_O = "O"; 		//��Ʊ��ַ���ͣ���֯����
	//��Ʊ����: SET_FP_FPTYPE
	public static final String FAPIAO_TYPE_NOR = "NOR"; 		//��ͨ��Ʊ
	public static final String FAPIAO_TYPE_INC = "INC";         //��ֵ˰��Ʊ
	//��Ʊ/��Ʊ��״̬��SET_FP_STATUS 
	public static final String FAPIAO_STATUS_SAV = "SAV"; 		//���
	public static final String FAPIAO_STATUS_REC = "REC"; 		//����ȡ
	public static final String FAPIAO_STATUS_DRA = "DRA"; 		//���
	public static final String FAPIAO_STATUS_CAN = "CAN"; 		//������
	public static final String FAPIAO_STATUS_DIS = "DIS"; 		//�ѱ���

	//��Ʊ��ת������SET_FP_ACTION 
	public static final String FAPIAOTRANSITION_ACTION_IN = "IN"; 		//��Ʊ����
	public static final String FAPIAOTRANSITION_ACTION_OUT = "OUT"; 	//��Ʊ����
	public static final String FAPIAOTRANSITION_ACTION_BACK = "BACK";   //��Ʊ�ؿ�
	public static final String FAPIAOTRANSITION_ACTION_CANN = "CANN"; 	//��Ʊ����
	public static final String FAPIAOTRANSITION_ACTION_INOUT = "INOUT"; //��Ʊ���뼴����
	public static final String FAPIAOTRANSITION_ACTION_CANC = "CANC";   //��Ʊ����
	public static final String FAPIAOTRANSITION_ACTION_DISCA = "DISCA"; //��Ʊ����
	public static final String FAPIAOTRANSITION_ACTION_MO = "MO";		//��Ʊ�
	public static final String FAPIAOTRANSITION_ACTION_PRNT = "PRNT";	//��Ʊ��ӡ
	public static final String FAPIAOTRANSITION_ACTION_REDR = "REDR";	//��Ʊ�ؿ�
	public static final String FAPIAOTRANSITION_ACTION_REPR = "REPR";	//��Ʊ�ش�
	public static final String FAPIAOTRANSITION_ACTION_RESP = "RESP";	//��Ʊ��ϵ�޸�
	//��Ʊ���Ѽ�¼���� SET_FP_SRCTYPE
	public static final String FAPIAO_PSTYPE_D = "D";	//��Ʊ��ӡ
	public static final String FAPIAO_PSTYPE_P = "P";	//Ԥ��
	public static final String FAPIAO_PSTYPE_C = "C";	//֧��
	public static final String FAPIAO_PSTYPE_CSIP = "CSIP";	//����֧��
	//��Ʊ��ӡʶ�������� SET_FP_PINTYPE
	public static final String FAPIAO_PINTYPE_ID = "ID";	//���֤��
	public static final String FAPIAO_PINTYPE_ORG = "ORG";	//��֯��������
	public static final String FAPIAO_PINTYPE_TAX = "TAX";	//��˰��ʶ���
	
	//Cabel Model��deviceClass
	public static final String DeviceClass_CM ="CM";
	
}
