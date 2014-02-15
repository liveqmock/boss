package com.dtv.oss.util;

public class Constant{
  /**
   * common constant
   */
	
//	------Posten�ع���������￪ʼ ���صĴ��뿽���������������--------START----
	
	
//	------Posten�ع����뵽������� ���صĴ��뿽���������������--------END-------
  public static final boolean DEBUGMODE = true;
  public static final int SUCCESS = 0;
  public static final int FAIL = -1;
  public static final int PARENT_COMPANY_ID = 1;

	//T_OpGroup	OpGroupLevel            SET_S_OPGROUPLEVEL����Ա���Ӧ�ļ���
	public static final String OPGROUP_LEVEL_PARENT_COMPANY	= "C";		//�ܹ�˾����Ա��
	public static final String OPGROUP_LEVEL_FILIALE		= "B";		//�ֹ�˾����Ա��
	public static final String OPGROUP_LEVEL_STREET			= "S";		//�ֵ�վ����Ա��

	//T_Organization	OrgType         SET_S_ORGANIZATIONORGTYPE�ڵ�����
	public static final String ORGANIZATION_TYPE_PARENT_COMPANY	= "C";		//�ܹ�˾
	public static final String ORGANIZATION_TYPE_DEPARTMENT		= "D";		//����
	public static final String ORGANIZATION_TYPE_FILIALE		= "B";		//�ֹ�˾
	public static final String ORGANIZATION_TYPE_PARTENER		= "P";		//�������
	public static final String ORGANIZATION_TYPE_SHOP			= "O";		//�ŵ�
	public static final String ORGANIZATION_TYPE_STREET			= "S";		//�ֵ�վ

	//T_Organization	OrgSubType             SET_S_ORGANIZATIONORGCATEGORY��֯�����ڵ����͵�ϸ����Ϣ
	public static final String ORGANIZATION_ORGSUBTYPE_ENGINEER			= "E";		//��ʦ��
	public static final String ORGANIZATION_ORGSUBTYPE_MARKET			= "M";		//�г���
	public static final String ORGANIZATION_ORGSUBTYPE_MATERIAL			= "L";		//���ʲ�
	public static final String ORGANIZATION_ORGSUBTYPE_CUSTOMER_SERVICE	= "C";		//�ͷ�����
	public static final String ORGANIZATION_ORGSUBTYPE_CALL_CENTER		= "T";		//�绰����
	public static final String ORGANIZATION_ORGSUBTYPE_FINANCIAL		= "F";		//Ӫ������
	public static final String ORGANIZATION_ORGSUBTYPE_FRONT_MAINTAIN	= "D";		//ǰ����ά
	public static final String ORGANIZATION_ORGSUBTYPE_OPERATION_ACCEPT	= "I";		//ҵ������
	public static final String ORGANIZATION_ORGSUBTYPE_FILIALE_MAINTAIN	= "B";		//�ֹ�˾��ά��

    //T_Organization	OrgSubType             SET_S_ORGANIZATIONPARTNERCATEGORY
	public static final String ORGANIZATION_ORGSUBTYPE_PROXY		= "S";		//������
	public static final String ORGANIZATION_ORGSUBTYPE_INSTALL		= "Z";		//��װ�����ṩ��
	public static final String ORGANIZATION_ORGSUBTYPE_PAYMENT		= "P";		//��������
	public static final String ORGANIZATION_ORGSUBTYPE_PRODUCT		= "A";		//��Ʒ�ṩ��
	public static final String ORGANIZATION_ORGSUBTYPE_DEVICE		= "G";		//�豸�ṩ��

	//T_SystemLog	LogClass                SET_S_LOGCLASS��־����
	public static final String SYSTEMLOG_LOGCLASS_NORMAL	= "N";		//һ�������Ϣ
	public static final String SYSTEMLOG_LOGCLASS_KEY		= "C";		//�ؼ�������Ϣ
	public static final String SYSTEMLOG_LOGCLASS_WARNING	= "W";		//������Ϣ
	public static final String SYSTEMLOG_LOGCLASS_ERROR		= "E";		//������Ϣ

	//T_SystemLog	LogType                 SET_S_LOGTYPE��־��Ϣ
	public static final String SYSTEMLOG_LOGTYPE_SYSTEM			= "S";		//ϵͳ
	public static final String SYSTEMLOG_LOGTYPE_APPLICATION	= "A";		//Ӧ�ó���

	//T_OrgGovernedDistrict	Flag            SET_S_ORGGOVERNEDDISTRICTFLAG��֯�������ͱ��
	public static final String ORGGOVERNEDDISTRICT_FLAG_FILIALE		= "S";		//�ֹ�˾��Ͻ����
	public static final String ORGGOVERNEDDISTRICT_FLAG_DISTRICT	= "T";		//����Ͻ�Ľֵ�
	//T_DistrictSetting SET_G_DISTRICTTYPE����������	P	ʡ��ֱϽ��
	public static final String 	DISTRICTSETTING_TYPE_CITY		= "C";		//�ء���
	public static final String 	DISTRICTSETTING_TYPE_DISTRICT	= "T";		//������
	
	
	//����Ϊ�����õ� ��add by zhouxushun , 2005-12-09
	//SET_C_BIDIMCONFIGSETTINGCONFIGTYPE
	public static final String SERVEY_CALL_BACK="C";						//�ط�
	public static final String SERVEY_DIAGNOSIS="D";						//���
	public static final String SERVEY_MARKET_INFO="M";						//�г�
	//SET_C_BIDIMCONFIGSETTINGCONFIGSUBTYPE
	public static final String SERVEY_CALL_BACK_SUB_C="C";					//Ͷ�߻ط�
	public static final String SERVEY_CALL_BACK_SUB_N="N";					//��ͨ���
	public static final String SERVEY_CALL_BACK_SUB_O="O";					//�����ط�
	public static final String SERVEY_CALL_BACK_SUB_P="P";					//רҵ���
	public static final String SERVEY_CALL_BACK_SUB_R="R";					//���޻ط�
	//SET_C_BIDIMCONFIGSETTINGVALUETYPE
	public static final String SERVEY_TAGTYPE_SELECT="S";				//����ѡ���
	public static final String SERVEY_TAGTYPE_CHECKBOX="C";			//��ѡ��
	public static final String SERVEY_TAGTYPE_TEXT="M";                  //�ı���
	public static final String SERVEY_TAGTYPE_RADIO="R";                //��ѡ��
	public static final String SERVEY_TAGTYPE_DATE="D";                //���ڿ�
	
	//SET_G_GENERALSTATUS
	public static final String GENERAL_STATUS_VALIDATE="V";					//��Ч
	
	//SET_G_YESNOFLAG
	public static final String GENERAL_FLAG_Y="Y";							//��
	public static final String GENERAL_FLAG_N="N";							//��
	
	//SET_V_CUSTSERVICEINTERACTIONSTATUS
	public static final String CUSTSERVICEINTERACTION_STATUS_NEW = "N";             //ҵ��������״̬������
	
    //	SET_F_AIREFERTYPE
	public static final String AIREFERTYPE_M = "M"; 						//����-�ۺ�������Դ���ͣ���������
	public static final String AIREFERTYPE_A = "A"; 						//����-�ۺ�������Դ���ͣ����ʲ���
	public static final String AIREFERTYPE_P = "P"; 						//����-�ۺ�������Դ���ͣ����޵�

    //	SET_F_PAYMENTRECORDSOURCETYPE
	public static final String PAYMENTRECORDSOURCETYPE_OTHER = "O";			//֧����¼-֧����¼��Դ���ͣ�����
	public static final String PAYMENTRECORDSOURCETYPE_REPAIR = "P";		//֧����¼-֧����¼��Դ���ͣ����޵�
	public static final String PAYMENTRECORDSOURCETYPE_ACCEPT = "M"; 		//֧����¼-֧����¼��Դ���ͣ�����
     
//	SET_F_BRFEETYPE
	public static final String BRFEETYPE_PREPAY = "P"; 					//Ӫҵ�������Ԥ�յ����ӷ�
}