package com.dtv.oss.service.util;

/**
 * common constant
 */
public class CommonConstDefinition {

	public static final String COMMAND_DEFAULT_STRING = " ";
	public static final double NEAR_ZERO = (double) Math.pow(Math.E, -10);

    //t_financialsetting
	public static final String FORCED_DEPOSIT_ACCTITEMTYPEID ="A000000";
	public static final String RETURN_DEVICE_ACCTITEMTYPEID  ="D000000";
	public static final String ACCTITEMTYPEID_PUNISHMENT = "N000000";

	//Separator of Customer information, used by Web to get CustInfoTree
	public static final String WEB_COMMON_SEPARATOR = ":";
	public static final boolean DEBUGMODE = true;
	public static final int SUCCESS = 0;
	public static final int FAIL = -1;
	public static final String STATUS_CANCELED = "C";
	//SET_W_JOBCARDCREATEMETHOD ����������Դ
	public static final String  JOBCARDCREATEMETHOD_A="A";   //�Զ�����
	public static final String  JOBCARDCREATEMETHOD_B="B";   //��������
	public static final String  JOBCARDCREATEMETHOD_M="M";   //�ֶ�����
	//SET_W_JOBCARDPAYSTATUS ��������״̬
	public static final String  JOBCARDPAYSTATUS_D="D";   //�Ѹ�
	public static final String  JOBCARDPAYSTATUS_R="R";   //�˷�
	public static final String  JOBCARDPAYSTATUS_W="W";   //δ��
	
	//SET_F_ADJUSTMENTREFERRECORDTYPE

	public static final String  ADJUSTMENT_REFERRE_CORD_TYPE_F="F";  //���ü�¼
	public static final String  ADJUSTMENT_REFERRE_CORD_TYPE_P="P";  //Ԥ���¼
	public static final String  ADJUSTMENT_REFERRE_CORD_TYPE_C="C";  //֧����¼
	public static final String  ADJUSTMENT_REFERRE_CORD_TYPE_D="D";  //�ֿۼ�¼

	//SET_F_ACCOUNTADJUSTMENTDIRECTION
	public static final String  ACCOUNT_ADJUSTMENTD_IRECTION_R="R";  //Ӧ��
	public static final String  ACCOUNT_ADJUSTMENTD_IRECTION_P="P";  //ʵ��

	//SET_F_ACCOUNTADJUSTTYPE
	public static final String ACCOUNT_ADJUST_TYPE_S="S";  //��������
	public static final String ACCOUNT_ADJUST_TYPE_M="M";  //�ֹ�����
	public static final String ACCOUNT_ADJUST_TYPE_I="I";  //���ʵ�����
	public static final String ACCOUNT_ADJUST_TYPE_C="C";  //ά�޵�����

	//SET_A_CATVTERMINALSTATUS
	public static final String CATVTERMINAL_STATUS_DESTROY = "D";	 //����
	public static final String CATVTERMINAL_STATUS_SJ = "I"; 		//��˺�˽��
	public static final String CATVTERMINAL_STATUS_NORMAL = "N"; 	//����
	public static final String CATVTERMINAL_STATUS_OUT = "O"; 		//Ǩ��ͣ��
	public static final String CATVTERMINAL_STATUS_KG = "R"; 		//�չ�
	public static final String CATVTERMINAL_STATUS_SUSPEND = "S"; 	//ͣ��
	public static final String CATVTERMINAL_STATUS_UNREG = "U"; 	//���ڲ�
/*
	//SET_F_FTSTATUS
	public static final String FTSOURCE_STATUS_NORMAL = "N";		//����
	public static final String FTSOURCE_STATUS_CANCEL = "C";		//ȡ��
	public static final String FTSOURCE_STATUS_LOCK="L";			//����
*/
	//SET_F_CURRENCY
	public static final String CURRENCY_RMB = "RMB";

	//definition for t_deviceclass
	public static final String DEVICECALSS_SMARTCARD = "SC";
	public static final String DEVICECALSS_STB = "STB";
	public static final String DEVICECALSS_CM = "CM";
	public static final String DEVICECALSS_IPP = "IPP";

	//SET_F_BRFEETYPE
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


	//SET_F_ACCOUNTADJUSTMENTREFRECORDTYPE
	public static final String ACCOUNTADJUSTMENTREFRECORDTYPE_F = "F";  //������

	//SET_V_CSIPAYMENTTYPE
	public static final String CSIPAYMENTTYPE_C = "C"; 					//�ֽ�
	public static final String CSIPAYMENTTYPE_V = "V";					//�ֿ�ȯ
	public static final String CSIPAYMENTTYPE_X = "X"; 					//ת��(�Ź���)

	//SET_F_PAYMENTRECORSOURCETYPE
	public static final String PAYMENTRECORSOURCETYPE_O = "O"; 			//���Ѽ�¼����Դ���ͣ�����
	public static final String PAYMENTRECORSOURCETYPE_M = "M";			//���Ѽ�¼����Դ���ͣ�����
	public static final String PAYMENTRECORSOURCETYPE_P = "P";			//���Ѽ�¼����Դ���ͣ����޵�

	//���ѷ�ʽPAYMENT MOPID
	public static final int PAYMENT_MOPID_CASH = 1; 					//�ֽ𸶷�
	public static final int PAYMENT_MOPID_TOKEN = 1001; 				//�ֿ�ȯ
	public static final int PAYMENT_MOPID_GROUP = 1002; 				//�Ź�ȯת��
	public static final int PAYMENT_MOPID_POS = 1003; 					//POS������
	public static final int PAYMENT_MOPID_CHEQUE = 1004; 				//֧Ʊ

	//t_methodofpayment(�ֹ�Ԥ��)
	public static final int METHOD_OF_PAYMENT_PREPAY = 7;


	//��������
	public static final int METHOD_OF_PAYMENT_ICBC = 102;



	//ͨ�����ݶ���,������ʱ�䳤�����͡����ڸ�ʽ��ͨ��״̬���Ƿ��ǡ����������ͣ� add & modify by zhouxushun , 2005-9-28
	//SET_G_TIMEUNITTYPE
	public static final String TIMEUNITTYPE_MONTH="M";              		//ʱ�䳤������:��
	public static final String TIMEUNITTYPE_QUARTER="Q";					//ʱ�䳤������:����
	public static final String TIMEUNITTYPE_HALF_YEAR="H";					//ʱ�䳤������:����
	public static final String TIMEUNITTYPE_YEAR="Y";						//ʱ�䳤������:��
	//SET_G_DATEFORMAT
	public static final String DATEFORMAT_COLON="1";						//���ڸ�ʽ:YYYY:MM:DD
	public static final String DATEFORMAT_DASH="2";							//���ڸ�ʽ:YYYY-MM-DD
	public static final String DATEFORMAT_NO_SIGN="2";						//���ڸ�ʽ:YYYYMMDD
	//SET_G_GENERALSTATUS
	public static final String GENERALSTATUS_VALIDATE="V";					//ͨ��״̬:��Ч
	public static final String GENERALSTATUS_INVALIDATE="I";				//ͨ��״̬:��Ч
	//SET_G_PROCESSSTATUS
	public static final String PROCESSSTATUS_SUCESS="S";					//ͨ�ô�����״̬:�ɹ�
	public static final String PROCESSSTATUS_FAIL="F";						//ͨ��״̬:ʧ��
	//SET_G_YESNOFLAG
	public static final String YESNOFLAG_YES="Y";							//�Ƿ���:��
	public static final String YESNOFLAG_NO="N";							//�Ƿ��ǣ���
	//SET_G_DISTRICTTYPE
	public static final String DISTRICTTYPE_PROVINCE="P";					//����������:ʡ��ֱϽ��
	public static final String DISTRICTTYPE_CITY="C";						//����������:�ء���
	public static final String DISTRICTTYPE_TOWN="T";						//����������:������
	public static final String DISTRICTTYPE_STATION="S";					//����������:�ֵ�վ


	//�ͻ�������Ϣ,����:�ͻ��Ա𡢿ͻ�״̬���ͻ���Դ���͡��˻���ַ��Ϣ�Ƿ���ȷ���ͻ����͡�
	//֧�����ͣ����Լ��ſͻ���Ч����                                           add & modify by zhouxushun,2005-9-28
	//SET_C_CUSTOMERGENDER
	public static final String CUSTOMERGENDER_MALE="M";						//�ͻ��Ա�:��
	public static final String CUSTOMERGENDER_FEMALE="F";					//�ͻ��Ա�Ů
	//SET_C_CUSTOMERSTATUS
	public static final String CUSTOMER_STATUS_POTENTIAL = "P";				//�ͻ�״̬��Ǳ��
	public static final String CUSTOMER_STATUS_NORMAL = "N";				//�ͻ�״̬������
	public static final String CUSTOMER_STATUS_CANCEL = "C";				//�ͻ�״̬��ȡ��
	//SET_C_CUSTOPENSOURCETYPE
	public static final String OPENSOURCETYPE_BRANCH = "O"; 				//�ͻ���Դ���ͣ��ŵ�
	public static final String OPENSOURCETYPE_STREETSTATION = "S"; 			//�ͻ���Դ���ͣ��ֵ�վ
	public static final String OPENSOURCETYPE_PROXY = "P"; 					//�ͻ���Դ���ͣ��ֹ�˾������
	public static final String OPENSOURCETYPE_DEPARTMENT = "D"; 			//�ͻ���Դ���ͣ�����
	public static final String OPENSOURCETYPE_TELEPHONESALE = "T"; 			//�ͻ���Դ���ͣ��绰����
	public static final String OPENSOURCETYPE_CALLCENTER = "C"; 			//�ͻ���Դ���ͣ�������������
	public static final String OPENSOURCETYPE_GROUPBARGAIN = "G"; 			//�ͻ���Դ���ͣ��Ź�����
	public static final String OPENSOURCETYPE_SUPERMARKET = "M"; 			//�ͻ���Դ���ͣ���������
	public static final String OPENSOURCETYPE_RETAIL = "X"; 				//�ͻ���Դ���ͣ��ֳ���̯����
	public static final String OPENSOURCETYPE_CABLEPROXY = "Y"; 			//�ͻ���Դ���ͣ�����ͨ����
	public static final String OPENSOURCETYPE_JOBPROXY = "H"; 				//�ͻ���Դ���ͣ���ҵ����
	public static final String OPENSOURCETYPE_HOUSEMATCHED = "F"; 			//�ͻ���Դ���ͣ���������
	public static final String OPENSOURCETYPE_COMPANYSEND = "R"; 			//�ͻ���Դ���ͣ�����Ԥ��Ȩ
	public static final String OPENSOURCETYPE_A = "A"; 						//�ͻ���Դ���ͣ�������
	//SET_C_BILLADDRESSFLAG
	public static final String BILLADDRESSFLAG_YES= "Y";		 			//�û����˻���ַ��Ϣ�Ƿ���ȷ����
	public static final String BILLADDRESSFLAG_NO= "N"; 					//�û����˻���ַ��Ϣ�Ƿ���ȷ����
	public static final String BILLADDRESSFLAG_UNSURE= "U"; 				//�û����˻���ַ��Ϣ�Ƿ���ȷ����ȷ��
	//SET_C_CUSTOMERTYPE
	public static final String CUSTOMERTYPE_BRANCH = "B"; 					//�ͻ����ͣ��ŵ��û�
	public static final String CUSTOMERTYPE_ENTERPRISE = "E"; 				//�ͻ����ͣ���ҵ�ͻ�
	public static final String CUSTOMERTYPE_FOREIGN = "F"; 					//�ͻ����ͣ��⼮�û�
	public static final String CUSTOMERTYPE_MONITOR = "M"; 					//�ͻ����ͣ����ͻ�
	public static final String CUSTOMERTYPE_NORMAL = "N"; 					//�ͻ����ͣ���ͨ�ͻ�
	public static final String CUSTOMERTYPE_SURVEY = "Q"; 					//�ͻ����ͣ��г�����
	public static final String CUSTOMERTYPE_SERVICE = "S"; 					//�ͻ����ͣ������û�
	public static final String CUSTOMERTYPE_TEST = "T"; 					//�ͻ����ͣ�����ͻ�
	public static final String CUSTOMERTYPE_VIP = "V"; 						//�ͻ����ͣ�VIP�ͻ�
	public static final String CUSTOMERTYPE_GROUP = "G"; 					//�ͻ����ͣ����ſͻ�
	
	//SET_C_CUSTOMERSTYLE
	public static final String CUSTOMERSTYLE_SINGLE = "S"; 					//�ͻ����ͣ����˿ͻ�
	public static final String CUSTOMERSTYLE_GROUP = "G"; 					//�ͻ����ͣ����ſͻ�
	//SET_C_CUSTOMERPAYTYPE
	public static final String CUSTOMERPAYTYPE_PERSON = "P"; 				//֧�����ͣ����Լ��ſͻ���Ч��������֧��
	public static final String CUSTOMERPAYTYPE_COMBINE= "C"; 				//֧�����ͣ����Լ��ſͻ���Ч��������֧��


	//�ͻ���Ʒ��������״̬���ͻ���Ʒ״̬���ͻ�ҵ���ʺ�״̬���Ƿ���Լ��ʱ�����ţ�    add & modify by zhouxushun ,2005-9-28
	//SET_C_CPCMSTATUS
	//�ϳ�����,��ͨ��״̬ ��Ч/��Ч �� 2007-4-18
//	public static final String CPCM_STATUS_NEW = "N";						//CPCAMPAIGNMAP��Ʒ�Ż�ӳ��״̬:�½�
//	public static final String CPCM_STATUS_CANCEL = "C";					//CPCAMPAIGNMAP��Ʒ�Ż�ӳ��״̬:ȡ��
//	public static final String CPCM_STATUS_VALID = "V";						//CPCAMPAIGNMAP��Ʒ�Ż�ӳ��״̬����Ч
//	public static final String CPCM_STATUS_INVALID = "I";					//CPCAMPAIGNMAP��Ʒ�Ż�ӳ��״̬��ʧ��
	//SET_M_CUSTOMERCAMPAIGNSTATUS
	public static final String CUSTOMERCAMPAIGNSTATUS_CANCEL = "C";			//�ͻ������״̬��ȡ��
	public static final String CUSTOMERCAMPAIGNSTATUS_NEW = "N";			//�ͻ������״̬������
	public static final String CUSTOMERCAMPAIGNSTATUS_NORMAL = "V";			//�ͻ������״̬����Ч
	public static final String CUSTOMERCAMPAIGNSTATUS_INVALID = "I";		//�ͻ������״̬��ʧЧ
	public static final String CUSTOMERCAMPAIGNSTATUS_TRANSFE = "T";		//�ͻ������״̬����ת��
	//SET_C_CUSTOMERPRODUCTPSTATUS
	public static final String CUSTOMERPRODUCTP_STATUS_INIT="I";			//�ͻ���Ʒ״̬����ʼ
	public static final String CUSTOMERPRODUCTP_STATUS_NORMAL="N";			//�ͻ���Ʒ״̬������
	public static final String CUSTOMERPRODUCTP_STATUS_SYSTEMSUSPEND="S";	//�ͻ���Ʒ״̬��ϵͳ��ͣ
	public static final String CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND="H";	//�ͻ���Ʒ״̬��������ͣ
	public static final String CUSTOMERPRODUCTP_STATUS_CANCEL = "C";		//�ͻ���Ʒ״̬��ȡ��
	public static final String CUSTOMERPRODUCTP_STATUS_MOVE = "M";		    //�ͻ���Ʒ״̬����ʱ״̬��Ǩ��
	public static final String CUSTOMERPRODUCTP_STATUS_BEFOREHANDCLOSE = "P";//�ͻ���Ʒ״̬��Ԥ�˻�
	//SET_C_CP_DEVICEPROVIDEFLAG
	public static final String CUSTOMERPRODUCT_DEVICEPROVIDEFLAG_B="B";			//�豸��Դ������
	public static final String CUSTOMERPRODUCT_DEVICEPROVIDEFLAG_R="R";			//�豸��Դ������
	public static final String CUSTOMERPRODUCT_DEVICEPROVIDEFLAG_F="F";			//�豸��Դ������
	public static final String CUSTOMERPRODUCT_DEVICEPROVIDEFLAG_S="S";			//�豸��Դ���Դ�
	
	//SET_C_SERVICEACCOUNTSTATUS
	public static final String SERVICEACCOUNT_STATUS_INIT = "I";			//�ͻ�ҵ���ʺ�״̬����ʼ
	public static final String SERVICEACCOUNT_STATUS_NORMAL="N";			//�ͻ�ҵ���ʺ�״̬������
	public static final String SERVICEACCOUNT_STATUS_SYSTEMSUSPEND = "S";	//�ͻ�ҵ���ʺ�״̬��ϵͳ��ͣ
	public static final String SERVICEACCOUNT_STATUS_REQUESTSUSPEND = "H";	//�ͻ�ҵ���ʺ�״̬��������ͣ
	public static final String SERVICEACCOUNT_STATUS_CANCEL = "C";			//�ͻ�ҵ���ʺ�״̬��ȡ��
	public static final String SERVICEACCOUNT_STATUS_BEFOREHANDCLOSE = "P";	//�ͻ�ҵ���ʺ�״̬��Ԥ�˻�
	//SET_C_CPONTIMEORNOT
	public static final String CPONTIMEORNOT_YES = "Y";						//Լ��ʱ�����ţ���
	public static final String CPONTIMEORNOT_NO= "N";						//Լ��ʱ�����ţ���
	public static final String CPONTIMEORNOT_OHTER= "O";					//Լ��ʱ�����ţ�����Ҫ
	//SET_R_PHONENOSTATUS �绰����״̬
	public static final String PHONENO_STATUS_NEW= "N";							//�½�
	public static final String PHONENO_STATUS_AVAILABLE= "A";					//����
	public static final String PHONENO_STATUS_USED= "U";						//����
	public static final String PHONENO_STATUS_INVALID= "I";						//����
	public static final String PHONENO_STATUS_LOCKED= "L";						//����
	//SET_R_PHONENOUESLOGACTION �绰����ʹ�ü�¼
	public static final String PHONENOUSELOG_ACTION_CREATE= "C";					//����
	public static final String PHONENOUSELOG_ACTION_MODIFY= "M";					//�޸�
	public static final String PHONENOUSELOG_ACTION_USED= "U";						//ռ��
	public static final String PHONENOUSELOG_ACTION_REUSE= "R";						//����
	public static final String PHONENOUSELOG_ACTION_XIAO= "X";						//ע��
	
	//�ͻ���ϵ�����������޵�״̬���������͡����ϼ��𡢱��޵�����Ķ������͡�
	//�շ��б����޵���ת��ԭ�򡢻طñ�־��������͡��ط�����                      add & modify by zhouxushun , 2005-9-28
	//SET_C_PROBLEMSTATUS
	public static final String CUSTOMERPROBLEM_STATUS_WAIT="W";					//���޵�״̬��������
	public static final String CUSTOMERPROBLEM_STATUS_PROCESSING="C";				//���޵�״̬�����ڴ���
	public static final String CUSTOMERPROBLEM_STATUS_SUCCESS="D";					//���޵�״̬��������
	//public static final String CUSTOMERPROBLEM_STATUS_UPGRADE="U";				//���޵�״̬��������
	public static final String CUSTOMERPROBLEM_STATUS_FAIL="S";					//���޵�״̬��ά�޲��ɹ�
	public static final String CUSTOMERPROBLEM_STATUS_TERMINAL="T";				//���޵�״̬���޷�����ά��
	public static final String CUSTOMERPROBLEM_STATUS_CANCEL="CN";				//���޵�״̬��ȡ��
	//SET_C_CPPROBLEMCATEGORY
	public static final String CPPROBLEMCATEGORY_SELF_INSTALL_FAIL="S";		//�������ͣ��԰�װʧ��
	public static final String CPPROBLEMCATEGORY_INSTALL_FAIL="W";			//�������ͣ����Ű�װһ��֮��
	public static final String CPPROBLEMCATEGORY_NORMAL="N";				//�������ͣ���ͨ����
	//SET_C_CPPROBLEMLEVEL
	public static final String CPPROBLEMLEVEL_SINGLE="S";					//���ϼ��𣺵������
	public static final String CPPROBLEMLEVEL_MUCH_AREA="M";				//���ϼ��𣺴��������
	public static final String CPPROBLEMLEVEL_PUZZLED="P";					//���ϼ���������֢
	//SET_C_CPPROCESSACTION ���޵�����Ķ�������
	public static final String CUSTPROBLEMPROCESS_ACTION_CREATE	= "W";	         //���� �������޵�---->������
	public static final String CUSTPROBLEMPROCESS_ACTION_ASSIGN = "A";	         //�ɹ� ������---->���ڴ���
	public static final String CUSTPROBLEMPROCESS_ACTION_SUCCESS = "E";	         //¼��ά����Ϣ ���ڴ���---->������
	public static final String CUSTPROBLEMPROCESS_ACTION_FAIL = "F";	         //����ά�޹��� ���ڴ���---->ά�޲��ɹ�
	public static final String CUSTPROBLEMPROCESS_ACTION_END = "S";	             //��ֹά�� ά�޲��ɹ�---->�޷�����ά��
	public static final String CUSTPROBLEMPROCESS_ACTION_TRANSFER = "WT";        //��������ת ������---->������
	public static final String CUSTPROBLEMPROCESS_ACTION_CANCEL = "R";           //ȡ��ά�޹��� ���ڴ���---->������ ������---->ȡ��
	public static final String CUSTPROBLEMPROCESS_ACTION_STOP = "M";             //�ֹ��������޵� ������---->������
	public static final String CUSTPROBLEMPROCESS_ACTION_RETRANSFER = "T";       //ά�޲��ɹ���ת ά�޲��ɹ�---->������
	public static final String CUSTPROBLEMPROCESS_ACTION_CALLBACK = "B";         //�ط�
	public static final String CUSTPROBLEMPROCESS_ACTION_CALLBACK_CACHE = "C";   //�ط��ݴ�
	public static final String CUSTPROBLEMPROCESS_ACTION_DIAGNOSIS = "D";
//	T_JOBCARD ����ԤԼ��� CONTACTRESULT
	public static final String JOBCARD_CONTACT_RESULT_RESOLVED_BY_PHONE 	= "G";		//�绰֧�ֹ����ų�
	public static final String JOBCARD_CONTACT_RESULT_HOUSE_REAPIR 				= "R";		//����ά��
	public static final String JOBCARD_CONTACT_RESULT_CANT_CONTACT_USER		= "C";		//�޷���ϵ�û�
	public static final String JOBCARD_CONTACT_RESULT_RESOLVED_BYSLEFPHONE		= "T";		//�绰��ϵ�Ѿ���
	public static final String JOBCARD_CONTACT_RESULT_REFUSE_PAYMENT	= "U";		//�û���Ը��֧������ȡ������
	//SET_C_CPFEECLA
	public static final String CPFEECLA_NORMAL="N";							//�շ��б�����ά��
	public static final String CPFEECLA_FEE="F";							//�շ��б��շ�ά��
	public static final String CPFEECLA_A="A";								//�շ��б�7�������б���
	//SET_C_CPCALLBACKFLAG
	public static final String CCPCALLBACKFLAG_YES="Y";						//�طñ�־���ѻط�
	public static final String CPCALLBACKFLAG_NO="N";						//�طñ�־��δ�ط�
	public static final String CPCALLBACKFLAG_T="T";						//�طñ�־���ط��ݴ�
	//SET_C_DIAGNOSISINFOREFERSOURCETYPE
	public static final String DIAGNOSISINFOTYPE_EARLY="E";					//������ͣ��������
	public static final String DIAGNOSISINFOTYPE_A="A";						//������ͣ�רҵ���
	//SET_C_CALLBACKINFOTYPE
	public static final String CALLBACKINFOTYPE_OPEN="O";					//�ط����ͣ������ط�
	public static final String CALLBACKINFOTYPE_REPAIR="R";					//�ط����ͣ����޻ط�
	public static final String CALLBACKINFOTYPE_C="C";						//�ط����ͣ�Ͷ�߻ط�
	//SET_C_CALLBACKINFOSETTINGVALUETYPE
	public static final String CALLBACKINFOSETTINGVALUETYPE_MANU="M";		//�ط�����ֵ���ͣ��ֹ�����
	public static final String CALLBACKINFOSETTINGVALUETYPE_SYSTEM="S";		//�ط�����ֵ���ͣ�ϵͳ����
	//SET_C_SENDMESSAGESENDTYPE
	public static final String SENDMESSAGESENDTYPE_SYSTEM="S";				//������Ϣ���ͣ�ͨ�������з���
	public static final String SENDMESSAGESENDTYPE_EMAIL="E";				//������Ϣ���ͣ�ͨ��Email����
	//SET_C_SENDMESSAGESOURCETYPE
	public static final String SENDMESSAGESOURCETYPE_MANU="M";				//��Ϣ������Դ���ֹ�������Ϣ
	public static final String SENDMESSAGESOURCETYPE_SYSTEM="S";			//��Ϣ������Դ��ϵͳ������Ϣ
	//SET_C_SENDMESSAGESTATUS
	public static final String SENDMESSAGESTATUS_NEW="N";					//��Ϣ״̬���½�
	public static final String SENDMESSAGESTATUS_CANCEL="C";				//��Ϣ״̬��ȡ��
	public static final String SENDMESSAGESTATUS_PROCESSED="P";				//��Ϣ״̬���Ѵ���


	//ҵ�������������ͻ���Ʒ���������ҵ�����������͡�״̬�仯ԭ�򡢰�װ���͡�
	//���õ�֧��״̬��ҵ��������״̬����������������ͻ�������ԭ��Ѻ���״̬��
	//Ѻ��Ľ����Ű�װ/ά�޵�ʱ���                                           add & modify by zhouxushun , 2005-9-28
	//SET_V_CSICUSTPRODUCTINFOACTION
	public static final String CSICUSTPRODUCTINFO_ACTION_OPEN = "O";			//�ͻ���Ʒ����������¿���
	public static final String CSICUSTPRODUCTINFO_ACTION_NEWPRODUCT = "N";	//�ͻ���Ʒ�����������Ʒ����
	public static final String CSICUSTPRODUCTINFO_ACTION_CANCEL = "C";		//�ͻ���Ʒ�����������Ʒȡ��
	public static final String CSICUSTPRODUCTINFO_ACTION_ASCEND = "A";		//�ͻ���Ʒ�����������Ʒ����
	public static final String CSICUSTPRODUCTINFO_ACTION_DESCEND = "D";		//�ͻ���Ʒ�����������Ʒ����
	public static final String CSICUSTPRODUCTINFO_ACTION_CHANGEACCOUNT = "F";//�ͻ���Ʒ����������ı��Ʒ�ĸ����˻�
	public static final String CSICUSTPRODUCTINFO_ACTION_PAUSE = "P";		//�ͻ���Ʒ�����������Ʒ��ͣ
	public static final String CSICUSTPRODUCTINFO_ACTION_RESUME = "R";		//�ͻ���Ʒ�����������Ʒ�ָ�
	public static final String CSICUSTPRODUCTINFO_ACTION_S = "S";			//�ͻ���Ʒ����������豸����
	//SET_V_CUSTSERVICEINTERACTIONTYPE
	
	public static final String CUSTSERVICEINTERACTIONTYPE_GO = "GO"; 		//ҵ�����������ͣ����ſͻ�����
	public static final String CUSTSERVICEINTERACTIONTYPE_GS = "GS"; 		//ҵ�����������ͣ��ӿͻ�����
	public static final String CUSTSERVICEINTERACTIONTYPE_GM = "GM"; 		//ҵ�����������ͣ��ӿͻ�ת���˿ͻ�	

	public static final String CUSTSERVICEINTERACTIONTYPE_CO = "CO";        //ҵ�����������ͣ��ͻ����������ϵ���¼�룩
	public static final String CUSTSERVICEINTERACTIONTYPE_BK = "BK"; 		//ҵ�����������ͣ�ԤԼ
	public static final String CUSTSERVICEINTERACTIONTYPE_OS = "OS"; 		//ҵ�����������ͣ��ŵ꿪��
	public static final String CUSTSERVICEINTERACTIONTYPE_OB = "OB"; 		//ҵ�����������ͣ�ԤԼ����
//	public static final String CUSTSERVICEINTERACTIONTYPE_OD = "OD"; 		//ҵ�����������ͣ����ſ���
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
	public static final String CUSTSERVICEINTERACTIONTYPE_OSA = "OSA";		//ҵ�����������ͣ��û�����
	public static final String CUSTSERVICEINTERACTIONTYPE_DS = "DS"; 		//ҵ�����������ͣ��豸����
	public static final String CUSTSERVICEINTERACTIONTYPE_DU = "DU"; 		//ҵ�����������ͣ��豸����
	public static final String CUSTSERVICEINTERACTIONTYPE_CP = "CP";		//ҵ�����������ͣ�ȡ���ͻ���Ʒ
	public static final String CUSTSERVICEINTERACTIONTYPE_PM = "PM";		//ҵ�����������ͣ�Ǩ�ƿͻ���Ʒ
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
	public static final String CUSTSERVICEINTERACTIONTYPE_UR = "UR";		//ҵ�����������ͣ��ָ��û�
	public static final String CUSTSERVICEINTERACTIONTYPE_UC = "UC";		//ҵ�����������ͣ��û�����
	public static final String CUSTSERVICEINTERACTIONTYPE_SP = "SP";		//ҵ�����������ͣ��û�Ԥ�˻�
	public static final String CUSTSERVICEINTERACTIONTYPE_RC = "RC";		//ҵ�����������ͣ��û�ʵ�˻�
	public static final String CUSTSERVICEINTERACTIONTYPE_UT = "UT";		//ҵ�����������ͣ��û�����
	public static final String CUSTSERVICEINTERACTIONTYPE_BP = "BP";		//ҵ�����������ͣ�ԤԼ�����ͻ���Ʒ
	public static final String CUSTSERVICEINTERACTIONTYPE_BDS = "BDS";		//ҵ�����������ͣ��ײ�ת��    
	public static final String CUSTSERVICEINTERACTIONTYPE_BDP = "BDP";		//ҵ�����������ͣ��ײ�Ԥ��
	public static final String CUSTSERVICEINTERACTIONTYPE_BDE = "BDE";		//ҵ�����������ͣ��ײ�����
	public static final String CUSTSERVICEINTERACTIONTYPE_BDC = "BDC";		//ҵ�����������ͣ��ײ�ȡ��
	public static final String CUSTSERVICEINTERACTIONTYPE_CAA = "CAA";		//ҵ�����������ͣ�ģ��ҵ������
	public static final String CUSTSERVICEINTERACTIONTYPE_CAO = "CAO";		//ҵ�����������ͣ�ģ��ҵ�񿪻�
	public static final String CUSTSERVICEINTERACTIONTYPE_CAS = "CAS";		//ҵ�����������ͣ�ģ�����ҵ��ͣ��
	public static final String CUSTSERVICEINTERACTIONTYPE_CAR = "CAR";		//ҵ�����������ͣ�ģ�����ҵ��ָ�
	public static final String CUSTSERVICEINTERACTIONTYPE_CAM = "CAM";		//ҵ������������: Ǯ������
	public static final String CUSTSERVICEINTERACTIONTYPE_CAC = "CAC";		//ҵ�����������ͣ�Ǯ����ֵ
	public static final String CUSTSERVICEINTERACTIONTYPE_MAC = "MAC";		//ҵ�����������ͣ��ֹ��������
	public static final String CUSTSERVICEINTERACTIONTYPE_MB = "MB";		//ҵ�����������ͣ��ֹ��շ�
	
	
	//SET_V_CUSTSERVICEINTERACTIONSTATUSREASON
	public static final String CSI_STATUSREASON_M = "M"; 					//״̬�仯ԭ��������ͣ
	public static final String CSI_STATUSREASON_P = "P"; 					//״̬�仯ԭ���û�Ͷ����ͣ
	public static final String CSI_STATUSREASON_O = "O"; 					//״̬�仯ԭ��Ǩ������
	public static final String CSI_STATUSREASON_A = "A"; 					//״̬�仯ԭ��˫������δ����
	public static final String CSI_STATUSREASON_B = "B"; 					//״̬�仯ԭ���ظ�ԤԼ
	public static final String CSI_STATUSREASON_C = "C"; 					//״̬�仯ԭ�򣺶Խ�Ŀ���ݲ���������װ
	public static final String CSI_STATUSREASON_D = "D"; 					//״̬�仯ԭ��������ϲ�ȫ
	public static final String CSI_STATUSREASON_E = "E"; 					//״̬�仯ԭ����ʱ�ı����������װ
	public static final String CSI_STATUSREASON_F = "F"; 					//״̬�仯ԭ������
	public static final String CSI_STATUSREASON_U = "U"; 					//״̬�仯ԭ�����Ǹ���
	//SET_V_CUSTSERVICEINTERACTIONINSTYPE
	public static final String CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL = "S";    //��װ���ͣ��԰�װ
	public static final String CUSTSERVICEINTERACTIONIN_STYPE_INSTALL = "C";        //��װ���ͣ����Ű�װ
	
	//ҵ���˻���������
	public static final String SA_TRANFER_TYPE_I="I";						//ϵͳ��ҵ���ʻ�����
	public static final String SA_TRANFER_TYPE_O="O";						//ϵͳ��ҵ���ʻ�����
	//ҵ���ʻ�״̬
	public static final String SA_STATUS_NORMAL="N";						//����
	public static final String SA_STATUS_INITIAL="I";						//��ʼ
	public static final String SA_STATUS_CANCEL="C";						//ȡ��
	public static final String SA_STATUS_SYSPAUSE="S";						//ϵͳ��ͣ
	public static final String SA_STATUS_PAUSE="H";						    //������ͣ

	//SET_V_CUSTSERVICEINTERACTIONPAYSTATUS
	public static final String CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY = "W";   //���õ�֧��״̬��δ��
	public static final String CUSTSERVICEINTERACTION_PAYSTATUS_PAYED = "D";        //���õ�֧��״̬���Ѹ�
	public static final String CUSTSERVICEINTERACTION_PAYSTATUS_RETURNMONEY = "R";  //���õ�֧��״̬���˷�
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
	//SET_V_CSIPROCESSLOGACTION
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
	public static final String CSIPROCESSLOG_ACTION_INSTALL = "IB";           		//���������������װԤԼ
	public static final String CSIPRPCESSLOG_ACTION_PROCESS ="P";                   //����������������ڽ���
	

	//SET_V_CC_CUSTSERVICEINTERACTIONSTATUSREASON
	public static final String CSI_CC_STATUSREASON_CA ="CA";						//�ͻ�������ԭ�������������ȶ�
	public static final String CSI_CC_STATUSREASON_CB ="CB";						//�ͻ�������ԭ�򣺰�Ǩ��˫����δ�������
	public static final String CSI_CC_STATUSREASON_CC ="CC";						//�ͻ�������ԭ�򣺼�������ʹ��
	public static final String CSI_CC_STATUSREASON_CD ="CD";						//�ͻ�������ԭ�򣺵�ǰ�ʷѲ�����
	public static final String CSI_CC_STATUSREASON_CE ="CE";						//�ͻ�������ԭ�򣺷�������������
	public static final String CSI_CC_STATUSREASON_CF ="CF";						//�ͻ�������ԭ�򣺷��ݳ���
	public static final String CSI_CC_STATUSREASON_CG ="CG";						//�ͻ�������ԭ������ȱ��������
	public static final String CSI_CC_STATUSREASON_CI ="CI";						//�ͻ�������ԭ�򣺰�װ��ַ���˾�ס
	public static final String CSI_CC_STATUSREASON_CN ="CN";						//�ͻ�������ԭ������
	//SET_V_FDSTATUS
	public static final String FDSTATUS_CREATE = "N"; 								//Ѻ���״̬��δ��
	public static final String FDSTATUS_BACK = "Y"; 								//Ѻ���״̬������
	public static final String FDSTATUS_CONFISCATE = "S"; 							//Ѻ���״̬��û��
	public static final String FDSTATUS_PART_CONFISCATE = "P"; 						//Ѻ���״̬������û��
	//SET_V_FDVALUE
	public static final String FDVALUE_ZERO= "0";							//Ѻ��Ľ�0
	public static final String FDVALUE_ONEONEOO="1100";						//Ѻ��Ľ�1100
	//SET_C_CSIPREFEREDTIME
	public static final String CSIPREFEREDTIME_AM ="A";						//���Ű�װ/ά�޵�ʱ��Σ�����
	public static final String CSIPREFEREDTIME_PM ="P";						//���Ű�װ/ά�޵�ʱ��Σ�����
	public static final String CSIPREFEREDTIME_ALL_DAY ="E";				//���Ű�װ/ά�޵�ʱ��Σ�ȫ��
	//SET_V_CSISCHEDULEACTION
	public static final String CSISCHEDULEACTION_PAUSE= "P";				//�ų��������ͣ���ͣ
	public static final String CSISCHEDULEACTION_RESUME="R";				//�ų��������ͣ��ָ�


	//�ʻ����ϣ��������ʻ�״̬���ʻ�����ʻ����͡�����״̬��Ԥ������͡��������͡�    add & modify by zhouxushun , 2005-9-29
	//SET_F_ACCOUNTSTATUS
	public static final String ACCOUNT_STATUS_NORMAL = "N";					//�ʻ�״̬������
	public static final String ACCOUNT_STATUS_CLOSE = "C"; 					//�ʻ�״̬���ر�
	public static final String ACCOUNT_STATUS_OWNED = "O"; 					//�ʻ�״̬��һ��Ƿ��
	public static final String ACCOUNT_STATUS_OWNEDAGAIN = "T"; 			//�ʻ�״̬������Ƿ��
	//SET_F_ACCOUNTCLASS
	public static final String ACCOUNTCLASS_CUSTOMER = "C";					//�ʻ���𣺿ͻ��ʻ�
	public static final String ACCOUNTCLASS_PARTNER = "P";					//�ʻ���𣺺�������ʻ�
	public static final String ACCOUNTCLASS_SMS = "S";						//�ʻ����SMS�ʻ�
	//SET_F_ACCOUNTTYPE
	public static final String ACCOUNTTYPE_NORMAL = "N";					//�ʻ����ͣ���ͨ�ʻ�
	public static final String ACCOUNTTYPE_PREPAID = "P";					//�ʻ����ͣ�Ԥ�����ʻ�
	//SET_F_ACCOUNTBANKACCOUNTSTATUS
	public static final String ACCOUNTBANKACCOUNT_STATUS_CANCEL = "C"; 		//����״̬������ȡ��
	public static final String ACCOUNTBANKACCOUNT_STATUS_SUCCESS = "S"; 	//����״̬������ɹ�
	public static final String ACCOUNTBANKACCOUNT_STATUS_UNSUCCESS = "U"; 	//����״̬�����䲻�ɹ�
	public static final String ACCOUNTBANKACCOUNT_STATUS_WAIT = "W"; 		//����״̬��������
	//SET_F_PREPAYMENTTYPE
	public static final String PREPAYMENTTYPE_CASH = "C";					//Ԥ������ͣ��ֽ�
	public static final String PREPAYMENTTYPE_TRANSLUNARY="T";				//Ԥ������ͣ��������
	//SET_F_MOPPAYTYPE
	public static final String MOPPAYTYPE_CREDITCARD = "CR";				//�������ͣ����ÿ�
	public static final String MOPPAYTYPE_ONLINE = "OL";					//�������ͣ����߸���
	public static final String MOPPAYTYPE_PAYBYBANK = "BP";					//�������ͣ����пۿ�
	public static final String MOPPAYTYPE_CASH = "CH";						//�������ͣ��ֽ�
	public static final String MOPPAYTYPE_OTHER = "OT";						//�������ͣ�����
	public static final String MOPPAYTYPE_CD = "CD";						//�������ͣ��۾ɵֿ�


	//�������ݣ��������ʵ�״̬���ʵ���Դ��������-����״̬��������-FT��Դ��
	//������-FT�ʵ���ǡ�������-FT���ʱ�ǡ��ֿۼ�¼��֧����¼-֧��ȯ���͡�
	//֧����¼-���Ѽ�¼�����͡�֧����¼-֧����¼��Դ���͡�����-״̬��
	//����-�ۺ�������Դ���͡����ʼ�¼-���ʱ�ǡ����ʼ�¼-���ʼ�¼���͡�
	//���ʼ�¼-���ʹ�����¼���͡�                                                   add & modify by zhouxushun , 2005-9-29
	//SET_F_INVOICESTATUS
	public static final String INVOICE_STATUS_WAIT = "W";					//�ʵ�״̬��������
	public static final String INVOICE_STATUS_CANCEL = "C";					//�ʵ�״̬��ȡ��
	public static final String INVOICE_STATUS_OWE = "O";					//�ʵ�״̬������
	public static final String INVOICE_STATUS_PAID = "D";					//�ʵ�״̬���Ѿ�����
	public static final String INVOICE_STATUS_BAD = "B";					//�ʵ�״̬������ע��
	public static final String INVOICE_STATUS_BADLOCK = "L";				//�ʵ�״̬������������

	public static final String INVOICE_STATUS_RETURNMONEY = "R";			//�ʵ�״̬���˿�
	public static final String INVOICE_STATUS_QFZT = "T";					//�ʵ�״̬��Ƿ��׷��
	public static final String INVOICE_STATUS_NORMAL = "N";					//�ʵ�״̬������
	//SET_F_INVOICESOURCETYPE
	public static final String INVOICESOURCETYPE_M = "M"; 					//�ʵ���Դ����������
	public static final String INVOICESOURCETYPE_A = "A"; 					//�ʵ���Դ�����ʲ���
	public static final String INVOICESOURCETYPE_O = "O"; 					//�ʵ���Դ����������
	public static final String INVOICESOURCETYPE_P = "P"; 					//�ʵ���Դ�����޵�����
	//SET_F_FTSTATUS
	public static final String AISTATUS_VALIDATE = "V"; 					//������-����״̬����Ч
	public static final String AISTATUS_INVALIDATE = "I"; 					//������-����״̬����Ч
	public static final String AISTATUS_LOCK = "L"; 						//������-����״̬������
	public static final String AISTATUS_POTENTIAL = "P"; 					//������-����״̬��Ԥ����
	public static final String AISTATUS_RETURN = "R"; 					    //������-����״̬���˻�
	public static final String AISTATUS_DELETE = "D"; 					    //������-����״̬��û��
	public static final String AISTATUS_VALIDATE1 = "1"; 					    //������-����״̬���˻�
	public static final String AISTATUS_VALIDATE2 = "3"; 					    //������-����״̬��û��
	
//SET_F_FTSOURCETYPE
	public static final String FTSOURCETYPE_PAYBYCUSTOMER = "P";			//������-FT��Դ���ͻ�����
	public static final String FTSOURCETYPE_INSTANTFEE = "D";				//������-FT��Դ��һ���Է���
	public static final String FTSOURCETYPE_PERIODACCOUNTING = "A";			//������-FT��Դ�������Գ��ʲ���
	public static final String FTSOURCETYPE_TIAOZHANG = "T";				//������-FT��Դ���˹��������
	public static final String FTSOURCETYPE_PUNISHMENT = "O";				//������-FT��Դ�����ɽ�
	public static final String FTSOURCETYPE_OPENACCOUNT = "S";				//������-FT��Դ������Ԥ��
	public static final String FTSOURCETYPE_NEXT = "N";						//������-FT��Դ������ת
	public static final String FTSOURCETYPE_CANCEL = "C";					//������-FT��Դ���˻���
	//SET_F_FTINVOICEDFLAG
	public static final String FTINVOICEDFLAG_YES = "Y";					//������-FT�ʵ���ǣ��ѳ������ʵ���
	public static final String FTINVOICEDFLAG_NO = "N";						//������-FT�ʵ���ǣ�δ�������ʵ���
	//ET_F_FTACCOUNTEDFLAG
	public static final String FTACCOUNTEDFLAG_YES = "Y";					//������-FT���ʱ�ǣ��Ѽ����ʻ����
	public static final String FTACCOUNTEDFLAG_NO = "N";					//������-FT���ʱ�ǣ�δ�����ʻ����
	//SET_F_DEDUCTIONRECORDTYPE
	public static final String DEDUCTIONRECORDTYPE_CASH = "C";				//�ֿۼ�¼��Ԥ�����ñ��ʵ��ۿ�
	public static final String DEDUCTIONRECORDTYPE_TRANSLUNARY = "T";		//�ֿۼ�¼��Ԥ���ֽ��ʵ��ۿ�
	//SET_F_PAYMENTTICKETTY
	public static final String PAYMENTTICKETTY_DK = "DK";					//֧����¼-֧��ȯ���ͣ��ֿ�ȯ
	public static final String PAYMENTTICKETTY_TG = "TG";					//֧����¼-֧��ȯ���ͣ��Ź�ȯ
	//SET_F_PAYMENTRECORDTYPE
	public static final String PAYMENTRECORD_TYPE_PRESAVE = "P";            //֧����¼-���Ѽ�¼�����ͣ��ͻ�Ԥ����
	public static final String PAYMENTRECORD_TYPE_ACCEPT_CASE = "C";		//֧����¼-���Ѽ�¼�����ͣ�֧���������
	public static final String PAYMENTRECORD_TYPE_BILLING = "N"; 			//֧����¼-���Ѽ�¼�����ͣ��ͻ��ʵ�����
	public static final String PAYMENTRECORD_TYPE_RETURN_FEE = "RF";		//֧����¼-���Ѽ�¼�����ͣ������˷�
	public static final String PAYMENTRECORD_TYPE_RETURN_RR = "RR";			//֧����¼-���Ѽ�¼�����ͣ�Ԥ���˷�
	//2005-12-30 Start modified by Wesley Shu
	public static final String PAYMENTRECORD_TYPE_RETURN_PREFEE = "RR";     //֧����¼-���Ѽ�¼�����ͣ�Ԥ���˷�
	//end

	/*
	public static final String PAYMENTRECORD_TYPE_ONINVOICE = "N"; 			//֧����¼-���Ѽ�¼�����ͣ��ֽ���
	public static final String PAYMENTRECORD_TYPE_TPRESAVE = "TP"; 			//֧����¼-���Ѽ�¼�����ͣ��������Ԥ����
	public static final String PAYMENTRECORD_TYPE_PUTINTO = "X"; 			//֧����¼-���Ѽ�¼�����ͣ�ת��(�Ź���)
	public static final String PAYMENTRECORD_TYPE_TONINVOICE = "TN"; 		//֧����¼-���Ѽ�¼�����ͣ�������Ҹ��ʵ�
	public static final String PAYMENTRECORD_TYPE_RETURNMONEY = "D"; 		//֧����¼-���Ѽ�¼�����ͣ����
	public static final String PAYMENTRECORD_TYPE_TOKENINVOICE = "CN"; 		//֧����¼-���Ѽ�¼�����ͣ��ֿ�ȯ���ʵ�
	public static final String PAYMENTRECORD_TYPE_TOKENPRESAVE = "CP"; 		//֧����¼-���Ѽ�¼�����ͣ��ֿ�ȯԤ����
	*/
	//SET_F_PAYMENTRECORDSOURCETYPE
	public static final String PAYMENTRECORDSOURCETYPE_OTHER = "O";			//֧����¼-֧����¼��Դ���ͣ�����
	public static final String PAYMENTRECORDSOURCETYPE_REPAIR = "P";		//֧����¼-֧����¼��Դ���ͣ����޵�
	public static final String PAYMENTRECORDSOURCETYPE_ACCEPT = "M"; 		//֧����¼-֧����¼��Դ���ͣ�����
	public static final String PAYMENTRECORDSOURCETYPE_ADJUST = "T";        //֧����¼-֧����¼��Դ���ͣ�����
	public static final String PAYMENTRECORDSOURCETYPE_JOBCARD = "J";        //֧����¼-֧����¼��Դ���ͣ�����
	public static final String PAYMENTRECORDSOURCETYPE_FUTURERIGHT = "R";        //��Ȩ��ֵ
	//SET_F_AASTATUS
	public static final String AASTATUS_UNSTAT = "0";						//����-״̬��δͳ��
	public static final String AASTATUS_STAT = "1";							//����-״̬����ͳ��
	public static final String AASTATUS_CANCLE = "2"; 						//����-״̬��ȡ��
	//SET_F_FTREFERTYPE
	public static final String FTREFERTYPE_M = "M"; 						//����-�ۺ�������Դ���ͣ���������
	public static final String FTREFERTYPE_A = "A"; 						//����-�ۺ�������Դ���ͣ����ʲ���
	public static final String FTREFERTYPE_P = "P"; 						//����-�ۺ�������Դ���ͣ����޵�
	public static final String FTREFERTYPE_F = "F"; 						//��Ȩ��ֵ
	public static final String FTREFERTYPE_J = "J"; 						//����-�ۺ�������Դ���ͣ�����
	//SET_F_SETOFFFLAG
	public static final String SETOFFFLAG_W = "W"; 							//���ʼ�¼-���ʱ�ǣ�������
	public static final String SETOFFFLAG_P = "P"; 							//���ʼ�¼-���ʱ�ǣ���������
	public static final String SETOFFFLAG_D = "D"; 							//���ʼ�¼-���ʱ�ǣ�������
	//SET_F_SETOFFTYPE
	public static final String SETOFFTYPE_P = "P"; 							//���ʼ�¼-���ʼ�¼���ͣ�ֱ������
	public static final String SETOFFTYPE_D = "D"; 							//���ʼ�¼-���ʼ�¼���ͣ�Ԥ������
	//2005-12-27 start added by Wesley Shu
	public static final String SETOFFTYPE_F = "F"; 					        //���ʼ�¼-���ʼ�¼���ͣ�ǿ��Ԥ��
	//2005-12-27 end added by Wesley Shu

	//SET_F_SETOFFREFERTYPE
	public static final String SETOFFREFERTYPE_M = "M"; 					//���ʼ�¼-���ʹ�����¼���ͣ����޵�
	public static final String SETOFFREFERTYPE_I = "I"; 					//���ʼ�¼-���ʹ�����¼���ͣ��ʵ�
	public static final String SETOFFREFERTYPE_C = "C"; 					//���ʼ�¼-���ʹ�����¼���ͣ�����
	public static final String SETOFFREFERTYPE_F = "F"; 					//���ʼ�¼-���ʹ�����¼���ͣ�����
	public static final String SETOFFREFERTYPE_D = "D"; 					//���ʼ�¼-���ʹ�����¼���ͣ��ֿۼ�¼
	public static final String SETOFFREFERTYPE_P = "P"; 					//���ʼ�¼-���ʹ�����¼���ͣ�֧��
	public static final String SETOFFREFERTYPE_J = "J"; 					//���ʼ�¼-���ʹ�����¼���ͣ�����
	//2005-12-16 start added by Wesley Shu
	//SET_F_FTCREATINGMETHOD
	public static final String FTCREATINGMETHOD_A = "A"; 					//���ô�����Դ���ͣ������Ʒ�
	public static final String FTCREATINGMETHOD_B = "B"; 					//���ô�����Դ���ͣ������Ʒ�
	public static final String FTCREATINGMETHOD_I = "I"; 					//���ô�����Դ���ͣ��ӿڲ���
	public static final String FTCREATINGMETHOD_R = "R"; 					//���ô�����Դ���ͣ������˷�
	public static final String FTCREATINGMETHOD_X = "X"; 					//���ô�����Դ���ͣ����ݿ⵼��
	public static final String FTCREATINGMETHOD_T = "T";                    //���ô�����Դ���ͣ����ʲ���
	public static final String FTCREATINGMETHOD_M = "M";					//���ô�����Դ����: �ֹ�
	//2005-12-16 end added by Wesley Shu

	//2005-12-21 start added by Wesley Shu
	//SET_F_PREPAYMENTDEDUCTIONMODE
	public static final String PREPAYMENTDEDUCTIONMODE_C = "C"; 			//Ԥ��ֿ۷�ʽ���ֽ�����
	public static final String PREPAYMENTDEDUCTIONMODE_P = "P"; 			//Ԥ��ֿ۷�ʽ���������ֿ�
	public static final String PREPAYMENTDEDUCTIONMODE_T = "T"; 			//Ԥ��ֿ۷�ʽ�������������
	//2005-12-21 end added by Wesley Shu

	//SET_F_PDR_REFERRECORDTYPE
	public static final String F_PDR_REFERRECORDTYPE_C="C";              //��Դ��¼���ͣ�����
	public static final String F_PDR_REFERRECORDTYPE_F="F";              //��Դ��¼���ͣ�������
	public static final String F_PDR_REFERRECORDTYPE_I="I";              //��Դ��¼���ͣ��ʵ�
	public static final String F_PDR_REFERRECORDTYPE_P="P";              //��Դ��¼���ͣ����޵�
	public static final String F_PDR_REFERRECORDTYPE_J="J";              //��Դ��¼���ͣ�����
	//*************************************************
	//* ��ʱû�ж���Ĺ�������ģ���ǣ�
	//* 3.3.3	������
	//* 3.3.4	�Ʒ��������ò�����
	//* 3.3.5	��ʷ�������ò���
	//*************************************************/


	//��Ʒ��������ҵ��״̬����Ʒ״̬����Ʒ��𡢲�Ʒ��״̬����Ʒ�������͡�
	//��Ʒ����ȡֵ��ʽ��ҵ��������ϵ����Ʒ������ϵ���͡���Ʒ�Ʒ�ģʽ��
	//��Ʒ����ȡֵ��Դ���͡�ҵ����Դȡֵ���͡�ҵ����Դ��ϸ��¼״̬��                 add & modify by zhouxushun , 2005-9-29
	//SET_P_SERVICESTATUS
	public static final String SERVICESTATUS_NEW = "R";						//ҵ��״̬������
	public static final String SERVICESTATUS_NORMAL = "N";					//ҵ��״̬������
	public static final String SERVICESTATUS_CANCEL = "C";					//ҵ��״̬��ֹͣ
	public static final String SERVICESTATUS_TERMINAL = "T";				//ҵ��״̬����ֹ
	//SET_P_PRODUCTSTATUS
	public static final String PRODUCTSTATUS_NEW = "R";						//��Ʒ״̬:����
	public static final String PRODUCTSTATUS_NORMAL = "N";					//��Ʒ״̬:����
	public static final String PRODUCTSTATUS_CANCEL = "C";					//��Ʒ״̬:ֹͣ
	public static final String PRODUCTSTATUS_TERMINAL = "T";				//��Ʒ״̬:��ֹ
	//SET_P_PRODUCTCLASS
	public static final String PRODUCTCLASS_HARD = "H";						//��Ʒ���Ӳ����Ʒ
	public static final String PRODUCTCLASS_SOFTWARE = "S";					//��Ʒ��������Ʒ
	//SET_P_PACKAGESTATUS
	public static final String PACKAGESTATUS_NEW = "R";						//��Ʒ��״̬������
	public static final String PACKAGESTATUS_NORMAL = "N";					//��Ʒ��״̬������
	public static final String PACKAGESTATUS_CANCEL = "C";					//��Ʒ��״̬��ֹͣ
	public static final String PACKAGESTATUS_TERMINAL = "T";				//��Ʒ��״̬����ֹ
	//SET_P_PRODUCTPROPERTYTYPE
	public static final String PRODUCTPROPERTYTYPE_INTEGER = "I";			//��Ʒ�������ͣ�����
	public static final String PRODUCTPROPERTYTYPE_STRING = "S";			//��Ʒ�������ͣ��ַ���
	public static final String PRODUCTPROPERTYTYPE_OTHER = "O";				//��Ʒ�������ͣ�����
	public static final String PRODUCTPROPERTYTYPE_DOUBLE = "F";				//��Ʒ�������ͣ�������
	//SET_P_PRODUCTPROPERTYMODE
	public static final String PRODUCTPROPERTYMODE_F = "F";					//��Ʒ����ȡֵ��ʽ���̶�
	public static final String PRODUCTPROPERTYMODE_C = "C";					//��Ʒ����ȡֵ��ʽ��������
	//SET_P_SERVICEDEPENDENCYTYPE
	public static final String SERVICEDEPENDENCYTYPE_DEPENDENCE = "D";		//ҵ��������ϵ������
	public static final String SERVICEDEPENDENCYTYPE_EXCLUDE = "C";			//ҵ��������ϵ���ų�
	//SET_P_PRODUCTDEPENDENCYTYPE
	public static final String PRODUCTDEPENDENCYTYPE_D = "D";				//��Ʒ������ϵ���� ����Ȩ����
	public static final String PRODUCTDEPENDENCYTYPE_P = "P";				//��Ʒ������ϵ���� ����������
	public static final String PRODUCTDEPENDENCYTYPE_C = "C";				//��Ʒ������ϵ���� ���ų�
	public static final String PRODUCTDEPENDENCYTYPE_A = "A";				//��Ʒ������ϵ���� ������
	public static final String PRODUCTDEPENDENCYTYPE_F = "F";				//��Ʒ������ϵ���� ������
	//SET_P_PRODUCTBILLINGMODE
	public static final String PRODUCTBILLINGMODE_U = "U";					//��Ʒ�Ʒ�ģʽ�������Ʒ�
	public static final String PRODUCTBILLINGMODE_F = "F";					//��Ʒ�Ʒ�ģʽ���̶���
	//SET_P_PRODUCTPROPERTYVALUESOURCETYPE
	public static final String PRODUCTPROPERTYVALUESOURCETYPE_P = "P";		//��Ʒ����ȡֵ��Դ���ͣ���������
	public static final String PRODUCTPROPERTYVALUESOURCETYPE_R = "R";		//��Ʒ����ȡֵ��Դ���ͣ�ҵ����Դ
	//SET_P_SERVICERESOURCEVALUETYPE
	public static final String SERVICERESOURCEVALUETYPE_STRING = "S";		//��Ʒ����ȡֵ��Դ���ͣ���������
	public static final String SERVICERESOURCEVALUETYPE_INTEGER = "I";		//��Ʒ����ȡֵ��Դ���ͣ�ҵ����Դ
	//SET_P_SERVICERESOURCEDETAILSTATUS
	public static final String SERVICERESOURCEDETAILSTATUS_N = "N";			//ҵ����Դ��ϸ��¼״̬���½�
	public static final String SERVICERESOURCEDETAILSTATUS_A = "A";			//ҵ����Դ��ϸ��¼״̬������
	public static final String SERVICERESOURCEDETAILSTATUS_U = "U";			//ҵ����Դ��ϸ��¼״̬������
	public static final String SERVICERESOURCEDETAILSTATUS_I = "I";			//ҵ����Դ��ϸ��¼״̬������


	//�������̣��������������͡�������״̬���������͡�����ԭ�򡢽���ֶΡ����������
	//����ԤԼ�������������������ɹ���ԭ��δ�ܰ��չ涨��ʱ��Ӧ���ŵ�ԭ�򡢴���  add & modify by zhouxushun , 2005-9-29
	//SET_W_JOBCARDTYPE
	public static final String JOBCARD_TYPE_REPAIR = "R";					//�������ͣ�ά��
	public static final String JOBCARD_TYPE_INSTALLATION = "I";	            //�������ͣ���װ
	public static final String JOBCARD_TYPE_CATV = "C";                     //��������: ģ���������ʩ��
	//SET_W_JOBCARDSTATUS
	public static final String JOBCARD_STATUS_WAIT = "W";					//������״̬��������
	public static final String JOBCARD_STATUS_BOOKED = "B";					//������״̬����ԤԼ
	public static final String JOBCARD_STATUS_SUCCESS = "S";					//������״̬������ɹ�
	public static final String JOBCARD_STATUS_FAIL = "F";					//������״̬�������ɹ�
	public static final String JOBCARD_STATUS_TERMINAL = "T";				//������״̬���޷���������
	public static final String JOBCARD_STATUS_CANCEL = "C";					//������״̬��ȡ��
	public static final String JOBCARD_STATUS_CANCEL_ACCEPT = "C1";			//������״̬������ȡ��
	public static final String JOBCARD_STATUS_C_INSTALL = "C2";				//������״̬����װȡ��
	//SET_W_JOBCARDTROUBLETYPE
	public static final String JOBCARDTROUBLETYPE_A = "A";					//�������ͣ��豸����
	public static final String JOBCARDTROUBLETYPE_B = "B";					//�������ͣ������źŹ���
	public static final String JOBCARDTROUBLETYPE_C = "C";					//�������ͣ������źŹ���
	public static final String JOBCARDTROUBLETYPE_D = "D";					//�������ͣ��û�����ȡ��
	public static final String JOBCARDTROUBLETYPE_E = "E";					//�������ͣ��Զ��ָ�
	public static final String JOBCARDTROUBLETYPE_F = "F";					//�������ͣ��û���������
	public static final String JOBCARDTROUBLETYPE_G = "G";					//�������ͣ�����
	//SET_W_JOBCARDTROUBLESUBTYPE
	public static final String JOBCARDTROUBLESUBTYPE_A = "A";				//����ԭ�����ܿ�
	public static final String JOBCARDTROUBLESUBTYPE_B = "B";				//����ԭ�򣺻�����
	public static final String JOBCARDTROUBLESUBTYPE_C = "C";				//����ԭ��ң����
	public static final String JOBCARDTROUBLESUBTYPE_D = "D";				//����ԭ��������·
	public static final String JOBCARDTROUBLESUBTYPE_E = "E";				//����ԭ��������·
	public static final String JOBCARDTROUBLESUBTYPE_F = "F";				//����ԭ�򣺲�������
	//SET_W_JOBCARDRESOLUTIONTYPE
	public static final String JOBCARDRESOLUTIONTYPE_A = "A";				//����ֶΣ������豸
	public static final String JOBCARDRESOLUTIONTYPE_B = "B";				//����ֶΣ�������·
	public static final String JOBCARDRESOLUTIONTYPE_C = "C";				//����ֶΣ���װ����
	public static final String JOBCARDRESOLUTIONTYPE_D = "D";				//����ֶΣ�Ұ���豸����
	public static final String JOBCARDRESOLUTIONTYPE_E = "E";				//����ֶΣ��Զ��ָ�
	public static final String JOBCARDRESOLUTIONTYPE_F = "F";				//����ֶΣ�ȡ������
	public static final String JOBCARDRESOLUTIONTYPE_G = "G";				//����ֶΣ�����
	//SET_W_JOBCARDMATERIALUSAGE
	public static final String JOBCARDMATERIALUSAGE_A = "A";				//�����������
	public static final String JOBCARDMATERIALUSAGE_B = "B";				//�����������������
	public static final String JOBCARDMATERIALUSAGE_C = "C";				//�����������װ����
	//SET_W_JOBCARDCONTACTRESULT
	public static final String JOBCARDCONTACTRESULT_G = "G";				//����ԤԼ������绰֧�ֹ����ų�
	public static final String JOBCARDCONTACTRESULT_R = "R";				//����ԤԼ���������ά��
	public static final String JOBCARDCONTACTRESULT_C = "C";				//����ԤԼ������޷���ϵ�û�
	public static final String JOBCARDCONTACTRESULT_T = "T";				//����ԤԼ������绰��ϵ�Ѻ�
	public static final String JOBCARDCONTACTRESULT_U = "U";				//����ԤԼ������û���Ը֧������ȡ������
	//SET_W_JOBWORKRESULT
	public static final String JOBCARD_WORKRESULT_SUCCESS = "S";					//�������������ɹ�
	public static final String JOBCARD_WORKRESULT_FAIL = "F";					//�������������ʧ��
	//SET_W_JOBRESULTREASON
	public static final String JOBRESULTREASON_A = "A";						//�����ɹ���ԭ�򣺲�Ը��װ����
	public static final String JOBRESULTREASON_B = "B";						//�����ɹ���ԭ�򣺲�Ը������
	public static final String JOBRESULTREASON_C = "C";						//�����ɹ���ԭ��˫������δ����
	public static final String JOBRESULTREASON_D = "D";						//�����ɹ���ԭ�������밲װ
	public static final String JOBRESULTREASON_E = "E";						//�����ɹ���ԭ����������
	public static final String JOBRESULTREASON_F = "F";						//�����ɹ���ԭ��������ϲ�ȫ
	public static final String JOBRESULTREASON_G = "G";						//�����ɹ���ԭ���������
	public static final String JOBRESULTREASON_H = "H";						//�����ɹ���ԭ�򣺸���
	public static final String JOBRESULTREASON_O = "O";						//�����ɹ���ԭ���û�������װ
	public static final String JOBRESULTREASON_P = "P";						//�����ɹ���ԭ�򣺶��豸����������
	public static final String JOBRESULTREASON_Q = "Q";						//�����ɹ���ԭ�򣺶Խ�Ŀ���ݲ�����
	public static final String JOBRESULTREASON_R = "R";						//�����ɹ���ԭ�򣺶Ի�ʷ����߲�����
	public static final String JOBRESULTREASON_S = "S";						//�����ɹ���ԭ������
	public static final String JOBRESULTREASON_A1 = "A1";					//�����ɹ���ԭ���û���Ը��װ����
	public static final String JOBRESULTREASON_B1 = "B1";					//�����ɹ���ԭ�򣺷ǹ�˾���η�Χ�ڹ���
	public static final String JOBRESULTREASON_C1 = "C1";					//�����ɹ���ԭ���û���Ըά��
	public static final String JOBRESULTREASON_D1 = "D1";					//�����ɹ���ԭ���豸�����޷�������
	public static final String JOBRESULTREASON_E1 = "E1";					//�����ɹ���ԭ��С�������źŸ����޷����
	public static final String JOBRESULTREASON_F1 = "F1";					//�����ɹ���ԭ�򣺹��Ϸ�������
	public static final String JOBRESULTREASON_G1 = "G1";					//�����ɹ���ԭ��תר����
	public static final String JOBRESULTREASON_H1 = "H1";					//�����ɹ���ԭ���û��������Ҫ���޷�����
	//SET_W_JOBOUTOFDATEREASON
	public static final String JOBOUTOFDATEREASON_A = "A";					//δ�ܰ��չ涨��ʱ��Ӧ���ŵ�ԭ���û�����
	public static final String JOBOUTOFDATEREASON_B = "B";					//δ�ܰ��չ涨��ʱ��Ӧ���ŵ�ԭ���޷���ϵ�û�
	public static final String JOBOUTOFDATEREASON_C = "C";					//δ�ܰ��չ涨��ʱ��Ӧ���ŵ�ԭ����������
	public static final String JOBOUTOFDATEREASON_D = "D";					//δ�ܰ��չ涨��ʱ��Ӧ���ŵ�ԭ����ά����������޷�����
	public static final String JOBOUTOFDATEREASON_E = "E";					//δ�ܰ��չ涨��ʱ��Ӧ���ŵ�ԭ��תר����
	public static final String JOBOUTOFDATEREASON_F = "F";					//δ�ܰ��չ涨��ʱ��Ӧ���ŵ�ԭ������
	//SET_W_JOBCARDLOGTYPE
	public static final String JOBCARDLOGTYPE_BOOKING = "B";				//��������ԤԼ
	public static final String JOBCARDLOGTYPE_DROPIN_WORK = "W";			//�����������Ź���
	public static final String JOBCARDLOGTYPE_END = "C";					//������������
	public static final String JOBCARDLOGTYPE_ABORT = "A";					//��������ȡ��
	public static final String JOBCARDLOGTYPE_NEW = "N";					//������������
	//�ݶ�
	public static final String JOBCARDPROCESS_ACTION_CREATE ="W";		//��������---->������
	public static final String JOBCARDPROCESS_ACTION_CANCEL ="A";		//������---->ȡ��
	public static final String JOBCARDPROCESS_ACTION_MODIFY ="M";       //������---->�޸�
	
	//public static final String JOBCARDPROCESS_ACTION_TELEPHONE ="G";		//������---->����ɹ� �����ڵ绰����
	public static final String JOBCARDPROCESS_ACTION_BOOKING ="B";		//������---->��ԤԼ
	public static final String JOBCARDPROCESS_ACTION_TRANSFER ="T";		//������---->������
	public static final String JOBCARDPROCESS_ACTION_SUCCESS ="S";		//��ԤԼ---->����ɹ�
	//public static final String JOBCARDPROCESS_ACTION_FAIL ="F";			//��ԤԼ---->����ʧ��
	public static final String JOBCARDPROCESS_ACTION_END ="C";			//����ʧ��---->�޷���������
	public static final String JOBCARDPROCESS_ACTION_REBOOKING ="RB";	//����ʧ��---->��ԤԼ

	//�豸����������Ӳ���豸״̬��Ӳ����ַ���͡�Ӳ�����۷�ʽ��Ӳ���Ƿ�Ϊ���ֻ���
	//�豸��ת�Ķ������͡��豸�ͺ�״̬�������ٶȡ��豸��תԭ���豸��������ԭ��
	//�豸�ͺŲ������͡�                                                       add & modify by zhouxushun , 2005-9-29
	//SET_D_DEVICESTATUS
	public static final String DEVICE_STATUS_STOCK = "S";					//Ӳ���豸״̬�����
	public static final String DEVICE_STATUS_WAITFORSELL = "W";				//Ӳ���豸״̬������
	public static final String DEVICE_STATUS_LOCK = "L";					//Ӳ���豸״̬������
	public static final String DEVICE_STATUS_SOLD = "C";					//Ӳ���豸״̬�����ۣ������޸�Ϊ������
	public static final String DEVICE_STATUS_WAIT4REPAIR = "R";				//Ӳ���豸״̬������
	public static final String DEVICE_STATUS_REPAIRING = "M";				//Ӳ���豸״̬������
	public static final String DEVICE_STATUS_DISCARD = "O";					//Ӳ���豸״̬������
	public static final String DEVICE_STATUS_LOST = "I";					//Ӳ���豸״̬����ʧ
	public static final String DEVICE_STATUS_OFFLINE = "X";					//Ӳ���豸״̬������
	//SET_D_DEVICEADDRESSTYPE
	public static final String DEVICE_ADDRESSTYPE_DEPOT = "D";				//Ӳ����ַ���ͣ��ֿ�
	public static final String DEVICE_ADDRESSTYPE_WITHUSER = "B";			//Ӳ����ַ���ͣ��û�
	public static final String DEVICE_ADDRESSTYPE_INORGANIZATION = "T";		//Ӳ����ַ���ͣ���֯����
	//SET_D_DEVICESELLMETHOD
	public static final String DEVICESELLMETHOD_LEASEHOLD = "L";			//Ӳ�����۷�ʽ������
	public static final String DEVICESELLMETHOD_SALE = "B";					//Ӳ�����۷�ʽ������
	//SET_D_USEFLAG
	public static final String DEVICE_USEFLAG_USED = "U";					//Ӳ���Ƿ�Ϊ���ֻ������豸
	public static final String DEVICE_USEFLAG_NEW = "N";					//Ӳ���Ƿ�Ϊ���ֻ������豸
	//SET_D_DTACTION
	public static final String DEVICE_TRANSACTION_ACTION_N = "N";			//�豸��ת�Ķ������ͣ����豸���
	public static final String DEVICE_TRANSACTION_ACTION_R = "R";			//�豸��ת�Ķ������ͣ��豸����
	public static final String DEVICE_TRANSACTION_ACTION_B = "B";			//�豸��ת�Ķ������ͣ��豸����
	public static final String DEVICE_TRANSACTION_ACTION_O = "O";			//�豸��ת�Ķ������ͣ��豸����
	public static final String DEVICE_TRANSACTION_ACTION_T = "T";			//�豸��ת�Ķ������ͣ��豸�������
	public static final String DEVICE_TRANSACTION_ACTION_H = "H";			//�豸��ת�Ķ������ͣ��豸�˻�
	public static final String DEVICE_TRANSACTION_ACTION_G = "G";			//�豸��ת�Ķ������ͣ��豸����
	public static final String DEVICE_TRANSACTION_ACTION_I = "I";			//�豸��ת�Ķ������ͣ�����
	public static final String DEVICE_TRANSACTION_ACTION_L = "L";			//�豸��ת�Ķ������ͣ���ʧ
	public static final String DEVICE_TRANSACTION_ACTION_M = "M";			//�豸��ת�Ķ������ͣ��ֹ��޸��豸״̬
	public static final String DEVICE_TRANSACTION_ACTION_A = "A";			//�豸��ת�Ķ������ͣ���ع���
	public static final String DEVICE_TRANSACTION_ACTION_E = "E";			//�豸��ת�Ķ������ͣ�ͬ��ַ����
	public static final String DEVICE_TRANSACTION_ACTION_P = "P";			//�豸��ת�Ķ������ͣ��豸����
	public static final String DEVICE_TRANSACTION_ACTION_X = "X";			//�豸��ת�Ķ������ͣ��豸��Ϣ�޸�
	public static final String DEVICE_TRANSACTION_ACTION_J = "J";			//�豸��ת�Ķ������ͣ��豸����
	
	public static final String DEVICE_TRANSACTION_ACTION_DM = "DM";			//�豸��ת�Ķ������ͣ��豸���
	public static final String DEVICE_TRANSACTION_ACTION_DP = "DP";			//�豸��ת�Ķ������ͣ��豸Ԥ��Ȩ
	public static final String DEVICE_TRANSACTION_ACTION_DU = "DU";			//�豸��ת�Ķ������ͣ��豸�����
	
	//SET_D_DEVICEMODELSTATUS
	public static final String DEVICEMODELSTATUS_NORMAL = "N";				//�豸�ͺ�״̬������
	public static final String DEVICEMODELSTATUS_NEW = "R";					//�豸�ͺ�״̬������
	public static final String DEVICEMODELSTATUS_CANCEL = "C";				//�豸�ͺ�״̬��ȡ��
	public static final String DEVICEMODELSTATUS_TERMINATE = "T";			//�豸�ͺ�״̬��ֹͣ
	//SET_D_CABLETYPE
	public static final String CABLETYPE_C8 = "C8";							//�����ٶȣ�860M
	public static final String CABLETYPE_C7 = "C7";							//�����ٶȣ�750M
	//SET_D_TRANSFERREASON
	public static final String DEVICE_TRANSFERREASON_STOCK = "S";			//�豸��תԭ�򣺿��
	public static final String DEVICE_TRANSFERREASON_MEND = "M";			//�豸��תԭ��ά��
	//SET_D_STOCKINREASON
	public static final String DEVICE_STOCKINREASON_DAMNIFY = "D";			//�豸��������ԭ����
	public static final String DEVICE_STOCKINREASON_BACK = "B";				//�豸��������ԭ���˻�
	public static final String DEVICE_STOCKINREASON_REPAIR = "R";			//�豸��������ԭ���޸�
	//SET_D_DEVICEMODELPROPERTYVALUETYPE
	public static final String DEVICEMODELPROPERTYVALUETYPE_INTEGER = "I";	//�豸�ͺŲ������ͣ�����
	public static final String DEVICEMODELPROPERTYVALUETYPE_STRING = "S";	//�豸�ͺŲ������ͣ��ַ���
	public static final String DEVICEMODELPROPERTYVALUETYPE_OTHER = "O";	//�豸�ͺŲ������ͣ�����
	public static final String DEVICEMODELPROPERTYVALUETYPE_DOUBLE = "F";	//�豸�ͺŲ������ͣ�������


	//�г����������Ź�ȯͷ��Ϣ״̬���Ź�����ϸ��Ϣ״̬���ͻ������״̬��
	//�����ʱ�䳤�����͡����������                                     add & modify by zhouxushun , 2005-9-29
	//SET_M_GROUPBARGAINSTATUS
	public static final String GROUPBARGAINSTATUS_NEW = "N";				//�Ź�ȯͷ��Ϣ״̬���½�
	public static final String GROUPBARGAINSTATUS_SOLD = "S";				//�Ź�ȯͷ��Ϣ״̬���۳�
	public static final String GROUPBARGAINSTATUS_CANCEL = "C";				//�Ź�ȯͷ��Ϣ״̬��ȡ��
	//SET_M_GROUPBARGAINDETAILSTATUS
	public static final String GROUPBARGAINDETAILSTATUS_WAIT = "W";			//�Ź�����ϸ��Ϣ״̬������
	public static final String GROUPBARGAINDETAILSTATUS_SOLD = "S";			//�Ź�����ϸ��Ϣ״̬���۳�
	public static final String GROUPBARGAINDETAILSTATUS_RETURN = "R";		//�Ź�����ϸ��Ϣ״̬������
	public static final String GROUPBARGAINDETAILSTATUS_CANCEL = "C";		//�Ź�����ϸ��Ϣ״̬������
	public static final String GROUPBARGAINDETAILSTATUS_LOCK = "L";			//�Ź�����ϸ��Ϣ״̬������
	//SET_M_CAMPAIGNTIMELENGTHUNITTYPE
	public static final String CAMPAIGNTIMELENGTHUNITTYPE_YEAR = "Y";		//�����ʱ�䳤�����ͣ���
	public static final String CAMPAIGNTIMELENGTHUNITTYPE_MONTH = "M";		//�����ʱ�䳤�����ͣ���
	public static final String CAMPAIGNTIMELENGTHUNITTYPE_DAY = "D";		//�����ʱ�䳤�����ͣ���
	//SET_M_CAMPAIGNCAMPAIGNTYPE
	public static final String CAMPAIGNCAMPAIGNTYPE_OPEN = "B";					//��������ͣ��ײ�
	public static final String CAMPAIGNCAMPAIGNTYPE_NORMAL = "A";				//��������ͣ���ͨ����
	//public static final String CAMPAIGNCAMPAIGNTYPE_GROUPBARGAIN = "T";			//��������ͣ��Ź��ײ�
	//public static final String CAMPAIGNCAMPAIGNTYPE_GROUPBARGAIN_OR_OPEN = "O";	//��������ͣ��Ź��򿪻��ײ�

	//ϵͳ������������Ա���Ӧ�ļ��𡢽ڵ����͡���־������־��Ϣ����֯��ɫ      add & modify by zhouxushun , 2005-9-29
	//SET_S_OPGROUPLEVEL
	public static final String OPGROUPLEVEL_COMPANY = "C";					//����Ա���Ӧ�ļ����ܹ�˾����Ա��
	public static final String OPGROUPLEVEL_FILIALE = "B";					//����Ա���Ӧ�ļ��𣺷ֹ�˾����Ա��
	public static final String OPGROUPLEVEL_STATICON = "S";					//����Ա���Ӧ�ļ��𣺽ֵ�վ����Ա��
	//SET_S_ORGANIZATIONORGTYPE
	public static final String ORGANIZATIONORGTYPE_GENERALCOMPANY = "C"; 	//�ڵ����ͣ��ܹ�˾
	public static final String ORGANIZATIONORGTYPE_DEPARTMENT = "D"; 		//�ڵ����ͣ�����
	public static final String ORGANIZATIONORGTYPE_SUBCOMPANY = "B"; 		//�ڵ����ͣ��ֹ�˾
	public static final String ORGANIZATIONORGTYPE_PARTNER = "P"; 			//�ڵ����ͣ��������
	public static final String ORGANIZATIONORGTYPE_BRANCH = "O"; 			//�ڵ����ͣ��ŵ�
	public static final String ORGANIZATIONORGTYPE_STREETSTATION = "S"; 	//�ڵ����ͣ��ֵ�վ
	//��ʱû�������������
	//public static final String ORGANIZATIONORGSUBTYPE_AGENT = "S"; 			//�ڵ����ͣ�������

	//SET_S_LOGCLASS
	public static final String LOGCLASS_NORMAL = "N"; 						//��־����һ�������Ϣ
	public static final String LOGCLASS_CRUX = "C"; 						//��־���𣺹ؼ�������Ϣ
	public static final String LOGCLASS_WARN = "W"; 						//��־���𣺾�����Ϣ
	public static final String LOGCLASS_ERROR = "E"; 						//��־���𣺴�����Ϣ
	//SET_S_LOGTYPE
	public static final String LOGTYPE_SYSTEM = "S"; 						//��־��Ϣ��ϵͳ
	public static final String LOGTYPE_APPLICATION = "A"; 					//��־��Ϣ��Ӧ�ó���
	//SET_S_ROLEORGNIZATIONORGROLE
	
	public static final String ROLEORGNIZATIONORGROLE_SERVICE = "S"; 		//��֯��ɫ��ά��
	public static final String ROLEORGNIZATIONORGROLE_INSTALL = "I"; 		//��֯��ɫ����װ
	public static final String ROLEORGNIZATIONORGROLE_TS = "C"; 			//��֯��ɫ��Ͷ�ߴ���
	public static final String ROLEORGANIZATIONORGROLE_REPAIRE = "R";		//��֯��ɫ: ����
	public static final String ROLEORGANIZATIONORGROLE_M = "M";		        //��֯��ɫ: ģ�����ʩ��������


	//��̨���̣���������̨����ִ��״̬���ų�����״̬���ų�����״̬���ų�����״̬��   add & modify by zhouxushun , 2005-9-29
	//SET_B_BGJOBSTATUS
	public static final String BGJOBSTATUS_NORMAL = "N"; 					//��̨����ִ��״̬���ȴ�ִ��
	public static final String BGJOBSTATUS_END = "D"; 						//��̨����ִ��״̬��ִ�н���
	public static final String BGJOBSTATUS_ERROR = "E"; 					//��̨����ִ��״̬��ִ�д���
	public static final String BGJOBSTATUS_PROCESS = "B"; 					//��̨����ִ��״̬������ִ��
	//SET_B_SCHEDULESTATUS
	public static final String SCHEDULESTATUS_NORMAL = "N"; 				//�ų�����״̬��������
	public static final String SCHEDULESTATUS_CANCEL = "C"; 				//�ų�����״̬����ȡ��
	public static final String SCHEDULESTATUS_SUCCESS = "S"; 				//�ų�����״̬������ʧ��
	public static final String SCHEDULESTATUS_FAIL = "F"; 					//�ų�����״̬������ɹ�
	//SET_B_BATCHJOBPROCESSACTION
	public static final String BATCHJOBPROCESSACTION_CREATE = "C"; 				//�ų�������״̬������
	public static final String BATCHJOBPROCESSACTION_MODIFY = "M"; 				//�ų�������״̬���޸�
	public static final String BATCHJOBPROCESSACTION_EXCUTE = "E"; 				//�ų�������״̬��ִ��
	public static final String BATCHJOBPROCESSACTION_CANCEL = "D"; 				//�ų�������״̬��ȡ��
	//SET_B_SCHEDULERESULT
	public static final String SCHEDULERESULT_SUCCESS = "S"; 				//�ų�����ִ�н�����ɹ�
	public static final String SCHEDULERESULT_FAIL = "F"; 					//�ų�����ִ�н����ʧ��


	//�ӿڣ����������нӿڴ������͡��ʵ���ӡ�ӿڴ������͡����пۿ�ؽ�����͡�   add & modify by zhouxushun , 2005-9-29
	//SET_I_PIFROCESSTYPE
	public static final String PIFROCESSTYPE_INPUT = "I"; 					//���нӿڴ������ͣ����пۿ�������
	public static final String PIFROCESSTYPE_OUTPUT = "O"; 					//���нӿڴ������ͣ����пۿ��ļ�����
	public static final String PIFROCESSTYPE_AO = "AO"; 					//���нӿڴ������ͣ������ʺź˲��ļ�����
	public static final String PIFROCESSTYPE_AI = "AI"; 					//���нӿڴ������ͣ������ʺź˲�������
	//SET_I_IPPROCESSTYPE
	public static final String IPPROCESSTYPE_INVOICE = "I"; 				//�ʵ���ӡ�ӿڴ������ͣ���ӡ�ʵ�
	public static final String IPPROCESSTYPE_PRESS_INVOICE = "D"; 			//�ʵ���ӡ�ӿڴ������ͣ���ӡ���ʵ�
	//SET_F_BDRSTATUS
	public static final String BDRSTATUS_E = "E"; 							//���пۿ�ؽ�����ͣ�������
	public static final String BDRSTATUS_F = "F"; 							//���пۿ�ؽ�����ͣ����пۿ�ɹ�
	public static final String BDRSTATUS_S = "S"; 							//���пۿ�ؽ�����ͣ����пۿ�ʧ��
	public static final String BDRSTATUS_SF = "SF"; 						//���пۿ�ؽ�����ͣ����пڿ�ɹ�������У��ʧ��
	public static final String BDRSTATUS_FF = "FF"; 						//���пۿ�ؽ�����ͣ����пۿ�ʧ��������У��ʧ��


	//*************************************************
	//* ��ʱû�ж���Ĺ�������ģ���ǣ�
	//* 3.11	���硢
	//* 3.12	ģ���ն˹���
	//* 3.3.5	��ʷ�������ò���
	//*************************************************/
	public static final String GENERALSTATUS_INVALID="I";                   //add by david.Yang

	//����ƥ�����   ,add by zhouxushun
	public static final String MATCH_SC_TO_STB_M="M";						//�������

	public static final String MATCH_SC_TO_STB_D="D";						//���������
	//�������� 2006-2-16 add by wesley
	public static final String MATCH_SE_CA_CLEARPWD="C";					//��������
	public static final String MATCH_SE_CA_SENDMAIL="S";					//������Ϣ

	//��̨������ģ�飬add by zhouxushun 2005-12-27
	//SET_B_BATCHJOBREFERTYPE
	public static final String BATCH_JOB_REFER_TYPE_C="C";					//����
	public static final String BATCH_JOB_REFER_TYPE_Q="Q";					//������ѯ
	public static final String BATCH_JOB_REFER_TYPE_U="U";					//��ģ�⵼��
	//SET_B_BATCHJOBTYPE
	public static final String BATCH_JOB_TYPE_AL1="AL1";					//����һ������
	public static final String BATCH_JOB_TYPE_AL2="AL2";					//������������
	public static final String BATCH_JOB_TYPE_AS="AS";						//����Ƿ����ͣ
	public static final String BATCH_JOB_TYPE_PC="PC";						//��Ʒȡ��
	public static final String BATCH_JOB_TYPE_PR="PR";						//��Ʒ�ָ�
	public static final String BATCH_JOB_TYPE_PS="PS";						//��Ʒ��ͣ
	public static final String BATCH_JOB_TYPE_SM="SM";						//���������ʼ�
	public static final String BATCH_JOB_TYPE_SO="SO";						//����������Ϣ
	public static final String BATCH_JOB_TYPE_UC="UC";						//�û�ȡ��
	public static final String BATCH_JOB_TYPE_UR="UR";						//�û��ָ�
	//SET_B_SCHEDULETYPE
	public static final String SCHEDULE_TYPE_I="I";							//����ִ��
	public static final String SCHEDULE_TYPE_S="S";							//��ʱִ��
	//SET_B_BATCHJOBSTATUS
	public static final String BATCH_JOB_STATUS_CANCEL="C";					//ȡ��
	public static final String BATCH_JOB_STATUS_FAIL="F";					//ִ��ʧ��
	public static final String BATCH_JOB_STATUS_WAIT="N";					//�ȴ�ִ��
	public static final String BATCH_JOB_STATUS_SUCESS="S";					//ִ�гɹ�
	//SET_B_BATCHJOBACTION----------->SET_B_BATCHJOBTYPE
	//SET_G_PROCESSSTATUS ----------->SET_B_BATCHJOBSTATUS
	//������̨������ģ��


	//������ѯ�ã�add by zhouxushun 2006-1-9
	//SET_B_QUERYTYPE
	public static final String QUERY_TYPE_CUSTOMER="C";						//�ͻ�
	public static final String QUERY_TYPE_ACCOUNT="A";						//�ʻ�
	public static final String QUERY_TYPE_PRODUCT="P";						//�ͻ���Ʒ
	public static final String QUERY_TYPE_CUTOMER_CAMPAING="M";				//�ͻ��Ż�
	
	//SET_B_QUERYSTATUS
	public static final String QUERY_STATUS_NEW="N";						//�½�
	public static final String QUERY_STATUS_PROCESSING="W";					//���ڴ���
	public static final String QUERY_STATUS_SUCCESS="S";					//����ɹ�
	public static final String QUERY_STATUS_FAIL="F";						//����ʧ��
	public static final String QUERY_STATUS_CANCEL="C";						//ȡ��
	//2006-1-24 BY WESLEY
	public static final String QUERY_STATUS_REFERED="I";					//������


	//SET_F_ACCOUNTADJUSTMENTSTATUS
	public static final String ADJUST_STATUS_CREATE ="W";                   //����
	public static final String ADJUST_STATUS_VALIDATION  ="V" ;             //��Ч
	public static final String ADJUST_STATUS_INVALIDATION ="I" ;             //��Ч

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
//	T_GroupBargain SET_M_GROUPBARGAINSTATUS�Ź�ȯͷ��Ϣ״̬ Status
	public static final String GROUPBARGAIN_STATUS_NEW				= "N";	//�½�		
	public static final String GROUPBARGAIN_STATUS_SALED			= "S";	//�۳�		
	public static final String GROUPBARGAIN_STATUS_CANCEL			= "C";	//ȡ��
	public static final String GROUPBARGAIN_STATUS_FINISH			= "F";	//���
//	SET_M_GROUPBARGAINDETAILSTATUS�Ź�����ϸ��Ϣ״̬ T_GroupBargainDetail	Status
	
	public static final String GROUPBARGAINDETAIL_STATUS_WAIT 		="W";	//����
	public static final String GROUPBARGAINDETAIL_STATUS_SALED 		="S";	//�۳�
	public static final String GROUPBARGAINDETAIL_STATUS_RECYCLE	="R";	//����
	public static final String GROUPBARGAINDETAIL_STATUS_CANCEL		="C";	//����
	public static final String GROUPBARGAINDETAIL_STATUS_LOCKED		="L";	//����
	public static final String SERVICEACCOUNTTRANSFER		="ST";	//ҵ���ʻ�����
	//�ͻ�Ͷ��
	//SET_C_CUSTOMERCOMPLAINPROCESSSTATUS
	public static final String CUSTCOMPLAIN_PROCESS_SUCESS 		="V";	//����ɹ�
	public static final String CUSTCOMPLAIN_PROCESS_FALI 		="F";	//����ʧ��
	public static final String CUSTCOMPLAIN_PROCESS_WAIT 		="W";	//������
	//SET_C_CUSTCOMPLAINPROCESSACTION
	public static final String CUSTCOMPLAIN_PROCESS_TR		="TR";	//��ת
	public static final String CUSTCOMPLAIN_PROCESS_CL		="CL";	//�ֹ�����
	public static final String CUSTCOMPLAIN_PROCESS_US 		="US";	//����ά��
	public static final String CUSTCOMPLAIN_PROCESS_AF		="AF";	//����
	public static final String CUSTCOMPLAIN_PROCESS_PS		="PS";	//�绰֧��
	public static final String CUSTCOMPLAIN_PROCESS_CD		="CD";	//�����豸
	public static final String CUSTCOMPLAIN_PROCESS_PA		="PA";	//�绰��ǫ
	public static final String CUSTCOMPLAIN_PROCESS_UA		="UA";	//������ǫ
	public static final String CUSTCOMPLAIN_PROCESS_CB		="CB";	//Ͷ�߻ط�
	public static final String CUSTCOMPLAIN_PROCESS_CR		="CR";	//�½�
	
	//SET_C_CUSTOMERCOMPLAINSTATUS
	public static final String CUSTOMER_COMPLAIN_SUCESS 		="V";	//����ɹ�
	public static final String CUSTOMER_COMPLAIN_FALI 		="F";	//����ʧ��
	public static final String CUSTOMER_COMPLAIN_WAIT 		="D";	//���ڴ���
	
	//��ͬ״̬:
	public static final String CONTRACTSTATUS_NEW="N"; 		//�½�
	public static final String CONTRACTSTATUS_EFFECT="E";	//��Ч
	public static final String CONTRACTSTATUS_OPEN="U";		//����
	public static final String CONTRACTSTATUS_CANCEL="C"; 	//ȡ��
	 
	public static final String CONTRACT_USED="U"; 	//ʹ�ú�ͬ
	public static final String CONTRACT_CREATE="N"; 	//������ͬ
	public static final String CONTRACT_MODIFY="M";//�޸�

/*
*�������˼��������ݶ���
*
*/
	public static final String SETOFF_LEVEL_I = "I"; 	//��������
	public static final String SETOFF_LEVEL_F = "F";        //��������
	
	public static final String BIDIM_STATUS_AVA = "V"; 	//2ά��������״̬
	public static final String BIDIM_DEFALT_VALUE_YES = "Y";        //�Ƿ�Ĭ��ֵ
	
	//��Ȩ
	public static final String FUTURERIGHT_RESULT_V = "V"; 	//����
	public static final String FUTURERIGHT_RESULT_C = "C"; 	//ȡ��
	public static final String FUTURERIGHT_RESULT_X = "X" ;  //��ʹ��
	
	//SET_S_ORGANIZATIONPARTNERCATEGORY        T_Organization��� OrgSubType �ֶ�
	public static final String ORG_ORGSUBTYPE_A ="A";						//���������ࣺ��Ʒ�ṩ��
	public static final String ORG_ORGSUBTYPE_G ="G";						//���������ࣺ�豸�ṩ��
	public static final String ORG_ORGSUBTYPE_P ="P";						//���������ࣺ��������
	public static final String ORG_ORGSUBTYPE_S ="S";						//���������ࣺ������
	public static final String ORG_ORGSUBTYPE_Z ="Z";						//���������ࣺ��װ�����ṩ��
	
	public static final String OSS_AUTHORIZATION_OPERATE_A ="A";			//Ԥ��Ȩ
	public static final String OSS_AUTHORIZATION_OPERATE_D ="D";//��Ԥ��Ȩ
//	SET_V_CSIPAYMENTSETTINGOPTION
	public static final String CSI_PAYMENT_OPTION_IM ="IM";   //����֧��
	public static final String CSI_PAYMENT_OPTION_OP ="OP";   //��ѡ��
	public static final String CSI_PAYMENT_OPTION_IV ="IV";   //���¶��ʵ���֧��
	public static final String CSI_PAYMENT_OPTION_SP ="SP";   //���Ű�װ�ɹ�����ȡ
	
//ǰ̨ҳ�洫�������ȷ���Ƿ�����֧��������ֵ
	public static final String CSI_PAYMENT_OPTION_PAY="P";  //����֧������
	public static final String CSI_PAYMENT_OPTION_FINISH="F";//�����ӳ�֧��,�����ʵ�
	//����command���ظ�webAction ����ֵ��ʹ�ñ�־��
	public static final String COMMAND_ID_DEVICE="D";  //���ؽ��Ϊ�豸
	public static final String COMMAND_ID_IMMEDIATEFEE="I";//���ؽ��Ϊ����
	
	
    //���Ʒ�����Ϣ���õ��ı�������variable
    public static final String SEND_MSG_CUSTOMER_NAME_VARIABLE="{$CUSTOMERNAME}";				//�ͻ�����
    public static final String SEND_MSG_CUSTOMER_NAME_VARIABLE_RGE="\\{\\$CUSTOMERNAME\\}";	
    public static final String SEND_MSG_ACCOUNT_BALANCE_VARIABLE="{$ACCOUNTBALANCE}";			//�ʻ����
    public static final String SEND_MSG_ACCOUNT_BALANCE_VARIABLE_RGE="\\{\\$ACCOUNTBALANCE\\}";	
    public static final String SEND_MSG_INVOICEAMOUNT_VARIABLE="{$INVOICEAMOUNT}";				//�ʵ����
    public static final String SEND_MSG_INVOICEAMOUNT_VARIABLE_RGE="\\{\\$INVOICEAMOUNT\\}";	

	public static final String SP_OPERATETYPE_F001 = "F001";

	//SET_F_BRRATETYPE
	public static final String BILLING_RULE_RATE_TYPE_V="V";			//��ֵ
	public static final String BILLING_RULE_RATE_TYPE_P="P";			//����
	//SET_A_CATVTERMINALRECORDSOURCE
	public static final String CATV_TERMINAL_RECORDSOURCE_E="E";	//����
	public static final String CATV_TERMINAL_RECORDSOURCE_S="S";	//��װ
	public static final String CATV_TERMINAL_RECORDSOURCE_K="K";	//�չ�
	public static final String CATV_TERMINAL_RECORDSOURCE_I="I";	//���ݵ���

	//SET_A_CONSTRUCTIONSTATUS
	public static final String CONSTRUCTION_STATUS_NEW="I";	//�½�
	public static final String CONSTRUCTION_STATUS_CONFIRM="F";	//ȷ��
	public static final String CONSTRUCTION_STATUS_CANCEL="C";	//ȡ��
	
	//��SET_A_CATVTERMINALSTATUS
	public static final String CATV_TERMINAL_STATUS_N="N";	//��ͨ
	public static final String CATV_TERMINAL_STATUS_S="S";	//�ض�
	public static final String CATV_TERMINAL_STATUS_D="D";	//����
	public static final String CATV_TERMINAL_STATUS_I="I";	//�½�
	public static final String CATV_TERMINAL_STATUS_J="J";	//��˺�˽��
	public static final String CATV_TERMINAL_STATUS_U="U";	//���ڲ�
	
	
	//�ն����ͣ�SET_A_CATVTERMTYPE
	public static final String CATV_TERMINAL_TYPE_N="N";	//��ͨס��
	public static final String CATV_TERMINAL_TYPE_E="E";	//��ҵ��λ
	
	//SET_W_JOBCARDSUBTYPE
	public static final String CATV_JOBCARD_SUBTYPE_F = "F";   //��˵�
	public static final String CATV_JOBCARD_SUBTYPE_H = "H";   //�ָ���ͨ��ҵ��
	public static final String CATV_JOBCARD_SUBTYPE_T = "T";   //ͣ����ҵ��
	public static final String CATV_JOBCARD_SUBTYPE_Q = "Q";   //Ǩ��ͣ����ҵ��
	public static final String CATV_JOBCARD_SUBTYPE_R = "R";   //Ǩ�뿪ͨ��ҵ��
	public static final String CATV_JOBCARD_SUBTYPE_Z = "Z";   //���˰�װ��ҵ��
	public static final String CATV_JOBCARD_SUBTYPE_W = "W";   //����ʩ����
	//SET_A_CATVTERMPROCESSTYPE
	public static final String CATV_TERMINAL_PROCESSTYPE_A = "A";  //�������׵���
	public static final String CATV_TERMINAL_PROCESSTYPE_B = "B";  //��װ
	public static final String CATV_TERMINAL_PROCESSTYPE_C = "C";  //��Ϣ�޸�
	public static final String CATV_TERMINAL_PROCESSTYPE_D = "D";  //����
	public static final String CATV_TERMINAL_PROCESSTYPE_E = "E";  //Ǩ��
	public static final String CATV_TERMINAL_PROCESSTYPE_F = "F";  //Ǩ��
	public static final String CATV_TERMINAL_PROCESSTYPE_G = "G";  //�������
	public static final String CATV_TERMINAL_PROCESSTYPE_H = "H";  //������˸���
	public static final String CATV_TERMINAL_PROCESSTYPE_I = "I";  //Ƿ�ѷ��
	public static final String CATV_TERMINAL_PROCESSTYPE_J = "J";  //Ƿ�ѷ�˸���
	public static final String CATV_TERMINAL_PROCESSTYPE_K = "K";  //����
	
	//SET_A_CATVOPENTYPE
	public static final String CATV_OPEN_TYPE_NEW="N";				//�����ն�(N)
	public static final String CATV_OPEN_TYPE_A="A";				//�����˿�(A)
	public static final String CATV_OPEN_TYPE_O="O";				//�ָ���ͨ(O)

	//SET_W_JOBCARDREFERTYPE������������
	public static final String JOBCARD_REFERTYPE_P="P";				//���޵�
	public static final String JOBCARD_REFERTYPE_S="S";				//����
	
	
	//T_DEVICEPREMATCHRECORD��������(OperationType) 
	public static final String SET_D_DEVICEPREAUTH_OPERATIONTYPE_AM = "AM"; 		//��¼�豸������-�������ͣ����
	public static final String SET_D_DEVICEPREAUTH_OPERATIONTYPE_AP = "AP"; 		//��¼�豸������-�������ͣ�Ԥ��Ȩ
	public static final String SET_D_DEVICEPREAUTH_OPERATIONTYPE_DA = "DA"; 		//��¼�豸������-�������ͣ���������Ȩ
	public static final String SET_D_DEVICEPREAUTH_OPERATIONTYPE_DM = "DM"; 		//��¼�豸������-�������ͣ������
	public static final String SET_D_DEVICEPREAUTH_OPERATIONTYPE_DP = "DP"; 		//��¼�豸������-�������ͣ�����Ȩ
	
	
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
	
	public static final int EXPORT_MAX =65000 ;                         //Excel�����������

}	
