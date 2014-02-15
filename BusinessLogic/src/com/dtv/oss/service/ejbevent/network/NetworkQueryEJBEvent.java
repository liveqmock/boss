package com.dtv.oss.service.ejbevent.network;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class NetworkQueryEJBEvent extends QueryEJBEvent {

	 
	 
	 

	 
	
	public static final int LDAP_PROCESS_LOG_QUERY = 328;//��ѯldap������־
	
	public static final int LDAP_HOST_QUERY = 330;//��ѯldap����
	
	public static final int LDAP_PRODUCT_QUERY = 331;//��ѯLDAP ��Ʒ
	
	public static final int LDAP_COMMAND_QUERY = 332;//��ѯLDAP ����
	
	public static final int LDAP_EVENTCMDMAP_QUERY = 333;//��ѯLDAD�¼�����ӳ��
	
	public static final int LDAP_SMSPRODTOPROD_QUERY = 334;//��ѯsms��Ʒ��ldap��ƷӰ���ϵ
	
	public static final int LDAP_ATTR_QUERY = 335;//��ѯLDAP����
	
	public static final int ORLE_ORGANIZATION_QUERY = 336;//��ѯ��֯��ɫ
	
	public static final int Bill_BOARD_QUERY = 337;//��ѯ��ʷ������Ϣ
	
	public static final int SYSTEM_SETTING_QUERY = 338;//ϵͳȫ�����ò�ѯ
	
	public static final int LDAP_EVENT_QUERY = 340;//ldap������¼
	
	public static final int LDAP_COND_QUERY = 341;//ldap������ѯ
	 
 
	public NetworkQueryEJBEvent() {
		super();
	}

	public NetworkQueryEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
	}
}
