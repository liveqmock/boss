package com.dtv.oss.service.ejbevent.network;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class NetworkQueryEJBEvent extends QueryEJBEvent {

	 
	 
	 

	 
	
	public static final int LDAP_PROCESS_LOG_QUERY = 328;//查询ldap处理日志
	
	public static final int LDAP_HOST_QUERY = 330;//查询ldap主机
	
	public static final int LDAP_PRODUCT_QUERY = 331;//查询LDAP 产品
	
	public static final int LDAP_COMMAND_QUERY = 332;//查询LDAP 命令
	
	public static final int LDAP_EVENTCMDMAP_QUERY = 333;//查询LDAD事件命令映射
	
	public static final int LDAP_SMSPRODTOPROD_QUERY = 334;//查询sms产品与ldap产品影射关系
	
	public static final int LDAP_ATTR_QUERY = 335;//查询LDAP属性
	
	public static final int ORLE_ORGANIZATION_QUERY = 336;//查询组织角色
	
	public static final int Bill_BOARD_QUERY = 337;//查询历史公告信息
	
	public static final int SYSTEM_SETTING_QUERY = 338;//系统全局配置查询
	
	public static final int LDAP_EVENT_QUERY = 340;//ldap命令处理记录
	
	public static final int LDAP_COND_QUERY = 341;//ldap条件查询
	 
 
	public NetworkQueryEJBEvent() {
		super();
	}

	public NetworkQueryEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
	}
}
