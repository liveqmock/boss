package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class ConfigQueryEJBEvent extends QueryEJBEvent {

	public static final int CONFIG_SERVICEINFO_QUERY = 1555; // ҵ�����ѯ

 
	
	
 
	public static final int CONFIG_SERVICERELATION_QUERY = 1556; // ҵ�����������ϵ��ѯ

	public static final int CONFIG_SERVICERESOURCE_QUERY = 1557; // ҵ����Դ��ѯ

	public static final int CONFIG_SERVICERESOURCEDETAIL_QUERY = 1558; // ҵ����Դ��ϸ��ѯ

	public static final int CONFIG_PRODUCT_BASE_INFO_QUERY = 1600; // ��Ʒ������Ϣ����

	public static final int CONFIG_PRODUCT_PACKAGE_BASE_INFO_QUERY = 1602; // ��Ʒ������Ϣ����

	public static final int CONFIG_PRODUCT_DEPENDENCY_QUERY = 1605; // ��Ʒ������ϵ����

	public static final int CONFIG_PRODUCT_PROPERTY_QUERY = 1610; // ��Ʒ���Բ���

	public static final int QUERY_POINTS_ACTIVITY = 305; // ���ֻ

	public static final int QUERY_POINTS_GOODS = 306;// ������Ʒ

	public static final int QUERY_POINTS_RULE = 307;// ���ֹ���
	
	public static final int QUERY_POINTS_COND = 4000;// ��������

	public static final int QUERY_BIDIM_CONFIG = 308;// 2ά���ò�ѯ

	public static final int QUERY_BIDIM_CONFIG_WITH_VALUES = 3081;// 2ά���ò�ѯ

	public static final int QUERY_BIDIM_CONFIG_VALUE = 309;// 2ά����value��ѯ

	public static final int QUERY_DISTRICT_SETTING = 101;

	public static final int QUERY_ORGANIZATION = 102;

	public static final int QUERY_CUMU_RULE = 310;// ��ѯ�ۼӹ���

	public static final int QUERY_SEGMENT = 311;// ��ѯ�ۼӹ���

	public static final int QUERY_SEGMENT_DISTRICT = 312;// ��ѯ�г���������Ӧ����������

	public static final int CONFIG_PAYMENT_TYPE_QUERY_LIST = 313;// ��ѯ�ֿ�ȯ����

	public static final int CONFIG_BRCONDITION_QUERY = 314;// ��ѯ��������

	public static final int CONFIG_METHOD_OF_PAYMENT_QUERY = 315;// ��ѯ���ѷ�ʽ
	
	public static final int CONFIG_BILLING_RULE_QUERY = 316;//��ѯ֧������
	
	public static final int CONFIG_OPGROUP_QUERY = 317;//��ѯ����Ա��
	
	public static final int OPERATOR_SUB_GROUP_QUERY = 318;//��ѯ����֯�µĲ���Ա
	
	public static final int MANUAL_TRANSFER_QUERY = 319;//�ֹ���ת��ѯ
	
	public static final int CAMPAIGN_QUERY = 325;//��ѯ�����
	
	 
	
	public static final int ORLE_ORGANIZATION_QUERY = 336;//��ѯ��֯��ɫ
	
	public static final int Bill_BOARD_QUERY = 337;//��ѯ��ʷ������Ϣ
	
	public static final int SYSTEM_SETTING_QUERY = 338;//ϵͳȫ�����ò�ѯ
	
	public static final int CSI_REASON_QUERY = 340;//����ԭ���ѯ
	
    public static final int CSI_TYPE_REASON2DEVICE = 341;//�豸��; 
	
	public static final int SWAP_DEVICE_REASON = 342;//�豸����
	
	public static final int IPPV_QUERY = 326;// ��ѯIPPVǮ��
	
	public static final int DTV_MIGRATION_QUERY = 327;// ��ѯƽ��С��
	
	 
	 
 
	public ConfigQueryEJBEvent() {
		super();
	}

	public ConfigQueryEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
	}
}
