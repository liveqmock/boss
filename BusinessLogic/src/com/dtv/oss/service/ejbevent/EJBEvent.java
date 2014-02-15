package com.dtv.oss.service.ejbevent;

public interface EJBEvent extends java.io.Serializable {

	// �˻������йض�������-------------��ʼ--------------------------
	public static final int CLOSECUSTOMERACCOUNT = 125; // �ͻ��ʻ�����

	public static final int OPENACCOUNTFORCUSTOMER = 203; // �����ʻ�

	public static final int PAYCHEK =30 ; //�ʵ����
	
	public static final int PRESAVE = 31; // Ԥ��

	public static final int PAYBYBILL = 32; // ֧���˵�

	public static final int UPDATEACCOUNTINFO = 206; // �޸��˻���Ϣ
	
	public static final int BATCHALTEACCOUNTINFO =207 ; //�����޸��˻���Ϣ
	
	public static final int CHECKBANKINFOFORMAT =217 ; //�����޸��˻���Ϣ_������Ϣ��ʽУ��
	
	public static final int INVOICE_BATCH_PAY = 208;//����֧���ʵ�
	
	public static final int MERGEINVOICE_BATCH_PAY =210; //����֧���ϲ��ʵ�
	
	public static final int SINGLE_ACCOUNT_PAY =300;  //���ʻ�֧��

	// �˻������й�����-------------����---------------------------------
	// ���ʲ����йض�������-------------------------��ʼ-----------------
	public static final int ADJUSTMENT = 34; // �˻�����

	public static final int CSI_ADJUSTMENT = 39; // �˻�����

	public static final int INVOICE_ADJUSTMENT = 36; // δ���ʵ�����

	public static final int REPAIR_ADJUST_OP = 408; // ���޵�����

	// ���ʲ����йض�������-------------------------����-----------------

	// �˷Ѳ����йض�������-------------��ʼ--------------------------
	public static final int RETURNFEE4FAILINSTALLATION = 105; // ��װ���ɹ��˷�

	// �˷Ѳ����йض�������-------------����--------------------------

	// �طò����йض�������-------------��ʼ-----------------------------
	public static final int CALLFOROPENACCOUNT = 103; // �����ط�

	public static final int SETCALLFLAG4OPENACCOUNT = 110; // �����ط��ݴ�

	public static final int CALL_CUSTOMER_FOR_REPAIR = 405; // ���޻ط�

	public static final int SET_CALLFLAG_FOR_REPAIR = 106; // ���޻ط��ݴ�

	public static final int CALL_FOR_COMPLAIN = 424; // Ͷ�߻ط�

	public static final int SET_CALL_FOR_COMPLAIN = 426; // Ͷ�߻ط�

	// �طò����йض�������-------------����------------------------------

	// �й�Customer����--------------��ʼ---------------------------
	public static final int BOOKING = 100; 					// ԤԼ

	public static final int CANCELBOOKING = 201; 			// ȡ��ԤԼ

	public static final int ALTERBOOKING = 202; 			// �ı�ԤԼ

	public static final int AGENT_BOOKING = 204; 			// ������ԤԼ

	public static final int CHECK_AGENT_BOOKING = 205; 		// ȷ�ϴ�����ԤԼ

	public static final int OPENACCOUNTINBRANCH = 101; 		// �ŵ꿪��

	public static final int OPENACCOUNTFORBOOKING = 102; 	// ԤԼ����
	
	public static final int OPENACCOUNTDIRECTLY = 103; 		//�ͻ����ϵ���¼�뿪��

	public static final int ALTERCUSTOMERINFO = 207; 		// �޸Ŀͻ���Ϣ

	public static final int MOVETODIFFERENTPLACE = 107; 	// Ǩ��

	public static final int TRANSFERTODIFFERENTPLACE = 108; // ��ع���

	public static final int TRANSFERTOSAMEPLACE = 109; 		// ԭ�ع���

	public static final int CLOSE_CUSTOMER = 126; 			// �ͻ�����

	public static final int WITHDRAW = 124; 				// �˻�

	public static final int OPEN_CATV_ACCOUNT = 127; 		// ģ����ӿ���


	
	// �й�Customer����--------------����---------------------------

	// �ͻ���Ʒ��ҵ���ʻ���ز���----------------------��ʼ----------------------------
	public static final int DEVICESWAP = 106; // �豸����
	public static final int DEVICEUPGRADE = 128; // �豸����
	public static final int PRODUCTACCOUNTMODIFY =53; // customerProduct(accountID) modify
	public static final int DEVICEPROVIDEMODIFY=54;//

	public static final int PURCHASE = 56; // product(device) purchase

	public static final int SUSPEND = 57; // product��sa suspend

	public static final int RENT = 80; // product��sa rent
	
	public static final int RESUME = 58; // product��sa resume

	public static final int TRANSFER = 60; // product��sa transfer

	public static final int CANCEL = 62; // product��sa cancel

	public static final int UPGRADE = 63; // product upgrade

	public static final int DOWNGRADE = 64; // product downgrade

	public static final int UPDOWNGRADE = 65; // product updowngrade
	
	public static final int SERVICE_ACCOUNT_CODE_UPDATE = 66; // product updowngrade
	
	public static final int BATCH_SUSPEND = 68; // ҵ���ʻ�������ͣ
	
	public static final int BATCH_RESUME = 70; // ҵ���ʻ������ָ�
	
	public static final int PROTOCOL_DATE_MODIFY = 71; //Э���ֹ�����޸�

	public static final int MOVE = 69; // product��sa move
	// add by Hoarce Lin 2004-11-29
	public static final int RESEND_EMM_BY_SERVICE_ACCOUNT = 900; // ����ҵ���ʻ�����Ȩ
	
	public static final int REAUTHORIZE_FOR_SA_BY_CUSTOMER = 908; // ���ݿͻ�����Ȩ�ͻ��µ�ҵ���ʻ�

	// add by WESLEY 2006/02/23
	public static final int REAUTHORIZEPRODUCT = 901; // ��Ʒ����Ȩ

	public static final int CLEARPASSWORD = 902; // �������

	public static final int PARTNERSHIP = 903; // �������

	public static final int SEND_EMAIL_CA = 904; // ����EMAIL

	public static final int CANCEL_PARTNERSHIP = 912; // ����������

	// �ͻ���Ʒ��ҵ���ʻ���ز���----------------------����----------------------------

	public static final int BATCHPAUSEPRODUCT = 120; // ������ͣ�ͻ���Ʒ

	public static final int BATCHRESUMEPRODUCT = 121; // �����ָ��ͻ���Ʒ

	public static final int BATCHASSIGNCAMPAIGN = 123; // ���������Ż�

	public static final int PAYFEEFOROPENING = 104; // ֧��������

	public static final int UPDATECSIFEE2PAYMENT = 111; // ��������

	// public static final int DEVICEEXCHANGE = 9; //device exchange

	public static final int DEVICERETURN = 10; // device return

	public static final int JOBCARDASSIGN = 12; // JobCard Assign

	public static final int CLOSE = 14; // JobCard/CustomerProblem close

	public static final int LOGIN = 21; // operator login
	
	public static final int BEFOREHAND_CLOSE = 25; // JobCard/CustomerProblem beforehand close
	
	public static final int REAL_CLOSE = 26; // JobCard/CustomerProblem beforehand close
	// started by thurm
	public static final int WITHDRAW_FORCED_DEPOSIT = 35; // �˻�Ѻ��

	public static final int WITHDRAW_CONFISCATE_DEPOSIT = 37; // û��Ѻ��

	// start of CustomerRelation and workflow 400~599
	// CustomerRelation Repair 401~420
	public static final int PROCESS_CUSTOMER_PROBLEM = 401; // ����

	public static final int MANUAL_TRANSFER_REPAIR_SHEET = 402; // ���޵��ֹ���ת

	public static final int MANUAL_CLOSE_REPAIR_SHEET = 403; // �ֹ��������޵�

	public static final int TERMINATE_REPAIR_INFO = 404; // ��ֹά��

	// CustomerRelation Other 441~460
	public static final int CREATE_MESSAGE = 441; // ������Ϣ

	public static final int CREATE_CALLBACKINFOSETTING = 445; // ����CallbackInfoSetting

	public static final int UPDATE_CALLBACKINFOSETTING = 446; // �޸�CallbackInfoSetting

	public static final int DELETE_CALLBACKINFOSETTING = 447; // ɾ��CallbackInfoSetting

	public static final int CREATE__CALLBACKINFOVALUE = 448; // ����CallbackInfoValue

	public static final int UPDATE__CALLBACKINFOVALUE = 449; // �޸�CallbackInfoValue

	public static final int DELETE__CALLBACKINFOVALUE = 450; // ɾ��CallbackInfoValue

	public static final int CREATE_DIANOSISPARAMETER = 451; // ������ϲ���

	public static final int UPDATE_DIANOSISPARAMETER = 452; // ������ϲ���

	public static final int DELETE_DIANOSISPARAMETER = 453; // ɾ����ϲ���

	// Workflow Repair 461~480
	public static final int ASSIGN_REPAIR = 461; // �����ɹ�

	public static final int CONTACT_USER_FOR_REPAIR = 462; // ά��ԤԼ

	public static final int REGISTER_REPAIR_INFO = 463; // ¼��ά����Ϣ

	public static final int CLOSE_REPAIR_INFO = 464; // ����ά�޹���
	// public static final int PRINT_REPAIR_INFO = 465; //��ӡά�޹���

	public static final int CANCEL_REPAIR_JOB_CARD = 466; // ȡ��ά�޹���,ʵ�������޸���ͬһ��

	public static final int DIAGNOSIS_REPAIR = 467;// ά�����
	// Workflow Installation 481~500
	public static final int CONTACT_USER_FOR_INSTALLATION = 481; // ��װԤԼ

	public static final int REGISTER_INSTALLATION_INFO = 482; // ¼�밲װ��Ϣ
	// public static final int PRINT_INSTALLATION_INFO = 483; //��ӡ��װ����

	public static final int CLOSE_INSTALLATION_INFO = 484; // ������װ����

	public static final int CANCEL_INSTALLATION_JOB_CARD = 485; // ȡ����װ����,ʵ�������޸���ͬһ��

	public static final int UPDATE_JOB_CARD = 521; // �޸Ĺ���

	
	public static final int MANUAL_CREATE_JOBCARD = 522;// �ֹ���������
	
	
	
	public static final int BATCH_CATV_REGISTER_INSTALLATION_INFO = 535;    // catv����¼��ʩ����Ϣ
	
	// Statistic 551~599
	// market about userpoints exchange
	public static final int USERPOINTS_EXCHANGE = 530;
	
	public static final int BATCH_CONTACT_USER_FOR_INSTALLATION = 531; // ������װԤԼ
	public static final int BATCH_REGISTER_INSTALLATION_INFO = 532;    // ����¼�밲װ��Ϣ
	public static final int BATCH_CONTACT_USER_FOR_REPAIR = 533;       // ����ά��ԤԼ
	public static final int BATCH_CATV_CONTACT_USER = 534;             // ����ģ�⹤��ԤԼ

	public static final int CUSTOMER_ADD_SUBSCRIBER = 1000; // �ŵ������ύ
	
	public static final int CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER = 1005; // ԤԼ�����ύ
	
	public static final int CUSTOMER_BOOKINGUSER_CANCEL_SUBSCRIBER =1006; //ԤԼ������ԤԼȡ��
	
	public static final int CUSTOMER_BOOKINGUSER_UPDATE_SUBSCRIBER =1007; //ԤԼ������ԤԼ�޸�
	
	public static final int CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER_FORBOOKING = 1008; // ԤԼ������ԤԼ�ύ

	// ------------��Լ����¼����忪ʼ������¼���2000-2999����ʼ----------------------------
	public static final int CHECK_BOOKINGUSER_PRODUCTPG_CAMPAINPG = 2000; // ���ԤԼ����ԤԼ�Ĳ�Ʒ�����Żݰ�
	
	public static final int CHECK_CUSTOMERINFO = 2001; // ��鿪����Ϣ

	public static final int CHECK_PRODUCTPG_CAMPAINPG = 2002; // ��鿪���Ĳ�Ʒ�����Żݰ�

	public static final int CHECK_HARDPRODUCTINFO = 2003; // ��鿪����Ӳ����Ʒ

	public static final int CHECK_FREEINFO = 2004; // ��������Ϣ

	public static final int CHECK_FOR_MOVE = 2006; // Ǩ��ǰ���
	
	public static final int CHECK_OPEN_ACCOUNT_CATV = 2009; //���ģ�⿪����Ϣ
	
	public static final int CACULATE_OPEN_CATV_FEE = 2010; //���ģ�⿪����Ϣ
	
	public static final int CHECK_BUY_PRODUCT = 2011; //�ظ�����,���ѡ���Ʒ��Ч��	

	public static final int CHECK_BATCHBUY_PRODUCTINFO = 2012; // �ظ������鿪���Ĳ�Ʒ�����Żݰ�
	
	public static final int BATCHADD_PRODUCTPACKAGE =2013; //�������Ӳ�Ʒ��

	public static final int CUSTOMER_BATCHBUY_ADD_SUBSCRIBER = 1013; // �ظ������ŵ������ύ
	// ------------��Լ����¼����忪ʼ������¼���2000-2999������----------------------------

	// �ų��õ��¼�
	public static final int SCHEDULE_CREATE = 801; // �ų̴���

	public static final int SCHEDULE_MODIFY = 802; // �ų��޸�

	public static final int SCHEDULE_CANCEL = 803; // �ų�ȡ��

	// �ų��¼�����

	// ������ѯ��
	public static final int BATCH_QUERY_CREATE = 901; // ������ѯ����

	public static final int BATCH_QUERY_MODIFY = 902; // ������ѯ�޸�

	public static final int BATCH_QUERY_CANCEL = 903; // ������ѯȡ��

	public static final int BATCH_QUERY_EXCUTE = 904; // ������ѯִ��

	public static final int BATCH_QUERY_RESULT_CHANGE = 905; // ������ѯ�����ת

	// ������ѯ��-end

	// ���������¼�
	public static final int BATCHJOB_CREATE = 1101; // �������񵥴���

	public static final int BATCHJOB_MODIFY = 1102; // ���������޸�

	public static final int BATCHJOB_CANCEL = 1103; // ��������ȡ��

	// �������񵥽���
	// �г���¼�
	public static final int CAMPAIGN_CREATE = 1150; // ���������

	public static final int CAMPAIGN_UPDATE = 1151; // ������޸�

	public static final int GROUPBARGAIN_CREATE = 1552; // �Ź� �½�

	public static final int GROUPBARGAIN_DELETE = 1553; // �Ź� ɾ��

	public static final int GROUPBARGAIN_UPDATE = 1554; // �Ź� ����

	public static final int GROUPBARGAIN_SALE = 1555; // �Ź� �۳�

	public static final int CONFIG_ACTIVITY_CREATE = 1557; // �����

	public static final int CONFIG_ACTIVITY_UPDATE = 1558; // �޸Ļ

	public static final int CONFIG_ACTIVITY_DELETE = 1559; // delete the
															// activity

	public static final int CONFIG_GOODS_DELETE = 1560; // delete the goods

	public static final int CONFIG_GOODS_CREATE = 1561; // create the goods

	public static final int CONFIG_GOODS_EDIT = 1562; // update the goods

	public static final int CONFIG_RULE_DELETE = 1563; // delete the rule

	public static final int CONFIG_RULE_CREATE = 1564; // create the rule

	public static final int CONFIG_RULE_EDIT = 1565; // update the rule
	
	public static final int CONFIG_COND_DELETE = 8000; // delete the rule

	public static final int CONFIG_COND_CREATE = 8001; // create the rule

	public static final int CONFIG_COND_EDIT = 8002; // update the rule

	public static final int CONFIG_BIDIM_DELETE = 1566; // delete the BIDIM

	public static final int CONFIG_BIDIM_CREATE = 1567; // create the BIDIM

	public static final int CONFIG_BIDIM_EDIT = 1568; // update the BIDIM

	public static final int CONFIG_BIDIM_UPDATE = 15681; // update the BIDIM

	public static final int CONFIG_VALUE_DELETE = 1570; // delete the value

	public static final int CONFIG_VALUE_CREATE = 1571; // create the value

	public static final int CONFIG_VALUE_EDIT = 1572; // update the value

	public static final int CONFIG_CUMULATED_RULE_DELETE = 1570; // delete
																	// the value

	public static final int CONFIG_CUMULATED_RULE_CREATE = 1571; // create
																	// the value

	public static final int CONFIG_CUMULATED_RULE_EDIT = 1572; // update the
																// value

	public static final int CONFIG_MARKET_SEGMENT_DELETE = 1580; // delete
																	// the value

	public static final int CONFIG_MARKET_SEGMENT_CREATE = 1581; // create
																	// the value

	public static final int CONFIG_MARKET_SEGMENT_EDIT = 1582; // update the
																// value

	public static final int CONFIG_DISTRICT_CREATE = 1584; // create the value

	// �ֿ�����¼�start
	public static final int DEPOT_CREATE = 1601; // �ֿⴴ��

	public static final int DEPOT_DELETE = 1602; // �ֿ�ɾ��

	public static final int DEPOT_UPDATE = 1603; // �ֿ����

	// �ֿ�����¼�end

	// ҵ������¼�
	public static final int SERVICE_CREATE = 3001; // ҵ���崴��

	public static final int SERVICE_DELETE = 3002; // ҵ����ɾ��

	public static final int SERVICE_UPDATE = 3003; // ҵ�������

	// ҵ�����������¼�
	public static final int SERVICEDEPENDCE_CREATE = 3004; // ҵ����������

	public static final int SERVICEDEPENDCE_DELETE = 3005; // ҵ������ɾ��

	// ҵ����Դ�����¼�
	public static final int SERVICERESOURCE_CREATE = 3006; // ҵ����Դ����

	public static final int SERVICERESOURCE_DELETE = 3007; // ҵ����Դɾ��

	public static final int SERVICERESOURCE_UPDATE = 3008; // ҵ����Դ����

	// ҵ����Դ��ϸ�����¼�
	public static final int SERVICERESOURCEDETAIL_CREATE = 3009; // ҵ����Դ��ϸ����

	public static final int SERVICERESOURCEDETAIL_DELETE = 3010; // ҵ����Դ��ϸɾ��

	// ��Ʒ����
	public static final int PRODUCT_CREATE = 4001; // ��Ӳ�Ʒ

	public static final int PRODUCT_MODIFY = 4002; // �޸Ĳ�Ʒ
	
	public static final int PRODUCT_DELETE = 4003; // ɾ����Ʒ

	// ��Ʒ������ϵ����
	public static final int PRODUCT_DEPENDENCY_CREATE = 4005; // ��Ӳ�Ʒ��ϵ

	public static final int PRODUCT_DEPENDENCY_MODIFY = 4006; // �޸Ĳ�Ʒ��ϵ

	public static final int PRODUCT_DEPENDENCY_DELETE = 4007; // ɾ����Ʒ��ϵ

	// ��Ʒ���Բ���
	public static final int PRODUCT_PROPERTY_CREATE = 4010; // ��Ӳ�Ʒ����

	public static final int PRODUCT_PROPERTY_MODFIY = 4011; // �޸Ĳ�Ʒ����

	public static final int PRODUCT_PROPERTY_DELETE = 4012; // �޸Ĳ�Ʒ����

	// ��Ʒ������
	public static final int PRODUCT_PACKAGE_CREATE = 4013; // ��Ӳ�Ʒ��

	public static final int PRODUCT_PACKAGE_MODIFY = 4014; // �޸Ĳ�Ʒ��

	public static final int CONFIG_PACKAGE_MARKET_SEGMENT = 4016; // ����packageLine
	
	//��Ʒ�豸����
	public static final int DELETE_DEVICE_TO_PRODUCT = 4017;
	
	public static final int ADD_DEVICE_TO_PRODUCT = 4018;

	// ����֧������
	public static final int BILLING_RULE_CONDITION_CREATE = 4020; // ����֧������

	public static final int BILLING_RULE_CONDITION_MODIFY = 4021; // �޸�֧������

	public static final int BILLING_RULE_CONDITION_DELE = 4022; // ɾ��֧������

	// ���ѷ�ʽ����
	public static final int METHOD_OF_PAYMENT_CREATE = 4025; // �������ѷ�ʽ

	public static final int METHOD_OF_PAYMENT_MODIFY = 4026; // �޸ĸ��ѷ�ʽ

	public static final int METHOD_OF_PAYMENT_DELE = 4027; // ɾ�����ѷ�ʽ
	
//	 ���ѹ������
	public static final int BILLING_RULE_CREATE = 4030; // �������ѷ�ʽ

	public static final int BILLING_RULE_MODIFY = 4031; // �޸ĸ��ѷ�ʽ

	public static final int BILLING_RULE_DELE = 4032; // ɾ�����ѷ�ʽ
	
//	 ϵͳ���ò���
	public static final int BOSS_CONFIG_CREATE = 4040; // �������ѷ�ʽ

	public static final int BOSS_CONFIG_MODIFY = 4041; // �޸ĸ��ѷ�ʽ
	
	public static final int DELETE_GROUP_TO_MEMBER = 4044; // ɾ��group member
	
	public static final int ADD_GROUP_TO_MEMBER = 4045; // �޸ĸ��ѷ�ʽ
	
	public static final int DELETE_GROUP_TO_PRIVILEGE = 4042; // ɾ��group member
	
	public static final int ADD_GROUP_TO_PRIVILEGE = 4043; // �޸ĸ��ѷ�ʽ
		
	public static final int ADD_OPGROUP = 4050; // �޸ĸ��ѷ�ʽ
	
	public static final int MODIFY_OPGROUP = 4051; // �޸ĸ��ѷ�ʽ
	
	public static final int DELETE_ORG_TO_DISTRICT = 4084; // ɾ��group member
	
	public static final int ADD_ORG_TO_DISTRICT = 4085; // �޸ĸ��ѷ�ʽ
	
	public static final int DELETE_OPTOGROUP = 4086; // ɾ��group member
	
	public static final int ADD_OPTOGROUP = 4087; // ���Ӳ���Ա��
	
	public static final int CREATE_OPERATOR = 4090; // ��Ӳ���Ա
	
	public static final int MODIFY_OPERATOR = 4091; // �޸Ĳ���Ա
	
	public static final int RECACULATE_RULE = 4100; // ���¼Ʒ�
	
	public static final int DELETE_PRODUCT = 5000; // ɾ��group member
	
	public static final int ADD_PRODUCT = 5001; // �޸ĸ��ѷ�ʽ
	
	public static final int MODIFY_LDAP_HOST = 5010; // �޸�ldaphost
	
	public static final int ADD_LDAP_HOST = 5011; // ���ldaphost
	
	public static final int MODIFY_LDAP_PRODUCT = 5020; // �޸�ldaphost
	
	public static final int ADD_LDAP_PRODUCT= 5021; // ���ldaphost
	
	public static final int MODIFY_LDAP_EVENTCMDMAP = 5022; // �޸�ldaphost
	
	public static final int ADD_LDAP_EVENTCMDMAP= 5023; // ���ldaphost
	
	public static final int MODIFY_LDAPPROSMS = 5030; // �޸�ldaphost
	
	public static final int ADD_LDAPPROSMS= 5031; // ���ldaphost
	
	public static final int MODIFY_LDAPATTR = 5032; // �޸�ldaphost
	
	public static final int ADD_LDAPATTR= 5033; // ���ldaphost
	
	public static final int DEL_SERVICE = 5039; // ɾ��ҵ��
	
	public static final int ADD_SERVICE= 5040; // ���ҵ��
	
	public static final int MODIFY_LDAPCOND = 5041; // �޸�ldapcond
	
	public static final int ADD_LDAPCOND = 5042; // ���ldapcond
	//�������
	public static final int FRIEND_PHONENO_CREATE	= 201; //����������� 	 
	public static final int FRIEND_PHONENO_DELETE	= 202; //�������ɾ��
	
//	 ����֧������
	public static final int BILLBOARD_CREATE = 6000; // ����������Ϣ

	public static final int BILLBOARD_MODIFY = 6001; // �޸Ĺ�����Ϣ

	public static final int BILLBOARD_DELE = 6002; // ɾ��������Ϣ
	
	public static final int SYSTEM_SETTING_MODIFY = 6003; // �޸�ϵͳȫ��������Ϣ
	
	public static final int CAMPAIGN_COND_CREATE = 6015; // �������������
	
	public static final int CAMPAIGN_COND_UPDATE = 6016; // ����������޸�
	
	public static final int CAMPAIGN_COND_PRODUCT = 6017; // ����������޸�
	
	public static final int CAMPAIGN_COND_PACKAGE = 6018; // ����������޸�
	
	public static final int CAMPAIGN_COND_CAMPAIGN = 6019; // ����������޸�
	
	public static final int CAMPAIGN_PAYMENTMETHOD_DELETE = 6020; // �ײ͸��ѷ�ʽɾ��
	
	public static final int BUNDLE_PREPAYMENT_DELETE = 6023; // �ײ͸��ѷ�ʽMODIFY
	
	public static final int AGMT_CAMPAIGN_DELETE = 6024; // �ײ͸��ѷ�ʽMODIFY
	
    public static final int MANUAL_TRANSFER_CREATE = 6040; // �ֹ���ת����
	
	public static final int MANUAL_TRANSFER_MODIFY = 6041; // �ֹ���ת����
	
	public static final int MANUAL_TRANSFER_DELETE = 6042; // �ֹ���תɾ��
	
    public static final int FEE_PLAN_CREATE = 6050; // ֧�Ѽƻ�����
	
	public static final int FEE_PLAN_MODIFY = 6051; // ֧�Ѽƻ�����
	
	public static final int FEE_PLAN_DELETE = 6052; // ֧�Ѽƻ�ɾ��
	
    public static final int FEE_PLAN_ITEM_CREATE = 6055; // ֧�Ѽƻ�����
	
	public static final int FEE_PLAN_ITEM_MODIFY = 6056; // ֧�Ѽƻ�����
	 
    public static final int CONFIG_CSI_REASON_NEW = 6070;
	
	public static final int CONFIG_CSI_REASON_UPDATE = 6071; 
	
    public static final int CONFIG_CSI_REASON_DETAIL_NEW = 6072;
	
	public static final int CONFIG_CSI_REASON_DETAIL_UPDATE = 6073; 
	 
	//�ͻ����Ʒѹ���ά��	add by jason 2007-6-22
	public static final int CUSTOMER_BILLING_RULE_NEW = 7001; 			//�½�
	
	public static final int CUSTOMER_BILLING_RULE_UPDATE = 7002; 		//�޸�
	
	public static final int CONFIG_MACHINEROOM_UPDATE = 7003; 
	
	public static final int CONFIG_MACHINEROOM_CREATE = 7004; 
	
	public static final int CONFIG_MACHINEROOM_REMOVE = 7005; 
	
    public static final int CONFIG_FIBERTRANSMITTER_UPDATE = 7006; 
     
	public static final int CONFIG_FIBERTRANSMITTER_CREATE = 7007; 
	
	public static final int CONFIG_FIBERTRANSMITTER_REMOVE = 7008; 
	
	public static final int CONFIG_FIBERRECEIVER_UPDATE = 7009; 
    
	public static final int CONFIG_FIBERRECEIVER_CREATE = 7010; 
	
	public static final int CONFIG_FIBERRECEIVER_REMOVE = 7011; 
	
    public static final int CONFIG_FIBERNODE_UPDATE = 7012; 
    
	public static final int CONFIG_FIBERNODE_CREATE = 7013; 
	
	public static final int CONFIG_FIBERNODE_REMOVE = 7014; 
	
	public static final int CATV_TERMINAL_NEW = 7015;
	
	public static final int CATV_TERMINAL_UPDATE = 7016;
		
	public static final int CATV_CONTACT_USER_FOR_CONSTRUCTION = 7018;
	
	public static final int CATV_REGISTER_JOBCARD = 7019;
	
	public static final int CATV_CLOSE_JOBCARD = 7020;
	
	public static final int CAMPAIGN_PAYMENT_AWARD_CREATE = 7021;
	
	public static final int CAMPAIGN_PAYMENT_AWARD_DELETE = 7022;
	
	public static final int CATV_CANCEL_JOBCARD = 7023;
	
	public static final int ACCOUNT_MANUAL_FEE = 7026;
	public static final int UPLOAD_FOR_BATCH_MESSAGE = 7027;//���ڸ軪����������Ϣʱ�ͻ���Ϣ�ϴ�
	
	public static final int BATCH_MESSAGE_CREATEJOB = 7028;//���ڴ�������������Ϣ����
	
	public static final int IPPV_CREATE = 7124;// IPPVǮ������

	public static final int IPPV_CHARGE = 7125;

	public static final int IPPV_UPDATE = 7126; // IPPVǮ���޸�
	
	public static final int IPPV_DELETE = 7127; // IPPVǮ��ɾ��
	
	public static final int DTV_MGT_CREATE = 7030;// ƽ��С������

	public static final int DTV_MGT_UPDATE = 7031;// ƽ��С���޸�
	
	public static final int UPLOAD_FOR_BATCH_SUSPEND = 7032;//���ڸ軪�����ض�ʱ�ͻ���Ϣ�ϴ�
	
	public static final int BATCH_SUSPEND_CREATEJOB = 7033;//���ڴ��������ض�����
	
	public static final int UPLOAD_FOR_BATCH_RESUME = 7034;//���ڸ軪������ͨʱ�ͻ���Ϣ�ϴ�
	
	public static final int BATCH_RESUME_CREATEJOB = 7035;//���ڴ���������ͨ����
	
	public static final int CUSTOMER_PROTOCOL =7036; //�ͻ�Э�鴴����ά��
	
	public static final int UPLOAD_FOR_FoundCustomer = 7037;    //���ڽ����ͻ���Ϣ�ϴ�
	
	public static final int EXPORT_FOR_CUSTOMER =7038;    //������ת�ͻ�����
	
	public static final int BATCH_MODIFY_CUST =7128;     //�����޸Ŀͻ���Ϣ
	
	public static final int BATCHPRESAVE = 3191; // ����Ԥ��

	public String getEJBCommandClassName();

	public void setEJBCommandClassName(String ejbCommandClassName);

	public int getOperatorID();

	public void setOperatorID(int i);

	public String getRemoteHostAddress();

	public void setRemoteHostAddress(String remoteHostAddress);
	
	
	/*
	 * P4�еģ����ο�
	 * 
	 * 
	 * public static final int CANCEL_CUSTOMER_PROBLEM = 107; //ȡ�����޵� public
	 * static final int JOBCARD_TRANSFER = 406; //������ת public static final int
	 * REPAIR_ADJUST_OP = 407; //ά�޵����� public static final int
	 * QUERY_TYPE_JOBCARD_PROCESS_LOG = 3; public static final int
	 * QUERY_TYPE_CP_TRANSITION_INFO = 4; public static final int
	 * QUERY_TYPE_CSI_PROCESS_LOG = 5; public static final int
	 * QUERY_TYPE_CUSTOMER_PROBLEM2JOBCARD = 6; public static final int
	 * QUERY_TYPE_JOBCARD_PRINT = 8; public static final int
	 * QUERY_TYPE_JOBCARD_WITH_CUSTOMER_PROBLEM = 7; public static final int
	 * QUERY_DIAGNOSTICATE_PARAMETER = 9; //���ѱ��޵���ϲ���
	 */
	// end of CustomerRelation and workflow

	
	/* ɾ������
	 public static final int PAYRIGHTNOW = 33; // �˻���������
	 public static final int CREATECUSTADDITIONALINFO = 112; // ��ӿͻ�������Ϣ
	 	public static final int CHANGECUSTADDITIONALINFO = 113; // �޸Ŀͻ�������Ϣ
	public static final int DELAYCUSTADDITIONALINFO = 114; 	// �ͻ�������Ϣ--����
	public static final int ORDER = 55; // product(device) order
	public static final int ACTIVE = 59; // product��sa ACTIVE
	public static final int ALERT = 61; // product��sa alert
	public static final int SEND_SPECIAL_CA = 909; // ��������CA����
	public static final int JOBCARDCOMPLETE = 11; // JobCard Complete
	public static final int FAIL = 13; // JobCard/CustomerProblem fail
	public static final int BATCHCOMPLETE = 15; // CustomerProblem batch
	public static final int BATCHUPDATEPROBLEMLEVEL = 16;
	public static final int JOBCARDUPDATE = 17; // �޸Ĺ���
	public static final int LOGOUT = 22; // operator logout
	// add by wangcx 2004-08-26
	public static final int RESEND_EMM = 41; // account payrightnow
	public static final int QUERY_TYPE_CUSTOMER_PROBLEM = 406; // ��ѯ���޵�
	public static final int RECORD_DIANOSIS_INFO = 407; // �Ǽ������Ϣ
	// CustomerRelation Complain 421~440
	public static final int ACCEPT_COMPLAIN = 421; // Ͷ������
	public static final int ASSIGN_COMPLAIN = 422; // Ͷ�߷���
	public static final int PROCESS_COMPLAIN = 423; // Ͷ�ߴ���
	public static final int QUERY_COMPLAIN = 425; // ��ѯͶ�ߵ�
	public static final int SEND_MESSAGE = 442; // ������Ϣ
	public static final int SET_CALL_BACK_INFO = 443; // ���ûط���Ϣ
	public static final int SET_DIANOSIS_PARAMETER = 444; // ������ϲ���
	public static final int PROCESS_INSTALLATION = 486; // ��װ
	// Query 501~520
	public static final int QUERY_TYPE_JOBCARD = 501; // ������ѯ
	public static final int FIREST_OWED_FEE = 1001; // һ��Ƿ��
	public static final int SECOND_OWED_FEE = 1002; // ����Ƿ��
    public static final int SECOND_OWED_FEE_AFTER_SUSPEND = 1000; // ����Ƿ�Ѻ���ͣ
	public static final int CHANGE_PRODUCT_CURRENT_ACCOUNT = 1003; // �ı��Ʒ�ĸ����˻�
	public static final int CUSTOMER_GROUPBARGAIN_END_RECAMPAIGN = 1004; // ���Ź������û��������
	public static final int CHECK_PRODUCTPG_CAMPAINPG_RETURN_DEVICECLASS = 2005; // ��鿪����Ϣ���������豸����
	public static final int CHECK_CUSTOMERINFO_DIRECTLY = 2008; //�ͻ����ϵ���¼��ʱ�ļ�鿪����Ϣ����Ϊû�в�Ʒѡ��
	public static final int GROUPBARGAIN_DETAIL_UPDATE = 815; // �Ź� ɾ��
	public static final int GROUPBARGAIN_DETAIL_DELETE = 816; // �Ź� ����
	public static final int CONFIG_SERVICEINFO_QUERY = 1556; // ҵ�����ѯ
    public static final int CONFIG_DISTRICT_DELETE = 1583; // delete the value
	public static final int CONFIG_PACKAGE_LINE = 4015; // ����packageLine
	public static final int DELETE_OPGROUP = 4049; // ɾ��group member
	public static final int BUNDLE_PAYMENTMETHOD_CREATE = 6021; // �ײ͸��ѷ�ʽCREATE
	public static final int BUNDLE_PAYMENTMETHOD_MODIFY = 6022; // �ײ͸��ѷ�ʽMODIFY
	*/
	
}
